package org.bear.framework.security;

import java.io.Serializable;
import java.security.Principal;
import java.util.Set;

import org.bear.framework.attr.Attributable;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-20
 */
public interface SessionUser extends Principal, Serializable {

    String getToken();

    long getId();

    Set<Role> getRoles();

    Attributable getAttributes();

    Attributable getSession();

    <T> T getNativeUser();
}
