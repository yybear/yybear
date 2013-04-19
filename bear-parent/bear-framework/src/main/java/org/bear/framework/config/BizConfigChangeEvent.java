package org.bear.framework.config;

import org.springframework.context.ApplicationEvent;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-22
 */
public class BizConfigChangeEvent extends ApplicationEvent {
    private static final long serialVersionUID = -4431597667934751975L;

    public BizConfigChangeEvent(BizConfig config) {
        super(config);
    }

    public BizConfig getBizConfig() {
        return (BizConfig)getSource();
    }
}
