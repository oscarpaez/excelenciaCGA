package co.com.CGAwebComercial.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.Incidencia;
import co.com.CGAwebComercial.entyties.Zona_venta;
import co.com.CGAwebComercial.util.HibernateUtil;

public class IncidenciaDao extends GenericDao<Incidencia>{

	public Long valorPedidosPerdidos(String zona){

		Session session = HibernateUtil.getSessionfactory().openSession();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();

			Criteria consulta = session.createCriteria(Incidencia.class);
			consulta.createAlias("zona", "z");
			consulta.add(Restrictions.eq("z.id_zona_venta", zona));
			consulta.add(Restrictions.between("fechaRegistro", fechaInicial , fechaFinal));
			consulta.setProjection(Projections.sum("valorVenta"));
			Long  totalWages = (Long) consulta.uniqueResult();
			totalWages =(totalWages == null)? 0: totalWages;
			return totalWages;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	

	}



	public BigDecimal valorPedidosPerdidosSucursal(List<Zona_venta> lista){

		Session session = HibernateUtil.getSessionfactory().openSession();
		BigDecimal  totalWages = new BigDecimal("0");
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();

			for (Zona_venta zona: lista) {

				Criteria consulta = session.createCriteria(Incidencia.class);
				consulta.createAlias("zona", "z");
				consulta.add(Restrictions.eq("z.id_zona_venta", zona.getId_zona_venta()));
				consulta.add(Restrictions.between("fechaRegistro", fechaInicial , fechaFinal));
				consulta.setProjection(Projections.sum("valorVenta"));
				Long total = (Long) consulta.uniqueResult();			
				total= (total == null)? 0: total;
				System.out.println(total + " ** " + zona.getId_zona_venta());
				totalWages = totalWages.add(new BigDecimal(total));
			}
			return totalWages;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}
	
	@SuppressWarnings("unchecked")
	public List<Incidencia> valorPedidosPerdidosUsuarios(String zona){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Incidencia> listaIncidencia = new ArrayList<>();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();

			Criteria consulta = session.createCriteria(Incidencia.class);
			consulta.createAlias("zona", "z");
			consulta.add(Restrictions.eq("z.id_zona_venta", zona));
			consulta.add(Restrictions.between("fechaRegistro", fechaInicial , fechaFinal));
			listaIncidencia = consulta.list();
			return listaIncidencia;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	

	}
	
	@SuppressWarnings("unchecked")
	public List<Incidencia> pedidosPerdidosSucursal(List<Zona_venta> listaZona, int idCiudad){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Incidencia> listaIncidencia = new ArrayList<>();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();
			
			listaZona.get(0).getCiudad().getId();
				
				Criteria consulta = session.createCriteria(Incidencia.class);
				consulta.createAlias("zona", "z");
				consulta.add(Restrictions.eq("z.ciudad.id", idCiudad));
				consulta.add(Restrictions.between("fechaRegistro", fechaInicial , fechaFinal));
				listaIncidencia = consulta.list();
			
			return listaIncidencia;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	

	}

}
