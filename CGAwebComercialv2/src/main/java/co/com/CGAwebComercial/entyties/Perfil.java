package co.com.CGAwebComercial.entyties;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@SuppressWarnings("serial")
@Entity
public class Perfil implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column (length =10 , nullable = false)
	private String descripcion;
	
	@Column (length =10 )
	private String comercial;
	
	@Column (length =10 )
	private String costo;
	
	@Column (length =10 )
	private String compra;
	
	@Column (length =10 )
	private String logistica;
	
	@Column (length =10 )
	private String gastos;
	
	@Column (length =10 )
	private String gh;
	
	@Column (length =10 )
	private String produccion;
	
	@Column (length =10 )
	private String financiera;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getComercial() {
		return comercial;
	}

	public void setComercial(String comercial) {
		this.comercial = comercial;
	}

	public String getCosto() {
		return costo;
	}

	public void setCosto(String costo) {
		this.costo = costo;
	}

	public String getCompra() {
		return compra;
	}

	public void setCompra(String compra) {
		this.compra = compra;
	}

	public String getLogistica() {
		return logistica;
	}

	public void setLogistica(String logistica) {
		this.logistica = logistica;
	}

	public String getGastos() {
		return gastos;
	}

	public void setGastos(String gastos) {
		this.gastos = gastos;
	}

	public String getGh() {
		return gh;
	}

	public void setGh(String gh) {
		this.gh = gh;
	}

	public String getProduccion() {
		return produccion;
	}

	public void setProduccion(String produccion) {
		this.produccion = produccion;
	}

	public String getFinanciera() {
		return financiera;
	}

	public void setFinanciera(String financiera) {
		this.financiera = financiera;
	}
}
