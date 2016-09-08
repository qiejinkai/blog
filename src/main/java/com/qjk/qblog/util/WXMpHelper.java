package com.qjk.qblog.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.qjk.qblog.data.Setting;
import com.qjk.qblog.data.WxmpMessage;
import com.qjk.qblog.http.CURL;
import com.qjk.qblog.http.IGet;

public final class WXMpHelper {

	/**
	 * 消息类型
	 * 
	 * @author qiejinkai
	 *
	 */
	public enum MessageType {
		TEXT("text", "文本消息"), IMAGE("image", "图片消息"), VOICE("voice", "语音消息"), VIDEO(
				"video", "视频消息"), LOCATION("location", "地理位置消息"), LINK("link",
				"链接消息"), EVENT("event", "事件消息");

		private String value;
		private String summary;

		private MessageType() {
			// TODO Auto-generated constructor stub
		}

		private MessageType(String value, String summary) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}

		public String getSummary() {
			return this.summary;
		}
	}

	/**
	 * 事件类型
	 * 
	 * @author qiejinkai
	 *
	 */
	public enum EventType {
		subscribe("subscribe", "关注事件"), unsubscribe("unsubscribe", "取消关注事件"), SCAN(
				"SCAN", "已关注扫描二维码事件"), LOCATION("LOCATION", "上报地理位置事件"), CLICK(
				"CLICK", "自定义菜单点击事件"), VIEW("VIEW", "点击菜单跳转链接事件");

		private String value;
		private String summary;

		private EventType() {

		}

		private EventType(String value, String summary) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}

		public String getSummary() {
			return this.summary;
		}
	}

	Logger logger = Logger.getLogger(WXLoginHelper.class);

	private Map<String, String> wxmp;

	public WXMpHelper(Map<String, String> wxmp) {
		super();
		this.wxmp = wxmp;
	}

	private void validateSetting(String... params) throws Exception {
		if (wxmp == null) {
			throw new Exception("微信公众号参数尚未设置");
		}

		if (params != null && params.length > 0) {
			Set<String> error = new HashSet<String>();
			Arrays.stream(params).forEach(param -> {
				if (!Value.isEmpty(param)) {
					String value = Value.stringValueForKey(wxmp, param, "");
					if (value.isEmpty()) {
						error.add("微信公众号" + param + "参数尚未设置");
					}
				}

			});

			if (!error.isEmpty()) {
				throw new Exception(error.iterator().next());
			}
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

	public boolean validateSign(String signature, String timestamp, String nonce)
			throws Exception {

		validateSetting("token");

		String token = Value.stringValueForKey(wxmp, "token", "");

		boolean result = false;

		if (Value.isEmpty(signature) || Value.isEmpty(nonce)
				|| Value.isEmpty(timestamp)) {

			return result;
		}

		String[] strs = { token, timestamp, nonce };

		// 字典排序
		Arrays.sort(strs);
		// 拼接字符串
		String string = strs[0] + strs[1] + strs[2];
		// sha1加密
		string = DigestUtil.sha1(string);

		result = Value.isEmpty(string) ? false : string
				.equalsIgnoreCase(signature) ? true : false;

		return result;
	}

	public String getAccessToken() throws Exception {

		validateSetting("access_token_api", "appid", "appkey");

		String result = "";

		String access_token_api = Value.stringValueForKey(wxmp,
				"access_token_api", "");
		String appid = Value.stringValueForKey(wxmp, "appid", "");
		String appkey = Value.stringValueForKey(wxmp, "appkey", "");

		access_token_api = access_token_api.replaceAll("\\{appid\\}", appid);
		access_token_api = access_token_api.replaceAll("\\{appkey\\}", appkey);

		try {
			IGet get = CURL.get(new URL(access_token_api));

			result = get.exec();

			validateResult(result);

		} catch (Throwable e) {
			throw new Exception("微信公众号 : " + e.getMessage(), e);
		}

		return result;
	}

	public WxmpMessage analysisXml(String xml) {
		WxmpMessage message = null;
		try {
			if (Value.isEmpty(xml)) {
				return null;
			}
			
			logger.info("wxmp : "+xml);
			// 将字符串转化为XML文档对象
			Document document = DocumentHelper.parseText(xml);
			// 获得文档的根节点
			Element root = document.getRootElement();
			// 遍历根节点下所有子节点
			Iterator<?> iter = root.elementIterator();

			// 遍历所有结点
			message = new WxmpMessage();
			// 利用反射机制，调用set方法
			// 获取该实体的元类型
			Class<?> c = message.getClass();

			while (iter.hasNext()) {
				Element ele = (Element) iter.next();
				// 获取set方法中的参数字段（实体类的属性）
				Field field = c.getDeclaredField(toLowerCaseFirst(ele.getName()));
				// 获取set方法，field.getType())获取它的参数数据类型
				Method method = c.getDeclaredMethod("set" + ele.getName(),
						field.getType());
				// 调用set方法
				method.invoke(message, ele.getText());
			}
		} catch (Exception e) {
			logger.error("xml 格式异常: " + xml);
			e.printStackTrace();
		}
		return message;
	}
	
	private static String  toLowerCaseFirst(String string){

		if(!Value.isEmpty(string)){
			string =string.substring(0,1).toLowerCase()+string.substring(1);
		}
		
		return string;
	}

	public String formatXml(String to, String from, String content) {
		StringBuffer sb = new StringBuffer();
		Date date = new Date();
		sb.append("<xml><ToUserName><![CDATA[");
		sb.append(to);
		sb.append("]]></ToUserName><FromUserName><![CDATA[");
		sb.append(from);
		sb.append("]]></FromUserName><CreateTime>");
		sb.append(date.getTime());
		sb.append("</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[");
		sb.append(content);
		sb.append("]]></Content><FuncFlag>0</FuncFlag></xml>");
		return sb.toString();
	}
}
