package org.bear.filestore.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-21
 */
public class IOUtils extends org.apache.commons.io.IOUtils {

    private static final int TRANSFER_SIZE = 64 * 1024;

    public static int copy(InputStream in, OutputStream out) throws IOException {
        int count = 0;
        try {
            byte[] buffer = new byte[TRANSFER_SIZE];
            int readed;
            while ((readed = in.read(buffer)) != -1) {
                out.write(buffer, 0, readed);
                count += readed;
            }
            out.flush();
        } finally {
            try {
                in.close();
            } catch (IOException ignored) {
            }
            try {
                out.close();
            } catch (IOException ignored) {
            }
        }
        return count;
    }
}
