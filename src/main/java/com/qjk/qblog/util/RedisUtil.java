package com.qjk.qblog.util;

import com.qjk.qblog.data.Setting;
import com.qjk.qblog.data.User;

public final class RedisUtil {
	public static String USER_PREFIX = "user:id:";
	public static String SETTING_PREFIX = "setting:name:";

	private RedisUtil() {

	}

	public static String getKey(Class<?> c, Object id) {

		String key = "";

		if (c == User.class) {
			key = USER_PREFIX + id;
		}

		if (c == Setting.class) {
			key = SETTING_PREFIX + id;
		}

		return key;
	}

}
