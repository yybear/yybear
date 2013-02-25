/*
 * Project:  any
 * Module:   any-file-store
 * File:     SpaceType.java
 * Modifier: xyang
 * Modified: 2012-07-04 10:30
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
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 12-7-2
 */
public enum SpaceType implements LabeledEnum {
    BIN("普通文件"),
    IMAGE("图片"),
    AVATAR("头像"),
    VIDEO("视频");
    private String label;

    SpaceType(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
