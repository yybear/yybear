package org.bear.framework.message;

import java.util.Locale;

import org.springframework.context.MessageSource;
/**
 * .
 * <p/>
 *
 * @author <a href="mailto:ganqing@any123.com">gan qing</a>
 * @version V1.0, 13-2-19
 */
public final class NLS {
	private static MessageSource msgSource;

    public void setMessageSource(MessageSource msgSource) {
        NLS.msgSource = msgSource;
    }

    public static String getMessage(String key) {
        return getMessage(key, null, null, null);
    }

    public static String getMessage(String key, Object[] args) {
        return getMessage(key, args, null, null);
    }

    public static String getMessage(String key, Object[] args, String defaultMessage) {
        return getMessage(key, args, defaultMessage, null);
    }

    public static String getMessage(String key, Object[] args, String defaultMessage, Locale locale) {
        return msgSource.getMessage(key, args, defaultMessage, locale);
    }
}
