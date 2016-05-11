package co.com.CGAwebComercial.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.Funcionario;
import co.com.CGAwebComercial.util.HibernateUtil;

public class FuncionarioDao extends GenericDao<Funcionario> {
	
	public Funcionario buscarPersona(int idPersona){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		try {
			Criteria consulta = session.createCriteria(Funcionario.class);
			consulta.createAlias("persona", "p");
			consulta.add(Restrictions.eq("p.cedula", idPersona));
			Funcionario resultado = (Funcionario)consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Funcionario> listarVendedores(){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		try {
			Criteria consulta = session.createCriteria(Funcionario.class);
			consulta.createAlias("comision", "c");
			consulta.createAlias("persona", "p");
			Criterion resul =Restrictions.or(Restrictions.eq("c.idComision", 1),
					Restrictions.eq("c.idComision", 2), Restrictions.eq("c.idComision", 3),
					Restrictions.eq("c.idComision", 4), Restrictions.eq("c.idComision", 5));
			
			consulta.add(resul);
			consulta.addOrder(Order.asc("p.nombre"));
			List<Funcionario> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Funcionario> listarVendedoresInternos(){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		try {
			Criteria consulta = session.createCriteria(Funcionario.class);
			consulta.createAlias("comision", "c");
			consulta.createAlias("persona", "p");
			Criterion resul =Restrictions.or(Restrictions.eq("c.idComision", 6), Restrictions.eq("c.idComision", 7));			
			consulta.add(resul);
			consulta.addOrder(Order.asc("p.nombre"));
			List<Funcionario> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

}
