package org.bear.commonservice.app;

import java.util.List;

import org.bear.commonservice.dao.BizDao;
import org.bear.commonservice.model.Biz;
import org.bear.framework.config.BizConfig;
import org.bear.framework.config.BizConfigService;
import org.bear.framework.ex.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-4-8
 */
public class BizConfigServiceImpl implements BizConfigService {
    private BizDao bizDAO;
    private RedisTemplate<String, String> redis;

    @Autowired
    public void setBizDAO(BizDao bizDAO) {
        this.bizDAO = bizDAO;
    }

    public void setRedis(RedisTemplate<String, String> redis) {
        this.redis = redis;
    }


    @Override
    public int getBizId(String bizKey) {
        Biz biz = bizDAO.findByKey(bizKey);
        if (biz == null) {
            throw new EntityNotFoundException(Biz.class, bizKey);
        }
        return biz.getId();
    }

    @Override
    public String getBizKey(int bizId) {
        return bizDAO.findOne(bizId).getKey();
    }

    @Override
    @Transactional(readOnly = true)
    public BizConfig getBizConfig(String bizKey, String configKey) {
        Biz biz = bizDAO.findByKey(bizKey);
        return biz == null ? null : toBizConfig(biz, configKey);
    }

    @Override
    @Transactional(readOnly = true)
    public BizConfig getBizConfig(int bizId, String configKey) {
        Biz biz = bizDAO.findOne(bizId);
        return biz == null ? null : toBizConfig(biz, configKey);
    }

    @Override
    @Transactional
    public void saveBizConfig(BizConfig bizConfig) {
        Biz biz = bizDAO.findByKey(bizConfig.getBizKey());
        if ("__del__".equals(bizConfig.getConfigValue())) {
            biz.getAttributes().remove(bizConfig.getConfigKey());
        } else {
            biz.getAttributes().put(bizConfig.getConfigKey(), bizConfig.getConfigValue());
        }
        bizDAO.save(biz);
        redis.convertAndSend("bizConfig:" + bizConfig.getConfigKey(), bizConfig);
    }

    @Override
    public List<BizConfig> getBizConfigs(String configKey) {
        List<BizConfig> configs = Lists.newArrayList();
        for (Biz biz : bizDAO.findAll()) {
            if (biz.getAttributes().containsKey(configKey)) {
                configs.add(toBizConfig(biz, configKey));
            }
        }
        return configs;
    }

    private BizConfig toBizConfig(Biz biz, String configKey) {
        BizConfig bizConfig = new BizConfig();
        bizConfig.setBizId(biz.getId());
        bizConfig.setBizKey(biz.getKey());
        bizConfig.setBizName(biz.getName());
        bizConfig.setConfigKey(configKey);
        bizConfig.setConfigValue(biz.getAttributes().get(configKey));
        return bizConfig;
    }
}
