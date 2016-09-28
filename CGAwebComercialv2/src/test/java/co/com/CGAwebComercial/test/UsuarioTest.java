package co.com.CGAwebComercial.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Ignore;
import org.junit.Test;

import co.com.CGAwebComercial.bean.directorGBean;
import co.com.CGAwebComercial.dao.DetalleDao;
import co.com.CGAwebComercial.dao.FuncionarioDao;
import co.com.CGAwebComercial.dao.LineaDao;
import co.com.CGAwebComercial.dao.PersonaDao;
import co.com.CGAwebComercial.dao.Registro_IngresosDao;
import co.com.CGAwebComercial.dao.UsuarioDao;
import co.com.CGAwebComercial.dao.Zona_ventaDao;
import co.com.CGAwebComercial.entyties.Detalle;
import co.com.CGAwebComercial.entyties.Funcionario;
import co.com.CGAwebComercial.entyties.Persona;
import co.com.CGAwebComercial.entyties.Registro_Ingresos;
import co.com.CGAwebComercial.entyties.Usuario;
import co.com.CGAwebComercial.entyties.Zona_venta;
import co.com.CGAwebComercial.util.ComisionVendedores;

public class UsuarioTest {
	
	@Test
	@Ignore
	public void salvar(){
		
		PersonaDao daoP = new PersonaDao();
		//Long codigo = (Long) Long.parseLong("1");
		//System.out.println(codigo);
		int codigo = (int) Integer.parseInt("10283817");
		Persona persona = daoP.buscar(codigo);
			
		
		Usuario usuario = new Usuario();
		usuario.setUsuario("Dia@CGA");
		usuario.setClaveCritografia("123456");
		SimpleHash hash = new SimpleHash("md5", usuario.getClaveCritografia());
		usuario.setClave(hash.toHex());		
		usuario.setPersona(persona);
		usuario.setFecha_Creacion(new Date());
		
		UsuarioDao dao = new UsuarioDao();
		dao.salvar(usuario);
		
		//System.out.println(persona.getNombre());
	}
	
	@Test
	@Ignore
	public void autenticar(){
		
		int usuario = 8126091;
		String clave = "123456";
		
	    UsuarioDao dao = new UsuarioDao();
	    Usuario usuario1 = dao.autenticar(usuario, clave);
		
		System.out.println("Usuario:" + usuario1.getPersona().getNombre());
	}
	
	@Test
	@Ignore
	public void listarVenDir(){
		
		Zona_ventaDao daoV = new Zona_ventaDao();
		List<Zona_venta> ListaZona = daoV.buscarZonaSucursal(1);
//		for (Zona_venta zona_venta : ListaZona) {
//			System.out.println(zona_venta.getId_zona_venta());
//		}
		FuncionarioDao daoF = new FuncionarioDao();
		List<Funcionario> listaVendedor  = daoF.listarVendedoresParaDirector(ListaZona);
		System.out.println(listaVendedor.size());
		for (Funcionario funcionario : listaVendedor) {
			System.out.println("Usuario:" + funcionario.getId_funcionario()+ "  " +funcionario.getPersona().getNombre());
		}
	}
	
	@Test
	@Ignore
	public void listarComisionGerente(){
		
		directorGBean grb = new directorGBean();
		grb.listarComisionGerente();
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	@Ignore
	public void listarF(){
		
//		LineaDao dao = new LineaDao();
//		dao.listarPru("01", "2016");
		
		FuncionarioDao dao = new FuncionarioDao();
		List results = dao.listarVendedoresPais("funcionario","01", "2016");
		System.out.println(results.size());
		int i = 0;
		List<ComisionVendedores> ListaComisionVendedores = new ArrayList<>();
		for (Iterator iterator = results.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			int d = (int) object;
			System.out.println(d +" "+object + "- " + i++);
			LineaDao daoL = new LineaDao();
			ComisionVendedores vendedores = new ComisionVendedores();
			vendedores = daoL.listarVendedoresPorPais("funcionario", d, "01",  "2016");	
			System.out.println(vendedores.getCedula());
			ListaComisionVendedores.add(vendedores);
		}
		
	}
	
	@Test
	@Ignore
	public void listar(){

//		FuncionarioDao dao = new FuncionarioDao();
//		dao.listarVendedoresPais("funcionario","04", "2016");
		
		DetalleDao daoD = new DetalleDao();
		List<Detalle> detalle = daoD.fucionarioPais("funcionario", 406);
		System.out.println(detalle.get(0).getCedulaEspecialista());
//		for (Detalle detalle2 : detalle) {
//			System.out.println("U" + detalle2.getCedulaEspecialista());
//		}
	}
	
	@Test
	public void listaIngresos(){
		
		Registro_IngresosDao dao = new Registro_IngresosDao();
		
		List<Registro_Ingresos> listaR = dao.listaIngresos("09", "2016");
		System.out.println(listaR);
		for (Registro_Ingresos r : listaR) {
			System.out.println(r.getPersona().getNombre());			
		}
	}
	
	
	
}
