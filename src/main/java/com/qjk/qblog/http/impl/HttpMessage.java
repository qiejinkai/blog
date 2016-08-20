package com.qjk.qblog.http.impl;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.qjk.qblog.http.IMessage;



public class HttpMessage implements IMessage {

	protected int _responseCode;
	protected String _responseMessage;
	protected Map<String,List<String>> _responseHeaders;
	
	protected final HttpURLConnection conn;
	
	public HttpMessage (URL url) throws Throwable{
		conn = (HttpURLConnection) url.openConnection();
	}
	
	public void addHeader(String key, String value) throws Throwable {
		conn.addRequestProperty(key, value);
	}

	public String exec() throws Throwable{
		
		String text = null;
		
		try {
			
			conn.connect();
	
			String charset = "UTF-8";
			
			String contentType = conn.getContentType();
			
			if(contentType != null){
				int i = contentType.indexOf("charset=");
				if(i >= 0){
					charset = contentType.substring(i + 8).trim();
				}
			}
			
			_responseCode = conn.getResponseCode();
			_responseMessage = conn.getResponseMessage();
			_responseHeaders = conn.getHeaderFields();
			
			Reader reader = new InputStreamReader(conn.getInputStream(),charset);
			
			try {
				char buf[] = new char[10240];
				
				StringBuilder sb = new StringBuilder();
				
				int length;
				
				while((length = reader.read(buf)) > 0){
					sb.append(buf,0,length);
				}
				
				text = sb.toString();
			}
			finally {
				reader.close();
			}
		}
		finally {
			conn.disconnect();
		}
		
		return text;
	}

	public void setTimeout(int timeout) throws Throwable {
		conn.setConnectTimeout(timeout);
		conn.setReadTimeout(timeout);
	}

	public int getResponseCode() {
		return _responseCode;
	}

	public String getResponseMessage() {
		return _responseMessage;
	}

	public Map<String, List<String>> getResponseHeaders() {
		return _responseHeaders;
	}

}
