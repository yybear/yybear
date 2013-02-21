package org.bear.global.service;

import org.bear.global.service.ServiceUnavailableException;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:ganqing@any123.com">gan qing</a>
 * @version V1.0, 13-2-19
 */
public interface ServiceReference<T> {
	T getService() throws ServiceUnavailableException;

    void destroy();
}
