package org.bear.framework.ex;
/**
 * .
 * <p/>
 *
 * @author <a href="mailto:ganqing@any123.com">gan qing</a>
 * @version V1.0, 13-2-19
 */
public interface ErrorCode {
	/**
     * 自定义错误起始值
     */
    public static final int CUSTOM_ERROR_START = 100;

    /**
     * 请求成功执行
     */
    public static final int SUCCEED = 0;

    /**
     * 服务器内部错误
     */
    public static final int SERVER_ERROR = 1;

    /**
     * 非法参数
     */
    public static final int ILLEGAL_PARAM = 2;

    /**
     * 必须参数不存在
     */
    public static final int MISS_PARAM = 3;

    /**
     * 状态错误
     */
    public static final int ILLEGAL_STATE = 4;

    /**
     * 不支持此操作
     */
    public static final int UN_SUPPORTED = 5;

    /**
     * 没有权限
     */
    public static final int NO_PERMISSON = 6;
}
