package org.bear.filestore.image;

import org.bear.filestore.image.impl.AwtImageTransform;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-22
 */
public class ImageTransformFactory {
	/**
     * 默认转换工具类的名称.
     */
    public static final Class<? extends ImageTransform> defaultImageTransform = AwtImageTransform.class;

    /**
     * 图像转换的实现类.
     */
    private static Class<? extends ImageTransform> imageTransformClazz = defaultImageTransform;

    /**
     * 设置图像转换的实现类.
     *
     * @param clazz 图像转换的实现类.
     */
    public void setTransformProvider(Class<? extends ImageTransform> clazz) {
        imageTransformClazz = clazz;
    }

    /**
     * 获取图像转换的实现类.
     *
     * @return 图像转换的实现类.
     */
    public ImageTransform getImageTransform() {
        try {
            return imageTransformClazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
