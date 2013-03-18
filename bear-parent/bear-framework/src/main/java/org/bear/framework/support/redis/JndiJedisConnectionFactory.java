package org.bear.framework.support.redis;

import javax.naming.NamingException;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisUtils;
import org.springframework.jndi.JndiObjectLocator;

import redis.clients.jedis.JedisPool;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-12
 */
public class JndiJedisConnectionFactory extends JndiObjectLocator implements InitializingBean, RedisConnectionFactory {
	private JedisPool pool = null;
    private int dbIndex = 0;

    public void setDbIndex(int dbIndex) {
        this.dbIndex = dbIndex;
    }
    
    protected JedisConnection postProcessConnection(JedisConnection connection) {
        return connection;
    }
    
	@Override
	public DataAccessException translateExceptionIfPossible(RuntimeException ex) {
		return JedisUtils.convertJedisAccessException(ex);
	}

	@Override
	public RedisConnection getConnection() {
		return postProcessConnection(new JedisConnection(pool.getResource(), pool, dbIndex));
	}

	@Override
	public void afterPropertiesSet() throws IllegalArgumentException,
			NamingException {
		super.afterPropertiesSet();
		
		pool = (JedisPool)lookup();
	}

}
