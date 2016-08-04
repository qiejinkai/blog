package qblog;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.qjk.qblog.dao.ISettingDao;
import com.qjk.qblog.data.Setting;
import com.qjk.qblog.data.SettingOption;


@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestSettingDao extends AbstractJUnit4SpringContextTests  {
	
	@Resource
	ISettingDao settingDao;
	
	@Test
	public void testAdd(){
		
		
		Setting setting = new Setting();
		setting.setName("sms");
		setting.setSummary("短信通道参数设置");
		setting.setVersion("1.0.0");
		setting.setTypes(Setting.TYPE_FIXED|Setting.TYPE_NAME|Setting.TYPE_VALUE);
		
		SettingOption o1 = new SettingOption();
		o1.setName("url");
		o1.setSummary("http地址");
		o1.setValue("http://1234.html");
		o1.setSetting(setting);

		
		SettingOption o2 = new SettingOption();
		o2.setName("prefix");
		o2.setSummary("前缀好");
		o2.setValue("");
		o2.setSetting(setting);

		
		SettingOption o3 = new SettingOption();
		o3.setName("method");
		o3.setSummary("提交方式");
		o3.setValue("post");
		o3.setSetting(setting);
		
		SettingOption o4 = new SettingOption();
		o4.setName("charset");
		o4.setSummary("编码");
		o4.setValue("utf-8");
		o4.setSetting(setting);

		setting.getOptions().add(o1);
		setting.getOptions().add(o2);
		setting.getOptions().add(o3);
		setting.getOptions().add(o4);
		
		settingDao.addSetting(setting);
		
		
		
	}
	
	@Test
	public void testDelete(){
		settingDao.deleteSetting(1);
	}
	
	@Test
	public void testUpdate(){
		Setting setting = settingDao.getSettingByName("sms");
		setting.setCtime(new Date().getTime()/1000);
		setting.setMtime(new Date().getTime()/1000);
		settingDao.updateSetting(setting);
	}
	
	@Test
	public void testSelect(){
		long start = System.currentTimeMillis();
		Setting setting = settingDao.getSettingByName("sms");
		long end = System.currentTimeMillis();
		System.out.println("用时："+(end-start));

		long start2 = System.currentTimeMillis();
		Setting setting2 = settingDao.getSettingById(1);
		long end2 = System.currentTimeMillis();
		System.out.println("用时："+(end2-start2));
//		for (SettingOption option : setting.getOptions()) {
//			System.out.println(option.getName()+" , "+option.getValue());
//		}
	}
}
