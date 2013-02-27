package org.bear.framework.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.bear.framework.Constants;
import org.bear.framework.ex.GlobalException;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-26
 */
public class Codecs {
	private static HashFunction HF = Hashing.murmur3_128("yybear".hashCode());

    private Codecs() {
    }

    public static byte[] hash(byte[] bytes) {
        return HF.hashBytes(bytes).asBytes();
    }

    public static String hash(String str) {
        return encode(hash(getBytes(str)));
    }

    public static String hash(String str, int len) {
        return StringUtils.left(hash(str), len);
    }

    public static String encode(byte[] bytes) {
        return Base64.encodeBase64URLSafeString(bytes);
    }

    public static String encode(long l) {
        ByteBuffer buf = ByteBuffer.allocate(8);
        buf.putLong(l);
        return encode(buf.array());
    }

    public static byte[] decode(String str) {
        return Base64.decodeBase64(str);
    }

    public static String uuid() {
        UUID uuid = UUID.randomUUID();
        ByteBuffer buf = ByteBuffer.allocate(16);
        buf.putLong(uuid.getMostSignificantBits());
        buf.putLong(uuid.getLeastSignificantBits());
        return encode(hash(buf.array()));
    }

    public static String uuid(int len) {
        return StringUtils.left(uuid(), len);
    }

    public static byte[] getBytes(String s) {
        if (s == null) {
            return null;
        }
        try {
            return s.getBytes(Constants.DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new GlobalException(e);
        }
    }

    public static String toString(byte[] bytes) {
        try {
            return new String(bytes, Constants.DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new GlobalException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println(hash(""));
    }
}
