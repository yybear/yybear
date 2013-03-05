package org.bear.framework.validate;

import static java.lang.annotation.ElementType.PARAMETER;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-1
 */
@Target({PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented  
public @interface ParamName {
	String value();
}