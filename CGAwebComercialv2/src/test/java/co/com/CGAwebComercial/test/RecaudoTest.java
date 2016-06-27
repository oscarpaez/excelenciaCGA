package co.com.CGAwebComercial.test;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import co.com.CGAwebComercial.dao.PresupuestoDao;
import co.com.CGAwebComercial.dao.RecaudoDao;
import co.com.CGAwebComercial.dao.Zona_ventaDao;
import co.com.CGAwebComercial.entyties.Recaudo;
import co.com.CGAwebComercial.entyties.Zona_venta;

public class RecaudoTest {

	@Test
	@Ignore
	public void sumaRecaudo() {
		
		RecaudoDao dao = new RecaudoDao();
		List<Long> ValorNeto =dao.listarDetalleValorNeto("",1683);
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
	@Ignore
	public void sumaPrepuesto() {
		PresupuestoDao daoP = new PresupuestoDao();
		BigDecimal pre = daoP.datoPorLineaSum(184);
		
		System.out.println( "@@@@@+++" + pre);
	}
	
	@Test
	@Ignore
	public void sumaDes() {
		RecaudoDao dao = new RecaudoDao();
		List<Long> lista = dao.listarDetalleValorDescuento("",1683);
		for (Long long1 : lista) {
			System.out.println( "@@@@@+++" + long1.intValue());
		}
		
	}
	
	@Test
	@Ignore
	public void recaudoInternos() {
		RecaudoDao dao = new RecaudoDao();
		List<Recaudo> lista = dao.carteraInternosGraficas(116);
		for (Recaudo long1 : lista) {
			System.out.println(lista.size() + "@@@@@+++" + long1.getPresupuesto());
		}
		
	}
	
	@Test
	@Ignore
	public void sumalineasPais() {
		PresupuestoDao daoP = new PresupuestoDao();
		Date i = daoP.fechaInicial("04","2016");
		Date f = daoP.fechaFinal("04","2016");
		List<BigDecimal> l = daoP.datoPorLineaPais(1, 1000, i, f);
		
		for (BigDecimal long1 : l) {
			System.out.println( "@@@@@+++" + long1);
		}
	}
	
	
	@Test
	@Ignore
	public void listaDeZona(){
		Zona_ventaDao daoZ = new Zona_ventaDao();
		List <Zona_venta> listaV = daoZ.buscarZonaSucursal(1);
		
		for (Zona_venta zona_venta : listaV) {
			System.out.println(zona_venta.getFuncionario().getId_funcionario());
		}
	}
	
	@Test
	public void recaudoPaisPeriodosAn(){
		
		RecaudoDao dao = new RecaudoDao();
		dao.recaudoPaisPeriodosAn();
	}
}
