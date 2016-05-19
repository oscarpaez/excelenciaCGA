package co.com.CGAwebComercial.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import co.com.CGAwebComercial.dao.BajaRotacionDao;
import co.com.CGAwebComercial.dao.ContadoAnticipoDao;
import co.com.CGAwebComercial.dao.DetalleDao;
import co.com.CGAwebComercial.dao.FuncionarioDao;
import co.com.CGAwebComercial.dao.PromedioVentaDao;
import co.com.CGAwebComercial.dao.RecaudoDao;
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
	private BarChartModel descuentoV;
	 
	private PromedioVenta promedioVenta;
	
	private Recursos recurso;
	private int indexVIE=0;
	private int codVen;
	private String tipo="";
	private String fechaBusqueda ="";
	private String fechaBusquedaYear= "";
	private Double promedioMes;
	
	
	
	public vendedorLBRBean(){
		
		recurso = new Recursos();
		listaFechas = recurso.cargarFechas();
	}
	
	
	public void vendedorLBR(){

		try{
			listaVendedores = new ArrayList<>();
			FuncionarioDao daoF = new FuncionarioDao();
			Funcionario funcionario = daoF.buscarPersona(indexVIE);
			System.out.println( "este t" + tipo );  
			tipo = (tipo.equals("funcionario"))? "codEspecialista": (tipo.equals("codEspecialista"))? "codEspecialista": "codVendedorInt";
			
			ComisionVendedores vendedor = new ComisionVendedores();
			BajaRotacionDao daoB = new BajaRotacionDao();
			Long sumatotal;
			if(autenticacion.getFechaBusqueda() == null || autenticacion.getFechaBusqueda().equals("")){
				System.out.println(fechaBusqueda +" entro " +fechaBusquedaYear );
				Messages.addGlobalInfo(tipo + " Fecha null " + indexVIE);
				sumatotal = daoB.SumaTotalFechas( tipo, funcionario.getId_funcionario(), fechaBusqueda, fechaBusquedaYear);
			}
			else{
				System.out.println(fechaBusqueda +" confec " +fechaBusquedaYear );
				fechaBusqueda = autenticacion.getFechaBusqueda();
				fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
				System.out.println(tipo + " Fecha null " + funcionario.getId_funcionario());
				System.out.println(fechaBusqueda +" confec2 " +fechaBusquedaYear );
				//Messages.addGlobalInfo("Fecha valida " + autenticacion.getFechaBusqueda());
				sumatotal = daoB.SumaTotalFechas( tipo, funcionario.getId_funcionario(), fechaBusqueda, fechaBusquedaYear);
			}	
						
				System.out.println("suma" + sumatotal);
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
			if(autenticacion.getFechaBusqueda() != null ){
				fechaBusqueda = autenticacion.getFechaBusqueda();
				fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			}
			System.out.println("codigo " + codVen);
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

			if(desempenoVentas == null){
				LineChartModel model = new LineChartModel();
				LineChartModel model2 = new LineChartModel();
				BarChartModel model3 = new BarChartModel();
				LineChartSeries presupuesto = new LineChartSeries();
				LineChartSeries ventas = new LineChartSeries();

				LineChartSeries presupuestoI = new LineChartSeries();
				LineChartSeries realI = new LineChartSeries();
				LineChartSeries presupuestoU = new LineChartSeries();
				ChartSeries descuento = new ChartSeries();

				RecaudoDao dao = new RecaudoDao();
				List<Recaudo> listaRecaudo = new ArrayList<>();
				FuncionarioDao daoF = new FuncionarioDao();
				Funcionario funcionario = daoF.buscarPersona(autenticacion.getUsuarioLogin().getPersona().getCedula());
				if(autenticacion.getUsuarioLogin().getPerfil().getId() == 6){
					listaRecaudo = dao.carteraInternosGraficas(funcionario.getId_funcionario());
					tipo = "funcionarioI";
				}	
				else{
					listaRecaudo = dao.listarPresupuesto(autenticacion.getUsuarioLogin().getPersona().getCedula());
					tipo = "funcionario";
				}
				System.out.println( autenticacion.getUsuarioLogin().getPerfil().getId()+ "tipo :" + tipo );
				
				int i=0;
				String[] periodo = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
						"Julio", "Agosto", "Septiembre", "Octubre", "Noviembre",
				"Diciembre"}; 
				int[] P = new int[listaRecaudo.size()];
				int[] R = new int[listaRecaudo.size()];
				int[] PI = new int[listaRecaudo.size()];
				int[] PU = new int[listaRecaudo.size()];
				int[] PT = new int[listaRecaudo.size()];
				int[] D = new int[listaRecaudo.size()];
				

				List<Long>  PV = dao.listarDetalleValorNeto(tipo,funcionario.getId_funcionario());
				List<Long>  PPT = dao.listarDetalleCostoTotal(tipo, funcionario.getId_funcionario());
				List<Long>  PVD = dao.listarDetalleValorDescuento(tipo,funcionario.getId_funcionario());
				System.out.println(PVD.size());
				for (Recaudo recaudo : listaRecaudo) {
					System.out.println(recaudo.getPresupuesto() + ", " + recaudo.getReal());
					P[i] = recaudo.getPresupuesto().intValue();
					R[i] = recaudo.getReal().intValue();
					//PU[i] = recaudo.get
					System.out.println(P[i] + " ** " + R[i] );
					presupuesto.set(periodo[i], P[i]);
					ventas.set(periodo[i], R[i] );
					
					PI[i] =(int) (long) PV.get(i)* -1;					
					presupuestoI.set(periodo[i], PI[i] );

					PT[i] =(int) (long) PPT.get(i);
					realI.set( periodo[i], PT[i] );

					PU[i] =(int) (long) PPT.get(i);
					PU[i]= PU[i] - PI[i];
					presupuestoU.set(periodo[i], PU[i]);
					D[i] = (int) (long) PVD.get(i);
					BigDecimal desc = new BigDecimal(D[i]); 
					BigDecimal desc1 = new BigDecimal(PI[i]);
					desc = desc.divide(desc1,  2, RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
					descuento.set(periodo[i], desc.intValue() );
					i++;
				}

				ventas.setLabel("Real");
				presupuesto.setLabel("Plan");			

				model.addSeries(presupuesto);
				model.addSeries(ventas);

				desempenoVentas = model;
				desempenoVentas.setTitle("Desempeño Recaudo");
				desempenoVentas.setAnimate(true);
				desempenoVentas.setLegendPosition("ne");

				desempenoVentas.setShowPointLabels(true);
				desempenoVentas.getAxes().put(AxisType.X, new CategoryAxis("Periodo"));
				Axis yAxis = desempenoVentas.getAxis(AxisType.Y);
				yAxis.setMin(0);
				yAxis.setMax(500000000);
				//yAxis.setTickFormat("%'d");

				presupuestoI.setLabel("Presupuesto Ingresos");
				//presupuestoU.setLabel("Real utilidad");
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
				xAxis.setMax(1000000000);
				xAxis.setTickFormat("%'d");
				
				descuento.setLabel("Descuentos");				
				model3.addSeries(descuento);
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
}
