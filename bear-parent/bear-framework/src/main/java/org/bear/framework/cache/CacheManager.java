package org.bear.framework.cache;

import java.util.Collection;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 12-9-28
 */
public interface CacheManager {

    boolean cacheExists(String region);

    Cache getCache(String region);

    Cache getDefaultCache();

    Cache createCache(CacheConfig cacheConfig);

    Collection<String> getCacheRegions();
}
