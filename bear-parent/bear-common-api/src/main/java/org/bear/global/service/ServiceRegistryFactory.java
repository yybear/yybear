package org.bear.global.service;

import java.util.Iterator;
import java.util.ServiceLoader;

import org.bear.global.service.ServiceRegistry;
import org.bear.global.service.ServiceRegistryFactory;
import org.bear.global.service.ServiceUnavailableException;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:ganqing@any123.com">gan qing</a>
 * @version V1.0, 13-2-19
 */
public final class ServiceRegistryFactory {
	private static ServiceRegistryFactory INSTANCE = new ServiceRegistryFactory();

    public static ServiceRegistryFactory getInstance() {
        return INSTANCE;
    }

    private ServiceRegistry serviceRegistry;

    private ServiceRegistryFactory() {
        Iterator<ServiceRegistry> iterator = ServiceLoader.load(ServiceRegistry.class).iterator();
        if (iterator.hasNext()) {
            serviceRegistry = iterator.next();
        } else {
            throw new ServiceUnavailableException("serviceRegistry not found");
        }
    }

    public ServiceRegistry getServiceRegistry() {
        return serviceRegistry;
    }
}
