package com.qjk.qblog.service;

import org.springframework.transaction.annotation.Transactional;

import com.qjk.qblog.data.Admin;

@Transactional
public interface IAdminService {

	
	Admin login(String account,String password,String ip) ;
}
