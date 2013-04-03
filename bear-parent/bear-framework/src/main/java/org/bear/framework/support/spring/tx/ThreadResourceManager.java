package org.bear.framework.support.spring.tx;
/**
 * 线程资源绑定接口.
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-4-2
 */
public interface ThreadResourceManager {
	boolean hasBind();
	
	void bind();
	
	void unBind();
}
