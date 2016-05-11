package co.com.CGAwebComercial.entyties;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the funcionario database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Funcionario implements Serializable {
	

	@Id
	private int id_funcionario;
	
	
	@Column (length = 50)
	private String descripcion;

	private int estado;

	@OneToOne
	@JoinColumn(nullable = false)	
	private Persona persona;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Comision comision;

	public int getId_funcionario() {
		return id_funcionario;
	}

	public void setId_funcionario(int id_funcionario) {
		this.id_funcionario = id_funcionario;
	}

	

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Comision getComision() {
		return comision;
	}

	public void setComision(Comision comision) {
		this.comision = comision;
	}	
	
	

}