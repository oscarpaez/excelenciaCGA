package co.com.CGAwebComercial.bean;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import co.com.CGAwebComercial.dao.EsquemaGerentesDao;
import co.com.CGAwebComercial.dao.GerentesDao;
import co.com.CGAwebComercial.entyties.EsquemaGerentes;
import co.com.CGAwebComercial.entyties.Gerentes;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class EsquemaGerentesBean implements Serializable {

	
	List<EsquemaGerentes> lista;
	List<EsquemaGerentes> pais;
	List<EsquemaGerentes> listasucursal;
	Gerentes gerente;
	
	private int codSap=0;
	private int sucursal=0;
	private int comision=0;
	
	private int totalp;
	private int totalR;
	private int totalC;
	
	private String totalplan;
	private String totalReal;
	private String totalComision;

	
	public void listarEsquemaGerentes(){

		try{
			
			GerentesDao daoG = new GerentesDao();
			gerente =  daoG.buscarCod(codSap);
			EsquemaGerentesDao dao = new EsquemaGerentesDao();
			lista = dao.listarEsquema(sucursal);
			for (EsquemaGerentes esquema : lista) {
				
				if(esquema.getLinea()== 1){
					
					if(esquema.getCumplimiento() >= 95){
						esquema.setComision(gerente.getBasecomision() * gerente.getUtilidadOperacionalPais()/100);
					}
					else{
						esquema.setComision(0);
					}
				}
				else if(esquema.getLinea()== 2){
					
					if(esquema.getCumplimiento() >= 95){
						esquema.setComision(gerente.getBasecomision()  * gerente.getUtilidadOperacionalUEN() /100);
					}
					else{
						esquema.setComision(0);
					}
				}
				else if(esquema.getLinea()== 3){

					if(esquema.getCumplimiento() >= 95){
						esquema.setComision(gerente.getBasecomision()  * gerente.getUtilidadOperacionalSucursal() /100);
					}
					else{
						esquema.setComision(0);
					}
				}
				else if(esquema.getLinea()== 4){

					if(esquema.getCumplimiento() >= 95){
						esquema.setComision(gerente.getBasecomision()  * gerente.getUtilidadBrutaLineaPais() /100);
					}
					else{
						esquema.setComision(0);
					}
				}
				else if(esquema.getLinea()== 5){

					if(esquema.getCumplimiento() >= 95){
						esquema.setComision(gerente.getBasecomision()  * gerente.getUtilidadBrutaSucursal() /100);
					}
					else{
						esquema.setComision(0);
					}
				}
				
				else if(esquema.getLinea()== 6){

					if(esquema.getCumplimiento() >= 95){
						esquema.setComision(gerente.getBasecomision()  * gerente.getRentabilidadMixdelineasSucursal() /100);
					}
					else{
						esquema.setComision(0);
					}
				}
				
				else if(esquema.getLinea()== 7){

					if(esquema.getCumplimiento() >= 95){
						esquema.setComision(gerente.getBasecomision()  * gerente.getRentabilidadporlineasZonacargo() /100);
					}
					else{
						esquema.setComision(0);
					}
				}
				
				else if(esquema.getLinea()== 8){

					if(esquema.getCumplimiento() >= 95){
						esquema.setComision(gerente.getBasecomision()  * gerente.getRecaudodeCarteraSucursal() /100);
					}
					else{
						esquema.setComision(0);
					}
				}
				totalp += esquema.getImportePlan();
				totalR += esquema.getImporteReal();
				totalC += esquema.getComision();
			}
			totalplan = new DecimalFormat("###,###").format(totalp);
			totalReal = new DecimalFormat("###,###").format(totalR);
			totalComision = new DecimalFormat("###,###").format(totalC);
			
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Esquema Gerentes");
		}
	}


	public int getSucursal() {
		return sucursal;
	}
	public void setSucursal(int sucursal) {
		this.sucursal = sucursal;
	}
	public int getComision() {
		return comision;
	}
	public void setComision(int comision) {
		this.comision = comision;
	}
	public List<EsquemaGerentes> getLista() {
		return lista;
	}
	public void setLista(List<EsquemaGerentes> lista) {
		this.lista = lista;
	}


	public List<EsquemaGerentes> getPais() {
		return pais;
	}
	public void setPais(List<EsquemaGerentes> pais) {
		this.pais = pais;
	}
	public List<EsquemaGerentes> getListasucursal() {
		return listasucursal;
	}
	public void setListasucursal(List<EsquemaGerentes> listasucursal) {
		this.listasucursal = listasucursal;
	}
	public Gerentes getGerente() {
		return gerente;
	}
	public void setGerente(Gerentes gerente) {
		this.gerente = gerente;
	}
	public int getCodSap() {
		return codSap;
	}
	public void setCodSap(int codSap) {
		this.codSap = codSap;
	}
	public String getTotalplan() {
		return totalplan;
	}
	public void setTotalplan(String totalplan) {
		this.totalplan = totalplan;
	}
	public String getTotalReal() {
		return totalReal;
	}


	public void setTotalReal(String totalReal) {
		this.totalReal = totalReal;
	}


	public String getTotalComision() {
		return totalComision;
	}


	public void setTotalComision(String totalComision) {
		this.totalComision = totalComision;
	}


	public int getTotalp() {
		return totalp;
	}


	public void setTotalp(int totalp) {
		this.totalp = totalp;
	}


	public int getTotalR() {
		return totalR;
	}


	public void setTotalR(int totalR) {
		this.totalR = totalR;
	}


	public int getTotalC() {
		return totalC;
	}


	public void setTotalC(int totalC) {
		this.totalC = totalC;
	}
	
    
}
