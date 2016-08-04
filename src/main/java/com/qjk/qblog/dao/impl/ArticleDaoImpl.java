package com.qjk.qblog.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Repository;

import com.qjk.qblog.dao.IArticleDao;
import com.qjk.qblog.data.Article;
import com.qjk.qblog.data.Pager;

@Repository
public class ArticleDaoImpl extends BaseDaoImpl<Article> implements IArticleDao {

	@Override
	public void addArticle(Article article) {

		this.save(article);
	}

	@Override
	public void updateArticle(Article article) {

		this.update(article);
	}

	@Override
	public void deleteArticle(long id) {

		this.delete( id);

	}

	@Override
	public Pager<Article> selectPager(Pager<Article> pager) {		
	
		if(pager != null){
			pager = this.queryPager(pager);
			return pager;
		}
		
		return null;
	}
	
	

	@Override
	public Article findArticleById(long id) {

		return this.findById( id);
	}

	@Override
	protected Class<Article> getDataClass() {
		
		return Article.class;
	}

	@Override
	public List<Article> selectArticleList() {
		
		return this.findListByHQL("from Article", null);
	}
	

}
