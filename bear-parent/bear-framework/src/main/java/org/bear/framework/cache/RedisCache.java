package org.bear.framework.cache;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-26
 */
public interface RedisCache extends Cache{
	public static final RedisSerializer DEFAULT_SERIALIZER = new JdkSerializationRedisSerializer();
	
	<K, V> RedisOperations<K, V> redis();

    <K, V> ValueOperations<K, V> valueOp();

    <K, F, V> HashOperations<K, F, V> hashOp();

    <K, V> ListOperations<K, V> listOp();

    <K, V> SetOperations<K, V> setOp();

    <K, V> ZSetOperations<K, V> zSetOp();

    void rename(Object oldKey, Object newKey);

    boolean expire(Object key, int ttl, int tti);

    <K, Integer> Map<K, Integer> getExpires(Object... keys);

    <K, Integer> Map<K, Integer> getExpires(Collection keys);

    long incrBy(Object key, int delta);

    <F, V> Map<F, V> getHash(Object key, Object... fields);

    <F, V> Map<F, V> getHash(Object key, Collection fields);

    <K, F, V> Map<K, Map<F, V>> getHashs(Object... keys);

    <K, F, V> Map<K, Map<F, V>> getHashs(Collection keys, Object... fields);

    <K, F, V> Map<K, Map<F, V>> getHashs(Collection keys, Collection fields);

    boolean putHash(Object key, Object field, Object value);

    boolean putHash(Object key, Object field, Object value, int ttl, int tti);

    boolean putHashIfAbsent(Object key, Object field, Object value);

    boolean putHashIfAbsent(Object key, Object field, Object value, int ttl, int tti);

    void putHash(Object key, Map<Object, Object> hashes);

    void putHash(Object key, Map<Object, Object> hashes, int ttl, int tti);

    long pushListLeft(Object key, Object... values);

    long pushListLeft(Object key, Collection<Object> values);

    long pushListLeft(Object key, Object value, int ttl, int tti);

    long pushListLeft(Object key, Collection<Object> values, int ttl, int tti);

    long pushListRight(Object key, Object... values);

    long pushListRight(Object key, Collection<Object> values);

    long pushListRight(Object key, Object value, int ttl, int tti);

    long pushListRight(Object key, Collection<Object> values, int ttl, int tti);

    long putSet(Object key, Object... values);

    long putSet(Object key, Object value, int ttl, int tti);

    long putSet(Object key, Collection<Object> values);

    long putSet(Object key, Collection<Object> values, int ttl, int tti);

    long putZSet(Object key, Object value, double score);

    long putZSet(Object key, Object value, double score, int ttl, int tti);

    long putZSet(Object key, Map<Object, Double> map);

    long putZSet(Object key, Map<Object, Double> map, int ttl, int tti);
    
    long zrem(Object key, Object... members);
    
    <V> List<V> zrange(Object key, int start, int stop);
    
    <V> List<V> zrangeByScore(Object key, int start, int stop, double mix, double max);
    
    Long zcard(Object key);
    
    Long zcount(Object key, double min, double max);
    
    long zremRangeByScore(Object key, double min, double max);
    
    long getTTLTime(Object key);
}
