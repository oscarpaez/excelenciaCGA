package co.com.CGAwebComercial.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.Detalle;
import co.com.CGAwebComercial.entyties.Funcionario;
import co.com.CGAwebComercial.entyties.Zona_venta;
import co.com.CGAwebComercial.entyties.codUEN;
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

	public List<Funcionario> listarVendedoresParaDirector( List<Zona_venta> ListaZona ){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Funcionario> listaFuncionarios = new ArrayList<>();
		try{

			for (Zona_venta zona_venta : ListaZona) {

				if(zona_venta.getFuncionario().getId_funcionario() != 1){
					Criteria consulta = session.createCriteria(Funcionario.class);
					consulta.add(Restrictions.eq("id_funcionario", zona_venta.getFuncionario().getId_funcionario()));
					Funcionario resultado = (Funcionario)consulta.uniqueResult();
					listaFuncionarios.add(resultado);
				}
			}	
			return listaFuncionarios;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	//*Lista todos los funcionarios de la tabla detalle*//
	@SuppressWarnings("rawtypes")
	public List<Integer>  listarVendedoresPais(String tipo, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Integer> listaFuncionarios = new ArrayList<>();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);


			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.groupProperty(tipo));
			List results = consulta.list();
			for (Iterator iterator = results.iterator(); iterator.hasNext();) {
				Object object = (Object) iterator.next();
				int d = (int) object;
				listaFuncionarios.add(d);
			}

			return listaFuncionarios; 
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	//*Lista todos los funcionarios de la tabla detalle con el nombre *//
	@SuppressWarnings({ "unchecked" })
	public List<Object[]>  listarVendedoresPaisD(String tipo, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		//List<Integer> listaFuncionarios = new ArrayList<>();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);

			String nombre = (tipo.equals("funcionario"))? "nombreEspecialista" : "nombreVendedorInt";
			System.out.println(tipo + "@@" + nombre);
			System.out.println(fechaFinal + "###" + fechaInicial); 
			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			//consulta.setProjection(Projections.groupProperty("nombreEspecialista"));
			consulta.setProjection(Projections.projectionList().add(
					Projections.groupProperty(tipo)).add(Projections.groupProperty(nombre)));				
			List<Object[]> results = consulta.list();
			
			System.out.println(results.size());
			for (Object[] objects : results) {
				System.out.println(objects[0] + "EEEEE" + objects[1]);
			}
			return results; 
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	//*Lista todos los clientes de la tabla detalle por un vendedor *//
	@SuppressWarnings({ "unchecked" })
	public List<Object[]>  listarClientesPaisD(String tipo, int idPersona, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		//List<Integer> listaFuncionarios = new ArrayList<>();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);

			//String nombre = (tipo.equals("funcionario"))? "nombreEspecialista" : "nombreVendedorInt";

			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			//consulta.setProjection(Projections.groupProperty("nombreEspecialista"));
			consulta.setProjection(Projections.projectionList().add(
					Projections.groupProperty("cliente")).add(Projections.groupProperty("nombreCliente")));
			consulta.add(Restrictions.eq(tipo, idPersona));
			List<Object[]> results = consulta.list();

			for (Object[] objects : results) {
				System.out.println(objects[0] + "EEEEE" + objects[1]);
			}
			return results; 
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	//*Lista todos los funcionarios de la tabla detalle por oficina y linea "gg" "/gg/listaVendedores1"*//
	@SuppressWarnings("rawtypes")
	public List<Integer>  listarVendedoresOficinaLinea(int linea, int oficina, String tipo, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Integer> listaFuncionarios = new ArrayList<>();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);


			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq("sucursal", oficina ));
			consulta.add(Restrictions.eq("linea", linea));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.groupProperty(tipo));
			List results = consulta.list();
			for (Iterator iterator = results.iterator(); iterator.hasNext();) {
				Object object = (Object) iterator.next();
				int d = (int) object;
				listaFuncionarios.add(d);
			}

			return listaFuncionarios; 
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	//*Lista todos los funcionarios de la tabla detalle por oficina "gg" "/gg/listaVendedores2"*//
	@SuppressWarnings("rawtypes")
	public List<Integer>  listarVendedoresOficina(int oficina, String tipo, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Integer> listaFuncionarios = new ArrayList<>();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);

			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq("sucursal", oficina ));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.groupProperty(tipo));
			List results = consulta.list();
			for (Iterator iterator = results.iterator(); iterator.hasNext();) {
				Object object = (Object) iterator.next();
				int d = (int) object;
				listaFuncionarios.add(d);
			}
			return listaFuncionarios; 
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}



	//*Lista todos los funcionarios de la tabla detalle para UEN*//
	@SuppressWarnings("rawtypes")
	public List<Integer>  listarVendedoresUEN(String tipo, int linea, int ofi, String uen, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Integer> listaFuncionarios = new ArrayList<>();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);


			int oficina = (ofi == 1 )? ofi*1000 : (ofi == 7 )? 2000 : (ofi+1)*1000 ;

			codUENDao daoUEN = new codUENDao();
			int cod = Integer.parseInt(uen);
			codUEN codUen = daoUEN.buscar(cod);

			Criteria consulta = session.createCriteria(Detalle.class);
			consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq("sucursal", oficina ));
			consulta.add(Restrictions.eq("uenSegmento", codUen.getDescUEN()));
			consulta.add(Restrictions.eq("linea", linea));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.groupProperty(tipo));
			List results = consulta.list();
			for (Iterator iterator = results.iterator(); iterator.hasNext();) {
				Object object = (Object) iterator.next();
				int d = (int) object;
				listaFuncionarios.add(d);
			}

			return listaFuncionarios; 
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	//*Lista todos los funcionarios de la tabla detalle por oficina y linea "gg" "/gg/listaVendedores1"*//
	@SuppressWarnings("rawtypes")
	public List<Integer>  listarVendedoresDirectorLinea(int linea, String tipo, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Integer> listaFuncionarios = new ArrayList<>();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);


			Criteria consulta = session.createCriteria(Detalle.class);
			Criterion resul;
			if(linea == 6){
				resul =Restrictions.or(Restrictions.eq("linea", 6),
				Restrictions.eq("linea", 10));
			}
			else{
				resul =Restrictions.or(Restrictions.eq("linea", linea));
			}
			consulta.add(resul);
			//consulta.add(Restrictions.eq("linea", linea));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.groupProperty(tipo));
			List results = consulta.list();
			for (Iterator iterator = results.iterator(); iterator.hasNext();) {
				Object object = (Object) iterator.next();
				int d = (int) object;
				listaFuncionarios.add(d);
			}

			return listaFuncionarios; 
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}


	//*Lista todos los Directores del pais "gh" "/gh/comisionD"*//
	@SuppressWarnings({ "unchecked" })
	public List<Funcionario> listarDirectorPais(){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Funcionario> resultado = new ArrayList<>();
		try{
			Criteria consulta = session.createCriteria(Funcionario.class);
			consulta.createAlias("comision", "c");
			consulta.createAlias("persona", "p");
			Criterion resul =Restrictions.or(Restrictions.eq("c.idComision", 9),
					Restrictions.eq("c.idComision", 10), Restrictions.eq("c.idComision", 11),
					Restrictions.eq("c.idComision", 12), Restrictions.eq("c.idComision", 13),
					Restrictions.eq("c.idComision", 14));

			consulta.add(resul);
			consulta.addOrder(Order.asc("p.nombre"));
			resultado = consulta.list();
			return resultado;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

}
