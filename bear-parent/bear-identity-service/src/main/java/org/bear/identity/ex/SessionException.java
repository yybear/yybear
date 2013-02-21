package org.bear.identity.ex;

import org.bear.framework.ex.GlobalException;


public class SessionException extends GlobalException {

	private static final long serialVersionUID = -5461197421138791707L;

	public SessionException() {
		super();
	}

	public SessionException(int code, String defaultMessage, Object... args) {
		super(code, defaultMessage, args);
	}
	
	public SessionException(int code, Object... args) {
		super(code, args);
	}

	public SessionException(int code, String defaultMessage, Throwable cause, Object... args) {
		super(code, defaultMessage, cause, args);
	}

	public SessionException(int code) {
		super(code);
	}

	public SessionException(String message, Throwable cause) {
		super(message, cause);
	}

	public SessionException(String message) {
		super(message);
	}

	public SessionException(Throwable cause) {
		super(cause);
	}

}
