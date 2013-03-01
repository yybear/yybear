package org.bear.identity.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * .
 * <p/>
 * 
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-27
 */
@Entity
@IdClass(value = UserAttributePK.class)
@Table(name = "ids_user_attribute")
public class UserAttribute {

	private Long uid;
	@Column(name="attr_key")
	private String attrKey;

	@Column(name="attr_value")
	private String attrValue;

	public UserAttribute() {
		super();
	}

	public UserAttribute(Long uid, String key, String value) {
		super();
		this.uid = uid;
		this.attrValue = value;
		this.attrKey = key;
	}

	@Id
	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}
	@Id
	public String getAttrKey() {
		return attrKey;
	}

	public void setAttrKey(String attrKey) {
		this.attrKey = attrKey;
	}

	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}

}
