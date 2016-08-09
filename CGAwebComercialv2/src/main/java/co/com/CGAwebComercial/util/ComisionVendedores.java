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
	private BigDecimal utilpresupuesto;
	private BigDecimal utilidadReal;
	private BigDecimal umbralCV;
	private BigDecimal cumplimiento;
	private BigDecimal cumplimientoU;
	private BigDecimal comisionVentaLinea;
	private BigDecimal cumplimientoVL;
	private BigDecimal comisionVentaPais;
	private BigDecimal cumplimientoVP;
	private BigDecimal comisionMixLinea;
	private BigDecimal cumplimientoML;
	private BigDecimal comisionZonasCargo;
	private BigDecimal cumplimientoZC;
	private BigDecimal comisionRecaudo;
	private BigDecimal cumplimientoR;
	private BigDecimal comisionTotal;	
	private String imagen1;
	private String imagen;
	private int comision;
	private int comisionR;
	private int totalAjuste;
	private int totalConAjuste;
	private String comisionS;
	private String liquidar;
	private String detalleVL;
	private String detalleVP;
	private String detalleML;
	private String detalleZC;
	private String detalleR;
	
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
	
	public BigDecimal getUtilpresupuesto() {
		return utilpresupuesto;
	}
	public void setUtilpresupuesto(BigDecimal utilpresupuesto) {
		this.utilpresupuesto = utilpresupuesto;
	}
	public BigDecimal getUtilidadReal() {
		return utilidadReal;
	}
	public void setUtilidadReal(BigDecimal utilidadReal) {
		this.utilidadReal = utilidadReal;
	}
	public String getImagen1() {
		return imagen1;
	}
	public void setImagen1(String imagen1) {
		this.imagen1 = imagen1;
	}
	public BigDecimal getCumplimientoU() {
		return cumplimientoU;
	}
	public void setCumplimientoU(BigDecimal cumplimientoU) {
		this.cumplimientoU = cumplimientoU;
	}
	
	public BigDecimal getComisionVentaLinea() {
		return comisionVentaLinea;
	}
	public void setComisionVentaLinea(BigDecimal comisionVentaLinea) {
		this.comisionVentaLinea = comisionVentaLinea;
	}
	public BigDecimal getComisionVentaPais() {
		return comisionVentaPais;
	}
	public void setComisionVentaPais(BigDecimal comisionVentaPais) {
		this.comisionVentaPais = comisionVentaPais;
	}
	public BigDecimal getComisionMixLinea() {
		return comisionMixLinea;
	}
	public void setComisionMixLinea(BigDecimal comisionMixLinea) {
		this.comisionMixLinea = comisionMixLinea;
	}
	public BigDecimal getComisionZonasCargo() {
		return comisionZonasCargo;
	}
	public void setComisionZonasCargo(BigDecimal comisionZonasCargo) {
		this.comisionZonasCargo = comisionZonasCargo;
	}
	public BigDecimal getComisionRecaudo() {
		return comisionRecaudo;
	}
	public void setComisionRecaudo(BigDecimal comisionRecaudo) {
		this.comisionRecaudo = comisionRecaudo;
	}
	public BigDecimal getCumplimientoVL() {
		return cumplimientoVL;
	}
	public void setCumplimientoVL(BigDecimal cumplimientoVL) {
		this.cumplimientoVL = cumplimientoVL;
	}
	public BigDecimal getCumplimientoVP() {
		return cumplimientoVP;
	}
	public void setCumplimientoVP(BigDecimal cumplimientoVP) {
		this.cumplimientoVP = cumplimientoVP;
	}
	public BigDecimal getCumplimientoML() {
		return cumplimientoML;
	}
	public void setCumplimientoML(BigDecimal cumplimientoML) {
		this.cumplimientoML = cumplimientoML;
	}
	public BigDecimal getCumplimientoZC() {
		return cumplimientoZC;
	}
	public void setCumplimientoZC(BigDecimal cumplimientoZC) {
		this.cumplimientoZC = cumplimientoZC;
	}
	public BigDecimal getCumplimientoR() {
		return cumplimientoR;
	}
	public void setCumplimientoR(BigDecimal cumplimientoR) {
		this.cumplimientoR = cumplimientoR;
	}
	public String getDetalleVL() {
		return detalleVL;
	}
	public void setDetalleVL(String detalleVL) {
		this.detalleVL = detalleVL;
	}
	public String getDetalleVP() {
		return detalleVP;
	}
	public void setDetalleVP(String detalleVP) {
		this.detalleVP = detalleVP;
	}
	public String getDetalleML() {
		return detalleML;
	}
	public void setDetalleML(String detalleML) {
		this.detalleML = detalleML;
	}
	public String getDetalleZC() {
		return detalleZC;
	}
	public void setDetalleZC(String detalleZC) {
		this.detalleZC = detalleZC;
	}
	public String getDetalleR() {
		return detalleR;
	}
	public void setDetalleR(String detalleR) {
		this.detalleR = detalleR;
	}
	public BigDecimal getComisionTotal() {
		return comisionTotal;
	}
	public void setComisionTotal(BigDecimal comisionTotal) {
		this.comisionTotal = comisionTotal;
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
