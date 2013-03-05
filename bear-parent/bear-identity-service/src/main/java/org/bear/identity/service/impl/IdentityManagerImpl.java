package org.bear.identity.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.bear.identity.ex.CredentialException;
import org.bear.identity.ex.ErrorCode;
import org.bear.identity.ex.IdentityException;
import org.bear.identity.model.Credential;
import org.bear.identity.model.Group;
import org.bear.identity.model.PasswordProtection;
import org.bear.identity.model.Session;
import org.bear.identity.model.User;
import org.bear.identity.service.CredentialManager;
import org.bear.identity.service.IdentityManager;
import org.bear.identity.service.SessionManager;
import org.bear.identity.service.UserManager;
import org.bear.identity.type.CredentialType;
import org.bear.identity.type.ProtectionQuestion;
import org.bear.identity.type.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-25
 */
public class IdentityManagerImpl implements IdentityManager {
	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private UserManager userManager;
	@Autowired
	private CredentialManager credentialManager;
	@Override
	public String registerUser(User user, Credential credential,
			Map<String, String> userAttributes) {
		if (user == null || credential == null || StringUtils.isBlank(credential.getValue()))
			throw new IdentityException(ErrorCode.IDENTITY_INVALID_REGISTER_USER_INFO);

		if (this.isCredentialExists(credential))
			throw new CredentialException(ErrorCode.CREDENTIAL_EXISTS);

		// 创建用户
		long uid = this.userManager.createUser(user);

		// 用户属性
		this.userManager.setUserAttributes(uid, userAttributes);

		// 注册凭证
		credential.setUid(uid);
		this.credentialManager.createCredential(credential, true);

		this.userManager.updateUserStatus(uid, UserStatus.NORMAL);

		// 创建默认会话
		String token = this.sessionManager.createSession(uid);

		return token;
	}
	@Override
	public String login(Credential credential, long ttl) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void logout(String token) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public long validateToken(String token) {
		return sessionManager.validateToken(token);
	}
	@Override
	public User validateTokenWithUser(String token) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long requestActivation(Credential credential, long ttl) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean validateActivation(Credential credential) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean validateActivationAndCreateCredential(Credential credential) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void changePassword(long uid, String oldPwd, String newPwd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resetPassword(long uid, String newPwd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void protectPassword(PasswordProtection protection, String pwd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean validatePasswordProtection(PasswordProtection protection) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean hasPasswordProtection(long uid) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public ProtectionQuestion getProtectionQuestion(long uid) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void createCredential(Credential credential) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeCredential(Credential credential) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateCredential(Credential credential) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Credential matchCredential(Credential credential) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Credential> getUserCredentials(long uid) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isCredentialExists(Credential credential) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public long getCredentialUid(Credential credential) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void updateUserStatus(long uid, UserStatus status) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public UserStatus getUserStatus(long uid) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public User getUser(long uid) {
		return userManager.getUser(uid);
	}
	@Override
	public Map<Long, User> batchGetUser(Set<Long> uid) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void batchUpdateUser(Set<User> users) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getUserAttribute(long uid, String key) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setUserAttribute(long uid, String key, String value) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeUserAttribute(long uid, String key) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Map<String, String> getUserAttributes(long uid, Set<String> keys) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setUserAttributes(long uid, Map<String, String> values) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeUserAttributes(long uid, Set<String> keys) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Map<Long, String> batchGetUserAttribute(Set<Long> uids, String key) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void batchSetUserAttribute(String key, Map<Long, String> idValues) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void batchRemoveUserAttribute(Set<Long> uids, String key) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Map<Long, Map<String, String>> batchGetUserAttributes(
			Map<Long, Set<String>> idKeys) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void batchSetUserAttributes(Map<Long, Map<String, String>> idValues) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void batchRemoveUserAttributes(Map<Long, Set<String>> idKeys) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Set<String> getUserAttributeNames(long uid) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void clearUserAttributes(long uid) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getSessionAttribute(String token, String key) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Map<String, String> getSessionAttributes(String token,
			Set<String> keys) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setSessionAttribute(String token, String key, String value) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setSessionAttributes(String token, Map<String, String> values) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeSessionAttribute(String token, String key) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeSessionAttributes(String token, Set<String> keys) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Set<String> getSessionAttributeNames(String token) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void clearSessionAttributes(String token) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String createGuestSession() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isNickNameExists(long uid, String nickName) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public User getUserByNick(String nick) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long countUser(String nickName, CredentialType type, String name,
			Date startTime, Date endTime, Map<String, String> attributes) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public User[] listUser(String nickName, CredentialType type, String name,
			Date startTime, Date endTime, long offset, long limit,
			Map<String, String> attributes) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long createGroup(Group group) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void updateGroup(Group group) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeGroup(long groupId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public long countGroups(String name) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<Group> listGroups(String name, long offset, long limit) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void addUserToGroup(long uid, long groupId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeUserFromGroup(long uid, long groupId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isUserInGroup(long uid, long groupId) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public long countGroupUsers(long groupId) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<User> listGroupUsers(long groupId, long offset, long limit) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Group> getUserGroups(long uid) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isGroupNameExists(long groupId, String name) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Group getGroup(long groupId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Group getGroupByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isAdminUser(long uid) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isAdminSession(String token) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void resetAdminUser(long uid) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<Session> activeSessions(int start, int limit, long timeRange) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long countActiveSessions(long timeRange) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Map<Long, List<Credential>> batchGetUserAllCredentials(
			Set<Long> userIds) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
