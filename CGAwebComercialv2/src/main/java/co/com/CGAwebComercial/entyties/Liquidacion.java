package co.com.CGAwebComercial.entyties;

import java.io.Serializable;
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
public class Liquidacion implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Temporal(TemporalType.DATE)
	private Date fechaLiquidacion;
	
	private int ejercicio;
	
	private int periodo;
	
	private int codSap;
	

	@Column(length = 50)
	private String nombre;

	@Column(length = 50)
	private String concepto;
	
	private int valorAjuste;
	
	private int valorComision;
	
	private int valorTotal;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFechaLiquidacion() {
		
		return fechaLiquidacion;
	}

	public void setFechaLiquidacion(Date fechaLiquidacion) {
		
		this.fechaLiquidacion = fechaLiquidacion;
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

	public int getCodSap() {
		return codSap;
	}

	public void setCodSap(int codSap) {
		this.codSap = codSap;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public int getValorAjuste() {
		return valorAjuste;
	}

	public void setValorAjuste(int valorAjuste) {
		this.valorAjuste = valorAjuste;
	}

	public int getValorComision() {
		return valorComision;
	}

	public void setValorComision(int valorComision) {
		this.valorComision = valorComision;
	}

	public int getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(int valorTotal) {
		this.valorTotal = valorTotal;
	}
}
