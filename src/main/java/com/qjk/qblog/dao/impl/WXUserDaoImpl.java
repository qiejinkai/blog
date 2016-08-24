package com.qjk.qblog.dao.impl;

import org.springframework.stereotype.Repository;

import com.qjk.qblog.dao.IWXUserDao;
import com.qjk.qblog.data.Pager;
import com.qjk.qblog.data.WXUser;

@Repository
public class WXUserDaoImpl extends BaseDaoImpl<WXUser> implements IWXUserDao {

	@Override
	public WXUser findWXUserbyOpenId(String openId) {
		String hql = "from WXUser where openId=?";
		return this.findOneByHQL(hql, openId);
	}

	@Override
	public void addWXUser(WXUser user) {

		this.save(user);
	}

	@Override
	public void deleteWXUserById(long WXid) {
		this.delete(WXid);

	}

	@Override
	public WXUser updateWXUser(WXUser user) {
		this.update(user);
		return user;
	}

	@Override
	protected Class<WXUser> getDataClass() {

		return WXUser.class;
	}
	
	@Override
	public WXUser findWXUserbyId(long wxid) {
		
		return this.findById(wxid);
	}
	
	@Override
	public Pager<WXUser> selectPager(Pager<WXUser> pager) {
		return super.queryPager(pager);
	}

}
