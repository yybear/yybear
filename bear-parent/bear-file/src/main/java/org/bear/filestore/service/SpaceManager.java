package org.bear.filestore.service;

import org.bear.filestore.model.Space;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-22
 */
public interface SpaceManager {

    Space getSpace(String bizKey);

    Space getSpace(int bizId);

    void saveSpace(Space space);
}
