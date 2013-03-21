package org.bear.framework.encrypt;

import java.security.GeneralSecurityException;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-20
 */
public interface EncryptService {
	String encrypt(String str) throws GeneralSecurityException;

    String decrypt(String str) throws GeneralSecurityException;

    byte[] encrypt(byte[] bytes) throws GeneralSecurityException;

    byte[] decrypt(byte[] bytes) throws GeneralSecurityException;

    String getMethod();
}
