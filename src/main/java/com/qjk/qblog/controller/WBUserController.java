package com.qjk.qblog.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qjk.qblog.data.User;
import com.qjk.qblog.data.WBUser;
import com.qjk.qblog.service.ISettingService;
import com.qjk.qblog.service.IWBUserService;
import com.qjk.qblog.util.CacheManagerUtil;
import com.qjk.qblog.util.JsonUtil;
import com.qjk.qblog.util.RequestUtil;
import com.qjk.qblog.util.Value;
import com.qjk.qblog.util.WBHelper;

@Controller
@RequestMapping("/wbuser")
public class WBUserController {

	Logger logger = Logger.getLogger(WBUserController.class);

	@Resource
	IWBUserService WBUserService;
	@Resource
	ISettingService settingService;
	
	@Resource
	CacheManagerUtil cacheManagerUtil;

	WBHelper WBHelper;

	@RequestMapping(value = { "/test", "/test/" }, method = RequestMethod.GET)
	public String test(){
		return "weixin";
	}

	@RequestMapping(value = { "/login", "/login/" }, method = RequestMethod.GET)
	public String login(String r, HttpServletRequest request) {
		
		r = Value.isEmpty(r)?"/":r;
		if (RequestUtil.isUserLogin(request)) {
			logger.info("用户已登录");
			return "redirect:" + r;
		}

		String api_code;
		try {
			api_code = getWBHelper().getCodeUrl(URLEncoder.encode(r, "utf-8"),RequestUtil.isMobile(request));
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "redirect:" + r;
		}

		return "redirect:" + api_code;
	}

	private WBHelper getWBHelper() {

		if (WBHelper == null) {
			synchronized (WBUserController.class) {

				if (WBHelper == null) {
					Map<String, String> wb = settingService
							.getOptionsByName("weibo");
					WBHelper = new WBHelper(wb);
				}
			}
		}

		return WBHelper;

	}

	@RequestMapping(value = { "/callback", "/callback/" }, method = RequestMethod.GET)
	public String callback(String state,String code,
			HttpServletRequest request) {

		String r = "/";
		try {
			r = Value.isEmpty(state)?"/":URLDecoder.decode(state, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			r="/";
		}
		
		if (Value.isEmpty(code)) {
			logger.info("用户取消授权");
			return "redirect:"+r;
		}
		
//		Object cache= cacheManagerUtil.getOauthCodeCache("wx_public_code:"+code);
//
//		logger.info("WBUserController cache" +cache);
//		if(!Value.isEmpty(cache)){
//
//			logger.info("code已经使用");
//			return "redirect:"+r;
//		}
//		cacheManagerUtil.putOauthCodeCache("wx_public_code:"+code, code);
		
		WBHelper WBHelper = getWBHelper();
		try {
			// 获取token
			String result = WBHelper.getToken(code);

			Map<String, Object> token = JsonUtil.fromJson(result);

			String access_token = Value.stringValueForKey(token,
					"access_token", "");
			long expires_in = Value.longValueForKey(token, "expires_in", 0);
			String refresh_token = Value.stringValueForKey(token,
					"refresh_token", "");
			String openId = Value.stringValueForKey(token, "uid", "");

			if (Value.isEmpty(access_token)) {
				throw new Exception("微博 获取token失败 token为空");
			}
			
			if (Value.isEmpty(openId)) {
				throw new Exception("微博 获取openId失败 openId为空");
			}

			// 获取userInfo
			String infoString = WBHelper.getUserInfo(openId, access_token);

			if (Value.isEmpty(openId)) {
				throw new Exception("微博 获取userInfo失败 userInfo为空");
			}
			Map<String, Object> info = JsonUtil.fromJson(infoString);
			logger.info("userinfo : "+infoString);
			String nick = Value.stringValueForKey(info, "name", "");
			String logo = Value.stringValueForKey(info, "profile_image_url", "");
			String location = Value.stringValueForKey(info, "location", "");
			String gender = Value.stringValueForKey(info, "gender","");

			WBUser user = new WBUser();
			user.setAccess_token(access_token);
			user.setOpenid(openId);
			user.setExpires_in(expires_in);
			user.setNick(nick);
			user.setLogo(logo);
			user.setLocation(location);
			user.setRefresh_token(refresh_token);
			user.setGender(gender);
			// 创建用户
			WBUserService.join(user);

			// 登陆
			User u = WBUserService.login(openId,
					RequestUtil.getRealIpAddress(request));

			request.getSession().setAttribute("user", u);

		} catch (Throwable e) {
			logger.error(e);
		}

		return "redirect:"+r;

	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String r = "/article/detail/3";
		String en= URLEncoder.encode(r, "utf-8");
		String de= URLDecoder.decode(en, "utf-8");
		System.out.println(en);
		System.out.println(de);
	}

}
