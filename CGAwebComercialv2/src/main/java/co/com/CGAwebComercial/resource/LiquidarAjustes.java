package co.com.CGAwebComercial.resource;

import co.com.CGAwebComercial.entyties.Liquidacion;

public class LiquidarAjustes {
	
	
	public Liquidacion liquidar(Liquidacion vendedor, String fechaBusqueda, String fechaBusquedaYear, String concepto){

		try{
			Liquidacion liquidacion = new Liquidacion();
			liquidacion.setCodSap(vendedor.getCodSap());
			liquidacion.setNombre(vendedor.getNombre());
			liquidacion.setPeriodo(Integer.parseInt(fechaBusqueda));
			liquidacion.setEjercicio(Integer.parseInt(fechaBusquedaYear));
			liquidacion.setConcepto(vendedor.getConcepto());
			liquidacion.setValorAjuste(0);
			liquidacion.setValorComision(vendedor.getValorComision());
			liquidacion.setValorTotal(vendedor.getValorComision());
			
			return liquidacion;
		} catch (RuntimeException ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
