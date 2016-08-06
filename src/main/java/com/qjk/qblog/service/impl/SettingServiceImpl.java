package com.qjk.qblog.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.qjk.qblog.dao.ISettingDao;
import com.qjk.qblog.data.Setting;
import com.qjk.qblog.service.ISettingService;

@Service
public class SettingServiceImpl implements ISettingService {
	
	@Resource
	private ISettingDao settingDao;

	@Cacheable(cacheNames="systemConfig2",key="T(com.qjk.qblog.util.RedisUtil).getKey('setting',#name)")
	public Setting getSettingByName(String name) {
		
		Assert.hasText(name, "name 不能为空");
		
		return settingDao.getSettingByName(name);
	}

	@Override
	public void deleteSettingByName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteSettingById(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Setting updateSetting(Setting setting) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Setting addSetting(Setting setting) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Setting> getSettings() {
		// TODO Auto-generated method stub
		return null;
	}

}
