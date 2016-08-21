package com.qjk.qblog.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import com.qjk.qblog.group.ValidateInPost;

@Entity
@Table(name = "qblog_user")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long uid;
	private String account;
	private String phone;
	private String email;
	private String logo;
	private String nick;
	private String password;
	private long ctime;
	private long lastLoginTime;
	private long loginTime;
	private String lastLoginIp;
	private String loginIp;

	private QQUser qqUser;
	private WXUser wxUser;

	

	@OneToOne(fetch = FetchType.LAZY)
	public QQUser getQqUser() {
		return qqUser;
	}

	@OneToOne(fetch = FetchType.LAZY)
	public WXUser getWxUser() {
		return wxUser;
	}

	public void setWxUser(WXUser wxUser) {
		this.wxUser = wxUser;
	}

	public void setQqUser(QQUser qqUser) {
		this.qqUser = qqUser;
	}

	public User() {

	}

	public User(long uid) {
		this.uid = uid;
	}

	@Column(length = 255)
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(length = 20)
	public long getCtime() {
		return ctime;
	}

	public void setCtime(long ctime) {
		this.ctime = ctime;
	}

	@Column(length = 20)
	public long getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	@Column(length = 20)
	public long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}

	@Column(length = 32)
	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	@Column(length = 32)
	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	@Column(length = 32)
	@NotNull(message = "手机号不能为空", groups = { ValidateInPost.class })
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(length = 64)
	@NotNull(message = "邮箱不能为空", groups = { ValidateInPost.class })
	@Email(message = "邮箱格式不正确", groups = { ValidateInPost.class })
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(length = 255)
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Column(length = 32)
	@NotNull(message = "昵称不能为空", groups = { ValidateInPost.class })
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	@Column(length = 64)
	@NotNull(message = "密码不能为空", groups = { ValidateInPost.class })
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
