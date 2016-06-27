package co.com.CGAwebComercial.entyties;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the plan database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Plan implements Serializable {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id_plan;

	@Column(scale = 3)
	private BigDecimal comision;

	@Column(scale = 3)
	private BigDecimal costo;

	@Column(scale = 3)
	private BigDecimal costo_Real;

	@Column(scale = 2)
	private BigDecimal distribucion_Linea;

	@Column(scale = 3)
	private BigDecimal ingreso;

	@Column(scale = 3)
	private BigDecimal ingreso_Cumplimiento;

	@Column(scale = 3)
	private BigDecimal ingreso_Real;

	@Column(scale = 3)
	private BigDecimal umbral;

	@Column(scale = 3)
	private BigDecimal utilidad;

	@Column(scale = 2)
	private BigDecimal utilidad_Cumplimiento;

	@Column(scale = 3)
	private BigDecimal utilidad_Real;

	@Column(scale = 3)
	private BigDecimal valor_Base;

	@Column(scale = 3)
	private BigDecimal valor_bruto_comision;

	@Column(scale = 1)
	private BigDecimal valor_Comision_Pagar;
	
	@Transient
	private BigDecimal valor_Comision_PagarR;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaIngreso;
	
	@Transient
	private Date fechaIni;
	
	@Transient
	private Date fechaFin;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Funcionario funcionario;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Linea linea;
	
	@Transient
	private String imagen;
	
	@Transient
	private String imagen1;

	public int getId_plan() {
		return id_plan;
	}

	public void setId_plan(int id_plan) {
		this.id_plan = id_plan;
	}

	public BigDecimal getComision() {
		return comision;
	}

	public void setComision(BigDecimal comision) {
		this.comision = comision;
	}

	public BigDecimal getCosto() {
		return costo;
	}

	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}

	public BigDecimal getCosto_Real() {
		return costo_Real;
	}

	public void setCosto_Real(BigDecimal costo_Real) {
		this.costo_Real = costo_Real;
	}

	public BigDecimal getDistribucion_Linea() {
		return distribucion_Linea;
	}

	public void setDistribucion_Linea(BigDecimal distribucion_Linea) {
		this.distribucion_Linea = distribucion_Linea;
	}

	public BigDecimal getIngreso() {
		return ingreso;
	}

	public void setIngreso(BigDecimal ingreso) {
		this.ingreso = ingreso;
	}

	public BigDecimal getIngreso_Cumplimiento() {
		return ingreso_Cumplimiento;
	}

	public void setIngreso_Cumplimiento(BigDecimal ingreso_Cumplimiento) {
		this.ingreso_Cumplimiento = ingreso_Cumplimiento;
	}

	public BigDecimal getIngreso_Real() {
		return ingreso_Real;
	}

	public void setIngreso_Real(BigDecimal ingreso_Real) {
		this.ingreso_Real = ingreso_Real;
	}

	public BigDecimal getUmbral() {
		return umbral;
	}

	public void setUmbral(BigDecimal umbral) {
		this.umbral = umbral;
	}

	public BigDecimal getUtilidad() {
		return utilidad;
	}

	public void setUtilidad(BigDecimal utilidad) {
		this.utilidad = utilidad;
	}

	public BigDecimal getUtilidad_Cumplimiento() {
		return utilidad_Cumplimiento;
	}

	public void setUtilidad_Cumplimiento(BigDecimal utilidad_Cumplimiento) {
		this.utilidad_Cumplimiento = utilidad_Cumplimiento;
	}

	public BigDecimal getUtilidad_Real() {
		return utilidad_Real;
	}

	public void setUtilidad_Real(BigDecimal utilidad_Real) {
		this.utilidad_Real = utilidad_Real;
	}

	public BigDecimal getValor_Base() {
		return valor_Base;
	}

	public void setValor_Base(BigDecimal valor_Base) {
		this.valor_Base = valor_Base;
	}

	public BigDecimal getValor_bruto_comision() {
		return valor_bruto_comision;
	}

	public void setValor_bruto_comision(BigDecimal valor_bruto_comision) {
		this.valor_bruto_comision = valor_bruto_comision;
	}

	public BigDecimal getValor_Comision_Pagar() {
		return valor_Comision_Pagar;
	}

	public void setValor_Comision_Pagar(BigDecimal valor_Comision_Pagar) {
		this.valor_Comision_Pagar = valor_Comision_Pagar;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Linea getLinea() {
		return linea;
	}

	public void setLinea(Linea linea) {
		this.linea = linea;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
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

	public BigDecimal getValor_Comision_PagarR() {
		return valor_Comision_PagarR;
	}

	public void setValor_Comision_PagarR(BigDecimal valor_Comision_PagarR) {
		this.valor_Comision_PagarR = valor_Comision_PagarR;
	}

	public String getImagen1() {
		return imagen1;
	}

	public void setImagen1(String imagen1) {
		this.imagen1 = imagen1;
	}
}