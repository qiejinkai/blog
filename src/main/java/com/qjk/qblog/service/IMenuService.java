package com.qjk.qblog.service;

import org.springframework.transaction.annotation.Transactional;

import com.qjk.qblog.data.Menu;

@Transactional
public interface IMenuService {
	
	Menu getAdminMenu();
}
