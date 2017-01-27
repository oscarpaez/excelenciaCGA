package co.com.CGAwebComercial.entyties.encuesta;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
public class Hijo implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Temporal(TemporalType.DATE)
	private Date fechanacimiento;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Persona persona;

	private String nombre;

	public Hijo() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFechanacimiento() {
		return fechanacimiento;
	}

	public void setFechanacimiento(Date fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}