package co.com.CGAwebComercial.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import co.com.CGAwebComercial.dao.DetalleDao;
import co.com.CGAwebComercial.dao.FuncionarioDao;
import co.com.CGAwebComercial.dao.PromedioVentaDao;
import co.com.CGAwebComercial.dao.RecaudoDao;
import co.com.CGAwebComercial.entyties.Detalle_venta;
import co.com.CGAwebComercial.entyties.Detallesin;
import co.com.CGAwebComercial.entyties.Funcionario;
import co.com.CGAwebComercial.entyties.Plan;
import co.com.CGAwebComercial.entyties.PromedioVenta;
import co.com.CGAwebComercial.entyties.Recaudo;
import co.com.CGAwebComercial.resource.Recursos;
import co.com.CGAwebComercial.util.Fechas;


@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class PlanBean implements Serializable {
	
	@ManagedProperty("#{autenticacionBean}")
	private AutenticacionBean autenticacion;

	private List<Plan> listaplan;
	private Recursos recurso;
	private Plan plan;
	private List<Detalle_venta> listadetalle;
	private List<Detallesin> listaDetalle1;
	private List<Fechas> listaFechas;
	private List<Fechas> listaFechasR;
	
	private LineChartModel desempenoVentas;
	private LineChartModel desempenoRecaudo;
	
	private PromedioVenta promedioVenta;
	
	private int codigo;
	private int idPersona;
	private String accion;
	private String accionR;
	private String totalPreIng;
	private String totalPreUti;
	private String totalRealIng;
	private String totalRealUti;
	private String totalD;
	private Date fechaInicial;
	private Date fechaFinal;
	private BigDecimal total;
	private BigDecimal totalR;
	private BigDecimal umbral;
	private int totalDetalle;
	private String fechaActual;
	private String fechaBusqueda;
	private String fechaBusquedaYear;
	private String fechaConsulta;
	private Double promedioMes;
	private String tipo;
	
	
	@PostConstruct
	public void inicioVista(){

		try{
			recurso = new Recursos();
			listaFechas = recurso.cargarFechas();
			if(fechaConsulta == null){
				
				Calendar fechas = Calendar.getInstance();
				int month = fechas.get(Calendar.MONTH)+1;
				for (Fechas fecha: listaFechas) {
					System.out.println(fecha.getValorMes() + "/////////////////" + month);
					fechaConsulta  = (month<10)?(fecha.getValorMes().equals(String.valueOf("0"+month)))? fecha.getMes():fechaConsulta:(fecha.getValorMes().equals(String.valueOf(""+month)))? fecha.getMes(): fechaConsulta;
				}
				System.out.println(fechaConsulta + "$$$$$$$$$$");
				listarPlan();
			}

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo La vista de Inicio");
		}
	}
	
	
	
	//*Lista los datos del Plan del vendedor interno y externo *//
	
	public void listarPlan(){
		
		try {
			for (Fechas fecha: listaFechas) {
				fechaConsulta = (fecha.getValorMes().equals(autenticacion.getFechaBusqueda()))? fecha.getMes() : fechaConsulta;
			}
			plan = new Plan();
			tipo = (autenticacion.getUsuarioLogin().getPerfil().getId() == 1)? "funcionario" : "funcionarioI";
			if(autenticacion.getFechaBusqueda() != null && autenticacion.getFechaBusquedaYear() != null){
				fechaBusqueda = autenticacion.getFechaBusqueda();
				fechaBusquedaYear = autenticacion.getFechaBusquedaYear();	
				listarPlanPorFechas();
			}
			else{
				total =  new BigDecimal("0.00");
				BigDecimal totalPresupuesto = new BigDecimal("0.00");
				BigDecimal totalPresupuestoUtilidad = new BigDecimal("0.00");
				BigDecimal totalValorReal = new BigDecimal("0.00");
				BigDecimal valorReal = new BigDecimal("0.00");				
				BigDecimal totalValorUtilidad = new BigDecimal("0.00");
				BigDecimal valorUtilidad = new BigDecimal("0.00");
				int numero = 0;

				FuncionarioDao daoF = new FuncionarioDao();
				Funcionario funcionario = daoF.buscarPersona(autenticacion.getUsuarioLogin().getPersona().getCedula());
				DetalleDao daoD = new DetalleDao();
				listaplan = daoD.listarPlanSinPrueba(tipo, funcionario.getId_funcionario());
				//listaplan = daoD.listarPlan(funcionario.getId_funcionario());
			Plan planLinea2_3 = null;	
			for (Plan plan : listaplan) {
					
					if(tipo == "funcionarioI" &&  plan.getLinea().getId() == 2){
						planLinea2_3 = new Plan();
						for(int k=0; k<listaplan.size(); k++){
							
							if(listaplan.get(k).getLinea().getId() == 3)
								planLinea2_3 = listaplan.get(k);
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
					
					totalPresupuesto = totalPresupuesto.add(plan.getIngreso());
					totalPresupuestoUtilidad = totalPresupuestoUtilidad.add(plan.getUtilidad()); 
					totalValorReal = totalValorReal.add(plan.getIngreso_Real());
					totalValorUtilidad = totalValorUtilidad.add(plan.getUtilidad_Real());
					totalPreIng = new DecimalFormat("###,###").format(totalPresupuesto);
					totalPreUti = new DecimalFormat("###,###").format(totalPresupuestoUtilidad);
					totalRealIng = new DecimalFormat("###,###").format(totalValorReal);
					totalRealUti = new DecimalFormat("###,###").format(totalValorUtilidad);
					
					if (plan.getIngreso() == null || plan.getIngreso().compareTo(BigDecimal.ZERO) == 0  ){
						valorReal = new BigDecimal("0");
						plan.setIngreso_Cumplimiento(valorReal.setScale(2, BigDecimal.ROUND_HALF_UP));
					}
					else{
						valorReal = plan.getIngreso_Real().divide(plan.getIngreso(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
						plan.setIngreso_Cumplimiento(valorReal.setScale(2, BigDecimal.ROUND_HALF_UP));
					}
					
					if(plan.getUtilidad() == null || plan.getUtilidad().compareTo(BigDecimal.ZERO) == 0){
						valorReal = new BigDecimal("0");
						plan.setUtilidad_Cumplimiento(valorReal.setScale(2, BigDecimal.ROUND_HALF_UP));
					}
					else{
						valorReal = plan.getUtilidad_Real().divide(plan.getUtilidad(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
						plan.setUtilidad_Cumplimiento(valorReal.setScale(2, BigDecimal.ROUND_HALF_UP));
					}
					
					if(umbral != null){
						plan.setUmbral(umbral);
						numero = umbral.compareTo(plan.getIngreso_Cumplimiento().divide(new BigDecimal("100.00")));
					}
					else{
					    plan.setUmbral(plan.getFuncionario().getComision().getUmbralVenta());
					    numero = plan.getFuncionario().getComision().getUmbralVenta().compareTo(plan.getIngreso_Cumplimiento().divide(new BigDecimal("100.00")));
					}
					if(numero == 1 ){
						plan.setValor_Comision_Pagar(new BigDecimal("0.00"));
						total = total.add(plan.getValor_Comision_Pagar());
						accion= new DecimalFormat("###,###").format(total);
						plan.setImagen("rojo.png");
					}
					else{
						
						if(plan.getLinea().getId() == 15){
							if(plan.getIngreso_Real().intValue() >= 137891000){
								plan.setValor_Comision_Pagar(new BigDecimal("0.00"));
								total = total.add(plan.getValor_Comision_Pagar());
								accion= new DecimalFormat("###,###").format(total);
								plan.setImagen("rojo.png");
							}
							else{
								valorUtilidad= plan.getDistribucion_Linea().multiply(plan.getFuncionario().getComision().getValorBaseVenta().divide(new BigDecimal("100.00")));
								valorUtilidad= valorUtilidad.multiply(plan.getIngreso_Cumplimiento()); 
								plan.setValor_Comision_Pagar(valorUtilidad);
								total = total.add(plan.getValor_Comision_Pagar());
								accion = new DecimalFormat("###,###").format(total);
								plan.setImagen("verde.png");
							}
						}
						else{
//							valorUtilidad= plan.getDistribucion_Linea().multiply(plan.getFuncionario().getComision().getValorBaseVenta().divide(new BigDecimal("100.00")));
//							valorUtilidad= valorUtilidad.multiply(plan.getIngreso_Cumplimiento()); 
//							plan.setValor_Comision_PagarR(valorUtilidad);
//							total = total.add(plan.getValor_Comision_Pagar());
//							accion = new DecimalFormat("###,###").format(total);
							plan.setImagen("verde.png");
						}
					}
					plan.setDistribucion_Linea(plan.getDistribucion_Linea().multiply(new BigDecimal("100")));
			}
			autenticacion.setFechaBusqueda(fechaBusqueda);
			autenticacion.setFechaBusquedaYear(fechaBusquedaYear);
					
					
			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista del Plan");
		}
	}
	
	//*Lista los datos del Plan del vendedor interno y externo  Por fecha de busqueda*//
	public void listarPlanPorFechas(){
		
		try {
			for (Fechas fecha: listaFechas) {
				fechaConsulta = (fecha.getValorMes().equals(autenticacion.getFechaBusqueda()))? fecha.getMes() : fechaConsulta;
			}
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();	
			//tipo =(autenticacion.getUsuarioLogin().getId() == 4)? "funcionario" : "funcionarioI";
			total =  new BigDecimal("0.00");
			totalR =  new BigDecimal("0.00");
			BigDecimal totalPresupuesto = new BigDecimal("0.00");
			BigDecimal totalPresupuestoUtilidad = new BigDecimal("0.00");
			BigDecimal totalValorReal = new BigDecimal("0.00");
			BigDecimal totalValorUtilidad = new BigDecimal("0.00");
			BigDecimal valorReal = new BigDecimal("0.00");
			BigDecimal valorUtilidad = new BigDecimal("0.00");
			BigDecimal valorUtilidadR = new BigDecimal("0.00");
			BigDecimal cumplimiento = new BigDecimal("0.00");
			int numero = 0;
			int numero1 = 0;
			//int i = 0;
			
			FuncionarioDao daoF = new FuncionarioDao();
			Funcionario funcionario = daoF.buscarPersona(autenticacion.getUsuarioLogin().getPersona().getCedula());
			
			tipo = (autenticacion.getUsuarioLogin().getPerfil().getId() == 1)? "funcionario" : "funcionarioI"; 
			DetalleDao daoD = new DetalleDao();
			
			if(fechaBusqueda != null && fechaBusquedaYear != null){
				
				listaplan = daoD.listarPlanPorFechasSinPrueba(tipo, funcionario.getId_funcionario(), fechaBusqueda, fechaBusquedaYear);
				
				//int progress1 = 100/listaplanV.size();
				Plan planLinea2_3 = null;
				for (Plan plan : listaplan) {
					
					if(tipo == "funcionarioI" &&  plan.getLinea().getId() == 2){
						planLinea2_3 = new Plan();
						for(int k=0; k<listaplan.size(); k++){
							
							if(listaplan.get(k).getLinea().getId() == 3)
								planLinea2_3 = listaplan.get(k);
						}
						
						plan.setIngreso_Real(plan.getIngreso_Real().add(planLinea2_3.getIngreso_Real()));
						plan.setUtilidad_Real(plan.getUtilidad_Real().add(planLinea2_3.getUtilidad_Real()));
						
					}
					else if(tipo == "funcionarioI" &&  plan.getLinea().getId() == 3){
						plan.setIngreso_Real(new BigDecimal("0.00"));
						plan.setUtilidad_Real(new BigDecimal("0.00"));
					}
					totalPresupuesto = totalPresupuesto.add(plan.getIngreso());
					totalPresupuestoUtilidad = totalPresupuestoUtilidad.add(plan.getUtilidad()); 
					totalValorReal = totalValorReal.add(plan.getIngreso_Real());
					totalValorUtilidad = totalValorUtilidad.add(plan.getUtilidad_Real());
					totalPreIng = new DecimalFormat("###,###").format(totalPresupuesto);
					totalPreUti = new DecimalFormat("###,###").format(totalPresupuestoUtilidad);
					totalRealIng = new DecimalFormat("###,###").format(totalValorReal);
					totalRealUti = new DecimalFormat("###,###").format(totalValorUtilidad);
					cumplimiento =(totalValorReal.longValue() == 0 || totalPresupuesto.longValue() == 0)? new BigDecimal("0") :  totalValorReal.divide(totalPresupuesto, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
					totalD = new DecimalFormat("###,###.##").format(cumplimiento);
					
					if (plan.getIngreso() == null || plan.getIngreso().compareTo(BigDecimal.ZERO) == 0  ){
						valorReal = new BigDecimal("0");
						plan.setIngreso_Cumplimiento(valorReal.setScale(2, BigDecimal.ROUND_HALF_UP));
					}
					else{
						valorReal = plan.getIngreso_Real().divide(plan.getIngreso(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
						plan.setIngreso_Cumplimiento(valorReal.setScale(2, BigDecimal.ROUND_HALF_UP));
					}
					
					if(plan.getUtilidad() == null || plan.getUtilidad().compareTo(BigDecimal.ZERO) == 0){
						valorReal = new BigDecimal("0");
						plan.setUtilidad_Cumplimiento(valorReal.setScale(2, BigDecimal.ROUND_HALF_UP));
					}
					else{
						valorReal = plan.getUtilidad_Real().divide(plan.getUtilidad(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
						plan.setUtilidad_Cumplimiento(valorReal.setScale(2, BigDecimal.ROUND_HALF_UP));
					}
					
					if(umbral != null){
						plan.setUmbral(umbral);
						numero = umbral.compareTo(plan.getUtilidad_Cumplimiento().divide(new BigDecimal("100.00")));
						numero1 = umbral.compareTo(plan.getIngreso_Cumplimiento().divide(new BigDecimal("100.00")));
					}
					else{
					    plan.setUmbral(plan.getFuncionario().getComision().getUmbralVenta());
					    numero = plan.getFuncionario().getComision().getUmbralVenta().compareTo(plan.getUtilidad_Cumplimiento().divide(new BigDecimal("100.00")));
					    numero1 = plan.getFuncionario().getComision().getUmbralVenta().compareTo(plan.getIngreso_Cumplimiento().divide(new BigDecimal("100.00")));
					}
					
					if(numero == 1 ){
						plan.setValor_Comision_Pagar(new BigDecimal("0.00"));
						total = total.add(plan.getValor_Comision_Pagar());
						accion= new DecimalFormat("###,###").format(total);
						plan.setImagen("rojo.png");
					}
					else{
						
						if(plan.getLinea().getId() == 15){
							
							if(plan.getUtilidad_Real().intValue() >= 137891000){
								plan.setValor_Comision_Pagar(new BigDecimal("0.00"));
								total = total.add(plan.getValor_Comision_Pagar());
								accion= new DecimalFormat("###,###").format(total);
								plan.setImagen("rojo.png");
							}
							else{
								valorUtilidad= plan.getDistribucion_Linea().multiply(plan.getFuncionario().getComision().getValorBaseVenta().divide(new BigDecimal("100.00")));
								valorUtilidad= valorUtilidad.multiply(plan.getUtilidad_Cumplimiento()); 
								plan.setValor_Comision_Pagar(valorUtilidad);
								total = total.add(plan.getValor_Comision_Pagar());
								accion= new DecimalFormat("###,###").format(total);
								plan.setImagen("verde.png");
							}
						}
						else{
							//valorUtilidad= plan.getDistribucion_Linea().multiply(plan.getFuncionario().getComision().getValorBaseVenta().divide(new BigDecimal("100.00")));
							valorUtilidad= valorUtilidad.multiply(plan.getUtilidad_Cumplimiento());
							plan.setValor_Comision_Pagar(valorUtilidad);
							total = total.add(plan.getValor_Comision_Pagar());
							accion= new DecimalFormat("###,###").format(total);
							plan.setImagen("verde.png");
						}
					}
					
					if(numero1 == 1 ){
						plan.setValor_Comision_PagarR(new BigDecimal("0.00"));
						totalR = totalR.add(plan.getValor_Comision_PagarR());
						accionR= new DecimalFormat("###,###").format(totalR);
						plan.setImagen("rojo.png");
					}
					else{
						
						if(plan.getLinea().getId() == 15){
							if(plan.getIngreso_Real().intValue() >= 137891000){
								plan.setValor_Comision_PagarR(new BigDecimal("0.00"));
								totalR = totalR.add(plan.getValor_Comision_PagarR());
								accionR= new DecimalFormat("###,###").format(totalR);
								plan.setImagen("rojo.png");
							}
							else{
								valorUtilidadR= plan.getDistribucion_Linea().multiply(plan.getFuncionario().getComision().getValorBaseVenta().divide(new BigDecimal("100.00")));
								valorUtilidadR= valorUtilidadR.multiply(plan.getIngreso_Cumplimiento()); 
								plan.setValor_Comision_PagarR(valorUtilidadR);
								totalR = totalR.add(plan.getValor_Comision_PagarR());
								accionR = new DecimalFormat("###,###").format(totalR);
								plan.setImagen("verde.png");
							}
						}
						else{
							//valorUtilidadR= plan.getDistribucion_Linea().multiply(plan.getFuncionario().getComision().getValorBaseVenta().divide(new BigDecimal("100.00")));
							valorUtilidadR= valorUtilidadR.multiply(plan.getIngreso_Cumplimiento()); 
							plan.setValor_Comision_PagarR(valorUtilidadR);
							totalR = totalR.add(plan.getValor_Comision_PagarR());
							accionR = new DecimalFormat("###,###").format(totalR);
							plan.setImagen("verde.png");
						}
					}
					//plan.setDistribucion_Linea(plan.getDistribucion_Linea().multiply(new BigDecimal("100")));
//					plan.setDistribucion_Linea(plan.getDistribucion_Linea().setScale(2, BigDecimal.ROUND_HALF_UP));
					
				}
				
			}
			autenticacion.setFechaBusqueda(fechaBusqueda);
			autenticacion.setFechaBusquedaYear(fechaBusquedaYear);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista del Plan");
		}
	}
	
	public void listarDetalle(){
		
		try{
			if(autenticacion.getFechaBusqueda() != null && autenticacion.getFechaBusquedaYear() != null){
				DetalleDao dao = new DetalleDao();
				listaDetalle1 = dao.listarDetallePorFecha(tipo,codigo, idPersona, autenticacion.getFechaBusqueda(), autenticacion.getFechaBusquedaYear());
				int i = 0;
				for (Detallesin detalle : listaDetalle1) {
					listaDetalle1.get(i).setValorNeto(listaDetalle1.get(i).getValorNeto()* -1);
					totalDetalle += detalle.getValorNeto();
					i++;
				}
				totalD = new DecimalFormat("###,###").format(totalDetalle);
			}
			else{
				DetalleDao dao = new DetalleDao();
				listaDetalle1 = dao.listarDetallePorFecha(tipo,codigo, idPersona, autenticacion.getFechaBusqueda(), autenticacion.getFechaBusquedaYear());
				int i = 0;
				for (Detallesin detalle : listaDetalle1) {
					listaDetalle1.get(i).setValorNeto(listaDetalle1.get(i).getValorNeto()* -1);
					totalDetalle += detalle.getValorNeto();
					i++;
				}
				totalD = new DecimalFormat("###,###").format(totalDetalle);
			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista del Detalle");
		}
	}
	
	public void desempenoVentas(){
		
		try{
			
			if(desempenoVentas == null){
				LineChartModel model = new LineChartModel();
				LineChartModel model2 = new LineChartModel();
				LineChartSeries presupuesto = new LineChartSeries();
				LineChartSeries ventas = new LineChartSeries();
				
				LineChartSeries presupuestoI = new LineChartSeries();
				LineChartSeries realI = new LineChartSeries();
				LineChartSeries presupuestoU = new LineChartSeries();
				//LineChartSeries realU = new LineChartSeries();
				
				RecaudoDao dao = new RecaudoDao();
				List<Recaudo> listaRecaudo = dao.listarPresupuesto(autenticacion.getUsuarioLogin().getPersona().getCedula());
				int i=0;
				String[] periodo = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
									"Julio", "Agosto", "Septiembre", "Octubre", "Noviembre",
									"Diciembre"}; 
				int[] P = new int[listaRecaudo.size()];
				int[] R = new int[listaRecaudo.size()];
				int[] PI = new int[listaRecaudo.size()];
				int[] PU = new int[listaRecaudo.size()];
				int[] PT = new int[listaRecaudo.size()];
				//int[] RU = new int[listaRecaudo.size()];
				FuncionarioDao daoF = new FuncionarioDao();
				Funcionario funcionario = daoF.buscarPersona(autenticacion.getUsuarioLogin().getPersona().getCedula());
				
				List<Long>  PV = dao.listarDetalleValorNeto(tipo,funcionario.getId_funcionario());
				List<Long>  PPT = dao.listarDetalleCostoTotal(tipo, funcionario.getId_funcionario());
				for (Recaudo recaudo : listaRecaudo) {
					
					P[i] = recaudo.getPresupuesto().intValue();
					R[i] = recaudo.getReal().intValue();
					//PU[i] = recaudo.get
					presupuesto.set(periodo[i], P[i]);
					ventas.set(periodo[i], R[i]);
					
					PI[i] =(int) (long) PV.get(i)* -1;					
					presupuestoI.set(periodo[i], PI[i] );
					
					PT[i] =(int) (long) PPT.get(i);
					realI.set( periodo[i], PT[i] );
					
					PU[i] =(int) (long) PPT.get(i);
					PU[i]= PU[i] - PI[i];
					presupuestoU.set(periodo[i], PU[i]);
					i++;
				}
				
				ventas.setLabel("Real");
				presupuesto.setLabel("Plan");			
				
				model.addSeries(presupuesto);
				model.addSeries(ventas);
				
				desempenoVentas = model;
				desempenoVentas.setTitle("Desempeño Recaudo");
				desempenoVentas.setAnimate(true);
				desempenoVentas.setLegendPosition("e");
				
				desempenoVentas.setShowPointLabels(true);
				desempenoVentas.getAxes().put(AxisType.X, new CategoryAxis("Periodo"));
				Axis yAxis = desempenoVentas.getAxis(AxisType.Y);
				yAxis.setMin(0);
				yAxis.setMax(500000000);
				yAxis.setTickFormat("%'d");
				
				presupuestoI.setLabel("Presupuesto Ingresos");
				presupuestoU.setLabel("Real utilidad");
				realI.setLabel("Real Ingresos");
				
				model2.addSeries(presupuestoI);
				model2.addSeries(realI);
				
				desempenoRecaudo = model2;
				desempenoRecaudo.setTitle("Desempeño Ventas");
				desempenoRecaudo.setAnimate(true);
				desempenoRecaudo.setLegendPosition("ne");
				desempenoRecaudo.setShowPointLabels(true);
				desempenoRecaudo.getAxes().put(AxisType.X, new CategoryAxis("Periodo"));
				Axis xAxis = desempenoRecaudo.getAxis(AxisType.Y);
				xAxis.setMin(0);
				xAxis.setMax(500000000);
				xAxis.setTickFormat("%'d");
			}
			else{
				
			}
			
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la grafica de Desempeño ventas");
		}
	}
	
	public void promedioVentas(){
		
		try {
			FuncionarioDao daoF = new FuncionarioDao();
			Funcionario funcionario = daoF.buscarPersona(autenticacion.getUsuarioLogin().getPersona().getCedula());
			DetalleDao dao = new DetalleDao();			
			promedioMes = dao.promedioVentas(funcionario.getId_funcionario());
			promedioMes = promedioMes *-1;
			PromedioVentaDao daoP = new PromedioVentaDao();
			promedioVenta = daoP.listarMeta(funcionario.getId_funcionario());
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo promedio de ventas");
		}
	}

	
	
	public List<Plan> getListaplan() {
		return listaplan;
	}
	public void setListaplan(List<Plan> listaplan) {
		this.listaplan = listaplan;
	}
	public Plan getPlan() {
		return plan;
	}
	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	
	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}

	public List<Detalle_venta> getListadetalle() {
		return listadetalle;
	}

	public void setListadetalle(List<Detalle_venta> listadetalle) {
		this.listadetalle = listadetalle;
	}	

	public String getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(String fechaActual) {
		this.fechaActual = fechaActual;
	}


	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public Date getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public AutenticacionBean getAutenticacion() {
		return autenticacion;
	}

	public void setAutenticacion(AutenticacionBean autenticacion) {
		this.autenticacion = autenticacion;
	}

	public List<Fechas> getListaFechas() {
		return listaFechas;
	}

	public void setListaFechas(List<Fechas> listaFechas) {
		this.listaFechas = listaFechas;
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

	public int getTotalDetalle() {
		return totalDetalle;
	}
	public void setTotalDetalle(int totalDetalle) {
		this.totalDetalle = totalDetalle;
	}

	public String getTotalD() {
		return totalD;
	}

	public void setTotalD(String totalD) {
		this.totalD = totalD;
	}

	public LineChartModel getDesempenoVentas() {
		return desempenoVentas;
	}

	public void setDesempenoVentas(LineChartModel desempenoVentas) {
		this.desempenoVentas = desempenoVentas;
	}

	public LineChartModel getDesempenoRecaudo() {
		return desempenoRecaudo;
	}

	public void setDesempenoRecaudo(LineChartModel desempenoRecaudo) {
		this.desempenoRecaudo = desempenoRecaudo;
	}

	public BigDecimal getUmbral() {
		return umbral;
	}

	public void setUmbral(BigDecimal umbral) {
		this.umbral = umbral;
	}
	public String getTotalPreIng() {
		return totalPreIng;
	}
	public void setTotalPreIng(String totalPreIng) {
		this.totalPreIng = totalPreIng;
	}
	public String getTotalPreUti() {
		return totalPreUti;
	}
	public void setTotalPreUti(String totalPreUti) {
		this.totalPreUti = totalPreUti;
	}
	public String getTotalRealIng() {
		return totalRealIng;
	}
	public void setTotalRealIng(String totalRealIng) {
		this.totalRealIng = totalRealIng;
	}
	public String getTotalRealUti() {
		return totalRealUti;
	}
	public void setTotalRealUti(String totalRealUti) {
		this.totalRealUti = totalRealUti;
	}
	public PromedioVenta getPromedioVenta() {
		return promedioVenta;
	}
	public void setPromedioVenta(PromedioVenta promedioVenta) {
		this.promedioVenta = promedioVenta;
	}
	public Double getPromedioMes() {
		return promedioMes;
	}
	public void setPromedioMes(Double promedioMes) {
		this.promedioMes = promedioMes;
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
	public BigDecimal getTotalR() {
		return totalR;
	}
	public void setTotalR(BigDecimal totalR) {
		this.totalR = totalR;
	}
	public String getAccionR() {
		return accionR;
	}
	public void setAccionR(String accionR) {
		this.accionR = accionR;
	}
	public List<Detallesin> getListaDetalle1() {
		return listaDetalle1;
	}
	public void setListaDetalle1(List<Detallesin> listaDetalle1) {
		this.listaDetalle1 = listaDetalle1;
	}
	public String getFechaConsulta() {
		return fechaConsulta;
	}
	public void setFechaConsulta(String fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}
	public List<Fechas> getListaFechasR() {
		return listaFechasR;
	}
	public void setListaFechasR(List<Fechas> listaFechasR) {
		this.listaFechasR = listaFechasR;
	}
}
