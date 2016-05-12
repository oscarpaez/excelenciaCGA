package co.com.CGAwebComercial.bean;

import java.io.Serializable;
import java.math.BigDecimal;
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
import co.com.CGAwebComercial.dao.ContadoAnticipoDao;
import co.com.CGAwebComercial.dao.FuncionarioDao;
import co.com.CGAwebComercial.dao.LiquidacionDao;
import co.com.CGAwebComercial.entyties.Ajuste;
import co.com.CGAwebComercial.entyties.ContadoAnticipo;
import co.com.CGAwebComercial.entyties.Funcionario;
import co.com.CGAwebComercial.entyties.Liquidacion;
import co.com.CGAwebComercial.resource.Recursos;
import co.com.CGAwebComercial.util.ComisionVendedores;
import co.com.CGAwebComercial.util.Fechas;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ContadoAnticipoBean implements Serializable{
	
	@ManagedProperty("#{autenticacionBean}")
	private AutenticacionBean autenticacion;
	
	private Recursos recurso;
	
	private List<ContadoAnticipo> listaContado;
	private List<Ajuste> listaVendedoresAjuste;
	private List<Fechas> listaFechas;
	private Ajuste ajuste;
	private Liquidacion liquidacion;
	private ComisionVendedores vendedor;
	private ContadoAnticipo contado;
	
	private int codSap= 0;
	private int totalAjuste=0;
	private String nombreRegistro="";	
	private String fechaBusqueda;
	private String fechaBusquedaYear;
	private int index = 0;
	private String habilitar;
	private String liquidar;
	
	
	public ContadoAnticipoBean() {
		recurso = new Recursos();
		listaFechas = recurso.cargarFechas();
		listarContadoAnticipo();
		//listarContadoAnticipoN();
	}
	
	@PostConstruct
	public void listarContadoAnticipo(){
		
		try{
			if(autenticacion.getFechaBusqueda() != null && autenticacion.getFechaBusquedaYear() != null){
				fechaBusqueda = autenticacion.getFechaBusqueda();
				fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
				listarContadoAnticipoN();
			}
			else{
				listarContadoAnticipoN();
				
			}	
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Contado");
		}	
		
	}
	
	public void listarContadoAnticipoFechas(){
		
		try{
			listaContado = new ArrayList<>(); 
			List<Funcionario> listaVendedor = null;
			FuncionarioDao daoF = new FuncionarioDao();
			if(autenticacion.getTipoVendedor().equals("I") ){
				listaVendedor = daoF.listarVendedoresInternos();
			}
			else{
				listaVendedor = daoF.listarVendedores();
			}
			int i =0;
			for (Funcionario funcionario : listaVendedor) {
				ContadoAnticipoDao daoC = new ContadoAnticipoDao();
				Long total = daoC.sumaTotalFechas(funcionario.getId_funcionario(), fechaBusqueda, fechaBusquedaYear);
				
				if(total != null){
					ContadoAnticipo contado = new ContadoAnticipo();
					contado.setCedula(funcionario.getPersona().getCedula());
					contado.setNoPersonal(funcionario.getId_funcionario());
					contado.setVendedor(funcionario.getPersona().getNombre());
					contado.setTotalRecaudo(total.intValue());
					contado.setPorcentaje(new BigDecimal("0.25"));
					contado.setComision(contado.getPorcentaje().multiply(new BigDecimal(total.toString()).divide(new BigDecimal("100.00"))));
					listaContado.add(i,contado);
				}
				else{
					ContadoAnticipo contado = new ContadoAnticipo();
					contado.setCedula(funcionario.getPersona().getCedula());
					contado.setNoPersonal(funcionario.getId_funcionario());
					contado.setVendedor(funcionario.getPersona().getNombre());
					contado.setTotalRecaudo(0);
					contado.setPorcentaje(new BigDecimal("0.25"));
					contado.setComision(new BigDecimal("0.00"));
					listaContado.add(i,contado);
				}
				autenticacion.setFechaBusqueda(fechaBusqueda);
				autenticacion.setFechaBusquedaYear(fechaBusquedaYear);
			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Contado Fechas");
		}
	}
	
	//*Contado y anticipo verificando  el estado de los vendedores *//
	public void listarContadoAnticipoN(){
		
		try {
			List<Funcionario> listaVendedor = null;
			List<ContadoAnticipo> listaRegistros;
			listaContado = new ArrayList<>();
			String tipo;
			FuncionarioDao daoF = new FuncionarioDao();
			ContadoAnticipoDao daoCA = new ContadoAnticipoDao();
			if(autenticacion.getTipoVendedor().equals("I") ){
				listaVendedor = daoF.listarVendedoresInternos();
				tipo = "codVendedorInt";
			}
			else{
				listaVendedor = daoF.listarVendedores();
				tipo = "codEspecialista";
			}
			
			for (Funcionario funcionario : listaVendedor) {
				
				if(funcionario.getEstado() == 1){
					LiquidacionDao daoL = new LiquidacionDao();
					List<Liquidacion> liquidacion = daoL.buscarLiquidacion("Contado y Anticipo", funcionario.getId_funcionario());
					liquidar = (liquidacion.size() <= 0) ? "false" : "true";
					ContadoAnticipo contado = new ContadoAnticipo();
					contado.setCedula(funcionario.getPersona().getCedula());
					contado.setNoPersonal(funcionario.getId_funcionario());
					contado.setVendedor(funcionario.getPersona().getNombre());
					contado.setComision(new BigDecimal("0.00"));
					contado.setLiquidar(liquidar);
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
					}
					listaContado.add(contado);
				}
				autenticacion.setFechaBusqueda(fechaBusqueda);
				autenticacion.setFechaBusquedaYear(fechaBusquedaYear);
			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Contado ");
		}
	}
	
	public void liquidar(ActionEvent evento){

		try{
			ajuste = new Ajuste();
			listaVendedoresAjuste = new ArrayList<>();			
			liquidacion = new Liquidacion();
			contado = (ContadoAnticipo) evento.getComponent().getAttributes().get("vendedorSelecionado");
			liquidacion.setCodSap(contado.getNoPersonal());
			liquidacion.setNombre(contado.getVendedor());
			liquidacion.setPeriodo(Integer.parseInt(fechaBusqueda));
			liquidacion.setEjercicio(Integer.parseInt(fechaBusquedaYear));
			liquidacion.setConcepto("Contado y Anticipo");
			liquidacion.setValorAjuste(0);
			liquidacion.setValorComision(contado.getComision().intValue());
			liquidacion.setValorTotal(contado.getComision().intValue());
			codSap = contado.getNoPersonal();
			nombreRegistro = contado.getVendedor();
			ajuste.setCodSap(contado.getNoPersonal());
			ajuste.setNombre(contado.getVendedor());	
			ajuste.setConcepto("Contado y Anticipo");
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
			setHabilitar("true");
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
			habilitar ="false";
			
			for( int i = 0 ; i  < listaContado.size(); i++){
				if(listaContado.get(i).getNoPersonal() == liquidacion.getCodSap()){
					listaContado.get(i).setLiquidar("true");
				}   
			}

			AjusteDao daoA = new AjusteDao();
			for (Ajuste ajuste : listaVendedoresAjuste) {
				Messages.addGlobalInfo(ajuste.getFechaAjuste()+"te");
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


	public AutenticacionBean getAutenticacion() {
		return autenticacion;
	}

	public void setAutenticacion(AutenticacionBean autenticacion) {
		this.autenticacion = autenticacion;
	}

	public List<ContadoAnticipo> getListaContado() {
		return listaContado;
	}

	public void setListaContado(List<ContadoAnticipo> listaContado) {
		this.listaContado = listaContado;
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

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
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

	public ContadoAnticipo getContado() {
		return contado;
	}

	public void setContado(ContadoAnticipo contado) {
		this.contado = contado;
	}

	public String getHabilitar() {
		return habilitar;
	}

	public void setHabilitar(String habilitar) {
		this.habilitar = habilitar;
	}
}
