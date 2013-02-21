package org.bear.identity.type;

import org.bear.global.type.LabeledEnum;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-20
 */
public enum ProtectionQuestion implements LabeledEnum {
	/**
	 * 
	 */
	FAV_FILM("我最喜爱的电影？"),

	/**
	 * 
	 */
	FAV_MUSIC("我最喜欢的歌曲？"),

	/**
	 * 
	 */
	FATHER_NAME("我的父亲名字？"),

	/**
	 * 
	 */
	MATHER_NAME("我的母亲名字？"),

	/**
	 * 
	 */
	LOVER_NAME("我的爱人名字？");


	String label;

	ProtectionQuestion(String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return this.label;
	}
}
