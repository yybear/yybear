package org.bear.identity.ex;

import org.bear.framework.ex.GlobalException;

public class CredentialException extends GlobalException {

	private static final long serialVersionUID = 8097221294618848749L;

	public CredentialException() {
		super();
	}

	public CredentialException(int code, String defaultMessage, Object... args) {
		super(code, defaultMessage, args);
	}
	
	public CredentialException(int code, Object... args) {
		super(code, args);
	}

	public CredentialException(int code, String defaultMessage,
			Throwable cause, Object... args) {
		super(code, defaultMessage, cause, args);
	}

	public CredentialException(int code) {
		super(code);
	}

	public CredentialException(String message, Throwable cause) {
		super(message, cause);
	}

	public CredentialException(String message) {
		super(message);
	}

	public CredentialException(Throwable cause) {
		super(cause);
	}

}
