package co.com.CGAwebComercial.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.Detalle_venta;
import co.com.CGAwebComercial.entyties.Plan;
import co.com.CGAwebComercial.util.HibernateUtil;

public class Detalle_ventaDao extends GenericDao<Detalle_venta>{
	
	@SuppressWarnings("unchecked")
	public List<Detalle_venta> listarDetalle(int codigo, int idPersona){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Detalle_venta> detalle = null;
		try{
			
			Criteria consulta = session.createCriteria(Detalle_venta.class);
			consulta.createAlias("linea", "l");
			consulta.add(Restrictions.eq("l.id", codigo));
			consulta.createAlias("funcionario", "f");
			consulta.add(Restrictions.eq("f.id_funcionario", idPersona));
			detalle = consulta.list();
			return detalle;
		
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}

	@SuppressWarnings({ "unchecked" })
	public List<Plan> listarDetalle2(int idPersona){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Detalle_venta> detalle = null;
		List<Plan> listaPlan = null;
		int linea = 0;
		try{
			
			Criteria consulta = session.createCriteria(Detalle_venta.class);
			consulta.createAlias("funcionario", "f");
			consulta.createAlias("linea", "l");
			consulta.add(Restrictions.eq("f.id_funcionario", idPersona));
			//consulta.setProjection(Projections.sum("valorNeto"));
			consulta.addOrder(Order.asc("linea"));			
			detalle = consulta.list();
			
			
			for (Detalle_venta detalle_venta : detalle) {
				//Plan  plan = new Plan();
				
				if(linea != detalle_venta.getLinea().getId()){
					
					linea = detalle_venta.getLinea().getId();
					detalle = listarDetalleSuma(detalle_venta.getLinea().getId(), idPersona);
				}
			}
			return listaPlan;
		
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}
	
	@SuppressWarnings("unchecked")
	public List<Detalle_venta> listarDetalleSuma(int codigo, int idPersona){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Detalle_venta> detalle = null;
		try{
			
			Criteria consulta = session.createCriteria(Detalle_venta.class);
			consulta.createAlias("linea", "l");
			consulta.add(Restrictions.eq("l.id", codigo));
			consulta.createAlias("funcionario", "f");
			consulta.add(Restrictions.eq("f.id_funcionario", idPersona));
			consulta.setProjection(Projections.sum("valorNeto"));
			detalle = consulta.list();
			return detalle;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}
}
