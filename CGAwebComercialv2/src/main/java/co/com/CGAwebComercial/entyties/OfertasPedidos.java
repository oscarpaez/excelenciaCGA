package co.com.CGAwebComercial.entyties;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class OfertasPedidos implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private long nOferta;
	
	private int posOferta;
	
	@Temporal(TemporalType.DATE)
	private Date  fechaCreOfe;
	
	@Column(length =10)
	private String tpOferta;
	
	@Column(length =32)
	private String claseOferta;
	
	private int  codOficina;
	
	@Column(length =32)
	private String oficina;
	
	@Temporal(TemporalType.DATE)
	private Date  fechaEntOfe;
	
	@Column (scale = 2)
	private BigDecimal valorOferta;
	
	private int  codCliente;
	
	@Column(length =64)
	private String cliente;
	
	@Column(length =20)
	private String  uen;
	
	private int codInterno;
	
	@Column(length =32)
	private String interno;
	
	private int codEspecialista;
	
	@Column(length =32)
	private String especialista;
	
	private int motivoRechazo;
	
	@Column(length =32)
	private String descripcionMotRec;

	private long nPedido;
	
	private int posPedido;
	
	@Temporal(TemporalType.DATE)
	private Date  fechaCrePed;
	
	@Temporal(TemporalType.DATE)
	private Date  fechaEntPed;
	
	@Column (scale = 2)
	private BigDecimal valorPedido;
	
	private int codMaterial;
	
	@Column(length =32)
	private String material;
	
	private int codInternoPed;
	
	@Column(length =32)
	private String internoPed;
	
	private int codEspecialistaPed;
	
	@Column(length =32)
	private String especialistaPed;	
	
	private int motivoRechazoPed;
	
	@Column(length =32)
	private String descripcionMotRecPed;
	
	@Transient
	private String imagen;
	
	@Transient
	private BigDecimal porcentaje;
	
	@Transient
	private Long llamadaEntrante;
	
	@Transient
	private Long llamadaSalientes;
	
	@Transient
	private Long llamadaNoContestadas;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getnOferta() {
		return nOferta;
	}

	public void setnOferta(long nOferta) {
		this.nOferta = nOferta;
	}

	public int getPosOferta() {
		return posOferta;
	}

	public void setPosOferta(int posOferta) {
		this.posOferta = posOferta;
	}

	public Date getFechaCreOfe() {
		return fechaCreOfe;
	}

	public void setFechaCreOfe(Date fechaCreOfe) {
		this.fechaCreOfe = fechaCreOfe;
	}

	public String getTpOferta() {
		return tpOferta;
	}

	public void setTpOferta(String tpOferta) {
		this.tpOferta = tpOferta;
	}

	public String getClaseOferta() {
		return claseOferta;
	}

	public void setClaseOferta(String claseOferta) {
		this.claseOferta = claseOferta;
	}

	public int getCodOficina() {
		return codOficina;
	}

	public void setCodOficina(int codOficina) {
		this.codOficina = codOficina;
	}

	public String getOficina() {
		return oficina;
	}

	public void setOficina(String oficina) {
		this.oficina = oficina;
	}

	public Date getFechaEntOfe() {
		return fechaEntOfe;
	}

	public void setFechaEntOfe(Date fechaEntOfe) {
		this.fechaEntOfe = fechaEntOfe;
	}

	public BigDecimal getValorOferta() {
		return valorOferta;
	}

	public void setValorOferta(BigDecimal valorOferta) {
		this.valorOferta = valorOferta;
	}

	public int getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(int codCliente) {
		this.codCliente = codCliente;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getUen() {
		return uen;
	}

	public void setUen(String uen) {
		this.uen = uen;
	}

	public int getCodInterno() {
		return codInterno;
	}

	public void setCodInterno(int codInterno) {
		this.codInterno = codInterno;
	}

	public String getInterno() {
		return interno;
	}

	public void setInterno(String interno) {
		this.interno = interno;
	}

	public int getCodEspecialista() {
		return codEspecialista;
	}

	public void setCodEspecialista(int codEspecialista) {
		this.codEspecialista = codEspecialista;
	}

	public String getEspecialista() {
		return especialista;
	}

	public void setEspecialista(String especialista) {
		this.especialista = especialista;
	}

	public int getMotivoRechazo() {
		return motivoRechazo;
	}

	public void setMotivoRechazo(int motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}

	public String getDescripcionMotRec() {
		return descripcionMotRec;
	}

	public void setDescripcionMotRec(String descripcionMotRec) {
		this.descripcionMotRec = descripcionMotRec;
	}

	public long getnPedido() {
		return nPedido;
	}

	public void setnPedido(long nPedido) {
		this.nPedido = nPedido;
	}

	public int getPosPedido() {
		return posPedido;
	}

	public void setPosPedido(int posPedido) {
		this.posPedido = posPedido;
	}

	public Date getFechaCrePed() {
		return fechaCrePed;
	}

	public void setFechaCrePed(Date fechaCrePed) {
		this.fechaCrePed = fechaCrePed;
	}

	public Date getFechaEntPed() {
		return fechaEntPed;
	}

	public void setFechaEntPed(Date fechaEntPed) {
		this.fechaEntPed = fechaEntPed;
	}

	public BigDecimal getValorPedido() {
		return valorPedido;
	}

	public void setValorPedido(BigDecimal valorPedido) {
		this.valorPedido = valorPedido;
	}

	public int getCodMaterial() {
		return codMaterial;
	}

	public void setCodMaterial(int codMaterial) {
		this.codMaterial = codMaterial;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public int getCodInternoPed() {
		return codInternoPed;
	}

	public void setCodInternoPed(int codInternoPed) {
		this.codInternoPed = codInternoPed;
	}

	public String getInternoPed() {
		return internoPed;
	}

	public void setInternoPed(String internoPed) {
		this.internoPed = internoPed;
	}

	public int getCodEspecialistaPed() {
		return codEspecialistaPed;
	}

	public void setCodEspecialistaPed(int codEspecialistaPed) {
		this.codEspecialistaPed = codEspecialistaPed;
	}

	public String getEspecialistaPed() {
		return especialistaPed;
	}

	public void setEspecialistaPed(String especialistaPed) {
		this.especialistaPed = especialistaPed;
	}

	public int getMotivoRechazoPed() {
		return motivoRechazoPed;
	}

	public void setMotivoRechazoPed(int motivoRechazoPed) {
		this.motivoRechazoPed = motivoRechazoPed;
	}

	public String getDescripcionMotRecPed() {
		return descripcionMotRecPed;
	}

	public void setDescripcionMotRecPed(String descripcionMotRecPed) {
		this.descripcionMotRecPed = descripcionMotRecPed;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public BigDecimal getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}

	public Long getLlamadaEntrante() {
		return llamadaEntrante;
	}

	public void setLlamadaEntrante(Long llamadaEntrante) {
		this.llamadaEntrante = llamadaEntrante;
	}

	public Long getLlamadaSalientes() {
		return llamadaSalientes;
	}

	public void setLlamadaSalientes(Long llamadaSalientes) {
		this.llamadaSalientes = llamadaSalientes;
	}

	public Long getLlamadaNoContestadas() {
		return llamadaNoContestadas;
	}

	public void setLlamadaNoContestadas(Long llamadaNoContestadas) {
		this.llamadaNoContestadas = llamadaNoContestadas;
	}	
}
