package org.bear.filestore.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.bear.filestore.handle.SpaceHandleChain;

import org.bear.filestore.service.SpaceManager;
import org.bear.framework.support.spring.ConfigurableInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 根据路径获取文件.
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-4-7
 */
public class FileInterceptor extends ConfigurableInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(FileInterceptor.class);
    @Autowired
    private SpaceHandleChain spaceHandleChain;

    @Autowired
    private SpaceManager spaceService;

    @Override
    public boolean internalPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = urlPathHelper.getRequestUri(request).substring(urlPathHelper.getContextPath(request).length() + 1);
        int index = path.indexOf("/");
        if (index != -1) {
            String bizKey = path.substring(0, index);
            try {
                spaceHandleChain.requestFile(spaceService.getSpace(bizKey), null, path.substring(index + 1), request, response);
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                LOG.error("Request file error," + e.getMessage());
            }
            return false;
        }
        return true;
    }
}
