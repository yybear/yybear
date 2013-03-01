package org.bear.identity.dao;

import java.util.List;
import java.util.Set;

import org.bear.identity.model.UserAttribute;
import org.bear.identity.model.UserAttributePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-28
 */
@Repository
public interface UserAttributeDao extends JpaRepository<UserAttribute, UserAttributePK> {
	@Query("select us from UserAttribute us where us.uid=?1 and us.attrKey in (?2)")
	List<UserAttribute> getAttributes(Long pid, Set<String> keys);
	
	@Modifying
	@Query("delete from UserAttribute us where us.uid=?1")
	void clearAttributes(Long uid);
}
