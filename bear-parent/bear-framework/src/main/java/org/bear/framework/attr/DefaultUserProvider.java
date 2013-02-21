package org.bear.framework.attr;

import javax.servlet.http.HttpServletRequest;

import org.bear.framework.security.SessionUser;
import org.bear.framework.security.UserProvider;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-20
 */
public class DefaultUserProvider implements UserProvider {

	@Override
	public SessionUser getUser(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SessionUser getUser(String token) {
		// TODO Auto-generated method stub
		return null;
	}

}
