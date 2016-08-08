package com.qjk.qblog.util;

import com.qjk.qblog.data.Menu;
import com.qjk.qblog.data.Setting;
import com.qjk.qblog.data.User;

public final class RedisUtil {
	public static String USER_PREFIX = "user:id:";
	public static String SETTING_PREFIX = "setting:name:";
	public static String MENU_PREFIX = "menu:name:";

	private RedisUtil() {

	}

	public static String getKey(String name, Object id) {

		String key = "";

		if (name.equals(User.class.getSimpleName().toLowerCase())) {
			key = USER_PREFIX + id;
		}

		if (name.equals(Setting.class.getSimpleName().toLowerCase())) {
			key = SETTING_PREFIX + id;
		}
		
		if (name.equals(Menu.class.getSimpleName().toLowerCase())) {
			key = MENU_PREFIX + id;
		}

		return key;
	}
	
	public static void main(String[] args) {
		System.out.println(User.class.getSimpleName().toLowerCase());
	}

}
