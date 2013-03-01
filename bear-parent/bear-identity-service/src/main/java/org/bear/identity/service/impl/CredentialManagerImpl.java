package org.bear.identity.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bear.identity.dao.CredentialDao;
import org.bear.identity.dao.PasswordDao;
import org.bear.identity.dao.UserDao;
import org.bear.identity.ex.CredentialException;
import org.bear.identity.ex.ErrorCode;
import org.bear.identity.model.Credential;
import org.bear.identity.model.CredentialActivation;
import org.bear.identity.model.Password;
import org.bear.identity.model.PasswordProtection;
import org.bear.identity.service.CredentialManager;
import org.bear.identity.type.CredentialType;
import org.bear.identity.type.ProtectionQuestion;
import org.bear.identity.util.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-26
 */
@Service
public class CredentialManagerImpl implements CredentialManager {
	@Autowired
	private CredentialDao credentialDao;
	@Autowired
	private PasswordDao passwordDao;
	@Autowired
	private UserDao userDao;
	
	@Override
	public long authenticate(Credential credential) throws CredentialException {
		return 0;
	}

	@Override
	public long requestActivation(Credential credential, long ttl)
			throws CredentialException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean validateActivation(Credential credential)
			throws CredentialException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createCredential(Credential credential)
			throws CredentialException {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 是否可激活的凭证
	 * 
	 * @param credential
	 * @return
	 */
	protected boolean isActivableCredential(Credential credential) {
		// 如果uid==0则表示注册时，还没有uid，此时仅用来验证登录凭证的有效性
		return credential != null && credential.getType() != null
				&& !StringUtils.isBlank(credential.getName()) && credential.getUid() != null
				&& credential.getUid().longValue() >= 0
				&& (credential.getUid().longValue() == 0 
				|| this.userDao.exists(credential.getUid()));
	}

	@Override
	public void createCredential(Credential credential, boolean updatePassword)
			throws CredentialException {
		if (!this.isActivableCredential(credential))
			throw new CredentialException(ErrorCode.CREDENTIAL_INVALID);

		credential.setId(null);// 创建凭证时，id必须为空，否则以下判断是否存在时会根据id直接匹配而忽略type和name的检查
		if (this.isCredentialExists(credential))
			throw new CredentialException(ErrorCode.CREDENTIAL_EXISTS);

		this.credentialDao.save(credential);
		
		if (updatePassword && StringUtils.isNotBlank(credential.getValue())) {
			Password pw = new Password(credential.getUid(), 
					PasswordUtils.encrypt(credential.getValue()));
			passwordDao.save(pw);
		}
	}
	
	@Override
	public void removeCredential(Credential credential)
			throws CredentialException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCredential(Credential credential)
			throws CredentialException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Credential matchCredential(Credential credential)
			throws CredentialException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changePassword(long uid, String oldPwd, String newPwd)
			throws CredentialException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetPassword(long uid, String newPwd)
			throws CredentialException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void protectPassword(PasswordProtection protection, String pwd)
			throws CredentialException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean validatePasswordProtection(PasswordProtection protection)
			throws CredentialException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasPasswordProtection(long uid) throws CredentialException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ProtectionQuestion getProtectionQuestion(long uid)
			throws CredentialException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Credential> getUserCredentials(long uid)
			throws CredentialException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCredentialExists(Credential credential)
			throws CredentialException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getCredentialUid(Credential credential)
			throws CredentialException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clearExpiredActivations() throws CredentialException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isActivationExpired(CredentialActivation activation)
			throws CredentialException {
		// TODO Auto-generated method stub
		return false;
	}
	
}
