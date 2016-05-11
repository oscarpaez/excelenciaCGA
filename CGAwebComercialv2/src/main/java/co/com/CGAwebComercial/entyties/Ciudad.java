package co.com.CGAwebComercial.entyties;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the ciudad database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Ciudad implements Serializable {
	
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