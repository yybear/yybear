package org.bear.global.service;

import java.util.Collection;

import org.bear.global.service.ServiceReference;
/**
 * .
 * <p/>
 *
 * @author <a href="mailto:ganqing@any123.com">gan qing</a>
 * @version V1.0, 13-2-19
 */
public interface ServiceRegistry {
	<T> void exportService(Class<T> type, T service);

    <T> void unexportService(Class<T> type);

    <T> ServiceReference<T> getService(Class<T> type);

    Collection<ServiceReference> getServices();
}
