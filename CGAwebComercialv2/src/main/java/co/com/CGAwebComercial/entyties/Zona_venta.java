package co.com.CGAwebComercial.entyties;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the `Zona ventas` database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Zona_venta implements Serializable {
	
	@Id
	@Column(length = 6)	
	private String id_zona_venta;

	@Column(length = 20)
	private String nombre;
	
	@ManyToOne
	@JoinColumn
	private Ciudad ciudad;
	
	@ManyToOne
	@JoinColumn
	private Funcionario funcionario;
	
	
	public String getId_zona_venta() {
		return id_zona_venta;
	}

	public void setId_zona_venta(String id_zona_venta) {
		this.id_zona_venta = id_zona_venta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	
	
}