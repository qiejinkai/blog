package com.qjk.qblog.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.qjk.qblog.dao.IArticleDao;
import com.qjk.qblog.data.Article;
import com.qjk.qblog.data.Pager;
import com.qjk.qblog.service.IArticleService;

@Service
public class ArticleServiceImpl implements IArticleService {

	@Resource
	IArticleDao articleDao;

	public Pager<Article> getArticlesPager(int pageIndex, long groupId) {
		Pager<Article> pager = new Pager<Article>().openCounter(pageIndex);
		Map<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put(" AND alias=?", Article.ALIAS_ARTICLE);
		sqlParams.put(" AND groupId=?", groupId);
		pager.setSqlParams(sqlParams).setOrder(" ORDER BY ctime DESC");
		pager = articleDao.selectPager(pager);

		return pager;
	}

	@Override
	public Article findArticleById(long id) {
		Article article = articleDao.findArticleById(id);
		
		// Assert.isTrue(article.getHidden() == Article.HIDDEN_NONE,"文章不可见");

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
		}

		object.setAlias(article.getAlias());
		object.setAuthor(article.getAuthor());
		object.setContent(article.getContent());
		object.setGroupId(article.getGroupId());
		object.setHidden(article.getHidden());
		object.setImage(article.getImage());
		object.setContentType(article.getContentType());
		object.setMtime(now);
		object.setSummary(article.getSummary());
		object.setTags(article.getTags());
		object.setTitle(article.getTitle());
		object.setLink(article.getLink());

		articleDao.updateArticle(object);

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
		}

	}

	@Override
	public void hiddenArticleById(long id) {
		if (id != 0) {

			Article article = articleDao.findArticleById(id);

			if (article != null) {
				article.setHidden(Math.abs(article.getHidden() - 1));
				articleDao.updateArticle(article);
			}

		}

	}

}
