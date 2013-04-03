package org.bear.framework.support.spring;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bear.framework.support.spring.tx.ThreadResourceManager;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;


/**
 * 给当前线程绑定资源，类似spring OpenEntityManagerInViewFilter 和 OpenSessionInViewFilter 的做法.
 * 当然需要绑定的资源是可以配置，相当于XXInViewFilter的一个集合.
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-4-2
 */
public class OpenThreadResourceInViewFilter extends OncePerRequestFilter {
	public static final String DEFAULT_THREAD_RESOURCE_MANAGER_BEAN_NAME = "threadResourceManager";

    private String threadResourceManagerBeanName = DEFAULT_THREAD_RESOURCE_MANAGER_BEAN_NAME;
	
	public String getThreadResourceManagerBeanName() {
		return threadResourceManagerBeanName;
	}

	public void setThreadResourceManagerBeanName(
			String threadResourceManagerBeanName) {
		this.threadResourceManagerBeanName = threadResourceManagerBeanName;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		ThreadResourceManager resourceManager = lookupSessionFactory(request);
        boolean participate = false;
        if (resourceManager.hasBind()) {
            participate = true;
        } else {
            logger.debug("Bind threadResources in OpenThreadResourceInViewFilter");
            resourceManager.bind();
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            if (!participate) {
                logger.debug("Unbind threadResources in OpenThreadResourceInViewFilter");
                resourceManager.unBind();
            }
        }
	}
	
	 protected ThreadResourceManager lookupSessionFactory(HttpServletRequest request) {
	        return lookupThreadResourceManager();
	    }

	    protected ThreadResourceManager lookupThreadResourceManager() {
	        if (logger.isDebugEnabled()) {
	            logger.debug("Using ThreadResourceManager '" + getThreadResourceManagerBeanName() + "' for OpenThreadResourceInViewFilter");
	        }
	        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
	        return wac.getBean(getThreadResourceManagerBeanName(), ThreadResourceManager.class);
	    }
}
