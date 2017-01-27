package co.com.CGAwebComercial.entyties.encuesta;

import java.io.Serializable;
import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
public class Area implements Serializable {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String descripcion;

	public Area() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}