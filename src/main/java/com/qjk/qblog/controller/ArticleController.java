package com.qjk.qblog.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qjk.qblog.data.AGroup;
import com.qjk.qblog.data.Article;
import com.qjk.qblog.data.Pager;
import com.qjk.qblog.service.IAGroupSerivce;
import com.qjk.qblog.service.IArticleService;
import com.qjk.qblog.util.Value;

@Controller
@RequestMapping(value = "/article")
public class ArticleController {
	@Resource
	IAGroupSerivce groupSerivce;
	@Resource
	IArticleService articleService;

	@RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
	public String article(Model model) {

		return article(model, 0, 1, "");
	}

	@RequestMapping(value = { "/{groupId}/", "/{groupId}" }, method = RequestMethod.GET)
	public String article(Model model, @PathVariable long groupId) {

		return article(model, groupId, 1, "");
	}

	@RequestMapping(value = { "/{groupId}/{pageIndex}",
			"/{groupId}/{pageIndex}/" }, method = RequestMethod.GET)
	public String article(Model model, @PathVariable long groupId,
			@PathVariable int pageIndex) {

		return article(model, groupId, pageIndex, "");
	}

	@RequestMapping(value = { "/{groupId}/{pageIndex}/{keywords}/",
			"/{groupId}/{pageIndex}/{keywords}" }, method = RequestMethod.GET)
	public String article(Model model, @PathVariable long groupId,
			@PathVariable int pageIndex, @PathVariable String keywords) {
		
		if(groupId != 0){
			AGroup group = groupSerivce.findGroupById(groupId);
			if(group != null){
				model.addAttribute("curr_group", group);
			}
		}
		System.out.println(keywords);
		if(!Value.isEmpty(keywords)){
			model.addAttribute("keywords", keywords);
		}
		List<AGroup> groups = groupSerivce.findGroupList();
		model.addAttribute("groups", groups);
		Pager<Article> pager = articleService.getArticlesPager(pageIndex, groupId,keywords);
		model.addAttribute("pager", pager);
		List<Article> lastestArticles = articleService.getLastestArticles(10);
		model.addAttribute("lastest", lastestArticles);
		List<Article> mostPvArticles = articleService.getMostPvArticles(4);
		model.addAttribute("mostpv", mostPvArticles);

		return "article";
	}
	
	@RequestMapping(value="/detail/{id}")
	public String article_detail(@PathVariable long id,Model model){
		
		Article article = articleService.findArticleById(id);
		
		if(article == null || article.getHidden() == Article.HIDDEN_HIDDEN || !Article.ALIAS_ARTICLE.equals(article.getAlias())){
			return "404";
		}else{

			List<AGroup> groups = groupSerivce.findGroupList();
			model.addAttribute("groups", groups);
			List<Article> lastestArticles = articleService.getLastestArticles(10);
			model.addAttribute("lastest", lastestArticles);
			List<Article> mostPvArticles = articleService.getMostPvArticles(4);
			model.addAttribute("mostpv", mostPvArticles);

			model.addAttribute("curr_group", article.getGroup());
			model.addAttribute("article", article);

			Article prev = articleService.getPrevArticle(article.getCtime(),article.getAlias());
			model.addAttribute("prev", prev);
			Article next = articleService.getNextArticle(article.getCtime(),article.getAlias());
			model.addAttribute("next", next);
		}
		
		
		return "article_detail";
	}
}