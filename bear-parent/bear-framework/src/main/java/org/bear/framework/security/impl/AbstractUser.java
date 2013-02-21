package org.bear.framework.security.impl;

import java.util.Set;

import org.bear.framework.security.Role;
import org.bear.framework.security.SessionUser;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-20
 */
public abstract class AbstractUser implements SessionUser {
	private static final long serialVersionUID = -1896776068518435029L;
	private String token;
    private Long id;
    private String name;
    private Set<Role> roles;
    private Object nativeUser;

    protected AbstractUser(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public long getId() {
        if (id == null) {
            id = internalGetId();
        }
        return id;
    }

    @Override
    public String getName() {
        if (name == null) {
            name = internalGetName();
        }
        return name;
    }

    @Override
    public Set<Role> getRoles() {
        if (roles == null) {
            roles = internalRoles();
        }
        return roles;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getNativeUser() {
        if (nativeUser == null) {
            nativeUser = internalGetNativeUser();
        }
        return (T) nativeUser;
    }

    protected abstract long internalGetId();

    protected abstract String internalGetName();

    protected abstract Set<Role> internalRoles();

    protected abstract Object internalGetNativeUser();

    @Override
    public int hashCode() {
        return token.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SessionUser && token.equals(((SessionUser) obj).getToken());
    }

    @Override
    public String toString() {
        return "[SessionUser token:" + token + "]";
    }
}
