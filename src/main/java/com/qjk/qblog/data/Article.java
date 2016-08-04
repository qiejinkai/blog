package com.qjk.qblog.data;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.qjk.qblog.group.ValidateInPost;

@Entity
@Table(name="qblog_article")
public class Article implements Serializable{
	
	public static final String ALIAS_ARTICLE="article";
	public static final String ALIAS_BANNER="banner";
	public static final String ALIAS_FRIENDLINK ="friendlink";
	public static final String ALIAS_INFORMATION="information";
	public static final String ALIAS_DIARY="diary";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long articleId;
	private String title;
	private String image;
	private String alias;
	private long grouId;
	private String tags;
	private String content;
	private String contentType;
	private long ctime;
	private long mtime;
	private String summery;
	private String author;
	private String link;
	private int hidden;
	private long indexNo;
	private int pv;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getArticleId() {
		return articleId;
	}
	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}
	@Column(length=64)
	@NotNull(message="文章标题不能为空",groups={ValidateInPost.class})
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public long getGrouId() {
		return grouId;
	}
	public void setGrouId(long grouId) {
		this.grouId = grouId;
	}
	@Column(length=255)
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Column(length=255)
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	@Column(columnDefinition="mediumtext")
	@Basic(fetch=FetchType.LAZY)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(length=64)
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
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

	@Column(length=512)
	public String getSummery() {
		return summery;
	}
	public void setSummery(String summery) {
		this.summery = summery;
	}

	@Column(length=64)
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(length=255)
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public int getHidden() {
		return hidden;
	}
	public void setHidden(int hidden) {
		this.hidden = hidden;
	}

	public int getPv() {
		return pv;
	}
	public void setPv(int pv) {
		this.pv = pv;
	}
	public long getIndexNo() {
		return indexNo;
	}
	public void setIndexNo(long indexNo) {
		this.indexNo = indexNo;
	}
	
	@Column(length=32)
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	} 
	
	
	
	
	
}
