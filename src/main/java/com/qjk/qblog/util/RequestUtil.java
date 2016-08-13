package com.qjk.qblog.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public final class RequestUtil {

	public final static String IE9 = "MSIE 9.0";
	public final static String IE8 = "MSIE 8.0";
	public final static String IE7 = "MSIE 7.0";
	public final static String IE6 = "MSIE 6.0";
	public final static String MAXTHON = "Maxthon";
	public final static String QQ = "QQBrowser";
	public final static String GREEN = "GreenBrowser";
	public final static String SE360 = "360SE";
	public final static String FIREFOX = "Firefox";
	public final static String OPERA = "Opera";
	public final static String CHROME = "Chrome";
	public final static String SAFARI = "Safari";
	public final static String MOBILE = "Mobile";
	public final static String WEIXIN = "MicroMessenger";
	public final static String ANDROID = "Android";
	public final static String WAP = "WAP";
	public final static String IPAD = "iPad";
	public final static String IPHONE = "iPhone";
	public final static String IPOD = "iPod";

	public static boolean isMobile(HttpServletRequest request) {
		if (request == null) {
			return false;
		}
		String userAgent = request.getHeader("User-Agent");

		if (Value.isEmpty(userAgent)) {
			return false;
		}

		if (regex(MOBILE, userAgent)) {
			return true;
		}
		if (regex(WEIXIN, userAgent)) {
			return true;
		}
		if (regex(ANDROID, userAgent)) {
			return true;
		}
		if (regex(WAP, userAgent)) {
			return true;
		}
		if (regex(IPAD, userAgent)) {
			return true;
		}
		if (regex(IPHONE, userAgent)) {
			return true;
		}
		if (regex(IPOD, userAgent)) {
			return true;
		}

		return false;
	}

	private static boolean regex(String regex, String str) {
		Pattern p = Pattern.compile(regex, Pattern.MULTILINE);
		Matcher m = p.matcher(str);
		return m.find();
	}
}
