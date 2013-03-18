package org.bear.filestore.image.impl;

import java.io.IOException;

import org.bear.filestore.model.Dimension;
import org.im4java.core.GMOperation;
import org.im4java.core.GraphicsMagickCmd;
import org.im4java.core.Stream2BufferedImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * GraphicsMagick 图片处理实现.
 * <p/>
 * 
 * @author <a href="mailto:ganqing@any123.com">gan qing</a>
 * @version V1.0, 2013-3-11
 */
public class GMImageTransform extends AwtImageTransform {
	private static final Logger LOG = LoggerFactory
			.getLogger(GMImageTransform.class);
	private static final String ACTION = "convert";


	@Override
	public Dimension getSize(String filePath) throws IOException {
		load(filePath);
		return new Dimension(imgWidth, imgHeight);
	}

	@Override
	public Dimension getSize() throws IOException {
		return new Dimension(imgWidth, imgHeight);
	}

	@Override
	public void resize(int width, int height) throws IOException {
		zoomImage(width, height);
		modified = true;
	}

	@Override
	public void resizeWithMax(Integer maxWidth, Integer maxHeight)
			throws IOException {
		Double ratio = null;
		if (maxWidth != null && maxWidth > 0 && imgWidth > maxWidth) {
			ratio = (double) maxWidth / imgWidth;
		}
		if (maxHeight != null && maxHeight > 0 && imgHeight > maxHeight) {
			double yRatio = (double) maxHeight / imgHeight;
			if (ratio == null || yRatio < ratio) {
				ratio = yRatio;
			}
		}
		if (ratio != null) {
			zoomImage((int) (imgWidth * ratio), (int) (imgHeight * ratio));
		}
		modified = true;
	}

	@Override
	public void rotate(double rotate) throws IOException {
		GMOperation op = new GMOperation();
		op.addImage();
		op.rotate(rotate);

		convert(op);
		modified = true;
	}

	@Override
	public void rotateWithMax(double rotate, Integer maxWidth, Integer maxHeight)
			throws IOException {
		GMOperation op = new GMOperation();
		op.addImage();
		op.rotate(rotate);
		if ((maxWidth != null && maxWidth > 0)
				&& (maxHeight != null && maxHeight > 0)) {
			op.append().resize(maxWidth, maxHeight);
		} else if ((maxWidth != null && maxWidth > 0)
				&& (maxHeight == null || maxHeight <= 0)) {
			// 限宽度
			op.append().resize(maxWidth, null);
		} else if ((maxWidth == null || maxWidth <= 0)
				&& (maxHeight != null && maxHeight > 0)) {
			// 限长度
			op.append().resize(null, maxHeight);
		}
		convert(op);

		modified = true;
	}

	@Override
	public void crop(int left, int top, Integer width, Integer height)
			throws IOException {
		if (width == null || width == 0) {
			width = imgWidth - left;
		}
		if (height == null || height == 0) {
			height = imgHeight - top;
		}
		if (width > 0 && height > 0 && width <= imgWidth && height <= imgHeight) {
			cropImage(left, top, width, height);
		}
		modified = true;
	}

	protected void cropImage(int left, int top, int width, int height) throws IOException {
		GMOperation op = new GMOperation();
		op.addImage();
		op.crop(left, top, width, height);
		convert(op);
	}

	protected void zoomImage(int width, int height) throws IOException {
		GMOperation op = new GMOperation();  
	    op.addImage();
	    op.resize(width, height);
	    convert(op);
	}
	
	private void convert(GMOperation op) throws IOException {
		op.addImage("png:-");
		GraphicsMagickCmd convert = new GraphicsMagickCmd(ACTION); 
	    try {
	    	//convert.setAsyncMode(false);
	    	Stream2BufferedImage s2b = new Stream2BufferedImage();
	    	convert.setOutputConsumer(s2b);
			convert.run(op, srcBImage);
			
			destBImage = s2b.getImage();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new IOException(e.getMessage());
		}
	}

	public static void main(String[] args) {

		try {
			GMImageTransform gm = new GMImageTransform();
			gm.load("C:\\Users\\SanYuan\\Downloads\\t1.gif");

			gm.zoomImage(100, 100);

			gm.save("C:\\Users\\SanYuan\\Downloads\\t2.gif");
		} catch (Exception e) {
		}
	}

}
