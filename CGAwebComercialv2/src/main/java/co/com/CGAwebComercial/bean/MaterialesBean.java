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

import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import co.com.CGAwebComercial.dao.HistoricoMaterialesDao;
import co.com.CGAwebComercial.dao.MaterialesDao;
import co.com.CGAwebComercial.dao.SucursalDao;
import co.com.CGAwebComercial.dao.Zona_FuncionarioDao;
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

	private Materiales materiales;
	private Sucursal sucursal;

	private String imagen;
	private String camino;
	private StreamedContent ruta;
	private StreamedContent foto;
	

	@PostConstruct
	public void listarMaterial(){

		try{
			imagen = "imagen.png";
			listaMateriales = new ArrayList<>();
			MaterialesDao dao = new MaterialesDao();

			if (autenticacion.getUsuarioLogin().getPerfil() == null){
				System.out.println("##");
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
			
			System.out.println(materiales.getNombreImagen() + " Nombre original"  );
			
			if(materiales.getNombreImagen() != null){

				String [] extension = materiales.getNombreImagen().split("\\.");			
				System.out.println(extension.length);
				Path origen = Paths.get(materiales.getImagen());
				Path destino = Paths.get("C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\prueba\\resources\\imagenesMateriales\\" + idMaterial.getId() +"."+ extension[1]);
				//Path destino = Paths.get("C:\\Users\\opaez\\Desktop\\git\\CGAwebComercialv2\\src\\main\\webapp\\resources\\imagenesMateriales\\" + idMaterial.getId() +"."+ extension[1]);
				//Path destino = Paths.get("C:\\imagenesMateriales\\" + idMaterial +"."+ extension[1]);
				Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);

				System.out.println(destino + "Nombre de la imagen destino"  );
				idMaterial.setImagen(idMaterial.getId() +"."+ extension[1]);
			}
			dao.merge(idMaterial);
			
			HistoricaMateriales hm = new HistoricaMateriales();
			hm.setAncho(materiales.getAncho());
			hm.setCodigo(materiales.getCodigo());
			hm.setDescripcion(materiales.getDescripcion());
			hm.setEspesor(materiales.getEspesor());
			hm.setFechaModificacion(new Date());
			hm.setLargo(materiales.getLargo());
			hm.setTeorico(materiales.getTeorico());
			hm.setUnidad(materiales.getUnidad());
			hm.setIdMaterial(idMaterial.getId());
			hm.setImagen(materiales.getImagen());
			hm.setSucursal(materiales.getSucursal());

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
			materiales = m;

			SucursalDao dao = new SucursalDao();
			listaSucursal = dao.listar();
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

			HistoricoMaterialesDao daoM = new HistoricoMaterialesDao();
			daoM.salvar(hm);			

			materiales = m;			
			MaterialesDao dao = new MaterialesDao();
			dao.borrar(materiales);
			
			Path archivo = Paths.get("C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\prueba\\resources\\imagenesMateriales\\"  + materiales.getImagen());
			//Path archivo = Paths.get("C:\\Users\\opaez\\Desktop\\git\\CGAwebComercialv2\\src\\main\\webapp\\resources\\imagenesMateriales\\"  + materiales.getImagen());
			Files.deleteIfExists(archivo);			

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
			System.out.println(camino + "2222");
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
	
}
