package com.qjk.qblog.util;

import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.log4j.Logger;

import com.qjk.qblog.http.CURL;
import com.qjk.qblog.http.IGet;
import com.qjk.qblog.http.IPost;

/**
 * 微博授权
 * 
 * @author qiejinkai
 *
 */
public final class WBHelper {

	Logger logger = Logger.getLogger(WBHelper.class);

	private Map<String, String> wb;

	public WBHelper(Map<String, String> wb) {
		super();
		this.wb = wb;
	}

	public String getCodeUrl(String state, boolean isMobileClient)
			throws Exception {

		if (wb == null) {
			throw new Exception("微博参数尚未设置");
		}

		String api_code = isMobileClient ?wb.get("api_code_mobile"):wb.get("api_code");
		String appId = wb.get("appid");
		String redirect_uri = wb.get("redirect_uri");
		String scope = wb.get("scope");

		if (Value.isEmpty(api_code) || Value.isEmpty(appId)
				|| Value.isEmpty(redirect_uri)) {
			throw new Exception("微博参数尚未设置");
		}

		api_code = api_code.replaceAll("\\{appid\\}", appId);
		api_code = api_code.replaceAll("\\{state\\}", state);
		api_code = api_code.replaceAll("\\{redirect_uri\\}",
				URLEncoder.encode(redirect_uri, "utf-8"));
		api_code = api_code.replaceAll("\\{scope\\}",
				Value.isEmpty(scope) ? "snsapi_userinfo" : scope);

		// logger.info("api_code : "+api_code);
		return api_code;

	}

	public String getToken(String code) throws Exception {

		if (wb == null) {
			throw new Exception("微博参数尚未设置");
		}

		String api_token = wb.get("api_token");
		String appId = wb.get("appid");
		String appkey = wb.get("appkey");
		String redirect_uri = wb.get("redirect_uri");

		if (Value.isEmpty(api_token) || Value.isEmpty(appId)
				|| Value.isEmpty(appkey) || Value.isEmpty(redirect_uri)) {
			throw new Exception("未配置微博参数");
		}

		try {
			IPost post = CURL.post(new URL(api_token));
			post.addValue("client_id", appId);
			post.addValue("client_secret", appkey);
			post.addValue("grant_type", "authorization_code");
			post.addValue("redirect_uri", redirect_uri);
			post.addValue("code", code);
			String result = post.exec();

			// logger.info("api_token result : "+result);
			validateResult(result);

			return result;

		} catch (Throwable e) {
			throw new Exception("微博获取token失败 : " + e.getMessage() + "   "
					+ api_token, e);
		}

	}

	public String getUserId(String token) throws Exception {
		if (wb == null) {
			throw new Exception("微博参数尚未设置");
		}

		String api_userId = wb.get("api_userId");

		if (Value.isEmpty(api_userId) ) {
			throw new Exception("未配置微博参数");
		}

		try {

			IPost post = CURL.post(new URL(api_userId));
			post.addValue("access_token", token);
			String result = post.exec();

			validateResult(result);
			
			return result;
		} catch (Throwable e) {
			throw new Exception("微博 获取userInfo失败 : " + e.getMessage(), e);
		}
	}

	public String getUserInfo(String openId, String token) throws Exception {
		if (wb == null) {
			throw new Exception("微博参数尚未设置");
		}

		String api_userInfo = wb.get("api_userInfo");
		api_userInfo = api_userInfo.replaceAll("\\{access_token\\}", token);
		api_userInfo = api_userInfo.replaceAll("\\{uid\\}", openId);
		
		if (Value.isEmpty(api_userInfo) ) {
			throw new Exception("未配置微博参数");
		}

		try {

			IGet get = CURL.get(new URL(api_userInfo));
				
			String result = get.exec();

			validateResult(result);
			return result;

		} catch (Throwable e) {
			throw new Exception("微博 获取userInfo失败 : " + e.getMessage(), e);
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

		int errorcode = Value.intValueForKey(object, "error_code", 0);
		if (errorcode != 0) {
			throw new Exception("错误码：" + errorcode + " ; 错误信息"
					+ Value.stringValueForKey(object, "error_description", ""));
		}

	}

	// public static void main(String[] args) {
	// String a =
	// "https://graph.wb.com/oauth2.0/authorize?response_type=code&client_id={appid}&redirect_uri={redirect_uri}&scope={scope}&state={state}&display={display}";
	//
	// a = a.replaceAll("\\{appid\\}", "123123");
	// System.out.println(a);
	// }

}
