package org.bear.filestore.handle.impl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.bear.filestore.enums.ErrorCode;
import org.bear.filestore.enums.SpaceType;
import org.bear.filestore.ex.FileStoreException;
import org.bear.filestore.fs.Storage;
import org.bear.filestore.fs.VirtualFile;
import org.bear.filestore.image.ImageTransform;
import org.bear.filestore.image.ImageTransformFactory;
import org.bear.filestore.model.Dimension;
import org.bear.filestore.model.File;
import org.bear.filestore.model.Rectangle;
import org.bear.filestore.model.Space;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 图片路径格式{id}-{zoom level}-{seq}.{ext}
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-4-8
 */
public class ImageSpaceHandle extends AbstractFileSpaceHandle {
    public static final String ZOOM_PARAM = "z";

    protected Storage thumbStorage;
    protected ImageTransformFactory imageTransformFactory;

    public void setThumbStorage(Storage thumbStorage) {
        this.thumbStorage = thumbStorage;
    }

    public void setImageTransformFactory(ImageTransformFactory imageTransformFactory) {
        this.imageTransformFactory = imageTransformFactory;
    }

    @Override
    public boolean accept(SpaceType spaceType) {
        return spaceType == SpaceType.IMAGE;
    }

    @Override
    protected VirtualFile decorate(VirtualFile vf, Space space, File file, Map<String, String> params, HttpServletRequest request) {
        Dimension[] zooms = space.getZooms();
        if (params.containsKey(ZOOM_PARAM) && ArrayUtils.isNotEmpty(zooms)) {
            int index = NumberUtils.toInt(params.get(ZOOM_PARAM));
            if (index < zooms.length) {
                vf = getThumbVirtualFile(vf, file.getExt(), zooms[index].getWidth(), zooms[index].getHeight());
            }
        }
        return vf;
    }

    protected VirtualFile getThumbVirtualFile(VirtualFile vf, String ext, int maxWidth, int maxHeight) {
        VirtualFile tvf = thumbStorage.getVirtualFile(vf.getKey() + "-" + maxWidth + "-" + maxHeight);
        if (!tvf.exist() || tvf.lastModified() < vf.lastModified()) {//如果不存在或者比原始文件老,就需要重新创建缩略文件
            ImageTransform itf = imageTransformFactory.getImageTransform();
            try {
                itf.load(vf.getInputStream());
                itf.resizeWithMax(maxWidth, maxHeight);
                itf.save(tvf.getOutputStream(), ext);
            } catch (IOException e) {
                throw new FileStoreException(ErrorCode.IMAGE_RESIZE_ERROR, "resize image [" + vf + "] error");
            }
        }
        return tvf;
    }

    @Override
    protected InputStream decorate(Space space, File file, InputStream input, MultipartHttpServletRequest request) {
        if (!file.isImage()) {
            throw new FileStoreException(ErrorCode.ILLEGAL_PARAM, "not a image");
        }
        if (space.isSaveMetadata()) {
            ImageTransform itf = imageTransformFactory.getImageTransform();
            try {
                itf.load(input);
                Dimension size = itf.getSize();
                file.getData().put("width", size.getWidth());
                file.getData().put("height", size.getHeight());
            } catch (IOException ignored) {
            }
        }
        if ("gif".equals(file.getExt())) {
            return input;
        }
        Dimension max = space.getInitResize();
        Rectangle crop = space.getInitCrop();
        if (max != null || crop != null) {
            ImageTransform itf = imageTransformFactory.getImageTransform();
            try {
                itf.load(input);
                if (max != null) {
                    itf.resizeWithMax(max.getWidth(), max.getHeight());
                }
                if (crop != null) {
                    itf.crop(crop.getLeft(), crop.getTop(), crop.getWidth(), crop.getHeight());
                }
                if (!itf.isModified()) {
                    return input;
                }
                final java.io.File tmpFile = java.io.File.createTempFile("fs_th", null);
                itf.save(new FileOutputStream(tmpFile), file.getExt());
                return new FileInputStream(tmpFile) {
                    @Override
                    public void close() throws IOException {
                        FileUtils.deleteQuietly(tmpFile);
                    }
                };
            } catch (IOException e) {
                throw new FileStoreException(ErrorCode.IMAGE_RESIZE_ERROR, "resize image [" + file.getName() + "] error");
            }
        }
        return input;
    }
}
