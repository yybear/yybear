package org.bear.filestore.handle.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.bear.api.identity.IdentityService;
import org.bear.filestore.Constants;
import org.bear.filestore.enums.SpaceType;
import org.bear.filestore.ex.FileStoreException;
import org.bear.filestore.handle.SpaceHandle;
import org.bear.filestore.handle.SpaceHandleChain;
import org.bear.filestore.model.File;
import org.bear.filestore.model.Space;
import org.bear.filestore.service.FileStoreManager;
import org.bear.framework.ex.NoPermissonException;
import org.bear.framework.security.SecHelper;
import org.bear.global.type.AccessScope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * 存储空间的访问控制处理类.
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-4-8
 */
public class AccessScopeCheckSpaceHandle implements SpaceHandle {
    protected FileStoreManager fileStoreService;
    private IdentityService identityService;

    public void setFileStoreService(FileStoreManager fileStoreService) {
        this.fileStoreService = fileStoreService;
    }

    public void setIdentityService(IdentityService identityService) {
        this.identityService = identityService;
    }

    @Override
    public boolean accept(SpaceType spaceType) {
        return true;
    }

    @Override
    public void requestFile(Space space, File file, String path, HttpServletRequest request, HttpServletResponse response, SpaceHandleChain handleChain) throws FileStoreException {
        AccessScope as = file.getScope();
        if (as == null) {
            as = space.getReadScope();
        }
        if (as != null) {
            switch (as) {
                case USER:
                    SecHelper.assertUser();
                    break;
                case SELF:
                    if (file.getUserId() != SecHelper.getUserId()) {
                        throw new NoPermissonException(path + ",scope:" + as);
                    }
                    break;
                case TOKEN:
                    if (!fileStoreService.hasPermission(request.getParameter("token"), file.getId(), false)) {
                        throw new NoPermissonException(path + ",scope:" + as);
                    }
                    break;
                case SESSION:
                    String token = SecHelper.getToken();
                    String value = null;
					try {
						value = identityService.getSessionAttribute(token, Constants.FS + file.getId());
					} catch (Exception ignore) {
					} 
					if (StringUtils.isEmpty(value)) {
                        throw new NoPermissonException(path + ",scope:" + as);
                    }
                    break;
                case ROLE:
            }
        }
        handleChain.requestFile(space, file, path, request, response);
    }

    @Override
    public boolean uploadNextFile(Space space, File file, InputStream input, MultipartHttpServletRequest request, HttpServletResponse response, SpaceHandleChain handleChain) throws FileStoreException {
        AccessScope as = space.getWriteScope();
        if (as != null) {
            switch (as) {
                case USER:
                    SecHelper.assertUser();
                    break;
                case SELF:
                    if (file.getUserId() != SecHelper.getUserId()) {
                        throw new NoPermissonException();
                    }
                    break;
                case TOKEN:
                    String token = request.getParameter("token");
                    if (file.getId() != null) {
                        if (!fileStoreService.hasPermission(token, file.getId(), true)) {
                            throw new NoPermissonException(file.getId().toString());
                        }
                    } else {
                        if (!fileStoreService.hasPermission(token, space.getBizKey(), file.getOwner(), true)) {
                            throw new NoPermissonException("space:" + space.getBizKey() + ",owner:" + file.getOwner());
                        }
                    }
                    break;
                case ROLE:
            }
        }
        return handleChain.uploadNextFile(space, file, input, request, response);
    }
}
