package com.qjk.qblog.service;

import org.springframework.transaction.annotation.Transactional;

import com.qjk.qblog.data.WxmpMessage;

@Transactional
public interface IWxmpMessageService {
	
	WxmpMessage addMessage(WxmpMessage message);

}
