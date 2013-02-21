package org.bear.framework.support.spring;

import org.bear.global.service.ServiceRegistry;
import org.bear.global.service.ServiceRegistryFactory;
import org.springframework.beans.factory.FactoryBean;
/**
 * .
 * <p/>
 *
 * @author <a href="mailto:ganqing@any123.com">gan qing</a>
 * @version V1.0, 13-2-19
 */
public class ServiceRegistryFactoryBean implements FactoryBean<ServiceRegistry>{

	@Override
    public ServiceRegistry getObject() throws Exception {
        return ServiceRegistryFactory.getInstance().getServiceRegistry();
    }

    @Override
    public Class<?> getObjectType() {
        return ServiceRegistry.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
