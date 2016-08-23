package com.qjk.qblog.controller.admin;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qjk.qblog.data.Pager;
import com.qjk.qblog.data.User;
import com.qjk.qblog.service.IUserService;

@Controller
@RequestMapping(value="/admin/user")
public class UserManagerController {

	@Resource
	IUserService userService;

	@RequestMapping(value = { "/users/", "/users" }, method = RequestMethod.GET)
	public String user_list(Model model) {

		return user_list(model, 1, "");
	}

	@RequestMapping(value = { "/users/{pageIndex}", "/users/{pageIndex}/" }, method = RequestMethod.GET)
	public String user_list(Model model, @PathVariable int pageIndex) {

		return user_list(model, pageIndex, "");
	}

	@RequestMapping(value = { "/users/{pageIndex}/{keywords}",
			"/users/{pageIndex}/{keywords}/" }, method = RequestMethod.GET)
	public String user_list(Model model, @PathVariable int pageIndex,
			@PathVariable String keywords) {
		Pager<User> pager = userService.getAllUser(pageIndex, keywords);

		model.addAttribute("pager", pager);

		return "admin/user/user_list";
	}

}
