package org.bear.identity.dao;

import org.bear.identity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-26
 */
public interface UserDao extends JpaRepository<User, Long>{

}
