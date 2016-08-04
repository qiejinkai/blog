package com.qjk.qblog.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qjk.qblog.data.Setting;

@Transactional(propagation = Propagation.SUPPORTS)
public interface ISettingDao {
	
	
	void addSetting(Setting setting);
	
	void updateSetting(Setting setting);
	
	List<Setting> selectSettingList();
	
	void deleteSetting (long id);
	
	Setting getSettingById(long id);
	
	Setting getSettingByName(String name);
	
	
}
