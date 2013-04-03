package org.bear.framework.support.spring.tx;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-4-2
 */
public class DefaultThreadResourceManager implements ThreadResourceManager {
	
	private static Logger LOG = LoggerFactory.getLogger(DefaultThreadResourceManager.class);
    private List<ResourceProvider> providers;

    public void setProviders(List<ResourceProvider> providers) {
        this.providers = providers;
    }

	@Override
	public boolean hasBind() {
		return TransactionSynchronizationManager.hasResource(this);
	}

	@Override
	public void bind() {
		TransactionSynchronizationManager.bindResource(this, this);
        for (ResourceProvider provider : providers) { // 主要是批量绑定资源提供者.
            Object resource = provider.getResource();
            if (resource != null) {
                TransactionSynchronizationManager.bindResource(provider.getKey(), resource);
            }
        }
		
	}

	@Override
	public void unBind() {
		TransactionSynchronizationManager.unbindResource(this);
		for (ResourceProvider provider : providers) {
            try {
                Object key = provider.getKey();
                provider.releaseResource(key != null ? TransactionSynchronizationManager.unbindResource(key) : null);
            } catch (Throwable e) {
                LOG.error("Unbind resource error", e);
            }
        }
	}

}
