package org.bear.identity.dao;

import java.util.List;

import org.bear.framework.dao.AttributeEntityDao;
import org.bear.identity.model.Session;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-26
 */
public interface SessionDao extends AttributeEntityDao<Session>{
	public static final Session[] EMPTY_SESSION_ARRAY = new Session[0];

	/**
	 * 刷新最后活动时间
	 * 
	 * @param id
	 */
	void hit(long id);

	/**
	 * 根据token获取Session实例
	 * 
	 * @param token
	 * @return
	 */
	Session getByToken(String token);
	
	/**
	 * 根据用户id获取Session实例
	 * 
	 * @param uid
	 * @return
	 */
	Session[] listByUid(long uid);
}
