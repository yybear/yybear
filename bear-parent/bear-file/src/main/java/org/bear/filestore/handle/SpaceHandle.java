package org.bear.filestore.handle;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bear.filestore.enums.SpaceType;
import org.bear.filestore.ex.FileStoreException;
import org.bear.filestore.model.File;
import org.bear.filestore.model.Space;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 文件上传，读取处理类.
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-4-7
 */
public interface SpaceHandle {
    static final String PARAMS = "fs_params";

    /**
     * 是否接受该类型的文件.
     * @param spaceType
     * @return
     */
    boolean accept(SpaceType spaceType);

    /**
     * 获取文件.
     * @param space
     * @param file
     * @param path
     * @param request
     * @param response
     * @param handleChain
     * @throws FileStoreException
     */
    void requestFile(Space space, File file, String path, HttpServletRequest request, HttpServletResponse response, SpaceHandleChain handleChain) throws FileStoreException;

    /**
     * 上传文件.
     * @param space
     * @param file
     * @param input
     * @param request
     * @param response
     * @param handleChain
     * @return
     * @throws FileStoreException
     */
    boolean uploadNextFile(Space space, File file, InputStream input, MultipartHttpServletRequest request, HttpServletResponse response, SpaceHandleChain handleChain) throws FileStoreException;
}
