package com.qjk.qblog.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.qjk.qblog.dao.IMessageDao;
import com.qjk.qblog.dao.IUserDao;
import com.qjk.qblog.data.Message;
import com.qjk.qblog.data.Pager;
import com.qjk.qblog.data.User;
import com.qjk.qblog.service.IMessageService;
import com.qjk.qblog.util.Value;

@Service
public class MessageServiceImpl implements IMessageService {

	@Resource
	IMessageDao messageDao;
	@Resource
	IUserDao userDao;

	@Override
	public void addMessage(String content, long uid, long quoteId,
			long articleId) {

		Assert.isTrue(uid != 0, "未找到留言用户");
		Assert.hasText(content, "留言内容不能为空");
		User user = userDao.findUserById(uid);
		Assert.notNull(user, "留言用户不存在");
		Message message = new Message();
		message.setContent(content);
		message.setUser(user);
		message.setCtime(new Date().getTime() / 1000);
		message.setArticleId(articleId);
		if (quoteId != 0) {
			Message quote = messageDao.findMessageById(quoteId);
			if (quote != null) {
				message.setQuote(quote);
			}
		}

		messageDao.addMessage(message);
	}

	@Override
	public void deleteMessage(long id) {

		messageDao.deleteMessage(id);
	}

	@Override
	public Pager<Message> selectPager(long articleId, long uid, int pageIndex) {
		return selectPager(pageIndex, "", articleId, uid);
	}

	@Override
	public Pager<Message> selectPager(int pageIndex, String keywords,
			long articleId, long uid) {
		Pager<Message> pager = new Pager<Message>().openCounter(pageIndex, 10);
		Map<String, Object> sqlParams = new LinkedHashMap<String, Object>();
		sqlParams.put(" AND articleId=?", articleId);
		if(!Value.isEmpty(keywords)){
			sqlParams.put(" AND content like ?", "%"+keywords+"%");
		}
		if (uid != 0) {
			sqlParams.put(" AND user.uid=?", uid);
		}
		pager.setSqlParams(sqlParams);
		pager.setOrder("ORDER BY ctime desc ");
		pager = messageDao.selectPager(pager);

		return pager;
	}

}
