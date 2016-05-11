package co.com.CGAwebComercial.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import co.com.CGAwebComercial.dao.AjusteDao;
import co.com.CGAwebComercial.dao.LiquidacionDao;
import co.com.CGAwebComercial.entyties.Ajuste;
import co.com.CGAwebComercial.entyties.Funcionario;
import co.com.CGAwebComercial.entyties.Liquidacion;
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
	private Liquidacion liquidacion;
	private List<Ajuste> listaAjuste;
	private List<Fechas> listaFechas;
	private List<Funcionario> listaVendedor;
	
	private String tipo;
	private int cod;
	
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
}
