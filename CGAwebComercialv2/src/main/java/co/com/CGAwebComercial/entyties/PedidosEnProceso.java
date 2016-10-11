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
public class PedidosEnProceso implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private long docVenta;
	
	private int oficina;
	
	private int codCliente;
	
	@Column (length = 255)
	private String cliente;
	
	private int codEspecialista;
	
	@Column (length = 120)
	private String especialista;
	
	private int codInterno;
	
	@Column(length =120)
	private String interno;
	
	private int codMaterial;
	
	@Column(length =120)
	private String material;
	
	
	private int ctdPed;
	
	@Column (scale = 2)
	private BigDecimal valorNeto;
	
	@Column (scale = 2)
	private BigDecimal rentabilidad;
	
	@Column (scale = 2)
	private BigDecimal rentabilidadX;
	
	@Column(length =120)
	private String status;
	
	@Column(length =120)
	private String linea;
	
	@Temporal(TemporalType.DATE)
	private Date fechaCreacion;
	
	@Temporal(TemporalType.DATE)
	private Date fechaEntrega;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getDocVenta() {
		return docVenta;
	}

	public void setDocVenta(long docVenta) {
		this.docVenta = docVenta;
	}

	public int getOficina() {
		return oficina;
	}

	public void setOficina(int oficina) {
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

	public int getCodEspecialista() {
		return codEspecialista;
	}

	public void setCodEspecialista(int codEspecialista) {
		this.codEspecialista = codEspecialista;
	}

	public String getEspecialista() {
		return especialista;
	}

	public void setEspecialista(String especialista) {
		this.especialista = especialista;
	}

	public int getCodInterno() {
		return codInterno;
	}

	public void setCodInterno(int codInterno) {
		this.codInterno = codInterno;
	}

	public String getInterno() {
		return interno;
	}

	public void setInterno(String interno) {
		this.interno = interno;
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

	public int getCtdPed() {
		return ctdPed;
	}

	public void setCtdPed(int ctdPed) {
		this.ctdPed = ctdPed;
	}

	public BigDecimal getValorNeto() {
		return valorNeto;
	}

	public void setValorNeto(BigDecimal valorNeto) {
		this.valorNeto = valorNeto;
	}

	public BigDecimal getRentabilidad() {
		return rentabilidad;
	}

	public void setRentabilidad(BigDecimal rentabilidad) {
		this.rentabilidad = rentabilidad;
	}

	public BigDecimal getRentabilidadX() {
		return rentabilidadX;
	}

	public void setRentabilidadX(BigDecimal rentabilidadX) {
		this.rentabilidadX = rentabilidadX;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLinea() {
		return linea;
	}

	public void setLinea(String linea) {
		this.linea = linea;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}
}