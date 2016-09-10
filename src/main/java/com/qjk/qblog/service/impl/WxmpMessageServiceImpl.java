package com.qjk.qblog.service.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qjk.qblog.dao.IWxmpMessageDao;
import com.qjk.qblog.data.Message;
import com.qjk.qblog.data.Pager;
import com.qjk.qblog.data.WxmpMessage;
import com.qjk.qblog.service.IWxmpMessageService;
import com.qjk.qblog.util.Value;

@Service
public class WxmpMessageServiceImpl implements IWxmpMessageService {
	
	@Resource
	IWxmpMessageDao messageDao;
	
	@Override
	public WxmpMessage addMessage(WxmpMessage message) {
		
		if(message != null){
			messageDao.saveMessage(message);
		}
		
		return message;
	}

	@Override
	public Pager<WxmpMessage> selectPager(int pageIndex, String keywords) {
		Pager<WxmpMessage> pager = new Pager<WxmpMessage>().openCounter(pageIndex, 10);
		Map<String, Object> sqlParams = new LinkedHashMap<String, Object>();
		if(!Value.isEmpty(keywords)){
			sqlParams.put(" AND fromUserName = ?", keywords);
		}
		pager.setSqlParams(sqlParams);
		pager.setOrder("ORDER BY createTime desc ");
		pager = messageDao.selectPager(pager);

		return pager;
	}

}
