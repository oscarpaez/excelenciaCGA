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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id_cliente;
	
	private int id;

	@Column (length = 50)
	private String descripcion;

	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}