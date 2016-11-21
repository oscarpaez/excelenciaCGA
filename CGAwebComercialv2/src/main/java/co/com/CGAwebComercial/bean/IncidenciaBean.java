package co.com.CGAwebComercial.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import co.com.CGAwebComercial.dao.CausaPerdidaVentaDao;
import co.com.CGAwebComercial.dao.CiudadDao;
import co.com.CGAwebComercial.dao.ClienteDao;
import co.com.CGAwebComercial.dao.EstadoPedidosProyectadosDao;
import co.com.CGAwebComercial.dao.FuncionarioDao;
import co.com.CGAwebComercial.dao.IncidenciaDao;
import co.com.CGAwebComercial.dao.LineaDao;
import co.com.CGAwebComercial.dao.MaterialDao;
import co.com.CGAwebComercial.dao.OficinaVendedorInternoDao;
import co.com.CGAwebComercial.dao.PedidosEnProcesoDao;
import co.com.CGAwebComercial.dao.PedidosProyectadosDao;
import co.com.CGAwebComercial.dao.RegularidadPerdidaVentaDao;
import co.com.CGAwebComercial.dao.Zona_FuncionarioDao;
import co.com.CGAwebComercial.dao.Zona_ventaDao;
import co.com.CGAwebComercial.entyties.CausaPerdidaVenta;
import co.com.CGAwebComercial.entyties.Ciudad;
import co.com.CGAwebComercial.entyties.Cliente;
import co.com.CGAwebComercial.entyties.EstadoPedidosProyectados;
import co.com.CGAwebComercial.entyties.Funcionario;
import co.com.CGAwebComercial.entyties.Incidencia;
import co.com.CGAwebComercial.entyties.Linea;
import co.com.CGAwebComercial.entyties.Material;
import co.com.CGAwebComercial.entyties.PedidosEnProceso;
import co.com.CGAwebComercial.entyties.PedidosProyectados;
import co.com.CGAwebComercial.entyties.RegularidadPerdidaVenta;
import co.com.CGAwebComercial.entyties.Zona_Funcionario;
import co.com.CGAwebComercial.entyties.Zona_venta;
import co.com.CGAwebComercial.resource.Recursos;


@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class IncidenciaBean implements Serializable{

	@ManagedProperty("#{autenticacionBean}")
	private AutenticacionBean autenticacion;
	
	private List<CausaPerdidaVenta> listaCausa;
	private List<RegularidadPerdidaVenta> listaRegularidad;
	private List<Linea> listaLinea;
	private List<Incidencia> listaIncidencia;
	private List<Incidencia> listaFiltrarIncidencia;
 	private List<Cliente> listaCliente;
	private List<Material> listaMaterial;
	private List<Ciudad> listaCiudad;
	private List<EstadoPedidosProyectados> listaEstadoPP;
	private List<PedidosEnProceso> listaPedidosEnProceso;
	private List<PedidosEnProceso> listaFiltroPP;
	private List<PedidosProyectados> listaPedidosProyectados;
	private List<PedidosProyectados> listaPedidosP;
	
	private Zona_venta zona;
	private Incidencia incidencia;
	private PedidosProyectados pedidosProyectados;
	private Cliente cliente;
	private Linea linea;
	private Material material;
	private EstadoPedidosProyectados estadoPP;
	private Ciudad ciudad;
	
	private Date fechaIni;
	private Date fechaFin;
	
	private String valorTotal;
	private String valorTotalC;
	private String valorTotalR;
	private String clienteO;
	private String materialO;
	private String nombreC;
	private String proNeg;
	private String[] selectTipoNegocio;
	private int idFun;
	private int idCiudad;
	
	public void listarIncidencias(){
		
		try{
			IncidenciaDao dao = new IncidenciaDao();
			
			if(autenticacion.getUsuarioLogin().getPerfil().getId() == 6){
				listaIncidencia = dao.valorPedidosPerdidosUsuarios("funcionarioI", proNeg, autenticacion.getUsuarioLogin().getId());
			}
			else if(autenticacion.getUsuarioLogin().getPerfil().getId() == 1){
				listaIncidencia = dao.valorPedidosPerdidosUsuarios("funcionario",proNeg, autenticacion.getUsuarioLogin().getId());
			}
			else if(autenticacion.getUsuarioLogin().getPerfil().getId() == 7 || autenticacion.getUsuarioLogin().getPerfil().getId() == 8){
				
			}	
			else{
				listaIncidencia = dao.pedidosPerdidosPais( fechaIni, fechaFin);
				//listaIncidencia = dao.listar();
			}
			
			Long sumaTotal = (long) 0;
			Long sumaTotalC = (long) 0;
			int i =0;
			for (Incidencia incidencia : listaIncidencia) {
				sumaTotal += (incidencia.getValorVenta() == null)? 0 : incidencia.getValorVenta();
				sumaTotalC += (incidencia.getPrecioCompetencia() == null)? 0 :incidencia.getPrecioCompetencia();
				
				String lineas = "";
				if(incidencia.getIdLineas() != null){
					for (String retval: incidencia.getIdLineas().split("-")) {
				         int codigo = Integer.parseInt(retval); 
						 LineaDao daoL = new LineaDao();
				         Linea linea = daoL.buscar(codigo);
				         lineas = lineas +"\n"+ linea.getNombre();
				    }
					listaIncidencia.get(i).getLinea().setNombre(lineas);
				}
				i++;
			}
			
			valorTotal = new DecimalFormat("###,###").format(sumaTotal);
			valorTotalC = new DecimalFormat("###,###").format(sumaTotalC);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error No se cargo la lista de Incidencias");
		}
	}
	
	
	public void listarAsesor(){
		
		try{
			clienteO = null;
			materialO = null;
			material = new Material();
			cliente = new Cliente();
			ciudad = new Ciudad();
			estadoPP = new EstadoPedidosProyectados();
			Zona_ventaDao dao = new Zona_ventaDao();
			zona = new Zona_venta();
			
			int venI = 0;
			CiudadDao daoCi = new CiudadDao();
			Ciudad ciudad = new Ciudad();
			List <Zona_venta> zonaV = new ArrayList<>();
			FuncionarioDao daoF = new FuncionarioDao();
			Funcionario funcionario = daoF.buscar(autenticacion.getUsuarioLogin().getId());
			incidencia = new Incidencia();
			if(autenticacion.getUsuarioLogin().getPerfil().getId() == 6){
				OficinaVendedorInternoDao daoOF = new OficinaVendedorInternoDao();
				venI = daoOF.ciudadInterno(autenticacion.getUsuarioLogin().getId());
				venI = venI / 1000; 
				ciudad = daoCi.buscar(venI);
			}
			else if (autenticacion.getUsuarioLogin().getPerfil().getId() == 1){
				List<Zona_venta > zonaL = dao.buscarZona(autenticacion.getUsuarioLogin().getId());
				venI = zonaL.get(0).getCiudad().getId();
				ciudad = daoCi.buscar(venI);
				Zona_ventaDao daoZ = new Zona_ventaDao();
				zonaV = daoZ.buscarZona(funcionario.getId_funcionario());
				incidencia.setZonaId(zonaV.get(0).getId_zona_venta());
			}
			else{
				List<Zona_venta > zonaL = dao.buscarZona(autenticacion.getUsuarioLogin().getId());
				venI = zonaL.get(0).getCiudad().getId();
				ciudad = daoCi.buscar(venI);
				Zona_ventaDao daoZ = new Zona_ventaDao();
				zonaV = daoZ.buscarZona(funcionario.getId_funcionario());
				incidencia.setZonaId(zonaV.get(0).getId_zona_venta());
			}
			
			CausaPerdidaVentaDao daoC = new CausaPerdidaVentaDao();
			listaCausa = daoC.listar();
			
			RegularidadPerdidaVentaDao daoR = new RegularidadPerdidaVentaDao();
			listaRegularidad = daoR.listar();
			
			LineaDao daoL = new LineaDao();
			listaLinea = daoL.listar();
			
//			CiudadDao daoCi = new CiudadDao();
//			listaCiudad = daoCi.listar();
			
			//incidencia.setZona(zonaL.get(0));
			incidencia.setFuncionario(funcionario);
			incidencia.setCiudad(ciudad);
			//incidencia.setZonaId(zonaV.get(0).getId_zona_venta());
			
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se cargaron los datos Asesor.");
		}
	}
	
	public void salvar(){
		
		try{
			IncidenciaDao dao = new IncidenciaDao();
			String lineas = "";
			for (int i = 0; i < selectTipoNegocio.length; i++) {
				LineaDao daoL = new LineaDao();
				int a = Integer.parseInt(selectTipoNegocio[i]);
				Linea linea = daoL.buscar(a);
				incidencia.setLinea(linea);
				lineas = lineas + selectTipoNegocio[i] + "-";
			}
			//incidencia.setLinea(linea);
			incidencia.setIdLineas(lineas);
			dao.salvar(incidencia); 
			selectTipoNegocio = new String[0];
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se guardo el Proyecto");
		}
	}
	
	public void editar(){

		try{
			IncidenciaDao dao = new IncidenciaDao();
			LineaDao daoL = new LineaDao();
			Linea linea = daoL.buscar(incidencia.getId());
			incidencia.setLinea(linea);
			dao.merge(incidencia); 
			Messages.addGlobalInfo("La oferta se edito Correctamente"+ incidencia.getResultaNegocio());
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se guardo el Proyecto");
		}
	}

	
	public void salvarProyectados(){
		try{
			PedidosProyectadosDao dao = new PedidosProyectadosDao();
			pedidosProyectados.setFuncionario(incidencia.getZona().getFuncionario());
			pedidosProyectados.setFechaInicio(new Date());
	 		pedidosProyectados.setPropuesto("M");
			
			if(clienteO.equals("")){

				if(materialO.equals("")){

					ClienteDao  daoC = new ClienteDao();
					Cliente clienteB = daoC.buscar(cliente.getId());
					pedidosProyectados.setCliente(clienteB);

					MaterialDao daoM = new MaterialDao();
					Material materialB = daoM.buscar(material.getId());
					pedidosProyectados.setMaterial(materialB);
					
					EstadoPedidosProyectadosDao daoD = new EstadoPedidosProyectadosDao();
					EstadoPedidosProyectados estadoB = new EstadoPedidosProyectados();
					estadoB = daoD.buscar(estadoPP.getId());
					pedidosProyectados.setEstado(estadoB);
					
					CiudadDao daoCi = new CiudadDao();
					Ciudad ciudadB = new Ciudad();
					ciudadB = daoCi.buscar(ciudad.getId());
					pedidosProyectados.setCiudad(ciudadB);					
				}
				else{
					MaterialDao daoM = new MaterialDao();
					Material materialB = new Material();
					materialB.setId_material(9999999);
					materialB.setDescripcion(materialO);
					materialB.setLinea(pedidosProyectados.getLinea());
					daoM.salvar(materialB);
					materialB = daoM.ultimoRegistro();
					pedidosProyectados.setMaterial(materialB);

					ClienteDao  daoC = new ClienteDao();
					Cliente clienteB = daoC.buscar(cliente.getId());
					pedidosProyectados.setCliente(clienteB);
					
					EstadoPedidosProyectadosDao daoD = new EstadoPedidosProyectadosDao();
					EstadoPedidosProyectados estadoB = new EstadoPedidosProyectados();
					estadoB = daoD.buscar(estadoPP.getId());
					pedidosProyectados.setEstado(estadoB);
					
					CiudadDao daoCi = new CiudadDao();
					Ciudad ciudadB = new Ciudad();
					ciudadB = daoCi.buscar(ciudad.getId());
					pedidosProyectados.setCiudad(ciudadB);
				}				
			}
			else{
				
				if(materialO.equals("")){
					
					ClienteDao  daoC = new ClienteDao();
					Cliente clienteB = new Cliente();
					clienteB.setId(9999999);
					clienteB.setDescripcion(clienteO);
					daoC.salvar(clienteB);
					clienteB = daoC.ultimoRegistro();
					pedidosProyectados.setCliente(clienteB);
					
					MaterialDao daoM = new MaterialDao();
					Material materialB = daoM.buscar(material.getId());
					pedidosProyectados.setMaterial(materialB);
					
					EstadoPedidosProyectadosDao daoD = new EstadoPedidosProyectadosDao();
					EstadoPedidosProyectados estadoB = new EstadoPedidosProyectados();
					estadoB = daoD.buscar(estadoPP.getId());
					pedidosProyectados.setEstado(estadoB);
					
					CiudadDao daoCi = new CiudadDao();
					Ciudad ciudadB = new Ciudad();
					ciudadB = daoCi.buscar(ciudad.getId());
					pedidosProyectados.setCiudad(ciudadB);
				}
				else{
					ClienteDao  daoC = new ClienteDao();
					Cliente clienteB = new Cliente();
					clienteB.setId(9999999);
					clienteB.setDescripcion(clienteO);
					daoC.salvar(clienteB);
					clienteB = daoC.ultimoRegistro();
					pedidosProyectados.setCliente(clienteB);
					
					MaterialDao daoM = new MaterialDao();
					Material materialB = new Material();
					materialB.setId_material(9999999);
					materialB.setDescripcion(materialO);
					materialB.setLinea(pedidosProyectados.getLinea());
					daoM.salvar(materialB);
					materialB = daoM.ultimoRegistro();
					pedidosProyectados.setMaterial(materialB);
					
					EstadoPedidosProyectadosDao daoD = new EstadoPedidosProyectadosDao();
					EstadoPedidosProyectados estadoB = new EstadoPedidosProyectados();
					estadoB = daoD.buscar(estadoPP.getId());
					pedidosProyectados.setEstado(estadoB);
					
					CiudadDao daoCi = new CiudadDao();
					Ciudad ciudadB = new Ciudad();
					ciudadB = daoCi.buscar(ciudad.getId());
					pedidosProyectados.setCiudad(ciudadB);
				}
				
			}
			clienteO = null;
			materialO = null;
			material = new Material();
			cliente = new Cliente();
			dao.salvar(pedidosProyectados); 
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se guardo el Proyecto");
		}
	}
	
	public void popular(){

		try {
			
			if(material.getId() != 0){
				MaterialDao dao = new MaterialDao();
				material = dao.buscar( material.getId());
				listaLinea = dao.buscarPorLinea(material.getLinea().getId());
			}			
			else{
				listaLinea = new ArrayList<>();
				LineaDao daoL = new LineaDao();
				listaLinea = daoL.listar();
			}
		} catch (RuntimeException ex) {
			Messages.addGlobalError("Error No se cargaron las ciudades");
			ex.printStackTrace();
		}

	}
	
	public void registroProyectados(){
		
		try{
			pedidosProyectados = new PedidosProyectados();
			clienteO = null;
			materialO = null;
			material = new Material();
			cliente = new Cliente();
			ciudad = new Ciudad();
			estadoPP = new EstadoPedidosProyectados();
			cliente = new Cliente();
			
			Zona_ventaDao dao = new Zona_ventaDao();
			zona = new Zona_venta();
			List<Zona_venta > zonaL = dao.buscarZona(autenticacion.getUsuarioLogin().getId());
			incidencia = new Incidencia();
			incidencia.setZona(zonaL.get(0));
			//listarAsesor();			
			ClienteDao daoC = new ClienteDao();
			listaCliente = daoC.listar();
//			
////			LineaDao daoL = new LineaDao();
////			listaLinea = daoL.listar();
//			
			MaterialDao daoM = new MaterialDao();
			listaMaterial  = daoM.listar();
//			
			EstadoPedidosProyectadosDao daoE = new EstadoPedidosProyectadosDao();
			listaEstadoPP = daoE.listar();
			
			CiudadDao daoCi = new CiudadDao();
			listaCiudad = daoCi.listar();
					
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se inicio los Registros Proyectados");
		}
	}
	
	public void vistaInput(){
		
		try{
			
			
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Actualizo el input cliente");
		}
		
	}
	
	public void listarIncidenciasPorUsuario(){

		try{
			Zona_ventaDao dao = new Zona_ventaDao();
			List<Zona_venta > zonaL = dao.buscarZona(autenticacion.getUsuarioLogin().getId());
			
			IncidenciaDao daoI = new IncidenciaDao();
			listaIncidencia = daoI.valorPedidosPerdidosUsuarios("",zonaL.get(0).getId_zona_venta(), zonaL.get(0).getFuncionario().getId_funcionario());
			
			Long sumaTotal = (long) 0;
			for (Incidencia incidencia : listaIncidencia) {
				sumaTotal += incidencia.getValorVenta();
			}
			valorTotal = new DecimalFormat("###,###").format(sumaTotal);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error No se cargo la lista de Incidencias");
		}
	}
	
	public void listarIncidenciasPorSucursal(){

		try{
			
			Zona_ventaDao dao = new Zona_ventaDao();
			List<Zona_venta > zonaL = new ArrayList<>();
			IncidenciaDao daoI = new IncidenciaDao();
			
			Zona_FuncionarioDao daoF = new Zona_FuncionarioDao();
			Zona_Funcionario zonaF = daoF.buscarFuncionarioZona(autenticacion.getUsuarioLogin().getId());
			
			if(idCiudad >0){
				zonaL = dao.buscarZonaSucursal(idCiudad);
				listaIncidencia = daoI.pedidosPerdidosSucursal(zonaL, idCiudad, proNeg);
			}
			else{
				zonaL = dao.buscarZonaSucursal(zonaF.getCiudad().getId());
				listaIncidencia = daoI.pedidosPerdidosSucursal(zonaL, zonaF.getCiudad().getId(), proNeg);
			}			
//			Zona_ventaDao dao = new Zona_ventaDao();
//			List<Zona_venta > zonaL = dao.buscarZonaSucursal(zonaF.getCiudad().getId());
//			
//			IncidenciaDao daoI = new IncidenciaDao();
//			listaIncidencia = daoI.pedidosPerdidosSucursal(zonaL, zonaF.getCiudad().getId());
			
			Long sumaTotal = (long) 0;
			Long sumaTotalR = (long) 0;
			for (Incidencia incidencia : listaIncidencia) {
				sumaTotal += incidencia.getValorVenta();
				//sumaTotalR += incidencia.getPrecioCompetencia().;
			}
			valorTotal = new DecimalFormat("###,###").format(sumaTotal);
			valorTotalC = new DecimalFormat("###,###").format(sumaTotalR);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error No se cargo la lista de Incidencias Por sucursales");
		}
	}
	
	public void listarPedidosProceso(){

		try{
			PedidosEnProcesoDao dao = new PedidosEnProcesoDao();
			
			String tipo = (autenticacion.getUsuarioLogin().getPerfil().getId() == 1)? "codEspecialista" : "codInterno";
			
			listaPedidosEnProceso = dao.pedidosProcesoUsuario(tipo ,autenticacion.getUsuarioLogin().getId());
			
			Long sumaTotal = (long) 0;
			for (PedidosEnProceso pep : listaPedidosEnProceso) {
				sumaTotal += pep.getValorNeto().longValue();
			}
			valorTotal = new DecimalFormat("###,###").format(sumaTotal);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error No se cargo la lista los pedidos en proceso");
		}
	}
	
	public void listarPedidosProcesoPais(){

		try{
			PedidosEnProcesoDao dao = new PedidosEnProcesoDao();
			listaPedidosEnProceso = dao.pedidosProcesoPais();
			
			Long sumaTotal = (long) 0;
			Long sumaTotalR = (long) 0;
			for (PedidosEnProceso pep : listaPedidosEnProceso) {
				sumaTotal += pep.getValorNeto().longValue();
				sumaTotalR+= pep.getRentabilidad().longValue();
			}
			valorTotal = new DecimalFormat("###,###").format(sumaTotal);
			valorTotalR = new DecimalFormat("###,###").format(sumaTotalR);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error No se cargo la lista de Proceso en Pais");
		}
	}
	
	public void listarPedidosProcesoOficina(){

		try{
			
			int oficina = 0;
			Recursos recurso = new Recursos();				
			
			if(idCiudad >0){
				oficina = recurso.idOficina(idCiudad); 
			}
			else{
				Zona_FuncionarioDao daoF = new Zona_FuncionarioDao();
				Zona_Funcionario zonaF = daoF.buscarFuncionarioZona(autenticacion.getUsuarioLogin().getId());
				oficina = recurso.idOficina(zonaF.getCiudad().getId());
			}			
			
			PedidosEnProcesoDao dao = new PedidosEnProcesoDao();
			listaPedidosEnProceso = dao.pedidosProcesoOficina(oficina);
			
			BigDecimal sumaTotal = new BigDecimal("0");
			for (PedidosEnProceso pep : listaPedidosEnProceso) {
				sumaTotal = sumaTotal.add(pep.getValorNeto());
			}
			valorTotal = new DecimalFormat("###,###").format(sumaTotal);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error No se cargo la lista de Proceso por Oficina");
		}
	}
	
	public void listarPedidosProyectadosU(){

		try{
			PedidosProyectadosDao dao = new PedidosProyectadosDao();
			listaPedidosProyectados = dao.pedidosProyectadosUsuario(autenticacion.getUsuarioLogin().getId());
			
			Long sumaTotal = (long) 0;
			for (PedidosProyectados pp : listaPedidosProyectados) {
				sumaTotal += pp.getValorPedido();
			}
			valorTotal = new DecimalFormat("###,###").format(sumaTotal);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error No se cargo la lista pedidos Proyectados por usuario");
		}
	}
	
	public void listarPedidosProyectadosOficina(){

		try{
			
			PedidosProyectadosDao dao = new PedidosProyectadosDao();
			
			if(idCiudad >0){
				listaPedidosProyectados = dao.pedidosProyectadosOficina(idCiudad);
			}
			else{
				Zona_FuncionarioDao daoF = new Zona_FuncionarioDao();
				Zona_Funcionario zonaF = daoF.buscarFuncionarioZona(autenticacion.getUsuarioLogin().getId());
				listaPedidosProyectados = dao.pedidosProyectadosOficina(zonaF.getCiudad().getId());
			}	
			
//			Zona_FuncionarioDao daoF = new Zona_FuncionarioDao();
//			Zona_Funcionario zonaF = daoF.buscarFuncionarioZona(autenticacion.getUsuarioLogin().getId());
			
//			PedidosProyectadosDao dao = new PedidosProyectadosDao();
//			listaPedidosProyectados = dao.pedidosProyectadosOficina(zonaF.getCiudad().getId());
			
			Long sumaTotal = (long) 0;
			for (PedidosProyectados pp : listaPedidosProyectados) {
				sumaTotal += pp.getValorPedido();
			}
			valorTotal = new DecimalFormat("###,###").format(sumaTotal);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error No se cargo la lista  pedidos Proyectados por Oficina");
		}
	}
	
	public void editar(Incidencia incidencia1){

		try {
			
			incidencia = incidencia1;
//			String lineas = "";
////			if(incidencia.getIdLineas() != null){
////				for (String retval: incidencia.getIdLineas().split("-")) {
////			         int codigo = Integer.parseInt(retval); 
////					 LineaDao daoL = new LineaDao();
////			         Linea linea = daoL.buscar(codigo);
////			         lineas = lineas +"\n"+ linea.getNombre();
////			    }
////			}
//			incidencia.getLinea().setNombre(lineas);
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			Messages.addGlobalError("Error no se guardo el Proyecto");
		}
	}

	public AutenticacionBean getAutenticacion() {
		return autenticacion;
	}

	public void setAutenticacion(AutenticacionBean autenticacion) {
		this.autenticacion = autenticacion;
	}

	public List<CausaPerdidaVenta> getListaCausa() {
		return listaCausa;
	}

	public void setListaCausa(List<CausaPerdidaVenta> listaCausa) {
		this.listaCausa = listaCausa;
	}

	public List<RegularidadPerdidaVenta> getListaRegularidad() {
		return listaRegularidad;
	}

	public void setListaRegularidad(List<RegularidadPerdidaVenta> listaRegularidad) {
		this.listaRegularidad = listaRegularidad;
	}

	public Zona_venta getZona() {
		return zona;
	}

	public void setZona(Zona_venta zona) {
		this.zona = zona;
	}

	public Incidencia getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(Incidencia incidencia) {
		this.incidencia = incidencia;
	}

	public List<Linea> getListaLinea() {
		return listaLinea;
	}

	public void setListaLinea(List<Linea> listaLinea) {
		this.listaLinea = listaLinea;
	}

	public List<Incidencia> getListaIncidencia() {
		return listaIncidencia;
	}

	public void setListaIncidencia(List<Incidencia> listaIncidencia) {
		this.listaIncidencia = listaIncidencia;
	}
	public String getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(String valorTotal) {
		this.valorTotal = valorTotal;
	}
	public List<Cliente> getListaCliente() {
		return listaCliente;
	}
	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}
	public List<Material> getListaMaterial() {
		return listaMaterial;
	}
	public void setListaMaterial(List<Material> listaMaterial) {
		this.listaMaterial = listaMaterial;
	}
	public List<EstadoPedidosProyectados> getListaEstadoPP() {
		return listaEstadoPP;
	}
	public void setListaEstadoPP(List<EstadoPedidosProyectados> listaEstadoPP) {
		this.listaEstadoPP = listaEstadoPP;
	}
	public PedidosProyectados getPedidosProyectados() {
		return pedidosProyectados;
	}
	public void setPedidosProyectados(PedidosProyectados pedidosProyectados) {
		this.pedidosProyectados = pedidosProyectados;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Linea getLinea() {
		return linea;
	}
	public void setLinea(Linea linea) {
		this.linea = linea;
	}
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	public EstadoPedidosProyectados getEstadoPP() {
		return estadoPP;
	}
	public void setEstadoPP(EstadoPedidosProyectados estadoPP) {
		this.estadoPP = estadoPP;
	}
	public int getIdFun() {
		return idFun;
	}
	public void setIdFun(int idFun) {
		this.idFun = idFun;
	}
	public List<PedidosEnProceso> getListaPedidosEnProceso() {
		return listaPedidosEnProceso;
	}
	public void setListaPedidosEnProceso(List<PedidosEnProceso> listaPedidosEnProceso) {
		this.listaPedidosEnProceso = listaPedidosEnProceso;
	}
	public List<PedidosProyectados> getListaPedidosProyectados() {
		return listaPedidosProyectados;
	}
	public void setListaPedidosProyectados(List<PedidosProyectados> listaPedidosProyectados) {
		this.listaPedidosProyectados = listaPedidosProyectados;
	}
	public List<Ciudad> getListaCiudad() {
		return listaCiudad;
	}
	public void setListaCiudad(List<Ciudad> listaCiudad) {
		this.listaCiudad = listaCiudad;
	}
	public String getClienteO() {
		return clienteO;
	}
	public void setClienteO(String clienteO) {
		this.clienteO = clienteO;
	}
	public String getMaterialO() {
		return materialO;
	}
	public void setMaterialO(String materialO) {
		this.materialO = materialO;
	}
	public Ciudad getCiudad() {
		return ciudad;
	}
	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}
	public List<Incidencia> getListaFiltrarIncidencia() {
		return listaFiltrarIncidencia;
	}
	public void setListaFiltrarIncidencia(List<Incidencia> listaFiltrarIncidencia) {
		this.listaFiltrarIncidencia = listaFiltrarIncidencia;
	}
	public List<PedidosEnProceso> getListaFiltroPP() {
		return listaFiltroPP;
	}
	public void setListaFiltroPP(List<PedidosEnProceso> listaFiltroPP) {
		this.listaFiltroPP = listaFiltroPP;
	}
	public List<PedidosProyectados> getListaPedidosP() {
		return listaPedidosP;
	}
	public void setListaPedidosP(List<PedidosProyectados> listaPedidosP) {
		this.listaPedidosP = listaPedidosP;
	}
	public String getValorTotalR() {
		return valorTotalR;
	}
	public void setValorTotalR(String valorTotalR) {
		this.valorTotalR = valorTotalR;
	}
	public int getIdCiudad() {
		return idCiudad;
	}
	public void setIdCiudad(int idCiudad) {
		this.idCiudad = idCiudad;
	}
	public String getNombreC() {
		return nombreC;
	}
	public void setNombreC(String nombreC) {
		this.nombreC = nombreC;
	}
	public String getValorTotalC() {
		return valorTotalC;
	}
	public void setValorTotalC(String valorTotalC) {
		this.valorTotalC = valorTotalC;
	}
	public String[] getSelectTipoNegocio() {
		return selectTipoNegocio;
	}
	public void setSelectTipoNegocio(String[] selectTipoNegocio) {
		this.selectTipoNegocio = selectTipoNegocio;
	}
	public String getProNeg() {
		return proNeg;
	}
	public void setProNeg(String proNeg) {
		this.proNeg = proNeg;
	}
	public Date getFechaIni() {
		return fechaIni;
	}
	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}	
}
