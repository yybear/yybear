package org.bear.filestore.handle;
/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-4-8
 */
public interface ClientHandleFactory {
	ClientHandle getClientHandle(String client);
}
