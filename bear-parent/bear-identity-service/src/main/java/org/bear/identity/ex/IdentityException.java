package org.bear.identity.ex;

import org.bear.framework.ex.GlobalException;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-25
 */
public class IdentityException extends GlobalException{
	private static final long serialVersionUID = -5709243447427174475L;

	public IdentityException() {
		super();
	}

	public IdentityException(int code, String defaultMessage, Object... args) {
		super(code, defaultMessage, args);
	}

	public IdentityException(int code, String defaultMessage, Throwable cause, Object... args) {
		super(code, defaultMessage, cause, args);
	}

	public IdentityException(int code) {
		super(code);
	}

	public IdentityException(String message, Throwable cause) {
		super(message, cause);
	}

	public IdentityException(String message) {
		super(message);
	}

	public IdentityException(Throwable cause) {
		super(cause);
	}
}
