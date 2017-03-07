package co.com.CGAwebComercial.entyties;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
public class CargaTrabajo implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column (length = 255)
	private String equipo;
	
	@Column (length = 255)
	private String capacidad;
	
	private int horaDia;
	
	private int horaContratadas;
	
	@Temporal(TemporalType.DATE)
	private Date fechaContratada;
	
	@Temporal(TemporalType.DATE)
	private Date fechaDisponibilidad;
	
	@Column (length = 255)
	private String horaDisponible;
	
	@Column (length = 255)
	private String observacion;
	
	@Temporal(TemporalType.DATE)
	private Date fechaRegistros;
	
	@ManyToOne
	private TrabajoArea trabajoArea;
	
	@ManyToOne
	private Sucursal sucursal;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEquipo() {
		return equipo;
	}

	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}

	public String getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}

	public int getHoraDia() {
		return horaDia;
	}

	public void setHoraDia(int horaDia) {
		this.horaDia = horaDia;
	}

	public int getHoraContratadas() {
		return horaContratadas;
	}

	public void setHoraContratadas(int horaContratadas) {
		this.horaContratadas = horaContratadas;
	}

	public Date getFechaContratada() {
		return fechaContratada;
	}

	public void setFechaContratada(Date fechaContratada) {
		this.fechaContratada = fechaContratada;
	}

	public Date getFechaDisponibilidad() {
		return fechaDisponibilidad;
	}

	public void setFechaDisponibilidad(Date fechaDisponibilidad) {
		this.fechaDisponibilidad = fechaDisponibilidad;
	}

	public String getHoraDisponible() {
		return horaDisponible;
	}

	public void setHoraDisponible(String horaDisponible) {
		this.horaDisponible = horaDisponible;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getFechaRegistros() {
		return fechaRegistros;
	}

	public void setFechaRegistros(Date fechaRegistros) {
		this.fechaRegistros = fechaRegistros;
	}

	public TrabajoArea getTrabajoArea() {
		return trabajoArea;
	}

	public void setTrabajoArea(TrabajoArea trabajoArea) {
		this.trabajoArea = trabajoArea;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
}
