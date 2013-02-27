package org.bear.identity.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bear.api.identity.Session;
import org.bear.global.type.Gender;
import org.springframework.util.CollectionUtils;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-20
 */
public class ConvertUtils {
	public static org.bear.api.identity.PasswordProtection toApiPasswordProtection(
			org.bear.identity.model.PasswordProtection protection) {
		if (protection == null)
			return null;

		org.bear.api.identity.PasswordProtection result = new org.bear.api.identity.PasswordProtection();
		result.setUid(protection.getUid() == null ? -1 : protection.getUid().longValue());
		result.setQuestion(toApiQuestion(protection.getQuestion()));
		result.setAnswer(protection.getAnswer());
		return result;
	}

	public static org.bear.identity.model.PasswordProtection toServicePasswordProtection(
			org.bear.api.identity.PasswordProtection protection) {
		if (protection == null)
			return null;

		org.bear.identity.model.PasswordProtection result = new org.bear.identity.model.PasswordProtection();
		result.setUid(protection.getUid() < 0 ? null : protection.getUid());
		result.setQuestion(toServiceQuestion(protection.getQuestion()));
		result.setAnswer(protection.getAnswer());
		return result;
	}

	public static org.bear.identity.type.ProtectionQuestion toServiceQuestion(
			org.bear.api.identity.ProtectionQuestion question) {
		return question == null ? null : org.bear.identity.type.ProtectionQuestion.values()[question
				.ordinal()];
	}

	public static org.bear.api.identity.ProtectionQuestion toApiQuestion(
			org.bear.identity.type.ProtectionQuestion question) {
		return question == null ? null : org.bear.api.identity.ProtectionQuestion.values()[question
				.ordinal()];
	}

	public static org.bear.identity.model.User toServiceUser(org.bear.api.identity.User user) {
		if (user == null)
			return null;

		org.bear.identity.model.User result = new org.bear.identity.model.User();

		result.setId(user.getId() < 0 ? null : user.getId());
		result.setUserName(user.getUserName());
		result.setNickName(user.getNickName());
		result.setRealName(user.getRealName());
		result.setIdNo(user.getIdNo());
		result.setLocation(user.getLocation());
		if(null == user.getGender()) 
			result.setGender(Gender.UNKNOWN);
		else 
			result.setGender(Gender.values()[user.getGender().ordinal()]);
		result.setBirthday(toServiceDate(user.getBirthday()));
		result.setDescription(user.getDescription());
		result.setPhoto(user.getPhoto());
		result.setStatus(toServiceUserStatus(user.getStatus()));

		result.setCreateTime(toServiceDate(user.getCreateTime()));
		result.setUpdateTime(toServiceDate(user.getUpdateTime()));

		return result;
	}
	
	public static Set<org.bear.identity.model.User> toServiceUserSet(
			List<org.bear.api.identity.User> users) {
		if (CollectionUtils.isEmpty(users))
			return Collections.emptySet();

		Set<org.bear.identity.model.User> result = new LinkedHashSet<org.bear.identity.model.User>(
				users.size());
		for (org.bear.api.identity.User identity : users) {
			result.add(toServiceUser(identity));
		}
		return result;
	}

	public static org.bear.api.identity.User toApiUser(org.bear.identity.model.User user) {
		if (user == null)
			return null;

		org.bear.api.identity.User result = new org.bear.api.identity.User();

		result.setId(user.getId() == null ? -1 : user.getId().longValue());
		result.setUserName(user.getUserName());
		result.setNickName(user.getNickName());
		result.setRealName(user.getRealName());
		result.setIdNo(user.getIdNo());
		result.setLocation(user.getLocation());
		result.setGender(org.bear.api.type.Gender.values()[user.getGender().ordinal()]);
		result.setBirthday(toApiDate(user.getBirthday()));
		result.setDescription(user.getDescription());
		result.setPhoto(user.getPhoto());
		result.setStatus(toApiUserStatus(user.getStatus()));

		result.setCreateTime(toApiDate(user.getCreateTime()));
		result.setUpdateTime(toApiDate(user.getUpdateTime()));

		return result;
	}

	public static List<org.bear.api.identity.User> toApiUserList(
			List<org.bear.identity.model.User> users) {
		if (CollectionUtils.isEmpty(users))
			return Collections.emptyList();

		List<org.bear.api.identity.User> result = new ArrayList<org.bear.api.identity.User>(
				users.size());
		for (org.bear.identity.model.User identity : users) {
			result.add(toApiUser(identity));
		}
		return result;
	}

	public static Map<String, org.bear.api.identity.User> toApiUserMap(
			Map<Long, org.bear.identity.model.User> userMap) {
		if (CollectionUtils.isEmpty(userMap))
			return Collections.emptyMap();

		Map<String, org.bear.api.identity.User> result = new HashMap<String, org.bear.api.identity.User>(
				userMap.size());
		for (Map.Entry<Long, org.bear.identity.model.User> entry : userMap.entrySet()) {
			result.put(createMapKey(entry.getKey()), toApiUser(entry.getValue()));
		}
		return result;
	}
	
	private static String createMapKey(Long key) {
		return String.valueOf(key);
	}

	public static org.bear.identity.type.UserStatus toServiceUserStatus(
			org.bear.api.identity.UserStatus userStatus) {
		return userStatus == null ? null : org.bear.identity.type.UserStatus.values()[userStatus
				.ordinal()];
	}

	public static org.bear.api.identity.UserStatus toApiUserStatus(
			org.bear.identity.type.UserStatus userStatus) {
		return userStatus == null ? null : org.bear.api.identity.UserStatus.values()[userStatus
				.ordinal()];
	}

	public static org.bear.identity.model.Credential toServiceCredential(
			org.bear.api.identity.Credential credential) {
		if (credential == null)
			return null;

		org.bear.identity.model.Credential result = new org.bear.identity.model.Credential();
		result.setId(credential.getId() < 0 ? null : credential.getId());
		result.setUid(credential.getUid() < 0 ? null : credential.getUid());
		result.setType(toServiceCredentialType(credential.getType()));
		result.setName(credential.getName());
		result.setValue(credential.getValue());
		return result;
	}

	public static org.bear.api.identity.Credential toApiCredential(
			org.bear.identity.model.Credential credential) {
		if (credential == null)
			return null;

		org.bear.api.identity.Credential result = new org.bear.api.identity.Credential();
		result.setId(credential.getId() == null ? -1 : credential.getId().longValue());
		result.setUid(credential.getUid() == null ? -1 : credential.getUid().longValue());
		result.setType(toApiCredentialType(credential.getType()));
		result.setName(credential.getName());
		result.setValue(credential.getValue());
		return result;
	}

	public static List<org.bear.identity.model.Credential> toServiceCredential(
			List<org.bear.api.identity.Credential> credentials) {
		if (CollectionUtils.isEmpty(credentials))
			return Collections.emptyList();

		List<org.bear.identity.model.Credential> result = new ArrayList<org.bear.identity.model.Credential>(
				credentials.size());
		for (org.bear.api.identity.Credential credential : credentials) {
			result.add(toServiceCredential(credential));
		}
		return result;
	}

	public static List<org.bear.api.identity.Credential> toApiCredential(
			List<org.bear.identity.model.Credential> credentials) {
		if (CollectionUtils.isEmpty(credentials))
			return Collections.emptyList();

		List<org.bear.api.identity.Credential> result = new ArrayList<org.bear.api.identity.Credential>(
				credentials.size());
		for (org.bear.identity.model.Credential credential : credentials) {
			result.add(toApiCredential(credential));
		}
		return result;
	}

	public static org.bear.identity.type.CredentialType toServiceCredentialType(
			org.bear.api.identity.CredentialType credential) {
		return credential == null ? null : org.bear.identity.type.CredentialType.values()[credential
				.ordinal()];
	}

	public static org.bear.api.identity.CredentialType toApiCredentialType(
			org.bear.identity.type.CredentialType ct) {
		return ct == null ? null : org.bear.api.identity.CredentialType.values()[ct.ordinal()];
	}

	public static Date toServiceDate(long date) {
		return new Date(date);
	}

	public static long toApiDate(Date date) {
		return date.getTime();
	}

	public static org.bear.api.identity.Group toApiGroup(org.bear.identity.model.Group group) {
		if (group == null)
			return null;

		org.bear.api.identity.Group result = new org.bear.api.identity.Group();
		result.setId(group.getId() == null ? 0L : group.getId().longValue());
		result.setName(group.getName());
		result.setDescription(group.getDescription());
		return result;
	}

	public static org.bear.identity.model.Group toServiceGroup(org.bear.api.identity.Group group) {
		if (group == null)
			return null;

		org.bear.identity.model.Group result = new org.bear.identity.model.Group();
		result.setId(group.getId() <= 0 ? null : group.getId());
		result.setName(group.getName());
		result.setDescription(group.getDescription());
		return result;
	}

	public static List<org.bear.api.identity.Group> toApiGroups(
			List<org.bear.identity.model.Group> groups) {
		if (CollectionUtils.isEmpty(groups))
			return Collections.emptyList();

		List<org.bear.api.identity.Group> result = new ArrayList<org.bear.api.identity.Group>();
		for (org.bear.identity.model.Group group : groups) {
			result.add(toApiGroup(group));
		}
		return result;
	}

	public static List<org.bear.identity.model.Group> toServiceGroups(
			List<org.bear.api.identity.Group> groups) {
		if (CollectionUtils.isEmpty(groups))
			return Collections.emptyList();

		List<org.bear.identity.model.Group> result = new ArrayList<org.bear.identity.model.Group>();
		for (org.bear.api.identity.Group group : groups) {
			result.add(toServiceGroup(group));
		}
		return result;
	}
	
	public static int convertTtlTti(Long num) {
		int res = -1;
		if(null != num && num > 0) { // redis缓存单位是秒，本地时间单位是毫秒
			res = (int)(num/1000);
		}
		
		return res;
	}
	public static Session toApiSession(org.bear.identity.model.Session in) {
		Session out = new Session();
		out.setClientIp(in.getClientIp());
		out.setCreationTime(in.getCreationTime() == null?0:in.getCreationTime().getTime());
		out.setId(in.getId());
		out.setLastActiveTime(in.getLastActiveTime() == null?0:in.getLastActiveTime().getTime());
		out.setToken(in.getToken());
		out.setUid(in.getUid());
		out.setTti(in.getTti());
		out.setTtl(in.getTtl());
		
		return out;
	}

	public static List<Session> toApiSessionList(List<org.bear.identity.model.Session> inList) {
		List<Session> outList = new ArrayList<Session>(inList.size());
		for(org.bear.identity.model.Session in:inList) {
			outList.add(toApiSession(in));
		}
		
		return outList;
	}
	
	public static Map<Long, Set<String>> toMapSet(Map<String, List<String>> in) {
		if(null == in)
			return null;
		
		Map<Long, Set<String>> out = new HashMap<Long, Set<String>>();
		for(String key : in.keySet()) {
			Set<String> set = new HashSet<String>();
			set.addAll(in.get(key));
			out.put(Long.valueOf(key), set);
		}
		return out;
	}
}
