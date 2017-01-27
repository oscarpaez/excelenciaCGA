package co.com.CGAwebComercial.entyties.encuesta;

import java.io.Serializable;
import javax.persistence.*;


@SuppressWarnings("serial")
@Entity
public class Dotacion implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;	

	@OneToOne
	@JoinColumn(nullable = false)
	private Persona persona;

	@OneToOne
	@JoinColumn(nullable = false)
	private Talla talla;

	@OneToOne
	@JoinColumn(nullable = false)
	private Tipodotacion Tipodotacion;

	public Dotacion() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Talla getTalla() {
		return talla;
	}

	public void setTalla(Talla talla) {
		this.talla = talla;
	}

	public Tipodotacion getTipodotacion() {
		return Tipodotacion;
	}

	public void setTipodotacion(Tipodotacion tipodotacion) {
		Tipodotacion = tipodotacion;
	}	
}