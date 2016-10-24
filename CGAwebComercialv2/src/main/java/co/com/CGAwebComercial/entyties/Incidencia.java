package co.com.CGAwebComercial.entyties;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@SuppressWarnings("serial")
@Entity
public class Incidencia  implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(length = 50)
	private String cliente;
	
	private Long valorVenta;
	
	@Column(length = 250)
	private String necesidad;
	
	@Temporal(TemporalType.DATE)
	private Date fechaRegistro;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private CausaPerdidaVenta causaPerdida;
	
	@OneToOne
	@JoinColumn(nullable = false)	
	private RegularidadPerdidaVenta  regularidad;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private Zona_venta zona;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private Linea linea;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getNecesidad() {
		return necesidad;
	}

	public void setNecesidad(String necesidad) {
		this.necesidad = necesidad;
	}

	public Long getValorVenta() {
		return valorVenta;
	}

	public void setValorVenta(Long valorVenta) {
		this.valorVenta = valorVenta;
	}

	public CausaPerdidaVenta getCausaPerdida() {
		return causaPerdida;
	}

	public void setCausaPerdida(CausaPerdidaVenta causaPerdida) {
		this.causaPerdida = causaPerdida;
	}

	public RegularidadPerdidaVenta getRegularidad() {
		return regularidad;
	}

	public void setRegularidad(RegularidadPerdidaVenta regularidad) {
		this.regularidad = regularidad;
	}

	public Zona_venta getZona() {
		return zona;
	}

	public void setZona(Zona_venta zona) {
		this.zona = zona;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Linea getLinea() {
		return linea;
	}

	public void setLinea(Linea linea) {
		this.linea = linea;
	}
	
}
