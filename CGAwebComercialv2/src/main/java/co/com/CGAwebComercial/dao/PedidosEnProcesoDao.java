package co.com.CGAwebComercial.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.PedidosEnProceso;
import co.com.CGAwebComercial.util.HibernateUtil;


public class PedidosEnProcesoDao  extends GenericDao<PedidosEnProceso>{
	
	public BigDecimal valorPedidosProceso(String tipo, int idFun){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();
			
			tipo =(tipo == "funcionario")? "codEspecialista": "codInterno";
			System.out.println("RRR"+ tipo);
			Criteria consulta = session.createCriteria(PedidosEnProceso.class);
			consulta.add(Restrictions.eq(tipo, idFun));
			consulta.add(Restrictions.between("fechaEntrega", fechaInicial , fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			BigDecimal  totalWages = (BigDecimal) consulta.uniqueResult();
			totalWages =(totalWages == null)? new BigDecimal("0"): totalWages;
			return totalWages;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}

	public BigDecimal valorPedidosProcesoOficina(int sucursal){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();
			
			Criteria consulta = session.createCriteria(PedidosEnProceso.class);
			if(sucursal == 4000){
				Criterion resul =Restrictions.in("oficina", new Integer[]{4000,7000});
				consulta.add(resul);
			}
			else{
				consulta.add(Restrictions.eq("oficina", sucursal ));				
			}
			consulta.add(Restrictions.between("fechaEntrega", fechaInicial , fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			BigDecimal  totalWages = (BigDecimal) consulta.uniqueResult();
			totalWages =(totalWages == null)? new BigDecimal("0"): totalWages;
			return totalWages;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}
	
	@SuppressWarnings("unchecked")
	public List<PedidosEnProceso> pedidosProcesoUsuario(String tipo, int idFun){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<PedidosEnProceso> listaPedidosProceso = new ArrayList<>();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();
			
			Criteria consulta = session.createCriteria(PedidosEnProceso.class);
			consulta.add(Restrictions.eq(tipo , idFun));
			consulta.add(Restrictions.between("fechaEntrega", fechaInicial , fechaFinal));
			listaPedidosProceso = consulta.list();
			return listaPedidosProceso;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}
	
	@SuppressWarnings("unchecked")
	public List<PedidosEnProceso> pedidosProcesoOficina(int sucursal){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		List<PedidosEnProceso> listaPedidosProceso = new ArrayList<>();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();
			
			Criteria consulta = session.createCriteria(PedidosEnProceso.class);
			if(sucursal == 4000){
				Criterion resul =Restrictions.in("oficina", new Integer[]{4000,7000});
				consulta.add(resul);
			}
			else{
				consulta.add(Restrictions.eq("oficina", sucursal ));				
			}
			consulta.add(Restrictions.between("fechaEntrega", fechaInicial , fechaFinal));
			listaPedidosProceso = consulta.list();
			
			return listaPedidosProceso;
			
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}
	
	public BigDecimal valorPedidosProcesoPais(){

		Session session = HibernateUtil.getSessionfactory().openSession();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();

			Criteria consulta = session.createCriteria(PedidosEnProceso.class);
			consulta.add(Restrictions.between("fechaEntrega", fechaInicial , fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			BigDecimal  totalWages = (BigDecimal) consulta.uniqueResult();
			totalWages =(totalWages == null)? new BigDecimal("0"): totalWages;
			return totalWages;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}
	
	@SuppressWarnings("unchecked")
	public List<PedidosEnProceso> pedidosProcesoPais(){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<PedidosEnProceso> listaPedidosProceso = new ArrayList<>();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();
			
			Criteria consulta = session.createCriteria(PedidosEnProceso.class);
			consulta.add(Restrictions.between("fechaEntrega", fechaInicial , fechaFinal));
			listaPedidosProceso = consulta.list();
			return listaPedidosProceso;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}

}
