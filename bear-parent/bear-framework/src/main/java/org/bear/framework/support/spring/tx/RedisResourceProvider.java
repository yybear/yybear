package org.bear.framework.support.spring.tx;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisConnectionUtils;

/**
 * redis资源提供实现.
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-4-2
 */
public class RedisResourceProvider implements ResourceProvider<RedisConnection> {
    private RedisConnectionFactory connectionFactory;

    public void setConnectionFactory(RedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Object getKey() {
        return null;
    }

    @Override
    public RedisConnection getResource() {
        RedisConnectionUtils.bindConnection(connectionFactory);
        return null;
    }

    @Override
    public void releaseResource(RedisConnection session) {
        RedisConnectionUtils.unbindConnection(connectionFactory);
    }
}
