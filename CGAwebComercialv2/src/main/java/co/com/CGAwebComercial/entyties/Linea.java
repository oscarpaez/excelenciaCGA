package co.com.CGAwebComercial.entyties;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the linea database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Linea implements Serializable {
	

	@Id
	private int id;
	
	@Column (length = 50)
	private String nombre;

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
	
	

	

}