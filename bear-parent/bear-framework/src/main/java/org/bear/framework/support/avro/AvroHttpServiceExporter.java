package org.bear.framework.support.avro;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aopalliance.intercept.MethodInterceptor;
import org.apache.avro.AvroRuntimeException;
import org.apache.avro.ipc.AvroHttpTransceiver;
import org.apache.avro.ipc.Responder;
import org.apache.avro.ipc.specific.SpecificResponder;
import org.bear.framework.ClassLoaderInterceptor;
import org.bear.global.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.support.RemoteExporter;
import org.springframework.web.HttpRequestHandler;
/**
 * .
 * <p/>
 *
 * @author <a href="mailto:ganqing@any123.com">gan qing</a>
 * @version V1.0, 13-2-19
 */
public class AvroHttpServiceExporter extends RemoteExporter implements
		InitializingBean, DisposableBean, HttpRequestHandler {
	private static final Logger LOG = LoggerFactory
			.getLogger(AvroHttpServiceExporter.class);

	private static Map<Class, Responder> responderPool = new ConcurrentHashMap<Class, Responder>();
	private ServiceRegistry serviceRegistry;
	private boolean proxyTargetClass = true;
	
	public void setProxyTargetClass(boolean proxyTargetClass) {
		this.proxyTargetClass = proxyTargetClass;
	}

	@Autowired(required = false)
    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }
	
	@Override
	public void destroy() throws Exception {
		if(serviceRegistry != null)
			serviceRegistry.unexportService(getServiceInterface());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (serviceRegistry != null) {
            List<MethodInterceptor> il = new ArrayList<MethodInterceptor>();
            il.add(new ClassLoaderInterceptor(getBeanClassLoader()));
            /*if (enableMonitor) {
                il.add(monitorInterceptor);
            }*/
            /*if (threadResourceManager != null) {
                il.add(new ThreadResourceSupportInterceptor(threadResourceManager));
            }*/
            serviceRegistry.exportService(getServiceInterface(), getProxyForService0(il));
        }
	}

	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// get responder from pool
		Responder responder = responderPool.get(getServiceInterface());
		if (null == responder) {
			responder = new SpecificResponder(getServiceInterface(),
					getService());
			responderPool.put(getServiceInterface(), responder);
		}

		List<ByteBuffer> requestBufs = AvroHttpTransceiver.readBuffers(request
				.getInputStream());
		try {
			List<ByteBuffer> responseBufs = responder.respond(requestBufs);
			response.setContentLength(AvroHttpTransceiver
					.getLength(responseBufs));
			AvroHttpTransceiver.writeBuffers(responseBufs,
					response.getOutputStream());
		} catch (AvroRuntimeException e) {
			LOG.error(e.getMessage(), e);
			throw new ServletException(e);
		}
	}
	
	private Object getProxyForService0(List<MethodInterceptor> interceptors) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addInterface(getServiceInterface());
        for (MethodInterceptor interceptor : interceptors) {
            proxyFactory.addAdvice(interceptor);
        }
        proxyFactory.setTarget(getService());
        proxyFactory.setOptimize(proxyTargetClass);
        proxyFactory.setProxyTargetClass(proxyTargetClass);
        return proxyFactory.getProxy(getBeanClassLoader());
    }
}
