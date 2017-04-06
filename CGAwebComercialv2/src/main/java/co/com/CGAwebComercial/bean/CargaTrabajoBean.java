package co.com.CGAwebComercial.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import co.com.CGAwebComercial.dao.CargaTrabajoDao;
import co.com.CGAwebComercial.dao.HistoricaCargaTrabajoDao;
import co.com.CGAwebComercial.dao.SucursalDao;
import co.com.CGAwebComercial.dao.TrabajoAreaDao;
import co.com.CGAwebComercial.dao.Zona_FuncionarioDao;
import co.com.CGAwebComercial.entyties.CargaTrabajo;
import co.com.CGAwebComercial.entyties.HistoricaCargaTrabajo;
import co.com.CGAwebComercial.entyties.Sucursal;
import co.com.CGAwebComercial.entyties.TrabajoArea;
import co.com.CGAwebComercial.entyties.Zona_Funcionario;
import co.com.CGAwebComercial.util.Team;


@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CargaTrabajoBean implements Serializable {

	@ManagedProperty("#{autenticacionBean}")
	private AutenticacionBean autenticacion;

	private List<CargaTrabajo> listaCargaTrabajo;
	private List<CargaTrabajo> filtroCargaTrabajo;
	private List<Team> team;
	private List<TrabajoArea> listaTA;
	private List<Sucursal>  listaSucursal;


	private CargaTrabajo carga;
	
	public CargaTrabajoBean() {
		
	}

	@PostConstruct
	public void listarCarga(){

		try{
			if(autenticacion != null){
				autenticacion.registroIngreso(autenticacion.getUsuarioLogin());
			}			
			
			team = new ArrayList<Team>();

			TrabajoAreaDao daoT = new TrabajoAreaDao();
			listaTA = daoT.listar();
			CargaTrabajoDao dao = new CargaTrabajoDao();			
			
			
			if(autenticacion.getUsuarioLogin().getId() == 0){
				for (TrabajoArea ta  : listaTA) {
					
					Team t = new Team(ta.getDescripcion());	
					listaCargaTrabajo = dao.listarCargaPorArea(ta.getId(), (long) 0);

					if(! listaCargaTrabajo.isEmpty()){

						for (CargaTrabajo cargaT : listaCargaTrabajo) {
							t.getStats().add((CargaTrabajo) cargaT);
						}
						team.add(t);
					}
				}			
			}		
			else if (autenticacion.getUsuarioLogin().getPerfil().getId() == 24 || autenticacion.getUsuarioLogin().getPerfil().getId() == 23
					|| autenticacion.getUsuarioLogin().getPerfil().getId() == 25 || autenticacion.getUsuarioLogin().getPerfil().getId() == 6
					|| autenticacion.getUsuarioLogin().getPerfil().getId() == 1 || autenticacion.getUsuarioLogin().getPerfil().getId() == 7
					|| autenticacion.getUsuarioLogin().getPerfil().getId() == 8 || autenticacion.getUsuarioLogin().getPerfil().getId() == 11
					|| autenticacion.getUsuarioLogin().getPerfil().getId() == 20 || autenticacion.getUsuarioLogin().getPerfil().getId() == 26){
				System.out.println("##");
				for (TrabajoArea ta  : listaTA) {
				
					Team t = new Team(ta.getDescripcion());	
					listaCargaTrabajo = dao.listarCargaPorArea(ta.getId(), (long) 0);

					if(! listaCargaTrabajo.isEmpty()){

						for (CargaTrabajo cargaT : listaCargaTrabajo) {
							t.getStats().add((CargaTrabajo) cargaT);
						}
						team.add(t);
					}
				}
			}
			else if (autenticacion.getUsuarioLogin().getPerfil().getId() == 22) {
				Zona_FuncionarioDao daoF = new Zona_FuncionarioDao(); 
				Zona_Funcionario zona = daoF.buscarFuncionarioZona(autenticacion.getUsuarioLogin().getId());
				//Zona_Funcionario zona = daoF.buscarFuncionarioZona(autenticacion.getUsuarioLogin().getId());

				SucursalDao daoS = new SucursalDao();	
				Sucursal sucursal = daoS.buscarSucursal(zona.getCiudad().getId());

				for (TrabajoArea ta  : listaTA) {

					Team t = new Team(ta.getDescripcion());	
					listaCargaTrabajo = dao.listarCargaPorArea(ta.getId(), sucursal.getId());

					if(! listaCargaTrabajo.isEmpty()){

						for (CargaTrabajo cargaT : listaCargaTrabajo) {
							t.getStats().add((CargaTrabajo) cargaT);
						}
						team.add(t);
					}
				}
			}		
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Carga de Trabajo");
		}
	}


	public void editarCargaTrabajo(){

		try{
			//materiales.setSucursal(sucursal);
			carga.setFechaRegistros(new Date());
			CargaTrabajoDao dao = new CargaTrabajoDao();
			dao.merge(carga);

			int idCargaT = dao.ultimoRegistroIngresado();

			HistoricaCargaTrabajo daoH = new HistoricaCargaTrabajo();
			daoH.setCapacidad(carga.getCapacidad());
			daoH.setEquipo(carga.getEquipo());
			daoH.setFechaContratada(carga.getFechaContratada());
			daoH.setFechaDisponibilidad(carga.getFechaDisponibilidad());
			daoH.setFechaRegistros(carga.getFechaRegistros());
			daoH.setHoraContratadas(carga.getHoraContratadas());
			daoH.setHoraDia(carga.getHoraDia());
			daoH.setHoraDisponible(carga.getHoraDisponible());
			daoH.setObservacion(carga.getObservacion());
			daoH.setIdCargaTrabajo(idCargaT);

			HistoricaCargaTrabajoDao daoCT = new HistoricaCargaTrabajoDao();
			daoCT.salvar(daoH);


			carga = new CargaTrabajo();

			listarCarga();
			//listaCargaTrabajo = dao.listar();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se guardo la carga de trabajo");
		}	
	}

	public void editar(CargaTrabajo c){

		try{

			listaSucursal = new ArrayList<>();
			listaTA = new ArrayList<>();

			carga =(c == null)? new CargaTrabajo() : c;			
			System.out.println( "Nombre Carga T"  );

			SucursalDao daoS = new SucursalDao();
			listaSucursal = daoS.listar();

			TrabajoAreaDao daoT = new TrabajoAreaDao();
			listaTA = daoT.listar();

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se edito la carga de trabajo");
		}

	}

	public void borrarCargaTrabajo(CargaTrabajo c){

		try{
			HistoricaCargaTrabajo daoH = new HistoricaCargaTrabajo();
			daoH.setCapacidad(c.getCapacidad());
			daoH.setEquipo(c.getEquipo());
			daoH.setFechaContratada(c.getFechaContratada());
			daoH.setFechaDisponibilidad(c.getFechaDisponibilidad());
			daoH.setFechaRegistros(c.getFechaRegistros());
			daoH.setHoraContratadas(c.getHoraContratadas());
			daoH.setHoraDia(carga.getHoraDia());
			daoH.setHoraDisponible(c.getHoraDisponible());
			daoH.setObservacion(c.getObservacion());
			daoH.setIdCargaTrabajo(c.getId());

			HistoricaCargaTrabajoDao daoCT = new HistoricaCargaTrabajoDao();
			daoCT.salvar(daoH);


			CargaTrabajoDao dao = new CargaTrabajoDao();
			dao.borrar(c);	

			listarCarga();	
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se borro el Material");
		}
	}
	
	public String cambioPagina1(){

		try{
				//Thread.sleep(4000);
			if(autenticacion.getUsuarioLogin().getPerfil().getId() == 24){
				System.out.println(autenticacion.getUsuarioLogin().getPerfil().getId());
				listarCarga();
			}
			else{
				Faces.redirect("./pages/iv/materiales.xhtml");
			}			
			//Faces.redirect("./pages/of/ofertaPedido2.xhtml");
			return "of/materiales.xhtml?faces-redirect=true";
		} catch (RuntimeException | IOException  ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error No se realizo la Gráfica de Gestion de llamadas");
		}
		return null;
	}

	public AutenticacionBean getAutenticacion() {
		return autenticacion;
	}

	public void setAutenticacion(AutenticacionBean autenticacion) {
		this.autenticacion = autenticacion;
	}

	public List<CargaTrabajo> getListaCargaTrabajo() {
		return listaCargaTrabajo;
	}

	public void setListaCargaTrabajo(List<CargaTrabajo> listaCargaTrabajo) {
		this.listaCargaTrabajo = listaCargaTrabajo;
	}

	public List<CargaTrabajo> getFiltroCargaTrabajo() {
		return filtroCargaTrabajo;
	}

	public void setFiltroCargaTrabajo(List<CargaTrabajo> filtroCargaTrabajo) {
		this.filtroCargaTrabajo = filtroCargaTrabajo;
	}

	public CargaTrabajo getCarga() {
		return carga;
	}

	public void setCarga(CargaTrabajo carga) {
		this.carga = carga;
	}

	public List<Team> getTeam() {
		return team;
	}

	public void setTeam(List<Team> team) {
		this.team = team;
	}

	public List<TrabajoArea> getListaTA() {
		return listaTA;
	}

	public void setListaTA(List<TrabajoArea> listaTA) {
		this.listaTA = listaTA;
	}

	public List<Sucursal> getListaSucursal() {
		return listaSucursal;
	}

	public void setListaSucursal(List<Sucursal> listaSucursal) {
		this.listaSucursal = listaSucursal;
	}	
}
