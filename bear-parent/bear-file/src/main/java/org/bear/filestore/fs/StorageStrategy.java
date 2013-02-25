package org.bear.filestore.fs;


import org.bear.filestore.model.File;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-22
 */
public interface StorageStrategy {
    Storage select(File file);
}
