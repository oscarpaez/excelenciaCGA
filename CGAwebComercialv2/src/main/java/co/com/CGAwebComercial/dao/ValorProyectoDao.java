package co.com.CGAwebComercial.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.ValorProyecto;
import co.com.CGAwebComercial.util.HibernateUtil;

public class ValorProyectoDao extends GenericDao<ValorProyecto>{
	
	/* Se obtiene el valor de los Proyectos por  Especialista */
	public Long listarProyectos(int idFun){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();
			
			Criteria consulta = session.createCriteria(ValorProyecto.class);
			consulta.createAlias("funcionario", "f");
			consulta.add(Restrictions.eq("f.id_funcionario", idFun));
			consulta.add(Restrictions.between("fechaRegistro", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorMes"));
			Long valor = (Long) consulta.uniqueResult();
			valor = (valor == null)? 0 :valor;
			
			return valor;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	
	/* Se lista el valor de los Proyectos por  Especialista */
	@SuppressWarnings("unchecked")
	public List<ValorProyecto> listarProyectosAsesor(int idFun){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		List<ValorProyecto> listaProyecto = new ArrayList<>();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();
			
			Criteria consulta = session.createCriteria(ValorProyecto.class);
			consulta.createAlias("funcionario", "f");
			consulta.add(Restrictions.eq("f.id_funcionario", idFun));
			consulta.add(Restrictions.between("fechaRegistro", fechaInicial, fechaFinal));			
			listaProyecto =  consulta.list();
			
			return listaProyecto;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	
	/* Se lista el valor de los Proyectos por  Especialista */
	
	public Long sumaProyectosOficina(int idCiu){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();
			
			Criteria consulta = session.createCriteria(ValorProyecto.class);
			consulta.createAlias("ciudad", "c");
			consulta.add(Restrictions.eq("c.id", idCiu));
			consulta.add(Restrictions.between("fechaRegistro", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorMes"));
			Long valor = (Long) consulta.uniqueResult();
			valor = (valor == null)? 0 :valor;
			
			return valor;			
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	
	/* Se lista el valor de los Proyectos por  Oficina */
	@SuppressWarnings("unchecked")
	public List<ValorProyecto> listarProyectosOficina(int idCiu){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		List<ValorProyecto> listaProyecto = new ArrayList<>();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();
			
			Criteria consulta = session.createCriteria(ValorProyecto.class);
			consulta.createAlias("ciudad", "c");
			consulta.add(Restrictions.eq("c.id", idCiu));
			consulta.add(Restrictions.between("fechaRegistro", fechaInicial, fechaFinal));			
			listaProyecto =  consulta.list();
			
			return listaProyecto;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

}
