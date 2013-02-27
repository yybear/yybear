package org.bear.identity.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.bear.identity.ex.UserException;
import org.bear.identity.model.User;
import org.bear.identity.service.UserManager;
import org.bear.identity.type.CredentialType;
import org.bear.identity.type.UserStatus;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-26
 */
public class UserManagerImpl implements UserManager {

	@Override
	public long createUser(User user) throws UserException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateUserStatus(long uid, UserStatus status)
			throws UserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserStatus getUserStatus(long uid) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(long uid) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Long, User> batchGetUser(Set<Long> uids) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUser(User user) throws UserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void batchUpdateUser(Set<User> users) throws UserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getUserAttribute(long uid, String key) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUserAttribute(long uid, String key, String value)
			throws UserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUserAttribute(long uid, String key) throws UserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, String> getUserAttributes(long uid, Set<String> keys)
			throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUserAttributes(long uid, Map<String, String> values)
			throws UserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUserAttributes(long uid, Set<String> keys)
			throws UserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<Long, String> batchGetUserAttribute(Set<Long> uids, String key)
			throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void batchSetUserAttribute(String key, Map<Long, String> idValues)
			throws UserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void batchRemoveUserAttribute(Set<Long> uids, String key)
			throws UserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<Long, Map<String, String>> batchGetUserAttributes(
			Map<Long, Set<String>> idKeys) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void batchSetUserAttributes(Map<Long, Map<String, String>> idValues)
			throws UserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void batchRemoveUserAttributes(Map<Long, Set<String>> idKeys)
			throws UserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<String> getUserAttributeNames(long uid) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearUserAttributes(long uid) throws UserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isNickNameExists(long uid, String nickName)
			throws UserException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User getUserByNick(String nick) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countUser(String nickName, CredentialType type, String name,
			Date startTime, Date endTime, Map<String, String> attributes)
			throws UserException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public User[] listUser(String nickName, CredentialType type, String name,
			Date startTime, Date endTime, Map<String, String> attributes,
			long offset, long limit) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAdminUser(long uid) throws UserException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resetAdminUser(long uid) throws UserException {
		// TODO Auto-generated method stub
		
	}

}
