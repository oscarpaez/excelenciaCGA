package co.com.CGAwebComercial.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.Detalle;
import co.com.CGAwebComercial.entyties.Presupuesto;
import co.com.CGAwebComercial.util.HibernateUtil;

public class PresupuestoDao extends GenericDao<Presupuesto>{
	
	@SuppressWarnings("unchecked")
	public List<Presupuesto> datoPorLinea(int codigo, int idPersona, Date fechaInicial, Date fechaFinal){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Presupuesto> presupuesto = null;
		try{
			Criteria consulta = session.createCriteria(Presupuesto.class);			
			consulta.add(Restrictions.eq("linea", codigo));
			consulta.add(Restrictions.eq("funcionario", idPersona));
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			presupuesto = consulta.list();
			
			return presupuesto;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}
	
	@SuppressWarnings("unchecked")
	public List<Presupuesto> datoPorLinea( int idPersona){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Presupuesto> presupuesto = null;
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();
			Criteria consulta = session.createCriteria(Detalle.class);			
			consulta.add(Restrictions.eq("funcionario", idPersona));
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			presupuesto = consulta.list();
			
			return presupuesto;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}
	
	
	public BigDecimal datoPorLineaSum(int idPersona){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		
		try{
			BigDecimal totalWages = new BigDecimal("0.00");
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();
			System.out.println(fechaInicial +"-" +fechaFinal);
			Criteria consulta = session.createCriteria(Presupuesto.class);	
			consulta.add(Restrictions.eq("funcionario", idPersona));
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			totalWages = (BigDecimal) consulta.uniqueResult();
			return totalWages;
			
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}
	
public BigDecimal datoPorLineaSumFechas(int idPersona, String fecMes, String fecYear){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		
		try{
			Date fechaFinal = fechaFinal(fecMes, fecYear);
			Date fechaInicial = fechaInicial(fecMes, fecYear);
			Criteria consulta = session.createCriteria(Presupuesto.class);	
			consulta.add(Restrictions.eq("funcionario", idPersona));
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal totalWages = (BigDecimal) consulta.uniqueResult();
			return totalWages;
			
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}
}
