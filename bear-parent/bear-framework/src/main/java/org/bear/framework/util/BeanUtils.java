package org.bear.framework.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-4-8
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

    /**
     * 将source上得字段值应用到target上,遵循以下原则:
     * 只应用target上为空的字段，除非为forcePropertie
     *
     * @param source           源对象
     * @param target           目标对象
     * @param allowEmptyString 是否允许空字符串，不允许的话""字符串会被当做null处理
     * @param forceProperties  强制覆盖的字段
     * @param <T>              类型
     * @return 目标对象
     * @throws org.springframework.beans.BeansException 反射异常
     */
    public static <T> T applyIf(final Object source, final T target, final boolean allowEmptyString, final String... forceProperties) throws BeansException {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        final Set<String> forceList = new HashSet<String>();
        final Set<String> ignoreList = new HashSet<String>();
        if (forceProperties != null) {
            Collections.addAll(forceList, forceProperties);
        }
        for (PropertyDescriptor targetPd : getPropertyDescriptors(target.getClass())) {
            Method readMethod = targetPd.getReadMethod();
            if (readMethod == null)
                continue;
            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                readMethod.setAccessible(true);
            }
            try {
                Object value = readMethod.invoke(target);
                if (value == null || forceList.contains(targetPd.getName())) {//如果值为空或者为强制列表,不加入排除列表
                    continue;
                }
                if (!allowEmptyString && value instanceof String && "".equals(value)) {//为空字符串，如果不允许空则用不加入到排除列表
                    continue;
                }

                ignoreList.add(targetPd.getName());
            } catch (Throwable ex) {
                throw new FatalBeanException("Could not applyIf properties from source to target", ex);
            }
        }
        copyProperties(source, target, ignoreList.toArray(new String[ignoreList.size()]));//不在排除列表的会被source值强制覆盖
        return target;
    }

    public static <T> T applyIf(final Object source, final T target, final String... forceProperties) throws BeansException {
        return applyIf(source, target, true, forceProperties);
    }

}
