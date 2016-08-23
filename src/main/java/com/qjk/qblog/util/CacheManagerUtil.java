package com.qjk.qblog.util;

import javax.annotation.Resource;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public final class CacheManagerUtil {

	private static final String CAHCE_SYSTEM = "systemConfig";
	private static final String CACHE_LOGIN = "loginConfig";
	private static final String CACHE_OAUHT2_CODE = "oauthCodeConfig";

	@Resource
	private CacheManager cacheManager;

	private CacheManagerUtil() {

	}

	public Cache getCache() {

		if (cacheManager != null) {

			Cache cache = cacheManager.getCache("default");
			return cache;
		}
		return null;
	}

	public Cache getCache(String cacheName) {
		if (cacheManager != null) {
			if (Value.isEmpty(cacheName)) {

				Cache cache = cacheManager.getCache("default");
				return cache;
			}

			Cache cache = cacheManager.getCache(cacheName);
			return cache;
		}

		return null;

	}

	public Object getSystemCache(String key) {

		Cache cache = getCache(CAHCE_SYSTEM);
		if (cache != null) {
			
			if(cache.get(key) != null){
				return cache.get(key).get();
			}
		}

		return null;
	}

	public boolean putSystemCache(String key, Object obj) {
		Cache cache = getCache(CAHCE_SYSTEM);
		if (cache != null) {
			cache.put(key, obj);
			return true;
		}

		return false;
	}
	

	public Object getOauthCodeCache(String key) {

		Cache cache = getCache(CACHE_OAUHT2_CODE);
		if (cache != null) {
			
			if(cache.get(key) != null){
				return cache.get(key).get();
			}
		}

		return null;
	}

	public boolean putOauthCodeCache(String key, Object obj) {
		Cache cache = getCache(CACHE_OAUHT2_CODE);
		if (cache != null) {
			cache.put(key, obj);
			return true;
		}

		return false;
	}

	public Object getLoginCache(String key) {

		Cache cache = getCache(CACHE_LOGIN);
		if (cache != null) {
			
			if(cache.get(key) != null){
				return cache.get(key).get();
			}
		}

		return null;
	}

	public boolean putLoginCache(String key, Object obj) {
		Cache cache = getCache(CACHE_LOGIN);
		if (cache != null) {
			cache.put(key, obj);
			return true;
		}

		return false;
	}
}
