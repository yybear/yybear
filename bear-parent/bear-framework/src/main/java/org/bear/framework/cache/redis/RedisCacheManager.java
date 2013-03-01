package org.bear.framework.cache.redis;

import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.bear.framework.cache.AbstractCacheManager;
import org.bear.framework.cache.Cache;
import org.bear.framework.cache.CacheConfig;
import org.bear.framework.cache.CacheManager;
import org.bear.framework.cache.RedisCache;
import org.bear.framework.util.NamedThreadFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-26
 */
public class RedisCacheManager extends AbstractCacheManager implements DisposableBean{

	private String namespace;
    private boolean enableCleanup = true;
    private RedisConnectionFactory connectionFactory;
    private RedisTemplate redis;
    // 默认使用JDK序列化工具
    private RedisSerializer defaultSerializer = RedisCache.DEFAULT_SERIALIZER;
    private ScheduledExecutorService scheduler;

    public RedisCacheManager() {
        setDefaultConfig(new RedisCacheConfig());
    }

    public void setConnectionFactory(RedisConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setEnableCleanup(boolean enableCleanup) {
        this.enableCleanup = enableCleanup;
    }

    public void setRedisTemplate(RedisTemplate redis) {
        this.redis = redis;
    }

    public void setDefaultSerializer(RedisSerializer defaultSerializer) {
        this.defaultSerializer = defaultSerializer;
    }

    @Override
    public RedisCache createCache(CacheConfig cacheConfig) {
        return (RedisCache) super.createCache(cacheConfig);
    }

    @Override
    public RedisCache getDefaultCache() {
        return (RedisCache) super.getDefaultCache();
    }

    @Override
    public RedisCache getCache(String region) {
        return (RedisCache) super.getCache(region);
    }

    @Override
    protected CacheConfig preCreateCache(CacheConfig config) {
        RedisCacheConfig redisConfig = (RedisCacheConfig) config;
        String cacheNs = redisConfig.getNamespace();
        redisConfig.setNamespace((namespace != null ? namespace + RedisCacheImpl.SEPARATOR : "") + (cacheNs == null ? redisConfig.getRegion() : cacheNs));
        return super.preCreateCache(redisConfig);
    }

    @Override
    public String getCacheType() {
        return "redis";
    }

    @Override
    protected Cache internalCreateCache(CacheConfig config) {
        return new RedisCacheImpl((RedisCacheConfig) config, redis);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.hasLength(namespace, "Property namespace is required");
        if (redis == null) {
            redis = new RedisTemplate();
            redis.setConnectionFactory(connectionFactory);
            redis.setExposeConnection(true);
            redis.setKeySerializer(redis.getStringSerializer());
            redis.setHashKeySerializer(redis.getStringSerializer());
            redis.setDefaultSerializer(defaultSerializer);
            redis.afterPropertiesSet();
        }
        if (enableCleanup) {
            scheduler = Executors.newScheduledThreadPool(1, new NamedThreadFactory("redisCache-cleaner-" + namespace, true));
            scheduler.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    cleanup();
                }
            }, RedisCacheImpl.CLEANUP_PERIOD_SECONDS, RedisCacheImpl.CLEANUP_PERIOD_SECONDS, TimeUnit.SECONDS);
        }
    }

    @Override
    public void destroy() throws Exception {
        if (enableCleanup) {
            scheduler.shutdown();
        }
    }

    private void cleanup() {
        for (String region : getCacheRegions()) {
            Cache cache = getCache(region);
            if (cache instanceof RedisCacheImpl) {
                try {
                    ((RedisCacheImpl) cache).cleanup();
                } catch (Throwable e) {
                    LOG.error("Cleanup cache error", e);
                }
            }
        }
    }

}
