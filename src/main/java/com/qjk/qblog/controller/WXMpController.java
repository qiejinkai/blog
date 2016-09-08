package com.qjk.qblog.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qjk.qblog.data.WxmpMessage;
import com.qjk.qblog.service.ISettingService;
import com.qjk.qblog.util.WXMpHelper;

/**
 * 微信公众平台接口
 * 
 * @author qiejinkai
 *
 */
@Controller
@RequestMapping(value = "/wxmp")
public class WXMpController {

	Logger logger = Logger.getLogger(WXMpController.class);
	@Resource
	ISettingService settingService;

	WXMpHelper WXMpHelper;

	private WXMpHelper getWXMpHelper() {

		if (WXMpHelper == null) {
			synchronized (WXUserController.class) {

				if (WXMpHelper == null) {
					Map<String, String> WX = settingService
							.getOptionsByName("wxmp");
					WXMpHelper = new WXMpHelper(WX);
				}
			}
		}

		return WXMpHelper;

	}

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	@ResponseBody
	public String in(String signature, String timestamp, String nonce,
			String echostr) {

		WXMpHelper helper = getWXMpHelper();
		boolean result = false;
		try {
			result = helper.validateSign(signature, timestamp, nonce);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = false;
		}

		logger.info(result ? "微信公众平台接入成功" : "微信公众平台接入失败");

		return result ? echostr : "";
	}

	@RequestMapping(value = { "", "/" }, method = RequestMethod.POST)
	@ResponseBody
	public void msg(HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		WXMpHelper helper = getWXMpHelper();
		try{
			// 读取接收到的xml消息
			StringBuffer sb = new StringBuffer();
			InputStream is = request.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String s = "";
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			String xml = sb.toString(); // 次即为接收到微信端发送过来的xml数据
			WxmpMessage message = helper.analysisXml(xml);
			
			result = helper.formatXml(message.getFromUserName(), message.getToUserName(), "你好");
			 
			response.setCharacterEncoding("UTF-8");

			response.setHeader("Content-Type", "text/plain;charset=UTF-8");
			
			OutputStream out = response.getOutputStream();
			
			out.write(result.getBytes("UTF-8"));
			
			out.flush();
			
			out.close();
			
		}catch(Exception e){
			
		}
	}
}
