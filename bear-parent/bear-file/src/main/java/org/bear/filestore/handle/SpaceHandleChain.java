package org.bear.filestore.handle;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bear.filestore.ex.FileStoreException;
import org.bear.filestore.model.File;
import org.bear.filestore.model.Space;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 文件上传，读取处理链.
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-4-7
 */
public interface SpaceHandleChain {
	/**
	 * 获取文件.
	 * @param space
	 * @param file
	 * @param path
	 * @param request
	 * @param response
	 * @throws FileStoreException
	 */
	void requestFile(Space space, File file, String path, HttpServletRequest request, HttpServletResponse response) throws FileStoreException;

	/**
	 * 上传文件.
	 * @param space
	 * @param file
	 * @param input
	 * @param request
	 * @param response
	 * @return
	 * @throws FileStoreException
	 */
    boolean uploadNextFile(Space space, File file, InputStream input, MultipartHttpServletRequest request, HttpServletResponse response) throws FileStoreException;
}
