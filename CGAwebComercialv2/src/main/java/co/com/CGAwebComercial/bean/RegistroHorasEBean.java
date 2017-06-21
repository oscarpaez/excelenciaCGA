package co.com.CGAwebComercial.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import co.com.CGAwebComercial.dao.EstadoHEDao;
import co.com.CGAwebComercial.dao.MotivoHorasEDao;
import co.com.CGAwebComercial.dao.ProcesoHEDao;
import co.com.CGAwebComercial.dao.PuestoTrabajoDao;
import co.com.CGAwebComercial.dao.RegistroHorasEDao;
import co.com.CGAwebComercial.dao.SucursalDao;
import co.com.CGAwebComercial.dao.TipoHoraEDao;
import co.com.CGAwebComercial.dao.UsuarioDao;
import co.com.CGAwebComercial.entyties.EstadoHE;
import co.com.CGAwebComercial.entyties.MotivoHorasE;
import co.com.CGAwebComercial.entyties.ProcesoHE;
import co.com.CGAwebComercial.entyties.PuestoTrabajo;
import co.com.CGAwebComercial.entyties.RegistroHorasE;
import co.com.CGAwebComercial.entyties.Sucursal;
import co.com.CGAwebComercial.entyties.TipoHoraE;
import co.com.CGAwebComercial.entyties.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class RegistroHorasEBean implements Serializable{

	@ManagedProperty("#{autenticacionBean}")
	private AutenticacionBean autenticacion;

	private RegistroHorasE registroHE;
	private List<RegistroHorasE> listaRegistros;
	private List<TipoHoraE> listaTipoHoraE;
	private List<PuestoTrabajo> listaPuestoTrabajo;
	private List<MotivoHorasE> listaMotivoHorasE;
	private List<ProcesoHE> listaProcesoHE;	
	private List<Sucursal> listaSucursal;
	private List<EstadoHE> listaEstadoHE;

	private int idList;
	private String value1;

	public void listar(){

		try{
			RegistroHorasEDao dao = new RegistroHorasEDao();
			listaRegistros = dao.listarPorSucursal();

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de datos");
		}


	}


	public void listarDatos(){

		try{
			System.out.println(idList + "id item");

			if(idList != 0 ){
				RegistroHorasEDao daoR = new RegistroHorasEDao();
				registroHE = daoR.buscar(idList);
			}
			else{
				registroHE = new RegistroHorasE();
			}

			EstadoHEDao daoE = new EstadoHEDao();
			listaEstadoHE = daoE.listar();

			SucursalDao daoS = new SucursalDao();
			listaSucursal = daoS.listar();

			ProcesoHEDao daoP = new ProcesoHEDao();
			listaProcesoHE = daoP.listar();

			PuestoTrabajoDao daoPu = new PuestoTrabajoDao();
			listaPuestoTrabajo = daoPu.listar();

			MotivoHorasEDao daoM = new MotivoHorasEDao();
			listaMotivoHorasE = daoM.listar();

			TipoHoraEDao  daoT = new TipoHoraEDao();
			listaTipoHoraE = daoT.listar();



		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de datos");
		}
	}

	public void salvar(){

		try{
			RegistroHorasEDao dao = new RegistroHorasEDao();

			UsuarioDao daoU = new UsuarioDao();
			Usuario usuario = daoU.buscar(2); 
			registroHE.setUsuario(usuario);
			registroHE.setEstadoHS(String.valueOf(registroHE.getEstadoH().getId() -1));
			dao.merge(registroHE);

			System.out.println("fffff" +registroHE.getId());
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Salvo el registro de Horas Extras");
		}
	}

	public void limpiar(){

		try{
			System.out.println(registroHE.getId());
			if(registroHE.getId() != 0){
				registroHE = new RegistroHorasE();
				idList = 0;
			}


		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Salvo el registro de Horas Extras");
		}
	}

	public void liberarHoras(RegistroHorasE r) {

		try{
			System.out.println(r.getId() + "id item");
			System.out.println(r.getEstadoH().getId());

			RegistroHorasEDao dao = new RegistroHorasEDao();
			UsuarioDao daoU = new UsuarioDao();
			Usuario usuario = daoU.buscar(2); 
			r.setUsuario(usuario);

			EstadoHEDao daoE = new EstadoHEDao();

			int a = r.getEstadoHS().equals("0") ? 1 : r.getEstadoHS().equals("1") ? 2 : 3 ;
			System.out.println(a);
			EstadoHE estadoHe = daoE.buscar(a);
			r.setEstadoH(estadoHe);				
			dao.merge(r);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Salvo el registro de Horas Extras");
		}
	}


	public AutenticacionBean getAutenticacion() {
		return autenticacion;
	}
	public void setAutenticacion(AutenticacionBean autenticacion) {
		this.autenticacion = autenticacion;
	}
	public List<TipoHoraE> getListaTipoHoraE() {
		return listaTipoHoraE;
	}
	public void setListaTipoHoraE(List<TipoHoraE> listaTipoHoraE) {
		this.listaTipoHoraE = listaTipoHoraE;
	}
	public List<PuestoTrabajo> getListaPuestoTrabajo() {
		return listaPuestoTrabajo;
	}
	public void setListaPuestoTrabajo(List<PuestoTrabajo> listaPuestoTrabajo) {
		this.listaPuestoTrabajo = listaPuestoTrabajo;
	}
	public List<MotivoHorasE> getListaMotivoHorasE() {
		return listaMotivoHorasE;
	}
	public void setListaMotivoHorasE(List<MotivoHorasE> listaMotivoHorasE) {
		this.listaMotivoHorasE = listaMotivoHorasE;
	}
	public List<ProcesoHE> getListaProcesoHE() {
		return listaProcesoHE;
	}
	public void setListaProcesoHE(List<ProcesoHE> listaProcesoHE) {
		this.listaProcesoHE = listaProcesoHE;
	}
	public List<Sucursal> getListaSucursal() {
		return listaSucursal;
	}
	public void setListaSucursal(List<Sucursal> listaSucursal) {
		this.listaSucursal = listaSucursal;
	}
	public List<EstadoHE> getListaEstadoHE() {
		return listaEstadoHE;
	}
	public void setListaEstadoHE(List<EstadoHE> listaEstadoHE) {
		this.listaEstadoHE = listaEstadoHE;
	}
	public RegistroHorasE getRegistroHE() {
		return registroHE;
	}
	public void setRegistroHE(RegistroHorasE registroHE) {
		this.registroHE = registroHE;
	}
	public List<RegistroHorasE> getListaRegistros() {
		return listaRegistros;
	}
	public void setListaRegistros(List<RegistroHorasE> listaRegistros) {
		this.listaRegistros = listaRegistros;
	}
	public int getIdList() {
		return idList;
	}
	public void setIdList(int idList) {
		this.idList = idList;
	}
	public String getValue1() {
		return value1;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
}
