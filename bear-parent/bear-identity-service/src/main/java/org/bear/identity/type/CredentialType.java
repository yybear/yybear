package org.bear.identity.type;

import org.bear.global.type.LabeledEnum;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-20
 */
public enum CredentialType implements LabeledEnum {
	/**
	 * 
	 */
	USERNAME("用户名"),

	/**
	 * 
	 */
	EMAIL("邮箱"),

	/**
	 * 
	 */
	MOBILE("手机"),

	/**
	 * 
	 */
	OAUTH("OAuth");

	String label;

	CredentialType(String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return this.label;
	}

}
