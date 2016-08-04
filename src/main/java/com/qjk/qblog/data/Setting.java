package com.qjk.qblog.data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="qblog_setting")
public class Setting implements Serializable{
	
	/**
	 * 固定选项
	 */
	public static final long TYPE_FIXED = 1 << 0;
	
	/**
	 * 只读选项
	 */
	public static final long TYPE_READONLY = 1 << 1;
	
	/**
	 * 命名选项
	 */
	public static final long TYPE_NAME = 1 << 2;
	
	/**
	 * 值选项
	 */
	public static final long TYPE_VALUE = 1 << 3;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long settingId;
	private String name;
	private String summary;
	private long types;
	private String version;
	private long ctime;
	private long mtime;
	private Set<SettingOption>options = new HashSet<SettingOption>();
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getSettingId() {
		return settingId;
	}
	public void setSettingId(long settingId) {
		this.settingId = settingId;
	}
	@Column(length=32)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(length=255)
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public long getTypes() {
		return types;
	}
	public void setTypes(long types) {
		this.types = this.types | types;
	}

	@Column(length=16)
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public long getCtime() {
		return ctime;
	}
	public void setCtime(long ctime) {
		this.ctime = ctime;
	}
	public long getMtime() {
		return mtime;
	}
	public void setMtime(long mtime) {
		this.mtime = mtime;
	}
	@OneToMany(mappedBy="setting",cascade={CascadeType.ALL},fetch=FetchType.EAGER)
	public Set<SettingOption> getOptions() {
		return options;
	}
	public void setOptions(Set<SettingOption> options) {
		this.options = options;
	}
	
	
	
}
