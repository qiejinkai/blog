package com.qjk.qblog.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.qjk.qblog.group.ValidateInPost;

/**
 * 文章分组
 * @author qiejinkai
 *
 */
@Entity
@Table(name="qblog_agroup")
public class AGroup implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long groupId;
	private String name;
	private String summary;
	private long count;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	
	@Column(length=64)
	@NotEmpty(message="分组名称不能为空",groups={ValidateInPost.class})
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}

	@Column(length=255)
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	
	
}
