package co.com.CGAwebComercial.bean;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import co.com.CGAwebComercial.dao.BajaRotacionDao;
import co.com.CGAwebComercial.dao.DetallesinDao;
import co.com.CGAwebComercial.dao.FuncionarioDao;
import co.com.CGAwebComercial.dao.LineaDao;
import co.com.CGAwebComercial.dao.LiquidacionDao;
import co.com.CGAwebComercial.dao.AjusteDao;
import co.com.CGAwebComercial.dao.AjusteVendedorDao;
import co.com.CGAwebComercial.entyties.Ajuste;
import co.com.CGAwebComercial.entyties.AjusteVendedor;
import co.com.CGAwebComercial.entyties.Cliente;
import co.com.CGAwebComercial.entyties.Funcionario;
import co.com.CGAwebComercial.entyties.Linea;
import co.com.CGAwebComercial.entyties.Liquidacion;
import co.com.CGAwebComercial.entyties.Persona;
import co.com.CGAwebComercial.entyties.bajaRotacion;
import co.com.CGAwebComercial.resource.Recursos;
import co.com.CGAwebComercial.util.ComisionVendedores;
import co.com.CGAwebComercial.util.Fechas;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class BajaRotacionBean implements Serializable {

	@ManagedProperty("#{autenticacionBean}")
	private AutenticacionBean autenticacion;	

	private Recursos recurso;

	private List<ComisionVendedores> listaVendedores;
	private List<Ajuste> listaVendedoresAjuste;
	private List<bajaRotacion> listaFacturas;
	private List<Fechas> listaFechas;
	private List<Cliente> listaClientes;
	private List<AjusteVendedor> listaAjusteVendedor;

	private List<Funcionario> listaEspecialista; 
	private List<Funcionario> listaEspecialistaD;
	private List<ComisionVendedores> listaEspecialistaCambio;
	private List<Linea> listaLinea;


	private AjusteVendedor ajusteVendedor;
	private Funcionario vendedorA;
	private Funcionario vendedorR;
	private Cliente clienteD;
	private Linea linea;
	private Linea lineaA;

	List<Integer> listaED;

	private ComisionVendedores vendedor;
	private Liquidacion liquidacion;
	private Ajuste ajuste;

	private String nombreRegistro;
	private String fechaBusqueda ="";
	private String fechaBusquedaYear= "";
	private String tipoEspecialista = "";
	private String tipo= "";
	private String totalDetalle= "";
	private String habilitar; 
	private String liquidar;
	private String observacion;
	private String disable= "true";
	private int totalAjuste=0;
	private int idfuncionario;
	private int index = 0;
	private int codSap= 0;


	@PostConstruct
	public void listarVendedoresEspecialistas(){

		try{
			recurso = new Recursos();
			habilitar = "false";

			if(autenticacion.getFechaBusqueda() != null && autenticacion.getFechaBusquedaYear() != null){
				listaFechas = recurso.cargarFechas();
				fechaBusqueda = autenticacion.getFechaBusqueda();
				fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
				listarVendedoresEspecialistasFechas();
			}
			else{
				listaFechas = recurso.cargarFechas();
				List<Funcionario> listaVendedor = null;
				int i=0;
				listaVendedores = new ArrayList<>();
				FuncionarioDao daoF = new FuncionarioDao();
				if(autenticacion.getTipoVendedor().equals("I") ){
					listaVendedor = daoF.listarVendedoresInternos();
					tipo = "codVendedorInt";
				}
				else if (autenticacion.getTipoVendedor().equals("D")) {
					directorNacional();
				} 
				else{
					listaVendedor = daoF.listarVendedores();
					tipo = "codEspecialista";
				}
				for (Funcionario funcionario : listaVendedor) {

					ComisionVendedores vendedor = new ComisionVendedores();
					BajaRotacionDao daoB = new BajaRotacionDao();
					Long sumatotal = daoB.SumaTotal(tipo, funcionario.getId_funcionario());
					LiquidacionDao daoL = new LiquidacionDao();
					List<Liquidacion> liquidacion = daoL.buscarLiquidacion("LBR", funcionario.getId_funcionario());
					liquidar = (liquidacion.size() <= 0) ? "false" : "true";
					if(sumatotal != null){

						int total = (int) (long) sumatotal* -1;
						vendedor.setCedula(funcionario.getPersona().getCedula());
						vendedor.setId(funcionario.getId_funcionario());
						vendedor.setNombre(funcionario.getPersona().getNombre());
						vendedor.setConcepto("LBR");
						vendedor.setIngresoReal(total);
						vendedor.setComision((total * 9) / 1000);
						vendedor.setTotalConAjuste(vendedor.getComision());
						vendedor.setLiquidar(liquidar);
						listaVendedores.add(i, vendedor);
						i++;
					}
					else{
						vendedor.setCedula(funcionario.getPersona().getCedula());
						vendedor.setId(funcionario.getId_funcionario());
						vendedor.setNombre(funcionario.getPersona().getNombre());
						vendedor.setConcepto("LBR");
						vendedor.setIngresoReal(0);
						vendedor.setComision(0);
						vendedor.setTotalConAjuste(vendedor.getComision());
						vendedor.setLiquidar(liquidar);
						listaVendedores.add(i, vendedor);
						i++;
					}
				}
			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Vendedores Baja Rotación");
		}
	}

	public void listarVendedoresEspecialistasFechas(){

		try{
			List<Funcionario> listaVendedor = null;
			int i=0;
			listaVendedores = new ArrayList<>();
			FuncionarioDao daoF = new FuncionarioDao();
			if(autenticacion.getTipoVendedor().equals("I") ){
				listaVendedor = daoF.listarVendedoresInternos();
				tipo = "codVendedorInt";
			}
			else if (autenticacion.getTipoVendedor().equals("D")) {
				directorNacional();
			} 
			else{
				listaVendedor = daoF.listarVendedores();
				tipo = "codEspecialista";
			}

			for (Funcionario funcionario : listaVendedor) {

				ComisionVendedores vendedor = new ComisionVendedores();
				BajaRotacionDao daoB = new BajaRotacionDao();
				Long sumatotal = daoB.SumaTotalFechas( tipo, funcionario.getId_funcionario(), fechaBusqueda, fechaBusquedaYear);
				LiquidacionDao daoL = new LiquidacionDao();
				List<Liquidacion> liquidacion = daoL.buscarLiquidacion("LBR", funcionario.getId_funcionario());
				liquidar = (liquidacion.size() <= 0) ? "false" : "true";
				if(sumatotal != null){
					int total = (int) (long) sumatotal* -1;
					vendedor.setCedula(funcionario.getPersona().getCedula());
					vendedor.setId(funcionario.getId_funcionario());
					vendedor.setNombre(funcionario.getPersona().getNombre());
					vendedor.setConcepto("LBR");
					vendedor.setIngresoReal(total);
					vendedor.setComision((total * 9) / 1000);
					vendedor.setTotalConAjuste(vendedor.getComision());
					vendedor.setLiquidar(liquidar);
					listaVendedores.add(i, vendedor);
					i++;
				}
				else{
					vendedor.setCedula(funcionario.getPersona().getCedula());
					vendedor.setId(funcionario.getId_funcionario());
					vendedor.setNombre(funcionario.getPersona().getNombre());
					vendedor.setConcepto("LBR");
					vendedor.setIngresoReal(0);
					vendedor.setComision(0);
					vendedor.setTotalConAjuste(vendedor.getComision());
					vendedor.setLiquidar(liquidar);
					listaVendedores.add(i, vendedor);
					i++;
				}
			}
			autenticacion.setFechaBusqueda(fechaBusqueda);
			autenticacion.setFechaBusquedaYear(fechaBusquedaYear);

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Vendedores Baja Rotación");
		}
	}

	//*LBR verificando  el estado de los vendedores *//	
	public void listarVendedoresFechas(){

		try{
			List<Funcionario> listaVendedor = null;

			//int i=0;
			listaVendedores = new ArrayList<>();
			FuncionarioDao daoF = new FuncionarioDao();
			if(autenticacion.getTipoVendedor().equals("I") ){
				listaVendedor = daoF.listarVendedoresInternos();
				tipo = "codVendedorInt";
			}
			else if (autenticacion.getTipoVendedor().equals("D")) {
				directorNacional();
			} 
			else{
				listaVendedor = daoF.listarVendedores();
				tipo = "codEspecialista";
			}

			for (Funcionario funcionario : listaVendedor) {

				ComisionVendedores vendedor = new ComisionVendedores();
				LiquidacionDao daoL = new LiquidacionDao();
				List<Liquidacion> liquidacion = daoL.buscarLiquidacion("LBR", funcionario.getId_funcionario());
				liquidar = (liquidacion.size() <= 0) ? "false" : "true";

				if(funcionario.getEstado() == 1){
					vendedor.setCedula(funcionario.getPersona().getCedula());
					vendedor.setId(funcionario.getId_funcionario());
					vendedor.setNombre(funcionario.getPersona().getNombre());
					vendedor.setConcepto("LBR");
					BajaRotacionDao daoB = new BajaRotacionDao();
					listaFacturas = daoB.listaLBR(tipo, funcionario.getId_funcionario(), fechaBusqueda, fechaBusquedaYear);
					System.out.println(tipo +  funcionario.getId_funcionario()+ fechaBusqueda + fechaBusquedaYear);
					for ( bajaRotacion LBR : listaFacturas) {
						System.out.println(LBR + "" +listaFacturas.size());
						Funcionario fun = daoF.buscar(LBR.getCodVendedorInt());
						if(  fun == null ||  fun.getEstado() != 1){
							vendedor.setIngresoReal( vendedor.getIngresoReal() + LBR.getValorNeto());
							vendedor.setComision( vendedor.getComision() + (LBR.getValorNeto() * 18) / 1000);
							vendedor.setTotalConAjuste(vendedor.getComision());
						}
						else{
							vendedor.setIngresoReal(vendedor.getIngresoReal() + LBR.getValorNeto());
							vendedor.setComision(vendedor.getComision() + (LBR.getValorNeto() * 9) / 1000);
							vendedor.setTotalConAjuste(vendedor.getComision());
						}
					}
					vendedor.setIngresoReal(vendedor.getIngresoReal()* -1);
					vendedor.setComision(vendedor.getComision()* -1);
					vendedor.setLiquidar(liquidar);
					listaVendedores.add(vendedor);
				}
			}
			autenticacion.setFechaBusqueda(fechaBusqueda);
			autenticacion.setFechaBusquedaYear(fechaBusquedaYear);

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Vendedores Baja Rotación");
		}
	}	


	public void listarDetalle(){

		try{
			if(autenticacion.getFechaBusqueda() != null && autenticacion.getFechaBusquedaYear() != null){
				fechaBusqueda = autenticacion.getFechaBusqueda();
				fechaBusquedaYear = autenticacion.getFechaBusquedaYear();	
				FuncionarioDao daoF = new FuncionarioDao();
				Funcionario funcionario = daoF.buscar(idfuncionario);
				nombreRegistro = funcionario.getPersona().getNombre(); 
				BajaRotacionDao daoB = new BajaRotacionDao();
				listaFacturas = daoB.listarDetalle(tipo,idfuncionario, fechaBusqueda, fechaBusquedaYear);
				Long sumatotal = daoB.SumaTotalFechas( tipo, idfuncionario, fechaBusqueda, fechaBusquedaYear);
				totalDetalle = new DecimalFormat("###,###").format(sumatotal.intValue()* -1);
			}
			else{
				Messages.addGlobalError("No selecciono el mes");
			}

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Vendedores Baja Rotación");
		}
	}

	public void directorNacional(){

		try{
			listaVendedores = new ArrayList<>();
			ComisionVendedores vendedor = new ComisionVendedores();
			FuncionarioDao daoF = new FuncionarioDao();
			Funcionario funcionario = daoF.buscar(1);
			BajaRotacionDao dao = new BajaRotacionDao();
			Long total = dao.SumaTotalDirector(funcionario.getId_funcionario(), fechaBusqueda, fechaBusquedaYear);

			if(total != null){
				int sumatotal = (int) (long) total* -1;
				vendedor.setId(funcionario.getId_funcionario());
				vendedor.setNombre(funcionario.getPersona().getNombre());
				vendedor.setIngresoReal(sumatotal);
				vendedor.setComision((sumatotal * 12) / 1000);
				listaVendedores.add(0,vendedor);
			}	
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de Director nacional");
		}
	}

	public void liquidar(ActionEvent evento){

		try{
			ajuste = new Ajuste();
			listaVendedoresAjuste = new ArrayList<>();			
			liquidacion = new Liquidacion();
			vendedor = (ComisionVendedores) evento.getComponent().getAttributes().get("vendedorSelecionado");
			liquidacion.setCodSap(vendedor.getId());
			liquidacion.setNombre(vendedor.getNombre());
			liquidacion.setPeriodo(Integer.parseInt(fechaBusqueda));
			liquidacion.setEjercicio(Integer.parseInt(fechaBusquedaYear));
			liquidacion.setConcepto(vendedor.getConcepto());
			liquidacion.setValorAjuste(0);
			liquidacion.setValorComision(vendedor.getComision());
			liquidacion.setValorTotal(vendedor.getComision());
			codSap = vendedor.getId();
			nombreRegistro = vendedor.getNombre();
			ajuste.setCodSap(vendedor.getId());
			ajuste.setNombre(vendedor.getNombre());	
			ajuste.setConcepto("LBR");
			FuncionarioDao daoF = new FuncionarioDao();
			Funcionario funcionario = daoF.buscarPersona(autenticacion.getUsuarioLogin().getPersona().getCedula());
			ajuste.setCodSapUsuario(funcionario.getId_funcionario());
			ajuste.setNombreUsuario(funcionario.getPersona().getNombre());
			listaVendedoresAjuste.add(ajuste);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la liquidacion");
		}
	}

	public void adicionarAjuste(ActionEvent evento){

		try{
			ajuste= (Ajuste) evento.getComponent().getAttributes().get("vendedorSelecionado2");
			Ajuste ajuste1 = new Ajuste(); 
			ajuste1.setCodSap(ajuste.getCodSap());
			ajuste1.setNombre(ajuste.getNombre());
			ajuste1.setPeriodo(0);
			ajuste1.setEjercicio(0);
			ajuste1.setConcepto(ajuste.getConcepto());
			ajuste1.setFacturapedido(0);
			ajuste1.setNota("");
			ajuste1.setValorajuste(0);
			ajuste1.setCodSapUsuario(ajuste.getCodSapUsuario());
			ajuste1.setNombreUsuario(ajuste.getNombreUsuario());
			listaVendedoresAjuste.add(ajuste1); 
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la vista Ajuste");
		}
	}

	public void guardarAguste(){

		try{
			Date fecha = new Date();
			totalAjuste = 0;
			habilitar = "true";
			for (Ajuste ajuste : listaVendedoresAjuste) {
				Messages.addGlobalInfo(ajuste.getFechaAjuste()+"te");
				totalAjuste += ajuste.getValorajuste();
				liquidacion.setValorAjuste(totalAjuste);
				ajuste.setFechaAjuste(fecha);
			}
			liquidacion.setValorTotal(liquidacion.getValorComision() + totalAjuste);
			Messages.addGlobalInfo("Se guardaron los Ajustes Correctamente");
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se pudo guardar los Ajustes");
		}
	}

	public void salvarLiquidacion(){

		try{
			Date fecha = new Date();
			LiquidacionDao dao = new LiquidacionDao();
			liquidacion.setFechaLiquidacion(fecha);
			dao.salvar(liquidacion);
			habilitar = "false";
			for( int i = 0 ; i  < listaVendedores.size(); i++){
				if(listaVendedores.get(i).getId() ==liquidacion.getCodSap()){
					listaVendedores.get(i).setLiquidar("true");
				}   
			}

			AjusteDao daoA = new AjusteDao();			
			totalAjuste = 0;
			for (Ajuste ajuste : listaVendedoresAjuste) {
				Messages.addGlobalInfo(ajuste.getFechaAjuste()+"te");
				totalAjuste += ajuste.getValorajuste();
				liquidacion.setValorAjuste(totalAjuste);
				ajuste.setFechaAjuste(fecha);
				daoA.salvar(ajuste);
			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se pudo guardar La liquidación");
		}
	}

	//*carga la lista de los Vendedores y Los Item de la tabla detalle *//

	public void listaCodVededores(){

		try{
			listaEspecialistaCambio = new ArrayList<>();
			if(listaAjusteVendedor != null){
				System.out.println("listaAjusteVendedor");
				listaAjusteVendedor.removeAll(listaAjusteVendedor);
			}
			listaAjusteVendedor = new ArrayList<>();
			listaEspecialista = new ArrayList<>();
			listaEspecialistaD = new ArrayList<>();
			listaED  = new ArrayList<>();
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			tipo = (autenticacion.getTipoVendedor().equals("I") )? "funcionarioI" : "funcionario";

			FuncionarioDao daoF = new FuncionarioDao();
			listaEspecialista = (tipo.equals("funcionarioI"))? daoF.listarVendedoresInternos() : daoF.listarVendedores();

			List<Object[]> listaE = daoF.listarVendedoresPaisD(tipo, fechaBusqueda, fechaBusquedaYear);

			for (Object[] objects : listaE) {

				Persona persona  = new Persona();		
				persona.setNombre((String)objects[1]);
				Funcionario fun = new Funcionario();
				fun.setPersona(persona);
				fun.setId_funcionario((int)objects[0]);				
				listaEspecialistaD.add(fun);
			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no Cargo la lista de vendedores");
		}
	}

	//*carga la lista de los Vendedores y Las lineas de la tabla detalle *//

	public void listaCodVededoresLinea(){

		try{
			listaEspecialistaCambio = new ArrayList<>();
			if(listaAjusteVendedor != null){
				System.out.println("listaAjusteVendedor");
				listaAjusteVendedor.removeAll(listaAjusteVendedor);
			}
			listaAjusteVendedor = new ArrayList<>();
			linea = new Linea();
			lineaA = new Linea();
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			tipo = (autenticacion.getTipoVendedor().equals("I") )? "funcionarioI" : "funcionario";

			FuncionarioDao daoF = new FuncionarioDao();
			listaEspecialista = (tipo.equals("funcionarioI"))? daoF.listarVendedoresInternos() : daoF.listarVendedores();

			LineaDao daoL = new LineaDao();
			listaLinea = daoL.listar();

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no Cargo la lista de vendedores");
		}
	}

	//*carga los Clientes de un Vendedores *//

	public void listaCodClientes(){

		try{
			listaClientes = new ArrayList<>();
			FuncionarioDao daoF = new FuncionarioDao();
			if(vendedorR != null){
				List<Object[]> listaE =	daoF.listarClientesPaisD(tipo, vendedorR.getId_funcionario(), fechaBusqueda, fechaBusquedaYear);

				for (Object[] objects : listaE) {

					Cliente cliente  = new Cliente();		
					cliente.setDescripcion((String)objects[1]);
					cliente.setId_cliente((int)objects[0]);				
					listaClientes.add(cliente);
				}
			}
			else{
				listaClientes = new ArrayList<>();
			}

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no Cargo la lista de vendedores");
		}
	}

	public void cambioVendedor(){

		try{
			//			ComisionVendedores  cambioV = new ComisionVendedores(); 
			//			cambioV.setCedula(vendedorA.getId_funcionario());
			//			cambioV.setNombre(vendedorA.getPersona().getNombre());
			//			cambioV.setPresupuesto(vendedorR.getId_funcionario());
			//			cambioV.setConcepto(vendedorR.getPersona().getNombre());
			//			cambioV.setTipo(clienteD.getDescripcion());
			//			cambioV.setComision(clienteD.getId_cliente());
			//			listaEspecialistaCambio.add(cambioV);

			ajusteVendedor = new AjusteVendedor();
			if (clienteD != null){
				ajusteVendedor.setCodSap(vendedorA.getId_funcionario());
				ajusteVendedor.setNombre(vendedorA.getPersona().getNombre());
				ajusteVendedor.setCodSapA(vendedorR.getId_funcionario());
				ajusteVendedor.setNombreA(vendedorR.getPersona().getNombre());
				ajusteVendedor.setCodCliente(clienteD.getId_cliente());
				ajusteVendedor.setNombreCliente(clienteD.getDescripcion());
				ajusteVendedor.setObservacion(observacion);

				System.out.println(clienteD.getDescripcion() + "Clientes");
				listaAjusteVendedor.add(ajusteVendedor);
				disable = "false";
				clienteD = null;
			}	
			else if (vendedorA != null && vendedorR !=null){
				ajusteVendedor.setCodSap(vendedorA.getId_funcionario());
				ajusteVendedor.setNombre(vendedorA.getPersona().getNombre());
				ajusteVendedor.setCodSapA(vendedorR.getId_funcionario());
				ajusteVendedor.setNombreA(vendedorR.getPersona().getNombre());
				ajusteVendedor.setObservacion(observacion);
				System.out.println(vendedorR.getPersona().getNombre() + "Asesor");
				//System.out.println(clienteD.getDescripcion() + "Asesor");
				listaAjusteVendedor.add(ajusteVendedor);
				vendedorA = null;
				vendedorR = null; 
				disable = "false";
			}
			else if (linea != null && lineaA != null){
				ajusteVendedor.setCodSap(vendedorA.getId_funcionario());
				ajusteVendedor.setNombre(vendedorA.getPersona().getNombre());
				ajusteVendedor.setCodLinea(linea.getId());
				ajusteVendedor.setNombreLinea(linea.getNombre());
				ajusteVendedor.setCodLineaA(lineaA.getId());
				ajusteVendedor.setNombreLineaA(lineaA.getNombre());
				ajusteVendedor.setObservacion(observacion);	
				System.out.println(lineaA.getNombre());
				//System.out.println(clienteD.getDescripcion() + "Lineass");
				listaAjusteVendedor.add(ajusteVendedor);
				vendedorA = null;
				linea = null;
				lineaA = null;
				disable = "false";
			}


		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no adiciono el Vendedor a la lista.");
		}

	}

	public void borrarCambioVendedor(AjusteVendedor  vendedores){

		try{
			//ComisionVendedores  vendedores = (ComisionVendedores) event.getComponent().getAttributes().get("vendedorSelecionado");
			int index = 0;
			for (AjusteVendedor ven : listaAjusteVendedor) {

				if(ven.getCodSap() == vendedores.getCodSap()){
					listaAjusteVendedor.remove(index);
					disable = (listaAjusteVendedor.size() <=0)? "true" : disable;
					break;
				}
			}

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no borro el Vendedor a la lista.");
		}
	}

	//*Actualiza el codigo del vendedor en la tabla detalleSin y en lenta y baja rotación *//
	public void actualizarVendedor(){

		try{
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			tipo = (autenticacion.getTipoVendedor().equals("I") )? "funcionarioI" : "funcionario";

			DetallesinDao daoS = new DetallesinDao();
			AjusteVendedorDao daoA = new AjusteVendedorDao();
			for (AjusteVendedor ven : listaAjusteVendedor) {
				daoS.listarDetallePorFecha(tipo, ven.getCodSap(), ven.getCodSapA(), fechaBusqueda, fechaBusquedaYear);
				Date fecha = new Date();
				ven.setFechaAjuste(fecha);
				daoA.salvar(ven);
			}
			listaAjusteVendedor.removeAll(listaAjusteVendedor);
			Messages.addGlobalInfo("Se actualizaron los datos de los Vendedores Correctamente");
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se actulizaron los Vendedores.");
		}
	}

	//*Actualiza el codigo del vendedor Por cliente en la tabla detalleSin y en lenta y baja rotación *//
	public void actualizarVendedorPorCliente(){

		try{
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			tipo = (autenticacion.getTipoVendedor().equals("I") )? "funcionarioI" : "funcionario";

			DetallesinDao daoS = new DetallesinDao();
			AjusteVendedorDao daoA = new AjusteVendedorDao();
			for (AjusteVendedor ven : listaAjusteVendedor) {
				daoS.actualizarDetalle(tipo, ven.getCodSap(), ven.getCodSapA(),ven.getCodCliente() , fechaBusqueda, fechaBusquedaYear);
				Date fecha = new Date();
				ven.setFechaAjuste(fecha);
				daoA.salvar(ven);
			}
			listaAjusteVendedor.removeAll(listaAjusteVendedor);
			Messages.addGlobalInfo("Se actualizaron los datos de los Vendedores Correctamente");
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se actulizaron los Vendedores.");
		}
	}

	
	//*Se actualizan los datos del vendedor por linea*//
	public void actualizarVendedorPorLinea(){

		try{
			fechaBusqueda = autenticacion.getFechaBusqueda();
			fechaBusquedaYear = autenticacion.getFechaBusquedaYear();
			tipo = (autenticacion.getTipoVendedor().equals("I") )? "funcionarioI" : "funcionario";

			DetallesinDao daoS = new DetallesinDao();
			AjusteVendedorDao daoA = new AjusteVendedorDao();
			for (AjusteVendedor ven : listaAjusteVendedor) {
				daoS.actualizarDetallePorLinea(tipo, ven.getCodSap(), ven.getCodLinea(), ven.getCodLineaA(), fechaBusqueda, fechaBusquedaYear );
				Date fecha = new Date();
				ven.setFechaAjuste(fecha);
				daoA.salvar(ven);
			}
			listaAjusteVendedor.removeAll(listaAjusteVendedor);
			Messages.addGlobalInfo("Se actualizaron los datos de los Vendedores Por linea Correctamente");
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se actulizaron los Vendedores por Linea.");
		}
	}
	
	public List<ComisionVendedores> getListaVendedores() {
		return listaVendedores;
	}

	public void setListaVendedores(List<ComisionVendedores> listaVendedores) {
		this.listaVendedores = listaVendedores;
	}
	public String getFechaBusqueda() {
		return fechaBusqueda;
	}
	public void setFechaBusqueda(String fechaBusqueda) {
		this.fechaBusqueda = fechaBusqueda;
	}
	public String getFechaBusquedaYear() {
		return fechaBusquedaYear;
	}
	public void setFechaBusquedaYear(String fechaBusquedaYear) {
		this.fechaBusquedaYear = fechaBusquedaYear;
	}

	public int getIdfuncionario() {
		return idfuncionario;
	}

	public void setIdfuncionario(int idfuncionario) {
		this.idfuncionario = idfuncionario;
	}

	public List<bajaRotacion> getListaFacturas() {
		return listaFacturas;
	}

	public void setListaFacturas(List<bajaRotacion> listaFacturas) {
		this.listaFacturas = listaFacturas;
	}

	public Recursos getRecurso() {
		return recurso;
	}

	public void setRecurso(Recursos recurso) {
		this.recurso = recurso;
	}

	public List<Fechas> getListaFechas() {
		return listaFechas;
	}

	public void setListaFechas(List<Fechas> listaFechas) {
		this.listaFechas = listaFechas;
	}

	public AutenticacionBean getAutenticacion() {
		return autenticacion;
	}

	public void setAutenticacion(AutenticacionBean autenticacion) {
		this.autenticacion = autenticacion;
	}
	public String getTipoEspecialista() {
		return tipoEspecialista;
	}
	public void setTipoEspecialista(String tipoEspecialista) {
		this.tipoEspecialista = tipoEspecialista;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}

	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public ComisionVendedores getVendedor() {
		return vendedor;
	}
	public void setVendedor(ComisionVendedores vendedor) {
		this.vendedor = vendedor;
	}

	public String getNombreRegistro() {
		return nombreRegistro;
	}

	public void setNombreRegistro(String nombreRegistro) {
		this.nombreRegistro = nombreRegistro;
	}

	public String getTotalDetalle() {
		return totalDetalle;
	}

	public void setTotalDetalle(String totalDetalle) {
		this.totalDetalle = totalDetalle;
	}

	public Ajuste getAjuste() {
		return ajuste;
	}

	public void setAjuste(Ajuste ajuste) {
		this.ajuste = ajuste;
	}

	public List<Ajuste> getListaVendedoresAjuste() {
		return listaVendedoresAjuste;
	}

	public void setListaVendedoresAjuste(List<Ajuste> listaVendedoresAjuste) {
		this.listaVendedoresAjuste = listaVendedoresAjuste;
	}

	public int getCodSap() {
		return codSap;
	}

	public void setCodSap(int codSap) {
		this.codSap = codSap;
	}

	public int getTotalAjuste() {
		return totalAjuste;
	}

	public void setTotalAjuste(int totalAjuste) {
		this.totalAjuste = totalAjuste;
	}

	public Liquidacion getLiquidacion() {
		return liquidacion;
	}

	public void setLiquidacion(Liquidacion liquidacion) {
		this.liquidacion = liquidacion;
	}

	public String getLiquidar() {
		return liquidar;
	}

	public void setLiquidar(String liquidar) {
		this.liquidar = liquidar;
	}

	public String getHabilitar() {
		return habilitar;
	}

	public void setHabilitar(String habilitar) {
		this.habilitar = habilitar;
	}

	public List<Funcionario> getListaEspecialista() {
		return listaEspecialista;
	}

	public void setListaEspecialista(List<Funcionario> listaEspecialista) {
		this.listaEspecialista = listaEspecialista;
	}

	public List<Funcionario> getListaEspecialistaD() {
		return listaEspecialistaD;
	}

	public void setListaEspecialistaD(List<Funcionario> listaEspecialistaD) {
		this.listaEspecialistaD = listaEspecialistaD;
	}

	public List<Integer> getListaED() {
		return listaED;
	}

	public void setListaED(List<Integer> listaED) {
		this.listaED = listaED;
	}

	public List<ComisionVendedores> getListaEspecialistaCambio() {
		return listaEspecialistaCambio;
	}

	public void setListaEspecialistaCambio(List<ComisionVendedores> listaEspecialistaCambio) {
		this.listaEspecialistaCambio = listaEspecialistaCambio;
	}

	public Funcionario getVendedorA() {
		return vendedorA;
	}

	public void setVendedorA(Funcionario vendedorA) {
		this.vendedorA = vendedorA;
	}

	public Funcionario getVendedorR() {
		return vendedorR;
	}

	public void setVendedorR(Funcionario vendedorR) {
		this.vendedorR = vendedorR;
	}

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public Cliente getClienteD() {
		return clienteD;
	}

	public void setClienteD(Cliente clienteD) {
		this.clienteD = clienteD;
	}

	public List<AjusteVendedor> getListaAjusteVendedor() {
		return listaAjusteVendedor;
	}

	public void setListaAjusteVendedor(List<AjusteVendedor> listaAjusteVendedor) {
		this.listaAjusteVendedor = listaAjusteVendedor;
	}

	public AjusteVendedor getAjusteVendedor() {
		return ajusteVendedor;
	}

	public void setAjusteVendedor(AjusteVendedor ajusteVendedor) {
		this.ajusteVendedor = ajusteVendedor;
	}

	public List<Linea> getListaLinea() {
		return listaLinea;
	}

	public void setListaLinea(List<Linea> listaLinea) {
		this.listaLinea = listaLinea;
	}

	public Linea getLinea() {
		return linea;
	}

	public void setLinea(Linea linea) {
		this.linea = linea;
	}

	public Linea getLineaA() {
		return lineaA;
	}

	public void setLineaA(Linea lineaA) {
		this.lineaA = lineaA;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getDisable() {
		return disable;
	}

	public void setDisable(String disable) {
		this.disable = disable;
	}
}
