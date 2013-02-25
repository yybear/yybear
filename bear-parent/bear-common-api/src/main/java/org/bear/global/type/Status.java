package org.bear.global.type;
/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-21
 */
public enum Status implements LabeledEnum {
	ENABLED("启用"),
    DISABLED("停用"),
    DELETED("删除");
	private String label;

	Status(String title) {
		this.label = title;
	}

	public String getLabel() {
		return label;
	}
}
