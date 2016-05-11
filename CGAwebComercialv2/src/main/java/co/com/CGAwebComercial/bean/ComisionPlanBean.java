package co.com.CGAwebComercial.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import co.com.CGAwebComercial.dao.DetalleDao;
import co.com.CGAwebComercial.dao.FuncionarioDao;
import co.com.CGAwebComercial.entyties.Funcionario;
import co.com.CGAwebComercial.entyties.Plan;
import co.com.CGAwebComercial.resource.Recursos;
import co.com.CGAwebComercial.util.ComisionVendedores;
import co.com.CGAwebComercial.util.Fechas;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ComisionPlanBean implements Serializable{

	@ManagedProperty("#{autenticacionBean}")
	private AutenticacionBean autenticacion;
	
	private Recursos recurso;

	private List<ComisionVendedores> ListaComisionVendedores;
	private List<Plan> listaplanV;	
	private List<Fechas> listaFechas;
	private Plan plan1;

	private BigDecimal total;
	private BigDecimal umbral;
	private String fechaBusqueda;
	private String fechaBusquedaYear;
	private int idFuncionario;
	private int idFuncionarioSin=0;
	private int bajRoot =0;
	private String accion;
	private String totalPreIng;
	private String totalPreUti;
	private String totalRealIng;
	private String totalRealUti;
	private String bajRot;
	private String tipo;
	
	public void listarPlan(){

		try{
			
			if(autenticacion.getFechaBusquedaYear().equals(null) ){
				total =  new BigDecimal("0.00");
				BigDecimal totalPresupuesto = new BigDecimal("0.00");
				BigDecimal totalPresupuestoUtilidad = new BigDecimal("0.00");
				BigDecimal totalValorReal = new BigDecimal("0.00");
				BigDecimal valorReal = new BigDecimal("0.00");				
				BigDecimal totalValorUtilidad = new BigDecimal("0.00");
				BigDecimal valorUtilidad = new BigDecimal("0.00");
				int numero = 0;
				FuncionarioDao daoF = new FuncionarioDao();
				Funcionario funcionario;
				DetalleDao daoD = new DetalleDao();	
				if(idFuncionarioSin != 0){
					funcionario = daoF.buscarPersona(idFuncionarioSin);
					listaplanV = daoD.listarPlanSin(funcionario.getId_funcionario());
				}
				else{
					funcionario = daoF.buscarPersona(idFuncionario);
					listaplanV = daoD.listarPlan(funcionario.getId_funcionario());
				}
					
				for (Plan planL : listaplanV) {
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
			BigDecimal totalPresupuesto = new BigDecimal("0.00");
			BigDecimal totalPresupuestoUtilidad = new BigDecimal("0.00");
			BigDecimal totalValorReal = new BigDecimal("0.00");
			BigDecimal totalValorUtilidad = new BigDecimal("0.00");
			BigDecimal valorReal = new BigDecimal("0.00");
			BigDecimal valorUtilidad = new BigDecimal("0.00");
			int numero = 0;
			
			FuncionarioDao daoF = new FuncionarioDao();
			//Funcionario funcionario = daoF.buscarPersona(autenticacion.getUsuarioLogin().getPersona().getCedula());
			Funcionario funcionario;
			
			DetalleDao daoD = new DetalleDao();	
			if(fechaBusqueda != null && fechaBusquedaYear != null){
				
				if(idFuncionarioSin != 0){
					funcionario = daoF.buscarPersona(idFuncionarioSin);
					listaplanV = daoD.listarPlanPorFechasSin(funcionario.getId_funcionario(), fechaBusqueda, fechaBusquedaYear);
				}
				else{
					if(autenticacion.getTipoVendedor().equals("I") ){
					   tipo= "funcionarioI";
					}
					else{
						tipo= "funcionario";
					}
					funcionario = daoF.buscarPersona(idFuncionario);
					listaplanV = daoD.listarPlanPorFechas(funcionario.getId_funcionario(), fechaBusqueda, fechaBusquedaYear);
				}
					
				
				
				for (Plan plan : listaplanV) {
					totalPresupuesto = totalPresupuesto.add(plan.getIngreso());
					totalPresupuestoUtilidad = totalPresupuestoUtilidad.add(plan.getUtilidad()); 
					totalValorReal = totalValorReal.add(plan.getIngreso_Real());
					totalValorUtilidad = totalValorUtilidad.add(plan.getUtilidad_Real());
					totalPreIng = new DecimalFormat("###,###").format(totalPresupuesto);
					totalPreUti = new DecimalFormat("###,###").format(totalPresupuestoUtilidad);
					totalRealIng = new DecimalFormat("###,###").format(totalValorReal);
					totalRealUti = new DecimalFormat("###,###").format(totalValorUtilidad);
					
					if (plan.getIngreso() == null || plan.getIngreso().compareTo(BigDecimal.ZERO) == 0  ){
						plan.setIngreso_Real(new BigDecimal("0"));
						valorReal = plan.getIngreso_Real();
					}
					else{
						valorReal = plan.getIngreso_Real().divide(plan.getIngreso(), 3, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
						plan.setIngreso_Cumplimiento(valorReal);
					}
					valorReal = plan.getUtilidad_Real().divide(plan.getUtilidad(), 3, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
					plan.setUtilidad_Cumplimiento(valorReal);
					
					if(umbral != null){
						plan.setUmbral(umbral);
						numero = umbral.compareTo(plan.getUtilidad_Cumplimiento().divide(new BigDecimal("100.00")));
					}
					else{
					    plan.setUmbral(plan.getFuncionario().getComision().getUmbralVenta());
					    numero = plan.getFuncionario().getComision().getUmbralVenta().compareTo(plan.getUtilidad_Cumplimiento().divide(new BigDecimal("100.00")));
					}
					
					if(numero == 1 ){
						plan.setValor_Comision_Pagar(new BigDecimal("0.00"));
						total = total.add(plan.getValor_Comision_Pagar());
						accion= new DecimalFormat("###,###").format(total);
						plan.setImagen("rojo.png");
					}
					else{
						valorUtilidad= plan.getDistribucion_Linea().multiply(plan.getFuncionario().getComision().getValorBaseVenta().divide(new BigDecimal("100.00")));
						//valorUtilidad= valorUtilidad.multiply(plan.getUtilidad_Cumplimiento()).divide(new BigDecimal("100.00"));
						valorUtilidad= valorUtilidad.multiply(plan.getUtilidad_Cumplimiento());
						plan.setValor_Comision_Pagar(valorUtilidad);
						total = total.add(plan.getValor_Comision_Pagar());
						accion= new DecimalFormat("###,###").format(total);
						plan.setImagen("verde.png");
					}
				}
			}
			autenticacion.setFechaBusqueda(fechaBusqueda);
			autenticacion.setFechaBusquedaYear(fechaBusquedaYear);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista del Plan");
		}
	}

	public AutenticacionBean getAutenticacion() {
		return autenticacion;
	}
	public void setAutenticacion(AutenticacionBean autenticacion) {
		this.autenticacion = autenticacion;
	}
	public Recursos getRecurso() {
		return recurso;
	}
	public void setRecurso(Recursos recurso) {
		this.recurso = recurso;
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
	public List<Fechas> getListaFechas() {
		return listaFechas;
	}
	public void setListaFechas(List<Fechas> listaFechas) {
		this.listaFechas = listaFechas;
	}
	public Plan getPlan1() {
		return plan1;
	}
	public void setPlan1(Plan plan1) {
		this.plan1 = plan1;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
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
	public int getIdFuncionario() {
		return idFuncionario;
	}
	public void setIdFuncionario(int idFuncionario) {
		this.idFuncionario = idFuncionario;
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
	public String getBajRot() {
		return bajRot;
	}
	public void setBajRot(String bajRot) {
		this.bajRot = bajRot;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
}
