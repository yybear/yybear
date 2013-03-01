package org.bear.identity.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bear.identity.dao.UserAttributeDao;
import org.bear.identity.dao.UserDao;
import org.bear.identity.ex.ErrorCode;
import org.bear.identity.ex.UserException;
import org.bear.identity.model.User;
import org.bear.identity.model.UserAttribute;
import org.bear.identity.model.UserAttributePK;
import org.bear.identity.service.UserManager;
import org.bear.identity.type.CredentialType;
import org.bear.identity.type.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedCaseInsensitiveMap;

import com.google.common.collect.Iterables;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-26
 */
@Service
public class UserManagerImpl implements UserManager {

	private static final String USER_ATTRIBUTE_ADMIN = "_ADMIN_";
	private static final String USER_ATTRIBUTE_ADMIN_VALUE = "true";

	@Autowired
	protected UserDao userDao;
	@Autowired
	protected UserAttributeDao userAttributeDao;

	@Override
	public long createUser(User user) throws UserException {
		if (!this.isCreatableUser(user))
			throw new UserException(ErrorCode.IDENTITY_INVALID_REGISTER_USER_INFO);

		if (this.isNickNameExists(0, user.getNickName()))
			throw new UserException(ErrorCode.IDENTITY_NICKNAME_EXISTS);

		return this.userDao.save(user).getId();
	}

	protected boolean isCreatableUser(User user) {
		return user != null && StringUtils.isNotBlank(user.getNickName());
	}

	@Override
	public void updateUserStatus(long uid, UserStatus status) throws UserException {
		User user = userDao.findOne(uid);
		user.setStatus(status);
		this.userDao.save(user);
	}

	@Override
	public User getUser(long uid) throws UserException {
		User user = this.userDao.findOne(uid);
		if (user == null)
			throw new UserException(ErrorCode.IDENTITY_USER_NOT_EXIST, null, new Object[] { uid });

		return user;
	}

	@Override
	public Map<Long, User> batchGetUser(Set<Long> uids) throws UserException {
		if (CollectionUtils.isEmpty(uids))
			return Collections.emptyMap();
		Iterable<User> users = this.userDao.findAll(uids);
		if (Iterables.isEmpty(users))
			return Collections.emptyMap();

		Map<Long, User> result = new HashMap<Long, User>();
		for (User user : users) {
			if (user != null)
				result.put(user.getId(), user);
		}
		return Collections.unmodifiableMap(result);
	}

	protected boolean isUpdatableUser(User user) {
		return this.isCreatableUser(user);
	}

	protected void validateUpdateUser(User user) {
		if (!this.isUpdatableUser(user))
			throw new UserException(ErrorCode.IDENTITY_INVALID_USER_INFO);

		if (this.isNickNameExists(user.getId(), user.getNickName()))
			throw new UserException(ErrorCode.IDENTITY_NICKNAME_EXISTS);
	}

	@Override
	public void updateUser(User user) throws UserException {
		this.validateUpdateUser(user);

		this.userDao.save(user);
	}

	@Override
	public void batchUpdateUser(Set<User> users) throws UserException {
		if (CollectionUtils.isEmpty(users))
			return;

		for (User user : users) {
			this.validateUpdateUser(user);
		}

		this.userDao.save(users);
	}

	@Override
	public String getUserAttribute(long uid, String key) throws UserException {
		UserAttributePK pk = new UserAttributePK(uid, key);
		UserAttribute ua = this.userAttributeDao.findOne(pk);
		if(null == ua)
			return null;
		return ua.getAttrValue();
	}

	@Override
	public void setUserAttribute(long uid, String key, String value) throws UserException {
		UserAttribute ua = new UserAttribute(uid, key, value);
		this.userAttributeDao.save(ua);
	}

	@Override
	public void removeUserAttribute(long uid, String key) throws UserException {
		UserAttributePK pk = new UserAttributePK(uid, key);
		this.userAttributeDao.delete(pk);
	}

	@Override
	public Map<String, String> getUserAttributes(long uid, Set<String> keys) throws UserException {
		List<UserAttribute> list = this.userAttributeDao.getAttributes(uid, keys);
		Map<String, String> map = new HashMap<String, String>(list.size());
		for(UserAttribute ua : list) {
			map.put(ua.getAttrKey(), ua.getAttrValue());
		}
		return map;
	}

	@Override
	public void setUserAttributes(long uid, Map<String, String> values) throws UserException {
		List<UserAttribute> list = new ArrayList<UserAttribute>(values.size());
		for(Entry<String,String> e : values.entrySet()) {
			list.add(new UserAttribute(uid, e.getKey(), e.getValue()));
		}
		this.userAttributeDao.save(list);
	}

	@Override
	public void removeUserAttributes(long uid, Set<String> keys) throws UserException {
		for(String key : keys) {
			userAttributeDao.delete(new UserAttributePK(uid, key));
		}
	}

	@Override
	public Map<Long, String> batchGetUserAttribute(Set<Long> uids, String key) throws UserException {
		int size = uids.size();
		Map<Long, String> map = new HashMap<Long, String>(size);
		for(Long uid : uids) {
			UserAttribute ua = userAttributeDao.findOne(new UserAttributePK(uid, key));
			map.put(ua.getUid(), ua.getAttrValue());
		}
		return map;
	}

	@Override
	public void batchSetUserAttribute(String key, Map<Long, String> idValues) throws UserException {
		int size = idValues.size();
		List<UserAttribute> list = new ArrayList<UserAttribute>(size);
		for(Entry<Long,String> e : idValues.entrySet()) {
			list.add(new UserAttribute(e.getKey(), key, e.getValue()));
		}
		userAttributeDao.save(list);
	}

	@Override
	public void batchRemoveUserAttribute(Set<Long> uids, String key) throws UserException {
		//this.userDao.batchRemoveAttribute(uids, key);
		// TODO
	}

	@Override
	public Map<Long, Map<String, String>> batchGetUserAttributes(Map<Long, Set<String>> idKeys)
			throws UserException {
		// TODO
		//return this.userDao.batchGetAttributes(idKeys);
		return null;
	}

	@Override
	public void batchSetUserAttributes(Map<Long, Map<String, String>> idValues) throws UserException {
		// TODO
		//this.userDao.batchSetAttributes(idValues);
	}

	@Override
	public void batchRemoveUserAttributes(Map<Long, Set<String>> idKeys) throws UserException {
		// TODO
		//this.userDao.batchRemoveAttributes(idKeys);
	}

	@Override
	public Set<String> getUserAttributeNames(long uid) throws UserException {
		// TODO
		//return this.userDao.getAttributeNames(uid);
		return null;
	}

	@Override
	public void clearUserAttributes(long uid) throws UserException {
		this.userAttributeDao.clearAttributes(uid);
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public UserStatus getUserStatus(long uid) throws UserException {
		User user = this.userDao.findOne(uid);
		if (user == null)
			throw new UserException(ErrorCode.IDENTITY_USER_NOT_EXIST, null, new Object[] { uid });

		return user.getStatus();
	}

	@Override
	public boolean isNickNameExists(long uid, String nickName) throws UserException {
		return this.userDao.countSameNickName(uid, nickName) > 0;
	}

	@Override
	public User getUserByNick(String nick) throws UserException {
		User user = this.userDao.getByNickName(nick);

		if (user == null)
			throw new UserException(ErrorCode.IDENTITY_USER_NOT_EXIST, null, new Object[] { nick });

		return user;
	}

	@Override
	public long countUser(String nickName, CredentialType type, String name, Date startTime, Date endTime,
			Map<String, String> attributes) throws UserException {
		//return this.userDao.countUser(nickName, type, name, startTime, endTime, attributes);
		// TODO
		return 0;
	}

	@Override
	public User[] listUser(String nickName, CredentialType type, String name, Date startTime, Date endTime,
			Map<String, String> attributes, long offset, long limit) throws UserException {
		//return this.userDao.listUser(nickName, type, name, startTime, endTime, attributes, offset, limit);
		// TODO
		return null;
	}

	@Override
	public boolean isAdminUser(long uid) throws UserException {
		return uid > 0
				&& USER_ATTRIBUTE_ADMIN_VALUE.equals(getUserAttribute(uid, USER_ATTRIBUTE_ADMIN));
	}

	@Override
	public void resetAdminUser(long uid) throws UserException {
		User user = this.userDao.findOne(uid);
		if (user == null)
			throw new UserException(ErrorCode.IDENTITY_USER_NOT_EXIST, null, new Object[] { uid });

		Map<String, String> match = new HashMap<String, String>();
		match.put(USER_ATTRIBUTE_ADMIN, USER_ATTRIBUTE_ADMIN_VALUE);
		/*long[] admins = this.userDao.match(match);
		if (!ArrayUtils.isEmpty(admins)) {
			Set<Long> ids = new HashSet<Long>(Arrays.asList(ArrayUtils.toObject(admins)));
			this.userDao.batchRemoveAttribute(ids, USER_ATTRIBUTE_ADMIN);
		}

		this.userDao.setAttribute(uid, USER_ATTRIBUTE_ADMIN, USER_ATTRIBUTE_ADMIN_VALUE);*/
	}

}
