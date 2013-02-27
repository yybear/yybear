package org.bear.identity.service.impl;

import java.util.List;

import org.bear.identity.ex.CredentialException;
import org.bear.identity.model.Credential;
import org.bear.identity.model.CredentialActivation;
import org.bear.identity.model.PasswordProtection;
import org.bear.identity.service.CredentialManager;
import org.bear.identity.type.ProtectionQuestion;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-26
 */
public class CredentialManagerImpl implements CredentialManager {

	@Override
	public long authenticate(Credential credential) throws CredentialException {
		// TODO Auto-generated method stub
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

	@Override
	public void createCredential(Credential credential, boolean updatePassword)
			throws CredentialException {
		// TODO Auto-generated method stub
		
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
