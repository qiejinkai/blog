package com.qjk.qblog.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qjk.qblog.data.Admin;

@Transactional(propagation=Propagation.SUPPORTS)
public interface IAdminDao {
	
	Admin addAdmin(Admin admin);
	
	Admin updateAdmin(Admin admin);
	
	void deleteAdmin(long id);
	
	Admin findAdminById(long id);
	
	Admin findAdminByAccount(String account);
	
	List<Admin> queryAdminList();
	
	
}
