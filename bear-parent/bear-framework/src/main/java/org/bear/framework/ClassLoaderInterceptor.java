package org.bear.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;
/**
 * .
 * <p/>
 *
 * @author <a href="mailto:ganqing@any123.com">gan qing</a>
 * @version V1.0, 13-2-19
 */
public class ClassLoaderInterceptor implements MethodInterceptor {
	private static final Logger LOG = LoggerFactory
			.getLogger(ClassLoaderInterceptor.class);
	
    private ClassLoader classLoader;

    public ClassLoaderInterceptor(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        ClassLoader originalClassLoader = ClassUtils.overrideThreadContextClassLoader(classLoader);
        LOG.info("classLoader is {}", classLoader.hashCode());
        LOG.info("originalClassLoader is {}", originalClassLoader.hashCode());
        try {
            return invocation.proceed();
        } finally {
        	if(null != originalClassLoader)
        		Thread.currentThread().setContextClassLoader(originalClassLoader);
        }
    }
}