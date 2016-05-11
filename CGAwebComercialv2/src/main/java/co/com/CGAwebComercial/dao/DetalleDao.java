package co.com.CGAwebComercial.dao;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.Detalle;
import co.com.CGAwebComercial.entyties.Detallesin;
import co.com.CGAwebComercial.entyties.Funcionario;
import co.com.CGAwebComercial.entyties.Linea;
import co.com.CGAwebComercial.entyties.Plan;
import co.com.CGAwebComercial.entyties.Presupuesto;
import co.com.CGAwebComercial.util.HibernateUtil;

public class DetalleDao extends GenericDao<Detalle> {

	@SuppressWarnings({ "unchecked" })
	public List<Plan> listarPlan(int idPersona){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Detalle> detalle = null;
		List<Plan> listaPlan = new ArrayList<>();
		int linea = 0;
		int valor = 0;
		int idPlan =1;
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();
			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq("funcionario", idPersona));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.addOrder(Order.asc("linea"));			
			detalle = consulta.list();
			if (detalle.size() <= 0){
				Plan  plan = new Plan();
				listaPlan.add(plan);
				
			}
			else{
				for (Detalle  detalle_venta : detalle) {
					Plan  plan = new Plan();
	
					if(linea != detalle_venta.getLinea()){
						linea = detalle_venta.getLinea();
	
						Long valorNeto = listarDetalleSuma(detalle_venta.getLinea(), idPersona);
						int valN = (valorNeto == null)? 0: (int) (long) valorNeto; 
						plan.setIngreso_Real(new BigDecimal(valorNeto * -1));
						
						Long costoTotal = sumaUtilidad(detalle_venta.getLinea(), idPersona);
						int cosT = (costoTotal == null)? 0: (int) (long) costoTotal;
						
						if(cosT == 0){
							valor = 0;
						}
						else{
							
							if(valN < 0){
								valor =  (valN* -1) - (cosT) ;
							}
							else{
								 
								valor =  (valN) - (cosT) ;
							}
						}
						plan.setUtilidad_Real(new BigDecimal(valor ));	
	
						PresupuestoDao dao = new PresupuestoDao();
						List<Presupuesto> pre = dao.datoPorLinea(detalle_venta.getLinea(), idPersona, fechaInicial, fechaFinal);
						for (Presupuesto presupuesto : pre) {
							plan.setIngreso(presupuesto.getIngresos());
							plan.setUtilidad(presupuesto.getUtilidad());
							plan.setDistribucion_Linea(presupuesto.getDistribucionPorLinea());
						}
						FuncionarioDao daoF = new FuncionarioDao();
						Funcionario funcionario = daoF.buscar(idPersona);
						plan.setFuncionario(funcionario);
	
						LineaDao daoL = new LineaDao();
						Linea linea1 = daoL.buscar(linea);
						plan.setLinea(linea1);
						plan.setId_plan(idPlan);
	
						listaPlan.add(plan);
						idPlan ++;
					}
				}
			}	
			return listaPlan;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}

	//listar lineas por funcionario	
	@SuppressWarnings("unchecked")
	public List<Plan> listarPlanPorFechas( int idPersona, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Detalle> detalle = null;
		List<Plan> listaPlan = new ArrayList<>();
		int linea = 0;
		int valor = 0;
		int idPlan =1;
		try{
			Date fechaFinal = fechaFinal(fecMes, fecYear);
			Date fechaInicial = fechaInicial(fecMes, fecYear);
			System.out.println("fechaBuscarlienas" +fechaFinal + "fdf"+ fechaInicial);
			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq("funcionario", idPersona));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.addOrder(Order.asc("linea"));			
			detalle = consulta.list();
			System.out.println("cantidad de detalle" + detalle.size() + idPersona);
			for (Detalle  detalle_venta : detalle) {
				
				Plan  plan = new Plan();
				
				if(linea != detalle_venta.getLinea()){
					linea = detalle_venta.getLinea();

					Long valorNeto = listarDetalleSumaFecha(detalle_venta.getLinea(), idPersona, fechaInicial, fechaFinal);
					int valN = (valorNeto == null)? 0: (int) (long) valorNeto; 
					plan.setIngreso_Real(new BigDecimal(valN * -1));
					
					Long costoTotal = sumaUtilidadFecha(detalle_venta.getLinea(), idPersona, fechaInicial, fechaFinal);
					int cosT= (costoTotal == null)? 0: (int) (long) costoTotal;
					valor = (valN < 0)? 0: (valN < 0) ? (valN* -1) - (cosT): (valN) - (cosT);
					plan.setUtilidad_Real(new BigDecimal(valor ));	

					PresupuestoDao dao = new PresupuestoDao();
					List<Presupuesto> pre = dao.datoPorLinea(detalle_venta.getLinea(), idPersona, fechaInicial, fechaFinal);

					for (Presupuesto presupuesto : pre) {
						plan.setIngreso(presupuesto.getIngresos());
						plan.setUtilidad(presupuesto.getUtilidad());
						plan.setDistribucion_Linea(presupuesto.getDistribucionPorLinea());
					}
					FuncionarioDao daoF = new FuncionarioDao();
					Funcionario funcionario = daoF.buscar(idPersona);
					plan.setFuncionario(funcionario);

					LineaDao daoL = new LineaDao();
					Linea linea1 = daoL.buscar(linea);
					plan.setLinea(linea1);
					plan.setId_plan(idPlan);
					listaPlan.add(plan);
					idPlan ++;
				}
			}
			return listaPlan;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}
    
	
	
	@SuppressWarnings("unchecked")
	public List<Detalle> listarDetalle(int codigo, int idPersona){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Detalle> detalle = null;
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();
			System.out.println("fecha" +fechaFinal + "fdf"+ fechaInicial);
			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq("linea", codigo));
			consulta.add(Restrictions.eq("funcionario", idPersona));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			detalle = consulta.list();
			return detalle;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}

	@SuppressWarnings("unchecked")
	public List<Detalle> listarDetallePorFecha(int codigo, int idPersona,  String fecMes, String fecYear ){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Detalle> detalle = null;
		try{
			Date fechaFinal = fechaFinal(fecMes, fecYear);
			Date fechaInicial = fechaInicial(fecMes, fecYear);
			System.out.println("fecha" +fechaFinal + "fecha ini"+ fechaInicial);
			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq("linea", codigo));
			consulta.add(Restrictions.eq("funcionario", idPersona));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			detalle = consulta.list();
			return detalle;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}


	public Long listarDetalleSuma(int codigo, int idPersona){

		Session session = HibernateUtil.getSessionfactory().openSession();

		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();			
			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq("linea", codigo));
			consulta.add(Restrictions.eq("funcionario", idPersona));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
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

	public Long listarDetalleSumaFecha(int codigo, int idPersona,  Date fechaInicial, Date fechaFinal){

		Session session = HibernateUtil.getSessionfactory().openSession();

		try{
			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq("linea", codigo));
			consulta.add(Restrictions.eq("funcionario", idPersona));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
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
	public Long  sumaUtilidad(int codigo, int idPersona){

		Session session = HibernateUtil.getSessionfactory().openSession();

		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();
			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq("linea", codigo));
			consulta.add(Restrictions.eq("funcionario", idPersona));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("costoTotal"));
			Long totalWages = (Long) consulta.uniqueResult();
			return totalWages;			

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}

	public Long  sumaUtilidadFecha(int codigo, int idPersona, Date fechaInicial, Date fechaFinal){

		Session session = HibernateUtil.getSessionfactory().openSession();

		try{
			
			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq("linea", codigo));
			consulta.add(Restrictions.eq("funcionario", idPersona));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("costoTotal"));
			Long totalWages = (Long) consulta.uniqueResult();
			return totalWages;			

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}
	
	public Double promedioVentas(int idPersona){
		
		Session session = HibernateUtil.getSessionfactory().openSession();

		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();
			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq("funcionario", idPersona));
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
	
	@SuppressWarnings({ "unchecked" })
	public List<Plan> listarPlanSin(int idPersona){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Detallesin> detalle = null;
		List<Plan> listaPlan = new ArrayList<>();
		int linea = 0;
		int valor = 0;
		int idPlan =1;
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();
			System.out.println(idPersona+ "fecha inicial " + fechaInicial+ "fecfin " +fechaFinal);
			Criteria consulta = session.createCriteria(Detallesin.class);
			consulta.add(Restrictions.eq("funcionario", idPersona));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.addOrder(Order.asc("linea"));			
			detalle = consulta.list();

			for (Detallesin  detalle_venta : detalle) {
				Plan  plan = new Plan();

				if(linea != detalle_venta.getLinea()){
					linea = detalle_venta.getLinea();

					Long valorNeto = listarDetalleSuma(detalle_venta.getLinea(), idPersona);
					int valN = (valorNeto == null || valorNeto == 0)? 0: (int) (long) valorNeto;
					plan.setIngreso_Real(new BigDecimal(valorNeto * -1));
					
					Long costoTotal = sumaUtilidadFecha(detalle_venta.getLinea(), idPersona, fechaInicial, fechaFinal);
					int cosT =(costoTotal == null || costoTotal == 0)? 0: (int) (long) costoTotal;
					valor = (valN < 0)? 0: (valN < 0) ? (valN* -1) - (cosT): (valN) - (cosT);
					plan.setUtilidad_Real(new BigDecimal(valor ));	

					PresupuestoDao dao = new PresupuestoDao();
					List<Presupuesto> pre = dao.datoPorLinea(detalle_venta.getLinea(), idPersona, fechaInicial, fechaFinal);
					
					for (Presupuesto presupuesto : pre) {
						plan.setIngreso(presupuesto.getIngresos());
						plan.setUtilidad(presupuesto.getUtilidad());
						plan.setDistribucion_Linea(presupuesto.getDistribucionPorLinea());
					}
					FuncionarioDao daoF = new FuncionarioDao();
					Funcionario funcionario = daoF.buscar(idPersona);
					plan.setFuncionario(funcionario);

					LineaDao daoL = new LineaDao();
					Linea linea1 = daoL.buscar(linea);
					plan.setLinea(linea1);
					plan.setId_plan(idPlan);
					listaPlan.add(plan);
					idPlan ++;
				}
			}
			return listaPlan;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}
	
	@SuppressWarnings("unchecked")
	public List<Plan> listarPlanPorFechasSin(int idPersona, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Detallesin> detalle = null;
		List<Plan> listaPlan = new ArrayList<>();
		int linea = 0;
		int valor = 0;
		int idPlan =1;
		try{
			Date fechaFinal = fechaFinal(fecMes, fecYear);
			Date fechaInicial = fechaInicial(fecMes, fecYear);
			Criteria consulta = session.createCriteria(Detallesin.class);
			consulta.add(Restrictions.eq("funcionario", idPersona));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.addOrder(Order.asc("linea"));			
			detalle = consulta.list();
			
			for (Detallesin  detalle_venta : detalle) {
				PlanDao daoP = new PlanDao();
				Plan  plan = daoP.buscar(idPlan);
				
				if(linea != detalle_venta.getLinea()){
					linea = detalle_venta.getLinea();

					Long valorNeto = listarDetalleSumaFecha(detalle_venta.getLinea(), idPersona, fechaInicial, fechaFinal);
					int valN = 0;
					if(valorNeto == null || valorNeto == 0){
						plan.setIngreso_Real(new BigDecimal("0.00"));
					}
					else{
						valN = (int) (long) valorNeto* -1;
						plan.setIngreso_Real(new BigDecimal(valorNeto * -1));
					} 
					
					Long costoTotal = sumaUtilidadFecha(detalle_venta.getLinea(), idPersona, fechaInicial, fechaFinal);
					int cosT =0;
					if(costoTotal == null || costoTotal == 0){
						//plan.setIngreso_Real(new BigDecimal("0.00"));
					}
					else{
						cosT = (int) (long) costoTotal;
						//plan.setIngreso_Real(new BigDecimal(valorNeto * -1));
					} 
					
					valor =  valN - cosT ;
					
					plan.setUtilidad_Real(new BigDecimal(valor ));	

					System.out.println("Presupuesto" + fechaInicial+ " - " + fechaFinal);

					PresupuestoDao dao = new PresupuestoDao();
					List<Presupuesto> pre = dao.datoPorLinea(detalle_venta.getLinea(), idPersona, fechaInicial, fechaFinal);

					for (Presupuesto presupuesto : pre) {
						plan.setIngreso(presupuesto.getIngresos());
						plan.setUtilidad(presupuesto.getUtilidad());
						plan.setDistribucion_Linea(presupuesto.getDistribucionPorLinea());
					}
					FuncionarioDao daoF = new FuncionarioDao();
					Funcionario funcionario = daoF.buscar(idPersona);
					plan.setFuncionario(funcionario);

					LineaDao daoL = new LineaDao();
					Linea linea1 = daoL.buscar(linea);
					plan.setLinea(linea1);
					plan.setId_plan(idPlan);

					listaPlan.add(plan);
					idPlan ++;
				}
			}

			return listaPlan;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}
	
	@SuppressWarnings({ "unchecked" })
	public List<Plan> listarPlansinX(int idPersona){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Detallesin> detalle = null;
		List<Plan> listaPlan = new ArrayList<>();
		int linea = 0;
		int valor = 0;
		int idPlan =1;
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();
			System.out.println(idPersona+ "fecha inicial " + fechaInicial+ "fecfin " +fechaFinal);
			Criteria consulta = session.createCriteria(Detallesin.class);
			consulta.add(Restrictions.eq("funcionario", idPersona));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.addOrder(Order.asc("linea"));			
			detalle = consulta.list();

			for (Detallesin  detalle_venta : detalle) {
				PlanDao daoP = new PlanDao();
				Plan  plan = daoP.buscar(idPlan);
				
				if(linea != detalle_venta.getLinea()){
					linea = detalle_venta.getLinea();

					Long valorNeto = listarDetalleSuma(detalle_venta.getLinea(), idPersona);

					int valN = (int) (long) valorNeto* -1;
					plan.setIngreso_Real(new BigDecimal(valorNeto * -1));
					Long costoTotal = sumaUtilidad(detalle_venta.getLinea(), idPersona);
					
					int cosT = (int) (long) costoTotal;
					valor =  valN - cosT ;
					plan.setUtilidad_Real(new BigDecimal(valor ));	

					PresupuestoDao dao = new PresupuestoDao();
					List<Presupuesto> pre = dao.datoPorLinea(detalle_venta.getLinea(), idPersona, fechaInicial, fechaFinal);
					
					for (Presupuesto presupuesto : pre) {
						System.out.println("presupuestoooo---" + presupuesto.getIngresos());
						plan.setIngreso(presupuesto.getIngresos());
						plan.setUtilidad(presupuesto.getUtilidad());
						plan.setDistribucion_Linea(presupuesto.getDistribucionPorLinea());
					}
					FuncionarioDao daoF = new FuncionarioDao();
					Funcionario funcionario = daoF.buscar(idPersona);
					plan.setFuncionario(funcionario);

					LineaDao daoL = new LineaDao();
					Linea linea1 = daoL.buscar(linea);
					plan.setLinea(linea1);
					plan.setId_plan(idPlan);

					listaPlan.add(plan);
					idPlan ++;
				}
			}
			return listaPlan;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}
	
	
	/*pruebas*/
	public Long listarDetalleSumaFuncinario(String tipo, int linea, int idPersona, Date fechaInicial, Date fechaFinal){

		Session session = HibernateUtil.getSessionfactory().openSession();

		try{
			System.out.println(fechaFinal+ "$$$$33" + fechaInicial );
			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq("linea", linea));
			consulta.add(Restrictions.eq(tipo, idPersona));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long  totalWages = (Long) consulta.uniqueResult();
			return totalWages;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}
	
	public Long  sumaUtilidadFechaPrueba(String tipo, int codigo, int idPersona, Date fechaInicial, Date fechaFinal){

		Session session = HibernateUtil.getSessionfactory().openSession();

		try{
			
			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq("linea", codigo));
			consulta.add(Restrictions.eq(tipo, idPersona));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("costoTotal"));
			Long totalWages = (Long) consulta.uniqueResult();
			return totalWages;			

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}
	
	//*Carga Inicial Modulo Gestion humana ventas con LBR*// 
	public List<Plan> listarPlanPrueba(String tipo, int idPersona){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Plan> listaPlan = new ArrayList<>();
		int valor = 0;
		int idPlan =1;
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();
			LineaDao daoL = new LineaDao();
			List<Linea> lineas = daoL.listar();
			
			for (Linea linea1 : lineas) {
				
				Plan  plan = new Plan();
				Long valorNeto = listarDetalleSuma(linea1.getId(), idPersona);
				
				int valN = (valorNeto == null) ? 0:(int) (long) valorNeto;
				plan.setIngreso_Real(new BigDecimal(valN* -1));
				
				Long costoTotal = sumaUtilidad(linea1.getId(), idPersona);
				
				int cosT = (costoTotal == null)? 0: (int) (long) costoTotal;
				
				if(cosT == 0){
					valor = 0;
				}
				else{
					
					if(valN < 0){
						valor =  (valN* -1) - (cosT) ;
					}
					else{
						 
						valor =  (valN) - (cosT) ;
					}
				}
					
				plan.setUtilidad_Real(new BigDecimal(valor ));	
				
				PresupuestoDao dao = new PresupuestoDao();
				List<Presupuesto> pre = dao.datoPorLinea(linea1.getId(), idPersona, fechaInicial, fechaFinal);
				
				if(pre.size() > 0) {				
					for (Presupuesto presupuesto : pre) {
						plan.setIngreso(presupuesto.getIngresos());
						plan.setUtilidad(presupuesto.getUtilidad());
						plan.setDistribucion_Linea(presupuesto.getDistribucionPorLinea());
					}
				}
				else{
					plan.setIngreso(new BigDecimal("0.00"));
					plan.setUtilidad(new BigDecimal("0.00"));
					plan.setDistribucion_Linea(new BigDecimal("0.00"));
				}
				FuncionarioDao daoF = new FuncionarioDao();
				Funcionario funcionario = daoF.buscar(idPersona);
				plan.setFuncionario(funcionario);
				plan.setLinea(linea1);
				plan.setId_plan(idPlan);
				listaPlan.add(plan);
				idPlan ++;
			}
			return listaPlan;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}
	
	public Long  sumaUtilidad1(String tipo, int codigo, int idPersona){

		Session session = HibernateUtil.getSessionfactory().openSession();

		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();
			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq("linea", codigo));
			consulta.add(Restrictions.eq(tipo, idPersona));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("costoTotal"));
			Long totalWages = (Long) consulta.uniqueResult();
			return totalWages;			

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}
	
	//*Carga Modulo Gestion humana con los para metros de fecha y funcionario ventas con LBR*//
	public List<Plan> listarPlanPorFechasPrueba(String tipo, int idPersona, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Plan> listaPlan = new ArrayList<>();
		int valor = 0;
		int idPlan =1;
		try{
			Date fechaFinal = fechaFinal(fecMes, fecYear);
			Date fechaInicial = fechaInicial(fecMes, fecYear);
			
			/*Nuevo detalle*/
			LineaDao daoL = new LineaDao();
			List<Linea> lineas = daoL.listar();
			for (Linea linea1 : lineas) {
				
				Plan  plan = new Plan();
				Long valorNeto = listarDetalleSumaFuncinario(tipo, linea1.getId(), idPersona, fechaInicial,  fechaFinal);
				int valN; 
				if(valorNeto == null){
					valN = 0;
				}
				else{
					valN = (int) (long) valorNeto;
				}
				plan.setIngreso_Real(new BigDecimal(valN* -1));
				
				Long costoTotal = sumaUtilidadFechaPrueba(tipo, linea1.getId(), idPersona, fechaInicial, fechaFinal);
				int cosT;
				if(costoTotal == null){
					cosT = 0;
				}
				else{
					 cosT = (int) (long) costoTotal;
				}
				
				
				if(cosT == 0){
						valor = 0;
				}
				else{
					
					if(valN < 0){
						valor =  (valN* -1) - (cosT) ;
					}
					else{
						 
						valor =  (valN) - (cosT) ;
					}
				}
				
				plan.setUtilidad_Real(new BigDecimal(valor ));	
				PresupuestoDao dao = new PresupuestoDao();
				List<Presupuesto> pre = dao.datoPorLinea(linea1.getId(), idPersona, fechaInicial, fechaFinal);
				
				if(pre.size() > 0) {				
					for (Presupuesto presupuesto : pre) {
						plan.setIngreso(presupuesto.getIngresos());
						plan.setUtilidad(presupuesto.getUtilidad());
						plan.setDistribucion_Linea(presupuesto.getDistribucionPorLinea());
					}
				}
				else{
					plan.setIngreso(new BigDecimal("0.00"));
					plan.setUtilidad(new BigDecimal("0.00"));
					plan.setDistribucion_Linea(new BigDecimal("0.00"));
				
				}
				FuncionarioDao daoF = new FuncionarioDao();
				Funcionario funcionario = daoF.buscar(idPersona);
				plan.setFuncionario(funcionario);
				plan.setLinea(linea1);
				plan.setId_plan(idPlan);
				listaPlan.add(plan);
				idPlan ++;
				
				
			}
			////*fin*///
			return listaPlan;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}
	
	//*Carga Inicial Modulo Gestion humana Sin lenta y baja rotacion*//
	public List<Plan> listarPlanSinPrueba(String tipo, int idPersona){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Plan> listaPlan = new ArrayList<>();
		int valor = 0;
		int idPlan =1;
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();
			LineaDao daoL = new LineaDao();
			
			List<Linea> lineas = daoL.listar();			
			for (Linea linea1 : lineas) {
				
				Plan  plan = new Plan();
				
				Long valorNeto = listarDetalleSumaSin(tipo, linea1.getId(), idPersona);				
				
				int valN = (valorNeto == null) ? 0:(int) (long) valorNeto;
				plan.setIngreso_Real(new BigDecimal(valN* -1));
				
				Long costoTotal = sumaUtilidadSin(tipo, linea1.getId(), idPersona);
				
				int cosT = (costoTotal == null)? 0: (int) (long) costoTotal;
				
				if(cosT == 0){
					valor = 0;
				}
				else{
					
					if(valN < 0){
						valor =  (valN* -1) - (cosT) ;
					}
					else{
						 
						valor =  (valN) - (cosT) ;
					}
				}
					
				plan.setUtilidad_Real(new BigDecimal(valor ));	
				
				PresupuestoDao dao = new PresupuestoDao();
				List<Presupuesto> pre = dao.datoPorLinea(linea1.getId(), idPersona, fechaInicial, fechaFinal);
				
				if(pre.size() > 0) {				
					for (Presupuesto presupuesto : pre) {
						plan.setIngreso(presupuesto.getIngresos());
						plan.setUtilidad(presupuesto.getUtilidad());
						plan.setDistribucion_Linea(presupuesto.getDistribucionPorLinea());
					}
				}
				else{
					plan.setIngreso(new BigDecimal("0.00"));
					plan.setUtilidad(new BigDecimal("0.00"));
					plan.setDistribucion_Linea(new BigDecimal("0.00"));
				}
				FuncionarioDao daoF = new FuncionarioDao();
				Funcionario funcionario = daoF.buscar(idPersona);
				plan.setFuncionario(funcionario);
				plan.setLinea(linea1);
				plan.setId_plan(idPlan);
				listaPlan.add(plan);
				idPlan ++;
			}
			
			return listaPlan;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}

	//*Carga Modulo Gestion humana Sin LBR con parametros de fecha y funcionario*//
	public List<Plan> listarPlanPorFechasSinPrueba(String tipo, int idPersona, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Plan> listaPlan = new ArrayList<>();
		int valor = 0;
		int idPlan =1;
		try{
			Date fechaFinal = fechaFinal(fecMes, fecYear);
			Date fechaInicial = fechaInicial(fecMes, fecYear);
			LineaDao daoL = new LineaDao();
			List<Linea> lineas = daoL.listar();
			for (Linea linea1 : lineas) {
				
				System.out.println("cantidad de linEAS" + linea1.getNombre() + idPersona);
				Plan  plan = new Plan();
				Long valorNeto = listarDetalleSumaSinFechas(tipo, linea1.getId(), idPersona, fechaInicial, fechaFinal);				
				int valN; 
				if(valorNeto == null){
					valN = 0;
				}
				else{
					valN = (int) (long) valorNeto;
				}
				plan.setIngreso_Real(new BigDecimal(valN* -1));
				
				Long costoTotal = sumaUtilidadSinFechas(tipo, linea1.getId(), idPersona, fechaInicial, fechaFinal);
				int cosT;
				if(costoTotal == null){
					cosT = 0;
				}
				else{
					 cosT = (int) (long) costoTotal;
				}
				
				
				if(cosT == 0){
						valor = 0;
				}
				else{
					
					if(valN < 0){
						valor =  (valN* -1) - (cosT) ;
					}
					else{
						 
						valor =  (valN) - (cosT) ;
					}
				}
				
				plan.setUtilidad_Real(new BigDecimal(valor ));	

				PresupuestoDao dao = new PresupuestoDao();
				List<Presupuesto> pre = dao.datoPorLinea(linea1.getId(), idPersona, fechaInicial, fechaFinal);
				
				if(pre.size() > 0) {				
					for (Presupuesto presupuesto : pre) {
						plan.setIngreso(presupuesto.getIngresos());
						plan.setUtilidad(presupuesto.getUtilidad());
						plan.setDistribucion_Linea(presupuesto.getDistribucionPorLinea());
					}
				}
				else{
					plan.setIngreso(new BigDecimal("0.00"));
					plan.setUtilidad(new BigDecimal("0.00"));
					plan.setDistribucion_Linea(new BigDecimal("0.00"));
				
				}
				FuncionarioDao daoF = new FuncionarioDao();
				Funcionario funcionario = daoF.buscar(idPersona);
				plan.setFuncionario(funcionario);
				plan.setLinea(linea1);
				plan.setId_plan(idPlan);
				listaPlan.add(plan);
				idPlan ++;
			}
			return listaPlan;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}
	
	public Long listarDetalleSumaSin(String tipo, int codigo, int idPersona){

		Session session = HibernateUtil.getSessionfactory().openSession();

		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();			
			Criteria consulta = session.createCriteria(Detallesin.class);
			consulta.add(Restrictions.eq("linea", codigo));
			consulta.add(Restrictions.eq(tipo, idPersona));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
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
	
	public Long  sumaUtilidadSin(String tipo, int codigo, int idPersona){

		Session session = HibernateUtil.getSessionfactory().openSession();

		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();
			Criteria consulta = session.createCriteria(Detallesin.class);
			consulta.add(Restrictions.eq("linea", codigo));
			consulta.add(Restrictions.eq(tipo, idPersona));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("costoTotal"));
			Long totalWages = (Long) consulta.uniqueResult();
			return totalWages;			

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}
	
	
	
	public Long listarDetalleSumaSinFechas(String tipo, int codigo, int idPersona, Date fechaInicial, Date fechaFinal){

		Session session = HibernateUtil.getSessionfactory().openSession();

		try{
			Criteria consulta = session.createCriteria(Detallesin.class);
			consulta.add(Restrictions.eq("linea", codigo));
			consulta.add(Restrictions.eq(tipo, idPersona));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
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
	
	public Long  sumaUtilidadSinFechas(String tipo, int codigo, int idPersona, Date fechaInicial, Date fechaFinal){

		Session session = HibernateUtil.getSessionfactory().openSession();

		try{
			Criteria consulta = session.createCriteria(Detallesin.class);
			consulta.add(Restrictions.eq("linea", codigo));
			consulta.add(Restrictions.eq(tipo, idPersona));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("costoTotal"));
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
