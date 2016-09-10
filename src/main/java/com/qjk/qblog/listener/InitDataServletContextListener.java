package com.qjk.qblog.listener;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.loader.custom.Return;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.qjk.qblog.dao.IMenuDao;
import com.qjk.qblog.dao.ISettingDao;
import com.qjk.qblog.data.Menu;
import com.qjk.qblog.data.Setting;
import com.qjk.qblog.data.SettingOption;
import com.qjk.qblog.util.Value;

/**
 * 系统启动时初始化 数据
 * 
 * @author qiejinkai
 *
 */
public class InitDataServletContextListener implements ServletContextListener {

	Logger logger = Logger.getLogger(InitDataServletContextListener.class);

	private static final String MENUFILELOCATION = "menuFileLocation";
	private static final String SETTINGFILELOCATION = "settingFileLocation";
	private static final String ISINITDATA = "isInitData";

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {

		String isInitData = event.getServletContext().getInitParameter(
				ISINITDATA);

		if (!Value.isEmpty(isInitData) && isInitData.equals("true")) {

			logger.info("InitDataServletContextListener contextInitialized");
			String menuLocation = event.getServletContext().getInitParameter(
					MENUFILELOCATION);
			menuLocation = this.getClass().getResource(menuLocation).getPath();

			if (!Value.isEmpty(menuLocation)) {
				initMenu(event.getServletContext(), menuLocation);
			}

			String settingLocation = event.getServletContext()
					.getInitParameter(SETTINGFILELOCATION);
			settingLocation = this.getClass().getResource(settingLocation)
					.getPath();

			if (!Value.isEmpty(settingLocation)) {
				initSetting(event.getServletContext(), settingLocation);
			}

			logger.info("InitDataServletContextListener contextInitialized End");
		}

	}

	private void initSetting(ServletContext servletContext, String filePath) {
		ISettingDao settingDao = (ISettingDao) getDefaulApplicationContext(
				servletContext).getBean("settingDaoImpl");
		if (settingDao != null) {
			File path = new File(filePath);

			final SAXReader reader = new SAXReader();
			if (path.isDirectory()) {

				Arrays.stream(path.listFiles())
						.filter(file -> file.getName().endsWith(".setting"))
						.forEach(file -> {
							try {
								Document document = reader.read(file);

								Element root = document.getRootElement();
								String name = root.elementText("name");
								String version = root.elementText("version");

								if (!Value.isEmpty(name)
										&& !Value.isEmpty(version)) {
									Setting s = settingDao
											.getSettingByName(name);
									if (s != null) {
										if (version.equals(s.getVersion())) {
											return;
										} else {
//											System.out.println(s.getSettingId());
//											System.out.println(s.getName());
											settingDao.deleteSetting(s
													.getSettingId());
										}
									}
									final Setting setting = new Setting();
									setting.setName(name);
									setting.setVersion(version);
									setting.setCtime(new Date().getTime() / 1000);
									setting.setSummary(root
											.elementText("summary"));
									setting.setTypes(Value.longValue(
											root.elementText("types"), 0));
									setting.setTitle(Value.stringValue(
											root.elementText("title"), ""));

									List<Element> els = root.elements("option");

									Stream<Element> elstream = els.stream();
									elstream.map(
											el -> {
												SettingOption option = new SettingOption();
												option.setName(el
														.elementText("name"));
												option.setValue(el
														.elementText("value"));
												option.setSummary(el
														.elementText("summary"));
												option.setSetting(setting);
												return option;
											}).forEach(
											o -> setting.getOptions().add(o));

									settingDao.addSetting(setting);

								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						});

			}

		}
	}

	private Menu makeMenu(Menu menu, Element root) {

		if (root != null && menu != null) {
			List<Element> els = root.elements("menu");

			Stream<Element> elstream = els.stream();
			elstream.forEach(el -> {
				Menu child = new Menu();
				child.setName(el.elementText("name"));
				child.setTitle(el.elementText("title"));
				child.setIcon(el.elementText("icon"));
				child.setHref(el.elementText("href"));
				child.setResource(el.elementText("resource"));
				child.setParent(menu);
				child = makeMenu(child, el);
				menu.getChilden().add(child);
			});

			return menu;

		}
		return null;
	}

	private void initMenu(ServletContext servletContext, String filePath) {
		IMenuDao menuDao = (IMenuDao) getDefaulApplicationContext(
				servletContext).getBean("menuDaoImpl");
		if (menuDao != null) {

			SAXReader reader = new SAXReader();

			File path = new File(filePath);
			if (path.isDirectory()) {
				Arrays.stream(path.listFiles())
						.filter(file -> file.getName().endsWith(".menu"))
						.peek(file -> {
							try {
								Document document = reader.read(file);

								Element el = document.getRootElement();

								String name = el.elementText("name");
								String title = el.elementText("title");
								String version = el.elementText("version");

								if (!Value.isEmpty(name)
										&& !Value.isEmpty(version)) {

									Menu menu = menuDao
											.findRootMenuByName(name);

									if (menu != null) {
										if (version.equals(menu.getVersion())) {
											return;
										} else {
											menuDao.deleteMenu(menu.getMenuId());
										}
									}
									menu = new Menu();
									menu.setName(name);
									menu.setTitle(title);
									menu.setVersion(version);
									menu = makeMenu(menu, el);
									menuDao.addMenu(menu);

								}
							} catch (Exception e) {
								e.printStackTrace();
								logger.error(e);
							}
						}).collect(Collectors.toList());
			}

		}
	}

	private ApplicationContext getDefaulApplicationContext(
			ServletContext context) {

		return WebApplicationContextUtils.getWebApplicationContext(context);
	}

}
