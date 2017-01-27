package co.com.CGAwebComercial.entyties.encuesta;

import java.io.Serializable;
import javax.persistence.*;


@SuppressWarnings("serial")
@Entity
public class Valorsalud implements Serializable {
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@OneToOne
	@JoinColumn(nullable = false)
	private Persona persona;

	@OneToOne
	@JoinColumn(nullable = false)
	private Salud salud;

	@OneToOne
	@JoinColumn(nullable = false)
	private Tiposatisfaccion satisfaccion;

	public Valorsalud() {
	
	}

	public int getId() {
		return id;
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

	public Salud getSalud() {
		return salud;
	}

	public void setSalud(Salud salud) {
		this.salud = salud;
	}

	public Tiposatisfaccion getSatisfaccion() {
		return satisfaccion;
	}

	public void setSatisfaccion(Tiposatisfaccion satisfaccion) {
		this.satisfaccion = satisfaccion;
	}
}