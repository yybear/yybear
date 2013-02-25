package org.bear.identity.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.avro.AvroRemoteException;
import org.bear.api.identity.Credential;
import org.bear.api.identity.CredentialType;
import org.bear.api.identity.Group;
import org.bear.api.identity.IdentityService;
import org.bear.api.identity.PasswordProtection;
import org.bear.api.identity.ProtectionQuestion;
import org.bear.api.identity.User;
import org.bear.api.identity.UserStatus;
import org.bear.api.type.GlobalException;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-20
 */
public class AvroIdentityServiceImpl implements IdentityService {

	@Override
	public String registerUser(User user, Credential credential,
			Map<String, String> userAttributes) throws AvroRemoteException,
			GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String login(Credential credential, long ttl)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void logout(String token) throws AvroRemoteException,
			GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long validateToken(String token) throws AvroRemoteException,
			GlobalException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public User validateTokenWithUser(String token) throws AvroRemoteException,
			GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long requestActivation(Credential credential, long ttl)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean validateActivation(Credential credential)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validateActivationAndCreateCredential(Credential credential)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Void changePassword(long uid, String oldPwd, String newPwd)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void resetPassword(long uid, String newPwd)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void protectPassword(PasswordProtection protection, String pwd)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validatePasswordProtection(PasswordProtection protection)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasPasswordProtection(long uid) throws AvroRemoteException,
			GlobalException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ProtectionQuestion getProtectionQuestion(long uid)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void createCredential(Credential credential)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void removeCredential(Credential credential)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void updateCredential(Credential credential)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Credential matchCredential(Credential credential)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Credential> getUserCredentials(long uid)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCredentialExists(Credential credential)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getCredentialUid(Credential credential)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Void updateUserStatus(long uid, UserStatus status)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserStatus getUserStatus(long uid) throws AvroRemoteException,
			GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(long uid) throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserByNick(String nick) throws AvroRemoteException,
			GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, User> batchGetUser(List<Long> uids)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void updateUser(User user) throws AvroRemoteException,
			GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void batchUpdateUser(List<User> users) throws AvroRemoteException,
			GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserAttribute(long uid, String key)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void setUserAttribute(long uid, String key, String value)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void removeUserAttribute(long uid, String key)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getUserAttributes(long uid, List<String> keys)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void setUserAttributes(long uid, Map<String, String> values)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void removeUserAttributes(long uid, List<String> keys)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> batchGetUserAttribute(List<Long> uids, String key)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void batchSetUserAttribute(String key, Map<String, String> idValues)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void batchRemoveUserAttribute(List<Long> uids, String key)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Map<String, String>> batchGetUserAttributes(
			Map<String, List<String>> idKeys) throws AvroRemoteException,
			GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void batchSetUserAttributes(Map<String, Map<String, String>> idValues)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void batchRemoveUserAttributes(Map<String, List<String>> idKeys)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getUserAttributeNames(long uid)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void clearUserAttributes(long uid) throws AvroRemoteException,
			GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSessionAttribute(String token, String key)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getSessionAttributes(String token,
			List<String> keys) throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void setSessionAttribute(String token, String key, String value)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void setSessionAttributes(String token, Map<String, String> values)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void removeSessionAttribute(String token, String key)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void removeSessionAttributes(String token, List<String> keys)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getSessionAttributeNames(String token)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void clearSessionAttributes(String token)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createGuestSession() throws AvroRemoteException,
			GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isNickNameExists(long uid, String nickName)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long countUser(String nickName, CredentialType type, String name,
			long startTime, long endTime, Map<String, String> attributes)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<User> listUser(String nickName, CredentialType type,
			String name, long startTime, long endTime,
			Map<String, String> attributes, long offset, long limit)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long createGroup(Group group) throws AvroRemoteException,
			GlobalException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Void updateGroup(Group group) throws AvroRemoteException,
			GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void removeGroup(long groupId) throws AvroRemoteException,
			GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isGroupNameExists(long groupId, String name)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Group getGroup(long groupId) throws AvroRemoteException,
			GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Group getGroupByName(String name) throws AvroRemoteException,
			GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countGroups(String name) throws AvroRemoteException,
			GlobalException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Group> listGroups(String name, long offset, long limit)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void addUserToGroup(long uid, long groupId)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void removeUserFromGroup(long uid, long groupId)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUserInGroup(long uid, long groupId)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long countGroupUsers(long groupId) throws AvroRemoteException,
			GlobalException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<User> listGroupUsers(long groupId, long offset, long limit)
			throws AvroRemoteException, GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Group> getUserGroups(long uid) throws AvroRemoteException,
			GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAdminUser(long uid) throws AvroRemoteException,
			GlobalException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAdminSession(String token) throws AvroRemoteException,
			GlobalException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Void resetAdminUser(long uid) throws AvroRemoteException,
			GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

}
