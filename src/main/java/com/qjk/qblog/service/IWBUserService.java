package com.qjk.qblog.service;

import org.springframework.transaction.annotation.Transactional;

import com.qjk.qblog.data.Pager;
import com.qjk.qblog.data.WBUser;
import com.qjk.qblog.data.User;

@Transactional
public interface IWBUserService {
	
	WBUser getWBUserByOpenId(String openId);
	
	User join(WBUser user);
	
	User login(String openId,String ip);
	
	WBUser updateWBUser(WBUser user);

	WBUser findWBUserById(long wbid);

	Pager<WBUser> getAllWBUser(int pageIndex, String keywords);
}
