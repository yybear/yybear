package org.bear.identity.model;

import java.io.Serializable;

import org.bear.identity.type.CredentialType;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-20
 */
public class Credential implements Serializable {

	private static final long serialVersionUID = 1093980158411446973L;

	private Long id;
	private Long uid; // optional
	private CredentialType type; // optional
	private String name; // optional
	private String value; // optional

	public Credential() {
		super();
	}

	public Credential(Credential c) {
		this();
		this.id = c.id;
		this.uid = c.uid;
		this.type = c.type;
		this.name = c.name;
		this.value = c.value;
	}
	
	public Credential(CredentialActivation c) {
		this();
		this.uid = c.getUid();
		this.type = c.getType();
		this.name = c.getName();
		this.value = c.getValue();
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public CredentialType getType() {
		return type;
	}

	public void setType(CredentialType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Credential other = (Credential) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Credential [id=" + id + ", uid=" + uid + ", type=" + type + ", name=" + name + ", value="
				+ value + "]";
	}

}
