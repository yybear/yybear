package org.bear.framework.security;

import java.io.Serializable;
import java.security.Principal;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-20
 */
public interface Role extends Principal, Serializable {
    static final String ROLE_GUEST = "guest";
    static final String ROLE_USER = "user";
    static final String ROLE_ROOT = "root";
}
