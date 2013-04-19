package org.bear.filestore.handle.impl;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.bear.filestore.enums.SpaceType;
import org.bear.filestore.fs.VirtualFile;
import org.bear.filestore.model.File;
import org.bear.filestore.model.Space;
import org.bear.filestore.service.VideoManager;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * .
 * <p/>
 * 
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-4-8
 */
public class VideoSpaceHandle extends ImageSpaceHandle {
	public static final String VIDEO_CAPTURE_PARAM = "c";
	private VideoManager videoService;

	public void setVideoService(VideoManager videoService) {
		this.videoService = videoService;
	}

	@Override
	public boolean accept(SpaceType spaceType) {
		return spaceType == SpaceType.VIDEO;
	}

	@Override
	protected String prepareXsendfilePath(HttpServletRequest request,
			File file, String xsendfilePath) {
		String start = request.getParameter("start");
		if (start != null) {
			int index = xsendfilePath.indexOf('/', 1);
			String ext = file.getExt();
			return xsendfilePath.substring(0, index) + '-' + ext
					+ xsendfilePath.substring(index) + "?start=" + start;
		}
		return xsendfilePath;
	}

	@Override
	protected VirtualFile decorate(VirtualFile vf, Space space, File file,
			Map<String, String> params, HttpServletRequest request) {
		if (params.containsKey(VIDEO_CAPTURE_PARAM)) {
			int second = NumberUtils.toInt(params.get(VIDEO_CAPTURE_PARAM), 3);
			VirtualFile capture = thumbStorage.getVirtualFile(file.toKey()
					+ "-" + second);
			if (!capture.exist() || capture.lastModified() < vf.lastModified()) {
				try {
					videoService.capture(vf.getNativeFile(),
							capture.getNativeFile(), second, null).get(10,
							TimeUnit.SECONDS);
				} catch (Exception ignored) {
				}
			}
			file.setName(file.getSimpleName() + ".jpg");
			return super.decorate(capture, space, file, params, request);
		} else {
			return vf;
		}
	}

	@Override
	protected InputStream decorate(Space space, File file, InputStream input,
			MultipartHttpServletRequest request) {
		return input;
	}

	@Override
	protected void postUploaded(Space space, File file) {
		final java.io.File video = fileStoreService.getVirtualFile(file)
				.getNativeFile();
		final java.io.File tmp = new java.io.File(video.getAbsolutePath()
				+ ".flv");
		final Long fid = file.getId();
		if (space.isSaveMetadata()) {
			file.getData().put("duration", videoService.getDuration(video));
			fileStoreService.saveFile(file);
		}
		if ("flv".equals(file.getExt())) {
			videoService.addMetaData(video, tmp, new Runnable() {
				@Override
				public void run() {
					try {
						if (tmp.exists()) {
							video.delete();
							tmp.renameTo(video);
							File file = fileStoreService.getFile(fid);
							file.setSize((int) video.length());
							fileStoreService.saveFile(file);
						}
					} catch (Exception e) {
						LOG.error("Move file error", e);
					}
				}
			});
		} else {
			videoService.convert(video, tmp, new Runnable() {
				@Override
				public void run() {
					try {
						if (tmp.exists()) {
							video.delete();
							tmp.renameTo(video);
							File file = fileStoreService.getFile(fid);
							file.setSize((int) video.length());
							fileStoreService.saveFile(file);
						}
					} catch (Exception e) {
						LOG.error("Move file error", e);
					}
				}
			});
		}
	}
}
