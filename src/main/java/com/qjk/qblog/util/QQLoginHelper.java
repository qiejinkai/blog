package com.qjk.qblog.util;

import java.net.URL;
import java.util.Map;

import com.qjk.qblog.http.CURL;

public final class QQLoginHelper {

	private Map<String, String> qq;

	public QQLoginHelper(Map<String, String> qq) {
		super();
		this.qq = qq;
	}

	public String getCodeUrl(String state, boolean isMoblie) throws Exception {

		if (qq == null) {
			throw new Exception("QQ互联参数尚未设置");
		}

		String api_code = qq.get("api_code");
		String appId = qq.get("appid");
		String redirect_uri = qq.get("redirect_uri");
		String scope = qq.get("scope");

		if (Value.isEmpty(api_code) || Value.isEmpty(appId)
				|| Value.isEmpty(redirect_uri)) {
			throw new Exception("QQ互联参数尚未设置");
		}

		api_code = api_code.replaceAll("\\{appid\\}", appId);
		api_code = api_code.replaceAll("\\{state\\}", state);
		api_code = api_code.replaceAll("\\{redirect_uri\\}", redirect_uri);
		api_code = api_code.replaceAll("\\{scope\\}", Value.isEmpty(scope) ? "get_user_info"
				: scope);
		if (isMoblie) {
			api_code += "&amp;display=mobile";
		}
		return api_code;

	}

	public String getToken(String code, String state) throws Exception {

		if (qq == null) {
			throw new Exception("QQ互联参数尚未设置");
		}

		String api_token = qq.get("api_token");
		String appId = qq.get("appid");
		String appkey = qq.get("appkey");
		String redirect_uri = qq.get("redirect_uri");

		if (Value.isEmpty(api_token) || Value.isEmpty(appId)
				|| Value.isEmpty(appkey) || Value.isEmpty(redirect_uri)) {
			throw new Exception("未配置QQ互联参数");
		}
		api_token = api_token.replaceAll("\\{appid\\}", appId);
		api_token = api_token.replaceAll("\\{appkey\\}", appkey);
		api_token = api_token.replaceAll("\\{code\\}", code);
		api_token = api_token.replaceAll("\\{redirect_uri\\}", redirect_uri);
		api_token = api_token.replaceAll("\\{state\\}", state);

		try {
			String result = CURL.get(new URL(api_token)).exec();

			// TODO 解析 返回码

			return result;

		} catch (Throwable e) {
			throw new Exception("QQ互联 获取token失败 : ", e);
		}

	}

	public String getOpenId(String token) throws Exception {
		if (qq == null) {
			throw new Exception("QQ互联参数尚未设置");
		}

		String api_openId = qq.get("api_openId");

		if (Value.isEmpty(api_openId)) {
			throw new Exception("未配置QQ互联参数");
		}
		api_openId = api_openId.replaceAll("\\{token\\}", token);

		try {
			String result = CURL.get(new URL(api_openId)).exec();

			return result;

		} catch (Throwable e) {
			throw new Exception("QQ互联 获取openId失败 : ", e);
		}

	}

	public String getUserInfo(String openId, String token) throws Exception {
		if (qq == null) {
			throw new Exception("QQ互联参数尚未设置");
		}

		String api_userInfo = qq.get("api_userInfo");
		String appId = qq.get("appid");

		if (Value.isEmpty(api_userInfo) || Value.isEmpty(appId)) {
			throw new Exception("未配置QQ互联参数");
		}
		api_userInfo = api_userInfo.replaceAll("\\{token\\}", token);
		api_userInfo = api_userInfo.replaceAll("\\{appid\\}", appId);
		api_userInfo = api_userInfo.replaceAll("\\{openId\\}", openId);

		try {
			String result = CURL.get(new URL(api_userInfo)).exec();

			return result;

		} catch (Throwable e) {
			throw new Exception("QQ互联 获取userInfo失败 : ", e);
		}
	}
	
	public static void main(String[] args) {
		String a = "https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id={appid}&redirect_uri={redirect_uri}&scope={scope}&state={state}&display={display}";
	
		a = a.replaceAll("\\{appid\\}", "123123");
		System.out.println(a);
	}

}
