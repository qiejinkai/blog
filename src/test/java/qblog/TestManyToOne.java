package qblog;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.qjk.qblog.data.Many;
import com.qjk.qblog.data.One;


@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestManyToOne extends AbstractJUnit4SpringContextTests {
	
	@Resource
	SessionFactory sf;
	
//	@BeforeClass
//	public void beforeTest(){
//		One one = new One();
//		one.setName("");
//	}
	
	@Test
	public void testSelectOne(){
		Session session = sf.openSession();
		Transaction ts = session.beginTransaction();
		
		One one = null;
		
		one = session.get(One.class, 1L);
		
		System.out.println(one);
		
		ts.commit();
		session.close();
		
		sf.close();
		
	}
	

	@Test
	public void testSelectMany(){
		Session session = sf.openSession();
		Transaction ts = session.beginTransaction();
		
		One one = null;
		
		Many many = session.get(Many.class, 1L);
		
//		String hql = " from Many where  one_oneId = ";
		
		System.out.println(many.getOne().getName());
		
		ts.commit();
		session.close();
		
		sf.close();
		
	}
}
