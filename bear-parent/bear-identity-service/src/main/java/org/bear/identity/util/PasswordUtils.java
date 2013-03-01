package org.bear.identity.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.bear.identity.ex.PasswordException;

/**
 * 密码加密解密工具类.
 * <p/>
 * 
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-28
 */
public class PasswordUtils {
	protected static final String ENCODING = "UTF-8";
	protected static final String SECRET_KEY = "yybear-chenyi";

	protected static final String DIGEST_ALGORITHM = "HmacMD5";

	protected static final String CIPHER_ALGORITHM = "AES";

	protected static final String RANDOM_ALGORITHM = "SHA1PRNG";

	protected static Mac digester;

	protected static Cipher encryptCipher;

	protected static Cipher decryptCipher;

	static {
		try {
			byte[] key = generateKey(128);
			SecretKeySpec digestKey = new SecretKeySpec(key, DIGEST_ALGORITHM);
			digester = Mac.getInstance(DIGEST_ALGORITHM);
			digester.init(digestKey);

			SecretKeySpec cipherKey = new SecretKeySpec(key, CIPHER_ALGORITHM);
			encryptCipher = Cipher.getInstance(CIPHER_ALGORITHM);
			encryptCipher.init(Cipher.ENCRYPT_MODE, cipherKey);
			decryptCipher = Cipher.getInstance(CIPHER_ALGORITHM);
			decryptCipher.init(Cipher.DECRYPT_MODE, cipherKey);
		} catch (Exception e) {
		}
	}
	
	public static String digest(String value) {
		try {
			return Hex.encodeHexString(digestBytes(value.getBytes(ENCODING)));
		} catch (Exception e) {
			throw new PasswordException(e.getMessage(), e);
		}
	}

	public static String encrypt(String value) {
		try {
			return Hex.encodeHexString(encryptBytes(value.getBytes(ENCODING)));
		} catch (Exception e) {
			throw new PasswordException(e.getMessage(), e);
		}
	}

	public static String decrypt(String value) {
		try {
			return new String(decryptBytes(Hex.decodeHex(value.toCharArray())), ENCODING);
		} catch (Exception e) {
			throw new PasswordException(e.getMessage(), e);
		}
	}

	protected static byte[] digestBytes(byte[] value) throws Exception {
		return digester.doFinal(value);
	}

	protected static byte[] encryptBytes(byte[] value) throws Exception {
		return encryptCipher.doFinal(value);
	}

	protected static byte[] decryptBytes(byte[] value) throws Exception {
		return decryptCipher.doFinal(value);
	}

	public static byte[] generateKey(int length) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance(CIPHER_ALGORITHM);
		kgen.init(length, new SecureRandom(SECRET_KEY.getBytes(ENCODING)));
		SecretKey secretKey = kgen.generateKey();
		return secretKey.getEncoded();
	}

	protected static Random createRandom() {
		try {
			return SecureRandom.getInstance(RANDOM_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
		}

		return new Random();
	}

	public static String random(int length) {
		int len = length / 2;
		if (length % 2 != 0)
			len++;

		byte[] pwd = new byte[len];
		createRandom().nextBytes(pwd);
		return Hex.encodeHexString(pwd);
	}

	public static void main(String[] args) throws Exception {

		String v = "这是明文";
		System.out.println("v=" + v);
		System.out.println("digest=" + PasswordUtils.digest(v));
		String e = PasswordUtils.encrypt(v);
		System.out.println("encrypt=" + e);
		System.out.println("decrypt=" + PasswordUtils.decrypt(e));
		System.out.println("random=" + PasswordUtils.random(6));
	}
}
