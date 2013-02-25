package org.bear.filestore.image.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.bear.filestore.image.ImageTransform;
import org.bear.filestore.model.Dimension;

/**
 * .
 * <p/>
 * 
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-22
 */
public class AwtImageTransform implements ImageTransform {

	private BufferedImage srcBImage;
	private BufferedImage destBImage;
	private int imgWidth;
	private int imgHeight;
	private boolean modified = false;

	@Override
	public void load(String srcFilePath) throws IOException {
		srcBImage = ImageIO.read(new File(srcFilePath));
		imgWidth = srcBImage.getWidth();
		imgHeight = srcBImage.getHeight();
	}

	@Override
	public void save(String destFilePath) throws IOException {
		String postfix = destFilePath
				.substring(destFilePath.lastIndexOf(".") + 1);
		if (destBImage == null) {
			destBImage = srcBImage;
		}
		ImageIO.write(destBImage, postfix, new File(destFilePath));
	}

	@Override
	public void load(InputStream input) throws IOException {
		srcBImage = ImageIO.read(input);
		imgWidth = srcBImage.getWidth();
		imgHeight = srcBImage.getHeight();
	}

	@Override
	public void save(OutputStream out, String postfix) throws IOException {
		if (destBImage == null) {
			destBImage = srcBImage;
		}
		ImageIO.write(destBImage, postfix, out);
	}

	public boolean isModified() {
		return modified;
	}

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
	}

	@Override
	public void rotate(double rotate) throws IOException {
		if (rotate < 1e-3) {
			return;
		}
		double radian = rotate * Math.PI / 180; // get radian
		double w = Math.abs(imgHeight * Math.sin(radian))
				+ Math.abs(imgWidth * Math.cos(radian)); // get rotated height
		double h = Math.abs(imgHeight * Math.cos(radian))
				+ Math.abs(imgWidth * Math.sin(radian)); // get rotated width
		rotateImage((int) w, (int) h, radian, 1);
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
	}

	@Override
	public void rotateWithMax(double rotate, Integer maxWidth, Integer maxHeight)
			throws IOException {
		if (rotate < 1e-3) {
			return;
		}
		double radian = rotate * Math.PI / 180; // get radian
		double w = Math.abs(imgHeight * Math.sin(radian))
				+ Math.abs(imgWidth * Math.cos(radian)); // get rotated height
		double h = Math.abs(imgHeight * Math.cos(radian))
				+ Math.abs(imgWidth * Math.sin(radian)); // get rotated width
		double ratio = 1;
		if (maxWidth != null && maxWidth > 0) {
			ratio = (double) maxWidth / w;
		}
		if (maxHeight != null && maxHeight > 0) {
			double yRatio = (double) maxHeight / h;
			if (ratio == 1 || yRatio < ratio) {
				ratio = yRatio;
			}
		}
		rotateImage((int) Math.abs(w), (int) Math.abs(h), radian, ratio);
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
	}

	private void zoomImage(int width, int height) {
		destBImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = destBImage.createGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);
		destBImage.getGraphics().drawImage(
				srcBImage.getScaledInstance(width, height, Image.SCALE_SMOOTH),
				0, 0, null);
		modified = true;
	}

	private void rotateImage(int w, int h, double radian, double ratio) {
		AffineTransform transform = new AffineTransform();
		transform.setToScale(ratio, ratio);
		transform.rotate(radian, w / 2, h / 2);
		transform.translate(w / 2 - imgWidth / 2, h / 2 - imgHeight / 2);
		AffineTransformOp ato = new AffineTransformOp(transform, null);
		w *= ratio;
		h *= ratio;
		destBImage = new BufferedImage(w, h, srcBImage.getType());
		Graphics gs = destBImage.getGraphics();
		gs.setColor(Color.white); // set canvas background to white
		gs.fillRect(0, 0, w, h);
		ato.filter(srcBImage, destBImage);
		modified = true;
	}

	private void cropImage(int left, int top, int width, int height) {
		destBImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = destBImage.createGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);
		destBImage.getGraphics().drawImage(srcBImage, -left, -top, imgWidth,
				imgHeight, null);
		modified = true;
	}

}
