package org.bear.framework.encrypt.impl;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.bear.framework.encrypt.AbstractEncryptService;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-21
 */
public class DESEncryptServiceImpl extends AbstractEncryptService {
    @Override
    protected Cipher initEncryptCipher(byte[] key) throws GeneralSecurityException {
        SecureRandom sr = new SecureRandom();
        SecretKey sKey = SecretKeyFactory.getInstance(getMethod()).generateSecret(new DESKeySpec(key));
        Cipher cipher = Cipher.getInstance(getMethod());
        cipher.init(Cipher.ENCRYPT_MODE, sKey, sr);
        return cipher;
    }

    @Override
    protected Cipher initDecryptCipher(byte[] key) throws GeneralSecurityException {
        SecureRandom sr = new SecureRandom();
        SecretKey sKey = SecretKeyFactory.getInstance(getMethod()).generateSecret(new DESKeySpec(key));
        Cipher cipher = Cipher.getInstance(getMethod());
        cipher.init(Cipher.DECRYPT_MODE, sKey, sr);
        return cipher;
    }

    @Override
    public String getMethod() {
        return "DES";
    }
}
