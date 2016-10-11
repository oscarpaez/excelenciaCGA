package co.com.CGAwebComercial.entyties;

import java.io.Serializable;
import java.math.BigDecimal;
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
public class PedidosProyectados implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private Long valorPedido;
	
	@Column(scale = 2)
	private BigDecimal rentabilidad;
	
	private int dim1;
	
	private int dim2;
	
	private int dim3;
	
	private int cantidad;
	
	@Column(length = 512)
	private String observacion;
	
	@Column(length = 2)
	private String un;
	
	@Column(length = 10)
	private String moneda;
	
	@Column(length = 2)
	private String propuesto;
	
	@Temporal(TemporalType.DATE)
	private Date fechaEntrega;
	
	@Temporal(TemporalType.DATE)
	private Date fechaInicio;
	
	@OneToOne
	@JoinColumn(nullable = false)	
	private Funcionario funcionario;
	
	@OneToOne
	@JoinColumn(nullable = false)	
	private Cliente cliente;
	
	@OneToOne
	@JoinColumn(nullable = false)	
	private Linea linea;
	
	@OneToOne
	@JoinColumn(nullable = false)	
	private Material material;
	
	@OneToOne
	@JoinColumn(nullable = false)	
	private EstadoPedidosProyectados estado;
	
	@OneToOne
	@JoinColumn(nullable = false)	
	private Ciudad ciudad;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getValorPedido() {
		return valorPedido;
	}

	public void setValorPedido(Long valorPedido) {
		this.valorPedido = valorPedido;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Linea getLinea() {
		return linea;
	}

	public void setLinea(Linea linea) {
		this.linea = linea;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public int getDim1() {
		return dim1;
	}

	public void setDim1(int dim1) {
		this.dim1 = dim1;
	}

	public int getDim2() {
		return dim2;
	}

	public void setDim2(int dim2) {
		this.dim2 = dim2;
	}

	public int getDim3() {
		return dim3;
	}

	public void setDim3(int dim3) {
		this.dim3 = dim3;
	}

	public EstadoPedidosProyectados getEstado() {
		return estado;
	}

	public void setEstado(EstadoPedidosProyectados estado) {
		this.estado = estado;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	public BigDecimal getRentabilidad() {
		return rentabilidad;
	}

	public void setRentabilidad(BigDecimal rentabilidad) {
		this.rentabilidad = rentabilidad;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getUn() {
		return un;
	}

	public void setUn(String un) {
		this.un = un;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getPropuesto() {
		return propuesto;
	}

	public void setPropuesto(String propuesto) {
		this.propuesto = propuesto;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
}
