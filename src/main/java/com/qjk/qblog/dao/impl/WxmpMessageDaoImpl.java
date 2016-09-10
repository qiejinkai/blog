package com.qjk.qblog.dao.impl;

import org.springframework.stereotype.Repository;

import com.qjk.qblog.dao.IWxmpMessageDao;
import com.qjk.qblog.data.WBUser;
import com.qjk.qblog.data.WxmpMessage;

@Repository
public class WxmpMessageDaoImpl extends BaseDaoImpl<WxmpMessage> implements IWxmpMessageDao {

	@Override
	public WxmpMessage saveMessage(WxmpMessage message) {

		this.save(message);
		return message;
	}

	@Override
	protected Class<WxmpMessage> getDataClass() {
		
		return WxmpMessage.class;
	}

}
