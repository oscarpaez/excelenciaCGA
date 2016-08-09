package co.com.CGAwebComercial.entyties;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


@SuppressWarnings("serial")
@Entity
public class Esquemas implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(scale = 2)
	private BigDecimal umbralRecaudo;
	
	@Column(scale = 2)
	private BigDecimal umbralComision;
	
	@Column(scale = 2)
	private BigDecimal ventaLinea;
	
	@Column(scale = 2)
	private BigDecimal ventaPais;
	
	@Column(scale = 2)
	private BigDecimal mixLinea;
	
	@Column(scale = 2)
	private BigDecimal zonasCargo;
	
	@Column(scale = 2)
	private BigDecimal recaudoCartera;
	
	@OneToOne
	@JoinColumn
	private Perfil perfil;

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getUmbralRecaudo() {
		return umbralRecaudo;
	}

	public void setUmbralRecaudo(BigDecimal umbralRecaudo) {
		this.umbralRecaudo = umbralRecaudo;
	}

	public BigDecimal getUmbralComision() {
		return umbralComision;
	}

	public void setUmbralComision(BigDecimal umbralComision) {
		this.umbralComision = umbralComision;
	}

	public BigDecimal getVentaLinea() {
		return ventaLinea;
	}

	public void setVentaLinea(BigDecimal ventaLinea) {
		this.ventaLinea = ventaLinea;
	}

	public BigDecimal getVentaPais() {
		return ventaPais;
	}

	public void setVentaPais(BigDecimal ventaPais) {
		this.ventaPais = ventaPais;
	}

	public BigDecimal getMixLinea() {
		return mixLinea;
	}

	public void setMixLinea(BigDecimal mixLinea) {
		this.mixLinea = mixLinea;
	}

	public BigDecimal getZonasCargo() {
		return zonasCargo;
	}

	public void setZonasCargo(BigDecimal zonasCargo) {
		this.zonasCargo = zonasCargo;
	}

	public BigDecimal getRecaudoCartera() {
		return recaudoCartera;
	}

	public void setRecaudoCartera(BigDecimal recaudoCartera) {
		this.recaudoCartera = recaudoCartera;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
}
