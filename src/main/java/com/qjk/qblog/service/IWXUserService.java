package com.qjk.qblog.service;

import org.springframework.transaction.annotation.Transactional;

import com.qjk.qblog.data.Pager;
import com.qjk.qblog.data.WXUser;
import com.qjk.qblog.data.User;

@Transactional
public interface IWXUserService {
	
	WXUser getWXUserByOpenId(String openId);
	
	User join(WXUser user);
	
	User login(String openId,String ip);
	
	WXUser updateWXUser(WXUser user);

	WXUser findWXUserById(long wxid);

	Pager<WXUser> getAllWXUser(int pageIndex, String keywords);
}
