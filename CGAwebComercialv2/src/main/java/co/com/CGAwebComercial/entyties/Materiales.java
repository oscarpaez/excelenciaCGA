package co.com.CGAwebComercial.entyties;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.primefaces.model.StreamedContent;


@SuppressWarnings("serial")
@Entity
public class Materiales implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private int codigo;
	
	@Column (length = 255)
	private String descripcion;
	
	@Column(scale = 2)
	private BigDecimal espesor;
	
	@Column(scale = 2)
	private BigDecimal ancho;
	
	@Column(scale = 2)
	private BigDecimal largo;
	
	private int unidad;
	
	@Column(scale = 2)
	private BigDecimal teorico;
	
	@ManyToOne
	private Sucursal sucursal;
	
	@Temporal(TemporalType.DATE)
	private Date fechaModificacion;
	
	@Column (length = 255)
	private String imagen;
	
	@Transient
	private String nombreImagen;
	
	@Transient
	private StreamedContent ruta;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getEspesor() {
		return espesor;
	}

	public void setEspesor(BigDecimal espesor) {
		this.espesor = espesor;
	}

	public BigDecimal getAncho() {
		return ancho;
	}

	public void setAncho(BigDecimal ancho) {
		this.ancho = ancho;
	}

	public BigDecimal getLargo() {
		return largo;
	}

	public void setLargo(BigDecimal largo) {
		this.largo = largo;
	}

	public int getUnidad() {
		return unidad;
	}

	public void setUnidad(int unidad) {
		this.unidad = unidad;
	}

	public BigDecimal getTeorico() {
		return teorico;
	}

	public void setTeorico(BigDecimal teorico) {
		this.teorico = teorico;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getNombreImagen() {
		return nombreImagen;
	}

	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}

	public StreamedContent getRuta() {
		return ruta;
	}

	public void setRuta(StreamedContent ruta) {
		this.ruta = ruta;
	}	
}
