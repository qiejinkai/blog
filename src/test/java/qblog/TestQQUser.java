package qblog;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.qjk.qblog.dao.IQQUserDao;
import com.qjk.qblog.data.QQUser;
import com.qjk.qblog.data.User;
import com.qjk.qblog.data.WXUser;
import com.qjk.qblog.service.IQQUserService;
import com.qjk.qblog.service.IWXUserService;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestQQUser extends AbstractJUnit4SpringContextTests  {

	@Resource IQQUserService qqUserService;
	@Resource IWXUserService wxUserService;
	@Resource IQQUserDao qqUserDao;
	
	@Test
	public void testwx(){
		WXUser wxUser= new WXUser();
		wxUser.setOpenid("4444444");
		wxUser.setNick("kkk");
		wxUser.setLogo("ffffffd");
		wxUserService.join(wxUser);
		wxUserService.login(wxUser.getOpenid(), "cccccc");
		
		
	}
	
	@Test
	public void test(){
		
		QQUser user = new QQUser();
		user.setOpenid("asdhjashdjkahjskdhajkhdkjasdasd");
		user.setAccess_token("ajskhfdjsadjkfjaskhdfjkha");
		user.setExpires_in(300000);
		user.setNick("小凯");
		user.setLogo("logo1");
		
		User u = qqUserService.join(user);
		
//		System.out.println(u.getUid());
		
	}
	
	@Test
	public void testupdate(){
		QQUser user = qqUserService.getQQUserByOpenId("asdhjashdjkahjskdhajkhdkjasdasd");
		user.setAccess_token("1234");
		System.out.println(user.getUser().getQqUser().getAccess_token());;
		qqUserService.updateQQUser(user);
		
		
	}
	
}
