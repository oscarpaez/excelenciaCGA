package co.com.CGAwebComercial.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import co.com.CGAwebComercial.dao.CiudadDao;
import co.com.CGAwebComercial.dao.DetalleDao;
import co.com.CGAwebComercial.dao.EsquemasDao;
import co.com.CGAwebComercial.dao.FuncionarioDao;
import co.com.CGAwebComercial.dao.LineaDao;
import co.com.CGAwebComercial.dao.OficinaVendedorInternoDao;
import co.com.CGAwebComercial.dao.PresupuestoDao;
import co.com.CGAwebComercial.dao.RecaudoDao;
import co.com.CGAwebComercial.dao.Zona_FuncionarioDao;
import co.com.CGAwebComercial.dao.Zona_ventaDao;
import co.com.CGAwebComercial.entyties.Detalle;
import co.com.CGAwebComercial.entyties.Esquemas;
import co.com.CGAwebComercial.entyties.Funcionario;
import co.com.CGAwebComercial.entyties.OficinaVendedorInterno;
import co.com.CGAwebComercial.entyties.Plan;
import co.com.CGAwebComercial.entyties.Recaudo;
import co.com.CGAwebComercial.entyties.Zona_Funcionario;
import co.com.CGAwebComercial.entyties.Zona_venta;
import co.com.CGAwebComercial.resource.Recursos;
import co.com.CGAwebComercial.util.ComisionVendedores;
import co.com.CGAwebComercial.util.Fechas;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class directorGBean implements Serializable{

	@ManagedProperty("#{autenticacionBean}")
	private AutenticacionBean autenticacion;

	private Recursos recurso;

	private List<ComisionVendedores> ListaComisionGerente;
	private List<ComisionVendedores> ListaComisionVendedores;
	private List<ComisionVendedores> listaFiltrados;
	private List<Detalle> listadetalle;
	private List<Plan> listaplanV;	
	private List<Fechas> listaFechas;
	private List<Fechas> listaFechasR;

	private String tipo;
	private String fechaBusqueda;
	private String fechaBusquedaYear;
	private String tablaFun;
	private String tablaLin;
	private String totalPre;
	private String totalRe;
	private String totalReA;
	private String utilidad;
	private String fechaConsulta;
	private String nombreVendedor;
	private String nombreCiudad;
	
	private BigDecimal total;
	private BigDecimal totalR;

	private int cod;
	private int idFuncionario;
	private int idLinea;
	private int idCiudad;
	private Integer progress=0;

	public directorGBean() {
		recurso = new Recursos();
		listaFechas = recurso.cargarFechas();
		listaFechasR = recurso.cargarFechasTotal();
		tablaFun = "E";
		tablaLin = "false";
	}


	public void listarComisionGerente(){

		try{
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			ListaComisionGerente = new ArrayList<>();
			List<Funcionario> listaVendedor = new ArrayList<>();
			FuncionarioDao daoF = new FuncionarioDao();
			Funcionario funcionario = daoF.buscarPersona(autenticacion.getUsuarioLogin().getPersona().getCedula());

			Zona_FuncionarioDao daoZ = new Zona_FuncionarioDao();
			Zona_Funcionario zonaF = daoZ.buscarFuncionarioZona(funcionario.getId_funcionario());
			
			Zona_ventaDao daoV = new Zona_ventaDao();
			List<Zona_venta> ListaZona = daoV.buscarZonaSucursal(zonaF.getCiudad().getId() );
			listaVendedor  = daoF.listarVendedoresParaDirector(ListaZona);
			tipo="funcionario";
			ComisionVendedores comisionDir = new ComisionVendedores();
			DetalleDao daoD = new DetalleDao();
			int sucursal = (zonaF.getCiudad().getId() == 1)? zonaF.getCiudad().getId() * 1000 : (zonaF.getCiudad().getId()+1)*1000;
			for (Funcionario fun : listaVendedor) {
				List<Long> listFun  = daoD.sumaDirector(tipo, fun.getId_funcionario(), fechaBusqueda, fechaBusquedaYear, sucursal);
				comisionDir.setIngresoReal(comisionDir.getIngresoReal() + listFun.get(0).intValue());
				comisionDir.setPresupuesto(comisionDir.getPresupuesto() + listFun.get(1).intValue());
			}
			double v = comisionDir.getPresupuesto();
			double f = comisionDir.getIngresoReal();
			BigDecimal valorReal = new BigDecimal("0.00");
			comisionDir.setCumplimiento(new BigDecimal(v));
			//valorReal =  new BigDecimal(v/f).multiply(new BigDecimal("100"));
			valorReal = comisionDir.getCumplimiento().divide( new BigDecimal(f), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			//comisionDir.setCumplimiento(new BigDecimal(comisionDir.getPresupuesto()/comisionDir.getIngresoReal()), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
			comisionDir.setCumplimiento(valorReal);
			comisionDir.setCedula(funcionario.getPersona().getCedula());
			comisionDir.setId(funcionario.getId_funcionario());
			comisionDir.setNombre(funcionario.getPersona().getNombre());
			comisionDir.setUmbralCV(new BigDecimal("0.85"));
			String imagen = ((comisionDir.getPresupuesto()/comisionDir.getIngresoReal())*100 >= (comisionDir.getUmbralCV().intValue()*100))?"verde.png" : "rojo.png";
			comisionDir.setImagen(imagen);
			ListaComisionGerente.add(comisionDir);


		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo el Mix de linea del  Director.");
		}
	}

	public void listarCumplimientoZonas(){

		try{
			ListaComisionGerente = new ArrayList<>();
			ComisionVendedores comisionDir = new ComisionVendedores();
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
//			FuncionarioDao daoF = new FuncionarioDao();
//			Funcionario funcionario = daoF.buscarPersona(autenticacion.getUsuarioLogin().getPersona().getCedula());

			PresupuestoDao daoP = new PresupuestoDao();
			//BigDecimal linea = daoP.datoPorLineaSumFechas(funcionario.getId_funcionario() , fechaBusqueda, fechaBusquedaYear);
//			BigDecimal linea = daoP.datoPorLineaSumFechas(2516 , fechaBusqueda, fechaBusquedaYear);
			//comisionDir.setPresupuestoB(daoP.datoPorLineaSumFechas(funcionario.getId_funcionario() , fechaBusqueda, fechaBusquedaYear));
			comisionDir.setPresupuestoB(daoP.datoPorLineaSumFechas(2516, fechaBusqueda, fechaBusquedaYear));
			DetalleDao daoD = new DetalleDao();
			//Long valorNeto = daoD.sumaLineaFuncionario(funcionario.getId_funcionario() , fechaBusqueda, fechaBusquedaYear);
			Long valorNeto = daoD.sumaLineaFuncionario(2516 , fechaBusqueda, fechaBusquedaYear);
			comisionDir.setIngresoRealB(new BigDecimal(valorNeto));

			comisionDir.setCumplimiento(comisionDir.getIngresoRealB().divide(comisionDir.getPresupuestoB(),2, BigDecimal.ROUND_HALF_UP));
			comisionDir.setIngresoReal(comisionDir.getPresupuestoB().intValue());
			comisionDir.setPresupuesto(comisionDir.getIngresoRealB().intValue());
			comisionDir.setUmbralCV(new BigDecimal("0.85"));
			String imagen = ((comisionDir.getPresupuestoB().intValue()/comisionDir.getIngresoRealB().intValue())*100 >= (comisionDir.getUmbralCV().intValue()*100))?"verde.png" : "rojo.png";
			comisionDir.setImagen(imagen);
			//int valN = (valorNeto == null)? valN = 0 :(int) (long) valorNeto; 
			ListaComisionGerente.add(comisionDir);


		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo el cumplimiento de Zonas del  Director.");
		}
	}

	public void listarRecaudoDirector(){

		try{
			for (Fechas fecha: listaFechas) {
				fechaConsulta = (fecha.getValorMes().equals(autenticacion.getFechaBusqueda()))? fecha.getMes() : fechaConsulta;
			}
			ListaComisionGerente = new ArrayList<>();
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			FuncionarioDao daoF = new FuncionarioDao();
			Funcionario funcionario = daoF.buscarPersona(autenticacion.getUsuarioLogin().getPersona().getCedula());

			Zona_FuncionarioDao daoZ = new Zona_FuncionarioDao();
			Zona_Funcionario zonaF = daoZ.buscarFuncionarioZona(funcionario.getId_funcionario());

			CiudadDao daoC = new CiudadDao();
			ListaComisionGerente = daoC.listarOficinasDirectores(zonaF.getCiudad().getId(), fechaBusqueda, fechaBusquedaYear);


		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo el Recaudo de cartera del Director.");
		}
	}
	
	public void inicioVistaDirL(){

		try{

			if(fechaConsulta == null){

				Calendar fechas = Calendar.getInstance();
				int month = fechas.get(Calendar.MONTH)+1;
				for (Fechas fecha: listaFechas) {
					System.out.println(fecha.getValorMes() + "/////////////////" + month);
					fechaConsulta  = (month<10)?(fecha.getValorMes().equals(String.valueOf("0"+month)))? fecha.getMes():fechaConsulta:(fecha.getValorMes().equals(String.valueOf(""+month)))? fecha.getMes(): fechaConsulta;
				}
				System.out.println(fechaConsulta + "$$$$$$$$$$");
				listarVendedoresLinea();
			}

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo La vista de Inicio");
		}
	}


	//*Lista Vendedores para el director Linea Nacional "DL DA" "vista /dl/plandl" *//
	public void listarVendedoresLinea(){
		
		try{
			for (Fechas fecha: listaFechas) {
				fechaConsulta = (fecha.getValorMes().equals(autenticacion.getFechaBusqueda()))? fecha.getMes() : fechaConsulta;
			}
			ListaComisionVendedores = new ArrayList<>();
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			int t = 0;
			int progress1 = 0;
			FuncionarioDao daoF = new FuncionarioDao();
			List<Funcionario> listaVendedor = new ArrayList<>();
			List<Integer> listaVendedores = new ArrayList<>();
			BigDecimal totalPresupuesto = new BigDecimal("0");
			BigDecimal totalReal = new BigDecimal("0");
			if(autenticacion.getTipoVendedor().equals("I") ){
				listaVendedor = daoF.listarVendedoresInternos();
				tipo="funcionarioI";
				tablaFun = "I";
				idLinea = (autenticacion.getUsuarioLogin().getPerfil().getId() == 2 )? 1 : 6;
				listaVendedores =  daoF.listarVendedoresDirectorLinea(idLinea, tipo, fechaBusqueda, fechaBusquedaYear);
			}
			else{
				listaVendedor = daoF.listarVendedores();
				tipo="funcionario";
				tablaFun = "E";
				idLinea = (autenticacion.getUsuarioLogin().getPerfil().getId() == 2 )? 1 : 6;
				listaVendedores =  daoF.listarVendedoresDirectorLinea(idLinea, tipo, fechaBusqueda, fechaBusquedaYear);
			}
			
			DetalleDao daoD = new DetalleDao();
			progress1 = 100 /listaVendedor.size();
			for (Integer idFun : listaVendedores) {
				ComisionVendedores vendedores = new ComisionVendedores();
				vendedores = daoD.listarPlanLinea(idLinea, tipo, idFun, fechaBusqueda, fechaBusquedaYear);
				Funcionario funcionario = daoF.buscar(idFun);

				if(funcionario != null){
					vendedores.setCedula(funcionario.getPersona().getCedula());
					vendedores.setNombre(funcionario.getPersona().getNombre());
					vendedores.setId(funcionario.getId_funcionario());
					vendedores.setTipo(funcionario.getDescripcion());
                }	
				else{
//					sucursales.setCumplimiento(new BigDecimal("0.00"));
//					sucursales.setCumplimientoU(new BigDecimal("0.00"));
//					sucursales.setImagen1("rojo.png");
//					sucursales.setImagen("rojo.png");
					List<Detalle> detalle = daoD.fucionarioPais(tipo, idFun);
					
					if(tipo.equals("funcionario")){
						if(detalle.get(0).getCedulaEspecialista() == 0){
							vendedores.setCedula(2);
						}
						else{
							vendedores.setCedula(detalle.get(0).getCedulaEspecialista());
						}
						vendedores.setNombre(detalle.get(0).getNombreEspecialista());
					}
					else{
						vendedores.setCedula(detalle.get(0).getCedulaVendedorInterno());
						vendedores.setNombre(detalle.get(0).getNombreVendedorInt());
					}
					vendedores.setId(idFun);
					vendedores.setTipo("No especialista");
					
			   }
				ListaComisionVendedores.add(vendedores);
				
				totalPresupuesto = totalPresupuesto.add(vendedores.getPresupuestoB());
				totalReal = totalReal.add(vendedores.getIngresoRealB());
				
				t=progress1;
				setProgress(t);
			}	
//			for (Funcionario fun : listaVendedor) {
//				
//				ComisionVendedores vendedores = new ComisionVendedores();
//				vendedores = daoD.listarPlanLinea(idLinea, tipo, fun.getId_funcionario(), fechaBusqueda, fechaBusquedaYear);
//				vendedores.setId(fun.getId_funcionario());
//				vendedores.setCedula(fun.getPersona().getCedula());
//				vendedores.setUmbralCV(new BigDecimal("85"));
//				vendedores.setNombre(fun.getPersona().getNombre());
//				vendedores.setTipo(fun.getComision().getNombre());
//				ListaComisionVendedores.add(vendedores);
//				
//				totalPresupuesto = totalPresupuesto.add(vendedores.getPresupuestoB());
//				totalReal = totalReal.add(vendedores.getIngresoRealB());
				
//				t=progress1;
//				setProgress(t);
//			}
			
			BigDecimal cumplimiento = (totalReal.intValue() == 0 && totalPresupuesto.intValue() == 0)? new BigDecimal("0") : totalReal.divide(totalPresupuesto, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100").setScale(2, BigDecimal.ROUND_HALF_UP));
			totalPre = new DecimalFormat("###,###").format(totalPresupuesto);
			totalRe = new DecimalFormat("###,###").format(totalReal);
			utilidad = new DecimalFormat("###,###.##").format(cumplimiento);
			if(progress != 0){
				setProgress(100);
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setProgress(0);
			}	
			
			
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo La lista del vendedor");
		}
	}
	//*Lista Vendedores para el director Linea Nacional "DL DA" "vista /dl/plandl" *//
	public void listarDetalleVendedoresLinea(){

		try{
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
//			BigDecimal totalPresupuesto = new BigDecimal("0");
			BigDecimal totalReal = new BigDecimal("0");
			DetalleDao dao = new DetalleDao();
			listadetalle = dao.listarDetalleNacional(tipo,  idLinea, idFuncionario,  fechaBusqueda, fechaBusquedaYear );
			
			for (Detalle detalle : listadetalle) {
				totalReal = totalReal.add(new BigDecimal(detalle.getValorNeto()).multiply(new BigDecimal("-1")));
				
			}
			totalRe = new DecimalFormat("###,###").format(totalReal);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo La lista del vendedor");
		}
	}
	
	public void inicioVista(){

		try{

			if(fechaConsulta == null){

				Calendar fechas = Calendar.getInstance();
				int month = fechas.get(Calendar.MONTH)+1;
				for (Fechas fecha: listaFechas) {
					System.out.println(fecha.getValorMes() + "/////////////////" + month);
					fechaConsulta  = (month<10)?(fecha.getValorMes().equals(String.valueOf("0"+month)))? fecha.getMes():fechaConsulta:(fecha.getValorMes().equals(String.valueOf(""+month)))? fecha.getMes(): fechaConsulta;
				}
				listarVendedores();
			}

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo La vista de Inicio");
		}
	}

	@SuppressWarnings("unused")
	public void listarVendedores(){

		try{
			
			for (Fechas fecha: listaFechas) {
					fechaConsulta = (fecha.getValorMes().equals(autenticacion.getFechaBusqueda()))? fecha.getMes() : fechaConsulta;
			}
			
			
			if(autenticacion.getUsuarioLogin().getPerfil().getId() == 9)
				autenticacion.setTipoVendedor("I");
			
			List<Object> listaV = new ArrayList<>();
			ListaComisionVendedores = new ArrayList<>();
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			BigDecimal valorReal = new BigDecimal("0.00");
			BigDecimal valorUtilidad = new BigDecimal("0.00");
			BigDecimal sumaPresupuesto = new BigDecimal("0.00");
			BigDecimal sumaIngresoR = new BigDecimal("0.00");
			BigDecimal TPresupuesto = new BigDecimal("0.00");
			BigDecimal TIngresoR = new BigDecimal("0.00");
			BigDecimal cumplimiento = new BigDecimal("0.00");
			totalR =  new BigDecimal("0.00");
			total =  new BigDecimal("0.00");
			int numero = 0;
			int numero1 = 0;
			int progress1 = 0;
			int t =0;
			
			DetalleDao dao = new DetalleDao();
			Zona_ventaDao daoV = new Zona_ventaDao();
			FuncionarioDao daoF = new FuncionarioDao();
			Funcionario funcionario = daoF.buscarPersona(autenticacion.getUsuarioLogin().getPersona().getCedula());

			Zona_FuncionarioDao daoZ = new Zona_FuncionarioDao();
			Zona_Funcionario zonaF = daoZ.buscarFuncionarioZona(funcionario.getId_funcionario());

			List<Funcionario> listaVendedor = new ArrayList<>();

			if(autenticacion.getTipoVendedor().equals("I") ){

				if(autenticacion.getUsuarioLogin().getPerfil().getId() == 2){

					listaVendedor = daoF.listarVendedoresInternos();
					tipo="funcionarioI";
					tablaFun = "I";

				}
				else{
					int sucursal= (zonaF.getCiudad().getId() == 1)? 1000 :(zonaF.getCiudad().getId() == 7)? 2000: (zonaF.getCiudad().getId()+1)*1000;
					OficinaVendedorInternoDao daoOF = new OficinaVendedorInternoDao();
					List<OficinaVendedorInterno> lista = daoOF.listaVenIntDirector(sucursal); 
					for (OficinaVendedorInterno ofiVenInt : lista) {
						Funcionario fun = daoF.buscar(ofiVenInt.getCodigosap());
						
						if (fun !=null){
							listaVendedor.add(fun);
						}
						else{
							fun = new Funcionario();
						}
					}
					tablaFun = "I";
					tipo="funcionarioI";
					listaV = dao.listarDetalleVendedoresOficina(zonaF.getCiudad().getId(), tipo, fechaBusqueda, fechaBusquedaYear);
				}
			}
			else if(autenticacion.getTipoVendedor().equals("L")){

				tablaFun = "L";
				LineaDao daoL = new LineaDao();
				ListaComisionVendedores = daoL.listarLineasPorCiudad(zonaF.getCiudad().getId(), fechaBusqueda, fechaBusquedaYear );
			}
			else{

				if(autenticacion.getUsuarioLogin().getPerfil().getId() == 2 || autenticacion.getUsuarioLogin().getPerfil().getId() == 12){

					listaVendedor = daoF.listarVendedores();
					tipo="funcionario";
					tablaFun = "E";
				}
				else{
					tablaFun = "E";
					tipo="funcionario";
					
					List<Zona_venta> ListaZona  = new ArrayList<>();
					
					if(autenticacion.getUsuarioLogin().getPerfil().getId() == 20){
						listaV = dao.listarDetalleVendedoresOficina(idCiudad, tipo, fechaBusqueda, fechaBusquedaYear);
						ListaZona = daoV.buscarZonaSucursal(idCiudad);
						System.out.println( "Entro lista" + idCiudad);
					}
					else{
						listaV = dao.listarDetalleVendedoresOficina(zonaF.getCiudad().getId(), tipo, fechaBusqueda, fechaBusquedaYear);
						ListaZona = daoV.buscarZonaSucursal(zonaF.getCiudad().getId());
					}
					System.out.println(ListaZona.size() + "Valor lista");
					
					listaVendedor  = daoF.listarVendedoresParaDirector(ListaZona);
				}	
			}
			progress1 = 100 /listaVendedor.size();
			for (Object object : listaV) {
				Object[] obc = (Object[]) object;
				Integer fun = (Integer) obc[1];
				System.out.println(sumaIngresoR + "--" + fun);
				
				for (int i =0;  i<listaVendedor.size(); i++) {
					
					if(listaVendedor.get(i).getId_funcionario() == fun  ){
						listaVendedor.remove(i);
						//i = listaVendedor.size();
					}
									
				}
				//System.out.println(fun + "!!!!!!!!!");
				Funcionario fun1 = daoF.buscar(fun);
				System.out.println(fun1.getId_funcionario());
				if(fun1 != null){
					valorReal = new BigDecimal("0.00");
					valorUtilidad = new BigDecimal("0.00");
					sumaPresupuesto = new BigDecimal("0.00");
					sumaIngresoR = new BigDecimal("0.00");	
					totalR =  new BigDecimal("0.00");
					total =  new BigDecimal("0.00");
					ComisionVendedores vendedores = new ComisionVendedores();
					vendedores.setId(fun1.getId_funcionario());
					vendedores.setCedula(fun1.getPersona().getCedula());
					vendedores.setUmbralCV(new BigDecimal("0.00"));
					vendedores.setNombre(fun1.getPersona().getNombre());
					vendedores.setTipo(fun1.getDescripcion());
					
					List<Zona_venta> zonaV = new ArrayList<>();
					if(autenticacion.getUsuarioLogin().getPerfil().getId() == 20){
						zonaV = daoV.buscarZonaVenOfi(fun1.getId_funcionario(), idCiudad );
					}
					else{
						zonaV = daoV.buscarZonaVenOfi(fun1.getId_funcionario(), zonaF.getCiudad().getId() );
					}
					 
					sumaIngresoR = new BigDecimal(((Long) obc[0] == 0)? (Long) obc[0] : (Long) obc[0] * -1);
					System.out.println(zonaV);
					if(zonaV.size() > 0 ){
						PresupuestoDao daoP = new PresupuestoDao();
						if(tipo.equals("funcionario")){
							BigDecimal pre = new BigDecimal("0");
							if(autenticacion.getUsuarioLogin().getPerfil().getId() == 20){
								pre = daoP.datoPorLineaSumFechasE(fun1.getId_funcionario(), idCiudad, fechaBusqueda, fechaBusquedaYear);
							}
							else{
								pre = daoP.datoPorLineaSumFechasE(fun1.getId_funcionario(), zonaF.getCiudad().getId(), fechaBusqueda, fechaBusquedaYear);
							}
							
							sumaPresupuesto = (pre == null) ? new BigDecimal("0.00"): pre;
						}
						else{
							BigDecimal pre = daoP.datoPorLineaSumFechas(fun1.getId_funcionario(), fechaBusqueda, fechaBusquedaYear);
							sumaPresupuesto = (pre == null) ? new BigDecimal("0.00"): pre;
						}
					}
					else{
						sumaPresupuesto = new BigDecimal("0.00");
					}
					
					vendedores.setPresupuesto(sumaPresupuesto.intValue());
					vendedores.setIngresoReal(sumaIngresoR.intValue());	
					
					vendedores.setCumplimiento((sumaIngresoR.intValue() == 0 || sumaPresupuesto.intValue() == 0 )? new BigDecimal("0"):sumaIngresoR.divide(sumaPresupuesto, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
					vendedores.setCumplimiento(vendedores.getCumplimiento().setScale(2, BigDecimal.ROUND_HALF_UP));
					vendedores.setImagen1((vendedores.getCumplimiento().intValue() >= 85)? "verde.png" :"rojo.png" );
					
					if(sumaPresupuesto.intValue() == 0  &&  sumaIngresoR.intValue() == 0){
						
					}
					else{
						ListaComisionVendedores.add(vendedores);
					}
					TPresupuesto = TPresupuesto.add(new BigDecimal(vendedores.getPresupuesto()));
					TIngresoR= TIngresoR.add(new BigDecimal(vendedores.getIngresoReal()));
					
					t += progress1;
					setProgress(t);					
				}
			}
				if(listaVendedor.size() >0){
					
					for (Funcionario f : listaVendedor) {
						PresupuestoDao daoP = new PresupuestoDao();
						BigDecimal pre = new BigDecimal("0");
						
						if(tipo.equals("funcionarioI")){
							pre = daoP.datoPorLineaSumFechas(f.getId_funcionario(), fechaBusqueda, fechaBusquedaYear);
						}						
						else if(autenticacion.getUsuarioLogin().getPerfil().getId() == 20){
							pre = daoP.datoPorLineaSumFechasE(f.getId_funcionario(), idCiudad, fechaBusqueda, fechaBusquedaYear);
						}
						else{
							pre = daoP.datoPorLineaSumFechasE(f.getId_funcionario(), zonaF.getCiudad().getId(), fechaBusqueda, fechaBusquedaYear);
						}
						sumaPresupuesto = (pre == null) ? new BigDecimal("0.00"): pre;
						ComisionVendedores vendedores = new ComisionVendedores();
						vendedores.setId(f.getId_funcionario());
						vendedores.setCedula(f.getPersona().getCedula());
						vendedores.setUmbralCV(new BigDecimal("0.00"));
						vendedores.setNombre(f.getPersona().getNombre());
						vendedores.setTipo(f.getDescripcion());
						
						vendedores.setPresupuesto(sumaPresupuesto.intValue());
						vendedores.setIngresoReal(0);	
						
						vendedores.setCumplimiento((sumaIngresoR.intValue() == 0 || sumaPresupuesto.intValue() == 0 )? new BigDecimal("0"):sumaIngresoR.divide(sumaPresupuesto, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
						vendedores.setCumplimiento(vendedores.getCumplimiento().setScale(2, BigDecimal.ROUND_HALF_UP));
						vendedores.setImagen1((vendedores.getCumplimiento().intValue() >= 85)? "verde.png" :"rojo.png" );
						
						ListaComisionVendedores.add(vendedores);
						
						TPresupuesto = TPresupuesto.add(new BigDecimal(vendedores.getPresupuesto()));
						TIngresoR= TIngresoR.add(new BigDecimal(vendedores.getIngresoReal()));				
						
					}
					
				}
			
			
			/*
			for (Funcionario fun : listaVendedor) {

				if(fun != null){
					valorReal = new BigDecimal("0.00");
					valorUtilidad = new BigDecimal("0.00");
					sumaPresupuesto = new BigDecimal("0.00");
					sumaIngresoR = new BigDecimal("0.00");	
					totalR =  new BigDecimal("0.00");
					total =  new BigDecimal("0.00");
					DetalleDao daoD = new DetalleDao();	
					ComisionVendedores vendedores = new ComisionVendedores();
					vendedores.setId(fun.getId_funcionario());
					vendedores.setCedula(fun.getPersona().getCedula());
					vendedores.setUmbralCV(new BigDecimal("0.00"));
					vendedores.setNombre(fun.getPersona().getNombre());
					vendedores.setTipo(fun.getDescripcion());

					PresupuestoDao daoP = new PresupuestoDao();
					if(tipo.equals("funcionario")){
						BigDecimal pre = daoP.datoPorLineaSumFechasE(fun.getId_funcionario(), zonaF.getCiudad().getId(), fechaBusqueda, fechaBusquedaYear);
						sumaPresupuesto = (pre == null) ? new BigDecimal("0.00"): pre;
					}
					else{
						BigDecimal pre = daoP.datoPorLineaSumFechas(fun.getId_funcionario(), fechaBusqueda, fechaBusquedaYear);
						sumaPresupuesto = (pre == null) ? new BigDecimal("0.00"): pre;
					}		
					listaplanV = daoD.listarPlanPorFechasPruebaE(tipo,fun.getId_funcionario(),zonaF.getCiudad().getId(), fechaBusqueda, fechaBusquedaYear);
					//listaplanV = daoD.listarPlanPorFechasPrueba(tipo,fun.getId_funcionario(), fechaBusqueda, fechaBusquedaYear);

					Plan planLinea2_3 = null; 
					for (Plan planL : listaplanV) {

						if(tipo == "funcionarioI" &&  planL.getLinea().getId() == 2){
							planLinea2_3 = new Plan();
							for(int k=0; k<listaplanV.size(); k++){

								if(listaplanV.get(k).getLinea().getId() == 3){
									planLinea2_3 = listaplanV.get(k);
								}
							}
							planL.setIngreso_Real(planL.getIngreso_Real().add(planLinea2_3.getIngreso_Real()));
							planL.setUtilidad_Real(planL.getUtilidad_Real().add(planLinea2_3.getUtilidad_Real()));						
						}
						else if(tipo == "funcionarioI" &&  planL.getLinea().getId() == 3){
							planL.setIngreso_Real(new BigDecimal("0.00"));
							planL.setUtilidad_Real(new BigDecimal("0.00"));
						}
						
						sumaIngresoR = (planL.getIngreso_Real() == null) ? new BigDecimal("0.00"): sumaIngresoR.add(planL.getIngreso_Real());

						if(planL.getIngreso() == null || planL.getIngreso().compareTo(BigDecimal.ZERO) == 0 ){
							valorReal = new BigDecimal("0.00");
							planL.setIngreso_Cumplimiento(valorReal.setScale(2, BigDecimal.ROUND_HALF_UP));
						}
						else{
							valorReal = planL.getIngreso_Real().divide(planL.getIngreso(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
							planL.setIngreso_Cumplimiento(valorReal.setScale(2, BigDecimal.ROUND_HALF_UP));
						}

						if(planL.getUtilidad() == null || planL.getUtilidad().compareTo(BigDecimal.ZERO) == 0){
							valorReal = new BigDecimal("0.00");
							planL.setUtilidad_Cumplimiento(valorReal.setScale(2, BigDecimal.ROUND_HALF_UP));
						}
						else{
							valorReal = planL.getUtilidad_Real().divide(planL.getUtilidad(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
							planL.setUtilidad_Cumplimiento(valorReal.setScale(2, BigDecimal.ROUND_HALF_UP));
						}

						if(planL.getFuncionario().getComision().getUmbralVenta() == null){
							planL.setUmbral(new BigDecimal("0.00"));
						} 
						else{
							planL.setUmbral(planL.getFuncionario().getComision().getUmbralVenta());
						}

						numero = planL.getFuncionario().getComision().getUmbralVenta().compareTo(planL.getUtilidad_Cumplimiento().divide(new BigDecimal("100.00")));
						numero1 = planL.getFuncionario().getComision().getUmbralVenta().compareTo(planL.getIngreso_Cumplimiento().divide(new BigDecimal("100.00")));

						if(numero == 1 ){
							planL.setValor_Comision_Pagar(new BigDecimal("0.00"));
							total = total.add(planL.getValor_Comision_Pagar());
						}
						else{

							if(planL.getLinea().getId() == 15){

								if(planL.getUtilidad_Real().intValue() >= 137891000){
									planL.setValor_Comision_Pagar(new BigDecimal("0.00"));
									total = total.add(planL.getValor_Comision_Pagar());
								}
								else{
									valorUtilidad= planL.getDistribucion_Linea().multiply(planL.getFuncionario().getComision().getValorBaseVenta().divide(new BigDecimal("100.00")));
									valorUtilidad= valorUtilidad.multiply(planL.getUtilidad_Cumplimiento()); 
									planL.setValor_Comision_Pagar(valorUtilidad);
									total = total.add(planL.getValor_Comision_Pagar());
									total.add(total);
								}
							}
							else{
								valorUtilidad= planL.getDistribucion_Linea().multiply(planL.getFuncionario().getComision().getValorBaseVenta().divide(new BigDecimal("100.00")));
								valorUtilidad= valorUtilidad.multiply(planL.getUtilidad_Cumplimiento()); 
								planL.setValor_Comision_Pagar(valorUtilidad);
								total = total.add(planL.getValor_Comision_Pagar());
								total.add(total);
							}	
						}
						if(numero1 == 1 ){
							planL.setValor_Comision_PagarR(new BigDecimal("0.00"));
							totalR = totalR.add(planL.getValor_Comision_PagarR());
						}
						else{

							if(planL.getLinea().getId() == 15){
								if(planL.getIngreso_Real().intValue() >= 137891000){
									planL.setValor_Comision_PagarR(new BigDecimal("0.00"));
									totalR = totalR.add(planL.getValor_Comision_PagarR());
								}
								else{
									valorUtilidad= planL.getDistribucion_Linea().multiply(planL.getFuncionario().getComision().getValorBaseVenta().divide(new BigDecimal("100.00")));
									valorUtilidad= valorUtilidad.multiply(planL.getIngreso_Cumplimiento()); 
									planL.setValor_Comision_PagarR(valorUtilidad);
									totalR = totalR.add(planL.getValor_Comision_PagarR());	
								}
							}
							else{
								valorUtilidad= planL.getDistribucion_Linea().multiply(planL.getFuncionario().getComision().getValorBaseVenta().divide(new BigDecimal("100.00")));
								valorUtilidad= valorUtilidad.multiply(planL.getIngreso_Cumplimiento()); 
								planL.setValor_Comision_PagarR(valorUtilidad);
								totalR = totalR.add(planL.getValor_Comision_PagarR());
							}	
						}
					}
					
					vendedores.setPresupuesto(sumaPresupuesto.intValue());
					vendedores.setIngresoReal(sumaIngresoR.intValue());
					if(sumaIngresoR.intValue() == 0 || sumaPresupuesto.intValue() == 0 ){
						vendedores.setCumplimiento(new BigDecimal("0"));
					}
					else{
						vendedores.setCumplimiento(sumaIngresoR.divide(sumaPresupuesto, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
					}
					vendedores.setCumplimiento(vendedores.getCumplimiento().setScale(2, BigDecimal.ROUND_HALF_UP));
					vendedores.setComision(total.intValue());
					vendedores.setComisionR(totalR.intValue());
					vendedores.setUmbralCV(total);
					vendedores.setImagen1((vendedores.getCumplimiento().intValue() >= 85)? "verde.png" :"rojo.png" );
					ListaComisionVendedores.add(vendedores);
					
					TPresupuesto = TPresupuesto.add(new BigDecimal(vendedores.getPresupuesto()));
					TIngresoR= TIngresoR.add(new BigDecimal(vendedores.getIngresoReal()));
					
					t += progress1;
					setProgress(t);
				}	
				
			}*/
			cumplimiento =(TPresupuesto.intValue() == 0 || TIngresoR.intValue() == 0)? new BigDecimal("0"): TIngresoR.divide(TPresupuesto, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			cumplimiento = cumplimiento.setScale(2, BigDecimal.ROUND_HALF_UP);
			totalPre = new DecimalFormat("###,###").format(TPresupuesto);
			totalReA = new DecimalFormat("###,###").format(TIngresoR);
			utilidad = new DecimalFormat("###,###.##").format(cumplimiento);
			if(progress > 0){
				setProgress(100);
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setProgress(0);
			}	
			autenticacion.setFechaBusqueda(fechaBusqueda);
			autenticacion.setFechaBusquedaYear(fechaBusquedaYear);


		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Vendedores del Director.");
		}
	}

	//*lista las lineas por ciudad del mix de linea*//

	public void listarCiudadPorMixLinea(){

		try{
			ListaComisionGerente = new ArrayList<>();
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			BigDecimal TPresupuesto = new BigDecimal("0.00");
			BigDecimal TIngresoR = new BigDecimal("0.00");
			BigDecimal cumplimiento = new BigDecimal("0.00");
			FuncionarioDao daoF = new FuncionarioDao();
			Funcionario funcionario = daoF.buscarPersona(autenticacion.getUsuarioLogin().getPersona().getCedula());

			Zona_FuncionarioDao daoZ = new Zona_FuncionarioDao();
			Zona_Funcionario zonaF = daoZ.buscarFuncionarioZona(funcionario.getId_funcionario());

			LineaDao daoC = new LineaDao();
			ListaComisionGerente = daoC.listarLineasMix(zonaF.getCiudad().getId(), fechaBusqueda, fechaBusquedaYear);
			
			for (ComisionVendedores vendedor : ListaComisionGerente) {
				TPresupuesto = TPresupuesto.add(vendedor.getPresupuestoB());
				TIngresoR = TIngresoR.add(vendedor.getIngresoRealB());
				
			}
			cumplimiento = TIngresoR.divide(TPresupuesto, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			cumplimiento = cumplimiento.setScale(2, BigDecimal.ROUND_HALF_UP);
			totalPre = new DecimalFormat("###,###").format(TPresupuesto);
			totalReA = new DecimalFormat("###,###").format(TIngresoR);
			utilidad = new DecimalFormat("###,###.##").format(cumplimiento);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo el Recaudo de cartera del Director.");
		}
	}
	
	//*lista las lineas por ciudad del mix de linea  jefe Internos*//

		public void listarCiudadPorMixLineaI(){

			try{
				ListaComisionGerente = new ArrayList<>();
				fechaBusqueda = autenticacion.getFechaBusqueda();
				fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
				BigDecimal TPresupuesto = new BigDecimal("0.00");
				BigDecimal TIngresoR = new BigDecimal("0.00");
				BigDecimal cumplimiento = new BigDecimal("0.00");
				LineaDao daoC = new LineaDao();
				ListaComisionGerente = daoC.listarLineasMixI( fechaBusqueda, fechaBusquedaYear);
				for (ComisionVendedores vendedor : ListaComisionGerente) {
					TPresupuesto = TPresupuesto.add(vendedor.getPresupuestoB());
					TIngresoR = TIngresoR.add(vendedor.getIngresoRealB());
					
				}
				cumplimiento = TIngresoR.divide(TPresupuesto, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
				cumplimiento = cumplimiento.setScale(2, BigDecimal.ROUND_HALF_UP);
				totalPre = new DecimalFormat("###,###").format(TPresupuesto);
				totalReA = new DecimalFormat("###,###").format(TIngresoR);
				utilidad = new DecimalFormat("###,###.##").format(cumplimiento);

			} catch (RuntimeException ex) {
				ex.printStackTrace();
				Messages.addGlobalError("Error no se Cargo el Recaudo de cartera del Director.");
			}
		}

	//*Muestra el recaudo y el valor de las Zonas a cargo*//
	public void listarZonasCargo(){

		try{
			for (Fechas fecha: listaFechas) {
				fechaConsulta = (fecha.getValorMes().equals(autenticacion.getFechaBusqueda()))? fecha.getMes() : fechaConsulta;
			}
			ListaComisionGerente = new ArrayList<>();
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			FuncionarioDao daoF = new FuncionarioDao();
			Funcionario funcionario = daoF.buscarPersona(autenticacion.getUsuarioLogin().getPersona().getCedula());

			CiudadDao daoC = new CiudadDao();
			ListaComisionGerente = daoC.listarZonasCargo(funcionario.getId_funcionario(), fechaBusqueda, fechaBusquedaYear);


		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo el Recaudo de cartera del Director.");
		}
	}

	//*Lista las lineas de las zonas Cargo*//

	public void listarLineasZonasCargo(){

		try{
			ListaComisionGerente = new ArrayList<>();
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			BigDecimal TPresupuesto = new BigDecimal("0.00");
			BigDecimal TIngresoR = new BigDecimal("0.00");
			BigDecimal cumplimiento = new BigDecimal("0.00");
			FuncionarioDao daoF = new FuncionarioDao();
			Funcionario funcionario = daoF.buscarPersona(autenticacion.getUsuarioLogin().getPersona().getCedula());

			LineaDao daoC = new LineaDao();
			ListaComisionGerente = daoC.listarLineasZonasCargo(funcionario.getId_funcionario(), fechaBusqueda, fechaBusquedaYear);

			for (ComisionVendedores vendedor : ListaComisionGerente) {
				TPresupuesto = TPresupuesto.add(vendedor.getPresupuestoB());
				TIngresoR = TIngresoR.add(vendedor.getIngresoRealB());
				
			}
			cumplimiento = TIngresoR.divide(TPresupuesto, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			cumplimiento = cumplimiento.setScale(2, BigDecimal.ROUND_HALF_UP);
			totalPre = new DecimalFormat("###,###").format(TPresupuesto);
			totalReA = new DecimalFormat("###,###").format(TIngresoR);
			utilidad = new DecimalFormat("###,###.##").format(cumplimiento);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo el Recaudo de cartera del Director.");
		}
	}

	//*Lista El recaudo del director*//

	public void listarRecaudo(){

		try{
			ListaComisionGerente = new ArrayList<>();

			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			FuncionarioDao daoF = new FuncionarioDao();
			Funcionario funcionario = daoF.buscarPersona(autenticacion.getUsuarioLogin().getPersona().getCedula());

			Zona_FuncionarioDao daoZ = new Zona_FuncionarioDao();
			Zona_Funcionario zonaF = daoZ.buscarFuncionarioZona(funcionario.getId_funcionario());

			Zona_ventaDao daoZo = new Zona_ventaDao();
			List<Zona_venta> listaZona = daoZo.buscarZonaSucursal(zonaF.getCiudad().getId());

			int meses =  daoZ.fechaFinalR();
			
			Calendar fecha = Calendar.getInstance();
			int year = fecha.get(Calendar.YEAR);
			fechaBusquedaYear = (fechaBusquedaYear == null || fechaBusquedaYear.equals(""))?  String.valueOf(year) : fechaBusquedaYear;	       
	        
			for (int i=1; i<meses; i++){
				
				fechaBusqueda = "0"+i;
				
				ComisionVendedores sucursales = new ComisionVendedores();
				sucursales.setPresupuestoB(new BigDecimal("0.00"));
				sucursales.setIngresoRealB(new BigDecimal("0.00"));
				sucursales.setConcepto((listaFechasR.get(i-1).getValorMes().equals(fechaBusqueda))? listaFechasR.get(i-1).getMes():null);
				
				for (Zona_venta zona_venta : listaZona) {
	
					RecaudoDao daoR = new RecaudoDao();
					List<Recaudo> listaRecaudo = daoR.listarRecaudoDirector(zona_venta.getFuncionario().getId_funcionario(), fechaBusqueda, fechaBusquedaYear);
	
					for (Recaudo recaudo : listaRecaudo) {
						
						//sucursales.setConcepto(recaudo.getFecha().toString());
						sucursales.setPresupuestoB(sucursales.getPresupuestoB().add(recaudo.getPresupuesto()));
						sucursales.setIngresoRealB(sucursales.getIngresoRealB().add(recaudo.getReal()));
					}
				}
				if(sucursales.getIngresoRealB().intValue() == 0 ){
					sucursales.setCumplimiento(new BigDecimal("0"));
				}
				else{
					sucursales.setCumplimiento(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP));
				}
	
				String semaforo = (sucursales.getCumplimiento().intValue() >= 85)? "verde.png" : "rojo.png";
				sucursales.setImagen1(semaforo);
				ListaComisionGerente.add(sucursales);
				
			}
			
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo el Recaudo de cartera del Director.");
		}
	}
	
	//*Lista El recaudo del Jefe interno*//

		public void listarRecaudoI(){

			try{
								
				ListaComisionGerente = new ArrayList<>();

				fechaBusqueda = autenticacion.getFechaBusqueda();
				fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
				
				RecaudoDao dao = new RecaudoDao();
				
				int meses =  dao.fechaFinalR();
				
				Calendar fecha = Calendar.getInstance();
				int year = fecha.get(Calendar.YEAR);
				fechaBusquedaYear = (fechaBusquedaYear == null || fechaBusquedaYear.equals(""))?  String.valueOf(year) : fechaBusquedaYear;	       
		       
				for (int i=1; i<meses; i++){
					
					fechaBusqueda = "0"+i;
				
					List <Recaudo> listaRecaudo = dao.recaudoPaisJefeI(fechaBusqueda, fechaBusquedaYear);
					
					ComisionVendedores sucursales = new ComisionVendedores();
					sucursales.setPresupuestoB(new BigDecimal("0.00"));
					sucursales.setIngresoRealB(new BigDecimal("0.00"));
					sucursales.setConcepto((listaFechasR.get(i-1).getValorMes().equals(fechaBusqueda))? listaFechasR.get(i-1).getMes():null);
					for (Recaudo recaudo : listaRecaudo) {
	
							sucursales.setPresupuestoB(sucursales.getPresupuestoB().add(recaudo.getPresupuesto()));
							sucursales.setIngresoRealB(sucursales.getIngresoRealB().add(recaudo.getReal()));
						
					}
					if(sucursales.getIngresoRealB().intValue() == 0 ){
						sucursales.setCumplimiento(new BigDecimal("0"));
					}
					else{
						sucursales.setCumplimiento(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP));
					}
	
					String semaforo = (sucursales.getCumplimiento().intValue() >= 85)? "verde.png" : "rojo.png";
					sucursales.setImagen1(semaforo);
					ListaComisionGerente.add(sucursales);
					
				}
				
			} catch (RuntimeException ex) {
				ex.printStackTrace();
				Messages.addGlobalError("Error no se Cargo el Recaudo de cartera del Director.");
			}
		}

	//*lista Presupuesto y real del mix de linea del Director de linea*//

	public void listarPresupuestoDirectorLinea(){

		try{
			for (Fechas fecha: listaFechas) {
				fechaConsulta = (fecha.getValorMes().equals(autenticacion.getFechaBusqueda()))? fecha.getMes() : fechaConsulta;
			}
			ListaComisionGerente = new ArrayList<>();
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			LineaDao daoL = new LineaDao();
			if(autenticacion.getUsuarioLogin().getPerfil().getId() == 2 ){

				ListaComisionGerente = daoL.listarPresupuestoDirectorLinea(1, fechaBusqueda, fechaBusquedaYear);
			}
			else{
				ListaComisionGerente = daoL.listarPresupuestoDirectorLineaA(fechaBusqueda, fechaBusquedaYear);
			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo el Recaudo de cartera del Director.");
		}
	}

	//*lista Presupuesto y real del mix de linea del Director de linea*//

	public void listarMixLineaPorDirectorDeLinea(){
		try{
			ListaComisionGerente = new ArrayList<>();
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			BigDecimal sumaPresupuesto = new BigDecimal("0.00");
			BigDecimal sumaIngresoR = new BigDecimal("0.00");
			BigDecimal cumplimiento = new BigDecimal("0.00");
			LineaDao daoL = new LineaDao();
			if(autenticacion.getUsuarioLogin().getPerfil().getId() == 2 ){

				ListaComisionGerente = daoL.listarOficinasDirectorLinea(1, fechaBusqueda, fechaBusquedaYear);
			}
			else{
				ListaComisionGerente = daoL.listarOficinasDirectorLineaAn( fechaBusqueda, fechaBusquedaYear);
			}
			
			for (ComisionVendedores vendedor : ListaComisionGerente) {
				sumaPresupuesto = sumaPresupuesto.add(vendedor.getPresupuestoB());
				sumaIngresoR =  sumaIngresoR.add(vendedor.getIngresoRealB()); 
				
			}
			cumplimiento = sumaIngresoR.divide(sumaPresupuesto, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			cumplimiento = cumplimiento.setScale(2, BigDecimal.ROUND_HALF_UP);
			totalPre = new DecimalFormat("###,###").format(sumaPresupuesto);
			totalRe = new DecimalFormat("###,###").format(sumaIngresoR);
			utilidad = new DecimalFormat("###,###.##").format(cumplimiento);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo el Recaudo de cartera del Director.");
		}

	}

	//*cumplimiento pasi Director de linea*//

	public void cumplimietoDirectorDeLinea(){

		try {
			for (Fechas fecha: listaFechas) {
				fechaConsulta = (fecha.getValorMes().equals(autenticacion.getFechaBusqueda()))? fecha.getMes() : fechaConsulta;
			}
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			listaplanV = new ArrayList<>();

			Plan plan = new Plan();
			LineaDao daoL = new LineaDao();			
			List<BigDecimal> listaPresupuesto = daoL.listaPais(fechaBusqueda, fechaBusquedaYear);

			EsquemasDao daoE = new EsquemasDao();
			List<Esquemas> esquema =  daoE.listar();

			plan.setIngreso(listaPresupuesto.get(2));
			plan.setUtilidad(listaPresupuesto.get(3));
			plan.setIngreso_Real(listaPresupuesto.get(0).abs());

			plan.setUtilidad_Real(listaPresupuesto.get(0).abs().subtract(listaPresupuesto.get(1)));	

			plan.setIngreso_Cumplimiento(plan.getIngreso_Real().divide(plan.getIngreso(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP));
			plan.getIngreso_Cumplimiento();
			String semaforo = (plan.getIngreso_Cumplimiento().intValue() >= esquema.get(0).getUmbralComision().intValue())? "verde.png" : "rojo.png";
			plan.setImagen1(semaforo);
			plan.setUtilidad_Cumplimiento(plan.getUtilidad_Real().divide(plan.getUtilidad(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP));
			semaforo = (plan.getUtilidad_Cumplimiento().intValue() >= esquema.get(0).getUmbralComision().intValue())? "verde.png" : "rojo.png";
			plan.setImagen(semaforo);
			plan.setUmbral(esquema.get(0).getUmbralComision());
			listaplanV.add(plan);
			//ListaComisionVendedores.add(vendedores);

			if(progress > 90){
				setProgress(100);
			}	

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se cargo la lista Pais");
		}
	}

	//*Lista el mix  de linea del Jefe Interno*//
	public void listarPresupuestoJefeInterno(){

		try{
			for (Fechas fecha: listaFechas) {
				fechaConsulta = (fecha.getValorMes().equals(autenticacion.getFechaBusqueda()))? fecha.getMes() : fechaConsulta;
			}
			ListaComisionGerente = new ArrayList<>();
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			CiudadDao daoC = new CiudadDao();
			ListaComisionGerente = daoC.listarPresupuestoJefeInternos(1000, fechaBusqueda, fechaBusquedaYear);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo el Recaudo de cartera del Director.");
		}
	}

	//*Trae los datos de los funcionarios por linea del detalle*//
	public void listaLineasFuncionario(){

		try{

			ListaComisionVendedores = new ArrayList<>();
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();	
			BigDecimal sumaPresupuesto = new BigDecimal("0.00");
			BigDecimal sumaIngresoR = new BigDecimal("0.00");
			BigDecimal cumplimiento = new BigDecimal("0.00");


			//				FuncionarioDao  daoF = new FuncionarioDao();
			//				Funcionario fun = daoF.buscarPersona(idFuncionario);
			//				
			tipo = (autenticacion.getTipoVendedor().equals("I"))? "funcionarioI": "funcionario";

			LineaDao daoL= new LineaDao();
			ListaComisionVendedores =daoL.listarVendedoresPorLinea (tipo, idFuncionario, fechaBusqueda, fechaBusquedaYear);
			
			for (ComisionVendedores vendedor : ListaComisionVendedores) {
				sumaPresupuesto = sumaPresupuesto.add(vendedor.getPresupuestoB());
				sumaIngresoR =  sumaIngresoR.add(vendedor.getIngresoRealB()); 
				
			}
			cumplimiento = sumaIngresoR.divide(sumaPresupuesto, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			cumplimiento = cumplimiento.setScale(2, BigDecimal.ROUND_HALF_UP);
			totalPre = new DecimalFormat("###,###").format(sumaPresupuesto);
			totalRe = new DecimalFormat("###,###").format(sumaIngresoR);
			utilidad = new DecimalFormat("###,###.##").format(cumplimiento);
			
			autenticacion.setFechaBusqueda(fechaBusqueda);
			autenticacion.setFechaBusquedaYear(fechaBusquedaYear);


		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se cargo la lista Pais");
		}
	}

	public void onComplete() {
		Messages.addGlobalError("Proceso Completado");
	}	

	public Recursos getRecurso() {
		return recurso;
	}
	public void setRecurso(Recursos recurso) {
		this.recurso = recurso;
	}
	public List<Fechas> getListaFechas() {
		return listaFechas;
	}
	public void setListaFechas(List<Fechas> listaFechas) {
		this.listaFechas = listaFechas;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public List<ComisionVendedores> getListaComisionVendedores() {
		return ListaComisionVendedores;
	}
	public void setListaComisionVendedores(List<ComisionVendedores> listaComisionVendedores) {
		ListaComisionVendedores = listaComisionVendedores;
	}
	public List<ComisionVendedores> getListaFiltrados() {
		return listaFiltrados;
	}
	public void setListaFiltrados(List<ComisionVendedores> listaFiltrados) {
		this.listaFiltrados = listaFiltrados;
	}
	public List<Plan> getListaplanV() {
		return listaplanV;
	}
	public void setListaplanV(List<Plan> listaplanV) {
		this.listaplanV = listaplanV;
	}
	public String getFechaBusqueda() {
		return fechaBusqueda;
	}
	public void setFechaBusqueda(String fechaBusqueda) {
		this.fechaBusqueda = fechaBusqueda;
	}
	public String getFechaBusquedaYear() {
		return fechaBusquedaYear;
	}
	public void setFechaBusquedaYear(String fechaBusquedaYear) {
		this.fechaBusquedaYear = fechaBusquedaYear;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public BigDecimal getTotalR() {
		return totalR;
	}
	public void setTotalR(BigDecimal totalR) {
		this.totalR = totalR;
	}
	public AutenticacionBean getAutenticacion() {
		return autenticacion;
	}
	public void setAutenticacion(AutenticacionBean autenticacion) {
		this.autenticacion = autenticacion;
	}
	public int getCod() {
		return cod;
	}
	public void setCod(int cod) {
		this.cod = cod;
	}
	public String getTablaFun() {
		return tablaFun;
	}
	public void setTablaFun(String tablaFun) {
		this.tablaFun = tablaFun;
	}
	public String getTablaLin() {
		return tablaLin;
	}
	public void setTablaLin(String tablaLin) {
		this.tablaLin = tablaLin;
	}
	public Integer getProgress() {
		return progress;
	}
	public void setProgress(Integer progress) {
		this.progress = progress;
	}
	public List<ComisionVendedores> getListaComisionGerente() {
		return ListaComisionGerente;
	}
	public void setListaComisionGerente(List<ComisionVendedores> listaComisionGerente) {
		ListaComisionGerente = listaComisionGerente;
	}
	public int getIdFuncionario() {
		return idFuncionario;
	}
	public void setIdFuncionario(int idFuncionario) {
		this.idFuncionario = idFuncionario;
	}
	public int getIdLinea() {
		return idLinea;
	}
	public void setIdLinea(int idLinea) {
		this.idLinea = idLinea;
	}
	public List<Detalle> getListadetalle() {
		return listadetalle;
	}
	public void setListadetalle(List<Detalle> listadetalle) {
		this.listadetalle = listadetalle;
	}
	public String getTotalPre() {
		return totalPre;
	}
	public void setTotalPre(String totalPre) {
		this.totalPre = totalPre;
	}
	public String getTotalRe() {
		return totalRe;
	}
	public void setTotalRe(String totalRe) {
		this.totalRe = totalRe;
	}
	public String getFechaConsulta() {
		return fechaConsulta;
	}
	public void setFechaConsulta(String fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}
	public String getUtilidad() {
		return utilidad;
	}
	public void setUtilidad(String utilidad) {
		this.utilidad = utilidad;
	}
	public String getNombreVendedor() {
		return nombreVendedor;
	}
	public void setNombreVendedor(String nombreVendedor) {
		this.nombreVendedor = nombreVendedor;
	}
	public String getTotalReA() {
		return totalReA;
	}
	public void setTotalReA(String totalReA) {
		this.totalReA = totalReA;
	}
	public List<Fechas> getListaFechasR() {
		return listaFechasR;
	}
	public void setListaFechasR(List<Fechas> listaFechasR) {
		this.listaFechasR = listaFechasR;
	}
	public int getIdCiudad() {
		return idCiudad;
	}
	public void setIdCiudad(int idCiudad) {
		this.idCiudad = idCiudad;
	}
	public String getNombreCiudad() {
		return nombreCiudad;
	}
	public void setNombreCiudad(String nombreCiudad) {
		this.nombreCiudad = nombreCiudad;
	}
}
