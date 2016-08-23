package qblog;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.qjk.qblog.data.User;
import com.qjk.qblog.util.CacheManagerUtil;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestCacheManageUtil extends AbstractJUnit4SpringContextTests {
	
	@Resource
	CacheManagerUtil util;
	@Test
	public void test(){
		String key = "user:id:1";
		User user = new User();
		user.setEmail("qiejinkai@qq.com");
		user.setPhone("13051701098");
		user.setNick("凯爷");
		boolean result = util.putLoginCache("user:id:1", user);
		System.out.println(result);
		System.out.println( util.getLoginCache(key));
		
		User u1 = (User) util.getLoginCache(key);
		System.out.println(" u1 : "+ u1.getEmail()+" , " +u1.getNick());
		User u2 = (User) util.getSystemCache(key);
		System.out.println(" u2 : "+ u2.getEmail()+" , " +u2.getNick());
		
	}
	
	@Test
	public void testOauth(){
		Object object = util.getOauthCodeCache("wx_oauth_pulic : "+"asjhdkajshdjkhasjkdhjkasd");
		System.out.println(object);
	}
}
