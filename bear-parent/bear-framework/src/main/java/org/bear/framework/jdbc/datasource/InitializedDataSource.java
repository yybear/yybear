package org.bear.framework.jdbc.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-28
 */
public class InitializedDataSource implements DataSource, InitializingBean, ResourceLoaderAware {
	public static final String MULTI_VALUE_ATTRIBUTE_DELIMITERS = ",; \n\r";

	protected final Logger logger = LoggerFactory.getLogger(InitializedDataSource.class);

	private DataSource dataSource;

	private String scriptEncoding = "UTF-8";

	private String[] sqlScripts = ArrayUtils.EMPTY_STRING_ARRAY;

	protected ResourceLoader resourceLoader;

	public InitializedDataSource() {
		super();
	}

	public InitializedDataSource(DataSource ds) {
		this();

		this.setDataSource(ds);
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setScripts(String scripts) {
		this.sqlScripts = StringUtils.tokenizeToStringArray(scripts, MULTI_VALUE_ATTRIBUTE_DELIMITERS);
	}
	
	public void setScriptEncoding(String scriptEncoding) {
		this.scriptEncoding = scriptEncoding;
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return dataSource.getLogWriter();
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		dataSource.setLogWriter(out);
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		dataSource.setLoginTimeout(seconds);
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return dataSource.getLoginTimeout();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return dataSource.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return dataSource.isWrapperFor(iface);
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}
	
	@Override
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	@Override
	public Connection getConnection(String username, String password)
			throws SQLException {
		return dataSource.getConnection(username, password);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//加载后执行sql脚本
		Assert.notNull(this.dataSource);

		if (!ArrayUtils.isEmpty(this.sqlScripts))
			this.populate();
	}
	
	protected void populate() throws Exception {
		logger.info("Initializing DataSource ...");
		for (String script : this.sqlScripts) {
			logger.info("\tpopulating: [" + script + "]");
			try {
				Resource resource = this.resourceLoader.getResource(script);
				if (resource.exists()) {
					DatabasePopulator populator = this.createPopulator(resource);
					DatabasePopulatorUtils.execute(populator, dataSource);
					logger.info("\t\t[DONE].");
				} else {
					logger.warn("\t\t[NOT EXISTS].");
				}
			} catch (Exception ex) {
				logger.warn("\t\t[IGNORE] with [" + ex.getMessage() + "].");
			}
		}
		logger.info("DataSource initialized.");
	}

	protected DatabasePopulator createPopulator(Resource script) {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.setContinueOnError(false);
		populator.setIgnoreFailedDrops(false);
		populator.setScripts(new Resource[] { script });
		populator.setSqlScriptEncoding(scriptEncoding);
		return populator;
	}
}
