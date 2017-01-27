package co.com.CGAwebComercial.entyties;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class SubActividades implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(length = 50)
	private String nombre;
	
	@Column(length = 50)
	private String avance;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Actividades activida;

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

	public String getAvance() {
		return avance;
	}

	public void setAvance(String avance) {
		this.avance = avance;
	}

	public Actividades getActivida() {
		return activida;
	}

	public void setActivida(Actividades activida) {
		this.activida = activida;
	}
}
