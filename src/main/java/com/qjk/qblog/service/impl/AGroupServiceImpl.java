package com.qjk.qblog.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.qjk.qblog.dao.IAGroupDao;
import com.qjk.qblog.data.AGroup;
import com.qjk.qblog.service.IAGroupSerivce;

@Service
public class AGroupServiceImpl implements IAGroupSerivce {

	@Resource
	IAGroupDao groupDao;

	@Cacheable(cacheNames = "artilceConfig")
	public List<AGroup> findGroupList() {

		return groupDao.selectAGroupList();
	}

}
