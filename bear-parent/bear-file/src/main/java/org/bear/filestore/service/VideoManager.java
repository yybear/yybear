package org.bear.filestore.service;

import java.io.File;
import java.util.concurrent.Future;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-22
 */
public interface VideoManager {

    Future<String> capture(File src, File dest, int second, Runnable callback);

    Future<String> addMetaData(File src, File dest, Runnable callback);

    Future<String> convert(File src, File dest, Runnable callback);

    int getDuration(File videoFile);
}
