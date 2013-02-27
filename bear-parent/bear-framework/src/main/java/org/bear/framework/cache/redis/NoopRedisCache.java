package org.bear.framework.cache.redis;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.bear.framework.cache.Cache;
import org.bear.framework.cache.RedisCache;
import org.bear.framework.cache.Statistics;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-26
 */
public final class NoopRedisCache implements RedisCache {
    public static final Cache NOOP_CACHE = new NoopRedisCache();

    @Override
    public String getRegion() {
        return "noop";
    }

    @Override
    public boolean exists(Object key) {
        return false;
    }

    @Override
    public <T> T get(Object key) {
        return null;
    }

    @Override
    public <K, T> Map<K, T> gets(Object... keys) {
        return Collections.emptyMap();
    }

    @Override
    public <K, T> Map<K, T> gets(Collection keys) {
        return Collections.emptyMap();
    }

    @Override
    public void put(Object key, Object value) {
    }

    @Override
    public void put(Object key, Object value, int ttl, int tti) {
    }

    @Override
    public boolean putIfAbsent(Object key, Object value) {
        return false;
    }

    @Override
    public boolean putIfAbsent(Object key, Object value, int ttl, int tti) {
        return false;
    }

    @Override
    public void put(Map map) {
    }

    @Override
    public void put(Map map, int ttl, int tti) {
    }

    @Override
    public void evict(Object... key) {
    }

    @Override
    public void evict(Collection keys) {
    }

    @Override
    public void clear() {
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public <T> List<T> getKeys(int start, int size) {
        return Collections.emptyList();
    }

    @Override
    public Statistics getStatistics() {
        return null;
    }

    @Override
    public <K, V> RedisOperations<K, V> redis() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <K, V> ValueOperations<K, V> valueOp() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <K, F, V> HashOperations<K, F, V> hashOp() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <K, V> ListOperations<K, V> listOp() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <K, V> SetOperations<K, V> setOp() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <K, V> ZSetOperations<K, V> zSetOp() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void rename(Object oldKey, Object newKey) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean expire(Object key, int ttl, int tti) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <K, Integer> Map<K, Integer> getExpires(Object... keys) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <K, Integer> Map<K, Integer> getExpires(Collection keys) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long incrBy(Object key, int delta) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <F, V> Map<F, V> getHash(Object key, Object... fields) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <F, V> Map<F, V> getHash(Object key, Collection fields) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <K, F, V> Map<K, Map<F, V>> getHashs(Collection keys, Object... fields) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <K, F, V> Map<K, Map<F, V>> getHashs(Collection keys, Collection fields) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <K, F, V> Map<K, Map<F, V>> getHashs(Object... keys) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean putHash(Object key, Object field, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean putHash(Object key, Object field, Object value, int ttl, int tti) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean putHashIfAbsent(Object key, Object field, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean putHashIfAbsent(Object key, Object field, Object value, int ttl, int tti) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putHash(Object key, Map<Object, Object> hashes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putHash(Object key, Map<Object, Object> hashes, int ttl, int tti) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long pushListLeft(Object key, Object... values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long pushListLeft(Object key, Collection<Object> values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long pushListLeft(Object key, Object value, int ttl, int tti) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long pushListLeft(Object key, Collection<Object> values, int ttl, int tti) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long pushListRight(Object key, Object... values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long pushListRight(Object key, Object value, int ttl, int tti) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long pushListRight(Object key, Collection<Object> values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long pushListRight(Object key, Collection<Object> values, int ttl, int tti) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long putSet(Object key, Object value, int ttl, int tti) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long putSet(Object key, Object... values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long putSet(Object key, Collection<Object> values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long putSet(Object key, Collection<Object> values, int ttl, int tti) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long putZSet(Object key, Object value, double score) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long putZSet(Object key, Object value, double score, int ttl, int tti) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long putZSet(Object key, Map<Object, Double> map) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long putZSet(Object key, Map<Object, Double> map, int ttl, int tti) {
        throw new UnsupportedOperationException();
    }

	@Override
	public long zrem(Object key, Object... members) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <V> List<V> zrange(Object key, int start, int stop) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <V> List<V> zrangeByScore(Object key, int start, int stop,
			double mix, double max) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long zcard(Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long zcount(Object key, double min, double max) {
		throw new UnsupportedOperationException();
	}

	@Override
	public long zremRangeByScore(Object key, double min, double max) {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getTTLTime(Object key) {
		throw new UnsupportedOperationException();
	}
}
