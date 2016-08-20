package com.qjk.qblog.http;


import java.util.List;
import java.util.Map;

public interface IMessage {

	public int getResponseCode();
	
	public String getResponseMessage();
	
	public Map<String,List<String>> getResponseHeaders();
	
	public void addHeader(String key,String value) throws Throwable;
	
	public void setTimeout(int timeout) throws Throwable;
	
	public String exec() throws Throwable;
	
}
