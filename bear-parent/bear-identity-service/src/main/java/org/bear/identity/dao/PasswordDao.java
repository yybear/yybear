package org.bear.identity.dao;

import org.bear.identity.model.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-28
 */
@Repository
public interface PasswordDao extends JpaRepository<Password, Long>{

}
