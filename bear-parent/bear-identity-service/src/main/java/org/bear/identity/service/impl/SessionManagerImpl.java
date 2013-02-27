package org.bear.identity.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bear.identity.ex.SessionException;
import org.bear.identity.model.Session;
import org.bear.identity.service.SessionManager;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-26
 */
public class SessionManagerImpl implements SessionManager {

	@Override
	public String createSession(long uid) throws SessionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createSession() throws SessionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createSession(long uid, long ttl, long tti)
			throws SessionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void destroySession(String token) throws SessionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Session getSession(String token) throws SessionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long validateToken(String token) throws SessionException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getAttribute(String token, String key)
			throws SessionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getAttributes(String token, Set<String> keys)
			throws SessionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getAttributeNames(String token) throws SessionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeAttribute(String token, String key)
			throws SessionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAttributes(String token, Set<String> keys)
			throws SessionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAttribute(String token, String key, String value)
			throws SessionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAttributes(String token, Map<String, String> values)
			throws SessionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearAttributes(String token) throws SessionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearExpiredSessions() throws SessionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isExpired(Session session) throws SessionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAdminSession(String token) throws SessionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Session> activeSessions(int start, int limit, long timeRange)
			throws SessionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countActiveSessions(long timeRange) throws SessionException {
		// TODO Auto-generated method stub
		return 0;
	}

}
