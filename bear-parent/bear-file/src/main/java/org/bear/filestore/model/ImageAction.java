package org.bear.filestore.model;

import org.bear.filestore.enums.ImageOperate;

import java.util.Map;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-22
 */
public class ImageAction {
    private ImageOperate imageOperate;
    private Map<String, Object> param;

    public ImageOperate getImageOperate() {
        return imageOperate;
    }

    public void setImageOperate(ImageOperate imageOperate) {
        this.imageOperate = imageOperate;
    }

    public Map<String, Object> getParam() {
        return param;
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }
}
