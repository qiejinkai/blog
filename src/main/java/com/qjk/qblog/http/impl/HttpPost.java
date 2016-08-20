package com.qjk.qblog.http.impl;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.net.URLEncoder;

import com.qjk.qblog.http.IPost;


public class HttpPost extends HttpMessage implements IPost {

	private StringBuilder data ;
	private InputStream in;
	private byte[] body;
	
	public HttpPost(URL url) throws Throwable {
		super(url);
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setDoInput(true);
	}

	
	public void addValue(String key, String value) throws Throwable {
		if(data == null){
			data = new StringBuilder();
			addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
			data.append(key).append("=").append(URLEncoder.encode(value,"utf-8"));
		}
		else {
			data.append("&").append(key).append("=").append(URLEncoder.encode(value,"utf-8"));
		}
	}
	
	
	public String exec() throws Throwable{
		
		String text = null;
		
		try {
			
			conn.connect();
			
			if(in != null){
				
				OutputStream out = conn.getOutputStream();
				
				byte[] buf = new byte[409600];
				int length;
				
				while((length = in.read(buf)) >0){
					out.write(buf, 0, length);
				}
				
				out.close();
				
			}
			else if(body != null){

				OutputStream out = conn.getOutputStream();
				
				out.write(body);
				
				out.close();
			}
			else {
				
				Writer w = new OutputStreamWriter(conn.getOutputStream(),"utf-8");
				
				if(data != null){
					w.write(data.toString());
				}
				
				w.flush();
				
				w.close();
				
			}
			
			String charset = "utf-8";
			
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

	
	public void setText(String text) throws Throwable {
		if(data == null){
			data = new StringBuilder();
			addHeader("Content-Type", "text/plain; charset=utf-8");
		}
		data.append(text);
	}

	
	public void setInputStream(InputStream in) throws Throwable {
		this.in = in;
	}

	
	public void setBody(byte[] body) {
		this.body = body;
	}

}
