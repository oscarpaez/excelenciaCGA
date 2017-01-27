package co.com.CGAwebComercial.util;

//import java.util.List;

import org.omnifaces.util.Messages;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
/*
import co.com.CGAwebComercial.dao.CausaPerdidaVentaDao;
import co.com.CGAwebComercial.dao.FuncionarioDao;
import co.com.CGAwebComercial.dao.IncidenciaDao;
import co.com.CGAwebComercial.dao.LineaDao;
import co.com.CGAwebComercial.dao.OfertasPedidosDao;
import co.com.CGAwebComercial.dao.RegularidadPerdidaVentaDao;
import co.com.CGAwebComercial.dao.SucursalDao;
import co.com.CGAwebComercial.entyties.CausaPerdidaVenta;
import co.com.CGAwebComercial.entyties.Funcionario;
import co.com.CGAwebComercial.entyties.Incidencia;
import co.com.CGAwebComercial.entyties.Linea;
import co.com.CGAwebComercial.entyties.OfertasPedidos;
import co.com.CGAwebComercial.entyties.RegularidadPerdidaVenta;
import co.com.CGAwebComercial.entyties.Sucursal;*/


public class ActualizarOfertas implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		// TODO Auto-generated method stub
		try{
			/*
			IncidenciaDao dao = new IncidenciaDao();
			List<Incidencia> listaI = dao.listarIncidenciaSi();

			OfertasPedidosDao daoF = new OfertasPedidosDao();

			for (Incidencia incidencia : listaI) {
				List<OfertasPedidos> listaOferta = daoF.listaBusquedaOferta(incidencia.getNoferta());
				for (OfertasPedidos oferta : listaOferta) {
					if(oferta.getnPedido() == 0){
						oferta.setOportunidadNeg(incidencia.getProbabilidadConNeg());
						daoF.merge(oferta);
					}
				}
				System.out.println("Oferta:" + incidencia.getNoferta());
			}

			listaI = dao.listarIncidenciaNO();
			System.out.println("Archivo: No" + listaI.size());
			for (Incidencia incidencia : listaI) {			
				List<OfertasPedidos> listaOferta = daoF.listaBusquedaOferta(incidencia.getNoferta());
				for (OfertasPedidos oferta : listaOferta) {
					if(oferta.getnPedido() == 0){
						oferta.setOfertaPerdida(1);
						daoF.merge(oferta);
					}
				}

			}

			List<OfertasPedidos> listaOferta = daoF.listaOfertaMotivoR();
			System.out.println(listaOferta.size() + "XXXWWW");
			for (OfertasPedidos oferta : listaOferta) {
				IncidenciaDao daoI = new IncidenciaDao();
//				List<Incidencia> listaIn = daoI.buscarOferta(oferta.getnOferta());
//				System.out.println(listaIn.size() + "WWW");
//				if(listaIn.size() < 1){
					Incidencia incidencia = new Incidencia();
					incidencia.setCliente(oferta.getCliente());
					incidencia.setFechaRegistro(oferta.getFechaCreOfe());
					incidencia.setResultaNegocio("No");
					incidencia.setValorVenta(oferta.getValorOferta().longValue());
					CausaPerdidaVentaDao daoC = new CausaPerdidaVentaDao();
					CausaPerdidaVenta causa = (oferta.getMotivoRechazo() == 0)? daoC.buscar(oferta.getMotivoRechazoPed()) : daoC.buscar(oferta.getMotivoRechazo());
					causa = (causa == null)? daoC.buscar(69): causa;
					incidencia.setCausaPerdida(causa);

//					System.out.println(oferta.getCodEspecialista()+ "Especialista 99");
//					Zona_ventaDao daoZ = new Zona_ventaDao(); 
//					List<Zona_venta>  listaZ = daoZ.buscarZona(oferta.getCodEspecialista());
					SucursalDao daoS = new SucursalDao();
					Sucursal sucursal = daoS.buscar(oferta.getCodOficina());
					incidencia.setCiudad(sucursal.getCiudad());
					
					FuncionarioDao daoFu = new FuncionarioDao();					
					Funcionario funcionario = daoFu.buscar(oferta.getCodEspecialista());
					incidencia.setFuncionario(funcionario);

					RegularidadPerdidaVentaDao daoR = new  RegularidadPerdidaVentaDao();
					RegularidadPerdidaVenta regularidad = daoR.buscar(8);
					incidencia.setRegularidad(regularidad);

					funcionario = daoFu.buscar(oferta.getCodInterno());
					incidencia.setFuncionarioI(funcionario);	
					
					LineaDao daoL = new LineaDao();
					Linea linea = daoL.buscar(1);
					incidencia.setLinea(linea);
					
					incidencia.setNoferta(oferta.getnOferta());
					daoI.merge(incidencia);				
//				}
			}*/
			System.out.println("Archivo: Motivo R tarea Programada" );

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error No se cargo la lista de Incidencias");
		}
	}


}
