package co.com.CGAwebComercial.test;


import java.math.BigDecimal;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import co.com.CGAwebComercial.dao.PresupuestoDao;
import co.com.CGAwebComercial.dao.RecaudoDao;

public class RecaudoTest {

	@Test
	@Ignore
	public void sumaRecaudo() {
		
		RecaudoDao dao = new RecaudoDao();
		List<Long> ValorNeto =dao.listarDetalleValorNeto(1683);
		for (Long long1 : ValorNeto) {
			System.out.println("VVVVVVV" +long1);
		}
	}
	
	@Test
	@Ignore
	public void sumaPre() {
		PresupuestoDao daoP = new PresupuestoDao();
		BigDecimal pre = daoP.datoPorLineaSumFechas(166, "03","2016");
		
		System.out.println( "@@@@@+++" + pre);
	}
	
	@Test
	@Ignore
	public void listarSucursales(){
		
		RecaudoDao daoP = new RecaudoDao();
		daoP.carteraInternos("02", "2016");
		
	}
	
	@Test
	public void sumaPrepuesto() {
		PresupuestoDao daoP = new PresupuestoDao();
		BigDecimal pre = daoP.datoPorLineaSum(184);
		
		System.out.println( "@@@@@+++" + pre);
	}
}
