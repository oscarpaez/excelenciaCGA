package co.com.CGAwebComercial.bean;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import co.com.CGAwebComercial.dao.CausaPerdidaVentaDao;
import co.com.CGAwebComercial.dao.IncidenciaDao;
import co.com.CGAwebComercial.dao.LineaDao;
import co.com.CGAwebComercial.dao.RegularidadPerdidaVentaDao;
import co.com.CGAwebComercial.dao.Zona_ventaDao;
import co.com.CGAwebComercial.entyties.CausaPerdidaVenta;
import co.com.CGAwebComercial.entyties.Incidencia;
import co.com.CGAwebComercial.entyties.Linea;
import co.com.CGAwebComercial.entyties.RegularidadPerdidaVenta;
import co.com.CGAwebComercial.entyties.Zona_venta;


@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class IncidenciaBean implements Serializable{

	@ManagedProperty("#{autenticacionBean}")
	private AutenticacionBean autenticacion;
	
	private List<CausaPerdidaVenta> listaCausa;
	private List<RegularidadPerdidaVenta> listaRegularidad;
	private List<Linea> listaLinea;
	private List<Incidencia> listaIncidencia;
	
	private Zona_venta zona;
	private Incidencia incidencia;
	private String valorTotal;
	
	public void listarIncidencias(){
		
		try{
			IncidenciaDao dao = new IncidenciaDao();
			listaIncidencia = dao.listar();
			System.out.println(listaIncidencia.size());
			Long sumaTotal = (long) 0;
			for (Incidencia incidencia : listaIncidencia) {
				sumaTotal += incidencia.getValorVenta();
			}
			
			valorTotal = new DecimalFormat("###,###").format(sumaTotal);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error No se cargo la lista de Incidencias");
		}
	}
	
	
	public void listarAsesor(){
		
		try{
			Zona_ventaDao dao = new Zona_ventaDao();
			zona = new Zona_venta();
			List<Zona_venta > zonaL = dao.buscarZona(autenticacion.getUsuarioLogin().getId());
			incidencia = new Incidencia();
			
			CausaPerdidaVentaDao daoC = new CausaPerdidaVentaDao();
			listaCausa = daoC.listar();
			
			RegularidadPerdidaVentaDao daoR = new RegularidadPerdidaVentaDao();
			listaRegularidad = daoR.listar();
			
			LineaDao daoL = new LineaDao();
			listaLinea = daoL.listar();
			
			System.out.println(autenticacion.getUsuarioLogin().getId() +" - " + zona);
			incidencia.setZona(zonaL.get(0));
			
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error se cargaron los datos Asesor.");
		}
	}
	
	public void salvar(){
		
		try{
			IncidenciaDao dao = new IncidenciaDao();
			dao.salvar(incidencia); 
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se guardo el Proyecto");
		}
	}

	public AutenticacionBean getAutenticacion() {
		return autenticacion;
	}

	public void setAutenticacion(AutenticacionBean autenticacion) {
		this.autenticacion = autenticacion;
	}

	public List<CausaPerdidaVenta> getListaCausa() {
		return listaCausa;
	}

	public void setListaCausa(List<CausaPerdidaVenta> listaCausa) {
		this.listaCausa = listaCausa;
	}

	public List<RegularidadPerdidaVenta> getListaRegularidad() {
		return listaRegularidad;
	}

	public void setListaRegularidad(List<RegularidadPerdidaVenta> listaRegularidad) {
		this.listaRegularidad = listaRegularidad;
	}

	public Zona_venta getZona() {
		return zona;
	}

	public void setZona(Zona_venta zona) {
		this.zona = zona;
	}

	public Incidencia getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(Incidencia incidencia) {
		this.incidencia = incidencia;
	}

	public List<Linea> getListaLinea() {
		return listaLinea;
	}

	public void setListaLinea(List<Linea> listaLinea) {
		this.listaLinea = listaLinea;
	}

	public List<Incidencia> getListaIncidencia() {
		return listaIncidencia;
	}

	public void setListaIncidencia(List<Incidencia> listaIncidencia) {
		this.listaIncidencia = listaIncidencia;
	}
	public String getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(String valorTotal) {
		this.valorTotal = valorTotal;
	}
}
