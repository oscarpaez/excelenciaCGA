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

import co.com.CGAwebComercial.entyties.Detalle;
import co.com.CGAwebComercial.entyties.Detallesin;
import co.com.CGAwebComercial.entyties.Presupuesto;
import co.com.CGAwebComercial.entyties.PresupuestoE;
import co.com.CGAwebComercial.entyties.Recaudo;
import co.com.CGAwebComercial.entyties.Zona_venta;
import co.com.CGAwebComercial.entyties.bajaRotacion;
import co.com.CGAwebComercial.util.HibernateUtil;

public class PresupuestoDao extends GenericDao<Presupuesto>{

	@SuppressWarnings("unchecked")
	public List<Presupuesto> datoPorLinea(int codigo, int idPersona, Date fechaInicial, Date fechaFinal){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Presupuesto> presupuesto = null;
		try{
			Criteria consulta = session.createCriteria(Presupuesto.class);			
			consulta.add(Restrictions.eq("linea", codigo));
			consulta.add(Restrictions.eq("funcionario", idPersona));
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			presupuesto = consulta.list();

			return presupuesto;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}

	//*Suma el presupuesto de la linea Por pais *//
	@SuppressWarnings({ "unchecked" })
	public List<PresupuestoE> datoPorLineaEPais(int codigo,Date fechaInicial, Date fechaFinal){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<PresupuestoE> presupuesto = null;
		try{
			
			Criteria consulta = session.createCriteria(PresupuestoE.class);
			consulta.add(Restrictions.eq("linea", codigo));
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			presupuesto = consulta.list();

			return presupuesto;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}
	
	@SuppressWarnings({ "unchecked" })
	public List<PresupuestoE> datoPorLineaE(String tipo, int codigo, int idPersona, Date fechaInicial, Date fechaFinal){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<PresupuestoE> presupuesto = null;
		try{
			Criteria consulta = session.createCriteria(PresupuestoE.class);
			consulta.add(Restrictions.eq("linea", codigo));
			consulta.add(Restrictions.eq("funcionario", idPersona));
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			presupuesto = consulta.list();

			return presupuesto;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}

	//*Lista presupuesto de la linea por pais  "dl"  "vistaModulo" *//
	public List <BigDecimal> datoPorLineaPaisE(int linea ){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List <BigDecimal> lista = new ArrayList<>();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();			
			Criteria consulta = session.createCriteria(PresupuestoE.class);
			if(linea == 6 ){
				Criterion resul =Restrictions.in("linea", new Integer[]{6,10});
				consulta.add(resul);
			}
			else{
				consulta.add(Restrictions.eq("linea", linea));
			}
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal valor = (BigDecimal) consulta.uniqueResult();
			lista.add(valor);

			consulta = session.createCriteria(Detalle.class);
			if(linea == 6 ){
				Criterion resul =Restrictions.in("linea", new Integer[]{6,10});
				consulta.add(resul);
			}
			else{
				consulta.add(Restrictions.eq("linea", linea));
			}
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long valorN = (Long) consulta.uniqueResult();
			valorN = (valorN == null)? 0 : valorN; 
			valor = new BigDecimal(valorN);
			lista.add(valor);
			return lista;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}


	//*Lista presupuesto de la oficina  "dcB"  " /dcB/vistaModulo" *//
	public List <BigDecimal> presupuestoPorOficina(int oficina ){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List <BigDecimal> lista = new ArrayList<>();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();	

			Criteria consulta = session.createCriteria(PresupuestoE.class);
			if(oficina == 4000){
				Criterion resul =Restrictions.in("oficinaVentas", new Integer[]{4000,7000});
				consulta.add(resul);
			}
			else{
				consulta.add(Restrictions.eq("oficinaVentas", oficina));
			}
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal valor = (BigDecimal) consulta.uniqueResult();
			lista.add(valor);
			
			consulta = session.createCriteria(Detalle.class);
			if(oficina == 4000){
				Criterion resul =Restrictions.in("sucursal", new Integer[]{4000,7000});
				consulta.add(resul);
			}
			else{
				consulta.add(Restrictions.eq("sucursal", oficina));
			}			
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long valorN = (Long) consulta.uniqueResult();
			valorN = (valorN == null)? 0 : valorN;
			valor = new BigDecimal(valorN);
			lista.add(valor);
			
			consulta = session.createCriteria(bajaRotacion.class);			
			if(oficina == 4000){
				Criterion resul =Restrictions.in("codoficina", new Integer[]{4000,7000});
				consulta.add(resul);
			}
			else{
				consulta.add(Restrictions.eq("codoficina", oficina));
			}
			Criterion resul =Restrictions.or(Restrictions.eq("almacen", 1020),
					Restrictions.eq("almacen", 2020), Restrictions.eq("almacen", 3020),
					Restrictions.eq("almacen", 4020), Restrictions.eq("almacen", 5020),
					Restrictions.eq("almacen", 6020));
			consulta.add(resul);
			consulta.add(Restrictions.between("fechaFactura", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long totalWages = (Long) consulta.uniqueResult();
			BigDecimal valLBR = (totalWages == null)? new BigDecimal("0"): new BigDecimal(totalWages);
			lista.add(valLBR);
			
			Zona_ventaDao daoz = new Zona_ventaDao();
			List<Zona_venta> listaZ = daoz.buscarZonaSucursal(oficina/1000);
			
			BigDecimal presupuestoR = new BigDecimal("0");
			BigDecimal realR = new BigDecimal("0");
			for (Zona_venta zona_venta : listaZ) {

				consulta = session.createCriteria(Recaudo.class);
				consulta.createAlias("zonaVenta", "z");
				consulta.add(Restrictions.eq("z.id_zona_venta", zona_venta.getId_zona_venta()));				
				consulta.add(Restrictions.between("fecha", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("presupuesto"));
				BigDecimal preR = (BigDecimal) consulta.uniqueResult();
				presupuestoR = (preR == null)? presupuestoR: presupuestoR.add(preR); 
				
				consulta.setProjection(Projections.sum("real"));
				BigDecimal rR = (BigDecimal) consulta.uniqueResult();
				realR = (rR == null)? realR: realR.add(rR);
			}
			
			lista.add(presupuestoR);
			lista.add(realR);
			
			return lista;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}

	//*Lista presupuesto de la oficina  "ji"  " /ji/vistaModulo" *//
	public List <BigDecimal> presupuestoPorOficinaI(){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List <BigDecimal> lista = new ArrayList<>();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();	

			Criteria consulta = session.createCriteria(PresupuestoE.class);
			Criterion resul =Restrictions.in("oficinaVentas", new Integer[]{1000,2000});
			consulta.add(resul);
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal valor = (BigDecimal) consulta.uniqueResult();
			lista.add(valor);
			
			consulta = session.createCriteria(Detalle.class);
			resul =Restrictions.in("sucursal", new Integer[]{1000,2000});
			consulta.add(resul);
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long valorN = (Long) consulta.uniqueResult();
			valorN = (valorN == null)? 0 : valorN;
			valor = new BigDecimal(valorN);
			lista.add(valor);

			return lista;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}



	//*Lista presupuesto de la linea por oficina *//
	public List <BigDecimal> datoPorLineaPais(int codigo, int oficina, Date fechaInicial, Date fechaFinal){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List <BigDecimal> lista = new ArrayList<>();
		try{

			Criteria consulta = session.createCriteria(PresupuestoE.class);
			consulta.add(Restrictions.eq("linea", codigo));
			consulta.add(Restrictions.eq("oficinaVentas", oficina));
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal valor = (BigDecimal) consulta.uniqueResult();
			valor = ( valor == null)? new BigDecimal("0"): valor; 
			lista.add(valor);
			consulta.setProjection(Projections.sum("utilidad"));
			valor = (BigDecimal) consulta.uniqueResult();
			valor = ( valor == null)? new BigDecimal("0"): valor;
			lista.add(valor);

			return lista;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}


	public BigDecimal datoPorLineaSum(int idPersona){

		Session session = HibernateUtil.getSessionfactory().openSession();

		try{
			BigDecimal totalWages = new BigDecimal("0.00");
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();
			
			Criteria consulta = session.createCriteria(Presupuesto.class);	
			consulta.add(Restrictions.eq("funcionario", idPersona));
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			totalWages = (BigDecimal) consulta.uniqueResult();
			return totalWages;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}

	public BigDecimal datoPorLineaSumFechas(int idPersona, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();

		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);

			Criteria consulta = session.createCriteria(Presupuesto.class);	
			consulta.add(Restrictions.eq("funcionario", idPersona));
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal totalWages = (BigDecimal) consulta.uniqueResult();
			return totalWages;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}

	public BigDecimal datoPorLineaSumFechasE(int idPersona, int idC,  String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();

		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);
			
			
			
			Criteria consulta = session.createCriteria(PresupuestoE.class);	
			consulta.add(Restrictions.eq("funcionario", idPersona));
			System.out.println(idC  + "--" +  idPersona);
			if(idC == 3 || idC == 6){
				Criterion resul = Restrictions.in("oficinaVentas", new Integer[]{4000,7000});
				consulta.add(resul);
			}		
			else{
				idC = (idC == 1)? 1000 :(idC == 7)? 2000: (idC+1)*1000;
				consulta.add(Restrictions.eq("oficinaVentas", idC));
			}	

			
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal totalWages = (BigDecimal) consulta.uniqueResult();
			return totalWages;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}


	//*Lista el presupuesto del vendedor Externo Por linea*//
	public List <BigDecimal> sumaLineaPorFuncionarioE(int codigo, int idPersona, Date fechaInicial, Date fechaFinal){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List <BigDecimal> lista = new ArrayList<>();
		try{
			Criteria consulta = session.createCriteria(PresupuestoE.class);	
			consulta.add(Restrictions.eq("linea", codigo));
			consulta.add(Restrictions.eq("funcionario", idPersona));
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal valor = (BigDecimal) consulta.uniqueResult();
			lista.add(valor);
			consulta.setProjection(Projections.sum("utilidad"));
			valor = (BigDecimal) consulta.uniqueResult();
			lista.add(valor);
			return lista;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}




	//*Lista el presupuesto del Funcionario por linea *//
	public List <BigDecimal> sumaLineaPorFuncionario(int codigo, int idPersona, Date fechaInicial, Date fechaFinal){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List <BigDecimal> lista = new ArrayList<>();
		try{

			Criteria consulta = session.createCriteria(Presupuesto.class);			
			consulta.add(Restrictions.eq("linea", codigo));
			consulta.add(Restrictions.eq("funcionario", idPersona));
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal valor = (BigDecimal) consulta.uniqueResult();
			lista.add(valor);
			consulta.setProjection(Projections.sum("utilidad"));
			valor = (BigDecimal) consulta.uniqueResult();
			lista.add(valor);
			return lista;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}

	//*Suma el ingreso y utilidad del funcionario en un periodo*//
	public List <BigDecimal> sumaPresupuestoVendedor( String tipo, int idPersona, Date fechaInicial, Date fechaFinal){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List <BigDecimal> lista = new ArrayList<>();
		try{
			Criteria consulta = session.createCriteria(PresupuestoE.class);			
			consulta.add(Restrictions.eq( "funcionario", idPersona));
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal valor = (BigDecimal) consulta.uniqueResult();
			lista.add(valor);
			consulta.setProjection(Projections.sum("utilidad"));
			valor = (BigDecimal) consulta.uniqueResult();
			lista.add(valor);
			return lista;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}

	//*Suma el presupuesto del funcionario Interno  en un periodo*//
	public List <BigDecimal> sumaPresupuestoVendedorI( String tipo, int idPersona, Date fechaInicial, Date fechaFinal){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List <BigDecimal> lista = new ArrayList<>();
		try{
			Criteria consulta = session.createCriteria(Presupuesto.class);			
			consulta.add(Restrictions.eq( "funcionario", idPersona));
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal valor = (BigDecimal) consulta.uniqueResult();
			lista.add(valor);
			consulta.setProjection(Projections.sum("utilidad"));
			valor = (BigDecimal) consulta.uniqueResult();
			lista.add(valor);
			return lista;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}

	//*Suma el ingreso y utilidad del Presupuesto del funcionario por ciudad y periodo*//
	public List <BigDecimal> sumaPresupuestoVendedorPorCiudad( int idCiudad, int idPersona, Date fechaInicial, Date fechaFinal){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List <BigDecimal> lista = new ArrayList<>();
		try{
			Criteria consulta = session.createCriteria(PresupuestoE.class);	
			consulta.add(Restrictions.eq( "oficinaVentas", idCiudad));
			consulta.add(Restrictions.eq( "funcionario", idPersona));
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal valor = (BigDecimal) consulta.uniqueResult();
			lista.add(valor);
			consulta.setProjection(Projections.sum("utilidad"));
			valor = (BigDecimal) consulta.uniqueResult();
			lista.add(valor);
			return lista;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}

	//*Suma el ingreso y utilidad por UEN*//
	public List <BigDecimal> sumaPresupuestoUEN( int uen, Date fechaInicial, Date fechaFinal){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List <BigDecimal> lista = new ArrayList<>();
		try{
			String uenS = String.valueOf(uen);
			Criteria consulta = session.createCriteria(PresupuestoE.class);			
			consulta.add(Restrictions.eq( "uen", uenS));
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal valor = (BigDecimal) consulta.uniqueResult();
			lista.add(valor);
			consulta.setProjection(Projections.sum("utilidad"));
			valor = (BigDecimal) consulta.uniqueResult();
			lista.add(valor);
			return lista;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}

	//*Lista el presupuesto de los especialistas por  LineaNacional "DL DA" "vista /dl/plandl"*//

	public BigDecimal sumaPorLineaNacional(int linea, int idPersona, Date fechaInicial, Date fechaFinal){

		Session session = HibernateUtil.getSessionfactory().openSession();
		try{
			Criteria consulta = session.createCriteria(PresupuestoE.class);
			if(linea == 6 ){
				Criterion resul =Restrictions.in("linea", new Integer[]{6,10});
				consulta.add(resul);
			}
			else{
				consulta.add(Restrictions.eq("linea", linea));
			}
			consulta.add(Restrictions.eq("funcionario", idPersona));
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal totalWages = (BigDecimal) consulta.uniqueResult();
			return totalWages;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}

	//*Lista el presupuesto de los INTERNO por  LineaNacional "DL DA" "vista /dl/plandl"*//

	public BigDecimal sumaPorLineaNacionalI(int linea, int idPersona, Date fechaInicial, Date fechaFinal){

		Session session = HibernateUtil.getSessionfactory().openSession();
		try{
			Criteria consulta = session.createCriteria(Presupuesto.class);
			if(linea == 6 ){
				Criterion resul =Restrictions.in("linea", new Integer[]{6,10});
				consulta.add(resul);
			}
			else{
				consulta.add(Restrictions.eq("linea", linea));
			}
			consulta.add(Restrictions.eq("funcionario", idPersona));
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal totalWages = (BigDecimal) consulta.uniqueResult();
			return totalWages;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}

	public List <BigDecimal> ventasMes(String tipo, int idfuncionario){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List <BigDecimal> lista = new ArrayList<>();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();

			Criteria consulta = session.createCriteria(Detallesin.class);
			consulta.add(Restrictions.eq(tipo, idfuncionario));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long  totalWages = (Long) consulta.uniqueResult();
			BigDecimal valN = (totalWages == null)? new BigDecimal("0"): new BigDecimal(totalWages);
			lista.add(valN);

			if(tipo.equals("funcionarioI")){
				consulta = session.createCriteria(Presupuesto.class);
			}
			else{
				consulta = session.createCriteria(PresupuestoE.class);
			}

			consulta.add(Restrictions.eq("funcionario", idfuncionario));
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal valorp = (BigDecimal) consulta.uniqueResult();
			valorp = (valorp==null)? new BigDecimal("0"):valorp;
			lista.add(valorp);

			
			String tipo1 = (tipo.equals("funcionarioI"))? "codVendedorInt": "codEspecialista";
			
			consulta = session.createCriteria(bajaRotacion.class);			
			consulta.add(Restrictions.eq(tipo1, idfuncionario));
			Criterion resul =Restrictions.or(Restrictions.eq("almacen", 1020),
					Restrictions.eq("almacen", 2020), Restrictions.eq("almacen", 3020),
					Restrictions.eq("almacen", 4020), Restrictions.eq("almacen", 5020),
					Restrictions.eq("almacen", 6020));
			consulta.add(resul);
			consulta.add(Restrictions.between("fechaFactura", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			totalWages = (Long) consulta.uniqueResult();
			BigDecimal valLBR = (totalWages == null)? new BigDecimal("0"): new BigDecimal(totalWages);
			lista.add(valLBR);
			
			consulta = session.createCriteria(Recaudo.class);
			consulta.createAlias("funcionario", "f");
			consulta.add(Restrictions.eq("f.id_funcionario", idfuncionario));
			consulta.add(Restrictions.between("fecha", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("presupuesto"));
			BigDecimal presupuestoR = (BigDecimal) consulta.uniqueResult();
			presupuestoR = (presupuestoR == null)? new BigDecimal("0"): presupuestoR; 
			lista.add(presupuestoR);
			
			consulta.setProjection(Projections.sum("real"));
			BigDecimal realR = (BigDecimal) consulta.uniqueResult();
			realR = (realR == null)? new BigDecimal("0"): realR;
			lista.add(realR);
			
			return lista;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	//*Lista el Presupuesto del Pais en el mes  "GG"*//
	public List <BigDecimal> listaPresupuestoPaisMes(){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List <BigDecimal> listaTotal = new ArrayList<>();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();

			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long  totalWages = (Long) consulta.uniqueResult();
			totalWages = (totalWages == null)? 0: totalWages;
			listaTotal.add(new BigDecimal(totalWages));
			
			consulta.setProjection(Projections.sum("costoTotal"));
			totalWages = (Long) consulta.uniqueResult();
			totalWages = (totalWages == null)? 0: totalWages;
			listaTotal.add(new BigDecimal(totalWages));

			consulta = session.createCriteria(PresupuestoE.class);			
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal valor = (BigDecimal) consulta.uniqueResult();
			listaTotal.add(valor);
			consulta.setProjection(Projections.sum("utilidad"));
			valor = (BigDecimal) consulta.uniqueResult();
			listaTotal.add(valor);

			return listaTotal;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	
	
	//*Lista el Presupuesto  Acumulado del Pais en el desde enero hasta la Fecha  "GG"*//
	public List <BigDecimal> listaPresupuestoPaisAcumulado(String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List <BigDecimal> listaTotal = new ArrayList<>();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial("01", fecYear);

			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long  totalWages = (Long) consulta.uniqueResult();
			listaTotal.add(new BigDecimal(totalWages));
			consulta.setProjection(Projections.sum("costoTotal"));
			totalWages = (Long) consulta.uniqueResult();
			listaTotal.add(new BigDecimal(totalWages));

			consulta = session.createCriteria(PresupuestoE.class);			
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal valor = (BigDecimal) consulta.uniqueResult();
			listaTotal.add(valor);
			consulta.setProjection(Projections.sum("utilidad"));
			valor = (BigDecimal) consulta.uniqueResult();
			listaTotal.add(valor);

			return listaTotal;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	//*Lista el Presupuesto del Pais en los meses que van en el años "DL"*//

	//	public List <BigDecimal> listaPresupuestoPais(){
	//
	//		Session session = HibernateUtil.getSessionfactory().openSession();
	//		List <BigDecimal> listaTotal = new ArrayList<>();
	//		try{
	//			int meses = fechaFinalR();
	//			System.out.println(meses);
	//			Date fechaFinal = null;
	//			Date fechaInicial = null;
	//			DateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
	//			for (int i=1; i<meses; i++){
	//				String fecInicial = "2016/0"+i+"/01";
	//				fechaInicial  = formatoFecha.parse(fecInicial);
	//				String fecFinal = (i== 2)? "2016/0"+i+"/29": (i== 4 || i==6 || i== 9 || i== 11)? "2016/0"+i+"/30" :"2016/0"+i+"/31";
	//				fechaFinal = formatoFecha.parse(fecFinal);
	//				Criteria consulta = session.createCriteria(Detalle.class);
	//				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
	//				consulta.setProjection(Projections.sum("valorNeto"));
	//				Long  totalWages = (Long) consulta.uniqueResult();
	//				listaTotal.set(0, new BigDecimal(totalWages));
	//				consulta.setProjection(Projections.sum("costoTotal"));
	//				totalWages = (Long) consulta.uniqueResult();
	//				listaTotal.set(1, new BigDecimal(totalWages));
	//	
	//				consulta = session.createCriteria(PresupuestoE.class);			
	//				consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
	//				consulta.setProjection(Projections.sum("ingresos"));
	//				BigDecimal valor = (BigDecimal) consulta.uniqueResult();
	//				listaTotal.set(2, valor);
	//				consulta.setProjection(Projections.sum("utilidad"));
	//				valor = (BigDecimal) consulta.uniqueResult();
	//				listaTotal.set(3, valor);
	//				
	//			return listaTotal;
	//
	//		} catch (RuntimeException ex) {
	//			throw ex;
	//		}
	//		finally{
	//			session.close();
	//		}
	//	}
}
