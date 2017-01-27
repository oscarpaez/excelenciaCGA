package co.com.CGAwebComercial.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.Actividades;
import co.com.CGAwebComercial.util.HibernateUtil;

public class ActividadesDao extends GenericDao<Actividades>{
	
	@SuppressWarnings("unchecked")
	public List<Actividades> listaPorUsuario(int idPer){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Actividades> listaActividades = new ArrayList<>();
		try{
			System.out.println("Cedula: " + idPer);
			Criteria consulta = session.createCriteria(Actividades.class);
			consulta.createAlias("usuario", "u");
			consulta.add(Restrictions.eq("u.id", idPer));
			listaActividades = consulta.list();
			return listaActividades;
			
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

}
