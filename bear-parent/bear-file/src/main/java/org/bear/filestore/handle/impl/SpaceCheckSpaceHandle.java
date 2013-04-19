package org.bear.filestore.handle.impl;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.bear.filestore.enums.ErrorCode;
import org.bear.filestore.enums.SpaceType;
import org.bear.filestore.ex.FileStoreException;
import org.bear.filestore.handle.SpaceHandle;
import org.bear.filestore.handle.SpaceHandleChain;
import org.bear.filestore.model.File;
import org.bear.filestore.model.Space;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-4-8
 */
public class SpaceCheckSpaceHandle implements SpaceHandle {

    @Override
    public boolean accept(SpaceType spaceType) {
        return true;
    }

    @Override
    public void requestFile(Space space, File file, String path, HttpServletRequest request, HttpServletResponse response, SpaceHandleChain handleChain) throws FileStoreException {
        handleChain.requestFile(space, null, path, request, response);
    }

    @Override
    public boolean uploadNextFile(Space space, File file, InputStream input, MultipartHttpServletRequest request, HttpServletResponse response, SpaceHandleChain handleChain) throws FileStoreException {
        if (space.getMaxSize() > 0 && space.getMaxSize() < file.getSize()) {
            throw new FileStoreException(ErrorCode.OVER_SIZE, "file [" + file.getName() + "] is too large");
        }
        if (!space.isExtAllow(file.getExt())) {
            throw new FileStoreException(ErrorCode.EXT_NOT_ALLOW, "file [" + file.getName() + "] type is not allow");
        }
        if (space.getQuota() > 0 && space.getQuota() - space.getUsed() < file.getSize()) {
            throw new FileStoreException(ErrorCode.SPACE_EXHAUST, "space [" + space.getBizKey() + "] exhaust");
        }
        return handleChain.uploadNextFile(space, file, input, request, response);
    }
}
