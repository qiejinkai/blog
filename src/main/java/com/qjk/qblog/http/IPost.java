package com.qjk.qblog.http;


import java.io.InputStream;

public interface IPost extends IMessage{

	public void addValue(String key,String value) throws Throwable;
	
	public void setText(String text) throws Throwable;

	public void setInputStream(InputStream in) throws Throwable;
	
	public void setBody(byte[] body);
	
}
