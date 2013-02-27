package org.bear.framework.cache.redis;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisPipelineException;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.connection.Subscription;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-27
 */
public class RedisConnectionWrapper implements RedisConnection {
    private final RedisConnection conn;
    protected final RedisKeylistener listener;

    public RedisConnectionWrapper(RedisConnection conn, RedisKeylistener listener) {
        this.conn = conn;
        this.listener = listener;
    }

    public void close() throws DataAccessException {
        conn.close();
    }

    public boolean isClosed() {
        return conn.isClosed();
    }

    public Object getNativeConnection() {
        return conn.getNativeConnection();
    }

    public boolean isQueueing() {
        return conn.isQueueing();
    }

    public boolean isPipelined() {
        return conn.isPipelined();
    }

    public void openPipeline() {
        conn.openPipeline();
    }

    public List<Object> closePipeline() throws RedisPipelineException {
        return conn.closePipeline();
    }

    public Object execute(String command, byte[]... args) {
        return conn.execute(command, args);
    }

    public Boolean exists(byte[] key) {
        return conn.exists(key);
    }

    public Long del(byte[]... keys) {
        Long ret = conn.del(keys);
        listener.onDelete(this, keys);
        return ret;
    }

    public DataType type(byte[] key) {
        return conn.type(key);
    }

    public Set<byte[]> keys(byte[] pattern) {
        return conn.keys(pattern);
    }

    public byte[] randomKey() {
        return conn.randomKey();
    }

    public void rename(byte[] oldName, byte[] newName) {
        conn.rename(oldName, newName);
        listener.onDelete(this, oldName);
        listener.onWrite(this, newName);
    }

    public Boolean renameNX(byte[] oldName, byte[] newName) {
        Boolean ret = conn.renameNX(oldName, newName);
        if (ret) {
            listener.onDelete(this, oldName);
            listener.onWrite(this, newName);
        }
        return ret;
    }

    public Boolean expire(byte[] key, long seconds) {
        return conn.expire(key, listener.beforeExpire(this, key, seconds));
    }

    public Boolean expireAt(byte[] key, long unixTime) {
        return conn.expire(key, listener.beforeExpire(this, key, unixTime / 1000));
    }

    public Boolean persist(byte[] key) {
        return conn.expire(key, listener.beforeExpire(this, key, -1));
    }

    public Boolean move(byte[] key, int dbIndex) {
        Boolean ret = conn.move(key, dbIndex);
        if (ret) {
            listener.onDelete(this, key);
        }
        return ret;
    }

    public Long ttl(byte[] key) {
        return conn.ttl(key);
    }

    public List<byte[]> sort(byte[] key, SortParameters params) {
        return conn.sort(key, params);
    }

    public Long sort(byte[] key, SortParameters params, byte[] storeKey) {
        Long ret = conn.sort(key, params, storeKey);
        if (ret > 0) {
            listener.onWrite(this, storeKey);
        }
        return ret;
    }

    public byte[] get(byte[] key) {
        return conn.get(key);
    }

    public byte[] getSet(byte[] key, byte[] value) {
        byte[] ret = conn.getSet(key, value);
        listener.onWrite(this, key);
        return ret;
    }

    public List<byte[]> mGet(byte[]... keys) {
        return conn.mGet(keys);
    }

    public void set(byte[] key, byte[] value) {
        conn.set(key, value);
        listener.onWrite(this, key);
    }

    public Boolean setNX(byte[] key, byte[] value) {
        Boolean ret = conn.setNX(key, value);
        if (ret) {
            listener.onWrite(this, key);
        }
        return ret;
    }

    public void setEx(byte[] key, long seconds, byte[] value) {
        conn.setEx(key, listener.beforeExpire(this, key, seconds), value);
        listener.onWrite(this, key, (int) seconds);
    }

    public void mSet(Map<byte[], byte[]> tuple) {
        conn.mSet(tuple);
        Set<byte[]> keys = tuple.keySet();
        listener.onWrite(this, keys.toArray(new byte[keys.size()][]));
    }

    public void mSetNX(Map<byte[], byte[]> tuple) {
        conn.mSetNX(tuple);
        Set<byte[]> keys = tuple.keySet();
        listener.onWrite(this, keys.toArray(new byte[keys.size()][]));
    }

    public Long incr(byte[] key) {
        Long ret = conn.incr(key);
        if (ret == 1l) {
            listener.onWrite(this, key);
        }
        return ret;
    }

    public Long incrBy(byte[] key, long value) {
        Long ret = conn.incrBy(key, value);
        if (ret == value) {
            listener.onWrite(this, key);
        }
        return ret;
    }

    public Long decr(byte[] key) {
        Long ret = conn.decr(key);
        if (ret == -1l) {
            listener.onWrite(this, key);
        }
        return ret;
    }

    public Long decrBy(byte[] key, long value) {
        Long ret = conn.decrBy(key, value);
        if (ret == -value) {
            listener.onWrite(this, key);
        }
        return ret;
    }

    public Long append(byte[] key, byte[] value) {
        return conn.append(key, value);
    }

    public byte[] getRange(byte[] key, long begin, long end) {
        return conn.getRange(key, begin, end);
    }

    public void setRange(byte[] key, byte[] value, long offset) {
        conn.setRange(key, value, offset);
    }

    public Boolean getBit(byte[] key, long offset) {
        return conn.getBit(key, offset);
    }

    public void setBit(byte[] key, long offset, boolean value) {
        conn.setBit(key, offset, value);
    }

    public Long strLen(byte[] key) {
        return conn.strLen(key);
    }

    public Long rPush(byte[] key, byte[] value) {
        Long ret = conn.rPush(key, value);
        if (ret == 1) {
            listener.onWrite(this, key);
        }
        return ret;
    }

    public Long lPush(byte[] key, byte[] value) {
        Long ret = conn.lPush(key, value);
        if (ret == 1) {
            listener.onWrite(this, key);
        }
        return ret;
    }

    public Long rPushX(byte[] key, byte[] value) {
        return conn.rPushX(key, value);
    }

    public Long lPushX(byte[] key, byte[] value) {
        return conn.lPushX(key, value);
    }

    public Long lLen(byte[] key) {
        return conn.lLen(key);
    }

    public List<byte[]> lRange(byte[] key, long begin, long end) {
        return conn.lRange(key, begin, end);
    }

    public void lTrim(byte[] key, long begin, long end) {
        conn.lTrim(key, begin, end);
    }

    public byte[] lIndex(byte[] key, long index) {
        return conn.lIndex(key, index);
    }

    public Long lInsert(byte[] key, Position where, byte[] pivot, byte[] value) {
        return conn.lInsert(key, where, pivot, value);
    }

    public void lSet(byte[] key, long index, byte[] value) {
        conn.lSet(key, index, value);
    }

    public Long lRem(byte[] key, long count, byte[] value) {
        return conn.lRem(key, count, value);
    }

    public byte[] lPop(byte[] key) {
        return conn.lPop(key);
    }

    public byte[] rPop(byte[] key) {
        return conn.rPop(key);
    }

    public List<byte[]> bLPop(int timeout, byte[]... keys) {
        return conn.bLPop(timeout, keys);
    }

    public List<byte[]> bRPop(int timeout, byte[]... keys) {
        return conn.bRPop(timeout, keys);
    }

    public byte[] rPopLPush(byte[] srcKey, byte[] dstKey) {
        return conn.rPopLPush(srcKey, dstKey);
    }

    public byte[] bRPopLPush(int timeout, byte[] srcKey, byte[] dstKey) {
        return conn.bRPopLPush(timeout, srcKey, dstKey);
    }

    public Boolean sAdd(byte[] key, byte[] value) {
        Boolean ret = conn.sAdd(key, value);
        if (ret) {
            listener.onWrite(this, key);
        }
        return ret;
    }

    public Boolean sRem(byte[] key, byte[] value) {
        return conn.sRem(key, value);
    }

    public byte[] sPop(byte[] key) {
        return conn.sPop(key);
    }

    public Boolean sMove(byte[] srcKey, byte[] destKey, byte[] value) {
        return conn.sMove(srcKey, destKey, value);
    }

    public Long sCard(byte[] key) {
        return conn.sCard(key);
    }

    public Boolean sIsMember(byte[] key, byte[] value) {
        return conn.sIsMember(key, value);
    }

    public Set<byte[]> sInter(byte[]... keys) {
        return conn.sInter(keys);
    }

    public Long sInterStore(byte[] destKey, byte[]... keys) {
        Long ret = conn.sInterStore(destKey, keys);
        if (ret > 0) {
            listener.onWrite(this, destKey);
        }
        return ret;
    }

    public Set<byte[]> sUnion(byte[]... keys) {
        return conn.sUnion(keys);
    }

    public Long sUnionStore(byte[] destKey, byte[]... keys) {
        Long ret = conn.sUnionStore(destKey, keys);
        if (ret > 0) {
            listener.onWrite(this, destKey);
        }
        return ret;
    }

    public Set<byte[]> sDiff(byte[]... keys) {
        return conn.sDiff(keys);
    }

    public Long sDiffStore(byte[] destKey, byte[]... keys) {
        Long ret = conn.sDiffStore(destKey, keys);
        if (ret > 0) {
            listener.onWrite(this, destKey);
        }
        return ret;
    }

    public Set<byte[]> sMembers(byte[] key) {
        return conn.sMembers(key);
    }

    public byte[] sRandMember(byte[] key) {
        return conn.sRandMember(key);
    }

    public Boolean zAdd(byte[] key, double score, byte[] value) {
        Boolean ret = conn.zAdd(key, score, value);
        if (ret) {
            listener.onWrite(this, key);
        }
        return ret;
    }

    public Boolean zRem(byte[] key, byte[] value) {
        return conn.zRem(key, value);
    }

    public Double zIncrBy(byte[] key, double increment, byte[] value) {
        return conn.zIncrBy(key, increment, value);
    }

    public Long zRank(byte[] key, byte[] value) {
        return conn.zRank(key, value);
    }

    public Long zRevRank(byte[] key, byte[] value) {
        return conn.zRevRank(key, value);
    }

    public Set<byte[]> zRange(byte[] key, long begin, long end) {
        return conn.zRange(key, begin, end);
    }

    public Set<Tuple> zRangeWithScores(byte[] key, long begin, long end) {
        return conn.zRangeWithScores(key, begin, end);
    }

    public Set<byte[]> zRangeByScore(byte[] key, double min, double max) {
        return conn.zRangeByScore(key, min, max);
    }

    public Set<Tuple> zRangeByScoreWithScores(byte[] key, double min, double max) {
        return conn.zRangeByScoreWithScores(key, min, max);
    }

    public Set<byte[]> zRangeByScore(byte[] key, double min, double max, long offset, long count) {
        return conn.zRangeByScore(key, min, max, offset, count);
    }

    public Set<Tuple> zRangeByScoreWithScores(byte[] key, double min, double max, long offset, long count) {
        return conn.zRangeByScoreWithScores(key, min, max, offset, count);
    }

    public Set<byte[]> zRevRange(byte[] key, long begin, long end) {
        return conn.zRevRange(key, begin, end);
    }

    public Set<Tuple> zRevRangeWithScores(byte[] key, long begin, long end) {
        return conn.zRevRangeWithScores(key, begin, end);
    }

    public Set<byte[]> zRevRangeByScore(byte[] key, double min, double max) {
        return conn.zRevRangeByScore(key, min, max);
    }

    public Set<Tuple> zRevRangeByScoreWithScores(byte[] key, double min, double max) {
        return conn.zRevRangeByScoreWithScores(key, min, max);
    }

    public Set<byte[]> zRevRangeByScore(byte[] key, double min, double max, long offset, long count) {
        return conn.zRevRangeByScore(key, min, max, offset, count);
    }

    public Set<Tuple> zRevRangeByScoreWithScores(byte[] key, double min, double max, long offset, long count) {
        return conn.zRevRangeByScoreWithScores(key, min, max, offset, count);
    }

    public Long zCount(byte[] key, double min, double max) {
        return conn.zCount(key, min, max);
    }

    public Long zCard(byte[] key) {
        return conn.zCard(key);
    }

    public Double zScore(byte[] key, byte[] value) {
        return conn.zScore(key, value);
    }

    public Long zRemRange(byte[] key, long begin, long end) {
        return conn.zRemRange(key, begin, end);
    }

    public Long zRemRangeByScore(byte[] key, double min, double max) {
        return conn.zRemRangeByScore(key, min, max);
    }

    public Long zUnionStore(byte[] destKey, byte[]... sets) {
        Long ret = conn.zUnionStore(destKey, sets);
        if (ret > 0) {
            listener.onWrite(this, destKey);
        }
        return ret;
    }

    public Long zUnionStore(byte[] destKey, Aggregate aggregate, int[] weights, byte[]... sets) {
        Long ret = conn.zUnionStore(destKey, aggregate, weights, sets);
        if (ret > 0) {
            listener.onWrite(this, destKey);
        }
        return ret;
    }

    public Long zInterStore(byte[] destKey, byte[]... sets) {
        Long ret = conn.zInterStore(destKey, sets);
        if (ret > 0) {
            listener.onWrite(this, destKey);
        }
        return ret;
    }

    public Long zInterStore(byte[] destKey, Aggregate aggregate, int[] weights, byte[]... sets) {
        Long ret = conn.zInterStore(destKey, aggregate, weights, sets);
        if (ret > 0) {
            listener.onWrite(this, destKey);
        }
        return ret;
    }

    public Boolean hSet(byte[] key, byte[] field, byte[] value) {
        Boolean ret = conn.hSet(key, field, value);
        if (ret) {
            listener.onWrite(this, key);
        }
        return ret;
    }

    public Boolean hSetNX(byte[] key, byte[] field, byte[] value) {
        Boolean ret = conn.hSetNX(key, field, value);
        if (ret) {
            listener.onWrite(this, key);
        }
        return ret;
    }

    public byte[] hGet(byte[] key, byte[] field) {
        return conn.hGet(key, field);
    }

    public List<byte[]> hMGet(byte[] key, byte[]... fields) {
        return conn.hMGet(key, fields);
    }

    public void hMSet(byte[] key, Map<byte[], byte[]> hashes) {
        conn.hMSet(key, hashes);
        listener.onWrite(this, key);
    }

    public Long hIncrBy(byte[] key, byte[] field, long delta) {
        Long ret = conn.hIncrBy(key, field, delta);
        if (ret == delta) {
            listener.onWrite(this, key);
        }
        return ret;
    }

    public Boolean hExists(byte[] key, byte[] field) {
        return conn.hExists(key, field);
    }

    public Boolean hDel(byte[] key, byte[] field) {
        return conn.hDel(key, field);
    }

    public Long hLen(byte[] key) {
        return conn.hLen(key);
    }

    public Set<byte[]> hKeys(byte[] key) {
        return conn.hKeys(key);
    }

    public List<byte[]> hVals(byte[] key) {
        return conn.hVals(key);
    }

    public Map<byte[], byte[]> hGetAll(byte[] key) {
        return conn.hGetAll(key);
    }

    public void multi() {
        conn.multi();
    }

    public List<Object> exec() {
        return conn.exec();
    }

    public void discard() {
        conn.discard();
    }

    public void watch(byte[]... keys) {
        conn.watch(keys);
    }

    public void unwatch() {
        conn.unwatch();
    }

    public boolean isSubscribed() {
        return conn.isSubscribed();
    }

    public Subscription getSubscription() {
        return conn.getSubscription();
    }

    public Long publish(byte[] channel, byte[] message) {
        return conn.publish(channel, message);
    }

    public void subscribe(MessageListener listener, byte[]... channels) {
        conn.subscribe(listener, channels);
    }

    public void pSubscribe(MessageListener listener, byte[]... patterns) {
        conn.pSubscribe(listener, patterns);
    }

    public void select(int dbIndex) {
        conn.select(dbIndex);
    }

    public byte[] echo(byte[] message) {
        return conn.echo(message);
    }

    public String ping() {
        return conn.ping();
    }

    public void bgWriteAof() {
        conn.bgWriteAof();
    }

    public void bgSave() {
        conn.bgSave();
    }

    public Long lastSave() {
        return conn.lastSave();
    }

    public void save() {
        conn.save();
    }

    public Long dbSize() {
        return conn.dbSize();
    }

    public void flushDb() {
        conn.flushDb();
    }

    public void flushAll() {
        conn.flushAll();
    }

    public Properties info() {
        return conn.info();
    }

    public void shutdown() {
        conn.shutdown();
    }

    public List<String> getConfig(String pattern) {
        return conn.getConfig(pattern);
    }

    public void setConfig(String param, String value) {
        conn.setConfig(param, value);
    }

    public void resetConfigStats() {
        conn.resetConfigStats();
    }
}

