package org.bear.filestore.handle.impl;

import com.google.common.collect.Maps;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.bear.filestore.enums.AccessType;
import org.bear.filestore.enums.ErrorCode;
import org.bear.filestore.enums.SpaceType;
import org.bear.filestore.ex.FileStoreException;
import org.bear.filestore.handle.SpaceHandle;
import org.bear.filestore.handle.SpaceHandleChain;
import org.bear.filestore.model.File;
import org.bear.filestore.model.Space;
import org.bear.filestore.service.FileStoreManager;
import org.bear.framework.ex.EntityNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Map;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-4-8
 */
public class ParseFileSpaceHandle implements SpaceHandle {
    public static final String DEFAULT_PARAM = "d";
    public static final String ACCESS_TYPE_PARAM = "a";
    private static final Logger LOG = LoggerFactory.getLogger(ParseFileSpaceHandle.class);
    private FileStoreManager fileStoreService;

    public void setFileStoreService(FileStoreManager fileStoreService) {
        this.fileStoreService = fileStoreService;
    }

    @Override
    public boolean accept(SpaceType spaceType) {
        return true;
    }

    /**
     * url规则:/{accessType:[i,e,o]}/{id}/{param name (one char)}{param value}-d2-p3/anything
     */
    @Override
    public void requestFile(Space space, File file, String path, HttpServletRequest request, HttpServletResponse response, SpaceHandleChain handleChain) throws FileStoreException {
        String[] arr = StringUtils.split(path, '/');
        int len = arr.length;
        int accessType;
        String id;
        Map<String, String> params = Maps.newHashMap();
        switch (len) {
            case 0:
                throw new FileStoreException(ErrorCode.ILLEGAL_PARAM);
            case 1:
                accessType = AccessType.ID;
                id = arr[0];
                break;
            default:
                for (String s : StringUtils.split(arr[0], '-')) {
                    int l = s.length();
                    if (l > 0) {
                        params.put(s.substring(0, 1), l > 1 ? s.substring(1) : null);
                    }
                }
                accessType = AccessType.toType(params.get(ACCESS_TYPE_PARAM));
                id = arr[1];
                break;
        }
        try {
            switch (accessType) {
                case AccessType.ID:
                    if ((space.getAccessType() & AccessType.ID) != 0) {
                        if (NumberUtils.isNumber(id)) {
                            file = fileStoreService.getFile(Long.valueOf(id));
                        }
                    }
                    break;
                case AccessType.ENCRYPT:
                    if ((space.getAccessType() & AccessType.ENCRYPT) != 0) {
                        file = fileStoreService.getFileByEncryptedId(id);
                    }
                    break;
                case AccessType.OWNER:
                    if ((space.getAccessType() & AccessType.OWNER) != 0) {
                        file = fileStoreService.getSingleFile(space.getBizKey(), id);
                    }
                    break;
            }
        } catch (Exception e) {
            LOG.debug("Get file error", e);
        }
        if (file == null && params.containsKey(DEFAULT_PARAM)) {
            long[] defalutIds = space.getDefaultIds();
            if (ArrayUtils.isNotEmpty(defalutIds)) {
                int index = NumberUtils.toInt(params.get(DEFAULT_PARAM));
                if (index < defalutIds.length) {
                    file = fileStoreService.getFile(defalutIds[index]);
                    LOG.debug("Found defalut file [{}] for id [{}]", file, id);
                }
            }
        }
        if (file == null) {
            throw new EntityNotFoundException(File.class, id);
        }
        if (file.getBizId() != space.getBizId()) {
            throw new FileStoreException(ErrorCode.ILLEGAL_PARAM, "space not match");
        }
        request.setAttribute(PARAMS, params);
        handleChain.requestFile(space, file, path, request, response);
    }

    @Override
    public boolean uploadNextFile(Space space, File file, InputStream input, MultipartHttpServletRequest request, HttpServletResponse response, SpaceHandleChain handleChain) throws FileStoreException {
        return handleChain.uploadNextFile(space, file, input, request, response);
    }
}
