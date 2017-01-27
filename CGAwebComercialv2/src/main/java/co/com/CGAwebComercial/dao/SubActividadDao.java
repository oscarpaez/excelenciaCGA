package co.com.CGAwebComercial.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.SubActividades;
import co.com.CGAwebComercial.util.HibernateUtil;

public class SubActividadDao extends GenericDao<SubActividades> {
	
	@SuppressWarnings("unchecked")
	public List<SubActividades> listaSubActividades(int idAct){
		
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		List<SubActividades> listaSubAct = new ArrayList<>();
		try{
			System.out.println(idAct+ "listaA");
			Criteria consulta = session.createCriteria(SubActividades.class);
			consulta.createAlias("activida", "a");
			consulta.add(Restrictions.eq("a.id", idAct));
			listaSubAct = consulta.list();
			return listaSubAct;
			
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

}
