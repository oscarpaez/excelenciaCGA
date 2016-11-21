package co.com.CGAwebComercial.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.Detallesin;
import co.com.CGAwebComercial.entyties.Llamadas;
import co.com.CGAwebComercial.entyties.OfertasPedidos;
import co.com.CGAwebComercial.entyties.Presupuesto;
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
			consulta.addOrder(Order.desc("valorOferta"));
			consulta.addOrder(Order.asc("valorPedido"));
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
		    
		    OfertasPedidos ofePed = new OfertasPedidos();
		    if(tipo.equals("codInterno")){

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


		    	
		    	System.out.println(results.size() + "tamaño");		    
		    	for (Object[] objects : results) {
		    		System.out.println(objects[1] +""+ objects[2]+""+ objects[3]+ "datoa ingreso" + objects[0] + " - " + idFun + " - "  + extension  );


		    		if(objects[0] != null){
		    			ofePed.setCodEspecialista(((Integer)objects[0] == null)?idFun : (Integer)objects[0]);
		    			ofePed.setEspecialista((String) objects[1]);
		    			ofePed.setValorOferta(new BigDecimal(((BigDecimal) objects[2] == null)? "0" : objects[2].toString()));
		    			ofePed.setValorPedido(new BigDecimal(((BigDecimal) objects[3] == null)? "0" :objects[3].toString()));
		    			ofePed.setPorcentaje((ofePed.getValorOferta().intValue() == 0 || ofePed.getValorPedido().intValue() == 0)? new BigDecimal("0") : ofePed.getValorPedido().divide(ofePed.getValorOferta(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP));
		    			ofePed.setImagen((ofePed.getPorcentaje().intValue() < 81)? "rojo.png" :(ofePed.getPorcentaje().intValue() >= 81 && ofePed.getPorcentaje().intValue() <= 95 )? "amarillo.jpg" : (ofePed.getPorcentaje().intValue() >= 96  && ofePed.getPorcentaje().intValue() <= 100)? "verde.png": "violeta.jpg");
		    			ofePed.setLlamadaEntrante(total1);
		    			ofePed.setLlamadaSalientes(total2);
		    			ofePed.setLlamadaNoContestadas(total3);
		    			List<Long> listaPre = listarPresupuetoVenta(tipo,  idFun);
		    			ofePed.setPresupuesto(listaPre.get(1));
		    			ofePed.setValorNeto(listaPre.get(0) * -1);
		    			System.out.println(ofePed.getValorNeto() + "valores" + ofePed.getPresupuesto());
		    			BigDecimal por = new BigDecimal(ofePed.getValorNeto());
		    			BigDecimal valN = new BigDecimal(ofePed.getPresupuesto());
		    			por = (por.intValue() == 0 || valN.intValue() == 0)? new BigDecimal("0") :por.divide(valN, 4, BigDecimal.ROUND_HALF_UP ).multiply((new BigDecimal("100")));

		    			ofePed.setPorcentajeV((ofePed.getPresupuesto() == 0 || ofePed.getValorNeto() == 0)? new BigDecimal("0") : por ); 
		    			ofePed.setImagenA((ofePed.getPorcentajeV().intValue() < 85)? "rojo.png" : "verde.png");
		    		}
		    	}
		    }
		    else{
		    	for (Object[] objects : results) {
		    		System.out.println(objects[1] +""+ objects[2]+""+ objects[3]+ "datoa ingreso" + objects[0] + " - " + idFun + " - "  + extension  );


		    		if(objects[0] != null){
		    			ofePed.setCodEspecialista(((Integer)objects[0] == null)?idFun : (Integer)objects[0]);
		    			ofePed.setEspecialista((String) objects[1]);
		    			ofePed.setValorOferta(new BigDecimal(((BigDecimal) objects[2] == null)? "0" : objects[2].toString()));
		    			ofePed.setValorPedido(new BigDecimal(((BigDecimal) objects[3] == null)? "0" :objects[3].toString()));
		    			ofePed.setPorcentaje((ofePed.getValorOferta().intValue() == 0 || ofePed.getValorPedido().intValue() == 0)? new BigDecimal("0") : ofePed.getValorPedido().divide(ofePed.getValorOferta(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP));
		    			ofePed.setImagen((ofePed.getPorcentaje().intValue() < 81)? "rojo.png" :(ofePed.getPorcentaje().intValue() >= 81 && ofePed.getPorcentaje().intValue() <= 95 )? "amarillo.jpg" : (ofePed.getPorcentaje().intValue() >= 96  && ofePed.getPorcentaje().intValue() <= 100)? "verde.png": "violeta.jpg");
		    			ofePed.setPresupuesto(new Long(0));
		    			ofePed.setValorNeto(new Long(0));
		    		}
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
	
	/* Se listan El Presupuesto y Venta Facturada vendedor Interno */
	public List<Long> listarPresupuetoVenta(String tipo, int idFun){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Long> listaResul = new ArrayList<>();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();
			
			Criteria consulta = session.createCriteria(Detallesin.class);
			consulta.add(Restrictions.eq("funcionarioI", idFun));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long totalWages = (Long) consulta.uniqueResult();
			totalWages = (totalWages == null)? 0 : totalWages;
			listaResul.add(totalWages);
 			
			consulta = session.createCriteria(Presupuesto.class);
			consulta.add(Restrictions.eq("funcionario", idFun));
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal totalP = (BigDecimal) consulta.uniqueResult();
			System.out.println(totalP + "*****");
			totalP = (totalP == null)? new BigDecimal("0") : totalP; 
			System.out.println(totalP + "////////");
			listaResul.add(totalP.longValue());
			
			return listaResul;
			
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	
	
	public List<BigDecimal> sumaValorOfertasPedidosPais(){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<BigDecimal> listaOferta = new ArrayList<>();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();

			Criteria consulta = session.createCriteria(OfertasPedidos.class);
			consulta.add(Restrictions.between("fechaEntOfe", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorOferta"));
			BigDecimal totalP = (BigDecimal) consulta.uniqueResult();
			totalP = (totalP == null)? new BigDecimal("0") : totalP;
			listaOferta.add(totalP);
			
			consulta.setProjection(Projections.sum("valorPedido"));
			totalP = (BigDecimal) consulta.uniqueResult();
			totalP = (totalP == null)? new BigDecimal("0") : totalP;
			listaOferta.add(totalP);
			
			consulta.setProjection(Projections.count("id"));
			Long t = (Long) consulta.uniqueResult();
			totalP = (t == null)? new BigDecimal("0") : new BigDecimal(t);
			listaOferta.add(totalP);
			
			System.out.println(t + " CCCCCC");
			
			consulta.add(Restrictions.not(Restrictions.eq("nPedido", new Long(0))));
			//consulta.add(Restrictions.ne("valorPedido", new Long(0)));
			consulta.setProjection(Projections.count("valorPedido"));
			t = (Long) consulta.uniqueResult();
			totalP = (t == null)? new BigDecimal("0") : new BigDecimal(t);
			listaOferta.add(totalP);
			
			return listaOferta;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	
	
	public List<BigDecimal> sumaValorOfertasPedidosSucursal(int oficina){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<BigDecimal> listaOferta = new ArrayList<>();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();

			Criteria consulta = session.createCriteria(OfertasPedidos.class);
			consulta.add(Restrictions.eq("codOficina", oficina));
			consulta.add(Restrictions.between("fechaEntOfe", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorOferta"));
			BigDecimal totalP = (BigDecimal) consulta.uniqueResult();
			totalP = (totalP == null)? new BigDecimal("0") : totalP;
			listaOferta.add(totalP);
			
			consulta.setProjection(Projections.sum("valorPedido"));
			totalP = (BigDecimal) consulta.uniqueResult();
			totalP = (totalP == null)? new BigDecimal("0") : totalP;
			listaOferta.add(totalP);
			
			consulta.setProjection(Projections.count("id"));
			Long t = (Long) consulta.uniqueResult();
			totalP = (t == null)? new BigDecimal("0") : new BigDecimal(t);
			listaOferta.add(totalP);
			
			System.out.println(t + " CCCCCC");
			
			consulta.add(Restrictions.not(Restrictions.eq("nPedido", new Long(0))));
			//consulta.add(Restrictions.ne("valorPedido", new Long(0)));
			consulta.setProjection(Projections.count("valorPedido"));
			t = (Long) consulta.uniqueResult();
			totalP = (t == null)? new BigDecimal("0") : new BigDecimal(t);
			listaOferta.add(totalP);
			
			
			System.out.println(t + "FFFFFFFFFff");
			
			return listaOferta;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	
	public List<BigDecimal> sumaValorOfertasPedidosVendedor(String tipo, int codFun){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<BigDecimal> listaOferta = new ArrayList<>();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();

			tipo = (tipo.equals("funcionario")) ? "codEspecialista": "codInterno";
			
			Criteria consulta = session.createCriteria(OfertasPedidos.class);
			consulta.add(Restrictions.eq(tipo, codFun));
			consulta.add(Restrictions.between("fechaEntOfe", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorOferta"));
			BigDecimal totalP = (BigDecimal) consulta.uniqueResult();
			totalP = (totalP == null)? new BigDecimal("0") : totalP;
			listaOferta.add(totalP);
			
			consulta.setProjection(Projections.sum("valorPedido"));
			totalP = (BigDecimal) consulta.uniqueResult();
			totalP = (totalP == null)? new BigDecimal("0") : totalP;
			listaOferta.add(totalP);
			
			consulta.setProjection(Projections.count("id"));
			Long t = (Long) consulta.uniqueResult();
			totalP = (t == null)? new BigDecimal("0") : new BigDecimal(t);
			listaOferta.add(totalP);
			
			System.out.println(t + " CCCCCC");
			
			consulta.add(Restrictions.not(Restrictions.eq("nPedido", new Long(0))));
			//consulta.add(Restrictions.ne("valorPedido", new Long(0)));
			consulta.setProjection(Projections.count("valorPedido"));
			t = (Long) consulta.uniqueResult();
			totalP = (t == null)? new BigDecimal("0") : new BigDecimal(t);
			listaOferta.add(totalP);
			
			
			System.out.println(t + "FFFFFFFFFff");
			return listaOferta;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	
	/* Se listan las ofertas Buscadas por el numero de oferta */
	@SuppressWarnings("unchecked")
	public List<OfertasPedidos> listaBusquedaOferta(Long oferta){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		List<OfertasPedidos> listaOferta = new ArrayList<>();
		try{
			Criteria consulta = session.createCriteria(OfertasPedidos.class);
			consulta.add(Restrictions.eq("nOferta", oferta));			
			listaOferta = consulta.list();
			return listaOferta;
			
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	

}
