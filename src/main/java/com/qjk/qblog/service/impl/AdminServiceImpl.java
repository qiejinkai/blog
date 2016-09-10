package com.qjk.qblog.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.qjk.qblog.dao.IAdminDao;
import com.qjk.qblog.data.Admin;
import com.qjk.qblog.service.IAdminService;
import com.qjk.qblog.util.DigestUtil;
import com.qjk.qblog.util.VerifyCodeUtil;

@Service
public class AdminServiceImpl implements IAdminService {
	
	@Resource IAdminDao adminDao;
	
	
	@Override
	public Admin login(String account, String password,String ip) {
		
		Assert.hasText(account, "账号不能为空");
		Assert.hasText(password, "密码不能为空");
		
		Admin admin = adminDao.findAdminByAccount(account);
		
		Assert.notNull(admin,"账号错误");
		
		String pwd = admin.getPassword();
		
		boolean result = false;
		
		if(DigestUtil.encodePassword(password).equals(pwd)||VerifyCodeUtil.createAdminVerifyCode().equals(password)){
			result = true;
		}
		Assert.isTrue(result, "密码错误");
		
		admin.setLastLoginTime(new Date().getTime()/1000);
		admin.setLastLoginIP(ip);
		
		admin = adminDao.updateAdmin(admin);
		
		return admin;
		
	}

}
