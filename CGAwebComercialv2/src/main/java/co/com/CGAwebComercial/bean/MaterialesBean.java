package co.com.CGAwebComercial.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import co.com.CGAwebComercial.dao.EstadoMaterialDao;
import co.com.CGAwebComercial.dao.HistoricoMaterialesDao;
import co.com.CGAwebComercial.dao.MaterialesDao;
import co.com.CGAwebComercial.dao.SucursalDao;
import co.com.CGAwebComercial.dao.Zona_FuncionarioDao;
import co.com.CGAwebComercial.entyties.EstadoMaterial;
import co.com.CGAwebComercial.entyties.HistoricaMateriales;
import co.com.CGAwebComercial.entyties.Materiales;
import co.com.CGAwebComercial.entyties.Sucursal;
import co.com.CGAwebComercial.entyties.Zona_Funcionario;



@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class MaterialesBean implements Serializable{

	@ManagedProperty("#{autenticacionBean}")
	private AutenticacionBean autenticacion;

	private List<Materiales> listaMateriales;
	private List<Materiales> filtroMateriales;
	private List<Sucursal> listaSucursal;
	private List<EstadoMaterial> listaEstado;

	private Materiales materiales;
	private Sucursal sucursal;

	private String imagen;
	private String camino;
	private StreamedContent ruta;
	private StreamedContent foto;
	

	@PostConstruct
	public void listarMaterial(){

		try{
			autenticacion.registroIngreso(autenticacion.getUsuarioLogin());
			
			imagen = "imagen.png";
			listaMateriales = new ArrayList<>();
			MaterialesDao dao = new MaterialesDao();
			
			System.out.println(autenticacion.getUsuarioLogin().getId());
			if(autenticacion.getUsuarioLogin().getId() == 0){
				listaMateriales = dao.listar();
			}
			else if (autenticacion.getUsuarioLogin().getPerfil().getId() == 23 || autenticacion.getUsuarioLogin().getPerfil().getId() == 24
					|| autenticacion.getUsuarioLogin().getPerfil().getId() == 25 || autenticacion.getUsuarioLogin().getPerfil().getId() == 6 
					|| autenticacion.getUsuarioLogin().getPerfil().getId() == 1 || autenticacion.getUsuarioLogin().getPerfil().getId() == 7
					|| autenticacion.getUsuarioLogin().getPerfil().getId() == 8 || autenticacion.getUsuarioLogin().getPerfil().getId() == 11
					|| autenticacion.getUsuarioLogin().getPerfil().getId() == 20 || autenticacion.getUsuarioLogin().getPerfil().getId() == 26){
				listaMateriales = dao.listar();	
			}
			else if (autenticacion.getUsuarioLogin().getPerfil().getId() == 21) {				
				Zona_FuncionarioDao daoF = new Zona_FuncionarioDao(); 
				Zona_Funcionario zona = daoF.buscarFuncionarioZona(autenticacion.getUsuarioLogin().getId());
				
				SucursalDao daoS = new SucursalDao();	
				Sucursal sucursal = daoS.buscarSucursal(zona.getCiudad().getId());
				listaMateriales = dao.listarMaterialSucursal(sucursal.getId());	
			}			
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Materiales");
		}
	}
	
	public void inicioDashboard(){
		
		try{
			listarMaterial();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se pudo iniciar el dashboard");
		}
		
	}
	
	public StreamedContent getFoto(String camino) throws IOException {
		if(camino == null || camino.isEmpty()){
			Path imagen = Paths.get("C:/imagenesMateriales/acero.jpg");
			InputStream flujo = Files.newInputStream(imagen);
			foto = new DefaultStreamedContent(flujo);			
		}
		else{
			Path imagen = Paths.get(camino);
			InputStream flujo = Files.newInputStream(imagen);
			foto = new DefaultStreamedContent(flujo);	
		}
		return foto;
	}

	public void crearMaterial(){

		try{

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Creo el Material");
		}
	}	
	
	public void editarMaterial(){

		try{
			materiales.setFechaModificacion(new Date());
			MaterialesDao dao = new MaterialesDao();			
			Materiales idMaterial = dao.merge(materiales);	
			
			if(materiales.getNombreImagen() != null){

				String [] extension = materiales.getNombreImagen().split("\\.");			
				Path origen = Paths.get(materiales.getImagen());
				Path destino = Paths.get("C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\excelenciaCGA\\resources\\imagenesMateriales\\" + idMaterial.getId() +"."+ extension[1]);
				//Path destino = Paths.get("C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\prueba\\resources\\imagenesMateriales\\" + idMaterial.getId() +"."+ extension[1]);
				//Path destino = Paths.get("C:\\Users\\opaez\\Desktop\\git\\CGAwebComercialv2\\src\\main\\webapp\\resources\\imagenesMateriales\\" + idMaterial.getId() +"."+ extension[1]);
				//Path destino = Paths.get("C:\\imagenesMateriales\\" + idMaterial +"."+ extension[1]);
				Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);

				idMaterial.setImagen(idMaterial.getId() +"."+ extension[1]);
			}
			else if(idMaterial.getImagen() == null){
					//idMaterial.setNombreImagen("imagen.png");
					//String [] extension = idMaterial.getNombreImagen().split("\\.");
					//Path origen = Paths.get("C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\excelenciaCGA\\resources\\imagenesMateriales\\imagen.png");
					//Path destino = Paths.get("C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\excelenciaCGA\\resources\\imagenesMateriales\\" + idMaterial.getId() +"."+ extension[1]);
					//Path origen = Paths.get("C:\\Users\\opaez\\Desktop\\git\\CGAwebComercialv2\\src\\main\\webapp\\resources\\imagenesMateriales\\imagen.png");
					//Path destino = Paths.get("C:\\Users\\opaez\\Desktop\\git\\CGAwebComercialv2\\src\\main\\webapp\\resources\\imagenesMateriales\\" + idMaterial.getId() +"."+ extension[1]);
					//Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
					//idMaterial.setImagen(idMaterial.getId() +"."+ extension[1]);
					idMaterial.setImagen("imagen.png");
			}
			dao.merge(idMaterial);
			
			HistoricaMateriales hm = new HistoricaMateriales();
			hm.setAncho(idMaterial.getAncho());
			hm.setCodigo(idMaterial.getCodigo());
			hm.setDescripcion(idMaterial.getDescripcion());
			hm.setEspesor(idMaterial.getEspesor());
			hm.setFechaModificacion(new Date());
			hm.setLargo(idMaterial.getLargo());
			hm.setTeorico(idMaterial.getTeorico());
			hm.setUnidad(idMaterial.getUnidad());
			hm.setIdMaterial(idMaterial.getId());
			hm.setImagen(idMaterial.getImagen());
			hm.setSucursal(idMaterial.getSucursal());
			hm.setPedido(idMaterial.getPedido());
			hm.setEstadoMaterial(idMaterial.getEstadoMaterial());
			hm.setUbicacion(idMaterial.getUbicacion());
			hm.setOrdenProducto(idMaterial.getOrdenProducto());
			hm.setAvance(idMaterial.getAvance());
			hm.setUsuario(autenticacion.getUsuarioLogin());

			HistoricoMaterialesDao daoM = new HistoricoMaterialesDao();
			daoM.salvar(hm);
			
			Thread.sleep(3000);

			materiales = new Materiales();
			listarMaterial();
		} catch (RuntimeException | IOException | InterruptedException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se guardo el Material");
		}	
	}

	public void editar(Materiales m){

		try{
			listaSucursal = new ArrayList<>();
			listaEstado = new ArrayList<>();
			materiales = m;

			SucursalDao dao = new SucursalDao();
			listaSucursal = dao.listar();
			
			EstadoMaterialDao daoE = new EstadoMaterialDao();
			listaEstado = daoE.listar();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se edito el Material ");
		}

	}

	public void borrarMaterial(Materiales m){

		try{
			HistoricaMateriales hm = new HistoricaMateriales();
			hm.setAncho(m.getAncho());
			hm.setCodigo(m.getCodigo());
			hm.setDescripcion(m.getDescripcion());
			hm.setEspesor(m.getEspesor());
			hm.setFechaModificacion(new Date());
			hm.setLargo(m.getLargo());
			hm.setTeorico(m.getTeorico());
			hm.setUnidad(m.getUnidad());
			hm.setIdMaterial(m.getId());
			hm.setImagen(m.getImagen());
			hm.setSucursal(m.getSucursal());
			hm.setPedido(m.getPedido());
			hm.setEstadoMaterial(m.getEstadoMaterial());
			hm.setUbicacion(m.getUbicacion());
			hm.setOrdenProducto(m.getOrdenProducto());
			hm.setAvance(m.getAvance());
			hm.setUsuario(autenticacion.getUsuarioLogin());

			HistoricoMaterialesDao daoM = new HistoricoMaterialesDao();
			daoM.salvar(hm);			

			materiales = m;			
			MaterialesDao dao = new MaterialesDao();
			dao.borrar(materiales);
			
			if(materiales.getImagen().equals("imagen.png")){
				
			}
			else{
				Path archivo = Paths.get("C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\excelenciaCGA\\resources\\imagenesMateriales\\" + materiales.getImagen());
				//Path archivo = Paths.get("C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\prueba\\resources\\imagenesMateriales\\"  + materiales.getImagen());
				//Path archivo = Paths.get("C:\\Users\\opaez\\Desktop\\git\\CGAwebComercialv2\\src\\main\\webapp\\resources\\imagenesMateriales\\"  + materiales.getImagen());
				Files.deleteIfExists(archivo);			
			}
			
			materiales = new Materiales();
			listarMaterial();
		} catch (RuntimeException | IOException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se borro el Material");
		}
	}

	public void cargarMaterial(){

		try{
			materiales = new Materiales();
			listaSucursal = new ArrayList<>();			

			SucursalDao dao = new SucursalDao();
			listaSucursal = dao.listar();			
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se cargo el Material");
		}

	}

	public void upload(FileUploadEvent evento){

		try {
			UploadedFile archivo = evento.getFile();
			Path temp = Files.createTempFile(null, null);
			Files.copy(archivo.getInputstream(), temp, StandardCopyOption.REPLACE_EXISTING);
			
			materiales.setImagen(temp.toString());
			materiales.setNombreImagen(evento.getFile().getFileName());			
		} catch (IOException erro) {
			Messages.addFlashGlobalError("Error al subir el archivo");
			erro.printStackTrace();
		}
	}
	
	public void mostrarImagen(String ima){
		
		try{
			imagen = ima;			
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Error al mostrar la imagen");
			erro.printStackTrace();
		}
	}
	
	public void calcularPeso(){
		
		try{
			//materiales = m;
			
//			materiales.setUnidad(materiales.getUnidad());
//			materiales.setEspesor(materiales.getEspesor());
//			materiales.setAncho(materiales.getAncho());
//			materiales.setLargo(materiales.getLargo());			
//			if(materiales.getEspesor() != null){
//				materiales.setEspesor(materiales.getEspesor());
//				System.out.println(materiales.getEspesor()+ " A " + materiales.getUnidad()); 
//			}	
//			else if(materiales.getAncho() != null){
//				materiales.setAncho(materiales.getAncho());
//				System.out.println(materiales.getAncho()+ " B " + materiales.getUnidad());
//			}	
//			else if(materiales.getLargo() != null){
//			    materiales.setLargo(materiales.getLargo());
//			    System.out.println(materiales.getLargo()+ " B " + materiales.getUnidad());
//			}    
//			else 
			if(materiales.getUnidad() > 0){				
				//materiales.setTeorico(materiales.getAncho().multiply(materiales.getLargo().multiply(materiales.getEspesor().multiply(new BigDecimal(materiales.getUnidad()).multiply(new BigDecimal("0.00000785"))))));
				System.out.println(materiales.getUnidad() + " ** " + materiales.getTeorico());
			}	
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Error al mostrar la imagen");
			erro.printStackTrace();
		}
		
	}
	
	public void valueChangeListenerMethod(ValueChangeEvent e) {
		System.out.println("valueChangeListener invoked:" 
                + " OLD: " + e.getOldValue() 
                + " NEW: " + e.getNewValue());
	}
	
	
	
	public String cambioPagina(){

		try{
			if(autenticacion.getUsuarioLogin().getPerfil().getId() == 23){
				System.out.println(autenticacion.getUsuarioLogin().getPerfil().getId());
				listarMaterial();
			}
			else{
			//Thread.sleep(4000);
				Faces.redirect("./pages/iv/cargaTrabajo.xhtml");
			}
			//Faces.redirect("./pages/of/ofertaPedido2.xhtml");
			return "of/materiales.xhtml?faces-redirect=true";
		} catch (RuntimeException | IOException  ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error No se realizo la Gráfica de Gestion de llamadas");
		}
		return null;
	}
	
	public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);
         
        HSSFCellStyle cellStyle = wb.createCellStyle();  
        cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
         
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            HSSFCell cell = header.getCell(i);
             
            cell.setCellStyle(cellStyle);
        }
    }
	
	

	public AutenticacionBean getAutenticacion() {
		return autenticacion;
	}

	public void setAutenticacion(AutenticacionBean autenticacion) {
		this.autenticacion = autenticacion;
	}

	public List<Materiales> getListaMateriales() {
		return listaMateriales;
	}

	public void setListaMateriales(List<Materiales> listaMateriales) {
		this.listaMateriales = listaMateriales;
	}

	public List<Materiales> getFiltroMateriales() {
		return filtroMateriales;
	}

	public void setFiltroMateriales(List<Materiales> filtroMateriales) {
		this.filtroMateriales = filtroMateriales;
	}

	public Materiales getMateriales() {
		return materiales;
	}

	public void setMateriales(Materiales materiales) {
		this.materiales = materiales;
	}

	public List<Sucursal> getListaSucursal() {
		return listaSucursal;
	}

	public void setListaSucursal(List<Sucursal> listaSucursal) {
		this.listaSucursal = listaSucursal;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public StreamedContent getRuta() {
		return ruta;
	}

	public void setRuta(StreamedContent ruta) {
		this.ruta = ruta;
	}

	public StreamedContent getFoto() throws IOException {
		if(camino == null || camino.isEmpty()){
			Path imagen = Paths.get("C:/imagenesMateriales/acero.jpg");
			InputStream flujo = Files.newInputStream(imagen);
			foto = new DefaultStreamedContent(flujo);			
			
		}
		else{
			Path imagen = Paths.get(camino);
			InputStream flujo = Files.newInputStream(imagen);
			foto = new DefaultStreamedContent(flujo);	
		}
		return foto;		
	}

	public void setFoto(StreamedContent foto) {
		this.foto = foto;
	}

	public String getCamino() {
		return camino;
	}

	public void setCamino(String camino) {
		this.camino = camino;
	}

	public List<EstadoMaterial> getListaEstado() {
		return listaEstado;
	}

	public void setListaEstado(List<EstadoMaterial> listaEstado) {
		this.listaEstado = listaEstado;
	}
	
}
