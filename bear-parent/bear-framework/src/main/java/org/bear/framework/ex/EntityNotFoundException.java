package org.bear.framework.ex;

import java.io.Serializable;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-21
 */
public class EntityNotFoundException extends GlobalException {
    private static final long serialVersionUID = -316544274085347903L;

    public EntityNotFoundException(Class clazz, Serializable key) {
        super(ENTITY_NOT_FOUND, null, clazz.getSimpleName(), key);
    }
}
