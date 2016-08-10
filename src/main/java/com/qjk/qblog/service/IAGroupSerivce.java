package com.qjk.qblog.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.qjk.qblog.data.AGroup;

@Transactional
public interface IAGroupSerivce {
	
	List<AGroup> findGroupList();
	
	AGroup addAGroup(AGroup aGroup);

	AGroup findGroupById(long groupId);
}
