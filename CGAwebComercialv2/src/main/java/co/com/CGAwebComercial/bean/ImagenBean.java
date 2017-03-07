package co.com.CGAwebComercial.bean;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@RequestScoped
public class ImagenBean {
	
	@ManagedProperty("#{param.camino}")
	private String camino;
	private StreamedContent foto;
	
	public String getCamino() {
		return camino;
	}
	public void setCamino(String camino) {
		this.camino = camino;
	}
	public StreamedContent getFoto() throws IOException {
		if(camino == null || camino.isEmpty()){
			Path imagen = Paths.get("C:/Users/opaez/Desktop/git/CGAwebComercialv2/src/main/webapp/resources/imagenesMateriales/imagen.png");
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

}
