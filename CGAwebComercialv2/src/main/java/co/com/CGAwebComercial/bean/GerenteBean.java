package co.com.CGAwebComercial.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.omnifaces.util.Messages;

import co.com.CGAwebComercial.dao.CiudadDao;
import co.com.CGAwebComercial.dao.DetalleDao;
import co.com.CGAwebComercial.dao.EsquemasDao;
import co.com.CGAwebComercial.dao.FuncionarioDao;
import co.com.CGAwebComercial.dao.LineaDao;
import co.com.CGAwebComercial.dao.PresupuestoDao;
import co.com.CGAwebComercial.entyties.Esquemas;
import co.com.CGAwebComercial.entyties.Funcionario;
import co.com.CGAwebComercial.entyties.Plan;
import co.com.CGAwebComercial.resource.Recursos;
import co.com.CGAwebComercial.util.ComisionVendedores;
import co.com.CGAwebComercial.util.Fechas;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class GerenteBean implements Serializable{

	@ManagedProperty("#{autenticacionBean}")
	private AutenticacionBean autenticacion;

	private Recursos recurso;

	private List<ComisionVendedores> ListaComisionVendedores;
	private List<ComisionVendedores> listaFiltrados;
	private List<Fechas> listaFechas;
	private List<Plan> listaplan;
	private List<Plan> listaplanV;

	private BigDecimal total;
	private BigDecimal totalR;
	private String fechaBusqueda;
	private String fechaBusquedaYear;
	private String tipo = "";
	private String sucursal;
	private String linea;
	private String vendedor; 
	private String uen;
	private String fechaConsulta;
	private String nombreEspecialista;
	private String nombreUEN;
	private String nombreOficina;
	private String totalPresupuesto;
	private String totalReal;
	private String totalCumplimiento;
	private String totalPresupuestoU;
	private String totalRealU;
	private String totalCumplimientoU;

	private int idCiudad;
	private int idLinea;
	private int idFuncionario;
	private int idOficina;
	private int inicio=0;
	private Integer progress=0;


	public GerenteBean() {
		recurso = new Recursos();
		listaFechas = recurso.cargarFechasTotal();
		progress = 0;
	}

	@SuppressWarnings("unused")
	public void listarPais(){

		try{
			if(autenticacion != null){
				autenticacion.registroIngreso(autenticacion.getUsuarioLogin());
			}
			listaplan = new ArrayList<>();
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();	
			int progress1  = 0;

			if(fechaBusqueda != null && fechaBusquedaYear != null){


				BigDecimal valorReal = new BigDecimal("0.00");
				BigDecimal valorUtilidad = new BigDecimal("0.00");
				BigDecimal sumaPresupuesto = new BigDecimal("0.00");
				BigDecimal sumaIngresoR = new BigDecimal("0.00");
				List<Funcionario> listaVendedor = null;
				int numero = 0;
				int numero1 = 0;
				int i =0;
				int j = 0;
				int t = 0;
				ListaComisionVendedores = new ArrayList<>();
				FuncionarioDao daoF = new FuncionarioDao();
				if(autenticacion.getTipoVendedor().equals("I") ){
					listaVendedor = daoF.listarVendedoresInternos();
					tipo="funcionarioI";
				}
				else{
					listaVendedor = daoF.listarVendedores();
					tipo="funcionario";
				}
				progress1 = 100 /listaVendedor.size();
				ComisionVendedores vendedores = new ComisionVendedores();
				Plan plan = new Plan();
				plan.setIngreso(new BigDecimal("0.00"));
				plan.setIngreso_Real(new BigDecimal("0.00"));
				plan.setUtilidad(new BigDecimal("0.00"));
				plan.setUtilidad_Real(new BigDecimal("0.00"));

				for (Funcionario funcionario : listaVendedor) {

					valorReal = new BigDecimal("0.00");
					valorUtilidad = new BigDecimal("0.00");
					sumaPresupuesto = new BigDecimal("0.00");
					sumaIngresoR = new BigDecimal("0.00");	
					totalR =  new BigDecimal("0.00");
					total =  new BigDecimal("0.00");
					DetalleDao daoD = new DetalleDao();	
					/*vendedores.setId(funcionario.getId_funcionario());
					vendedores.setCedula(funcionario.getPersona().getCedula());
					vendedores.setUmbralCV(new BigDecimal("0.00"));
					vendedores.setNombre(funcionario.getPersona().getNombre());
					vendedores.setTipo(funcionario.getComision().getNombre());*/

					PresupuestoDao daoP = new PresupuestoDao();
					BigDecimal pre = daoP.datoPorLineaSumFechas(funcionario.getId_funcionario(), fechaBusqueda, fechaBusquedaYear);
					sumaPresupuesto = (pre == null) ? new BigDecimal("0.00"): pre;
					listaplanV = daoD.listarPlanPorFechasPrueba(tipo,funcionario.getId_funcionario(), fechaBusqueda, fechaBusquedaYear);



					for (Plan planL : listaplanV) {

						if(planL == null){
							plan.setIngreso(new BigDecimal("0.00"));
							plan.setIngreso_Real(new BigDecimal("0.00"));
							plan.setUtilidad(new BigDecimal("0.00"));
							plan.setUtilidad_Real(new BigDecimal("0.00"));
						}
						else{
							plan.setIngreso(planL.getIngreso().add(plan.getIngreso()));
							plan.setIngreso_Real(plan.getIngreso_Real().add(planL.getIngreso_Real()));

							plan.setUtilidad(plan.getUtilidad().add(planL.getUtilidad()));
							plan.setUtilidad_Real(plan.getUtilidad_Real().add(planL.getUtilidad_Real()));
						}
					}
					t += progress1;
					setProgress(t);

				}
				plan.setIngreso_Cumplimiento(plan.getIngreso_Real().divide(plan.getIngreso(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				String semaforo = (plan.getIngreso_Cumplimiento().intValue() >= 85)? "verde.png" : "rojo.png";
				plan.setImagen1(semaforo);
				plan.setUtilidad_Cumplimiento(plan.getUtilidad_Real().divide(plan.getUtilidad(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				semaforo = (plan.getUtilidad_Cumplimiento().intValue() >= 85)? "verde.png" : "rojo.png";
				plan.setImagen(semaforo);
				listaplan.add(plan);
				ListaComisionVendedores.add(vendedores);

				if(progress > 90){
					setProgress(100);
				}	

			}

			autenticacion.setFechaBusqueda(fechaBusqueda);
			autenticacion.setFechaBusquedaYear(fechaBusquedaYear);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Comision de Vendedores");
		}	

	}

	//*Segun la oficina seleciona los valores de las lineas 3*//
	public void listarLineasPais(){

		try {
			if(autenticacion != null){
				autenticacion.registroIngreso(autenticacion.getUsuarioLogin());
			}
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			BigDecimal sumaRealU = new BigDecimal("0.00");
			BigDecimal sumaUtilidadP = new BigDecimal("0.00");
			BigDecimal sumaPresupuesto = new BigDecimal("0.00");
			BigDecimal sumaIngresoR = new BigDecimal("0.00");
			BigDecimal cumplimiento = new BigDecimal("0.00");
			BigDecimal cumplimientoU = new BigDecimal("0.00");
			idCiudad = (idCiudad== 1 )? 1000 : (idCiudad == 7 )? 2000 : (idCiudad+1)*1000 ;
			LineaDao daoL = new LineaDao();
			listaplan = daoL.listarLineas(tipo, idCiudad, fechaBusqueda, fechaBusquedaYear);
			
			for (Plan vendedor : listaplan) {
				sumaPresupuesto = sumaPresupuesto.add(vendedor.getIngreso());
				sumaIngresoR = sumaIngresoR.add(vendedor.getIngreso_Real());
				sumaUtilidadP = sumaUtilidadP.add(vendedor.getUtilidad());
				sumaRealU = sumaRealU.add(vendedor.getUtilidad_Real());
			}
			cumplimiento = (sumaIngresoR.intValue() == 0 || sumaPresupuesto.intValue() == 0 )? new BigDecimal("0"): sumaIngresoR.divide(sumaPresupuesto, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			cumplimiento = cumplimiento.setScale(2, BigDecimal.ROUND_HALF_UP);
			cumplimientoU = (sumaRealU.intValue() == 0 || sumaUtilidadP.intValue() == 0)? new BigDecimal("0") :sumaRealU.divide(sumaUtilidadP, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			cumplimientoU = cumplimientoU.setScale(2, BigDecimal.ROUND_HALF_UP);
			totalPresupuesto = new DecimalFormat("###,###").format(sumaPresupuesto);
			totalReal = new DecimalFormat("###,###").format(sumaIngresoR);
			totalCumplimiento = new DecimalFormat("###,###.##").format(cumplimiento);
			totalPresupuestoU = new DecimalFormat("###,###").format(sumaUtilidadP);
			totalRealU = new DecimalFormat("###,###").format(sumaRealU);
			totalCumplimientoU = new DecimalFormat("###,###.##").format(cumplimientoU);
			autenticacion.setFechaBusqueda(fechaBusqueda);
			autenticacion.setFechaBusquedaYear(fechaBusquedaYear);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista del Plan");
		}
	}

	//*Lista los vendedores por la linea seleccionada 3*//
	public void listarVendedoresPorLinea(){

		try{
			if(autenticacion != null){
				autenticacion.registroIngreso(autenticacion.getUsuarioLogin());
			}
			ListaComisionVendedores = new ArrayList<>();
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			BigDecimal sumaRealU = new BigDecimal("0.00");
			BigDecimal sumaUtilidadP = new BigDecimal("0.00");
			BigDecimal sumaPresupuesto = new BigDecimal("0.00");
			BigDecimal sumaIngresoR = new BigDecimal("0.00");
			BigDecimal cumplimiento = new BigDecimal("0.00");
			BigDecimal cumplimientoU = new BigDecimal("0.00");
			tipo = (autenticacion.getTipoVendedor().equals("I"))? "funcionario" : "funcionario";

			//idCiudad = (idCiudad== 1000 )? 1 : (idCiudad == 7000 )? 2 : (idCiudad/1000)-1 ;
			FuncionarioDao daoF = new FuncionarioDao();
			List<Integer> listaF = daoF.listarVendedoresOficinaLinea(idLinea, idCiudad , tipo, fechaBusqueda, fechaBusquedaYear);
			
			for (Integer integer : listaF) {
				ComisionVendedores plan = new ComisionVendedores();
				LineaDao daoL = new LineaDao();
				plan = daoL.listarVendedoresPorSucursalPorLinea(tipo, idLinea, integer, idCiudad, fechaBusqueda, fechaBusquedaYear);
				sumaPresupuesto = sumaPresupuesto.add(plan.getPresupuestoB());
				sumaIngresoR = sumaIngresoR.add(plan.getIngresoRealB());
				sumaUtilidadP = sumaUtilidadP.add(plan.getUtilpresupuesto());
				sumaRealU = sumaRealU.add(plan.getUtilidadReal());
				ListaComisionVendedores.add(plan);
			}
			cumplimiento = (sumaIngresoR.intValue() == 0 || sumaPresupuesto.intValue() == 0)? new BigDecimal("0"):sumaIngresoR.divide(sumaPresupuesto, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			cumplimiento = cumplimiento.setScale(2, BigDecimal.ROUND_HALF_UP);
			cumplimientoU = (sumaRealU.intValue() == 0 || sumaUtilidadP.intValue() == 0)? new BigDecimal("0"):sumaRealU.divide(sumaUtilidadP, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			cumplimientoU = cumplimientoU.setScale(2, BigDecimal.ROUND_HALF_UP);
			totalPresupuesto = new DecimalFormat("###,###").format(sumaPresupuesto);
			totalReal = new DecimalFormat("###,###").format(sumaIngresoR);
			totalCumplimiento = new DecimalFormat("###,###.##").format(cumplimiento);
			totalPresupuestoU = new DecimalFormat("###,###").format(sumaUtilidadP);
			totalRealU = new DecimalFormat("###,###").format(sumaRealU);
			totalCumplimientoU = new DecimalFormat("###,###.##").format(cumplimientoU);
			
//			Zona_ventaDao daoZ = new Zona_ventaDao();
//			List <Zona_venta> listaV = daoZ.buscarZonaSucursal(idCiudad);
//			System.out.println(listaV.size());
//			for (Zona_venta zona_venta : listaV) {
//				ComisionVendedores plan = new ComisionVendedores();
//				if(zona_venta.getFuncionario().getId_funcionario() != 1){
//
//					LineaDao daoL = new LineaDao();
//					plan = daoL.listarVendedoresPorLinea(tipo, idLinea, zona_venta.getFuncionario().getId_funcionario(), fechaBusqueda, fechaBusquedaYear);
//					ListaComisionVendedores.add(plan);
//				}
//				//System.out.println(plan.getImagen1() + "imagennnn");
//			}


		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Vendedores por Linea");
		}
	}

	//*Se listan las oficinas con presupuesto y real por pais 4*//
	public void listarOficinas(){

		try{
			if(autenticacion != null){
				autenticacion.registroIngreso(autenticacion.getUsuarioLogin());
			}
			ListaComisionVendedores = new ArrayList<>();
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			BigDecimal sumaRealU = new BigDecimal("0.00");
			BigDecimal sumaUtilidadP = new BigDecimal("0.00");
			BigDecimal sumaPresupuesto = new BigDecimal("0.00");
			BigDecimal sumaIngresoR = new BigDecimal("0.00");
			BigDecimal cumplimiento = new BigDecimal("0.00");
			BigDecimal cumplimientoU = new BigDecimal("0.00");
			CiudadDao daoC = new CiudadDao();
			ListaComisionVendedores = daoC.listarOficinas( fechaBusqueda, fechaBusquedaYear );
			
			for (ComisionVendedores vendedor : ListaComisionVendedores) {
				sumaPresupuesto = sumaPresupuesto.add(vendedor.getPresupuestoB());
				sumaIngresoR = sumaIngresoR.add(vendedor.getIngresoRealB());
				sumaUtilidadP = sumaUtilidadP.add(vendedor.getUtilpresupuesto());
				sumaRealU = sumaRealU.add(vendedor.getUtilidadReal());
			}
			cumplimiento = (sumaIngresoR.intValue() == 0 || sumaPresupuesto.intValue() == 0 )? new BigDecimal("0"): sumaIngresoR.divide(sumaPresupuesto, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			cumplimiento = cumplimiento.setScale(2, BigDecimal.ROUND_HALF_UP);
			cumplimientoU = (sumaRealU.intValue() == 0 || sumaUtilidadP.intValue() == 0 )? new BigDecimal("0") :sumaRealU.divide(sumaUtilidadP, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			cumplimientoU = cumplimientoU.setScale(2, BigDecimal.ROUND_HALF_UP);
			totalPresupuesto = new DecimalFormat("###,###").format(sumaPresupuesto);
			totalReal = new DecimalFormat("###,###").format(sumaIngresoR);
			totalCumplimiento = new DecimalFormat("###,###.##").format(cumplimiento);
			totalPresupuestoU = new DecimalFormat("###,###").format(sumaUtilidadP);
			totalRealU = new DecimalFormat("###,###").format(sumaRealU);
			totalCumplimientoU = new DecimalFormat("###,###.##").format(cumplimientoU);

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Vendedores por Linea");
		}
	}
	
	//*Se listan las oficinas con presupuesto y real por linea 5 "gg" "/gg/oficina3"*//
		public void listarOficinasPorLinea(){

			try{
				if(autenticacion != null){
					autenticacion.registroIngreso(autenticacion.getUsuarioLogin());
				}
				ListaComisionVendedores = new ArrayList<>();
				fechaBusqueda = autenticacion.getFechaBusqueda();
				fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
				CiudadDao daoC = new CiudadDao();
				ListaComisionVendedores = daoC.listarOficinas( fechaBusqueda, fechaBusquedaYear );

			} catch (RuntimeException ex) {
				ex.printStackTrace();
				Messages.addGlobalError("Error no se Cargo la lista de Vendedores por Linea");
			}
		}

	//*Se listan los vendedores por Ciudad donde se muestra el presupuesto y real 4*//
	public void listarVendeoresPorCiudad(){

		try{
			if(autenticacion != null){
				autenticacion.registroIngreso(autenticacion.getUsuarioLogin());
			}
			ListaComisionVendedores = new ArrayList<>();
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			BigDecimal sumaRealU = new BigDecimal("0.00");
			BigDecimal sumaUtilidadP = new BigDecimal("0.00");
			BigDecimal sumaPresupuesto = new BigDecimal("0.00");
			BigDecimal sumaIngresoR = new BigDecimal("0.00");
			BigDecimal cumplimiento = new BigDecimal("0.00");
			BigDecimal cumplimientoU = new BigDecimal("0.00");
			tipo = (tipo.equals("I"))? "funcionario" : "funcionario";
			
			int oficina = (idCiudad == 1 )? 1000 : (idCiudad == 7 )? 2000 : (idCiudad+1)*1000 ;
			FuncionarioDao daoF = new FuncionarioDao();			
			List<Integer> listaF =  daoF.listarVendedoresOficina(oficina , tipo, fechaBusqueda, fechaBusquedaYear);
				
			for (Integer integer : listaF) {
				ComisionVendedores plan = new ComisionVendedores();
				LineaDao daoL = new LineaDao();
				plan = daoL.listarVendedoresPorCiudad(oficina, tipo, integer, fechaBusqueda, fechaBusquedaYear);
				sumaPresupuesto = sumaPresupuesto.add(plan.getPresupuestoB());
				sumaIngresoR = sumaIngresoR.add(plan.getIngresoRealB());
				sumaUtilidadP = sumaUtilidadP.add(plan.getUtilpresupuesto());
				sumaRealU = sumaRealU.add(plan.getUtilidadReal());
				ListaComisionVendedores.add(plan);
			}
			cumplimiento = sumaIngresoR.divide(sumaPresupuesto, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			cumplimiento = cumplimiento.setScale(2, BigDecimal.ROUND_HALF_UP);
			cumplimientoU = (sumaRealU.intValue() == 0 || sumaUtilidadP.intValue() == 0)? new BigDecimal("0"):sumaRealU.divide(sumaUtilidadP, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			cumplimientoU = cumplimientoU.setScale(2, BigDecimal.ROUND_HALF_UP);
			totalPresupuesto = new DecimalFormat("###,###").format(sumaPresupuesto);
			totalReal = new DecimalFormat("###,###").format(sumaIngresoR);
			totalCumplimiento = new DecimalFormat("###,###.##").format(cumplimiento);
			totalPresupuestoU = new DecimalFormat("###,###").format(sumaUtilidadP);
			totalRealU = new DecimalFormat("###,###").format(sumaRealU);
			totalCumplimientoU = new DecimalFormat("###,###.##").format(cumplimientoU);

			//idCiudad = idCiudad / 1000;
//
//			Zona_ventaDao daoZ = new Zona_ventaDao();
//			List <Zona_venta> listaV = daoZ.buscarZonaSucursal(idCiudad);
//			int oficina = (idCiudad == 1 )? 1000 : (idCiudad == 7 )? 2000 : (idCiudad+1)*1000 ;
//			System.out.println(listaV.size());
//			for (Zona_venta zona_venta : listaV) {
//				ComisionVendedores plan = new ComisionVendedores();
//				if(zona_venta.getFuncionario().getId_funcionario() != 1){
//
//					LineaDao daoL = new LineaDao();
//					plan = daoL.listarVendedoresPorCiudad(oficina, tipo, zona_venta.getFuncionario().getId_funcionario(), fechaBusqueda, fechaBusquedaYear);
//					ListaComisionVendedores.add(plan);
//				}
//				//System.out.println(plan.getImagen1() + "imagennnn");
//			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Vendedores por Linea");
		}
	}

	//*Lista Presupuesto y el real de las lineas por periodo 5*//
	public void listarTotaldeLineasPais(){

		try {
			if(autenticacion != null){
				autenticacion.registroIngreso(autenticacion.getUsuarioLogin());
			}
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			BigDecimal sumaRealU = new BigDecimal("0.00");
			BigDecimal sumaUtilidadP = new BigDecimal("0.00");
			BigDecimal sumaPresupuesto = new BigDecimal("0.00");
			BigDecimal sumaIngresoR = new BigDecimal("0.00");
			BigDecimal cumplimiento = new BigDecimal("0.00");
			BigDecimal cumplimientoU = new BigDecimal("0.00");
			
			LineaDao daoL = new LineaDao();
			listaplan = daoL.listarLineasPorPais(fechaBusqueda, fechaBusquedaYear);
			
			for (Plan plan : listaplan) {
				sumaPresupuesto = sumaPresupuesto.add(plan.getIngreso());
				sumaIngresoR = sumaIngresoR.add(plan.getIngreso_Real());
				sumaUtilidadP = sumaUtilidadP.add(plan.getUtilidad());
				sumaRealU = sumaRealU.add(plan.getUtilidad_Real());
			}
			cumplimiento = sumaIngresoR.divide(sumaPresupuesto, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			cumplimiento = cumplimiento.setScale(2, BigDecimal.ROUND_HALF_UP);
			cumplimientoU = (sumaRealU.intValue() == 0 || sumaUtilidadP.intValue() == 0)? new BigDecimal("0"):sumaRealU.divide(sumaUtilidadP, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			cumplimientoU = cumplimientoU.setScale(2, BigDecimal.ROUND_HALF_UP);
			totalPresupuesto = new DecimalFormat("###,###").format(sumaPresupuesto);
			totalReal = new DecimalFormat("###,###").format(sumaIngresoR);
			totalCumplimiento = new DecimalFormat("###,###.##").format(cumplimiento);
			totalPresupuestoU = new DecimalFormat("###,###").format(sumaUtilidadP);
			totalRealU = new DecimalFormat("###,###").format(sumaRealU);
			totalCumplimientoU = new DecimalFormat("###,###.##").format(cumplimientoU);
			
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista del Plan");
		}
	}

	//*Se listan las oficinas con presupuesto y real por linea 5*//
	public void listarOficinasPorLineas(){

		try{
			if(autenticacion != null){
				autenticacion.registroIngreso(autenticacion.getUsuarioLogin());
			}
			ListaComisionVendedores = new ArrayList<>();
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			BigDecimal sumaRealU = new BigDecimal("0.00");
			BigDecimal sumaUtilidadP = new BigDecimal("0.00");
			BigDecimal sumaPresupuesto = new BigDecimal("0.00");
			BigDecimal sumaIngresoR = new BigDecimal("0.00");
			BigDecimal cumplimiento = new BigDecimal("0.00");
			BigDecimal cumplimientoU = new BigDecimal("0.00");
			
			CiudadDao daoC = new CiudadDao();
			ListaComisionVendedores = daoC.listarOficinasPorLinea(idLinea, fechaBusqueda, fechaBusquedaYear );
			
			for (ComisionVendedores vendedor : ListaComisionVendedores) {
				sumaPresupuesto = sumaPresupuesto.add(vendedor.getPresupuestoB());
				sumaIngresoR = sumaIngresoR.add(vendedor.getIngresoRealB());
				sumaUtilidadP = sumaUtilidadP.add(vendedor.getUtilpresupuesto());
				sumaRealU = sumaRealU.add(vendedor.getUtilidadReal());
			}
			cumplimiento = sumaIngresoR.divide(sumaPresupuesto, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			cumplimiento = cumplimiento.setScale(2, BigDecimal.ROUND_HALF_UP);
			cumplimientoU = sumaRealU.divide(sumaUtilidadP, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			cumplimientoU = cumplimientoU.setScale(2, BigDecimal.ROUND_HALF_UP);
			totalPresupuesto = new DecimalFormat("###,###").format(sumaPresupuesto);
			totalReal = new DecimalFormat("###,###").format(sumaIngresoR);
			totalCumplimiento = new DecimalFormat("###,###.##").format(cumplimiento);
			totalPresupuestoU = new DecimalFormat("###,###").format(sumaUtilidadP);
			totalRealU = new DecimalFormat("###,###").format(sumaRealU);
			totalCumplimientoU = new DecimalFormat("###,###.##").format(cumplimientoU);
			
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Vendedores por Linea");
		}
	}

	//*Se listan los vendedores por Ciudad y por linea donde se muestra el presupuesto y real 5*//
	public void listarVendeoresPorCiudadPorLinea(){

		try{
			if(autenticacion != null){
				autenticacion.registroIngreso(autenticacion.getUsuarioLogin());
			}
			ListaComisionVendedores = new ArrayList<>();
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			BigDecimal sumaRealU = new BigDecimal("0.00");
			BigDecimal sumaUtilidadP = new BigDecimal("0.00");
			BigDecimal sumaPresupuesto = new BigDecimal("0.00");
			BigDecimal sumaIngresoR = new BigDecimal("0.00");
			BigDecimal cumplimiento = new BigDecimal("0.00");
			BigDecimal cumplimientoU = new BigDecimal("0.00");
			
			tipo = (tipo.equals("I"))? "funcionarioI" : "funcionario";
			
			int oficina = (idCiudad == 1 )? 1000 : (idCiudad == 7 )? 2000 : (idCiudad+1)*1000 ;
			FuncionarioDao daoF = new FuncionarioDao();
			List<Integer> listaF =  daoF.listarVendedoresOficinaLinea(idLinea,  oficina, tipo,  fechaBusqueda, fechaBusquedaYear);
				
			for (Integer integer : listaF) {
				ComisionVendedores vendedor = new ComisionVendedores();
				LineaDao daoL = new LineaDao();
				vendedor = daoL.listarVendedoresPorCiudadPorLinea(idLinea,idCiudad, tipo, integer, fechaBusqueda, fechaBusquedaYear);
				sumaPresupuesto = sumaPresupuesto.add(vendedor.getPresupuestoB());
				sumaIngresoR = sumaIngresoR.add(vendedor.getIngresoRealB());
				sumaUtilidadP = sumaUtilidadP.add(vendedor.getUtilpresupuesto());
				sumaRealU = sumaRealU.add(vendedor.getUtilidadReal());
				ListaComisionVendedores.add(vendedor);
			}
			cumplimiento = sumaIngresoR.divide(sumaPresupuesto, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			cumplimiento = cumplimiento.setScale(2, BigDecimal.ROUND_HALF_UP);
			cumplimientoU = sumaRealU.divide(sumaUtilidadP, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			cumplimientoU = cumplimientoU.setScale(2, BigDecimal.ROUND_HALF_UP);
			totalPresupuesto = new DecimalFormat("###,###").format(sumaPresupuesto);
			totalReal = new DecimalFormat("###,###").format(sumaIngresoR);
			totalCumplimiento = new DecimalFormat("###,###.##").format(cumplimiento);
			totalPresupuestoU = new DecimalFormat("###,###").format(sumaUtilidadP);
			totalRealU = new DecimalFormat("###,###").format(sumaRealU);
			totalCumplimientoU = new DecimalFormat("###,###.##").format(cumplimientoU);
			
			//idCiudad = idCiudad / 1000;

//			Zona_ventaDao daoZ = new Zona_ventaDao();
//			List <Zona_venta> listaV = daoZ.buscarZonaSucursal(idCiudad);
//			System.out.println(listaV.size());
//			for (Zona_venta zona_venta : listaV) {
//				ComisionVendedores plan = new ComisionVendedores();
//				if(zona_venta.getFuncionario().getId_funcionario() != 1){
//
//					LineaDao daoL = new LineaDao();
//					plan = daoL.listarVendedoresPorCiudadPorLinea(idLinea,idCiudad, tipo, zona_venta.getFuncionario().getId_funcionario(), fechaBusqueda, fechaBusquedaYear);
//					ListaComisionVendedores.add(plan);
//				}
//				//System.out.println(plan.getImagen1() + "imagennnn");
//			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Vendedores por Linea");
		}
	}

	public void llamadoListar(){

		try{

			//listarComisionVendedoresFechas();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Vendedores por Linea");
		}

	}
	
	public void inicioListar(){

		try{
			if(inicio == 0){
			  listarComisionVendedoresFechas();
			  inicio =1;
			}	
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Vendedores por Linea");
		}

	}
	
	
	//*lista el presupuesto y el real de los vendedores por pais *//
	@SuppressWarnings({ "unused" })
	public void listarComisionVendedoresFechas(){

		try{
			if(autenticacion != null){
				autenticacion.registroIngreso(autenticacion.getUsuarioLogin());
			}
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();	
			int progress1  = 0;
			setProgress(0);
			if(fechaBusqueda != null && fechaBusquedaYear != null){

				BigDecimal sumaRealU = new BigDecimal("0.00");
				BigDecimal sumaUtilidadP = new BigDecimal("0.00");
				BigDecimal sumaPresupuesto = new BigDecimal("0.00");
				BigDecimal sumaIngresoR = new BigDecimal("0.00");
				BigDecimal cumplimiento = new BigDecimal("0.00");
				BigDecimal cumplimientoU = new BigDecimal("0.00");
				List<Funcionario> listaVendedor = null;
				int numero = 0;
				int numero1 = 0;
				//				int i =0;
				//				int j = 0;
				int t = 0;
				ListaComisionVendedores = new ArrayList<>();
				FuncionarioDao daoF = new FuncionarioDao();
				List<Integer>  result; 
				if(autenticacion.getTipoVendedor().equals("I") ){
					tipo="funcionarioI";
					result = daoF.listarVendedoresPais(tipo,fechaBusqueda, fechaBusquedaYear);
					//listaVendedor = daoF.listarVendedoresInternos();
				}
				else{
					tipo="funcionario";
					result = daoF.listarVendedoresPais(tipo, fechaBusqueda, fechaBusquedaYear);
					//listaVendedor = daoF.listarVendedores();
				}
				progress1 = 100 /result.size();
				LineaDao daoL = new LineaDao();
				
				for (Integer id : result) {

					ComisionVendedores vendedores = new ComisionVendedores();
					vendedores = daoL.listarVendedoresPorPais(tipo, id, fechaBusqueda, fechaBusquedaYear);	
//					sumaPresupuesto = sumaPresupuesto.add(vendedores.getPresupuestoB());
//					sumaIngresoR = sumaIngresoR.add(vendedores.getIngresoRealB());
//					sumaUtilidadP = sumaUtilidadP.add(vendedores.getUtilpresupuesto());
//					sumaRealU = sumaRealU.add(vendedores.getUtilidadReal());
					ListaComisionVendedores.add(vendedores);

					t += progress1;
					setProgress(t);
				}
				List<BigDecimal> listaPresupuesto = daoL.listaPais(fechaBusqueda, fechaBusquedaYear);

				sumaPresupuesto =listaPresupuesto.get(2);
				sumaUtilidadP = listaPresupuesto.get(3);
				sumaIngresoR = listaPresupuesto.get(0).abs();
				sumaRealU = listaPresupuesto.get(0).abs().subtract(listaPresupuesto.get(1));	
				cumplimiento = (sumaIngresoR.intValue() == 0 || sumaPresupuesto.intValue() == 0 )? new BigDecimal("0"):sumaIngresoR.divide(sumaPresupuesto, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
				cumplimiento = cumplimiento.setScale(2, BigDecimal.ROUND_HALF_UP);
				cumplimientoU = (sumaRealU.intValue() == 0 || sumaUtilidadP.intValue() == 0 )? new BigDecimal("0"):sumaRealU.divide(sumaUtilidadP, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
				cumplimientoU = cumplimientoU.setScale(2, BigDecimal.ROUND_HALF_UP);
				totalPresupuesto = new DecimalFormat("###,###").format(sumaPresupuesto);
				totalReal = new DecimalFormat("###,###").format(sumaIngresoR);
				totalCumplimiento = new DecimalFormat("###,###.##").format(cumplimiento);
				totalPresupuestoU = new DecimalFormat("###,###").format(sumaUtilidadP);
				totalRealU = new DecimalFormat("###,###").format(sumaRealU);
				totalCumplimientoU = new DecimalFormat("###,###.##").format(cumplimientoU);
			}
			
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
			
			autenticacion.setFechaBusqueda(fechaBusqueda);
			autenticacion.setFechaBusquedaYear(fechaBusquedaYear);

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Comision de Vendedores");
		}	
	}

	//*Lista los el plan y el real del UEN*//
	public void listarUEN(){

		try{
			if(autenticacion != null){
				autenticacion.registroIngreso(autenticacion.getUsuarioLogin());
			}
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			BigDecimal sumaRealU = new BigDecimal("0.00");
			BigDecimal sumaUtilidadP = new BigDecimal("0.00");
			BigDecimal sumaPresupuesto = new BigDecimal("0.00");
			BigDecimal sumaIngresoR = new BigDecimal("0.00");
			BigDecimal cumplimiento = new BigDecimal("0.00");
			BigDecimal cumplimientoU = new BigDecimal("0.00");
			LineaDao daoL = new LineaDao();
			ListaComisionVendedores = daoL.listarUEN(fechaBusqueda, fechaBusquedaYear );
			
			for (ComisionVendedores vendedor : ListaComisionVendedores) {
				sumaPresupuesto = sumaPresupuesto.add(vendedor.getPresupuestoB());
				sumaIngresoR = sumaIngresoR.add(vendedor.getIngresoRealB());
				sumaUtilidadP = sumaUtilidadP.add(vendedor.getUtilpresupuesto());
				sumaRealU = sumaRealU.add(vendedor.getUtilidadReal());
			}
			cumplimiento = (sumaIngresoR.intValue() == 0 || sumaPresupuesto.intValue()== 0)? new BigDecimal("0"):sumaIngresoR.divide(sumaPresupuesto, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			cumplimiento = cumplimiento.setScale(2, BigDecimal.ROUND_HALF_UP);
			cumplimientoU = (sumaRealU.intValue()== 0 || sumaUtilidadP.intValue() == 0)?new BigDecimal("0"):sumaRealU.divide(sumaUtilidadP, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			cumplimientoU = cumplimientoU.setScale(2, BigDecimal.ROUND_HALF_UP);
			totalPresupuesto = new DecimalFormat("###,###").format(sumaPresupuesto);
			totalReal = new DecimalFormat("###,###").format(sumaIngresoR);
			totalCumplimiento = new DecimalFormat("###,###.##").format(cumplimiento);
			totalPresupuestoU = new DecimalFormat("###,###").format(sumaUtilidadP);
			totalRealU = new DecimalFormat("###,###").format(sumaRealU);
			totalCumplimientoU = new DecimalFormat("###,###.##").format(cumplimientoU);

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de UEN");
		}
	}

	//*Se listan las oficinas con presupuesto y real de la UEN*//
	public void listarOficinasUEN(){

		try{
			if(autenticacion != null){
				autenticacion.registroIngreso(autenticacion.getUsuarioLogin());
			}
			ListaComisionVendedores = new ArrayList<>();
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			BigDecimal sumaRealU = new BigDecimal("0.00");
			BigDecimal sumaUtilidadP = new BigDecimal("0.00");
			BigDecimal sumaPresupuesto = new BigDecimal("0.00");
			BigDecimal sumaIngresoR = new BigDecimal("0.00");
			BigDecimal cumplimiento = new BigDecimal("0.00");
			BigDecimal cumplimientoU = new BigDecimal("0.00");
			CiudadDao daoC = new CiudadDao();
			
			ListaComisionVendedores = daoC.listarOficinasUEN(uen, fechaBusqueda, fechaBusquedaYear );
			
			for (ComisionVendedores vendedor : ListaComisionVendedores) {
				sumaPresupuesto = sumaPresupuesto.add(vendedor.getPresupuestoB());
				sumaIngresoR = sumaIngresoR.add(vendedor.getIngresoRealB());
				sumaUtilidadP = sumaUtilidadP.add(vendedor.getUtilpresupuesto());
				sumaRealU = sumaRealU.add(vendedor.getUtilidadReal());
			}
			cumplimiento = (sumaIngresoR.intValue() == 0 || sumaPresupuesto.intValue()== 0)?new BigDecimal("0"):sumaIngresoR.divide(sumaPresupuesto, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			cumplimiento = cumplimiento.setScale(2, BigDecimal.ROUND_HALF_UP);
			cumplimientoU = (sumaRealU.intValue() == 0 ||sumaUtilidadP.intValue()== 0)?new BigDecimal("0"):sumaRealU.divide(sumaUtilidadP, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			cumplimientoU = cumplimientoU.setScale(2, BigDecimal.ROUND_HALF_UP);
			totalPresupuesto = new DecimalFormat("###,###").format(sumaPresupuesto);
			totalReal = new DecimalFormat("###,###").format(sumaIngresoR);
			totalCumplimiento = new DecimalFormat("###,###.##").format(cumplimiento);
			totalPresupuestoU = new DecimalFormat("###,###").format(sumaUtilidadP);
			totalRealU = new DecimalFormat("###,###").format(sumaRealU);
			totalCumplimientoU = new DecimalFormat("###,###.##").format(cumplimientoU);

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Vendedores por Linea");
		}
	}

	//*Listas las lineas de la uen*//	
	public void listarLineasUEN(){

		try {
			if(autenticacion != null){
				autenticacion.registroIngreso(autenticacion.getUsuarioLogin());
			}
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			
			BigDecimal sumaRealU = new BigDecimal("0.00");
			BigDecimal sumaUtilidadP = new BigDecimal("0.00");
			BigDecimal sumaPresupuesto = new BigDecimal("0.00");
			BigDecimal sumaIngresoR = new BigDecimal("0.00");
			BigDecimal cumplimiento = new BigDecimal("0.00");
			BigDecimal cumplimientoU = new BigDecimal("0.00");
			LineaDao daoL = new LineaDao();
			ListaComisionVendedores = daoL.listarLineasUEN(idCiudad, uen, fechaBusqueda, fechaBusquedaYear);

			for (ComisionVendedores vendedor : ListaComisionVendedores) {
				sumaPresupuesto = sumaPresupuesto.add(vendedor.getPresupuestoB());
				sumaIngresoR = sumaIngresoR.add(vendedor.getIngresoRealB());
				sumaUtilidadP = sumaUtilidadP.add(vendedor.getUtilpresupuesto());
				sumaRealU = sumaRealU.add(vendedor.getUtilidadReal());
			}
			cumplimiento =(sumaIngresoR.intValue() == 0 || sumaPresupuesto.intValue() == 0)?new BigDecimal("0") :sumaIngresoR.divide(sumaPresupuesto, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			cumplimiento = cumplimiento.setScale(2, BigDecimal.ROUND_HALF_UP);
			cumplimientoU = (sumaRealU.intValue()== 0 || sumaUtilidadP.intValue()==0)? new BigDecimal("0"):sumaRealU.divide(sumaUtilidadP, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			cumplimientoU = cumplimientoU.setScale(2, BigDecimal.ROUND_HALF_UP);
			totalPresupuesto = new DecimalFormat("###,###").format(sumaPresupuesto);
			totalReal = new DecimalFormat("###,###").format(sumaIngresoR);
			totalCumplimiento = new DecimalFormat("###,###.##").format(cumplimiento);
			totalPresupuestoU = new DecimalFormat("###,###").format(sumaUtilidadP);
			totalRealU = new DecimalFormat("###,###").format(sumaRealU);
			totalCumplimientoU = new DecimalFormat("###,###.##").format(cumplimientoU);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista del Plan");
		}
	}	

	//*lista el presupuesto y el real de los vendedores UEN *//
	@SuppressWarnings({ "unused" })
	public void listarVendedoresUEN(){

		try{
			if(autenticacion != null){
				autenticacion.registroIngreso(autenticacion.getUsuarioLogin());
			}
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();	
			int progress1  = 0;
			setProgress(0);
			BigDecimal sumaPresupuesto = new BigDecimal("0");
			BigDecimal sumaReal= new BigDecimal("0");
			if(fechaBusqueda != null && fechaBusquedaYear != null){

				List<Funcionario> listaVendedor = null;
				int t = 0;
				ListaComisionVendedores = new ArrayList<>();
				FuncionarioDao daoF = new FuncionarioDao();
				List<Integer>  result; 
				if(autenticacion.getTipoVendedor().equals("I") ){
					tipo="funcionarioI";
					result = daoF.listarVendedoresUEN(tipo, idLinea, idOficina, uen, fechaBusqueda, fechaBusquedaYear);
					//listaVendedor = daoF.listarVendedoresInternos();
				}
				else{
					tipo="funcionario";
					result = daoF.listarVendedoresUEN(tipo, idLinea, idOficina, uen, fechaBusqueda, fechaBusquedaYear);
					//listaVendedor = daoF.listarVendedores();
				}
				progress1 = 100 /result.size();
				LineaDao daoL = new LineaDao();
				for (Integer id : result) {

					ComisionVendedores vendedores = new ComisionVendedores();
					vendedores = daoL.listarVendedorUEN(tipo, id, idLinea, idOficina, uen, fechaBusqueda, fechaBusquedaYear);
					
					sumaPresupuesto = sumaPresupuesto.add(vendedores.getPresupuestoB());
					sumaReal = sumaReal.add(vendedores.getIngresoRealB());
					ListaComisionVendedores.add(vendedores);

					t = t + progress1;
					setProgress(t);
				}
				
				totalPresupuesto = new DecimalFormat("###,###").format(sumaPresupuesto);
				totalReal = new DecimalFormat("###,###").format(sumaReal);
			}
			if(progress > 90){
				setProgress(100);
			}	
			autenticacion.setFechaBusqueda(fechaBusqueda);
			autenticacion.setFechaBusquedaYear(fechaBusquedaYear);

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Comision de Vendedores");
		}	
	}
	
	@PostConstruct
	public void inicioVista(){
		
		try{
			  
			if(fechaConsulta == null){
				Calendar fechas = Calendar.getInstance();
				int month = fechas.get(Calendar.MONTH)+1;
				for (Fechas fecha: listaFechas) {
					fechaConsulta  = (month<10)?(fecha.getValorMes().equals(String.valueOf("0"+month)))? fecha.getMes():fechaConsulta:(fecha.getValorMes().equals(String.valueOf(""+month)))? fecha.getMes(): fechaConsulta;
				}
				listarPais1();
			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la vista Inicial");
		}
	}

	//*Lista pais total*//
	public void listarPais1(){

		try {
			if(autenticacion != null){
				autenticacion.registroIngreso(autenticacion.getUsuarioLogin());
			}
			
			for (Fechas fecha: listaFechas) {
				fechaConsulta = (fecha.getValorMes().equals(autenticacion.getFechaBusqueda()))? fecha.getMes() : fechaConsulta;
			}
			
			 
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			listaplan = new ArrayList<>();

			Plan plan = new Plan();
			LineaDao daoL = new LineaDao();			
			List<BigDecimal> listaPresupuesto = daoL.listaPais(fechaBusqueda, fechaBusquedaYear);

			EsquemasDao daoE = new EsquemasDao();
			List<Esquemas> esquema =  daoE.listar();

			plan.setIngreso(listaPresupuesto.get(2));
			plan.setUtilidad(listaPresupuesto.get(3));
			plan.setIngreso_Real(listaPresupuesto.get(0).abs());
			
			plan.setUtilidad_Real(listaPresupuesto.get(0).abs().subtract(listaPresupuesto.get(1)));	
			
			System.out.println(plan.getIngreso_Real() +"  --  "+ plan.getIngreso());
			plan.setIngreso_Cumplimiento((plan.getIngreso_Real().intValue() == 0 || plan.getIngreso().intValue() == 0 )? new BigDecimal("0") :plan.getIngreso_Real().divide(plan.getIngreso(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP));
			plan.getIngreso_Cumplimiento();
			String semaforo = (plan.getIngreso_Cumplimiento().intValue() >= esquema.get(0).getUmbralComision().intValue())? "verde.png" : "rojo.png";
			plan.setImagen1(semaforo);
			plan.setUtilidad_Cumplimiento((plan.getUtilidad_Real().intValue()== 0 || plan.getUtilidad().intValue() == 0)? new BigDecimal("0") :plan.getUtilidad_Real().divide(plan.getUtilidad(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP));
			semaforo = (plan.getUtilidad_Cumplimiento().intValue() >= esquema.get(0).getUmbralComision().intValue())? "verde.png" : "rojo.png";
			plan.setImagen(semaforo);
			plan.setUmbral(esquema.get(0).getUmbralComision());
			listaplan.add(plan);
			//ListaComisionVendedores.add(vendedores);

			if(progress > 90){
				setProgress(100);
			}	

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se cargo la lista Pais");
		}
	}

	//*Trae los datos de los funcionarios por linea del detalle*//
	public void listaLineasFuncionario(){

		try{
			if(autenticacion != null){
				autenticacion.registroIngreso(autenticacion.getUsuarioLogin());
			}
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();	
			total =  new BigDecimal("0.00");
			totalR =  new BigDecimal("0.00");
			BigDecimal totalPresupuest = new BigDecimal("0.00");
			BigDecimal totalPresupuestoUtilidad = new BigDecimal("0.00");
			BigDecimal totalValorReal = new BigDecimal("0.00");
			BigDecimal totalValorUtilidad = new BigDecimal("0.00");
			BigDecimal valorReal = new BigDecimal("0.00");
			BigDecimal valorUtilidad = new BigDecimal("0.00");
			BigDecimal valorUtilidadR = new BigDecimal("0.00");
			int numero = 0;
			int numero1 = 0;
			int i = 0;
//			FuncionarioDao daoF = new FuncionarioDao();
			//Funcionario funcionario = daoF.buscarPersona(autenticacion.getUsuarioLogin().getPersona().getCedula());
//			Funcionario funcionario;
			tipo = (tipo == null || tipo.equals("")) ? (autenticacion.getTipoVendedor().equals("I") )? "funcionarioI" : "funcionario" : (tipo.equals("I"))? "funcionarioI" : "funcionario" ;
			
			DetalleDao daoD = new DetalleDao();	
			if(fechaBusqueda != null && fechaBusquedaYear != null){

				//funcionario = daoF.buscarPersona(idFuncionario);
				//nombreRegistro = funcionario.getPersona().getNombre();
				listaplanV = daoD.listarPlanPorFechasPrueba(tipo, idFuncionario, fechaBusqueda, fechaBusquedaYear);
				

				int progress1 = 100/listaplanV.size();
				Plan planLinea2_3 = null;
				for (Plan plan : listaplanV) {

					if(tipo == "funcionarioI" &&  plan.getLinea().getId() == 2){
						planLinea2_3 = new Plan();
						for(int k=0; k<listaplanV.size(); k++){

							if(listaplanV.get(k).getLinea().getId() == 3)
								planLinea2_3 = listaplanV.get(k);
						}
						plan.setIngreso_Real(plan.getIngreso_Real().add(planLinea2_3.getIngreso_Real()));
						plan.setUtilidad_Real(plan.getUtilidad_Real().add(planLinea2_3.getUtilidad_Real()));
						plan.setIngreso(plan.getIngreso().add(planLinea2_3.getIngreso()));
						plan.setUtilidad(plan.getUtilidad().add(planLinea2_3.getUtilidad()));
					}
					else if(tipo == "funcionarioI" &&  plan.getLinea().getId() == 3){
						plan.setIngreso_Real(new BigDecimal("0.00"));
						plan.setUtilidad_Real(new BigDecimal("0.00"));
						plan.setIngreso(new BigDecimal("0.00"));
						plan.setUtilidad(new BigDecimal("0.00"));
					}

					totalPresupuest = totalPresupuest.add(plan.getIngreso());
					totalPresupuestoUtilidad = totalPresupuestoUtilidad.add(plan.getUtilidad()); 
					totalValorReal = totalValorReal.add(plan.getIngreso_Real());
					totalValorUtilidad = totalValorUtilidad.add(plan.getUtilidad_Real());
					//					totalPreIng = new DecimalFormat("###,###").format(totalPresupuesto);
					//					totalPreUti = new DecimalFormat("###,###").format(totalPresupuestoUtilidad);
					//					totalRealIng = new DecimalFormat("###,###").format(totalValorReal);
					//					totalRealUti = new DecimalFormat("###,###").format(totalValorUtilidad);
					//					
					if (plan.getIngreso() == null || plan.getIngreso().compareTo(BigDecimal.ZERO) == 0  ){
						valorReal = new BigDecimal("0");
						plan.setIngreso_Cumplimiento(valorReal.setScale(2, BigDecimal.ROUND_HALF_UP));
					}
					else{
						valorReal = plan.getIngreso_Real().divide(plan.getIngreso(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
						plan.setIngreso_Cumplimiento(valorReal.setScale(2, BigDecimal.ROUND_HALF_UP));
					}
					//					
					if(plan.getUtilidad() == null || plan.getUtilidad().compareTo(BigDecimal.ZERO) == 0){
						valorReal = new BigDecimal("0");
						plan.setUtilidad_Cumplimiento(valorReal.setScale(2, BigDecimal.ROUND_HALF_UP));
					}
					else{
						valorReal = plan.getUtilidad_Real().divide(plan.getUtilidad(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
						plan.setUtilidad_Cumplimiento(valorReal.setScale(2, BigDecimal.ROUND_HALF_UP));
					}

					//					if(umbral != null){
					//						plan.setUmbral(umbral);
					//						numero = umbral.compareTo(plan.getUtilidad_Cumplimiento().divide(new BigDecimal("100.00")));
					//						numero1 = umbral.compareTo(plan.getIngreso_Cumplimiento().divide(new BigDecimal("100.00")));
					//					}
					//					else{
					plan.setUmbral(plan.getFuncionario().getComision().getUmbralVenta());
//					plan.getFuncionario().getComision().setUmbralVenta(new BigDecimal("0.85"));
//					
					numero = plan.getFuncionario().getComision().getUmbralVenta().compareTo(plan.getUtilidad_Cumplimiento().divide(new BigDecimal("100.00")));
					numero1 = plan.getFuncionario().getComision().getUmbralVenta().compareTo(plan.getIngreso_Cumplimiento().divide(new BigDecimal("100.00")));
					//					}

					if(numero == 1 ){
						plan.setValor_Comision_Pagar(new BigDecimal("0.00"));
						total = total.add(plan.getValor_Comision_Pagar());
						//accion= new DecimalFormat("###,###").format(total);
						plan.setImagen("rojo.png");
					}
					else{

						if(plan.getLinea().getId() == 15){

							if(plan.getUtilidad_Real().intValue() >= 137891000){
								plan.setValor_Comision_Pagar(new BigDecimal("0.00"));
								total = total.add(plan.getValor_Comision_Pagar());
								//accion= new DecimalFormat("###,###").format(total);
								plan.setImagen("rojo.png");
							}
							else{
								valorUtilidad= plan.getDistribucion_Linea().multiply(plan.getFuncionario().getComision().getValorBaseVenta().divide(new BigDecimal("100.00")));
								valorUtilidad= valorUtilidad.multiply(plan.getUtilidad_Cumplimiento()); 
								plan.setValor_Comision_Pagar(valorUtilidad);
								total = total.add(plan.getValor_Comision_Pagar());
								//accion= new DecimalFormat("###,###").format(total);
								plan.setImagen("verde.png");
							}
						}
						else{
							valorUtilidad= plan.getDistribucion_Linea().multiply(plan.getFuncionario().getComision().getValorBaseVenta().divide(new BigDecimal("100.00")));
							valorUtilidad= valorUtilidad.multiply(plan.getUtilidad_Cumplimiento());
							plan.setValor_Comision_Pagar(valorUtilidad);
							total = total.add(plan.getValor_Comision_Pagar());
							//accion= new DecimalFormat("###,###").format(total);
							plan.setImagen("verde.png");
						}
					}

					if(numero1 == 1 ){
						plan.setValor_Comision_PagarR(new BigDecimal("0.00"));
						totalR = totalR.add(plan.getValor_Comision_PagarR());
						//accionR= new DecimalFormat("###,###").format(totalR);
						plan.setImagen("rojo.png");
					}
					else{

						if(plan.getLinea().getId() == 15){
							if(plan.getIngreso_Real().intValue() >= 137891000){
								plan.setValor_Comision_PagarR(new BigDecimal("0.00"));
								totalR = totalR.add(plan.getValor_Comision_PagarR());
								//accionR= new DecimalFormat("###,###").format(totalR);
								plan.setImagen("rojo.png");
							}
							else{
								valorUtilidadR= plan.getDistribucion_Linea().multiply(plan.getFuncionario().getComision().getValorBaseVenta().divide(new BigDecimal("100.00")));
								valorUtilidadR= valorUtilidadR.multiply(plan.getIngreso_Cumplimiento()); 
								plan.setValor_Comision_PagarR(valorUtilidadR);
								totalR = totalR.add(plan.getValor_Comision_PagarR());
								//accionR = new DecimalFormat("###,###").format(totalR);
								plan.setImagen("verde.png");
							}
						}
						else{
							valorUtilidadR= plan.getDistribucion_Linea().multiply(plan.getFuncionario().getComision().getValorBaseVenta().divide(new BigDecimal("100.00")));
							valorUtilidadR= valorUtilidadR.multiply(plan.getIngreso_Cumplimiento()); 
							plan.setValor_Comision_PagarR(valorUtilidadR);
							totalR = totalR.add(plan.getValor_Comision_PagarR());
							//accionR = new DecimalFormat("###,###").format(totalR);
							plan.setImagen("verde.png");
						}
					}
					plan.setDistribucion_Linea(plan.getDistribucion_Linea().multiply(new BigDecimal("100")));
					//					plan.setDistribucion_Linea(plan.getDistribucion_Linea().setScale(2, BigDecimal.ROUND_HALF_UP));
					i += progress1;
					setProgress(i); 
				}
				totalPresupuesto = new DecimalFormat("###,###").format(totalPresupuest);
				totalReal = new DecimalFormat("###,###").format(totalValorReal);
				valorUtilidad = (totalValorReal.intValue() == 0 || totalPresupuest.intValue() == 0 )? new BigDecimal("0") :totalValorReal.divide(totalPresupuest, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
				valorUtilidad = valorUtilidad.setScale(2, BigDecimal.ROUND_HALF_UP);
				totalCumplimiento = new DecimalFormat("###,###.##").format(valorUtilidad);
				totalPresupuestoU = new DecimalFormat("###,###").format(totalPresupuestoUtilidad);
				totalRealU = new DecimalFormat("###,###").format(totalValorUtilidad);
				valorUtilidadR = (totalValorUtilidad.intValue() == 0 || totalPresupuestoUtilidad.intValue() == 0)? new BigDecimal("0") :totalValorUtilidad.divide(totalPresupuestoUtilidad, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
				valorUtilidadR = valorUtilidadR.setScale(2, BigDecimal.ROUND_HALF_UP);
				totalCumplimientoU = new DecimalFormat("###,###.##").format(valorUtilidadR);
				if(progress > 90){
					setProgress(100);
				}
			}
			autenticacion.setFechaBusqueda(fechaBusqueda);
			autenticacion.setFechaBusquedaYear(fechaBusquedaYear);


		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se cargo la lista Pais");
		}
	}

	public void onComplete() {
		//progress = 0;
		Messages.addGlobalError("Proceso Completado");
	}


	public AutenticacionBean getAutenticacion() {
		return autenticacion;
	}

	public void setAutenticacion(AutenticacionBean autenticacion) {
		this.autenticacion = autenticacion;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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

	public List<Plan> getListaplan() {
		return listaplan;
	}

	public void setListaplan(List<Plan> listaplan) {
		this.listaplan = listaplan;
	}

	public int getIdCiudad() {
		return idCiudad;
	}

	public void setIdCiudad(int idCiudad) {
		this.idCiudad = idCiudad;
	}

	public int getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(int idLinea) {
		this.idLinea = idLinea;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public String getLinea() {
		return linea;
	}

	public void setLinea(String linea) {
		this.linea = linea;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public int getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(int idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public String getUen() {
		return uen;
	}

	public void setUen(String uen) {
		this.uen = uen;
	}

	public int getIdOficina() {
		return idOficina;
	}

	public void setIdOficina(int idOficina) {
		this.idOficina = idOficina;
	}
	public String getFechaConsulta() {
		return fechaConsulta;
	}
	public void setFechaConsulta(String fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}
	public String getNombreEspecialista() {
		return nombreEspecialista;
	}
	public void setNombreEspecialista(String nombreEspecialista) {
		this.nombreEspecialista = nombreEspecialista;
	}
	public String getNombreUEN() {
		return nombreUEN;
	}
	public void setNombreUEN(String nombreUEN) {
		this.nombreUEN = nombreUEN;
	}
	public String getNombreOficina() {
		return nombreOficina;
	}

	public void setNombreOficina(String nombreOficina) {
		this.nombreOficina = nombreOficina;
	}
	public String getTotalPresupuesto() {
		return totalPresupuesto;
	}
	public void setTotalPresupuesto(String totalPresupuesto) {
		this.totalPresupuesto = totalPresupuesto;
	}
	public String getTotalReal() {
		return totalReal;
	}
	public void setTotalReal(String totalReal) {
		this.totalReal = totalReal;
	}
	public String getTotalCumplimiento() {
		return totalCumplimiento;
	}
	public void setTotalCumplimiento(String totalCumplimiento) {
		this.totalCumplimiento = totalCumplimiento;
	}
	public String getTotalPresupuestoU() {
		return totalPresupuestoU;
	}
	public void setTotalPresupuestoU(String totalPresupuestoU) {
		this.totalPresupuestoU = totalPresupuestoU;
	}
	public String getTotalRealU() {
		return totalRealU;
	}
	public void setTotalRealU(String totalRealU) {
		this.totalRealU = totalRealU;
	}
	public String getTotalCumplimientoU() {
		return totalCumplimientoU;
	}
	public void setTotalCumplimientoU(String totalCumplimientoU) {
		this.totalCumplimientoU = totalCumplimientoU;
	}
	public int getInicio() {
		return inicio;
	}
	public void setInicio(int inicio) {
		this.inicio = inicio;
	}
}
