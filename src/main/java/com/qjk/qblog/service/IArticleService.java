package com.qjk.qblog.service;

import org.springframework.transaction.annotation.Transactional;

import com.qjk.qblog.data.Article;
import com.qjk.qblog.data.Pager;

@Transactional
public interface IArticleService {
	
	Pager<Article> getArticles(int pageIndex,long groupId);

	Article findArticleById(long id);
}
