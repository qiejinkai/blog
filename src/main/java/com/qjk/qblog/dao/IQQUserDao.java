package com.qjk.qblog.dao;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qjk.qblog.data.QQUser;

@Transactional(propagation = Propagation.SUPPORTS)
public interface IQQUserDao {

	QQUser findQQUserbyOpenId(String openId);
	
	void addQQUser(QQUser user);
	
	void deleteQQUserById(long qqid);
	
	QQUser updateQQUser(QQUser user);
}
