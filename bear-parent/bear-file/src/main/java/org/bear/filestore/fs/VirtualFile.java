package org.bear.filestore.fs;

import org.bear.filestore.ex.FileIOException;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.WritableByteChannel;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-22
 */
public interface VirtualFile {

    String getKey();

    long lastModified();

    int getSize();

    boolean exist();

    boolean remove();

    InputStream getInputStream() throws FileIOException;

    OutputStream getOutputStream() throws FileIOException;

    void write(byte[] bytes, int position, int length) throws FileIOException;

    int read(byte[] bytes, int position, int length) throws FileIOException;

    void transferTo(int position, int count, WritableByteChannel target) throws FileIOException;

    File getNativeFile();

    String getXsendfilePath();
}
