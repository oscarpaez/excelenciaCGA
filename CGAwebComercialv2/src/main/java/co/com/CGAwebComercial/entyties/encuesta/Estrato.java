package co.com.CGAwebComercial.entyties.encuesta;

import java.io.Serializable;
import javax.persistence.*;


@SuppressWarnings("serial")
@Entity
public class Estrato implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private int descripcion;

	public Estrato() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(int descripcion) {
		this.descripcion = descripcion;
	}

}