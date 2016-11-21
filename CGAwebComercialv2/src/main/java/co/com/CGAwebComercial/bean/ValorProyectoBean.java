package co.com.CGAwebComercial.bean;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import co.com.CGAwebComercial.dao.CiudadDao;
import co.com.CGAwebComercial.dao.FuncionarioDao;
import co.com.CGAwebComercial.dao.ValorProyectoDao;
import co.com.CGAwebComercial.dao.Zona_FuncionarioDao;
import co.com.CGAwebComercial.dao.Zona_ventaDao;
import co.com.CGAwebComercial.entyties.Ciudad;
import co.com.CGAwebComercial.entyties.Funcionario;
import co.com.CGAwebComercial.entyties.ValorProyecto;
import co.com.CGAwebComercial.entyties.Zona_Funcionario;
import co.com.CGAwebComercial.entyties.Zona_venta;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ValorProyectoBean implements Serializable{

	@ManagedProperty("#{autenticacionBean}")
	private AutenticacionBean autenticacion;
	
	private List<ValorProyecto> listaProyectos;
	private List<ValorProyecto> listaProyectosP;
	private ValorProyecto  proyecto;
	
	private int idFun;
	private String totalVM;
	private String totalVP;
	
	public void listarAsesor(){
		
		try{
			FuncionarioDao daoF = new FuncionarioDao();
			Funcionario funcionario = daoF.buscar(autenticacion.getUsuarioLogin().getId());
			
			proyecto = new ValorProyecto();
			
			Zona_ventaDao dao = new Zona_ventaDao();
			List<Zona_venta > zonaL = dao.buscarZona(autenticacion.getUsuarioLogin().getId());
			int idCiu = zonaL.get(0).getCiudad().getId();
			CiudadDao daoCi = new CiudadDao();
			Ciudad ciudad = daoCi.buscar(idCiu);
			
			proyecto.setFuncionario(funcionario);
			proyecto.setCiudad(ciudad);
			
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error al cargar el formulario de Proyectos.");
		}
	}
	
	public void salvarProyecto(){
		
		try{
			ValorProyectoDao daoV = new ValorProyectoDao();
			daoV.salvar(proyecto);
			
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error No se guardaron los datos del Proyecto");
		}
	}
	
	public void listarProyectoAsesor(){
		
		try{
			ValorProyectoDao daoV = new ValorProyectoDao();
			listaProyectos = daoV.listarProyectosAsesor(idFun);
			
			Long sumaMes = (long) 0;
			Long sumaPro = (long) 0;
			for (ValorProyecto valorProyecto : listaProyectos) {
				sumaMes += valorProyecto.getValorMes();
				sumaPro += valorProyecto.getValorTotal();
			}
			totalVM = new DecimalFormat("###,###").format(sumaMes);
			totalVP = new DecimalFormat("###,###").format(sumaPro);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error No se listaron los proyectos del asesor");
		}
	}
	
	public void listarProyectoOficina(){

		try{
			listaProyectos = new ArrayList<>();
			Zona_FuncionarioDao daoF = new Zona_FuncionarioDao();
			Zona_Funcionario zonaF = daoF.buscarFuncionarioZona(idFun);
			
			ValorProyectoDao daoV = new ValorProyectoDao();
			listaProyectos = daoV.listarProyectosOficina(zonaF.getCiudad().getId());

			Long sumaMes = (long) 0;
			Long sumaPro = (long) 0;
			for (ValorProyecto valorProyecto : listaProyectos) {
				sumaMes += valorProyecto.getValorMes();
				sumaPro += valorProyecto.getValorTotal();
			}
			totalVM = new DecimalFormat("###,###").format(sumaMes);
			totalVP = new DecimalFormat("###,###").format(sumaPro);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error No se listaron los proyectos de la Oficina ");
		}
	}

	public AutenticacionBean getAutenticacion() {
		return autenticacion;
	}

	public void setAutenticacion(AutenticacionBean autenticacion) {
		this.autenticacion = autenticacion;
	}

	public ValorProyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(ValorProyecto proyecto) {
		this.proyecto = proyecto;
	}

	public List<ValorProyecto> getListaProyectos() {
		return listaProyectos;
	}

	public void setListaProyectos(List<ValorProyecto> listaProyectos) {
		this.listaProyectos = listaProyectos;
	}

	public int getIdFun() {
		return idFun;
	}

	public void setIdFun(int idFun) {
		this.idFun = idFun;
	}

	public List<ValorProyecto> getListaProyectosP() {
		return listaProyectosP;
	}

	public void setListaProyectosP(List<ValorProyecto> listaProyectosP) {
		this.listaProyectosP = listaProyectosP;
	}
	
	public String getTotalVM() {
		return totalVM;
	}

	public void setTotalVM(String totalVM) {
		this.totalVM = totalVM;
	}

	public String getTotalVP() {
		return totalVP;
	}

	public void setTotalVP(String totalVP) {
		this.totalVP = totalVP;
	}
}
