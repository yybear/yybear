package org.bear.framework.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-22
 */
public class BizConfigEventListener implements MessageListener, ApplicationContextAware {
    private ApplicationContext ctx;
    private RedisTemplate<String, Object> redis;

    @Autowired
    public void setRedis(RedisTemplate<String, Object> redis) {
        this.redis = redis;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        BizConfig bizConfig = (BizConfig) redis.getValueSerializer().deserialize(message.getBody());
        ctx.publishEvent(new BizConfigChangeEvent(bizConfig));
    }
}
