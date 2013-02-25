package org.bear.filestore.ex;

import java.io.IOException;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-22
 */
public class FileIOException extends FileStoreException {

    private static final long serialVersionUID = 927858202290123709L;

    public FileIOException(String message) {
        super(IO_ERROR, message);
    }

    public FileIOException(String message, IOException ex) {
        super(IO_ERROR, message, ex);
    }
}
