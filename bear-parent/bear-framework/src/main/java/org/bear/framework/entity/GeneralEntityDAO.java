package org.bear.framework.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-19
 */
public interface GeneralEntityDAO<E, PK extends Serializable> {
	E save(E entity);

    void deleteByPk(PK id);

    void delete(E entity);

    void batchDeleteByPK(Collection<PK> ids);

    void batchDelete(Collection<E> entities);

    E get(PK id);

    E load(PK id);

    Map<PK, E> batchGet(Collection<PK> ids);

    List<E> getAll();
}
