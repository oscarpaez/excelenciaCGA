package co.com.CGAwebComercial.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.Materiales;
import co.com.CGAwebComercial.util.HibernateUtil;

public class MaterialesDao extends GenericDao<Materiales>{
	
	
	@SuppressWarnings("unchecked")
	public List<Materiales> listarMaterialSucursal(Long id){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Materiales> listaMateriales = new ArrayList<>();
		try{
			
			Criteria consulta = session.createCriteria(Materiales.class);
			consulta.createAlias("sucursal", "s");
			consulta.add(Restrictions.eq("s.id", id ));
			listaMateriales = consulta.list();
			
			return listaMateriales;
			
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	
	public int ultimoRegistroIngresado(){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		try{
			
			Criteria consulta = session.createCriteria(Materiales.class);
			consulta.setProjection(Projections.max("id"));
			int valor =(Integer) consulta.uniqueResult();
			return valor;
			
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

}
