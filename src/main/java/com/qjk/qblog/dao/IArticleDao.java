package com.qjk.qblog.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qjk.qblog.data.Article;
import com.qjk.qblog.data.Pager;

@Transactional(propagation=Propagation.SUPPORTS)
public interface IArticleDao {

	void addArticle(Article article);
	
	void updateArticle(Article article);
	
	void deleteArticle(long id);
	
	Pager<Article> selectPager(Pager<Article> pager);
	
	List<Article> selectArticleList();
	
	Article findArticleById(long id);
	
}
