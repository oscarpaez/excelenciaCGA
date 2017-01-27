package co.com.CGAwebComercial.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import co.com.CGAwebComercial.dao.HelpDesk.Vista_helpdeskDao;
import co.com.CGAwebComercial.entyties.HelpDesk.Vista_helpdesk;


@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class Vista_helpdeskBean implements Serializable{

	@ManagedProperty("#{autenticacionBean}")
	private AutenticacionBean autenticacion;
	
	private List<Vista_helpdesk> listaVista;
	private List<Vista_helpdesk> listaVistaFiltro;
	
	
	public void listarVistaKpi(){
		
		try{
			Vista_helpdeskDao dao = new Vista_helpdeskDao();
			listaVista = dao.listarKpi();
			
			System.out.println(listaVista.size() + "Infrrr");
			
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de la Vista");
		}
	}


	public AutenticacionBean getAutenticacion() {
		return autenticacion;
	}


	public void setAutenticacion(AutenticacionBean autenticacion) {
		this.autenticacion = autenticacion;
	}


	public List<Vista_helpdesk> getListaVista() {
		return listaVista;
	}


	public void setListaVista(List<Vista_helpdesk> listaVista) {
		this.listaVista = listaVista;
	}


	public List<Vista_helpdesk> getListaVistaFiltro() {
		return listaVistaFiltro;
	}


	public void setListaVistaFiltro(List<Vista_helpdesk> listaVistaFiltro) {
		this.listaVistaFiltro = listaVistaFiltro;
	}
}
