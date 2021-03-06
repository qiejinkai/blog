package com.qjk.qblog.util;

import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.log4j.Logger;

import com.qjk.qblog.http.CURL;

public final class WXLoginHelper {
	
	Logger logger = Logger.getLogger(WXLoginHelper.class);

	private Map<String, String> wx;

	public WXLoginHelper(Map<String, String> wx) {
		super();
		this.wx = wx;
	}

	public String getCodeUrl(String state,boolean isWeixinClient) throws Exception {

		if (wx == null) {
			throw new Exception("微信授权登录参数尚未设置");
		}

		String api_code = isWeixinClient?wx.get("api_code"):wx.get("api_code");
		String appId = wx.get("appid");
		String redirect_uri = wx.get("redirect_uri");
		String scope = wx.get("scope");

		if (Value.isEmpty(api_code) || Value.isEmpty(appId)
				|| Value.isEmpty(redirect_uri)) {
			throw new Exception("微信授权登录参数尚未设置");
		}

		api_code = api_code.replaceAll("\\{appid\\}", appId);
		api_code = api_code.replaceAll("\\{state\\}", state);
		api_code = api_code.replaceAll("\\{redirect_uri\\}", URLEncoder.encode(redirect_uri, "utf-8"));
		api_code = api_code.replaceAll("\\{scope\\}",
				Value.isEmpty(scope) ? "snsapi_userinfo" : scope);

//		logger.info("api_code : "+api_code);
		return api_code;

	}

	public String getToken(String code) throws Exception {

		if (wx == null) {
			throw new Exception("微信授权登录参数尚未设置");
		}

		String api_token = wx.get("api_token");
		String appId = wx.get("appid");
		String appkey = wx.get("appkey");
		String redirect_uri = wx.get("redirect_uri");

		if (Value.isEmpty(api_token) || Value.isEmpty(appId)
				|| Value.isEmpty(appkey) || Value.isEmpty(redirect_uri)) {
			throw new Exception("未配置微信授权登录参数");
		}
		api_token = api_token.replaceAll("\\{appid\\}", appId);
		api_token = api_token.replaceAll("\\{appkey\\}", appkey);
		api_token = api_token.replaceAll("\\{code\\}", code);

		try {
//			logger.info("api_token : "+api_token);
			String result = CURL.get(new URL(api_token)).exec();

//			logger.info("api_token result : "+result);
			validateResult(result);

			return result;

		} catch (Throwable e) {
			throw new Exception("微信授权登录获取token失败 : "+e.getMessage() +"   "+api_token, e);
		}

	}

	public String getUserInfo(String openId, String token) throws Exception {
		if (wx == null) {
			throw new Exception("微信授权登录参数尚未设置");
		}

		String api_userInfo = wx.get("api_userInfo");
		String appId = wx.get("appid");

		if (Value.isEmpty(api_userInfo) || Value.isEmpty(appId)) {
			throw new Exception("未配置微信授权登录参数");
		}
		api_userInfo = api_userInfo.replaceAll("\\{token\\}", token);
		api_userInfo = api_userInfo.replaceAll("\\{appid\\}", appId);
		api_userInfo = api_userInfo.replaceAll("\\{openId\\}", openId);

		try {

//			logger.info("api_userInfo : "+api_userInfo);
			String result = CURL.get(new URL(api_userInfo)).exec();
//			logger.info("api_userInfo result : "+result);
			validateResult(result);
			return result;

		} catch (Throwable e) {
			throw new Exception("微信授权登录 获取userInfo失败 : " + e.getMessage(), e);
		}
	}

	private void validateResult(String result) throws Exception {

		if (Value.isEmpty(result)) {
			throw new Exception("返回结果为空");
		}

		Object object = JsonUtil.fromJson(result);

		if (object == null) {

			throw new Exception("返回结果为空");
		}

		int errorcode = Value.intValueForKey(object, "errcode", 0);
		if (errorcode != 0) {
			throw new Exception("错误码：" + errorcode + " ; 错误信息"
					+ Value.stringValueForKey(object, "errmsg", ""));
		}

	}

//	public static void main(String[] args) {
//		String a = "https://graph.wx.com/oauth2.0/authorize?response_type=code&client_id={appid}&redirect_uri={redirect_uri}&scope={scope}&state={state}&display={display}";
//
//		a = a.replaceAll("\\{appid\\}", "123123");
//		System.out.println(a);
//	}

}
