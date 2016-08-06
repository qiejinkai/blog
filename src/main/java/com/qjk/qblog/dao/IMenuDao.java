package com.qjk.qblog.dao;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qjk.qblog.data.Menu;

@Transactional(propagation=Propagation.SUPPORTS)
public interface IMenuDao {

	void addMenu(Menu menu);
	
	void updateMenu(Menu menu);
	
	Menu findMenuById(long id);
	
	Menu findMenuByName(String name);
	
	void deleteMenu(long id);

	Menu findRootMenuByName(String name);
	
	
	
}
