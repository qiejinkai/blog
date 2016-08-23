package com.qjk.qblog.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qjk.qblog.data.Pager;
import com.qjk.qblog.data.User;

@Transactional(propagation=Propagation.SUPPORTS)
public interface IUserDao {
	
	public User join(String nick,String logo);
	
	public void addUser(User user);
	
	public void deleteUser(long id);
	
	public User findUserById(long id);
	
	public void updateUser(User user);
	
	public List<User> selectUsers();

	public User findUserByAccount(String account);

	public User findUserByWxid(long wxid);

	public Pager<User> selectPager(Pager<User> pager);
	
}
