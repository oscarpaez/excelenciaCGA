package co.com.CGAwebComercial.dao;


import java.lang.reflect.ParameterizedType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
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

		Session session = HibernateUtil.getSessionfactory().openSession();
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

		Session session = HibernateUtil.getSessionfactory().openSession();
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

		Session session = HibernateUtil.getSessionfactory().openSession();
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

		Session session = HibernateUtil.getSessionfactory().openSession();
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

		Session session = HibernateUtil.getSessionfactory().openSession();
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

		Session session = HibernateUtil.getSessionfactory().openSession();
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
	
	@SuppressWarnings("rawtypes")
	public List buscarEspecialista(String tipo, int oficina){
		Session session = HibernateUtil.getSessionfactory().openSession();
		try{
			Criteria consulta = session.createCriteria(clase);
			consulta.add(Restrictions.eq("codOficina", oficina));
			consulta.setProjection(Projections.groupProperty(tipo));
			List results = consulta.list();			
			return results;
			
			
		} catch (RuntimeException ex) {			
			throw ex;
		}
		finally{
			session.close();
		}
		
	}
	
	public Date fechaInicial(String fecMes, String fecYear){

		try {
			Date fechaActual;
			DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
			String fechaInicial = fecYear+"-"+fecMes+"-01";
			fechaActual = formatoFecha.parse(fechaInicial);
			return fechaActual;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	public Date fechaInicial(){

		try {
			Date fechaActual = new Date();	   
			DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
			String fec = formatoFecha.format(fechaActual);
			String[]  fecx = fec.split("-");
			String fechaInicial = fecx[0]+"-"+fecx[1]+"-01";
			fechaActual = formatoFecha.parse(fechaInicial);			
			return fechaActual;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public Date fechaFinal(String fecMes, String fecYear){

		try {
			Date fechaActual= new Date();	   
			DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			int mes = Integer.parseInt("" + fecMes.charAt(1));
			mes = mes-1;
			int year = Integer.parseInt(fecYear);
			cal.set(year, mes, 1);
			cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			int dia = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			String fechaInicial = fecYear +"-"+fecMes+"-"+String.valueOf(dia);
     		fechaActual = formatoFecha.parse(fechaInicial);			
			return fechaActual;		
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public Date fechaFinal(){

		try {
			Date fechaActual = new Date();	   
			DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			int dia = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			String fec = formatoFecha.format(fechaActual);
			String[]  fecx = fec.split("-");
			String fechaFinal = fecx[0]+"-"+fecx[1]+"-"+ String.valueOf(dia);
			fechaActual = formatoFecha.parse(fechaFinal);
			return fechaActual;			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public int fechaFinalR(){

		try {
			Date fechaActual = new Date();	   
			DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
			String fec = formatoFecha.format(fechaActual);
			String[]  fecx = fec.split("-");
			int mes = Integer.parseInt(fecx[1]);
			return mes;			
		} catch (RuntimeException e) {
			// TODO: handle exception
		}
		return 0;

	}
}
