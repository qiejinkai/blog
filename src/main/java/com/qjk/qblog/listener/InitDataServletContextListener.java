package com.qjk.qblog.listener;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
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
			List<File> files = new ArrayList<File>(4);
			File path = new File(filePath);

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

			try {
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
							} else {
								settingDao
										.deleteSetting(setting.getSettingId());
							}
						}
						setting = new Setting();
						setting.setName(name);
						setting.setVersion(version);
						setting.setCtime(new Date().getTime() / 1000);
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
			} catch (Throwable e) {
				e.printStackTrace();
			} finally {
			}
		}
	}

	private Menu makeMenu(Menu menu, Element root) {
		if (root != null && menu != null) {
			List<Element> children = root.elements("menu");

			for (Element el : children) {
				Menu child = new Menu();
				child.setName(el.elementText("name"));
				child.setTitle(el.elementText("title"));
				child.setIcon(el.elementText("icon"));
				child.setHref(el.elementText("href"));
				child.setResource(el.elementText("resource"));
				child.setParent(menu);
				child = makeMenu(child, el);
				menu.getChilden().add(child);
			}

			return menu;

		}
		return null;
	}

	private void initMenu(ServletContext servletContext, String filePath) {
		IMenuDao menuDao = (IMenuDao) getDefaulApplicationContext(
				servletContext).getBean("menuDaoImpl");
		if (menuDao != null) {
			List<File> files = new ArrayList<File>(4);

			File path = new File(filePath);
			if (path.isDirectory()) {
				for (File f : path.listFiles(new FilenameFilter() {

					@Override
					public boolean accept(File dir, String name) {
						return name.endsWith(".menu");
					}
				})) {
					files.add(f);
				}
			}

			SAXReader reader = new SAXReader();

			try {
				for (File file : files) {
					Document document = reader.read(file);

					Element el = document.getRootElement();

					String name = el.elementText("name");
					String title = el.elementText("title");
					String version = el.elementText("version");

					if (!Value.isEmpty(name) && !Value.isEmpty(version)) {

						Menu menu = menuDao.findRootMenuByName(name);

						if (menu != null) {
							if (version.equals(menu.getVersion())) {
								continue;
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
						// logger.info(menu.getChilden().size());

					}
				}
			} catch (Throwable e) {
				logger.error(e.getMessage(), e);
			} finally {

			}
		}
	}

	private ApplicationContext getDefaulApplicationContext(
			ServletContext context) {

		return WebApplicationContextUtils.getWebApplicationContext(context);
	}

}
