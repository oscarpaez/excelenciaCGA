package co.com.CGAwebComercial.entyties;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class Actividades implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(length = 50)
	private String actividad;
	
	@Column(length = 50)
	private String avance;
	
	@Temporal(TemporalType.DATE)
	private Date fechaCreacion;
	
	@Temporal(TemporalType.DATE)
	private Date fechaInicio;
	
	@Temporal(TemporalType.DATE)
	private Date fechaFinal;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private PrioridadProyecto prioridad;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private EstadoProyecto estado;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private Usuario usuario;
	
	@Transient
	private List<SubActividades> listaSA;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public String getAvance() {
		return avance;
	}

	public void setAvance(String avance) {
		this.avance = avance;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public PrioridadProyecto getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(PrioridadProyecto prioridad) {
		this.prioridad = prioridad;
	}

	public EstadoProyecto getEstado() {
		return estado;
	}

	public void setEstado(EstadoProyecto estado) {
		this.estado = estado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<SubActividades> getListaSA() {
		return listaSA;
	}

	public void setListaSA(List<SubActividades> listaSA) {
		this.listaSA = listaSA;
	}		
}
