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

@Entity
@Table(name = "qblog_wbuser")
public class WBUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long wbid;
	private User user;
	private String openid;
	private String access_token;
	private String refresh_token;
	private long expires_in;
	private String gender;
	private String logo;
	private String nick;
	private String location;
	private String person_host;
	private long ctime;
	private long lastLoginTime;
	private long loginTime;
	private String lastLoginIp;
	private String loginIp;
	
	
	@Column(length=256)
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	@Column(length=256)
	public String getPerson_host() {
		return person_host;
	}

	public void setPerson_host(String person_host) {
		this.person_host = person_host;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getWbid() {
		return wbid;
	}

	public void setWbid(long wbid) {
		this.wbid = wbid;
	}

	@OneToOne(mappedBy = "wbUser")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(length = 1024)
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	@Column(length = 256)
	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	@Column(length = 256)
	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	@Column(length = 20)
	public long getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(long expires_in) {
		this.expires_in = expires_in;
	}

	@Column(length = 16)
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(length = 512)
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Column(length = 32)
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
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

}
