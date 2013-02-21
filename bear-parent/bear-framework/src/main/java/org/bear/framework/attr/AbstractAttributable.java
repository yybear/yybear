package org.bear.framework.attr;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.PropertyPlaceholderHelper;
import static org.springframework.util.SystemPropertyUtils.*;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-20
 */
public abstract class AbstractAttributable implements Attributable {

	private static final PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper(PLACEHOLDER_PREFIX, PLACEHOLDER_SUFFIX, VALUE_SEPARATOR, true);

    @Override
    public boolean hasAttribute(String key) {
        return getAttribute(key) != null;
    }

    @Override
    public String getAttribute(String key) {
        return getAttribute(key, String.class);
    }

    @Override
    public String getAttribute(String key, String defaultValue) {
        String value = getAttribute(key);
        return value == null ? defaultValue : value;
    }

    @Override
    public String getRequiredAttribute(String key) throws IllegalStateException {
        String value = getAttribute(key);
        if (value == null) {
            throw new IllegalStateException("required key [" + key + "] not found");
        }
        return value;
    }

    @Override
    public String resolvePlaceholders(String text) {
        return helper.replacePlaceholders(text, new PropertyPlaceholderHelper.PlaceholderResolver() {
            @Override
            public String resolvePlaceholder(String placeholderName) {
                return getAttribute(placeholderName);
            }
        });
    }

    @Override
    public String[] getArrayAttribute(String key) {
        return getAttribute(key, String[].class);
    }


    @Override
    public <T> T getAttribute(String key, Class<T> targetType, T defaultValue) {
        T value = getAttribute(key, targetType);
        return value == null ? defaultValue : value;
    }


    @Override
    public <T> T getRequiredAttribute(String key, Class<T> targetType) throws IllegalStateException {
        T value = getAttribute(key, targetType);
        if (value == null) {
            throw new IllegalStateException("required key [" + key + "] not found");
        }
        return value;
    }

    @Override
    public Map<String, String> getAttributes(String... keys) {
        return getAttributes(Arrays.asList(keys));
    }

    @Override
    public Map<String, String> getAttributes(Collection<String> keys) {
        Map<String, String> map = new HashMap<String, String>(keys.size());
        for (String key : keys) {
            String value = getAttribute(key);
            if (value != null) {
                map.put(key, value);
            }
        }
        return map;
    }

    @Override
    public void setAttributes(Map<String, ?> map) {
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            setAttribute(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public String toString() {
        return getAttributes(getAttributeNames()).toString();
    }
}
