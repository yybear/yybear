package org.bear.framework.ex;
/**
 * .
 * <p/>
 *
 * @author <a href="mailto:ganqing@any123.com">gan qing</a>
 * @version V1.0, 13-2-19
 */
public class GlobalException extends RuntimeException {

	private static final long serialVersionUID = 6775248361588205777L;
	
	private int code = ErrorCode.SERVER_ERROR;
	private Object args[];

	private String renderedMessage;

	public GlobalException() {
	}

	public GlobalException(String message) {
		super(message);
	}

	public GlobalException(String message, Throwable cause) {
		super(message, cause);
	}

	public GlobalException(Throwable cause) {
		super(cause);
	}

	public GlobalException(int code) {
		super();
		this.code = code;
	}

    public GlobalException(int code, Throwable cause) {
        this(cause);
        this.code = code;
    }
    
    public GlobalException(int code, Object... args) {
		super("");
		this.code = code;
		this.args = args;
	}

	public GlobalException(int code, String defaultMessage, Object... args) {
		super(defaultMessage);
		this.code = code;
		this.args = args;
	}

	public GlobalException(int code, String defaultMessage, Throwable cause, Object... args) {
		super(defaultMessage, cause);
		this.code = code;
		this.args = args;
	}

	public int getCode() {
		return code;
	}

	public Object[] getArgs() {
		return args;
	}

	@Override
	public String getMessage() {
		if (renderedMessage == null) {
			renderedMessage = ExceptionUtils.buildMessage(code, args, super.getMessage(), getCause());
		}
		return renderedMessage;
	}

	@Override
	public String toString() {
		return getMessage();
	}

	public static GlobalException fromRoot(Exception e) {
		return new GlobalException(ExceptionUtils.getRootCause(e));
	}

	public void setRenderedMessage(String renderedMessage) {
		this.renderedMessage = renderedMessage;
	}
}
