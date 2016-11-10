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

	public List<Long> valorPedidosPerdidos(String zona, int idFun){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Long> listaV = new ArrayList<>();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();

			Criteria consulta = session.createCriteria(Incidencia.class);
//			consulta.createAlias("zona", "z");
//			consulta.add(Restrictions.eq("z.id_zona_venta", zona));
			consulta.createAlias("funcionario", "f");
			consulta.add(Restrictions.eq("f.id_funcionario", idFun));
			consulta.add(Restrictions.eq("resultaNegocio", "NO"));
			consulta.add(Restrictions.between("fechaRegistro", fechaInicial , fechaFinal));
			consulta.setProjection(Projections.sum("valorVenta"));
			Long  totalWages = (Long) consulta.uniqueResult();
			System.out.println(totalWages + " Tamaño");
			totalWages =(totalWages == null)? 0: totalWages;
			listaV.add(totalWages);
			
			consulta = session.createCriteria(Incidencia.class);
			consulta.createAlias("funcionario", "f");
			consulta.add(Restrictions.eq("f.id_funcionario", idFun));
			consulta.add(Restrictions.eq("resultaNegocio", "SI"));
			consulta.add(Restrictions.between("fechaRegistro", fechaInicial , fechaFinal));
			consulta.setProjection(Projections.sum("valorVenta"));
			Long totalWages1 = (Long) consulta.uniqueResult();
			System.out.println(totalWages1 + " Tamaño");
			totalWages1 =(totalWages1 == null)? 0: totalWages1;
			listaV.add(totalWages1);
			
			consulta = session.createCriteria(Incidencia.class);
			consulta.createAlias("funcionario", "f");
			consulta.add(Restrictions.eq("f.id_funcionario", idFun));
			consulta.add(Restrictions.eq("resultaNegocio", "PR"));
			consulta.add(Restrictions.between("fechaRegistro", fechaInicial , fechaFinal));
			consulta.setProjection(Projections.sum("valorVenta"));
			totalWages = (Long) consulta.uniqueResult();
			System.out.println(totalWages + " Tamaño");
			totalWages =(totalWages == null)? 0: totalWages;
			listaV.add(totalWages);
			
			return listaV;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	

	}



	public List<BigDecimal> valorPedidosPerdidosSucursal(List<Zona_venta> lista, int idCiudad){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<BigDecimal>  totalWages = new ArrayList<>();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();

			//for (Zona_venta zona: lista) {

				Criteria consulta = session.createCriteria(Incidencia.class);
				//consulta.createAlias("zona", "z");
				consulta.createAlias("ciudad", "c");
				//consulta.add(Restrictions.eq("z.id_zona_venta", zona.getId_zona_venta()));
				consulta.add(Restrictions.eq("c.id", idCiudad));
				consulta.add(Restrictions.eq("resultaNegocio", "NO"));
				consulta.add(Restrictions.between("fechaRegistro", fechaInicial , fechaFinal));
				consulta.setProjection(Projections.sum("valorVenta"));
				Long total = (Long) consulta.uniqueResult();			
				total= (total == null)? 0: total;
				System.out.println(total + " ** " + idCiudad);
				totalWages.add(new BigDecimal(total));
				
				consulta = session.createCriteria(Incidencia.class);
				consulta.createAlias("ciudad", "c");
				consulta.add(Restrictions.eq("c.id", idCiudad));
				consulta.add(Restrictions.eq("resultaNegocio", "SI"));
				consulta.add(Restrictions.between("fechaRegistro", fechaInicial , fechaFinal));
				consulta.setProjection(Projections.sum("valorVenta"));
				total = (Long) consulta.uniqueResult();			
				total= (total == null)? 0: total;
				totalWages.add(new BigDecimal(total));
				
				consulta = session.createCriteria(Incidencia.class);
				consulta.createAlias("ciudad", "c");
				consulta.add(Restrictions.eq("c.id", idCiudad));
				consulta.add(Restrictions.eq("resultaNegocio", "PR"));
				consulta.add(Restrictions.between("fechaRegistro", fechaInicial , fechaFinal));
				consulta.setProjection(Projections.sum("valorVenta"));
				total = (Long) consulta.uniqueResult();			
				total= (total == null)? 0: total;
				totalWages.add(new BigDecimal(total));				
				
			//}
			return totalWages;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}
	
	@SuppressWarnings("unchecked")
	public List<Incidencia> valorPedidosPerdidosUsuarios(String zona, int idFun){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Incidencia> listaIncidencia = new ArrayList<>();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();

			Criteria consulta = session.createCriteria(Incidencia.class);
			//consulta.createAlias("zona", "z");
			//consulta.add(Restrictions.eq("z.id_zona_venta", zona));
			consulta.createAlias("funcionario", "f");
			consulta.add(Restrictions.eq("f.id_funcionario", idFun));
			
			if(zona.equals("")  || zona == null){
				
			}
			else{
				consulta.add(Restrictions.eq("resultaNegocio", zona));
			}
			//consulta.add(Restrictions.eq("resultaNegocio", "NO"));
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
	public List<Incidencia> pedidosPerdidosSucursal(List<Zona_venta> listaZona, int idCiudad, String zona){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Incidencia> listaIncidencia = new ArrayList<>();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();
			
			listaZona.get(0).getCiudad().getId();
				
				Criteria consulta = session.createCriteria(Incidencia.class);
				consulta.createAlias("ciudad", "c");
				consulta.add(Restrictions.eq("c.id", idCiudad));
				if(zona.equals("")  || zona == null){
					
				}
				else{
					consulta.add(Restrictions.eq("resultaNegocio", zona));
				}				
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
	
	//* suma el valor total del pais de las oportunidades de negocio. *//
	public List<BigDecimal> valorOportunidadNegocioPais(){


		Session session = HibernateUtil.getSessionfactory().openSession();
		List<BigDecimal>  totalWages = new ArrayList<>();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();



			Criteria consulta = session.createCriteria(Incidencia.class);
			consulta.add(Restrictions.between("fechaRegistro", fechaInicial , fechaFinal));
			consulta.setProjection(Projections.count("resultaNegocio"));
			Long total = (Long) consulta.uniqueResult();			
			total= (total == null)? 0: total;
			totalWages.add(new BigDecimal(total));

			consulta.add(Restrictions.eq("resultaNegocio", "NO"));
			consulta.setProjection(Projections.count("resultaNegocio"));
			total = (Long) consulta.uniqueResult();			
			total= (total == null)? 0: total;
			totalWages.add(new BigDecimal(total));

			return totalWages;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}
	
	//* suma el valor total de la oficna de las oportunidades de negocio. *//
	public List<BigDecimal> valorOportunidadNegocioOficina(int oficina){


		Session session = HibernateUtil.getSessionfactory().openSession();
		List<BigDecimal>  totalWages = new ArrayList<>();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();

			Criteria consulta = session.createCriteria(Incidencia.class);
			consulta.createAlias("ciudad", "c");
			consulta.add(Restrictions.eq("c.id", oficina));
			consulta.add(Restrictions.between("fechaRegistro", fechaInicial , fechaFinal));
			consulta.setProjection(Projections.count("resultaNegocio"));
			Long total = (Long) consulta.uniqueResult();			
			total= (total == null)? 0: total;
			totalWages.add(new BigDecimal(total));


			consulta.add(Restrictions.eq("resultaNegocio", "NO"));
			consulta.setProjection(Projections.count("resultaNegocio"));
			total = (Long) consulta.uniqueResult();			
			total= (total == null)? 0: total;
			totalWages.add(new BigDecimal(total));

			return totalWages;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}
	
	//* suma el valor total del vendedor de las oportunidades de negocio. *//
	public List<BigDecimal> valorOportunidadNegocioVendedor(int codFun){


		Session session = HibernateUtil.getSessionfactory().openSession();
		List<BigDecimal>  totalWages = new ArrayList<>();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();

			Criteria consulta = session.createCriteria(Incidencia.class);
			consulta.createAlias("funcionario", "f");
			consulta.add(Restrictions.eq("f.id_funcionario", codFun));
			consulta.add(Restrictions.between("fechaRegistro", fechaInicial , fechaFinal));
			consulta.setProjection(Projections.count("resultaNegocio"));
			Long total = (Long) consulta.uniqueResult();			
			total= (total == null)? 0: total;
			totalWages.add(new BigDecimal(total));


			consulta.add(Restrictions.eq("resultaNegocio", "NO"));
			consulta.setProjection(Projections.count("resultaNegocio"));
			total = (Long) consulta.uniqueResult();			
			total= (total == null)? 0: total;
			totalWages.add(new BigDecimal(total));

			return totalWages;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Incidencia> pedidosPerdidosPais(Date fechaIni, Date fechaFin){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Incidencia> listaIncidencia = new ArrayList<>();
		try{
			Date fechaFinal = (fechaFin != null)? fechaFin :fechaFinal();
			Date fechaInicial = (fechaIni != null )? fechaIni : fechaInicial();
			Criteria consulta = session.createCriteria(Incidencia.class);
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
