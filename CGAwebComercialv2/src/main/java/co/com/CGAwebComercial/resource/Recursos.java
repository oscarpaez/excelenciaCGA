package co.com.CGAwebComercial.resource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import co.com.CGAwebComercial.util.Fechas;

public class Recursos {


	private List<Fechas> listaFechas;

	public List<Fechas> cargarFechas(){

		listaFechas = new ArrayList<>();	
		Fechas fechas = new Fechas();
		/*
		fechas.setValorMes("01");
		fechas.setMes( "Enero");
		listaFechas.add(0,fechas);

		fechas = new Fechas();
		fechas.setValorMes("02");
		fechas.setMes( "Febrero");
		listaFechas.add(1,fechas);

		fechas = new Fechas();
		fechas.setValorMes("03");
		fechas.setMes( "Marzo");
		listaFechas.add(fechas);

		fechas = new Fechas();
		fechas.setValorMes("04");
		fechas.setMes( "Abril");	       
		listaFechas.add(fechas);

		fechas = new Fechas();
		fechas.setValorMes("05");
		fechas.setMes( "Mayo");
		listaFechas.add(fechas);

		fechas = new Fechas();
		fechas.setValorMes("06");
		fechas.setMes( "Junio");
		listaFechas.add(fechas);

		fechas = new Fechas();
		fechas.setValorMes("07");
		fechas.setMes( "Julio");
		listaFechas.add(fechas);*/

		fechas = new Fechas();
		fechas.setValorMes("08");
		fechas.setMes( "Agosto");
		listaFechas.add(fechas);

		fechas = new Fechas();
		fechas.setValorMes("09");
		fechas.setMes( "Septiembre");
		listaFechas.add(fechas);

		fechas = new Fechas();
		fechas.setValorMes("10");
		fechas.setMes( "Octubre");
		listaFechas.add(fechas);

		fechas = new Fechas();
		fechas.setValorMes("11");
		fechas.setMes( "Noviembre");
		listaFechas.add(fechas);

		fechas = new Fechas();
		fechas.setValorMes("12");
		fechas.setMes( "Diciembre");
		listaFechas.add(fechas);
		
		return listaFechas;
	}
	
	public List<Fechas> cargarFechasTotal(){

		listaFechas = new ArrayList<>();	
		Fechas fechas = new Fechas();
		
		fechas.setValorMes("01");
		fechas.setMes( "Enero");
		listaFechas.add(0,fechas);

		fechas = new Fechas();
		fechas.setValorMes("02");
		fechas.setMes( "Febrero");
		listaFechas.add(1,fechas);

		fechas = new Fechas();
		fechas.setValorMes("03");
		fechas.setMes( "Marzo");
		listaFechas.add(fechas);

		fechas = new Fechas();
		fechas.setValorMes("04");
		fechas.setMes( "Abril");	       
		listaFechas.add(fechas);

		fechas = new Fechas();
		fechas.setValorMes("05");
		fechas.setMes( "Mayo");
		listaFechas.add(fechas);

		fechas = new Fechas();
		fechas.setValorMes("06");
		fechas.setMes( "Junio");
		listaFechas.add(fechas);

		fechas = new Fechas();
		fechas.setValorMes("07");
		fechas.setMes( "Julio");
		listaFechas.add(fechas);

		fechas = new Fechas();
		fechas.setValorMes("08");
		fechas.setMes( "Agosto");
		listaFechas.add(fechas);

		fechas = new Fechas();
		fechas.setValorMes("09");
		fechas.setMes( "Septiembre");
		listaFechas.add(fechas);

		fechas = new Fechas();
		fechas.setValorMes("10");
		fechas.setMes( "Octubre");
		listaFechas.add(fechas);

		fechas = new Fechas();
		fechas.setValorMes("11");
		fechas.setMes( "Noviembre");
		listaFechas.add(fechas);

		fechas = new Fechas();
		fechas.setValorMes("12");
		fechas.setMes( "Diciembre");
		listaFechas.add(fechas);
		
		return listaFechas;
	}

	public int idOficina(int idCiudad){

		int oficina = 0;
		try{
			oficina = (idCiudad== 1 )? 1000 : (idCiudad == 7 )? 2000 : (idCiudad+1)*1000 ;

			return oficina;
		} catch (RuntimeException ex) {
			ex.printStackTrace();
		}
		return oficina;
	}
	
	public int yearActual(){
		
		Calendar fechas = Calendar.getInstance();
		int year = fechas.get(Calendar.YEAR);
		return year;
	}
}
