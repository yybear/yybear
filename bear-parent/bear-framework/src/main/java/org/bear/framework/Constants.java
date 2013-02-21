package org.bear.framework;

import java.nio.charset.Charset;
import java.util.regex.Pattern;
/**
 * .
 * <p/>
 *
 * @author <a href="mailto:ganqing@any123.com">gan qing</a>
 * @version V1.0, 13-2-19
 */
public class Constants {
    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final Charset CHARSET = Charset.forName(DEFAULT_CHARSET);

    public static final String RET = "ret";

    public static final Pattern EMAIL_PATTERN = Pattern.compile("^([a-z0-9A-Z]+[-|\\\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\\\.)+[a-zA-Z]{2,}$");
    public static final Pattern MOBILE_PATTERN = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

    public static final char SEPARATOR = '^';
    public static final char REDIS_SEPARATOR = ':';
}
