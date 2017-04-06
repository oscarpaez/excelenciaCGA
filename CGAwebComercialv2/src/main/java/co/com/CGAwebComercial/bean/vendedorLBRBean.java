package co.com.CGAwebComercial.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.omnifaces.util.Messages;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.MeterGaugeChartModel;

import co.com.CGAwebComercial.dao.BajaRotacionDao;
import co.com.CGAwebComercial.dao.ContadoAnticipoDao;
import co.com.CGAwebComercial.dao.DetalleDao;
import co.com.CGAwebComercial.dao.FuncionarioDao;
import co.com.CGAwebComercial.dao.GerentesDao;
import co.com.CGAwebComercial.dao.IncidenciaDao;
import co.com.CGAwebComercial.dao.OfertasPedidosDao;
import co.com.CGAwebComercial.dao.PedidosEnProcesoDao;
import co.com.CGAwebComercial.dao.PedidosProyectadosDao;
import co.com.CGAwebComercial.dao.PresupuestoDao;
import co.com.CGAwebComercial.dao.PromedioVentaDao;
import co.com.CGAwebComercial.dao.RecaudoDao;
import co.com.CGAwebComercial.dao.ValorProyectoDao;
import co.com.CGAwebComercial.entyties.ContadoAnticipo;
import co.com.CGAwebComercial.entyties.Funcionario;
import co.com.CGAwebComercial.entyties.PromedioVenta;
import co.com.CGAwebComercial.entyties.Recaudo;
import co.com.CGAwebComercial.resource.Recursos;
import co.com.CGAwebComercial.util.ComisionVendedores;
import co.com.CGAwebComercial.util.Fechas;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class vendedorLBRBean implements Serializable{

	@ManagedProperty("#{autenticacionBean}")
	private AutenticacionBean autenticacion;

	private List<ComisionVendedores> listaVendedores;
	private List<Fechas> listaFechas;
	private List<ContadoAnticipo> listaContado;	
	private List<ContadoAnticipo> listaRegistros;

	private LineChartModel desempenoVentas;
	private LineChartModel desempenoRecaudo;
	private MeterGaugeChartModel meterGaugeModel;
	private MeterGaugeChartModel meterGaugeModelA;
	private BarChartModel descuentoV;

	private PromedioVenta promedioVenta;

	private Recursos recurso;
	private int indexVIE=0;
	private int codVen;
	private String tipo="";
	private String fechaBusqueda ="";
	private String fechaBusquedaYear= "";
	private Double promedioMes;
	private String mesActual;
	private String nombreV;
	private String nombreC;
	private BigDecimal presupuestoMes;
	private BigDecimal totalRealMes;
	private BigDecimal realMes;	
	private BigDecimal lbr;	
	private BigDecimal pedidosPerdidos;
	private BigDecimal pedidosGanados;
	private BigDecimal pedidosProceso;
	private BigDecimal pedidosProyectados;
	private BigDecimal proyectos;
	private BigDecimal pedidosTotal;
	private BigDecimal presupuestoRecaudo;
	private BigDecimal realRecaudo;	
	private BigDecimal kpiOfertas;
	private BigDecimal kpiOpoNeg;
	private String kpiOimagen;
	private String kpiNimagen;
	

	public vendedorLBRBean(){
		recurso = new Recursos();
		listaFechas = recurso.cargarFechas();
	}


	public void vendedorLBR(){

		try{
			if(autenticacion != null){
				autenticacion.registroIngreso(autenticacion.getUsuarioLogin());
			}
			listaVendedores = new ArrayList<>();
			FuncionarioDao daoF = new FuncionarioDao();
			Funcionario funcionario = daoF.buscarPersona(indexVIE);
			tipo = (tipo.equals("funcionario"))? "codEspecialista": (tipo.equals("codEspecialista"))? "codEspecialista": "codVendedorInt";

			ComisionVendedores vendedor = new ComisionVendedores();
			BajaRotacionDao daoB = new BajaRotacionDao();
			Long sumatotal;
			if(autenticacion.getFechaBusqueda() == null || autenticacion.getFechaBusqueda().equals("")){
				sumatotal = daoB.SumaTotalFechas( tipo, funcionario.getId_funcionario(), fechaBusqueda, fechaBusquedaYear);
			}
			else{
				fechaBusqueda = autenticacion.getFechaBusqueda();
				fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
				sumatotal = daoB.SumaTotalFechas( tipo, funcionario.getId_funcionario(), fechaBusqueda, fechaBusquedaYear);
			}	
			if(sumatotal != null){
				int total = (int) (long) sumatotal* -1;
				vendedor.setCedula(funcionario.getPersona().getCedula());
				vendedor.setId(funcionario.getId_funcionario());
				vendedor.setNombre(funcionario.getPersona().getNombre());
				vendedor.setConcepto("LBR");
				vendedor.setIngresoReal(total);
				vendedor.setComision((total * 9) / 1000);
				vendedor.setTotalConAjuste(vendedor.getComision());
				listaVendedores.add(vendedor);
			}
			else{
				vendedor.setCedula(funcionario.getPersona().getCedula());
				vendedor.setId(funcionario.getId_funcionario());
				vendedor.setNombre(funcionario.getPersona().getNombre());
				vendedor.setConcepto("LBR");
				vendedor.setIngresoReal(0);
				vendedor.setComision(0);
				vendedor.setTotalConAjuste(vendedor.getComision());
				listaVendedores.add(vendedor);
			}

			autenticacion.setFechaBusqueda(fechaBusqueda);
			autenticacion.setFechaBusquedaYear(fechaBusquedaYear);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se cargo LBR");
		}
	}

	//*Contado y anticipo verificando  el estado de los vendedor *//
	public void listarContadoAnticipoN(){

		try {
			if(autenticacion != null){
				autenticacion.registroIngreso(autenticacion.getUsuarioLogin());
			}
			if(autenticacion.getFechaBusqueda() != null ){
				fechaBusqueda = autenticacion.getFechaBusqueda();
				fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			} 
			List<ContadoAnticipo> listaRegistros;
			listaContado = new ArrayList<>();
			FuncionarioDao daoF = new FuncionarioDao();
			ContadoAnticipoDao daoCA = new ContadoAnticipoDao();
			Funcionario funcionario = daoF.buscarPersona(codVen);

			if(funcionario.getEstado() == 1){
				ContadoAnticipo contado = new ContadoAnticipo();
				contado.setCedula(funcionario.getPersona().getCedula());
				contado.setNoPersonal(funcionario.getId_funcionario());
				contado.setVendedor(funcionario.getPersona().getNombre());
				contado.setComision(new BigDecimal("0.00"));
				listaRegistros = (tipo.equals("codVendedorInt") )?  daoCA.listaContadoInternos(funcionario.getId_funcionario(), fechaBusqueda, fechaBusquedaYear) : daoCA.listaContadoEspecialista(funcionario.getId_funcionario(), fechaBusqueda, fechaBusquedaYear);  
				for ( ContadoAnticipo contadoA : listaRegistros) {

					Funcionario fun = daoF.buscar(contadoA.getVendInterno());
					if(  fun == null ||  fun.getEstado() != 1){

						contado.setTotalRecaudo(contado.getTotalRecaudo() + contadoA.getTotalrecCA());
						contado.setPorcentaje(new BigDecimal("0.50"));
						contado.setComision(contado.getComision().add(contado.getPorcentaje().multiply(BigDecimal.valueOf(contadoA.getTotalrecCA()/ 100))));
					}
					else{
						contado.setTotalRecaudo(contado.getTotalRecaudo() + contadoA.getTotalrecCA());
						contado.setPorcentaje(new BigDecimal("0.25"));
						contado.setComision(contado.getComision().add(contado.getPorcentaje().multiply(BigDecimal.valueOf(contadoA.getTotalrecCA()/ 100))));
					}
					contado.setPorcentaje(new BigDecimal("0.25"));
				}
				listaContado.add(contado);
			}
			autenticacion.setFechaBusqueda(fechaBusqueda);
			autenticacion.setFechaBusquedaYear(fechaBusquedaYear);

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Contado ");
		}
	}

	//* Se listan las facturas del vendedor Contado y Anticipo *//
	public void listarFacturasContadoAnticipo(){

		try{
			if(autenticacion != null){
				autenticacion.registroIngreso(autenticacion.getUsuarioLogin());
			}
			if(autenticacion.getFechaBusqueda() != null ){
				fechaBusqueda = autenticacion.getFechaBusqueda();
				fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			}
			FuncionarioDao daoF = new FuncionarioDao();
			Funcionario funcionario = daoF.buscarPersona(codVen);
			ContadoAnticipoDao daoCA = new ContadoAnticipoDao();			
			listaRegistros = (tipo.equals("codVendedorInt") )?  daoCA.listaContadoInternos(funcionario.getId_funcionario(), fechaBusqueda, fechaBusquedaYear) : daoCA.listaContadoEspecialista(funcionario.getId_funcionario(), fechaBusqueda, fechaBusquedaYear);  
			autenticacion.setFechaBusqueda(fechaBusqueda);
			autenticacion.setFechaBusquedaYear(fechaBusquedaYear);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError(" Error no se Cargo la lista de Facturas de Contado y Anticipo ");
		}
	}

	//*Carga la grafica de desempeño de las ventas  del Vendedor*//
	public void desempenoVentas(){

		try{
			if(autenticacion != null){
				autenticacion.registroIngreso(autenticacion.getUsuarioLogin());
			}
			if(desempenoVentas == null){
				LineChartModel model = new LineChartModel();
				LineChartModel model2 = new LineChartModel();
				BarChartModel model3 = new BarChartModel();
				LineChartSeries presupuesto = new LineChartSeries();
				LineChartSeries ventas = new LineChartSeries();
				LineChartSeries leyenda = new LineChartSeries();

				LineChartSeries presupuestoI = new LineChartSeries();
				LineChartSeries realI = new LineChartSeries();
				LineChartSeries presupuestoU = new LineChartSeries();
				ChartSeries descuento = new ChartSeries();				
				ChartSeries descuento1 = new ChartSeries();

				RecaudoDao dao = new RecaudoDao();
				List<Recaudo> listaRecaudo = new ArrayList<>();
				FuncionarioDao daoF = new FuncionarioDao();
				Funcionario funcionario = new Funcionario();
				if(autenticacion.getUsuarioLogin().getPerfil().getId() == 20){
					funcionario = daoF.buscar(codVen);	
				}
				else{
					funcionario = daoF.buscarPersona(autenticacion.getUsuarioLogin().getPersona().getCedula());
				}
				 
				if(autenticacion.getUsuarioLogin().getPerfil().getId() == 6){
					listaRecaudo = dao.carteraInternosGraficas(funcionario.getId_funcionario());
					tipo = "funcionarioI";
				}	
				else{
					if(autenticacion.getUsuarioLogin().getPerfil().getId() == 20){
						listaRecaudo = dao.listarPresupuesto(funcionario.getPersona().getCedula());
					}
					else{
						listaRecaudo = dao.listarPresupuesto(autenticacion.getUsuarioLogin().getPersona().getCedula());
					}
					tipo = "funcionario";
				}
				int i=0;
				/*
				String[] periodo = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
						"Julio", "Agosto", "Septiembre", "Octubre", "Noviembre",
				"Diciembre"}; */
				String[] periodo = {"Ene", "Feb", "Mar", "Abr", "May", "Jun",
						"Jul", "Ago", "Sep", "Oct", "Nov", "Dic"}; 
				int[] P = new int[listaRecaudo.size()];
				int[] R = new int[listaRecaudo.size()];
				int[] PI = new int[listaRecaudo.size()];
				int[] PU = new int[listaRecaudo.size()];
				int[] PT = new int[listaRecaudo.size()];
				int[] D = new int[listaRecaudo.size()];
				int escala = 0;
				int escala1 = 0;

				mesActual = mesActualG();
				
				int funcionario_id = (autenticacion.getUsuarioLogin().getPerfil().getId() == 20)? codVen : funcionario.getId_funcionario();
				
				List<Long>  PV = dao.listarDetalleValorNeto(tipo,funcionario_id);
				List<Long>  PPT = dao.listarDetalleCostoTotal(tipo, funcionario_id);
				List<Long>  PVD = dao.listarDetalleValorDescuento(tipo, funcionario_id);
				List<BigDecimal> PR= dao.presupuestoVendedor(tipo, funcionario_id );
				
				/*
				List<Long>  PV = dao.listarDetalleValorNeto(tipo,funcionario.getId_funcionario());
				List<Long>  PPT = dao.listarDetalleCostoTotal(tipo, funcionario.getId_funcionario());
				List<Long>  PVD = dao.listarDetalleValorDescuento(tipo,funcionario.getId_funcionario());
				List<BigDecimal> PR= dao.presupuestoVendedor(tipo, funcionario.getId_funcionario());*/

				Long b;
				BigDecimal tem = new BigDecimal("0");

				for (Recaudo recaudo : listaRecaudo) {

					tem = (recaudo.getPresupuesto().intValue() == 0)? new BigDecimal("0"):recaudo.getPresupuesto().divide(new BigDecimal("1000000")).abs();
					b = tem.abs().longValue();
					P[i] = b.intValue();

					tem = recaudo.getReal().divide(new BigDecimal("1000000")).abs();
					b = tem.abs().longValue();
					R[i] = b.intValue();

					int a = (P[i] >  R[i])? P[i] :R[i];
					escala =( a > escala )? a:escala;

					presupuesto.set(periodo[i], P[i]);
					ventas.set(periodo[i], R[i] );

					b = PV.get(i) / 1000000;

					PI[i] =(int) (long) b* -1;
					//PI[i] =(int) (long) PV.get(i)* -1;					
					presupuestoI.set(periodo[i], PI[i] );

					//					b = PPT.get(i) / 1000000;
					//					PT[i] =(int) (long) b;

					b = PR.get(i).longValue() / 1000000;
					PT[i] =(int) (long) b;

					//PT[i] =(int) (long) PPT.get(i);
					realI.set( periodo[i], PT[i] );

					a = (PI[i]> PT[i] )? PI[i]:PT[i];
					escala1 =( a > escala1 )? a:escala1;

					PU[i] =(int) (long) PPT.get(i) /1000000;
					PU[i]= PU[i] - PI[i];
					presupuestoU.set(periodo[i], PU[i]);
					D[i] = (int) (long) PVD.get(i)/ 1000000;

					BigDecimal desc = new BigDecimal(D[i]); 
					BigDecimal desc1 = new BigDecimal(PI[i]);
					desc = (desc1.longValue() == 0)? new BigDecimal("0"):desc.divide(desc1,  2, RoundingMode.HALF_UP).multiply(new BigDecimal("100")).subtract(new BigDecimal("5"));
					descuento.set(periodo[i], desc.intValue());
					descuento1.set(periodo[i], 5 );
					i++;
				}

				ventas.setLabel("Real");
				presupuesto.setLabel("Presupuesto");
				leyenda.setLabel("Escala de valores en MILLONES");

				model.addSeries(presupuesto);
				model.addSeries(ventas);
				model.addSeries(leyenda);

				desempenoVentas = model;
				desempenoVentas.setTitle("Desempeño Recaudo");
				desempenoVentas.setAnimate(true);
				desempenoVentas.setLegendPosition("ne");
				
				desempenoVentas.setShowPointLabels(true);
				desempenoVentas.getAxes().put(AxisType.X, new CategoryAxis("Periodo    Escala de valores en MILLONES"));
				Axis yAxis = desempenoVentas.getAxis(AxisType.Y);
				yAxis.setMin(0);
				yAxis.setMax(escala + 200);
				yAxis.setTickFormat("%'d");

				presupuestoI.setLabel("Real Ingresos");
				//presupuestoU.setLabel("Real utilidad");
				realI.setLabel("Presupuesto Ingresos");


				model2.addSeries(presupuestoI);
				model2.addSeries(realI);
				model2.addSeries(leyenda);

				desempenoRecaudo = model2;
				desempenoRecaudo.setTitle("Desempeño Ventas");
				desempenoRecaudo.setAnimate(true);
				desempenoRecaudo.setLegendPosition("ne");
				desempenoRecaudo.setShowPointLabels(true);
				desempenoRecaudo.getAxes().put(AxisType.X, new CategoryAxis("Periodo"));
				Axis xAxis = desempenoRecaudo.getAxis(AxisType.Y);
				xAxis.setMin(0);
				xAxis.setMax(escala1 + 200);
				xAxis.setTickFormat("%'d");

				descuento.setLabel("Descuentos");	
				descuento1.setLabel("Meta Descuentos");
				model3.addSeries(descuento1);
				model3.addSeries(descuento);				
				model3.setStacked(true);
				descuentoV = model3;
				descuentoV.setTitle("Descuento Ventas");
				descuentoV.setAnimate(true);
				descuentoV.setLegendPosition("ne");
				descuentoV.setShowPointLabels(true);
				descuentoV.getAxes().put(AxisType.X, new CategoryAxis("Periodo"));
				xAxis = descuentoV.getAxis(AxisType.Y);
				xAxis.setMin(0);
				xAxis.setMax(100);
				xAxis.setTickFormat("%.0f%%");

				List<Number> interval = new ArrayList<Number>(){{
					add(60);
					add(85);
					add(100);
				}};				

				PresupuestoDao daoD = new PresupuestoDao();
				
				List<BigDecimal> ventaMes = daoD.ventasMes(tipo, funcionario_id );
				//List<BigDecimal> ventaMes = daoD.ventasMes(tipo, funcionario.getId_funcionario() );

				presupuestoMes = ventaMes.get(1);
				realMes = ventaMes.get(0).multiply(new BigDecimal("-1")); 
				lbr = ventaMes.get(2).multiply(new BigDecimal("-1"));
				presupuestoRecaudo = ventaMes.get(3);
				realRecaudo = ventaMes.get(4);
				
				totalRealMes = realMes.add(lbr); 
				
				BigDecimal porV = (ventaMes.get(1).longValue() == 0)? new BigDecimal("0"):totalRealMes.divide(ventaMes.get(1), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
				//BigDecimal porV = (ventaMes.get(1).longValue() == 0)? new BigDecimal("0"):realMes.divide(ventaMes.get(1), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
				int V = porV.intValue();
				
				interval.add(3,(V >100)? V:100); 

				meterGaugeModel = new MeterGaugeChartModel(V, interval);
				meterGaugeModel.setTitle("Cumplimiento " + mesActual + " Pedidos Facturados");
				meterGaugeModel.setGaugeLabel( V +"%");
				meterGaugeModel.setSeriesColors("ff0000, ffff00, 009933 ");
				
				
				/**/
				if(autenticacion.getUsuarioLogin().getPerfil().getId() == 1 || autenticacion.getUsuarioLogin().getPerfil().getId() == 20){
					//Zona_ventaDao daoZ = new Zona_ventaDao();
					//List<Zona_venta> zona = daoZ.buscarZona(funcionario.getId_funcionario());
					//List<Zona_venta> zona = daoZ.buscarZona(funcionario_id);
					
					IncidenciaDao daoI  = new IncidenciaDao();
					List<Long> valorPer  = daoI.valorPedidosPerdidos("funcionario", funcionario_id);
					
					pedidosPerdidos = new BigDecimal(valorPer.get(0));
					pedidosGanados =  new BigDecimal(valorPer.get(1));
					//pedidosProyectados = new BigDecimal(valorPer.get(2));
					
					PedidosEnProcesoDao daoP = new PedidosEnProcesoDao();
					//pedidosProceso  = daoP.valorPedidosProceso(tipo, funcionario.getId_funcionario());
					pedidosProceso  = daoP.valorPedidosProceso(tipo, funcionario_id);
					
					PedidosProyectadosDao daoPe = new PedidosProyectadosDao();
					//pedidosProyectados = daoPe.valorPedidosProyectados(funcionario.getId_funcionario());
					pedidosProyectados = daoPe.valorPedidosProyectados(funcionario_id);
					
					ValorProyectoDao daoV = new ValorProyectoDao();
					proyectos = new BigDecimal(daoV.listarProyectos(funcionario_id));
					
					pedidosTotal =pedidosProceso.add(pedidosGanados.add(totalRealMes).add(proyectos));
					
					
					OfertasPedidosDao daoOF = new OfertasPedidosDao();
					List<BigDecimal> listaOferta = daoOF.sumaValorOfertasPedidosVendedor(tipo, funcionario_id);
					
					kpiOfertas = (listaOferta.get(1).intValue() == 0 || listaOferta.get(0).intValue() == 0)? new BigDecimal("0"):listaOferta.get(1).divide(listaOferta.get(0), 4, BigDecimal.ROUND_HALF_UP ).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
					kpiOimagen = (kpiOfertas.intValue() > 80)? "verde.png" : (kpiOfertas.intValue() > 60 &&  kpiOfertas.intValue() < 80)? "amarillo.jpg"  : "rojo.png";
					
					//listaOferta= daoI.valorOportunidadNegocioVendedor(funcionario_id);
					kpiOpoNeg =  (listaOferta.get(3).intValue() == 0 || listaOferta.get(2).intValue() == 0)? new BigDecimal("0"): listaOferta.get(3).divide(listaOferta.get(2), 4, BigDecimal.ROUND_HALF_UP ).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
					kpiNimagen = (kpiOpoNeg.intValue() > 80)? "verde.png" : (kpiOpoNeg.intValue() > 60 &&  kpiOpoNeg.intValue() < 80)? "amarillo.jpg"  : "rojo.png";
					
					List<Number> intervalA = new ArrayList<Number>(){{
						add(60);
						add(85);
						add(100);
					}};				

					
					porV = (pedidosTotal.longValue() == 0 || ventaMes.get(1).intValue() == 0 )? new BigDecimal("0"):pedidosTotal.divide(ventaMes.get(1), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
					V = porV.intValue();
					
					intervalA.add(3,(V >100)? V:100); 
					
					meterGaugeModelA = new MeterGaugeChartModel(V, intervalA);
					meterGaugeModelA.setTitle("Cumplimiento " + mesActual + " Pedidos Facturados y Proyectados");
					meterGaugeModelA.setGaugeLabel( V +"%");
					meterGaugeModelA.setSeriesColors("ff0000, ffff00, 009933 ");
				}
				else{
					/*valores para el interno*/
					String tipo = "funcionarioI";
					IncidenciaDao daoI  = new IncidenciaDao();
					List<Long> valorPer  = daoI.valorPedidosPerdidos(tipo, funcionario_id);
					
					pedidosPerdidos = new BigDecimal(valorPer.get(0));
					pedidosGanados =  new BigDecimal(valorPer.get(1));
					pedidosProyectados = new BigDecimal(valorPer.get(2));
					
					PedidosEnProcesoDao daoP = new PedidosEnProcesoDao();
					pedidosProceso  = daoP.valorPedidosProceso(tipo, funcionario_id);
					
					pedidosTotal =pedidosProceso.add(pedidosGanados.add(realMes).add(lbr));
					
					/**/
					
					
					OfertasPedidosDao daoOF = new OfertasPedidosDao();
					List<BigDecimal> listaOferta = daoOF.sumaValorOfertasPedidosVendedor(tipo, funcionario_id);
					
					kpiOfertas = (listaOferta.get(1).intValue() == 0 || listaOferta.get(0).intValue() == 0)? new BigDecimal("0"):listaOferta.get(1).divide(listaOferta.get(0), 4, BigDecimal.ROUND_HALF_UP ).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
					kpiOimagen = (kpiOfertas.intValue() > 80)? "verde.png" : (kpiOfertas.intValue() > 60 &&  kpiOfertas.intValue() < 80)? "amarillo.jpg"  : "rojo.png";
					
					//listaOferta= daoI.valorOportunidadNegocioVendedor(funcionario_id);
					
					kpiOpoNeg =  (listaOferta.get(3).intValue() == 0 || listaOferta.get(2).intValue() == 0)? new BigDecimal("0"): listaOferta.get(3).divide(listaOferta.get(2), 4, BigDecimal.ROUND_HALF_UP ).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
					kpiNimagen = (kpiOpoNeg.intValue() > 80)? "verde.png" : (kpiOpoNeg.intValue() > 60 &&  kpiOpoNeg.intValue() < 80)? "amarillo.jpg"  : "rojo.png";
				
					/**/
					List<Number> intervalA = new ArrayList<Number>(){{
						add(60);
						add(85);
						add(100);
					}};				

					
					porV = (pedidosTotal.longValue() == 0 || ventaMes.get(1).intValue() == 0 )? new BigDecimal("0"):pedidosTotal.divide(ventaMes.get(1), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
					V = porV.intValue();
					
					intervalA.add(3,(V >100)? V:100); 
					
					meterGaugeModelA = new MeterGaugeChartModel(V, intervalA);
					meterGaugeModelA.setTitle("Cumplimiento " + mesActual + " Pedidos Facturados y Proyectados");
					meterGaugeModelA.setGaugeLabel( V +"%");
					meterGaugeModelA.setSeriesColors("ff0000, ffff00, 009933 ");	
					/**/
					
				}
				/**/
				promedioVentas();

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
			Funcionario funcionario = new Funcionario();
			if(autenticacion.getUsuarioLogin().getPerfil().getId() == 20){
				funcionario = daoF.buscar(codVen);				
			}
			else{
				funcionario = daoF.buscarPersona(autenticacion.getUsuarioLogin().getPersona().getCedula());
			}
			 
			DetalleDao dao = new DetalleDao();
			if(tipo.equals("funcionario")){
				promedioMes = dao.promedioVentas(funcionario.getId_funcionario());
			}else{ 
				promedioMes = dao.promedioVentasI(funcionario.getId_funcionario()); 
			}

			promedioMes = (promedioMes == null)? 0 : promedioMes *-1;
			PromedioVentaDao daoP = new PromedioVentaDao();
			promedioVenta = daoP.listarMeta(funcionario.getId_funcionario());
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ultima Actualización "+ autenticacion.getFechaDiaAnterior(), ""));
			
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo promedio de ventas");
		}
	}

	public String mesActualG(){

		String periodoM = "";
		try{
			GerentesDao daoG = new GerentesDao();
			Date fechaActual = daoG.fechaFinal();
			DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
			String fechaConverdita = formatoFecha.format(fechaActual); 
			String diaArray[] = fechaConverdita.split("-");
			for (String string : diaArray) {
				System.out.println(string);
			}
			int mes = Integer.parseInt("" + diaArray[1]);
			
			String[] periodo = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
					"Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

			periodoM = periodo[mes -1];
			return periodoM;

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo el mes Actual");
		}
		return periodoM;
	}

	public int getIndexVIE() {
		return indexVIE;
	}
	public void setIndexVIE(int indexVIE) {
		this.indexVIE = indexVIE;
	}
	public AutenticacionBean getAutenticacion() {
		return autenticacion;
	}
	public void setAutenticacion(AutenticacionBean autenticacion) {
		this.autenticacion = autenticacion;
	}
	public List<ComisionVendedores> getListaVendedores() {
		return listaVendedores;
	}
	public void setListaVendedores(List<ComisionVendedores> listaVendedores) {
		this.listaVendedores = listaVendedores;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
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
	public List<Fechas> getListaFechas() {
		return listaFechas;
	}
	public void setListaFechas(List<Fechas> listaFechas) {
		this.listaFechas = listaFechas;
	}
	public Recursos getRecurso() {
		return recurso;
	}
	public void setRecurso(Recursos recurso) {
		this.recurso = recurso;
	}
	public List<ContadoAnticipo> getListaContado() {
		return listaContado;
	}
	public void setListaContado(List<ContadoAnticipo> listaContado) {
		this.listaContado = listaContado;
	}
	public int getCodVen() {
		return codVen;
	}
	public void setCodVen(int codVen) {
		this.codVen = codVen;
	}
	public List<ContadoAnticipo> getListaRegistros() {
		return listaRegistros;
	}
	public void setListaRegistros(List<ContadoAnticipo> listaRegistros) {
		this.listaRegistros = listaRegistros;
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
	public BarChartModel getDescuentoV() {
		return descuentoV;
	}
	public void setDescuentoV(BarChartModel descuentoV) {
		this.descuentoV = descuentoV;
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
	public MeterGaugeChartModel getMeterGaugeModel() {
		return meterGaugeModel;
	}
	public void setMeterGaugeModel(MeterGaugeChartModel meterGaugeModel) {
		this.meterGaugeModel = meterGaugeModel;
	}
	public BigDecimal getPresupuestoMes() {
		return presupuestoMes;
	}
	public void setPresupuestoMes(BigDecimal presupuestoMes) {
		this.presupuestoMes = presupuestoMes;
	}
	public BigDecimal getRealMes() {
		return realMes;
	}
	public void setRealMes(BigDecimal realMes) {
		this.realMes = realMes;
	}
	public String getMesActual() {
		return mesActual;
	}
	public void setMesActual(String mesActual) {
		this.mesActual = mesActual;
	}
	public BigDecimal getPedidosPerdidos() {
		return pedidosPerdidos;
	}
	public void setPedidosPerdidos(BigDecimal pedidosPerdidos) {
		this.pedidosPerdidos = pedidosPerdidos;
	}
	public BigDecimal getPedidosProceso() {
		return pedidosProceso;
	}
	public void setPedidosProceso(BigDecimal pedidosProceso) {
		this.pedidosProceso = pedidosProceso;
	}
	public BigDecimal getPedidosProyectados() {
		return pedidosProyectados;
	}
	public void setPedidosProyectados(BigDecimal pedidosProyectados) {
		this.pedidosProyectados = pedidosProyectados;
	}
	public BigDecimal getPedidosTotal() {
		return pedidosTotal;
	}
	public void setPedidosTotal(BigDecimal pedidosTotal) {
		this.pedidosTotal = pedidosTotal;
	}
	public MeterGaugeChartModel getMeterGaugeModelA() {
		return meterGaugeModelA;
	}
	public void setMeterGaugeModelA(MeterGaugeChartModel meterGaugeModelA) {
		this.meterGaugeModelA = meterGaugeModelA;
	}
	public BigDecimal getLbr() {
		return lbr;
	}
	public void setLbr(BigDecimal lbr) {
		this.lbr = lbr;
	}
	public String getNombreV() {
		return nombreV;
	}
	public void setNombreV(String nombreV) {
		this.nombreV = nombreV;
	}
	public String getNombreC() {
		return nombreC;
	}
	public void setNombreC(String nombreC) {
		this.nombreC = nombreC;
	}
	public BigDecimal getPresupuestoRecaudo() {
		return presupuestoRecaudo;
	}
	public void setPresupuestoRecaudo(BigDecimal presupuestoRecaudo) {
		this.presupuestoRecaudo = presupuestoRecaudo;
	}
	public BigDecimal getRealRecaudo() {
		return realRecaudo;
	}
	public void setRealRecaudo(BigDecimal realRecaudo) {
		this.realRecaudo = realRecaudo;
	}
	public BigDecimal getTotalRealMes() {
		return totalRealMes;
	}
	public void setTotalRealMes(BigDecimal totalRealMes) {
		this.totalRealMes = totalRealMes;
	}
	public BigDecimal getPedidosGanados() {
		return pedidosGanados;
	}
	public void setPedidosGanados(BigDecimal pedidosGanados) {
		this.pedidosGanados = pedidosGanados;
	}
	public BigDecimal getKpiOfertas() {
		return kpiOfertas;
	}
	public void setKpiOfertas(BigDecimal kpiOfertas) {
		this.kpiOfertas = kpiOfertas;
	}
	public BigDecimal getKpiOpoNeg() {
		return kpiOpoNeg;
	}
	public void setKpiOpoNeg(BigDecimal kpiOpoNeg) {
		this.kpiOpoNeg = kpiOpoNeg;
	}
	public String getKpiOimagen() {
		return kpiOimagen;
	}
	public void setKpiOimagen(String kpiOimagen) {
		this.kpiOimagen = kpiOimagen;
	}
	public String getKpiNimagen() {
		return kpiNimagen;
	}
	public void setKpiNimagen(String kpiNimagen) {
		this.kpiNimagen = kpiNimagen;
	}
	public BigDecimal getProyectos() {
		return proyectos;
	}
	public void setProyectos(BigDecimal proyectos) {
		this.proyectos = proyectos;
	}
}
