package org.bear.framework.support.avro;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.avro.ipc.HttpTransceiver;
import org.apache.avro.ipc.Transceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
/**
 * .
 * <p/>
 *
 * @author <a href="mailto:ganqing@any123.com">gan qing</a>
 * @version V1.0, 13-2-19
 */
public class AvroHttpProxyFactoryBean<T> extends AvroClientInterceptor implements FactoryBean<T>{
	private static final Logger LOG = LoggerFactory.getLogger(AvroHttpProxyFactoryBean.class);

	public T getObject() {
		return (T) getServiceProxy();
	}

	public Class<?> getObjectType() {
		return getServiceInterface();
	}

	public boolean isSingleton() {
		return true;
	}

	@Override
	protected Transceiver getTransceiver() {
		Transceiver t = null;
		try {
			t = new HttpTransceiver(new URL(getServiceUrl()));
		} catch (MalformedURLException e) {
			LOG.error("the avro remote server url is wrong");
		}
		
		return t;
	}
}
