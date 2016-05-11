package co.com.CGAwebComercial.util;

import java.math.BigDecimal;

public class ComisionVendedores {
	
	
	private int id;
	private int cedula;
	private String nombre;
	private String tipo;
	private String concepto;
	private int presupuesto;
	private int ingresoReal;
	private BigDecimal presupuestoB;
	private BigDecimal ingresoRealB;
	private BigDecimal umbralCV;
	private BigDecimal cumplimiento;
	private String imagen;
	private int comision;
	private int comisionR;
	private int totalAjuste;
	private int totalConAjuste;
	private String comisionS;
	private String liquidar;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public int getPresupuesto() {
		return presupuesto;
	}
	public void setPresupuesto(int presupuesto) {
		this.presupuesto = presupuesto;
	}
	public int getIngresoReal() {
		return ingresoReal;
	}
	public void setIngresoReal(int ingresoReal) {
		this.ingresoReal = ingresoReal;
	}
	public BigDecimal getUmbralCV() {
		return umbralCV;
	}
	public void setUmbralCV(BigDecimal umbralCV) {
		this.umbralCV = umbralCV;
	}
	public int getComision() {
		return comision;
	}
	public void setComision(int comision) {
		this.comision = comision;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public BigDecimal getCumplimiento() {
		return cumplimiento;
	}
	public void setCumplimiento(BigDecimal cumplimiento) {
		this.cumplimiento = cumplimiento;
	}
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	public String getComisionS() {
		return comisionS;
	}
	public void setComisionS(String comisionS) {
		this.comisionS = comisionS;
	}
	public int getCedula() {
		return cedula;
	}
	public void setCedula(int cedula) {
		this.cedula = cedula;
	}
	public BigDecimal getPresupuestoB() {
		return presupuestoB;
	}
	public void setPresupuestoB(BigDecimal presupuestoB) {
		this.presupuestoB = presupuestoB;
	}
	public BigDecimal getIngresoRealB() {
		return ingresoRealB;
	}
	public void setIngresoRealB(BigDecimal ingresoRealB) {
		this.ingresoRealB = ingresoRealB;
	}
	public int getTotalAjuste() {
		return totalAjuste;
	}
	public void setTotalAjuste(int totalAjuste) {
		this.totalAjuste = totalAjuste;
	}
	public int getTotalConAjuste() {
		return totalConAjuste;
	}
	public void setTotalConAjuste(int totalConAjuste) {
		this.totalConAjuste = totalConAjuste;
	}
	public int getComisionR() {
		return comisionR;
	}
	public void setComisionR(int comisionR) {
		this.comisionR = comisionR;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	
	public String getLiquidar() {
		return liquidar;
	}
	public void setLiquidar(String liquidar) {
		this.liquidar = liquidar;
	}
	@Override
	public String toString() {
		return "ComisionVendedores [id=" + id + ", cedula=" + cedula + ", nombre=" + nombre + ", tipo=" + tipo
				+ ", concepto=" + concepto + ", presupuesto=" + presupuesto + ", ingresoReal=" + ingresoReal
				+ ", presupuestoB=" + presupuestoB + ", ingresoRealB=" + ingresoRealB + ", umbralCV=" + umbralCV
				+ ", cumplimiento=" + cumplimiento + ", imagen=" + imagen + ", comision=" + comision + ", comisionR="
				+ comisionR + ", totalAjuste=" + totalAjuste + ", totalConAjuste=" + totalConAjuste + ", comisionS="
				+ comisionS + "]";
	}
	
	
}
