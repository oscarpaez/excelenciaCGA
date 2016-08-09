package co.com.CGAwebComercial.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import co.com.CGAwebComercial.dao.ComisionDao;
import co.com.CGAwebComercial.dao.FuncionarioDao;
import co.com.CGAwebComercial.dao.GerentesDao;
import co.com.CGAwebComercial.entyties.Funcionario;
import co.com.CGAwebComercial.entyties.Gerentes;
import co.com.CGAwebComercial.resource.Recursos;
import co.com.CGAwebComercial.util.ComisionVendedores;
import co.com.CGAwebComercial.util.Fechas;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class GerentesBean implements Serializable{
	
	@ManagedProperty("#{autenticacionBean}")
	private AutenticacionBean autenticacion;
	
	private List<Gerentes> listaGerentes;
	private List<Funcionario> listaDirectores;
	private List<ComisionVendedores> comisionDirectores;
	private List<ComisionVendedores> listaVL;
	private List<Fechas> listaFechas;
	
	private Recursos recurso;
	
	private Funcionario funcionario;
	private Gerentes gerente;
	
	private int idFuncionario;
	private Integer progress=0;
	private String fechaBusqueda;
	private String fechaBusquedaYear;
	private String totalPresupuesto;
	private String totalIngreso;
	private String totalComision;
	private String totalCumplimiento;
	private String totalVLi;
	private String totalVPa;
	private String totalMLi;
	private String totalZCa;
	private String totalRe; 
	
	public GerentesBean() {
		recurso = new Recursos();
		listaFechas = recurso.cargarFechas();
		//listarDirectores();
		
	}
	
	public void listarGerentes(){
		
		try{
			GerentesDao dao = new GerentesDao();
			listaGerentes = dao.listar();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Gerentes");
		}
	}
	
	public void listarDirectores(){
		
		try{
			fechaBusqueda = (autenticacion.getFechaBusqueda() == null)? "": autenticacion.getFechaBusqueda();
			fechaBusquedaYear = (autenticacion.getFechaBusquedaYear() == null)? "": autenticacion.getFechaBusquedaYear();
			
			BigDecimal totalVL = new BigDecimal("0");
			BigDecimal totalVP= new BigDecimal("0");
			BigDecimal totalML= new BigDecimal("0");
			BigDecimal totalZC= new BigDecimal("0");
			BigDecimal totalR= new BigDecimal("0");
			BigDecimal totalComisionB= new BigDecimal("0");			
			
			comisionDirectores = new ArrayList<>();
			FuncionarioDao daoF = new FuncionarioDao();
			listaDirectores = daoF.listarDirectorPais();
			ComisionVendedores sucursales =  new ComisionVendedores();
			ComisionDao daoC = new ComisionDao();
			int t = 0;
			int progress1 = 100 /listaDirectores.size();
			for (Funcionario funcionario : listaDirectores) {
				System.out.println(funcionario.getPersona().getNombre());
				if(funcionario.getComision().getIdComision() == 9){
					
					sucursales = daoC.comisionDirectores(1, funcionario, fechaBusqueda, fechaBusquedaYear);
					sucursales.setCedula(funcionario.getPersona().getCedula());
					sucursales.setNombre(funcionario.getPersona().getNombre());
					sucursales.setId(funcionario.getId_funcionario());
					sucursales.setConcepto(funcionario.getComision().getNombre());
					
					totalVL = totalVL.add(sucursales.getComisionVentaLinea());
					totalVP = totalVP.add(sucursales.getComisionVentaPais());
					//totalML = totalML.add(sucursales.getComisionMixLinea());
					//totalZC = totalZC.add(sucursales.getComisionZonasCargo());
					//totalR  = totalR.add(sucursales.getComisionRecaudo());
					comisionDirectores.add(sucursales);
				}
				else if(funcionario.getComision().getIdComision() == 10){
					sucursales = daoC.comisionDirectoresA(funcionario, fechaBusqueda, fechaBusquedaYear);
					sucursales.setCedula(funcionario.getPersona().getCedula());
					sucursales.setNombre(funcionario.getPersona().getNombre());
					sucursales.setId(funcionario.getId_funcionario());
					sucursales.setConcepto(funcionario.getComision().getNombre());
					
					totalVL = totalVL.add(sucursales.getComisionVentaLinea());
					totalVP = totalVP.add(sucursales.getComisionVentaPais());
					//totalML = totalML.add(sucursales.getComisionMixLinea());
					//totalZC = totalZC.add(sucursales.getComisionZonasCargo());
					//totalR  = totalR.add(sucursales.getComisionRecaudo());
					
					comisionDirectores.add(sucursales);
				}
				else if(funcionario.getComision().getIdComision() == 11 ){
					sucursales = daoC.comisionDirectorComercial(funcionario, fechaBusqueda, fechaBusquedaYear);
					sucursales.setCedula(funcionario.getPersona().getCedula());
					sucursales.setNombre(funcionario.getPersona().getNombre());
					sucursales.setId(funcionario.getId_funcionario());
					sucursales.setConcepto(funcionario.getComision().getNombre());
					
					//totalVL = totalVL.add(sucursales.getComisionVentaLinea());
					//totalVP = totalVP.add(sucursales.getComisionVentaPais());
					totalML = totalML.add(sucursales.getComisionMixLinea());
					//totalZC = totalZC.add(sucursales.getComisionZonasCargo());
					totalR  = totalR.add(sucursales.getComisionRecaudo());
					
					comisionDirectores.add(sucursales);
				}
				else if(funcionario.getComision().getIdComision() == 12){
					sucursales = daoC.comisionJefeInternos(funcionario, fechaBusqueda, fechaBusquedaYear);
					sucursales.setCedula(funcionario.getPersona().getCedula());
					sucursales.setNombre(funcionario.getPersona().getNombre());
					sucursales.setId(funcionario.getId_funcionario());
					sucursales.setConcepto(funcionario.getComision().getNombre());
					
					//totalVL = totalVL.add(sucursales.getComisionVentaLinea());
					//totalVP = totalVP.add(sucursales.getComisionVentaPais());
					totalML = totalML.add(sucursales.getComisionMixLinea());
					//totalZC = totalZC.add(sucursales.getComisionZonasCargo());
					totalR  = totalR.add(sucursales.getComisionRecaudo());
					
					comisionDirectores.add(sucursales);
				}
				else if(funcionario.getComision().getIdComision() == 13  || funcionario.getComision().getIdComision() == 14){
					sucursales = daoC.comisionDirectorRegional(funcionario, fechaBusqueda, fechaBusquedaYear);
					sucursales.setCedula(funcionario.getPersona().getCedula());
					sucursales.setNombre(funcionario.getPersona().getNombre());
					sucursales.setId(funcionario.getId_funcionario());
					sucursales.setConcepto(funcionario.getComision().getNombre());
					
					//totalVL = totalVL.add(sucursales.getComisionVentaLinea());
					//totalVP = totalVP.add(sucursales.getComisionVentaPais());
					totalML = totalML.add(sucursales.getComisionMixLinea());
					totalZC = totalZC.add(sucursales.getComisionZonasCargo());
					totalR  = totalR.add(sucursales.getComisionRecaudo());
					comisionDirectores.add(sucursales);
				}
				t += progress1;
				setProgress(t);
			}
			totalVLi = new DecimalFormat("###,###").format(totalVL);
			totalVPa =  new DecimalFormat("###,###").format(totalVP);
			totalMLi =  new DecimalFormat("###,###").format(totalML);
			totalZCa = new DecimalFormat("###,###").format(totalZC);
			totalRe = new DecimalFormat("###,###").format(totalR);
			totalComisionB = totalR.add(totalVP.add(totalML).add(totalZC).add(totalR));
			totalComision = new DecimalFormat("###,###").format(totalComisionB);
			if(progress != 0){
				setProgress(100);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setProgress(0);
			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Directores");
		}
		
	}
	
	public void directorVL(){
		
		try{
			fechaBusqueda = (autenticacion.getFechaBusqueda() == null)? "": autenticacion.getFechaBusqueda();
			fechaBusquedaYear = (autenticacion.getFechaBusquedaYear() == null)? "": autenticacion.getFechaBusquedaYear();
			
			comisionDirectores = new ArrayList<>();
			BigDecimal sumaPresupuesto = new BigDecimal("0.00");
			BigDecimal sumaIngresoR = new BigDecimal("0.00");
			BigDecimal comision = new BigDecimal("0.00");
			FuncionarioDao daoF = new FuncionarioDao();
			Funcionario funcionario = daoF.buscar(idFuncionario);
			ComisionDao daoC = new ComisionDao();
			if(funcionario.getComision().getIdComision() == 9){
				comisionDirectores = daoC.directorVL(1, funcionario, fechaBusqueda, fechaBusquedaYear);
			}
			else{
				comisionDirectores = daoC.directorAnVL(funcionario, fechaBusqueda, fechaBusquedaYear);
			}
			
			for (ComisionVendedores vendedor : comisionDirectores) {
				sumaPresupuesto = sumaPresupuesto.add(vendedor.getPresupuestoB());
				sumaIngresoR =  sumaIngresoR.add(vendedor.getIngresoRealB()); 
				comision = comision.add(vendedor.getComisionVentaLinea());
			}
			BigDecimal cum = sumaIngresoR.divide(sumaPresupuesto, 2, BigDecimal.ROUND_HALF_UP);
			totalPresupuesto = new DecimalFormat("###,###").format(sumaPresupuesto);
			totalIngreso = new DecimalFormat("###,###").format(sumaIngresoR);
			BigDecimal comi = funcionario.getComision().getValorBaseVenta().multiply(new BigDecimal("0.8"));
			comi = comi.multiply(cum);
			
			System.out.println(cum.multiply(new BigDecimal("100")).intValue() );			
			totalComision = (cum.multiply(new BigDecimal("100")).intValue() >= 85)? new DecimalFormat("###,###").format(comi): new DecimalFormat("###,###").format(0);
			totalCumplimiento = new DecimalFormat("###,###.##").format(cum.multiply(new BigDecimal("100").setScale(2, BigDecimal.ROUND_HALF_UP)));
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo el mix de linea del Director");
		}
	}
	
	public void directorVP(){
		try{
			fechaBusqueda = (autenticacion.getFechaBusqueda() == null)? "": autenticacion.getFechaBusqueda();
			fechaBusquedaYear = (autenticacion.getFechaBusquedaYear() == null)? "": autenticacion.getFechaBusquedaYear();
			
			comisionDirectores = new ArrayList<>();
			BigDecimal sumaPresupuesto = new BigDecimal("0.00");
			BigDecimal sumaIngresoR = new BigDecimal("0.00");
			BigDecimal comision = new BigDecimal("0.00");
			FuncionarioDao daoF = new FuncionarioDao();
			Funcionario funcionario = daoF.buscar(idFuncionario);
			ComisionDao daoC = new ComisionDao();
			comisionDirectores = daoC.cumplimientoPais(funcionario, fechaBusqueda, fechaBusquedaYear);
			
			for (ComisionVendedores vendedor : comisionDirectores) {
				sumaPresupuesto = sumaPresupuesto.add(vendedor.getPresupuestoB());
				sumaIngresoR =  sumaIngresoR.add(vendedor.getIngresoRealB()); 
				comision = comision.add(vendedor.getComisionVentaLinea());
			}
			BigDecimal cum = sumaIngresoR.divide(sumaPresupuesto, 2, BigDecimal.ROUND_HALF_UP);
			totalPresupuesto = new DecimalFormat("###,###").format(sumaPresupuesto);
			totalIngreso = new DecimalFormat("###,###").format(sumaIngresoR);
			BigDecimal comi = funcionario.getComision().getValorBaseVenta().multiply(new BigDecimal("0.2"));
			comi = comi.multiply(cum);
			totalComision = (cum.intValue() >= 85)? new DecimalFormat("###,###").format(comi) : new DecimalFormat("###,###.##").format(new BigDecimal("0.00"));
			totalCumplimiento = new DecimalFormat("###,###.##").format(cum.multiply(new BigDecimal("100").setScale(2, BigDecimal.ROUND_HALF_UP)));
		
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la venta Pais del Director");
		}
	}
	
	public void directorML(){
		try{
			fechaBusqueda = (autenticacion.getFechaBusqueda() == null)? "": autenticacion.getFechaBusqueda();
			fechaBusquedaYear = (autenticacion.getFechaBusquedaYear() == null)? "": autenticacion.getFechaBusquedaYear();
			
			comisionDirectores = new ArrayList<>();
			BigDecimal sumaPresupuesto = new BigDecimal("0.00");
			BigDecimal sumaIngresoR = new BigDecimal("0.00");
			BigDecimal comision = new BigDecimal("0.00");
			FuncionarioDao daoF = new FuncionarioDao();
			Funcionario funcionario = daoF.buscar(idFuncionario);
			ComisionDao daoC = new ComisionDao();
			comisionDirectores = daoC.detalleML(funcionario, fechaBusqueda, fechaBusquedaYear);
			
			for (ComisionVendedores vendedor : comisionDirectores) {
				sumaPresupuesto = sumaPresupuesto.add(vendedor.getPresupuestoB());
				sumaIngresoR =  sumaIngresoR.add(vendedor.getIngresoRealB()); 
				comision = comision.add(vendedor.getComisionMixLinea());
			}
			BigDecimal cum = sumaIngresoR.divide(sumaPresupuesto, 4, BigDecimal.ROUND_HALF_UP);
			totalPresupuesto = new DecimalFormat("###,###").format(sumaPresupuesto);
			totalIngreso = new DecimalFormat("###,###").format(sumaIngresoR);
			totalComision = new DecimalFormat("###,###").format(comision); 
			totalCumplimiento = new DecimalFormat("###,###.##").format(cum.multiply(new BigDecimal("100").setScale(2, BigDecimal.ROUND_HALF_UP)));
		
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la venta Pais del Director");
		}
	}
	
	
	public void directorRecaudo(){
		
		try{

			fechaBusqueda = (autenticacion.getFechaBusqueda() == null)? "": autenticacion.getFechaBusqueda();
			fechaBusquedaYear = (autenticacion.getFechaBusquedaYear() == null)? "": autenticacion.getFechaBusquedaYear();
			
			comisionDirectores = new ArrayList<>();
			FuncionarioDao daoF = new FuncionarioDao();
			Funcionario funcionario = daoF.buscar(idFuncionario);
			ComisionDao daoC = new ComisionDao();
			comisionDirectores = daoC.detalleRecaudo(funcionario, fechaBusqueda, fechaBusquedaYear);
		
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo el detalle del Recaudo del Director");
		}	
	}
	
	public void directorZC(){
		
		try{
			fechaBusqueda = (autenticacion.getFechaBusqueda() == null)? "": autenticacion.getFechaBusqueda();
			fechaBusquedaYear = (autenticacion.getFechaBusquedaYear() == null)? "": autenticacion.getFechaBusquedaYear();
			
			comisionDirectores = new ArrayList<>();
			BigDecimal sumaPresupuesto = new BigDecimal("0.00");
			BigDecimal sumaIngresoR = new BigDecimal("0.00");
			BigDecimal comision = new BigDecimal("0.00");
			FuncionarioDao daoF = new FuncionarioDao();
			Funcionario funcionario = daoF.buscar(idFuncionario);
			ComisionDao daoC = new ComisionDao();
			comisionDirectores = daoC.detalleZC(funcionario, fechaBusqueda, fechaBusquedaYear);
			
			for (ComisionVendedores vendedor : comisionDirectores) {
				sumaPresupuesto = sumaPresupuesto.add(vendedor.getPresupuestoB());
				sumaIngresoR =  sumaIngresoR.add(vendedor.getIngresoRealB()); 
				comision = comision.add(vendedor.getComisionZonasCargo());
			}
			BigDecimal cum = sumaIngresoR.divide(sumaPresupuesto, 4, BigDecimal.ROUND_HALF_UP);
			totalPresupuesto = new DecimalFormat("###,###").format(sumaPresupuesto);
			totalIngreso = new DecimalFormat("###,###").format(sumaIngresoR);
			totalComision = new DecimalFormat("###,###").format(comision); 
			totalCumplimiento = new DecimalFormat("###,###.##").format(cum.multiply(new BigDecimal("100").setScale(2, BigDecimal.ROUND_HALF_UP)));
		
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la venta Pais del Director");
		}
	}
	
	public void onComplete() {
		Messages.addGlobalInfo("Proceso Completado");
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
	public List<Funcionario> getListaDirectores() {
		return listaDirectores;
	}
	public void setListaDirectores(List<Funcionario> listaDirectores) {
		this.listaDirectores = listaDirectores;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	public List<ComisionVendedores> getComisionDirectores() {
		return comisionDirectores;
	}

	public void setComisionDirectores(List<ComisionVendedores> comisionDirectores) {
		this.comisionDirectores = comisionDirectores;
	}

	public String getFechaBusqueda() {
		return fechaBusqueda;
	}

	public void setFechaBusqueda(String fechaBusqueda) {
		this.fechaBusqueda = fechaBusqueda;
	}

	public String getFechaBusquedaYear() {
		return fechaBusquedaYear;
	}

	public void setFechaBusquedaYear(String fechaBusquedaYear) {
		this.fechaBusquedaYear = fechaBusquedaYear;
	}

	public int getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(int idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public List<ComisionVendedores> getListaVL() {
		return listaVL;
	}

	public void setListaVL(List<ComisionVendedores> listaVL) {
		this.listaVL = listaVL;
	}

	public String getTotalPresupuesto() {
		return totalPresupuesto;
	}

	public void setTotalPresupuesto(String totalPresupuesto) {
		this.totalPresupuesto = totalPresupuesto;
	}

	public String getTotalIngreso() {
		return totalIngreso;
	}

	public void setTotalIngreso(String totalIngreso) {
		this.totalIngreso = totalIngreso;
	}

	public String getTotalComision() {
		return totalComision;
	}

	public void setTotalComision(String totalComision) {
		this.totalComision = totalComision;
	}
	public String getTotalCumplimiento() {
		return totalCumplimiento;
	}

	public void setTotalCumplimiento(String totalCumplimiento) {
		this.totalCumplimiento = totalCumplimiento;
	}

	public AutenticacionBean getAutenticacion() {
		return autenticacion;
	}

	public void setAutenticacion(AutenticacionBean autenticacion) {
		this.autenticacion = autenticacion;
	}

	public List<Fechas> getListaFechas() {
		return listaFechas;
	}

	public void setListaFechas(List<Fechas> listaFechas) {
		this.listaFechas = listaFechas;
	}

	public Recursos getRecurso() {
		return recurso;
	}

	public void setRecurso(Recursos recurso) {
		this.recurso = recurso;
	}

	public String getTotalVLi() {
		return totalVLi;
	}

	public void setTotalVLi(String totalVLi) {
		this.totalVLi = totalVLi;
	}

	public String getTotalVPa() {
		return totalVPa;
	}

	public void setTotalVPa(String totalVPa) {
		this.totalVPa = totalVPa;
	}

	public String getTotalMLi() {
		return totalMLi;
	}

	public void setTotalMLi(String totalMLi) {
		this.totalMLi = totalMLi;
	}

	public String getTotalZCa() {
		return totalZCa;
	}

	public void setTotalZCa(String totalZCa) {
		this.totalZCa = totalZCa;
	}

	public String getTotalRe() {
		return totalRe;
	}

	public void setTotalRe(String totalRe) {
		this.totalRe = totalRe;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}
}
