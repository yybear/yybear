package org.bear.identity.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bear.identity.ex.SessionException;
import org.bear.identity.model.Session;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-20
 */
public interface SessionManager {
	/**
	 * TOKEN无效
	 */
	public static final long VALIDATE_TOKEN_INVALID = 0;
	/**
	 * 临时会话TOKEN
	 */
	public static final long VALIDATE_TOKEN_TEMP = -1;
	/**
	 * 相同用户已在其他地方登录
	 */
	public static final long VALIDATE_TOKEN_DUPLICATED = -2;

	/**
	 * 创建会话（默认生命周期）
	 * 
	 * @param uid
	 *            用户ID
	 * @return SSO Token
	 * @throws SessionException
	 */
	String createSession(long uid) throws SessionException;

	/**
	 * 创建临时会话（默认生命周期）
	 * 
	 * @return
	 * @throws SessionException
	 */
	String createSession() throws SessionException;

	/**
	 * 创建会话（指定存活时间）
	 * 
	 * @param uid
	 *            用户ID
	 * @param ttl
	 *            存活时间，单位：毫秒
	 * @param tti
	 *            TODO
	 * @return SSO Token
	 * @throws SessionException
	 */
	String createSession(long uid, long ttl, long tti) throws SessionException;

	/**
	 * 销毁会话
	 * 
	 * @param token
	 *            SSO Token
	 * @throws SessionException
	 */
	public void destroySession(String token) throws SessionException;

	/**
	 * 获取会话
	 * 
	 * @param token
	 * @return 会话信息
	 * @throws SessionException
	 */
	public Session getSession(String token) throws SessionException;

	/**
	 * 校验SSO Token是否有效
	 * 
	 * @param token
	 * @return 如果>0，则会话有效，并且返回值代表用户ID，否则<=0，则会话已失效
	 * @throws SessionException
	 */
	public long validateToken(String token) throws SessionException;

	/**
	 * 获取用户当前会话属性值
	 * 
	 * @param token
	 *            用户当前SSO令牌
	 * 
	 * @param key
	 *            属性名
	 * 
	 * @return 属性值
	 * 
	 * @throws SessionException
	 * 
	 */
	public String getAttribute(String token, String key)
			throws SessionException;

	/**
	 * 批量获取用户当前会话属性值
	 * 
	 * @param token
	 *            用户当前SSO令牌
	 * 
	 * @param keys
	 *            属性名集合
	 * 
	 * @return 属性值映射列表
	 * 
	 * @throws SessionException
	 * 
	 */
	public Map<String, String> getAttributes(String token, Set<String> keys)
			throws SessionException;

	/**
	 * 获取当前用户会话已有的属性名集合
	 * 
	 * @param token
	 *            用户当前SSO令牌
	 * 
	 * @return 属性名集合
	 * 
	 * @throws SessionException
	 * 
	 */
	public Set<String> getAttributeNames(String token) throws SessionException;

	/**
	 * 移除会话属性
	 * 
	 * @param token
	 * @param key
	 * @throws SessionException
	 */
	public void removeAttribute(String token, String key)
			throws SessionException;

	public void removeAttributes(String token, Set<String> keys)
			throws SessionException;

	/**
	 * 设置用户当前会话属性值
	 * 
	 * @param token
	 *            用户当前SSO令牌
	 * 
	 * @param key
	 *            属性名
	 * 
	 * @param value
	 *            属性值
	 * 
	 * @throws SessionException
	 * 
	 */
	public void setAttribute(String token, String key, String value)
			throws SessionException;

	/**
	 * 批量设置用户当前会话属性值
	 * 
	 * @param token
	 *            用户当前SSO令牌
	 * 
	 * @param values
	 *            属性值映射列表
	 * 
	 * @throws SessionException
	 * 
	 */
	public void setAttributes(String token, Map<String, String> values)
			throws SessionException;

	/**
	 * 清除会话所有属性
	 * 
	 * @param token
	 *            用户当前SSO令牌
	 * @throws SessionException
	 */
	public void clearAttributes(String token) throws SessionException;

	/**
	 * 清理过期会话
	 * 
	 * @throws SessionException
	 */
	public void clearExpiredSessions() throws SessionException;

	/**
	 * 判断会话是否已过期
	 * 
	 * @param session
	 * @return true-已过期
	 * @throws SessionException
	 */
	public boolean isExpired(Session session) throws SessionException;

	/**
	 * 判断当前会话是否是否超级管理员会话
	 * 
	 * @param token
	 *            会话Token
	 */
	public boolean isAdminSession(String token) throws SessionException;
	
	public List<Session> activeSessions(int start, int limit, long timeRange) throws SessionException;
	
	public long countActiveSessions(long timeRange) throws SessionException;
}
