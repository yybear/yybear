package org.bear.framework.attr;

import java.util.Collection;
import java.util.Map;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-20
 */
public interface Attributable {
	boolean hasAttribute(String key);

    String getAttribute(String key);

    String getAttribute(String key, String defaultValue);

    String getRequiredAttribute(String key) throws IllegalStateException;

    String resolvePlaceholders(String text);

    String[] getArrayAttribute(String key);

    <T> T getAttribute(String key, Class<T> targetType);

    <T> T getAttribute(String key, Class<T> targetType, T defaultValue);

    <T> T getRequiredAttribute(String key, Class<T> targetType) throws IllegalStateException;

    String[] getAttributeNames();

    Map<String, String> getAttributes(String... keys);

    Map<String, String> getAttributes(Collection<String> keys);

    void setAttribute(String key, Object value);

    void setAttributes(Map<String, ?> map);

    void removeAttribute(String key);
}
