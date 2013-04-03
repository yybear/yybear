package org.bear.framework.support.spring.tx;


/**
 * 绑定资源的提供接口. 因为使用的是TransactionSynchronizationManager，所以key和resource
 * 需要按照它的要求，具体参考spring代码.
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-4-2
 */
public interface ResourceProvider<T> {

    Object getKey();

    T getResource();

    void releaseResource(T resource);
}
