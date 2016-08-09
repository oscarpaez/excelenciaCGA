package co.com.CGAwebComercial.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.Proyecto;
import co.com.CGAwebComercial.util.HibernateUtil;

public class ProyectoDao extends GenericDao<Proyecto>{
	
	@SuppressWarnings("unchecked")
	public List<Proyecto> listaProyectosInactivos(int usuario){
		

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Proyecto> listaProyecto = new ArrayList<>();
		try{
				Long estado = (long) 7;
				Criteria consulta = session.createCriteria(Proyecto.class);
				consulta.createAlias("usuario", "u");
				consulta.createAlias("estado", "e");
				consulta.add(Restrictions.eq("e.id", estado ));
				consulta.add(Restrictions.eq("u.id",  usuario ));
				listaProyecto = consulta.list();
				
				return listaProyecto;
				
			} catch (RuntimeException ex) {
				throw ex;
			}
			finally{
				session.close();
			}
	}
	
	@SuppressWarnings("unchecked")
	public List<Proyecto> listaProyectosActivos(int usuario){
		

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Proyecto> listaProyecto = new ArrayList<>();
		try{
				Long estado = (long) 7;
				Criteria consulta = session.createCriteria(Proyecto.class);
				consulta.createAlias("usuario", "u");
				consulta.createAlias("estado", "e");
				consulta.add(Restrictions.ne("e.id", estado ));
				consulta.add(Restrictions.eq("u.id",  usuario ));
				listaProyecto = consulta.list();
				
				return listaProyecto;
				
			} catch (RuntimeException ex) {
				throw ex;
			}
			finally{
				session.close();
			}
	}
	
	@SuppressWarnings("unchecked")
	public int listaProyecto(int usuario){
		Session session = HibernateUtil.getSessionfactory().openSession();
		int valor = 0;
		try{
				Criteria consulta = session.createCriteria(Proyecto.class);
				consulta.createAlias("usuario", "u");
				consulta.add(Restrictions.eq("u.id",  usuario ));
				List<Proyecto> proyecto = consulta.list();
				
				if(proyecto.size() >0){
				
					consulta = session.createCriteria(Proyecto.class);
					consulta.createAlias("usuario", "u");
					consulta.add(Restrictions.eq("u.id",  usuario ));
					consulta.setProjection(Projections.max("item"));
					valor =(Integer) consulta.uniqueResult();
				}	
				return valor;
				
			} catch (RuntimeException ex) {
				throw ex;
			}
			finally{
				session.close();
			}
	}
	

}
