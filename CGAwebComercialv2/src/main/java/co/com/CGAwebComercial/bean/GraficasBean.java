package co.com.CGAwebComercial.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
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
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.MeterGaugeChartModel;

import co.com.CGAwebComercial.dao.CiudadDao;
import co.com.CGAwebComercial.dao.FuncionarioDao;
import co.com.CGAwebComercial.dao.GerentesDao;
import co.com.CGAwebComercial.dao.IncidenciaDao;
import co.com.CGAwebComercial.dao.LineaDao;
import co.com.CGAwebComercial.dao.OfertasPedidosDao;
import co.com.CGAwebComercial.dao.PedidosEnProcesoDao;
import co.com.CGAwebComercial.dao.PresupuestoDao;
import co.com.CGAwebComercial.dao.PromedioVentaDao;
import co.com.CGAwebComercial.dao.RecaudoDao;
import co.com.CGAwebComercial.dao.Zona_FuncionarioDao;
import co.com.CGAwebComercial.dao.Zona_ventaDao;
import co.com.CGAwebComercial.entyties.Ciudad;
import co.com.CGAwebComercial.entyties.Funcionario;
import co.com.CGAwebComercial.entyties.PromedioVenta;
import co.com.CGAwebComercial.entyties.Zona_Funcionario;
import co.com.CGAwebComercial.entyties.Zona_venta;
import co.com.CGAwebComercial.resource.Recursos;
import co.com.CGAwebComercial.util.ComisionVendedores;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class GraficasBean implements Serializable{

	@ManagedProperty("#{autenticacionBean}")
	private AutenticacionBean autenticacion;

	private List<ComisionVendedores> ListaComisionVendedores;
	private List<ComisionVendedores> listaLineas;
	
	private LineChartModel desempenoVentas;
	private LineChartModel desempenoRecaudo;
	private MeterGaugeChartModel meterGaugeModel;
	private MeterGaugeChartModel meterGaugeModelA;
	private MeterGaugeChartModel descuentoP;
	private BarChartModel descuentoV;

	private BigDecimal promedioMes;
	private BigDecimal pedidosTotal;
	private BigDecimal pedidosProyectados;
	private BigDecimal pedidosProceso;
	private BigDecimal pedidosPerdidos;
	private BigDecimal pedidosGanados;
	private BigDecimal presupuestoRecaudo;
	private BigDecimal realRecaudo;
	private BigDecimal kpiOfertas;
	private BigDecimal kpiOpoNeg;
	private PromedioVenta promedioVenta;
	private int descuentoMes;
	private Long presupuestoMes;
	private Long presupuestoAcumulado;
	private Long realAcumulado;
	private Long realMes;
	private Long lbr;
	private int idLinea;
	private int idCiudad;
	private Long proDescuento;
	private String totalPreO;
	private String totolRealO;
	private String totalCumO;
	private String totalPreUO;
	private String totolRealUO;
	private String totalCumUO;
	private String totalPreL;
	private String totolRealL;
	private String totalCumL;
	private String totalPreUL;
	private String totolRealUL;
	private String totalCumUL;
	private String mesActual;
	private String mesInicial;
	private String nombreSucursal;
	private String kpiOimagen;
	private String kpiNimagen;

	//*Carga la grafica de desempeño de las ventas y recaudo del "DL" *//

	public void desempenoVentas(){

		try{

			if(desempenoVentas == null){
				LineChartModel model = new LineChartModel();
				LineChartModel model2 = new LineChartModel();

				LineChartSeries recaudo = new LineChartSeries();
				LineChartSeries realR = new LineChartSeries();
				LineChartSeries leyenda = new LineChartSeries();

				LineChartSeries presupuesto = new LineChartSeries();
				LineChartSeries realD = new LineChartSeries();
				LineChartSeries leyenda1 = new LineChartSeries();



				List <BigDecimal> listaRecaudo = new ArrayList<>();
				String titulo = "";
				RecaudoDao dao = new RecaudoDao();
				
				if(autenticacion.getUsuarioLogin().getPerfil().getId() == 12){
					listaRecaudo = dao.recaudoPaisPeriodosAn();
					idLinea = 6;
					titulo = "Desempeño País";
				}
				else if(autenticacion.getUsuarioLogin().getPerfil().getId() == 8 || autenticacion.getUsuarioLogin().getPerfil().getId() == 7){
					listaRecaudo = dao.recaudoPaisPeriodosDirectorB(autenticacion.getUsuarioLogin().getPersona().getCedula());
					titulo = "Desempeño Recaudo";
				}
				else if(autenticacion.getUsuarioLogin().getPerfil().getId() == 9){
					listaRecaudo = dao.recaudoPaisPeriodosJefeI(autenticacion.getUsuarioLogin().getPersona().getCedula());
					titulo = "Desempeño Recaudo";
				}
				else if(autenticacion.getUsuarioLogin().getPerfil().getId() == 20){
					listaRecaudo = dao.recaudoPaisPeriodosDirectorP(idCiudad);
					titulo = "Desempeño Recaudo";
				}
				else{
					listaRecaudo = dao.recaudoPaisPeriodos();
					idLinea = 1;
					titulo = "Desempeño País";
				}
				String[] periodo = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
						"Julio", "Agosto", "Septiembre", "Octubre", "Noviembre",
				"Diciembre"}; 
				int[] pre = new int[listaRecaudo.size()/4];
				int[] realV = new int[listaRecaudo.size()/4];
				int[] reca = new int[listaRecaudo.size()/4];
				int[] real = new int[listaRecaudo.size()/4];
				int j=0;
				int escala = 0;
				int escala1 = 0;
				
				mesActual = periodo[(listaRecaudo.size()/4)];
				
				Long b;
				BigDecimal tem = new BigDecimal("0");
				
				for (int i=0; i<listaRecaudo.size()/4;  i++) {
					tem = listaRecaudo.get(j).divide(new BigDecimal("1000000")).abs();
					System.out.println(tem + "primer dato");
					b = tem.abs().longValue();
					reca[i] = (int)(long)b;
					tem = listaRecaudo.get(j+1).divide(new BigDecimal("1000000")).abs();
					b = tem.abs().longValue();
					real[i] = (int)(long)b;
					tem = listaRecaudo.get(j+2).divide(new BigDecimal("1000000")).abs();
					b = tem.longValue();
					pre[i] = (int)(long)b;
					tem = listaRecaudo.get(j+3).divide(new BigDecimal("1000000")).abs();
					b = tem.longValue();
					realV[i] = (int)(long)b;

					int c = (reca[i] > real[i] )? reca[i]:real[i];
					escala =(c > escala )? c:escala; 

					int a = (realV[i] > pre[i] )? realV[i]:pre[i];
					escala1 =( a > escala1 )? a:escala1;
					
					recaudo.set(periodo[i], reca[i]);
					realR.set(periodo[i], real[i] );

					presupuesto.set(periodo[i], pre[i] );
					realD.set( periodo[i], realV[i] );

					j = j+4;
				}
				recaudo.setLabel("Presupuesto Venta");
				realR.setLabel("Real Ingresos");			
				leyenda.setLabel("Escala de valores en MILLONES");

				model.addSeries(recaudo);
				model.addSeries(realR);
				model.addSeries(leyenda);

				desempenoVentas = model;
				desempenoVentas.setTitle("Desempeño Ventas");
				desempenoVentas.setAnimate(true);
				desempenoVentas.setLegendPosition("se");

				desempenoVentas.setShowPointLabels(true);
				desempenoVentas.getAxes().put(AxisType.X, new CategoryAxis("Periodo        Escala de valores en MILLONES"));
				Axis yAxis = desempenoVentas.getAxis(AxisType.Y);
				yAxis.setMin(0);
				yAxis.setMax(escala+500);
				yAxis.setTickFormat("%'d");

				presupuesto.setLabel("Real");
				realD.setLabel("Presupuesto");
				leyenda1.setLabel("Escala de valores en MILLONES");

				model2.addSeries(presupuesto);
				model2.addSeries(realD);
				model2.addSeries(leyenda1);

				desempenoRecaudo = model2;
				desempenoRecaudo.setTitle(titulo);
				desempenoRecaudo.setAnimate(true);
				desempenoRecaudo.setLegendPosition("se");
				desempenoRecaudo.setShowPointLabels(true);
				desempenoRecaudo.getAxes().put(AxisType.X, new CategoryAxis("Periodo     Escala de valores en MILLONES"));
				Axis xAxis = desempenoRecaudo.getAxis(AxisType.Y);
				xAxis.setMin(0);
				xAxis.setMax(escala1+2000);
				xAxis.setTickFormat("%'d");

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ultima Actualización "+ autenticacion.getFechaDiaAnterior(), ""));
				//promedioVentasNacional();
				if(autenticacion.getUsuarioLogin().getPerfil().getId() == 8 || autenticacion.getUsuarioLogin().getPerfil().getId() == 7
						|| autenticacion.getUsuarioLogin().getPerfil().getId() == 9 ||  autenticacion.getUsuarioLogin().getPerfil().getId() == 20){
					promedioVentasOficina();
				}
				else
					promedioVentasNacional();
			}
			else{

			}

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la grafica de Desempeño ventas");
		}
	}

	//*Promedio de ventas nacional en por linea  "dl " "/dl/vistaModulo"*//
	public void promedioVentasNacional(){

		try {
			FuncionarioDao daoF = new FuncionarioDao();
			Funcionario funcionario = daoF.buscarPersona(autenticacion.getUsuarioLogin().getPersona().getCedula());

			PromedioVentaDao daoP = new PromedioVentaDao();			
			Double pro = daoP.promedioVentasNacional(idLinea);
			pro = (pro == null)? 0 : pro;
			promedioMes = new BigDecimal(pro.toString());
			proDescuento =  daoP.promedioDescuentoNacional(idLinea);			
			promedioMes = getPromedioMes().multiply(new BigDecimal("-1"));	
			promedioVenta = daoP.listarMeta(funcionario.getId_funcionario());

			List<Number> interval = new ArrayList<Number>(){{
				add(10);
				add(30);
				add(100);
			}};
			
			descuentoP = new MeterGaugeChartModel(proDescuento, interval);
			descuentoP.setTitle("Cumplimiento " + mesActual + " Descuento");
			descuentoP.setGaugeLabel(proDescuento +"%");
			descuentoP.setSeriesColors("009933, ffff00, ff0000, ");
			
			PresupuestoDao daoPr = new PresupuestoDao();
			List<BigDecimal> listaPre = daoPr.datoPorLineaPaisE(idLinea);
			
			presupuestoMes = listaPre.get(0).longValue();
			realMes = listaPre.get(1).multiply(new BigDecimal("-1")).longValue();
			
			listaPre.add(1, new BigDecimal(realMes));
			BigDecimal porV =(listaPre.get(1).longValue() == 0)? new BigDecimal("0"): listaPre.get(1).divide(listaPre.get(0), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			int V = porV.intValue();
			
			interval = new ArrayList<Number>(){{
				add(60);
				add(85);
				add(100);
			}};			
			interval.add(3,(V >100)? V:100);
			

			meterGaugeModel = new MeterGaugeChartModel(V, interval);
			meterGaugeModel.setTitle("Cumplimiento Mes " + mesActual);
			meterGaugeModel.setGaugeLabel( V +"%");
			meterGaugeModel.setSeriesColors("ff0000, ffff00, 009933 ");	
			
			

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo promedio de ventas Pais");
		}
	}

	//*Promedio de ventas oficna   "dcB " "/dcB/vistaModulo"*//

	public void promedioVentasOficina(){

		try {
			FuncionarioDao daoF = new FuncionarioDao();
			Funcionario funcionario = daoF.buscarPersona(autenticacion.getUsuarioLogin().getPersona().getCedula());

			Zona_FuncionarioDao daoFu =  new Zona_FuncionarioDao();
			Zona_Funcionario zona = daoFu.buscarFuncionarioZona(funcionario.getId_funcionario());

			int ciudad = 0;
			Recursos recursos = new Recursos();
			if( autenticacion.getUsuarioLogin().getPerfil().getId() == 20){				
				ciudad = recursos.idOficina(idCiudad);
				CiudadDao dao = new CiudadDao();
				Ciudad ciudadA =dao.buscar(idCiudad);
				nombreSucursal = ciudadA.getNombre();
			}
			else if( autenticacion.getUsuarioLogin().getPerfil().getId() == 7 || autenticacion.getUsuarioLogin().getPerfil().getId() == 8){
				ciudad = recursos.idOficina(zona.getCiudad().getId());
				idCiudad = zona.getCiudad().getId();
			}
			else{
			   ciudad = recursos.idOficina(zona.getCiudad().getId());
			   idCiudad = ciudad / 1000;
			}
			
			PromedioVentaDao daoP = new PromedioVentaDao();			
			Double pro = ( autenticacion.getUsuarioLogin().getPerfil().getId() == 9)? daoP.promedioVentasOficinaI():daoP.promedioVentasOficina(ciudad);
			pro = (pro == null)? 0 : pro;
			promedioMes = new BigDecimal(pro.toString());
			proDescuento = ( autenticacion.getUsuarioLogin().getPerfil().getId() == 9)? daoP.promedioDescuentoOficinaI() :  daoP.promedioDescuentoOficina(ciudad);			
			promedioMes = getPromedioMes().multiply(new BigDecimal("-1"));	
			promedioVenta = daoP.listarMeta(funcionario.getId_funcionario());

			List<Number> interval = new ArrayList<Number>(){{
				add(5);
				add(6);
				add(15);
			}};
			interval.add(3,(proDescuento >15)? proDescuento:15); 
			descuentoP = new MeterGaugeChartModel(proDescuento, interval);
			descuentoP.setTitle("Cumplimiento " + mesActual + " Descuento");
			descuentoP.setGaugeLabel(proDescuento +"%");
			descuentoP.setSeriesColors("009933, ffff00, ff0000, ");

			PresupuestoDao daoPr = new PresupuestoDao();
			List<BigDecimal> listaPre = (autenticacion.getUsuarioLogin().getPerfil().getId() == 9)? daoPr.presupuestoPorOficinaI() : daoPr.presupuestoPorOficina(ciudad);
			
			presupuestoMes = listaPre.get(0).longValue();
			realMes = listaPre.get(1).multiply(new BigDecimal("-1")).longValue(); 
			lbr = listaPre.get(2).multiply(new BigDecimal("-1")).longValue();
			presupuestoRecaudo = listaPre.get(3);
			realRecaudo = listaPre.get(4);
			listaPre.add(1, new BigDecimal(realMes));
			BigDecimal porV =(listaPre.get(1).longValue() == 0)? new BigDecimal("0"): listaPre.get(1).divide(listaPre.get(0), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			int V = porV.intValue();
			
			interval = new ArrayList<Number>(){{
				add(60);
				add(85);
				add(100);
			}};
			
			interval.add(3,(V>100)? V:100); 
			meterGaugeModel = new MeterGaugeChartModel(V, interval);
			meterGaugeModel.setTitle("Cumplimiento " + mesActual + " Pedidos Facturados" );
			meterGaugeModel.setGaugeLabel( V +"%");
			meterGaugeModel.setSeriesColors("ff0000, ffff00, 009933 ");

			/**/
			Zona_ventaDao doaZ = new Zona_ventaDao();
			List<Zona_venta> listaZona = ( autenticacion.getUsuarioLogin().getPerfil().getId() == 20)? doaZ.buscarZonaSucursal(idCiudad) : doaZ.buscarZonaSucursal(zona.getCiudad().getId());
			System.out.println("ciuddddddddddddd" + idCiudad);
			IncidenciaDao daoI = new IncidenciaDao();
			List<BigDecimal>  totalWages = daoI.valorPedidosPerdidosSucursal(listaZona, idCiudad);
			pedidosPerdidos = totalWages.get(0);
			pedidosGanados = totalWages.get(1);
			pedidosProyectados = totalWages.get(2);
			
			
			
			//int sucursal = (zona.getCiudad().getId() == 1)? 1000 : (zona.getCiudad().getId() == 7)? 2000 : (zona.getCiudad().getId()+1)*1000;
			PedidosEnProcesoDao daoEP = new PedidosEnProcesoDao();
			pedidosProceso =  daoEP.valorPedidosProcesoOficina(ciudad);
			
//			PedidosProyectadosDao daoPe = new PedidosProyectadosDao();
//			pedidosProyectados = ( autenticacion.getUsuarioLogin().getPerfil().getId() == 20)? daoPe.valorPedidosProyectadosOficina(idCiudad): daoPe.valorPedidosProyectadosOficina(zona.getCiudad().getId());
			
			pedidosTotal =pedidosProceso.add(pedidosProyectados.add(new BigDecimal(realMes)));
			
			
			OfertasPedidosDao daoOF = new OfertasPedidosDao();
			List<BigDecimal> listaOferta = daoOF.sumaValorOfertasPedidosSucursal(ciudad);
			
			kpiOfertas = (listaOferta.get(1).intValue() == 0 || listaOferta.get(0).intValue() == 0)? new BigDecimal("0"): listaOferta.get(1).divide(listaOferta.get(0), 4, BigDecimal.ROUND_HALF_UP ).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
			kpiOimagen = (kpiOfertas.intValue() > 80)? "verde.png" : (kpiOfertas.intValue() > 40 &&  kpiOfertas.intValue() < 80)? "amarillo.jpg"  : "rojo.png";
			
			listaOferta= daoI.valorOportunidadNegocioOficina(idCiudad);			
			
			kpiOpoNeg = (listaOferta.get(1).intValue() == 0 || listaOferta.get(0).intValue() == 0)? new BigDecimal("0") :listaOferta.get(1).divide(listaOferta.get(0), 4, BigDecimal.ROUND_HALF_UP ).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
			kpiNimagen = (kpiOpoNeg.intValue() > 1 && kpiOpoNeg.intValue() < 20)? "verde.png" : (kpiOpoNeg.intValue() > 20 &&  kpiOpoNeg.intValue() < 40)? "amarillo.jpg"  : "rojo.png";
			
//			if( autenticacion.getUsuarioLogin().getPerfil().getId() == 20){				
//				pedidosTotal =pedidosProceso.add(pedidosProyectados.add(new BigDecimal(realMes)));
//			}
//			else{
//				pedidosTotal =pedidosProceso.add(pedidosProyectados.add(new BigDecimal(realMes)).add(new BigDecimal(lbr)));
//			}			
			
			
			porV =(pedidosTotal.longValue() == 0)? new BigDecimal("0"): pedidosTotal.divide(listaPre.get(0), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			V = porV.intValue();
			interval.add(3,(V >100)? V:100); 
			meterGaugeModelA = new MeterGaugeChartModel(V, interval);
			meterGaugeModelA.setTitle("Cumplimiento " + mesActual + " Pedidos Facturados Y Proyectados" );
			meterGaugeModelA.setGaugeLabel( V +"%");
			meterGaugeModelA.setSeriesColors("ff0000, ffff00, 009933 ");
			
			/**/


		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo promedio de ventas Pais");
		}
	}

	//*Carga la grafica de desempeño de las ventas y recaudo del "GG" *//

	public void desempenoVentasGerentes(){

		try{
			mesActual = mesActualG();
			mesInicial = "Enero";
			PresupuestoDao daoPr = new PresupuestoDao();
			List<BigDecimal> listaPre = daoPr.listaPresupuestoPaisMes();
			
			realMes = listaPre.get(0).multiply(new BigDecimal("-1")).longValue();
			presupuestoMes = listaPre.get(2).longValue();
			listaPre.add(0, new BigDecimal(realMes));
			listaPre.add(2, new BigDecimal(presupuestoMes));
			BigDecimal porV = (listaPre.get(2).longValue() == 0)? new BigDecimal("0") :listaPre.get(0).divide(listaPre.get(2), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			int V = porV.intValue();
			
			List<Number> interval = new ArrayList<Number>(){{
				add(60);
				add(85);
				add(100);
			}};

			interval.add(3,(V >100)? V:100);
			
			meterGaugeModel = new MeterGaugeChartModel(V, interval);
			meterGaugeModel.setTitle("Cumplimiento " + mesActual + " Pedidos Facturados" );
			meterGaugeModel.setGaugeLabel( V +"%");
			meterGaugeModel.setSeriesColors("ff0000, ffff00, 009933 ");
			
			
			/**/
			PedidosEnProcesoDao daoEP = new PedidosEnProcesoDao();
			pedidosProceso =  daoEP.valorPedidosProcesoPais();
			
			pedidosTotal =pedidosProceso.add(new BigDecimal(realMes));
			
			
			OfertasPedidosDao daoOF = new OfertasPedidosDao();
			List<BigDecimal> listaOferta = daoOF.sumaValorOfertasPedidosPais();
			
			kpiOfertas = (listaOferta.get(1).intValue() == 0 || listaOferta.get(0).intValue() == 0)? new BigDecimal("0"): listaOferta.get(1).divide(listaOferta.get(0), 4, BigDecimal.ROUND_HALF_UP ).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
			kpiOimagen = (kpiOfertas.intValue() > 80)? "verde.png" : (kpiOfertas.intValue() > 60 &&  kpiOfertas.intValue() < 80)? "amarillo.jpg"  : "rojo.png";
			
			IncidenciaDao daoI = new IncidenciaDao();
			listaOferta= daoI.valorOportunidadNegocioPais();
			
			kpiOpoNeg = (listaOferta.get(1).intValue() == 0 || listaOferta.get(0).intValue() == 0)? new BigDecimal("0"): listaOferta.get(1).divide(listaOferta.get(0), 4, BigDecimal.ROUND_HALF_UP ).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
			kpiNimagen = (kpiOpoNeg.intValue() > 1 && kpiOpoNeg.intValue() < 20)? "verde.png" : (kpiOpoNeg.intValue() > 20 &&  kpiOpoNeg.intValue() < 40)? "amarillo.jpg"  : "rojo.png";
			
			porV =(pedidosTotal.longValue() == 0)? new BigDecimal("0"): pedidosTotal.divide(listaPre.get(2), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			V = porV.intValue();
			System.out.println(V + " WWW "+ pedidosTotal + " --- " + listaPre.get(2) );
			interval.add(3,(V >100)? V:100); 
			meterGaugeModelA = new MeterGaugeChartModel(V, interval);
			meterGaugeModelA.setTitle("Cumplimiento " + mesActual + " Pedidos Facturados Y Proyectados" );
			meterGaugeModelA.setGaugeLabel( V +"%");
			meterGaugeModelA.setSeriesColors("ff0000, ffff00, 009933 ");
			/**/
			
			
			
			List<BigDecimal> listaPreA = daoPr.listaPresupuestoPaisAcumulado("2016");
			porV = listaPreA.get(0).divide(listaPreA.get(2), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			V = (porV.abs().intValue() > 100)? 100 : porV.abs().intValue();
			
			descuentoP = new MeterGaugeChartModel(V, interval);
			descuentoP.setTitle("Acumulado de " +  mesInicial + " a " + mesActual);
			descuentoP.setGaugeLabel(V +"%");
			descuentoP.setSeriesColors("ff0000, ffff00, 009933");
			
			realAcumulado = listaPreA.get(0).abs().longValue();
			presupuestoAcumulado = listaPreA.get(2).abs().longValue();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ultima Actualización "+ autenticacion.getFechaDiaAnterior(), ""));
			presupuestoOficina();
			presupuestoLinea();

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo promedio de ventas Pais");
		}
	}
	
	public void presupuestoOficina(){
		
		try{
			CiudadDao daoC = new CiudadDao();
			ListaComisionVendedores = daoC.realOficinaMes("", "");
			BigDecimal sumaReal = new BigDecimal("0.00");
			BigDecimal cumplimiento = new BigDecimal("0.00");
			BigDecimal sumaPresupuesto = new BigDecimal("0.00");
			BigDecimal sumaRealU = new BigDecimal("0.00");
			BigDecimal cumplimientoU = new BigDecimal("0.00");
			BigDecimal sumaPresupuestoU = new BigDecimal("0.00");
			for (ComisionVendedores oficina : ListaComisionVendedores) {
				sumaPresupuesto = sumaPresupuesto.add(oficina.getPresupuestoB());
				sumaReal = sumaReal.add(oficina.getIngresoRealB());
				sumaPresupuestoU = sumaPresupuestoU.add(oficina.getUtilpresupuesto());
				sumaRealU = sumaRealU.add(oficina.getUtilidadReal());
			}
			cumplimiento = sumaReal.divide(sumaPresupuesto, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			cumplimientoU = sumaRealU.divide(sumaPresupuestoU, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			totalPreO =  new DecimalFormat("###,###").format(sumaPresupuesto);
			totolRealO =  new DecimalFormat("###,###").format(sumaReal);
			totalCumO =  new DecimalFormat("###,###").format(cumplimiento);
			totalPreUO =  new DecimalFormat("###,###").format(sumaPresupuestoU);
			totolRealUO =  new DecimalFormat("###,###").format(sumaRealU);
			totalCumUO =  new DecimalFormat("###,###").format(cumplimientoU);
			
			
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo promedio de ventas Pais");
		}
	}

	public void presupuestoLinea(){
		
		try{
			LineaDao daoL = new LineaDao();
			listaLineas = daoL.realLineasMes("", "");
			BigDecimal sumaReal = new BigDecimal("0.00");
			BigDecimal cumplimiento = new BigDecimal("0.00");
			BigDecimal sumaPresupuesto = new BigDecimal("0.00");
			BigDecimal sumaRealU = new BigDecimal("0.00");
			BigDecimal cumplimientoU = new BigDecimal("0.00");
			BigDecimal sumaPresupuestoU = new BigDecimal("0.00");
			for (ComisionVendedores linea : listaLineas) {
				sumaPresupuesto = sumaPresupuesto.add(linea.getPresupuestoB());
				sumaReal = sumaReal.add(linea.getIngresoRealB());
				sumaPresupuestoU = sumaPresupuestoU.add(linea.getUtilpresupuesto());
				sumaRealU = sumaRealU.add(linea.getUtilidadReal());
			}
			cumplimiento = sumaReal.divide(sumaPresupuesto, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			cumplimientoU = sumaRealU.divide(sumaPresupuestoU, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			totalPreL =  new DecimalFormat("###,###").format(sumaPresupuesto);
			totolRealL =  new DecimalFormat("###,###").format(sumaReal);
			totalCumL =  new DecimalFormat("###,###").format(cumplimiento);
			totalPreUL =  new DecimalFormat("###,###").format(sumaPresupuestoU);
			totolRealUL =  new DecimalFormat("###,###").format(sumaRealU);
			totalCumUL =  new DecimalFormat("###,###").format(cumplimientoU);
			
			
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo promedio de ventas Pais");
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
			System.out.println(mes);
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
	public AutenticacionBean getAutenticacion() {
		return autenticacion;
	}
	public void setAutenticacion(AutenticacionBean autenticacion) {
		this.autenticacion = autenticacion;
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
	public MeterGaugeChartModel getMeterGaugeModel() {
		return meterGaugeModel;
	}
	public void setMeterGaugeModel(MeterGaugeChartModel meterGaugeModel) {
		this.meterGaugeModel = meterGaugeModel;
	}
	public BigDecimal getPromedioMes() {
		return promedioMes;
	}
	public void setPromedioMes(BigDecimal promedioMes) {
		this.promedioMes = promedioMes;
	}
	public PromedioVenta getPromedioVenta() {
		return promedioVenta;
	}
	public void setPromedioVenta(PromedioVenta promedioVenta) {
		this.promedioVenta = promedioVenta;
	}
	public int getDescuentoMes() {
		return descuentoMes;
	}
	public void setDescuentoMes(int descuentoMes) {
		this.descuentoMes = descuentoMes;
	}
	public Long getProDescuento() {
		return proDescuento;
	}
	public void setProDescuento(Long proDescuento) {
		this.proDescuento = proDescuento;
	}
	public MeterGaugeChartModel getDescuentoP() {
		return descuentoP;
	}

	public void setDescuentoP(MeterGaugeChartModel descuentoP) {
		this.descuentoP = descuentoP;
	}
	public int getIdLinea() {
		return idLinea;
	}
	public void setIdLinea(int idLinea) {
		this.idLinea = idLinea;
	}
	public Long getRealMes() {
		return realMes;
	}
	public void setRealMes(Long realMes) {
		this.realMes = realMes;
	}
	public Long getPresupuestoMes() {
		return presupuestoMes;
	}
	public void setPresupuestoMes(Long presupuestoMes) {
		this.presupuestoMes = presupuestoMes;
	}
	public Long getPresupuestoAcumulado() {
		return presupuestoAcumulado;
	}
	public void setPresupuestoAcumulado(Long presupuestoAcumulado) {
		this.presupuestoAcumulado = presupuestoAcumulado;
	}
	public Long getRealAcumulado() {
		return realAcumulado;
	}
	public void setRealAcumulado(Long realAcumulado) {
		this.realAcumulado = realAcumulado;
	}
	public List<ComisionVendedores> getListaComisionVendedores() {
		return ListaComisionVendedores;
	}
	public void setListaComisionVendedores(List<ComisionVendedores> listaComisionVendedores) {
		ListaComisionVendedores = listaComisionVendedores;
	}
	public List<ComisionVendedores> getListaLineas() {
		return listaLineas;
	}
	public void setListaLineas(List<ComisionVendedores> listaLineas) {
		this.listaLineas = listaLineas;
	}
	public String getTotalPreO() {
		return totalPreO;
	}
	public void setTotalPreO(String totalPreO) {
		this.totalPreO = totalPreO;
	}
	public String getTotolRealO() {
		return totolRealO;
	}
	public void setTotolRealO(String totolRealO) {
		this.totolRealO = totolRealO;
	}
	public String getTotalCumO() {
		return totalCumO;
	}
	public void setTotalCumO(String totalCumO) {
		this.totalCumO = totalCumO;
	}
	public String getTotalPreL() {
		return totalPreL;
	}
	public void setTotalPreL(String totalPreL) {
		this.totalPreL = totalPreL;
	}
	public String getTotolRealL() {
		return totolRealL;
	}
	public void setTotolRealL(String totolRealL) {
		this.totolRealL = totolRealL;
	}
	public String getTotalCumL() {
		return totalCumL;
	}
	public void setTotalCumL(String totalCumL) {
		this.totalCumL = totalCumL;
	}
	public String getMesActual() {
		return mesActual;
	}
	public void setMesActual(String mesActual) {
		this.mesActual = mesActual;
	}
	public String getMesInicial() {
		return mesInicial;
	}
	public void setMesInicial(String mesInicial) {
		this.mesInicial = mesInicial;
	}
	public String getTotalPreUO() {
		return totalPreUO;
	}
	public void setTotalPreUO(String totalPreUO) {
		this.totalPreUO = totalPreUO;
	}
	public String getTotolRealUO() {
		return totolRealUO;
	}
	public void setTotolRealUO(String totolRealUO) {
		this.totolRealUO = totolRealUO;
	}
	public String getTotalCumUO() {
		return totalCumUO;
	}
	public void setTotalCumUO(String totalCumUO) {
		this.totalCumUO = totalCumUO;
	}
	public String getTotalPreUL() {
		return totalPreUL;
	}
	public void setTotalPreUL(String totalPreUL) {
		this.totalPreUL = totalPreUL;
	}
	public String getTotolRealUL() {
		return totolRealUL;
	}
	public void setTotolRealUL(String totolRealUL) {
		this.totolRealUL = totolRealUL;
	}
	public String getTotalCumUL() {
		return totalCumUL;
	}
	public void setTotalCumUL(String totalCumUL) {
		this.totalCumUL = totalCumUL;
	}
	public BigDecimal getPedidosTotal() {
		return pedidosTotal;
	}
	public void setPedidosTotal(BigDecimal pedidosTotal) {
		this.pedidosTotal = pedidosTotal;
	}
	public BigDecimal getPedidosProyectados() {
		return pedidosProyectados;
	}
	public void setPedidosProyectados(BigDecimal pedidosProyectados) {
		this.pedidosProyectados = pedidosProyectados;
	}
	public BigDecimal getPedidosProceso() {
		return pedidosProceso;
	}
	public void setPedidosProceso(BigDecimal pedidosProceso) {
		this.pedidosProceso = pedidosProceso;
	}
	public BigDecimal getPedidosPerdidos() {
		return pedidosPerdidos;
	}
	public void setPedidosPerdidos(BigDecimal pedidosPerdidos) {
		this.pedidosPerdidos = pedidosPerdidos;
	}
	public MeterGaugeChartModel getMeterGaugeModelA() {
		return meterGaugeModelA;
	}
	public void setMeterGaugeModelA(MeterGaugeChartModel meterGaugeModelA) {
		this.meterGaugeModelA = meterGaugeModelA;
	}
	public Long getLbr() {
		return lbr;
	}
	public void setLbr(Long lbr) {
		this.lbr = lbr;
	}
	public int getIdCiudad() {
		return idCiudad;
	}
	public void setIdCiudad(int idCiudad) {
		this.idCiudad = idCiudad;
	}
	public String getNombreSucursal() {
		return nombreSucursal;
	}
	public void setNombreSucursal(String nombreSucursal) {
		this.nombreSucursal = nombreSucursal;
	}
	public BigDecimal getPedidosGanados() {
		return pedidosGanados;
	}
	public void setPedidosGanados(BigDecimal pedidosGanados) {
		this.pedidosGanados = pedidosGanados;
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
}
