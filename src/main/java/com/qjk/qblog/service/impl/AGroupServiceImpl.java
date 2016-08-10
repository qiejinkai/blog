package com.qjk.qblog.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.qjk.qblog.dao.IAGroupDao;
import com.qjk.qblog.data.AGroup;
import com.qjk.qblog.service.IAGroupSerivce;

@Service
public class AGroupServiceImpl implements IAGroupSerivce {

	@Resource
	IAGroupDao groupDao;

	@Cacheable(cacheNames = "artilceConfig",key="'all_group'")
	public List<AGroup> findGroupList() {

		return groupDao.selectAGroupList();
	}

	@CacheEvict(cacheNames="artilceConfig",key="'all_group'")
	public AGroup addAGroup(AGroup aGroup) {

		Assert.notNull(aGroup, "未找到分组对象");

		AGroup g = groupDao.findAGroupByName(aGroup.getName());

		Assert.isNull(g, "分组名称已存在");

		groupDao.addAGroup(aGroup);

		return aGroup;
	}

	@Cacheable(cacheNames = "artilceConfig",key="'group:id:'+#groupId")
	public AGroup findGroupById(long groupId) {
		if(groupId != 0){
			return groupDao.findAGroupById(groupId);
		}
		return null;
	}

}
