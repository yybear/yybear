package org.bear.framework.support.spring.tx;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceException;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.jpa.EntityManagerFactoryAccessor;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;

/**
 * jpa资源提供实现.
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-4-2
 */
public class JpaResourceProvider extends EntityManagerFactoryAccessor implements ResourceProvider<EntityManagerHolder> {
	
	@Override
	public Object getKey() {
		return getEntityManagerFactory();
	}

	@Override
	public EntityManagerHolder getResource() {
		try {
			EntityManager em = createEntityManager();
			return new EntityManagerHolder(em);
		} catch (PersistenceException ex) {
			throw new DataAccessResourceFailureException("Could not create JPA EntityManager", ex);
		}
	}

	@Override
	public void releaseResource(EntityManagerHolder resource) {
		EntityManagerFactoryUtils.closeEntityManager(resource.getEntityManager());
	}

}
