package org.bear.identity.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-20
 */
public class Session implements Serializable {

	private static final long serialVersionUID = -4183650381441152587L;

	public static Comparator<Session> CREATE_TIME_COMPARATOR = new Comparator<Session>() {

		@Override
		public int compare(Session o1, Session o2) {
			long t1 = o1.getCreationTime() == null ? 0 : o1.getCreationTime()
					.getTime();
			long t2 = o2.getCreationTime() == null ? 0 : o2.getCreationTime()
					.getTime();
			return (int) (t1 - t2);
		}

	};

	private Long id;
	private String token;
	private Long uid;
	private Date creationTime;
	private Long ttl;
	private Long tti;
	private String clientIp;
	private Date lastActiveTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Long getTtl() {
		return ttl;
	}

	public void setTtl(Long ttl) {
		this.ttl = ttl;
	}

	public Long getTti() {
		return tti;
	}

	public void setTti(Long tti) {
		this.tti = tti;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public Date getLastActiveTime() {
		return lastActiveTime;
	}

	public void setLastActiveTime(Date lastActiveTime) {
		this.lastActiveTime = lastActiveTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Session other = (Session) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
