package com.qjk.qblog.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qjk.qblog.dao.IUserDao;
import com.qjk.qblog.data.Article;
import com.qjk.qblog.data.Pager;
import com.qjk.qblog.data.User;
import com.qjk.qblog.exception.UserException;
import com.qjk.qblog.service.IUserService;
import com.qjk.qblog.util.DigestUtil;
import com.qjk.qblog.util.Value;

@Service
public class UserServiceImpl implements IUserService {

	@Resource
	private IUserDao userDao;

	public void addUser(User user) {

		String password = user.getPassword();

		if (!Value.isEmpty(password)) {
			user.setPassword(DigestUtil.encodePassword(password));
		}

		userDao.addUser(user);

	}

	public void updateUser(User user) {
		userDao.updateUser(user);
	}

	public void deleteUser(User user) {
		if (user != null && user.getUid() != 0) {
			userDao.deleteUser(user.getUid());
		}

	}

	public User findUserById(long id) {
		User user = userDao.findUserById(id);
		user.setPassword(null);
		return user;

	}

	public List<User> queryUser() {
		return userDao.selectUsers();
	}

	public User login(String account, String password, String ip)
			throws UserException {

		if (Value.isEmpty(account)) {
			throw new UserException("请输入手机号或email");
		}
		if (Value.isEmpty(password)) {
			throw new UserException("请输入密码");
		}

		User user = null;

		user = userDao.findUserByAccount(account);

		if (user == null) {
			throw new UserException("用户不存在");
		}
		if (!DigestUtil.encodePassword(password).equals(user.getPassword())) {
			throw new UserException("密码不正确");
		}

		user.setLastLoginIp(user.getLoginIp());
		user.setLoginIp(ip);
		user.setLastLoginTime(user.getLoginTime());
		user.setLoginTime(new Date().getTime() / 1000);

		return user;

	}

	public User joinEmail(String email, String password) throws UserException {

		if (Value.isEmpty(email)) {
			throw new UserException("请输入email");
		}
		if (Value.isEmpty(password)) {
			throw new UserException("请输入密码");
		}

		User user = null;

		user = userDao.findUserByAccount(email);

		if (user != null) {
			throw new UserException("email已被使用");
		}

		user = new User();
		user.setEmail(email);
		user.setPassword(DigestUtil.encodePassword(password));
		user.setNick(email);

		userDao.addUser(user);

		return user;

	}

	public User joinPhone(String phone, String password) throws UserException {

		if (Value.isEmpty(phone)) {
			throw new UserException("请输入手机号");
		}
		if (Value.isEmpty(password)) {
			throw new UserException("请输入密码");
		}

		User user = null;

		user = userDao.findUserByAccount(phone);

		if (user != null) {
			throw new UserException("手机号已被使用");
		}

		user = new User();
		user.setPhone(phone);
		user.setPassword(DigestUtil.encodePassword(password));
		user.setNick(phone);
		userDao.addUser(user);

		return user;
	}

	@Override
	public Pager<User> getAllUser(int pageIndex, String keywords) {
		Pager<User> pager = new Pager<User>().openCounter(pageIndex, 20);
		Map<String, Object> sqlParams = new LinkedHashMap<String, Object>();
		if (!Value.isEmpty(keywords)) {

			sqlParams.put(" AND nick like ? ", "%" + keywords + "%");
		}
		pager.setSqlParams(sqlParams).setOrder(" ORDER BY ctime DESC");
		pager = userDao.selectPager(pager);
		return pager;
	}
	
	

}
