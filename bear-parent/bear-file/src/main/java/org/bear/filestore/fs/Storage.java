package org.bear.filestore.fs;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-22
 */
public interface Storage extends Comparable<Storage> {

    void init();

    void destroy();

    String getId();

    long getCapability();

    long getUsed();

    boolean isAvailable();

    boolean contains(String key);

    boolean remove(String key);

    VirtualFile getVirtualFile(String key);
}
