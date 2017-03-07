package co.com.CGAwebComercial.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import co.com.CGAwebComercial.dao.EstadoProyectoDao;
import co.com.CGAwebComercial.dao.PrioridadProyectoDao;
import co.com.CGAwebComercial.dao.ProyectoDao;
import co.com.CGAwebComercial.entyties.EstadoProyecto;
import co.com.CGAwebComercial.entyties.PrioridadProyecto;
import co.com.CGAwebComercial.entyties.Proyecto;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ProyectoBean implements Serializable {
	
	
	@ManagedProperty("#{autenticacionBean}")
	private AutenticacionBean autenticacion;
	
	List<Proyecto> listaProyecto;
	List<EstadoProyecto> listaEstado;
	List<PrioridadProyecto> listaPrioridad;
	
	Proyecto proyecto;
	EstadoProyecto estado;
	PrioridadProyecto prioridad;
	
//	public ProyectoBean () {
////		ProyectoDao daoP = new ProyectoDao();
////		listaProyecto = daoP.listar();
//		listar();
//	}
	 
	@PostConstruct
	public void listar(){
		try{
			ProyectoDao daoP = new ProyectoDao();
			listaProyecto = daoP.listaProyectosInactivos(autenticacion.getUsuarioLogin().getId());
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargaron Los estados");
		}
	}
	
	
	public void listarA(){
		try{
			ProyectoDao daoP = new ProyectoDao();
			listaProyecto = daoP.listaProyectosActivos(autenticacion.getUsuarioLogin().getId());
			
			for (Proyecto proyecto : listaProyecto) {
				
				long diferenciaEn_ms = proyecto.getFechaFin().getTime() - proyecto.getFechaInicio().getTime();
				long dias = diferenciaEn_ms / (1000 * 60 * 60 * 24);
				
				Date fechaHoy = new Date();
				float diasTrancurridos =  fechaHoy.getTime() - proyecto.getFechaInicio().getTime();
				float diasH = diasTrancurridos / (1000 * 60 * 60 * 24);
				
				float porDias =  (diasH / dias) * 100 ;
				proyecto.setImagen((porDias < proyecto.getAvance())? "azul.jpg": (porDias == proyecto.getAvance())? "verde.jpg" :(porDias-10 > proyecto.getAvance())? "amarillo.jpg": "rojo.png");
				/*
				if(proyecto.getEstado().getId() == 1){
					System.out.println("amarillo.jpg");
					proyecto.setImagen("amarillo.jpg");
				}
				else if(proyecto.getEstado().getId() == 2){
					proyecto.setImagen("azul.jpg");
				}
				else if(proyecto.getEstado().getId() == 3){
					proyecto.setImagen("verde.jpg");
				}
				else if(proyecto.getEstado().getId() == 4){
					proyecto.setImagen("violeta.jpg");
				}
				else if(proyecto.getEstado().getId() == 5){
					proyecto.setImagen("naranja.jpg");
				}
				else if(proyecto.getEstado().getId() == 6){
					proyecto.setImagen("rojo.jpg");
				}*/
			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargaron Los estados");
		}
	}
	public void cargarEstados(){
		
		try{
			proyecto = new Proyecto();
			listaEstado = new ArrayList<>();
			listaPrioridad = new ArrayList<>();
			
			EstadoProyectoDao daoE = new EstadoProyectoDao();
			listaEstado = daoE.listar();
			
			PrioridadProyectoDao daoP = new PrioridadProyectoDao();
			listaPrioridad = daoP.listar();
			
			
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargaron Los estados");
		}
	}
	
	public void guardarProyecto(){
		
		try{
			listaProyecto = new ArrayList<>();
			ProyectoDao daoP = new ProyectoDao();
			int valor = daoP.listaProyecto(autenticacion.getUsuarioLogin().getId());
			proyecto.setUsuario(autenticacion.getUsuarioLogin());
			proyecto.setItem(valor + 1);
			daoP.merge(proyecto);
			
			listaProyecto = daoP.listaProyectosInactivos(autenticacion.getUsuarioLogin().getId());
			Messages.addGlobalInfo("El proyecto se salvo Correctamente");
			cargarEstados();
			
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se guardo el Proyecto");
		}
	}
	
	public void editarProyecto(){
		
		try{
			listaProyecto = new ArrayList<>();
			ProyectoDao daoP = new ProyectoDao();
			proyecto.setUsuario(autenticacion.getUsuarioLogin());
			daoP.editar(proyecto);
			
			listaProyecto = daoP.listaProyectosActivos(autenticacion.getUsuarioLogin().getId());
			Messages.addGlobalInfo("El proyecto se edito Correctamente");
			cargarEstados();
			
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se guardo el Proyecto");
		}
	}
	
	public void editar(Proyecto proyec){
		
		try {
			listaEstado = new ArrayList<>();
			listaPrioridad = new ArrayList<>();
			//proyecto = (Proyecto) event.getComponent().getAttributes().get("proyectoSelecionado");
			proyecto = proyec; 
			
			EstadoProyectoDao daoE = new EstadoProyectoDao();
			listaEstado = daoE.listar();
			
			PrioridadProyectoDao daoP = new PrioridadProyectoDao();
			listaPrioridad = daoP.listar();
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			Messages.addGlobalError("Error no se guardo el Proyecto");
		}
	}

	public void borrar(Proyecto proyec){

		try {
			proyecto = proyec; 
			ProyectoDao daoP = new ProyectoDao();
			daoP.borrar(proyecto);
			
			listaProyecto = daoP.listar();
			Messages.addGlobalInfo("El proyecto se Borro Correctamente");
			cargarEstados();
		} catch (RuntimeException e) {
			e.printStackTrace();
			Messages.addGlobalError("Error no se guardo el Proyecto");
		}
	}
	
	public void borrarA(Proyecto proyec){

		try {
			proyecto = proyec; 
			ProyectoDao daoP = new ProyectoDao();
			daoP.borrar(proyecto);
			
			listaProyecto = daoP.listaProyectosActivos(autenticacion.getUsuarioLogin().getId());
			Messages.addGlobalInfo("El proyecto se Borro Correctamente");
			cargarEstados();
		} catch (RuntimeException e) {
			e.printStackTrace();
			Messages.addGlobalError("Error no se guardo el Proyecto");
		}
	}
	public List<Proyecto> getListaProyecto() {
		return listaProyecto;
	}

	public void setListaProyecto(List<Proyecto> listaProyecto) {
		this.listaProyecto = listaProyecto;
	}

	public List<EstadoProyecto> getListaEstado() {
		return listaEstado;
	}

	public void setListaEstado(List<EstadoProyecto> listaEstado) {
		this.listaEstado = listaEstado;
	}

	public List<PrioridadProyecto> getListaPrioridad() {
		return listaPrioridad;
	}

	public void setListaPrioridad(List<PrioridadProyecto> listaPrioridad) {
		this.listaPrioridad = listaPrioridad;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public EstadoProyecto getEstado() {
		return estado;
	}

	public void setEstado(EstadoProyecto estado) {
		this.estado = estado;
	}

	public PrioridadProyecto getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(PrioridadProyecto prioridad) {
		this.prioridad = prioridad;
	}

	public AutenticacionBean getAutenticacion() {
		return autenticacion;
	}

	public void setAutenticacion(AutenticacionBean autenticacion) {
		this.autenticacion = autenticacion;
	}
}
