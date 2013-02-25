package org.bear.filestore.fs;

import org.apache.commons.io.IOUtils;
import org.bear.filestore.ex.FileIOException;
import org.bear.filestore.ex.FileNotFoundException;

import java.io.*;
import java.nio.channels.WritableByteChannel;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-22
 */
public class FileSystemVirtualFile implements VirtualFile {
    private String key;
    private File nativeFile;
    private String xsendfilePath;

    public FileSystemVirtualFile(String key, File nativeFile, String xsendfilePath) {
        this.key = key;
        this.nativeFile = nativeFile;
        this.xsendfilePath = xsendfilePath;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public long lastModified() {
        return nativeFile.lastModified();
    }

    @Override
    public int getSize() {
        return (int) nativeFile.length();
    }

    @Override
    public boolean exist() {
        return nativeFile.exists();
    }

    @Override
    public boolean remove() {
        return nativeFile.delete();
    }

    @Override
    public InputStream getInputStream() throws FileIOException {
        try {
            return new FileInputStream(nativeFile);
        } catch (java.io.FileNotFoundException e) {
            throw new FileNotFoundException("raw file for key [" + key + "] not found", e);
        }
    }

    @Override
    public OutputStream getOutputStream() throws FileIOException {
        checkAndCreate();
        try {
            return new FileOutputStream(nativeFile);
        } catch (java.io.FileNotFoundException e) {
            throw new FileNotFoundException("raw file for key [" + key + "] not found", e);
        }
    }

    @Override
    public void write(byte[] bytes, int position, int length) throws FileIOException {
        checkAndCreate();
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(nativeFile, "rw");
            raf.seek(position);
            raf.write(bytes, 0, length);
        } catch (IOException e) {
            throw new FileIOException("write file for key [" + key + "] error", e);
        } finally {
            IOUtils.closeQuietly(raf);
        }
    }

    private void checkAndCreate() {
        if (!nativeFile.exists()) {
            File dir = nativeFile.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            try {
                if (!nativeFile.createNewFile())
                    throw new IOException();
            } catch (IOException e) {
                throw new FileIOException("createFile file " + nativeFile.getAbsolutePath() + "error", e);
            }
        }
    }

    @Override
    public int read(byte[] bytes, int position, int length) throws FileIOException {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(nativeFile, "r");
            return raf.read(bytes, position, length);
        } catch (java.io.FileNotFoundException e) {
            throw new FileNotFoundException("raw file for key [" + key + "] not found", e);
        } catch (IOException e) {
            throw new FileIOException("read file for key [" + key + "] error", e);
        } finally {
            IOUtils.closeQuietly(raf);
        }
    }

    @Override
    public void transferTo(int position, int count, WritableByteChannel target) throws FileIOException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(nativeFile);
            fis.getChannel().transferTo(position, count, target);
        } catch (java.io.FileNotFoundException e) {
            throw new FileNotFoundException("raw file for key [" + key + "] not found", e);
        } catch (IOException e) {
            throw new FileIOException("transfer file for key [" + key + "] error", e);
        } finally {
            IOUtils.closeQuietly(fis);
        }
    }

    @Override
    public File getNativeFile() {
        return nativeFile;
    }

    @Override
    public String getXsendfilePath() {
        return xsendfilePath;
    }

    @Override
    public String toString() {
        return "[" + key + ", " + nativeFile.getAbsolutePath() + "]";
    }
}
