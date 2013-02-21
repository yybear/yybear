package org.bear.global.service.impl;

import org.bear.global.service.ServiceReference;
import org.bear.global.service.ServiceUnavailableException;
/**
 * .
 * <p/>
 *
 * @author <a href="mailto:ganqing@any123.com">gan qing</a>
 * @version V1.0, 13-2-19
 */
public class InjvmServiceReference<T> implements ServiceReference<T> {

	private T service;
    private boolean destroyed = false;

    public InjvmServiceReference(T service) {
        this.service = service;
    }

    @Override
    public T getService() throws ServiceUnavailableException{
        if (destroyed) {
            throw new ServiceUnavailableException("service has been destroyed,can not be invoked any more!");
        }
        return service;
    }

    @Override
    public void destroy() {
        service = null;
        destroyed = true;
    }

}
