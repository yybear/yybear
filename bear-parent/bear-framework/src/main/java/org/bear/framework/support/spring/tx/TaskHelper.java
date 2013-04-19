package org.bear.framework.support.spring.tx;

import java.util.concurrent.Callable;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-4-9
 */
public class TaskHelper {
	private TaskHelper() {
    }

    public static Runnable wrapTask(final Runnable task, final ThreadResourceManager resourceManager) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    resourceManager.bind();
                    task.run();
                } finally {
                    resourceManager.unBind();
                }
            }
        };
    }

    public static <V> Callable<V> wrapTask(final Callable<V> callable, final ThreadResourceManager resourceManager) {
        return new Callable<V>() {
            @Override
            public V call() throws Exception {
                try {
                    resourceManager.bind();
                    return callable.call();
                } finally {
                    resourceManager.unBind();
                }
            }
        };
    }
}
