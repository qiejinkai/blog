package com.qjk.qblog.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.qjk.qblog.dao.IWXUserDao;
import com.qjk.qblog.dao.IUserDao;
import com.qjk.qblog.data.WXUser;
import com.qjk.qblog.data.User;
import com.qjk.qblog.service.IWXUserService;

@Service
public class WXUserServiceImpl implements IWXUserService {
	
	Logger logger = Logger.getLogger(WXUserServiceImpl.class);

	@Resource
	IWXUserDao WXUserDao;
	@Resource
	IUserDao userDao;

	@Override
	public WXUser getWXUserByOpenId(String openId) {

		Assert.hasText(openId, "未找到openId");

		return WXUserDao.findWXUserbyOpenId(openId);
	}

	@Override
	public User join(WXUser WXuser) {

		String openId = WXuser.getOpenid();
		WXUser u = WXUserDao.findWXUserbyOpenId(openId);
		User user = null;

		if (u == null) {

			WXuser.setCtime(new Date().getTime() / 1000);
			WXUserDao.addWXUser(WXuser);
			user = new User();
			user.setAccount(WXuser.getOpenid());
			user.setNick(WXuser.getNick());
			user.setLogo(WXuser.getLogo());
			user.setWxUser(WXuser);
			userDao.updateUser(user);
		} else {
			u.setAccess_token(WXuser.getAccess_token());
			u.setExpires_in(WXuser.getExpires_in());
			u.setRefresh_token(WXuser.getRefresh_token());
			u.setNick(WXuser.getNick());
			u.setLogo(WXuser.getLogo());
			u.setGender(WXuser.getGender());
			WXUserDao.updateWXUser(u);

			user = userDao.findUserByWxid(u.getWxid());
			user.setNick(WXuser.getNick());
			user.setLogo(WXuser.getLogo());
			userDao.updateUser(user);
		}

		return user;
	}

	@Override
	public User login(String openId, String ip) {

		Assert.hasText(openId, "未找到openId");

		WXUser WXUser = WXUserDao.findWXUserbyOpenId(openId);

		Assert.notNull(WXUser, "未找到微信用户");

		WXUser.setLastLoginIp(WXUser.getLoginIp());
		WXUser.setLastLoginTime(WXUser.getLoginTime());
		WXUser.setLoginIp(ip);
		WXUser.setLoginTime(new Date().getTime() / 1000);

		User user = userDao.findUserByWxid(WXUser.getWxid());
		
		if (user == null) {
			logger.info("login()   user is null");
			user = new User();
			user.setWxUser(WXUser);
			user.setCtime(new Date().getTime()/1000);
		}
		user.setLastLoginIp(user.getLoginIp());
		user.setLastLoginTime(user.getLoginTime());
		user.setLoginIp(ip);
		user.setLoginTime(new Date().getTime() / 1000);


		WXUserDao.updateWXUser(WXUser);
		userDao.updateUser(user);
		return user;
	}

	@Override
	public WXUser updateWXUser(WXUser user) {
		if (user != null) {
			WXUserDao.updateWXUser(user);
		}
		return null;
	}

}
