package org.bear.identity.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bear.framework.cache.RedisCache;
import org.bear.framework.cache.redis.RedisCacheManager;
import org.bear.framework.util.MapUtils;
import org.bear.identity.dao.SessionDao;
import org.bear.identity.model.Session;
import org.bear.identity.util.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-26
 */
@Component
public class RedisSessionDaoImpl implements SessionDao{

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DataFieldMaxValueIncrementer idGenerator;

	private RedisCacheManager cacheManager;

	private RedisCache entityCache;

	private RedisCache tokenCache;

	protected RedisCache attributeCache;

	protected String cacheName = "SS";
	
	static final String SESSION_SCORE_SET = "_SSET_";
	
	private boolean jobEnable = true;
	
	private long sessionTTL = 1209600000; // 默认2周
	
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	protected String getCacheKey(long uid) {
		return String.valueOf(uid);
	}
	
	public void setJobEnable(boolean jobEnable) {
		this.jobEnable = jobEnable;
	}

	public void setSessionTTL(long sessionTTL) {
		this.sessionTTL = sessionTTL;
	}

	@Override
	public long create(Session entity) {
		if (entity == null)
			return 0;

		long id = idGenerator.nextLongValue();
		entity.setId(id);

		String key = getCacheKey(id);
		int ttl = ConvertUtils.convertTtlTti(entity.getTtl());
		int tti = ConvertUtils.convertTtlTti(entity.getTti());
		entityCache.put(key, entity, ttl, tti);
		tokenCache.put(entity.getToken(), key, ttl, tti);
		attributeCache.put(key, new HashMap<String, String>(), ttl, tti);
		/*if(entity.getUid() > 0)
			addToSessionCacheSet(id);
		
		this.addToUidCache(entity.getUid(), id);*/
		return entity.getId();
	}
	
/*	protected void addToUidCache(long uid, long sid) {
		String key = this.getCacheKey(uid);
		if (ss == null) {
			ss = new HashSet<Long>();
		} else {
			ss = new HashSet<Long>(ss);
		}
		ss.add(sid);
		uidCache.put(key, ss, -1, uidCacheTti);
	}
*/	
	private void addToSessionCacheSet(long sid) {
		double score = new Date().getTime();
		// 存负时间，因为zset是score越小排在前面
		tokenCache.putZSet(SESSION_SCORE_SET, sid, -score);
	}
	
	private void addToSessionCacheSet(long sid, Date date) {
		double score = date.getTime();
		// 存负时间，因为zset是score越小排在前面
		tokenCache.putZSet(SESSION_SCORE_SET, sid, -score);
	}
	
	private void removeFromSessionCacheSet(long sid) {
		tokenCache.zrem(SESSION_SCORE_SET, sid);
	}

	/*protected void removeFromUidCache(long uid, long sid) {
		String key = this.getCacheKey(uid);
		Set<Long> ss = uidCache.get(key);
		if (ss == null) {
			ss = new HashSet<Long>();
		} else {
			ss = new HashSet<Long>(ss);
		}
		ss.remove(sid);
		if (ss.isEmpty()) {
			uidCache.evict(key);
		} else {
			uidCache.put(key, ss, -1, uidCacheTti);
		}
	}*/

	@Override
	public void update(Session entity) {
		if (entity == null || entity.getId() == null
				|| !this.exists(entity.getId()))
			return;

		Session old = this.get(entity.getId());

		String key = getCacheKey(entity.getId());
		int tti = ConvertUtils.convertTtlTti(entity.getTti());
		int ttl = ConvertUtils.convertTtlTti(entity.getTtl());
		entityCache.put(key, entity, ttl, tti);

		tokenCache.evict(old.getToken());
		tokenCache.put(entity.getToken(), key, ttl, tti);
	}

	@Override
	public long[] batchCreate(Session[] entities) {
		if (ArrayUtils.isEmpty(entities))
			return ArrayUtils.EMPTY_LONG_ARRAY;

		final long[] result = new long[entities.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = this.create(entities[i]);
		}
		return result;
	}

	@Override
	public void batchUpdate(Session[] entities) {
		if (ArrayUtils.isEmpty(entities))
			return;

		for (Session entity : entities) {
			if (entity != null)
				this.update(entity);
		}
	}

	@Override
	public long countAll() {
		return entityCache.getSize();
	}

	@Override
	public long[] getAllIds() {
		Collection<Long> ids = entityCache.getKeys(0, -1);
		return CollectionUtils.isEmpty(ids) ? ArrayUtils.EMPTY_LONG_ARRAY
				: ArrayUtils.toPrimitive(ids.toArray(new Long[0]));
	}

	@Override
	public Session getByToken(String token) {
		String key = tokenCache.get(token);

		return StringUtils.isEmpty(key) ? null : this.get(Long.parseLong(key));
	}

	@Override
	public String getAttribute(long pid, String key) {
		Map<String, String> attrs = this.getAllAttributes(getCacheKey(pid));

		if (CollectionUtils.isEmpty(attrs))
			return StringUtils.EMPTY;

		String value = attrs.get(key);
		return StringUtils.isEmpty(value) ? StringUtils.EMPTY : value;
	}

	protected Map<String, String> getAllAttributes(String pid) {
		Map<String, String> attrs = this.attributeCache.get(pid);
		return CollectionUtils.isEmpty(attrs) ? Collections
				.<String, String> emptyMap() : Collections
				.unmodifiableMap(attrs);
	}

	@Override
	public void setAttribute(long pid, String key, String value) {
		Map<String, String> map = new HashMap<String, String>(
				getAllAttributes(getCacheKey(pid)));
		map.put(key, value);
		this.attributeCache.put(getCacheKey(pid), map);
	}

	@Override
	public void removeAttribute(long pid, String key) {
		Map<String, String> map = new HashMap<String, String>(
				getAllAttributes(getCacheKey(pid)));
		map.remove(key);
		attributeCache.put(getCacheKey(pid), map);
	}

	@Override
	public Map<String, String> getAttributes(long pid, Set<String> keys) {
		if (CollectionUtils.isEmpty(keys))
			return Collections.emptyMap();

		Map<String, String> map = getAllAttributes(getCacheKey(pid));
		if (CollectionUtils.isEmpty(map))
			return Collections.emptyMap();

		Map<String, String> resMap = new HashMap<String, String>();
		for (String key : keys) {
			if (StringUtils.isNotEmpty(key) && map.containsKey(key))
				resMap.put(key, map.get(key));
		}
		return Collections.unmodifiableMap(resMap);
	}

	@Override
	public void setAttributes(long pid, Map<String, String> values) {
		if (!this.exists(pid))
			return;

		if (CollectionUtils.isEmpty(values))
			return;

		Map<String, String> map = new HashMap<String, String>(
				getAllAttributes(getCacheKey(pid)));
		for (Map.Entry<String, String> entry : values.entrySet()) {
			if (StringUtils.isNotBlank(entry.getKey()))
				map.put(entry.getKey(), entry.getValue());
		}
		attributeCache.put(pid, map);
	}

	@Override
	public void removeAttributes(long pid, Set<String> keys) {
		if (!this.exists(pid))
			return;

		if (CollectionUtils.isEmpty(keys))
			return;

		Map<String, String> map = new HashMap<String, String>(
				getAllAttributes(getCacheKey(pid)));
		for (String key : keys) {

			map.remove(key);
		}
		attributeCache.put(pid, map);
	}

	@Override
	public Map<Long, String> batchGetAttribute(Set<Long> pids, String key) {
		if (CollectionUtils.isEmpty(pids))
			return Collections.emptyMap();

		Map<Long, String> map = new HashMap<Long, String>();
		for (Long pid : pids) {
			String value = this.getAttribute(pid, key);
			map.put(pid, StringUtils.isEmpty(value) ? StringUtils.EMPTY : value);
		}
		return Collections.unmodifiableMap(map);
	}

	@Override
	public void batchSetAttribute(String key, Map<Long, String> pidValues) {
		if (CollectionUtils.isEmpty(pidValues))
			return;

		for (Map.Entry<Long, String> entry : pidValues.entrySet()) {
			this.setAttribute(entry.getKey(), key, entry.getValue());
		}
	}

	@Override
	public void batchRemoveAttribute(Set<Long> pids, String key) {
		if (CollectionUtils.isEmpty(pids))
			return;

		for (Long pid : pids) {
			this.removeAttribute(pid, key);
		}
	}

	@Override
	public Map<Long, Map<String, String>> batchGetAttributes(
			Map<Long, Set<String>> pidKeys) {
		if (CollectionUtils.isEmpty(pidKeys))
			return Collections.emptyMap();

		Map<Long, Map<String, String>> map = new HashMap<Long, Map<String, String>>();
		Set<Long> pids = pidKeys.keySet();
		for (Long pid : pids) {
			Map<String, String> keyValues = getAttributes(pid, pidKeys.get(pid));
			if (!keyValues.isEmpty())
				map.put(pid, getAttributes(pid, pidKeys.get(pid)));
		}
		return Collections.unmodifiableMap(map);
	}

	@Override
	public void batchSetAttributes(Map<Long, Map<String, String>> pidValues) {
		if (CollectionUtils.isEmpty(pidValues))
			return;

		Set<Long> pids = pidValues.keySet();
		for (Long pid : pids) {
			this.setAttributes(pid, pidValues.get(pid));
		}
	}

	@Override
	public void batchRemoveAttributes(Map<Long, Set<String>> pidKeys) {
		if (CollectionUtils.isEmpty(pidKeys))
			return;

		Set<Long> pids = pidKeys.keySet();
		for (Long pid : pids) {
			this.removeAttributes(pid, pidKeys.get(pid));
		}

	}

	@Override
	public Set<String> getAttributeNames(long pid) {
		Map<String, String> map = this.getAllAttributes(getCacheKey(pid));
		return CollectionUtils.isEmpty(map) ? Collections.<String> emptySet()
				: map.keySet();
	}

	@Override
	public void clearAttributes(long pid) {
		this.attributeCache.evict(pid);
	}

	@Override
	public void batchClearAttributes(long[] pids) {
		for (long pid : pids) {
			clearAttributes(pid);
		}
	}

	@Override
	public void clearAllAttributes() {
		attributeCache.clear();
	}

	@Override
	public boolean containsAttribute(long pid, String key) {
		return this.getAllAttributes(getCacheKey(pid)).containsKey(key);
	}

	@Override
	public Session get(long id) {
		Session s = this.entityCache.get(id);
		if(s == null) { // session不存在，在activeSessionSet里面清除
			removeFromSessionCacheSet(id);
		}
		return s;
	}

	@Override
	public boolean exists(long id) {
		return entityCache.exists(id);
	}

	@Override
	public void delete(long id) {
		Session s = this.entityCache.get(id);
		entityCache.evict(id);
		attributeCache.evict(id);

		if (s != null) {
			tokenCache.evict(s.getToken());
			//removeFromSessionCacheSet(id);
			//this.removeFromUidCache(s.getUid(), id);
		}
	}

	@Override
	public void deleteAll() {
		entityCache.clear();
		tokenCache.clear();
		//uidCache.clear();
		attributeCache.clear();
	}

	@Override
	public Session[] batchGet(long[] ids) {
		if (null == ids)
			return EMPTY_SESSION_ARRAY;
		Session[] sess = new Session[ids.length];
		for (int i = 0, size = ids.length; i < size; i++) {
			Session s = this.get(ids[i]);
			if(s != null) {
				sess[i] = s;
			} else {
				sess[i] = new Session();
			}
		}
		return sess;
	}

	@Override
	public void batchDelete(long[] ids) {
		if (null == ids)
			return;
		for (int i = 0, size = ids.length; i < size; i++) {
			this.delete(ids[i]);
		}
	}

	@Override
	public Session[] list(long start, long limit) {
		Collection<Object> ids = entityCache.getKeys((int) start, (int) limit);
		List<Session> sess = new ArrayList<Session>();
		for (Object id : ids) {
			sess.add(this.get((Long) id));
		}

		return sess.toArray(new Session[] {});
	}

	@Override
	public void iterate(
			org.bear.framework.dao.EntityDao.Processor<Session> processor,
			long batchSize) {
		long count = this.countAll();
		long start = 0;
		long limit = batchSize <= 0 ? DEFAULT_ITERATOR_BATCH_SIZE : batchSize;
		do {
			Session[] entities = this.list(start, limit);
			if (!ArrayUtils.isEmpty(entities)) {
				for (Session entity : entities) {
					processor.process(entity);
				}
			}
			start += limit;
		} while (start < count);
	}

	@Override
	public void hit(long id) {
		String idKey = getCacheKey(id);
		Session session = this.entityCache.get(idKey);
		if (session == null)
			return;

		Date now = new Date();
		session.setLastActiveTime(now);
		
		long ttlTime = entityCache.getTTLTime(id);
		if(ttlTime > 0) {
			int tti = ConvertUtils.convertTtlTti(session.getTti());
			entityCache.put(idKey, session, (int)ttlTime, tti);
		}
		if(session.getUid() > 0)
			addToSessionCacheSet(session.getId(), now);
	}

	@Override
	public long[] listIds(long start, long limit) {
		Collection<Long> ids = entityCache.getKeys((int) start, (int) limit);
		return CollectionUtils.isEmpty(ids) ? ArrayUtils.EMPTY_LONG_ARRAY
				: ArrayUtils.toPrimitive(ids.toArray(new Long[0]));
	}

	public void setIdGenerator(DataFieldMaxValueIncrementer idGenerator) {
		this.idGenerator = idGenerator;
	}

	public RedisCacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(RedisCacheManager cacheManager) {
		this.cacheManager = cacheManager;
		Assert.notNull(this.cacheManager);
		Assert.notNull(this.cacheName);

		this.entityCache = this.cacheManager.getCache(cacheName);
		this.tokenCache = this.cacheManager.getCache(cacheName + ":T");
		this.attributeCache = this.cacheManager.getCache(cacheName + ":A");
		//this.uidCache = this.cacheManager.getCache(cacheName + ":U");
	}

	@Override
	public long[] match(Map<String, String> map) {
		if (CollectionUtils.isEmpty(map))
			return ArrayUtils.EMPTY_LONG_ARRAY;

		long cnt = this.attributeCache.getSize();
		int start = 0;
		int size = (int) DEFAULT_ITERATOR_BATCH_SIZE;
		Set<Long> ids = new LinkedHashSet<Long>();
		do {
			Map<String, Map<String, String>> attrsMap = this.attributeCache
					.gets(this.attributeCache.getKeys(start, size));
			for (Map.Entry<String, Map<String, String>> entry : attrsMap
					.entrySet()) {
				if (!CollectionUtils.isEmpty(entry.getValue())
						&& MapUtils.containsAll(entry.getValue(), map)) {
					try {
						ids.add(Long.parseLong(entry.getKey()));
					} catch (Exception ex) {
						logger.warn(ex.getMessage(), ex);
					}
				}
			}
			start += size;
		} while (start < cnt);

		return ArrayUtils.toPrimitive(ids.toArray(new Long[0]));
	}

	@Override
	public List<Long> activeSessionIds(int start, int limit, long startTime, long endTime) {
		/*if(startTime != -1 && endTime != -1) {
			return tokenCache.zrangeByScore(SESSION_SCORE_SET, start, limit, 
					-startTime, -endTime);
		} else
			return tokenCache.zrange(SESSION_SCORE_SET, start, limit);*/
		throw new UnsupportedOperationException();
	}

	@Override
	public long countActiveSessionIds(long startTime, long endTime) {
		/*if(startTime != -1 && endTime != -1) {
			return tokenCache.zcount(SESSION_SCORE_SET, 
					-startTime, -endTime);
		} else
			return tokenCache.zcard(SESSION_SCORE_SET);*/
		throw new UnsupportedOperationException();
	}
	
	/**
	 * 定时清理SSET里面过期的session id
	 */
	public void jobClearIdleSessionIdInSSET() {
		/*if(!jobEnable)
			return;
		logger.info("jobClearIdleSessionIdInSSET start");
		long nowTime = new Date().getTime();
		long expiredTime = nowTime - sessionTTL; // 如果最近活动时间早于now-sessionTTL就可以认为过期了
		
		double min = -expiredTime;
		double max = -(expiredTime-172800000); // 定时器每天执行，为了保险减去2天
		long m = tokenCache.zremRangeByScore(SESSION_SCORE_SET, min, max);
		logger.info("remove {} idle sessionId", m);*/
		
		throw new UnsupportedOperationException();
	}

}
