package org.bear.framework.support.redis;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Protocol;
import static redis.clients.jedis.Protocol.toByteArray;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-27
 */
public class RedisUtils {
	private RedisUtils() {
    }

    public static void expire(RedisConnection conn, final long seconds, final byte[]... keys) {
        pipeline(conn, new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
                for (byte[] key : keys) {
                    conn.expire(key, seconds);
                }
                return null;
            }
        });
    }

    public static Map<byte[], byte[]> hGetAll(RedisConnection conn, byte[] key) {//jedis pipeline bug,return a string collection,so use execute..
        if (conn.isPipelined()) {
            conn.execute("HGETALL", key);
            return null;
        } else {
            return conn.hGetAll(key);
        }
    }

    public static List<byte[]> hMGet(RedisConnection conn, byte[] key, byte[]... fields) {
        if (conn.isPipelined()) {
            final byte[][] params = new byte[fields.length + 1][];
            params[0] = key;
            System.arraycopy(fields, 0, params, 1, fields.length);
            getJedis(conn).hmget(key, params);
            conn.execute("HMGET", params);
            return null;
        } else {
            return conn.hMGet(key, fields);
        }
    }


    public static Long zrem(RedisConnection conn, byte[] key, byte[]... members) {
        return getJedis(conn).zrem(key, members);
    }
    
    public static Long zrem(RedisConnection conn, byte[] key, double min, double max) {
        return getJedis(conn).zremrangeByScore(key, min, max);
    }

    public static Long zrem(RedisConnection conn, byte[] key, Collection<byte[]> members) {
        return zrem(conn, key, members.toArray(new byte[members.size()][]));
    }

    public static Set<byte[]> zRangeByScore(RedisConnection conn, byte[] key, byte[] min, byte[] max, int offset, int count) {
        return getJedis(conn).zrangeByScore(key, min, max, offset, count);
    }
    
    public static Set<byte[]> zRangeByScore(RedisConnection conn, byte[] key, double min, double max, int offset, int count) {
        return getJedis(conn).zrangeByScore(key, min, max, offset, count);
    }
    
    public static Set<byte[]> zRange(RedisConnection conn, byte[] key, int offset, int count) {
        return getJedis(conn).zrange(key, offset, offset + count -1);
    }
    
    public static Long zcard(RedisConnection conn, byte[] key) {
        return getJedis(conn).zcard(key);
    }
    
    public static Long zcount(RedisConnection conn, byte[] key, double min, double max) {
        return getJedis(conn).zcount(key, min, max);
    }

    public static Long getIdleSecond(RedisConnection conn, byte[] bytes) {
        return ((Jedis) conn.getNativeConnection()).objectIdletime(bytes);
    }

    @SuppressWarnings("unchecked")
    public static List<Long> getIdleSeconds(RedisConnection conn, final byte[][] members) {
        return pipeline(conn, new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection conn) throws DataAccessException {
                for (byte[] bytes : members) {
                    byte[][] args = new byte[2][];
                    args[0] = Protocol.Keyword.IDLETIME.raw;
                    args[1] = bytes;
                    conn.execute("OBJECT", args);
                }
                return null;
            }
        });
    }

    public static Long lpush(RedisConnection conn, byte[] key, byte[]... members) {
        return getJedis(conn).lpush(key, members);
    }

    public static Long rpush(RedisConnection conn, byte[] key, byte[][] members) {
        return getJedis(conn).rpush(key, members);
    }

    public static List<byte[]> bLPop(RedisConnection conn, int timeout, byte[]... keys) {
        return getJedis(conn).blpop(timeout, keys);
    }

    public static Long sadd(RedisConnection conn, byte[] key, byte[][] members) {
        return getJedis(conn).sadd(key, members);
    }

    public static Long zadd(RedisConnection conn, byte[] key, double score, byte[] bytes) {
        return getJedis(conn).zadd(key, score, bytes);
    }

    public static Long zadd(RedisConnection conn, byte[] key, Map<byte[], Double> map) {
        byte[][] args = new byte[map.size() * 2 + 1][];
        int i = 0;
        args[i++] = key;
        for (Map.Entry<byte[], Double> entry : map.entrySet()) {
            args[i++] = toByteArray(entry.getValue());
            args[i++] = entry.getKey();
        }
        return (Long) conn.execute("ZADD", args);
    }
    
    public static Long ttl(RedisConnection conn, byte[] key) {
    	return getJedis(conn).ttl(key);
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> pipeline(RedisConnection conn, RedisCallback<T> action) {
        boolean alreadyPipelined = conn.isPipelined();
        boolean pipelinedClosed = false;
        if (!alreadyPipelined) {
            conn.openPipeline();
        }
        try {
            Object result = action.doInRedis(conn);
            if (result != null) {
                throw new InvalidDataAccessApiUsageException("Callback cannot returned a non-null value as it gets overwritten by the pipeline");
            }
            result = conn.closePipeline();
            pipelinedClosed = true;
            return (List) result;
        } finally {
            if (!alreadyPipelined && !pipelinedClosed) {
                conn.closePipeline();
            }
        }
    }

    private static Jedis getJedis(RedisConnection conn) {
        return (Jedis) conn.getNativeConnection();
    }
}
