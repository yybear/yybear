package org.bear.identity.dao.impl;

import java.util.Map;
import java.util.Set;

import org.bear.identity.dao.SessionDao;
import org.bear.identity.model.Session;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-26
 */
public class RedisSessionDaoImpl implements SessionDao{

	@Override
	public void hit(long id) {
		
	}

	@Override
	public Session getByToken(String token) {
		return null;
	}

	@Override
	public Session[] listByUid(long uid) {
		return null;
	}

	@Override
	public String getAttribute(long pid, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAttribute(long pid, String key, String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAttribute(long pid, String key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, String> getAttributes(long pid, Set<String> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAttributes(long pid, Map<String, String> values) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAttributes(long pid, Set<String> keys) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<Long, String> batchGetAttribute(Set<Long> pids, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void batchSetAttribute(String key, Map<Long, String> pidValues) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void batchRemoveAttribute(Set<Long> pids, String key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<Long, Map<String, String>> batchGetAttributes(
			Map<Long, Set<String>> pidKeys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void batchSetAttributes(Map<Long, Map<String, String>> pidValues) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void batchRemoveAttributes(Map<Long, Set<String>> pidKeys) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<String> getAttributeNames(long pid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearAttributes(long pid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void batchClearAttributes(long[] pids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearAllAttributes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean containsAttribute(long pid, String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long[] match(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long create(Session entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Session get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update(Session entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long[] batchCreate(Session[] entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Session[] batchGet(long[] ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void batchUpdate(Session[] entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void batchDelete(long[] ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long countAll() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Session[] list(long start, long limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long[] listIds(long start, long limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long[] getAllIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void iterate(
			org.bear.framework.dao.EntityDao.Processor<Session> processor,
			long batchSize) {
		// TODO Auto-generated method stub
		
	}

}
