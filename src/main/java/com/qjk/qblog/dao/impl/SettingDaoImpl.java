package com.qjk.qblog.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qjk.qblog.dao.ISettingDao;
import com.qjk.qblog.data.Setting;
import com.qjk.qblog.util.RedisUtil;

@Repository
public class SettingDaoImpl extends BaseDaoImpl<Setting> implements ISettingDao {

	@Override
	public void addSetting(Setting setting) {

		this.save(setting);

		this.add2Redis(RedisUtil.getKey(Setting.class, setting.getName()),
				setting);
	}

	@Override
	public void updateSetting(Setting setting) {

		if (setting == null || setting.getSettingId() == 0) {
			return;
		}
		this.update(setting);

		this.updateFromRedis(
				RedisUtil.getKey(getDataClass(), setting.getName()), setting);

		// this.execHQL(
		// "update from Setting set name=?,types=?,summary=?,mtime=?,version=? where settingId=?",
		// setting.getName(),setting.getTypes(),setting.getSummary(),setting.getMtime(),setting.getVersion(),setting.getSettingId());
	}

	@Override
	public List<Setting> selectSettingList() {

		return this.findListByHQL("from Setting ", null);
	}

	@Override
	public void deleteSetting(long id) {

		this.delete(id);
	}

	@Override
	protected Class<Setting> getDataClass() {

		return Setting.class;
	}

	@Override
	public Setting getSettingById(long id) {

		return this.findById(id);
	}

	@Override
	public Setting getSettingByName(String name) {

		Setting setting = this.getFromRedis(RedisUtil.getKey(getDataClass(),
				name));

		if (setting == null) {
			setting = this.findOneByHQL("from Setting where name=?", name);
			if (setting != null) {
				this.add2Redis(
						RedisUtil.getKey(Setting.class, setting.getName()),
						setting);
			}
			return setting;
		}

		return setting;
	}

}
