package com.qjk.qblog.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public final class Value {

	private Value() {

	}

	public static String stringValue(Object value,String defaultValue){
		
		if(value != null){
			if(value instanceof String){
				return (String) value;
			}
			return value.toString();
		}
		
		return defaultValue;
	}
	
	public static boolean booleanValue(Object value,boolean defaultValue){
		if(value != null){
			if(value instanceof Boolean){
				return (Boolean) value;
			}
			else if(value instanceof String){
				return "true".equals(value);
			}
			else if(value instanceof Number){
				return ((Number) value).doubleValue() != 0.0;
			}
			return true;
		}
		return defaultValue;
	}
	
	public static Boolean booleanValue(Object value,Boolean defaultValue){
		if(value != null){
			if(value instanceof Boolean){
				return (Boolean) value;
			}
			else if(value instanceof String){
				
				if("".equals(value)) {
					return defaultValue;
				}
				
				return "true".equals(value);
			}
			else if(value instanceof Number){
				return ((Number) value).doubleValue() != 0.0;
			}
			return true;
		}
		return defaultValue;
	}
	
	public static int intValue(Object value,int defaultValue){
		if(value != null){
			if(value instanceof Number){
				return ((Number) value).intValue();
			}
			else if(value instanceof String){
				try{
					
					if(((String) value).startsWith("0x")){
						return Integer.valueOf(((String)value).substring(2), 16);
					}
					
					return Integer.valueOf((String)value);
				}
				catch(Throwable ex){
					return defaultValue;
				}
			}
			else {
				try{
					return Integer.valueOf(value.toString());
				}
				catch(Throwable ex){
					return defaultValue;
				}
			}
		}
		return defaultValue;
	}
	
	public static Integer intValue(Object value,Integer defaultValue){
		
		if(value != null){
			if(value instanceof Number){
				return ((Number) value).intValue();
			}
			else if(value instanceof String){
				try{
					
					if(((String) value).startsWith("0x")){
						return Integer.valueOf(((String)value).substring(2), 16);
					}
					else if(((String) value).equals("")) {
						return defaultValue;
					}
					
					return Integer.valueOf((String)value);
				}
				catch(Throwable ex){
					return defaultValue;
				}
			}
			else {
				try{
					return Integer.valueOf(value.toString());
				}
				catch(Throwable ex){
					return defaultValue;
				}
			}
		}
		return defaultValue;
	}
	
	public static long longValue(Object value,long defaultValue){
		if(value != null){
			if(value instanceof Number){
				return ((Number) value).longValue();
			}
			else if(value instanceof String){
				try{
					
					if(((String) value).startsWith("0x")){
						return Long.valueOf(((String)value).substring(2), 16);
					}
					
					return Long.valueOf((String)value);
				}
				catch(Throwable ex){
					try {
						return (long) (double) Double.valueOf((String)value);
					}
					catch(Throwable exx) {
						return defaultValue;
					}
				}
			}
			else {
				
				String v = value.toString();
				
				try{
					
					if(v.startsWith("0x")){
						return Long.valueOf(v.substring(2), 16);
					}
					
					return Long.valueOf(v);
				}
				catch(Throwable ex){
					try {
						return (long) (double) Double.valueOf(v);
					}
					catch(Throwable exx) {
						return defaultValue;
					}
				}
			}
		}
		return defaultValue;
	}
	
	public static Long longValue(Object value,Long defaultValue){
		if(value != null){
			if(value instanceof Number){
				return ((Number) value).longValue();
			}
			else if(value instanceof String){
				try{
					
					if(((String) value).startsWith("0x")){
						return Long.valueOf(((String)value).substring(2), 16);
					}
					else if(((String) value).equals("")) {
						return defaultValue;
					}
					
					return Long.valueOf((String)value);
				}
				catch(Throwable ex){
					return defaultValue;
				}
			}
			else {
				try{
					return Long.valueOf(value.toString());
				}
				catch(Throwable ex){
					return defaultValue;
				}
			}
		}
		return defaultValue;
	}
	
	public static double doubleValue(Object value,double defaultValue){
		if(value != null){
			if(value instanceof Number){
				return ((Number) value).doubleValue();
			}
			else if(value instanceof String){
				try{
					return Double.valueOf((String)value);
				}
				catch(Throwable ex){
					return defaultValue;
				}
			}
			else {
				try{
					return Double.valueOf(value.toString());
				}
				catch(Throwable ex){
					return defaultValue;
				}
			}
		}
		return defaultValue;
	}
	
	public static Double doubleValue(Object value,Double defaultValue){
		if(value != null){
			if(value instanceof Number){
				return ((Number) value).doubleValue();
			}
			else if(value instanceof String){
				try{
					
					if(((String) value).equals("")) {
						return defaultValue;
					}
					
					return Double.valueOf((String)value);
				}
				catch(Throwable ex){
					return defaultValue;
				}
			}
			else {
				try{
					return Double.valueOf(value.toString());
				}
				catch(Throwable ex){
					return defaultValue;
				}
			}
		}
		return defaultValue;
	}
	
	public static float floatValue(Object value,float defaultValue){
		if(value != null){
			if(value instanceof Number){
				return ((Number) value).floatValue();
			}
			else if(value instanceof String){
				try{
					return Float.valueOf((String)value);
				}
				catch(Throwable ex){
					return defaultValue;
				}
			}
			else {
				try{
					return Float.valueOf(value.toString());
				}
				catch(Throwable ex){
					return defaultValue;
				}
			}
		}
		return defaultValue;
	}
	
	public static Float floatValue(Object value,Float defaultValue){
		if(value != null){
			if(value instanceof Number){
				return ((Number) value).floatValue();
			}
			else if(value instanceof String){
				try{
					
					if(((String) value).equals("")) {
						return defaultValue;
					}
					
					return Float.valueOf((String)value);
				}
				catch(Throwable ex){
					return defaultValue;
				}
			}
			else {
				try{
					return Float.valueOf(value.toString());
				}
				catch(Throwable ex){
					return defaultValue;
				}
			}
		}
		return defaultValue;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,Object> mapValue(Object value){
		if(value != null){
			if(value instanceof Map<?,?>){
				return (Map<String,Object>) value;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Object> listValue(Object value){
		if(value != null){
			if(value instanceof List<?>){
				return (List<Object>) value;
			}
		}
		return null;
	}
	
	public static Object objectValueForKey(Object object,String key,Object defaultValue){
		if(object != null && key != null){
			
			Object v = null;
			
			if(object instanceof Map){
				v = ((Map<?,?>)object).get(key);
			}
			else if(object instanceof List){
				int c = ((List<?>)object).size();
				
				if("@last".equals(key)){
					if(c > 0){
						v =  ((List<?>)object).get(c - 1);
					}
				}
				else if("@first".equals(key)){
					if(c > 0){
						v =  ((List<?>)object).get(0);
					}
				}
				else if(key.startsWith("@")){
					String[] kv = key.split(":");
					String isv = kv.length > 1 ? kv[1]:"";
					String isn = kv.length > 0 ? kv[0].substring(1):"";
					
					for(Object item : (List<?>)object){
						String n = Value.stringValueForKey(item,isn, null);
						if(isv.equals(n)){
							v = item;
							break;
						}
					}
				}
				else {
					int i = Integer.valueOf(key);
					if(i >=0 && i < c){
						v =  ((List<?>)object).get(i);
					}
				}
			}
			else if(object.getClass().isArray()){
				
				int c = Array.getLength(object);
				if("@last".equals(key)){
					if(c > 0){
						v =  Array.get(object, c -1);
					}
				}
				else if("@first".equals(key)){
					if(c > 0){
						v =  Array.get(object, 0);
					}
				}
				else if(key.startsWith("@")){
					String[] kv = key.split(":");
					String isv = kv.length > 1 ? kv[1]:"";
					String isn = kv.length > 0 ? kv[0].substring(1):"";
					
					for(int i=0;i<c;i++){
						Object item = Array.get(object, i);
						String n = Value.stringValueForKey(item,isn, null);
						if(isv.equals(n)){
							v = item;
							break;
						}
					}
				}
				else {
					int i = Integer.valueOf(key);
					if(i >=0 && i < c){
						v =  Array.get(object, i);
					}
				}
			}
			else {
				
				Class<?> clazz = object.getClass();
				
				try {
					Field field = clazz.getField(key);
					v = field.get(object);
				} catch (Throwable e) {
					
					if(key.length() > 1){
						try {
							Method method = clazz.getMethod("get"+key.substring(0,1).toUpperCase()+key.substring(1));
							v = method.invoke(object);
						} catch (Throwable ex) {
							
						}
					}
					
				}
			}
			
			if(v != null){
				return v;
			}
		}
		return defaultValue;
	}
	
	public static Object objectValueForKeys(Object object,String[] keys,int index,Object defaultValue){ 
		if(object != null && keys != null && index < keys.length ){
			
			String key = keys[index];
			
			if("".equals(key)){
				
				StringBuilder sb = new StringBuilder();
				
				int count = 0;
				
				while(index + 1 < keys.length) {
					
					key = keys[index + 1];
					
					if("".equals(key)){
						break;
					}
					
					if(count != 0){
						sb.append(".");
					}
					
					sb.append(key);
					
					index ++;
					count ++;
				}
				
				key = sb.toString();
				
			}
			
			Object v = objectValueForKey(object, key , null);
			
			if(v != null){
				
				if(index +1 < keys.length){
					return objectValueForKeys(v,keys,index +1, defaultValue);
				}
				
				return v;
			}
		}
		return defaultValue;
	}
	
	public static Object objectValueForKeys(Object object,String[] keys,Object defaultValue){ 
		return objectValueForKeys(object,keys,0,defaultValue);
	}
	
	public static Object objectValueForKeys(Object object,String keys,Object defaultValue){ 
		return objectValueForKeys(object,keys.split("\\."),0,defaultValue);
	}
	
	public static String stringValueForKey(Object object,String key,String defaultValue){
		return stringValue(objectValueForKey(object,key,null),defaultValue);
	}
	
	public static int intValueForKey(Object object,String key,int defaultValue){
		return intValue(objectValueForKey(object,key,null),defaultValue);
	}
	
	public static long longValueForKey(Object object,String key,long defaultValue){
		return longValue(objectValueForKey(object,key,null),defaultValue);
	}
	
	public static boolean booleanValueForKey(Object object,String key,boolean defaultValue){
		return booleanValue(objectValueForKey(object,key,null),defaultValue);
	}
	
	public static double doubleValueForKey(Object object,String key,double defaultValue){
		return doubleValue(objectValueForKey(object,key,null),defaultValue);
	}
	
	
	@SuppressWarnings("unchecked")
	public static void setObjectValueForKey(Object object,String key,Object value){
		if(object != null){
			if(object instanceof Map){
				if(value == null){
					((Map<String,Object>) object).remove(key);
				}
				else {
					((Map<String,Object>) object).put(key, value);
				}
			}
		}
	}
	
	public static class DMap extends HashMap<String,Object> {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -3153480453092950568L;

		public DMap s(String key,Object value){
			put(key,value);
			return this;
		}
		
	}
	
	public static class DList extends ArrayList<Object> {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -7352770023930661571L;

		public DList a(Object value){
			add(value);
			return this;
		}
	}
	
	public static class DSet<T extends Object> extends HashSet<T> {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -7352770023930661571L;

		public DSet<T> a(T value){
			add(value);
			return this;
		}
	}
	
	public static DMap map(){
		return new DMap();
	}
	
	public static DList list(){
		return new DList(); 
	}
	
	public static <T extends Object> DSet<T> set(Class<T> claszz){
		return new DSet<T>(); 
	}
	
	public static boolean isEmpty(Object value){
		
		if(value == null){
			return true;
		}
		
		if(value instanceof String){
			return "".equals(value);
		}
		
		return false;
	}
	

}
