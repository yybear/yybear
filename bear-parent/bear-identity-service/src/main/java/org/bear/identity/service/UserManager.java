package org.bear.identity.service;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.bear.identity.ex.UserException;
import org.bear.identity.model.User;
import org.bear.identity.type.CredentialType;
import org.bear.identity.type.UserStatus;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-20
 */
public interface UserManager {
	long createUser(User user) throws UserException;

	/**
	 * 设置用户状态
	 * 
	 * @param uid
	 * @param status
	 * @throws UserException
	 * 
	 */
	public void updateUserStatus(long uid, UserStatus status) throws UserException;

	/**
	 * 获取用户状态
	 * 
	 * @param uid
	 * @return TODO
	 * @throws UserException
	 */
	public UserStatus getUserStatus(long uid) throws UserException;

	/**
	 * 获取用户信息
	 * 
	 * @param uid
	 * 
	 * @throws UserException
	 * 
	 */
	public User getUser(long uid) throws UserException;

	/**
	 * 批量获取用户信息
	 * 
	 * @throws UserException
	 * 
	 * @param uids
	 */
	public Map<Long, User> batchGetUser(Set<Long> uids) throws UserException;

	/**
	 * 更新用户信息
	 * 
	 * @throws UserException
	 * 
	 * @param user
	 */
	public void updateUser(User user) throws UserException;

	/**
	 * 批量更新用户信息
	 * 
	 * @throws UserException
	 * 
	 * @param users
	 */
	public void batchUpdateUser(Set<User> users) throws UserException;

	/**
	 * 获取用户属性值
	 * 
	 * @param uid
	 *            用户ID
	 * @param key
	 *            属性名
	 * @return 属性值
	 * @throws UserException
	 * 
	 */
	public String getUserAttribute(long uid, String key) throws UserException;

	public void setUserAttribute(long uid, String key, String value) throws UserException;

	public void removeUserAttribute(long uid, String key) throws UserException;

	public Map<String, String> getUserAttributes(long uid, Set<String> keys) throws UserException;

	public void setUserAttributes(long uid, Map<String, String> values) throws UserException;

	public void removeUserAttributes(long uid, Set<String> keys) throws UserException;

	public Map<Long, String> batchGetUserAttribute(Set<Long> uids, String key) throws UserException;

	public void batchSetUserAttribute(String key, Map<Long, String> idValues) throws UserException;

	public void batchRemoveUserAttribute(Set<Long> uids, String key) throws UserException;

	public Map<Long, Map<String, String>> batchGetUserAttributes(Map<Long, Set<String>> idKeys)
			throws UserException;

	public void batchSetUserAttributes(Map<Long, Map<String, String>> idValues) throws UserException;

	public void batchRemoveUserAttributes(Map<Long, Set<String>> idKeys) throws UserException;

	public Set<String> getUserAttributeNames(long uid) throws UserException;

	public void clearUserAttributes(long uid) throws UserException;

	public boolean isNickNameExists(long uid, String nickName) throws UserException;

	public User getUserByNick(String nick) throws UserException;

	/**
	 * 统计符合条件的用户总数
	 * 
	 * @return 符合条件的用户数
	 * 
	 * @param nickName
	 *            昵称
	 * @param type
	 *            凭证类型
	 * @param name
	 *            凭证名(登录名)
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param attributes
	 *            TODO
	 */
	public long countUser(String nickName, CredentialType type, String name, Date startTime, Date endTime,
			Map<String, String> attributes) throws UserException;

	/**
	 * 列出符合条件的用户
	 * 
	 * @return 分页用户列表
	 * 
	 * @param nickName
	 *            昵称
	 * @param type
	 *            凭证类型
	 * @param name
	 *            凭证名(登录名)
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param attributes
	 *            TODO
	 * @param offset
	 *            分页起始位置
	 * @param limit
	 *            分页大小
	 */
	public User[] listUser(String nickName, CredentialType type, String name, Date startTime, Date endTime,
			Map<String, String> attributes, long offset, long limit) throws UserException;

	/**
	 * 判断用户是否超级管理员
	 * 
	 * @param uid
	 *            用户标识
	 */
	public boolean isAdminUser(long uid) throws UserException;

	/**
	 * 重置超级管理员（系统仅有一个超级管理员）
	 * 
	 * @param uid
	 *            用户标识
	 */
	public void resetAdminUser(long uid) throws UserException;
}
