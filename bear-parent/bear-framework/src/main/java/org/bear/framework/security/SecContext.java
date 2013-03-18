package org.bear.framework.security;

import java.util.HashMap;
import java.util.Map;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-6
 */
public final class SecContext {
	private static ThreadLocal<SecContext> LOCAL = new InheritableThreadLocal<SecContext>() {
        @Override
        protected SecContext initialValue() {
            return new SecContext();
        }
    };

    private SessionUser sessionUser;
    private final Map<String, Object> values = new HashMap<String, Object>();

    private SecContext() {
    }

    public static SecContext getContext() {
        return LOCAL.get();
    }

    public static void clearContext() {
        LOCAL.remove();
    }

    public SessionUser getSessionUser() {
        return sessionUser;
    }

    public void setSessionUser(SessionUser sessionUser) {
        this.sessionUser = sessionUser;
    }

    public SecContext set(String key, Object value) {
        if (value == null) {
            values.remove(key);
        } else {
            values.put(key, value);
        }
        return this;
    }

    public SecContext remove(String key) {
        values.remove(key);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) values.get(key);
    }

}
