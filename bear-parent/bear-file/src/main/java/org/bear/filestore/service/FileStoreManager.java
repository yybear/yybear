
package org.bear.filestore.service;

import org.bear.filestore.ex.FileIOException;
import org.bear.filestore.model.File;
import org.bear.filestore.model.ImageAction;
import org.bear.filestore.fs.VirtualFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-22
 */
public interface FileStoreManager {

    File saveFile(File file);

    void removeFile(Long... ids);

    void removeFiles(String bizKey, String owner);

    VirtualFile getVirtualFile(File file);

    int saveFileInputStream(File file, InputStream inputStream) throws FileIOException;

    void writeFile(long id, byte[] bytes, int position) throws FileIOException;

    byte[] readFile(long id, int position, int count) throws FileIOException;

    void transformImage(long id, List<ImageAction> actions);

    /**
     * 获取加密id
     * @param id
     * @param ttl <=0 表示没有时间限制
     * @return
     */
    String getEncryptedId(Long id, int ttl);

    Map<Long, String> getEncryptedIdMap(List<Long> ids, int ttl);

    File getFile(long id);

    Map<Long, File> getFileMap(Set<Long> ids);

    File getFileByEncryptedId(String id);

    Map<Long, File> getFileMapByEncryptedId(Set<String> ids);

    File getSingleFile(String bizKey, String owner);

    List<File> getFiles(String bizKey, String owner);

    Map<String, List<File>> batchGetFiles(String bizKey, Set<String> owners);
    
    int countUserFiles(String bizKey, String owner, long userId);

    List<File> getUserFiles(String bizKey, String owner, long userId, int start, int size);

    boolean hasPermission(String token, Long id, boolean writeable);

    boolean hasPermission(String token, String bizKey, String owner, boolean writeable);
}
