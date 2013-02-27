package org.bear.framework.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * 获取类泛型参数的类型.
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-26
 */
public class GenericUtils {

	public static Class getGenericParameter(Class clazz, int index) {
        Type genType = clazz.getGenericSuperclass();
        if (genType instanceof ParameterizedType) {
            Type param = ((ParameterizedType) genType).getActualTypeArguments()[index];
            if (param instanceof Class) {
                return (Class) param;
            }
        }
        return null;
    }

    public static Class getGenericParameter0(Class clazz) {
        return getGenericParameter(clazz, 0);
    }

}
