package com.qjk.qblog.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.druid.pool.DruidDataSource.CreateConnectionTask;
import com.qjk.qblog.util.DigestUtil;
import com.qjk.qblog.util.GraphicsUtil;
import com.qjk.qblog.util.VerifyCodeUtil;

@Controller
@RequestMapping("/image")
public class ImageController {

	private static final String allowType = ".jpg,.jpeg,.png,.gif,.bmp";

	@RequestMapping(value = "/verifycode", method = RequestMethod.GET)
	public void drawVerfiyImage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// System.out.println("drawVerfiyImage");
		String content = VerifyCodeUtil.randomCode(4);
		request.getSession(true).setAttribute("code", content);
		ServletOutputStream out = response.getOutputStream();
		GraphicsUtil.drawImage(content, out);
		out.flush();
		out.close();
	}
	
	@RequestMapping(value = "/create2", method = RequestMethod.POST)
	@ResponseBody
	public Object create(HttpServletRequest request) {
		Map<String, String> result = new HashMap<String, String>();
		String url = "";
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if (!file.isEmpty()) {

					try {
						String path = request.getSession().getServletContext()
								.getRealPath("/img/upload");
						String fileName = file.getOriginalFilename();

						Assert.isTrue(fileName.indexOf(".") >= 0, "未找到图片后缀名");

						String fileType = fileName.substring(fileName
								.lastIndexOf("."));

						Assert.hasText(fileType, "未找到图片后缀名");

						Assert.isTrue(allowType.contains(fileType), "不允许上传的图片类型");

						fileName = DigestUtil.md5(fileName + new Date().getTime()
								+ Math.random())
								+ fileType;

						File f = new File(path + "/" + fileName);

						FileUtils.copyInputStreamToFile(file.getInputStream(), f);

						url = "/img/upload/" + fileName;
						result.put("url", url);
						
					} catch (Exception e) {

						result.put("error", e.getMessage());
					}
				}
			}

		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public Object create3(@RequestParam("image") MultipartFile file,
			HttpServletRequest request) {

		Map<String, String> result = new HashMap<String, String>();
		String url = "";
		if (file != null) {

			if (!file.isEmpty()) {

				try {
					String path = request.getSession().getServletContext()
							.getRealPath("/img/upload");
					String fileName = file.getOriginalFilename();

					Assert.isTrue(fileName.indexOf(".") >= 0, "未找到图片后缀名");

					String fileType = fileName.substring(fileName
							.lastIndexOf("."));

					Assert.hasText(fileType, "未找到图片后缀名");

					Assert.isTrue(allowType.contains(fileType), "不允许上传的图片类型");

					fileName = DigestUtil.md5(fileName + new Date().getTime()
							+ Math.random())
							+ fileType;

					File f = new File(path + "/" + fileName);

					FileUtils.copyInputStreamToFile(file.getInputStream(), f);

					url = "/img/upload/" + fileName;

				} catch (Exception e) {

					result.put("error", e.getMessage());
				}
			}

		}
		return result;
	}

	public static void main(String[] args) {
		String filename = "123.txt";
		System.out.println(filename.substring(filename.lastIndexOf(".")));
	}
}
