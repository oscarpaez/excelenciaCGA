package co.com.CGAwebComercial.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import co.com.CGAwebComercial.dao.CiudadDao;
import co.com.CGAwebComercial.dao.OfertasPedidosDao;
import co.com.CGAwebComercial.dao.OficinaVendedorInternoDao;
import co.com.CGAwebComercial.dao.Zona_FuncionarioDao;
import co.com.CGAwebComercial.entyties.Ciudad;
import co.com.CGAwebComercial.entyties.OfertasPedidos;
import co.com.CGAwebComercial.entyties.Zona_Funcionario;
import co.com.CGAwebComercial.resource.Recursos;
import co.com.CGAwebComercial.util.Fechas;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class OfertaPedidoBean implements Serializable{

	@ManagedProperty("#{autenticacionBean}")
	private AutenticacionBean autenticacion;
	
	private List<OfertasPedidos> listaOfertaPedidos;
	private List<OfertasPedidos> listaFiltrarOfePed;
	
	private Recursos recursos;
	private List<Fechas> listaFechas;
	
	private BigDecimal sumaOfe;
	private BigDecimal sumaPed;
	private String valorTotal;
	private String valorTotalP;
	private String tipo;
	private String detalleNombre;
	private String fechaConsulta;
	private int idFun;
	private int idCiudad;
	private int detalleIdFun;
	private int detalleIdFunG;
	
	public OfertaPedidoBean (){
		recursos = new Recursos();
		listaFechas = recursos.cargarFechas();
	}
	
	public void listarOfertaPedidos(){
		
		try {
			if(detalleIdFun > 0){
				idFun = detalleIdFun;
				tipo = "codEspecialista";
				System.out.println(tipo + " --- " + idFun);
			}
			else if(detalleIdFunG > 0){
				idFun = detalleIdFunG;
				tipo = "codInterno";
				
				System.out.println(tipo + " -G- " + idFun);
			}
			else{
				tipo = (autenticacion.getUsuarioLogin().getPerfil().getId() == 1)? "codEspecialista" : "codInterno";
				idFun = autenticacion.getUsuarioLogin().getId();
				System.out.println(tipo + " xxxxxxx " + idFun);
			}			
			OfertasPedidosDao dao = new OfertasPedidosDao();
			listaOfertaPedidos = dao.listarOfertaPedidos(tipo, idFun );
			sumaOfe = new BigDecimal("0");
			sumaPed = new BigDecimal("0");
			for (OfertasPedidos of : listaOfertaPedidos) {
				
				System.out.println("oferta" + of.getValorOferta());
				sumaOfe = sumaOfe.add((of.getValorOferta() == null)? new BigDecimal("0") : of.getValorOferta()); 
				sumaPed = sumaPed.add(of.getValorPedido());
			}
			
			valorTotal = new DecimalFormat("###,###").format(sumaOfe);
			valorTotalP = new DecimalFormat("###,###").format(sumaPed);
			
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error No se cargo la lista de Ofertas Especialista");
		}
	}
	
	public void alistarPedidosLlamadas(){
		
		try{
			idCiudad = 0;
			listarOfertaPedidosOficina();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error No se cargo el inicio de la vista llamadas");
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void listarOfertaPedidosOficina(){

		try{
			if(fechaConsulta == null){

				Calendar fechas = Calendar.getInstance();
				int month = fechas.get(Calendar.MONTH)+1;
				for (Fechas fecha: listaFechas) {
					System.out.println(fecha.getValorMes() + "/////////////////" + month);
					fechaConsulta  = (month<10)?(fecha.getValorMes().equals(String.valueOf("0"+month)))? fecha.getMes():fechaConsulta:(fecha.getValorMes().equals(String.valueOf(""+month)))? fecha.getMes(): fechaConsulta;
				}
			}	
			System.out.println("inicio ciudad" + idCiudad);
			listaOfertaPedidos = new ArrayList<>();
			sumaOfe = new BigDecimal("0");
			sumaPed = new BigDecimal("0");
			if(idCiudad <= 0){
				System.out.println("entro uno");	
				Zona_FuncionarioDao daoF = new Zona_FuncionarioDao();
				Zona_Funcionario zonaF = daoF.buscarFuncionarioZona(autenticacion.getUsuarioLogin().getId());
				
				Recursos recurso = new Recursos();			
				idCiudad = recurso.idOficina(zonaF.getCiudad().getId());
				
				tipo =(autenticacion.getUsuarioLogin().getPerfil().getId() >= 15 || autenticacion.getUsuarioLogin().getPerfil().getId() <= 19)? "codInterno" : "codEspecialista";
			}
			else{
				tipo = "codEspecialista";
			}
			
			if(autenticacion.getUsuarioLogin().getPerfil().getId() >= 15 || autenticacion.getUsuarioLogin().getPerfil().getId() <= 19){
				OficinaVendedorInternoDao daoF = new OficinaVendedorInternoDao();
				
				List<Object[]> results1 =  daoF.listaVenInt(idCiudad);	
				System.out.println(results1.size() + "????" + idCiudad);
				OfertasPedidosDao daoOF = new OfertasPedidosDao();
				
				for (Object[] objects : results1) {			
					 
					Integer valor = (Integer) objects[1];
					if(valor != 1){
						OfertasPedidos ofePed = daoOF.listarOfertaPedidosOficinaLlamadas(tipo, (Integer) objects[0], (Integer) objects[1] );
						if (ofePed.getValorOferta() != null){
							sumaOfe = sumaOfe.add(ofePed.getValorOferta());
							sumaPed = sumaPed.add(ofePed.getValorPedido());
							listaOfertaPedidos.add(ofePed);
						}
					}
				}
			}
			else{				
				System.out.println( tipo +" entro ciudad " + idCiudad);
				OfertasPedidosDao daoOf = new OfertasPedidosDao();
				List results = daoOf.buscarEspecialista(tipo, idCiudad);

				System.out.println(results.size());
				for (Iterator iterator = results.iterator(); iterator.hasNext();) {
					Object object = (Object) iterator.next();
					int d = (int) object;
					System.out.println("est " + d);
					OfertasPedidos ofePed = new OfertasPedidos();
					List<Object[]> results1 =  daoOf.listarOfertaPedidosOficina(tipo, d);				
					for (Object[] objects : results1) {

						System.out.println(objects[0] + "EEEEE" + objects[2] + " - " + objects[3]);
						ofePed.setCodEspecialista((Integer)objects[0]);
						ofePed.setEspecialista((String) objects[1]);
						ofePed.setValorOferta(new BigDecimal(objects[2].toString()));
						ofePed.setValorPedido(new BigDecimal(objects[3].toString()));
						ofePed.setPorcentaje((ofePed.getValorOferta().intValue() == 0 || ofePed.getValorPedido().intValue() == 0)? new BigDecimal("0") : ofePed.getValorPedido().divide(ofePed.getValorOferta(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
						ofePed.setImagen((ofePed.getPorcentaje().intValue() < 81)? "rojo.png" :(ofePed.getPorcentaje().intValue() >= 81 && ofePed.getPorcentaje().intValue() <= 95 )? "amarillo.jpg" : (ofePed.getPorcentaje().intValue() >= 96  && ofePed.getPorcentaje().intValue() <= 100)? "verde.png": "violeta.jpg");

					}
					sumaOfe = sumaOfe.add(ofePed.getValorOferta());
					sumaPed = sumaPed.add(ofePed.getValorPedido());
					listaOfertaPedidos.add(ofePed);
				}
			}
			valorTotal = new DecimalFormat("###,###").format(sumaOfe);
			valorTotalP = new DecimalFormat("###,###").format(sumaPed);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error No se cargo la lista de Oferta Oficina");
		}
	}
	
	public void listarOfertaPedidosPais(){
		
		try{
			listaOfertaPedidos = new ArrayList<>();
			sumaOfe = new BigDecimal("0");
			sumaPed = new BigDecimal("0");
			
			CiudadDao daoC = new CiudadDao();
			List<Ciudad> listaCiudad = daoC.listar();
			
			OfertasPedidosDao daoF = new OfertasPedidosDao();
			
			for (Ciudad ciudad : listaCiudad) {
				Recursos recurso = new Recursos();	
				OfertasPedidos ofePed = new OfertasPedidos();
				int oficina = recurso.idOficina(ciudad.getId());
				Object[]  objects = daoF.listarOfertaPedidosPais(oficina);
				System.out.println(objects[0] + "EEEEE" + objects[1] + " - " + objects[2]);
				
				ofePed.setOficina((String) objects[0]);
				ofePed.setValorOferta(new BigDecimal(objects[1].toString()));
				ofePed.setValorPedido(new BigDecimal(objects[2].toString()));
				ofePed.setCodOficina((int) objects[3]);
				ofePed.setPorcentaje(ofePed.getValorOferta().divide(ofePed.getValorPedido(), 4, BigDecimal.ROUND_HALF_UP));
				ofePed.setImagen((ofePed.getPorcentaje().intValue() < 81)? "rojo.png" :(ofePed.getPorcentaje().intValue() >= 81 && ofePed.getPorcentaje().intValue() <= 95 )? "amarillo.jpg" : (ofePed.getPorcentaje().intValue() >= 96  && ofePed.getPorcentaje().intValue() <= 100)? "verde.png": "violeta.jpg");
				
				sumaOfe = sumaOfe.add(ofePed.getValorOferta());
				sumaPed = sumaPed.add(ofePed.getValorPedido());
				listaOfertaPedidos.add(ofePed);
			}
			valorTotal = new DecimalFormat("###,###").format(sumaOfe);
			valorTotalP = new DecimalFormat("###,###").format(sumaPed);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error No se cargo la lista de Ofertas Pais");
		}
	}

	public AutenticacionBean getAutenticacion() {
		return autenticacion;
	}

	public void setAutenticacion(AutenticacionBean autenticacion) {
		this.autenticacion = autenticacion;
	}

	public List<OfertasPedidos> getListaOfertaPedidos() {
		return listaOfertaPedidos;
	}

	public void setListaOfertaPedidos(List<OfertasPedidos> listaOfertaPedidos) {
		this.listaOfertaPedidos = listaOfertaPedidos;
	}

	public List<OfertasPedidos> getListaFiltrarOfePed() {
		return listaFiltrarOfePed;
	}

	public void setListaFiltrarOfePed(List<OfertasPedidos> listaFiltrarOfePed) {
		this.listaFiltrarOfePed = listaFiltrarOfePed;
	}

	public String getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(String valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getIdFun() {
		return idFun;
	}

	public void setIdFun(int idFun) {
		this.idFun = idFun;
	}

	public BigDecimal getSumaOfe() {
		return sumaOfe;
	}

	public void setSumaOfe(BigDecimal sumaOfe) {
		this.sumaOfe = sumaOfe;
	}

	public BigDecimal getSumaPed() {
		return sumaPed;
	}

	public void setSumaPed(BigDecimal sumaPed) {
		this.sumaPed = sumaPed;
	}

	public String getValorTotalP() {
		return valorTotalP;
	}

	public void setValorTotalP(String valorTotalP) {
		this.valorTotalP = valorTotalP;
	}

	public int getDetalleIdFun() {
		return detalleIdFun;
	}

	public void setDetalleIdFun(int detalleIdFun) {
		this.detalleIdFun = detalleIdFun;
	}

	public String getDetalleNombre() {
		return detalleNombre;
	}

	public void setDetalleNombre(String detalleNombre) {
		this.detalleNombre = detalleNombre;
	}

	public int getIdCiudad() {
		return idCiudad;
	}

	public void setIdCiudad(int idCiudad) {
		this.idCiudad = idCiudad;
	}

	public int getDetalleIdFunG() {
		return detalleIdFunG;
	}

	public void setDetalleIdFunG(int detalleIdFunG) {
		this.detalleIdFunG = detalleIdFunG;
	}

	public Recursos getRecursos() {
		return recursos;
	}

	public void setRecursos(Recursos recursos) {
		this.recursos = recursos;
	}

	public List<Fechas> getListaFechas() {
		return listaFechas;
	}

	public void setListaFechas(List<Fechas> listaFechas) {
		this.listaFechas = listaFechas;
	}

	public String getFechaConsulta() {
		return fechaConsulta;
	}

	public void setFechaConsulta(String fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}	
}
