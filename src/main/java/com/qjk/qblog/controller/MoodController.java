package com.qjk.qblog.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qjk.qblog.data.Article;
import com.qjk.qblog.data.Pager;
import com.qjk.qblog.service.IArticleService;

@Controller
@RequestMapping(value="/mood")
public class MoodController {
	
	@Resource IArticleService articleService;
	
	@RequestMapping(value={"","/"},method=RequestMethod.GET)
	public String mood(Model model){
				
		return mood(model,1);
	}
	
	
	@RequestMapping(value={"/{pageIndex}","/{pageIndex}/"},method=RequestMethod.GET)
	public String mood(Model model,@PathVariable int pageIndex){
		
		Pager<Article> pager = articleService.getArticlesPager(pageIndex, Article.ALIAS_MOOD);
		
		model.addAttribute("pager", pager);
		
		
		return "mood";
	}


}
