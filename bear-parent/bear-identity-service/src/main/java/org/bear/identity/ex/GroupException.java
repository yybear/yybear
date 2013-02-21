package org.bear.identity.ex;

import org.bear.framework.ex.GlobalException;


/**
 * 用户组异常
 * 
 * @author Sun
 * 
 */
public class GroupException extends GlobalException {

	private static final long serialVersionUID = -8476032169767056417L;

	public GroupException() {
		super();
	}

	public GroupException(int code, String defaultMessage, Object... args) {
		super(code, defaultMessage, args);
	}
	
	public GroupException(int code, Object... args) {
		super(code, args);
	}

	public GroupException(int code, String defaultMessage, Throwable cause, Object... args) {
		super(code, defaultMessage, cause, args);
	}

	public GroupException(int code) {
		super(code);
	}

	public GroupException(String message, Throwable cause) {
		super(message, cause);
	}

	public GroupException(String message) {
		super(message);
	}

	public GroupException(Throwable cause) {
		super(cause);
	}

}
