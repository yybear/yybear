package org.bear.identity.type;

import org.bear.global.type.LabeledEnum;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-20
 */
public enum UserStatus implements LabeledEnum {
	/**
	 * 
	 */
	INIT("初始"),

	/**
	 * 
	 */
	NORMAL("正常"),

	/**
	 * 
	 */
	DISABLED("已禁用"),

	/**
	 * 
	 */
	ARCHIVED("已归档"),

	/**
	 * 
	 */
	LOCKED("已锁定");

	String label;

	UserStatus(String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return this.label;
	}

}
