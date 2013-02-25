package org.bear.identity.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-20
 */
@Entity
/*@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)*/
@Table(name="ids_credential_activation")
@PrimaryKeyJoinColumn(name="cid")
public class CredentialActivation extends Credential {

	private static final long serialVersionUID = -1204745507175360634L;
	
	@Column(columnDefinition = "timestamp null")
	@Temporal(TemporalType.TIMESTAMP)
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
