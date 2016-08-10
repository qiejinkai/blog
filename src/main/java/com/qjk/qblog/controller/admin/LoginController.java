package com.qjk.qblog.controller.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qjk.qblog.data.Admin;
import com.qjk.qblog.data.Menu;
import com.qjk.qblog.data.User;
import com.qjk.qblog.service.IAdminService;
import com.qjk.qblog.service.IMenuService;

@Controller
public class LoginController {

	
	@Resource IAdminService adminService;
	@Resource IMenuService menuService;
	
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin(){
		
		return "admin/index";
	}
	

	@RequestMapping(value = "/admin/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request) {
		Object admin = request.getSession().getAttribute("admin");
		if (admin != null && admin instanceof Admin) {
			return "redirect:/admin";
		}

		return "admin/login/login";
	}

	@RequestMapping(value = "/admin/login", method = RequestMethod.POST)
	public String login(String account, String password, String code,
			Model model, HttpServletRequest req) {

		try {

			Assert.hasText(code, "请输入验证码");

			Object verifyCode = req.getSession(true).getAttribute("code");

			Assert.isTrue(code.equals(verifyCode), "验证码错误");

			Admin admin = adminService.login(account, password);
			Menu menu = menuService.getAdminMenu();

			req.getSession(true).setAttribute("admin", admin);
			req.getSession(true).setAttribute("menu", menu);

		} catch (Exception e) {

			model.addAttribute("error", e.getMessage());

			return "admin/login/login";
		}

		return "redirect:/admin";
	}
	@RequestMapping(value = "/admin/logout", method = RequestMethod.GET)
	public String logout( HttpServletRequest req){

		req.getSession(true).removeAttribute("admin");
		req.getSession(true).removeAttribute("menu");

		return "redirect:/admin";
	}
}