package org.bear.filestore.handle.impl;

import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.bear.filestore.handle.ClientHandle;
import org.bear.filestore.model.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-4-8
 */
public class JsonClientHandle implements ClientHandle {
    private HttpMessageConverter<Object> converter;

    public void setConverter(HttpMessageConverter<Object> converter) {
        this.converter = converter;
    }

    @Override
    public void doHandle(List<File> files, HttpServletRequest request, HttpServletResponse response) throws IOException {
        converter.write(files.size() == 1 ? files.get(0) : files, MediaType.APPLICATION_JSON, new ServletServerHttpResponse(response));
    }
}
