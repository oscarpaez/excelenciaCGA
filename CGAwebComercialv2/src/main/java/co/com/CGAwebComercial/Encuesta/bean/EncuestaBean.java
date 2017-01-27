package co.com.CGAwebComercial.Encuesta.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.event.FlowEvent;

import co.com.CGAwebComercial.dao.Encuesta.AreaDao;
import co.com.CGAwebComercial.dao.Encuesta.DeporteDao;
import co.com.CGAwebComercial.dao.Encuesta.EstadoCivilDao;
import co.com.CGAwebComercial.dao.Encuesta.EstadoConyugeDao;
import co.com.CGAwebComercial.dao.Encuesta.EstratoDao;
import co.com.CGAwebComercial.dao.Encuesta.EstudioDao;
import co.com.CGAwebComercial.dao.Encuesta.GeneroDao;
import co.com.CGAwebComercial.dao.Encuesta.HijosDao;
import co.com.CGAwebComercial.dao.Encuesta.HorarioDao;
import co.com.CGAwebComercial.dao.Encuesta.IngresoDao;
import co.com.CGAwebComercial.dao.Encuesta.MedioTransporteDao;
import co.com.CGAwebComercial.dao.Encuesta.PersonaDao;
import co.com.CGAwebComercial.dao.Encuesta.SucursalDao;
import co.com.CGAwebComercial.dao.Encuesta.TipoContratoDao;
import co.com.CGAwebComercial.dao.Encuesta.TipoViviendaDao;
import co.com.CGAwebComercial.entyties.encuesta.Area;
import co.com.CGAwebComercial.entyties.encuesta.Deporte;
import co.com.CGAwebComercial.entyties.encuesta.Estadocivil;
import co.com.CGAwebComercial.entyties.encuesta.Estadoconyuge;
import co.com.CGAwebComercial.entyties.encuesta.Estrato;
import co.com.CGAwebComercial.entyties.encuesta.Estudio;
import co.com.CGAwebComercial.entyties.encuesta.Genero;
import co.com.CGAwebComercial.entyties.encuesta.Hijo;
import co.com.CGAwebComercial.entyties.encuesta.Horario;
import co.com.CGAwebComercial.entyties.encuesta.Ingreso;
import co.com.CGAwebComercial.entyties.encuesta.Mediotransporte;
import co.com.CGAwebComercial.entyties.encuesta.Persona;
import co.com.CGAwebComercial.entyties.encuesta.Sucursal;
import co.com.CGAwebComercial.entyties.encuesta.Tipocontrato;
import co.com.CGAwebComercial.entyties.encuesta.Tipovivienda;


@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class EncuestaBean implements Serializable {


	private List<Area> listaArea;
	private List<Estrato> listaEstrato;
	private List<Tipovivienda> listaVivienda;
	private List<Hijo> listaHijo;
	private List<Mediotransporte> listaTransporte;
	private List<Estadocivil> listaEstadoCivil;
	private List<Estadoconyuge> listaEstadoConyuge;
	private List<Sucursal> listaSucursal;
	private List<Tipocontrato> listaContrato;
	private List<Horario> listaHorario; 
	private List<Ingreso> listaIngreso;
	private List<Estudio> listaEstudio;
	private List<Deporte> listaDeporte;
	private List<Persona> listaPersona;
	
	private List<String> nombreS;
	private Area area;
	private Persona persona;
	private Hijo hijo;
	private Horario horario;

	private Integer numeroHijos;
	private Integer cedula;
	private Integer genero;
	private String[] guests;
	private String prueba;
	private String render;
	private Integer talla1;
	private Integer talla2;
	private Integer talla3;
	private Integer talla4;
	private Integer talla5;
	private Integer salud1;
	private Integer salud2;
	private Integer salud3;
	private Integer salud4;
	private Integer salud5;
	private Integer salud6;
	private Integer salud7;
	private Integer dotacion;

	private boolean skip;

	public EncuestaBean(){

		guests = new String[0];
		//numeroHijos = 0;		
		render = "false";
		listarEntida();
	}


	public void listarEntida(){

		try{
			AreaDao daoA = new AreaDao();
			listaArea = daoA.listar();
			
			EstratoDao daoE = new EstratoDao();
			listaEstrato = daoE.listar();
			
			TipoViviendaDao daoV = new TipoViviendaDao();
			listaVivienda = daoV.listar();
			
			MedioTransporteDao daoT = new MedioTransporteDao();
			listaTransporte = daoT.listar();
			
			EstadoCivilDao daoEs = new EstadoCivilDao();
			listaEstadoCivil = daoEs.listar();
			
			EstadoConyugeDao daoC = new EstadoConyugeDao();
			listaEstadoConyuge = daoC.listar();
			
			SucursalDao daoS = new SucursalDao();
			listaSucursal = daoS.listar();
			
			HorarioDao daoH = new HorarioDao();
			listaHorario = daoH.listar();
			
			TipoContratoDao daoTC = new TipoContratoDao();
			listaContrato = daoTC.listar();
			
			IngresoDao daoI = new IngresoDao();
			listaIngreso = daoI.listar();
			
			EstudioDao daoEst = new EstudioDao();
			listaEstudio = daoEst.listar();
			
			DeporteDao daoD = new DeporteDao();
			listaDeporte = daoD.listar();
			
			horario = new Horario(); 
			persona = new Persona();
			
			//persona.setNombre("prueba");
			listaHijo = new ArrayList<>();
			nombreS = new ArrayList<>();
			for(int i=0; i<5; i++){
				Hijo h = new Hijo();
				String a = "";
				listaHijo.add(h);
				nombreS.add(a);
			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("No listaron las entidades");
		}
	}

	public void listarRegistrados(){

		try{
			PersonaDao daoP = new PersonaDao();
			listaPersona = daoP.listar();
			//int i =0;			
			/*
			for (Persona p :listaPersona) {
				
				p.setTh((p.getTieneHijos() == 1)? "Si" : "NO");
				HijosDao daoH = new HijosDao();
				List<Hijo> h = daoH.listaHijos(p.getId());
				
				if(h.size() >0){
					p.setH1((h.get(0).getNombre()));
					p.setF1(h.get(0).getFechanacimiento());
					
					p.setH2((h.get(1).getNombre()));
					p.setF2(h.get(1).getFechanacimiento());
					
					p.setH3((h.get(2).getNombre()));
					p.setF3(h.get(2).getFechanacimiento());
					
					p.setH4((h.get(3).getNombre()));
					p.setF4(h.get().getFechanacimiento());
					
					p.setH5((h.get(0).getNombre()));
					p.setF5(h.get(0).getFechanacimiento());
				}				
			}*/
			
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("No se Pudieron agregar los campos");
		}
	}

	
	public void verificarHijos(){
		
		try{
			System.out.println(persona.getTieneHijos());
			if(persona.getTieneHijos() == 1){
				render = "true";
			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("No  se puedo verificar cantidad de hijos");
		}
	}
	
		public void salvar(){
		
		try{
			PersonaDao dao = new PersonaDao();
			System.out.println( genero + "Nombre persona" + persona.getNombre());
			
			GeneroDao daoG = new GeneroDao();
			Genero generoA = daoG.buscar(genero);
			persona.setGenero(generoA);
			
			HorarioDao daoH = new HorarioDao();
			horario = daoH.buscar(horario.getId());
			persona.setHorario(horario);
			
			if(persona.getEstadoConyuge() == null){
				
				EstadoConyugeDao daoEC = new EstadoConyugeDao();
				Estadoconyuge ec = daoEC.buscar(8);
				persona.setEstadoConyuge(ec);
			}			
			
			persona.setFechaActualizacion(new Date());
			dao.salvar(persona);
			
			Persona p = dao.ultimoRegistro();
			
			if(persona.getTieneHijos() == 1){
				
				for (Hijo h :listaHijo) {
					System.out.println("nombre" + h.getNombre());
					System.out.println("fecha" + h.getFechanacimiento());					
					
					if(h.getFechanacimiento() != null && !h.getNombre().equals("")){
						System.out.println("persona" + p.getNombre());
						h.setPersona(p);
						HijosDao daoHi = new HijosDao();
						daoHi.salvar(h);						
					}
				}
			}
			
			Faces.redirect("./pages/ad/actualizacionDatos.xhtml?faces-redirect=true");
			/*
			SaludDao daoS = new SaludDao();
			TipoSatisfacionDao daoTS = new TipoSatisfacionDao(); 
			ValorSaludDao daoVS = new ValorSaludDao();
			
			Salud salud = daoS.buscar(1);
			Tiposatisfaccion ts = daoTS.buscar(salud1);
			Valorsalud vSalud = new Valorsalud();
			vSalud.setSalud(salud);
			vSalud.setSatisfaccion(ts);
			vSalud.setPersona(p);			
			daoVS.salvar(vSalud);
			
			
			salud = daoS.buscar(2);
			ts = daoTS.buscar(salud2);
			vSalud = new Valorsalud();
			vSalud.setSalud(salud);
			vSalud.setSatisfaccion(ts);
			vSalud.setPersona(p);			
			daoVS.salvar(vSalud);
			
			salud = daoS.buscar(3);
			ts = daoTS.buscar(salud3);
			vSalud = new Valorsalud();
			vSalud.setSalud(salud);
			vSalud.setSatisfaccion(ts);
			vSalud.setPersona(p);			
			daoVS.salvar(vSalud);
			
			salud = daoS.buscar(4);
			ts = daoTS.buscar(salud4);
			vSalud = new Valorsalud();
			vSalud.setSalud(salud);
			vSalud.setSatisfaccion(ts);
			vSalud.setPersona(p);			
			daoVS.salvar(vSalud);
			
			salud = daoS.buscar(5);
			ts = daoTS.buscar(salud5);
			vSalud = new Valorsalud();
			vSalud.setSalud(salud);
			vSalud.setSatisfaccion(ts);
			vSalud.setPersona(p);			
			daoVS.salvar(vSalud);
			
			salud = daoS.buscar(6);
			ts = daoTS.buscar(salud6);
			vSalud = new Valorsalud();
			vSalud.setSalud(salud);
			vSalud.setSatisfaccion(ts);
			vSalud.setPersona(p);			
			daoVS.salvar(vSalud);*/
			
			/*
			if(persona.getDotacion() == 1){
				TallaDao daoT = new TallaDao();
				TipoDotacionDao daoTi = new TipoDotacionDao();
				DotacionDao daoD = new DotacionDao();
				
				Talla talla = daoT.buscar(talla1);
				Tipodotacion td = daoTi.buscar(1);
				Dotacion dotacion = new Dotacion();				
				dotacion.setTalla(talla);
				dotacion.setTipodotacion(td);
				dotacion.setPersona(p);
				daoD.salvar(dotacion);
				
				talla = daoT.buscar(talla2);
				td = daoTi.buscar(2);
				dotacion = new Dotacion();				
				dotacion.setTalla(talla);
				dotacion.setTipodotacion(td);
				dotacion.setPersona(p);
				daoD.salvar(dotacion);
				
				talla = daoT.buscar(talla3);
				td = daoTi.buscar(3);
				dotacion = new Dotacion();				
				dotacion.setTalla(talla);
				dotacion.setTipodotacion(td);
				dotacion.setPersona(p);
				daoD.salvar(dotacion);
				
				talla = daoT.buscar(talla4);
				td = daoTi.buscar(4);
				dotacion = new Dotacion();				
				dotacion.setTalla(talla);
				dotacion.setTipodotacion(td);
				dotacion.setPersona(p);
				daoD.salvar(dotacion);
				
				talla = daoT.buscar(talla5);
				td = daoTi.buscar(5);
				dotacion = new Dotacion();
				
				dotacion.setTalla(talla);
				dotacion.setTipodotacion(td);
				dotacion.setPersona(p);
				daoD.salvar(dotacion);
			}*/
			
			
			//System.out.println("Tamaño array" + guests.length);
			//System.out.println(persona.getTieneHijos()  + " " + guests[0] + "--" +guests[1]);
//			for (String nombre : guests) {
//				System.out.println("nom: " +nombre);
//			}
//			System.out.println("Tamaño list" + listaHijo.size()); 
//			for (Hijo h : listaHijo) {
//				System.out.println("nom: " +h.getNombre());
//			}
		} catch (RuntimeException | IOException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("No  se puedo verificar cantidad de hijos");
		}
	}
	
	
	public String onFlowProcess(FlowEvent event) {
		if(skip) {
			skip = false;   //reset in case user goes back
			return "confirm";
		}
		else {
			return event.getNewStep();
		}
	}

	public String[] getGuests() {
		return guests;
	}

	public void setGuests(String[] guests) {
		this.guests = guests;
	}

	public Integer getNumeroHijos() {
		return numeroHijos;
	}

	public void setNumeroHijos(Integer numeroHijos) {
		this.numeroHijos = numeroHijos;
	}

	public String getPrueba() {
		return prueba;
	}

	public void setPrueba(String prueba) {
		this.prueba = prueba;
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public List<Area> getListaArea() {
		return listaArea;
	}

	public void setListaArea(List<Area> listaArea) {
		this.listaArea = listaArea;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Hijo getHijo() {
		return hijo;
	}

	public void setHijo(Hijo hijo) {
		this.hijo = hijo;
	}

	public String getRender() {
		return render;
	}

	public void setRender(String render) {
		this.render = render;
	}

	public List<Hijo> getListaHijo() {
		return listaHijo;
	}

	public void setListaHijo(List<Hijo> listaHijo) {
		this.listaHijo = listaHijo;
	}

	public Integer getCedula() {
		return cedula;
	}

	public void setCedula(Integer cedula) {
		this.cedula = cedula;
	}

	public Integer getGenero() {
		return genero;
	}

	public void setGenero(Integer genero) {
		this.genero = genero;
	}

	public List<Estrato> getListaEstrato() {
		return listaEstrato;
	}

	public void setListaEstrato(List<Estrato> listaEstrato) {
		this.listaEstrato = listaEstrato;
	}

	public List<Tipovivienda> getListaVivienda() {
		return listaVivienda;
	}

	public void setListaVivienda(List<Tipovivienda> listaVivienda) {
		this.listaVivienda = listaVivienda;
	}

	public List<Mediotransporte> getListaTransporte() {
		return listaTransporte;
	}

	public void setListaTransporte(List<Mediotransporte> listaTransporte) {
		this.listaTransporte = listaTransporte;
	}

	public List<Estadocivil> getListaEstadoCivil() {
		return listaEstadoCivil;
	}

	public void setListaEstadoCivil(List<Estadocivil> listaEstadoCivil) {
		this.listaEstadoCivil = listaEstadoCivil;
	}

	public List<Estadoconyuge> getListaEstadoConyuge() {
		return listaEstadoConyuge;
	}

	public void setListaEstadoConyuge(List<Estadoconyuge> listaEstadoConyuge) {
		this.listaEstadoConyuge = listaEstadoConyuge;
	}

	public List<String> getNombreS() {
		return nombreS;
	}

	public void setNombreS(List<String> nombreS) {
		this.nombreS = nombreS;
	}

	public List<Sucursal> getListaSucursal() {
		return listaSucursal;
	}

	public void setListaSucursal(List<Sucursal> listaSucursal) {
		this.listaSucursal = listaSucursal;
	}

	public List<Tipocontrato> getListaContrato() {
		return listaContrato;
	}

	public void setListaContrato(List<Tipocontrato> listaContrato) {
		this.listaContrato = listaContrato;
	}

	public List<Horario> getListaHorario() {
		return listaHorario;
	}

	public void setListaHorario(List<Horario> listaHorario) {
		this.listaHorario = listaHorario;
	}

	public List<Ingreso> getListaIngreso() {
		return listaIngreso;
	}

	public void setListaIngreso(List<Ingreso> listaIngreso) {
		this.listaIngreso = listaIngreso;
	}

	public Horario getHorario() {
		return horario;
	}

	public void setHorario(Horario horario) {
		this.horario = horario;
	}

	public List<Estudio> getListaEstudio() {
		return listaEstudio;
	}

	public void setListaEstudio(List<Estudio> listaEstudio) {
		this.listaEstudio = listaEstudio;
	}

	public Integer getTalla1() {
		return talla1;
	}

	public void setTalla1(Integer talla1) {
		this.talla1 = talla1;
	}

	public Integer getTalla2() {
		return talla2;
	}

	public void setTalla2(Integer talla2) {
		this.talla2 = talla2;
	}

	public Integer getTalla3() {
		return talla3;
	}

	public void setTalla3(Integer talla3) {
		this.talla3 = talla3;
	}

	public Integer getTalla4() {
		return talla4;
	}

	public void setTalla4(Integer talla4) {
		this.talla4 = talla4;
	}

	public Integer getTalla5() {
		return talla5;
	}

	public void setTalla5(Integer talla5) {
		this.talla5 = talla5;
	}

	public Integer getSalud1() {
		return salud1;
	}

	public void setSalud1(Integer salud1) {
		this.salud1 = salud1;
	}

	public Integer getSalud2() {
		return salud2;
	}

	public void setSalud2(Integer salud2) {
		this.salud2 = salud2;
	}

	public Integer getSalud3() {
		return salud3;
	}

	public void setSalud3(Integer salud3) {
		this.salud3 = salud3;
	}

	public Integer getSalud4() {
		return salud4;
	}

	public void setSalud4(Integer salud4) {
		this.salud4 = salud4;
	}

	public Integer getSalud5() {
		return salud5;
	}

	public void setSalud5(Integer salud5) {
		this.salud5 = salud5;
	}

	public Integer getSalud6() {
		return salud6;
	}

	public void setSalud6(Integer salud6) {
		this.salud6 = salud6;
	}

	public Integer getSalud7() {
		return salud7;
	}

	public void setSalud7(Integer salud7) {
		this.salud7 = salud7;
	}

	public Integer getDotacion() {
		return dotacion;
	}

	public void setDotacion(Integer dotacion) {
		this.dotacion = dotacion;
	}

	public List<Deporte> getListaDeporte() {
		return listaDeporte;
	}

	public void setListaDeporte(List<Deporte> listaDeporte) {
		this.listaDeporte = listaDeporte;
	}
	
	public List<Persona> getListaPersona() {
		return listaPersona;
	}

	public void setListaPersona(List<Persona> listaPersona) {
		this.listaPersona = listaPersona;
	}	
}
