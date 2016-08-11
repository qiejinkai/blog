package com.qjk.qblog.service;

import java.util.List;

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

	void homeShowArticleById(long id);

	List<Article> getHomeShowArticles();
	
	List<Article> getLastestArticles(int size);
	
	List<Article> getMostPvArticles(int size);
	
	List<Article> getArticles(int pageIndex,int size,String alias);

	Pager<Article> getArticlesPager(int pageIndex, long groupId, String keywords);

	Article getPrevArticle(long ctime, String alias);

	Article getNextArticle(long ctime, String alias);

	List<Article> getLastestArticles(int i, String aliasDiary);

	List<Article> getMostPvArticles(int i, String aliasDiary);
}
