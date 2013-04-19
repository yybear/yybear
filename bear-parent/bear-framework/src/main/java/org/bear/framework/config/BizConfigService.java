package org.bear.framework.config;

import java.util.List;

/**
 * 业务配置管理服务.
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-22
 */
public interface BizConfigService {
    /**
     * 根据业务key获取业务id
     *
     * @param bizKey 业务key
     * @return 业务id
     */
    int getBizId(String bizKey);

    /**
     * 根据业务id获取业务key
     *
     * @param bizId 业务id
     * @return 业务key
     */
    String getBizKey(int bizId);

    /**
     * 根据业务key获取业务配置对象
     *
     * @param bizKey    业务key
     * @param configKey 配置key
     * @return 业务配置对象
     */
    BizConfig getBizConfig(String bizKey, String configKey);

    /**
     * 根据业务id获取业务配置对象
     *
     * @param bizId     业务id
     * @param configKey 配置key
     * @return 业务配置对象
     */
    BizConfig getBizConfig(int bizId, String configKey);

    /**
     * 保存一个业务配置
     *
     * @param bizConfig 业务配置对象
     */
    void saveBizConfig(BizConfig bizConfig);

    /**
     * 获取已配置的所有业务配置对象列表
     *
     * @param configKey 配置key
     * @return 业务配置对象列表
     */
    List<BizConfig> getBizConfigs(String configKey);
}
