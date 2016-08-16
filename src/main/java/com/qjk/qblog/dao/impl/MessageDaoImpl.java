package com.qjk.qblog.dao.impl;

import org.springframework.stereotype.Repository;

import com.qjk.qblog.dao.IMessageDao;
import com.qjk.qblog.data.Message;
import com.qjk.qblog.data.Pager;

@Repository
public class MessageDaoImpl extends BaseDaoImpl<Message> implements IMessageDao{

	@Override
	public void addMessage(Message message) {
		this.save(message);
		
	}

	@Override
	public void deleteMessage(long messageId) {
		this.delete(messageId);
		
	}

	@Override
	public Message findMessageById(long id) {
		
		return this.findById(id);
	}
	
	@Override
	public Pager<Message> selectPager(Pager<Message> pager) {
		
		if(pager != null){
			pager = this.queryPager(pager);
			return pager;
		}
		
		return null;
	}

	@Override
	protected Class<Message> getDataClass() {
		
		return Message.class;
	}

}
