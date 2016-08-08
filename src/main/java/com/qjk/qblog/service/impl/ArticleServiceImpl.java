package com.qjk.qblog.service.impl;

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

	public Pager<Article> getArticles(int pageIndex, long groupId) {
		Pager<Article> pager = new Pager<Article>().openCounter(pageIndex);
		Map<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put(" AND alias=?", Article.ALIAS_ARTICLE);
		sqlParams.put(" AND hidden = ?", Article.HIDDEN_NONE);
		sqlParams.put(" AND groupId=?", groupId);
		pager.setSqlParams(sqlParams).setOrder(" ORDER BY ctime DESC");
		pager = articleDao.selectPager(pager);

		return pager;
	}

	@Override
	public Article findArticleById(long id) {
		Article article = articleDao.findArticleById(id);

		Assert.notNull(article, "未找到文章");
		Assert.isTrue(article.getAlias().equals(Article.ALIAS_ARTICLE),
				"错误的文章类型");
		// Assert.isTrue(article.getHidden() == Article.HIDDEN_NONE,"文章不可见");

		return article;
	}

}
