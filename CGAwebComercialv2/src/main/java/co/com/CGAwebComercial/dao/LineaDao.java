package co.com.CGAwebComercial.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.Ciudad;
import co.com.CGAwebComercial.entyties.Detalle;
import co.com.CGAwebComercial.entyties.Esquemas;
import co.com.CGAwebComercial.entyties.Funcionario;
import co.com.CGAwebComercial.entyties.Linea;
import co.com.CGAwebComercial.entyties.Plan;
import co.com.CGAwebComercial.entyties.PresupuestoE;
import co.com.CGAwebComercial.entyties.codUEN;
import co.com.CGAwebComercial.util.ComisionVendedores;
import co.com.CGAwebComercial.util.HibernateUtil;

public class LineaDao extends GenericDao<Linea> {



	//*Lista el presupuesto y el real de la linea en una oficina  *//
	public List<Plan>  listarLineas (String tipo, int oficina, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Plan> listaPlan = new ArrayList<>();
		int valor = 0;
		int idPlan =1;
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);

			/*Lista las lineas por oficina*/
			LineaDao daoL = new LineaDao();
			List<Linea> lineas = daoL.listar();
			for (Linea linea1 : lineas) {

				Plan  plan = new Plan();
				List <Long> lista = sumaValorNetoDeLineaPorOficina(tipo, linea1.getId(), oficina, fechaInicial,  fechaFinal);

				int valN = (lista.get(0) == null)? 0 : (int) (long) lista.get(0);
				int cosT = (lista.get(1) == null)? 0 : (int) (long) lista.get(1) ;
				valor = (cosT == 0) ? 0 : (valN < 0) ? (valN* -1) - (cosT) : (valN) - (cosT) ; 

				plan.setIngreso_Real(new BigDecimal(valN* -1));
				plan.setUtilidad_Real(new BigDecimal(valor ));

				PresupuestoDao dao = new PresupuestoDao();
				List<BigDecimal> pre = dao.datoPorLineaPais(linea1.getId(), oficina, fechaInicial, fechaFinal);

				plan.setIngreso(pre.get(0));
				plan.setUtilidad(pre.get(1));
				if(plan.getIngreso_Real().longValue() == 0 || plan.getIngreso_Real() == null
						|| plan.getIngreso().longValue() == 0 || plan.getIngreso() == null){
					plan.setIngreso_Cumplimiento(new BigDecimal("0"));
				}
				else{
					plan.setIngreso_Cumplimiento(plan.getIngreso_Real().divide(plan.getIngreso(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
					plan.setIngreso_Cumplimiento(plan.getIngreso_Cumplimiento().setScale(2, BigDecimal.ROUND_HALF_UP));
				}

				String semaforo = (plan.getIngreso_Cumplimiento().intValue() >= 85)? "verde.png" : "rojo.png";
				plan.setImagen1(semaforo);

				if(plan.getIngreso_Real().longValue() == 0 || plan.getIngreso_Real() == null
						|| plan.getUtilidad().longValue() == 0 || plan.getUtilidad() == null){
					plan.setUtilidad_Cumplimiento(new BigDecimal("0"));
				}
				else{
					plan.setUtilidad_Cumplimiento(plan.getUtilidad_Real().divide(plan.getUtilidad(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
					plan.setUtilidad_Cumplimiento(plan.getUtilidad_Cumplimiento().setScale(2, BigDecimal.ROUND_HALF_UP));
				}

				semaforo = (plan.getUtilidad_Cumplimiento().intValue() >= 85)? "verde.png" : "rojo.png";
				plan.setImagen(semaforo);

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

	//*Suma le valor real y utilidad de una oficina por linea  "GG" "gg/linea2"*//
	public List <Long> sumaValorNetoDeLineaPorOficina(String tipo, int linea, int oficina, Date fechaInicial, Date fechaFinal){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List <Long> lista = new ArrayList<>();
		try{
			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq("linea", linea));
			//consulta.add(Restrictions.eq("centroFabricacion", oficina));
			consulta.add(Restrictions.eq("sucursal", oficina));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long  totalWages = (Long) consulta.uniqueResult();
			lista.add(totalWages);
			consulta.setProjection(Projections.sum("costoTotal"));
			totalWages = (Long) consulta.uniqueResult();
			lista.add(totalWages);
			return lista;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}


	//*Lista el presupuesto y el real de la linea en una oficina  *//
	public ComisionVendedores  listarVendedoresPorLinea (String tipo, int linea, int idfuncionario, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		Plan  plan = new Plan();
		ComisionVendedores vendedor = new ComisionVendedores();
		int valor = 0;
		//int idPlan =1;
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);

			List <Long> lista = sumaLineaPorVendedor( tipo,  linea, idfuncionario,  fechaInicial, fechaFinal);

			int valN = (lista.get(0) == null)? 0 : (int) (long) lista.get(0);
			int cosT = (lista.get(1) == null)? 0 : (int) (long) lista.get(1) ;
			valor = (cosT == 0) ? 0 : (valN < 0) ? (valN* -1) - (cosT) : (valN) - (cosT) ; 

			vendedor.setIngresoRealB(new BigDecimal(valN* -1));
			vendedor.setUtilidadReal(new BigDecimal(valor ));
			plan.setIngreso_Real(new BigDecimal(valN* -1));
			plan.setUtilidad_Real(new BigDecimal(valor ));

			PresupuestoDao dao = new PresupuestoDao();
			List<BigDecimal> pre = dao.sumaLineaPorFuncionario(linea, idfuncionario, fechaInicial, fechaFinal);

			if(pre != null){
				vendedor.setPresupuestoB(pre.get(0));
				vendedor.setUtilpresupuesto(pre.get(1));
				plan.setIngreso(pre.get(0));
				plan.setUtilidad(pre.get(1));

				if(vendedor.getPresupuestoB() == null || vendedor.getPresupuestoB().intValue() == 0
						||	vendedor.getIngresoRealB() == null  || vendedor.getIngresoRealB().intValue() == 0){
					vendedor.setPresupuestoB((vendedor.getPresupuestoB() == null)? new BigDecimal("0"):vendedor.getPresupuestoB());
					vendedor.setCumplimiento(new BigDecimal("0.00"));
					plan.setIngreso_Cumplimiento(new BigDecimal("0.00"));
				}
				else{
					vendedor.setCumplimiento(vendedor.getIngresoRealB().divide(vendedor.getPresupuestoB(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
					plan.setIngreso_Cumplimiento(plan.getIngreso_Real().divide(plan.getIngreso(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				}

				String semaforo = (vendedor.getCumplimiento().intValue() >= 85)? "verde.png" : "rojo.png";
				vendedor.setImagen1(semaforo);
				plan.setImagen1(semaforo);

				if(vendedor.getUtilidadReal() == null || vendedor.getUtilidadReal().intValue() == 0
						||	vendedor.getUtilpresupuesto() == null  || vendedor.getUtilpresupuesto().intValue() == 0){
					vendedor.setUtilpresupuesto((vendedor.getUtilpresupuesto() == null)? new BigDecimal("0.00") : vendedor.getUtilpresupuesto());  
					vendedor.setCumplimientoU(new BigDecimal("0.00"));
					plan.setUtilidad_Cumplimiento(new BigDecimal("0.00"));
				}
				else{
					vendedor.setCumplimientoU(vendedor.getUtilidadReal().divide(vendedor.getUtilpresupuesto(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
					plan.setUtilidad_Cumplimiento(plan.getUtilidad_Real().divide(plan.getUtilidad(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				}	

				semaforo = (vendedor.getCumplimientoU().intValue() >= 85)? "verde.png" : "rojo.png";
				plan.setImagen(semaforo);
				vendedor.setImagen(semaforo);
				FuncionarioDao daoF = new FuncionarioDao();
				Funcionario funcionario = daoF.buscar(idfuncionario);

				vendedor.setCedula(funcionario.getPersona().getCedula());
				vendedor.setNombre(funcionario.getPersona().getNombre());
				vendedor.setId(funcionario.getId_funcionario());
				vendedor.setTipo(funcionario.getComision().getNombre());

				return vendedor;
			}

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
		return null;	
	}

	//*Suma el real y la utilidad por linea de un vendedor *//
	public List <Long> sumaLineaPorVendedor(String tipo, int linea, int idPersona, Date fechaInicial, Date fechaFinal){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List <Long> lista = new ArrayList<>();
		try{

			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq("linea", linea));
			consulta.add(Restrictions.eq(tipo, idPersona));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long  totalWages = (Long) consulta.uniqueResult();
			lista.add(totalWages);
			consulta.setProjection(Projections.sum("costoTotal"));
			totalWages = (Long) consulta.uniqueResult();
			lista.add(totalWages);
			return lista;


		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}

	//*Lista el presupuesto y el real de los Vendedores por ciudad *//
	public ComisionVendedores  listarVendedoresPorCiudad (int idCiudad, String tipo, int idfuncionario, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		Plan  plan = new Plan();
		ComisionVendedores vendedor = new ComisionVendedores();
		int valor = 0;
		//int idPlan =1;
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);
			List <Long> lista = sumaVendedorPorCiudadG( tipo, idCiudad,  idfuncionario,  fechaInicial, fechaFinal);

			int valN = (lista.get(0) == null)? 0 : (int) (long) lista.get(0);
			int cosT = (lista.get(1) == null)? 0 : (int) (long) lista.get(1) ;
			valor = (cosT == 0) ? 0 : (valN < 0) ? (valN* -1) - (cosT) :(cosT < 0)? (valN) - (cosT* -1) : (valN) - (cosT) ; 

			vendedor.setIngresoRealB(new BigDecimal(valN* -1));
			vendedor.setUtilidadReal(new BigDecimal(valor ));
			plan.setIngreso_Real(new BigDecimal(valN* -1));
			plan.setUtilidad_Real(new BigDecimal(valor ));

			PresupuestoDao dao = new PresupuestoDao();
			List<BigDecimal> pre = dao.sumaPresupuestoVendedorPorCiudad(idCiudad, idfuncionario, fechaInicial, fechaFinal);
			if( pre.get(1)  != null  ||  pre.get(0) != null  ){
				vendedor.setPresupuestoB((pre.get(0)==null)? new BigDecimal("0"):pre.get(0));
				vendedor.setUtilpresupuesto((pre.get(1)== null)? new BigDecimal("0"):pre.get(1));
				plan.setIngreso(pre.get(0));
				plan.setUtilidad(pre.get(1));
				if(vendedor.getIngresoRealB().intValue() == 0 || vendedor.getPresupuestoB().intValue() ==0){
					vendedor.setCumplimiento(new BigDecimal("0"));
				}
				else{
					vendedor.setCumplimiento(vendedor.getIngresoRealB().divide(vendedor.getPresupuestoB(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
					vendedor.setCumplimiento(vendedor.getCumplimiento().setScale(2, BigDecimal.ROUND_HALF_UP));
					plan.setIngreso_Cumplimiento(plan.getIngreso_Real().divide(plan.getIngreso(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				}	
				String semaforo = (vendedor.getCumplimiento().intValue() >= 85)? "verde.png" : "rojo.png";
				vendedor.setImagen1(semaforo);
				plan.setImagen1(semaforo);
				if(vendedor.getUtilidadReal().intValue() ==0 || vendedor.getUtilpresupuesto().intValue() ==0){
					vendedor.setCumplimientoU(new BigDecimal("0"));
				}
				else{
					vendedor.setCumplimientoU(vendedor.getUtilidadReal().divide(vendedor.getUtilpresupuesto(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
					vendedor.setCumplimientoU(vendedor.getCumplimientoU().setScale(2, BigDecimal.ROUND_HALF_UP));
					plan.setUtilidad_Cumplimiento(plan.getUtilidad_Real().divide(plan.getUtilidad(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				}

				semaforo = (vendedor.getCumplimientoU().intValue() >= 85)? "verde.png" : "rojo.png";
				plan.setImagen(semaforo);
				vendedor.setImagen(semaforo);
				FuncionarioDao daoF = new FuncionarioDao();
				Funcionario funcionario = daoF.buscar(idfuncionario);

				vendedor.setCedula(funcionario.getPersona().getCedula());
				vendedor.setNombre(funcionario.getPersona().getNombre());
				vendedor.setId(funcionario.getId_funcionario());
				vendedor.setTipo(funcionario.getComision().getNombre());

				return vendedor;
			}
			else{
				vendedor.setPresupuestoB(new BigDecimal("0.00"));
				vendedor.setUtilpresupuesto(new BigDecimal("0.00"));
				vendedor.setCumplimiento(new BigDecimal("0.00"));
				vendedor.setCumplimientoU(new BigDecimal("0.00"));
				vendedor.setImagen1("rojo.png");
				vendedor.setImagen("rojo.png");
				DetalleDao daoD = new DetalleDao();
				List<Detalle> detalle = daoD.fucionarioPais(tipo, idfuncionario);
				if(tipo.equals("funcionario")){
					if(detalle.get(0).getCedulaEspecialista() == 0){
						vendedor.setCedula(2);
					}
					else{
						vendedor.setCedula(detalle.get(0).getCedulaEspecialista());
					}

					vendedor.setNombre(detalle.get(0).getNombreEspecialista());
				}
				else{
					vendedor.setCedula(detalle.get(0).getCedulaVendedorInterno());
					vendedor.setNombre(detalle.get(0).getNombreVendedorInt());
				}
				vendedor.setId(idfuncionario);
				vendedor.setTipo("No especialista");
				return vendedor;
			}

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}

	}

	//*Lista el presupuesto y el real de los Vendedores por ciudad por linea*//
	public ComisionVendedores  listarVendedoresPorCiudadPorLinea (int linea, int idCiudad, String tipo, int idfuncionario, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		ComisionVendedores vendedor = new ComisionVendedores();
		int valor = 0;
		//int idPlan =1;
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);

			int oficina = (idCiudad== 1 )? 1000 : (idCiudad == 7 )? 2000 : (idCiudad+1)*1000 ;
			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq("sucursal", oficina));
			consulta.add(Restrictions.eq("linea", linea));
			consulta.add(Restrictions.eq(tipo, idfuncionario));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long  totalWages = (Long) consulta.uniqueResult();
			int valN = (totalWages == null)? 0 : (int) (long) totalWages;
			consulta.setProjection(Projections.sum("costoTotal"));
			totalWages = (Long) consulta.uniqueResult();
			int cosT = (totalWages == null)? 0 : (int) (long) totalWages;		

			valor = (cosT == 0) ? 0 : (valN < 0) ? (valN* -1) - (cosT) : (valN) - (cosT) ; 
			vendedor.setIngresoRealB(new BigDecimal(valN* -1));
			vendedor.setUtilidadReal(new BigDecimal(valor ));

			consulta = session.createCriteria(PresupuestoE.class);
			consulta.add(Restrictions.eq("linea", linea));
			consulta.add(Restrictions.eq("funcionario", idfuncionario));
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal valorp = (BigDecimal) consulta.uniqueResult();
			vendedor.setPresupuestoB((valorp==null)? new BigDecimal("0"):valorp);

			consulta.setProjection(Projections.sum("utilidad"));
			valorp = (BigDecimal) consulta.uniqueResult();
			vendedor.setUtilpresupuesto((valorp== null)? new BigDecimal("0"):valorp);

			if(vendedor.getIngresoRealB().intValue() == 0 || vendedor.getPresupuestoB().intValue() ==0){
				vendedor.setCumplimiento(new BigDecimal("0"));
			}
			else{
				vendedor.setCumplimiento(vendedor.getIngresoRealB().divide(vendedor.getPresupuestoB(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));

			}	
			String semaforo = (vendedor.getCumplimiento().intValue() >= 85)? "verde.png" : "rojo.png";
			vendedor.setImagen1(semaforo);

			if(vendedor.getUtilidadReal().intValue() ==0 || vendedor.getUtilpresupuesto().intValue() ==0){
				vendedor.setCumplimientoU(new BigDecimal("0"));
			}
			else{
				vendedor.setCumplimientoU(vendedor.getUtilidadReal().divide(vendedor.getUtilpresupuesto(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
			}

			semaforo = (vendedor.getCumplimientoU().intValue() >= 85)? "verde.png" : "rojo.png";
			vendedor.setImagen(semaforo);
			FuncionarioDao daoF = new FuncionarioDao();
			Funcionario funcionario = daoF.buscar(idfuncionario);

			if(funcionario != null){
				vendedor.setCedula(funcionario.getPersona().getCedula());
				vendedor.setNombre(funcionario.getPersona().getNombre());
				vendedor.setId(funcionario.getId_funcionario());
				vendedor.setTipo(funcionario.getComision().getNombre());
			}
			else{
				DetalleDao daoD = new DetalleDao();
				List<Detalle> detalle = daoD.fucionarioPais("funcionario", idfuncionario);
				if(tipo.equals("funcionario")){
					if(detalle.get(0).getCedulaEspecialista() == 0){
						vendedor.setCedula(2);
					}
					else{
						vendedor.setCedula(detalle.get(0).getCedulaEspecialista());
					}

					vendedor.setNombre(detalle.get(0).getNombreEspecialista());
				}
				else{
					vendedor.setCedula(detalle.get(0).getCedulaVendedorInterno());
					vendedor.setNombre(detalle.get(0).getNombreVendedorInt());
				}
				vendedor.setId(idfuncionario);
				vendedor.setTipo("No especialista");
			}
			return vendedor;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}

	}

	//*Suma el real y utilidad de todas las lineas de un funcionario *//
	public List <Long>  sumaVendedorPorCiudad(String tipo, int idPersona, Date fechaInicial, Date fechaFinal){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List <Long> lista = new ArrayList<>();
		try{

			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq(tipo, idPersona));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long  totalWages = (Long) consulta.uniqueResult();
			lista.add(totalWages);
			consulta.setProjection(Projections.sum("costoTotal"));
			totalWages = (Long) consulta.uniqueResult();
			lista.add(totalWages);
			return lista;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}

	//*Suma el real y utilidad de todas las lineas de un funcionario *//
	public List <Long>  sumaVendedorPorCiudadG(String tipo, int idCiudad, int idPersona, Date fechaInicial, Date fechaFinal){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List <Long> lista = new ArrayList<>();
		try{

			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq(tipo, idPersona));
			consulta.add(Restrictions.eq("sucursal", idCiudad));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long  totalWages = (Long) consulta.uniqueResult();
			lista.add(totalWages);
			consulta.setProjection(Projections.sum("costoTotal"));
			totalWages = (Long) consulta.uniqueResult();
			lista.add(totalWages);
			return lista;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}

	//*Lista el total del presupuesto y el real de la linea por pais "gg" "gg/linea3"  *//
	public List<Plan>  listarLineasPorPais ( String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Plan> listaPlan = new ArrayList<>();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);

			/*Lista las lineas por oficina*/
			LineaDao daoL = new LineaDao();
			List<Linea> lineas = daoL.listar();

			//FuncionarioDao daoF = new FuncionarioDao();
			//PresupuestoDao daoP = new PresupuestoDao();

			for (Linea linea1 : lineas) {

				Plan  plan = new Plan();

				plan.setIngreso(new BigDecimal("0"));
				plan.setUtilidad(new BigDecimal("0"));

				Criteria consulta = session.createCriteria(PresupuestoE.class);
				consulta.add(Restrictions.eq("linea", linea1.getId()));
				consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("ingresos"));
				BigDecimal valor1 = (BigDecimal) consulta.uniqueResult();
				valor1 = (valor1 == null)? new BigDecimal("0"): valor1;

				plan.setIngreso(valor1);

				consulta.setProjection(Projections.sum("utilidad"));
				valor1 = (BigDecimal) consulta.uniqueResult();
				valor1 = ( valor1 == null)? new BigDecimal("0"): valor1;

				plan.setUtilidad(valor1);

				consulta = session.createCriteria(Detalle.class);
				consulta.add(Restrictions.eq("linea", linea1.getId()));
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("valorNeto"));
				Long valorN = (Long) consulta.uniqueResult();
				valor1 = (valorN == null)? new BigDecimal("0"): new BigDecimal(valorN);
				plan.setIngreso_Real(valor1.multiply(new BigDecimal("-1")));

				consulta.setProjection(Projections.sum("costoTotal"));
				valorN = (Long) consulta.uniqueResult();
				valor1 = (valorN == null)? new BigDecimal("0"): new BigDecimal(valorN);
				plan.setUtilidad_Real(valor1);

				Long valor2 = (plan.getIngreso_Real().longValue() == 0) ? 0 : (plan.getIngreso_Real().longValue() < 0) ? (plan.getIngreso_Real().longValue()* -1) - (plan.getUtilidad_Real().longValue()) : (plan.getIngreso_Real().longValue()) - (plan.getUtilidad_Real().longValue()) ; 
				plan.setUtilidad_Real(new BigDecimal(valor2));

				if(plan.getIngreso_Real().intValue() == 0 || plan.getIngreso().intValue() == 0){
					plan.setIngreso_Cumplimiento(new BigDecimal("0"));
				}
				else{
					plan.setIngreso_Cumplimiento(plan.getIngreso_Real().divide(plan.getIngreso(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
					plan.setIngreso_Cumplimiento(plan.getIngreso_Cumplimiento().setScale(2, BigDecimal.ROUND_HALF_UP));
				}
				String semaforo = (plan.getIngreso_Cumplimiento().intValue() >= 85)? "verde.png" : "rojo.png";
				plan.setImagen1(semaforo);

				if(plan.getUtilidad_Real().intValue() ==0 || plan.getUtilidad().intValue() == 0){
					plan.setUtilidad_Cumplimiento(new BigDecimal("0"));
				}
				else{
					plan.setUtilidad_Cumplimiento(plan.getUtilidad_Real().divide(plan.getUtilidad(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
					plan.setUtilidad_Cumplimiento(plan.getUtilidad_Cumplimiento().setScale(2, BigDecimal.ROUND_HALF_UP));
				}
				semaforo = (plan.getUtilidad_Cumplimiento().intValue() >= 85)? "verde.png" : "rojo.png";
				plan.setImagen(semaforo);
				EsquemasDao daoE = new EsquemasDao();
				List<Esquemas> esquema = daoE.listar();
				plan.setUmbral(esquema.get(0).getUmbralComision());
				plan.setLinea(linea1);
				//plan.setId_plan(idPlan);
				listaPlan.add(plan);
				//idPlan ++;
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

	//*Suma le valor real y utilidad de la linea por pais*//
	public List <Long> sumaDeLineaPorPais(int linea, Date fechaInicial, Date fechaFinal){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List <Long> lista = new ArrayList<>();
		try{
			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq("linea", linea));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long  totalWages = (Long) consulta.uniqueResult();
			lista.add(totalWages);
			consulta.setProjection(Projections.sum("costoTotal"));
			totalWages = (Long) consulta.uniqueResult();
			lista.add(totalWages);
			return lista;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}

	//*Lista el presupuesto y el real de los Vendedores por ciudad  5*//
	public ComisionVendedores  listarVendedoresPorCiudadPorLinea (String tipo, int linea, int idfuncionario, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		Plan  plan = new Plan();
		ComisionVendedores vendedor = new ComisionVendedores();
		int valor = 0;
		//int idPlan =1;
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);

			List <Long> lista = sumaVendedorPorCiudadPorLinea( tipo, linea, idfuncionario,  fechaInicial, fechaFinal);

			int valN = (lista.get(0) == null)? 0 : (int) (long) lista.get(0);
			int cosT = (lista.get(1) == null)? 0 : (int) (long) lista.get(1) ;
			valor = (cosT == 0) ? 0 : (valN < 0) ? (valN* -1) - (cosT) : (valN) - (cosT) ; 

			vendedor.setIngresoRealB(new BigDecimal(valN* -1));
			vendedor.setUtilidadReal(new BigDecimal(valor ));
			plan.setIngreso_Real(new BigDecimal(valN* -1));
			plan.setUtilidad_Real(new BigDecimal(valor ));

			PresupuestoDao dao = new PresupuestoDao();
			List<BigDecimal> pre = dao.sumaLineaPorFuncionario(linea, idfuncionario, fechaInicial, fechaFinal);

			if(pre != null){
				vendedor.setPresupuestoB(pre.get(0));
				vendedor.setUtilpresupuesto(pre.get(1));
				plan.setIngreso(pre.get(0));
				plan.setUtilidad(pre.get(1));

				vendedor.setCumplimiento(vendedor.getIngresoRealB().divide(vendedor.getPresupuestoB(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				plan.setIngreso_Cumplimiento(plan.getIngreso_Real().divide(plan.getIngreso(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				String semaforo = (vendedor.getCumplimiento().intValue() >= 85)? "verde.png" : "rojo.png";
				vendedor.setImagen1(semaforo);
				plan.setImagen1(semaforo);
				vendedor.setCumplimientoU(vendedor.getUtilidadReal().divide(vendedor.getUtilpresupuesto(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				plan.setUtilidad_Cumplimiento(plan.getUtilidad_Real().divide(plan.getUtilidad(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				semaforo = (vendedor.getCumplimientoU().intValue() >= 85)? "verde.png" : "rojo.png";
				plan.setImagen(semaforo);
				vendedor.setImagen(semaforo);
				FuncionarioDao daoF = new FuncionarioDao();
				Funcionario funcionario = daoF.buscar(idfuncionario);

				EsquemasDao daoE = new EsquemasDao();
				List<Esquemas> esquema = daoE.listar(); 
				vendedor.setUmbralCV(esquema.get(0).getUmbralComision());
				vendedor.setCedula(funcionario.getPersona().getCedula());
				vendedor.setNombre(funcionario.getPersona().getNombre());
				vendedor.setId(funcionario.getId_funcionario());
				vendedor.setTipo(funcionario.getComision().getNombre());

				return vendedor;
			}

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
		return null;	
	}

	//*Suma el real y utilidad de funcionario de linea por ciudad 5*//
	public List <Long>  sumaVendedorPorCiudadPorLinea(String tipo, int linea, int idPersona, Date fechaInicial, Date fechaFinal){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List <Long> lista = new ArrayList<>();
		try{

			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq(tipo, idPersona));
			consulta.add(Restrictions.eq("linea", linea));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long  totalWages = (Long) consulta.uniqueResult();
			lista.add(totalWages);
			consulta.setProjection(Projections.sum("costoTotal"));
			totalWages = (Long) consulta.uniqueResult();
			lista.add(totalWages);
			return lista;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}

	//*Lista el presupuesto y el real de los Vendedores por pais 5 "gg" "/gg/listaVendedores"*//
	public ComisionVendedores  listarVendedoresPorPais(String tipo, int idfuncionario, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		Plan  plan = new Plan();
		ComisionVendedores vendedor = new ComisionVendedores();
		int valor = 0;
		//int idPlan =1;
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);

			List <Long> lista = sumaVendedorPorCiudad( tipo,  idfuncionario,  fechaInicial, fechaFinal);

			int valN = (lista.get(0) == null)? 0 : (int) (long) lista.get(0);
			int cosT = (lista.get(1) == null)? 0 : (int) (long) lista.get(1) ;

			valor = (cosT == 0)? 0 : (valN < 0)? (cosT < 0)? (valN* -1)-(cosT* -1 ):(valN* -1)-(cosT): (cosT < 0)? (valN)-(cosT * -1): (valN)-(cosT);
			vendedor.setIngresoRealB(new BigDecimal(valN* -1));
			vendedor.setUtilidadReal(new BigDecimal(valor ));
			plan.setIngreso_Real(new BigDecimal(valN* -1));
			plan.setUtilidad_Real(new BigDecimal(valor ));

			PresupuestoDao dao = new PresupuestoDao();
			List<BigDecimal> pre = null;
			if(tipo.equals("funcionarioI")){
				pre = dao.sumaPresupuestoVendedorI(tipo, idfuncionario, fechaInicial, fechaFinal );
			}
			else{
				pre = dao.sumaPresupuestoVendedor(tipo, idfuncionario, fechaInicial, fechaFinal);
			}

			if(pre.get(0) != null || pre.get(1) != null){
				vendedor.setPresupuestoB(pre.get(0));
				vendedor.setUtilpresupuesto(pre.get(1));
				plan.setIngreso(pre.get(0));
				plan.setUtilidad(pre.get(1));

				vendedor.setCumplimiento(vendedor.getIngresoRealB().divide(vendedor.getPresupuestoB(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP));
				plan.setIngreso_Cumplimiento(plan.getIngreso_Real().divide(plan.getIngreso(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				String semaforo = (vendedor.getCumplimiento().intValue() >= 85)? "verde.png" : "rojo.png";
				vendedor.setImagen1(semaforo);
				plan.setImagen1(semaforo);
				vendedor.setCumplimientoU(vendedor.getUtilidadReal().divide(vendedor.getUtilpresupuesto(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP));
				plan.setUtilidad_Cumplimiento(plan.getUtilidad_Real().divide(plan.getUtilidad(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP));
				semaforo = (vendedor.getCumplimientoU().intValue() >= 85)? "verde.png" : "rojo.png";
				plan.setImagen(semaforo);
				vendedor.setImagen(semaforo);
				FuncionarioDao daoF = new FuncionarioDao();
				Funcionario funcionario = daoF.buscar(idfuncionario);

				vendedor.setCedula(funcionario.getPersona().getCedula());
				vendedor.setNombre(funcionario.getPersona().getNombre());
				vendedor.setId(funcionario.getId_funcionario());
				vendedor.setTipo(funcionario.getComision().getNombre());

				return vendedor;
			}
			else{
				vendedor.setPresupuestoB(new BigDecimal("0.00"));
				vendedor.setUtilpresupuesto(new BigDecimal("0.00"));
				vendedor.setCumplimiento(new BigDecimal("0.00"));
				vendedor.setCumplimientoU(new BigDecimal("0.00"));
				vendedor.setImagen1("rojo.png");
				vendedor.setImagen("rojo.png");
				DetalleDao daoD = new DetalleDao();
				List<Detalle> detalle = daoD.fucionarioPais(tipo, idfuncionario);
				if(tipo.equals("funcionario")){
					if(detalle.get(0).getCedulaEspecialista() == 0){
						vendedor.setCedula(2);
					}
					else{
						vendedor.setCedula(detalle.get(0).getCedulaEspecialista());
					}

					vendedor.setNombre(detalle.get(0).getNombreEspecialista());
				}
				else{
					vendedor.setCedula(detalle.get(0).getCedulaVendedorInterno());
					vendedor.setNombre(detalle.get(0).getNombreVendedorInt());
				}
				vendedor.setId(idfuncionario);
				vendedor.setTipo("No especialista");
				return vendedor;
			}


		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}

	//*Lista el detalle de la UEN*//
	public List<ComisionVendedores> listarUEN(String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<ComisionVendedores> listarUEN = new ArrayList<>();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);

			codUENDao daoC =  new codUENDao();
			List<codUEN> listaUEN = daoC.listar();

			for (codUEN codUEN : listaUEN) {

				ComisionVendedores vendedor = new ComisionVendedores();
				Criteria consulta = session.createCriteria(Detalle.class);
				consulta.add(Restrictions.eq("uenSegmento", codUEN.getDescUEN()));
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("valorNeto"));
				Long  totalWages = (Long) consulta.uniqueResult();
				consulta.setProjection(Projections.sum("costoTotal"));
				Long totalWages1 = (Long) consulta.uniqueResult();

				BigDecimal b = new BigDecimal(totalWages *-1);
				//Integer c = b.abs().intValue();
				//int valN = (totalWages == null)? 0 : (int) (long) totalWages;
				int cosT = (totalWages1 == null)? 0 : (int) (long) totalWages1;
				//valor = (cosT == 0) ? 0 : (valN < 0) ? (valN* -1) - (cosT) : (valN) - (cosT) ; 

				vendedor.setUtilidadReal((cosT == 0) ? new BigDecimal("0") : b.subtract(new BigDecimal(totalWages1)));
				vendedor.setId(codUEN.getId());
				vendedor.setNombre(codUEN.getNombre());
				vendedor.setIngresoRealB(b.abs());

				PresupuestoDao daoP = new PresupuestoDao();
				List<BigDecimal> pre = daoP.sumaPresupuestoUEN(codUEN.getId(),fechaInicial, fechaFinal);

				if(pre != null){
					vendedor.setPresupuestoB(pre.get(0).setScale(0, BigDecimal.ROUND_HALF_UP));
					vendedor.setUtilpresupuesto(pre.get(1).setScale(0, BigDecimal.ROUND_HALF_UP));

					vendedor.setCumplimiento(vendedor.getIngresoRealB().divide(vendedor.getPresupuestoB(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
					vendedor.setCumplimiento(vendedor.getCumplimiento().setScale(2, BigDecimal.ROUND_HALF_UP));
					String semaforo = (vendedor.getCumplimiento().intValue() >= 85)? "verde.png" : "rojo.png";
					vendedor.setImagen1(semaforo);
					vendedor.setCumplimientoU(vendedor.getUtilidadReal().divide(vendedor.getUtilpresupuesto(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
					vendedor.setCumplimientoU(vendedor.getCumplimientoU().setScale(2, BigDecimal.ROUND_HALF_UP));
					semaforo = (vendedor.getCumplimientoU().intValue() >= 85)? "verde.png" : "rojo.png";
					vendedor.setImagen(semaforo);
				}
				listarUEN.add(vendedor);
			}

			return listarUEN;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	//*prueba group by*//

	@SuppressWarnings("rawtypes")
	public List <BigDecimal> listarPru(String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List <BigDecimal> listaTotal = new ArrayList<>();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);


			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.groupProperty("funcionario"));
			List results = consulta.list();

			int id = 0;
			listaTotal.add(0, new BigDecimal("0.00"));
			listaTotal.add(1, new BigDecimal("0.00"));
			listaTotal.add(2, new BigDecimal("0.00"));
			listaTotal.add(3, new BigDecimal("0.00"));
			for (Iterator iterator = results.iterator(); iterator.hasNext();) {
				Object object = (Object) iterator.next();
				id = (Integer) object;

				List<Long>  listaV = sumaDetalleVendedor(id, fechaFinal, fechaInicial);

				listaTotal.set(0, listaTotal.get(0).add(new BigDecimal(listaV.get(0))));
				listaTotal.set(1, listaTotal.get(1).add(new BigDecimal(listaV.get(1))));

				FuncionarioDao daoF = new FuncionarioDao();
				Funcionario funcionario = daoF.buscar(id);

				if(funcionario == null){
					listaTotal.set(2, listaTotal.get(2).add(new BigDecimal("0.00")));
					listaTotal.set(3, listaTotal.get(3).add(new BigDecimal("0.00")));
				}
				else{

					if(funcionario.getComision().getIdComision() >= 1 && funcionario.getComision().getIdComision() <= 5){

						PresupuestoDao daoP = new PresupuestoDao();
						List <BigDecimal> listaPre = daoP.sumaPresupuestoVendedor( "",  id, fechaInicial, fechaFinal);
						listaTotal.set(2, listaTotal.get(2).add(listaPre.get(0)));
						listaTotal.set(3, listaTotal.get(3).add(listaPre.get(1)));
					}
					else{
						listaTotal.set(2, listaTotal.get(2).add(new BigDecimal("0.00")));
						listaTotal.set(3, listaTotal.get(3).add(new BigDecimal("0.00")));
					}
				}
			}
			return listaTotal;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}		
	}

	//*Lista el presupuesto y el real del pais*//
	public List <BigDecimal> listaPais(String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List <BigDecimal> listaTotal = new ArrayList<>();
		listaTotal.add(0, new BigDecimal("0.00"));
		listaTotal.add(1, new BigDecimal("0.00"));
		listaTotal.add(2, new BigDecimal("0.00"));
		listaTotal.add(3, new BigDecimal("0.00"));
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);

			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long  totalWages = (Long) consulta.uniqueResult();
			listaTotal.set(0, new BigDecimal(totalWages));
			consulta.setProjection(Projections.sum("costoTotal"));
			totalWages = (Long) consulta.uniqueResult();
			listaTotal.set(1, new BigDecimal(totalWages));

			consulta = session.createCriteria(PresupuestoE.class);			
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal valor = (BigDecimal) consulta.uniqueResult();
			listaTotal.set(2, valor);
			consulta.setProjection(Projections.sum("utilidad"));
			valor = (BigDecimal) consulta.uniqueResult();
			listaTotal.set(3, valor);

			return listaTotal;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	//*Suma el valor real y costo total del pais*//
	public List <Long> sumaPais(String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List <Long> lista = new ArrayList<>();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);

			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long  totalWages = (Long) consulta.uniqueResult();
			lista.add(totalWages);
			consulta.setProjection(Projections.sum("costoTotal"));
			totalWages = (Long) consulta.uniqueResult();
			lista.add(totalWages);
			return lista;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}

	//*Suma el valor real y costo total del pais*//
	public List <Long> sumaDetalleVendedor(int id, Date fechaFinal, Date fechaInicial){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List <Long> lista = new ArrayList<>();
		try{

			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq("funcionario", id));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long  totalWages = (Long) consulta.uniqueResult();
			lista.add(totalWages);
			consulta.setProjection(Projections.sum("costoTotal"));
			totalWages = (Long) consulta.uniqueResult();
			lista.add(totalWages);
			return lista;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}


	//*Lista el total del presupuesto y el real de la linea por UEN  *//
	public List<ComisionVendedores>  listarLineasUEN (int ofi, String uen, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<ComisionVendedores> listaoficinas = new ArrayList<>();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);

			/*Lista las lineas por oficina*/
			LineaDao daoL = new LineaDao();
			List<Linea> lineas = daoL.listar();

			int oficina = (ofi == 1 )? ofi*1000 : (ofi == 7 )? 2000 : (ofi+1)*1000 ;
			for (Linea linea1 : lineas) {
				ComisionVendedores  sucursales = new ComisionVendedores();
				Criteria consulta = session.createCriteria(PresupuestoE.class);			
				consulta.add(Restrictions.eq("oficinaVentas", oficina ));
				consulta.add(Restrictions.eq("uen", uen ));
				consulta.add(Restrictions.eq("linea", linea1.getId() ));
				consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("ingresos"));
				BigDecimal valor = (BigDecimal) consulta.uniqueResult();

				sucursales.setPresupuestoB((valor == null)? new BigDecimal("0"):valor);
				consulta.setProjection(Projections.sum("utilidad"));
				valor = (BigDecimal) consulta.uniqueResult();
				sucursales.setUtilpresupuesto((valor == null)? new BigDecimal("0"):valor);

				codUENDao daoUEN = new codUENDao();
				int cod = Integer.parseInt(uen);
				codUEN codUen = daoUEN.buscar(cod);

				consulta = session.createCriteria(Detalle.class);
				consulta.add(Restrictions.eq("sucursal", oficina ));
				consulta.add(Restrictions.eq("uenSegmento", codUen.getDescUEN()));
				consulta.add(Restrictions.eq("linea", linea1.getId()));
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("valorNeto"));
				Long  totalWages = (Long) consulta.uniqueResult();
				totalWages = (totalWages == null)? 0: totalWages;
				sucursales.setIngresoRealB(new BigDecimal(totalWages* -1));

				consulta.setProjection(Projections.sum("costoTotal"));
				totalWages = (Long) consulta.uniqueResult();
				totalWages = (totalWages == null)? 0: totalWages;
				sucursales.setUtilidadReal(sucursales.getIngresoRealB().subtract(new BigDecimal(totalWages)));

				if(sucursales.getIngresoRealB().intValue() == 0 || sucursales.getPresupuestoB().intValue() == 0 ){
					sucursales.setCumplimiento(new BigDecimal("0"));
				}
				else{
					sucursales.setCumplimiento(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				}

				String semaforo = (sucursales.getCumplimiento().intValue() >= 85)? "verde.png" : "rojo.png";
				sucursales.setImagen1(semaforo);

				if(sucursales.getUtilidadReal().intValue() == 0 || sucursales.getPresupuestoB().intValue() == 0){
					sucursales.setCumplimientoU(new BigDecimal("0"));
				}
				else{
					sucursales.setCumplimientoU(sucursales.getUtilidadReal().divide(sucursales.getUtilpresupuesto(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				}

				semaforo = (sucursales.getCumplimientoU().intValue() >= 85)? "verde.png" : "rojo.png";
				sucursales.setImagen(semaforo);
				sucursales.setNombre(linea1.getNombre());
				sucursales.setId(linea1.getId());
				listaoficinas.add(sucursales);
			}
			////*fin*///
			return listaoficinas;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}

	//*Lista el total del presupuesto y el real de la linea por UEN  *//

	public ComisionVendedores  listarVendedorUEN(String tipo, int idFuncionario, int linea, int ofi, String uen, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		//List<ComisionVendedores> listaoficinas = new ArrayList<>();
		try{

			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);

			int oficina = (ofi == 1 )? ofi*1000 : (ofi == 7 )? 2000 : (ofi+1)*1000 ;
			ComisionVendedores  sucursales = new ComisionVendedores();
			Criteria consulta = session.createCriteria(PresupuestoE.class);			
			consulta.add(Restrictions.eq("oficinaVentas", oficina ));
			consulta.add(Restrictions.eq("uen", uen ));
			consulta.add(Restrictions.eq("linea", linea ));
			consulta.add(Restrictions.eq( "funcionario", idFuncionario ));
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal valor = (BigDecimal) consulta.uniqueResult();

			sucursales.setPresupuestoB((valor == null)? new BigDecimal("0"):valor);

			consulta.setProjection(Projections.sum("utilidad"));
			BigDecimal utilidad = (BigDecimal) consulta.uniqueResult();
			sucursales.setUtilpresupuesto((valor == null)? new BigDecimal("0"):valor);

			codUENDao daoUEN = new codUENDao();
			int cod = Integer.parseInt(uen);
			codUEN codUen = daoUEN.buscar(cod);

			consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq("sucursal", oficina ));
			consulta.add(Restrictions.eq("uenSegmento", codUen.getDescUEN()));
			consulta.add(Restrictions.eq("linea", linea));
			consulta.add(Restrictions.eq(tipo, idFuncionario));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long  totalWages = (Long) consulta.uniqueResult();
			totalWages = (totalWages == null)? 0: totalWages;
			sucursales.setIngresoRealB(new BigDecimal(totalWages* -1));

			consulta.setProjection(Projections.sum("costoTotal"));
			totalWages = (Long) consulta.uniqueResult();
			totalWages = (totalWages == null)? 0: totalWages;
			sucursales.setUtilidadReal(new BigDecimal(totalWages));

			if(valor != null || utilidad  != null){

				sucursales.setCumplimiento(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP));
				String semaforo = (sucursales.getCumplimiento().intValue() >= 85)? "verde.png" : "rojo.png";
				sucursales.setImagen1(semaforo);

				sucursales.setCumplimientoU(sucursales.getUtilidadReal().divide(sucursales.getUtilpresupuesto(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP));
				semaforo = (sucursales.getCumplimientoU().intValue() >= 85)? "verde.png" : "rojo.png";
				sucursales.setImagen(semaforo);
				FuncionarioDao daoF = new FuncionarioDao();
				Funcionario funcionario = daoF.buscar(idFuncionario);

				sucursales.setCedula(funcionario.getPersona().getCedula());
				sucursales.setNombre(funcionario.getPersona().getNombre());
				sucursales.setId(funcionario.getId_funcionario());
				sucursales.setTipo(funcionario.getComision().getNombre());

				return sucursales;
			}
			else{
				sucursales.setCumplimiento(new BigDecimal("0.00"));
				sucursales.setCumplimientoU(new BigDecimal("0.00"));
				sucursales.setImagen1("rojo.png");
				sucursales.setImagen("rojo.png");
				DetalleDao daoD = new DetalleDao();
				List<Detalle> detalle = daoD.fucionarioPais(tipo, idFuncionario);
				if(tipo.equals("funcionario")){
					if(detalle.get(0).getCedulaEspecialista() == 0){
						sucursales.setCedula(2);
					}
					else{
						sucursales.setCedula(detalle.get(0).getCedulaEspecialista());
					}

					sucursales.setNombre(detalle.get(0).getNombreEspecialista());
				}
				else{
					sucursales.setCedula(detalle.get(0).getCedulaVendedorInterno());
					sucursales.setNombre(detalle.get(0).getNombreVendedorInt());
				}
				sucursales.setId(idFuncionario);
				sucursales.setTipo("No especialista");
				return sucursales;
			}


		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}


	//*Lista las lineas del mix de lineas*//

	public List<ComisionVendedores> listarLineasMix(int idCiudad, String fecMes, String fecYear ){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<ComisionVendedores> listaoficinas = new ArrayList<>();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);
			ComisionVendedores sucursales = null;

			sucursales = new ComisionVendedores();

			int oficina = (idCiudad == 1 )? 1000 : (idCiudad == 7 )? 2000 : (idCiudad+1)*1000 ;

			LineaDao daoL = new LineaDao();
			List<Linea> lineas = daoL.listar();

			for (Linea linea1 : lineas) {
				sucursales = new ComisionVendedores();

				Criteria consulta = session.createCriteria(PresupuestoE.class);			
				consulta.add(Restrictions.eq("linea", linea1.getId()));
				consulta.add(Restrictions.eq("oficinaVentas", oficina ));
				consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("ingresos"));
				BigDecimal valor = (BigDecimal) consulta.uniqueResult();
				sucursales.setPresupuestoB(valor);

				consulta.setProjection(Projections.sum("utilidad"));
				valor = (BigDecimal) consulta.uniqueResult();
				sucursales.setUtilpresupuesto(valor);

				consulta = session.createCriteria(Detalle.class);
				consulta.add(Restrictions.eq("sucursal", oficina ));
				consulta.add(Restrictions.eq("linea", linea1.getId()));
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("valorNeto"));
				Long  totalWages = (Long) consulta.uniqueResult();
				totalWages = (totalWages == null)? 0: totalWages;
				sucursales.setIngresoRealB(new BigDecimal(totalWages* -1));

				consulta.setProjection(Projections.sum("costoTotal"));
				totalWages = (Long) consulta.uniqueResult();
				totalWages = (totalWages == null)? 0: totalWages;
				sucursales.setUtilidadReal(new BigDecimal(totalWages));

				if(sucursales.getIngresoRealB().intValue() == 0 || sucursales.getUtilidadReal().intValue() == 0 ){
					sucursales.setCumplimiento(new BigDecimal("0"));
				}
				else{
					sucursales.setCumplimiento(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP));
				}

				String semaforo = (sucursales.getCumplimiento().intValue() >= 85)? "verde.png" : "rojo.png";
				sucursales.setImagen1(semaforo);

				if(sucursales.getUtilidadReal().intValue() == 0){
					sucursales.setCumplimientoU(new BigDecimal("0"));
				}
				else{
					sucursales.setCumplimientoU(sucursales.getUtilidadReal().divide(sucursales.getUtilpresupuesto(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				}
				semaforo = (sucursales.getCumplimientoU().intValue() >= 85)? "verde.png" : "rojo.png";
				sucursales.setImagen(semaforo);
				sucursales.setNombre(linea1.getNombre());
				listaoficinas.add(sucursales);
			}
			return listaoficinas;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}

	}

	//*Lista las lineas del mix de lineas jefe Internos *//

	public List<ComisionVendedores> listarLineasMixI(String fecMes, String fecYear ){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<ComisionVendedores> listaoficinas = new ArrayList<>();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);
			ComisionVendedores sucursales = null;

			sucursales = new ComisionVendedores();

			LineaDao daoL = new LineaDao();
			List<Linea> lineas = daoL.listar();

			for (Linea linea1 : lineas) {
				sucursales = new ComisionVendedores();

				Criteria consulta = session.createCriteria(PresupuestoE.class);			
				consulta.add(Restrictions.eq("linea", linea1.getId()));
				Criterion resul =Restrictions.in("oficinaVentas", new Integer[]{1000,2000});
				consulta.add(resul);
				consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("ingresos"));
				BigDecimal valor = (BigDecimal) consulta.uniqueResult();
				sucursales.setPresupuestoB(valor);

				consulta.setProjection(Projections.sum("utilidad"));
				valor = (BigDecimal) consulta.uniqueResult();
				sucursales.setUtilpresupuesto(valor);

				consulta = session.createCriteria(Detalle.class);
				resul =Restrictions.in("sucursal", new Integer[]{1000,2000});
				consulta.add(resul);
				consulta.add(Restrictions.eq("linea", linea1.getId()));
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("valorNeto"));
				Long  totalWages = (Long) consulta.uniqueResult();
				totalWages = (totalWages == null)? 0: totalWages;
				sucursales.setIngresoRealB(new BigDecimal(totalWages* -1));

				consulta.setProjection(Projections.sum("costoTotal"));
				totalWages = (Long) consulta.uniqueResult();
				totalWages = (totalWages == null)? 0: totalWages;
				sucursales.setUtilidadReal(new BigDecimal(totalWages));

				if(sucursales.getIngresoRealB().intValue() == 0 || sucursales.getUtilidadReal().intValue() == 0 ){
					sucursales.setCumplimiento(new BigDecimal("0"));
				}
				else{
					sucursales.setCumplimiento(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				}

				String semaforo = (sucursales.getCumplimiento().intValue() >= 85)? "verde.png" : "rojo.png";
				sucursales.setImagen1(semaforo);

				if(sucursales.getUtilidadReal().intValue() == 0){
					sucursales.setCumplimientoU(new BigDecimal("0"));
				}
				else{
					sucursales.setCumplimientoU(sucursales.getUtilidadReal().divide(sucursales.getUtilpresupuesto(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				}
				semaforo = (sucursales.getCumplimientoU().intValue() >= 85)? "verde.png" : "rojo.png";
				sucursales.setImagen(semaforo);
				sucursales.setNombre(linea1.getNombre());
				listaoficinas.add(sucursales);
			}
			return listaoficinas;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}

	}

	//*Lista las lineas de las Zonas Cargo*//

	public List<ComisionVendedores> listarLineasZonasCargo(int idFuncionario, String fecMes, String fecYear ){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<ComisionVendedores> listaoficinas = new ArrayList<>();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);
			ComisionVendedores sucursales = null;

			sucursales = new ComisionVendedores();

			LineaDao daoL = new LineaDao();
			List<Linea> lineas = daoL.listar();

			for (Linea linea1 : lineas) {
				sucursales = new ComisionVendedores();

				Criteria consulta = session.createCriteria(PresupuestoE.class);			
				consulta.add(Restrictions.eq("linea", linea1.getId()));
				consulta.add(Restrictions.eq("funcionario", idFuncionario ));
				consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("ingresos"));
				BigDecimal valor = (BigDecimal) consulta.uniqueResult();
				valor = (valor== null)? new BigDecimal("0"): valor;
				sucursales.setPresupuestoB(valor);

				consulta.setProjection(Projections.sum("utilidad"));
				valor = (BigDecimal) consulta.uniqueResult();
				valor = (valor== null)? new BigDecimal("0"): valor;
				sucursales.setUtilpresupuesto(valor);

				consulta = session.createCriteria(Detalle.class);
				consulta.add(Restrictions.eq("funcionario", idFuncionario ));
				consulta.add(Restrictions.eq("linea", linea1.getId()));
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("valorNeto"));
				Long  totalWages = (Long) consulta.uniqueResult();
				totalWages = (totalWages == null)? 0: totalWages;
				sucursales.setIngresoRealB(new BigDecimal(totalWages* -1));

				consulta.setProjection(Projections.sum("costoTotal"));
				totalWages = (Long) consulta.uniqueResult();
				totalWages = (totalWages == null)? 0: totalWages;
				sucursales.setUtilidadReal(new BigDecimal(totalWages));

				if(sucursales.getIngresoRealB().intValue() == 0 || sucursales.getPresupuestoB().intValue() == 0
						|| sucursales.getPresupuestoB() == null || sucursales.getIngresoRealB()== null){
					sucursales.setCumplimiento(new BigDecimal("0"));
				}
				else{
					sucursales.setCumplimiento(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP));
				}

				String semaforo = (sucursales.getCumplimiento().intValue() >= 85)? "verde.png" : "rojo.png";
				sucursales.setImagen1(semaforo);

				if(sucursales.getUtilidadReal().intValue() == 0 || sucursales.getUtilpresupuesto().intValue() == 0){
					sucursales.setCumplimientoU(new BigDecimal("0"));
				}
				else{
					sucursales.setCumplimientoU(sucursales.getUtilidadReal().divide(sucursales.getUtilpresupuesto(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				}
				semaforo = (sucursales.getCumplimientoU().intValue() >= 85)? "verde.png" : "rojo.png";
				sucursales.setImagen(semaforo);
				sucursales.setNombre(linea1.getNombre());
				listaoficinas.add(sucursales);
			}
			return listaoficinas;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}

	}

	//*Lista las lineas Por ciudad "Director Generico"*//

	public List<ComisionVendedores> listarLineasPorCiudad(int idCiudad, String fecMes, String fecYear ){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<ComisionVendedores> listaoficinas = new ArrayList<>();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);
			ComisionVendedores sucursales = null;

			sucursales = new ComisionVendedores();
			int oficina = (idCiudad == 1 )? 1000 : (idCiudad == 7 )? 2000 : (idCiudad+1)*1000 ;

			LineaDao daoL = new LineaDao();
			List<Linea> lineas = daoL.listar();

			for (Linea linea1 : lineas) {
				sucursales = new ComisionVendedores();

				Criteria consulta = session.createCriteria(PresupuestoE.class);			
				consulta.add(Restrictions.eq("linea", linea1.getId()));
				consulta.add(Restrictions.eq("oficinaVentas", oficina ));
				consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("ingresos"));
				BigDecimal valor = (BigDecimal) consulta.uniqueResult();
				sucursales.setPresupuestoB(valor);

				consulta.setProjection(Projections.sum("utilidad"));
				valor = (BigDecimal) consulta.uniqueResult();
				sucursales.setUtilpresupuesto(valor);

				consulta = session.createCriteria(Detalle.class);
				consulta.add(Restrictions.eq("sucursal", oficina ));
				consulta.add(Restrictions.eq("linea", linea1.getId()));
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("valorNeto"));
				Long  totalWages = (Long) consulta.uniqueResult();
				totalWages = (totalWages == null)? 0: totalWages;
				sucursales.setIngresoRealB(new BigDecimal(totalWages* -1));

				consulta.setProjection(Projections.sum("costoTotal"));
				totalWages = (Long) consulta.uniqueResult();
				totalWages = (totalWages == null)? 0: totalWages;
				sucursales.setUtilidadReal(new BigDecimal(totalWages));

				if(sucursales.getIngresoRealB().intValue() == 0 || sucursales.getUtilidadReal().intValue() == 0 ){
					sucursales.setCumplimiento(new BigDecimal("0"));
				}
				else{
					sucursales.setCumplimiento(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				}

				String semaforo = (sucursales.getCumplimiento().intValue() >= 85)? "verde.png" : "rojo.png";
				sucursales.setImagen1(semaforo);

				if(sucursales.getUtilidadReal().intValue() == 0){
					sucursales.setCumplimientoU(new BigDecimal("0"));
				}
				else{
					sucursales.setCumplimientoU(sucursales.getUtilidadReal().divide(sucursales.getUtilpresupuesto(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				}
				semaforo = (sucursales.getCumplimientoU().intValue() >= 85)? "verde.png" : "rojo.png";
				sucursales.setImagen(semaforo);
				sucursales.setNombre(linea1.getNombre());
				listaoficinas.add(sucursales);
			}
			return listaoficinas;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}

	}


	//*Lista presupuesto y el real del "Director de linea "*//

	public List<ComisionVendedores> listarPresupuestoDirectorLinea(int linea, String fecMes, String fecYear ){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<ComisionVendedores> listaoficinas = new ArrayList<>();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);
			ComisionVendedores sucursales = null;

			sucursales = new ComisionVendedores();

			Criteria consulta = session.createCriteria(PresupuestoE.class);			
			consulta.add(Restrictions.eq("linea", linea));
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal valor = (BigDecimal) consulta.uniqueResult();
			sucursales.setPresupuestoB(valor);

			consulta.setProjection(Projections.sum("utilidad"));
			valor = (BigDecimal) consulta.uniqueResult();
			sucursales.setUtilpresupuesto(valor);

			consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq("linea", linea));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long  totalWages = (Long) consulta.uniqueResult();
			totalWages = (totalWages == null)? 0: totalWages;
			sucursales.setIngresoRealB(new BigDecimal(totalWages* -1));

			consulta.setProjection(Projections.sum("costoTotal"));
			totalWages = (Long) consulta.uniqueResult();
			totalWages = (totalWages == null)? 0: totalWages;
			sucursales.setUtilidadReal(new BigDecimal(totalWages));

			if(sucursales.getIngresoRealB().intValue() == 0 || sucursales.getUtilidadReal().intValue() == 0 ){
				sucursales.setCumplimiento(new BigDecimal("0"));
			}
			else{
				sucursales.setCumplimiento(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP));
			}

			String semaforo = (sucursales.getCumplimiento().intValue() >= 85)? "verde.png" : "rojo.png";
			sucursales.setImagen1(semaforo);

			if(sucursales.getUtilidadReal().intValue() == 0){
				sucursales.setCumplimientoU(new BigDecimal("0"));
			}
			else{
				sucursales.setCumplimientoU(sucursales.getUtilidadReal().divide(sucursales.getUtilpresupuesto(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
			}
			semaforo = (sucursales.getCumplimientoU().intValue() >= 85)? "verde.png" : "rojo.png";
			sucursales.setImagen(semaforo);
			LineaDao daoL = new LineaDao();
			Linea l = daoL.buscar(linea);
			sucursales.setNombre(l.getNombre());
			listaoficinas.add(sucursales);

			return listaoficinas;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}

	}

	//*Lista las lineas Por ciudad "Director Generico"   "vista dl/detalleMixLinea"*//

	public List<ComisionVendedores> listarOficinasDirectorLinea(int linea, String fecMes, String fecYear ){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<ComisionVendedores> listaoficinas = new ArrayList<>();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);
			ComisionVendedores sucursales = null;

			sucursales = new ComisionVendedores();

			CiudadDao daoC = new CiudadDao();
			List<Ciudad> ciudades = daoC.listar();

			for (Ciudad ciudad : ciudades) {


				int oficina = (ciudad.getId() == 1 )? 1000 : (ciudad.getId() == 7 )? 2000 : (ciudad.getId()+1)*1000 ;

				sucursales = new ComisionVendedores();

				Criteria consulta = session.createCriteria(PresupuestoE.class);			
				consulta.add(Restrictions.eq("linea", linea));
				consulta.add(Restrictions.eq("oficinaVentas", oficina ));
				consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("ingresos"));
				BigDecimal valor = (BigDecimal) consulta.uniqueResult();
				valor = (valor == null)? new BigDecimal("0"): valor;
				sucursales.setPresupuestoB(valor);

				consulta.setProjection(Projections.sum("utilidad"));
				valor = (BigDecimal) consulta.uniqueResult();
				valor = (valor == null)? new BigDecimal("0"): valor;
				sucursales.setUtilpresupuesto(valor);

				consulta = session.createCriteria(Detalle.class);
				consulta.add(Restrictions.eq("sucursal", oficina ));
				consulta.add(Restrictions.eq("linea", linea));
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("valorNeto"));
				Long  totalWages = (Long) consulta.uniqueResult();
				totalWages = (totalWages == null)? 0: totalWages;
				sucursales.setIngresoRealB(new BigDecimal(totalWages* -1));

				int valN = (totalWages == null)? 0 : (int) (long) totalWages;

				consulta.setProjection(Projections.sum("costoTotal"));
				totalWages = (Long) consulta.uniqueResult();
				totalWages = (totalWages == null)? 0: totalWages;

				int cosT = (totalWages == null)? 0 : (int) (long) totalWages ;
				int valorU = (cosT == 0) ? 0 : (valN < 0) ? (valN* -1) - (cosT) : (valN) - (cosT) ; 

				sucursales.setUtilidadReal(new BigDecimal(valorU));

				if(sucursales.getIngresoRealB().intValue() == 0 || sucursales.getPresupuestoB().intValue() == 0 ){
					sucursales.setCumplimiento(new BigDecimal("0"));
				}
				else{
					sucursales.setCumplimiento(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP));
				}

				String semaforo = (sucursales.getCumplimiento().intValue() >= 85)? "verde.png" : "rojo.png";
				sucursales.setImagen1(semaforo);

				if(sucursales.getUtilidadReal().intValue() == 0 || sucursales.getUtilpresupuesto().intValue() == 0){
					sucursales.setCumplimientoU(new BigDecimal("0"));
				}
				else{
					sucursales.setCumplimientoU(sucursales.getUtilidadReal().divide(sucursales.getUtilpresupuesto(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				}
				semaforo = (sucursales.getCumplimientoU().intValue() >= 85)? "verde.png" : "rojo.png";
				sucursales.setImagen(semaforo);
				LineaDao daoL = new LineaDao();
				Linea l = daoL.buscar(linea);
				sucursales.setNombre(l.getNombre());
				sucursales.setConcepto(ciudad.getNombre());
				EsquemasDao daoE = new EsquemasDao();
				List<Esquemas> esquema = daoE.listar();
				sucursales.setUmbralCV(esquema.get(0).getUmbralComision());
				listaoficinas.add(sucursales);
			}
			return listaoficinas;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}

	}

	//*Lista presupuesto y el real del "Director de linea An"*//

	public List<ComisionVendedores> listarPresupuestoDirectorLineaA(String fecMes, String fecYear ){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<ComisionVendedores> listaoficinas = new ArrayList<>();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);
			ComisionVendedores sucursales = null;

			List<Linea> lineas = new ArrayList<>();
			Linea linea1 = new Linea();
			linea1.setId(6);
			linea1.setNombre("Acero Antidesgaste");
			lineas.add(0, linea1);
			linea1 = new Linea();
			linea1.setId(10);
			linea1.setNombre("Acero Oxi-Antidesgaste");
			lineas.add(1, linea1);
			sucursales = new ComisionVendedores();
			sucursales.setPresupuestoB(new BigDecimal("0"));
			sucursales.setUtilpresupuesto(new BigDecimal("0"));
			sucursales.setIngresoRealB(new BigDecimal("0"));
			sucursales.setUtilidadReal(new BigDecimal("0"));
			for (Linea linea : lineas) {

				Criteria consulta = session.createCriteria(PresupuestoE.class);			
				consulta.add(Restrictions.eq("linea", linea.getId()));
				consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("ingresos"));
				BigDecimal valor = (BigDecimal) consulta.uniqueResult();
				valor = (valor == null)? new BigDecimal("0"): valor;
				sucursales.setPresupuestoB(sucursales.getPresupuestoB().add(valor));

				consulta.setProjection(Projections.sum("utilidad"));
				valor = (BigDecimal) consulta.uniqueResult();
				valor = (valor == null)? new BigDecimal("0"): valor;
				sucursales.setUtilpresupuesto(sucursales.getUtilpresupuesto().add(valor));

				consulta = session.createCriteria(Detalle.class);
				consulta.add(Restrictions.eq("linea", linea.getId()));
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("valorNeto"));
				Long  totalWages = (Long) consulta.uniqueResult();
				totalWages = (totalWages == null)? 0: totalWages;
				sucursales.setIngresoRealB(sucursales.getIngresoRealB().add(new BigDecimal(totalWages)));

				consulta.setProjection(Projections.sum("costoTotal"));
				totalWages = (Long) consulta.uniqueResult();
				totalWages = (totalWages == null)? 0: totalWages;
				sucursales.setUtilidadReal(sucursales.getUtilidadReal().add( new BigDecimal(totalWages)));
			}
			if(sucursales.getIngresoRealB().intValue() == 0 || sucursales.getUtilidadReal().intValue() == 0 ){
				sucursales.setCumplimiento(new BigDecimal("0"));
			}
			else{
				sucursales.setIngresoRealB(sucursales.getIngresoRealB().multiply(new BigDecimal("-1")));
				sucursales.setCumplimiento(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
			}

			String semaforo = (sucursales.getCumplimiento().intValue() >= 85)? "verde.png" : "rojo.png";
			sucursales.setImagen1(semaforo);

			if(sucursales.getUtilidadReal().intValue() == 0){
				sucursales.setCumplimientoU(new BigDecimal("0"));
			}
			else{
				sucursales.setCumplimientoU(sucursales.getUtilidadReal().divide(sucursales.getUtilpresupuesto(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
			}
			semaforo = (sucursales.getCumplimientoU().intValue() >= 85)? "verde.png" : "rojo.png";
			sucursales.setImagen(semaforo);
			EsquemasDao daoE = new EsquemasDao();
			List<Esquemas> esquema = daoE.listar();
			sucursales.setUmbralCV(esquema.get(0).getUmbralComision());
			listaoficinas.add(sucursales);

			return listaoficinas;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}

	}

	//*Lista las lineas Por ciudad "Director Linea Antidesgate"*//

	public List<ComisionVendedores> listarOficinasDirectorLineaAn(String fecMes, String fecYear ){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<ComisionVendedores> listaoficinas = new ArrayList<>();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);
			ComisionVendedores sucursales = null;

			List<Linea> lineas = new ArrayList<>();
			Linea linea1 = new Linea();
			linea1.setId(6);
			linea1.setNombre("Acero Antidesgaste");
			lineas.add(0, linea1);
			linea1 = new Linea();
			linea1.setId(10);
			linea1.setNombre("Acero Oxi-Antidesgaste");
			lineas.add(1, linea1);

			CiudadDao daoC = new CiudadDao();
			List<Ciudad> ciudades = daoC.listar();

			for (Ciudad ciudad : ciudades) {


				int oficina = (ciudad.getId() == 1 )? 1000 : (ciudad.getId() == 7 )? 2000 : (ciudad.getId()+1)*1000 ;
				for (Linea linea : lineas) {


					sucursales = new ComisionVendedores();

					Criteria consulta = session.createCriteria(PresupuestoE.class);			
					consulta.add(Restrictions.eq("linea", linea.getId()));
					consulta.add(Restrictions.eq("oficinaVentas", oficina ));
					consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
					consulta.setProjection(Projections.sum("ingresos"));
					BigDecimal valor = (BigDecimal) consulta.uniqueResult();
					valor = (valor == null)? new BigDecimal("0"): valor;
					sucursales.setPresupuestoB(valor);

					consulta.setProjection(Projections.sum("utilidad"));
					valor = (BigDecimal) consulta.uniqueResult();
					valor = (valor == null)? new BigDecimal("0"): valor;
					sucursales.setUtilpresupuesto(valor);

					consulta = session.createCriteria(Detalle.class);
					consulta.add(Restrictions.eq("sucursal", oficina ));
					consulta.add(Restrictions.eq("linea", linea.getId()));
					consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
					consulta.setProjection(Projections.sum("valorNeto"));
					Long  totalWages = (Long) consulta.uniqueResult();
					totalWages = (totalWages == null)? 0: totalWages;
					sucursales.setIngresoRealB(new BigDecimal(totalWages* -1));

					consulta.setProjection(Projections.sum("costoTotal"));
					totalWages = (Long) consulta.uniqueResult();
					totalWages = (totalWages == null)? 0: totalWages;
					sucursales.setUtilidadReal(new BigDecimal(totalWages));

					if(sucursales.getIngresoRealB().intValue() == 0 || sucursales.getPresupuestoB().intValue() == 0 ){
						sucursales.setCumplimiento(new BigDecimal("0"));
					}
					else{
						sucursales.setCumplimiento(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
						sucursales.setCumplimiento(sucursales.getCumplimiento().setScale(2, BigDecimal.ROUND_HALF_UP));
					}

					String semaforo = (sucursales.getCumplimiento().intValue() >= 85)? "verde.png" : "rojo.png";
					sucursales.setImagen1(semaforo);

					if(sucursales.getUtilidadReal().intValue() == 0  || sucursales.getUtilpresupuesto().intValue() == 0 ){
						sucursales.setCumplimientoU(new BigDecimal("0"));
					}
					else{
						sucursales.setCumplimientoU(sucursales.getUtilidadReal().divide(sucursales.getUtilpresupuesto(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
					}
					semaforo = (sucursales.getCumplimientoU().intValue() >= 85)? "verde.png" : "rojo.png";
					sucursales.setImagen(semaforo);
					sucursales.setNombre(linea.getNombre());
					sucursales.setConcepto(ciudad.getNombre());
					EsquemasDao daoE = new EsquemasDao();
					List<Esquemas> esquema = daoE.listar();
					sucursales.setUmbralCV(esquema.get(0).getUmbralComision());
					listaoficinas.add(sucursales);

				}
			}
			return listaoficinas;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}

	}


	//*Lista el presupuesto y el real de la lineas por vendedor  *//
	public List<ComisionVendedores>  listarVendedoresPorLinea (String tipo, int idfuncionario, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<ComisionVendedores> listaLineas = new ArrayList<>();
		ComisionVendedores vendedor = new ComisionVendedores();
		int valor = 0;

		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);


			LineaDao daoL = new LineaDao();
			List<Linea> lineas = daoL.listar();
			for (Linea linea1 : lineas) {

				vendedor = new ComisionVendedores();
				List <Long> lista = sumaLineaPorVendedor( tipo,  linea1.getId(), idfuncionario,  fechaInicial, fechaFinal);

				int valN = (lista.get(0) == null)? 0 : (int) (long) lista.get(0);
				int cosT = (lista.get(1) == null)? 0 : (int) (long) lista.get(1) ;
				valor = (cosT == 0) ? 0 : (valN < 0) ? (valN* -1) - (cosT) : (valN) - (cosT) ; 

				vendedor.setIngresoRealB(new BigDecimal(valN* -1));
				vendedor.setUtilidadReal(new BigDecimal(valor ));

				PresupuestoDao dao = new PresupuestoDao();
				List<BigDecimal> pre = new ArrayList<>();

				if (tipo.equals("funcionarioI")){
					pre = dao.sumaLineaPorFuncionario(linea1.getId(), idfuncionario, fechaInicial, fechaFinal);
				}	
				else{
					pre = dao.sumaLineaPorFuncionarioE(linea1.getId(), idfuncionario, fechaInicial, fechaFinal);
				}	
				if(pre != null){
					vendedor.setPresupuestoB((pre.get(0) == null)? new BigDecimal("0") : pre.get(0));
					vendedor.setUtilpresupuesto((pre.get(1) == null)?new BigDecimal("0") : pre.get(1));

					if(vendedor.getPresupuestoB() == null || vendedor.getPresupuestoB().intValue() == 0
							||	vendedor.getIngresoRealB() == null  || vendedor.getIngresoRealB().intValue() == 0){
						vendedor.setCumplimiento(new BigDecimal("0"));
					}
					else{
						vendedor.setCumplimiento(vendedor.getIngresoRealB().divide(vendedor.getPresupuestoB(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP));
					}

					String semaforo = (vendedor.getCumplimiento().intValue() >= 85)? "verde.png" : "rojo.png";
					vendedor.setImagen1(semaforo);


					if(vendedor.getUtilidadReal() == null || vendedor.getUtilidadReal().intValue() == 0
							||	vendedor.getUtilpresupuesto() == null  || vendedor.getUtilpresupuesto().intValue() == 0){
						vendedor.setUtilpresupuesto((vendedor.getUtilpresupuesto() == null)? new BigDecimal("0"):vendedor.getUtilpresupuesto());
						vendedor.setCumplimientoU(new BigDecimal("0"));
					}
					else{
						vendedor.setCumplimientoU(vendedor.getUtilidadReal().divide(vendedor.getUtilpresupuesto(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
					}	

					semaforo = (vendedor.getCumplimientoU().intValue() >= 85)? "verde.png" : "rojo.png";
					vendedor.setImagen(semaforo);

					vendedor.setNombre(linea1.getNombre());
					vendedor.setId(linea1.getId());
					EsquemasDao daoE = new EsquemasDao();
					Esquemas esquema = daoE.buscar(1);
					vendedor.setUmbralCV(esquema.getUmbralComision());

					listaLineas.add(vendedor);
				}

			}	
			return listaLineas;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	//*Lista presupuesto y real de las Lineas *//
	public List<ComisionVendedores> realLineasMes(String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();

		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);

			ComisionVendedores sucursales = null;
			List<ComisionVendedores> listaoficinas = new ArrayList<>();
			LineaDao daoL = new LineaDao();
			List<Linea> listaLineas = daoL.listar();

			for (Linea linea : listaLineas) {

				sucursales = new ComisionVendedores();
				sucursales.setId(linea.getId());
				sucursales.setNombre(linea.getNombre());

				Criteria consulta = session.createCriteria(PresupuestoE.class);
				consulta.add(Restrictions.eq("linea", linea.getId()));
				consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("ingresos"));
				BigDecimal valor = (BigDecimal) consulta.uniqueResult();
				valor = (valor == null)? new BigDecimal("0") : valor;
				sucursales.setPresupuestoB(valor);
				
				consulta.setProjection(Projections.sum("utilidad"));
				valor = (BigDecimal) consulta.uniqueResult();
				valor = (valor == null)? new BigDecimal("0") : valor;
				sucursales.setUtilpresupuesto(valor);
				

				consulta = session.createCriteria(Detalle.class);
				consulta.add(Restrictions.eq("linea", linea.getId()));
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("valorNeto"));
				Long valN = (Long) consulta.uniqueResult();
				valN = (valN == null)? 0 : valN;
				valor = (valN == null)? new BigDecimal("0") : new BigDecimal(valN);
				sucursales.setIngresoRealB(valor);
				
				consulta.setProjection(Projections.sum("costoTotal"));
				Long cosT = (Long) consulta.uniqueResult();
				cosT = (cosT == null)? 0 : cosT;
				cosT = (valN > 0)? valN + cosT : valN + cosT;
				//cosT = (valN < 0) ? (valN* -1) - (cosT) :(cosT < 0)? (valN) - (cosT* -1) : (valN) - (cosT) ;
				sucursales.setUtilidadReal(new BigDecimal(cosT));

				if(sucursales.getIngresoRealB()== null ||  sucursales.getPresupuestoB() == null
						|| sucursales.getIngresoRealB().longValue() == 0 ||  sucursales.getPresupuestoB().longValue() == 0){

					sucursales.setCumplimiento(new BigDecimal("0"));
				}
				else{
					sucursales.setCumplimiento(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
					sucursales.setCumplimiento(sucursales.getCumplimiento().setScale(2, BigDecimal.ROUND_HALF_UP));
				}
				
				if(sucursales.getUtilidadReal()== null ||  sucursales.getUtilpresupuesto() == null
						|| sucursales.getUtilidadReal().longValue() == 0 ||  sucursales.getUtilpresupuesto().longValue() == 0){
					sucursales.setCumplimientoU(new BigDecimal("0"));
				}
				else{
					sucursales.setCumplimientoU(sucursales.getUtilidadReal().divide(sucursales.getUtilpresupuesto(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
					sucursales.setCumplimientoU(sucursales.getCumplimientoU().setScale(2, BigDecimal.ROUND_HALF_UP));
				}
				
				EsquemasDao daoE = new EsquemasDao();
				Esquemas esquema = daoE.buscar(1);
				sucursales.setUmbralCV(esquema.getUmbralComision());

				String semaforo = (sucursales.getCumplimiento().intValue() >= 85 )?"verde.png" : "rojo.png";
				sucursales.setImagen1(semaforo);
				
				semaforo = (sucursales.getCumplimientoU().intValue() >= 85 )?"verde.png" : "rojo.png";
				sucursales.setImagen(semaforo);

				listaoficinas.add(sucursales);
				sucursales = new ComisionVendedores();
			}

			return listaoficinas;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}

	}
}


