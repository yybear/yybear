package org.bear.filestore.handle.impl;

import org.bear.filestore.enums.SpaceType;

/**
 * 二进制文件空间处理类.
 * <p/>
 * 
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-4-8
 */
public class BinSpaceHandle extends AbstractFileSpaceHandle {

	@Override
	public boolean accept(SpaceType spaceType) {
		return spaceType == SpaceType.BIN;
	}
}
