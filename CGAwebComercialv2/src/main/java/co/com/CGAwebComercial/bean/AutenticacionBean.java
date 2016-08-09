package co.com.CGAwebComercial.bean;

import java.io.IOException;
//import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


import org.omnifaces.util.Faces;
//import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import co.com.CGAwebComercial.dao.PersonaDao;
import co.com.CGAwebComercial.dao.UsuarioDao;
import co.com.CGAwebComercial.entyties.Persona;
import co.com.CGAwebComercial.entyties.Usuario;

//import co.com.consecionario.util.FacesUtil;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class AutenticacionBean implements Serializable {
	
	private Usuario usuarioLogin;
    private List<Persona> listaPersona;
    private Persona persona;
    private String fechaActual;
    private String fechaDiaAnterior;
    private String fechaBusqueda;
	private String fechaBusquedaYear;
	private String tipoVendedor;
	private List<String> imagenes;
	private BigDecimal umbral;
	private int index = 0;
	private Integer progress=0;
	
	@PostConstruct
	public void iniciar(){
		
		try {
			fechaActual();
			persona = new Persona();
			PersonaDao dao = new PersonaDao();
			listaPersona = dao.listar();			
			
		}catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error al login verifique el usuario y contrase�a" + ex.getMessage());
		}	
	}
	
	public String entrar() {
		try {
			
			
			UsuarioDao dao = new UsuarioDao();
			usuarioLogin = dao.autenticar(persona.getCedula(), usuarioLogin.getClave());
			
			if(usuarioLogin == null){
				Messages.addGlobalError("La el usuario o la cantraseña son incorrectas", "info");
				return null;
			}
			else if(usuarioLogin.getPerfil().getId() == 1  || usuarioLogin.getPerfil().getId() == 6 ){
				Messages.addGlobalInfo("Bienvenido: "+ usuarioLogin.getPersona().getNombre());
				return "ve/vistaModulo.xhtml?faces-redirect=true";
			}
			else if(usuarioLogin.getPerfil().getId() == 2 || usuarioLogin.getPerfil().getId() == 12){
				Messages.addGlobalInfo("Bienvenido: "+ usuarioLogin.getPersona().getNombre());
				return "dl/vistaModulo.xhtml?faces-redirect=true";
			}
			else if(usuarioLogin.getPerfil().getId() == 7 ){
				Messages.addGlobalInfo("Bienvenido: "+ usuarioLogin.getPersona().getNombre());
				return "dr/vistaModulo.xhtml?faces-redirect=true";
			}
			else if(usuarioLogin.getPerfil().getId() == 8 ){
				Messages.addGlobalInfo("Bienvenido: "+ usuarioLogin.getPersona().getNombre());
				return "dcB/vistaModulo.xhtml?faces-redirect=true";
			}
			else if(usuarioLogin.getPerfil().getId() == 9 ){
				//Messages.addGlobalInfo("Bienvenido: "+ usuarioLogin.getPersona().getNombre());
				return "ji/vistaModulo.xhtml?faces-redirect=true";
			}
			else if(usuarioLogin.getPerfil().getId() == 11 ){
				Messages.addGlobalInfo("Bienvenido: "+ usuarioLogin.getPersona().getNombre());
				return "gg/vistaModulo.xhtml?faces-redirect=true";
			}
			else{
				Messages.addGlobalInfo("Bienvenido: "+ usuarioLogin.getPersona().getNombre());
				return "vistaModulo.xhtml?faces-redirect=true";
			}			
			
		}  catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error al login verifique el usuario y contrase�a" + ex.getMessage());
			return null;
		}
		
	}
	
	public String salir(){
		
		try {
			usuarioLogin = null;			
			fechaBusqueda = null;
			fechaBusquedaYear = null;	
			umbral = null;
			index = 0;
			Messages.addGlobalWarn("Se cerro la session");
			Faces.redirect("./index");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "/pages/login.xhtml?faces-redirect=true";
	}
	
	public void fechaActual(){

		try {
			SimpleDateFormat formateador = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("es_ES"));
			Date fechaDate = new Date();
			fechaActual=formateador.format(fechaDate);
			
			Date ayer = new Date( fechaDate.getTime()-86400000);
			fechaDiaAnterior = formateador.format(ayer);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}	
	

	public void onComplete() {
		//progress = 0;
		Messages.addGlobalError("Proceso Completado");
	}
	
	public void progreso(int valor){
		this.progress = valor;
	}
	
	
	
	public Usuario getUsuarioLogin() {
		if(usuarioLogin == null){
			usuarioLogin = new Usuario();
		}
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public List<Persona> getListaPersona() {
		return listaPersona;
	}

	public void setListaPersona(List<Persona> listaPersona) {
		this.listaPersona = listaPersona;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public String getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(String fechaActual) {
		this.fechaActual = fechaActual;
	}

	public String getFechaBusqueda() {
		if(fechaBusqueda == null){
			fechaBusqueda = "";
		}
		return fechaBusqueda;
	}

	public void setFechaBusqueda(String fechaBusqueda) {
		this.fechaBusqueda = fechaBusqueda;
	}

	public String getFechaBusquedaYear() {
		if(fechaBusquedaYear == null){
			fechaBusquedaYear = "";
		}
		return fechaBusquedaYear;
	}

	public void setFechaBusquedaYear(String fechaBusquedaYear) {
		this.fechaBusquedaYear = fechaBusquedaYear;
	}

	public List<String> getImagenes() {
		if(imagenes == null){
			imagenes = new ArrayList<String>();
			imagenes.add("amoblamient.jpg");
			imagenes.add("ing.jpg");
			imagenes.add("innovacion");
		}
		return imagenes;
	}

	public void setImagenes(List<String> imagenes) {
		this.imagenes = imagenes;
	}

	public BigDecimal getUmbral() {
		return umbral;
	}

	public void setUmbral(BigDecimal umbral) {
		this.umbral = umbral;
	}

	public String getTipoVendedor() {
		if(tipoVendedor == null){
			tipoVendedor = "";
		}
		return tipoVendedor;
	}

	public void setTipoVendedor(String tipoVendedor) {
		this.tipoVendedor = tipoVendedor;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public String getFechaDiaAnterior() {
		return fechaDiaAnterior;
	}

	public void setFechaDiaAnterior(String fechaDiaAnterior) {
		this.fechaDiaAnterior = fechaDiaAnterior;
	}
}
