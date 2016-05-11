package co.com.CGAwebComercial.entyties;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@SuppressWarnings("serial")
@Entity
public class Comision implements Serializable{
	
	@Id
	private int idComision;
	
	@Column(length = 50, nullable = false)
	private String nombre;
	
	@Column(scale = 2)
	private BigDecimal valorBaseVenta;
	
	@Column(scale = 2)
	private BigDecimal umbralVenta;
	
	@Column(scale = 2)
	private BigDecimal valorBaseRecaudo;
	
	@Column(scale = 2)
	private BigDecimal umbralRecaudo;

	public int getIdComision() {
		return idComision;
	}

	public void setIdComision(int idComision) {
		this.idComision = idComision;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getValorBaseVenta() {
		return valorBaseVenta;
	}

	public void setValorBaseVenta(BigDecimal valorBaseVenta) {
		this.valorBaseVenta = valorBaseVenta;
	}

	public BigDecimal getUmbralVenta() {
		return umbralVenta;
	}

	public void setUmbralVenta(BigDecimal umbralVenta) {
		this.umbralVenta = umbralVenta;
	}

	public BigDecimal getValorBaseRecaudo() {
		return valorBaseRecaudo;
	}

	public void setValorBaseRecaudo(BigDecimal valorBaseRecaudo) {
		this.valorBaseRecaudo = valorBaseRecaudo;
	}

	public BigDecimal getUmbralRecaudo() {
		return umbralRecaudo;
	}

	public void setUmbralRecaudo(BigDecimal umbralRecaudo) {
		this.umbralRecaudo = umbralRecaudo;
	}

	
	
}
