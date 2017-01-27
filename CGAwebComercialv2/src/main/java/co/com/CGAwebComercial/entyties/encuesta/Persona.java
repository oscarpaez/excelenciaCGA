package co.com.CGAwebComercial.entyties.encuesta;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


@SuppressWarnings("serial")
@Entity
public class Persona implements Serializable {
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String actividad;

	private String barrio;

	private String cargo;

	private int cedula;

	private String celular;

	private String completarformacion;

	private String conemergencia;

	private String conquienvives;

	private String convivesdiscapacidad;
	
	private int tieneHijos;

	private String correo;

	private String direccion;
	
	private int dotacion;

	@Temporal(TemporalType.DATE)
	private Date fechaIngreso;

	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;
	
	@Temporal(TemporalType.DATE)
	private Date fechaActualizacion;

	private String fondoCesantias;

	private String fondoexcequial;

	private String hijosactividades;

	private String institucionestudio;

	private String nombre;

	private String rh;

	private String tallerformacion;

	private String telefono;

	private String tiposactividades;

	private String tituloestudio;
	
	private String  chaqueta;
	
	private String camisa;
	
	private String bata;
	
	private String botas;
	
	private String pantalon;
	
	private String sesacionSalud;
	
	private String agudezaVisual;
	
	private String agudezaAuditiva;
	
	private String resistensia;
	
	private String nutricion;
	
	private String actividadFisica;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private Genero genero;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private Estrato estrato;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private Tipovivienda  vivienda;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private Mediotransporte transporte;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private Estadocivil estadoCivil;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private Estadoconyuge estadoConyuge;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private Sucursal sucursal;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private Area area;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private Tipocontrato contrato;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private Horario horario;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private Ingreso ingreso;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private Estudio estudio;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private Deporte deporte;
	
	@Transient
	private String tel;
	
	@Transient
	private String cel;
	
	private String th;
	
	private String h1;
	
	private String h2;
	
	private String h3;
	
	private String h4;
	
	private String h5;
	
	private Date f1;
	
	private Date f2;
	
	private Date f3;
		
	private Date f4;
	
	private Date f5;
	

	public Persona() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getActividad() {
		return this.actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public String getBarrio() {
		return this.barrio;
	}

	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

	public String getCargo() {
		return this.cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public int getCedula() {
		return this.cedula;
	}

	public void setCedula(int cedula) {
		this.cedula = cedula;
	}

	public String getCompletarformacion() {
		return this.completarformacion;
	}

	public void setCompletarformacion(String completarformacion) {
		this.completarformacion = completarformacion;
	}

	public String getConemergencia() {
		return this.conemergencia;
	}

	public void setConemergencia(String conemergencia) {
		this.conemergencia = conemergencia;
	}

	public String getConquienvives() {
		return this.conquienvives;
	}

	public void setConquienvives(String conquienvives) {
		this.conquienvives = conquienvives;
	}

	public String getConvivesdiscapacidad() {
		return this.convivesdiscapacidad;
	}

	public void setConvivesdiscapacidad(String convivesdiscapacidad) {
		this.convivesdiscapacidad = convivesdiscapacidad;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Date getFechaIngreso() {
		return this.fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getFondoCesantias() {
		return this.fondoCesantias;
	}

	public void setFondoCesantias(String fondoCesantias) {
		this.fondoCesantias = fondoCesantias;
	}

	public String getFondoexcequial() {
		return this.fondoexcequial;
	}

	public void setFondoexcequial(String fondoexcequial) {
		this.fondoexcequial = fondoexcequial;
	}

	public String getHijosactividades() {
		return this.hijosactividades;
	}

	public void setHijosactividades(String hijosactividades) {
		this.hijosactividades = hijosactividades;
	}

	public String getInstitucionestudio() {
		return this.institucionestudio;
	}

	public void setInstitucionestudio(String institucionestudio) {
		this.institucionestudio = institucionestudio;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRh() {
		return this.rh;
	}

	public void setRh(String rh) {
		this.rh = rh;
	}

	public String getTallerformacion() {
		return this.tallerformacion;
	}

	public void setTallerformacion(String tallerformacion) {
		this.tallerformacion = tallerformacion;
	}

	public String getTiposactividades() {
		return this.tiposactividades;
	}

	public void setTiposactividades(String tiposactividades) {
		this.tiposactividades = tiposactividades;
	}

	public String getTituloestudio() {
		return this.tituloestudio;
	}

	public void setTituloestudio(String tituloestudio) {
		this.tituloestudio = tituloestudio;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public Estrato getEstrato() {
		return estrato;
	}

	public void setEstrato(Estrato estrato) {
		this.estrato = estrato;
	}

	public Tipovivienda getVivienda() {
		return vivienda;
	}

	public void setVivienda(Tipovivienda vivienda) {
		this.vivienda = vivienda;
	}

	public Mediotransporte getTransporte() {
		return transporte;
	}

	public void setTransporte(Mediotransporte transporte) {
		this.transporte = transporte;
	}

	public Estadocivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(Estadocivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public Estadoconyuge getEstadoConyuge() {
		return estadoConyuge;
	}

	public void setEstadoConyuge(Estadoconyuge estadoConyuge) {
		this.estadoConyuge = estadoConyuge;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Tipocontrato getContrato() {
		return contrato;
	}

	public void setContrato(Tipocontrato contrato) {
		this.contrato = contrato;
	}

	public Horario getHorario() {
		return horario;
	}

	public void setHorario(Horario horario) {
		this.horario = horario;
	}

	public Ingreso getIngreso() {
		return ingreso;
	}

	public void setIngreso(Ingreso ingreso) {
		this.ingreso = ingreso;
	}

	public Estudio getEstudio() {
		return estudio;
	}

	public void setEstudio(Estudio estudio) {
		this.estudio = estudio;
	}

	public Deporte getDeporte() {
		return deporte;
	}

	public void setDeporte(Deporte deporte) {
		this.deporte = deporte;
	}

	public int getTieneHijos() {
		return tieneHijos;
	}

	public void setTieneHijos(int tieneHijos) {
		this.tieneHijos = tieneHijos;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCel() {
		return cel;
	}

	public void setCel(String cel) {
		this.cel = cel;
	}

	public int getDotacion() {
		return dotacion;
	}

	public void setDotacion(int dotacion) {
		this.dotacion = dotacion;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getChaqueta() {
		return chaqueta;
	}

	public void setChaqueta(String chaqueta) {
		this.chaqueta = chaqueta;
	}

	public String getCamisa() {
		return camisa;
	}

	public void setCamisa(String camisa) {
		this.camisa = camisa;
	}

	public String getBata() {
		return bata;
	}

	public void setBata(String bata) {
		this.bata = bata;
	}

	public String getBotas() {
		return botas;
	}

	public void setBotas(String botas) {
		this.botas = botas;
	}

	public String getPantalon() {
		return pantalon;
	}

	public void setPantalon(String pantalon) {
		this.pantalon = pantalon;
	}

	public String getSesacionSalud() {
		return sesacionSalud;
	}

	public void setSesacionSalud(String sesacionSalud) {
		this.sesacionSalud = sesacionSalud;
	}

	public String getAgudezaVisual() {
		return agudezaVisual;
	}

	public void setAgudezaVisual(String agudezaVisual) {
		this.agudezaVisual = agudezaVisual;
	}

	public String getAgudezaAuditiva() {
		return agudezaAuditiva;
	}

	public void setAgudezaAuditiva(String agudezaAuditiva) {
		this.agudezaAuditiva = agudezaAuditiva;
	}

	public String getResistensia() {
		return resistensia;
	}

	public void setResistensia(String resistensia) {
		this.resistensia = resistensia;
	}

	public String getNutricion() {
		return nutricion;
	}

	public void setNutricion(String nutricion) {
		this.nutricion = nutricion;
	}

	public String getActividadFisica() {
		return actividadFisica;
	}

	public void setActividadFisica(String actividadFisica) {
		this.actividadFisica = actividadFisica;
	}

	public String getH1() {
		return h1;
	}

	public void setH1(String h1) {
		this.h1 = h1;
	}

	public String getH2() {
		return h2;
	}

	public void setH2(String h2) {
		this.h2 = h2;
	}

	public String getH3() {
		return h3;
	}

	public void setH3(String h3) {
		this.h3 = h3;
	}

	public String getH4() {
		return h4;
	}

	public void setH4(String h4) {
		this.h4 = h4;
	}

	public String getH5() {
		return h5;
	}

	public void setH5(String h5) {
		this.h5 = h5;
	}

	public Date getF1() {
		return f1;
	}

	public void setF1(Date f1) {
		this.f1 = f1;
	}

	public Date getF2() {
		return f2;
	}

	public void setF2(Date f2) {
		this.f2 = f2;
	}

	public Date getF3() {
		return f3;
	}

	public void setF3(Date f3) {
		this.f3 = f3;
	}

	public Date getF4() {
		return f4;
	}

	public void setF4(Date f4) {
		this.f4 = f4;
	}

	public Date getF5() {
		return f5;
	}

	public void setF5(Date f5) {
		this.f5 = f5;
	}
	
	public String getTh() {
		return th;
	}
	
	public void setTh(String th) {
		this.th = th;
	}	
}