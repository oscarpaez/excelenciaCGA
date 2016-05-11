package co.com.CGAwebComercial.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.Sucursal;
import co.com.CGAwebComercial.util.HibernateUtil;

public class SucursalDao extends GenericDao<Sucursal>{
	
	public Sucursal buscarSucursal(int codigo){

		Session session = HibernateUtil.getSessionfactory().openSession();
		try {
			Criteria consulta = session.createCriteria(Sucursal.class);
			consulta.createAlias("ciudad", "c");
			consulta.add(Restrictions.eq("c.id",codigo));
			Sucursal resultado = (Sucursal)consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

}
