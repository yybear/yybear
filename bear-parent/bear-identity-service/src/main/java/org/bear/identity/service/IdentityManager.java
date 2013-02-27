package org.bear.identity.service;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bear.identity.ex.IdentityException;
import org.bear.identity.model.Credential;
import org.bear.identity.model.Group;
import org.bear.identity.model.PasswordProtection;
import org.bear.identity.model.Session;
import org.bear.identity.model.User;
import org.bear.identity.type.CredentialType;
import org.bear.identity.type.ProtectionQuestion;
import org.bear.identity.type.UserStatus;
/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-25
 */
public interface IdentityManager {
	/**
	 * 注册
	 * 
	 * @param user
	 *            用户信息
	 * @param credential
	 *            凭证
	 * @param userAttributes
	 *            用户扩展属性
	 * 
	 * @return 注册成功后自动创建的SSO会话Token
	 * 
	 */
	public String registerUser(User user, Credential credential, Map<String, String> userAttributes)
			throws IdentityException;

	/**
	 * 登录
	 * 
	 * @param credential
	 *            登录凭证
	 * 
	 * @param ttl
	 *            会话保持时间（毫秒）, -1表示默认保持时间（取决于空闲时间）
	 * 
	 * @return 登录成功后创建的SSO会话Token
	 * 
	 */
	public String login(Credential credential, long ttl) throws IdentityException;

	/**
	 * 注销
	 * 
	 * @param token
	 *            用户当前SSO令牌(token)
	 * @throws IdentityException
	 * 
	 */
	public void logout(String token) throws IdentityException;

	/**
	 * 验证token是否具有合法会话，并返回会话用户id
	 * 
	 * @param token
	 *            用户当前SSO令牌
	 * @return 用户唯一标识号
	 * @throws IdentityException
	 * 
	 */
	public long validateToken(String token) throws IdentityException;

	/**
	 * 验证token是否具有合法会话，并返回会话用户
	 * 
	 * @param token
	 *            用户当前SSO令牌
	 * @return 用户
	 * @throws IdentityException
	 * 
	 */
	public User validateTokenWithUser(String token) throws IdentityException;

	/**
	 * 发送验证码到所需绑定凭证对应的地址（邮件/手机）
	 * 
	 * @param credential
	 *            凭证
	 * @param ttl
	 *            TODO
	 * @throws IdentityException
	 * 
	 */
	public long requestActivation(Credential credential, long ttl) throws IdentityException;

	/**
	 * 校验激活码
	 * 
	 * @param credential
	 *            凭证
	 * 
	 * @throws IdentityException
	 * 
	 */
	public boolean validateActivation(Credential credential) throws IdentityException;

	/**
	 * 校验激活码，并校验成功后创建凭证
	 * 
	 * @param credential
	 *            凭证
	 * 
	 * @throws IdentityException
	 * 
	 */
	public boolean validateActivationAndCreateCredential(Credential credential) throws IdentityException;

	/**
	 * 修改密码
	 * 
	 * @param uid
	 *            用户唯一标识号
	 * @param oldPwd
	 *            原密码
	 * @param newPwd
	 *            新密码
	 * 
	 * @throws IdentityException
	 * 
	 */
	public void changePassword(long uid, String oldPwd, String newPwd) throws IdentityException;

	/**
	 * 用新密码重置用户密码
	 * 
	 * @param uid
	 *            用户唯一标识号
	 * @param newPwd
	 *            新密码，为“”时产生随机密码
	 * @throws IdentityException
	 */
	public void resetPassword(long uid, String newPwd) throws IdentityException;

	/**
	 * 保护密码
	 * 
	 * @param protection
	 *            密码保护（密码问题-答案）
	 * @param pwd
	 *            当前用户密码
	 * @throws IdentityException
	 * 
	 */
	public void protectPassword(PasswordProtection protection, String pwd) throws IdentityException;

	/**
	 * 密码保护验证
	 * 
	 * @param protection
	 *            用户密码保护（密码问题-答案）
	 * @return 验证是否通过
	 * @throws IdentityException
	 * 
	 */
	public boolean validatePasswordProtection(PasswordProtection protection) throws IdentityException;

	/**
	 * 判断用户密保是否存在
	 * 
	 * @param uid
	 * @return
	 * @throws IdentityException
	 */
	public boolean hasPasswordProtection(long uid) throws IdentityException;

	/**
	 * 获取用户密保问题
	 * 
	 * @param uid
	 * @return
	 * @throws IdentityException
	 */
	public ProtectionQuestion getProtectionQuestion(long uid) throws IdentityException;

	/**
	 * 创建用户凭证绑定
	 * 
	 * @param credential
	 *            凭证
	 * @throws IdentityException
	 * 
	 */
	public void createCredential(Credential credential) throws IdentityException;

	/**
	 * 取消用户凭证绑定
	 * 
	 * @param credential
	 *            凭证
	 * @throws IdentityException
	 * 
	 */
	public void removeCredential(Credential credential) throws IdentityException;

	/**
	 * 更新用户凭证
	 * 
	 * @param credential
	 * @throws IdentityException
	 */
	public void updateCredential(Credential credential) throws IdentityException;

	/**
	 * 根据凭证部分内容获取完整信息
	 * 
	 * @param credential
	 * @return
	 * @throws CredentialException
	 */
	public Credential matchCredential(Credential credential) throws IdentityException;

	/**
	 * 获取用户所绑定的凭证
	 * 
	 * @param uid
	 * @throws IdentityException
	 * 
	 */
	public List<Credential> getUserCredentials(long uid) throws IdentityException;

	/**
	 * 判断凭证是否已经存在
	 * 
	 * @param credential
	 * @return
	 */
	public boolean isCredentialExists(Credential credential) throws IdentityException;

	/**
	 * 根据Credential（type和name）获取用户ID
	 * 
	 * @param credential
	 * @return
	 * @throws CredentialException
	 */
	public long getCredentialUid(Credential credential) throws IdentityException;

	/**
	 * 设置用户状态
	 * 
	 * @param uid
	 * @param status
	 * @throws IdentityException
	 * 
	 */
	public void updateUserStatus(long uid, UserStatus status) throws IdentityException;

	/**
	 * 获取用户状态
	 * 
	 * @param uid
	 * @return
	 * @throws IdentityException
	 */
	public UserStatus getUserStatus(long uid) throws IdentityException;

	/**
	 * 获取用户信息
	 * 
	 * @param uid
	 * 
	 * @throws IdentityException
	 * 
	 */
	public User getUser(long uid) throws IdentityException;

	/**
	 * 批量获取用户信息
	 * 
	 * @throws IdentityException
	 * 
	 * @param uid
	 */
	public Map<Long, User> batchGetUser(Set<Long> uid) throws IdentityException;

	/**
	 * 更新用户信息
	 * 
	 * @throws IdentityException
	 * 
	 * @param user
	 */
	public void updateUser(User user) throws IdentityException;

	/**
	 * 批量更新用户信息
	 * 
	 * @throws IdentityException
	 * 
	 * @param users
	 */
	public void batchUpdateUser(Set<User> users) throws IdentityException;

	/**
	 * 获取用户属性值
	 * 
	 * @param uid
	 *            用户ID
	 * @param key
	 *            属性名
	 * @return 属性值
	 * @throws IdentityException
	 * 
	 */
	public String getUserAttribute(long uid, String key) throws IdentityException;

	public void setUserAttribute(long uid, String key, String value) throws IdentityException;

	public void removeUserAttribute(long uid, String key) throws IdentityException;

	public Map<String, String> getUserAttributes(long uid, Set<String> keys) throws IdentityException;

	public void setUserAttributes(long uid, Map<String, String> values) throws IdentityException;

	public void removeUserAttributes(long uid, Set<String> keys) throws IdentityException;

	public Map<Long, String> batchGetUserAttribute(Set<Long> uids, String key) throws IdentityException;

	public void batchSetUserAttribute(String key, Map<Long, String> idValues) throws IdentityException;

	public void batchRemoveUserAttribute(Set<Long> uids, String key) throws IdentityException;

	public Map<Long, Map<String, String>> batchGetUserAttributes(Map<Long, Set<String>> idKeys)
			throws IdentityException;

	public void batchSetUserAttributes(Map<Long, Map<String, String>> idValues) throws IdentityException;

	public void batchRemoveUserAttributes(Map<Long, Set<String>> idKeys) throws IdentityException;

	public Set<String> getUserAttributeNames(long uid) throws IdentityException;

	public void clearUserAttributes(long uid) throws IdentityException;

	/**
	 * 获取用户当前会话属性值
	 * 
	 * @param token
	 *            用户当前SSO令牌
	 * @param key
	 *            属性名
	 * @return 属性值
	 * @throws IdentityException
	 * 
	 */
	public String getSessionAttribute(String token, String key) throws IdentityException;

	/**
	 * 批量获取用户当前会话属性值
	 * 
	 * @param token
	 *            用户当前SSO令牌
	 * @param keys
	 *            属性名集合
	 * @return 属性值映射列表
	 * @throws IdentityException
	 * 
	 */
	public Map<String, String> getSessionAttributes(String token, Set<String> keys) throws IdentityException;

	/**
	 * 设置用户当前会话属性值
	 * 
	 * @param token
	 *            用户当前SSO令牌
	 * @param key
	 *            属性名
	 * @param value
	 *            属性值
	 * @throws IdentityException
	 * 
	 */
	public void setSessionAttribute(String token, String key, String value) throws IdentityException;

	/**
	 * 批量设置用户当前会话属性值
	 * 
	 * @param token
	 *            用户当前SSO令牌
	 * @param values
	 *            属性值映射列表
	 * @throws IdentityException
	 * 
	 */
	public void setSessionAttributes(String token, Map<String, String> values) throws IdentityException;

	/**
	 * 移除用户会话属性
	 * 
	 * @param token
	 *            用户当前SSO令牌
	 * @param key
	 *            属性名
	 * @throws IdentityException
	 * 
	 */
	public void removeSessionAttribute(String token, String key) throws IdentityException;

	/**
	 * 批量移除用户会话属性
	 * 
	 * @param token
	 *            用户当前SSO令牌
	 * @param keys
	 *            属性名集合
	 * @throws IdentityException
	 * 
	 */
	public void removeSessionAttributes(String token, Set<String> keys) throws IdentityException;

	/**
	 * 获取当前用户会话已有的属性名集合
	 * 
	 * @param token
	 *            用户当前SSO令牌
	 * @return 属性名集合
	 * @throws IdentityException
	 * 
	 */
	public Set<String> getSessionAttributeNames(String token) throws IdentityException;

	/**
	 * 清除用户会话所有属性
	 * 
	 * @param token
	 *            用户当前SSO令牌
	 * @throws IdentityException
	 * 
	 */
	public void clearSessionAttributes(String token) throws IdentityException;

	/**
	 * 创建临时会话
	 * 
	 * @return
	 * @throws IdentityException
	 */
	public String createGuestSession() throws IdentityException;

	/**
	 * 检测昵称是否已存在
	 * 
	 * @param uid
	 *            用户ID，<=0时表示注册时检测，>0时表示编辑基本信息时检测
	 * @param nickName
	 * @return
	 * @throws IdentityException
	 */
	public boolean isNickNameExists(long uid, String nickName) throws IdentityException;

	/**
	 * 根据昵称获取用户信息
	 * 
	 * @param nick
	 * @return
	 * @throws IdentityException
	 */
	public User getUserByNick(String nick) throws IdentityException;

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
			Map<String, String> attributes) throws IdentityException;

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
	 * @param offset
	 *            分页起始位置
	 * @param limit
	 *            分页大小
	 * @param attributes
	 *            TODO
	 */
	public User[] listUser(String nickName, CredentialType type, String name, Date startTime, Date endTime,
			long offset, long limit, Map<String, String> attributes) throws IdentityException;

	/**
	 * 创建用户组
	 * 
	 * @return 返回用户组标识
	 * 
	 * @param group
	 */
	public long createGroup(Group group) throws IdentityException;

	/**
	 * 更新用户组
	 * 
	 * @param group
	 */
	public void updateGroup(Group group) throws IdentityException;

	/**
	 * 删除用户组
	 * 
	 * @param groupId
	 *            用户组标识
	 */
	public void removeGroup(long groupId) throws IdentityException;

	/**
	 * 获取符合条件用户组数量
	 * 
	 * @param name
	 *            用户组名称，空不作为条件，支持模糊（like）匹配
	 */
	public long countGroups(String name) throws IdentityException;

	/**
	 * 获取符合条件用户组列表
	 * 
	 * @param name
	 *            用户组名称，空不作为条件，支持模糊（like）匹配
	 * 
	 * @param offset
	 *            分页起始位置
	 * 
	 * @param limit
	 *            分页大小
	 */
	public List<Group> listGroups(String name, long offset, long limit) throws IdentityException;

	/**
	 * 将用户添加到用户组
	 * 
	 * @param uid
	 *            用户标识
	 * 
	 * @param groupId
	 *            用户组标识
	 */
	public void addUserToGroup(long uid, long groupId) throws IdentityException;

	/**
	 * 从用户组中移除用户
	 * 
	 * @param uid
	 *            用户标识
	 * 
	 * @param groupId
	 *            用户组标识
	 */
	public void removeUserFromGroup(long uid, long groupId) throws IdentityException;

	/**
	 * 判断用户是否在指定组内
	 * 
	 * @param uid
	 *            用户标识
	 * 
	 * @param groupId
	 *            用户组标识
	 */
	public boolean isUserInGroup(long uid, long groupId) throws IdentityException;

	/**
	 * 获取指定用户组下的用户数
	 * 
	 * @param groupId
	 *            用户组标识
	 */
	public long countGroupUsers(long groupId) throws IdentityException;

	/**
	 * 列出指定用户组下的用户
	 * 
	 * @param groupId
	 *            用户组标识
	 * 
	 * @param offset
	 * @param limit
	 */
	public List<User> listGroupUsers(long groupId, long offset, long limit) throws IdentityException;

	/**
	 * 获取指定用户所属的用户组列表
	 * 
	 * @param uid
	 *            用户标识
	 */
	public List<Group> getUserGroups(long uid) throws IdentityException;

	/**
	 * 判断用户组名称是否存在
	 * 
	 * @return 存在返回true，否则false
	 * 
	 * @param name
	 *            用户组名称
	 */
	public boolean isGroupNameExists(long groupId, String name) throws IdentityException;

	/**
	 * 获取用户组
	 * 
	 * @return 用户组信息
	 * 
	 * @param groupId
	 *            用户组ID
	 */
	public Group getGroup(long groupId) throws IdentityException;

	/**
	 * 根据名称获取用户组
	 * 
	 * @return 用户组信息
	 * 
	 * @param name
	 *            用户组名称
	 */
	public Group getGroupByName(String name) throws IdentityException;

	/**
	 * 判断用户是否超级管理员
	 * 
	 * @param uid
	 *            用户标识
	 */
	public boolean isAdminUser(long uid) throws IdentityException;

	/**
	 * 判断当前会话是否是否超级管理员会话
	 * 
	 * @param token
	 *            会话Token
	 */
	public boolean isAdminSession(String token) throws IdentityException;

	/**
	 * 重置超级管理员（系统仅有一个超级管理员）
	 * 
	 * @param uid
	 *            用户标识
	 */
	public void resetAdminUser(long uid) throws IdentityException;
	
	public List<Session> activeSessions(int start, int limit, long timeRange) throws IdentityException;
	
	public long countActiveSessions(long timeRange) throws IdentityException;
	
	public Map<Long, List<Credential>> batchGetUserAllCredentials(
			Set<Long> userIds) throws IdentityException;
}
