package org.bear.framework.security;

import javax.servlet.http.HttpServletRequest;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-20
 */
public interface UserProvider {
	SessionUser getUser(HttpServletRequest request);

    SessionUser getUser(String token);
}
