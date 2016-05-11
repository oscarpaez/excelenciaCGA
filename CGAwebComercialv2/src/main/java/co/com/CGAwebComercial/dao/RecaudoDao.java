package co.com.CGAwebComercial.dao;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.Ciudad;
import co.com.CGAwebComercial.entyties.Comision;
import co.com.CGAwebComercial.entyties.Detalle;
import co.com.CGAwebComercial.entyties.Funcionario;
import co.com.CGAwebComercial.entyties.OficinaVendedorInterno;
import co.com.CGAwebComercial.entyties.Recaudo;
import co.com.CGAwebComercial.entyties.Zona_venta;
import co.com.CGAwebComercial.util.ComisionVendedores;
import co.com.CGAwebComercial.util.HibernateUtil;

public class RecaudoDao extends GenericDao<Recaudo> {

	@SuppressWarnings("unchecked")
	public List<Recaudo> listarRecaudo(int idPersona){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Recaudo> recaudo = null;		
		try{
			FuncionarioDao dao = new FuncionarioDao();
			Funcionario funcionario = dao.buscarPersona(idPersona);
			Zona_ventaDao daoZ = new Zona_ventaDao();
			List<Zona_venta> zona = daoZ.buscarZona(funcionario.getId_funcionario());

			for (Zona_venta zona_venta : zona) {
				Criteria consulta = session.createCriteria(Recaudo.class);			
				consulta.createAlias("zonaVenta", "z");
				consulta.add(Restrictions.eq("z.id_zona_venta", zona_venta.getId_zona_venta()));
				recaudo = consulta.list();

			}			
			return recaudo;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Recaudo> listarPresupuesto(int idPersona){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Recaudo> recaudo = null;
		List<Recaudo> listaRecaudo = new ArrayList<>();		
		try{
			FuncionarioDao dao = new FuncionarioDao();
			Funcionario funcionario = dao.buscarPersona(idPersona);
			Zona_ventaDao daoZ = new Zona_ventaDao();
			List<Zona_venta> zona = daoZ.buscarZona(funcionario.getId_funcionario());

			ComisionDao daoC = new ComisionDao();
			Comision comision = daoC.buscar(1);
			for (Zona_venta zona_venta : zona) {
				Criteria consulta = session.createCriteria(Recaudo.class);			
				consulta.createAlias("zonaVenta", "z");
				consulta.add(Restrictions.eq("z.id_zona_venta", zona_venta.getId_zona_venta()));
				recaudo = consulta.list();

			}
			for (Recaudo recaudo1 : recaudo ) {
				

				int real = recaudo1.getReal().compareTo(new BigDecimal("0.00"));
				if(real == 0){
					recaudo1.setCumplimiento(new BigDecimal("0.00"));
					recaudo1.setUmbral(comision.getUmbralRecaudo());
					recaudo1.setImagen("rojo.png");
					recaudo1.setValorComision(new BigDecimal("0.00"));
				}
				else{

					double cum =  recaudo1.getReal().doubleValue();
					double pre = recaudo1.getPresupuesto().doubleValue();
					cum= cum / pre *100  ; 
					
					BigDecimal cumplimiento =recaudo1.getReal().divide(recaudo1.getPresupuesto(), 2, BigDecimal.ROUND_HALF_UP);  
					recaudo1.setCumplimiento(cumplimiento.multiply(new BigDecimal("100")));
					recaudo1.setUmbral(comision.getUmbralRecaudo());
					int numero = recaudo1.getUmbral().compareTo(recaudo1.getCumplimiento().divide(new BigDecimal("100")));
					if(numero == 1){
						recaudo1.setImagen("rojo.png");
						recaudo1.setValorComision(new BigDecimal("0.00"));
					}
					else{
						System.out.println("xxxxxxf"+recaudo1.getCumplimiento() + comision.getUmbralRecaudo());
						recaudo1.setImagen("verde.png");
						recaudo1.setValorComision(recaudo1.getCumplimiento().multiply(comision.getValorBaseRecaudo()).divide(new BigDecimal("100")));
					}
				}	
				listaRecaudo.add(recaudo1);
			}
			return listaRecaudo;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}

	}

	public List<Long> listarDetalleValorNeto(int idPersona){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Long> ValorNeto = new ArrayList<>();
		try{
			int meses = fechaFinalR();
			System.out.println(meses);
			Date fechaFinal = null;
			Date fechaInicial = null;
			DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
			for (int i=1; i< meses; i++){
				String fecFinal = "2016-0"+i+"-31";
				fechaFinal = formatoFecha.parse(fecFinal);

				String fecInicial = "2016-0"+i+"-01";
				fechaInicial  = formatoFecha.parse(fecInicial);
				Criteria consulta = session.createCriteria(Detalle.class);
				consulta.add(Restrictions.eq("funcionario", idPersona));
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("valorNeto"));
				Long totalWages = (Long) consulta.uniqueResult();
				ValorNeto.add(totalWages);
			}
			return ValorNeto;

		} catch (RuntimeException ex) {
			throw ex;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			session.close();
		}
		return ValorNeto;	
	}

	public List<Long> listarDetalleCostoTotal(int idPersona){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Long> costoTotal = new ArrayList<>();
		try{
			int meses = fechaFinalR();
			System.out.println(meses);
			Date fechaFinal = null;
			Date fechaInicial = null;
			DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
			for (int i=1; i< meses; i++){
				String fecFinal = "2016-0"+i+"-31";
				fechaFinal = formatoFecha.parse(fecFinal);

				String fecInicial = "2016-0"+i+"-01";
				fechaInicial  = formatoFecha.parse(fecInicial);
				Criteria consulta = session.createCriteria(Detalle.class);
				consulta.add(Restrictions.eq("funcionario", idPersona));
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("costoTotal"));
				Long totalWages = (Long) consulta.uniqueResult();
				costoTotal.add(totalWages);
			}
			return costoTotal;

		} catch (RuntimeException ex) {
			throw ex;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			session.close();
		}
		return costoTotal;	
	}

	@SuppressWarnings({ "unchecked" })
	public List<ComisionVendedores> carteraInternos(String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();

		try{
			Date fechaFinal;
			Date fechaInicial;
			ComisionVendedores sucursales = null;
			List<ComisionVendedores> listaoficinas = new ArrayList<>();
			Recaudo itemSucursal = null ;
			List<Recaudo> recaudo = null;				
			
			CiudadDao daoC = new CiudadDao();
			List<Ciudad> listaSucursales = daoC.listar();
			for (Ciudad ciudad : listaSucursales) {
				Zona_ventaDao daoZ = new Zona_ventaDao();
				List<Zona_venta> Listazona = daoZ.buscarZonaSucursal(ciudad.getId());
				
				BigDecimal sumaR = new BigDecimal("0.00");
				BigDecimal sumaP = new BigDecimal("0.00");
				sucursales = new ComisionVendedores();
				sucursales.setId(ciudad.getId());
				sucursales.setNombre(ciudad.getNombre());
				
			
				for (Zona_venta zona_venta : Listazona) {
					
					if (fecMes.equals("")){
						fechaFinal = fechaFinal();
						fechaInicial = fechaInicial();	
					}
					else{
						fechaFinal = fechaFinal(fecMes, fecYear);
						fechaInicial = fechaInicial(fecMes, fecYear);
					}
					Criteria consulta = session.createCriteria(Recaudo.class);			
					consulta.createAlias("zonaVenta", "z");
					consulta.add(Restrictions.eq("z.id_zona_venta", zona_venta.getId_zona_venta()));
					consulta.add(Restrictions.between("fecha", fechaInicial, fechaFinal));
					recaudo = consulta.list();

						for (Recaudo recaudo1 : recaudo ) {
							itemSucursal = new Recaudo();
							sumaR = sumaR.add(recaudo1.getReal());
							itemSucursal.setReal(sumaR);
							sumaP = sumaP.add(recaudo1.getPresupuesto());
							itemSucursal.setPresupuesto(sumaP);
							
							sucursales.setPresupuestoB(itemSucursal.getPresupuesto());
							sucursales.setIngresoRealB(itemSucursal.getReal());
						}
				}
				BigDecimal cumplimiento = new BigDecimal("0.00"); 
				double va;
				
				if(sucursales.getIngresoRealB()== null ||  sucursales.getPresupuestoB() == null){
					va = 0;
				}
				else{
				 va =(double) sucursales.getIngresoRealB().doubleValue() / sucursales.getPresupuestoB().doubleValue();
				}
				
				if(va == 0){
					sucursales.setCumplimiento(new BigDecimal("0.00"));
				}
				else{
					String cum = String.valueOf(va);
					cumplimiento = new BigDecimal(cum); 
					sucursales.setComisionS(new DecimalFormat("###.##").format(cumplimiento.multiply(new BigDecimal("100"))));
					sucursales.setCumplimiento(cumplimiento.multiply(new BigDecimal("100")));
				}
				
				sucursales.setUmbralCV(new BigDecimal("95.00"));
				int numero = sucursales.getUmbralCV().compareTo(sucursales.getCumplimiento());
				if(numero == 1){
					sucursales.setImagen("rojo.png");
				}
				else{
					sucursales.setImagen("verde.png");
				}
				listaoficinas.add(sucursales);
				sucursales = new ComisionVendedores();
			}

			return listaoficinas;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	
	}
	
	
	@SuppressWarnings({ "unchecked" })
	public List<OficinaVendedorInterno> listaVendedorInterno(int oficina){
		
		Session session = HibernateUtil.getSessionfactory().openSession();		
		List<OficinaVendedorInterno> listaVenInt;
		try{
			Criteria consulta = session.createCriteria(OficinaVendedorInterno.class);			
			consulta.add(Restrictions.eq("oficinadeventas", oficina));			
			listaVenInt = consulta.list();			
			
			return listaVenInt;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

}
