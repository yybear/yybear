package org.bear.commonservice.dao;

import org.bear.commonservice.model.Biz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-27
 */
@Repository
public interface BizDao extends JpaRepository<Biz, Integer>{
	
}
