package qblog;

import java.util.Date;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.qjk.qblog.dao.IUserDao;
import com.qjk.qblog.data.User;
import com.qjk.qblog.service.IUserService;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestUserService extends AbstractJUnit4SpringContextTests{
	
	
	@Resource
	IUserService userService;
	
	@Resource
	IUserDao userDao;
	
	@Resource
	SessionFactory sf;
	
	@Test
	public void testAddUser(){

		User user = new User();
		user.setNick("qiejinkai");
		user.setPhone("13051701098");
		user.setEmail("qiejinkai@126.com");
		userService.addUser(user);
		System.out.println(user.getUid());
	}
	
	@Test
	public void testLoadUser(){

		User user = userDao.findUserById(1);
		System.out.println(user.getCtime());
	}
	
}
