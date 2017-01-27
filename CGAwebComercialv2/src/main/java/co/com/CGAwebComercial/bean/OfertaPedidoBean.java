package co.com.CGAwebComercial.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.model.chart.MeterGaugeChartModel;

import co.com.CGAwebComercial.dao.CausaPerdidaVentaDao;
import co.com.CGAwebComercial.dao.CiudadDao;
import co.com.CGAwebComercial.dao.FuncionarioDao;
import co.com.CGAwebComercial.dao.IncidenciaDao;
import co.com.CGAwebComercial.dao.LineaDao;
import co.com.CGAwebComercial.dao.OfertasPedidosDao;
import co.com.CGAwebComercial.dao.OficinaVendedorInternoDao;
import co.com.CGAwebComercial.dao.RegularidadPerdidaVentaDao;
import co.com.CGAwebComercial.dao.SucursalDao;
import co.com.CGAwebComercial.dao.Zona_FuncionarioDao;
import co.com.CGAwebComercial.dao.Zona_ventaDao;
import co.com.CGAwebComercial.entyties.CausaPerdidaVenta;
import co.com.CGAwebComercial.entyties.Ciudad;
import co.com.CGAwebComercial.entyties.Funcionario;
import co.com.CGAwebComercial.entyties.Incidencia;
import co.com.CGAwebComercial.entyties.Linea;
import co.com.CGAwebComercial.entyties.OfertasPedidos;
import co.com.CGAwebComercial.entyties.RegularidadPerdidaVenta;
import co.com.CGAwebComercial.entyties.Sucursal;
import co.com.CGAwebComercial.entyties.Zona_Funcionario;
import co.com.CGAwebComercial.entyties.Zona_venta;
import co.com.CGAwebComercial.resource.Recursos;
import co.com.CGAwebComercial.util.Fechas;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class OfertaPedidoBean implements Serializable{

	@ManagedProperty("#{autenticacionBean}")
	private AutenticacionBean autenticacion;

	private List<OfertasPedidos> listaOfertaPedidos;
	private List<OfertasPedidos> listaFiltrarOfePed;

	private Recursos recursos;
	private List<Fechas> listaFechas;
	private List<CausaPerdidaVenta> listaCausa;
	private List<RegularidadPerdidaVenta> listaRegularidad;
	
	private Incidencia incidencia1;

	private MeterGaugeChartModel meterGaugeModel;

	private BigDecimal sumaOfe;
	private BigDecimal sumaPed;
	private BigDecimal sumaPre;
	private BigDecimal sumaVal;
	private BigDecimal sumPor;
	private BigDecimal sumPorV;
	private String valorTotal;
	private String valorTotalP;
	private String valorTotalPre;
	private String valorTotalF;
	private String valorPor;
	private String valorPorV;
	private String tipo;
	private String detalleNombre;
	private String fechaConsulta;
	private String nombreCiu; 
	private int idFun;
	private int idCiudad;
	private int detalleIdFun;
	private int detalleIdFunG;
	private int funI;
	private int fun;

	public OfertaPedidoBean (){
		recursos = new Recursos();
		listaFechas = recursos.cargarFechas();
	}

	public void listarOfertaPedidos(){

		try {
			
			if(detalleIdFun > 0){
				idFun = detalleIdFun;
				tipo = "codEspecialista";
			}
			else if(detalleIdFunG > 0){
				System.out.println(autenticacion.getTipoVendedor()+ "#####");
				if(autenticacion.getTipoVendedor().equals("E") || autenticacion.getTipoVendedor().equals("")){
					idFun = detalleIdFunG;
					tipo = "codEspecialista";
				}
				else{
					idFun = detalleIdFunG;
					tipo = "codInterno";
				}
			}
			else{
				tipo = (autenticacion.getUsuarioLogin().getPerfil().getId() == 1)? "codEspecialista" : "codInterno";
				idFun = autenticacion.getUsuarioLogin().getId();
			}
			System.out.println(idFun + " Revision " + tipo);
			OfertasPedidosDao dao = new OfertasPedidosDao();
			listaOfertaPedidos = dao.listarOfertaPedidos(tipo, idFun );
			sumaOfe = new BigDecimal("0");
			sumaPed = new BigDecimal("0");
			for (OfertasPedidos of : listaOfertaPedidos) {

				sumaOfe = sumaOfe.add((of.getValorOferta() == null)? new BigDecimal("0") : of.getValorOferta()); 
				sumaPed = sumaPed.add(of.getValorPedido());
			}

			valorTotal = new DecimalFormat("###,###").format(sumaOfe);
			valorTotalP = new DecimalFormat("###,###").format(sumaPed);			

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error No se cargo la lista de Ofertas Especialista");
		}
	}

	public void alistarPedidosLlamadas(){

		try{
			idCiudad = 0;
			listarOfertaPedidosOficina();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error No se cargo el inicio de la vista llamadas");
		}
	}

	@SuppressWarnings("rawtypes")
	public void listarOfertaPedidosOficina(){

		try{
			if(fechaConsulta == null){

				Calendar fechas = Calendar.getInstance();
				int month = fechas.get(Calendar.MONTH)+1;
				for (Fechas fecha: listaFechas) {
					fechaConsulta  = (month<10)?(fecha.getValorMes().equals(String.valueOf("0"+month)))? fecha.getMes():fechaConsulta:(fecha.getValorMes().equals(String.valueOf(""+month)))? fecha.getMes(): fechaConsulta;
				}
			}	
			listaOfertaPedidos = new ArrayList<>();
			sumaOfe = new BigDecimal("0");
			sumaPed = new BigDecimal("0");
			sumaPre = new BigDecimal("0");
			sumaVal = new BigDecimal("0");
			if(idCiudad <= 0){

				Zona_FuncionarioDao daoF = new Zona_FuncionarioDao();
				Zona_Funcionario zonaF = daoF.buscarFuncionarioZona(autenticacion.getUsuarioLogin().getId());

				Recursos recurso = new Recursos();			
				idCiudad = recurso.idOficina(zonaF.getCiudad().getId());
				nombreCiu = zonaF.getCiudad().getNombre();
				tipo =(autenticacion.getUsuarioLogin().getPerfil().getId() >= 15 || autenticacion.getUsuarioLogin().getPerfil().getId() <= 19)? "codInterno" : "codEspecialista";
			}
			else{
				tipo = "codEspecialista";
			}

			if(autenticacion.getUsuarioLogin().getPerfil().getId() >= 15 || autenticacion.getUsuarioLogin().getPerfil().getId() <= 19){
				OficinaVendedorInternoDao daoF = new OficinaVendedorInternoDao();

				List<Object[]> results1 =  daoF.listaVenInt(idCiudad);	
				OfertasPedidosDao daoOF = new OfertasPedidosDao();
				for (Object[] objects : results1) {			

					Integer valor = (Integer) objects[1];
					if(valor != 1){
						OfertasPedidos ofePed = daoOF.listarOfertaPedidosOficinaLlamadas(tipo, (Integer) objects[0], (Integer) objects[1] );
						if (ofePed.getValorOferta() != null){
							sumaOfe = sumaOfe.add(ofePed.getValorOferta());
							sumaPed = sumaPed.add(ofePed.getValorPedido());
							sumaPre = sumaPre.add(new BigDecimal(ofePed.getPresupuesto().toString()));
							sumaVal = sumaVal.add(new BigDecimal(ofePed.getValorNeto().toString()));
							listaOfertaPedidos.add(ofePed);
						}
					}
				}
			}
			else{				
				OfertasPedidosDao daoOf = new OfertasPedidosDao();
				List results = daoOf.buscarEspecialista(tipo, idCiudad);

				for (Iterator iterator = results.iterator(); iterator.hasNext();) {
					Object object = (Object) iterator.next();
					int d = (int) object;
					OfertasPedidos ofePed = new OfertasPedidos();
					List<Object[]> results1 =  daoOf.listarOfertaPedidosOficina(tipo, d);				
					for (Object[] objects : results1) {

						ofePed.setCodEspecialista((Integer)objects[0]);
						ofePed.setEspecialista((String) objects[1]);
						ofePed.setValorOferta(new BigDecimal(objects[2].toString()));
						ofePed.setValorPedido(new BigDecimal(objects[3].toString()));
						ofePed.setPorcentaje((ofePed.getValorOferta().intValue() == 0 || ofePed.getValorPedido().intValue() == 0)? new BigDecimal("0") : ofePed.getValorPedido().divide(ofePed.getValorOferta(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
						ofePed.setImagen((ofePed.getPorcentaje().intValue() < 81)? "rojo.png" :(ofePed.getPorcentaje().intValue() >= 81 && ofePed.getPorcentaje().intValue() <= 95 )? "amarillo.jpg" : (ofePed.getPorcentaje().intValue() >= 96  && ofePed.getPorcentaje().intValue() <= 100)? "verde.png": "violeta.jpg");
					}
					sumaOfe = sumaOfe.add(ofePed.getValorOferta());
					sumaPed = sumaPed.add(ofePed.getValorPedido());
					listaOfertaPedidos.add(ofePed);
				}
			}
			valorTotal = new DecimalFormat("###,###").format(sumaOfe);
			valorTotalP = new DecimalFormat("###,###").format(sumaPed);
			sumPor = sumaPed.divide(sumaOfe, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			valorPor =  new DecimalFormat("###,###").format(sumPor);
			
			valorTotalPre = (sumaPre == null)?  new DecimalFormat("###,###").format(0) : new DecimalFormat("###,###").format(sumaPre);
			valorTotalF = (sumaVal == null)?  new DecimalFormat("###,###").format(0) :new DecimalFormat("###,###").format(sumaVal);
			sumPorV = (sumaVal.intValue() ==0 || sumaPre.intValue()==0)? new BigDecimal("0") :sumaVal.divide(sumaPre, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			valorPorV =  new DecimalFormat("###,###").format(sumPorV);
			
			if(idCiudad != 0){
				crearGraficaOf(sumPor.intValue());
			}


		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error No se cargo la lista de Oferta Oficina");
		}
	}
	
	public void listarOfertaPedidosOficina1(){
		
		try{
			listaOfertaPedidos = new ArrayList<>();
			sumaOfe = new BigDecimal("0");
			sumaPed = new BigDecimal("0");
			sumaPre = new BigDecimal("0");
			sumaVal = new BigDecimal("0");
			
			if(autenticacion.getTipoVendedor().equals("E") || autenticacion.getTipoVendedor().equals("")){
				tipo = "codEspecialista";
				Zona_FuncionarioDao daoF = new Zona_FuncionarioDao();
				Zona_Funcionario zonaF = daoF.buscarFuncionarioZona(autenticacion.getUsuarioLogin().getId());
				
				Recursos recurso = new Recursos();			
				idCiudad = recurso.idOficina(zonaF.getCiudad().getId());
				nombreCiu = zonaF.getCiudad().getNombre();
				
				Zona_ventaDao daoZ = new Zona_ventaDao();
				List<Zona_venta> listaZona = daoZ.buscarZonaSucursal(zonaF.getCiudad().getId());
				
				OfertasPedidosDao daoOF = new OfertasPedidosDao();
				for (Zona_venta zona : listaZona) {
					
					if(zona.getFuncionario().getId_funcionario() != 1){
						
						OfertasPedidos ofePed = daoOF.listarOfertaPedidosOficinaLlamadas(tipo, zona.getFuncionario().getId_funcionario(), 0 );
						if (ofePed.getValorOferta() != null){
							sumaOfe = sumaOfe.add(ofePed.getValorOferta());
							sumaPed = sumaPed.add(ofePed.getValorPedido());
							System.out.println(ofePed.getPresupuesto() + "*****");
							sumaPre = sumaPre.add(new BigDecimal(ofePed.getPresupuesto().toString()));
//							sumaVal = sumaVal.add(new BigDecimal(ofePed.getValorNeto().toString()));
							listaOfertaPedidos.add(ofePed);
						}
					}
				}
				
			}
			else{
				tipo = "codInterno";
				
				Zona_FuncionarioDao daoF = new Zona_FuncionarioDao();
				Zona_Funcionario zonaF = daoF.buscarFuncionarioZona(autenticacion.getUsuarioLogin().getId());

				Recursos recurso = new Recursos();			
				idCiudad = recurso.idOficina(zonaF.getCiudad().getId());
				nombreCiu = zonaF.getCiudad().getNombre();
				
				OficinaVendedorInternoDao daoOf = new OficinaVendedorInternoDao();

				List<Object[]> results1 =  daoOf.listaVenInt(idCiudad);	
				OfertasPedidosDao daoOF = new OfertasPedidosDao();
				for (Object[] objects : results1) {			

					Integer valor = (Integer) objects[1];
					if(valor != 1){
						OfertasPedidos ofePed = daoOF.listarOfertaPedidosOficinaLlamadas(tipo, (Integer) objects[0], (Integer) objects[1] );
						if (ofePed.getValorOferta() != null){
							sumaOfe = sumaOfe.add(ofePed.getValorOferta());
							sumaPed = sumaPed.add(ofePed.getValorPedido());
							sumaPre = sumaPre.add(new BigDecimal(ofePed.getPresupuesto().toString()));
							sumaVal = sumaVal.add(new BigDecimal(ofePed.getValorNeto().toString()));
							listaOfertaPedidos.add(ofePed);
						}
					}
				}
			}
			
			BigDecimal sumaO = new BigDecimal("0");
			BigDecimal sumaT = new BigDecimal("0");
			
			for (OfertasPedidos of : listaOfertaPedidos) {
				sumaO = sumaO.add(of.getValorOferta());
				sumaT = sumaT.add(of.getValorPedido());
			}
			valorTotal = new DecimalFormat("###,###").format(sumaO);
			valorTotalP = new DecimalFormat("###,###").format(sumaT);
			
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error No se cargo la lista de Oferta Oficina");
		}
	}

	public void listarOfertaPedidosPais(){

		try{
			listaOfertaPedidos = new ArrayList<>();
			sumaOfe = new BigDecimal("0");
			sumaPed = new BigDecimal("0");

			CiudadDao daoC = new CiudadDao();
			List<Ciudad> listaCiudad = daoC.listar();

			OfertasPedidosDao daoF = new OfertasPedidosDao();

			for (Ciudad ciudad : listaCiudad) {
				Recursos recurso = new Recursos();	
				OfertasPedidos ofePed = new OfertasPedidos();
				int oficina = recurso.idOficina(ciudad.getId());
				Object[]  objects = daoF.listarOfertaPedidosPais(oficina);
				
				ofePed.setOficina((String) objects[0]);
				ofePed.setValorOferta(new BigDecimal(objects[1].toString()));
				ofePed.setValorPedido(new BigDecimal(objects[2].toString()));
				ofePed.setCodOficina((int) objects[3]);
				ofePed.setPorcentaje(ofePed.getValorOferta().divide(ofePed.getValorPedido(), 4, BigDecimal.ROUND_HALF_UP));
				ofePed.setImagen((ofePed.getPorcentaje().intValue() < 81)? "rojo.png" :(ofePed.getPorcentaje().intValue() >= 81 && ofePed.getPorcentaje().intValue() <= 95 )? "amarillo.jpg" : (ofePed.getPorcentaje().intValue() >= 96  && ofePed.getPorcentaje().intValue() <= 100)? "verde.png": "violeta.jpg");

				sumaOfe = sumaOfe.add(ofePed.getValorOferta());
				sumaPed = sumaPed.add(ofePed.getValorPedido());
				sumaPre = sumaPre.add(new BigDecimal(ofePed.getPresupuesto()));
				sumaVal = sumaVal.add(new BigDecimal(ofePed.getValorNeto()));
				listaOfertaPedidos.add(ofePed);
			}
			valorTotal = new DecimalFormat("###,###").format(sumaOfe);
			valorTotalP = new DecimalFormat("###,###").format(sumaPed);
			valorTotalPre = new DecimalFormat("###,###").format(sumaPre);
			valorTotalF = new DecimalFormat("###,###").format(sumaVal);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error No se cargo la lista de Ofertas Pais");
		}
	}

	//*Se  crea la grafica para Gestion de llamadas*//
	public void crearGraficaOf(int V){

		try{
			Recursos recurso = new Recursos();
			String mesActual = recurso.mesActualG();
			List<Number> interval = new ArrayList<Number>(){{
				add(30);
				add(60);
				add(100);
			}};
			interval.add(3,(V >100)? V:100);

			meterGaugeModel = new MeterGaugeChartModel(V, interval);
			meterGaugeModel.setTitle("Cumplimiento " + mesActual + " Ofertas y Pedidos" );
			meterGaugeModel.setGaugeLabel( V +"%");
			meterGaugeModel.setSeriesColors("ff0000, ffff00, 009933 ");

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error No se realizo la Gráfica de Gestion de llamadas");
		}
	}

	public String cambioPagina(){

		try{
			try {
				Thread.sleep(4000);
				Faces.redirect("./pages/of/ofertaPedido.xhtml");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Faces.redirect("./pages/of/ofertaPedido.xhtml");
			return "of/ofertaPedido.xhtml?faces-redirect=true";
		} catch (RuntimeException | IOException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error No se realizo la Gráfica de Gestion de llamadas");
		}
		return null;
	}

	public String cambioPagina1(){

		try{
			try {
				Thread.sleep(4000);
				Faces.redirect("./pages/of/ofertaPedido2.xhtml");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Faces.redirect("./pages/of/ofertaPedido2.xhtml");
			return "of/ofertaPedido2.xhtml?faces-redirect=true";
		} catch (RuntimeException | IOException  ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error No se realizo la Gráfica de Gestion de llamadas");
		}
		return null;
	}

	public void salvarIncidencia(OfertasPedidos  ofe, int porcentaje)  {

		try{
			
			String tipo = (autenticacion.getUsuarioLogin().getPerfil().getId() == 1)? "funcionario" : "funcionarioI";
			
			OfertasPedidosDao dao = new OfertasPedidosDao();
			OfertasPedidos oferta = new OfertasPedidos();
			IncidenciaDao daoI = new IncidenciaDao();
			Incidencia incidencia = new Incidencia();
			List<OfertasPedidos> listaOferta = dao.listaBusquedaOferta(ofe.getnOferta());
			Long sumaT = (long) 0;
			for (OfertasPedidos oferta1 : listaOferta) {
				oferta1.setOportunidadNeg(porcentaje);
				dao.merge(oferta1);
				sumaT = sumaT + oferta1.getValorOferta().longValue();
				oferta = oferta1;
				
			}
			
			incidencia.setCliente(oferta.getCliente());
			incidencia.setFechaRegistro(new Date());
			incidencia.setProbabilidadConNeg(porcentaje);
			incidencia.setResultaNegocio("SI");
			incidencia.setValorVenta(sumaT);
			CausaPerdidaVentaDao daoC = new CausaPerdidaVentaDao();
			CausaPerdidaVenta causaP = daoC.buscar(69);
			incidencia.setCausaPerdida(causaP);
			
			//*Interno*//
			if(tipo.equals("funcionarioI")){
				OficinaVendedorInternoDao daoOVI = new OficinaVendedorInternoDao();
				int idSuc = daoOVI.ciudadInterno(idFun); 
				SucursalDao daoS = new SucursalDao();
				Sucursal sucursal = daoS.buscar(idSuc);
				incidencia.setCiudad(sucursal.getCiudad());
			}
			//*Interno*// 
			else{
				Zona_ventaDao daoZ = new Zona_ventaDao();
				List<Zona_venta> zona = daoZ.buscarZona(idFun);
				incidencia.setCiudad(zona.get(0).getCiudad());				
			}
			FuncionarioDao daoF = new FuncionarioDao();
			Funcionario funcionarioI = daoF.buscar(oferta.getCodInterno());
			incidencia.setFuncionarioI(funcionarioI); 
			
			Funcionario funcionario = daoF.buscar(oferta.getCodEspecialista());
			incidencia.setFuncionario(funcionario);
			
			//*Linea Falta linea en Ofertas y Pedidos se coloca una temporal*//
			LineaDao daoL = new LineaDao();
			Linea linea = daoL.buscar(1);				
			incidencia.setLinea(linea);
			//**//
			
			RegularidadPerdidaVentaDao daoR = new RegularidadPerdidaVentaDao();
			RegularidadPerdidaVenta regularidad = daoR.buscar(8);
			incidencia.setRegularidad(regularidad);
			
			incidencia.setNoferta(ofe.getnOferta());
			
			daoI.salvar(incidencia);

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " +ofe.getnOferta() + ", New:" + porcentaje);
			FacesContext.getCurrentInstance().addMessage(null, msg);


		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error No se realizo la actualización de la oferta");
		}
	}
	
	
	public void editarIncidenciaPerdida(OfertasPedidos  ofe)  {

		try{
			
			
			
			//daoI.salvar(incidencia);

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " +ofe.getnOferta() + ", New:" );
			FacesContext.getCurrentInstance().addMessage(null, msg);


		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error No se realizo la actualización de la oferta");
		}
	}
	
	public void cargaDialogo(OfertasPedidos ofe){
		
		try{
			String tipo = (autenticacion.getUsuarioLogin().getPerfil().getId() == 1)? "funcionario" : "funcionarioI";
			OfertasPedidosDao dao = new OfertasPedidosDao();
			OfertasPedidos oferta = new OfertasPedidos();
			
			incidencia1 = new Incidencia();
			List<OfertasPedidos> listaOferta = dao.listaBusquedaOferta(ofe.getnOferta());
			Long sumaT = (long) 0;
			for (OfertasPedidos oferta1 : listaOferta) {
				//oferta1.setOportunidadNeg(porcentaje);
				//dao.merge(oferta1);
				sumaT = sumaT + oferta1.getValorOferta().longValue();
				oferta = oferta1;
				
			}
			
			incidencia1.setCliente(oferta.getCliente());
			incidencia1.setFechaRegistro(new Date());
			incidencia1.setProbabilidadConNeg(0);
			incidencia1.setResultaNegocio("NO");
			incidencia1.setValorVenta(sumaT);
//			CausaPerdidaVentaDao daoC = new CausaPerdidaVentaDao();
//			CausaPerdidaVenta causaP = daoC.buscar(69);
//			incidencia.setCausaPerdida(causaP);
//			
			//*Interno*//
			if(tipo.equals("funcionarioI")){
				OficinaVendedorInternoDao daoOVI = new OficinaVendedorInternoDao();
				int idSuc = daoOVI.ciudadInterno(idFun); 
				SucursalDao daoS = new SucursalDao();
				Sucursal sucursal = daoS.buscar(idSuc);
				incidencia1.setCiudad(sucursal.getCiudad());
			}
			//*Interno*// 
			else{
				Zona_ventaDao daoZ = new Zona_ventaDao();
				List<Zona_venta> zona = daoZ.buscarZona(idFun);
				incidencia1.setCiudad(zona.get(0).getCiudad());				
			} 
			
			FuncionarioDao daoF = new FuncionarioDao();
			Funcionario funcionarioI = daoF.buscar(oferta.getCodInterno());
			incidencia1.setFuncionarioI(funcionarioI); 
			
			Funcionario funcionario = daoF.buscar(oferta.getCodEspecialista());
			incidencia1.setFuncionario(funcionario);
			
			//*Linea Falta linea en Ofertas y Pedidos se coloca una temporal*//
			LineaDao daoL = new LineaDao();
			Linea linea = daoL.buscar(1);				
			incidencia1.setLinea(linea);
			//**//
			
//			RegularidadPerdidaVentaDao daoR = new RegularidadPerdidaVentaDao();
//			RegularidadPerdidaVenta regularidad = daoR.buscar(8);
//			incidencia.setRegularidad(regularidad);
			
			incidencia1.setNoferta(ofe.getnOferta());
			
			RegularidadPerdidaVentaDao daoR = new RegularidadPerdidaVentaDao();
			listaRegularidad = daoR.listar();
			
			CausaPerdidaVentaDao daoC = new CausaPerdidaVentaDao();
			listaCausa = daoC.listar();
			
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error No se Cargo el dialogo");
		}
		
	}
	
	
	public void salvarIncidenciaPerdidos(){
		
		try{
			OfertasPedidosDao dao = new OfertasPedidosDao();
			List<OfertasPedidos> listaOferta = dao.listaBusquedaOferta(incidencia1.getNoferta());
			for (OfertasPedidos oferta1 : listaOferta) {
				oferta1.setOfertaPerdida(1);
				dao.merge(oferta1);
			}
			
			IncidenciaDao daoI = new IncidenciaDao();
			daoI.salvar(incidencia1);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error No se Salvo la oferta Perdida");
		}
	}
	
	public AutenticacionBean getAutenticacion() {
		return autenticacion;
	}

	public void setAutenticacion(AutenticacionBean autenticacion) {
		this.autenticacion = autenticacion;
	}

	public List<OfertasPedidos> getListaOfertaPedidos() {
		return listaOfertaPedidos;
	}

	public void setListaOfertaPedidos(List<OfertasPedidos> listaOfertaPedidos) {
		this.listaOfertaPedidos = listaOfertaPedidos;
	}

	public List<OfertasPedidos> getListaFiltrarOfePed() {
		return listaFiltrarOfePed;
	}

	public void setListaFiltrarOfePed(List<OfertasPedidos> listaFiltrarOfePed) {
		this.listaFiltrarOfePed = listaFiltrarOfePed;
	}

	public String getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(String valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getIdFun() {
		return idFun;
	}

	public void setIdFun(int idFun) {
		this.idFun = idFun;
	}

	public BigDecimal getSumaOfe() {
		return sumaOfe;
	}

	public void setSumaOfe(BigDecimal sumaOfe) {
		this.sumaOfe = sumaOfe;
	}

	public BigDecimal getSumaPed() {
		return sumaPed;
	}

	public void setSumaPed(BigDecimal sumaPed) {
		this.sumaPed = sumaPed;
	}

	public String getValorTotalP() {
		return valorTotalP;
	}

	public void setValorTotalP(String valorTotalP) {
		this.valorTotalP = valorTotalP;
	}

	public int getDetalleIdFun() {
		return detalleIdFun;
	}

	public void setDetalleIdFun(int detalleIdFun) {
		this.detalleIdFun = detalleIdFun;
	}

	public String getDetalleNombre() {
		return detalleNombre;
	}

	public void setDetalleNombre(String detalleNombre) {
		this.detalleNombre = detalleNombre;
	}

	public int getIdCiudad() {
		return idCiudad;
	}

	public void setIdCiudad(int idCiudad) {
		this.idCiudad = idCiudad;
	}

	public int getDetalleIdFunG() {
		return detalleIdFunG;
	}

	public void setDetalleIdFunG(int detalleIdFunG) {
		this.detalleIdFunG = detalleIdFunG;
	}

	public Recursos getRecursos() {
		return recursos;
	}

	public void setRecursos(Recursos recursos) {
		this.recursos = recursos;
	}

	public List<Fechas> getListaFechas() {
		return listaFechas;
	}

	public void setListaFechas(List<Fechas> listaFechas) {
		this.listaFechas = listaFechas;
	}

	public String getFechaConsulta() {
		return fechaConsulta;
	}

	public void setFechaConsulta(String fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}

	public BigDecimal getSumaPre() {
		return sumaPre;
	}

	public void setSumaPre(BigDecimal sumaPre) {
		this.sumaPre = sumaPre;
	}

	public BigDecimal getSumaVal() {
		return sumaVal;
	}

	public void setSumaVal(BigDecimal sumaVal) {
		this.sumaVal = sumaVal;
	}

	public String getValorTotalPre() {
		return valorTotalPre;
	}

	public void setValorTotalPre(String valorTotalPre) {
		this.valorTotalPre = valorTotalPre;
	}

	public String getValorTotalF() {
		return valorTotalF;
	}

	public void setValorTotalF(String valorTotalF) {
		this.valorTotalF = valorTotalF;
	}

	public BigDecimal getSumPor() {
		return sumPor;
	}

	public void setSumPor(BigDecimal sumPor) {
		this.sumPor = sumPor;
	}

	public BigDecimal getSumPorV() {
		return sumPorV;
	}

	public void setSumPorV(BigDecimal sumPorV) {
		this.sumPorV = sumPorV;
	}

	public String getValorPor() {
		return valorPor;
	}

	public void setValorPor(String valorPor) {
		this.valorPor = valorPor;
	}

	public String getValorPorV() {
		return valorPorV;
	}

	public void setValorPorV(String valorPorV) {
		this.valorPorV = valorPorV;
	}

	public MeterGaugeChartModel getMeterGaugeModel() {
		return meterGaugeModel;
	}

	public void setMeterGaugeModel(MeterGaugeChartModel meterGaugeModel) {
		this.meterGaugeModel = meterGaugeModel;
	}

	public String getNombreCiu() {
		return nombreCiu;
	}

	public void setNombreCiu(String nombreCiu) {
		this.nombreCiu = nombreCiu;
	}

	public int getFunI() {
		return funI;
	}

	public void setFunI(int funI) {
		this.funI = funI;
	}

	public int getFun() {
		return fun;
	}

	public void setFun(int fun) {
		this.fun = fun;
	}

	public List<CausaPerdidaVenta> getListaCausa() {
		return listaCausa;
	}

	public void setListaCausa(List<CausaPerdidaVenta> listaCausa) {
		this.listaCausa = listaCausa;
	}

	public List<RegularidadPerdidaVenta> getListaRegularidad() {
		return listaRegularidad;
	}

	public void setListaRegularidad(List<RegularidadPerdidaVenta> listaRegularidad) {
		this.listaRegularidad = listaRegularidad;
	}

	public Incidencia getIncidencia1() {
		return incidencia1;
	}

	public void setIncidencia1(Incidencia incidencia1) {
		this.incidencia1 = incidencia1;
	}
}
