package co.com.CGAwebComercial.entyties;

import java.io.Serializable;
import java.util.Date;

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
public class Proyecto implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(length = 100)
	private String nombre;
	
	@Column(length = 512)
	private String descripcion;
	
	@Column(length = 512)
	private String area;
	
	@Column(length = 100)
	private String representante;
	
	@Column(length = 100)
	private String representanteEx;

	private Long valor;
	
	@Temporal(TemporalType.DATE)
	private Date fechaInicio;
	
	@Temporal(TemporalType.DATE)
	private Date fechaFin;
	
	@Temporal(TemporalType.DATE)
	private Date fechaSolicitud;
	
	private Long presupuesto;
	
	private int avance;
	
	private int item;
	
	@Column(length = 512)
	private String planDeAvance;
	
	@Column(length = 100)
	private String responsable;
	
	@Column(length = 100)
	private String roi;
	
	@Column(length = 100)
	private String tir;
	
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
	private String imagen;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getRepresentante() {
		return representante;
	}

	public void setRepresentante(String representante) {
		this.representante = representante;
	}

	public String getRepresentanteEx() {
		return representanteEx;
	}

	public void setRepresentanteEx(String representanteEx) {
		this.representanteEx = representanteEx;
	}

	public Long getValor() {
		return valor;
	}

	public void setValor(Long valor) {
		this.valor = valor;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Long getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(Long presupuesto) {
		this.presupuesto = presupuesto;
	}

	public int getAvance() {
		return avance;
	}

	public void setAvance(int avance) {
		this.avance = avance;
	}

	public String getPlanDeAvance() {
		return planDeAvance;
	}

	public void setPlanDeAvance(String planDeAvance) {
		this.planDeAvance = planDeAvance;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
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

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
	}

	public String getRoi() {
		return roi;
	}

	public void setRoi(String roi) {
		this.roi = roi;
	}

	public String getTir() {
		return tir;
	}

	public void setTir(String tir) {
		this.tir = tir;
	}
	
}
