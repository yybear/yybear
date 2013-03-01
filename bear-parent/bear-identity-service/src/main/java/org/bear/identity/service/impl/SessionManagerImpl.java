package org.bear.identity.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bear.identity.dao.SessionDao;
import org.bear.identity.ex.ErrorCode;
import org.bear.identity.ex.SessionException;
import org.bear.identity.model.Session;
import org.bear.identity.service.SessionManager;
import org.bear.identity.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-26
 */
@Service
public class SessionManagerImpl implements SessionManager {
	private static final long DEFAULT_SESSION_TTI = 1000 * 60 * 30;
	
	private static final long NEGATIVE_ONE = -1l;
	private long tti = DEFAULT_SESSION_TTI;
	
	public void setTti(long tti) {
		this.tti = tti;
	}

	@Autowired
	private SessionDao sessionDao;
	@Autowired
	private UserManager userManager;

	@Override
	public String createSession(long uid) throws SessionException {
		return createSession(uid, NEGATIVE_ONE, NEGATIVE_ONE);
	}

	@Override
	public String createSession() throws SessionException {
		return null;
	}

	@Override
	public String createSession(long uid, long ttl, long tti)
			throws SessionException {
		Session session = new Session();
		session.setUid(uid);
		session.setLastActiveTime(new Date());
		if(ttl>0) {
			session.setTtl(ttl);
			session.setTti(NEGATIVE_ONE); 
		} else {
			session.setTtl(NEGATIVE_ONE);
			session.setTti(tti > 0 ? tti : this.tti);
		}
		session.setCreationTime(session.getLastActiveTime());
		
		session.setToken(createToken());
		
		sessionDao.create(session);
		return session.getToken();
	}

	protected String createToken() {
		return DigestUtils.md5DigestAsHex(UUID.randomUUID().toString()
				.getBytes());
	}
	
	@Override
	public void destroySession(String token) throws SessionException {
		Session session = sessionDao.getByToken(token);
		if(null == session)
			return;
		sessionDao.delete(session.getId());
	}

	@Override
	public Session getSession(String token) throws SessionException {
		return sessionDao.getByToken(token);
	}

	@Override
	public long validateToken(String token) throws SessionException {
		Session session = this.getSession(token);
		if (session == null)
			return VALIDATE_TOKEN_INVALID;
		
		this.sessionDao.hit(session.getId());

		return session.getUid();
	}

	@Override
	public String getAttribute(String token, String key)
			throws SessionException {
		Session session = this.getSession(token);
		if (session == null)
			throw new SessionException(ErrorCode.SESSION_INVALID_TOKEN);

		return this.sessionDao.getAttribute(session.getId(), key);
	}

	@Override
	public Map<String, String> getAttributes(String token, Set<String> keys)
			throws SessionException {
		Session session = this.getSession(token);
		if (session == null)
			throw new SessionException(ErrorCode.SESSION_INVALID_TOKEN);

		return this.sessionDao.getAttributes(session.getId(), keys);
		
	}

	@Override
	public Set<String> getAttributeNames(String token) throws SessionException {
		Session session = this.getSession(token);
		if (session == null)
			throw new SessionException(ErrorCode.SESSION_INVALID_TOKEN);

		return this.sessionDao.getAttributeNames(session.getId());
	}

	@Override
	public void removeAttribute(String token, String key)
			throws SessionException {
		Session session = this.getSession(token);
		if (session == null)
			return;
		sessionDao.removeAttribute(session.getId(), key);
		
	}

	@Override
	public void removeAttributes(String token, Set<String> keys)
			throws SessionException {
		Session session = this.getSession(token);
		if (session == null)
			return;
		sessionDao.removeAttributes(session.getId(), keys);
	}

	@Override
	public void setAttribute(String token, String key, String value)
			throws SessionException {
		Session session = this.getSession(token);
		if (session == null)
			throw new SessionException(ErrorCode.SESSION_INVALID_TOKEN);
		sessionDao.setAttribute(session.getId(), key, value);
	}

	@Override
	public void setAttributes(String token, Map<String, String> values)
			throws SessionException {
		Session session = this.getSession(token);
		if (session == null)
			throw new SessionException(ErrorCode.SESSION_INVALID_TOKEN);
		sessionDao.setAttributes(session.getId(), values);
	}

	@Override
	public void clearAttributes(String token) throws SessionException {
		Session session = this.getSession(token);
		if (session == null)
			return;
		sessionDao.clearAttributes(session.getId());
	}

	@Override
	public void clearExpiredSessions() throws SessionException {
		// 缓存服务自身负责清理，这里不做实现
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isExpired(Session session) throws SessionException {
		// 缓存服务自身负责清理，这里不做实现
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isAdminSession(String token) throws SessionException {
		return this.userManager.isAdminUser(this.validateToken(token));
	}

	@Override
	public List<Session> activeSessions(int start, int limit, long timeRange)
			throws SessionException {
		// TODO: 暂时不提供
		throw new UnsupportedOperationException();
	}

	@Override
	public long countActiveSessions(long timeRange) throws SessionException {
		// TODO: 暂时不提供
		throw new UnsupportedOperationException();
	}

}
