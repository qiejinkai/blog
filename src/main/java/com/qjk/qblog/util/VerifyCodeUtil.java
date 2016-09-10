package com.qjk.qblog.util;

import java.util.Date;
import java.util.Random;

public final class VerifyCodeUtil {
	
	public static String createAdminVerifyCode() {
		long now = new Date().getTime()/(1000*30);//10秒换一次
		String userOpenId = "ojJkXweIFYpKGzYg_edVxpz2u6AU";
		
		String code = DigestUtil.md5(userOpenId+now);
		
		if(!Value.isEmpty(code) && code.length()>6){
			code = code.substring(0,6);
		}
		
		return code;
	}

	
	public static String randomCode(int length){
		
		if(length == 0){
			return "";
		}
		StringBuilder sb= new StringBuilder(length);
		Random r = new Random();
		for (int i = 0; i < length; i++) {

			sb.append(r.nextInt(10));
		}
		
		return sb.toString();
	}public VerifyCodeUtil() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args) {
		System.out.println(randomCode(4));
	}
}
