package co.com.CGAwebComercial.resource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.omnifaces.util.Messages;

import co.com.CGAwebComercial.dao.GerentesDao;
import co.com.CGAwebComercial.util.Fechas;

public class Recursos {


	private List<Fechas> listaFechas;

	public List<Fechas> cargarFechas(){

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
	
	public int idOficinaDivsion(int idCiudad){

		int oficina = 0;
		try{
			oficina = (idCiudad == 1000 )? 1 : (idCiudad == 7000 )? 2 : (idCiudad - 1000)/1000 ;

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

	public String mesActualG(){

		String periodoM = "";
		try{
			GerentesDao daoG = new GerentesDao();
			Date fechaActual = daoG.fechaFinal();
			DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
			String fechaConverdita = formatoFecha.format(fechaActual); 
			String diaArray[] = fechaConverdita.split("-");
			for (String string : diaArray) {
				System.out.println(string);
			}
			int mes = Integer.parseInt("" + diaArray[1]);
			System.out.println(mes);
			String[] periodo = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
					"Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

			periodoM = periodo[mes -1];
			return periodoM;

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo el mes Actual");
		}
		return periodoM;
	}
	
	public int ultimoDiaMes(int year, int mes){
			
		int dia = 0;
		try{
			
			Calendar calendario=Calendar.getInstance();
			calendario.set(year, mes, 1);
			dia = calendario.getActualMaximum(Calendar.DAY_OF_MONTH);
			System.out.println(dia);
			return dia;
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
		return dia;
		
		
	}
}
