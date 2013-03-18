package org.bear.framework.security;

import java.util.Set;

import org.bear.framework.attr.Attributable;
import org.bear.framework.ex.NoPermissonException;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-6
 */
public class SecHelper {

	private SecHelper() {
    }

    public static SessionUser getSessionUser() {
        return SecContext.getContext().getSessionUser();
    }

    public static long getUserId() {
        return getSessionUser().getId();
    }

    public static Set<Role> getRoles() {
        return getSessionUser().getRoles();
    }

    public static Attributable getAttrs() {
        return getSessionUser().getAttributes();
    }

    public static Attributable getSession() {
        return getSessionUser().getSession();
    }

    public static String getToken() {
        return getSessionUser().getToken();
    }

    public static String getClientIp() {
        return SecContext.getContext().get(Constants.CLIENT_IP);
    }

    public static boolean isGuest() {
        return getRoles().contains(Constants.GUEST);
    }

    public static boolean isRoot() {
        return getRoles().contains(Constants.ROOT);
    }

    public static void assertUser() {
        if (isGuest()) {
            throw new NoPermissonException("guest is not allow");
        }
    }

    public static void assertRoot() {
        if (!isRoot()) {
            throw new NoPermissonException("not root");
        }
    }

}
