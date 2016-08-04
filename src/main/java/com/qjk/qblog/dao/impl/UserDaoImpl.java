package com.qjk.qblog.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qjk.qblog.dao.IUserDao;
import com.qjk.qblog.data.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {

	public void addUser(User user) {
		
		this.save(user);
		
	}

	public void deleteUser(long id) {
		
		this.delete( id);
	}

	public User findUserById(long id) {
		
		return this.findById( id);
	}

	public void updateUser(User user) {
		this.update(user);
		
	}

	public List<User> selectUsers() {
		
		return this.findListByHQL("from User", null);
	}

	public User findUserByAccount(String account) {
		
		return this.findOneByHQL("from User u where u.phone =? or u.email =?", account,account);
	}
	
	@Override
	protected Class<User> getDataClass() {
		
		return User.class;
	}
}
