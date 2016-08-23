package com.qjk.qblog.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.qjk.qblog.data.Article;
import com.qjk.qblog.data.Pager;
import com.qjk.qblog.data.User;
import com.qjk.qblog.exception.UserException;

@Transactional
public interface IUserService {
	
	User joinPhone(String phone,String password) throws UserException;
	
	User joinEmail(String email,String password) throws UserException;
	
	void addUser(User user);
	
	void updateUser(User user);
	
	void deleteUser(User user);
	
	User login(String account,String password,String ip) throws UserException;
	
	User findUserById(long id);
	
	List<User> queryUser();

	Pager<User> getAllUser(int pageIndex, String keywords);
	
}
