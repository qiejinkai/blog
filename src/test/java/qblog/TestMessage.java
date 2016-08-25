package qblog;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.qjk.qblog.dao.IMessageDao;
import com.qjk.qblog.data.Message;
import com.qjk.qblog.data.User;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestMessage extends AbstractJUnit4SpringContextTests {
	
	@Resource IMessageDao messageDao;
	
	@Test
	public void testAdd(){
		for(int i = 0 ;i<220;i++){
		Message message = new Message();
		message.setContent("lalalalalalal");
		message.setCtime(new Date().getTime()/1000);
		message.setArticleId(25);
		
		User user = new User();
		user.setUid(12);
		message.setUser(new User(12));
//		message.setQuote( new Message(2));
		
		
			message.setContent("ddd"+i);
			messageDao.addMessage(message);
		}
		
		
	}
}
