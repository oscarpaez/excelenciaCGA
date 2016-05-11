package co.com.CGAwebComercial.test;

import java.util.Date;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Ignore;
import org.junit.Test;

import co.com.CGAwebComercial.dao.PersonaDao;
import co.com.CGAwebComercial.dao.UsuarioDao;
import co.com.CGAwebComercial.entyties.Persona;
import co.com.CGAwebComercial.entyties.Usuario;

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
	
	public void autenticar(){
		
		int usuario = 8126091;
		String clave = "123456";
		
	    UsuarioDao dao = new UsuarioDao();
	    Usuario usuario1 = dao.autenticar(usuario, clave);
		
		System.out.println("Usuario:" + usuario1.getPersona().getNombre());
	}
}
