package com.qjk.qblog.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.qjk.qblog.dao.IAGroupDao;
import com.qjk.qblog.dao.IArticleDao;
import com.qjk.qblog.data.AGroup;
import com.qjk.qblog.data.Article;
import com.qjk.qblog.data.Pager;
import com.qjk.qblog.service.IArticleService;
import com.sun.tools.doclets.internal.toolkit.util.Group;

@Service
public class ArticleServiceImpl implements IArticleService {

	@Resource
	IArticleDao articleDao;
	@Resource
	IAGroupDao groupDao;

	public Pager<Article> getArticlesPager(int pageIndex, long groupId) {
		Pager<Article> pager = new Pager<Article>().openCounter(pageIndex);
		Map<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put(" AND alias=?", Article.ALIAS_ARTICLE);
		if (groupId != 0) {
			sqlParams.put(" AND group.groupId=?", groupId);
		}
		pager.setSqlParams(sqlParams).setOrder(" ORDER BY ctime DESC");
		pager = articleDao.selectPager(pager);

		return pager;
	}

	@Override
	public Article findArticleById(long id) {
		Article article = articleDao.findArticleById(id);

		return article;
	}

	@Override
	public Article saveOrUpdateArticle(Article article) {

		Assert.notNull(article, "未找到文章对象");

		Article object = articleDao.findArticleById(article.getArticleId());

		long now = new Date().getTime() / 1000;
		if (object == null) {
			object = new Article();
			object.setCtime(now);
			object.setIndexNo(now);
			AGroup group = article.getGroup();
			object.setGroup(group);
		}

		object.setAlias(article.getAlias());
		object.setAuthor(article.getAuthor());
		object.setContent(article.getContent());
		object.setHidden(article.getHidden());
		object.setImage(article.getImage());
		object.setContentType(article.getContentType());
		object.setMtime(now);
		object.setSummary(article.getSummary());
		object.setTags(article.getTags());
		object.setTitle(article.getTitle());
		object.setLink(article.getLink());
		object.setHomeShow(article.getHomeShow());

		articleDao.updateArticle(object);
		
		groupDao.refreshCount();

		return object;
	}

	@Override
	public Pager<Article> getArticlesPager(int pageIndex, String alias) {
		Pager<Article> pager = new Pager<Article>().openCounter(pageIndex);
		Map<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put(" AND alias=?", alias);
		pager.setSqlParams(sqlParams).setOrder(" ORDER BY ctime DESC");
		pager = articleDao.selectPager(pager);

		return pager;
	}

	@Override
	public void deleteArticleById(long id) {

		if (id != 0) {
			articleDao.deleteArticle(id);
			groupDao.refreshCount();
		}

	}

	@Override
	public void hiddenArticleById(long id) {
		if (id != 0) {

			Article article = articleDao.findArticleById(id);

			if (article != null) {
				article.setHidden(Math.abs(article.getHidden() - 1));
				articleDao.updateArticle(article);
				groupDao.refreshCount();
			}

		}

	}

	@CacheEvict(cacheNames = "artilceConfig", key = "'home_show_article'")
	public void homeShowArticleById(long id) {

		Article article = articleDao.findArticleById(id);

		if (article != null) {
			article.setHomeShow(Math.abs(article.getHomeShow() - 1));
			articleDao.updateArticle(article);
		}

	}

	@Cacheable(cacheNames = "artilceConfig", key = "'home_show_article'")
	public List<Article> getHomeShowArticles() {
		Pager<Article> pager = new Pager<Article>().openCounter(1, 9999)
				.closeCounter();
		Map<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put(" AND homeShow=?", Article.HOME_SHOW_TRUE);
		sqlParams.put(" AND hidden=?", Article.HIDDEN_NONE);
		sqlParams.put(" AND alias=?", Article.ALIAS_ARTICLE);
		pager.setSqlParams(sqlParams).setOrder(" ORDER BY ctime DESC");
		pager = articleDao.selectPager(pager);

		return pager != null ? pager.getList() : new ArrayList<Article>();
	}

	@Override
	public List<Article> getArticles(int pageIndex, int size, String alias) {
		Pager<Article> pager = new Pager<Article>()
				.openCounter(pageIndex, size).closeCounter();
		Map<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put(" AND hidden=?", Article.HIDDEN_NONE);
		sqlParams.put(" AND alias=?", alias);
		pager.setSqlParams(sqlParams).setOrder(" ORDER BY ctime DESC");
		pager = articleDao.selectPager(pager);

		return pager != null ? pager.getList() : new ArrayList<Article>();
	}

	@Override
	public List<Article> getLastestArticles(int size) {

		Pager<Article> pager = new Pager<Article>().openCounter(1, size)
				.closeCounter();
		Map<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put(" AND hidden=?", Article.HIDDEN_NONE);
		sqlParams.put(" AND alias=?", Article.ALIAS_ARTICLE);
		pager.setSqlParams(sqlParams).setOrder(" ORDER BY ctime DESC");
		pager = articleDao.selectPager(pager);

		return pager != null ? pager.getList() : new ArrayList<Article>();
	}

	@Override
	public List<Article> getMostPvArticles(int size) {
		Pager<Article> pager = new Pager<Article>().openCounter(1, size)
				.closeCounter();
		Map<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put(" AND hidden=?", Article.HIDDEN_NONE);
		sqlParams.put(" AND alias=?", Article.ALIAS_ARTICLE);
		pager.setSqlParams(sqlParams).setOrder(" ORDER BY pv DESC");
		pager = articleDao.selectPager(pager);

		return pager != null ? pager.getList() : new ArrayList<Article>();
	}

}
