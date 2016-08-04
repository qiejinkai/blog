package com.qjk.qblog.util;

import com.qjk.qblog.data.User;

public final class RedisUtil {
	public static String USER_PREFIX = "user:";
	public static String PROJECT_PREFIX ="project:";
	public static String TRADE_PREFIX="trade:";
	
	private RedisUtil(){
		
	}
	
	public static String getKey(Class<?> c,long id){
		
		String key = "";
		
		if(c == User.class){
			key = USER_PREFIX+id;
		}
		
		return key;
	}
	
	
}
