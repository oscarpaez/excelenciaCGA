package co.com.CGAwebComercial.entyties;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
public class AjusteVendedor implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private int codSap;
	
	@Column(length = 50)
	private String nombre;
	
	private int codSapA;
	
	@Column(length = 50)
	private String nombreA;
	
	private int codCliente;
	
	@Column(length = 50)
	private String nombreCliente;
	
	private int codLinea;
	
	@Column(length = 50)
	private String nombreLinea;
	
	private int codLineaA;
	
	@Column(length = 50)
	private String nombreLineaA;
	
	@Column(length = 255)
	private String observacion;
	
	@Temporal(TemporalType.DATE)
	private Date fechaAjuste;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCodSap() {
		return codSap;
	}

	public void setCodSap(int codSap) {
		this.codSap = codSap;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCodSapA() {
		return codSapA;
	}

	public void setCodSapA(int codSapA) {
		this.codSapA = codSapA;
	}

	public String getNombreA() {
		return nombreA;
	}

	public void setNombreA(String nombreA) {
		this.nombreA = nombreA;
	}

	public int getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(int codCliente) {
		this.codCliente = codCliente;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public int getCodLinea() {
		return codLinea;
	}

	public void setCodLinea(int codLinea) {
		this.codLinea = codLinea;
	}

	public String getNombreLinea() {
		return nombreLinea;
	}

	public void setNombreLinea(String nombreLinea) {
		this.nombreLinea = nombreLinea;
	}

	public int getCodLineaA() {
		return codLineaA;
	}

	public void setCodLineaA(int codLineaA) {
		this.codLineaA = codLineaA;
	}

	public String getNombreLineaA() {
		return nombreLineaA;
	}

	public void setNombreLineaA(String nombreLineaA) {
		this.nombreLineaA = nombreLineaA;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getFechaAjuste() {
		return fechaAjuste;
	}

	public void setFechaAjuste(Date fechaAjuste) {
		this.fechaAjuste = fechaAjuste;
	}	
}
