package org.bear.filestore.ex;

import org.bear.filestore.enums.ErrorCode;
import org.bear.framework.ex.GlobalException;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-22
 */
public class FileStoreException extends GlobalException implements ErrorCode {
    private static final long serialVersionUID = -82865838657820984L;

    public FileStoreException() {
    }

    public FileStoreException(String message) {
        super(message);
    }

    public FileStoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileStoreException(Throwable cause) {
        super(cause);
    }

    public FileStoreException(int code) {
        super(code);
    }

    public FileStoreException(int code, String defaultMessage, Object... args) {
        super(code, defaultMessage, args);
    }

    public FileStoreException(int code, String defaultMessage, Throwable cause, Object... args) {
        super(code, defaultMessage, cause, args);
    }
}
