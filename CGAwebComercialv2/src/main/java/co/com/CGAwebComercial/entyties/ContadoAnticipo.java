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
import javax.persistence.Transient;

import org.hibernate.annotations.NamedNativeQuery;

@SuppressWarnings("serial")
@Entity
@NamedNativeQuery(name = "order1", query = "select NoPersonal, sum(totalrecCA)*0.25 as Total from contadoanticipo group by NoPersonal order by NoPersonal", resultClass = ContadoAnticipo.class)
public class ContadoAnticipo implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private int sociedad;
	
	private int  ejercicio;
	
	private int  periodo;
	
	private int  ofVentas;
	
	@Column(length = 255)
	private String zVentas; 
	
	private int NoPersonal;
	
	@Column(length = 255)
	private String vendedor; 
	  
	private int cliente;
	
	@Transient
	private int cedula;
	
	private int noFactura;
	
	private int vendInterno;
	
	private int docComp;
	
	@Temporal(TemporalType.DATE)
	private Date fecComp;
	  
	private int docContable;
	  
	private int posDoc;
	
	@Transient
	private String liquidar;
	
	@Column(length = 10)
	private String TpDoc;
	
	@Column(length = 10)
	private String conPago;
	
	@Column(length = 10)
	private String moneda;
	     
	private int presupuesto;
	
	private int mora;
	
	private int recVtsMes;
	  
	private int recCartJur;
	
	private int recSVen30;
	
	private int recSVen;
	
	private int rec130Dias;
	
	private int rec3160Dias;
	
	private int rec6190Dias;
	
	private int rec91120Dias;
	
	private int  rec121150Dias;
	
	private int  rec151Dias;
	
	private int totalRecaudo;
	
	private int totPptoRec;
	
	private int cumplePpto;
	
	private int totalrecCA;
	
	@Transient
	private BigDecimal porcentaje;
	
	@Transient
	private BigDecimal comision;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSociedad() {
		return sociedad;
	}

	public void setSociedad(int sociedad) {
		this.sociedad = sociedad;
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

	public int getOfVentas() {
		return ofVentas;
	}

	public void setOfVentas(int ofVentas) {
		this.ofVentas = ofVentas;
	}

	public String getzVentas() {
		return zVentas;
	}

	public void setzVentas(String zVentas) {
		this.zVentas = zVentas;
	}

	public int getNoPersonal() {
		return NoPersonal;
	}

	public void setNoPersonal(int noPersonal) {
		NoPersonal = noPersonal;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public int getCliente() {
		return cliente;
	}

	public void setCliente(int cliente) {
		this.cliente = cliente;
	}

	public int getNoFactura() {
		return noFactura;
	}

	public void setNoFactura(int noFactura) {
		this.noFactura = noFactura;
	}

	public int getVendInterno() {
		return vendInterno;
	}

	public void setVendInterno(int vendInterno) {
		this.vendInterno = vendInterno;
	}

	public int getDocComp() {
		return docComp;
	}

	public void setDocComp(int docComp) {
		this.docComp = docComp;
	}

	public Date getFecComp() {
		return fecComp;
	}

	public void setFecComp(Date fecComp) {
		this.fecComp = fecComp;
	}

	public int getDocContable() {
		return docContable;
	}

	public void setDocContable(int docContable) {
		this.docContable = docContable;
	}

	public int getPosDoc() {
		return posDoc;
	}

	public void setPosDoc(int posDoc) {
		this.posDoc = posDoc;
	}

	public String getTpDoc() {
		return TpDoc;
	}

	public void setTpDoc(String tpDoc) {
		TpDoc = tpDoc;
	}

	public String getConPago() {
		return conPago;
	}

	public void setConPago(String conPago) {
		this.conPago = conPago;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public int getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(int presupuesto) {
		this.presupuesto = presupuesto;
	}

	public int getMora() {
		return mora;
	}

	public void setMora(int mora) {
		this.mora = mora;
	}

	public int getRecVtsMes() {
		return recVtsMes;
	}

	public void setRecVtsMes(int recVtsMes) {
		this.recVtsMes = recVtsMes;
	}

	public int getRecCartJur() {
		return recCartJur;
	}

	public void setRecCartJur(int recCartJur) {
		this.recCartJur = recCartJur;
	}

	public int getRecSVen30() {
		return recSVen30;
	}

	public void setRecSVen30(int recSVen30) {
		this.recSVen30 = recSVen30;
	}

	public int getRecSVen() {
		return recSVen;
	}

	public void setRecSVen(int recSVen) {
		this.recSVen = recSVen;
	}

	public int getRec130Dias() {
		return rec130Dias;
	}

	public void setRec130Dias(int rec130Dias) {
		this.rec130Dias = rec130Dias;
	}

	public int getRec3160Dias() {
		return rec3160Dias;
	}

	public void setRec3160Dias(int rec3160Dias) {
		this.rec3160Dias = rec3160Dias;
	}

	public int getRec6190Dias() {
		return rec6190Dias;
	}

	public void setRec6190Dias(int rec6190Dias) {
		this.rec6190Dias = rec6190Dias;
	}

	public int getRec91120Dias() {
		return rec91120Dias;
	}

	public void setRec91120Dias(int rec91120Dias) {
		this.rec91120Dias = rec91120Dias;
	}

	public int getRec121150Dias() {
		return rec121150Dias;
	}

	public void setRec121150Dias(int rec121150Dias) {
		this.rec121150Dias = rec121150Dias;
	}

	public int getRec151Dias() {
		return rec151Dias;
	}

	public void setRec151Dias(int rec151Dias) {
		this.rec151Dias = rec151Dias;
	}

	public int getTotalRecaudo() {
		return totalRecaudo;
	}

	public void setTotalRecaudo(int totalRecaudo) {
		this.totalRecaudo = totalRecaudo;
	}

	public int getTotPptoRec() {
		return totPptoRec;
	}

	public void setTotPptoRec(int totPptoRec) {
		this.totPptoRec = totPptoRec;
	}

	public int getCumplePpto() {
		return cumplePpto;
	}

	public void setCumplePpto(int cumplePpto) {
		this.cumplePpto = cumplePpto;
	}

	public BigDecimal getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}

	public BigDecimal getComision() {
		return comision;
	}

	public void setComision(BigDecimal comision) {
		this.comision = comision;
	}

	public int getTotalrecCA() {
		return totalrecCA;
	}

	public void setTotalrecCA(int totalrecCA) {
		this.totalrecCA = totalrecCA;
	}

	public int getCedula() {
		return cedula;
	}

	public void setCedula(int cedula) {
		this.cedula = cedula;
	}

	public String getLiquidar() {
		return liquidar;
	}

	public void setLiquidar(String liquidar) {
		this.liquidar = liquidar;
	}
	
}
