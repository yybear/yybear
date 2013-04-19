package org.bear.framework.config;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import org.bear.framework.ex.EntityNotFoundException;
import org.bear.framework.util.BeanUtils;
import org.bear.framework.util.GenericUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * 业务配置支持模板类,用来给公用服务提供业务配置功能.
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 12-8-21
 */
public class BizConfigTemplate<T extends BizConfig> implements ApplicationListener, InitializingBean {
    private static final Set<String> IGNORE_FIELDS = Sets.newHashSet("bizId", "bizKey", "bizName", "configKey", "configValue");
    private Class<T> configClass;
    private String configKey;
    private BizConfigService bizConfigService;
    private Map<Object, T> configCache = Maps.newHashMap();

    @SuppressWarnings("unchecked")
    public BizConfigTemplate() {
        configClass = GenericUtils.getGenericParameter0(getClass());
        if (configClass == null) {
            configClass = (Class<T>) BizConfig.class;
        }
    }

    public BizConfigTemplate(Class<T> configClass) {
        this.configClass = configClass;
    }

    public void setConfigClass(Class<T> configClass) {
        this.configClass = configClass;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public void setBizConfigService(BizConfigService bizConfigService) {
        this.bizConfigService = bizConfigService;
    }

    public T getBizConfigByKey(String bizKey) {
        return internalGetConfig(bizKey);
    }

    public T getBizConfigById(int bizId) {
        return internalGetConfig(bizId);
    }

    public void saveBizConfig(T config) {
        SerializeWriter out = new SerializeWriter();
        JSONSerializer serializer = new JSONSerializer(out);
        serializer.getPropertyFilters().add(new PropertyFilter() {
            public boolean apply(Object source, String name, Object value) {
                return !IGNORE_FIELDS.contains(name);
            }
        });
        config.setConfigValue(out.toString());
        bizConfigService.saveBizConfig(config);
    }

    private T internalGetConfig(Object key) {
        T config = null;
        if (configCache.containsKey(key)) {
            config = configCache.get(key);
        } else {
            BizConfig bc = key instanceof String ? bizConfigService.getBizConfig((String) key, configKey) : bizConfigService.getBizConfig((Integer) key, configKey);
            if (bc != null) {
                String value = bc.getConfigValue();
                try {
                    config = StringUtils.isBlank(value) ? configClass.newInstance() : JSON.parseObject(value, configClass);
                    BeanUtils.copyProperties(bc, config);
                    configCache.put(config.getBizId(), config);
                    configCache.put(config.getBizKey(), config);
                } catch (Exception ignored) {
                }
            }
            if (config == null) {
                configCache.put(key, null);
            }
        }
        if (config == null) {
            throw new EntityNotFoundException(configClass, (Serializable) key);
        }
        return config;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof BizConfigChangeEvent) {
            BizConfig bc = ((BizConfigChangeEvent) event).getBizConfig();
            configCache.remove(bc.getBizId());
            configCache.remove(bc.getBizKey());
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (configKey == null) {
            configKey = configClass.getSimpleName();
        }
    }
}
