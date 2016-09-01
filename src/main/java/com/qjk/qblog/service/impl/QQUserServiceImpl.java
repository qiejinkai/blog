package com.qjk.qblog.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.qjk.qblog.dao.IQQUserDao;
import com.qjk.qblog.dao.IUserDao;
import com.qjk.qblog.data.Pager;
import com.qjk.qblog.data.QQUser;
import com.qjk.qblog.data.User;
import com.qjk.qblog.service.IQQUserService;
import com.qjk.qblog.util.Value;

@Service
public class QQUserServiceImpl implements IQQUserService {

	@Resource
	IQQUserDao qqUserDao;
	@Resource
	IUserDao userDao;

	@Override
	public QQUser getQQUserByOpenId(String openId) {

		Assert.hasText(openId, "未找到openId");

		return qqUserDao.findQQUserbyOpenId(openId);
	}

	@Override
	public User join(QQUser qquser) {

		String openId = qquser.getOpenid();
		QQUser u = qqUserDao.findQQUserbyOpenId(openId);
		User user = null;

		if (u == null) {

			qquser.setCtime(new Date().getTime() / 1000);
			qqUserDao.addQQUser(qquser);
			user = new User();
			user.setCtime(new Date().getTime() / 1000);
			user.setAccount(qquser.getOpenid());
			user.setNick(qquser.getNick());
			user.setLogo(qquser.getLogo());
			user.setQqUser(qquser);
			userDao.updateUser(user);
		} else {
			u.setAccess_token(qquser.getAccess_token());
			u.setExpires_in(qquser.getExpires_in());
			u.setRefresh_token(qquser.getRefresh_token());
			u.setNick(qquser.getNick());
			u.setLogo(qquser.getLogo());
			qqUserDao.updateQQUser(u);

			user = userDao.findUserByQqid(u.getQqid());
			user.setNick(qquser.getNick());
			user.setLogo(qquser.getLogo());
			userDao.updateUser(user);
		}

		return user;
	}

	@Override
	public User login(String openId, String ip) {

		Assert.hasText(openId, "未找到openId");

		QQUser qqUser = qqUserDao.findQQUserbyOpenId(openId);

		Assert.notNull(qqUser, "未找到QQ用户");

		qqUser.setLastLoginIp(qqUser.getLoginIp());
		qqUser.setLastLoginTime(qqUser.getLoginTime());
		qqUser.setLoginIp(ip);
		qqUser.setLoginTime(new Date().getTime() / 1000);

		qqUserDao.updateQQUser(qqUser);

		User user = userDao.findUserByQqid(qqUser.getQqid());

		if (user == null) {
			user = new User();
			user.setAccount(qqUser.getOpenid());
			user.setQqUser(qqUser);
			user.setCtime(new Date().getTime() / 1000);
			user.setNick(qqUser.getNick());
		}
		user.setLastLoginIp(user.getLoginIp());
		user.setLastLoginTime(user.getLoginTime());
		user.setLoginIp(ip);
		user.setLoginTime(new Date().getTime() / 1000);

		userDao.updateUser(user);
		return user;
	}

	@Override
	public QQUser updateQQUser(QQUser user) {
		if (user != null) {
			qqUserDao.updateQQUser(user);
		}
		return null;
	}

	@Override
	public Pager<QQUser> getAllQQUser(int pageIndex, String keywords) {
		Pager<QQUser> pager = new Pager<QQUser>().openCounter(pageIndex, 20);
		Map<String, Object> sqlParams = new LinkedHashMap<String, Object>();
		if (!Value.isEmpty(keywords)) {

			sqlParams.put(" AND nick like ? ", "%" + keywords + "%");
		}
		pager.setSqlParams(sqlParams).setOrder(" ORDER BY ctime DESC");
		pager = qqUserDao.selectPager(pager);
		return pager;
	}

	@Override
	public QQUser findQQUserById(long qqid) {

		return qqUserDao.findQQUserbyId(qqid);
	}
}
