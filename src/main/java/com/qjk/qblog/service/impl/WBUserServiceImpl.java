package com.qjk.qblog.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.qjk.qblog.dao.IWBUserDao;
import com.qjk.qblog.dao.IUserDao;
import com.qjk.qblog.data.Pager;
import com.qjk.qblog.data.QQUser;
import com.qjk.qblog.data.WBUser;
import com.qjk.qblog.data.User;
import com.qjk.qblog.service.IWBUserService;
import com.qjk.qblog.util.Value;

@Service
public class WBUserServiceImpl implements IWBUserService {
	
	Logger logger = Logger.getLogger(WBUserServiceImpl.class);

	@Resource
	IWBUserDao WBUserDao;
	@Resource
	IUserDao userDao;

	@Override
	public WBUser getWBUserByOpenId(String openId) {

		Assert.hasText(openId, "未找到openId");

		return WBUserDao.findWBUserbyOpenId(openId);
	}

	@Override
	public User join(WBUser wbUser) {

		String openId = wbUser.getOpenid();
		WBUser u = WBUserDao.findWBUserbyOpenId(openId);
		User user = null;

		if (u == null) {

			wbUser.setCtime(new Date().getTime() / 1000);
			WBUserDao.addWBUser(wbUser);
			user = new User();
			user.setCtime(new Date().getTime() / 1000);
			user.setAccount(wbUser.getOpenid());
			user.setNick(wbUser.getNick());
			user.setLogo(wbUser.getLogo());
			user.setWbUser(wbUser);
			userDao.updateUser(user);
		} else {
			u.setAccess_token(wbUser.getAccess_token());
			u.setLocation(wbUser.getLocation());
			u.setPerson_host(wbUser.getPerson_host());
			u.setExpires_in(wbUser.getExpires_in());
			u.setRefresh_token(wbUser.getRefresh_token());
			u.setNick(wbUser.getNick());
			u.setLogo(wbUser.getLogo());
			u.setGender(wbUser.getGender());
			WBUserDao.updateWBUser(u);

			user = userDao.findUserByWbid(u.getWbid());
			user.setNick(u.getNick());
			user.setLogo(u.getLogo());
			userDao.updateUser(user);
		}

		return user;
	}

	@Override
	public User login(String openId, String ip) {

		Assert.hasText(openId, "未找到openId");

		WBUser WBUser = WBUserDao.findWBUserbyOpenId(openId);

		Assert.notNull(WBUser, "未找到微博用户");

		WBUser.setLastLoginIp(WBUser.getLoginIp());
		WBUser.setLastLoginTime(WBUser.getLoginTime());
		WBUser.setLoginIp(ip);
		WBUser.setLoginTime(new Date().getTime() / 1000);

		User user = userDao.findUserByWbid(WBUser.getWbid());
		
		if (user == null) {
			user = new User();
			user.setWbUser(WBUser);
			user.setCtime(new Date().getTime()/1000);
		}
		user.setLastLoginIp(user.getLoginIp());
		user.setLastLoginTime(user.getLoginTime());
		user.setLoginIp(ip);
		user.setLoginTime(new Date().getTime() / 1000);


		WBUserDao.updateWBUser(WBUser);
		userDao.updateUser(user);
		return user;
	}

	@Override
	public WBUser updateWBUser(WBUser user) {
		if (user != null) {
			WBUserDao.updateWBUser(user);
		}
		return null;
	}
	
	@Override
	public WBUser findWBUserById(long wxid) {
		
		return WBUserDao.findWBUserbyId(wxid);
	}
	
	@Override
	public Pager<WBUser> getAllWBUser(int pageIndex, String keywords) {
		Pager<WBUser> pager = new Pager<WBUser>().openCounter(pageIndex, 20);
		Map<String, Object> sqlParams = new LinkedHashMap<String, Object>();
		if (!Value.isEmpty(keywords)) {

			sqlParams.put(" AND nick like ? ", "%" + keywords + "%");
		}
		pager.setSqlParams(sqlParams).setOrder(" ORDER BY ctime DESC");
		pager = WBUserDao.selectPager(pager);
		return pager;
	}

}
