package com.qjk.qblog.controller.admin;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qjk.qblog.data.Message;
import com.qjk.qblog.data.Pager;
import com.qjk.qblog.data.QQUser;
import com.qjk.qblog.data.User;
import com.qjk.qblog.data.WXUser;
import com.qjk.qblog.service.IMessageService;
import com.qjk.qblog.service.IQQUserService;
import com.qjk.qblog.service.IUserService;
import com.qjk.qblog.service.IWXUserService;

@Controller
@RequestMapping(value="/admin/user")
public class UserManagerController {

	@Resource
	IUserService userService;
	@Resource
	IQQUserService qqUserService;
	@Resource
	IWXUserService wxUserService;
	@Resource
	IMessageService messageService;

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
	@RequestMapping(value={"/users/{uid}/detail","/users/{uid}/detail/"},method=RequestMethod.GET)
	public String user_detail(Model model,@PathVariable long uid){
		
		if(uid == 0){
			return "redirect:/admin/user/users";
		}
		
		User user = userService.findUserById(uid);
		
		if(user == null){

			return "redirect:/admin/user/users";
		}
		
		model.addAttribute("user", user);
		return "admin/user/user_detail";
	}
	
	@RequestMapping(value = { "/qqusers/", "/qqusers" }, method = RequestMethod.GET)
	public String qquser_list(Model model) {

		return qquser_list(model, 1, "");
	}

	@RequestMapping(value = { "/qqusers/{pageIndex}", "/qqusers/{pageIndex}/" }, method = RequestMethod.GET)
	public String qquser_list(Model model, @PathVariable int pageIndex) {

		return qquser_list(model, pageIndex, "");
	}

	@RequestMapping(value = { "/qqusers/{pageIndex}/{keywords}",
			"/qqusers/{pageIndex}/{keywords}/" }, method = RequestMethod.GET)
	public String qquser_list(Model model, @PathVariable int pageIndex,
			@PathVariable String keywords) {
		Pager<QQUser> pager = qqUserService.getAllQQUser(pageIndex, keywords);

		model.addAttribute("pager", pager);

		return "admin/user/qquser_list";
	}
	@RequestMapping(value={"/qqusers/{qqid}/detail","/qqusers/{qqid}/detail/"},method=RequestMethod.GET)
	public String qquser_detail(Model model,@PathVariable long qqid){
		
		if(qqid == 0){
			return "redirect:/admin/user/qqusers";
		}
		
		QQUser user = qqUserService.findQQUserById(qqid);
		
		if(user == null){

			return "redirect:/admin/user/qqusers";
		}
		
		model.addAttribute("user", user);
		return "admin/user/qquser_detail";
	}
	
	@RequestMapping(value = { "/wxusers/", "/wxusers" }, method = RequestMethod.GET)
	public String wxuser_list(Model model) {

		return wxuser_list(model, 1, "");
	}

	@RequestMapping(value = { "/wxusers/{pageIndex}", "/wxusers/{pageIndex}/" }, method = RequestMethod.GET)
	public String wxuser_list(Model model, @PathVariable int pageIndex) {

		return wxuser_list(model, pageIndex, "");
	}

	@RequestMapping(value = { "/wxusers/{pageIndex}/{keywords}",
			"/wxusers/{pageIndex}/{keywords}/" }, method = RequestMethod.GET)
	public String wxuser_list(Model model, @PathVariable int pageIndex,
			@PathVariable String keywords) {
		Pager<WXUser> pager = wxUserService.getAllWXUser(pageIndex, keywords);

		model.addAttribute("pager", pager);

		return "admin/user/wxuser_list";
	}
	@RequestMapping(value={"/wxusers/{wxid}/detail","/wxusers/{wxid}/detail/"},method=RequestMethod.GET)
	public String wxuser_detail(Model model,@PathVariable long wxid){
		
		if(wxid == 0){
			return "redirect:/admin/user/wxusers";
		}
		
		WXUser user = wxUserService.findWXUserById(wxid);
		
		if(user == null){

			return "redirect:/admin/user/wxusers";
		}
		
		model.addAttribute("user", user);
		return "admin/user/wxuser_detail";
	}
	@RequestMapping(value = { "/message/", "/message" }, method = RequestMethod.GET)
	public String message_list(Model model) {

		return message_list(model, 1, "");
	}

	@RequestMapping(value = { "/message/{pageIndex}", "/message/{pageIndex}/" }, method = RequestMethod.GET)
	public String message_list(Model model, @PathVariable int pageIndex) {

		return message_list(model, pageIndex, "");
	}

	@RequestMapping(value = { "/message/{pageIndex}/{keywords}",
			"/message/{pageIndex}/{keywords}/" }, method = RequestMethod.GET)
	public String message_list(Model model, @PathVariable int pageIndex,
			@PathVariable String keywords) {
		Pager<Message> pager = messageService.selectPager(pageIndex, keywords,0,0);

		model.addAttribute("pager", pager);

		return "admin/user/message_list";
	}
}
