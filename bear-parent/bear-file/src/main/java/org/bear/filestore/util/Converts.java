package org.bear.filestore.util;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bear.api.fs.Action;
import org.bear.filestore.enums.ImageOperate;
import org.bear.filestore.model.File;
import org.bear.filestore.model.ImageAction;
import org.bear.framework.ex.ErrorCode;
import org.bear.framework.ex.GlobalException;
import org.bear.global.type.AccessScope;
import org.bear.framework.util.TypeUtils;

import java.util.List;
import java.util.Map;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-22
 */
public class Converts {

    private Converts() {
    }

    public static org.bear.api.fs.File toApiFile(File file) {
        if (file == null) {
            return null;
        }
        org.bear.api.fs.File tf = new org.bear.api.fs.File();
        tf.setId(file.getId());
        tf.setBizKey(file.getBizKey());
        tf.setOwner(file.getOwner());
        tf.setUserId(file.getUserId());
        tf.setName(file.getName());
        tf.setUpdateAt(file.getUpdateAt().getTime());
        tf.setSize(file.getSize());
        tf.setScope(org.bear.api.type.AccessScope.values()[file.getScope().ordinal()]);
        tf.setData(TypeUtils.toStringMap(file.getData()));
        return tf;
    }

    public static List<org.bear.api.fs.File> toApiFileList(List<File> files) {
        List<org.bear.api.fs.File> list = Lists.newArrayListWithCapacity(files.size());
        for (File file : files) {
            list.add(Converts.toApiFile(file));
        }
        return list;
    }

    public static File toFile(File file, org.bear.api.fs.File tf) {
        if (file == null) {
            file = new File();
            file.setBizKey(tf.getBizKey());
        }
        if (tf.getOwner() != null) {
            file.setOwner(tf.getOwner());
        }
        file.setUserId(tf.getUserId());
        file.setName(tf.getName());
        if (tf.getScope() != null) {
            file.setScope(TypeUtils.convertEnum(tf.getScope(), AccessScope.class));
        }
        file.setData(TypeUtils.toMap(tf.getData()));
        return file;
    }

    public static ImageAction toImageAction(Action action) {
        if (action.getOperate() == null) {
            throw new GlobalException(ErrorCode.ILLEGAL_PARAM, "operate is required; it must not be null");
        }
        if (action.getParam() == null) {
            throw new GlobalException(ErrorCode.ILLEGAL_PARAM, "param is required; it must not be null");
        }
        ImageAction imageAction = new ImageAction();
        imageAction.setImageOperate(TypeUtils.convertEnum(action.getOperate(), ImageOperate.class));
        Map<String, Object> map = Maps.newHashMap();
        map.putAll(action.getParam());
        imageAction.setParam(map);
        return imageAction;
    }
}
