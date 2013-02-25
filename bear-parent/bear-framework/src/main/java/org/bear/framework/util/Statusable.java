package org.bear.framework.util;

import org.bear.global.type.Status;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-21
 */
public interface Statusable {

    Status getStatus();

    void setStatus(Status status);
}
