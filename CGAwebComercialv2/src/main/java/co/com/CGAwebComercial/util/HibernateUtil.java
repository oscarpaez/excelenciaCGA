package co.com.CGAwebComercial.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	
	private static final SessionFactory sessionFactory = crearFabricadeSecciones() ;
	private static final SessionFactory sessionFactory1 = crearFabricadeSecciones1() ;

    public static SessionFactory getSessionfactory() {
		return sessionFactory;
	}
    
    
    public static Connection getConexion(){
    	Session session = crearFabricadeSecciones().openSession();
    	
    	Connection conexion = session.doReturningWork(new ReturningWork<Connection>() {    		
    		public Connection execute(Connection conn) throws SQLException {
    			return conn;
    		}
		});
    	return conexion;
    	
    } 
	
	private static SessionFactory crearFabricadeSecciones() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
        	Configuration configuracion = new Configuration().configure();
            ServiceRegistry servicio = new StandardServiceRegistryBuilder().applySettings(configuracion.getProperties()).build();
            SessionFactory fabrica = configuracion.buildSessionFactory(servicio);
            
            return fabrica;
			    
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

	private static SessionFactory crearFabricadeSecciones1() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
        	Configuration configuracion = new Configuration().configure("/hibernate.cfg2.xml");
            ServiceRegistry servicio = new StandardServiceRegistryBuilder().applySettings(configuracion.getProperties()).build();
            SessionFactory fabrica = configuracion.buildSessionFactory(servicio);
            
            return fabrica;
			    
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }


	public static SessionFactory getSessionfactory1() {
		return sessionFactory1;
	}

}
