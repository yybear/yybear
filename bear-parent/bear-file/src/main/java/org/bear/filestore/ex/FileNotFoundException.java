package org.bear.filestore.ex;

import java.io.IOException;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-22
 */
public class FileNotFoundException extends FileStoreException {

    private static final long serialVersionUID = 927858202290123709L;

    public FileNotFoundException(String message) {
        super(FILE_NOT_FOUND, message);
    }

    public FileNotFoundException(String message, IOException ex) {
        super(FILE_NOT_FOUND, message, ex);
    }
}
