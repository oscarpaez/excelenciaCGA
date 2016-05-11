package co.com.CGAwebComercial.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.EsquemaGerentes;
import co.com.CGAwebComercial.util.HibernateUtil;

public class EsquemaGerentesDao extends GenericDao<EsquemaGerentes>{
	
	@SuppressWarnings("unchecked")
	public List<EsquemaGerentes> listarEsquema(int sucursal){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<EsquemaGerentes> lista;
		try{
			Criteria consulta = session.createCriteria(EsquemaGerentes.class);	
			Criterion resul =Restrictions.or(Restrictions.eq("sucursal", sucursal),
					Restrictions.eq("sucursal", 1));
			consulta.add(resul);
			consulta.addOrder(Order.asc("periodo"));
			lista = consulta.list();
			return lista;
			
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}


}
