package com.qjk.qblog.data;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.qjk.qblog.group.ValidateInArticlePost;
import com.qjk.qblog.group.ValidateInPost;
import com.qjk.qblog.util.Value;

@Entity
@Table(name="qblog_article")
public class Article implements Serializable{
	
	public static final String ALIAS_ARTICLE="article";
	public static final String ALIAS_BANNER="banner";
	public static final String ALIAS_FRIENDLINK ="friendlink";
	public static final String ALIAS_INFORMATION="information";
	public static final String ALIAS_DIARY="diary";
	public static final String ALIAS_NOTICE="notice";
	
	public static final int HIDDEN_NONE = 0;
	public static final int HIDDEN_HIDDEN = 1;
	
	public static final int HOME_SHOW_FALSE = 0;
	public static final int HOME_SHOW_TRUE = 1;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long articleId;
	private String title;
	private String image;
	private String alias;
	private AGroup group;
	private String tags;
	private String content;
	private String contentType;
	private long ctime;
	private long mtime;
	private String summary;
	private String author;
	private String link;
	private int hidden = HIDDEN_NONE;
	private long indexNo;
	private int pv;
	private int homeShow = HOME_SHOW_FALSE;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getArticleId() {
		return articleId;
	}
	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}
	@Column(length=64)
	@NotEmpty(message="标题不能为空",groups={ValidateInArticlePost.class,ValidateInPost.class})
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getHomeShow() {
		return homeShow;
	}
	public void setHomeShow(int homeShow) {
		this.homeShow = homeShow;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	public AGroup getGroup() {
		return group;
	}
	public void setGroup(AGroup group) {
		this.group = group;
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
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
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
	
	public static boolean contains(String alias){
		if(Value.isEmpty(alias)){
			return false;
		}
		if(ALIAS_ARTICLE.equals(alias))return true;
		if(ALIAS_BANNER.equals(alias))return true;
		if(ALIAS_DIARY.equals(alias))return true;
		if(ALIAS_FRIENDLINK.equals(alias))return true;
		if(ALIAS_INFORMATION.equals(alias))return true;
		if(ALIAS_NOTICE.equals(alias))return true;
		return false;
	}
	
	
}
