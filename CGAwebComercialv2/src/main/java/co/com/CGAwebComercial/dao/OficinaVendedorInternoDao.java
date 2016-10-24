package co.com.CGAwebComercial.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.OficinaVendedorInterno;
import co.com.CGAwebComercial.util.HibernateUtil;

public class OficinaVendedorInternoDao  extends GenericDao<OficinaVendedorInterno>{

	
	
	@SuppressWarnings("unchecked")
	public List<OficinaVendedorInterno> listaVenIntDirector( int codSucursal ){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<OficinaVendedorInterno> listaOfiVenInt; 
		try{
			Criteria consulta = session.createCriteria(OficinaVendedorInterno.class);
			consulta.add(Restrictions.eq("oficinadeventas", codSucursal));
			listaOfiVenInt = consulta.list();
			
			return listaOfiVenInt;
			
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	
	@SuppressWarnings({"unchecked" })
	public List<Object[]> listaVenInt( int codSucursal ){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Object[]> results = new ArrayList<>();
		try{
			Criteria consulta = session.createCriteria(OficinaVendedorInterno.class);
			consulta.add(Restrictions.eq("oficinadeventas", codSucursal));
			//consulta.setProjection(Projections.property("codigosap"));
			consulta.setProjection(Projections.projectionList().add(
					Projections.property("codigosap")).add(
					Projections.property("extension")));
			//consulta.setProjection(Projections.property("extension"));
			results = consulta.list();			
			return results;		
			
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
}
