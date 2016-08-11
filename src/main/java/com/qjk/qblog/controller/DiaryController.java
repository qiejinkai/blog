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
import com.qjk.qblog.service.IArticleService;

@Controller
@RequestMapping("/diary")
public class DiaryController {
	
	@Resource IArticleService articleService;
	
	
	@RequestMapping(value={"","/"},method =RequestMethod.GET)
	public String diary(Model model){
		
		return diary(model,1);
	}
	
	@RequestMapping(value={"/{pageIndex}","/{pageIndex}/"},method =RequestMethod.GET)
	public String diary(Model model ,@PathVariable int pageIndex){
		
		Pager<Article> pager = articleService.getArticlesPager(pageIndex,Article.ALIAS_DIARY);
		model.addAttribute("pager", pager);
		List<Article> lastestArticles = articleService.getLastestArticles(10,Article.ALIAS_DIARY);
		model.addAttribute("lastest", lastestArticles);
		List<Article> mostPvArticles = articleService.getMostPvArticles(4,Article.ALIAS_DIARY);
		model.addAttribute("mostpv", mostPvArticles);
		return "diary";
	}
	
	
	@RequestMapping(value="/detail/{id}")
	public String diary_detail(@PathVariable long id,Model model){
		
		Article article = articleService.findArticleById(id);
		
		if(article == null || article.getHidden() == Article.HIDDEN_HIDDEN || !Article.ALIAS_DIARY.equals(article.getAlias())){
			return "404";
		}else{

			List<Article> lastestArticles = articleService.getLastestArticles(10,Article.ALIAS_DIARY);
			model.addAttribute("lastest", lastestArticles);
			List<Article> mostPvArticles = articleService.getMostPvArticles(4,Article.ALIAS_DIARY);
			model.addAttribute("mostpv", mostPvArticles);

			model.addAttribute("curr_group", article.getGroup());
			model.addAttribute("article", article);

			Article prev = articleService.getPrevArticle(article.getCtime(),article.getAlias());
			model.addAttribute("prev", prev);
			Article next = articleService.getNextArticle(article.getCtime(),article.getAlias());
			model.addAttribute("next", next);
		}
		
		
		return "diary_detail";
	}
	
}
