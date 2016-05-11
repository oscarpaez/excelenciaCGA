package co.com.CGAwebComercial.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class HibernateContexto implements ServletContextListener{

	public void contextDestroyed(ServletContextEvent arg0) {
		HibernateUtil.getSessionfactory().close();
		
		
	}

	public void contextInitialized(ServletContextEvent evento) {
		HibernateUtil.getSessionfactory(); 
		
	}
	
	

}
