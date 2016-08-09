package com.qjk.qblog.service;

import org.springframework.transaction.annotation.Transactional;

import com.qjk.qblog.data.Article;
import com.qjk.qblog.data.Pager;

@Transactional
public interface IArticleService {

	Pager<Article> getArticlesPager(int pageIndex, long groupId);

	Pager<Article> getArticlesPager(int pageIndex,String alias);

	Article findArticleById(long id);

	Article saveOrUpdateArticle(Article article);

	void deleteArticleById(long id);

	void hiddenArticleById(long id);
}
