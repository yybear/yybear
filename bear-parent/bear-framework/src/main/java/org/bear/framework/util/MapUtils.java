package org.bear.framework.util;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.springframework.util.CollectionUtils;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-27
 */
public class MapUtils {
	/**
	 * 判断container中是否全部包含check
	 * 
	 * @param container
	 * @param check
	 * @return
	 */
	public static boolean containsAll(Map<?, ?> container, Map<?, ?> check) {
		if (CollectionUtils.isEmpty(container) || CollectionUtils.isEmpty(check))
			return false;

		EqualsBuilder eb = new EqualsBuilder();
		for (Entry<?, ?> entry : check.entrySet()) {
			if (container.containsKey(entry.getKey())) {
				eb.append(entry.getValue(), container.get(entry.getKey()));
			} else {
				return false;
			}
		}
		return eb.isEquals();
	}
}
