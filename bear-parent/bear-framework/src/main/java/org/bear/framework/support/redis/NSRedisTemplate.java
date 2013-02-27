package org.bear.framework.support.redis;

import java.util.List;

import org.bear.framework.cache.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

/**
 * 扩张spring的redis template以支持namespace.
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-26
 */
public class NSRedisTemplate<K, V> extends RedisTemplate<K, V>{
	private String namespace;

    public NSRedisTemplate() {
        setDefaultSerializer(null);
        setExposeConnection(true);
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    @Autowired
    public void setConnectionFactory(RedisConnectionFactory connectionFactory) {
        super.setConnectionFactory(connectionFactory);
    }

    @Override
    public <T> T execute(RedisCallback<T> action) {
        return execute(action, true);
    }

    @Override
    public <T> T execute(RedisCallback<T> action, boolean decorateConnection) {
        return execute(action, decorateConnection, false);
    }

    @SuppressWarnings("unchecked")
    public <T> T execute(RedisCallback<T> action, boolean decorateConnection, boolean pipeline) {
        RedisConnectionFactory factory = getConnectionFactory();
        final RedisConnection conn = RedisConnectionUtils.getConnection(factory);
        RedisConnection conn0 = conn;
        if (decorateConnection) {
            conn0 = decorateRedisConnection(conn);
        }
        boolean alreadyPipelined = conn0.isPipelined();
        boolean pipelinedClosed = false;
        if (pipeline && !alreadyPipelined) {
            conn0.openPipeline();
        }
        try {
            T result = action.doInRedis(conn0);
            if (pipeline && !alreadyPipelined) {
                if (result != null) {
                    throw new InvalidDataAccessApiUsageException("Callback cannot returned a non-null value as it gets overwritten by the pipeline");
                }
                result = (T) conn0.closePipeline();
                pipelinedClosed = true;
            }
            return result;
        } finally {
            if (pipeline && !alreadyPipelined && !pipelinedClosed) {
                conn0.closePipeline();
            }
            RedisConnectionUtils.releaseConnection(conn, factory);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> pipeline(RedisCallback<T> action) {
        return (List) execute(action, false, true);
    }

    @Override
    public void afterPropertiesSet() {
        if (getKeySerializer() == null) {
            if (namespace != null) {
                NamespaceKeyRedisSerializer serializer = new NamespaceKeyRedisSerializer();
                serializer.setNamespace(namespace);
                setKeySerializer(serializer);
            } else {
                setKeySerializer(getStringSerializer());
            }
        }
        if (getHashKeySerializer() == null) {
            setHashKeySerializer(getStringSerializer());
        }

        if (this.getDefaultSerializer() == null) {
            setDefaultSerializer(RedisCache.DEFAULT_SERIALIZER);
        }
        super.afterPropertiesSet();
    }

    protected RedisConnection decorateRedisConnection(RedisConnection conn) {
        return conn;
    }
}
