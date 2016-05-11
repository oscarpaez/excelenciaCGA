package co.com.CGAwebComercial.entyties;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@SuppressWarnings("serial")
@Entity
public class Gerentes implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private int cedula;
	
	private int codigoSap;
	
	@Column(length = 50)
	private String nombre;
	
	@Column(length = 50)
	private String cargo;
		
	private int codSucursal;
	
	@Column(length = 50)
	private String sucursal;
	
	private int basecomision;
	
	private int UtilidadOperacionalPais;
	
	private int UtilidadOperacionalUEN;
	
	private int UtilidadOperacionalSucursal;
	
	private int UtilidadBrutaLineaPais;
	
	private int UtilidadBrutaSucursal;
	
	private int RentabilidadMixdelineasSucursal;
	
	private int RentabilidadporlineasZonacargo;
	
	private int RecaudodeCarteraSucursal;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCedula() {
		return cedula;
	}

	public void setCedula(int cedula) {
		this.cedula = cedula;
	}

	public int getCodigoSap() {
		return codigoSap;
	}

	public void setCodigoSap(int codigoSap) {
		this.codigoSap = codigoSap;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public int getCodSucursal() {
		return codSucursal;
	}

	public void setCodSucursal(int codSucursal) {
		this.codSucursal = codSucursal;
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public int getBasecomision() {
		return basecomision;
	}

	public void setBasecomision(int basecomision) {
		this.basecomision = basecomision;
	}

	public int getUtilidadOperacionalPais() {
		return UtilidadOperacionalPais;
	}

	public void setUtilidadOperacionalPais(int utilidadOperacionalPais) {
		UtilidadOperacionalPais = utilidadOperacionalPais;
	}

	public int getUtilidadOperacionalUEN() {
		return UtilidadOperacionalUEN;
	}

	public void setUtilidadOperacionalUEN(int utilidadOperacionalUEN) {
		UtilidadOperacionalUEN = utilidadOperacionalUEN;
	}

	public int getUtilidadOperacionalSucursal() {
		return UtilidadOperacionalSucursal;
	}

	public void setUtilidadOperacionalSucursal(int utilidadOperacionalSucursal) {
		UtilidadOperacionalSucursal = utilidadOperacionalSucursal;
	}

	public int getUtilidadBrutaLineaPais() {
		return UtilidadBrutaLineaPais;
	}

	public void setUtilidadBrutaLineaPais(int utilidadBrutaLineaPais) {
		UtilidadBrutaLineaPais = utilidadBrutaLineaPais;
	}

	public int getRentabilidadMixdelineasSucursal() {
		return RentabilidadMixdelineasSucursal;
	}

	public void setRentabilidadMixdelineasSucursal(int rentabilidadMixdelineasSucursal) {
		RentabilidadMixdelineasSucursal = rentabilidadMixdelineasSucursal;
	}

	public int getRentabilidadporlineasZonacargo() {
		return RentabilidadporlineasZonacargo;
	}

	public void setRentabilidadporlineasZonacargo(int rentabilidadporlineasZonacargo) {
		RentabilidadporlineasZonacargo = rentabilidadporlineasZonacargo;
	}

	public int getRecaudodeCarteraSucursal() {
		return RecaudodeCarteraSucursal;
	}

	public void setRecaudodeCarteraSucursal(int recaudodeCarteraSucursal) {
		RecaudodeCarteraSucursal = recaudodeCarteraSucursal;
	}

	public int getUtilidadBrutaSucursal() {
		return UtilidadBrutaSucursal;
	}

	public void setUtilidadBrutaSucursal(int utilidadBrutaSucursal) {
		UtilidadBrutaSucursal = utilidadBrutaSucursal;
	}
	
}
