package org.bear.filestore.util;

import org.apache.commons.lang.StringUtils;
import org.bear.filestore.model.File;
import org.bear.framework.Constants;
import org.bear.framework.ex.EntityNotFoundException;
import org.bear.framework.util.DateUtils;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-21
 */
public class EncryptUtils {
	public static String toEncryptString(Long id, int ttl) {
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        if (ttl > 0) {
            sb.append(Constants.SEPARATOR).append(DateUtils.getCurrentShortSecond() + ttl);
        }
        return sb.toString();
    }

    public static Long getSrcFileId(String s) {
        String[] arr = StringUtils.split(s, Constants.SEPARATOR);
        if (arr != null && arr.length > 0) {
            Long sId = Long.valueOf(arr[0]);
            if (arr.length > 1) {
                long expired = Long.valueOf(arr[1]);
                if (expired < DateUtils.getCurrentShortSecond()) {
                    throw new EntityNotFoundException(File.class, s);
                }
            }
            return sId;
        }
        throw new EntityNotFoundException(File.class, s);
    }
}
