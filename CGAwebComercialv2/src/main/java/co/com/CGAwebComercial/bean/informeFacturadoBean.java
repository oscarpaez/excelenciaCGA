package co.com.CGAwebComercial.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import co.com.CGAwebComercial.dao.informeFacturadoDao;
import co.com.CGAwebComercial.entyties.InformeFacturado;


@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class informeFacturadoBean implements Serializable{
	
	@ManagedProperty("#{autenticacionBean}")
	private AutenticacionBean autenticacion;
	
	private List<InformeFacturado> listaInformeFact;
 	
	@PostConstruct
	public void listar(){
		
		try{
			informeFacturadoDao dao = new informeFacturadoDao();
			listaInformeFact = dao.listar();
			
			
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de informe de Facturado");
		}
	}

	public AutenticacionBean getAutenticacion() {
		return autenticacion;
	}

	public void setAutenticacion(AutenticacionBean autenticacion) {
		this.autenticacion = autenticacion;
	}

	public List<InformeFacturado> getListaInformeFact() {
		return listaInformeFact;
	}

	public void setListaInformeFact(List<InformeFacturado> listaInformeFact) {
		this.listaInformeFact = listaInformeFact;
	}	
}
