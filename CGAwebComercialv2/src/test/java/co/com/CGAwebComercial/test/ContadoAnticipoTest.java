package co.com.CGAwebComercial.test;


import org.junit.Test;

import co.com.CGAwebComercial.dao.ContadoAnticipoDao;

public class ContadoAnticipoTest {
	
	@Test
	public void listaContado(){
		
		ContadoAnticipoDao dao = new ContadoAnticipoDao();
		dao.listaContadoEspecialista(219, "03", "2016");
		
	}
}
