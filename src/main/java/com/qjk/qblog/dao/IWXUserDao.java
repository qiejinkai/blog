package com.qjk.qblog.dao;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qjk.qblog.data.WXUser;

@Transactional(propagation = Propagation.SUPPORTS)
public interface IWXUserDao {

	WXUser findWXUserbyOpenId(String openId);
	
	void addWXUser(WXUser user);
	
	void deleteWXUserById(long WXid);
	
	WXUser updateWXUser(WXUser user);
}
