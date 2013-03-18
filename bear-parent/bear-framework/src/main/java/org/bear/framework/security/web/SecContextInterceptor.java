package org.bear.framework.security.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.bear.framework.ex.NoPermissonException;
import org.bear.framework.security.Constants;
import org.bear.framework.security.SecContext;
import org.bear.framework.security.SecHelper;
import org.bear.framework.security.UserProvider;
import org.bear.framework.support.spring.ConfigurableInterceptor;
import org.bear.framework.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-6
 */
public class SecContextInterceptor extends ConfigurableInterceptor{
	private static final Logger LOG = LoggerFactory.getLogger(SecContextInterceptor.class);
	 
	private UserProvider userProvider;
    private String[] needLogins;
    private String redirectUrl;

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public void setNeedLogins(String[] needLogins) {
        this.needLogins = needLogins;
    }

    public void setUserProvider(UserProvider userProvider) {
		this.userProvider = userProvider;
	}

	@Override
    public boolean internalPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	LOG.debug("SecContextInterceptor");
    	SecContext.getContext().set(Constants.CLIENT_IP, RequestUtils.getClientIP(request));
        SecContext.getContext().setSessionUser(userProvider.getUser(request));
        if (ArrayUtils.isNotEmpty(needLogins) && RequestUtils.matchAny(request, urlPathHelper, pathMatcher, needLogins) && SecHelper.isGuest()) {
            if (redirectUrl != null) {
                response.sendRedirect(redirectUrl + ServletUriComponentsBuilder.fromRequest(request).build().encode());
            } else {
                throw new NoPermissonException("need login");
            }
            return false;
        }
        return true;
    }

    @Override
    public void internalAfterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //SecContext.clearContext();
    }
}
