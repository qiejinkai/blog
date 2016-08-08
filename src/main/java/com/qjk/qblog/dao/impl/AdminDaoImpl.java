package com.qjk.qblog.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qjk.qblog.dao.IAdminDao;
import com.qjk.qblog.data.Admin;

@Repository
public class AdminDaoImpl extends BaseDaoImpl<Admin> implements IAdminDao{

	@Override
	public Admin addAdmin(Admin admin) {
		this.save(admin);
		return admin;
	}

	@Override
	public Admin updateAdmin(Admin admin) {
		this.update(admin);
		return admin;
	}

	@Override
	public void deleteAdmin(long id) {
		
		this.delete(id);
		
	}

	@Override
	public Admin findAdminById(long id) {
		
		return this.findAdminById(id);
	}

	@Override
	public Admin findAdminByAccount(String account) {
		
		return this.findOneByHQL("from Admin where account=?", account);
	}

	@Override
	public List<Admin> queryAdminList() {
		// TODO Auto-generated method stub
		return this.findListByHQL("from Admin", null);
	}

	@Override
	protected Class<Admin> getDataClass() {
		
		return Admin.class;
	}

}
