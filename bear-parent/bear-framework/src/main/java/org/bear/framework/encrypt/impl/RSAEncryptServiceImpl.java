package org.bear.framework.encrypt.impl;

import javax.crypto.Cipher;

import org.bear.framework.encrypt.AbstractEncryptService;

import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-21
 */
public class RSAEncryptServiceImpl extends AbstractEncryptService {
    @Override
    protected Cipher initEncryptCipher(byte[] key) throws GeneralSecurityException {
        SecureRandom sr = new SecureRandom();
        Cipher cipher = Cipher.getInstance(getMethod());
        cipher.init(Cipher.ENCRYPT_MODE, KeyFactory.getInstance(getMethod()).generatePrivate(new PKCS8EncodedKeySpec(key)),sr);
        return cipher;
    }

    @Override
    protected Cipher initDecryptCipher(byte[] key) throws GeneralSecurityException {
        SecureRandom sr = new SecureRandom();
        Cipher cipher = Cipher.getInstance(getMethod());
        cipher.init(Cipher.DECRYPT_MODE, KeyFactory.getInstance(getMethod()).generatePublic(new X509EncodedKeySpec(key)),sr);
        return cipher;
    }

    @Override
    public String getMethod() {
        return "RSA";
    }
}
