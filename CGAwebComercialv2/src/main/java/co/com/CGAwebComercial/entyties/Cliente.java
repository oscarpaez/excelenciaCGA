package co.com.CGAwebComercial.entyties;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the cliente database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Cliente implements Serializable {


	@Id
	private int id_cliente;

	@Column (length = 50)
	private String descripcion;
	
	
	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
}