package com.qjk.qblog.dao.impl;

import org.springframework.stereotype.Repository;

import com.qjk.qblog.dao.IWBUserDao;
import com.qjk.qblog.data.Pager;
import com.qjk.qblog.data.WBUser;

@Repository
public class WBUserDaoImpl extends BaseDaoImpl<WBUser> implements IWBUserDao {

	@Override
	public WBUser findWBUserbyOpenId(String openId) {
		String hql = "from WBUser where openId=?";
		return this.findOneByHQL(hql, openId);
	}

	@Override
	public void addWBUser(WBUser user) {

		this.save(user);
	}

	@Override
	public void deleteWBUserById(long wbid) {
		this.delete(wbid);

	}

	@Override
	public WBUser updateWBUser(WBUser user) {
		this.update(user);
		return user;
	}

	@Override
	protected Class<WBUser> getDataClass() {

		return WBUser.class;
	}
	
	@Override
	public WBUser findWBUserbyId(long wbid) {
		
		return this.findById(wbid);
	}
	
	@Override
	public Pager<WBUser> selectPager(Pager<WBUser> pager) {
		return super.queryPager(pager);
	}

}
