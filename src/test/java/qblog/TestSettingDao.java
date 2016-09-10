package qblog;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.qjk.qblog.dao.ISettingDao;
import com.qjk.qblog.data.Setting;
import com.qjk.qblog.data.SettingOption;
import com.qjk.qblog.service.ISettingService;
import com.qjk.qblog.util.Value;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestSettingDao extends AbstractJUnit4SpringContextTests {

	@Resource
	ISettingDao settingDao;

	@Test
	public void testAdd() {

		Setting setting = new Setting();
		setting.setName("sms");
		setting.setSummary("短信通道参数设置");
		setting.setVersion("1.0.0");
		setting.setTypes(Setting.TYPE_FIXED | Setting.TYPE_NAME
				| Setting.TYPE_VALUE);

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
	public void testDelete() {
		settingDao.deleteSetting(1);
	}

	@Test
	public void testUpdate() {
		Setting setting = settingDao.getSettingByName("sms");
		setting.setCtime(new Date().getTime() / 1000);
		setting.setMtime(new Date().getTime() / 1000);
		settingDao.updateSetting(setting);
	}

	@Test
	public void testSelect() {
		long start = System.currentTimeMillis();
		Setting setting = settingDao.getSettingByName("sms");
		long end = System.currentTimeMillis();
		System.out.println("用时：" + (end - start));

		long start2 = System.currentTimeMillis();
		Setting setting2 = settingDao.getSettingById(1);
		long end2 = System.currentTimeMillis();
		System.out.println("用时：" + (end2 - start2));
		// for (SettingOption option : setting.getOptions()) {
		// System.out.println(option.getName()+" , "+option.getValue());
		// }
	}

	@Test
	public void testMakeSetting() throws Throwable {
		List<File> files = new ArrayList<File>(4);

		File path = new File(
				"/Applications/ide/workspace/qblog/src/main/resources/setting/");

		if (path.isDirectory()) {
			for (File f : path.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".setting");
				}
			})) {
				files.add(f);
			}
		}

		SAXReader reader = new SAXReader();
		for (File file : files) {
			Document document = reader.read(file);

			Element root = document.getRootElement();
			String name = root.elementText("name");
			String version = root.elementText("version");

			if (!Value.isEmpty(name) && !Value.isEmpty(version)) {
				Setting setting = settingDao.getSettingByName(name);
				if (setting != null) {
					if (version.equals(setting.getVersion())) {
						continue;
					}else{
						settingDao.deleteSetting(setting.getSettingId());
					}
				}
				setting = new Setting();
				setting.setName(name);
				setting.setVersion(version);
				setting.setCtime(new Date().getTime()/1000);
				setting.setSummary(root.elementText("summary"));
				setting.setTypes(Value.longValue(root.elementText("types"), 0));
				
				List<Element> els = root.elements("option");
				
				for (Element el : els) {
					SettingOption option = new SettingOption();
					option.setName(el.elementText("name"));
					option.setValue(el.elementText("value"));
					option.setSummary(el.elementText("summary"));
					option.setSetting(setting);
					setting.getOptions().add(option);
				}
				
				settingDao.addSetting(setting);
				

			}

		}
	}
	
	@Resource
	private ISettingService settingService;
	
	@Resource
	private CacheManager cacheManager;
	
	@Test
	public void testServiceFind(){
		
		String name = "sms";
		System.out.println("第一次查询");
		Setting setting = settingService.getSettingByName(name);
		
		System.out.println(setting.getName());
		
		List<SettingOption>list =  setting.getOptions();
		for (SettingOption settingOption : list) {
			System.out.println(settingOption.getName() +" , "+settingOption.getValue());
		}

		System.out.println("第二次查询");
		Setting setting2 = settingService.getSettingByName(name);
		
		System.out.println(setting2.getName());
		
		List<SettingOption>list2 =  setting2.getOptions();
		for (SettingOption settingOption : list2) {
			System.out.println(settingOption.getName() +" , "+settingOption.getValue());
		}
		
//		try {
//			Thread.sleep(11000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		System.out.println("第三次查询");
		Setting setting3 = settingService.getSettingByName(name);
		
		System.out.println(setting3.getName());
		
		List<SettingOption>list3 =  setting3.getOptions();
		for (SettingOption settingOption : list3) {
			System.out.println(settingOption.getName() +" , "+settingOption.getValue());
		}
		
		System.out.println("第四次查询");
		
		Setting setting4  = cacheManager.getCache("systemConfig").get("setting:name:sms", Setting.class);
		
		if(setting4 != null){
		
			System.out.println(setting4.getName());
			
			List<SettingOption>list4 =  setting4.getOptions();
			for (SettingOption settingOption : list4) {
				System.out.println(settingOption.getName() +" , "+settingOption.getValue());
			}
		}
		
		System.out.println("第五次查询");
		
		Setting setting5  = cacheManager.getCache("systemConfig2").get("setting:name:sms", Setting.class);
		
		if(setting5 != null){
		
			System.out.println(setting5.getName());
			
			List<SettingOption>list5 =  setting5.getOptions();
			for (SettingOption settingOption : list5) {
				System.out.println(settingOption.getName() +" , "+settingOption.getValue());
			}
		}
		
	}
	
	@Test
	public void testServiceUpdate(){
		Setting setting = settingService.getSettingByName("wxmp");
		List<SettingOption> options = setting.getOptions();
		String token = "asd";
		long expires_in = 7200;
		long now = new Date().getTime()/1000;
		options = options.stream().peek(o->{
			
			if(o != null){
				if("access_token".equals(o.getName())){
					o.setValue(token);
				}
				if("access_token_expire".equals(o.getName())){
					o.setValue(expires_in+"");
				}
				if("access_token_ctime".equals(o.getName())){
					o.setValue(now+"");
				}
			}
		}).collect(Collectors.toList());
		
		setting.setOptions(options);
		
		settingService.updateSetting(setting);
	}
}
