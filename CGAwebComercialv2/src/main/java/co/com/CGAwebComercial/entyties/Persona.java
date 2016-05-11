package co.com.CGAwebComercial.entyties;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the Persona database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Persona implements Serializable {
	

	@Id
	private int cedula;
	
	
	@Column(length=50)
	private String email;
	
	@Column(length=50)
	private String nombre;

	@Column(length=7)
	private String telefono;
	
	@Column(length=10)
	private String celular;
	
	

	public int getCedula() {
		return cedula;
	}

	public void setCedula(int cedula) {
		this.cedula = cedula;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

}