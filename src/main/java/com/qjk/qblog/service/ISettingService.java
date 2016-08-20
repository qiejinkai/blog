package com.qjk.qblog.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.qjk.qblog.data.Setting;

@Transactional
public interface ISettingService {
	
	Setting getSettingByName(String name);
	
	void deleteSettingByName(String name);
	
	void deleteSettingById(long id);
	
	Setting updateSetting(Setting setting);
	
	Setting addSetting(Setting setting);
	
	List<Setting> getSettings();

	Setting getSettingById(long id);

	Map<String, String> getOptionsByName(String string);
	
}
