package org.bear.identity.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.bear.identity.type.ProtectionQuestion;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-28
 */
@Entity
@Table(name="ids_password")
public class Password implements Serializable{
	private static final long serialVersionUID = -5881039962542822005L;

	public Password() {
		super();
	}

	public Password(Long uid, String pwd) {
		super();
		this.uid = uid;
		this.pwd = pwd;
	}

	@Id
	private Long uid;
	
	private String pwd;
	
	@Column(name="protection_question")
	private ProtectionQuestion protectionQuestion;
	
	@Column(name="protection_answer")
	private String protectionAnswer;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public ProtectionQuestion getProtectionQuestion() {
		return protectionQuestion;
	}

	public void setProtectionQuestion(ProtectionQuestion protectionQuestion) {
		this.protectionQuestion = protectionQuestion;
	}

	public String getProtectionAnswer() {
		return protectionAnswer;
	}

	public void setProtectionAnswer(String protectionAnswer) {
		this.protectionAnswer = protectionAnswer;
	}
	
}
