package co.com.CGAwebComercial.entyties;

import java.io.Serializable;
import java.math.BigDecimal;
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
public class Presupuesto implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idPresupuesto;
	
	@Temporal(TemporalType.DATE)
	private Date periodo;
	
	@Column(scale = 2)
	private BigDecimal ingresos;
	
	@Column(scale = 2)
	private BigDecimal costo;
	
	@Column(scale = 2)
	private  BigDecimal utilidad;
	
	@Column(scale = 2)
	private BigDecimal distribucionPorLinea;
	
	@Column(scale = 2)
	private BigDecimal distribucionPorUtilidad;
	
	private int oficinaVentas;	
	
	@Column(length = 10)
	private String zonaVentas;	
		
	private int funcionario;	
	
	private int linea;
	
	@Column(length = 10)
	private String uen;

	public int getIdPresupuesto() {
		return idPresupuesto;
	}

	public void setIdPresupuesto(int idPresupuesto) {
		this.idPresupuesto = idPresupuesto;
	}

	public Date getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Date periodo) {
		this.periodo = periodo;
	}

	public BigDecimal getIngresos() {
		return ingresos;
	}

	public void setIngresos(BigDecimal ingresos) {
		this.ingresos = ingresos;
	}

	public BigDecimal getCosto() {
		return costo;
	}

	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}

	public BigDecimal getUtilidad() {
		return utilidad;
	}

	public void setUtilidad(BigDecimal utilidad) {
		this.utilidad = utilidad;
	}

	public BigDecimal getDistribucionPorLinea() {
		return distribucionPorLinea;
	}

	public void setDistribucionPorLinea(BigDecimal distribucionPorLinea) {
		this.distribucionPorLinea = distribucionPorLinea;
	}

	public int getOficinaVentas() {
		return oficinaVentas;
	}

	public void setOficinaVentas(int oficinaVentas) {
		this.oficinaVentas = oficinaVentas;
	}

	public String getZonaVentas() {
		return zonaVentas;
	}

	public void setZonaVentas(String zonaVentas) {
		this.zonaVentas = zonaVentas;
	}

	public int getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(int funcionario) {
		this.funcionario = funcionario;
	}

	public int getLinea() {
		return linea;
	}

	public void setLinea(int linea) {
		this.linea = linea;
	}

	public String getUen() {
		return uen;
	}

	public void setUen(String uen) {
		this.uen = uen;
	}
}