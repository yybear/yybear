package org.bear.global.type;


/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-25
 */
public enum AccessScope implements LabeledEnum {
    ALL("所有人"),
    USER("登录用户"),
    ROLE("角色"),
    TOKEN("token认证"),
    SELF("仅自己可访问"),
    SESSION("需要会话授权才能访问");
    private String label;

    AccessScope(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
