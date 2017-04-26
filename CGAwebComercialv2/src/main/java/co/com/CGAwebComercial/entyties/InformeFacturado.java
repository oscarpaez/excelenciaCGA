package co.com.CGAwebComercial.entyties;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;




@SuppressWarnings("serial")
@Entity
public class InformeFacturado implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column (scale = 2)
	private BigDecimal pptoKG;
	
	@Column (scale = 2)
	private BigDecimal realKG;
	
	@Column (scale = 2)
	private BigDecimal ejecKG;
	
	@Column (scale = 2)
	private BigDecimal pptoVentas;
	
	@Column (scale = 2)
	private BigDecimal realVetas;
	
	@Column (scale = 2)
	private BigDecimal ejecVentas;
	
	@Column (scale = 2)
	private BigDecimal pptoUtilidad;
	
	@Column (scale = 2)
	private BigDecimal realUtilidad;
	
	@Column (scale = 2)
	private BigDecimal rentabUtilidad;
	
	@Column (scale = 2)
	private BigDecimal pendPlanta;
	
	@Column (scale = 2)
	private BigDecimal pendNoFact;
	
	@Column (scale = 2)
	private BigDecimal pendMesSigui;
	
	@Column (scale = 2)
	private BigDecimal acumulado;
	
	@Temporal(TemporalType.DATE)
	private Date  fechaEntrada;
	
	@OneToOne
	private Sucursal sucursal;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getPptoKG() {
		return pptoKG;
	}

	public void setPptoKG(BigDecimal pptoKG) {
		this.pptoKG = pptoKG;
	}

	public BigDecimal getRealKG() {
		return realKG;
	}

	public void setRealKG(BigDecimal realKG) {
		this.realKG = realKG;
	}

	public BigDecimal getEjecKG() {
		return ejecKG;
	}

	public void setEjecKG(BigDecimal ejecKG) {
		this.ejecKG = ejecKG;
	}

	public BigDecimal getPptoVentas() {
		return pptoVentas;
	}

	public void setPptoVentas(BigDecimal pptoVentas) {
		this.pptoVentas = pptoVentas;
	}

	public BigDecimal getRealVetas() {
		return realVetas;
	}

	public void setRealVetas(BigDecimal realVetas) {
		this.realVetas = realVetas;
	}

	public BigDecimal getEjecVentas() {
		return ejecVentas;
	}

	public void setEjecVentas(BigDecimal ejecVentas) {
		this.ejecVentas = ejecVentas;
	}

	public BigDecimal getPptoUtilidad() {
		return pptoUtilidad;
	}

	public void setPptoUtilidad(BigDecimal pptoUtilidad) {
		this.pptoUtilidad = pptoUtilidad;
	}

	public BigDecimal getRealUtilidad() {
		return realUtilidad;
	}

	public void setRealUtilidad(BigDecimal realUtilidad) {
		this.realUtilidad = realUtilidad;
	}

	public BigDecimal getRentabUtilidad() {
		return rentabUtilidad;
	}

	public void setRentabUtilidad(BigDecimal rentabUtilidad) {
		this.rentabUtilidad = rentabUtilidad;
	}

	public BigDecimal getPendPlanta() {
		return pendPlanta;
	}

	public void setPendPlanta(BigDecimal pendPlanta) {
		this.pendPlanta = pendPlanta;
	}

	public BigDecimal getPendNoFact() {
		return pendNoFact;
	}

	public void setPendNoFact(BigDecimal pendNoFact) {
		this.pendNoFact = pendNoFact;
	}

	public BigDecimal getPendMesSigui() {
		return pendMesSigui;
	}

	public void setPendMesSigui(BigDecimal pendMesSigui) {
		this.pendMesSigui = pendMesSigui;
	}

	public BigDecimal getAcumulado() {
		return acumulado;
	}

	public void setAcumulado(BigDecimal acumulado) {
		this.acumulado = acumulado;
	}

	public Date getFechaEntrada() {
		return fechaEntrada;
	}

	public void setFechaEntrada(Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
		
}
