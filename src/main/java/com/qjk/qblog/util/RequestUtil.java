package com.qjk.qblog.util;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.qjk.qblog.data.User;

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

	public final static boolean isMobile(HttpServletRequest request) {
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

	public final static String getRealIpAddress(HttpServletRequest request)
			throws IOException {
		// 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址

		String ip = request.getHeader("X-Forwarded-For");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		} else if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = (String) ips[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		return ip != null && ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}

	public static boolean isUserLogin(HttpServletRequest request) {

		if (request != null) {
			Object object = request.getSession().getAttribute("user");

			if (object != null) {
				try {
					User user = (User) object;
					user.getAccount();
					return true;
				} catch (Exception e) {
					return false;
				}

			}

		}

		return false;
	}
}
