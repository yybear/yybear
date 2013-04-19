package org.bear.filestore.handle.impl;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.bear.filestore.enums.SpaceType;
import org.bear.filestore.model.File;
import org.bear.filestore.model.Space;

import java.io.InputStream;

/**
 * 头像空间处理类.
 * <p/>
 * 
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-4-8
 */
public class AvatarSpaceHandle extends ImageSpaceHandle {

	@Override
	public boolean accept(SpaceType spaceType) {
		return spaceType == SpaceType.AVATAR;
	}

	@Override
	protected InputStream decorate(Space space, File file, InputStream input,
			MultipartHttpServletRequest request) {
		file.setName(file.getSimpleName() + ".jpg");
		return super.decorate(space, file, input, request);
	}
}
