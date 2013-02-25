package org.bear.filestore.service;

/**
 * mimeType获取服务
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-22
 */
public interface MimeTypeManager {

    static final String MIMETYPE_BINARY = "application/octet-stream";
    static final String ICON_UNKOWN = "unknow.gif";

    /**
     * 根据mimeType获取扩展名
     *
     * @param mimeType mine类型
     * @return 扩展名
     */
    String getExtension(String mimeType);

    /**
     * 根据扩展名获取图标
     *
     * @param ext 扩展名
     * @return 图标
     */
    String getIconByExt(String ext);

    /**
     * 根据扩展名获取mimeType
     *
     * @param ext 扩展名
     * @return mine类型
     */
    String getMimeTypeByExt(String ext);

    /**
     * 根据文件名获取图标
     *
     * @param fileName 文件名
     * @return 图标
     */
    String getIcon(String fileName);

    /**
     * 根据文件名获取mimeType
     *
     * @param fileName 文件名
     * @return mine类型
     */
    String getMimeType(String fileName);
}
