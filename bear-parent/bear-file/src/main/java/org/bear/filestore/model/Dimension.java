/*
 * Project:  any
 * Module:   any-file-store
 * File:     Dimension.java
 * Modifier: xyang
 * Modified: 2012-08-22 20:33
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package org.bear.filestore.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 12-8-22
 */
public class Dimension implements Serializable {
    private static final long serialVersionUID = -1806871866141863404L;
    private int width;
    private int height;

    public Dimension() {
    }

    public Dimension(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
