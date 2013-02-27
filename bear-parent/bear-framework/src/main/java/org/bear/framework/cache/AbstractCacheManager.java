package org.bear.framework.cache;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.bear.framework.cache.redis.NoopRedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-26
 */
public abstract class AbstractCacheManager implements CacheManager, InitializingBean {
    public static final String DEFAULT_REGION = "_";
    protected static Logger LOG = LoggerFactory.getLogger(AbstractCacheManager.class);
    private CacheConfig defaultConfig;
    private Map<String, CacheConfig> configs = new HashMap<String, CacheConfig>();
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();

    public void setDefaultConfig(CacheConfig defaultConfig) {
        this.defaultConfig = defaultConfig;
    }

    public void setConfigs(List<CacheConfig> configs) {
        for (CacheConfig config : configs) {
            this.configs.put(config.getRegion(), config);
        }
    }

    @Override
    public boolean cacheExists(String region) {
        return caches.containsKey(region);
    }

    @Override
    public Cache getCache(String region) {
        Cache cache = caches.get(region);
        if (cache == null) {
            CacheConfig config = configs.get(region);
            if (config == null && defaultConfig != null) {
                config = defaultConfig.clone();
                config.setRegion(region);
            }
            if (config == null) {
                cache = NoopRedisCache.NOOP_CACHE;
                LOG.warn("Cache config for region [{}] not found, use noopCache", region);
            } else {
                cache = createCache(config);
            }
        }
        return cache;
    }

    @Override
    public Cache getDefaultCache() {
        return getCache(DEFAULT_REGION);
    }

    @Override
    public Cache createCache(CacheConfig cacheConfig) {
        String region = cacheConfig.getRegion();
        Assert.hasLength(region, "Property region is required");
        if (cacheExists(region)) {
            LOG.debug("Using existing cache region [{}]", cacheConfig.getRegion());
            return getCache(region);
        } else {
            LOG.debug("Creating new cache region [{}]", cacheConfig.getRegion());
            Cache cache = internalCreateCache(preCreateCache(cacheConfig));
            caches.put(cache.getRegion(), cache);
            return postCreateCache(cache);
        }
    }

    @Override
    public Collection<String> getCacheRegions() {
        return caches.keySet();
    }

    protected CacheConfig preCreateCache(CacheConfig config) {
        return config;
    }
    
    protected Cache postCreateCache(Cache cache) {
        return cache;
    }
    
    protected String getCacheType() {
        return "abstract";
    }

    protected abstract Cache internalCreateCache(CacheConfig cacheConfig);
}
