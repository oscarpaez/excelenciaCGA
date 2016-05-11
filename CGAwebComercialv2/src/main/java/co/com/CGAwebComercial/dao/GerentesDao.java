package co.com.CGAwebComercial.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.Gerentes;
import co.com.CGAwebComercial.util.HibernateUtil;

public class GerentesDao extends GenericDao<Gerentes> {
	
	
	public Gerentes buscarCod(int codigo){

		Session session = HibernateUtil.getSessionfactory().openSession();
		try {
			Criteria consulta = session.createCriteria(Gerentes.class);
			consulta.add(Restrictions.eq("codigoSap", codigo));
			Gerentes resultado = (Gerentes)consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

}
