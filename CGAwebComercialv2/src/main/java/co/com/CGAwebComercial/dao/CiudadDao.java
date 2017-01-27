package co.com.CGAwebComercial.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.Ciudad;
import co.com.CGAwebComercial.entyties.Detalle;
import co.com.CGAwebComercial.entyties.Esquemas;
import co.com.CGAwebComercial.entyties.OficinaVendedorInterno;
import co.com.CGAwebComercial.entyties.Presupuesto;
import co.com.CGAwebComercial.entyties.PresupuestoE;
import co.com.CGAwebComercial.entyties.codUEN;
import co.com.CGAwebComercial.entyties.HelpDesk.Vista_helpdesk;
import co.com.CGAwebComercial.util.ComisionVendedores;
import co.com.CGAwebComercial.util.HibernateUtil;

public class CiudadDao extends GenericDao<Ciudad>{

	//*lista el presupuesto y el real de las oficinas "gg" "gg/oficina"*//
	public List<ComisionVendedores> listarOficinas(String fecMes, String fecYear ){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<ComisionVendedores> listaoficinas = new ArrayList<>();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);
			ComisionVendedores sucursales = null;
			CiudadDao daoC = new CiudadDao();
			List<Ciudad> listaSucursales = daoC.listar();

			for (Ciudad ciudad : listaSucursales) {
				sucursales = new ComisionVendedores();
				sucursales.setId(ciudad.getId());
				sucursales.setNombre(ciudad.getNombre());

				int oficina = (ciudad.getId() == 1 )? ciudad.getId()*1000 : (ciudad.getId() == 7 )? 2000 : (ciudad.getId()+1)*1000 ;

				Criteria consulta = session.createCriteria(PresupuestoE.class);			
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
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("valorNeto"));
				Long  totalWages = (Long) consulta.uniqueResult();
				totalWages = (totalWages == null)? 0: totalWages;
				sucursales.setIngresoRealB(new BigDecimal(totalWages* -1));
	
				consulta.setProjection(Projections.sum("costoTotal"));
				totalWages = (Long) consulta.uniqueResult();
				totalWages = (totalWages == null)? 0: totalWages;
				sucursales.setUtilidadReal(sucursales.getIngresoRealB().subtract(new BigDecimal(totalWages)));

				if(sucursales.getIngresoRealB().intValue() == 0 ){
					sucursales.setCumplimiento(new BigDecimal("0"));
				}
				else{
					sucursales.setCumplimiento(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
					sucursales.setCumplimiento(sucursales.getCumplimiento().setScale(2, BigDecimal.ROUND_HALF_UP));
				}

				String semaforo = (sucursales.getCumplimiento().intValue() >= 85)? "verde.png" : "rojo.png";
				sucursales.setImagen1(semaforo);

				if(sucursales.getUtilidadReal().intValue() == 0){
					sucursales.setCumplimientoU(new BigDecimal("0"));
				}
				else{
					sucursales.setCumplimientoU(sucursales.getUtilidadReal().divide(sucursales.getUtilpresupuesto(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
					sucursales.setCumplimientoU(sucursales.getCumplimientoU().setScale(2, BigDecimal.ROUND_HALF_UP ));
				}
				semaforo = (sucursales.getCumplimientoU().intValue() >= 85)? "verde.png" : "rojo.png";
				sucursales.setImagen(semaforo);
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

	//*Se lista el presupuesto y el real por oficina segun la linea requerida  5*//	
	public List<ComisionVendedores> listarOficinasPorLinea(int linea, String fecMes, String fecYear ){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<ComisionVendedores> listaoficinas = new ArrayList<>();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);
			ComisionVendedores sucursales = null;
			CiudadDao daoC = new CiudadDao();
			List<Ciudad> listaSucursales = daoC.listar();

			for (Ciudad ciudad : listaSucursales) {
				sucursales = new ComisionVendedores();
				sucursales.setId(ciudad.getId());
				sucursales.setNombre(ciudad.getNombre());

				int oficina = (ciudad.getId() == 1 )? ciudad.getId()*1000 : (ciudad.getId() == 7 )? 2000 : (ciudad.getId()+1)*1000 ;

				Criteria consulta = session.createCriteria(PresupuestoE.class);			
				consulta.add(Restrictions.eq("oficinaVentas", oficina ));
				consulta.add(Restrictions.eq("linea", linea ));
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
				consulta.add(Restrictions.eq("linea", linea ));
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("valorNeto"));
				Long  totalWages = (Long) consulta.uniqueResult();
				totalWages = (totalWages == null)? 0: totalWages;
				sucursales.setIngresoRealB(new BigDecimal(totalWages* -1));
				
				consulta.setProjection(Projections.sum("costoTotal"));
				totalWages = (Long) consulta.uniqueResult();
				totalWages = (totalWages == null)? 0: totalWages;
				sucursales.setUtilidadReal(sucursales.getIngresoRealB().subtract(new BigDecimal(totalWages)));
				
				if(sucursales.getIngresoRealB().intValue() == 0 || sucursales.getIngresoRealB() == null
						|| sucursales.getPresupuestoB().intValue()== 0 || sucursales.getPresupuestoB() == null){
					sucursales.setCumplimiento(new BigDecimal("0"));
				}
				else{
					sucursales.setCumplimiento(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
					sucursales.setCumplimiento(sucursales.getCumplimiento().setScale(2, BigDecimal.ROUND_HALF_UP));
				}
				String semaforo = (sucursales.getCumplimiento().intValue() >= 85)? "verde.png" : "rojo.png";
				sucursales.setImagen1(semaforo);

				if(sucursales.getUtilidadReal().intValue() == 0 || sucursales.getUtilidadReal() == null
						|| sucursales.getUtilpresupuesto().intValue() == 0 || sucursales.getUtilpresupuesto() == null ){
					sucursales.setCumplimientoU(new BigDecimal("0"));
				}
				else{
					sucursales.setCumplimientoU(sucursales.getUtilidadReal().divide(sucursales.getUtilpresupuesto(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
					sucursales.setCumplimientoU(sucursales.getCumplimientoU().setScale(2, BigDecimal.ROUND_HALF_UP ));
				}
				semaforo = (sucursales.getCumplimientoU().intValue() >= 85)? "verde.png" : "rojo.png";
				sucursales.setImagen(semaforo);
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

	//*Lista las oficinas por la UEN*//
	public List<ComisionVendedores> listarOficinasUEN(String uen,String fecMes, String fecYear ){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<ComisionVendedores> listaoficinas = new ArrayList<>();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);
			ComisionVendedores sucursales = null;
			CiudadDao daoC = new CiudadDao();
			List<Ciudad> listaSucursales = daoC.listar();

			for (Ciudad ciudad : listaSucursales) {
				sucursales = new ComisionVendedores();
				sucursales.setId(ciudad.getId());
				sucursales.setNombre(ciudad.getNombre());

				int oficina = (ciudad.getId() == 1 )? ciudad.getId()*1000 : (ciudad.getId() == 7 )? 2000 : (ciudad.getId()+1)*1000 ;

				Criteria consulta = session.createCriteria(PresupuestoE.class);			
				consulta.add(Restrictions.eq("oficinaVentas", oficina ));
				consulta.add(Restrictions.eq("uen", uen ));
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
					sucursales.setCumplimiento(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
					sucursales.setCumplimiento(sucursales.getCumplimiento().setScale(2, BigDecimal.ROUND_HALF_UP));
				}

				String semaforo = (sucursales.getCumplimiento().intValue() >= 85)? "verde.png" : "rojo.png";
				sucursales.setImagen1(semaforo);

				if(sucursales.getUtilidadReal().intValue() == 0 || sucursales.getPresupuestoB().intValue() == 0){
					sucursales.setCumplimientoU(new BigDecimal("0"));
				}
				else{
					sucursales.setCumplimientoU(sucursales.getUtilidadReal().divide(sucursales.getUtilpresupuesto(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
					sucursales.setCumplimientoU(sucursales.getCumplimientoU().setScale(2, BigDecimal.ROUND_HALF_UP));
				}
				semaforo = (sucursales.getCumplimientoU().intValue() >= 85)? "verde.png" : "rojo.png";
				sucursales.setImagen(semaforo);
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

	//*Lista el preupuesto y real por oficina "directores"*//
	public List<ComisionVendedores> listarOficinasDirectores(int idCiudad, String fecMes, String fecYear ){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<ComisionVendedores> listaoficinas = new ArrayList<>();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);
			ComisionVendedores sucursales = null;

			sucursales = new ComisionVendedores();

			int oficina = (idCiudad == 1 )? 1000 : (idCiudad == 7 )? 2000 : (idCiudad+1)*1000 ;

			Criteria consulta = session.createCriteria(PresupuestoE.class);	
			if(oficina == 4000){
				Criterion resul =Restrictions.in("oficinaVentas", new Integer[]{4000,7000});
				consulta.add(resul);
			}
			else{
				consulta.add(Restrictions.eq("oficinaVentas", oficina ));
			}			
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal valor = (BigDecimal) consulta.uniqueResult();
			sucursales.setPresupuestoB(valor);
			
			consulta.setProjection(Projections.sum("utilidad"));
			valor = (BigDecimal) consulta.uniqueResult();
			sucursales.setUtilpresupuesto(valor);
			

			consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq("sucursal", oficina ));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long  totalWages = (Long) consulta.uniqueResult();
			totalWages = (totalWages == null)? 0: totalWages;
			sucursales.setIngresoRealB(new BigDecimal(totalWages* -1));
			
			consulta.setProjection(Projections.sum("costoTotal"));
			totalWages = (Long) consulta.uniqueResult();
			totalWages = (totalWages == null)? 0: totalWages;
			sucursales.setUtilidadReal(new BigDecimal(totalWages));

			if(sucursales.getIngresoRealB().intValue() == 0 ){
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
			listaoficinas.add(sucursales);

			return listaoficinas;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}

	}

	//*Lista el preupuesto y real por Zonas a crgo "directores"*//
	public List<ComisionVendedores> listarZonasCargo(int idFucionario, String fecMes, String fecYear ){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<ComisionVendedores> listaoficinas = new ArrayList<>();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);
			ComisionVendedores sucursales = null;

			sucursales = new ComisionVendedores();

			Criteria consulta = session.createCriteria(PresupuestoE.class);			
			consulta.add(Restrictions.eq("funcionario", idFucionario ));
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal valor = (BigDecimal) consulta.uniqueResult();
			sucursales.setPresupuestoB(valor);

			consulta.setProjection(Projections.sum("utilidad"));
			valor = (BigDecimal) consulta.uniqueResult();
			sucursales.setUtilpresupuesto(valor);

			consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq("funcionario", idFucionario ));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long  totalWages = (Long) consulta.uniqueResult();
			totalWages = (totalWages == null)? 0: totalWages;
			sucursales.setIngresoRealB(new BigDecimal(totalWages* -1));

			consulta.setProjection(Projections.sum("costoTotal"));
			totalWages = (Long) consulta.uniqueResult();
			totalWages = (totalWages == null)? 0: totalWages;
			sucursales.setUtilidadReal(new BigDecimal(totalWages));

			if(sucursales.getIngresoRealB().intValue() == 0 ){
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
			listaoficinas.add(sucursales);

			return listaoficinas;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}

	}

	//*Lista el preupuesto y real por oficina "jefe Internos"*//
	public List<ComisionVendedores> listarPresupuestoJefeInternos(int idCiudad, String fecMes, String fecYear ){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<ComisionVendedores> listaoficinas = new ArrayList<>();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);
			ComisionVendedores sucursales = null;

			sucursales = new ComisionVendedores();
			sucursales = new ComisionVendedores();
			sucursales.setPresupuestoB(new BigDecimal("0"));
			sucursales.setUtilpresupuesto(new BigDecimal("0"));
			sucursales.setIngresoRealB(new BigDecimal("0"));
			sucursales.setUtilidadReal(new BigDecimal("0"));

			OficinaVendedorInternoDao daoOf = new OficinaVendedorInternoDao();
			List<OficinaVendedorInterno> listVI = daoOf.listaVenIntDirector(idCiudad);
			for (OficinaVendedorInterno interno : listVI) {

				Criteria consulta = session.createCriteria(Presupuesto.class);			
				consulta.add(Restrictions.eq("funcionario", interno.getCodigosap()));
				consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("ingresos"));
				BigDecimal valor = (BigDecimal) consulta.uniqueResult();
				valor = (valor == null)? new BigDecimal("0"): valor;
				sucursales.setPresupuestoB(sucursales.getPresupuestoB().add(valor));

				consulta.setProjection(Projections.sum("utilidad"));
				valor = (BigDecimal) consulta.uniqueResult();
				valor = (valor == null)? new BigDecimal("0"): valor;
				sucursales.setUtilpresupuesto(sucursales.getUtilpresupuesto().add(valor));

			}
			Criteria consulta = session.createCriteria(Detalle.class);
			Criterion resul =Restrictions.in("sucursal", new Integer[]{1000,2000});
			consulta.add(resul);
			//consulta.add(Restrictions.eq("sucursal", idCiudad));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long  totalWages = (Long) consulta.uniqueResult();
			totalWages = (totalWages == null)? 0: totalWages;
			sucursales.setIngresoRealB(sucursales.getIngresoRealB().add(new BigDecimal(totalWages)));

			consulta.setProjection(Projections.sum("costoTotal"));
			totalWages = (Long) consulta.uniqueResult();
			totalWages = (totalWages == null)? 0: totalWages;
			sucursales.setUtilidadReal(sucursales.getUtilidadReal().add( new BigDecimal(totalWages)));
			
			if(sucursales.getIngresoRealB().intValue() == 0 || sucursales.getUtilidadReal().intValue() == 0 ){
				sucursales.setCumplimiento(new BigDecimal("0"));
			}
			else{
				sucursales.setIngresoRealB(sucursales.getIngresoRealB().multiply(new BigDecimal("-1")));
				sucursales.setCumplimiento(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				sucursales.setCumplimiento(sucursales.getCumplimiento().setScale(2, BigDecimal.ROUND_HALF_UP ));
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
			listaoficinas.add(sucursales);

			return listaoficinas;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}

	}
	
	//*Lista presupuesto y real de las oficinas *//
	public List<ComisionVendedores> realOficinaMes(String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();

		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);
			
			ComisionVendedores sucursales = null;
			List<ComisionVendedores> listaoficinas = new ArrayList<>();
			CiudadDao daoC = new CiudadDao();
			List<Ciudad> listaSucursales = daoC.listar();
			for (Ciudad ciudad : listaSucursales) {
				
				int oficina = (ciudad.getId() == 1 )? 1000 : (ciudad.getId() == 7 )? 2000 : (ciudad.getId()+1)*1000 ;
				
				sucursales = new ComisionVendedores();
				sucursales.setId(ciudad.getId());
				sucursales.setNombre(ciudad.getNombre());

				Criteria consulta = session.createCriteria(PresupuestoE.class);
				consulta.add(Restrictions.eq("oficinaVentas", oficina));
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
				consulta.add(Restrictions.eq("sucursal", oficina));
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("valorNeto"));
				Long valN = (Long) consulta.uniqueResult();
				valN = (valN == null)? 0 : valN;
				valor = (valN == null)? new BigDecimal("0") : new BigDecimal(valN).multiply(new BigDecimal("-1"));
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
	
	
	
	@SuppressWarnings("unchecked")
	public void prueba(){
		
		Session session = HibernateUtil.getSessionfactory1().openSession();
		List<Vista_helpdesk> prueba = new ArrayList<>();
//		List<Ciudad> prueba = new ArrayList<>();
//		List<Ost_ticket> prueba = new ArrayList<>();
		try{
//			Vista_helpdeskDao dao= new Vista_helpdeskDao();
//			prueba = dao.listar();
//			
			
//			Criteria consulta = session.createCriteria(Ciudad.class);
//			prueba = consulta.list();
			
			Criteria consulta = session.createCriteria(Vista_helpdesk.class);
			prueba = consulta.list();
			
			System.out.println(prueba.size()+ "####");
//			for (Ciudad ciudad : prueba) {
//				System.out.println(ciudad.getNombre() + "id search");
//			}
			for (Vista_helpdesk v : prueba) {				
				System.out.println(v.getArea() + " -- " + v.getFirstname());
			}
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			//session.close();
		}
		
	}
}
