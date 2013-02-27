package org.bear.identity.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.avro.AvroRemoteException;
import org.bear.api.identity.Credential;
import org.bear.api.identity.CredentialType;
import org.bear.api.identity.Group;
import org.bear.api.identity.IdentityService;
import org.bear.api.identity.PasswordProtection;
import org.bear.api.identity.ProtectionQuestion;
import org.bear.api.identity.Session;
import org.bear.api.identity.User;
import org.bear.api.identity.UserStatus;
import org.bear.api.type.GlobalException;
import org.bear.identity.service.IdentityManager;
import static org.bear.identity.util.ConvertUtils.*;
import static org.bear.framework.util.TypeUtils.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-20
 */
public class AvroIdentityServiceImpl implements IdentityService {
	@Autowired
	protected IdentityManager identityManager;
	
	@Override
	public String registerUser(final User user, final Credential credential,
			final Map<String, String> userAttributes) throws GlobalException, AvroRemoteException {

		return identityManager.registerUser(toServiceUser(user),
				toServiceCredential(credential), userAttributes);
	}

	@Override
	public String login(final Credential credential, final long ttl) throws GlobalException, AvroRemoteException {

		return identityManager.login(toServiceCredential(credential), ttl);
	}

	@Override
	public Void logout(final String token) throws GlobalException, AvroRemoteException {

		identityManager.logout(token);
		return null;
	}

	@Override
	public long validateToken(final String token) throws GlobalException, AvroRemoteException {

		return identityManager.validateToken(token);
	}

	@Override
	public User validateTokenWithUser(final String token) throws GlobalException, AvroRemoteException {

		return toApiUser(identityManager.validateTokenWithUser(token));
	}

	@Override
	public long requestActivation(final Credential credential, final long ttl) throws GlobalException,
			AvroRemoteException {

		return identityManager.requestActivation(toServiceCredential(credential), ttl);
	}

	@Override
	public boolean validateActivation(final Credential credential) throws GlobalException, AvroRemoteException {

		return identityManager.validateActivation(toServiceCredential(credential));
	}

	@Override
	public Void changePassword(final long uid, final String oldPwd, final String newPwd) throws GlobalException,
			AvroRemoteException {

		identityManager.changePassword(uid, oldPwd, newPwd);
		return null;
	}

	@Override
	public Void resetPassword(final long uid, String newPwd) throws GlobalException, AvroRemoteException {
		identityManager.resetPassword(uid, newPwd);
		return null;
	}

	@Override
	public Void protectPassword(final PasswordProtection protection, final String pwd) throws GlobalException,
			AvroRemoteException {

		identityManager.protectPassword(toServicePasswordProtection(protection), pwd);
		return null;
	}

	@Override
	public boolean validatePasswordProtection(final PasswordProtection protection) throws GlobalException,
			AvroRemoteException {

		return identityManager.validatePasswordProtection(toServicePasswordProtection(protection));
	}

	@Override
	public Void createCredential(final Credential credential) throws GlobalException, AvroRemoteException {

		identityManager.createCredential(toServiceCredential(credential));
		return null;
	}

	@Override
	public Void removeCredential(final Credential credential) throws GlobalException, AvroRemoteException {

		identityManager.removeCredential(toServiceCredential(credential));
		return null;
	}

	@Override
	public List<Credential> getUserCredentials(final long uid) throws GlobalException, AvroRemoteException {

		return Collections.unmodifiableList(toApiCredential(identityManager
				.getUserCredentials(uid)));
	}

	@Override
	public Void updateUserStatus(final long uid, final UserStatus status) throws GlobalException, AvroRemoteException {

		identityManager.updateUserStatus(uid, toServiceUserStatus(status));
		return null;
	}

	@Override
	public User getUser(final long uid) throws GlobalException, AvroRemoteException {

		return toApiUser(identityManager.getUser(uid));
	}

	@Override
	public Map<String, User> batchGetUser(final List<Long> uid) throws GlobalException, AvroRemoteException {

		return Collections.unmodifiableMap(toApiUserMap(identityManager.batchGetUser(toSet(uid))));
	}

	@Override
	public Void updateUser(final User user) throws GlobalException, AvroRemoteException {

		identityManager.updateUser(toServiceUser(user));
		return null;
	}

	@Override
	public Void batchUpdateUser(final List<User> user) throws GlobalException, AvroRemoteException {

		identityManager.batchUpdateUser(toServiceUserSet(user));
		return null;
	}

	@Override
	public String getUserAttribute(final long uid, final String key) throws GlobalException, AvroRemoteException {

		return identityManager.getUserAttribute(uid, key);
	}

	@Override
	public Void setUserAttribute(final long uid, final String key, final String value) throws GlobalException,
			AvroRemoteException {

		identityManager.setUserAttribute(uid, key, value);
		return null;
	}

	@Override
	public Void removeUserAttribute(final long uid, final String key) throws GlobalException, AvroRemoteException {

		identityManager.removeUserAttribute(uid, key);
		return null;
	}

	@Override
	public Map<String, String> getUserAttributes(final long uid, final List<String> keys) throws GlobalException,
			AvroRemoteException {

		return identityManager.getUserAttributes(uid, toSet(keys));
	}

	@Override
	public Void setUserAttributes(final long uid, final Map<String, String> values) throws GlobalException,
			AvroRemoteException {

		identityManager.setUserAttributes(uid, values);
		return null;
	}

	@Override
	public Void removeUserAttributes(final long uid, final List<String> keys) throws GlobalException, AvroRemoteException {

		identityManager.removeUserAttributes(uid, toSet(keys));
		return null;
	}

	@Override
	public Map<String, String> batchGetUserAttribute(final List<Long> uids, final String key)
			throws GlobalException, AvroRemoteException {

		return toStringKey(identityManager.batchGetUserAttribute(toSet(uids), key));
	}

	@Override
	public Void batchSetUserAttribute(final String key, final Map<String, String> idValues)
			throws GlobalException, AvroRemoteException {

		identityManager.batchSetUserAttribute(key, toLongKey(idValues));
		return null;
	}

	@Override
	public Void batchRemoveUserAttribute(final List<Long> uids, final String key) throws GlobalException,
			AvroRemoteException {

		identityManager.batchRemoveUserAttribute(toSet(uids), key);
		return null;
	}

	@Override
	public Map<String, Map<String, String>> batchGetUserAttributes(final Map<String, List<String>> idKeys)
			throws GlobalException, AvroRemoteException {

		return toStringKey(identityManager.batchGetUserAttributes(toMapSet(idKeys)));
	}

	@Override
	public Void batchSetUserAttributes(final Map<String, Map<String, String>> idValues) throws GlobalException,
			AvroRemoteException {

		identityManager.batchSetUserAttributes(toLongKey(idValues));
		return null;
	}

	@Override
	public Void batchRemoveUserAttributes(final Map<String, List<String>> idKeys) throws GlobalException,
			AvroRemoteException {
		Map<String, Set<String>> ids = new HashMap<String, Set<String>>();
		for(String id : idKeys.keySet()) {
			ids.put(id, toSet(idKeys.get(id)));
		}
		identityManager.batchRemoveUserAttributes(toLongKey(ids));
		return null;
	}

	@Override
	public List<String> getUserAttributeNames(final long uid) throws GlobalException, AvroRemoteException {

		return toList(identityManager.getUserAttributeNames(uid));
	}

	@Override
	public String getSessionAttribute(final String token, final String key) throws GlobalException, AvroRemoteException {

		return identityManager.getSessionAttribute(token, key);
	}

	@Override
	public Map<String, String> getSessionAttributes(final String token, final List<String> keys)
			throws GlobalException, AvroRemoteException {

		return identityManager.getSessionAttributes(token, toSet(keys));
	}

	@Override
	public Void setSessionAttribute(final String token, final String key, final String value)
			throws GlobalException, AvroRemoteException {

		identityManager.setSessionAttribute(token, key, value);
		return null;
	}

	@Override
	public Void setSessionAttributes(final String token, final Map<String, String> values)
			throws GlobalException, AvroRemoteException {

		identityManager.setSessionAttributes(token, values);
		return null;
	}

	@Override
	public Void removeSessionAttribute(final String token, final String key) throws GlobalException, AvroRemoteException {

		identityManager.removeSessionAttribute(token, key);
		return null;
	}

	@Override
	public Void removeSessionAttributes(final String token, final List<String> keys) throws GlobalException,
			AvroRemoteException {

		identityManager.removeSessionAttributes(token, toSet(keys));
		return null;
	}

	@Override
	public List<String> getSessionAttributeNames(final String token) throws GlobalException, AvroRemoteException {

		return toList(identityManager.getSessionAttributeNames(token));
	}

	@Override
	public Void clearSessionAttributes(final String token) throws GlobalException, AvroRemoteException {

		identityManager.clearSessionAttributes(token);
		return null;
	}

	@Override
	public Void clearUserAttributes(final long uid) throws GlobalException, AvroRemoteException {

		identityManager.clearUserAttributes(uid);
		return null;
	}

	@Override
	public boolean isCredentialExists(final Credential credential) throws GlobalException, AvroRemoteException {

		return identityManager.isCredentialExists(toServiceCredential(credential));
	}

	@Override
	public long getCredentialUid(final Credential credential) throws GlobalException, AvroRemoteException {

		return identityManager.getCredentialUid(toServiceCredential(credential));
	}

	@Override
	public boolean validateActivationAndCreateCredential(final Credential credential) throws GlobalException,
			AvroRemoteException {

		return identityManager.validateActivationAndCreateCredential(
				toServiceCredential(credential));
	}

	@Override
	public UserStatus getUserStatus(final long uid) throws GlobalException, AvroRemoteException {

		return toApiUserStatus(identityManager.getUserStatus(uid));
	}

	@Override
	public Void updateCredential(final Credential credential) throws GlobalException, AvroRemoteException {

		identityManager.updateCredential(toServiceCredential(credential));
		return null;

	}

	@Override
	public Credential matchCredential(final Credential credential) throws GlobalException, AvroRemoteException {

		org.bear.identity.model.Credential mapped = identityManager.matchCredential(
				toServiceCredential(credential));
		return mapped == null ? credential : toApiCredential(mapped);

	}

	@Override
	public String createGuestSession() throws GlobalException, AvroRemoteException {

		return identityManager.createGuestSession();

	}

	@Override
	public boolean hasPasswordProtection(long uid) throws GlobalException, AvroRemoteException {
		return this.identityManager.hasPasswordProtection(uid);
	}

	@Override
	public ProtectionQuestion getProtectionQuestion(long uid) throws GlobalException, AvroRemoteException {
		return toApiQuestion(this.identityManager.getProtectionQuestion(uid));
	}

	@Override
	public boolean isNickNameExists(long uid, String nickName) throws GlobalException, AvroRemoteException {
		return this.identityManager.isNickNameExists(uid, nickName);
	}

	@Override
	public User getUserByNick(String nick) throws GlobalException, AvroRemoteException {
		return toApiUser(this.identityManager.getUserByNick(nick));
	}

	@Override
	public long countUser(String nickName, CredentialType type, String name, long startTime, long endTime,
			Map<String, String> attributes) throws GlobalException, AvroRemoteException {
		return this.identityManager.countUser(nickName, toServiceCredentialType(type), name,
				toServiceDate(startTime), toServiceDate(endTime), attributes);
	}

	@Override
	public List<User> listUser(String nickName, CredentialType type, String name, long startTime,
			long endTime, Map<String, String> attributes, long offset, long limit) throws GlobalException,
			AvroRemoteException {
		return Collections.unmodifiableList(toApiUserList(Arrays.asList(this.identityManager
				.listUser(nickName, toServiceCredentialType(type), name,
						toServiceDate(startTime), toServiceDate(endTime), offset,
						limit, attributes))));
	}

	@Override
	public long createGroup(Group group) throws GlobalException, AvroRemoteException {
		return this.identityManager.createGroup(toServiceGroup(group));
	}

	@Override
	public Void updateGroup(Group group) throws GlobalException, AvroRemoteException {
		this.identityManager.updateGroup(toServiceGroup(group));
		return null;
	}

	@Override
	public Void removeGroup(long groupId) throws GlobalException, AvroRemoteException {
		this.identityManager.removeGroup(groupId);
		return null;
	}

	@Override
	public long countGroups(String name) throws GlobalException, AvroRemoteException {
		return this.identityManager.countGroups(name);
	}

	@Override
	public List<Group> listGroups(String name, long offset, long limit) throws GlobalException, AvroRemoteException {
		return toApiGroups(this.identityManager.listGroups(name, offset, limit));
	}

	@Override
	public Void addUserToGroup(long uid, long groupId) throws GlobalException, AvroRemoteException {
		this.identityManager.addUserToGroup(uid, groupId);
		return null;
	}

	@Override
	public Void removeUserFromGroup(long uid, long groupId) throws GlobalException, AvroRemoteException {
		this.identityManager.removeUserFromGroup(uid, groupId);
		return null;
	}

	@Override
	public boolean isUserInGroup(long uid, long groupId) throws GlobalException, AvroRemoteException {
		return this.identityManager.isUserInGroup(uid, groupId);
	}

	@Override
	public long countGroupUsers(long groupId) throws GlobalException, AvroRemoteException {
		return this.identityManager.countGroupUsers(groupId);
	}

	@Override
	public List<User> listGroupUsers(long groupId, long offset, long limit) throws GlobalException, AvroRemoteException {
		return toApiUserList(this.identityManager.listGroupUsers(groupId, offset, limit));
	}

	@Override
	public List<Group> getUserGroups(long uid) throws GlobalException, AvroRemoteException {
		return toApiGroups(this.identityManager.getUserGroups(uid));
	}

	@Override
	public boolean isGroupNameExists(long groupId, String name) throws GlobalException, AvroRemoteException {
		return this.identityManager.isGroupNameExists(groupId, name);
	}

	@Override
	public Group getGroup(long groupId) throws GlobalException, AvroRemoteException {
		return toApiGroup(this.identityManager.getGroup(groupId));
	}

	@Override
	public Group getGroupByName(String name) throws GlobalException, AvroRemoteException {
		return toApiGroup(this.identityManager.getGroupByName(name));
	}

	@Override
	public boolean isAdminUser(long uid) throws GlobalException, AvroRemoteException {
		return this.identityManager.isAdminUser(uid);
	}

	@Override
	public boolean isAdminSession(String token) throws GlobalException, AvroRemoteException {
		return identityManager.isAdminSession(token);
	}

	@Override
	public Void resetAdminUser(long uid) throws GlobalException, AvroRemoteException {
		this.identityManager.resetAdminUser(uid);
		return null;
	}
}
