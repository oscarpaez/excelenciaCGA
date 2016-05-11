package co.com.CGAwebComercial.test;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import co.com.CGAwebComercial.dao.BajaRotacionDao;
import co.com.CGAwebComercial.dao.ContadoAnticipoDao;
import co.com.CGAwebComercial.dao.DetalleDao;
import co.com.CGAwebComercial.dao.RecaudoDao;
import co.com.CGAwebComercial.entyties.OficinaVendedorInterno;

public class DetalleTest {

	@Test
	@Ignore
	public void promedio(){

		DetalleDao dao = new DetalleDao();
		Double promedio = dao.promedioVentas(1683);
		System.out.println(promedio);


	}

	@Test
	@Ignore
	public void sumavalorNeto(){

		//BajaRotacionDao dao = new BajaRotacionDao();
		//List<bajaRotacion> promedio = dao. listarDetalle(1683, "02", "2016");
//		for (bajaRotacion bajaRotacion : promedio) {
//			System.out.println(bajaRotacion.getFactura());
//		}




	}

	@Test
	@Ignore
	public void contado(){

		ContadoAnticipoDao dao = new ContadoAnticipoDao();
		Long promedio = dao.sumaTotalFechas(166, "02", "2016");
		System.out.println(promedio);

	}

	@Test
	@Ignore
	public void suma(){

		BajaRotacionDao dao = new BajaRotacionDao();
		Long promedio = dao.SumaTotalDirector(1, "02", "2016");
		
			System.out.println(promedio);
		
	}
	
	@Test
	@Ignore
	public void listaoficina(){
		
		//RecaudoDao dao = new RecaudoDao();
		
//		List<ComisionVendedores> lista = dao.carteraInternos();
//		for (ComisionVendedores comisionVendedores : lista) {
//			System.out.println(comisionVendedores.getNombre() + "$$$$$$$$$$$$$$$$$$$");
//			System.out.println("id: " + comisionVendedores.getId());
//			System.out.println("real: " + comisionVendedores.getIngresoReal());
//			System.out.println("comision: " + comisionVendedores.getComision());
//			System.out.println("pre: " + comisionVendedores.getPresupuesto());
//			System.out.println("imagen: " + comisionVendedores.getImagen());
//		}
		RecaudoDao daoR = new RecaudoDao();
		List<OficinaVendedorInterno> listaVenInt = daoR. listaVendedorInterno(1000);
		for (OficinaVendedorInterno o : listaVenInt) {
			System.out.println(o.getAsesor());
		}
		
	}
	
	@Test
	@Ignore
	public void sumaContadoAnticipo(){
		
		ContadoAnticipoDao dao = new ContadoAnticipoDao();
		Long valor = dao.sumaTotal(1355);
		System.out.println(valor + "33");
	}
	
	@Test
	public void listargroup(){
		
//		DetalleDao dao = new DetalleDao();
//		Long l = dao.listarDetalleSumaFuncinario("funcionario", 3, 148);
//		System.out.println(l + "$$$$33");
	}

}
