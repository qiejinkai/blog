package com.qjk.qblog.dao.impl;

import java.util.List;

import com.qjk.qblog.dao.IAGroupDao;
import com.qjk.qblog.data.AGroup;

public class AGroupDaoImpl extends BaseDaoImpl<AGroup> implements IAGroupDao{

	@Override
	public void addAGroup(AGroup aGroup) {
		
		this.save(aGroup);
	}

	@Override
	public void updateAGroup(AGroup aGroup) {
		
		this.update(aGroup);
	}

	@Override
	public void refreshCount(long id) {
		String hql = "update from AGroup a set a.count = (select count(1) from Acticle u where u.groupId = ?);";
		this.execHQL(hql, id);;
		
	}

	@Override
	public AGroup findAGroupById(long id) {
		
		return this.findById(id);
	}

	@Override
	public void deleteAGroup(long id) {
		this.delete(id);
		
	}

	@Override
	public List<AGroup> selectAGroupList() {
		
		return this.findListByHQL("from AGroup ", null);
	}

	@Override
	protected Class<AGroup> getDataClass() {
		
		return AGroup.class;
	}

	@Override
	public AGroup findAGroupByName(String name) {
		// TODO Auto-generated method stub
		return this.findOneByHQL("from AGroup where name=?", name);
	}
	


}
