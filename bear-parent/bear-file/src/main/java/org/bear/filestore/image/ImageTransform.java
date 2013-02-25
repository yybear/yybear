package org.bear.filestore.image;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bear.filestore.model.Dimension;

/**
 * 图片转换接口.
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-22
 */
public interface ImageTransform {
	void load(String srcFilePath) throws IOException;

    void save(String destFilePath) throws IOException;

    void load(InputStream input) throws IOException;

    void save(OutputStream out, String postfix) throws IOException;

    boolean isModified();

    Dimension getSize(String filePath) throws IOException;

    Dimension getSize() throws IOException;

    void resize(int width, int height) throws IOException;

    void rotate(double rotate) throws IOException;

    void resizeWithMax(Integer maxWidth, Integer maxHeight) throws IOException;

    void rotateWithMax(double rotate, Integer maxWidth, Integer maxHeight) throws IOException;

    void crop(int left, int top, Integer width, Integer height) throws IOException;
}
