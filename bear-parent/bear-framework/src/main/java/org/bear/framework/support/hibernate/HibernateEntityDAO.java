package org.bear.framework.support.hibernate;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.bear.framework.entity.GeneralEntityDAO;
import org.bear.framework.util.GenericUtils;
import org.bear.framework.util.Page;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * hibernate DAO支持类，不支持Criteria相关操作，只支持实体和hql操作.
 * <p/>
 * 
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-19
 */
public class HibernateEntityDAO<E, PK extends Serializable> implements
		GeneralEntityDAO<E, PK> {
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	private Class<E> entityClass;
	
	public HibernateEntityDAO() {
        this.entityClass = GenericUtils.getGenericParameter0(getClass());
    }

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	protected Session openSession() {
		return sessionFactory.openSession();
	}

	protected void closeSession() {
		getSession().close();
	}

	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	public E save(E entity) {
		return merge(entity);
	}

	@Override
	public void deleteByPk(Serializable id) {
		E entity = get(id);
		if(null != entity)
			getSession().delete(get(id));
	}

	@Override
	public void delete(E entity) {
		getSession().delete(entity);
	}

	@Override
	public void batchDeleteByPK(Collection<PK> ids) {
		if(ids == null)
			return;
		for(PK id : ids) {
			deleteByPk(id);
		}

	}

	@Override
	public void batchDelete(Collection<E> entities) {
		if(entities == null)
			return;
		for(E entity : entities)
			delete(entity);
	}

	@Override
	public E get(Serializable id) {
		return (E) getSession().get(entityClass, id);
	}

	@Override
	public E load(Serializable id) {
		return (E) getSession().load(entityClass, id);
	}

	@Override
	public Map<PK, E> batchGet(Collection<PK> ids) {
		if(null == ids)
			return MapUtils.EMPTY_MAP;
		Map<PK, E> map = new HashMap<PK, E>(ids.size());
		for(PK id : ids) {
			E e = get(id);
			if(null != e)
				map.put(id, e);
		}
		return map;
	}

	@Override
	public List<E> getAll() {
		return null;
	}
	
	protected ClassMetadata getClassMetadata() {
        return getSessionFactory().getClassMetadata(entityClass);
    }
	
	protected PK getId(E entity) {
        return (PK) getClassMetadata().getIdentifier(entity, null);
    }
	
	private static Query addParameters(Query query, Object... args) {
        if (args != null) {
            for (int i = 0, len = args.length; i < len; i++) {
                query.setParameter(i, args[i]);
            }
        }
        return query;
    }

    private static Query addParameters(Query query, Map<String, Object> args) {
        if (args != null) {
            for (Map.Entry<String, Object> entry : args.entrySet()) {
                Object arg = entry.getValue();
                if (arg.getClass().isArray()) {
                    query.setParameterList(entry.getKey(), (Object[]) entry.getValue());
                } else if (arg instanceof Collection) {
                    query.setParameterList(entry.getKey(), ((Collection) arg));
                } else {
                    query.setParameter(entry.getKey(), arg);
                }
            }
        }
        return query;
    }
	
	public E merge(E entity) {
		if(null == entity)
			return null;
		if(getId(entity) == null) {
			getSession().save(entity);
		} else {
			getSession().merge(entity);
		}
		return entity;
	}
	
	/**
	 * hql查询语句，支持序号定位参数.
	 * @param hql
	 * @param args
	 * @return
	 */
	public Query newHqlQuery(String hql, Object... args) {
		Query query = getSession().createQuery(hql);
		addParameters(query, args);
		return query;
	}
	
	/**
	 * hql查询语句，支持占位符参数.
	 * @param hql
	 * @param args
	 * @return
	 */
	public Query newHqlQuery(String hql, Map<String, Object> args) {
        Query query = getSession().createQuery(hql);
        return addParameters(query, args);
    }
	
	/**
	 * sql查询语句，支持序号定位参数.
	 * @param sql
	 * @param args
	 * @return
	 */
	public Query newSqlQuery(String sql, Object... args) {
        Query query = getSession().createSQLQuery(sql).addEntity(entityClass);
        return addParameters(query, args);
    }

	/**
	 * sql查询语句，支持占位符参数.
	 * @param sql
	 * @param args
	 * @return
	 */
	public Query newSqlQuery(String sql, Map<String, Object> args) {
        Query query = getSession().createSQLQuery(sql).addEntity(entityClass);
        return addParameters(query, args);
    }
	
	public List<E> query(Query query, int start, int size) {
        query.setFirstResult(start);
        if (size > 0) {
            query.setMaxResults(size);
        }
        return query.list();
    }
	
	/**
	 * 分页查询
	 * @param query
	 * @param countQuery
	 * @param start 小于0查询全部
	 * @param size  小于等于0查询全部
	 * @return
	 */
	public Page<E> pageQuery(Query query, Query countQuery, int start, int size) {
		int totalCount;
        List items = Collections.EMPTY_LIST;
        if (start < 0 || size <= 0) {
            items = query.list();
            totalCount = items.size();
        } else {
            totalCount = HibernateUtils.getInt(countQuery.uniqueResult());
            if (totalCount > 0) {
                items = query(query, start, size);
            }
        }
        return new Page(items, totalCount, start, size);
	}
	
}
