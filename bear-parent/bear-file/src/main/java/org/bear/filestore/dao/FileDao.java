package org.bear.filestore.dao;

import java.util.List;

import org.bear.filestore.model.File;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-22
 */
@Repository
public interface FileDao extends JpaRepository<File, Long> {
	List<File> findByBizIdAndOwner(Integer bizId, String owner);
	
	@Query("select count(f) from File f where f.bizId=?1 and f.owner=?2 and f.userId=?3")
	Number countUserFiles(Integer bizId, String owner, Long userId);
	
	@Query("select f from File f where f.bizId=?1 and f.owner=?2 and f.userId=?3")
	List<File> listUserFiles(Integer bizId, String owner, Long userId, Pageable pageable);
}
