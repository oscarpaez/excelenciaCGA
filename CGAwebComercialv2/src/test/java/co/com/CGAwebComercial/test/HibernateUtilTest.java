package co.com.CGAwebComercial.test;

import org.hibernate.Session;
import org.junit.Test;

import co.com.CGAwebComercial.util.HibernateUtil;

public class HibernateUtilTest {
	
	@Test
	public void conectar(){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		session.close();
		HibernateUtil.getSessionfactory().close();
	}

}
