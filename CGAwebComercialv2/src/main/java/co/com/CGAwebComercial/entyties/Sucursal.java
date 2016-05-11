package co.com.CGAwebComercial.entyties;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the sucursal database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Sucursal implements Serializable {
	

	@Id
	private int id;

	@Column (length = 50)
	private String nombre;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Ciudad ciudad;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	

}