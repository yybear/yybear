package org.bear.identity.ex;

import org.bear.framework.ex.GlobalException;


public class UserException extends GlobalException {

	private static final long serialVersionUID = -6209815415525744755L;

	public UserException() {
		super();
	}

	public UserException(int code, String defaultMessage, Object... args) {
		super(code, defaultMessage, args);
	}
	
	public UserException(int code, Object... args) {
		super(code, args);
	}

	public UserException(int code, String defaultMessage, Throwable cause,
			Object... args) {
		super(code, defaultMessage, cause, args);
	}

	public UserException(int code) {
		super(code);
	}

	public UserException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserException(String message) {
		super(message);
	}

	public UserException(Throwable cause) {
		super(cause);
	}

}
