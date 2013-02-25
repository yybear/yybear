package org.bear.framework.config;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 业务配置.
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-22
 */
public class BizConfig implements Serializable {
    private static final long serialVersionUID = 1887170484982025567L;
    private int bizId;
    private String bizKey;
    private String bizName;
    private String configKey;
    private String configValue;

    public int getBizId() {
        return bizId;
    }

    public void setBizId(int bizId) {
        this.bizId = bizId;
    }

    public String getBizKey() {
        return bizKey;
    }

    public void setBizKey(String bizKey) {
        this.bizKey = bizKey;
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}