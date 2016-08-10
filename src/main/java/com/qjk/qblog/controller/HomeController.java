package com.qjk.qblog.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qjk.qblog.data.Article;
import com.qjk.qblog.service.IArticleService;

@Controller
public class HomeController {
	@Resource IArticleService articleService;
	@RequestMapping(value={"/",""},method=RequestMethod.GET)
	public String home(Model model){
		List<Article> articles = articleService.getHomeShowArticles();
		model.addAttribute("articles",articles);		
		List<Article> lastestArticles = articleService.getLastestArticles(5);
		model.addAttribute("lastest",lastestArticles);
		List<Article> mostPvArticles = articleService.getMostPvArticles(5);
		model.addAttribute("mostpv",mostPvArticles);
		List<Article> notice = articleService.getArticles(1, 1, Article.ALIAS_NOTICE);
		model.addAttribute("notice",notice);
		List<Article> friendLinks = articleService.getArticles(1, 1, Article.ALIAS_FRIENDLINK);
		model.addAttribute("friendlinks", friendLinks);
		
			
		return "home";
	}
}
