package co.com.CGAwebComercial.bean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
	private String fechaIniS;
	private String fechaFinS;
	private int idFun;
	
	private Date fechaIni;
	private Date fechaFin;

	public Registro_IngresosBean() {
		recurso = new Recursos();
		listaFechas = recurso.cargarFechas();
	}
	
	public void inicioVistaRegistro(){

		try{
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			if(fechaIni == null || fechaFin == null){
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
			
			System.out.println(fechaFinS + " -////////- " + fechaIniS + " ///// "+ idFun);
			if(fechaFinS.equals("null") || fechaIniS.equals("null")){
				fechaBusqueda = autenticacion.getFechaBusqueda();
				fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
				Registro_IngresosDao daoR = new Registro_IngresosDao();
				System.out.println("uno" +fechaBusqueda +  " uno " + fechaBusquedaYear + "uno" + idFun );
				listaRegistros = daoR.listaIngresosUsuarios(fechaBusqueda, fechaBusquedaYear, idFun);
			}
			else{
				System.out.println("dos");
				DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
				fechaIni = format.parse(fechaIniS);				
				fechaFin = format.parse(fechaFinS);				
				Registro_IngresosDao daoR = new Registro_IngresosDao();
				listaRegistros = daoR.listaIngresosUsuariosF(fechaIni, fechaFin, idFun);				
			}
		} catch (RuntimeException | ParseException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Lista de Registros Usuario");
		}
	}
	
	public void buscarRegistroFecha(){
		
		try{
			
			System.out.println("Entrro");
			Registro_IngresosDao dao = new Registro_IngresosDao();
			listaRegistros = dao.buscarIngresos(fechaIni, fechaFin);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se cargo la Lista Busqueda de Registros Usuario");
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

	public Date getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getFechaIniS() {
		return fechaIniS;
	}

	public void setFechaIniS(String fechaIniS) {
		this.fechaIniS = fechaIniS;
	}

	public String getFechaFinS() {
		return fechaFinS;
	}

	public void setFechaFinS(String fechaFinS) {
		this.fechaFinS = fechaFinS;
	}
}
