package com.qjk.qblog.service;

import org.springframework.transaction.annotation.Transactional;

import com.qjk.qblog.data.Message;
import com.qjk.qblog.data.Pager;

@Transactional
public interface IMessageService {
	
	void addMessage(String content,long uid,long quoteId,long articleId);
	
	void deleteMessage(long id);
	
	Pager<Message> selectPager(long articleId,long uid ,int pageIndex);

	Pager<Message> selectPager(int pageIndex, String keywords, long articleId, long uid);
	
	
}
