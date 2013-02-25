package org.bear.framework.context;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.ContextLoader;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-21
 */
public class GlobalContextLoaderListener extends ContextLoader implements ServletContextListener {
	public static final String ANY_PROFILE_ACTIVE = "profile.active";
    public static final String PROFILE_CONFIG_LOCATION_PARAM = "profileConfigFileLocation";
    public static final String PROFILE_DEV = "dev";
    public static final String PROFILE_TEST = "test";
    public static final String PROFILE_PRODUCTION = "production";
    public static final String FILE_LOG = "filelog.enable";
    
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
