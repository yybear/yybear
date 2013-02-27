package org.bear.framework.util;

import java.text.Format;

import org.apache.commons.lang.time.FastDateFormat;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-27
 */
public class DateUtils extends org.apache.commons.lang.time.DateUtils {
    public static final Format DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd");
    public static final Format DATETIME_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
    public static final long START_SECOND = 1349800000;

    public static long toShortSecond(long timestamp) {
        return timestamp / MILLIS_PER_SECOND - START_SECOND;
    }

    public static long toNormalSecond(long shortSecond) {
        return START_SECOND + shortSecond;
    }

    public static long getCurrentShortSecond() {
        return toShortSecond(System.currentTimeMillis());
    }
}