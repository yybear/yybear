package org.bear.global.service.impl;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bear.global.service.ServiceReference;
import org.bear.global.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * .
 * <p/>
 *
 * @author <a href="mailto:ganqing@any123.com">gan qing</a>
 * @version V1.0, 13-2-19
 */
public class DefaultServiceRegistry implements ServiceRegistry {
	private static final Logger LOG = LoggerFactory.getLogger(DefaultServiceRegistry.class);
	
	private Map<Class, ServiceReference> registry = new ConcurrentHashMap<Class, ServiceReference>();

    @Override
    public <T> void exportService(Class<T> type, T service) {
        registry.put(type, new InjvmServiceReference<T>(service));
        LOG.debug("ServiceRegistry hashcode is {}, map size is {}", this.hashCode(), registry.size());
        LOG.debug("ServiceRegistry service key is {}", type);
    }

    @Override
    public <T> void unexportService(Class<T> type) {
        ServiceReference<T> sr = getService(type);
        if (sr != null) {
            sr.destroy();
            registry.remove(type);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> ServiceReference<T> getService(Class<T> type) {
        return registry.get(type);
    }

    @Override
    public Collection<ServiceReference> getServices() {
        return registry.values();
    }
}
