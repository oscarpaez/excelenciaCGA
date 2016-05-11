package co.com.CGAwebComercial.entyties;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class EsquemaGerentes implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private int ejercicio;
	
	private int periodo;
	
	@Column(length = 50)
	private String elementos;
	
	private int linea;
	
	private int sucursal;
	
	@Column(length = 50)
	private String nombreSucursal;
	
	private int unidadDeNegocio;
	
	private int importeReal;
	
	private int importePlan;
	
	private int utilidad;
	
	private int cumplimiento;
	
	@Transient
	private int comision;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(int ejercicio) {
		this.ejercicio = ejercicio;
	}

	public int getPeriodo() {
		return periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}

	public String getElementos() {
		return elementos;
	}

	public void setElementos(String elementos) {
		this.elementos = elementos;
	}

	public int getLinea() {
		return linea;
	}

	public void setLinea(int linea) {
		this.linea = linea;
	}

	public int getSucursal() {
		return sucursal;
	}

	public void setSucursal(int sucursal) {
		this.sucursal = sucursal;
	}

	public String getNombreSucursal() {
		return nombreSucursal;
	}

	public void setNombreSucursal(String nombreSucursal) {
		this.nombreSucursal = nombreSucursal;
	}

	public int getUnidadDeNegocio() {
		return unidadDeNegocio;
	}

	public void setUnidadDeNegocio(int unidadDeNegocio) {
		this.unidadDeNegocio = unidadDeNegocio;
	}

	public int getImporteReal() {
		return importeReal;
	}

	public void setImporteReal(int importeReal) {
		this.importeReal = importeReal;
	}

	public int getImportePlan() {
		return importePlan;
	}

	public void setImportePlan(int importePlan) {
		this.importePlan = importePlan;
	}

	public int getUtilidad() {
		return utilidad;
	}

	public void setUtilidad(int utilidad) {
		this.utilidad = utilidad;
	}

	public int getCumplimiento() {
		return cumplimiento;
	}

	public void setCumplimiento(int cumplimiento) {
		this.cumplimiento = cumplimiento;
	}

	public int getComision() {
		return comision;
	}

	public void setComision(int comision) {
		this.comision = comision;
	}
}
