package org.bear.framework.security.impl;

import org.bear.framework.security.Role;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-20
 */
public class DefaultRole implements Role {

	private static final long serialVersionUID = -5953475223444705325L;
	private String name;
	
	public DefaultRole(String name) {
        this.name = name;
    }
	
	@Override
	public String getName() {
		return name;
	}
	@Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Role && name.equals(((Role) obj).getName());
    }

    @Override
    public String toString() {
        return "[role:" + name + "]";
    }
}
