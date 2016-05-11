package co.com.CGAwebComercial.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import co.com.CGAwebComercial.dao.GerentesDao;
import co.com.CGAwebComercial.entyties.Gerentes;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class GerentesBean implements Serializable{
	
	private List<Gerentes> listaGerentes;
	private Gerentes gerente;
	
	@PostConstruct
	public void listarGerentes(){
		
		try{
			GerentesDao dao = new GerentesDao();
			listaGerentes = dao.listar();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Gerentes");
		}
	}
	public List<Gerentes> getListaGerentes() {
		return listaGerentes;
	}
	public void setListaGerentes(List<Gerentes> listaGerentes) {
		this.listaGerentes = listaGerentes;
	}
	public Gerentes getGerente() {
		return gerente;
	}
	public void setGerente(Gerentes gerente) {
		this.gerente = gerente;
	}
	
	

}
