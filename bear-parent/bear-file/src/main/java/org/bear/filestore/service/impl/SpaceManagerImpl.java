package org.bear.filestore.service.impl;

import org.bear.filestore.Constants;
import org.bear.filestore.model.Space;
import org.bear.filestore.service.SpaceManager;
import org.bear.framework.config.BizConfigTemplate;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-21
 */
public class SpaceManagerImpl extends BizConfigTemplate<Space> implements SpaceManager {
    @Override
    public Space getSpace(String bizKey) {
        return getBizConfigByKey(bizKey);
    }

    @Override
    public Space getSpace(int bizId) {
        return getBizConfigById(bizId);
    }

    @Override
    public void saveSpace(Space space) {
        saveBizConfig(space);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setConfigKey(Constants.FS);
    }
}
