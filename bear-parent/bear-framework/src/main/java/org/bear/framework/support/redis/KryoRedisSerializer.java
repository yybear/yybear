package org.bear.framework.support.redis;

import org.bear.framework.util.GenericUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-26
 */
public class KryoRedisSerializer<T> implements RedisSerializer<T>{
	public static final Kryo DEFAULT_KRYO = new Kryo();
    private Class<?> type;
    private Kryo kryo = DEFAULT_KRYO;

    public KryoRedisSerializer() {
        type = GenericUtils.getGenericParameter0(getClass());
    }

    public KryoRedisSerializer(Class<T> type) {
        this.type = type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public void setKryo(Kryo kryo) {
        this.kryo = kryo;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return null;
        }
        Output output = new Output(4096, -1);
        if (type != null) {
            kryo.writeObject(output, t);
        } else {
            kryo.writeClassAndObject(output, t);
        }
        return output.toBytes();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null) {
            return null;
        }
        Input input = new Input(bytes);
        if (type != null) {
            return (T) kryo.readObject(input, type);
        } else {
            return (T) kryo.readClassAndObject(input);
        }
    }
}
