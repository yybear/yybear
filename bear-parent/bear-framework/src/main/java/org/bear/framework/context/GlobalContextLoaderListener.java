package org.bear.framework.context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.core.env.AbstractEnvironment;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-21
 */
public class GlobalContextLoaderListener extends ContextLoaderListener {
	public static final String ANY_PROFILE_ACTIVE = "profile.active";
    public static final String PROFILE_CONFIG_LOCATION_PARAM = "profileConfigFileLocation";
    public static final String PROFILE_DEV = "dev";
    public static final String PROFILE_TEST = "test";
    public static final String PROFILE_PRODUCTION = "production";
    public static final String FILE_LOG = "filelog.enable";
    
    @Override
    public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
    	GlobalConfigLoader.load();
    	
        String activeProfile = System.getProperty(ANY_PROFILE_ACTIVE);
        if (activeProfile == null) {
            activeProfile = loadFromApplicationProperties(servletContext);
            if (activeProfile == null) {
                activeProfile = System.getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME);
                if (activeProfile == null) {
                    activeProfile = servletContext.getInitParameter(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME);
                    if (activeProfile == null) {
                        activeProfile = PROFILE_DEV;
                    }
                }
            }
        }
        if ("${profile.active}".equals(activeProfile)) {
            activeProfile = PROFILE_DEV;
        }
        System.setProperty(ANY_PROFILE_ACTIVE, activeProfile);
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, activeProfile);
        
		return super.initWebApplicationContext(servletContext);
    }
    
    private String loadFromApplicationProperties(ServletContext sc) {
        String location = sc.getInitParameter(PROFILE_CONFIG_LOCATION_PARAM);
        if (location == null) {
            location = "classpath:/application.properties";
        }
        InputStream is = null;
        try {
            is = ResourceUtils.getURL(location).openStream();
            Properties props = new Properties();
            props.load(is);
            return (String) props.get(ANY_PROFILE_ACTIVE);
        } catch (Exception ignored) {
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {
                }
            }
        }
        return null;
    }
}
