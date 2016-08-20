package com.qjk.qblog.http;

import java.net.URL;

import com.qjk.qblog.http.impl.HttpGet;
import com.qjk.qblog.http.impl.HttpPost;
import com.qjk.qblog.http.impl.HttpPut;

public class CURL {

	public static IGet get(URL url) throws Throwable{
		
		if("http".equals(url.getProtocol())){
			return new HttpGet(url);
		}
		
		if("https".equals(url.getProtocol())){
			return new HttpGet(url);
		}
		
		throw new Exception("不支持的协议 "+url.getProtocol());
	}
	
	public static IPost post(URL url) throws Throwable{
		
		if("http".equals(url.getProtocol())){
			return new HttpPost(url);
		}
		
		if("https".equals(url.getProtocol())){
			return new HttpPost(url);
		}
		
		throw new Exception("不支持的协议 "+url.getProtocol());
	}
	
	public static IPut put(URL url) throws Throwable{
		
		if("http".equals(url.getProtocol())){
			return new HttpPut(url);
		}
		
		if("https".equals(url.getProtocol())){
			return new HttpPut(url);
		}
		
		throw new Exception("不支持的协议 "+url.getProtocol());
	}
}
