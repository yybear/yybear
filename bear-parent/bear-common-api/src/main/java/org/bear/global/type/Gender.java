package org.bear.global.type;
/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-20
 */
public enum Gender implements LabeledEnum {
	/**
	 * 
	 */
	UNKNOWN("未知"),

	/**
	 * 
	 */
	MALE("男性"),

	/**
	 * 
	 */
	FEMALE("女性");

	String label;

	Gender(String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return this.label;
	}

}

