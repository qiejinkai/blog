package com.qjk.qblog.dao.impl;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Repository;

import com.qjk.qblog.dao.IAGroupDao;
import com.qjk.qblog.data.AGroup;
import com.qjk.qblog.data.Article;

@Repository
public class AGroupDaoImpl extends BaseDaoImpl<AGroup> implements IAGroupDao {

	@Override
	public void addAGroup(AGroup aGroup) {

		this.save(aGroup);
	}

	@Override
	public void updateAGroup(AGroup aGroup) {

		this.update(aGroup);
	}


	@CacheEvict(cacheNames="artilceConfig",key="'all_group'")
	public void refreshCount() {
		String hql = "update from AGroup a set a.count = (select count(u.title) from Article u where u.hidden=? and u.group.groupId = a.groupId)";
		this.execHQL(hql, Article.HIDDEN_NONE);

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
