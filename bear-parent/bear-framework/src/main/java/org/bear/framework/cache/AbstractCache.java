package org.bear.framework.cache;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * .
 * <p/>
 * 
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-26
 */
public abstract class AbstractCache<T extends CacheConfig> implements Cache {
	protected final T config;
	protected final Statistics statistics = new Statistics(this);

	public AbstractCache(T config) {
		this.config = config;
	}

	public T getConfig() {
		return config;
	}

	@Override
	public String getRegion() {
		return config.getRegion();
	}

	@Override
	public boolean exists(Object key) {
		return get(key) != null;
	}

	@Override
	public <K, T> Map<K, T> gets(Object... keys) {
		return gets(Arrays.asList(keys));
	}

	@Override
	public <K, T> Map<K, T> gets(Collection keys) {
		Map map = new HashMap(keys.size());
		for (Object key : keys) {
			Object value = get(key);
			if (value != null) {
				map.put(key, get(key));
			}
		}
		return map;
	}

	@Override
	public void put(Object key, Object value) {
		put(key, value, -1, -1);
	}

	@Override
	public boolean putIfAbsent(Object key, Object value) {
		return putIfAbsent(key, value, -1, -1);
	}

	@Override
	public void put(Map map) {
		put(map, -1, -1);
	}

	@Override
	public void put(Map map, int ttl, int tti) {
		for (Map.Entry entry : (Set<Map.Entry>) map.entrySet()) {
			put(entry.getKey(), entry.getValue(), ttl, tti);
		}
	}

	@Override
	public void evict(Object... keys) {
		evict(Arrays.asList(keys));
	}

	@Override
	public Statistics getStatistics() {
		return statistics;
	}

	protected int preTtl(int ttl) {
		int maxTtl = config.getMaxTimeToIdleSeconds();
		if (ttl < 0) {
			return config.getTimeToLiveSeconds();
		} else if (ttl == 0 || ttl > maxTtl) {
			return maxTtl;
		}
		return ttl;
	}

	protected int preTti(int tti) {
		int maxTti = config.getMaxTimeToIdleSeconds();
		if (tti < 0) {
			return config.getTimeToIdleSeconds();
		} else if (tti == 0 || tti > maxTti) {
			return maxTti;
		}
		return tti;
	}
}
