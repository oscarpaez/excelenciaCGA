package co.com.CGAwebComercial.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.Liquidacion;
import co.com.CGAwebComercial.util.HibernateUtil;

public class LiquidacionDao extends GenericDao<Liquidacion>{
	
	@SuppressWarnings("unchecked")
	public List<Liquidacion> listaLiquidacion(int codSap, String fecMes, String fecYear){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Liquidacion> listaLiquidacion;
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
			Criteria consulta = session.createCriteria(Liquidacion.class);
			consulta.add(Restrictions.eq("codSap", codSap));
			consulta.add(Restrictions.between("fechaLiquidacion", fechaInicial, fechaFinal));
			consulta.addOrder(Order.asc("concepto"));			
			listaLiquidacion = consulta.list();
			
			return listaLiquidacion;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	
	public Liquidacion listaLiquidacionSum(int codSap, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		Liquidacion liquidacion = new Liquidacion();
		List<Long> listResultados = new ArrayList<>();
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
			Criteria consulta = session.createCriteria(Liquidacion.class);
			consulta.add(Restrictions.eq("codSap", codSap));
			consulta.add(Restrictions.between("fechaLiquidacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.groupProperty(("nombre"))); 
			String nombre = (String) consulta.uniqueResult();
			consulta.setProjection(Projections.groupProperty(("codSap"))); 
			int cod = (Integer) consulta.uniqueResult();
			consulta.setProjection(Projections.groupProperty(("fechaLiquidacion")));
			Date fecha = (Date) consulta.uniqueResult();
			consulta.setProjection(Projections.sum("valorAjuste"));
			listResultados.add((Long)consulta.uniqueResult());
			consulta.setProjection(Projections.sum("valorComision"));
			listResultados.add((Long)consulta.uniqueResult());
			consulta.setProjection(Projections.sum("valorTotal"));
			listResultados.add((Long)consulta.uniqueResult());
			liquidacion.setNombre(nombre);
			liquidacion.setCodSap(cod);
			liquidacion.setFechaLiquidacion(fecha);
			liquidacion.setValorAjuste(listResultados.get(0).intValue());
			liquidacion.setValorComision(listResultados.get(1).intValue());
			liquidacion.setValorTotal(listResultados.get(2).intValue());
			
			return liquidacion;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	
	@SuppressWarnings("rawtypes")
	public List<Integer> listaCod(String fecMes, String fecYear){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Integer> listaCod = new ArrayList<>();
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
			Criteria consulta = session.createCriteria(Liquidacion.class);			
			consulta.add(Restrictions.between("fechaLiquidacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.groupProperty(("codSap"))); 
			List l = consulta.list();
			
			for (Iterator iterator = l.iterator(); iterator.hasNext();) {
				Object object = (Object) iterator.next();
				listaCod.add((Integer) object);
			}
			
			return listaCod;
			
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	
	//*Verifica si el usuario ya fue liquidado*//	
	@SuppressWarnings("unchecked")
	public List<Liquidacion>  buscarLiquidacion(String concepto, int codSap){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();
			Criteria consulta = session.createCriteria(Liquidacion.class);	
			consulta.add(Restrictions.eq("codSap", codSap));
			consulta.add(Restrictions.eq("concepto", concepto));
			consulta.add(Restrictions.between("fechaLiquidacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.groupProperty(("codSap"))); 
			List<Liquidacion> liquidacion = consulta.list();
			
			return liquidacion;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
		
	}

}
