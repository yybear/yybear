/*
 * Project:  any
 * Module:   any-file-store
 * File:     AccessType.java
 * Modifier: xyang
 * Modified: 2012-08-20 14:32
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

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 可以使用的访问方式.
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 12-7-1
 */
public final class AccessType {
    private static final Map<String, Integer> ACCESS_TYPES = Maps.newHashMap();
    /**
     * id方式
     */
    public static final int ID = 1;

    /**
     * 加密字符串
     */
    public static final int ENCRYPT = 2;

    /**
     * 所有者
     */
    public static final int OWNER = 2 << 1;


    static {
        ACCESS_TYPES.put("i", ID);
        ACCESS_TYPES.put("e", ENCRYPT);
        ACCESS_TYPES.put("o", OWNER);
    }

    private AccessType() {
    }

    public static Integer toType(String s) {
        if (s == null) {
            return ID;
        }
        Integer type = ACCESS_TYPES.get(s.toLowerCase());
        return type == null ? ID : type;
    }
}
