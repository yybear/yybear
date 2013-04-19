package org.bear.filestore.handle.impl;

import org.apache.commons.lang.StringUtils;
import org.bear.filestore.service.FileStoreManager;
import org.bear.filestore.service.MimeTypeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.bear.filestore.enums.ErrorCode;
import org.bear.filestore.ex.FileStoreException;
import org.bear.filestore.fs.VirtualFile;
import org.bear.filestore.handle.SpaceHandle;
import org.bear.filestore.handle.SpaceHandleChain;
import org.bear.filestore.model.File;
import org.bear.filestore.model.Space;
import org.bear.framework.util.Codecs;
import org.bear.global.type.AccessScope;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.util.Collections;
import java.util.Map;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 12-7-5
 */
public abstract class AbstractFileSpaceHandle implements SpaceHandle {
    protected static final Logger LOG = LoggerFactory.getLogger(AbstractFileSpaceHandle.class);
    protected FileStoreManager fileStoreService;
    private MimeTypeManager mimeTypeService;

    public void setFileStoreService(FileStoreManager fileStoreService) {
        this.fileStoreService = fileStoreService;
    }

    public void setMimeTypeService(MimeTypeManager mimeTypeService) {
        this.mimeTypeService = mimeTypeService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void requestFile(Space space, File file, String path, HttpServletRequest request, HttpServletResponse response, SpaceHandleChain handleChain) throws FileStoreException {
        Map<String, String> params = (Map<String, String>) request.getAttribute(PARAMS);
        if (params == null) {
            params = Collections.emptyMap();
        }
        sendFile(space, file, decorate(fileStoreService.getVirtualFile(file), space, file, params, request), request, response);
    }

    @Override
    public boolean uploadNextFile(Space space, File file, InputStream input, MultipartHttpServletRequest request, HttpServletResponse response, SpaceHandleChain handleChain) throws FileStoreException {
        fileStoreService.saveFileInputStream(file, decorate(space, file, input, request));
        postUploaded(space, file);
        return true;
    }

    protected void sendFile(Space space, File file, VirtualFile vf, HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(mimeTypeService.getMimeType(file.getName()));
        try {
            boolean isDownload = ServletRequestUtils.getBooleanParameter(request, "dl", false);
            response.setHeader("Content-Disposition", (isDownload ? "attachment" : "inline") + "; " + getEncodeFileName(request, file));
        } catch (Exception ignored) {
        }
        if (!checkModified(vf, request, response)) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return;
        }
        AccessScope as = file.getScope();
        if (as == null) {
            as = space.getReadScope();
        }
        if (as == null || as == AccessScope.ALL) {
            int cacheSeconds = space.getCacheSeconds();
            if (cacheSeconds > 0) {
                response.setDateHeader("Expires", System.currentTimeMillis() + cacheSeconds * 1000L);
                String headerValue = "max-age=" + cacheSeconds;
                response.setHeader("Cache-Control", headerValue);
            }
        }
        String xsendfilePath = vf.getXsendfilePath();
        if (xsendfilePath != null) {
            //System.out.println(prepareXsendfilePath(request, file, xsendfilePath));
            response.setHeader("X-Accel-Redirect", prepareXsendfilePath(request, file, xsendfilePath));
            int limitRate = space.getXsendfileLimitRate();
            if (limitRate > 0) {
                response.setIntHeader("X-Accel-Limit-Rate", limitRate);
            }
            return;
        }
        response.setHeader("Accept-Ranges", "bytes");
        int start = 0;
        int size = vf.getSize();
        String range = request.getHeader("Range");
        if (range != null && range.startsWith("bytes=")) {
            int minus = range.indexOf('-');
            if (minus > -1)
                range = range.substring(6, minus);
            try {
                start = Integer.parseInt(range);
            } catch (NumberFormatException ignored) {
            }
        }
        if (start > 0) {
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            StringBuilder sb = new StringBuilder("bytes ");
            sb.append(start).append("-").append(size - 1).append("/").append(size);
            response.setHeader("Content-Range", sb.toString());
        }
        response.setContentLength(size - start);
        try {
            vf.transferTo(start, size - start, Channels.newChannel(response.getOutputStream()));
        } catch (IOException e) {
            LOG.error("transfer file [{}],error", vf, e);
            throw new FileStoreException(ErrorCode.IO_ERROR);
        }
    }

    private String getEncodeFileName(HttpServletRequest request, File file) {
        if (ServletRequestUtils.getBooleanParameter(request, "uin", false)) {
            String ext = file.getExt();
            return "filename=\"" + file.getId() + (StringUtils.isNotEmpty(ext) ? "." + file.getExt() : "") + "\"";
        }
        String userAgent = StringUtils.lowerCase(request.getHeader("User-Agent"));
        String fileName = file.getName();
        try {
            String efn = URLEncoder.encode(fileName, "UTF-8");
            String defName = "filename=\"" + efn + "\"";
            if (StringUtils.isEmpty(userAgent) || userAgent.contains("msie")) {
                return defName;
            } else if (userAgent.contains("safari")) {
                return "filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO8859-1") + "\"";
            } else if (userAgent.contains("applewebkit")) {
                return "filename=\"" + MimeUtility.encodeText(fileName, "UTF8", "B") + "\"";
            } else if (userAgent.contains("opera") || userAgent.contains("mozilla")) {
                return "filename*=UTF-8''" + efn;
            } else {
                return defName;
            }
        } catch (UnsupportedEncodingException ignored) {
        }
        return fileName;
    }

    private boolean checkModified(VirtualFile vf, HttpServletRequest request, HttpServletResponse response) {
        if ("GET".equals(request.getMethod())) {
            Long modified = vf.lastModified();
            if (modified == null)
                return true;
            String etag = Codecs.encode(modified);
            String ifNoneMatch = request.getHeader("If-None-Match");
            if (StringUtils.isNotEmpty(ifNoneMatch) && etag.equals(ifNoneMatch)) {
                return false;
            }
            long ifModifiedSince = request.getDateHeader("If-Modified-Since");
            if (ifModifiedSince > 0L) {
                long modDate = (modified / 1000L) * 1000L;
                if (modDate <= ifModifiedSince) {
                    return false;
                }
            }
            response.setDateHeader("Last-Modified", modified);
            response.setHeader("ETag", etag);
        }
        return true;
    }

    protected String prepareXsendfilePath(HttpServletRequest request, File file, String xsendfilePath) {
        return xsendfilePath;
    }

    protected VirtualFile decorate(VirtualFile virtualFile, Space space, File file, Map<String, String> params, HttpServletRequest request) {
        return virtualFile;
    }

    protected InputStream decorate(Space space, File file, InputStream input, MultipartHttpServletRequest request) {
        return input;
    }

    protected void postUploaded(Space space, File file) {
    }
}
