package com.qjk.qblog.dao;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qjk.qblog.data.Pager;
import com.qjk.qblog.data.WxmpMessage;

@Transactional(propagation=Propagation.SUPPORTS)
public interface IWxmpMessageDao {
	
	WxmpMessage saveMessage(WxmpMessage message);

	Pager<WxmpMessage> selectPager(Pager<WxmpMessage> pager);

}
