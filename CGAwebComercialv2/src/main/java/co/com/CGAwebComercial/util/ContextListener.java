package co.com.CGAwebComercial.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		HibernateUtil.getSessionfactory().close();
		HibernateUtil.getSessionfactory1().close();
		HibernateUtil.getSessionfactory2().close();
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		HibernateUtil.getSessionfactory().openSession();
		HibernateUtil.getSessionfactory1().openSession();
		HibernateUtil.getSessionfactory2().openSession();
	}
}
