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

import com.qjk.qblog.data.AGroup;
import com.qjk.qblog.data.Article;
import com.qjk.qblog.data.Pager;
import com.qjk.qblog.service.IAGroupSerivce;
import com.qjk.qblog.service.IArticleService;
import com.qjk.qblog.util.RequestUtil;
import com.qjk.qblog.util.Value;

@Controller
@RequestMapping(value = "/article")
public class ArticleController {
	@Resource
	IAGroupSerivce groupSerivce;
	@Resource
	IArticleService articleService;

	@RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
	public String article(Model model, HttpServletRequest request) {

		return article(model, 0, 1, "", request);
	}

	@RequestMapping(value = { "/{groupId}/", "/{groupId}" }, method = RequestMethod.GET)
	public String article(Model model, @PathVariable long groupId,
			HttpServletRequest request) {

		return article(model, groupId, 1, "", request);
	}

	@RequestMapping(value = { "/{groupId}/{pageIndex}",
			"/{groupId}/{pageIndex}/" }, method = RequestMethod.GET)
	public String article(Model model, @PathVariable long groupId,
			@PathVariable int pageIndex, HttpServletRequest request) {

		return article(model, groupId, pageIndex, "", request);
	}

	@RequestMapping(value = { "/{groupId}/{pageIndex}/{keywords}/",
			"/{groupId}/{pageIndex}/{keywords}" }, method = RequestMethod.GET)
	public String article(Model model, @PathVariable long groupId,
			@PathVariable int pageIndex, @PathVariable String keywords,
			HttpServletRequest request) {
		model.addAttribute("curr_groupId", groupId);
		if (groupId != 0) {
			AGroup group = groupSerivce.findGroupById(groupId);
			if (group != null) {
				model.addAttribute("curr_group", group);
				model.addAttribute("curr_groupId", group.getGroupId());
			}
		}
		if (!Value.isEmpty(keywords)) {
			model.addAttribute("keywords", keywords);
		}
		if (RequestUtil.isMobile(request)) {
			Pager<Article> pager = articleService.getArticlesPager(pageIndex,
					groupId, keywords);
			if (pager != null) {
				model.addAttribute("articles", pager.getList());
			}
			return "mobile/article";
		} else {

			List<AGroup> groups = groupSerivce.findGroupList();
			model.addAttribute("groups", groups);
			Pager<Article> pager = articleService.getArticlesPager(pageIndex,
					groupId, keywords);
			model.addAttribute("pager", pager);
			List<Article> lastestArticles = articleService
					.getLastestArticles(10);
			model.addAttribute("lastest", lastestArticles);
			List<Article> mostPvArticles = articleService.getMostPvArticles(4);
			model.addAttribute("mostpv", mostPvArticles);

			return "article";
		}
	}

	@RequestMapping(value = { "/", "" }, method = RequestMethod.POST, params = { "json" })
	@ResponseBody
	public Object article_more(long groupId, int pageIndex, String keywords,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		Pager<Article> pager = articleService.getArticlesPager(pageIndex,
				groupId, keywords);
		if (pager == null || pager.getList() == null
				|| pager.getList().size() == 0) {
			result.put("ismore", "no");
		} else {
			result.put("articles", pager.getList());
			if (pager.getList().size() <= pager.getPageSize()) {
				result.put("ismore", "no");
			}
		}
		return result;
	}

	@RequestMapping(value = "/detail/{id}")
	public String article_detail(@PathVariable long id, Model model,
			HttpServletRequest request) {

		Article article = articleService.findArticleById(id);

		if (article == null || article.getHidden() == Article.HIDDEN_HIDDEN
				|| !Article.ALIAS_ARTICLE.equals(article.getAlias())) {
			return "404";
		} else {

			Article prev = articleService.getPrevArticle(article.getCtime(),
					article.getAlias());
			model.addAttribute("prev", prev);
			Article next = articleService.getNextArticle(article.getCtime(),
					article.getAlias());
			model.addAttribute("next", next);
			model.addAttribute("article", article);
			if (RequestUtil.isMobile(request)) {
				return "mobile/article_detail";
			}

			List<AGroup> groups = groupSerivce.findGroupList();
			model.addAttribute("groups", groups);

			List<Article> lastestArticles = articleService
					.getLastestArticles(10);
			model.addAttribute("lastest", lastestArticles);
			List<Article> mostPvArticles = articleService.getMostPvArticles(4);
			model.addAttribute("mostpv", mostPvArticles);

			model.addAttribute("curr_group", article.getGroup());

		}

		return "article_detail";
	}
}
