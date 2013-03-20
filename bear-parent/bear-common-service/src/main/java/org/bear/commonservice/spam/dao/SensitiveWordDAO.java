package org.bear.commonservice.spam.dao;

import java.util.List;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-19
 */
public interface SensitiveWordDAO {
	List<String> getSensitiveWords();
	void removeSensitiveWords(List<String> words);
	void saveSensitiveWord(List<String> words);
}
