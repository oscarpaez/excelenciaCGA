package co.com.CGAwebComercial.test;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import co.com.CGAwebComercial.dao.Detalle_ventaDao;
import co.com.CGAwebComercial.dao.FuncionarioDao;
import co.com.CGAwebComercial.dao.PersonaDao;
import co.com.CGAwebComercial.entyties.Detalle_venta;
import co.com.CGAwebComercial.entyties.Funcionario;
import co.com.CGAwebComercial.entyties.Persona;

public class FuncionarioTest {
	
	@Test
	@Ignore
	public void buscarPersona(){
		
		FuncionarioDao dao = new FuncionarioDao();
		Funcionario funcionario = dao.buscarPersona(8126091);
		
		System.out.println(funcionario.getId_funcionario() +  "--"+ funcionario.getPersona().getNombre());
	}
	
	@Test
	@Ignore
	public void buscarDetalle(){
		
		Detalle_ventaDao dao = new Detalle_ventaDao();
		List<Detalle_venta> detalle = dao.listarDetalle(1, 1683);
		System.out.println("tama"+ detalle.size());
		for (Detalle_venta detalle_venta : detalle) {
			System.out.println("Linea: -"+ detalle_venta.getLinea().getNombre());
		}
		
	} 
	@Test
	@Ignore
	public void listarPersona(){
	
		PersonaDao dao = new PersonaDao();
		List<Persona> listaPersona = dao.listar();
		
		for (Persona persona : listaPersona) {
			System.out.println("Cedula >" + persona.getCedula() + "Nombre >" + persona.getNombre());
		}
	}
	
	@Test
	@Ignore
	public void listarVendedorComision(){
	
		FuncionarioDao dao = new FuncionarioDao();
		List<Funcionario> fun =	dao.listarVendedores();
		
		for (Funcionario funcionario : fun) {
			System.out.println(funcionario.getPersona().getNombre());
		}
	}
	
	@Test
	public void busPersona(){
		
		FuncionarioDao dao = new FuncionarioDao();
		Funcionario fun = dao.buscar(2591);
		System.out.println("###"  +fun);
	}
}
