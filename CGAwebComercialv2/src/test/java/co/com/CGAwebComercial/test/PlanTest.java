package co.com.CGAwebComercial.test;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import co.com.CGAwebComercial.bean.GraficasBean;
import co.com.CGAwebComercial.dao.PlanDao;
import co.com.CGAwebComercial.dao.RecaudoDao;
import co.com.CGAwebComercial.dao.UsuarioDao;
import co.com.CGAwebComercial.entyties.Plan;
import co.com.CGAwebComercial.entyties.Recaudo;
import co.com.CGAwebComercial.entyties.Usuario;

public class PlanTest {
	
	@Test
	@Ignore
	public void listar(){
		
		int usuario = 8126091;
		String clave = "123456";
		
	    UsuarioDao dao = new UsuarioDao();
	    Usuario usuario1 = dao.autenticar(usuario, clave);
	    
	    
		
		PlanDao daoP = new PlanDao();
		List<Plan> listaPlan = daoP.listarItems(usuario1);
		
		for (Plan plan : listaPlan) {
			System.out.println("nombre: -"+ plan.getLinea().getNombre());
		}
	}
	
	@Test
	@Ignore
	public void recaudo(){
		int usuario = 8126091;
		RecaudoDao dao = new RecaudoDao();
		List<Recaudo> listaRecaudo = dao.listarRecaudo(usuario );
		
		for (Recaudo recaudo : listaRecaudo) {
			System.out.println("recaduo" + recaudo.getZonaVenta().getId_zona_venta()  + recaudo.getPresupuesto());
		}
	}
	
	@Test
	@Ignore
	public void detalleLista(){
		//int usuario = 94451204;
		
//		PresupuestoDao dao = new PresupuestoDao();
//		List<Presupuesto> pre = dao.datoPorLinea(1, usuario);
//		
//		for (Presupuesto presupuesto : pre) {
//			System.out.println(presupuesto.getIngresos()+ "ingre");
//		}
		/*RecaudoDao dao = new RecaudoDao();
		List<Recaudo> listaRecaudo = dao.listarPresupuesto(usuario);
		for (Recaudo recaudo : listaRecaudo) {
			System.out.println(recaudo.getFecha() + 
					recaudo.getImagen() +
					recaudo.getCumplimiento() +
					recaudo.getValorComision() ); 
		}*/
	}
	
	@Test
	public void mes(){
		GraficasBean a = new GraficasBean();
		a.mesActualG();
	}
	
	
}
