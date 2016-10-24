package co.com.CGAwebComercial.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.Llamadas;
import co.com.CGAwebComercial.entyties.OfertasPedidos;
import co.com.CGAwebComercial.util.HibernateUtil;


public class OfertasPedidosDao extends GenericDao<OfertasPedidos>{
	
	/* Se listan las ofertas Por vendedor Interno y Especialista */
	@SuppressWarnings("unchecked")
	public List<OfertasPedidos> listarOfertaPedidos(String tipo, int idFun){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		List<OfertasPedidos> listaOferta = new ArrayList<>();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();
			
			Criteria consulta = session.createCriteria(OfertasPedidos.class);
			consulta.add(Restrictions.eq(tipo, idFun));
			consulta.add(Restrictions.between("fechaEntOfe", fechaInicial, fechaFinal));
			listaOferta = consulta.list();
			return listaOferta;
			
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	
	/* Se listan las ofertas Por cada Especialista de la Oficina */
	@SuppressWarnings("unchecked")
	public List<Object[]> listarOfertaPedidosOficina(String tipo, int idFun){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		//List<OfertasPedidos> listaOferta = new ArrayList<>();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();
			
			String nombre = (tipo.equals("codEspecialista"))? "especialista" : "interno" ;
			
			Criteria consulta = session.createCriteria(OfertasPedidos.class);
			consulta.add(Restrictions.eq(tipo, idFun));
			consulta.add(Restrictions.between("fechaEntOfe", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.projectionList().add(
					Projections.property(tipo)).add(
							Projections.property(nombre)).add(
							Projections.sum("valorOferta")).add(
							Projections.sum("valorPedido")));			
			List<Object[]> results = consulta.list();
			return results;
			
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	
	/* Se listan las ofertas Por Oficina  */
	public Object[] listarOfertaPedidosPais(int idCiudad){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();
			
			Criteria consulta = session.createCriteria(OfertasPedidos.class);
			consulta.add(Restrictions.eq("codOficina", idCiudad));
			consulta.add(Restrictions.between("fechaEntOfe", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.projectionList().add(
					Projections.property("oficina")).add(
							Projections.sum("valorOferta")).add(
							Projections.sum("valorPedido")).add(
							Projections.property("codOficina")));			
			Object[]  results =  (Object[]) consulta.uniqueResult();
			return results;
			
			
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	/* Se listan las ofertas Por cada Especialista de la Oficina y cantidad de llamadas */
	@SuppressWarnings("unchecked")
	public OfertasPedidos listarOfertaPedidosOficinaLlamadas(String tipo, int idFun, int extension){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Object[]> results = new ArrayList<>();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();
			
			String nombre = (tipo.equals("codEspecialista"))? "especialista" : "interno" ;
			
			Criteria consulta = session.createCriteria(OfertasPedidos.class);
			consulta.add(Restrictions.eq(tipo, idFun));
			consulta.add(Restrictions.between("fechaEntOfe", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.projectionList().add(
					Projections.property(tipo)).add(
							Projections.property(nombre)).add(
							Projections.sum("valorOferta")).add(
							Projections.sum("valorPedido")));			
		    results = consulta.list();
		    
		    consulta = session.createCriteria(Llamadas.class);
		    consulta.add(Restrictions.eq("extension", extension));
		    consulta.add(Restrictions.eq("tipoLlamada", "Llamada entrante respondida"));
		    consulta.setProjection(Projections.rowCount());
		    Long total1 = (Long) consulta.uniqueResult();
		    total1 = (total1 == null)? 0 : total1; 
		    
		    consulta = session.createCriteria(Llamadas.class);
		    consulta.add(Restrictions.eq("extension", extension));
		    consulta.add(Restrictions.eq("tipoLlamada", "Llamada Saliente"));
		    consulta.setProjection(Projections.rowCount());
		    Long total2 = (Long) consulta.uniqueResult();
		    total2 = (total2 == null)? 0 : total2;
		    
		    consulta = session.createCriteria(Llamadas.class);
		    consulta.add(Restrictions.eq("extension", extension));
		    consulta.add(Restrictions.eq("tipoLlamada", "Llamada entrante sin respuesta"));
		    consulta.setProjection(Projections.rowCount());
		    Long total3 = (Long) consulta.uniqueResult();
		    total3 = (total3 == null)? 0 : total3;
		    
		    OfertasPedidos ofePed = new OfertasPedidos();
		    System.out.println(results.size() + "tamaño");		    
		    for (Object[] objects : results) {
		    	System.out.println(objects[1] +""+ objects[2]+""+ objects[3]+ "datoa ingreso" + objects[0] + " - " + idFun + " - "  + extension  );
				
		    	
		    	if(objects[0] != null){
			    	ofePed.setCodEspecialista(((Integer)objects[0] == null)?idFun : (Integer)objects[0]);
					ofePed.setEspecialista((String) objects[1]);
					ofePed.setValorOferta(new BigDecimal(((BigDecimal) objects[2] == null)? "0" : objects[2].toString()));
					ofePed.setValorPedido(new BigDecimal(((BigDecimal) objects[3] == null)? "0" :objects[3].toString()));
					ofePed.setPorcentaje((ofePed.getValorOferta().intValue() == 0 || ofePed.getValorPedido().intValue() == 0)? new BigDecimal("0") : ofePed.getValorPedido().divide(ofePed.getValorOferta(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
					ofePed.setImagen((ofePed.getPorcentaje().intValue() < 81)? "rojo.png" :(ofePed.getPorcentaje().intValue() >= 81 && ofePed.getPorcentaje().intValue() <= 95 )? "amarillo.jpg" : (ofePed.getPorcentaje().intValue() >= 96  && ofePed.getPorcentaje().intValue() <= 100)? "verde.png": "violeta.jpg");
					ofePed.setLlamadaEntrante(total1);
					ofePed.setLlamadaSalientes(total2);
					ofePed.setLlamadaNoContestadas(total3);
		    	}
		    }
		    
		    
			return ofePed;
			
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
}
