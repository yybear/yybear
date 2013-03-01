package org.bear.identity.model;

import java.io.Serializable;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-28
 */
public class UserAttributePK implements Serializable {
	private static final long serialVersionUID = 6515377283247775591L;
	private Long uid;
	private String attrKey;
	
	public UserAttributePK() {
		super();
	}
	public UserAttributePK(Long uid, String attrKey) {
		super();
		this.uid = uid;
		this.attrKey = attrKey;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	
	public String getAttrKey() {
		return attrKey;
	}
	public void setAttrKey(String attrKey) {
		this.attrKey = attrKey;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attrKey == null) ? 0 : attrKey.hashCode());
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAttributePK other = (UserAttributePK) obj;
		if (attrKey == null) {
			if (other.attrKey != null)
				return false;
		} else if (!attrKey.equals(other.attrKey))
			return false;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		return true;
	}
	
}
