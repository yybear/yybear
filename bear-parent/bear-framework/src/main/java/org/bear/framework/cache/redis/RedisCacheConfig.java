package org.bear.framework.cache.redis;

import org.bear.framework.cache.CacheConfig;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-26
 */
public class RedisCacheConfig extends CacheConfig{
	private static final long serialVersionUID = 2506911134481527201L;
	private String namespace;
    private float overflowClearFactor = 0.4f;
    private boolean enableKeyList = true;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public float getOverflowClearFactor() {
        return overflowClearFactor;
    }

    public void setOverflowClearFactor(float overflowClearFactor) {
        this.overflowClearFactor = overflowClearFactor;
    }

    public boolean isEnableKeyList() {
        return enableKeyList;
    }

    public void setEnableKeyList(boolean enableKeyList) {
        this.enableKeyList = enableKeyList;
    }
}
