package co.com.CGAwebComercial.dao.Encuesta;


import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.util.HibernateUtil;

public class GenericDao<Entidad> {

	private Class<Entidad> clase;

	@SuppressWarnings("unchecked")
	public GenericDao() {
		this.clase =  (Class<Entidad>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	public void salvar(Entidad entidad){

		Session session = HibernateUtil.getSessionfactory2().openSession();
		Transaction transacion = null;
		try {
			transacion = session.beginTransaction();
			session.save(entidad);
			transacion.commit();
		} catch (RuntimeException ex) {
			if(transacion != null){
				transacion.rollback();
			}
			throw ex;
		}
		finally{
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Entidad> listar(){

		Session session = HibernateUtil.getSessionfactory2().openSession();
		try {
			Criteria consulta = session.createCriteria(clase);			
			List<Entidad> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Entidad> listar(String campo){

		Session session = HibernateUtil.getSessionfactory2().openSession();
		try {
			Criteria consulta = session.createCriteria(clase);
			consulta.addOrder(Order.asc(campo));
			List<Entidad> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public Entidad buscar(int codigo){

		Session session = HibernateUtil.getSessionfactory2().openSession();
		try {
			Criteria consulta = session.createCriteria(clase);
			consulta.add(Restrictions.idEq(codigo));
			Entidad resultado = (Entidad)consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	public void borrar(Entidad entidad){

		Session session = HibernateUtil.getSessionfactory2().openSession();
		Transaction transacion = null;
		try {
			transacion = session.beginTransaction();
			session.delete(entidad);
			transacion.commit();
		} catch (RuntimeException ex) {
			if(transacion != null){
				transacion.rollback();
			}
			throw ex;
		}
		finally{
			session.close();
		}
	}

	public void editar(Entidad entidad){

		Session session = HibernateUtil.getSessionfactory2().openSession();
		Transaction transacion = null;
		try {
			transacion = session.beginTransaction();
			session.update(entidad);
			transacion.commit();
		} catch (RuntimeException ex) {
			if(transacion != null){
				transacion.rollback();
			}
			throw ex;
		}
		finally{
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public Entidad merge(Entidad entidad){

		Session session = HibernateUtil.getSessionfactory().openSession();
		Transaction transacion = null;
		try {
			transacion = session.beginTransaction();
			Entidad retorno = (Entidad) session.merge(entidad);
			transacion.commit();
			return retorno;
		} catch (RuntimeException ex) {
			if(transacion != null){
				transacion.rollback();
			}
			throw ex;
		}
		finally{
			session.close();
		}
	}	
}
