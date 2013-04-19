package org.bear.filestore.handle;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bear.filestore.model.File;

/**
 * 根据不同的客户端，做不同的响应.
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-4-8
 */
public interface ClientHandle {
    void doHandle(List<File> files, HttpServletRequest request, HttpServletResponse response) throws IOException;
}
