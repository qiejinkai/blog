package com.qjk.qblog.dao.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.qjk.qblog.dao.IMenuDao;
import com.qjk.qblog.data.Menu;

@Repository
public class MenuDaoImpl extends BaseDaoImpl<Menu> implements IMenuDao {

	@Override
	public void addMenu(Menu menu) {
		this.save(menu);
		
	}


	@CacheEvict(cacheNames="menuConfig",key="T(com.qjk.qblog.util.RedisUtil).getKey('menu','admin')")
	public void updateMenu(Menu menu) {
		
		this.update(menu);
	}

	@Override
	public Menu findMenuById(long id) {
		
		return this.findById(id);
	}

	@Override
	public Menu findMenuByName(String name) {
		
		return this.findOneByHQL("from Menu m where m.name=? and m.parent is null", name);
	}

	@CacheEvict(cacheNames="menuConfig",key="T(com.qjk.qblog.util.RedisUtil).getKey('menu','admin')")
	public void deleteMenu(long id) {
		
		this.delete(id);
		
	}
	
	@Override
	public Menu findRootMenuByName(String name) {

		return this.findOneByHQL("from Menu m where m.name=? and m.parent is null", name);
	}
	@Override
	protected Class<Menu> getDataClass() {
		
		return Menu.class;
	}

}
