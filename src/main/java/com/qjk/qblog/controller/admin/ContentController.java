package com.qjk.qblog.controller.admin;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
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

@Controller
@RequestMapping("/admin/content")
public class ContentController {

	Logger logger = Logger.getLogger(ContentController.class);

	@Resource
	IAGroupSerivce groupService;

	@Resource
	IArticleService articleService;

	@RequestMapping(value = "article/{groupId}/{pageIndex}", method = RequestMethod.GET)
	public String toArticle(@PathVariable long groupId,
			@PathVariable int pageIndex, Model model) {

		List<AGroup> groups = groupService.findGroupList();

		model.addAttribute("groups", groups);
		model.addAttribute("groupId", groupId);
		if (groupId != 0 && groups != null && groups.size() > 0) {
			Pager<Article> pager = articleService.getArticles(pageIndex,
					groupId);

			model.addAttribute("pager", pager);
		}

		return "admin/content/article";
	}

	@RequestMapping(value = "article", method = RequestMethod.GET)
	public String toArticle(Model model) {

		List<AGroup> groups = groupService.findGroupList();

		model.addAttribute("groups", groups);
		String path = "admin/content/article";
		if (groups != null && groups.size() > 0) {
			path = "/admin/content/article/" + groups.get(0).getGroupId()
					+ "/1";
			return "redirect:" + path;
		}

		return "admin/content/article";
	}

	@RequestMapping(value = "article/{id}/update", method = RequestMethod.GET)
	public String toUpdate(@PathVariable long id, Model model) {

		if (id == 0) {
			return "redirect:/admin/content/article";
		}

		try {

			Article article = articleService.findArticleById(id);

			model.addAttribute("article", article);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "redirect:/admin/content/article";
		}

		return "admin/content/article_update";
	}
}
