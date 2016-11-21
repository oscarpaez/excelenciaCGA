package co.com.CGAwebComercial.entyties;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
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
public class ValorProyecto implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	
	private long valorMes;
	
	private long valorTotal;
	
	private long nOferta;
	
	@Column(length =255)
	private String nombreProyecto;
	
	@Temporal(TemporalType.DATE)
	private Date fechaRegistro;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private Funcionario funcionario;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private Ciudad ciudad;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getValorMes() {
		return valorMes;
	}

	public void setValorMes(long valorMes) {
		this.valorMes = valorMes;
	}

	public long getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(long valorTotal) {
		this.valorTotal = valorTotal;
	}

	public long getnOferta() {
		return nOferta;
	}

	public void setnOferta(long nOferta) {
		this.nOferta = nOferta;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	public String getNombreProyecto() {
		return nombreProyecto;
	}

	public void setNombreProyecto(String nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
	}	
}
