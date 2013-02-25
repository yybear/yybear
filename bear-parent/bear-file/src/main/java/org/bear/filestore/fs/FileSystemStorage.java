package org.bear.filestore.fs;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-22
 */
public class FileSystemStorage extends AbstractStorage {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractStorageStrategy.class);
    private File directory;
    private boolean useXsendfile;

    public void setDirectory(File directory) {
        this.directory = directory;
    }

    public void setUseXsendfile(boolean useXsendfile) {
        this.useXsendfile = useXsendfile;
    }

    @Override
    public synchronized void init() {
        if (directory.exists()) {
            setEnable(true);
            LOG.info("Use storage [" + this + "]");
        } else {
            setEnable(false);
            LOG.error("FileSystemStorage path [" + directory.getAbsolutePath() + "] is invalid");
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public boolean contains(String key) {
        return getRawFile(key).exists();
    }

    @Override
    public boolean remove(String key) {
        return getRawFile(key).delete();
    }

    @Override
    public VirtualFile getVirtualFile(String key) {
        String path = getPath(key);
        String xpath = null;
        if (useXsendfile) {
            StringBuilder sb = new StringBuilder("/");
            sb.append(getId()).append("/").append(path);
            xpath = sb.toString();
        }
        return new FileSystemVirtualFile(key, new File(directory, path), xpath);
    }

    private File getRawFile(String key) {
        return new File(directory, getPath(key));
    }

    private String getPath(String key) {
        String hash = DigestUtils.md5Hex(key);
        StringBuilder path = new StringBuilder();
        path.append(StringUtils.substring(hash, 0, 2));
        path.append(File.separator);
        path.append(StringUtils.substring(hash, 2, 4));
        path.append(File.separator);
        path.append(hash);
        return path.toString();
    }
}
