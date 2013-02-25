package org.bear.filestore.dao;

import org.bear.filestore.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
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

}
