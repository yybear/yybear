package org.bear.framework.security;

import java.util.Collections;
import java.util.Set;

import org.bear.framework.attr.Attributable;
import org.bear.framework.security.impl.AbstractUser;
import org.bear.framework.security.impl.DefaultRole;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-6
 */
public class Constants {

	public static final String TOKEN = "identity_ut";
    public static final String CLIENT_IP = "clientIp";

    public static final AbstractUser GUEST_USER = new AbstractUser("") {
        private static final long serialVersionUID = -8062509454622278004L;

        @Override
        protected long internalGetId() {
            return -1l;
        }

        @Override
        protected String internalGetName() {
            return "游客";
        }

        @Override
        protected Set<Role> internalRoles() {
            return Collections.singleton(GUEST);
        }

        @Override
        protected Object internalGetNativeUser() {
            return null;
        }

        @Override
        public Attributable getAttributes() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Attributable getSession() {
            throw new UnsupportedOperationException();
        }
    };

    public static final Role GUEST = new DefaultRole(Role.ROLE_GUEST);
    public static final Role USER = new DefaultRole(Role.ROLE_USER);
    public static final Role ROOT = new DefaultRole(Role.ROLE_ROOT);
    
    /** BIZKEY for identity-authorizes-config */
	public static final String BIZKEY_IDENTITY_AUTHORIZES_CONFIG = "identity-authorizes-config";

}
