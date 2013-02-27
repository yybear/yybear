package org.bear.framework.cache.redis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.bear.framework.Constants;
import org.bear.framework.cache.AbstractCache;
import org.bear.framework.cache.KeyGenerator;
import org.bear.framework.cache.RedisCache;
import org.bear.framework.support.redis.NSRedisTemplate;
import org.bear.framework.support.redis.RedisUtils;
import org.bear.framework.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.SerializationUtils;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-26
 */
public class RedisCacheImpl extends AbstractCache<RedisCacheConfig> implements RedisCache{

	public static final char SEPARATOR = Constants.REDIS_SEPARATOR;
    public static final String CACHE_REGIONS = "cache_regions";
    public static final String CACHE_KEYS = "_keys";
    public static final String CACHE_STAT = "_stat";
    public static final int PAGE_SIZE = 256;
    public static final int CLEANUP_PERIOD_SECONDS = 30;
    
    private static Logger LOG = LoggerFactory.getLogger(RedisCacheImpl.class);
    private final NSRedisTemplate redis;
    private final HashOperations<String, String, RedisCacheConfig> configOp;
    private final RedisSerializer keySerializer;
    private final RedisSerializer valueSerializer;
    private final byte[] ns;
    private final byte[] keyListKey;
    private KeyGenerator keyGenerator = DefaultKeyGenerator.INSTANCE;

    public RedisCacheImpl(RedisCacheConfig config, RedisTemplate redis) {
        super(config);
        keySerializer = redis.getKeySerializer();
        valueSerializer = redis.getValueSerializer();
        ns = keySerializer.serialize(config.getNamespace() + SEPARATOR);
        keyListKey = rawKeyWithNs(CACHE_KEYS);
        configOp = redis.opsForHash();
        this.redis = initRedis(redis);
        writeRegionConfig();
    }

    @Override
    public <K, Integer> Map<K, Integer> getExpires(Object... keys) {
        return getExpires(Arrays.asList(keys));
    }

    @Override
    public <F, V> Map<F, V> getHash(Object key, Object... fields) {
        return getHash(key, fields == null ? null : Arrays.asList(fields));
    }

    @Override
    public <K, F, V> Map<K, Map<F, V>> getHashs(Object... keys) {
        return getHashs(Arrays.asList(keys));
    }

    @Override
    public <K, F, V> Map<K, Map<F, V>> getHashs(Collection keys, Object... fields) {
        return getHashs(keys, Arrays.asList(fields));
    }

    @Override
    public boolean putHash(Object key, Object field, Object value) {
        return putHash(key, field, value, -1, -1);
    }

    @Override
    public boolean putHashIfAbsent(Object key, Object field, Object value) {
        return putHashIfAbsent(key, field, value, -1, -1);
    }

    @Override
    public void putHash(Object key, Map<Object, Object> hashes) {
        putHash(key, hashes, -1, -1);
    }

    @Override
    public long pushListLeft(Object key, Object... values) {
        return pushListLeft(key, Arrays.asList(values));
    }

    @Override
    public long pushListLeft(Object key, Object value, int ttl, int tti) {
        return pushListLeft(key, Collections.singletonList(value), ttl, tti);
    }

    @Override
    public long pushListLeft(Object key, Collection<Object> values) {
        return pushListLeft(key, values, -1, -1);
    }

    @Override
    public long pushListRight(Object key, Object... values) {
        return pushListRight(key, Arrays.asList(values));
    }

    @Override
    public long pushListRight(Object key, Collection<Object> values) {
        return pushListRight(key, values, -1, -1);
    }

    @Override
    public long pushListRight(Object key, Object value, int ttl, int tti) {
        return pushListRight(key, Collections.singletonList(value), ttl, tti);
    }

    @Override
    public long putSet(Object key, Object... values) {
        return putSet(key, Arrays.asList(values));
    }

    @Override
    public long putSet(Object key, Object value, int ttl, int tti) {
        return putSet(key, Collections.singletonList(value), ttl, tti);
    }

    @Override
    public long putSet(Object key, Collection<Object> values) {
        return putSet(key, values, -1, -1);
    }

    @Override
    public long putZSet(Object key, Object value, double score) {
        return putZSet(key, value, score, -1, -1);
    }

    @Override
    public long putZSet(Object key, Map<Object, Double> map) {
        return putZSet(key, map, -1, -1);
    }

    private byte[] trimNs(byte[] key) {
        return Arrays.copyOfRange(key, ns.length, key.length);
    }

    private NSRedisTemplate initRedis(RedisTemplate redis0) {
    	// 定义监听器
        final RedisKeylistener listener = new RedisKeylistener() {
            @Override
            public void onWrite(RedisConnection conn, byte[]... keys) {
                int ttl = preTtl(-1);
                if (ttl > 0) {
                    RedisUtils.expire(conn, ttl, keys);
                }
                if (config.isEnableKeyList()) {
                    Map<byte[], Double> map = new HashMap<byte[], Double>(keys.length);
                    double score = ttl > 0 ? toScore(ttl, 0) : 0;
                    for (byte[] key : keys) {
                        map.put(trimNs(key), score);
                    }
                    RedisUtils.zadd(conn, keyListKey, map);
                }
            }

            @Override
            public void onWrite(RedisConnection conn, byte[] key, int ttl) {
                if (config.isEnableKeyList()) {
                    byte[] bytes = trimNs(key);
                    conn.zAdd(keyListKey, ttl > 0 ? toScore(ttl, 0) : 0, bytes);
                }
            }

            @Override
            public void onDelete(RedisConnection conn, byte[]... keys) {
                if (config.isEnableKeyList()) {
                    byte[][] rawKeys = new byte[keys.length][];
                    int i = 0;
                    for (byte[] key : keys) {
                        rawKeys[i++] = trimNs(key);
                    }
                    RedisUtils.zrem(conn, keyListKey, rawKeys);
                }
            }

            @Override
            public long beforeExpire(RedisConnection conn, byte[] key, long seconds) {
                return preTtl((int) seconds);
            }
        };
    	NSRedisTemplate redis = new NSRedisTemplate() {
            @Override
            protected RedisConnection decorateRedisConnection(RedisConnection conn) {
                return new RedisConnectionWrapper(conn, listener);
            }
        };
        redis.setConnectionFactory(redis0.getConnectionFactory());
        redis.setDefaultSerializer(new RedisSerializer() {
            @Override
            public byte[] serialize(Object o) throws SerializationException {
                return rawValue(o);
            }

            @Override
            public Object deserialize(byte[] bytes) throws SerializationException {
                return deValue(bytes);
            }
        });
        redis.setKeySerializer(new RedisSerializer() {
            @Override
            public byte[] serialize(Object o) throws SerializationException {
                return rawKeyWithNs(o);
            }

            @Override
            public Object deserialize(byte[] bytes) throws SerializationException {
                return deKey(bytes);
            }
        });
        redis.setHashKeySerializer(new RedisSerializer() {
            @Override
            public byte[] serialize(Object o) throws SerializationException {
                return rawKey0(o);
            }

            @Override
            public Object deserialize(byte[] bytes) throws SerializationException {
                return deKey(bytes);
            }
        });
        redis.afterPropertiesSet();
        return redis;
    }

    @Override
    public boolean exists(final Object key) {
        return (Boolean) redis.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
                return conn.exists(rawKeyWithNs(key));
            }
        }, false);
    }

    @Override
    public Object get(final Object key) {
        return redis.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
                return deValue(conn.get(rawKeyWithNs(key)));
            }
        }, false);
    }

    @Override
    public <K, T> Map<K, T> gets(final Collection keys) {
        if (keys.isEmpty()) {
            return Collections.emptyMap();
        }
        return (Map) redis.execute(new RedisCallback() {
            public Object doInRedis(RedisConnection conn) {
                return toMap(keys, conn.mGet(rawKeysWithNs(keys)));
            }
        }, false);
    }

    @Override
    public void put(final Object key, final Object value, final int ttl0, final int tti0) {
        redis.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
                int ttl = preTtl(ttl0);
                int tti = preTti(tti0);
                if (ttl > 0) {
                    conn.setEx(rawKeyWithNs(key), ttl, rawValue(value));
                } else {
                    conn.set(rawKeyWithNs(key), rawValue(value));
                }
                addToKeyList(conn, key, ttl, tti);
                return null;
            }
        }, false);
    }

    @Override
    public void put(final Map map, final int ttl0, final int tti0) {
        if (map.isEmpty()) {
            return;
        }
        redis.pipeline(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
                int ttl = preTtl(ttl0);
                int tti = preTti(tti0);
                for (Map.Entry entry : (Set<Map.Entry>) map.entrySet()) {
                    byte[] key = rawKeyWithNs(entry.getKey());
                    byte[] value = rawValue(entry.getValue());
                    if (ttl > 0) {
                        conn.setEx(key, ttl, value);
                    } else {
                        conn.set(key, value);
                    }
                    addToKeyList(conn, entry.getKey(), ttl, tti);
                }
                return null;
            }
        });
    }

    @Override
    public boolean putIfAbsent(final Object key, final Object value, final int ttl0, final int tti0) {
        return (Boolean) redis.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
                byte[] byteKey = rawKeyWithNs(key);
                int ttl = preTtl(ttl0);
                int tti = preTti(tti0);
                Boolean ok = conn.setNX(byteKey, rawValue(value));
                if (ok) {
                    if (ttl > 0) {
                        conn.expire(byteKey, ttl);
                    }
                    addToKeyList(conn, key, ttl, tti);
                }
                return ok;
            }
        }, false);
    }

    @Override
    public void evict(final Collection keys) {
        if (keys.isEmpty()) {
            return;
        }
        redis.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
                conn.del(rawKeysWithNs(keys));
                if (config.isEnableKeyList()) {
                    RedisUtils.zrem(conn, keyListKey, rawKeys(keys));
                }
                return null;
            }
        }, false);
        statistics.incrementEvictions(keys.size());
    }

    @Override
    public void clear() {
        if (config.isEnableKeyList()) {
            if (!config.isClearable()) {
                throw new IllegalStateException("region " + config.getRegion() + " is not clearable");
            }
            redis.execute(new RedisCallback() {
                @Override
                public Object doInRedis(RedisConnection conn) throws DataAccessException {
                    Set<byte[]> keys;
                    do {
                        keys = conn.zRange(keyListKey, 0, PAGE_SIZE - 1);
                        if (!keys.isEmpty()) {
                            conn.del(bytesArrWithNs(keys));
                            RedisUtils.zrem(conn, keyListKey, keys);
                        }
                    } while (keys.size() == PAGE_SIZE);
                    return null;
                }
            }, false);
            return;
        }
        throw new UnsupportedOperationException("KeyList is not enable");
    }

    @Override
    public int getSize() {
        if (config.isEnableKeyList()) {
            return (Integer) redis.execute(new RedisCallback() {
                @Override
                public Object doInRedis(RedisConnection conn) throws DataAccessException {
                    return conn.zCard(keyListKey).intValue();
                }
            }, false);
        }
        return -1;
    }

    @Override
    public <T> Collection<T> getKeys(final int start, final int size) {
        if (config.isEnableKeyList()) {
            return (Collection<T>) redis.execute(new RedisCallback() {
                @Override
                public Object doInRedis(RedisConnection conn) throws DataAccessException {
                    return SerializationUtils.deserialize(conn.zRange(keyListKey, start, size <= 0 ? -1 : start + size), keySerializer);
                }
            }, false);
        }
        throw new UnsupportedOperationException("KeyList is not enable");
    }

    //----------redisCache----------
    @Override
    public <K, V> RedisOperations<K, V> redis() {
        return redis;
    }

    @Override
    public <K, V> ValueOperations<K, V> valueOp() {
        return redis.opsForValue();
    }

    @Override
    public <K, F, V> HashOperations<K, F, V> hashOp() {
        return redis.opsForHash();
    }

    @Override
    public <K, V> ListOperations<K, V> listOp() {
        return redis.opsForList();
    }

    @Override
    public <K, V> SetOperations<K, V> setOp() {
        return redis.opsForSet();
    }

    @Override
    public <K, V> ZSetOperations<K, V> zSetOp() {
        return redis.opsForZSet();
    }

    @Override
    public void rename(final Object oldKey, final Object newKey) {
        redis.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
                conn.rename(rawKeyWithNs(oldKey), rawKeyWithNs(newKey));
                conn.zRem(keyListKey, rawKey(oldKey));
                addToKeyList(conn, newKey, preTtl(-1), preTtl(-1));
                return null;
            }
        }, false);
    }

    @Override
    public boolean expire(final Object key, final int ttl0, final int tti0) {
        return (Boolean) redis.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
                int ttl = preTtl(ttl0);
                int tti = preTti(tti0);
                byte[] byteKey = rawKeyWithNs(key);
                Boolean ok = ttl > 0 ? conn.expire(byteKey, ttl) : conn.persist(byteKey);
                if (ok) {
                    addToKeyList(conn, key, ttl, tti);
                }
                return ok;
            }
        }, false);
    }

    @Override
    public <K, Integer> Map<K, Integer> getExpires(final Collection keys) {
        List<Long> list = redis.pipeline(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
                for (Object key : keys) {
                    conn.ttl(rawKeyWithNs(key));
                }
                return null;
            }
        });
        Map map = new HashMap(keys.size());
        int i = 0;
        for (Object key : keys) {
            map.put(key, list.get(i++));
        }
        return map;
    }

    @Override
    public long incrBy(final Object key, final int delta) {//todo:bug jdk序列化不支持
        return (Long) redis.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
                byte[] byteKey = rawKeyWithNs(key);
                Long ret = delta > 0 ? conn.incrBy(byteKey, delta) : conn.decrBy(byteKey, Math.abs(delta));
                if (ret == delta) {
                    addToKeyList(conn, key, preTtl(-1), preTti(-1));
                }
                return ret;
            }
        }, false);
    }

    @Override
    public <F, V> Map<F, V> getHash(final Object key, final Collection fields) {
        return (Map) redis.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
                if (CollectionUtils.isEmpty(fields)) {
                    Map<byte[], byte[]> map = conn.hGetAll(rawKeyWithNs(key));
                    Map map1 = new HashMap(map.size());
                    for (Map.Entry<byte[], byte[]> entry : conn.hGetAll(rawKeyWithNs(key)).entrySet()) {
                        map1.put(deKey(entry.getKey()), deValue(entry.getValue()));
                    }
                    return map1;
                } else {
                    return toMap(fields, conn.hMGet(rawKeyWithNs(key), rawKeys(fields)));
                }
            }
        }, false);
    }

    @Override
    public <K, F, V> Map<K, Map<F, V>> getHashs(final Collection keys, final Collection fields) {
        if (keys.isEmpty()) {
            return Collections.emptyMap();
        }
        final boolean isAll = CollectionUtils.isEmpty(fields);
        List<List<byte[]>> list = redis.pipeline(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
                if (isAll) {
                    for (Object key : keys) {
                        RedisUtils.hGetAll(conn, rawKeyWithNs(key));
                    }
                } else {
                    byte[][] bytes = rawKeys(fields);
                    for (Object key : keys) {
                        RedisUtils.hMGet(conn, rawKeyWithNs(key), bytes);
                    }
                }
                return null;
            }
        });
        Map map = new HashMap(keys.size());
        int i = 0;
        for (Object key : keys) {
            List<byte[]> values = list.get(i++);
            Map map1 = new HashMap(values.size());
            if (!values.isEmpty()) {
                if (isAll) {
                    for (int j = 0, len = values.size(); j < len; ) {
                        map1.put(deKey(values.get(j++)), deValue(values.get(j++)));
                    }
                    map.put(key, map1);
                } else {
                    int j = 0;
                    for (Object field : fields) {
                        byte[] value = values.get(j++);
                        if (value != null) {
                            map1.put(field, deValue(value));
                        }
                    }
                }
            }
            if (!map1.isEmpty()) {
                map.put(key, map1);
            }
        }
        return map;
    }

    @Override
    public boolean putHash(final Object key, final Object field, final Object value, final int ttl0, final int tti0) {
        return (Boolean) redis.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
                byte[] byteKey = rawKeyWithNs(key);
                int ttl = preTtl(ttl0);
                int tti = preTti(tti0);
                Boolean ok = conn.hSet(byteKey, rawKey0(field), rawValue(value));
                if (ttl > 0) {
                    conn.expire(byteKey, ttl);
                }
                addToKeyList(conn, key, ttl, tti);
                return ok;
            }
        }, false);
    }

    @Override
    public boolean putHashIfAbsent(final Object key, final Object field, final Object value, final int ttl0, final int tti0) {
        return (Boolean) redis.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
                byte[] byteKey = rawKeyWithNs(key);
                int ttl = preTtl(ttl0);
                int tti = preTti(tti0);
                Boolean ok = conn.hSetNX(byteKey, rawKey0(field), rawValue(value));
                if (ok) {
                    if (ttl > 0) {
                        conn.expire(byteKey, ttl);
                    }
                    addToKeyList(conn, key, ttl, tti);
                }
                return ok;
            }
        }, false);
    }

    @Override
    public void putHash(final Object key, final Map<Object, Object> hashes, final int ttl0, final int tti0) {
        if (MapUtils.isEmpty(hashes)) {
            return;
        }
        redis.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
                byte[] byteKey = rawKeyWithNs(key);
                int ttl = preTtl(ttl0);
                int tti = preTti(tti0);
                Map<byte[], byte[]> map = new HashMap<byte[], byte[]>(hashes.size());
                for (Map.Entry entry : hashes.entrySet()) {
                    map.put(rawKey0(entry.getKey()), rawValue(entry.getValue()));
                }
                conn.hMSet(byteKey, map);
                if (ttl > 0) {
                    conn.expire(byteKey, ttl);
                }
                addToKeyList(conn, key, ttl, tti);
                return null;
            }
        }, false);
    }

    @Override
    public long pushListLeft(final Object key, final Collection<Object> values, final int ttl0, final int tti0) {
        return (Long) redis.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
                byte[] byteKey = rawKeyWithNs(key);
                int ttl = preTtl(ttl0);
                int tti = preTti(tti0);
                Long ret = RedisUtils.lpush(conn, byteKey, rawValues(values));
                if (ttl > 0) {
                    conn.expire(byteKey, ttl);
                }
                addToKeyList(conn, key, ttl, tti);
                return ret;
            }
        }, false);
    }

    @Override
    public long pushListRight(final Object key, final Collection<Object> values, final int ttl0, final int tti0) {
        return (Long) redis.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
                byte[] byteKey = rawKeyWithNs(key);
                int ttl = preTtl(ttl0);
                int tti = preTti(tti0);
                Long ret = RedisUtils.rpush(conn, byteKey, rawValues(values));
                if (ttl > 0) {
                    conn.expire(byteKey, ttl);
                }
                addToKeyList(conn, key, ttl, tti);
                return ret;
            }
        }, false);
    }

    @Override
    public long putSet(final Object key, final Collection<Object> values, final int ttl0, final int tti0) {
        return (Long) redis.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
                byte[] byteKey = rawKeyWithNs(key);
                int ttl = preTtl(ttl0);
                int tti = preTti(tti0);
                Long ret = RedisUtils.sadd(conn, byteKey, rawValues(values));
                if (ttl > 0) {
                    conn.expire(byteKey, ttl);
                }
                addToKeyList(conn, key, ttl, tti);
                return ret;
            }
        }, false);
    }

    @Override
    public long putZSet(final Object key, final Object value, final double score, final int ttl0, final int tti0) {
        return (Long) redis.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
                byte[] byteKey = rawKeyWithNs(key);
                int ttl = preTtl(ttl0);
                int tti = preTti(tti0);
                Long ret = RedisUtils.zadd(conn, byteKey, score, rawValue(value));
                if (ttl > 0) {
                    conn.expire(byteKey, ttl);
                }
                addToKeyList(conn, key, ttl, tti);
                return ret;
            }
        }, false);
    }

    @Override
    public long putZSet(final Object key, final Map<Object, Double> map, final int ttl0, final int tti0) {
        return (Long) redis.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
                byte[] byteKey = rawKeyWithNs(key);
                int ttl = preTtl(ttl0);
                int tti = preTti(tti0);
                Map<byte[], Double> map0 = new HashMap<byte[], Double>(map.size());
                for (Map.Entry<Object, Double> entry : map.entrySet()) {
                    map0.put(rawValue(entry.getKey()), entry.getValue());
                }
                Long ret = RedisUtils.zadd(conn, byteKey, map0);
                if (ttl > 0) {
                    conn.expire(byteKey, ttl);
                }
                addToKeyList(conn, key, ttl, tti);
                return ret;
            }
        }, false);
    }

    @Override
	public long zrem(final Object key, final Object... members) {
    	return (Long) redis.execute(new RedisCallback() {
    		@Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
    			List<byte[]> list = new ArrayList<byte[]>();
    			for(Object m : members) {
    				list.add(rawValue(m));
    			}
    			Long ret = RedisUtils.zrem(conn, rawKeyWithNs(key), list);
    			return ret;
    		}
    	}, false);
	}

	@Override
	public long zremRangeByScore(final Object key, final double min, final double max) {
		return (Long) redis.execute(new RedisCallback() {
    		@Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
    			Long ret = RedisUtils.zrem(conn, rawKeyWithNs(key), min, max);
    			return ret;
    		}
    	}, false);
	}

	@Override
	public <V> List<V> zrange(final Object key, final int start, final int limit) {
		return (List) redis.execute(new RedisCallback() {
    		@Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
    			Set<byte[]> ret = RedisUtils.zRange(conn, rawKeyWithNs(key), start, limit);
    			
    			List list = new ArrayList();
    			for(Iterator<byte[]> it = ret.iterator(); it.hasNext();) {
    				list.add(deValue(it.next()));
    			}
    			return list;
    		}
    	}, false);
	}
	
	@Override
	public <V> List<V> zrangeByScore(final Object key, final int start, final int limit, final double min, final double max) {
		return (List) redis.execute(new RedisCallback() {
    		@Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
    			Set<byte[]> ret = RedisUtils.zRangeByScore(conn, rawKeyWithNs(key), min, 
    					max, start, limit);
    			
    			List list = new ArrayList();
    			for(Iterator<byte[]> it = ret.iterator(); it.hasNext();) {
    				list.add(deValue(it.next()));
    			}
    			return list;
    		}
    	}, false);
	}

	@Override
	public Long zcard(final Object key) {
		return (Long) redis.execute(new RedisCallback() {
    		@Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
    			Long ret = RedisUtils.zcard(conn, rawKeyWithNs(key));
    			return ret;
    		}
    	}, false);
	}

	@Override
	public Long zcount(final Object key, final double min, final double max) {
		return (Long) redis.execute(new RedisCallback() {
    		@Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
    			Long ret = RedisUtils.zcount(conn, rawKeyWithNs(key), min, max);
    			return ret;
    		}
    	}, false);
	}

	@Override
	public long getTTLTime(final Object key) {
		return (Long) redis.execute(new RedisCallback() {
    		@Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
    			Long ret = RedisUtils.ttl(conn, rawKeyWithNs(key));
    			return ret;
    		}
    	}, false);
	}

	//------------------------------
    public void cleanup() {
        LOG.debug("Start to cleanup redis idle queue");
        redis.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
                double nowCheckpoint = toScore(0, 0);
                Set<RedisZSetCommands.Tuple> tuples;
                do {
                    tuples = conn.zRangeByScoreWithScores(keyListKey, 1, nowCheckpoint, 0, PAGE_SIZE - 1);
                    LOG.debug("Found [{}] keys may be expired", tuples.size());
                    int size = tuples.size();
                    byte[][] keys = new byte[size][];
                    byte[][] keysWithNs = new byte[size][];
                    Integer[] ttis = new Integer[size];
                    int i = 0;
                    for (RedisZSetCommands.Tuple tuple : tuples) {
                        byte[] key = tuple.getValue();
                        keys[i] = key;
                        keysWithNs[i] = bytesWithNs(key);
                        ttis[i] = (int) ((tuple.getScore() - Math.floor(tuple.getScore())) * 1e8);
                        i++;
                    }
                    List<Long> idleSeconds = RedisUtils.getIdleSeconds(conn, keysWithNs);
                    i = 0;
                    List<byte[]> zDels = new ArrayList<byte[]>(size);
                    List<byte[]> kDels = new ArrayList<byte[]>(size);
                    Map<byte[], Double> zRefreshs = new HashMap<byte[], Double>(size);
                    for (int tti : ttis) {
                        Long idleSecond = idleSeconds.get(i);
                        byte[] key = keys[i];
                        byte[] keyWithNs = keysWithNs[i];
                        if (tti > 0) {
                            if (idleSecond == null || idleSecond > tti) {
                                if (idleSecond != null) {
                                    kDels.add(keyWithNs);
                                }
                                zDels.add(keys[i]);
                                LOG.debug("Delete expired key [{}]", deKey(keyWithNs));
                            } else {
                                zRefreshs.put(key, toScore(tti - idleSecond.intValue(), tti));
                                LOG.debug("refresh key [{},next check [{}] seconds later]", deKey(keyWithNs), tti - idleSecond);
                            }
                        } else {
                            zDels.add(key);
                        }
                        i++;
                    }
                    if (!kDels.isEmpty()) {
                        conn.del(kDels.toArray(new byte[kDels.size()][]));
                    }
                    if (!zDels.isEmpty()) {
                        RedisUtils.zrem(conn, keyListKey, zDels);
                        statistics.incrementExpires(zDels.size());
                    }
                    if (!zRefreshs.isEmpty()) {
                        RedisUtils.zadd(conn, keyListKey, zRefreshs);
                    }
                } while (tuples.size() == PAGE_SIZE);
                checkMaxKeys(conn);
                updateStatistics(conn);
                return null;
            }
        }, false);
        if (!configOp.hasKey(CACHE_REGIONS, getRegion())) {
            writeRegionConfig();
        }
    }

    private void writeRegionConfig() {
        configOp.put(CACHE_REGIONS, getRegion(), config);
    }

    public void checkMaxKeys(RedisConnection conn) {
        int max = config.getMaxKeys();
        if (config.isEnableKeyList() && max > 0) {
            int size = getSize();
            int overflow = size - max;
            if (overflow / (float) max > config.getOverflowClearFactor()) {
                Set<byte[]> keys;
                boolean clearNoTtl = false;
                do {
                    keys = RedisUtils.zRangeByScore(conn, keyListKey, (keySerializer.serialize(clearNoTtl ? "0" : "1")), keySerializer.serialize("+inf"), 0, PAGE_SIZE - 1);
                    if (!clearNoTtl && keys.isEmpty()) {
                        LOG.debug("With ttl keys not found,try to get no ttl keys");
                        clearNoTtl = true;
                        continue;
                    }
                    if (keys.isEmpty()) {
                        break;
                    }
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Delete overflow keys [{}]", SerializationUtils.deserialize(keys, keySerializer));
                    }
                    conn.del(bytesArrWithNs(keys));
                    RedisUtils.zrem(conn, keyListKey, keys);
                    overflow -= keys.size();
                } while (overflow > 0);
            }
        }
    }

    public void updateStatistics(RedisConnection conn) {
        final Map<byte[], byte[]> hashes = new LinkedHashMap<byte[], byte[]>();
        for (Map.Entry<String, Serializable> entry : getStatistics().toMap().entrySet()) {
            hashes.put(rawKey0(entry.getKey()), rawValue(entry.getValue()));
        }
        conn.hMSet(rawKeyWithNs(CACHE_STAT), hashes);
    }

    private byte[] rawKey0(Object key) {
        byte[] result = keySerializer.serialize(key);
        if (result != null) {
            statistics.incrementWriteBytes(result.length);
        } else {
            throw new NullPointerException("Cache key is required");
        }
        return result;
    }

    private byte[] rawKey(Object key) {
        return rawKey0(keyGenerator.generate(key));
    }

    private byte[][] rawKeys(Collection keys) {
        final byte[][] rawKeys = new byte[keys.size()][];
        int i = 0;
        for (Object key : keys) {
            rawKeys[i++] = rawKey(key);
        }
        return rawKeys;
    }

    private byte[] rawKeyWithNs(Object key) {
        return bytesWithNs(rawKey(key));
    }

    private byte[][] rawKeysWithNs(Collection keys) {
        final byte[][] rawKeys = new byte[keys.size()][];
        int i = 0;
        for (Object key : keys) {
            rawKeys[i++] = rawKeyWithNs(key);
        }
        return rawKeys;
    }

    private byte[] bytesWithNs(byte[] bytes) {
        int len = bytes.length, nsLen = ns.length;
        byte[] result = Arrays.copyOf(ns, nsLen + len);
        System.arraycopy(bytes, 0, result, nsLen, len);
        return result;
    }

    private byte[][] bytesArrWithNs(Collection<byte[]> keys) {
        final byte[][] rawKeys = new byte[keys.size()][];
        int i = 0;
        for (byte[] key : keys) {
            rawKeys[i++] = bytesWithNs(key);
        }
        return rawKeys;
    }

    private Object deKey(byte[] key) {
        Object result = keySerializer.deserialize(key);
        if (result != null) {
            statistics.incrementReadBytes(key.length);
            statistics.incrementHits(1);
        } else {
            statistics.incrementMisses(1);
        }
        return result;
    }

    private byte[] rawValue(Object value) {
        byte[] result = valueSerializer.serialize(value);
        if (result != null) {
            statistics.incrementWriteBytes(result.length);
        }
        return result;
    }

    private byte[][] rawValues(Collection values) {
        final byte[][] rawValues = new byte[values.size()][];
        int i = 0;
        for (Object value : values) {
            rawValues[i++] = rawValue(value);
        }
        return rawValues;
    }

    private Object deValue(byte[] value) {
        Object result = valueSerializer.deserialize(value);
        if (result != null) {
            statistics.incrementReadBytes(value.length);
            statistics.incrementHits(1);
        } else {
            statistics.incrementMisses(1);
        }
        return result;
    }

    private void putValue(Map map, Object key, byte[] value) {
        if (value != null) {
            map.put(key, deValue(value));
        } else {
            statistics.incrementMisses(1);
        }
    }

    private Map toMap(Collection keys, List<byte[]> values) {
        Map map = new HashMap(values.size());
        int i = 0;
        for (Object key : keys) {
            putValue(map, key, values.get(i++));
        }
        return map;
    }

    private void addToKeyList(RedisConnection conn, Object key, int ttl, int tti) {
        if (tti > 0) {
            conn.zAdd(keyListKey, toScore(tti, tti), rawKey(key));
        } else if (config.isEnableKeyList()) {
            conn.zAdd(keyListKey, ttl > 0 ? toScore(ttl, 0) : 0, rawKey(key));
        }
        statistics.incrementPuts(1);
    }

    /**
     * 分数由最多8位的检测周期号,和最多7位tti组成,因为redis支持的idle信息更新周期为10秒,所以除以1e8
     *
     * @param checkpointSecond 下次检查的秒数
     * @param tti              发呆时间
     * @return 分数
     */
    private static double toScore(int checkpointSecond, int tti) {
        return (DateUtils.getCurrentShortSecond() + checkpointSecond) / CLEANUP_PERIOD_SECONDS + tti / 1e8;
    }

}
