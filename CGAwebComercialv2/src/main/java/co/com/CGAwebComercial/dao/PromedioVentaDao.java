package co.com.CGAwebComercial.dao;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.Detalle;
import co.com.CGAwebComercial.entyties.PromedioVenta;
import co.com.CGAwebComercial.util.HibernateUtil;

public class PromedioVentaDao extends GenericDao<PromedioVenta> {

	public PromedioVenta listarMeta(int idFuncionario){

		Session session = HibernateUtil.getSessionfactory().openSession();
		PromedioVenta promedioVenta = new PromedioVenta();
		try {
			Criteria consulta = session.createCriteria(PromedioVenta.class);
			consulta.createAlias("funcionario", "f");
			consulta.add(Restrictions.eq("f.id_funcionario", idFuncionario));
			consulta.add(Restrictions.eq("mes", "Junio"));
			promedioVenta = (PromedioVenta) consulta.uniqueResult();
			return promedioVenta;
		} catch (RuntimeException ex) {
			throw ex;
		}
	}

	//*Promedio de ventas nacional en por linea  "dl " "/dl/vistaModulo"*//
	public Double promedioVentasNacional(int linea){

		Session session = HibernateUtil.getSessionfactory().openSession();

		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();

			Criteria consulta = session.createCriteria(Detalle.class);
			if(linea == 6 ){
				Criterion resul =Restrictions.in("linea", new Integer[]{6,10});
				consulta.add(resul);
			}
			else{
				consulta.add(Restrictions.eq("linea", linea));
			}
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.avg("valorNeto"));
			Double totalWages = (Double) consulta.uniqueResult();
			return totalWages;			

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	//*Promedio de ventas nacional en por linea  "dl " "/dl/vistaModulo"*//
	public Long promedioDescuentoNacional(int linea){

		Session session = HibernateUtil.getSessionfactory().openSession();

		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();

			Criteria consulta = session.createCriteria(Detalle.class);
			if(linea == 6 ){
				Criterion resul =Restrictions.in("linea", new Integer[]{6,10});
				consulta.add(resul);
			}
			else{
				consulta.add(Restrictions.eq("linea", linea));
			}

			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long totalWages = (Long) consulta.uniqueResult();
			totalWages  = (totalWages == null) ? 0: totalWages;
			consulta.setProjection(Projections.sum("valorFacturaBruto"));
			Long totalWages1 = (Long) consulta.uniqueResult();
			totalWages1  = (totalWages1 == null) ? 0: totalWages1;
			
			//Long pro = (totalWages > 0)? totalWages + totalWages1 : totalWages1 - totalWages ;
			Long pro = (totalWages > totalWages1)? Math.abs(totalWages1) - Math.abs(totalWages) : Math.abs(totalWages) - Math.abs(totalWages1) ;
			Double pr = (Double) pro.doubleValue()/totalWages1.doubleValue();
			pr = Math.abs(pr *100);
			pro = pr.longValue();

			return pro;	

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	//*Promedio de ventas Oficina  "dcB " "/dcB/vistaModulo"*//
	public Double promedioVentasOficina(int oficina){

		Session session = HibernateUtil.getSessionfactory().openSession();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();

			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq("sucursal", oficina));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.avg("valorNeto"));
			Double totalWages = (Double) consulta.uniqueResult();
			return totalWages;			

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	
	//*Promedio de ventas Oficina  "ji " "/ji/vistaModulo"*//
		public Double promedioVentasOficinaI(){

			Session session = HibernateUtil.getSessionfactory().openSession();
			try{
				Date fechaFinal = fechaFinal();
				Date fechaInicial = fechaInicial();

				Criteria consulta = session.createCriteria(Detalle.class);
				Criterion resul =Restrictions.in("sucursal", new Integer[]{1000,2000});
				consulta.add(resul);
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.avg("valorNeto"));
				Double totalWages = (Double) consulta.uniqueResult();
				return totalWages;			

			} catch (RuntimeException ex) {
				throw ex;
			}
			finally{
				session.close();
			}
		}

	//*Promedio descuento Oficina  "dcB " "/dcB/vistaModulo"*//
	public Long promedioDescuentoOficina(int oficina){

		Session session = HibernateUtil.getSessionfactory().openSession();

		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();

			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq("sucursal", oficina));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long totalWages = (Long) consulta.uniqueResult();
			totalWages = (totalWages == null)? 0 : totalWages;
			consulta.setProjection(Projections.sum("valorFacturaBruto"));
			Long totalWages1 = (Long) consulta.uniqueResult();
			totalWages1 = (totalWages1 == null)? 0 : totalWages1;
			Long pro = totalWages - totalWages1;

			Double pr = (Double) pro.doubleValue()/totalWages1.doubleValue();
			pr = Math.abs(pr *100);
			pro = pr.longValue();

			return pro;	

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	
	//*Promedio descuento Oficina  "ji " "/ji/vistaModulo"*//
		public Long promedioDescuentoOficinaI(){

			Session session = HibernateUtil.getSessionfactory().openSession();

			try{
				Date fechaFinal = fechaFinal();
				Date fechaInicial = fechaInicial();

				Criteria consulta = session.createCriteria(Detalle.class);
				Criterion resul =Restrictions.in("sucursal", new Integer[]{1000,2000});
				consulta.add(resul);
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("valorNeto"));
				Long totalWages = (Long) consulta.uniqueResult();
				totalWages = (totalWages == null)? 0 : totalWages;
				consulta.setProjection(Projections.sum("valorFacturaBruto"));
				Long totalWages1 = (Long) consulta.uniqueResult();
				totalWages1 = (totalWages1 == null)? 0 : totalWages1;

				Long pro = totalWages - totalWages1;

				Double pr = (Double) pro.doubleValue()/totalWages1.doubleValue();
				pr = Math.abs(pr *100);
				pro = pr.longValue();

				return pro;	

			} catch (RuntimeException ex) {
				throw ex;
			}
			finally{
				session.close();
			}
		}
}
