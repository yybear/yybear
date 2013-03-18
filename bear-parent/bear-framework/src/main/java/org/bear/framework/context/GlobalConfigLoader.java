package org.bear.framework.context;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Properties;

import java.util.logging.Logger;

import org.bear.framework.Constants;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;
import org.springframework.util.ResourceUtils;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-14
 */
public class GlobalConfigLoader {
	public static final String HOME_KEY = "BEAR_HOME";
	public static final String HOME_NAME = "bear.home";
    public static final String HOME_DIRECTORY = "home";
    public static final String CONFIG_FILE_NAME = "bear.properties";
    private static final Logger LOG = Logger.getLogger(GlobalConfigLoader.class.getName());

    public static void load(final String... locations) {
    	GlobalConfigLoader al = new GlobalConfigLoader();
        al.loadConfig(locations);
    }

    public void loadConfig(final String... locations) {
        String homePath = getConfigProperty(HOME_NAME);
        File home;
        if (homePath == null) {
            home = determineHomeDir();
            homePath = home.getAbsolutePath();
            setConfigProperty(HOME_NAME, homePath);
        } else {
            LOG.info("home has been set to [" + homePath + "]");
            return;
        }
        File configFile = new File(home, CONFIG_FILE_NAME);
        Resource res;
        Properties result = new Properties();
        PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
        if (!configFile.exists()) {
            res = new UrlResource(getClass().getResource("/META-INF/" + CONFIG_FILE_NAME));
        } else {
            res = new FileSystemResource(configFile);
        }
        if (res.exists()) {
            try {
                loadProperties(res, result, propertiesPersister);
            } catch (IOException e) {
                LOG.warning("Could not load properties from " + res + ": " + e.getMessage());
            }
        } else {
            LOG.info("Load any global config error,config file not found");
        }
        if (locations != null) {
            for (String location : locations) {
                try {
                    loadProperties(new UrlResource(ResourceUtils.getURL(location)), result, propertiesPersister);
                } catch (IOException e) {
                    LOG.warning("Could not load properties from " + res + ": " + e.getMessage());
                }
            }
        }
        Enumeration names = result.propertyNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            setConfigProperty(name, result.getProperty(name));
        }
    }

    private void loadProperties(Resource res, Properties result, PropertiesPersister propertiesPersister) throws IOException {
        InputStream is = null;
        try {
            is = res.getInputStream();
            propertiesPersister.load(result, new InputStreamReader(is, Constants.DEFAULT_CHARSET));
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    protected String getConfigProperty(String key) {
        return System.getProperty(key);
    }

    protected void setConfigProperty(String key, String value) {
        if (getConfigProperty(key) == null) {
            LOG.info("Set global property [" + key + " = " + value + "]");
            System.setProperty(key, value);
        } else {
            LOG.info("Global property exist [" + key + " = " + value + "],skip");
        }
    }

    private File determineHomeDir() {
        File home;
        for (String path : new String[]{
                System.getProperty(HOME_KEY),
                System.getenv(HOME_KEY)
        }) {
            if (path != null) {
                home = new File(path);
                if (home.exists())
                    return home;
            }
        }
        for (String path : new String[]{
                System.getProperty("catalina.base"),
                System.getProperty("catalina.home")
        }) {
            if (path != null) {
                home = new File(path, HOME_DIRECTORY);
                if (home.exists())
                    return home;
            }
        }
        home = new File(System.getProperty("user.home"), HOME_DIRECTORY);
        if (!home.exists()) {
            home.mkdir();
        }
        return home;
    }

}
