package org.bear.framework.dao;

import java.util.Map;
import java.util.Set;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-26
 */
public interface AttributeEntityDao<E> extends EntityDao<E>{
	String getAttribute(long pid, String key);

	void setAttribute(long pid, String key, String value);

	void removeAttribute(long pid, String key);

	Map<String, String> getAttributes(long pid, Set<String> keys);

	void setAttributes(long pid, Map<String, String> values);

	void removeAttributes(long pid, Set<String> keys);

	Map<Long, String> batchGetAttribute(Set<Long> pids, String key);

	void batchSetAttribute(String key, Map<Long, String> pidValues);

	void batchRemoveAttribute(Set<Long> pids, String key);

	Map<Long, Map<String, String>> batchGetAttributes(Map<Long, Set<String>> pidKeys);

	void batchSetAttributes(Map<Long, Map<String, String>> pidValues);

	void batchRemoveAttributes(Map<Long, Set<String>> pidKeys);

	Set<String> getAttributeNames(long pid);

	void clearAttributes(long pid);

	void batchClearAttributes(long[] pids);

	void clearAllAttributes();

	boolean containsAttribute(long pid, String key);

	long[] match(Map<String, String> map);
}
