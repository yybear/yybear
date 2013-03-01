package org.bear.identity.ex;

import org.springframework.core.NestedRuntimeException;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-28
 */
public class PasswordException extends NestedRuntimeException {

	private static final long serialVersionUID = 3137946270476263278L;

	public PasswordException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public PasswordException(String msg) {
		super(msg);
	}
}
