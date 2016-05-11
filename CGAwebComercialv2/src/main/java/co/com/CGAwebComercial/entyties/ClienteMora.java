package co.com.CGAwebComercial.entyties;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@SuppressWarnings("serial")
@Entity
public class ClienteMora  implements Serializable{
	
	@Id
	private int id;
	
	@Column(length = 50)
	private String nombre;
	
	@Temporal(TemporalType.DATE)
	private Date fechacorte;

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

	public Date getFechacorte() {
		return fechacorte;
	}

	public void setFechacorte(Date fechacorte) {
		this.fechacorte = fechacorte;
	}
}
