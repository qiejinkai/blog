package com.qjk.qblog.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qjk.qblog.data.AGroup;

@Transactional(propagation = Propagation.SUPPORTS)
public interface IAGroupDao {
	void addAGroup(AGroup aGroup);

	void updateAGroup(AGroup aGroup);
	
	void refreshCount();

	AGroup findAGroupById(long id);
	
	AGroup findAGroupByName(String name);

	void deleteAGroup(long id);

	List<AGroup> selectAGroupList();
}
