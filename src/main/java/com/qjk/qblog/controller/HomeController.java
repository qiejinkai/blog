package com.qjk.qblog.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qjk.qblog.data.AGroup;
import com.qjk.qblog.data.Article;
import com.qjk.qblog.data.User;
import com.qjk.qblog.service.IAGroupSerivce;
import com.qjk.qblog.service.IArticleService;
import com.qjk.qblog.service.IUserService;
import com.qjk.qblog.service.impl.UserServiceImpl;
import com.qjk.qblog.util.RequestUtil;
import com.qjk.qblog.util.Value;

@Controller
public class HomeController {
	@Resource
	IArticleService articleService;
	@Resource
	IAGroupSerivce groupSerivce;
	
	@Resource IUserService userService;

	@RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request) {

		List<Article> bannerList = articleService.getArticles(1, 10,
				Article.ALIAS_BANNER);
		List<Article> infomationList = articleService.getArticles(1, 10,
				Article.ALIAS_INFORMATION);
		if (RequestUtil.isMobile(request)) {

			List<AGroup> groups = groupSerivce.findGroupList();
			model.addAttribute("groups", groups);
			List<Article> articles = articleService.getHomeShowArticles();
			model.addAttribute("articles", articles);
			model.addAttribute("bannerList", bannerList);
			model.addAttribute("infomationList", infomationList);
			return "mobile/home";
		} else {
			model.addAttribute("infomationList", infomationList);
			List<Article> articles = articleService.getHomeShowArticles();
			model.addAttribute("articles", articles);
			List<Article> lastestArticles = articleService
					.getLastestArticles(5);
			model.addAttribute("lastest", lastestArticles);
			List<Article> mostPvArticles = articleService.getMostPvArticles(4);
			model.addAttribute("mostpv", mostPvArticles);
			List<Article> notice = articleService.getArticles(1, 1,
					Article.ALIAS_NOTICE);
			model.addAttribute("notice", notice);
			List<Article> friendLinks = articleService.getArticles(1, 1,
					Article.ALIAS_FRIENDLINK);
			model.addAttribute("friendlinks", friendLinks);
			if(bannerList != null && bannerList.size()>0){
				
				model.addAttribute("banner", bannerList.get(0));
			}
			return "home";
		}
	}
	
	@RequestMapping(value={"/close","/close/"},method=RequestMethod.GET)
	public String auto_close(){
		return "auto_close";
	}


	@RequestMapping(value = { "/logout", "/logout/" })
	public String logout(String r, HttpServletRequest request) {
		
		if (Value.isEmpty(r)) {
			r = "/";
		}

		HttpSession session = request.getSession();
		
		if (session.getAttribute("user") != null) {

			session.removeAttribute("user");

		}

		return "redirect:" + r;
	}

	public String login(String r, HttpServletRequest request) {
		
		if (Value.isEmpty(r)) {
			r = "/";
		}
		
		User user = userService.findUserById(12);

		HttpSession session = request.getSession();
		
		session.setAttribute("user", user);

		return "redirect:" + r;
	}
}
