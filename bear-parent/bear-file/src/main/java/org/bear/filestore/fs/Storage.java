package org.bear.filestore.fs;

/**
 * 文件存储接口.
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-22
 */
public interface Storage extends Comparable<Storage> {

    void init();

    void destroy();

    /**
     * 当前存储总容量，-1表示不限容量
     * @return
     */
    long getCapability();

    /**
     * 已使用容量
     * @return
     */
    long getUsed();

    boolean isAvailable();

    /**
     * 是否包含某个文件
     * @param key
     * @return
     */
    boolean contains(String key);

    /**
     * 删除某个文件
     * @param key
     * @return
     */
    boolean remove(String key);

    /**
     * 获取文件的虚拟文件
     * @param key
     * @return
     */
    VirtualFile getVirtualFile(String key);
}
