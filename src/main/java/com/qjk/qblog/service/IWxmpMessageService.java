package com.qjk.qblog.service;

import org.springframework.transaction.annotation.Transactional;

import com.qjk.qblog.data.Pager;
import com.qjk.qblog.data.WxmpMessage;

@Transactional
public interface IWxmpMessageService {
	
	WxmpMessage addMessage(WxmpMessage message);


	Pager<WxmpMessage> selectPager(int pageIndex, String keywords);

}
