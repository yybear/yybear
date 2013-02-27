package org.bear.framework.cache;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-26
 */
public class Statistics implements Serializable, StatisticsMBean {
    private static final long serialVersionUID = 3288008570124776786L;
    private final Cache cache;
    private final AtomicLong hits = new AtomicLong();
    private final AtomicLong misses = new AtomicLong();
    private final AtomicLong puts = new AtomicLong();
    private final AtomicLong expires = new AtomicLong();
    private final AtomicLong evictions = new AtomicLong();
    private final AtomicLong readBytes = new AtomicLong();
    private final AtomicLong writeBytes = new AtomicLong();

    public Statistics(Cache cache) {
        this.cache = cache;
    }

    public int getSize() {
        return cache.getSize();
    }

    public long getHits() {
        return hits.longValue();
    }

    public long getMisses() {
        return misses.longValue();
    }

    public long getPuts() {
        return puts.longValue();
    }

    public long getExpires() {
        return expires.longValue();
    }

    public long getEvictions() {
        return evictions.longValue();
    }

    public long getReadBytes() {
        return readBytes.longValue();
    }

    public long getWriteBytes() {
        return writeBytes.longValue();
    }

    public void incrementHits(int delta) {
        hits.addAndGet(delta);
    }

    public void incrementMisses(int delta) {
        misses.addAndGet(delta);
    }

    public void incrementPuts(int delta) {
        puts.addAndGet(delta);
    }

    public void incrementExpires(int delta) {
        expires.addAndGet(delta);
    }

    public void incrementEvictions(int delta) {
        evictions.addAndGet(delta);
    }

    public void incrementReadBytes(int delta) {
        readBytes.addAndGet(delta);
    }

    public void incrementWriteBytes(int delta) {
        writeBytes.addAndGet(delta);
    }

    public void reset() {
        hits.set(0l);
        misses.set(0l);
        puts.set(0l);
        expires.set(0l);
        evictions.set(0l);
        readBytes.set(0l);
        writeBytes.set(0l);
    }

    public Map<String, Serializable> toMap() {
        Map<String, Serializable> map = new HashMap<String, Serializable>();
        //map.put("label", ((AbstractCache) cache).getConfig().getLabel());
        map.put("size", (long) getSize());
        map.put("hits", getHits());
        map.put("misses", getMisses());
        map.put("puts", getPuts());
        map.put("expires", getExpires());
        map.put("evictions", getEvictions());
        map.put("readBytes", getReadBytes());
        map.put("writeBytes", getWriteBytes());
        return map;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
