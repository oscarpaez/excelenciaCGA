package co.com.CGAwebComercial.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.Ajuste;
import co.com.CGAwebComercial.util.HibernateUtil;

public class AjusteDao extends GenericDao<Ajuste>{

	@SuppressWarnings("unchecked")
	public List<Ajuste> listaAjuste(int cod, String fecMes, String fecYear){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Ajuste> listaAjuste;
		try{
			
			Date fechaFinal;
			Date fechaInicial;
			if(fecMes.equals(null) || fecMes.equals("") || fecYear.equals(null) || fecYear.equals("")){
				fechaFinal = fechaFinal();
				fechaInicial = fechaInicial();		
			}
			else{
				fechaFinal = fechaFinal(fecMes, fecYear);
				fechaInicial = fechaInicial(fecMes, fecYear);
			}
			Criteria consulta = session.createCriteria(Ajuste.class);
			consulta.add(Restrictions.eq("codSap", cod));
			consulta.add(Restrictions.between("fechaAjuste", fechaInicial, fechaFinal));
			consulta.addOrder(Order.asc("nombre"));			
			listaAjuste = consulta.list();
			
			return listaAjuste;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
}
