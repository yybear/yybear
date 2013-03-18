package org.bear.framework.security.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.avro.AvroRemoteException;
import org.apache.commons.lang.StringUtils;
import org.bear.api.identity.IdentityService;
import org.bear.api.type.GlobalException;
import org.bear.framework.attr.Attributable;
import org.bear.framework.security.Constants;
import org.bear.framework.security.Role;
import org.bear.framework.security.SessionUser;
import org.bear.framework.security.UserProvider;
import org.hibernate.validator.internal.util.privilegedactions.GetClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-6
 */
public class DefaultUserProviderImpl implements UserProvider {
	private static final Logger LOG = LoggerFactory.getLogger(DefaultUserProviderImpl.class);
	private IdentityService identityService;

    public void setIdentityService(IdentityService identityService) {
        this.identityService = identityService;
    }

    @Override
    public SessionUser getUser(HttpServletRequest request) {
        return getUser(getToken(request));
    }

    @Override
    public SessionUser getUser(String token) {
        return StringUtils.isEmpty(token) ? Constants.GUEST_USER : new DefaultUser(token);
    }

    private String getToken(HttpServletRequest request) {
        String token = null;
        Cookie cookie = WebUtils.getCookie(request, Constants.TOKEN);
        if (cookie != null) {
            token = cookie.getValue();
        }
        if (token == null) {
            token = request.getParameter(Constants.TOKEN);
        }
        if (token == null) {
            token = request.getHeader(Constants.TOKEN);
        }
        return token;
    }

    private class DefaultUser extends AbstractUser {
        private static final long serialVersionUID = 6318032930581198122L;

        private DefaultUser(String token) {
            super(token);
        }

        @Override
        protected long internalGetId() {
            try {
				return identityService.validateToken(getToken());
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
            return 0;
        }

        @Override
        protected String internalGetName() {
            return ((org.bear.api.identity.User) getNativeUser()).getUserName();
        }

        @Override
        protected Set<Role> internalRoles() {
            return getId() > 0 ? Collections.singleton(Constants.USER) : Collections.singleton(Constants.GUEST);
        }

        @Override
        protected Object internalGetNativeUser() {
            try {
				return identityService.getUser(getId());
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
            return null;
        }

        @Override
        public Attributable getAttributes() {
            //return new Any123UserAttribute(getId());
        	return null;
        }

        @Override
        public Attributable getSession() {
            //return new Any123UserSession(getToken());
        	return null;
        }
    }

    /*private class Any123UserAttribute extends AbstractJSONAttributable {
        private long userId;
        private Attributable cache = new HashMapAttributable();

        private Any123UserAttribute(long userId) {
            this.userId = userId;
        }

        @Override
        protected void setStringAttribute(String key, String value) {
            identityService.setUserAttribute(userId, key, value);
            cache.setAttribute(key, value);
        }

        @Override
        protected void setStringAttributes(Map<String, String> map) {
            identityService.setUserAttributes(userId, map);
            cache.setAttributes(map);
        }

        @Override
        public String[] getAttributeNames() {
            return ArrayUtils.toArray(identityService.getUserAttributeNames(userId), String.class);
        }

        @Override
        public Map<String, String> getAttributes(Collection<String> keys) {
            Map<String, String> map = identityService.getUserAttributes(userId, new HashSet<String>(keys));
            cache.setAttributes(map);
            return map;
        }

        @Override
        public String getAttribute(String key) {
            String value = cache.getAttribute(key);
            if (!cache.hasAttribute(key)) {
                cache.setAttribute(key, value = identityService.getUserAttribute(userId, key));
            }
            return value;
        }

        @Override
        public void removeAttribute(String key) {
            identityService.removeUserAttribute(userId, key);
            cache.removeAttribute(key);
        }
    }*/

    /*private class Any123UserSession extends AbstractJSONAttributable {
        private String token;
        private Attributable cache = new HashMapAttributable();

        private Any123UserSession(String token) {
            this.token = token;
        }

        @Override
        protected void setStringAttribute(String key, String value) {
            identityService.setSessionAttribute(token, key, value);
            cache.setAttribute(key, value);
        }

        @Override
        protected void setStringAttributes(Map<String, String> map) {
            identityService.setSessionAttributes(token, map);
            cache.setAttributes(map);
        }

        @Override
        public String[] getAttributeNames() {
            return ArrayUtils.toArray(identityService.getSessionAttributeNames(token), String.class);
        }

        @Override
        public Map<String, String> getAttributes(Collection<String> keys) {
            Map<String, String> map = identityService.getSessionAttributes(token, new HashSet<String>(keys));
            cache.setAttributes(map);
            return map;
        }

        @Override
        public String getAttribute(String key) {
            String value = cache.getAttribute(key);
            if (!cache.hasAttribute(key)) {
                cache.setAttribute(key, value = identityService.getSessionAttribute(token, key));
            }
            return value;
        }

        @Override
        public void removeAttribute(String key) {
            identityService.removeSessionAttribute(token, key);
            cache.removeAttribute(key);
        }
    }*/

}
