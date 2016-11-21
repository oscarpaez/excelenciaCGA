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
import co.com.CGAwebComercial.dao.FuncionarioDao;
import co.com.CGAwebComercial.dao.LiquidacionDao;
import co.com.CGAwebComercial.dao.RecaudoDao;
import co.com.CGAwebComercial.dao.SucursalDao;
import co.com.CGAwebComercial.entyties.Ajuste;
import co.com.CGAwebComercial.entyties.Comision;
import co.com.CGAwebComercial.entyties.Funcionario;
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
public class RecaudoBean implements Serializable{

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
	private String cumplimientoS;
	private String habilitar;
	private String liquidar;
	private int idCiudad=0;
	private int presupuesto=0;
	private int real=0;
	private BigDecimal presupuestoB;
	private BigDecimal realB;
	private BigDecimal cumplimiento;

	public void listarRecaudo(){

		try{
			Recursos recurso = new Recursos();
			listaFechas = recurso.cargarFechasTotal();
			RecaudoDao dao = new RecaudoDao();
			listaRecaudo = dao.listarPresupuesto(idPersona );
			
			for (int i = 0; i<listaRecaudo.size(); i++) {
				listaRecaudo.get(i).setMes(listaFechas.get(i).getMes());
			}
			
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Recaudo");
		}
	}

	@PostConstruct
	public void carteraInternos(){

		try{
			recurso = new Recursos();			
			RecaudoDao dao = new RecaudoDao();
			if(autenticacion.getFechaBusqueda().equals("") || autenticacion.getFechaBusqueda().equals(null) ){			
				listaFechas = recurso.cargarFechas();
				listaCarteraInternos = dao.carteraInternos(fechaBusqueda, fechaBusquedaYear);
				autenticacion.setFechaBusqueda(fechaBusqueda);
				autenticacion.setFechaBusquedaYear(fechaBusquedaYear);
			}
			else{
				listaFechas = recurso.cargarFechas();
				fechaBusqueda = autenticacion.getFechaBusqueda();
				fechaBusquedaYear = autenticacion.getFechaBusquedaYear();	
				listaCarteraInternos = dao.carteraInternos(fechaBusqueda, fechaBusquedaYear);
			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la Recaudo Internos");
		}

	}

	public void listarComisionInternos(){
		    
		try{
			listaCarteraInternos = new ArrayList<>();
			SucursalDao daoC = new SucursalDao();
			int numero = 0;
			if(idCiudad == 7){
				idCiudad = 1;
			}
			Sucursal ciudad = daoC.buscarSucursal(idCiudad);
			RecaudoDao daoR = new RecaudoDao(); 
			List<OficinaVendedorInterno> listaVenInt = daoR. listaVendedorInterno(ciudad.getId());
			for (OficinaVendedorInterno Interno : listaVenInt) {
				LiquidacionDao daoL = new LiquidacionDao();
				List<Liquidacion> liquidacion = daoL.buscarLiquidacion("Cartera", Interno.getCodigosap());
				liquidar = (liquidacion.size() <= 0) ? "false" : "true";
				ComisionDao dao = new ComisionDao();
				Comision comision =dao.buscar(Interno.getTip());
				vendedoresInt = new ComisionVendedores();
				vendedoresInt.setCedula(Interno.getCedula());
				vendedoresInt.setId(Interno.getCodigosap());
				vendedoresInt.setNombre(Interno.getAsesor());
				vendedoresInt.setPresupuestoB(presupuestoB);
				vendedoresInt.setIngresoRealB(realB);
				vendedoresInt.setCumplimiento(cumplimiento);
				vendedoresInt.setLiquidar(liquidar);
				numero = cumplimiento.compareTo(new BigDecimal("85.00"));
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
			Messages.addGlobalError("Error no se Cargo la comision de los vendedores Internos");
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
			liquidacion.setPeriodo(Integer.parseInt(autenticacion.getFechaBusqueda()));
			liquidacion.setEjercicio(Integer.parseInt(autenticacion.getFechaBusquedaYear()));
			liquidacion.setConcepto("Cartera");
			liquidacion.setValorAjuste(0);
			liquidacion.setValorComision(vendedor.getComision());
			liquidacion.setValorTotal(vendedor.getComision());
			codSap = vendedor.getId();
			nombreRegistro = vendedor.getNombre();
			ajuste.setCodSap(vendedor.getId());
			ajuste.setNombre(vendedor.getNombre());	
			ajuste.setConcepto("Cartera");
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
			ajuste1.setConcepto("Cartera");
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
			habilitar ="false";
			
			for( int i = 0 ; i  < listaCarteraInternos.size(); i++){
				if(listaCarteraInternos.get(i).getId() == liquidacion.getCodSap()){
					listaCarteraInternos.get(i).setLiquidar("true");
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
	
	public List<Recaudo> getListaRecaudo() {
		return listaRecaudo;
	}

	public void setListaRecaudo(List<Recaudo> listaRecaudo) {
		this.listaRecaudo = listaRecaudo;
	}

	public Recaudo getRecaudo() {
		return recaudo;
	}

	public void setRecaudo(Recaudo recaudo) {
		this.recaudo = recaudo;
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

	public List<ComisionVendedores> getListaCarteraInternos() {
		return listaCarteraInternos;
	}

	public void setListaCarteraInternos(List<ComisionVendedores> listaCarteraInternos) {
		this.listaCarteraInternos = listaCarteraInternos;
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

	public BigDecimal getCumplimiento() {
		return cumplimiento;
	}

	public void setCumplimiento(BigDecimal cumplimiento) {
		this.cumplimiento = cumplimiento;
	}

	public ComisionVendedores getVendedoresInt() {
		return vendedoresInt;
	}

	public void setVendedoresInt(ComisionVendedores vendedoresInt) {
		this.vendedoresInt = vendedoresInt;
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

	public String getNombreRegistro() {
		return nombreRegistro;
	}

	public void setNombreRegistro(String nombreRegistro) {
		this.nombreRegistro = nombreRegistro;
	}

	public String getCumplimientoS() {
		return cumplimientoS;
	}

	public void setCumplimientoS(String cumplimientoS) {
		this.cumplimientoS = cumplimientoS;
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
	
	
}
