package org.bear.framework.cache.redis;

import org.springframework.data.redis.connection.RedisConnection;

/**
 * redis缓存可以操作的监听器.
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-27
 */
public interface RedisKeylistener {
	void onWrite(RedisConnection conn, byte[]... keys);

    void onWrite(RedisConnection conn, byte[] key, int ttl);

    void onDelete(RedisConnection conn, byte[]... keys);

    long beforeExpire(RedisConnection conn, byte[] key, long seconds);
}
