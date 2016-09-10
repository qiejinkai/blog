package com.qjk.qblog.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "qblog_wxmp_message")
public class WxmpMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long wxMsgId;
	private String toUserName = "";
	private String fromUserName = "";
	private String createTime = "";
	private String msgType = "";
	private String msgId = "";
	private String event = "";
	private String eventKey = "";
	private String ticket = "";
	private String latitude = "";
	private String longitude = "";
	private String precision = "";
	private String picUrl = "";
	private String mediaId = "";
	private String title = "";
	private String description = "";
	private String url = "";
	private String location_X = "";
	private String location_Y = "";
	private String scale = "";
	private String label = "";
	private String content = "";
	private String format = "";
	private String recognition = "";
	private String msg;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getWxMsgId() {
		return wxMsgId;
	}

	public void setWxMsgId(long wxMsgId) {
		this.wxMsgId = wxMsgId;
	}

	@Column(length=64)
	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	

	@Column(columnDefinition="mediumtext")
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Column(length=64)
	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	@Column(length=16)
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Column(length=16)
	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	@Column(length=64)
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	@Column(length=16)
	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	@Column(length=32)
	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	@Column(length=256)
	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	@Column(length=128)
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@Column(length=128)
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Column(length=64,name="precision_")
	public String getPrecision() {
		return precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	@Column(length=256)
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	@Column(length=64)
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	@Column(length=128)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(length=512)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(length=128)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(length=32)
	public String getLocation_X() {
		return location_X;
	}

	public void setLocation_X(String location_X) {
		this.location_X = location_X;
	}

	@Column(length=32)
	public String getLocation_Y() {
		return location_Y;
	}

	public void setLocation_Y(String location_Y) {
		this.location_Y = location_Y;
	}

	@Column(length=64)
	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	@Column(length=256)
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label; 
	}

	@Column(length=1024)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(length=64)
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@Column(length=64)
	public String getRecognition() {
		return recognition;
	}

	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}

}
