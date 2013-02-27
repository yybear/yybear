package org.bear.framework.cache;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 12-9-28
 */
public class CacheConfig implements Cloneable, Serializable, Comparable<CacheConfig> {
    private static final long serialVersionUID = 4823657240819832665L;
    public static final int DEFAULT_MAX_KEYS = -1;//最多key个数,默认不限制
    public static final int DEFAULT_TTL = 60 * 60 * 12;//默认ttl,12个小时
    public static final int DEFAULT_MAX_TTL = 60 * 60 * 24 * 7;//默认最大ttl,7天
    public static final int DEFAULT_TTI = -1;//默认tti,无
    public static final int DEFAULT_MAX_TTI = 60 * 60;//默认最大tti,1小时

    private String region;
    private String label;
    private boolean clearable = true;
    private int maxKeys = DEFAULT_MAX_KEYS;
    private int timeToIdleSeconds = DEFAULT_TTI;//tti,0表示不限制,但是不会超过max,-1表示使用默认值
    private int timeToLiveSeconds = DEFAULT_TTL;//ttl,0表示不限制,但是不会超过max,-1表示使用默认值
    private int maxTimeToIdleSeconds = DEFAULT_MAX_TTL;
    private int maxTimeToLiveSeconds = DEFAULT_MAX_TTI;
    private String evictionPolicy;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isClearable() {
        return clearable;
    }

    public void setClearable(boolean clearable) {
        this.clearable = clearable;
    }

    public int getMaxKeys() {
        return maxKeys;
    }

    public void setMaxKeys(int maxKeys) {
        this.maxKeys = maxKeys;
    }

    public int getTimeToIdleSeconds() {
        return timeToIdleSeconds;
    }

    public void setTimeToIdleSeconds(int timeToIdleSeconds) {
        this.timeToIdleSeconds = timeToIdleSeconds;
    }

    public int getTimeToLiveSeconds() {
        return timeToLiveSeconds;
    }

    public void setTimeToLiveSeconds(int timeToLiveSeconds) {
        this.timeToLiveSeconds = timeToLiveSeconds;
    }

    public int getMaxTimeToIdleSeconds() {
        return maxTimeToIdleSeconds;
    }

    public void setMaxTimeToIdleSeconds(int maxTimeToIdleSeconds) {
        this.maxTimeToIdleSeconds = maxTimeToIdleSeconds;
    }

    public int getMaxTimeToLiveSeconds() {
        return maxTimeToLiveSeconds;
    }

    public void setMaxTimeToLiveSeconds(int maxTimeToLiveSeconds) {
        this.maxTimeToLiveSeconds = maxTimeToLiveSeconds;
    }

    public String getEvictionPolicy() {
        return evictionPolicy;
    }

    public void setEvictionPolicy(String evictionPolicy) {
        this.evictionPolicy = evictionPolicy;
    }

    @Override
    public CacheConfig clone() {
        try {
            return (CacheConfig) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int compareTo(CacheConfig o) {
        return region.compareTo(o.getRegion());
    }
}
