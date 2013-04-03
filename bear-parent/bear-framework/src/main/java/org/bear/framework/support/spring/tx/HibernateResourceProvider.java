package org.bear.framework.support.spring.tx;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.orm.hibernate4.SessionHolder;

/**
 * hibernate资源提供实现.
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-4-2
 */
public class HibernateResourceProvider implements ResourceProvider<SessionHolder> {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Object getKey() {
        return sessionFactory;
    }

    @Override
    public SessionHolder getResource() {
        try {
            Session session = SessionFactoryUtils.openSession(sessionFactory);
            session.setFlushMode(FlushMode.MANUAL);
            return new SessionHolder(session);
        } catch (HibernateException ex) {
            throw new DataAccessResourceFailureException("Could not open Hibernate Session", ex);
        }
    }

    @Override
    public void releaseResource(SessionHolder holder) {
        holder.getSession().flush();
        SessionFactoryUtils.closeSession(holder.getSession());
    }
}
