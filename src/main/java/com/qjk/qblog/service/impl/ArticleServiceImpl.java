package com.qjk.qblog.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.qjk.qblog.util.Value;
import com.sun.tools.doclets.internal.toolkit.util.Group;

@Service
public class ArticleServiceImpl implements IArticleService {

	@Resource
	IArticleDao articleDao;
	@Resource
	IAGroupDao groupDao;
	
	private Pager<Article> articlesPager(int pageIndex,String alias, long groupId,int hidden,String keywords) {
		Pager<Article> pager = new Pager<Article>().openCounter(pageIndex);
		Map<String, Object> sqlParams = new LinkedHashMap<String, Object>();
		if(!Value.isEmpty(alias)){
			sqlParams.put(" AND alias=?",alias);
		}
		if(hidden != Article.HIDDEN_ALL){
			sqlParams.put(" AND hidden=?", hidden);
		}
		if (groupId != 0) {
			sqlParams.put(" AND group.groupId=?", groupId);
		}
		if (!Value.isEmpty(keywords)) {

			sqlParams.put(" AND ( title like ? ", "%" + keywords + "%");
			sqlParams.put(" OR tags like ? ) ", "%" + keywords + "%");
		}
		pager.setSqlParams(sqlParams).setOrder(" ORDER BY ctime DESC");
		pager = articleDao.selectPager(pager);
		return pager;
	}

	public Pager<Article> getArticlesPager(int pageIndex, long groupId) {
		

		return articlesPager(pageIndex, Article.ALIAS_ARTICLE, groupId, Article.HIDDEN_NONE, "");
	}

	public Pager<Article> getAllArticlesPager(int pageIndex, long groupId) {
		return articlesPager(pageIndex,Article.ALIAS_ARTICLE ,groupId, Article.HIDDEN_ALL, "");
	}
	public Pager<Article> getAllArticlesPager(int pageIndex, String alias) {
		return articlesPager(pageIndex,alias, 0, Article.HIDDEN_ALL, "");
	}

	@Override
	public Pager<Article> getArticlesPager(int pageIndex, long groupId,
			String keywords) {
		
		return articlesPager(pageIndex,Article.ALIAS_ARTICLE, groupId, Article.HIDDEN_NONE, keywords);
	}

	@Override
	public Article findArticleById(long id) {
		Article article = articleDao.findArticleById(id);
		if(article != null){
			articleDao.addPv(id);
		}

		return article;
	}	

	@CacheEvict(cacheNames = "artilceConfig", key = "'home_show_article'")
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

		return articlesPager(pageIndex, alias, 0, Article.HIDDEN_NONE, "");
	}

	@CacheEvict(cacheNames = "artilceConfig", key = "'home_show_article'")
	public void deleteArticleById(long id) {

		if (id != 0) {
			articleDao.deleteArticle(id);
			groupDao.refreshCount();
		}

	}

	@CacheEvict(cacheNames = "artilceConfig", key = "'home_show_article'")
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
		sqlParams.put(" AND alias=?", alias);
		sqlParams.put(" AND hidden=?", Article.HIDDEN_NONE);
		pager.setSqlParams(sqlParams).setOrder(" ORDER BY ctime DESC");
		pager = articleDao.selectPager(pager);

		return pager != null ? pager.getList() : new ArrayList<Article>();
	}

	@Override
	public List<Article> getLastestArticles(int size) {

		return getLastestArticles(size, Article.ALIAS_ARTICLE);
	}

	@Override
	public List<Article> getMostPvArticles(int size) {
		return getMostPvArticles(size, Article.ALIAS_ARTICLE);
	}

	@Override
	public Article getNextArticle(long ctime, String alias) {
		Pager<Article> pager = new Pager<Article>().openCounter(1, 1)
				.closeCounter();
		Map<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put(" AND hidden=?", Article.HIDDEN_NONE);
		sqlParams.put(" AND alias=?", alias);
		sqlParams.put(" AND ctime > ?", ctime);
		pager.setSqlParams(sqlParams).setOrder(" ORDER BY ctime");
		pager = articleDao.selectPager(pager);

		return pager != null && pager.getList() != null  && pager.getList().size()>0 ? pager.getList()
				.get(0) : null;

	}

	@Override
	public Article getPrevArticle(long ctime, String alias) {
		Pager<Article> pager = new Pager<Article>().openCounter(1, 1)
				.closeCounter();
		Map<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put(" AND hidden=?", Article.HIDDEN_NONE);
		sqlParams.put(" AND alias=?", alias);
		sqlParams.put(" AND ctime < ?", ctime);
		pager.setSqlParams(sqlParams).setOrder(" ORDER BY ctime DESC");
		pager = articleDao.selectPager(pager);

		return pager != null && pager.getList() != null && pager.getList().size()>0 ? pager.getList()
				.get(0) : null;
	}

	@Override
	public List<Article> getLastestArticles(int size, String alias) {
		Pager<Article> pager = new Pager<Article>().openCounter(1, size)
				.closeCounter();
		Map<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put(" AND hidden=?", Article.HIDDEN_NONE);
		sqlParams.put(" AND alias=?", alias);
		pager.setSqlParams(sqlParams).setOrder(" ORDER BY ctime DESC");
		pager = articleDao.selectPager(pager);

		return pager != null ? pager.getList() : new ArrayList<Article>();
	}

	@Override
	public List<Article> getMostPvArticles(int i, String alias) {
		Pager<Article> pager = new Pager<Article>().openCounter(1, i)
				.closeCounter();
		Map<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put(" AND hidden=?", Article.HIDDEN_NONE);
		sqlParams.put(" AND alias=?", alias);
		pager.setSqlParams(sqlParams).setOrder(" ORDER BY pv DESC");
		pager = articleDao.selectPager(pager);

		return pager != null ? pager.getList() : new ArrayList<Article>();
	}


}
