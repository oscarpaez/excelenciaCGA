package co.com.CGAwebComercial.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import co.com.CGAwebComercial.dao.ActividadesDao;
import co.com.CGAwebComercial.dao.EstadoProyectoDao;
import co.com.CGAwebComercial.dao.PrioridadProyectoDao;
import co.com.CGAwebComercial.dao.SubActividadDao;
import co.com.CGAwebComercial.entyties.Actividades;
import co.com.CGAwebComercial.entyties.EstadoProyecto;
import co.com.CGAwebComercial.entyties.PrioridadProyecto;
import co.com.CGAwebComercial.entyties.SubActividades;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ActividadesBean implements Serializable{
	
	@ManagedProperty("#{autenticacionBean}")
	private AutenticacionBean autenticacion;
	
	private List<Actividades> listaActividades;
	private List<EstadoProyecto> listaEstado;
	private List<PrioridadProyecto> listaPrioridad;
	
	private Actividades activida;
	private SubActividades subActiva;
	
	public ActividadesBean() {
		autenticacion.registroIngreso(autenticacion.getUsuarioLogin());
	}
	
	@PostConstruct
	public void listaActividadesUsuario(){
		
		try{
			ActividadesDao dao = new ActividadesDao();
			listaActividades = dao.listaPorUsuario(autenticacion.getUsuarioLogin().getId());
			List<SubActividades> listS = new ArrayList<>();
			for (Actividades actividades : listaActividades) {
				SubActividadDao daoS = new SubActividadDao();
				System.out.println(actividades.getId() + "RREE");
				listS = daoS.listaSubActividades(actividades.getId());
				if(!listS.isEmpty()){
					System.out.println(listS.size() + "QQEE");
					actividades.setListaSA(listS);
				}
			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Actividades de usuario");
		}
	}
	
	public void cargarActividad(){
		
		try{
			activida = new Actividades();
			listaEstado = new ArrayList<>();
			listaPrioridad = new ArrayList<>();
			
			EstadoProyectoDao daoE = new EstadoProyectoDao();
			listaEstado = daoE.listar();
			
			PrioridadProyectoDao daoP = new PrioridadProyectoDao();
			listaPrioridad = daoP.listar();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la actividad");
		}
		
	}
	
	public void guardarActividad(){
		
		try{
			ActividadesDao dao = new ActividadesDao();
			activida.setUsuario(autenticacion.getUsuarioLogin());
			dao.merge(activida);
			
			listaActividadesUsuario();
			//listaActividades = dao.listaPorUsuario(autenticacion.getUsuarioLogin().getId());
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se guardo la actividad");
		}
	}
	
	public void editarActividad(Actividades actividades){
		
		try{
			activida = actividades;			
			
			EstadoProyectoDao daoE = new EstadoProyectoDao();
			listaEstado = daoE.listar();
			
			PrioridadProyectoDao daoP = new PrioridadProyectoDao();
			listaPrioridad = daoP.listar();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se guardo la actividad");
		}
	}
	
	public void borrarActividad(Actividades actividades){

		try{
			SubActividadDao daoS = new SubActividadDao();
			List<SubActividades> ls = daoS.listaSubActividades(actividades.getId());
			
			if(ls.size()>0){
				for (SubActividades subA : ls) {
					daoS.borrar(subA);
				}
			}			
			ActividadesDao dao = new ActividadesDao();
			dao.borrar(actividades);			
			listaActividadesUsuario();
			//listaActividades = dao.listaPorUsuario(autenticacion.getUsuarioLogin().getId());
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se guardo la actividad");
		}
	}
	
	public void iniciarSubA(Actividades act){
		
		try{
			System.out.println(act.getId());
			subActiva = new SubActividades();
			subActiva.setActivida(act);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se inicio la subActividad");
		}
	}
	
	public void guardarSubActividad(){
		
		try{
			System.out.println(subActiva.getAvance());
			System.out.println(subActiva.getActivida().getEstado().getNombre());
			SubActividadDao dao = new SubActividadDao();
			dao.merge(subActiva);	
			listaActividadesUsuario();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se guardo la subActividad");
		}
	}
	
	public void editarSubActividad(SubActividades sub){

		try{
			subActiva = sub;
			System.out.println(subActiva.getNombre());
			
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se guardo la actividad");
		}
	}
	
	public void borrarSubActividad(SubActividades sub){

		try{
			SubActividadDao dao = new SubActividadDao();
			dao.borrar(sub);			
			listaActividadesUsuario();
			//listaActividades = dao.listaPorUsuario(autenticacion.getUsuarioLogin().getId());
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se guardo la actividad");
		}
	}

	public AutenticacionBean getAutenticacion() {
		return autenticacion;
	}

	public void setAutenticacion(AutenticacionBean autenticacion) {
		this.autenticacion = autenticacion;
	}

	public List<Actividades> getListaActividades() {
		return listaActividades;
	}

	public void setListaActividades(List<Actividades> listaActividades) {
		this.listaActividades = listaActividades;
	}

	public Actividades getActivida() {
		return activida;
	}

	public void setActivida(Actividades activida) {
		this.activida = activida;
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

	public SubActividades getSubActiva() {
		return subActiva;
	}

	public void setSubActiva(SubActividades subActiva) {
		this.subActiva = subActiva;
	}	
}
