package co.com.CGAwebComercial.test;

import org.junit.Test;

import co.com.CGAwebComercial.dao.LiquidacionDao;



public class LiquidacionTest {

	@Test
	public void sumLiquidacion(){

		LiquidacionDao dao = new LiquidacionDao();
		dao.listaCod("04" , "2016");
		//dao.listaLiquidacionSum(94, "04", "2016");
//		System.out.println(li.toString());
//		for (Liquidacion liquidacion : li) {
//			System.out.println(liquidacion.toString()+ "&&&");
//		}List<Liquidacion> li =

	}
}
