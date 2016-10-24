package co.com.CGAwebComercial.entyties;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
public class Llamadas implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(length = 100)
	private String tipoLlamada;
	
	@Temporal(TemporalType.DATE)
	private Date  fecha;
	
	@Temporal(TemporalType.TIME)
	private Date  hora;
	
	private int extension;
	
	@Column(length = 5)
	private String Ln;
	
	@Column(length = 15)
	private String numeroTelefon;
	
	@Column(length = 5)
	private String timbre ;
	
	@Temporal(TemporalType.TIME)
	private Date  duracion;
	
	private int centra;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipoLlamada() {
		return tipoLlamada;
	}

	public void setTipoLlamada(String tipoLlamada) {
		this.tipoLlamada = tipoLlamada;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public int getExtension() {
		return extension;
	}

	public void setExtension(int extension) {
		this.extension = extension;
	}

	public String getLn() {
		return Ln;
	}

	public void setLn(String ln) {
		Ln = ln;
	}

	public String getNumeroTelefon() {
		return numeroTelefon;
	}

	public void setNumeroTelefon(String numeroTelefon) {
		this.numeroTelefon = numeroTelefon;
	}	

	public String getTimbre() {
		return timbre;
	}

	public void setTimbre(String timbre) {
		this.timbre = timbre;
	}

	public Date getDuracion() {
		return duracion;
	}

	public void setDuracion(Date duracion) {
		this.duracion = duracion;
	}

	public int getCentra() {
		return centra;
	}

	public void setCentra(int centra) {
		this.centra = centra;
	}	
}
