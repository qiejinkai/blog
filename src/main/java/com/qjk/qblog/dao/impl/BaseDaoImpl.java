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
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.qjk.qblog.data.Pager;
import com.qjk.qblog.util.SerializeUtil;
import com.qjk.qblog.util.Value;

@Repository
public abstract class BaseDaoImpl<T> {

	@Resource
	protected SessionFactory sessionFactory;

	@Resource
	protected RedisTemplate<String, T> redisTemplate;

	protected abstract Class<T> getDataClass();

	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	protected void save(T t) {
		this.getSession().saveOrUpdate(t);
	}

	protected void update(T t) {
		this.getSession().saveOrUpdate(t);
	}

	protected T findById(Serializable id) {
		return this.getSession().get(getDataClass(), id);
	}

	protected void delete(Serializable id) {
		this.getSession().delete(findById(id));
	}

	protected List<T> findListByHQL(String hql, Object... params) {
		Query query = this.getSession().createQuery(hql);
		for (int i = 0; params != null && i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		return query.list();
	}

	protected T findOneByHQL(String hql, Object... params) {
		Query query = this.getSession().createQuery(hql);
		for (int i = 0; params != null && i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		return (T) query.uniqueResult();
	}

	protected long count(String hql, Object... params) {
		hql = "select count(*) " + hql;
		Query query = this.getSession().createQuery(hql);
		for (int i = 0; params != null && i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		return (Long) query.uniqueResult();
	}

	protected List<T> findListByHQLLimit(String hql, Object[] params,
			int firstResult, int maxResults) {
		Query query = this.getSession().createQuery(hql);
		for (int i = 0; params != null && i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		return query.list();
	}

	protected Pager<T> queryPager(Pager<T> pager) {

		StringBuilder hql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		List<T> list = null;
		hql.append(" from ").append(getDataClass().getSimpleName())
				.append(" where 1=1");

		if (pager != null) {
			
			Map<String, Object> fieldParamsMap = pager.getFieldParams();

			if (fieldParamsMap != null && fieldParamsMap.size() > 0) {

				Iterator<Entry<String, Object>> it = fieldParamsMap.entrySet()
						.iterator();
				while (it.hasNext()) {
					Entry<String, Object> entry = it.next();

					hql.append("AND ").append(entry.getKey()).append("=? ");
					params.add(entry.getValue());
				}

			}

			Map<String, Object> sqlParamsMap = pager.getSqlParams();

			if (sqlParamsMap != null && sqlParamsMap.size() > 0) {

				Iterator<Entry<String, Object>> it = sqlParamsMap.entrySet()
						.iterator();
				while (it.hasNext()) {
					Entry<String, Object> entry = it.next();

					hql.append(entry.getKey()).append(" ");
					params.add(entry.getValue());
				}

			}

			if (pager.isCounter()) {
				int totalRows = (int) count(hql.toString(), params.toArray());
				pager.setTotalRows(totalRows).calculate();
				if (totalRows > 0) {

					if (!Value.isEmpty(pager.getOrder())) {
						hql.append(" ").append(pager.getOrder());
					}

					list = findListByHQLLimit(hql.toString(), params.toArray(),
							pager.getFistRowNum(), pager.getPageSize());
				}
			} else {

				if (!Value.isEmpty(pager.getOrder())) {
					hql.append(" ").append(pager.getOrder());
				}

				list = findListByHQLLimit(hql.toString(), params.toArray(),
						pager.getFistRowNum(), pager.getPageSize());
			}

			pager.setList(list);

		}

		return pager;
	}

	protected void execHQL(String hql, Object... params) {
		Query query = this.getSession().createQuery(hql);
		for (int i = 0; params != null && i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		query.executeUpdate();
	}

	protected void execSQL(String sql, Object... params) {
		Query query = this.getSession().createSQLQuery(sql);
		for (int i = 0; params != null && i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		query.executeUpdate();
	}

	protected void add2Redis(final String id, final T t) {

		redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] key = id.getBytes();
				byte[] value = SerializeUtil.serialize(t);
				return connection.setNX(key, value);
			}
		});

	}

	protected T getFromRedis(final String keyId) {
		T result = redisTemplate.execute(new RedisCallback<T>() {
			public T doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] key = keyId.getBytes();
				byte[] value = connection.get(key);
				if (value == null) {
					return null;
				}
				T t = (T) SerializeUtil.unserialize(value);
				return t;
			}
		});
		return result;
	}

	protected void deleteFromRedis(String key) {
		List<String> list = new ArrayList<String>();
		list.add(key);
		deleteFromRedis(list);
	}

	/**
	 * 删除多个
	 * 
	 * @param keys
	 */
	protected void deleteFromRedis(List<String> keys) {
		redisTemplate.delete(keys);
	}

	protected boolean updateFromRedis(final String id, final T t) {

		if (getFromRedis(id) == null) {
			add2Redis(id, t);
			return true;
		}
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] key = id.getBytes();
				byte[] value = SerializeUtil.serialize(t);
				connection.set(key, value);
				return true;
			}
		});
		return result;
	}
}
