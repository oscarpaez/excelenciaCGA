package co.com.CGAwebComercial.entyties;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@SuppressWarnings("serial")
@Entity
public class RegistroHorasE implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private int idOperario;
	
	@Temporal(TemporalType.DATE)
	private Date  fechaCreacion;
	
	@Temporal(TemporalType.DATE)
	private Date  fechaSolicitud;
	
	private int nPedido;
	
	private int poscicion;
	
	private int nOrden;	
	
	@OneToOne
	private TipoHoraE tipoH;
	
	@OneToOne
	private PuestoTrabajo puestoT;
	
	@OneToOne
	private MotivoHorasE motivoH;	
	
	@OneToOne
	private ProcesoHE procesoH;
	
	@OneToOne
	private Sucursal sucursal;
	
	@OneToOne
	private EstadoHE estadoH; 
	
	@OneToOne
	private Usuario usuario;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdOperario() {
		return idOperario;
	}

	public void setIdOperario(int idOperario) {
		this.idOperario = idOperario;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public int getnPedido() {
		return nPedido;
	}

	public void setnPedido(int nPedido) {
		this.nPedido = nPedido;
	}

	public int getPoscicion() {
		return poscicion;
	}

	public void setPoscicion(int poscicion) {
		this.poscicion = poscicion;
	}

	public int getnOrden() {
		return nOrden;
	}

	public void setnOrden(int nOrden) {
		this.nOrden = nOrden;
	}

	public TipoHoraE getTipoH() {
		return tipoH;
	}

	public void setTipoH(TipoHoraE tipoH) {
		this.tipoH = tipoH;
	}

	public PuestoTrabajo getPuestoT() {
		return puestoT;
	}

	public void setPuestoT(PuestoTrabajo puestoT) {
		this.puestoT = puestoT;
	}

	public MotivoHorasE getMotivoH() {
		return motivoH;
	}

	public void setMotivoH(MotivoHorasE motivoH) {
		this.motivoH = motivoH;
	}

	public ProcesoHE getProcesoH() {
		return procesoH;
	}

	public void setProcesoH(ProcesoHE procesoH) {
		this.procesoH = procesoH;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public EstadoHE getEstadoH() {
		return estadoH;
	}

	public void setEstadoH(EstadoHE estadoH) {
		this.estadoH = estadoH;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}	
}
