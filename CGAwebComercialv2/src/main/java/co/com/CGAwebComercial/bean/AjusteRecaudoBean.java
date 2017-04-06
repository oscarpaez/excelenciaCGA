package co.com.CGAwebComercial.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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
import co.com.CGAwebComercial.dao.ComisionDao;
import co.com.CGAwebComercial.dao.LiquidacionDao;
import co.com.CGAwebComercial.dao.RecaudoDao;
import co.com.CGAwebComercial.dao.SucursalDao;
import co.com.CGAwebComercial.entyties.Ajuste;
import co.com.CGAwebComercial.entyties.Comision;
import co.com.CGAwebComercial.entyties.Liquidacion;
import co.com.CGAwebComercial.entyties.OficinaVendedorInterno;
import co.com.CGAwebComercial.entyties.Recaudo;
import co.com.CGAwebComercial.entyties.Sucursal;
import co.com.CGAwebComercial.resource.Recursos;
import co.com.CGAwebComercial.util.ComisionVendedores;
import co.com.CGAwebComercial.util.Fechas;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class AjusteRecaudoBean implements Serializable{
	
	@ManagedProperty("#{autenticacionBean}")
	private AutenticacionBean autenticacion;
	
	private Recursos recurso;

	private List<Recaudo> listaRecaudo;
	List<ComisionVendedores> listaCarteraInternos;
	private List<Ajuste> listaVendedoresAjuste;
	private List<Fechas> listaFechas;
	private Recaudo recaudo;
	private ComisionVendedores vendedoresInt;
	private Ajuste ajuste;
	private Liquidacion liquidacion;
	private ComisionVendedores vendedor;

	private int codigo;
	private int idPersona;
	private int codSap= 0;
	private int totalAjuste=0;
	private String nombreRegistro="";
	private String fechaBusqueda ="";
	private String fechaBusquedaYear= "";
	private int idCiudad;
	private int presupuesto;
	private int real;
	private BigDecimal presupuestoB;
	private BigDecimal realB;
	private BigDecimal cumplimiento;
	
	public AjusteRecaudoBean() {
		
	}
	
	@PostConstruct
	public void listarComisionInternos(){
		try{
			if(autenticacion != null){
				autenticacion.registroIngreso(autenticacion.getUsuarioLogin());
			}
			listaCarteraInternos = new ArrayList<>();
			SucursalDao daoC = new SucursalDao();
			int numero = 0;
			if(idCiudad == 7){
				idCiudad = 1;
			}
			Sucursal ciudad = daoC.buscarSucursal(idCiudad);
			RecaudoDao daoR = new RecaudoDao();
			List<OficinaVendedorInterno> listaVenInt = daoR. listaVendedorInterno(ciudad.getId().intValue());
			for (OficinaVendedorInterno Interno : listaVenInt) {
				ComisionDao dao = new ComisionDao();
				Comision comision =dao.buscar(Interno.getTip());
				vendedoresInt = new ComisionVendedores();

				vendedoresInt.setId(Interno.getCodigosap());
				vendedoresInt.setNombre(Interno.getAsesor());
				vendedoresInt.setPresupuestoB(presupuestoB);
				vendedoresInt.setIngresoRealB(realB);
				vendedoresInt.setCumplimiento(cumplimiento);
				numero = cumplimiento.compareTo(new BigDecimal("95.00"));
				
				if(numero == 1){
					vendedoresInt.setComisionS(new DecimalFormat("###.##").format(cumplimiento));
					vendedoresInt.setComision(cumplimiento.multiply(comision.getValorBaseRecaudo()).divide(new BigDecimal("100")).intValue());
				}
				else{
					vendedoresInt.setComisionS(new DecimalFormat("###.##").format(cumplimiento));
					vendedoresInt.setComision(0);	
				}
				listaCarteraInternos.add(vendedoresInt);
			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no seEEEEE Cargo la lista de cartera vendedores Internos");
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
			listaVendedoresAjuste.add(ajuste1); 
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la vista Ajuste");
		}
	}

	public void guardarAjuste(){

		try{
			AjusteDao dao = new AjusteDao();
			Date fecha = new Date();
			for (Ajuste ajuste : listaVendedoresAjuste) {
				totalAjuste += ajuste.getValorajuste();
				liquidacion.setValorAjuste(totalAjuste);
				ajuste.setFechaAjuste(fecha);
				dao.salvar(ajuste);
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
			liquidacion.setFechaLiquidacion(fecha);
			dao.salvar(liquidacion);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se pudo guardar La liquidación");
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
			liquidacion.setConcepto("Cartera");
			liquidacion.setValorAjuste(0);
			liquidacion.setValorComision(vendedor.getComision());
			liquidacion.setValorTotal(vendedor.getComision());
			codSap = vendedor.getId();
			nombreRegistro = vendedor.getNombre();
			ajuste.setCodSap(vendedor.getId());
			ajuste.setNombre(vendedor.getNombre());	
			ajuste.setConcepto("Cartera");
			listaVendedoresAjuste.add(ajuste);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la liquidacion de Ventas sin LBR");
		}
	}
	
	public AutenticacionBean getAutenticacion() {
		return autenticacion;
	}

	public void setAutenticacion(AutenticacionBean autenticacion) {
		this.autenticacion = autenticacion;
	}

	public List<ComisionVendedores> getListaCarteraInternos() {
		return listaCarteraInternos;
	}

	public void setListaCarteraInternos(List<ComisionVendedores> listaCarteraInternos) {
		this.listaCarteraInternos = listaCarteraInternos;
	}

	public List<Ajuste> getListaVendedoresAjuste() {
		return listaVendedoresAjuste;
	}

	public void setListaVendedoresAjuste(List<Ajuste> listaVendedoresAjuste) {
		this.listaVendedoresAjuste = listaVendedoresAjuste;
	}

	public ComisionVendedores getVendedoresInt() {
		return vendedoresInt;
	}

	public void setVendedoresInt(ComisionVendedores vendedoresInt) {
		this.vendedoresInt = vendedoresInt;
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

	public String getNombreRegistro() {
		return nombreRegistro;
	}

	public void setNombreRegistro(String nombreRegistro) {
		this.nombreRegistro = nombreRegistro;
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

	public int getIdCiudad() {
		return idCiudad;
	}

	public void setIdCiudad(int idCiudad) {
		this.idCiudad = idCiudad;
	}

	public int getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(int presupuesto) {
		this.presupuesto = presupuesto;
	}

	public int getReal() {
		return real;
	}

	public void setReal(int real) {
		this.real = real;
	}

	public BigDecimal getPresupuestoB() {
		return presupuestoB;
	}

	public void setPresupuestoB(BigDecimal presupuestoB) {
		this.presupuestoB = presupuestoB;
	}

	public BigDecimal getRealB() {
		return realB;
	}

	public void setRealB(BigDecimal realB) {
		this.realB = realB;
	}

	public BigDecimal getCumplimiento() {
		return cumplimiento;
	}

	public void setCumplimiento(BigDecimal cumplimiento) {
		this.cumplimiento = cumplimiento;
	}

	public Recursos getRecurso() {
		return recurso;
	}

	public void setRecurso(Recursos recurso) {
		this.recurso = recurso;
	}

	public List<Recaudo> getListaRecaudo() {
		return listaRecaudo;
	}

	public void setListaRecaudo(List<Recaudo> listaRecaudo) {
		this.listaRecaudo = listaRecaudo;
	}

	public List<Fechas> getListaFechas() {
		return listaFechas;
	}

	public void setListaFechas(List<Fechas> listaFechas) {
		this.listaFechas = listaFechas;
	}

	public Recaudo getRecaudo() {
		return recaudo;
	}

	public void setRecaudo(Recaudo recaudo) {
		this.recaudo = recaudo;
	}
}
