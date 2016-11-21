package co.com.CGAwebComercial.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import co.com.CGAwebComercial.dao.Registro_IngresosDao;
import co.com.CGAwebComercial.entyties.Registro_Ingresos;
import co.com.CGAwebComercial.resource.Recursos;
import co.com.CGAwebComercial.util.Fechas;


@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class Registro_IngresosBean implements Serializable {

	@ManagedProperty("#{autenticacionBean}")
	private AutenticacionBean autenticacion;
	
	private List<Registro_Ingresos> listaRegistros;	
	private List<Fechas> listaFechas;
	
	private Recursos recurso;
	
	private String fechaConsulta;
	private String fechaBusqueda;
	private String fechaBusquedaYear;
	private int idFun;

	public Registro_IngresosBean() {
		recurso = new Recursos();
		listaFechas = recurso.cargarFechas();
	}
	
	public void inicioVistaRegistro(){

		try{
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			if(fechaConsulta == null){
				Calendar fechas = Calendar.getInstance();
				int month = fechas.get(Calendar.MONTH)+1;
				for (Fechas fecha: listaFechas) {
					fechaConsulta  = (fecha.getValorMes().equals(String.valueOf("0"+month)))? fecha.getMes(): fechaConsulta;
				}
				listarRegistro();
			}

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo La vista de Inicio de Registros");
		}
	}
	
	public void listarRegistro(){
		
		try{
			Registro_IngresosDao daoR = new Registro_IngresosDao();
			listaRegistros = daoR.listaIngresos(fechaBusqueda, fechaBusquedaYear);
			
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Lista de Registros");
		}
	}
	
	public void listaRegistroUsuarios(){
		
		try{
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			Registro_IngresosDao daoR = new Registro_IngresosDao();
			listaRegistros = daoR.listaIngresosUsuarios(fechaBusqueda, fechaBusquedaYear, idFun);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Lista de Registros Usuario");
		}
	}

	public AutenticacionBean getAutenticacion() {
		return autenticacion;
	}

	public void setAutenticacion(AutenticacionBean autenticacion) {
		this.autenticacion = autenticacion;
	}

	public List<Registro_Ingresos> getListaRegistros() {
		return listaRegistros;
	}

	public void setListaRegistros(List<Registro_Ingresos> listaRegistros) {
		this.listaRegistros = listaRegistros;
	}

	public List<Fechas> getListaFechas() {
		return listaFechas;
	}

	public void setListaFechas(List<Fechas> listaFechas) {
		this.listaFechas = listaFechas;
	}

	public String getFechaConsulta() {
		return fechaConsulta;
	}

	public void setFechaConsulta(String fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
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

	public Recursos getRecurso() {
		return recurso;
	}

	public void setRecurso(Recursos recurso) {
		this.recurso = recurso;
	}

	public int getIdFun() {
		return idFun;
	}

	public void setIdFun(int idFun) {
		this.idFun = idFun;
	}
}
