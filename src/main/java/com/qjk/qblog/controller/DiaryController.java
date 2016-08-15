package com.qjk.qblog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qjk.qblog.data.Article;
import com.qjk.qblog.data.Message;
import com.qjk.qblog.data.Pager;
import com.qjk.qblog.service.IArticleService;
import com.qjk.qblog.service.IMessageService;
import com.qjk.qblog.util.RequestUtil;

@Controller
@RequestMapping("/diary")
public class DiaryController {
	
	@Resource IArticleService articleService;
	@Resource IMessageService messageService;
	
	
	@RequestMapping(value={"","/"},method =RequestMethod.GET)
	public String diary(Model model,HttpServletRequest request){
		
		return diary(model,1,request);
	}
	
	@RequestMapping(value={"/{pageIndex}","/{pageIndex}/"},method =RequestMethod.GET)
	public String diary(Model model ,@PathVariable int pageIndex,HttpServletRequest request){
		
		Pager<Article> pager = articleService.getArticlesPager(pageIndex,Article.ALIAS_DIARY);
		if(RequestUtil.isMobile(request)){
			model.addAttribute("articles", pager.getList());
			model.addAttribute("title", "日记");
			return "mobile/diary";
		}
		
		model.addAttribute("pager", pager);
		List<Article> lastestArticles = articleService.getLastestArticles(10,Article.ALIAS_DIARY);
		model.addAttribute("lastest", lastestArticles);
		List<Article> mostPvArticles = articleService.getMostPvArticles(4,Article.ALIAS_DIARY);
		model.addAttribute("mostpv", mostPvArticles);
		return "diary";
	}
	
	
	@RequestMapping(value="/detail/{id}")
	public String diary_detail(@PathVariable long id,Model model,HttpServletRequest request){
		
		return diary_detail(id, 1, model, request);
	}
	
	@RequestMapping(value="/detail/{id}/{pageIndex}")
	public String diary_detail(@PathVariable long id,@PathVariable int pageIndex,Model model,HttpServletRequest request){
		
		Article article = articleService.findArticleById(id);
		
		if(article == null || article.getHidden() == Article.HIDDEN_HIDDEN || !Article.ALIAS_DIARY.equals(article.getAlias())){
			return "404";
		}else{

			model.addAttribute("article", article);

			Article prev = articleService.getPrevArticle(article.getCtime(),article.getAlias());
			model.addAttribute("prev", prev);
			Article next = articleService.getNextArticle(article.getCtime(),article.getAlias());
			model.addAttribute("next", next);
			if(RequestUtil.isMobile(request)){
				model.addAttribute("title", "日记");
				return "mobile/diary_detail";
			}

			Pager<Message> pager = messageService.selectPager(id, 0, pageIndex);
			model.addAttribute("messages", pager);
			List<Article> lastestArticles = articleService.getLastestArticles(10,Article.ALIAS_DIARY);
			model.addAttribute("lastest", lastestArticles);
			List<Article> mostPvArticles = articleService.getMostPvArticles(4,Article.ALIAS_DIARY);
			model.addAttribute("mostpv", mostPvArticles);

		}
		
		
		return "diary_detail";
	}
	
	@RequestMapping(value = { "/","" }, method = RequestMethod.POST,params={"json"})
	@ResponseBody
	public Object diary_more(
			 int pageIndex,HttpServletRequest request){
		Map<String,Object> result = new HashMap<String, Object>();
		Pager<Article> pager = articleService.getArticlesPager(pageIndex,Article.ALIAS_DIARY);
		if(pager == null || pager.getList() ==null || pager.getList().size() == 0){
			result.put("ismore", "no");
		}else{
			result.put("articles", pager.getList());
			if(pager.getList().size()<= pager.getPageSize()){
				result.put("ismore", "no");
			}
		}
		return result;
	}
	
}
