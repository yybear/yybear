package org.bear.framework.cache;

import java.util.Collection;
import java.util.Map;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 12-9-28
 */
public interface Cache {

    String getRegion();

    boolean exists(Object key);

    <T> T get(Object key);

    <K, V> Map<K, V> gets(Object... keys);

    <K, V> Map<K, V> gets(Collection keys);

    void put(Object key, Object value);

    void put(Object key, Object value, int ttl, int tti);

    boolean putIfAbsent(Object key, Object value);

    boolean putIfAbsent(Object key, Object value, int ttl, int tti);

    void put(Map map);

    void put(Map map, int ttl, int tti);

    void evict(Object... keys);

    void evict(Collection keys);

    void clear();

    int getSize();

    <T> Collection<T> getKeys(int start, int size);

    Statistics getStatistics();
}
