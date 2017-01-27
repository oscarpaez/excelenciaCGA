package co.com.CGAwebComercial.test;

import org.hibernate.Session;
import org.junit.Ignore;
import org.junit.Test;

import co.com.CGAwebComercial.util.HibernateUtil;

public class HibernateUtilTest {
	
	@Test
	public void conectar(){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		session.close();
		HibernateUtil.getSessionfactory().close();
	}
	
	@Test
	@Ignore
	public void conectar1(){
		
		Session session = HibernateUtil.getSessionfactory1().openSession();
		session.close();
		HibernateUtil.getSessionfactory1().close();
	}

}
