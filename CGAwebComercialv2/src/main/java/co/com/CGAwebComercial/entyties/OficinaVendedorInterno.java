package co.com.CGAwebComercial.entyties;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@SuppressWarnings("serial")
@Entity
public class OficinaVendedorInterno implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private int oficinadeventas;
	
	@Column(length = 50)
	private String nomoficina;
	
	private int codigosap;
	
	@Column(length = 50)
	private String asesor;
	
	private int cedula;
	
	private int tip;
	
	private int estado;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOficinadeventas() {
		return oficinadeventas;
	}

	public void setOficinadeventas(int oficinadeventas) {
		this.oficinadeventas = oficinadeventas;
	}

	public String getNomoficina() {
		return nomoficina;
	}

	public void setNomoficina(String nomoficina) {
		this.nomoficina = nomoficina;
	}

	public int getCodigosap() {
		return codigosap;
	}

	public void setCodigosap(int codigosap) {
		this.codigosap = codigosap;
	}

	public String getAsesor() {
		return asesor;
	}

	public void setAsesor(String asesor) {
		this.asesor = asesor;
	}

	public int getCedula() {
		return cedula;
	}

	public void setCedula(int cedula) {
		this.cedula = cedula;
	}

	public int getTip() {
		return tip;
	}

	public void setTip(int tip) {
		this.tip = tip;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}
	
}
