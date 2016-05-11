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
public class Ajuste implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Temporal(TemporalType.DATE)
	private Date fechaAjuste;
	
	private int ejercicio;
	
	private int periodo;
	
	private int codSap;
	

	@Column(length = 50)
	private String nombre;

	@Column(length = 50)
	private String concepto;
	

	@Column(length = 255)
	private String nota;
	
	private int facturapedido;
	
	private int valorajuste;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFechaAjuste() {
		
		return fechaAjuste;
	}

	public void setFechaAjuste(Date fechaAjuste) {
		this.fechaAjuste = fechaAjuste;
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

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public int getFacturapedido() {
		return facturapedido;
	}

	public void setFacturapedido(int facturapedido) {
		this.facturapedido = facturapedido;
	}

	public int getValorajuste() {
		return valorajuste;
	}

	public void setValorajuste(int valorajuste) {
		this.valorajuste = valorajuste;
	}
	
}
