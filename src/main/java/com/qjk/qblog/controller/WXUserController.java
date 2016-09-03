package com.qjk.qblog.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qjk.qblog.data.WXUser;
import com.qjk.qblog.data.User;
import com.qjk.qblog.service.IWXUserService;
import com.qjk.qblog.service.ISettingService;
import com.qjk.qblog.util.CacheManagerUtil;
import com.qjk.qblog.util.JsonUtil;
import com.qjk.qblog.util.WXLoginHelper;
import com.qjk.qblog.util.RequestUtil;
import com.qjk.qblog.util.Value;

@Controller
@RequestMapping("/wxuser")
public class WXUserController {

	Logger logger = Logger.getLogger(WXUserController.class);

	@Resource
	IWXUserService WXUserService;
	@Resource
	ISettingService settingService;
	
	@Resource
	CacheManagerUtil cacheManagerUtil;

	WXLoginHelper WXLoginHelper;

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
			api_code = getWXLoginHelper().getCodeUrl(URLEncoder.encode(r, "utf-8"),RequestUtil.isWeixinClient(request));
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "redirect:" + r;
		}

		return "redirect:" + api_code;
	}

	private WXLoginHelper getWXLoginHelper() {

		if (WXLoginHelper == null) {
			synchronized (WXUserController.class) {

				if (WXLoginHelper == null) {
					Map<String, String> WX = settingService
							.getOptionsByName("weixin");
					WXLoginHelper = new WXLoginHelper(WX);
				}
			}
		}

		return WXLoginHelper;

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
		
		Object cache= cacheManagerUtil.getOauthCodeCache("wx_public_code:"+code);

		logger.info("WXUserController cache" +cache);
		if(!Value.isEmpty(cache)){

			logger.info("code已经使用");
			return "redirect:"+r;
		}
		cacheManagerUtil.putOauthCodeCache("wx_public_code:"+code, code);
		
		WXLoginHelper WXLoginHelper = getWXLoginHelper();
		try {
			// 获取token
			String result = WXLoginHelper.getToken(code);

			Map<String, Object> token = JsonUtil.fromJson(result);

			String access_token = Value.stringValueForKey(token,
					"access_token", "");
			long expires_in = Value.longValueForKey(token, "expires_in", 0);
			String refresh_token = Value.stringValueForKey(token,
					"refresh_token", "");
			String openId = Value.stringValueForKey(token, "openid", "");

			if (Value.isEmpty(access_token)) {
				throw new Exception("微信 获取token失败 token为空");
			}
			
			if (Value.isEmpty(openId)) {
				throw new Exception("微信 获取openId失败 openId为空");
			}

			// 获取userInfo
			String infoString = WXLoginHelper.getUserInfo(openId, access_token);

			if (Value.isEmpty(openId)) {
				throw new Exception("微信 获取userInfo失败 userInfo为空");
			}
			Map<String, Object> info = JsonUtil.fromJson(infoString);
			logger.info("userinfo : "+infoString);
			String nick = Value.stringValueForKey(info, "nickname", "");
			String logo = Value.stringValueForKey(info, "headimgurl", "");
			String unionid = Value.stringValueForKey(info, "unionid", "");
			String sex = Value.stringValueForKey(info, "sex","");

			WXUser user = new WXUser();
			user.setAccess_token(access_token);
			user.setOpenid(openId);
			user.setExpires_in(expires_in);
			user.setNick(nick);
			user.setLogo(logo);
			user.setRefresh_token(refresh_token);
			user.setUnionid(unionid);
			user.setGender(sex);
			// 创建用户
			WXUserService.join(user);

			// 登陆
			User u = WXUserService.login(openId,
					RequestUtil.getRealIpAddress(request));

			request.getSession().setAttribute("user", u);

		} catch (Throwable e) {
			logger.error(e);
		}

		return "redirect:"+r;

	}

	@RequestMapping(value = { "/callback/test", "/callback/test/" }, method = RequestMethod.GET)
	@ResponseBody
	public Object callback_test(String state,String code,
			HttpServletRequest request) {
		List<Object> res = new ArrayList<Object>();
		if (Value.isEmpty(code)) {
			res.add("用户取消授权");
			return res;
		}
		String r = "/";
		try {
			r = Value.isEmpty(state)?"/":URLDecoder.decode(state, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			r="/";
		}

		WXLoginHelper WXLoginHelper = getWXLoginHelper();
		try {
			// 获取token
			String result = WXLoginHelper.getToken(code);

			Map<String, Object> token = JsonUtil.fromJson(result);

			String access_token = Value.stringValueForKey(token,
					"access_token", "");
			res.add("access_token : "+access_token);
			long expires_in = Value.longValueForKey(token, "expires_in", 0);
			res.add("expires_in : "+expires_in);
			String refresh_token = Value.stringValueForKey(token,
					"refresh_token", "");
			res.add("refresh_token : "+refresh_token);
			String openId = Value.stringValueForKey(token, "openid", "");
			res.add("openID : "+openId);
			if (Value.isEmpty(access_token)) {
				res.add("微信 获取token失败 token为空");
				return res;
			}
			
			if (Value.isEmpty(openId)) {
				res.add("微信 获取openId失败 openId为空");
				return res;
			}

			// 获取userInfo
			String infoString = WXLoginHelper.getUserInfo(openId, access_token);

			res.add("infoString : "+infoString);
			if (Value.isEmpty(openId)) {
				res.add("微信 获取userInfo失败 userInfo为空");
				return res;
			}
//			Map<String, Object> info = JsonUtil.fromJson(infoString);
//
//			String nick = Value.stringValueForKey(info, "nickname", "");
//			String logo = Value.stringValueForKey(info, "headimgurl", "");
//			String unionid = Value.stringValueForKey(info, "unionid", "");
//			String sex = Value.stringValueForKey(info, "sex","");
//
//			WXUser user = new WXUser();
//			user.setAccess_token(access_token);
//			user.setOpenid(openId);
//			user.setExpires_in(expires_in);
//			user.setNick(nick);
//			user.setLogo(logo);
//			user.setRefresh_token(refresh_token);
//			user.setUnionid(unionid);
//			user.setGender(sex);
//			// 创建用户
//			WXUserService.join(user);
//
//			// 登陆
//			User u = WXUserService.login(openId,
//					RequestUtil.getRealIpAddress(request));
//
//			request.getSession().setAttribute("user", u);

		} catch (Throwable e) {
			res.add(e.getMessage());
			return res;
		}

		return res;

	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String r = "/article/detail/3";
		String en= URLEncoder.encode(r, "utf-8");
		String de= URLDecoder.decode(en, "utf-8");
		System.out.println(en);
		System.out.println(de);
	}

}
