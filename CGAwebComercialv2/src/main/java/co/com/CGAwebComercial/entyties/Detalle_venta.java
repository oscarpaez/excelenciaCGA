package co.com.CGAwebComercial.entyties;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the `Detalle venta` database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Detalle_venta implements Serializable {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id_detalle;	

	
	private  int codGrpMat;

	
	private int codOficina;

	@Column(length= 20)
	private String cond_Pago;

	@Column(scale = 3)
	private BigDecimal costoTotal;

	private int factura;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha_Contable;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha_Ingreso;

	@Column(length= 50)
	private String grupo_Mat;

	private int pedido;

	@Column(length= 20)
	private String por_Reconocer;

	private int pos;

	@Column(length= 50)
	private String tipo_Cond_Pago;

	@Column(length= 20)
	private String tipo_Factura;

	@Column(scale = 3)
	private BigDecimal valorNeto;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Linea linea;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Funcionario funcionario;

	public int getId_detalle() {
		return id_detalle;
	}

	public void setId_detalle(int id_detalle) {
		this.id_detalle = id_detalle;
	}

	public int getCodGrpMat() {
		return codGrpMat;
	}

	public void setCodGrpMat(int codGrpMat) {
		this.codGrpMat = codGrpMat;
	}

	public int getCodOficina() {
		return codOficina;
	}

	public void setCodOficina(int codOficina) {
		this.codOficina = codOficina;
	}

	public String getCond_Pago() {
		return cond_Pago;
	}

	public void setCond_Pago(String cond_Pago) {
		this.cond_Pago = cond_Pago;
	}

	public BigDecimal getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(BigDecimal costoTotal) {
		this.costoTotal = costoTotal;
	}

	public int getFactura() {
		return factura;
	}

	public void setFactura(int factura) {
		this.factura = factura;
	}

	public Date getFecha_Contable() {
		return fecha_Contable;
	}

	public void setFecha_Contable(Date fecha_Contable) {
		this.fecha_Contable = fecha_Contable;
	}

	public String getGrupo_Mat() {
		return grupo_Mat;
	}

	public void setGrupo_Mat(String grupo_Mat) {
		this.grupo_Mat = grupo_Mat;
	}

	public int getPedido() {
		return pedido;
	}

	public void setPedido(int pedido) {
		this.pedido = pedido;
	}

	public String getPor_Reconocer() {
		return por_Reconocer;
	}

	public void setPor_Reconocer(String por_Reconocer) {
		this.por_Reconocer = por_Reconocer;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public String getTipo_Cond_Pago() {
		return tipo_Cond_Pago;
	}

	public void setTipo_Cond_Pago(String tipo_Cond_Pago) {
		this.tipo_Cond_Pago = tipo_Cond_Pago;
	}

	public String getTipo_Factura() {
		return tipo_Factura;
	}

	public void setTipo_Factura(String tipo_Factura) {
		this.tipo_Factura = tipo_Factura;
	}

	public BigDecimal getValorNeto() {
		return valorNeto;
	}

	public void setValorNeto(BigDecimal valorNeto) {
		this.valorNeto = valorNeto;
	}

	public Linea getLinea() {
		return linea;
	}

	public void setLinea(Linea linea) {
		this.linea = linea;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getFecha_Ingreso() {
		return fecha_Ingreso;
	}

	public void setFecha_Ingreso(Date fecha_Ingreso) {
		this.fecha_Ingreso = fecha_Ingreso;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

}