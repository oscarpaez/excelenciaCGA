package co.com.CGAwebComercial.entyties;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the usuarios database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Usuario implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column (length = 32, nullable = false)
	private String clave;
	
	@Column (length = 50, nullable = false)
	private String usuario;
	
	@Transient
	private String claveCritografia;
	
	@Column (length = 50)
	private int estado;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha_Creacion;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha_Modificacion;

	@OneToOne
	@JoinColumn(nullable = false)	
	private Persona persona;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private Perfil perfil;

	@Column (length = 10)
	private int id_Usuario_Creador;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Date getFecha_Creacion() {
		return fecha_Creacion;
	}

	public void setFecha_Creacion(Date fecha_Creacion) {
		this.fecha_Creacion = fecha_Creacion;
	}

	public Date getFecha_Modificacion() {
		return fecha_Modificacion;
	}

	public void setFecha_Modificacion(Date fecha_Modificacion) {
		this.fecha_Modificacion = fecha_Modificacion;
	}

	public int getId_Usuario_Creador() {
		return id_Usuario_Creador;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public void setId_Usuario_Creador(int id_Usuario_Creador) {
		this.id_Usuario_Creador = id_Usuario_Creador;
	}

	public String getClaveCritografia() {
		return claveCritografia;
	}

	public void setClaveCritografia(String claveCritografia) {
		this.claveCritografia = claveCritografia;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	
}