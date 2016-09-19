package co.com.CGAwebComercial.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
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
	public List<Zona_venta> buscarZonaVenOfi(int idFuncionario, int idCiudad){
		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Zona_venta> zona = new ArrayList<>();
		try{
			Criteria consulta = session.createCriteria(Zona_venta.class);
			consulta.createAlias("funcionario", "f");
			consulta.createAlias("ciudad", "c");
			consulta.add(Restrictions.eq("f.id_funcionario", idFuncionario ));
			if(idCiudad == 3){
				Criterion resul =Restrictions.in("c.id", new Integer[]{3,6});
				consulta.add(resul);
			}
			else{
				consulta.add(Restrictions.eq("c.id", idCiudad ));
			}
			zona =  consulta.list();
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
			if(ciudad == 3){
				Criterion resul =Restrictions.in("c.id", new Integer[]{3,6});
				consulta.add(resul);
			}
			else{
				consulta.add(Restrictions.eq("c.id", ciudad ));
			}
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
	public List<Zona_venta> buscarZonaJefeInterno(){
		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Zona_venta> zona = null;
		try{
			Criteria consulta = session.createCriteria(Zona_venta.class);
			consulta.createAlias("ciudad", "c");
			Criterion resul =Restrictions.in("c.id", new Integer[]{1,7});
			consulta.add(resul);
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
