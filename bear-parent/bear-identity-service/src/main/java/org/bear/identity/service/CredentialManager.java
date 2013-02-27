package org.bear.identity.service;

import java.util.List;

import org.bear.identity.ex.CredentialException;
import org.bear.identity.model.Credential;
import org.bear.identity.model.CredentialActivation;
import org.bear.identity.model.PasswordProtection;
import org.bear.identity.type.ProtectionQuestion;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-26
 */
public interface CredentialManager {
	/**
	 * 认证
	 * 
	 * @param credential
	 * @return
	 * @throws CredentialException
	 */
	long authenticate(Credential credential) throws CredentialException;

	/**
	 * 发送验证码到所需绑定凭证对应的地址（邮件/手机）
	 * 
	 * @param credential
	 *            凭证
	 * @param ttl
	 *            激活码存活期（毫秒）
	 * @throws CredentialException
	 * 
	 */
	public long requestActivation(Credential credential, long ttl) throws CredentialException;

	/**
	 * 校验激活码
	 * 
	 * @param credential
	 *            凭证
	 * 
	 * @throws CredentialException
	 * 
	 */
	public boolean validateActivation(Credential credential) throws CredentialException;

	/**
	 * 创建用户凭证绑定
	 * 
	 * @param credential
	 *            凭证
	 * @throws CredentialException
	 * 
	 */
	public void createCredential(Credential credential) throws CredentialException;

	/**
	 * 创建用户凭证绑定，同时将凭证的密码设置帐户的主密码
	 * 
	 * @param credential
	 * @param updatePassword
	 * @throws CredentialException
	 */
	public void createCredential(Credential credential, boolean updatePassword) throws CredentialException;

	/**
	 * 取消用户凭证绑定
	 * 
	 * @param credential
	 *            凭证
	 * @throws CredentialException
	 * 
	 */
	public void removeCredential(Credential credential) throws CredentialException;

	/**
	 * 更新用户凭证
	 * 
	 * @param credential
	 * @throws CredentialException
	 */
	public void updateCredential(Credential credential) throws CredentialException;

	/**
	 * 根据凭证部分内容获取完整信息
	 * 
	 * @param credential
	 * @return
	 * @throws CredentialException
	 */
	public Credential matchCredential(Credential credential) throws CredentialException;

	/**
	 * 修改密码
	 * 
	 * @param uid
	 *            用户唯一标识号
	 * @param oldPwd
	 *            原密码
	 * @param newPwd
	 *            新密码
	 * 
	 * @throws CredentialException
	 * 
	 */
	public void changePassword(long uid, String oldPwd, String newPwd) throws CredentialException;

	/**
	 * 用新密码重置用户密码
	 * 
	 * @param uid
	 * @param newPwd
	 *            新密码，为“”时产生随机密码
	 * @throws CredentialException
	 */
	public void resetPassword(long uid, String newPwd) throws CredentialException;

	/**
	 * 保护密码
	 * 
	 * @param protection
	 *            密码保护（密码问题-答案）
	 * @param pwd
	 *            当前用户密码
	 * @throws CredentialException
	 * 
	 */
	public void protectPassword(PasswordProtection protection, String pwd) throws CredentialException;

	/**
	 * 密码保护验证
	 * 
	 * @param protection
	 *            用户密码保护（密码问题-答案）
	 * @return 验证是否通过
	 * @throws CredentialException
	 * 
	 */
	public boolean validatePasswordProtection(PasswordProtection protection) throws CredentialException;

	/**
	 * 判断用户密保是否存在
	 * 
	 * @param uid
	 * @return
	 * @throws CredentialException
	 */
	public boolean hasPasswordProtection(long uid) throws CredentialException;

	/**
	 * 获取用户密保问题
	 * 
	 * @param uid
	 * @return
	 * @throws CredentialException
	 */
	public ProtectionQuestion getProtectionQuestion(long uid) throws CredentialException;

	/**
	 * 获取用户所绑定的凭证
	 * 
	 * @param uid
	 * @throws CredentialException
	 * 
	 */
	public List<Credential> getUserCredentials(long uid) throws CredentialException;

	/**
	 * 判断凭证是否已经存在
	 * 
	 * @param credential
	 * @return
	 */
	public boolean isCredentialExists(Credential credential) throws CredentialException;

	/**
	 * 根据Credential（type和name）获取用户ID
	 * 
	 * @param credential
	 * @return
	 * @throws CredentialException
	 */
	public long getCredentialUid(Credential credential) throws CredentialException;

	/**
	 * 清除过期的激活码
	 * 
	 * @throws CredentialException
	 */
	void clearExpiredActivations() throws CredentialException;
	
	boolean isActivationExpired(CredentialActivation activation) throws CredentialException;
}
