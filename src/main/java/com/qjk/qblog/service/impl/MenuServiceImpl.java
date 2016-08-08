package com.qjk.qblog.service.impl;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.qjk.qblog.dao.IMenuDao;
import com.qjk.qblog.data.Menu;
import com.qjk.qblog.service.IMenuService;

@Service
public class MenuServiceImpl implements IMenuService {
	
	@Resource IMenuDao menuDao;


	@Cacheable(cacheNames="menuConfig",key="T(com.qjk.qblog.util.RedisUtil).getKey('menu','admin')")
	public Menu getAdminMenu() {
		
		return menuDao.findMenuByName("admin");
	}

}
