package com.qjk.qblog.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 留言
 * 
 * @author qiejinkai
 *
 */

@Entity
@Table(name="qblog_message")
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long messageId;
	private String content;
	private long articleId;
	private long ctime;
	private User user;
	private Message quote;
	
	public Message() {
	}
	
	public Message(long messageId){
		this.messageId = messageId;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getMessageId() {
		return messageId;
	}
	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}
	
	@Column(length=2048)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public long getArticleId() {
		return articleId;
	}
	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}
	public long getCtime() {
		return ctime;
	}
	public void setCtime(long ctime) {
		this.ctime = ctime;
	}
	
	@ManyToOne
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	public Message getQuote() {
		return quote;
	}
	public void setQuote(Message quote) {
		this.quote = quote;
	}
	
	
	

	

}
