package co.com.CGAwebComercial.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import co.com.CGAwebComercial.dao.AjusteDao;
import co.com.CGAwebComercial.dao.DetalleDao;
import co.com.CGAwebComercial.dao.FuncionarioDao;
import co.com.CGAwebComercial.dao.LiquidacionDao;
import co.com.CGAwebComercial.dao.PresupuestoDao;
import co.com.CGAwebComercial.entyties.Ajuste;
import co.com.CGAwebComercial.entyties.Funcionario;
import co.com.CGAwebComercial.entyties.Liquidacion;
import co.com.CGAwebComercial.entyties.Plan;
import co.com.CGAwebComercial.resource.Recursos;
import co.com.CGAwebComercial.util.ComisionVendedores;
import co.com.CGAwebComercial.util.Fechas;


@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ComisionBean implements Serializable{

	@ManagedProperty("#{autenticacionBean}")
	private AutenticacionBean autenticacion;
	
	private Recursos recurso;

	private List<ComisionVendedores> ListaComisionVendedores;
	private List<ComisionVendedores> listaFiltrados;
	private List<Ajuste> listaVendedoresAjuste;
	private List<Plan> listaplanV;
	private List<Fechas> listaFechas;
	private Plan plan1;
	private Ajuste ajuste;
	private Liquidacion liquidacion;
	private ComisionVendedores vendedor;

	private BigDecimal total;
	private BigDecimal totalR;
	private BigDecimal umbral;
	private String fechaBusqueda;
	private String fechaBusquedaYear;
	private int idFuncionario;
	private int idFuncionarioSin;
	private int bajRoot =0;
	private int codSap= 0;
	private int totalAjuste=0;
	private Integer progress=0;
	private String nombreRegistro="";
	private String accion;
	private String accionR;
	private String totalPreIng;
	private String totalPreUti;
	private String totalRealIng;
	private String totalRealUti;
	private String bajRot;
	private String sin;
	private String tipo;
	private String habilitar;
	private String liquidar;
	
	public ComisionBean(){
		recurso = new Recursos();
		listaFechas = recurso.cargarFechas();
		habilitar="false";
	}
	
	
	@SuppressWarnings("unused")
	public void listarComisionVendedores(){

		try{
			recurso = new Recursos();
			plan1 = new Plan();
			int i = 0; 
			int progress1  = 0;
			setProgress(0);
			if(autenticacion.getFechaBusqueda().equals("") ){
				
				listaFechas = recurso.cargarFechas();
				total =  new BigDecimal("0.00");
				BigDecimal valorReal = new BigDecimal("0.00");
				BigDecimal valorUtilidad = new BigDecimal("0.00");
				BigDecimal sumaPresupuesto = new BigDecimal("0.00");
				BigDecimal sumaIngresoR = new BigDecimal("0.00");
				BigDecimal pre = new BigDecimal("0.00");
				List<Funcionario> listaVendedor = null;
				int numero = 0;
				int numero1 = 0;
				int j =0;
				int t =0;
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
				for (Funcionario funcionario : listaVendedor) {
					    //if(i < 5){
						valorReal = new BigDecimal("0.00");
						valorUtilidad = new BigDecimal("0.00");
						sumaPresupuesto = new BigDecimal("0.00");
						sumaIngresoR = new BigDecimal("0.00");	
						total =  new BigDecimal("0.00");
						totalR =  new BigDecimal("0.00");
						DetalleDao daoD = new DetalleDao();	
						ComisionVendedores vendedores = new ComisionVendedores();
						vendedores.setId(funcionario.getId_funcionario());
						vendedores.setCedula(funcionario.getPersona().getCedula());
						vendedores.setUmbralCV(new BigDecimal("0.00"));
						vendedores.setNombre(funcionario.getPersona().getNombre());
						vendedores.setTipo(funcionario.getComision().getNombre());
						
						if(autenticacion.getIndex() == 2){
							vendedores.setConcepto("VentaSinLBR");
							PresupuestoDao daoP = new PresupuestoDao();
							pre = daoP.datoPorLineaSum(funcionario.getId_funcionario());
							sumaPresupuesto = (pre == null) ? new BigDecimal("0.00"): pre;
							listaplanV = daoD.listarPlanSinPrueba(tipo,funcionario.getId_funcionario());
						}
						else{
							vendedores.setConcepto("VentaConLBR");
							PresupuestoDao daoP = new PresupuestoDao();
							pre = daoP.datoPorLineaSum(funcionario.getId_funcionario());
							sumaPresupuesto = (pre == null) ? new BigDecimal("0.00"): pre;
							listaplanV = daoD.listarPlanPrueba(tipo, funcionario.getId_funcionario());
						}
						
						for (Plan planL : listaplanV) {
	
							sumaIngresoR = (planL.getIngreso_Real() == null) ? new BigDecimal("0.00"): sumaIngresoR.add(planL.getIngreso_Real()); 
								
							if(planL.getIngreso() == null || planL.getIngreso().compareTo(BigDecimal.ZERO) == 0){
								valorReal = new BigDecimal("0.00");
								planL.setIngreso_Cumplimiento(valorReal);
							}
							else{
								valorReal = planL.getIngreso_Real().divide(planL.getIngreso(), 1, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
								planL.setIngreso_Cumplimiento(valorReal);
							}
							
							if(planL.getUtilidad() == null || planL.getUtilidad().compareTo(BigDecimal.ZERO) == 0){
								valorReal = new BigDecimal("0.00");
								planL.setUtilidad_Cumplimiento(valorReal);
							}
							else{
								valorReal = planL.getUtilidad_Real().divide(planL.getUtilidad(), 1, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
								planL.setUtilidad_Cumplimiento(valorReal);
							}
							
							if(umbral != null){
								//autenticacion.setUmbral(umbral);
								planL.setUmbral(umbral);
								numero = umbral.compareTo(planL.getUtilidad_Cumplimiento().divide(new BigDecimal("100.00")));
								numero1 = umbral.compareTo(planL.getIngreso_Cumplimiento().divide(new BigDecimal("100.00")));
							}
							else{
								if(planL.getFuncionario().getComision().getUmbralVenta() == null){
									planL.setUmbral(new BigDecimal("0.00"));
								} 
								else{
									planL.setUmbral(planL.getFuncionario().getComision().getUmbralVenta());
								}
								numero = planL.getFuncionario().getComision().getUmbralVenta().compareTo(planL.getUtilidad_Cumplimiento().divide(new BigDecimal("100.00")));
								numero1 = planL.getFuncionario().getComision().getUmbralVenta().compareTo(planL.getIngreso_Cumplimiento().divide(new BigDecimal("100.00")));
							}
	
	
							if(numero == 1 ){
								plan1.setValor_Comision_Pagar(new BigDecimal("0.00"));
								total = total.add(plan1.getValor_Comision_Pagar());
							}
							else{
								valorUtilidad= planL.getDistribucion_Linea().multiply(planL.getFuncionario().getComision().getValorBaseVenta().divide(new BigDecimal("100.00")));
								valorUtilidad= valorUtilidad.multiply(planL.getUtilidad_Cumplimiento()); 
								planL.setValor_Comision_Pagar(valorUtilidad);
								total = total.add(planL.getValor_Comision_Pagar());
							}
							
							if(numero1 == 1 ){
								plan1.setValor_Comision_PagarR(new BigDecimal("0.00"));
								totalR = totalR.add(plan1.getValor_Comision_PagarR());
							}
							else{
								valorUtilidad= planL.getDistribucion_Linea().multiply(planL.getFuncionario().getComision().getValorBaseVenta().divide(new BigDecimal("100.00")));
								valorUtilidad= valorUtilidad.multiply(planL.getIngreso_Cumplimiento()); 
								planL.setValor_Comision_PagarR(valorUtilidad);
								totalR = totalR.add(planL.getValor_Comision_PagarR());								
							}
						}
						vendedores.setPresupuesto(sumaPresupuesto.intValue());
						vendedores.setIngresoReal(sumaIngresoR.intValue());
						vendedores.setComision(total.intValue());
						vendedores.setComisionR(totalR.intValue());
						ListaComisionVendedores.add(j,vendedores);
						valorReal = new BigDecimal("0.00");
						valorUtilidad = new BigDecimal("0.00");
						sumaPresupuesto = new BigDecimal("0.00");
						sumaIngresoR = new BigDecimal("0.00");
						pre = new BigDecimal("0.00");
						j++;
						t += progress1;
						}
					i++;
				//}
			}
			else{
				listaFechas = recurso.cargarFechas();
				fechaBusqueda = autenticacion.getFechaBusqueda();
				fechaBusquedaYear = autenticacion.getFechaBusquedaYear();	
				listarComisionVendedoresFechas();				
				
			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Comision de Vendedores");
		}	

	}

	@SuppressWarnings("unused")
	public void listarComisionVendedoresFechas(){

		try{
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();	
			int progress1  = 0;
			setProgress(0);
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
				for (Funcionario funcionario : listaVendedor) {

					   
					    //if(i>5 &&  i<7){
					    valorReal = new BigDecimal("0.00");
						valorUtilidad = new BigDecimal("0.00");
						sumaPresupuesto = new BigDecimal("0.00");
						sumaIngresoR = new BigDecimal("0.00");	
						totalR =  new BigDecimal("0.00");
						total =  new BigDecimal("0.00");
						DetalleDao daoD = new DetalleDao();	
						ComisionVendedores vendedores = new ComisionVendedores();
						vendedores.setId(funcionario.getId_funcionario());
						vendedores.setCedula(funcionario.getPersona().getCedula());
						vendedores.setUmbralCV(new BigDecimal("0.00"));
						vendedores.setNombre(funcionario.getPersona().getNombre());
						vendedores.setTipo(funcionario.getComision().getNombre());
						
						if(autenticacion.getIndex() == 2){
							vendedores.setConcepto("VentaSinLBR");
							LiquidacionDao daoL = new LiquidacionDao();
							List<Liquidacion> liquidacion = daoL.buscarLiquidacion("VentaSinLBR", funcionario.getId_funcionario());
							liquidar = (liquidacion.size() <= 0) ? "false" : "true";
							vendedores.setLiquidar(liquidar);
							PresupuestoDao daoP = new PresupuestoDao();
							BigDecimal pre = daoP.datoPorLineaSumFechas(funcionario.getId_funcionario(), fechaBusqueda, fechaBusquedaYear);
							sumaPresupuesto = (pre == null) ? new BigDecimal("0.00"): pre;
							listaplanV = daoD.listarPlanPorFechasSinPrueba(tipo,funcionario.getId_funcionario(), fechaBusqueda, fechaBusquedaYear);
						}
						else{
							vendedores.setConcepto("VentaConLBR");
							LiquidacionDao daoL = new LiquidacionDao();
							List<Liquidacion> liquidacion = daoL.buscarLiquidacion("VentaConLBR", funcionario.getId_funcionario());
							liquidar = (liquidacion.size() <= 0) ? "false" : "true";
							vendedores.setLiquidar(liquidar);
							PresupuestoDao daoP = new PresupuestoDao();
							BigDecimal pre = daoP.datoPorLineaSumFechas(funcionario.getId_funcionario(), fechaBusqueda, fechaBusquedaYear);
							sumaPresupuesto = (pre == null) ? new BigDecimal("0.00"): pre;
							listaplanV = daoD.listarPlanPorFechasPrueba(tipo,funcionario.getId_funcionario(), fechaBusqueda, fechaBusquedaYear);
						}
						
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
							
							if(umbral != null){
								//autenticacion.setUmbral(umbral);
								planL.setUmbral(umbral);
								numero = umbral.compareTo(planL.getUtilidad_Cumplimiento().divide(new BigDecimal("100.00")));
								numero1 = umbral.compareTo(planL.getIngreso_Cumplimiento().divide(new BigDecimal("100.00")));
							}
							else{
								
								if(planL.getFuncionario().getComision().getUmbralVenta() == null){
									planL.setUmbral(new BigDecimal("0.00"));
								} 
								else{
									planL.setUmbral(planL.getFuncionario().getComision().getUmbralVenta());
								}
								
								numero = planL.getFuncionario().getComision().getUmbralVenta().compareTo(planL.getUtilidad_Cumplimiento().divide(new BigDecimal("100.00")));
								numero1 = planL.getFuncionario().getComision().getUmbralVenta().compareTo(planL.getIngreso_Cumplimiento().divide(new BigDecimal("100.00")));
							}
							
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
						vendedores.setComision(total.intValue());
						vendedores.setComisionR(totalR.intValue());
						vendedores.setUmbralCV(total);
						ListaComisionVendedores.add(j,vendedores);
						j++;
						t += progress1;
						setProgress(t);
						//}
					i++;
				}
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

	public void listarPlan(){

		try{

			if(autenticacion.getFechaBusqueda().equals("")){

				total =  new BigDecimal("0.00" );
				BigDecimal totalPresupuesto = new BigDecimal("0.00");
				BigDecimal totalPresupuestoUtilidad = new BigDecimal("0.00");
				BigDecimal totalValorReal = new BigDecimal("0.00");
				BigDecimal valorReal = new BigDecimal("0.00");				
				BigDecimal totalValorUtilidad = new BigDecimal("0.00");
				BigDecimal valorUtilidad = new BigDecimal("0.00");
				int numero = 0;
				FuncionarioDao daoF = new FuncionarioDao();
				Funcionario funcionario = new Funcionario();
				DetalleDao daoD = new DetalleDao();	
				if(idFuncionarioSin != 0){
					funcionario = daoF.buscarPersona(idFuncionarioSin);
					nombreRegistro = funcionario.getPersona().getNombre();
					listaplanV = daoD.listarPlanSin(funcionario.getId_funcionario());
				}
				else{
					funcionario = daoF.buscarPersona(idFuncionario);
					nombreRegistro = funcionario.getPersona().getNombre();
					listaplanV = daoD.listarPlan(funcionario.getId_funcionario());
				}

				for (Plan planL : listaplanV) {

					if(planL.getIngreso() == null){
						planL.setIngreso(new BigDecimal("0.00"));
					}

					if(planL.getUtilidad() == null){
						planL.setUtilidad(new BigDecimal("0.00"));
					}

					totalPresupuesto = totalPresupuesto.add(planL.getIngreso());
					totalPresupuestoUtilidad = totalPresupuestoUtilidad.add(planL.getUtilidad()); 
					totalValorReal = totalValorReal.add(planL.getIngreso_Real());
					totalValorUtilidad = totalValorUtilidad.add(planL.getUtilidad_Real());
					valorReal = planL.getIngreso_Real().divide(planL.getIngreso(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
					planL.setIngreso_Cumplimiento(valorReal);

					valorReal = planL.getUtilidad_Real().divide(planL.getUtilidad(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
					planL.setUtilidad_Cumplimiento(valorReal);
					if(umbral != null){
						planL.setUmbral(umbral);
						numero = umbral.compareTo(planL.getUtilidad_Cumplimiento().divide(new BigDecimal("100.00")));
					}
					else{
						planL.setUmbral(planL.getFuncionario().getComision().getUmbralVenta());
						numero = planL.getFuncionario().getComision().getUmbralVenta().compareTo(planL.getUtilidad_Cumplimiento().divide(new BigDecimal("100.00")));
					}

					if(numero == 1 ){
						planL.setValor_Comision_Pagar(new BigDecimal("0.00"));
						total = total.add(planL.getValor_Comision_Pagar());
						accion= new DecimalFormat("###,###").format(total);
						planL.setImagen("rojo.png");
					}
					else{
						valorUtilidad= planL.getDistribucion_Linea().multiply(planL.getFuncionario().getComision().getValorBaseVenta().divide(new BigDecimal("100.00")));
						valorUtilidad= valorUtilidad.multiply(planL.getUtilidad_Cumplimiento()); 
						planL.setValor_Comision_Pagar(valorUtilidad);
						total = total.add(planL.getValor_Comision_Pagar());
						accion= new DecimalFormat("###,###").format(total);
						planL.setImagen("verde.png");
					}
				}
				totalPreIng = new DecimalFormat("###,###").format(totalPresupuesto);
				totalPreUti = new DecimalFormat("###,###").format(totalPresupuestoUtilidad);
				totalRealIng = new DecimalFormat("###,###").format(totalValorReal);
				totalRealUti = new DecimalFormat("###,###").format(totalValorUtilidad);
			}
			else{
				fechaBusqueda = autenticacion.getFechaBusqueda();
				fechaBusquedaYear = autenticacion.getFechaBusquedaYear();	
				listarPlanPorFechas();
			}


		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista del Plan");
		}

	}
	
	public void listarPlanPorFechas(){
		
		try {
			total =  new BigDecimal("0.00");
			totalR =  new BigDecimal("0.00");
			BigDecimal totalPresupuesto = new BigDecimal("0.00");
			BigDecimal totalPresupuestoUtilidad = new BigDecimal("0.00");
			BigDecimal totalValorReal = new BigDecimal("0.00");
			BigDecimal totalValorUtilidad = new BigDecimal("0.00");
			BigDecimal valorReal = new BigDecimal("0.00");
			BigDecimal valorUtilidad = new BigDecimal("0.00");
			BigDecimal valorUtilidadR = new BigDecimal("0.00");
			int numero = 0;
			int numero1 = 0;
			int i = 0;
			FuncionarioDao daoF = new FuncionarioDao();
			//Funcionario funcionario = daoF.buscarPersona(autenticacion.getUsuarioLogin().getPersona().getCedula());
			Funcionario funcionario;
			tipo =(tipo == null) ? (autenticacion.getTipoVendedor().equals("I") )? "funcionarioI" : "funcionario" : tipo;
			
			
			DetalleDao daoD = new DetalleDao();	
			if(fechaBusqueda != null && fechaBusquedaYear != null){
				
				if(idFuncionarioSin != 0){
					funcionario = daoF.buscarPersona(idFuncionarioSin);					
					nombreRegistro = funcionario.getPersona().getNombre();
					listaplanV = daoD.listarPlanPorFechasSinPrueba(tipo, funcionario.getId_funcionario(), fechaBusqueda, fechaBusquedaYear);
				}
				else{
					funcionario = daoF.buscarPersona(idFuncionario);
					nombreRegistro = funcionario.getPersona().getNombre();
					
					listaplanV = daoD.listarPlanPorFechasPrueba(tipo,funcionario.getId_funcionario(), fechaBusqueda, fechaBusquedaYear);
				}
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
								valorUtilidad= plan.getDistribucion_Linea().multiply(plan.getFuncionario().getComision().getValorBaseVenta().divide(new BigDecimal("100")));
								valorUtilidad= valorUtilidad.multiply(plan.getUtilidad_Cumplimiento()); 
								plan.setValor_Comision_Pagar(valorUtilidad);
								total = total.add(plan.getValor_Comision_Pagar());
								accion= new DecimalFormat("###,###").format(total);
								plan.setImagen("verde.png");
							}
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
								valorUtilidadR= plan.getDistribucion_Linea().multiply(plan.getFuncionario().getComision().getValorBaseVenta().divide(new BigDecimal("100")));
								valorUtilidadR= valorUtilidadR.multiply(plan.getIngreso_Cumplimiento()); 
								plan.setValor_Comision_PagarR(valorUtilidadR);
								totalR = totalR.add(plan.getValor_Comision_PagarR());
								accionR = new DecimalFormat("###,###").format(totalR);
								plan.setImagen("verde.png");
							}
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
					plan.setDistribucion_Linea(plan.getDistribucion_Linea().multiply(new BigDecimal("100")));
					//BigDecimal valorD = plan.getDistribucion_Linea().divide(new BigDecimal("1"), );
					plan.setDistribucion_Linea(plan.getDistribucion_Linea().setScale(2, BigDecimal.ROUND_HALF_UP));
				
//					plan.setDistribucion_Linea(plan.getDistribucion_Linea().setScale(2, BigDecimal.ROUND_HALF_UP));
					i += progress1;
					setProgress(i); 
					
				}
				if(progress > 90){
					setProgress(100);
				}
			}
			autenticacion.setFechaBusqueda(fechaBusqueda);
			autenticacion.setFechaBusquedaYear(fechaBusquedaYear);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista del Plan");
		}
	}
	
	
	public void liquidar(ActionEvent evento){

		try{
			ajuste = new Ajuste();
			listaVendedoresAjuste = new ArrayList<>();			
			liquidacion = new Liquidacion();
			vendedor = (ComisionVendedores) evento.getComponent().getAttributes().get("vendedorSelecionado");
			liquidacion.setCodSap(vendedor.getId());
			liquidacion.setNombre(vendedor.getNombre());
			liquidacion.setPeriodo(Integer.parseInt(fechaBusqueda));
			liquidacion.setEjercicio(Integer.parseInt(fechaBusquedaYear));
			liquidacion.setConcepto(vendedor.getConcepto());
			liquidacion.setValorAjuste(0);
			liquidacion.setValorComision(vendedor.getComisionR());
			liquidacion.setValorTotal(vendedor.getComisionR());
			codSap = vendedor.getId();
			nombreRegistro = vendedor.getNombre();
			ajuste.setCodSap(vendedor.getId());
			ajuste.setNombre(vendedor.getNombre());	
			ajuste.setConcepto(vendedor.getConcepto());
			FuncionarioDao daoF = new FuncionarioDao();
			Funcionario funcionario = daoF.buscarPersona(autenticacion.getUsuarioLogin().getPersona().getCedula());
			ajuste.setCodSapUsuario(funcionario.getId_funcionario());
			ajuste.setNombreUsuario(funcionario.getPersona().getNombre());
			listaVendedoresAjuste.add(ajuste);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la liquidacion de Ventas sin LBR");
		}
	}

	public void adicionarAjuste(ActionEvent evento){

		try{
			ajuste= (Ajuste) evento.getComponent().getAttributes().get("vendedorSelecionado2");
			Ajuste ajuste1 = new Ajuste(); 
			ajuste1.setCodSap(ajuste.getCodSap());
			ajuste1.setNombre(ajuste.getNombre());
			ajuste1.setPeriodo(0);
			ajuste1.setEjercicio(0);
			ajuste1.setConcepto(ajuste.getConcepto());
			ajuste1.setFacturapedido(0);
			ajuste1.setNota("");
			ajuste1.setValorajuste(0);
			ajuste1.setCodSapUsuario(ajuste.getCodSapUsuario());
			ajuste1.setNombreUsuario(ajuste.getNombreUsuario());
			listaVendedoresAjuste.add(ajuste1); 
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la vista Ajuste");
		}
	}

	public void guardarAjuste(){

		try{
			Date fecha = new Date();
			totalAjuste = 0;
			habilitar = "true";
            for (Ajuste ajuste : listaVendedoresAjuste) {
				Messages.addGlobalInfo(ajuste.getFechaAjuste()+"te");
				totalAjuste += ajuste.getValorajuste();
				liquidacion.setValorAjuste(totalAjuste);
				ajuste.setFechaAjuste(fecha);
			}
			liquidacion.setValorTotal(liquidacion.getValorComision() + totalAjuste);
			Messages.addGlobalInfo("Se guardaron los Ajustes Correctamente");
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se pudo guardar los Ajustes");
		}
	}

	public void salvarLiquidacion(){

		try{
			Date fecha = new Date();
			LiquidacionDao dao = new LiquidacionDao();
			liquidacion.setFechaLiquidacion(fecha);
			dao.salvar(liquidacion);
			habilitar = "false";

			for( int i = 0 ; i  < ListaComisionVendedores.size(); i++){
				if(ListaComisionVendedores.get(i).getId() ==liquidacion.getCodSap()){
					ListaComisionVendedores.get(i).setLiquidar("true");
				}   
			}

			AjusteDao daoA = new AjusteDao();
			for (Ajuste ajuste : listaVendedoresAjuste) {
				totalAjuste += ajuste.getValorajuste();
				liquidacion.setValorAjuste(totalAjuste);
				ajuste.setFechaAjuste(fecha);
				daoA.salvar(ajuste);
			} 
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se pudo guardar La liquidación");
		}
	}
	
	public void onComplete() {
		Messages.addGlobalError("Proceso Completado");
    }

	public List<ComisionVendedores> getListaComisionVendedores() {
		return ListaComisionVendedores;
	}
	public void setListaComisionVendedores(List<ComisionVendedores> listaComisionVendedores) {
		ListaComisionVendedores = listaComisionVendedores;
	}

	public List<Plan> getListaplanV() {
		return listaplanV;
	}

	public void setListaplanV(List<Plan> listaplanV) {
		this.listaplanV = listaplanV;
	}

	public BigDecimal getUmbral() {
		return umbral;
	}
	public void setUmbral(BigDecimal umbral) {
		this.umbral = umbral;
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
	public AutenticacionBean getAutenticacion() {
		return autenticacion;
	}
	public void setAutenticacion(AutenticacionBean autenticacion) {
		this.autenticacion = autenticacion;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Plan getPlan1() {
		return plan1;
	}

	public void setPlan1(Plan plan1) {
		this.plan1 = plan1;
	}

	public int getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(int idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
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

	public String getBajRot() {
		return bajRot;
	}

	public void setBajRot(String bajRot) {
		this.bajRot = bajRot;
	}

	public int getIdFuncionarioSin() {
		return idFuncionarioSin;
	}

	public void setIdFuncionarioSin(int idFuncionarioSin) {
		this.idFuncionarioSin = idFuncionarioSin;
	}

	public int getBajRoot() {
		return bajRoot;
	}

	public void setBajRoot(int bajRoot) {
		this.bajRoot = bajRoot;
	}

	public String getNombreRegistro() {
		return nombreRegistro;
	}

	public void setNombreRegistro(String nombreRegistro) {
		this.nombreRegistro = nombreRegistro;
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

	public List<Ajuste> getListaVendedoresAjuste() {
		return listaVendedoresAjuste;
	}

	public void setListaVendedoresAjuste(List<Ajuste> listaVendedoresAjuste) {
		this.listaVendedoresAjuste = listaVendedoresAjuste;
	}

	public Ajuste getAjuste() {
		return ajuste;
	}

	public void setAjuste(Ajuste ajuste) {
		this.ajuste = ajuste;
	}

	public Liquidacion getLiquidacion() {
		return liquidacion;
	}

	public void setLiquidacion(Liquidacion liquidacion) {
		this.liquidacion = liquidacion;
	}

	public ComisionVendedores getVendedor() {
		return vendedor;
	}

	public void setVendedor(ComisionVendedores vendedor) {
		this.vendedor = vendedor;
	}

	public int getCodSap() {
		return codSap;
	}

	public void setCodSap(int codSap) {
		this.codSap = codSap;
	}

	public int getTotalAjuste() {
		return totalAjuste;
	}

	public void setTotalAjuste(int totalAjuste) {
		this.totalAjuste = totalAjuste;
	}

	public String getSin() {
		return sin;
	}

	public void setSin(String sin) {
		this.sin = sin;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public List<ComisionVendedores> getListaFiltrados() {
		return listaFiltrados;
	}

	public void setListaFiltrados(List<ComisionVendedores> listaFiltrados) {
		this.listaFiltrados = listaFiltrados;
	}

	

	public Integer getProgress() {
        
		 return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}
	
	public String getHabilitar() {
		return habilitar;
	}

	public void setHabilitar(String habilitar) {
		this.habilitar = habilitar;
	}

	public String getLiquidar() {
		return liquidar;
	}

	public void setLiquidar(String liquidar) {
		this.liquidar = liquidar;
	}

	@Override
	public String toString() {
		return "ComisionBean [ListaComisionVendedores=" + ListaComisionVendedores + "]";
	}
}
