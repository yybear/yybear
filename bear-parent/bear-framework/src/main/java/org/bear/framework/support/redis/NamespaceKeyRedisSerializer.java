package org.bear.framework.support.redis;

import org.bear.framework.Constants;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-27
 */
public class NamespaceKeyRedisSerializer extends StringRedisSerializer {
	private String namespace;
	
	public void setNamespace(String namespace) {
        this.namespace = namespace + Constants.REDIS_SEPARATOR;
    }

    @Override
    public String deserialize(byte[] bytes) {
        if (bytes == null)
            return null;
        String s = super.deserialize(bytes);
        int index = s.indexOf(namespace);
        return index == 0 ? s.substring(namespace.length()) : s;
    }

    @Override
    public byte[] serialize(String string) {
        return super.serialize(getRawKey(string));
    }

    public String getRawKey(String string) {
        if (string == null)
            return null;
        return namespace == null ? string : namespace + string;
    }
}
