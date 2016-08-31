package com.qjk.qblog.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qjk.qblog.data.QQUser;
import com.qjk.qblog.data.User;
import com.qjk.qblog.service.IQQUserService;
import com.qjk.qblog.service.ISettingService;
import com.qjk.qblog.util.JsonUtil;
import com.qjk.qblog.util.QQLoginHelper;
import com.qjk.qblog.util.RequestUtil;
import com.qjk.qblog.util.Value;

@Controller
@RequestMapping("/qquser")
public class QQUserController {

	Logger logger = Logger.getLogger(QQUserController.class);

	@Resource
	IQQUserService qqUserService;
	@Resource
	ISettingService settingService;

	QQLoginHelper qqLoginHelper;

	@RequestMapping(value = { "/login", "/login/" }, method = RequestMethod.GET)
	public String login(String r, HttpServletRequest request) {

		if (RequestUtil.isUserLogin(request)) {
			logger.info("用户已登录");
			return "redirect:" + r;
		}

		String api_code;
		try {
			api_code = getQQLoginHelper().getCodeUrl(r,
					RequestUtil.isMobile(request));
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "redirect:" + r;
		}

		return "redirect:" + api_code;
	}

	private QQLoginHelper getQQLoginHelper() {

		if (qqLoginHelper == null) {
			synchronized (QQUserController.class) {

				if (qqLoginHelper == null) {
					Map<String, String> qq = settingService
							.getOptionsByName("qq");
					qqLoginHelper = new QQLoginHelper(qq);
				}
			}
		}

		return qqLoginHelper;

	}

	@RequestMapping(value = { "/callback", "/callback/" }, method = RequestMethod.GET)
	public String callback(String state, String usercancel, String code,
			HttpServletRequest request) {

		if (!Value.isEmpty(usercancel) && "1".equals(usercancel)) {// 用户取消授权

			logger.info("用户取消授权");
			return "redirect:/";
		}

		if (Value.isEmpty(code)) {
			logger.info("未找到授权code");
			return "redirect:/";
		}

		QQLoginHelper qqLoginHelper = getQQLoginHelper();
		try {
			//获取token
			String result = qqLoginHelper.getToken(code, state);

			Map<String, Object> token = JsonUtil.fromJson(result);

			String access_token = Value.stringValueForKey(token,
					"access_token", "");
			long expires_in = Value.longValueForKey(token, "expires_in", 0);
			String refresh_token = Value.stringValueForKey(token,
					"refresh_token", "");

			if (Value.isEmpty(access_token)) {
				throw new Exception("QQ互联 获取token失败 token为空");
			}
			//获取openId
			Map<String, Object> open = JsonUtil.fromJson(qqLoginHelper
					.getOpenId(access_token));
			String openId = Value.stringValueForKey(open, "openid", "");

			if (Value.isEmpty(openId)) {
				throw new Exception("QQ互联 获取openId失败 openId为空");
			}
			
			//获取userInfo
			String infoString = qqLoginHelper.getUserInfo(openId, access_token);

			if (Value.isEmpty(openId)) {
				throw new Exception("QQ互联 获取userInfo失败 userInfo为空");
			}
			Map<String, Object> info = JsonUtil.fromJson(infoString);

			logger.info("userinfo : "+infoString);
			String nick = Value.stringValueForKey(info, "nickname", "");
			String logo = Value.stringValueForKey(info, "figureurl_qq_1", "");

			QQUser user = new QQUser();
			user.setAccess_token(access_token);
			user.setOpenid(openId);
			user.setExpires_in(expires_in);
			user.setNick(nick);
			user.setLogo(logo);
			user.setRefresh_token(refresh_token);
			//创建用户
			qqUserService.join(user);
			
			//登陆
			User u = qqUserService.login(openId,
					RequestUtil.getRealIpAddress(request));

			request.getSession().setAttribute("user", u);

		} catch (Throwable e) {
			logger.error(e.getMessage());
			return "redirect:/";
		}

		return "redirect:/";

	}

}
