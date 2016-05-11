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
public class bajaRotacion implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idBajaRotacion;
	
	@Temporal(TemporalType.DATE)
	private Date fechaFactura;
	
	@Temporal(TemporalType.DATE)
	private Date fechaContab;
	
	@Column(length = 10)
	private String proyecto;
	
	private int factura;
	
	private int posfact;
	
	private int codoficina;
	
	@Column(length = 10)
	private String oficina;
	
	private int codCliente;
	
	@Column(length = 50)
	private String cliente;
	
	private int codMacrosegmento;
	
	@Column(length = 50)
	private String macrosegmento;
	
	private int codSegmento;
	
	@Column(length = 50)
	private String segmento;
	
	private int codMicrosegmento;
	
	@Column(length = 50)
	private String microsegmento;
	
	private int pedido;
	
	private int posPedido;
	
	private int posPadre;
	
	@Column(length = 10)
	private String clasePed;
	
	@Column(length = 50)
	private String tipodePedido;
	
	private int docMaterial;
	
	private int ordenDeF;
	
	private int centro;
	
	private int almacen;
	
	@Column(length = 10)
	private String denAlmacen;
	
	@Column(length = 10)
	private String zonaVenta;
	
	@Column(length = 10)
	private String moneda;
	
	private int codEspecialista;
	
	private int cedEspecialista;
	
	@Column(length = 50)
	private String especialista;
	
	private int codVendedorInt;
	
	private int cedVendedorInt;
	
	@Column(length = 50)
	private String vendedorInt;
	
	private int codMaterial;
	

	@Column(length = 50)
	private String material;
	
	private int CodMatOrg;
	
	@Column(length = 50)
	private String materialOriginal;
	
	@Column(scale = 2)
	private BigDecimal clValoracion;
	
	@Column(length = 10)
	private String dim1;
	
	@Column(length = 10)
	private String dim2;
	
	@Column(length = 10)
	private String dim3;
	
	private int codLinea;
	
	@Column(length = 50)
	private String linea;
	
	private int cebe;
	
	@Column(length = 5)
	private String umb;
	
	private int pesoNeto;
	
	private int pesoNetoSub;
	
	private int valorBruto;
	
	private int valorBrutoSub;
	
	@Column(scale = 2)
	private BigDecimal descValor;
	
	@Column(scale = 2)
	private BigDecimal descValorSub;
	
	@Column(scale = 2)
	private BigDecimal desc;
	
	private int flete;

	private int valorDev;
	
	private int valorNeto;

	public int getIdBajaRotacion() {
		return idBajaRotacion;
	}

	public void setIdBajaRotacion(int idBajaRotacion) {
		this.idBajaRotacion = idBajaRotacion;
	}

	public Date getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(Date fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public Date getFechaContab() {
		return fechaContab;
	}

	public void setFechaContab(Date fechaContab) {
		this.fechaContab = fechaContab;
	}

	public String getProyecto() {
		return proyecto;
	}

	public void setProyecto(String proyecto) {
		this.proyecto = proyecto;
	}

	public int getFactura() {
		return factura;
	}

	public void setFactura(int factura) {
		this.factura = factura;
	}

	public int getPosfact() {
		return posfact;
	}

	public void setPosfact(int posfact) {
		this.posfact = posfact;
	}

	public int getCodoficina() {
		return codoficina;
	}

	public void setCodoficina(int codoficina) {
		this.codoficina = codoficina;
	}

	public String getOficina() {
		return oficina;
	}

	public void setOficina(String oficina) {
		this.oficina = oficina;
	}

	public int getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(int codCliente) {
		this.codCliente = codCliente;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public int getCodMacrosegmento() {
		return codMacrosegmento;
	}

	public void setCodMacrosegmento(int codMacrosegmento) {
		this.codMacrosegmento = codMacrosegmento;
	}

	public String getMacrosegmento() {
		return macrosegmento;
	}

	public void setMacrosegmento(String macrosegmento) {
		this.macrosegmento = macrosegmento;
	}

	public int getCodSegmento() {
		return codSegmento;
	}

	public void setCodSegmento(int codSegmento) {
		this.codSegmento = codSegmento;
	}

	public String getSegmento() {
		return segmento;
	}

	public void setSegmento(String segmento) {
		this.segmento = segmento;
	}

	public int getCodMicrosegmento() {
		return codMicrosegmento;
	}

	public void setCodMicrosegmento(int codMicrosegmento) {
		this.codMicrosegmento = codMicrosegmento;
	}

	public String getMicrosegmento() {
		return microsegmento;
	}

	public void setMicrosegmento(String microsegmento) {
		this.microsegmento = microsegmento;
	}

	public int getPedido() {
		return pedido;
	}

	public void setPedido(int pedido) {
		this.pedido = pedido;
	}

	public int getPosPedido() {
		return posPedido;
	}

	public void setPosPedido(int posPedido) {
		this.posPedido = posPedido;
	}

	public int getPosPadre() {
		return posPadre;
	}

	public void setPosPadre(int posPadre) {
		this.posPadre = posPadre;
	}

	public String getClasePed() {
		return clasePed;
	}

	public void setClasePed(String clasePed) {
		this.clasePed = clasePed;
	}

	public String getTipodePedido() {
		return tipodePedido;
	}

	public void setTipodePedido(String tipodePedido) {
		this.tipodePedido = tipodePedido;
	}

	public int getDocMaterial() {
		return docMaterial;
	}

	public void setDocMaterial(int docMaterial) {
		this.docMaterial = docMaterial;
	}

	public int getOrdenDeF() {
		return ordenDeF;
	}

	public void setOrdenDeF(int ordenDeF) {
		this.ordenDeF = ordenDeF;
	}

	public int getCentro() {
		return centro;
	}

	public void setCentro(int centro) {
		this.centro = centro;
	}

	public int getAlmacen() {
		return almacen;
	}

	public void setAlmacen(int almacen) {
		this.almacen = almacen;
	}

	public String getDenAlmacen() {
		return denAlmacen;
	}

	public void setDenAlmacen(String denAlmacen) {
		this.denAlmacen = denAlmacen;
	}

	public String getZonaVenta() {
		return zonaVenta;
	}

	public void setZonaVenta(String zonaVenta) {
		this.zonaVenta = zonaVenta;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public int getCodEspecialista() {
		return codEspecialista;
	}

	public void setCodEspecialista(int codEspecialista) {
		this.codEspecialista = codEspecialista;
	}

	public int getCedEspecialista() {
		return cedEspecialista;
	}

	public void setCedEspecialista(int cedEspecialista) {
		this.cedEspecialista = cedEspecialista;
	}

	public String getEspecialista() {
		return especialista;
	}

	public void setEspecialista(String especialista) {
		this.especialista = especialista;
	}

	public int getCodVendedorInt() {
		return codVendedorInt;
	}

	public void setCodVendedorInt(int codVendedorInt) {
		this.codVendedorInt = codVendedorInt;
	}

	public int getCedVendedorInt() {
		return cedVendedorInt;
	}

	public void setCedVendedorInt(int cedVendedorInt) {
		this.cedVendedorInt = cedVendedorInt;
	}

	public String getVendedorInt() {
		return vendedorInt;
	}

	public void setVendedorInt(String vendedorInt) {
		this.vendedorInt = vendedorInt;
	}

	public int getCodMaterial() {
		return codMaterial;
	}

	public void setCodMaterial(int codMaterial) {
		this.codMaterial = codMaterial;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public int getCodMatOrg() {
		return CodMatOrg;
	}

	public void setCodMatOrg(int codMatOrg) {
		CodMatOrg = codMatOrg;
	}

	public String getMaterialOriginal() {
		return materialOriginal;
	}

	public void setMaterialOriginal(String materialOriginal) {
		this.materialOriginal = materialOriginal;
	}

	public BigDecimal getClValoracion() {
		return clValoracion;
	}

	public void setClValoracion(BigDecimal clValoracion) {
		this.clValoracion = clValoracion;
	}

	public String getDim1() {
		return dim1;
	}

	public void setDim1(String dim1) {
		this.dim1 = dim1;
	}

	public String getDim2() {
		return dim2;
	}

	public void setDim2(String dim2) {
		this.dim2 = dim2;
	}

	public String getDim3() {
		return dim3;
	}

	public void setDim3(String dim3) {
		this.dim3 = dim3;
	}

	public int getCodLinea() {
		return codLinea;
	}

	public void setCodLinea(int codLinea) {
		this.codLinea = codLinea;
	}

	public String getLinea() {
		return linea;
	}

	public void setLinea(String linea) {
		this.linea = linea;
	}

	public int getCebe() {
		return cebe;
	}

	public void setCebe(int cebe) {
		this.cebe = cebe;
	}

	public String getUmb() {
		return umb;
	}

	public void setUmb(String umb) {
		this.umb = umb;
	}

	public int getPesoNeto() {
		return pesoNeto;
	}

	public void setPesoNeto(int pesoNeto) {
		this.pesoNeto = pesoNeto;
	}

	public int getPesoNetoSub() {
		return pesoNetoSub;
	}

	public void setPesoNetoSub(int pesoNetoSub) {
		this.pesoNetoSub = pesoNetoSub;
	}

	public int getValorBruto() {
		return valorBruto;
	}

	public void setValorBruto(int valorBruto) {
		this.valorBruto = valorBruto;
	}

	public int getValorBrutoSub() {
		return valorBrutoSub;
	}

	public void setValorBrutoSub(int valorBrutoSub) {
		this.valorBrutoSub = valorBrutoSub;
	}

	public BigDecimal getDescValor() {
		return descValor;
	}

	public void setDescValor(BigDecimal descValor) {
		this.descValor = descValor;
	}

	public BigDecimal getDescValorSub() {
		return descValorSub;
	}

	public void setDescValorSub(BigDecimal descValorSub) {
		this.descValorSub = descValorSub;
	}

	public BigDecimal getDesc() {
		return desc;
	}

	public void setDesc(BigDecimal desc) {
		this.desc = desc;
	}

	public int getFlete() {
		return flete;
	}

	public void setFlete(int flete) {
		this.flete = flete;
	}

	public int getValorDev() {
		return valorDev;
	}

	public void setValorDev(int valorDev) {
		this.valorDev = valorDev;
	}

	public int getValorNeto() {
		return valorNeto;
	}

	public void setValorNeto(int valorNeto) {
		this.valorNeto = valorNeto;
	}
	
}	
