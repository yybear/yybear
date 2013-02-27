package org.bear.framework.cache.redis;

import org.apache.commons.lang.StringUtils;
import org.bear.framework.Constants;
import org.bear.framework.cache.KeyGenerator;
import org.bear.framework.util.Codecs;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-26
 */
public class DefaultKeyGenerator implements KeyGenerator {
	public static final KeyGenerator INSTANCE = new DefaultKeyGenerator();

    @Override
    public Object generate(Object... params) {
        String key = null;
        if (params != null) {
            switch (params.length) {
                case 0:
                    break;
                case 1:
                    if (params[0] != null) {
                        key = params[0].toString();
                    }
                    break;
                default:
                    key = StringUtils.join(params, Constants.REDIS_SEPARATOR);
            }
        }
        if (key != null && key.length() > 16) {
            key = Codecs.hash(key, 10);
        }
        return key;
    }
}
