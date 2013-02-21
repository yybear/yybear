package org.bear.identity.model;

import java.util.Date;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-20
 */
public class CredentialActivation extends Credential {

	private static final long serialVersionUID = -1204745507175360634L;

	private Date createTime;
	private long ttl;

	public CredentialActivation() {
		super();
	}
	
	public CredentialActivation(Credential c) {
		super(c);		
	}

	public CredentialActivation(CredentialActivation c) {
		super(c);

		this.createTime = c.createTime;
		this.ttl = c.ttl;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getTtl() {
		return ttl;
	}

	public void setTtl(long ttl) {
		this.ttl = ttl;
	}

}
