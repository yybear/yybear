package org.bear.searcher.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:ganqing@any123.com">gan qing</a>
 * @version V1.0, 2013-1-27
 */
public class SolrDispatchFilter extends org.apache.solr.servlet.SolrDispatchFilter{
	protected static final Logger LOG = LoggerFactory.getLogger(SolrDispatchFilter.class);
    private static final String DATA_DIR_PARAM = "data.dir";

    @Override
    public void init(FilterConfig config) throws ServletException {
    	// 设置solr home目录，默认是tomcat的根目录下solr
        System.setProperty("solr.solr.home", config.getServletContext().getRealPath("/WEB-INF/solr"));
        try {
            String dataDir = ResourceUtils.getFile(getDataDir("file:///opt/any/home/searcher/data")).getAbsolutePath();
            LOG.info("Use solr.data.dir:[{}]", dataDir);
            // 索引数据存放目录，默认是solr home下data
            System.setProperty("solr.data.dir", dataDir);
        } catch (FileNotFoundException ignored) {
            throw new ServletException("solr data dir not found");
        }
        super.init(config); // 然后调用父类的init
        /**
         * solr是作为一个内嵌的服务，并把它保存到servletContext里面
         */
        EmbeddedSolrServer solrServer = new EmbeddedSolrServer(cores, cores.getDefaultCoreName());
        config.getServletContext().setAttribute(SolrDispatchFilter.class.getName(), solrServer);
        
        // spring context loader
        ContextLoader loader = new ContextLoaderListener();
        loader.initWebApplicationContext(config.getServletContext());
    }

    private String getDataDir(String defaultDir) {
        InputStream is = null;
        try {
            is = ResourceUtils.getURL("classpath:/application.properties").openStream();
            Properties props = new Properties();
            props.load(is);
            String s = (String) props.get(DATA_DIR_PARAM);
            if (StringUtils.isNotEmpty(s)) {
                return s;
            }
        } catch (Exception ignored) {
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {
                }
            }
        }
        return defaultDir;
    }
}
