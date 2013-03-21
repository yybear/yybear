package org.bear.framework.encrypt;

import java.security.GeneralSecurityException;

import javax.crypto.Cipher;

import org.bear.framework.util.Codecs;
import org.springframework.beans.factory.InitializingBean;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-20
 */
public abstract class AbstractEncryptService implements EncryptService, InitializingBean{
	private Cipher encryptCipher;
    private Cipher decryptCipher;
    private String key;
    private String encryptKey;
    private String decryptKey;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEncryptKey() {
        return encryptKey;
    }

    public void setEncryptKey(String encryptKey) {
        this.encryptKey = encryptKey;
    }

    public String getDecryptKey() {
        return decryptKey;
    }

    public void setDecryptKey(String decryptKey) {
        this.decryptKey = decryptKey;
    }

    @Override
    public String encrypt(String str) throws GeneralSecurityException {
        return Codecs.encode(encrypt(Codecs.getBytes(str)));
    }

    @Override
    public String decrypt(String str) throws GeneralSecurityException {
        return Codecs.toString(decrypt(Codecs.decode(str)));
    }

    @Override
    public byte[] encrypt(byte[] bytes) throws GeneralSecurityException {
        return encryptCipher.doFinal(bytes);
    }

    @Override
    public byte[] decrypt(byte[] bytes) throws GeneralSecurityException {
        return decryptCipher.doFinal(bytes);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (encryptKey == null) {
            encryptKey = key;
        }
        if (decryptKey == null) {
            decryptKey = key;
        }
        encryptCipher = initEncryptCipher(Codecs.decode(encryptKey));
        decryptCipher = initDecryptCipher(Codecs.decode(decryptKey));
    }

    protected abstract Cipher initEncryptCipher(byte[] key) throws GeneralSecurityException;

    protected abstract Cipher initDecryptCipher(byte[] key) throws GeneralSecurityException;
}
