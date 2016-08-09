package com.qjk.qblog.controller.admin;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qjk.qblog.data.AGroup;
import com.qjk.qblog.data.Article;
import com.qjk.qblog.data.Pager;
import com.qjk.qblog.group.ValidateInArticlePost;
import com.qjk.qblog.group.ValidateInPost;
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
	public String articleTo(@PathVariable long groupId,
			@PathVariable int pageIndex, Model model) {

		List<AGroup> groups = groupService.findGroupList();

		model.addAttribute("groups", groups);
		model.addAttribute("groupId", groupId);
		if (groupId != 0 && groups != null && groups.size() > 0) {
			Pager<Article> pager = articleService.getArticlesPager(pageIndex,
					groupId);

			model.addAttribute("pager", pager);
		}

		return "admin/content/article";
	}

	@RequestMapping(value = "article", method = RequestMethod.GET)
	public String articleTo(Model model) {

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

	@RequestMapping(value = "article/{id}/{groupId}/set", method = RequestMethod.GET)
	public String articleToSet(@PathVariable long id,
			@PathVariable long groupId, Model model, Article article) {

		if (id == 0) {

			if (groupId == 0) {

				return "redirect:/admin/content/article";
			}

			article = new Article();
			article.setAlias(Article.ALIAS_ARTICLE);
			article.setHidden(Article.HIDDEN_NONE);
			article.setGroupId(groupId);

		} else {
			try {

				article = articleService.findArticleById(id);
				
				Assert.notNull(article);

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return "redirect:/admin/content/article";
			}
		}

		model.addAttribute("article", article);

		return "admin/content/article_set";
	}

	@RequestMapping(value = "article/{id}/{groupId}/set", method = RequestMethod.POST)
	public String articleSet(
			@Validated(ValidateInArticlePost.class) Article article,
			BindingResult br, Model model) {

		if (br.hasErrors()) {
			List<ObjectError> errors = br.getAllErrors();
			model.addAttribute("error", errors.get(0).getDefaultMessage());
		} else {

			try {
				articleService.saveOrUpdateArticle(article);
				model.addAttribute("success", "操作成功");
			} catch (Exception e) {
				model.addAttribute("error", e.getMessage());
			}

		}

		return "admin/content/article_set";
	}

	@RequestMapping(value = "article/{id}/{groupId}/delete", method = RequestMethod.GET)
	public String articleDelete(@PathVariable long id,
			@PathVariable long groupId) {

		articleService.deleteArticleById(id);

		return "redirect:/admin/content/article/" + groupId + "/1";
	}

	@RequestMapping(value = "article/{id}/{groupId}/hidden", method = RequestMethod.GET)
	public String articleHidden(@PathVariable long id,
			@PathVariable long groupId) {

		articleService.hiddenArticleById(id);

		return "redirect:/admin/content/article/" + groupId + "/1";
	}

	@RequestMapping(value = "article/group/add", method = RequestMethod.GET)
	public String toAddGroup() {

		return "admin/content/group_add";
	}

	@RequestMapping(value = "article/group/add", method = RequestMethod.POST)
	public String addGroup(@Validated(ValidateInPost.class) AGroup aGroup,
			BindingResult br, Model model) {
		if (br.hasErrors()) {
			List<ObjectError> errors = br.getAllErrors();
			model.addAttribute("error", errors.get(0).getDefaultMessage());
		} else {

			try {
				groupService.addAGroup(aGroup);

				return "redirect:/admin/content/article";
			} catch (Exception e) {
				model.addAttribute("error", e.getMessage());
			}

		}

		return "admin/content/group_add";
	}

//	@RequestMapping(value = "banner", method = RequestMethod.GET)
//	public String bannerTo(Model model) {
//
//		Pager<Article> pager = articleService.getArticlesPager(1,
//				Article.ALIAS_BANNER);
//
//		model.addAttribute("pager", pager);
//
//		return "admin/content/banner";
//	}
//
//
//	@RequestMapping(value = "banner/{p}", method = RequestMethod.GET)
//	public String bannerTo(Model model, @PathVariable int p) {
//
//		Pager<Article> pager = articleService.getArticlesPager(p,
//				Article.ALIAS_BANNER);
//
//		model.addAttribute("pager", pager);
//
//		return "admin/content/banner";
//	}
//
//	@RequestMapping(value = "banner/{id}/set", method = RequestMethod.GET)
//	public String bannerToSet(@PathVariable long id, Model model, Article article) {
//
//		try {
//
//			article = articleService.findArticleById(id);
//
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			return "redirect:/admin/content/banner/1";
//		}
//
//		if (article != null && !Article.ALIAS_BANNER.equals(article.getAlias())) {
//			return "redirect:/admin/content/banner/1";
//
//		} else if (article == null) {
//
//			article = new Article();
//			article.setAlias(Article.ALIAS_BANNER);
//			article.setHidden(Article.HIDDEN_NONE);
//
//		}
//
//		model.addAttribute("article", article);
//
//		return "admin/content/banner_set";
//	}
//
//	@RequestMapping(value = "banner/{id}/set", method = RequestMethod.POST)
//	public String bannerSet(
//			@Validated(ValidateInPost.class) Article article,
//			BindingResult br, Model model) {
//
//		if (br.hasErrors()) {
//			List<ObjectError> errors = br.getAllErrors();
//			model.addAttribute("error", errors.get(0).getDefaultMessage());
//		} else {
//
//			try {
//				articleService.saveOrUpdateArticle(article);
//				model.addAttribute("success", "操作成功");
//			} catch (Exception e) {
//				model.addAttribute("error", e.getMessage());
//			}
//
//		}
//
//		return "admin/content/banner_set";
//	}
	
	@RequestMapping(value = "{alias}/{id}/delete", method = RequestMethod.GET)
	public String delete(@PathVariable long id,
			@PathVariable String alias) {
		if(!Article.contains(alias) || Article.ALIAS_ARTICLE.equals(alias)){
			return "redirect:/admin";
		}
		articleService.deleteArticleById(id);

		return "redirect:/admin/content/"+alias+"/1";
	}
	@RequestMapping(value = "{alias}/{id}/hidden", method = RequestMethod.GET)
	public String hidden(@PathVariable long id,
			@PathVariable String alias) {

		if(!Article.contains(alias) || Article.ALIAS_ARTICLE.equals(alias)){
			return "redirect:/admin";
		}
		articleService.hiddenArticleById(id);

		return "redirect:/admin/content/"+alias+"/1";
	}
	
	@RequestMapping(value = "{alias}", method = RequestMethod.GET)
	public String to(Model model,@PathVariable String alias) {
		
		if(!Article.contains(alias) || Article.ALIAS_ARTICLE.equals(alias)){
			return "redirect:/admin";
		}
		Pager<Article> pager = articleService.getArticlesPager(1,
				alias);

		model.addAttribute("pager", pager);

		return "admin/content/"+alias;
	}
	@RequestMapping(value = "{alias}/{p}", method = RequestMethod.GET)
	public String to(Model model,@PathVariable String alias,@PathVariable int p) {
		
		if(!Article.contains(alias) || Article.ALIAS_ARTICLE.equals(alias)){
			return "redirect:/admin";
		}
		Pager<Article> pager = articleService.getArticlesPager(p,
				alias);

		model.addAttribute("pager", pager);

		return "admin/content/"+alias;
	}
	@RequestMapping(value = "{alias}/{id}/set", method = RequestMethod.GET)
	public String toSet(@PathVariable long id,@PathVariable String  alias, Model model, Article article) {

		if(!Article.contains(alias) || Article.ALIAS_ARTICLE.equals(alias)){
			return "redirect:/admin";
		}
		try {

			article = articleService.findArticleById(id);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "redirect:/admin/content/"+alias+"/1";
		}

		if (article != null && !alias.equals(article.getAlias())) {
			return "redirect:/admin/content/"+alias+"/1";

		} else if (article == null) {

			article = new Article();
			article.setAlias(alias);
			article.setHidden(Article.HIDDEN_NONE);

		}

		model.addAttribute("article", article);

		return "admin/content/"+alias+"_set";
	}
	
	@RequestMapping(value = "{alias}/{id}/set", method = RequestMethod.POST)
	public String set(@PathVariable String alias,
			@Validated(ValidateInPost.class) Article article,
			BindingResult br, Model model) {
		
		if(!Article.contains(alias) || Article.ALIAS_ARTICLE.equals(alias)){
			return "redirect:/admin";
		}
		if (br.hasErrors()) {
			List<ObjectError> errors = br.getAllErrors();
			model.addAttribute("error", errors.get(0).getDefaultMessage());
		} else {

			try {
				articleService.saveOrUpdateArticle(article);
				model.addAttribute("success", "操作成功");
			} catch (Exception e) {
				model.addAttribute("error", e.getMessage());
			}

		}

		return "admin/content/"+alias+"_set";
	}

}
