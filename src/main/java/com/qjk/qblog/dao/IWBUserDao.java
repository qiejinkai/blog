package com.qjk.qblog.dao;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qjk.qblog.data.Pager;
import com.qjk.qblog.data.WBUser;

@Transactional(propagation = Propagation.SUPPORTS)
public interface IWBUserDao {

	WBUser findWBUserbyOpenId(String openId);
	
	void addWBUser(WBUser user);
	
	void deleteWBUserById(long WXid);
	
	WBUser updateWBUser(WBUser user);

	WBUser findWBUserbyId(long wbid);

	Pager<WBUser> selectPager(Pager<WBUser> pager);
}
