package org.bear.framework.ex;
/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-6
 */
public class NoPermissonException extends GlobalException {
	private static final long serialVersionUID = 648562401451888450L;


    public NoPermissonException() {
        super(ErrorCode.NO_PERMISSON, null, "");
    }

    public NoPermissonException(String msg) {
        super(ErrorCode.NO_PERMISSON, null, msg);
    }

}
