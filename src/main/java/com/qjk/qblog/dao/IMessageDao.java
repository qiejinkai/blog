package com.qjk.qblog.dao;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qjk.qblog.data.Message;
import com.qjk.qblog.data.Pager;

@Transactional(propagation=Propagation.SUPPORTS)
public interface IMessageDao {
	
	void addMessage(Message message);
	
	void deleteMessage(long id);
	
	Message findMessageById(long id);
	
	Pager<Message> selectPager(Pager<Message> pager);
}
