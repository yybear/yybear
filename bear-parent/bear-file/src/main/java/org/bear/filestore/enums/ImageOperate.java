/*
 * Project:  any
 * Module:   any-file-store
 * File:     Operate.java
 * Modifier: xyang
 * Modified: 2012-08-23 21:35
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package org.bear.filestore.enums;

import org.bear.global.type.LabeledEnum;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 12-8-23
 */
public enum ImageOperate implements LabeledEnum {
    RESIZE("等比缩放"),
    CROP("裁剪"),
    ROTATE("旋转");
    private String label;

    ImageOperate(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
