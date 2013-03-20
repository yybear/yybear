package org.bear.commonservice.spam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.avro.AvroRemoteException;
import org.apache.commons.lang.StringUtils;
import org.bear.api.spam.SpamService;
import org.bear.api.type.GlobalException;
import org.bear.commonservice.spam.dao.SensitiveWordDAO;
import org.bear.commonservice.spam.dfa.TrieTree;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.task.TaskExecutor;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-19
 */
public class SpamServiceImpl implements SpamService, InitializingBean {
    private TaskExecutor taskExecutor;
    private SensitiveWordDAO sensitiveWordDAO;

    private TrieTree trieTree;

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

	@Override
	public boolean contain(String content) throws AvroRemoteException,
			GlobalException {
		return trieTree.contains(content);
	}

	@Override
	public boolean containAny(List<String> contents)
			throws AvroRemoteException, GlobalException {
		for (String content : contents) {
            if (contain(content)) {
                return true;
            }
        }
        return false;
	}

	@Override
	public String replace(String content) throws AvroRemoteException,
			GlobalException {
		return trieTree.replace(content);
	}

	@Override
	public String replaceWith(String content, String mark)
			throws AvroRemoteException, GlobalException {
		char c = mark.charAt(0);
        return trieTree.replace(content, c);
	}

	@Override
	public Map<String, String> batchReplace(Map<String, String> contents)
			throws AvroRemoteException, GlobalException {
		Map<String, String> map = new HashMap<String, String>(contents.size());
        for (Map.Entry<String, String> entry : contents.entrySet()) {
            String value = entry.getValue();
            if (StringUtils.isNotEmpty(value)) {
                map.put(entry.getKey(), trieTree.replace(entry.getValue()));
            }
        }
        return map;
	}

	@Override
	public List<String> getWords() throws AvroRemoteException, GlobalException {
		return sensitiveWordDAO.getSensitiveWords();
	}

	@Override
	public Void addWord(List<String> words) throws AvroRemoteException,
			GlobalException {
		sensitiveWordDAO.saveSensitiveWord(words);
		reloadTrieTree();
		return null;
	}
	
	@Override
    public void afterPropertiesSet() {
        reloadTrieTree();
    }

	@Override
	public Void removeWords(List<String> words) throws AvroRemoteException,
			GlobalException {
		sensitiveWordDAO.removeSensitiveWords(words);
		reloadTrieTree();
		return null;
	}
	
    private void reloadTrieTree() {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<String> words = sensitiveWordDAO.getSensitiveWords();
                trieTree = new TrieTree(words.toArray(new String[words.size()]));
            }
        });
    }

}
