package co.com.CGAwebComercial.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import co.com.CGAwebComercial.dao.AjusteDao;
import co.com.CGAwebComercial.dao.FuncionarioDao;
import co.com.CGAwebComercial.dao.LiquidacionDao;
import co.com.CGAwebComercial.entyties.Ajuste;
import co.com.CGAwebComercial.entyties.Funcionario;
import co.com.CGAwebComercial.entyties.Liquidacion;
import co.com.CGAwebComercial.resource.LiquidarAjustes;
import co.com.CGAwebComercial.resource.Recursos;
import co.com.CGAwebComercial.util.Fechas;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class AjusteBean implements Serializable{
	
	@ManagedProperty("#{autenticacionBean}")
	private AutenticacionBean autenticacion;	
	
	private Recursos recurso;
	private List<Liquidacion> listaLiquidacion;
	private List<Ajuste> listaVendedoresAjuste;
	private Liquidacion liquidacion;
	private List<Ajuste> listaAjuste;
	private List<Fechas> listaFechas;
	private List<Funcionario> listaVendedor;
	private Ajuste ajuste;
	private Liquidacion vendedor;
	
	private String nombreRegistro;
	private String tipo;
	private String habilitar;
	private int cod;
	private int codSap= 0;
	private int totalAjuste=0;
	
	public AjusteBean(){
		recurso = new Recursos();	
		listaFechas = recurso.cargarFechas();
	}
	
	@PostConstruct
	public void listarEmpleados(){
		
		try{
			listaLiquidacion = new ArrayList<>();
			LiquidacionDao daoL = new LiquidacionDao();
			List<Integer> listCod = daoL.listaCod(autenticacion.getFechaBusqueda(), autenticacion.getFechaBusquedaYear());
			
			for (Integer cod : listCod) {
				liquidacion = daoL.listaLiquidacionSum(cod,autenticacion.getFechaBusqueda(), autenticacion.getFechaBusquedaYear());
				listaLiquidacion.add(liquidacion);
			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Liquidacion");
		}
	}
	
	
	public void listarLiquidacion(){
		
		try{
			recurso = new Recursos();	
			listaFechas = recurso.cargarFechas();
			if(autenticacion.getFechaBusqueda().equals(null) || autenticacion.getFechaBusqueda().equals("")
					|| autenticacion.getFechaBusquedaYear().equals(null) || autenticacion.getFechaBusquedaYear().equals("")){
				LiquidacionDao daoL = new LiquidacionDao();
				listaLiquidacion = daoL.listaLiquidacion(cod, autenticacion.getFechaBusqueda(), autenticacion.getFechaBusquedaYear());
			}
			else{
				LiquidacionDao daoL = new LiquidacionDao();
				listaLiquidacion = daoL.listaLiquidacion(cod, autenticacion.getFechaBusqueda(), autenticacion.getFechaBusquedaYear());
			}
			
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Liquidacion");
		}
	}
	
	public void listarAjuste(){

		try{
			if(autenticacion.getFechaBusqueda().equals(null) && autenticacion.getFechaBusquedaYear().equals(null)){
				AjusteDao daoA = new AjusteDao();
				listaAjuste = daoA.listaAjuste(cod,autenticacion.getFechaBusqueda(), autenticacion.getFechaBusquedaYear());
			}
			else{
				AjusteDao daoA = new AjusteDao();
				listaAjuste = daoA.listaAjuste(cod, autenticacion.getFechaBusqueda(), autenticacion.getFechaBusquedaYear());
			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Liquidacion");
		}
	}
	
	public void liquidar(ActionEvent evento){

		try{
			LiquidarAjustes liquidar = new LiquidarAjustes(); 
			ajuste = new Ajuste();
			listaVendedoresAjuste = new ArrayList<>();			
			liquidacion = new Liquidacion();
			String concepto = "Concepto";
			vendedor = (Liquidacion) evento.getComponent().getAttributes().get("vendedorSelecionado");
			liquidacion = liquidar.liquidar(vendedor, autenticacion.getFechaBusqueda(), autenticacion.getFechaBusquedaYear(), concepto);
			codSap = vendedor.getCodSap();
			nombreRegistro = vendedor.getNombre();
			ajuste.setCodSap(vendedor.getCodSap());
			ajuste.setNombre(vendedor.getNombre());	
			ajuste.setConcepto(concepto);
			FuncionarioDao daoF = new FuncionarioDao();
			Funcionario funcionario = daoF.buscarPersona(autenticacion.getUsuarioLogin().getPersona().getCedula());
			ajuste.setCodSapUsuario(funcionario.getId_funcionario());
			ajuste.setNombreUsuario(funcionario.getPersona().getNombre());
			listaVendedoresAjuste.add(ajuste);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la liquidacion");
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
				ajuste1.setConcepto("Concepto");
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
			for (Ajuste ajuste : listaVendedoresAjuste) {
				totalAjuste += ajuste.getValorajuste();
				liquidacion.setValorAjuste(totalAjuste);
				ajuste.setFechaAjuste(fecha);
			}
			liquidacion.setValorTotal(liquidacion.getValorComision() + totalAjuste);
			
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se pudo guardar los Ajustes");
		}
	}
	
	public void salvarLiquidacion(){
		
		try{
			Date fecha = new Date();
			LiquidacionDao dao = new LiquidacionDao();
			liquidacion.setValorComision(0);
			liquidacion.setValorTotal(liquidacion.getValorAjuste());
			liquidacion.setFechaLiquidacion(fecha);
			dao.salvar(liquidacion);
			
			AjusteDao daoA = new AjusteDao();			
			totalAjuste = 0;
			for (Ajuste ajuste : listaVendedoresAjuste) {
				Messages.addGlobalInfo(ajuste.getFechaAjuste()+"te");
				totalAjuste += ajuste.getValorajuste();
				liquidacion.setValorAjuste(totalAjuste);
				ajuste.setFechaAjuste(fecha);
				daoA.salvar(ajuste);
			}
			listarEmpleados();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se pudo guardar La liquidación");
		}
	}


	
	public List<Liquidacion> getListaLiquidacion() {
		return listaLiquidacion;
	}
	public void setListaLiquidacion(List<Liquidacion> listaLiquidacion) {
		this.listaLiquidacion = listaLiquidacion;
	}
	public List<Ajuste> getListaAjuste() {
		return listaAjuste;
	}
	public void setListaAjuste(List<Ajuste> listaAjuste) {
		this.listaAjuste = listaAjuste;
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
	public List<Fechas> getListaFechas() {
		return listaFechas;
	}
	public void setListaFechas(List<Fechas> listaFechas) {
		this.listaFechas = listaFechas;
	}
	public List<Funcionario> getListaVendedor() {
		return listaVendedor;
	}
	public void setListaVendedor(List<Funcionario> listaVendedor) {
		this.listaVendedor = listaVendedor;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Liquidacion getLiquidacion() {
		return liquidacion;
	}
	public void setLiquidacion(Liquidacion liquidacion) {
		this.liquidacion = liquidacion;
	}
	public int getCod() {
		return cod;
	}
	public void setCod(int cod) {
		this.cod = cod;
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
	public Liquidacion getVendedor() {
		return vendedor;
	}
	public void setVendedor(Liquidacion vendedor) {
		this.vendedor = vendedor;
	}
	public String getNombreRegistro() {
		return nombreRegistro;
	}
	public void setNombreRegistro(String nombreRegistro) {
		this.nombreRegistro = nombreRegistro;
	}
	public int getCodSap() {
		return codSap;
	}
	public void setCodSap(int codSap) {
		this.codSap = codSap;
	}

	public String getHabilitar() {
		return habilitar;
	}

	public void setHabilitar(String habilitar) {
		this.habilitar = habilitar;
	}

	public int getTotalAjuste() {
		return totalAjuste;
	}

	public void setTotalAjuste(int totalAjuste) {
		this.totalAjuste = totalAjuste;
	}
}
