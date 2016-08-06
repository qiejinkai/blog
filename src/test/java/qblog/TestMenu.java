package qblog;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.qjk.qblog.dao.IMenuDao;
import com.qjk.qblog.data.Menu;
import com.qjk.qblog.util.Value;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestMenu extends AbstractJUnit4SpringContextTests {

	@Resource
	 IMenuDao menuDao;

	@Before
	public void initMenu() throws Throwable {

		List<File> files = new ArrayList<File>(4);

		File path = new File("classpath:menu/");

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
				menu.setName(name);
				menu.setTitle(title);
				menu.setVersion(version);
				menu = makeMenu(menu, el);

			}
		}
	}
	
	@Test
	public void testMake() throws DocumentException{
		List<File> files = new ArrayList<File>(4);

		File path = new File("/Applications/ide/workspace/qblog/src/main/resources/menu/");

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

			}
		}
	}
	
	@Test
	public void testSelect(){
		Menu menu = menuDao.findMenuByName("admin");
		
		printMenu(menu, 0);
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
	
	public static void main(String[] args) throws DocumentException {
		

		SAXReader reader = new SAXReader();
		Document document = reader.read(new File("/Applications/ide/workspace/qblog/src/main/resources/menu/admin.menu"));
		
		Menu menu = new Menu(); 

		Element el = document.getRootElement();

		String name = el.elementText("name");
		String title = el.elementText("title");
		String version = el.elementText("version");

		if (!Value.isEmpty(name) && !Value.isEmpty(version)) {
			menu.setName(name);
			menu.setTitle(title);
			menu.setVersion(version);
//			menu =makeMenu(menu, el);

		}
		
		printMenu(menu,0);
	}

	private static void printMenu(Menu menu,int index) {
		
		if(menu != null){
			StringBuilder sb = new StringBuilder();
			for(int i = 0 ;i <index;i++){
				sb.append("----");
			}
			
			System.out.println(sb.toString()+menu.getTitle());
			for(Menu childMenu : menu.getChilden()){
				printMenu(childMenu,index+1);
			}
		}
		
	}

}
