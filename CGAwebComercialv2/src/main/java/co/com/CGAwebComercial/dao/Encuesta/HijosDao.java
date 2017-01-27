package co.com.CGAwebComercial.dao.Encuesta;

import co.com.CGAwebComercial.entyties.encuesta.Hijo;
import co.com.CGAwebComercial.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.dao.Encuesta.GenericDao;

public class HijosDao extends GenericDao<Hijo>{
	
	@SuppressWarnings("unchecked")
	public List<Hijo> listaHijos(int id){
		
		Session session = HibernateUtil.getSessionfactory2().openSession();
		List<Hijo> lh = new ArrayList<>();
		
		try {
			
			Criteria consulta = session.createCriteria(Hijo.class);
			consulta.createAlias("persona_id", "p");
			consulta.add(Restrictions.eq("p.id", id));
			lh = consulta.list();
			
			return lh;
			
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally {
			session.close();
		}
		
		
	}

}
