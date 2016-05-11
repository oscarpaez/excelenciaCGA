package co.com.CGAwebComercial.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;


import co.com.CGAwebComercial.entyties.Zona_venta;
import co.com.CGAwebComercial.util.HibernateUtil;

public class Zona_ventaDao extends GenericDao<Zona_venta> {
	
	@SuppressWarnings("unchecked")
	public List<Zona_venta> buscarZona(int idFuncionario){
		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Zona_venta> zona = null;
		try{
			Criteria consulta = session.createCriteria(Zona_venta.class);
			consulta.createAlias("funcionario", "f");
			consulta.add(Restrictions.eq("f.id_funcionario", idFuncionario ));			
			zona = consulta.list();
			return zona;
			
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Zona_venta> buscarZonaSucursal(int ciudad){
		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Zona_venta> zona = null;
		try{
			Criteria consulta = session.createCriteria(Zona_venta.class);
			consulta.createAlias("ciudad", "c");
			consulta.add(Restrictions.eq("c.id", ciudad ));			
			zona = consulta.list();
			return zona;
			
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

}
