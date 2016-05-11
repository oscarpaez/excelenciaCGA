package co.com.CGAwebComercial.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.bajaRotacion;
import co.com.CGAwebComercial.util.HibernateUtil;

public class BajaRotacionDao extends GenericDao<bajaRotacion>{


	public Long SumaTotal(String tipo, int idFuncionario){

		Session session = HibernateUtil.getSessionfactory().openSession();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();			
			Criteria consulta = session.createCriteria(bajaRotacion.class);			
			consulta.add(Restrictions.eq(tipo, idFuncionario));
			Criterion resul =Restrictions.or(Restrictions.eq("almacen", 1020),
					Restrictions.eq("almacen", 2020), Restrictions.eq("almacen", 3020),
					Restrictions.eq("almacen", 4020), Restrictions.eq("almacen", 5020),
					Restrictions.eq("almacen", 6020));
			consulta.add(resul);
			consulta.add(Restrictions.between("fechaFactura", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long totalWages = (Long) consulta.uniqueResult();
			return totalWages;


		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	public Long SumaTotalFechas(String tipo, int idFuncionario, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		try{
			Date fechaFinal = fechaFinal(fecMes, fecYear);
			Date fechaInicial = fechaInicial(fecMes, fecYear);		
			Criteria consulta = session.createCriteria(bajaRotacion.class);			
			consulta.add(Restrictions.eq( tipo, idFuncionario));
			Criterion resul =Restrictions.or(Restrictions.eq("almacen", 1020),
					Restrictions.eq("almacen", 2020), Restrictions.eq("almacen", 3020),
					Restrictions.eq("almacen", 4020), Restrictions.eq("almacen", 5020),
					Restrictions.eq("almacen", 6020));
			consulta.add(resul);
			consulta.add(Restrictions.between("fechaFactura", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long totalWages = (Long) consulta.uniqueResult();
			return totalWages;


		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	
	//*Suma total validando estado de los funcionarios*//
	@SuppressWarnings("unchecked")
	public List<bajaRotacion> listaLBR(String tipo, int idFuncionario, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<bajaRotacion> listaLBR; 
		try{
			Date fechaFinal = fechaFinal(fecMes, fecYear);
			Date fechaInicial = fechaInicial(fecMes, fecYear);		
			Criteria consulta = session.createCriteria(bajaRotacion.class);			
			consulta.add(Restrictions.eq(  tipo, idFuncionario));
			Criterion resul =Restrictions.or(Restrictions.eq("almacen", 1020),
					Restrictions.eq("almacen", 2020), Restrictions.eq("almacen", 3020),
					Restrictions.eq("almacen", 4020), Restrictions.eq("almacen", 5020),
					Restrictions.eq("almacen", 6020));
			consulta.add(resul);
			consulta.add(Restrictions.between("fechaFactura", fechaInicial, fechaFinal));
			listaLBR = consulta.list();
			return listaLBR;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	@SuppressWarnings("unchecked")
	public List<bajaRotacion> listarDetalle(String tipo, int idFuncionario, String fecMes, String fecYear){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		List<bajaRotacion> listaFacturas;
		try{
			Date fechaFinal = fechaFinal(fecMes, fecYear);
			Date fechaInicial = fechaInicial(fecMes, fecYear);	
			Criteria consulta = session.createCriteria(bajaRotacion.class);	
			Criterion resul =Restrictions.or(Restrictions.eq("almacen", 1020),
					Restrictions.eq("almacen", 2020), Restrictions.eq("almacen", 3020),
					Restrictions.eq("almacen", 4020), Restrictions.eq("almacen", 5020),
					Restrictions.eq("almacen", 6020));
			consulta.add(resul);
			consulta.add(Restrictions.eq( tipo, idFuncionario));
			consulta.add(Restrictions.between("fechaFactura", fechaInicial, fechaFinal));
			listaFacturas = consulta.list();
			return listaFacturas;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	
	public Long SumaTotalDirector(int idFuncionario, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		try{
			Date fechaFinal;
			Date fechaInicial;
			if (fecMes.equals("")){
				fechaFinal = fechaFinal();
				fechaInicial = fechaInicial();	
			}
			else{
				fechaFinal = fechaFinal(fecMes, fecYear);
				fechaInicial = fechaInicial(fecMes, fecYear);
			}
			Criteria consulta = session.createCriteria(bajaRotacion.class);			
			Criterion resul =Restrictions.or(Restrictions.eq("almacen", 1020),
					Restrictions.eq("almacen", 2020), Restrictions.eq("almacen", 3020),
					Restrictions.eq("almacen", 4020), Restrictions.eq("almacen", 5020),
					Restrictions.eq("almacen", 6020));
			consulta.add(resul);
			consulta.add(Restrictions.between("fechaFactura", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long totalWages = (Long) consulta.uniqueResult();
			return totalWages;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
}
