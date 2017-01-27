package co.com.CGAwebComercial.dao;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.Ciudad;
import co.com.CGAwebComercial.entyties.Comision;
import co.com.CGAwebComercial.entyties.Detalle;
import co.com.CGAwebComercial.entyties.Detallesin;
import co.com.CGAwebComercial.entyties.Esquemas;
import co.com.CGAwebComercial.entyties.Funcionario;
import co.com.CGAwebComercial.entyties.OficinaVendedorInterno;
import co.com.CGAwebComercial.entyties.Presupuesto;
import co.com.CGAwebComercial.entyties.PresupuestoE;
import co.com.CGAwebComercial.entyties.Recaudo;
import co.com.CGAwebComercial.entyties.Zona_Funcionario;
import co.com.CGAwebComercial.entyties.Zona_venta;
import co.com.CGAwebComercial.resource.Recursos;
import co.com.CGAwebComercial.util.ComisionVendedores;
import co.com.CGAwebComercial.util.HibernateUtil;

public class RecaudoDao extends GenericDao<Recaudo> {

	@SuppressWarnings("unchecked")
	public List<Recaudo> listarRecaudo(int idPersona){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Recaudo> recaudo = null;		
		try{
			FuncionarioDao dao = new FuncionarioDao();
			Funcionario funcionario = dao.buscarPersona(idPersona);
			Zona_ventaDao daoZ = new Zona_ventaDao();
			List<Zona_venta> zona = daoZ.buscarZona(funcionario.getId_funcionario());

			for (Zona_venta zona_venta : zona) {
				Criteria consulta = session.createCriteria(Recaudo.class);			
				consulta.createAlias("zonaVenta", "z");
				consulta.add(Restrictions.eq("z.id_zona_venta", zona_venta.getId_zona_venta()));
				recaudo = consulta.list();

			}			
			return recaudo;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Recaudo> listarPresupuesto(int idPersona){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Recaudo> recaudo = null;
		List<Recaudo> listaRecaudo = new ArrayList<>();		
		try{
			Date fechaFinal;
			Date fechaInicial;
			int meses = fechaFinalR();
			DateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
			FuncionarioDao dao = new FuncionarioDao();
			Funcionario funcionario = dao.buscarPersona(idPersona);
			System.out.println(idPersona);
			Zona_ventaDao daoZ = new Zona_ventaDao();
			List<Zona_venta> zona = daoZ.buscarZona(funcionario.getId_funcionario());
			
			ComisionDao daoC = new ComisionDao();
			Comision comision = daoC.buscar(funcionario.getComision().getIdComision());
			for (int i=1; i<meses; i++){
				Recaudo recaudoA = new Recaudo();
				Recursos recurso = new Recursos();
				int year = recurso.yearActual();
				String fecInicial = year +"/0"+i+"/01";
				fechaInicial  = formatoFecha.parse(fecInicial);
				String fecFinal = (i== 2)? year+"/0"+i+"/29": (i== 4 || i==6 || i== 9 || i== 11)? year+"/0"+i+"/30" : year+"/0"+i+"/31";
				fechaFinal = formatoFecha.parse(fecFinal);
			
				for (Zona_venta zona_venta : zona) {
					Criteria consulta = session.createCriteria(Recaudo.class);			
					consulta.createAlias("zonaVenta", "z");
					consulta.add(Restrictions.eq("z.id_zona_venta", zona_venta.getId_zona_venta()));
					consulta.add(Restrictions.between("fecha", fechaInicial, fechaFinal));
					recaudo = consulta.list();
	
				}
				//System.out.println(recaudo.size());
				if(recaudo != null && recaudo.size() >0){
					for (Recaudo recaudo1 : recaudo ) {
		
		
						int real = recaudo1.getReal().compareTo(new BigDecimal("0.00"));
						if(real == 0){
							recaudo1.setCumplimiento(new BigDecimal("0.00"));
							recaudo1.setUmbral(comision.getUmbralRecaudo());
							recaudo1.setImagen("rojo.png");
							recaudo1.setValorComision(new BigDecimal("0.00"));
						}
						else{
		
							double cum =  recaudo1.getReal().doubleValue();
							double pre = recaudo1.getPresupuesto().doubleValue();
							cum= cum / pre *100  ; 
							System.out.println(recaudo1.getReal() + "---" + recaudo1.getPresupuesto() );
							BigDecimal cumplimiento =(recaudo1.getReal().longValue()== 0 || recaudo1.getPresupuesto().longValue() == 0)? new BigDecimal("0") : recaudo1.getReal().divide(recaudo1.getPresupuesto(), 2, BigDecimal.ROUND_HALF_UP);  
							recaudo1.setCumplimiento(cumplimiento.multiply(new BigDecimal("100")));
							recaudo1.setUmbral(comision.getUmbralRecaudo());
							int numero = recaudo1.getUmbral().compareTo(recaudo1.getCumplimiento().divide(new BigDecimal("100")));
							if(numero == 1){
								recaudo1.setImagen("rojo.png");
								recaudo1.setValorComision(new BigDecimal("0.00"));
							}
							else{
								recaudo1.setImagen("verde.png");
								recaudo1.setValorComision(recaudo1.getCumplimiento().multiply(comision.getValorBaseRecaudo()).divide(new BigDecimal("100")));
							}
						}	
						listaRecaudo.add(recaudo1);
					}
				}
				else{
					recaudoA.setPresupuesto(new BigDecimal("0.00"));
					recaudoA.setReal(new BigDecimal("0.00"));
					recaudoA.setCumplimiento(new BigDecimal("0.00"));
					recaudoA.setUmbral(comision.getUmbralRecaudo());
					recaudoA.setImagen("rojo.png");
					recaudoA.setValorComision(new BigDecimal("0.00"));
					listaRecaudo.add(recaudoA);
				}
			}
			return listaRecaudo;
		} catch (RuntimeException ex) {
			throw ex;
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			session.close();
		}
		return listaRecaudo;

	}

	public List<Long> listarDetalleValorNeto(String tipo, int idPersona){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Long> ValorNeto = new ArrayList<>();
		try{
			int meses = fechaFinalR();
			Date fechaFinal = null;
			Date fechaInicial = null;
			DateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
			Recursos recurso = new Recursos();
			int year = recurso.yearActual();
			for (int i=1; i<meses; i++){
				String fecInicial = year+"/0"+i+"/01";
				fechaInicial  = formatoFecha.parse(fecInicial);
				String fecFinal = (i== 2)? year+"/0"+i+"/29": (i== 4 || i==6 || i== 9 || i== 11)? year+"/0"+i+"/30" : year+"/0"+i+"/31";
				fechaFinal = formatoFecha.parse(fecFinal);
				Criteria consulta = session.createCriteria(Detalle.class);
				//Criteria consulta = session.createCriteria(Detallesin.class);
				consulta.add(Restrictions.eq(tipo, idPersona));
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("valorNeto"));
				Number totalWages = (Number) consulta.uniqueResult();
				ValorNeto.add((totalWages == null)? 0 :totalWages.longValue());
			}
			return ValorNeto;

		} catch (RuntimeException ex) {
			throw ex;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			session.close();
		}
		return ValorNeto;	
	}

	//*Lista el presupuesto del mes del especialista y el interno*//

	public List<BigDecimal> presupuestoVendedor(String tipo, int idPersona){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<BigDecimal> presupuesto = new ArrayList<>();
		try{
			Criteria consulta;
			int meses = fechaFinalR();
			Date fechaFinal = null;
			Date fechaInicial = null;
			DateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
			Recursos recurso = new Recursos();
			int year = recurso.yearActual();
			for (int i=1; i<meses; i++){
				String fecInicial = year+"/0"+i+"/01";			
				fechaInicial  = formatoFecha.parse(fecInicial);
				String fecFinal = (i== 2)? year+"/0"+i+"/29": (i== 4 || i==6 || i== 9 || i== 11)? year+"/0"+i+"/30" :year+"/0"+i+"/31";
				fechaFinal = formatoFecha.parse(fecFinal);

				if(tipo.equals("funcionarioI")){
					consulta = session.createCriteria(Presupuesto.class);
				}
				else{
					consulta = session.createCriteria(PresupuestoE.class);
				}

				consulta.add(Restrictions.eq("funcionario",  idPersona));
				consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("ingresos"));
				BigDecimal valorp = (BigDecimal) consulta.uniqueResult();
				valorp = (valorp==null)? new BigDecimal("0"):valorp;
				presupuesto.add(valorp);
			}	

		} catch (RuntimeException ex) {
			throw ex;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			session.close();
		}
		return presupuesto;
	}
	public List<Long> listarDetalleValorDescuento(String tipo,int idPersona){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Long> ValorDescuento = new ArrayList<>();
		try{
			int meses = fechaFinalR();
			Date fechaFinal;
			Date fechaInicial;
			DateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
			Recursos recurso = new Recursos();
			int year = recurso.yearActual();
			for (int i=1; i<meses; i++){
				String fecInicial = year+"/0"+i+"/01";	
				fechaInicial  = formatoFecha.parse(fecInicial);
				String fecFinal = (i== 2)? year+"/0"+i+"/29": (i== 4 || i==6 || i== 9 || i== 11)? year+"/0"+i+"/30" : year+"/0"+i+"/31";
				fechaFinal = formatoFecha.parse(fecFinal);

				//Criteria consulta = session.createCriteria(Detalle.class);
				Criteria consulta = session.createCriteria(Detallesin.class);
				consulta.add(Restrictions.eq(tipo, idPersona));
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("valorDescuentos"));
				Number totalWages = (Number) consulta.uniqueResult();
				ValorDescuento.add((totalWages == null)? 0 :totalWages.longValue());
			}
			return ValorDescuento;

		} catch (RuntimeException ex) {
			throw ex;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			session.close();
		}
		return ValorDescuento;	
	}

	public List<Long> listarDetalleCostoTotal(String tipo, int idPersona){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Long> costoTotal = new ArrayList<>();
		try{
			int meses = fechaFinalR();
			Date fechaFinal = null;
			Date fechaInicial = null;
			DateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
			Recursos recurso = new Recursos();
			int year = recurso.yearActual();
			for (int i=1; i<meses; i++){
				String fecInicial = year+"/0"+i+"/01";
				fechaInicial  = formatoFecha.parse(fecInicial);
				String fecFinal = (i== 2)? year+"/0"+i+"/29": (i== 4 || i==6 || i== 9 || i== 11)? year+"/0"+i+"/30" :year+"/0"+i+"/31";
				fechaFinal = formatoFecha.parse(fecFinal);
				//Criteria consulta = session.createCriteria(Detalle.class);
				Criteria consulta = session.createCriteria(Detallesin.class);
				consulta.add(Restrictions.eq(tipo, idPersona));
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("costoTotal"));
				Number totalWages = (Number) consulta.uniqueResult();
				costoTotal.add((totalWages == null)? 0 : totalWages.longValue());
			}
			return costoTotal;

		} catch (RuntimeException ex) {
			throw ex;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			session.close();
		}
		return costoTotal;	
	}

	@SuppressWarnings({ "unchecked" })
	public List<ComisionVendedores> carteraInternos(String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();

		try{
			Date fechaFinal;
			Date fechaInicial;
			ComisionVendedores sucursales = null;
			List<ComisionVendedores> listaoficinas = new ArrayList<>();
			Recaudo itemSucursal = null ;
			List<Recaudo> recaudo = null;				

			CiudadDao daoC = new CiudadDao();
			List<Ciudad> listaSucursales = daoC.listar();
			for (Ciudad ciudad : listaSucursales) {
				Zona_ventaDao daoZ = new Zona_ventaDao();
				List<Zona_venta> Listazona = daoZ.buscarZonaSucursal(ciudad.getId());

				BigDecimal sumaR = new BigDecimal("0.00");
				BigDecimal sumaP = new BigDecimal("0.00");
				sucursales = new ComisionVendedores();
				sucursales.setId(ciudad.getId());
				sucursales.setNombre(ciudad.getNombre());

				for (Zona_venta zona_venta : Listazona) {

					if (fecMes.equals("")){
						fechaFinal = fechaFinal();
						fechaInicial = fechaInicial();	
					}
					else{
						fechaFinal = fechaFinal(fecMes, fecYear);
						fechaInicial = fechaInicial(fecMes, fecYear);
					}
					Criteria consulta = session.createCriteria(Recaudo.class);			
					consulta.createAlias("zonaVenta", "z");
					consulta.add(Restrictions.eq("z.id_zona_venta", zona_venta.getId_zona_venta()));
					consulta.add(Restrictions.between("fecha", fechaInicial, fechaFinal));
					recaudo = consulta.list();

					for (Recaudo recaudo1 : recaudo ) {
						itemSucursal = new Recaudo();
						sumaR = sumaR.add(recaudo1.getReal());
						itemSucursal.setReal(sumaR);
						sumaP = sumaP.add(recaudo1.getPresupuesto());
						itemSucursal.setPresupuesto(sumaP);

						sucursales.setPresupuestoB(itemSucursal.getPresupuesto());
						sucursales.setIngresoRealB(itemSucursal.getReal());
					}
				}
				BigDecimal cumplimiento = new BigDecimal("0.00"); 
				double va;

				if(sucursales.getIngresoRealB()== null ||  sucursales.getPresupuestoB() == null){
					va = 0;
				}
				else{
					va =(double) sucursales.getIngresoRealB().doubleValue() / sucursales.getPresupuestoB().doubleValue();
				}

				if(va == 0){
					sucursales.setCumplimiento(new BigDecimal("0.00"));
				}
				else{
					String cum = String.valueOf(va);
					cumplimiento = new BigDecimal(cum); 
					sucursales.setComisionS(new DecimalFormat("###.##").format(cumplimiento.multiply(new BigDecimal("100"))));
					sucursales.setCumplimiento(cumplimiento.multiply(new BigDecimal("100")));
				}
				EsquemasDao daoE = new EsquemasDao();
				Esquemas esquema = daoE.buscar(1);
				sucursales.setUmbralCV(esquema.getUmbralRecaudo().multiply(new BigDecimal("100")));
				int numero = sucursales.getUmbralCV().compareTo(sucursales.getCumplimiento());
				if(numero == 1){
					sucursales.setImagen("rojo.png");
				}
				else{
					sucursales.setImagen("verde.png");
				}
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


	@SuppressWarnings({ "unchecked" })
	public List<OficinaVendedorInterno> listaVendedorInterno(int oficina){

		Session session = HibernateUtil.getSessionfactory().openSession();		
		List<OficinaVendedorInterno> listaVenInt;
		try{
			Criteria consulta = session.createCriteria(OficinaVendedorInterno.class);			
			consulta.add(Restrictions.eq("oficinadeventas", oficina));			
			listaVenInt = consulta.list();			

			return listaVenInt;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	//*Listar recaudo de internos para las graficas*//
	@SuppressWarnings({ "unchecked" })
	public List<Recaudo> carteraInternosGraficas(int codSap){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<ComisionVendedores> listaoficinas = new ArrayList<>();
		try{

			Criteria consulta = session.createCriteria(OficinaVendedorInterno.class);			
			consulta.add(Restrictions.eq("codigosap", codSap));			
			OficinaVendedorInterno listaVenInt = (OficinaVendedorInterno) consulta.uniqueResult();	

			Date fechaFinal;
			Date fechaInicial;
			ComisionVendedores sucursales = null;

			Recaudo itemSucursal = null ;
			List<Recaudo> recaudo = null;
			List<Recaudo> recaudoList = new ArrayList<>();
			int ci = listaVenInt.getOficinadeventas();
			//String c="";
			int codCiudad = Integer.parseInt(""+Integer.toString(ci).charAt(0));
			codCiudad = (codCiudad == 1)? codCiudad: codCiudad-1; 
			CiudadDao daoC = new CiudadDao();
			Ciudad ciudad = daoC.buscar(codCiudad);
			Zona_ventaDao daoZ = new Zona_ventaDao();
			List<Zona_venta> Listazona = daoZ.buscarZonaSucursal(ciudad.getId());


			sucursales = new ComisionVendedores();
			sucursales.setId(ciudad.getId());
			sucursales.setNombre(ciudad.getNombre());
			int meses = fechaFinalR();
			DateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
			Recursos recurso = new Recursos();
			int year = recurso.yearActual();
			for (int i=1; i<meses; i++){
				String fecInicial = year+"/0"+i+"/01";
				fechaInicial  = formatoFecha.parse(fecInicial);
				String fecFinal = (i== 2)? year+"/0"+i+"/29": (i== 4 || i==6 || i== 9 || i== 11)? year+"/0"+i+"/30" :year+"/0"+i+"/31";
				fechaFinal = formatoFecha.parse(fecFinal);
				BigDecimal sumaR = new BigDecimal("0.00");
				BigDecimal sumaP = new BigDecimal("0.00");
				for (Zona_venta zona_venta : Listazona) {
					consulta = session.createCriteria(Recaudo.class);			
					consulta.createAlias("zonaVenta", "z");
					consulta.add(Restrictions.eq("z.id_zona_venta", zona_venta.getId_zona_venta()));
					consulta.add(Restrictions.between("fecha", fechaInicial, fechaFinal));
					recaudo = consulta.list();
					System.out.println(recaudo.size() + " recuado" + fechaFinal);
					if(recaudo.size() >0){
						for (Recaudo recaudo1 : recaudo ) {
							itemSucursal = new Recaudo();
							sumaR = sumaR.add(recaudo1.getReal());
							itemSucursal.setReal(sumaR);
							sumaP = sumaP.add(recaudo1.getPresupuesto());
							itemSucursal.setPresupuesto(sumaP);
	
							sucursales.setPresupuestoB(itemSucursal.getPresupuesto());
							sucursales.setIngresoRealB(itemSucursal.getReal());
							//sucursales.setComision(itemSucursal.getPresupuesto().intValue());
							//recaudo.add(itemSucursal);
						}
					}
					else{
						itemSucursal = new Recaudo();
						sumaR = sumaR.add(new BigDecimal("0"));
						itemSucursal.setReal(sumaR);
						sumaP =sumaP.add(new BigDecimal("0"));
						itemSucursal.setPresupuesto(sumaP);
					}

				}
				recaudoList.add(itemSucursal);
				listaoficinas.add(sucursales);
				sucursales = new ComisionVendedores();
			}

			return recaudoList;
		} catch (RuntimeException ex) {
			throw ex;
		}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			session.close();
		}
		return null;
	}

	public Recaudo listarRecudoDirector(String zona, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);
			
			Criteria consulta = session.createCriteria(Recaudo.class);			
			consulta.createAlias("zonaVenta", "z");
			consulta.add(Restrictions.eq("z.id_zona_venta", zona));
			consulta.add(Restrictions.between("fecha", fechaInicial, fechaFinal));
			Recaudo recaudo = (Recaudo)consulta.uniqueResult();
			return recaudo;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}

	//*Lista el recaudo del director*//
	@SuppressWarnings("unchecked")
	public List<Recaudo> listarRecaudoDirector(int idPersona, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Recaudo> recaudo = new ArrayList<>();		
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);

			//			FuncionarioDao dao = new FuncionarioDao();
			//			Funcionario funcionario = dao.buscarPersona(idPersona);
			Zona_ventaDao daoZ = new Zona_ventaDao();

			List<Zona_venta> zona = daoZ.buscarZona(idPersona);

			if(zona != null){
				for (Zona_venta zona_venta : zona) {
					
					Criteria consulta = session.createCriteria(Recaudo.class);			
					consulta.createAlias("zonaVenta", "z");
					consulta.add(Restrictions.eq("z.id_zona_venta", zona_venta.getId_zona_venta()));
					consulta.add(Restrictions.between("fecha", fechaInicial, fechaFinal));
					recaudo = consulta.list();
				}	
				return recaudo;
			}
			else{
				recaudo.get(0).setPresupuesto(new BigDecimal("0.00"));
				recaudo.get(0).setReal(new BigDecimal("0.00"));
				return recaudo;
			}

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	//*Lista el recaudo del Pais en los meses que van en el años*//

	public List <BigDecimal> recaudoPaisPeriodos(){

		Session session = HibernateUtil.getSessionfactory().openSession();
		//List<Long> costoTotal = new ArrayList<>();
		List <BigDecimal> listaTotal = new ArrayList<>();
		try{
			int meses = fechaFinalR();
			Date fechaFinal = null;
			Date fechaInicial = null;
			DateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
			Recursos recurso = new Recursos();
			int year = recurso.yearActual();
			for (int i=1; i<meses; i++){
				String fecInicial = year+"/0"+i+"/01";
				fechaInicial  = formatoFecha.parse(fecInicial);
				String fecFinal = (i== 2)? year+"/0"+i+"/29": (i== 4 || i==6 || i== 9 || i== 11)? year+"/0"+i+"/30" :year+"/0"+i+"/31";
				fechaFinal = formatoFecha.parse(fecFinal);

				Criteria consulta = session.createCriteria(PresupuestoE.class);
				consulta.add(Restrictions.eq("linea" ,1));
				consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("ingresos"));
				BigDecimal valor = (BigDecimal) consulta.uniqueResult();
				listaTotal.add(valor);
				//costoTotal.add(totalWages);

				consulta = session.createCriteria(Detalle.class);
				consulta.add(Restrictions.eq("linea" ,1));
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("valorNeto"));
				Long totalWages = (Long) consulta.uniqueResult();
				listaTotal.add(new BigDecimal(totalWages));

				consulta = session.createCriteria(Detalle.class);
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("valorNeto"));
				totalWages = (Long) consulta.uniqueResult();
				listaTotal.add(new BigDecimal(totalWages));
				//listaTotal.set(0, new BigDecimal(totalWages));


				consulta = session.createCriteria(PresupuestoE.class);			
				consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("ingresos"));
				valor = (BigDecimal) consulta.uniqueResult();
				listaTotal.add(valor);
				//listaTotal.set(2, valor);

			}
			return listaTotal;

		} catch (RuntimeException ex) {
			throw ex;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			session.close();
		}
		return listaTotal;
	}

	//*Lista grafica linea Antidesgaste*//
	public List <BigDecimal> recaudoPaisPeriodosAn(){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List <BigDecimal> listaTotal = new ArrayList<>();
		try{
			int meses = fechaFinalR();
			
			Date fechaFinal = null;
			Date fechaInicial = null;
			DateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
			Recursos recurso = new Recursos();
			int year = recurso.yearActual();
			for (int i=1; i<meses; i++){
				String fecInicial = year+"/0"+i+"/01";
				fechaInicial  = formatoFecha.parse(fecInicial);
				String fecFinal = (i== 2)? year+"/0"+i+"/29": (i== 4 || i==6 || i== 9 || i== 11)? year+"/0"+i+"/30" :year+"/0"+i+"/31";
				fechaFinal = formatoFecha.parse(fecFinal);

				Criteria consulta = session.createCriteria(PresupuestoE.class);
				consulta.add(Restrictions.eq("linea", 6));
				consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("ingresos"));
				BigDecimal valor = (BigDecimal) consulta.uniqueResult();
				valor = (valor==null)? new BigDecimal("0"):valor;

				consulta = session.createCriteria(PresupuestoE.class);
				consulta.add(Restrictions.eq("linea", 10));
				consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("ingresos"));
				BigDecimal valor1 = (BigDecimal) consulta.uniqueResult();
				valor1 = (valor1==null)? new BigDecimal("0"):valor1;
				valor = valor.add(valor1);
				listaTotal.add(valor);
				//costoTotal.add(totalWages);

				Long totalWages = sumaLinea(fechaInicial, fechaFinal);
				listaTotal.add(new BigDecimal(totalWages));


				consulta = session.createCriteria(Detalle.class);
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("valorNeto"));
				totalWages = (Long) consulta.uniqueResult();
				totalWages = (totalWages== null)?0: totalWages;
				listaTotal.add(new BigDecimal(totalWages));
				//listaTotal.set(0, new BigDecimal(totalWages));


				consulta = session.createCriteria(PresupuestoE.class);			
				consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("ingresos"));
				valor = (BigDecimal) consulta.uniqueResult();
				valor = (valor==null)? new BigDecimal("0"):valor;
				listaTotal.add(valor);
				//listaTotal.set(2, valor);

			}
			return listaTotal;

		} catch (RuntimeException ex) {
			throw ex;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			session.close();
		}
		return listaTotal;
	}

	public Long sumaLinea(Date fechaInicial, Date fechaFinal){

		Session session = HibernateUtil.getSessionfactory().openSession();
		try{
			Criteria consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq("linea", 6));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long totalWages = (Long) consulta.uniqueResult();
			totalWages = (totalWages== null)?0: totalWages;

			consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq("linea", 10));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long totalWages1 = (Long) consulta.uniqueResult();
			totalWages1 = (totalWages1== null)?0: totalWages1;
			totalWages = totalWages + totalWages1;
			
			return totalWages;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	//*Lista grafica Para el director Comercial "dcB" "vistas /pages/dcB/vistaModulo"*//
	@SuppressWarnings("unchecked")
	public List <BigDecimal> recaudoPaisPeriodosDirectorB(int idCedula){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List <BigDecimal> listaTotal = new ArrayList<>();
		List <Recaudo> recaudo = new ArrayList<>();
		try{
			int meses = fechaFinalR();
			
			Date fechaFinal = null;
			Date fechaInicial = null;
			DateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
			Recursos recurso = new Recursos();
			int year = recurso.yearActual();
			for (int i=1; i<meses; i++){
				String fecInicial = year+"/0"+i+"/01";
				fechaInicial  = formatoFecha.parse(fecInicial);
				String fecFinal = (i== 2)? year+"/0"+i+"/29": (i== 4 || i==6 || i== 9 || i== 11)? year+"/0"+i+"/30" :year+"/0"+i+"/31";
				fechaFinal = formatoFecha.parse(fecFinal);
				
				FuncionarioDao daoF = new FuncionarioDao();
				Funcionario funcionario = daoF.buscarPersona(idCedula);

				Zona_FuncionarioDao daoFu =  new Zona_FuncionarioDao();
				Zona_Funcionario f = daoFu.buscarFuncionarioZona(funcionario.getId_funcionario());

				int sucursal = (f.getCiudad().getId() == 1)? 1000 : (f.getCiudad().getId() == 7)? 2000 : (f.getCiudad().getId()+1)*1000;
				
				Criteria consulta = session.createCriteria(PresupuestoE.class);
				if(sucursal == 4000){
					Criterion resul =Restrictions.in("oficinaVentas", new Integer[]{4000,7000});
					consulta.add(resul);
				}
				else{
					consulta.add(Restrictions.eq("oficinaVentas", sucursal ));
					
				}
				consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("ingresos"));
				BigDecimal valor = (BigDecimal) consulta.uniqueResult();
				valor = (valor==null)? new BigDecimal("0"):valor;
				listaTotal.add(valor);

				consulta = session.createCriteria(Detalle.class);
				if(sucursal == 4000){
					Criterion resul =Restrictions.in("sucursal", new Integer[]{4000,7000});
					consulta.add(resul);
				}
				else{
					consulta.add(Restrictions.eq("sucursal", sucursal ));
				}
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("valorNeto"));
				Long totalWages = (Long) consulta.uniqueResult();
				totalWages = (totalWages== null)?0: totalWages;
				listaTotal.add(new BigDecimal(totalWages));

				consulta = session.createCriteria(Zona_venta.class);
				consulta.createAlias("ciudad", "c");
				if(f.getCiudad().getId() == 3){
					Criterion resul =Restrictions.in("c.id", new Integer[]{3,6});
					consulta.add(resul);
				}
				else{
					consulta.add(Restrictions.eq("c.id", f.getCiudad().getId()));			
				}
				List<Zona_venta> zona = consulta.list();
				System.out.println(zona.size());
				BigDecimal presupuestoR = new BigDecimal("0");
				BigDecimal realR =  new BigDecimal("0");

				for (Zona_venta zona_venta : zona) {

					consulta = session.createCriteria(Recaudo.class);			
					consulta.createAlias("zonaVenta", "z");
					consulta.add(Restrictions.eq("z.id_zona_venta", zona_venta.getId_zona_venta()));
					consulta.add(Restrictions.between("fecha", fechaInicial, fechaFinal));
					recaudo = consulta.list();
					System.out.println(recaudo.size() +"fecha"+ fechaInicial +" --- "+ fechaFinal );
					if(recaudo.size()>0){
						presupuestoR = (recaudo.get(0).getPresupuesto().toString() == null)? presupuestoR.add(new BigDecimal("0")) : presupuestoR.add(recaudo.get(0).getPresupuesto());
						realR = (recaudo.get(0).getReal().toString() == null)? realR.add(new BigDecimal("0")) :realR.add(recaudo.get(0).getReal());
					}
				}
				listaTotal.add(realR);
				listaTotal.add(presupuestoR);

			}
			return listaTotal;

		} catch (RuntimeException ex) {
			throw ex;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			session.close();
		}
		return listaTotal;
	}

	//*Lista grafica Para el jefe Internos "ji" "vistas /pages/ji/vistaModulo"*//
	@SuppressWarnings("unchecked")
	public List <BigDecimal> recaudoPaisPeriodosJefeI(int idCedula){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List <BigDecimal> listaTotal = new ArrayList<>();
		List <Recaudo> recaudo = new ArrayList<>();
		try{
			int meses = fechaFinalR();
			
			Date fechaFinal = null;
			Date fechaInicial = null;
			DateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
			Recursos recurso = new Recursos();
			int year = recurso.yearActual();
			for (int i=1; i<meses; i++){
				String fecInicial = year+"/0"+i+"/01";
				fechaInicial  = formatoFecha.parse(fecInicial);
				String fecFinal = (i== 2)? year+"/0"+i+"/29": (i== 4 || i==6 || i== 9 || i== 11)? year+"/0"+i+"/30" :year+"/0"+i+"/31";
				fechaFinal = formatoFecha.parse(fecFinal);

				Criteria consulta = session.createCriteria(PresupuestoE.class);
				Criterion resul =Restrictions.in("oficinaVentas", new Integer[]{1000,2000});
				consulta.add(resul);
				consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("ingresos"));
				BigDecimal valor = (BigDecimal) consulta.uniqueResult();
				valor = (valor==null)? new BigDecimal("0"):valor;
				listaTotal.add(valor);

				consulta = session.createCriteria(Detalle.class);
				resul =Restrictions.in("sucursal", new Integer[]{1000,2000});
				consulta.add(resul);
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("valorNeto"));
				Long totalWages = (Long) consulta.uniqueResult();
				totalWages = (totalWages== null)?0: totalWages;
				listaTotal.add(new BigDecimal(totalWages));

//				FuncionarioDao daoF = new FuncionarioDao();
//				Funcionario funcionario = daoF.buscarPersona(idCedula);

//				Zona_FuncionarioDao daoFu = new Zona_FuncionarioDao();
//				Zona_Funcionario f = daoFu.buscarFuncionarioZona(funcionario.getId_funcionario());

				consulta = session.createCriteria(Zona_venta.class);
				consulta.createAlias("ciudad", "c");
				resul =Restrictions.in("c.id", new Integer[]{1,7});
				consulta.add(resul);
				List<Zona_venta> zona = consulta.list();

				BigDecimal presupuestoR = new BigDecimal("0");
				BigDecimal realR =  new BigDecimal("0");

				for (Zona_venta zona_venta : zona) {

					consulta = session.createCriteria(Recaudo.class);			
					consulta.createAlias("zonaVenta", "z");
					consulta.add(Restrictions.eq("z.id_zona_venta", zona_venta.getId_zona_venta()));
					consulta.add(Restrictions.between("fecha", fechaInicial, fechaFinal));
					recaudo = consulta.list();
					if(recaudo.size()>0){
						presupuestoR = (recaudo.get(0).getPresupuesto().toString() == null)? presupuestoR.add(new BigDecimal("0")) : presupuestoR.add(recaudo.get(0).getPresupuesto());
						realR = (recaudo.get(0).getReal().toString() == null)? realR.add(new BigDecimal("0")) :realR.add(recaudo.get(0).getReal());
					}
				}
				listaTotal.add(realR);
				listaTotal.add(presupuestoR);

			}
			return listaTotal;

		} catch (RuntimeException ex) {
			throw ex;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			session.close();
		}
		return listaTotal;
	}

	//*Lista El recaudo del jefe Internos "ji" "vistas /pages/ji/recaudoCarter"*//
	@SuppressWarnings("unchecked")
	public List <Recaudo> recaudoPaisJefeI(String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List <BigDecimal> listaTotal = new ArrayList<>();
		List <Recaudo> recaudo = new ArrayList<>();
		List <Recaudo> recaudo1 = new ArrayList<>();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null || fecYear.equals("") || fecYear == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecMes.equals("") || fecMes == null || fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);

			Criteria consulta = session.createCriteria(Zona_venta.class);
			consulta.createAlias("ciudad", "c");
			Criterion resul =Restrictions.in("c.id", new Integer[]{1,7});
			consulta.add(resul);
			List<Zona_venta> zona = consulta.list();

			BigDecimal presupuestoR = new BigDecimal("0");
			BigDecimal realR =  new BigDecimal("0");

			for (Zona_venta zona_venta : zona) {

				consulta = session.createCriteria(Recaudo.class);			
				consulta.createAlias("zonaVenta", "z");
				consulta.add(Restrictions.eq("z.id_zona_venta", zona_venta.getId_zona_venta()));
				consulta.add(Restrictions.between("fecha", fechaInicial, fechaFinal));
				recaudo = consulta.list();
				if(recaudo.size()>0){
					presupuestoR = (recaudo.get(0).getPresupuesto().toString() == null)? presupuestoR.add(new BigDecimal("0")) : presupuestoR.add(recaudo.get(0).getPresupuesto());
					realR = (recaudo.get(0).getReal().toString() == null)? realR.add(new BigDecimal("0")) :realR.add(recaudo.get(0).getReal());
					recaudo1.add(recaudo.get(0));
				}
			}
			listaTotal.add(realR);
			listaTotal.add(presupuestoR);


			return recaudo1;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}

	}	

	public Long sumaDirectorB(Date fechaInicial, Date fechaFinal){

		Session session = HibernateUtil.getSessionfactory().openSession();
		Long totalWages = null;
		try{

			Zona_ventaDao daoZ = new Zona_ventaDao();
			List<Zona_venta> lista = daoZ.buscarZonaSucursal(1);

			for (Zona_venta zona_venta : lista) {
				Criteria consulta = session.createCriteria(Recaudo.class);
				consulta.createAlias("zonaVenta", "z");
				consulta.add(Restrictions.eq("z.id_zona_venta", zona_venta.getId_zona_venta()));
				consulta.add(Restrictions.between("fecha", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("presupuesto"));
				//				totalWages1 = (Long) consulta.uniqueResult();
				//				
				//				consulta.setProjection(Projections.sum("real"));
				//				totalWages1 = (totalWages1== null)?0: totalWages1;
				//				totalWages = totalWages + totalWages1;
				//				System.out.println(totalWages);
			}

			return totalWages;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	
	//*Lista grafica Para el director Comercial Pais "dcP" "vistas /pages/dcP/vistaOficina"*//
		@SuppressWarnings("unchecked")
		public List <BigDecimal> recaudoPaisPeriodosDirectorP(int idCiudad){

			Session session = HibernateUtil.getSessionfactory().openSession();
			List <BigDecimal> listaTotal = new ArrayList<>();
			List <Recaudo> recaudo = new ArrayList<>();
			try{
				int meses = fechaFinalR();
				
				Date fechaFinal = null;
				Date fechaInicial = null;
				DateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
				Recursos recurso = new Recursos();
				int year = recurso.yearActual();
				int sucursal = recurso.idOficina(idCiudad);
				for (int i=1; i<meses; i++){
					String fecInicial = year+"/0"+i+"/01";
					fechaInicial  = formatoFecha.parse(fecInicial);
					String fecFinal = (i== 2)? year+"/0"+i+"/29": (i== 4 || i==6 || i== 9 || i== 11)? year+"/0"+i+"/30" : year+"/0"+i+"/31";
					fechaFinal = formatoFecha.parse(fecFinal);
					
					Criteria consulta = session.createCriteria(PresupuestoE.class);
					if(sucursal == 4000){
						Criterion resul =Restrictions.in("oficinaVentas", new Integer[]{4000,7000});
						consulta.add(resul);
					}
					else{
						consulta.add(Restrictions.eq("oficinaVentas", sucursal ));
						
					}
					consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
					consulta.setProjection(Projections.sum("ingresos"));
					BigDecimal valor = (BigDecimal) consulta.uniqueResult();
					valor = (valor==null)? new BigDecimal("0"):valor;
					listaTotal.add(valor);

					consulta = session.createCriteria(Detallesin.class);
					if(sucursal == 4000){
						Criterion resul =Restrictions.in("sucursal", new Integer[]{4000,7000});
						consulta.add(resul);
					}
					else{
						consulta.add(Restrictions.eq("sucursal", sucursal ));
					}
					consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
					consulta.setProjection(Projections.sum("valorNeto"));
					Long totalWages = (Long) consulta.uniqueResult();
					totalWages = (totalWages== null)?0: totalWages;
					listaTotal.add(new BigDecimal(totalWages));

					consulta = session.createCriteria(Zona_venta.class);
					consulta.createAlias("ciudad", "c");
					if(idCiudad == 3){
						Criterion resul =Restrictions.in("c.id", new Integer[]{3,6});
						consulta.add(resul);
					}
					else{
						consulta.add(Restrictions.eq("c.id", idCiudad));			
					}
					List<Zona_venta> zona = consulta.list();
					System.out.println(zona.size());
					BigDecimal presupuestoR = new BigDecimal("0");
					BigDecimal realR =  new BigDecimal("0");

					for (Zona_venta zona_venta : zona) {

						consulta = session.createCriteria(Recaudo.class);			
						consulta.createAlias("zonaVenta", "z");
						consulta.add(Restrictions.eq("z.id_zona_venta", zona_venta.getId_zona_venta()));
						consulta.add(Restrictions.between("fecha", fechaInicial, fechaFinal));
						recaudo = consulta.list();
						System.out.println(recaudo.size() +"fecha"+ fechaInicial +" --- "+ fechaFinal );
						if(recaudo.size()>0){
							presupuestoR = (recaudo.get(0).getPresupuesto().toString() == null)? presupuestoR.add(new BigDecimal("0")) : presupuestoR.add(recaudo.get(0).getPresupuesto());
							realR = (recaudo.get(0).getReal().toString() == null)? realR.add(new BigDecimal("0")) :realR.add(recaudo.get(0).getReal());
						}
					}
					listaTotal.add(realR);
					listaTotal.add(presupuestoR);

				}
				return listaTotal;

			} catch (RuntimeException ex) {
				throw ex;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				session.close();
			}
			return listaTotal;
		}

}
