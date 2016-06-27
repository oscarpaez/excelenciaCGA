package co.com.CGAwebComercial.entyties;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class PromedioVenta implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idPromedioVentas;
	
	private int year;
	
	@Column(length = 10)
	private String mes;
	
	private int meta;
	
	private int metaD;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Funcionario funcionario;
	
	@Transient
	private int valorPromedio;

	public int getIdPromedioVentas() {
		return idPromedioVentas;
	}

	public void setIdPromedioVentas(int idPromedioVentas) {
		this.idPromedioVentas = idPromedioVentas;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public int getMeta() {
		return meta;
	}

	public void setMeta(int meta) {
		this.meta = meta;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public int getValorPromedio() {
		return valorPromedio;
	}

	public void setValorPromedio(int valorPromedio) {
		this.valorPromedio = valorPromedio;
	}

	public int getMetaD() {
		return metaD;
	}

	public void setMetaD(int metaD) {
		this.metaD = metaD;
	} 
}
