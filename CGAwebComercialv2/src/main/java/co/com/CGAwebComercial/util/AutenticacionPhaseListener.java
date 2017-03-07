package co.com.CGAwebComercial.util;

import java.util.Map;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import co.com.CGAwebComercial.bean.AutenticacionBean;
import co.com.CGAwebComercial.entyties.Usuario;

@SuppressWarnings("serial")
public class AutenticacionPhaseListener implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent event) {
		
		boolean esPaginaActual;
		FacesContext context = event.getFacesContext();
		UIViewRoot uiViewRoot = context.getViewRoot();
		String paginaActual = uiViewRoot.getViewId();
		
		esPaginaActual = paginaActual.contains("ad/actualizacionDatos.xhtml");
		
		if(!esPaginaActual )
			esPaginaActual = paginaActual.contains("ma/kpi.xhtml");
		
		if(!esPaginaActual)
			esPaginaActual = paginaActual.contains("iv/materiales.xhtml");
		
		if(!esPaginaActual)
			esPaginaActual = paginaActual.contains("iv/cargaTrabajo.xhtml");
		
		if(!esPaginaActual){
			   esPaginaActual = paginaActual.contains("login.xhtml");
		}
		
		if(!esPaginaActual){
			ExternalContext exContext = context.getExternalContext();
			Map<String, Object> map = exContext.getSessionMap();
			AutenticacionBean autenticacion = (AutenticacionBean) map.get("autenticacionBean");
			Usuario usuario = autenticacion.getUsuarioLogin();
			
			if(usuario.getPerfil() == null){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("El usuario no esta autenticado ", ""));
				Application application = context.getApplication();
				NavigationHandler navigation = application.getNavigationHandler();
				navigation.handleNavigation(context, null, "/pages/login.xhtml?faces-redirect=true");		
			}
		}
		
		
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PhaseId getPhaseId() {
		
		return PhaseId.RESTORE_VIEW;
	}

}
