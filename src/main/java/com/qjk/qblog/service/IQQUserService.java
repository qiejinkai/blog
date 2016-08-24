package com.qjk.qblog.service;

import org.springframework.transaction.annotation.Transactional;

import com.qjk.qblog.data.Pager;
import com.qjk.qblog.data.QQUser;
import com.qjk.qblog.data.User;

@Transactional
public interface IQQUserService {
	
	QQUser getQQUserByOpenId(String openId);
	
	User join(QQUser user);
	
	User login(String openId,String ip);
	
	QQUser updateQQUser(QQUser user);

	Pager<QQUser> getAllQQUser(int pageIndex, String keywords);

	QQUser findQQUserById(long qqid);
}
