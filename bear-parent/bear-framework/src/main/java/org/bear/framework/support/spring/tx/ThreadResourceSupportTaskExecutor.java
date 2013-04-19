package org.bear.framework.support.spring.tx;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-4-9
 */
public class ThreadResourceSupportTaskExecutor extends ThreadPoolTaskExecutor {
    private static final long serialVersionUID = 845748676353680515L;
    private ThreadResourceManager threadResourceManager;

    @Autowired
    public void setThreadResourceManager(ThreadResourceManager threadResourceManager) {
        this.threadResourceManager = threadResourceManager;
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(TaskHelper.wrapTask(task, threadResourceManager));
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(TaskHelper.wrapTask(task, threadResourceManager));
    }

    @Override
    public void execute(Runnable task, long startTimeout) {
        super.execute(TaskHelper.wrapTask(task, threadResourceManager), startTimeout);
    }

    @Override
    public void execute(Runnable task) {
        super.execute(TaskHelper.wrapTask(task, threadResourceManager));
    }
}
