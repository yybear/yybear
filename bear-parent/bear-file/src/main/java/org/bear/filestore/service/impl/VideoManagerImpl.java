package org.bear.filestore.service.impl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.AsyncTaskExecutor;
import org.bear.filestore.ex.FileIOException;
import org.bear.filestore.service.VideoManager;
import org.bear.filestore.util.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-21
 */
public class VideoManagerImpl implements VideoManager {
    private static final Logger LOG = LoggerFactory.getLogger(VideoManagerImpl.class);
    private String captureCmd = "ffmpeg -y -i %s -f image2 -ss %s -vframes 1 %s";
    private String metaDataCmd = "yamdi -i %s -o %s";
    private String convertCmd = "ffmpeg -y -i %s -ac 1 -acodec copy -ar 8000 -vcodec flv -qmin 1 -qmax 4 %s";
    private String durationCmd = "ffprobe %1";
    private AsyncTaskExecutor executor;

    public void setCaptureCmd(String captureCmd) {
        this.captureCmd = captureCmd;
    }

    public void setMetaDataCmd(String metaDataCmd) {
        this.metaDataCmd = metaDataCmd;
    }

    public void setConvertCmd(String convertCmd) {
        this.convertCmd = convertCmd;
    }

    public void setDurationCmd(String durationCmd) {
        this.durationCmd = durationCmd;
    }

    public void setExecutor(AsyncTaskExecutor executor) {
        this.executor = executor;
    }

    @Override
    public Future<String> capture(final File src, final File dest, final int second, Runnable callback) {
        checkParentDir(dest.getParentFile());
        try {
            FileUtils.forceMkdir(dest.getParentFile());
        } catch (IOException e) {
            LOG.error("mkdir [" + dest.getParentFile().getAbsolutePath() + "] error", e);
        }
        return execute(callback, String.format(captureCmd, src.getAbsolutePath(), second, dest.getAbsolutePath()));
    }

    @Override
    public Future<String> addMetaData(final File src, final File dest, Runnable callback) {
        checkParentDir(dest.getParentFile());
        return execute(callback, String.format(metaDataCmd, src.getAbsolutePath(), dest.getAbsolutePath()));
    }

    @Override
    public Future<String> convert(final File src, final File dest, final Runnable callback) {
        checkParentDir(dest.getParentFile());
        final File tmp = new File(dest.getAbsolutePath() + ".flv");
        return executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                String out;
                try {
                    out = runCmd(String.format(convertCmd, src.getAbsolutePath(), tmp.getAbsolutePath()));
                    if (tmp.exists()) {
                        runCmd(String.format(metaDataCmd, tmp.getAbsolutePath(), dest.getAbsolutePath()));
                    }
                    if (callback != null) {
                        callback.run();
                    }
                    return out;
                } catch (IOException e) {
                    LOG.error("Convert video file [" + src.getAbsolutePath() + "] error", e);
                    return e.getMessage();
                } finally {
                    FileUtils.deleteQuietly(tmp);
                }
            }
        });
    }

    @Override
    public int getDuration(File videoFile) {
        Future<String> future = execute(null, String.format(durationCmd, videoFile.getAbsolutePath()));
        try {
            return NumberUtils.toInt(future.get(10, TimeUnit.SECONDS));
        } catch (Exception e) {
            LOG.error("Execute error", e);
        }
        return 0;
    }

    private void checkParentDir(File file) {
        try {
            FileUtils.forceMkdir(file.getParentFile());
        } catch (IOException e) {
            LOG.error("mkdir [" + file.getParentFile().getAbsolutePath() + "] error", e);
            throw new FileIOException(e.getMessage());
        }
    }

    private Future<String> execute(final Runnable callback, final String cmd) {
        return executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                try {
                    String out = runCmd(cmd);
                    if (callback != null) {
                        callback.run();
                    }
                    return out;
                } catch (Throwable e) {
                    LOG.error("Execute command [" + cmd + "]", e);
                    return e.getMessage();
                }
            }
        });
    }

    private static String runCmd(String cmd) throws IOException {
        LOG.debug("Execute command:[{}]", cmd);
        Writer out = new StringWriter();
        IOUtils.copy(Runtime.getRuntime().exec(cmd).getInputStream(), out);
        String s = out.toString();
        LOG.debug("Output: [{}]", s);
        return s;
    }
}
