package com.qjk.qblog.dao.impl;

import org.springframework.stereotype.Repository;

import com.qjk.qblog.dao.IQQUserDao;
import com.qjk.qblog.data.Pager;
import com.qjk.qblog.data.QQUser;

@Repository
public class QQUserDaoImpl extends BaseDaoImpl<QQUser> implements IQQUserDao {

	@Override
	public QQUser findQQUserbyOpenId(String openId) {
		String hql = "from QQUser where openId=?";
		return this.findOneByHQL(hql, openId);
	}

	@Override
	public void addQQUser(QQUser user) {

		this.save(user);
	}

	@Override
	public void deleteQQUserById(long qqid) {
		this.delete(qqid);

	}

	@Override
	public QQUser findQQUserbyId(long qqid) {

		return this.findById(qqid);
	}

	@Override
	public QQUser updateQQUser(QQUser user) {
		this.update(user);
		return user;
	}

	@Override
	protected Class<QQUser> getDataClass() {

		return QQUser.class;
	}

	@Override
	public Pager<QQUser> selectPager(Pager<QQUser> pager) {

		return this.queryPager(pager);
	}

}
