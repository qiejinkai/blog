package com.qjk.qblog.util;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Json������ 
 * Ĭ��ʹ��GSON
 * @author qiejinkai
 *
 */

public final class JsonUtil {

	private JsonUtil() {
		
	}
	
	public static String toJson(Object obj){
		
		Gson gson= new GsonBuilder().create();
		
		return gson.toJson(obj);
	}

	@SuppressWarnings("unchecked")
	public static <T> T fromJson(String json,T t){
		
		Gson gson= new GsonBuilder().create();
		
		return (T)gson.fromJson(json, t.getClass());
	}
	@SuppressWarnings("unchecked")
	
	public static HashMap<String, Object> fromJson(String json){
		
		Gson gson= new GsonBuilder().create();
				
		return gson.fromJson(json, new HashMap<String, Object>().getClass());
	}
	
	
	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("123", "123123");
		map.put("asd", "asdfa");
		System.out.println(JsonUtil.toJson(map));
		String string = "{'123':'123123','asd':'asdfa'}";
		
		HashMap<String, Object> map2 =JsonUtil.fromJson(string);
		
		System.out.println(Value.stringValueForKey(map2, "123", ""));
		
	}
	
	
	
}
