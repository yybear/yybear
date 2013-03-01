package org.bear.identity.dao;

import java.math.BigInteger;

import org.bear.identity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-26
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {
	@Query("select count(u) from User u where id=?1 and nickName=?2")
	Long countSameNickName(long uid, String nickName);
	
	@Query("select u from User u where nickName=?1")
	User getByNickName(String nickName);
}
