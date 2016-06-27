package co.com.CGAwebComercial.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.ContadoAnticipo;
import co.com.CGAwebComercial.util.HibernateUtil;

public class ContadoAnticipoDao extends GenericDao<ContadoAnticipo>{

	public Long sumaTotal(int idFuncionario){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();			
			Criteria consulta = session.createCriteria(ContadoAnticipo.class);			
			consulta.add(Restrictions.eq("NoPersonal", idFuncionario));
			consulta.add(Restrictions.between("fecComp", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("totalrecCA"));
			Long totalWages = (Long) consulta.uniqueResult();
			return totalWages;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	public Long sumaTotalFechas(int idFuncionario, String fecMes, String fecYear){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		try{
			Date fechaFinal = fechaFinal(fecMes, fecYear);
			Date fechaInicial = fechaInicial(fecMes, fecYear);		
			Criteria consulta = session.createCriteria(ContadoAnticipo.class);			
			consulta.add(Restrictions.eq("NoPersonal", idFuncionario));
			consulta.add(Restrictions.between("fecComp", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("totalrecCA"));
			Long totalWages = (Long) consulta.uniqueResult();
			return totalWages;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	
	@SuppressWarnings({ "rawtypes" })
	public List<ContadoAnticipo> listaContadoEspecialista(int idFuncionario, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<ContadoAnticipo> listaCA = new ArrayList<>();
		try{
			Date fechaFinal;
			Date fechaInicial;
			
			if (fecMes == null || fecMes.equals("") ){
				fechaFinal = fechaFinal();
				fechaInicial = fechaInicial();	
			}
			else{
				fechaFinal = fechaFinal(fecMes, fecYear);
				fechaInicial = fechaInicial(fecMes, fecYear);
			}
			Query sql = session.createQuery("FROM ContadoAnticipo  c "
					+ "	WHERE NOT EXISTS (FROM ClienteMora cl"
					+ " WHERE   c.cliente = cl.id AND  cl.fechacorte BETWEEN :fecIni  AND  :fecFin )"
					+ " AND  c.NoPersonal = :idFun" 
					+ " AND  c.fecComp  BETWEEN :fecIni  AND  :fecFin ");
			
//			DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
//			String fecIni = formatoFecha.format(fechaInicial);
//			DateFormat formatoFechaFin = new SimpleDateFormat("yyyy-MM-dd");
//			String fecFin = formatoFechaFin.format(fechaFinal);
			sql.setParameter("fecIni", fechaInicial);
			sql.setParameter("fecFin", fechaFinal);
			sql.setParameter("idFun", idFuncionario);
			
//			Query sql = session.createQuery("FROM ContadoAnticipo c  WHERE c.NoPersonal = 219");
					
			List lista = sql.list();
			
			for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
				Object obj = (Object) iterator.next();
				ContadoAnticipo ca = (ContadoAnticipo) obj;
				listaCA.add(ca);
			} 
			return listaCA; 
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	
	@SuppressWarnings({ "rawtypes" })
	public List<ContadoAnticipo> listaContadoInternos(int idFuncionario, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<ContadoAnticipo> listaCA = new ArrayList<>();
		try{
			Date fechaFinal;
			Date fechaInicial;
			
			if (fecMes.equals("")  || fecMes.equals(null)){
				fechaFinal = fechaFinal();
				fechaInicial = fechaInicial();	
			}
			else{
				fechaFinal = fechaFinal(fecMes, fecYear);
				fechaInicial = fechaInicial(fecMes, fecYear);
			}
			Query sql = session.createQuery("FROM ContadoAnticipo  c "
					+ "	WHERE NOT EXISTS (FROM ClienteMora cl"
					+ " WHERE   c.cliente = cl.id AND  cl.fechacorte BETWEEN :fecIni  AND  :fecFin )"
					+ " AND  c.vendInterno = :idFun" 
					+ " AND  c.fecComp  BETWEEN :fecIni  AND  :fecFin ");
			
//			DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
//			String fecIni = formatoFecha.format(fechaInicial);
//			DateFormat formatoFechaFin = new SimpleDateFormat("yyyy-MM-dd");
//			String fecFin = formatoFechaFin.format(fechaFinal);
			sql.setParameter("fecIni", fechaInicial);
			sql.setParameter("fecFin", fechaFinal);
			sql.setParameter("idFun", idFuncionario);
			
			List lista = sql.list();
			
			for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
				Object obj = (Object) iterator.next();
				ContadoAnticipo ca = (ContadoAnticipo) obj;
				listaCA.add(ca);
			} 
			
			return listaCA;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	
}
