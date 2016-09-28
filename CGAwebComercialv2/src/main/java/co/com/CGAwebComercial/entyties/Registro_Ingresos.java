package co.com.CGAwebComercial.entyties;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@SuppressWarnings("serial")
@Entity
public class Registro_Ingresos implements Serializable  {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id_Registro;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private Persona  persona;
	
	@Temporal(TemporalType.DATE)
	private Date fechaIngreso;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date horaIngreso;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private Funcionario funcionario;

	public int getId_Registro() {
		return id_Registro;
	}

	public void setId_Registro(int id_Registro) {
		this.id_Registro = id_Registro;
	}

	
	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Date getHoraIngreso() {
		return horaIngreso;
	}

	public void setHoraIngreso(Date horaIngreso) {
		this.horaIngreso = horaIngreso;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
}
