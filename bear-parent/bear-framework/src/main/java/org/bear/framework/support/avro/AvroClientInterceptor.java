package org.bear.framework.support.avro;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.avro.ipc.Transceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.bear.global.service.ServiceReference;
import org.bear.global.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.support.UrlBasedRemoteAccessor;
import org.springframework.util.Assert;
/**
 * .
 * <p/>
 *
 * @author <a href="mailto:ganqing@any123.com">gan qing</a>
 * @version V1.0, 13-2-19
 */
public abstract class AvroClientInterceptor extends UrlBasedRemoteAccessor
		implements MethodInterceptor {
	private static final Logger LOG = LoggerFactory.getLogger(AvroClientInterceptor.class);
	private Object serviceProxy;
	private ServiceRegistry serviceRegistry;

    @Autowired(required = false)
    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

	public Object getServiceProxy() {
		return serviceProxy;
	}

	abstract Transceiver getTransceiver();

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		Object[] args = invocation.getArguments();
		// 如果服务都部署在一个web服务器下，直接获取
		if (serviceRegistry != null) {
			if(LOG.isDebugEnabled()) {
				LOG.debug("ServiceRegistry hashcode is {}", serviceRegistry.hashCode());
				LOG.debug("call service interface is {}", getServiceInterface());
			}
            ServiceReference sr = serviceRegistry.getService(getServiceInterface());
            if (sr != null) {
            	LOG.debug("call service in same jvm");
                try {
                    return method.invoke(sr.getService(), args);
                } catch (InvocationTargetException e) {
                    throw e.getTargetException();
                }
            }
        }
		
		String name = method.getName();
		if (args.length == 0) {
			if ("toString".equals(name)) {
				return "Avro proxy for service URL [" + getServiceUrl() + "]";
			} else if ("hashCode".equals(name)) {
				return getServiceUrl().hashCode();
			}
		} else if (args.length == 1 && "equals".equals(name)) {
			return getServiceUrl().equals(args[0]);
		}

		Object client = SpecificRequestor.getClient(method.getDeclaringClass(),
				getTransceiver());
		Assert.notNull(client,
				"the Avro RPC client was not correctly created. Aborting.");
		ClassLoader originalClassLoader = overrideThreadContextClassLoader();
		try {
			return method.invoke(client, args);
		} catch (InvocationTargetException e) {
			Throwable target = e.getTargetException();
			throw target;
		} catch (Throwable ex) {
			throw ex;
		} finally {
			resetThreadContextClassLoader(originalClassLoader);
		}
	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		if (getServiceInterface() == null) {
			throw new IllegalArgumentException(
					"property serviceInterface is required.");
		}
		ProxyFactory pf = new ProxyFactory(getServiceInterface(), this);
		this.serviceProxy = pf.getProxy(getBeanClassLoader());
	}
}
