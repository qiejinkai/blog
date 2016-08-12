package com.qjk.qblog.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qjk.qblog.data.AGroup;
import com.qjk.qblog.data.Article;
import com.qjk.qblog.data.Pager;
import com.qjk.qblog.service.IAGroupSerivce;
import com.qjk.qblog.service.IArticleService;
import com.qjk.qblog.util.RequestUtil;

@Controller
public class HomeController {
	@Resource
	IArticleService articleService;
	@Resource
	IAGroupSerivce groupSerivce;

	@RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request) {

		if (RequestUtil.isMobile(request)) {

			List<AGroup> groups = groupSerivce.findGroupList();
			model.addAttribute("groups", groups);
			List<Article> articles = articleService.getHomeShowArticles();
			model.addAttribute("articles", articles);
			List<Article> bannerList = articleService.getArticles(1, 10,
					Article.ALIAS_BANNER);
			model.addAttribute("bannerList", bannerList);
			model.addAttribute("title", "首页");
			return "mobile/home";
		} else {
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

			return "home";
		}
	}
}
