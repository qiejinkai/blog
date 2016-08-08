package com.qjk.qblog.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

	private static final String TOPIC_ARTICLE = "article";
	private static final String TOPIC_MESSAGE = "message";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long messageId;
	private String content;
	private long uid;
	private String topic;
	private long ctime;
	private List<Message> answers = new ArrayList<Message>();
	private Message parent;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="parent_id")
	public Message getParent() {
		return parent;
	}
	public void setParent(Message parent) {
		this.parent = parent;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
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
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}

	@Column(length=16)
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public long getCtime() {
		return ctime;
	}
	public void setCtime(long ctime) {
		this.ctime = ctime;
	}
	@OneToMany(mappedBy="parent",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	public List<Message> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Message> answers) {
		this.answers = answers;
	}
	
	

}
