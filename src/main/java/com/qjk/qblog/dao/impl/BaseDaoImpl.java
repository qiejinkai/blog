package com.qjk.qblog.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import sun.tools.tree.ThisExpression;

import com.qjk.qblog.data.Pager;

@Repository
public abstract class BaseDaoImpl<T> {

	@Resource
	protected SessionFactory sessionFactory;

	protected abstract Class<T> getDataClass();

	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	protected void save(T t) {
		this.getSession().save(t);
	}

	protected void update(T t) {
		this.getSession().update(t);
	}

	protected T findById(Serializable id) {
		return this.getSession().load(getDataClass(), id);
	}

	protected void delete( Serializable id) {
		this.getSession().delete(findById( id));
	}

	protected List<T> findListByHQL(String hql, Object... params) {
		Query query = this.getSession().createQuery(hql);
		for (int i = 0; params != null && i < params.length; i++) {
			query.setParameter(i, params);
		}
		return query.list();
	}

	protected T findOneByHQL(String hql, Object... params) {
		Query query = this.getSession().createQuery(hql);
		for (int i = 0; params != null && i < params.length; i++) {
			query.setParameter(i, params);
		}
		return (T) query.uniqueResult();
	}

	protected int count(String hql, Object... params) {
		hql = "select count(*) " + hql;
		Query query = this.getSession().createQuery(hql);
		for (int i = 0; params != null && i < params.length; i++) {
			query.setParameter(i, params);
		}
		return (Integer) query.uniqueResult();
	}

	protected List<T> findListByHQLLimit(String hql, Object[] params, int firstResult,
			int maxResults) {
		Query query = this.getSession().createQuery(hql);
		for (int i = 0; params != null && i < params.length; i++) {
			query.setParameter(i, params);
		}
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		return query.list();
	}

	protected Pager<T> queryPager(Pager<T> pager) {

		StringBuilder hql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		List<T> list = null;
		hql.append(" from ").append(getDataClass().getSimpleName()).append(" where 1=1");
		
		if (pager != null) {
			Map<String, Object> fieldParamsMap = pager.getFieldParams();

			if (fieldParamsMap != null && fieldParamsMap.size() >0) {

				Iterator<Entry<String, Object>> it = fieldParamsMap.entrySet()
						.iterator();
				while (it.hasNext()) {
					Entry<String, Object> entry = it.next();

					hql.append("AND ").append(entry.getKey()).append("=? ");
					params.add(entry.getValue());
				}

			}
			
			Map<String, Object> sqlParamsMap = pager.getSqlParams();

			if (sqlParamsMap != null && sqlParamsMap.size() >0) {

				Iterator<Entry<String, Object>> it = sqlParamsMap.entrySet()
						.iterator();
				while (it.hasNext()) {
					Entry<String, Object> entry = it.next();

					hql.append(entry.getKey()).append(" ");
					params.add(entry.getValue());
				}

			}
			
			if (pager.isCounter()) {
				int totalRows = count(hql.toString(), params.toArray());
				pager.setTotalRows(totalRows).calculate();

				list = findListByHQLLimit(hql.toString(), params.toArray(),
						pager.getFistRowNum(), pager.getPageSize());
			} else {
				
				list = findListByHQL(hql.toString(), params.toArray());
			}

			pager.setList(list);

		}

		return pager;
	}
	
	protected void execHQL(String hql,Object...params){
		Query query = this.getSession().createQuery(hql);
		for (int i = 0; params != null && i < params.length; i++) {
			query.setParameter(i, params);
		}
		query.executeUpdate();
	}
	
	protected void execSQL(String sql,Object...params){
		Query query = this.getSession().createSQLQuery(sql);
		for (int i = 0; params != null && i < params.length; i++) {
			query.setParameter(i, params);
		}
		query.executeUpdate();
	}
}
