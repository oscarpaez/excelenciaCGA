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
import javax.persistence.Transient;


@SuppressWarnings("serial")
@Entity
public class Incidencia  implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(length = 50)
	private String cliente;
	
	private Long valorVenta;
	
	private Long Noferta;
	
	private int rentabilidad;
	
	private int probabilidadConNeg;
	
	@Column(length = 50)
	private String nombreCompetencia;
	
	private Long precioCompetencia;
	
	private int rentabilidadRequerida;
	
	@Column(length = 2)
	private String resultaNegocio;
	
	@Column(length = 250)
	private String necesidad;
	
	@Column(length = 50)
	private String canalesContacto;
	
	@Column(length = 10)
	private String zonaId;
	
	@Column(length = 30)
	private String idLineas;
	
	@Temporal(TemporalType.DATE)
	private Date fechaRegistro;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private CausaPerdidaVenta causaPerdida;
	
	@OneToOne
	@JoinColumn(nullable = false)	
	private RegularidadPerdidaVenta  regularidad;	
	
	@OneToOne
	@JoinColumn(nullable = false)
	private Linea linea;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private Funcionario funcionario;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private Ciudad ciudad;
	
	@Transient	
	private Zona_venta zona;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getNecesidad() {
		return necesidad;
	}

	public void setNecesidad(String necesidad) {
		this.necesidad = necesidad;
	}

	public Long getValorVenta() {
		return valorVenta;
	}

	public void setValorVenta(Long valorVenta) {
		this.valorVenta = valorVenta;
	}

	public CausaPerdidaVenta getCausaPerdida() {
		return causaPerdida;
	}

	public void setCausaPerdida(CausaPerdidaVenta causaPerdida) {
		this.causaPerdida = causaPerdida;
	}

	public RegularidadPerdidaVenta getRegularidad() {
		return regularidad;
	}

	public void setRegularidad(RegularidadPerdidaVenta regularidad) {
		this.regularidad = regularidad;
	}
	
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Linea getLinea() {
		return linea;
	}

	public void setLinea(Linea linea) {
		this.linea = linea;
	}

	public int getRentabilidad() {
		return rentabilidad;
	}

	public void setRentabilidad(int rentabilidad) {
		this.rentabilidad = rentabilidad;
	}

	public int getProbabilidadConNeg() {
		return probabilidadConNeg;
	}

	public void setProbabilidadConNeg(int probabilidadConNeg) {
		this.probabilidadConNeg = probabilidadConNeg;
	}

	public String getNombreCompetencia() {
		return nombreCompetencia;
	}

	public void setNombreCompetencia(String nombreCompetencia) {
		this.nombreCompetencia = nombreCompetencia;
	}

	public Long getPrecioCompetencia() {
		return precioCompetencia;
	}

	public void setPrecioCompetencia(Long precioCompetencia) {
		this.precioCompetencia = precioCompetencia;
	}

	public int getRentabilidadRequerida() {
		return rentabilidadRequerida;
	}

	public void setRentabilidadRequerida(int rentabilidadRequerida) {
		this.rentabilidadRequerida = rentabilidadRequerida;
	}

	public String getResultaNegocio() {
		return resultaNegocio;
	}

	public void setResultaNegocio(String resultaNegocio) {
		this.resultaNegocio = resultaNegocio;
	}

	public String getCanalesContacto() {
		return canalesContacto;
	}

	public void setCanalesContacto(String canalesContacto) {
		this.canalesContacto = canalesContacto;
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

	public String getZonaId() {
		return zonaId;
	}

	public void setZonaId(String zonaId) {
		this.zonaId = zonaId;
	}

	public Zona_venta getZona() {
		return zona;
	}

	public void setZona(Zona_venta zona) {
		this.zona = zona;
	}

	public String getIdLineas() {
		return idLineas;
	}

	public void setIdLineas(String idLineas) {
		this.idLineas = idLineas;
	}
	public Long getNoferta() {
		return Noferta;
	}
	public void setNoferta(Long noferta) {
		Noferta = noferta;
	}
}
