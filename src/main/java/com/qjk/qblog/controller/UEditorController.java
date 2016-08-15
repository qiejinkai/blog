package com.qjk.qblog.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baidu.ueditor.ActionEnter;

@Controller
@RequestMapping("/ueditor")
public class UEditorController {

	@RequestMapping("/config")
	public void ueditor(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Content-Type", "text/html");
		response.setContentType("application/json");
		String rootPath = request.getServletContext().getRealPath("/");
		

		try {

			request.setCharacterEncoding("utf-8");
			String exec = new ActionEnter(request, rootPath).exec();
			PrintWriter writer = response.getWriter();
			writer.write(exec);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
