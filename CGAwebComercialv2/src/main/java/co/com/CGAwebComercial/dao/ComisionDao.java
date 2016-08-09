package co.com.CGAwebComercial.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.Ciudad;
import co.com.CGAwebComercial.entyties.Comision;
import co.com.CGAwebComercial.entyties.Detalle;
import co.com.CGAwebComercial.entyties.Esquemas;
import co.com.CGAwebComercial.entyties.Funcionario;
import co.com.CGAwebComercial.entyties.Linea;
import co.com.CGAwebComercial.entyties.OficinaVendedorInterno;
import co.com.CGAwebComercial.entyties.Presupuesto;
import co.com.CGAwebComercial.entyties.PresupuestoE;
import co.com.CGAwebComercial.entyties.Recaudo;
import co.com.CGAwebComercial.entyties.Usuario;
import co.com.CGAwebComercial.entyties.Zona_Funcionario;
import co.com.CGAwebComercial.entyties.Zona_venta;
import co.com.CGAwebComercial.util.ComisionVendedores;
import co.com.CGAwebComercial.util.HibernateUtil;

public class ComisionDao extends GenericDao<Comision>{

	public ComisionVendedores comisionDirectores(int linea, Funcionario funcionario, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		ComisionVendedores sucursales =  new ComisionVendedores();
		try{
			System.out.println(fecYear + "" + fecMes );
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);


			Criteria consulta = session.createCriteria(PresupuestoE.class);			
			consulta.add(Restrictions.eq("linea", linea));
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal valor = (BigDecimal) consulta.uniqueResult();
			sucursales.setPresupuestoB(valor);

			consulta.setProjection(Projections.sum("utilidad"));
			valor = (BigDecimal) consulta.uniqueResult();
			sucursales.setUtilpresupuesto(valor);

			consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.eq("linea", linea));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long  totalWages = (Long) consulta.uniqueResult();
			totalWages = (totalWages == null)? 0: totalWages;
			sucursales.setIngresoRealB(new BigDecimal(totalWages* -1));

			consulta.setProjection(Projections.sum("costoTotal"));
			totalWages = (Long) consulta.uniqueResult();
			totalWages = (totalWages == null)? 0: totalWages;
			sucursales.setUtilidadReal(new BigDecimal(totalWages));

			//			System.out.println(sucursales.getIngresoRealB());
			//			System.out.println(sucursales.getPresupuestoB());
			if(sucursales.getIngresoRealB().intValue() == 0 || sucursales.getUtilidadReal().intValue() == 0 ){
				sucursales.setCumplimiento(new BigDecimal("0"));
			}
			else{
				sucursales.setCumplimiento(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
			}

			UsuarioDao daoU = new UsuarioDao();
			Usuario usuario = daoU.buscar(funcionario.getId_funcionario());
			
			EsquemasDao daoE = new EsquemasDao();
			Esquemas esquema = daoE.buscarEsquema(usuario.getPerfil().getId());
			if(sucursales.getCumplimiento().intValue() >= esquema.getUmbralComision().intValue()){
				BigDecimal comision = funcionario.getComision().getValorBaseVenta().multiply(esquema.getVentaLinea());

				sucursales.setComisionVentaLinea(comision.multiply(sucursales.getCumplimiento()).divide(new BigDecimal("100")));
				sucursales.setImagen1("verde.png");
			}
			else{
				sucursales.setComisionVentaLinea(new BigDecimal("0"));
				sucursales.setImagen1("rojo.png");
			}

			consulta = session.createCriteria(PresupuestoE.class);			
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			valor = (BigDecimal) consulta.uniqueResult();
			sucursales.setPresupuestoB(valor);

			consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			totalWages = (Long) consulta.uniqueResult();
			totalWages = (totalWages == null)? 0: totalWages;
			sucursales.setIngresoRealB(new BigDecimal(totalWages* -1));

			//			System.out.println(sucursales.getIngresoRealB() + "###");
			//			System.out.println(sucursales.getPresupuestoB()  + "####");
			if(sucursales.getIngresoRealB().intValue() == 0 || sucursales.getPresupuestoB().intValue() == 0 ){
				sucursales.setCumplimientoVP(new BigDecimal("0"));
			}
			else{
				sucursales.setCumplimientoVP(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
			}
			System.out.println(sucursales.getIngresoRealB());
			System.out.println(sucursales.getPresupuestoB());
			System.out.println(sucursales.getCumplimientoVP());
			System.out.println(esquema.getUmbralComision().intValue());
			if(sucursales.getCumplimientoVP().intValue() >= esquema.getUmbralComision().intValue()){
				BigDecimal comision = funcionario.getComision().getValorBaseVenta().multiply(esquema.getVentaPais());
				sucursales.setComisionVentaPais(comision.multiply(sucursales.getCumplimiento()).divide(new BigDecimal("100")));
				sucursales.setImagen1("verde.png");
			}
			else{
				sucursales.setComisionVentaPais(new BigDecimal("0"));
				sucursales.setImagen1("rojo.png");
			}

			sucursales.setDetalleVL("false");
			sucursales.setDetalleVP("false");
			sucursales.setDetalleML("true");
			sucursales.setDetalleZC("true");
			sucursales.setDetalleR("true");

			sucursales.setComisionTotal(sucursales.getComisionVentaLinea().add(sucursales.getComisionVentaPais()));
			return sucursales;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	//*Comision Director de linea Antidesgaste *//

	public ComisionVendedores comisionDirectoresA(Funcionario funcionario, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		ComisionVendedores sucursales =  new ComisionVendedores();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);


			Criteria consulta = session.createCriteria(PresupuestoE.class);
			Criterion resul =Restrictions.in("linea", new Integer[]{6,10});
			consulta.add(resul);
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal valor = (BigDecimal) consulta.uniqueResult();
			sucursales.setPresupuestoB(valor);

			consulta = session.createCriteria(Detalle.class);
			resul =Restrictions.in("linea", new Integer[]{6,10});
			consulta.add(resul);
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			Long  totalWages = (Long) consulta.uniqueResult();
			totalWages = (totalWages == null)? 0: totalWages;
			sucursales.setIngresoRealB(new BigDecimal(totalWages* -1));

			//			System.out.println(sucursales.getIngresoRealB());
			//			System.out.println(sucursales.getPresupuestoB());

			if(sucursales.getIngresoRealB().intValue() == 0 || sucursales.getPresupuestoB().intValue() == 0 ){
				sucursales.setCumplimientoVL(new BigDecimal("0"));
			}
			else{
				sucursales.setCumplimientoVL(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
			}
			UsuarioDao daoU = new UsuarioDao();
			Usuario usuario = daoU.buscar(funcionario.getId_funcionario());
			
			EsquemasDao daoE = new EsquemasDao();
			Esquemas esquema = daoE.buscarEsquema(usuario.getPerfil().getId());

			if(sucursales.getCumplimientoVL().intValue() >= esquema.getUmbralComision().intValue()){
				BigDecimal comision = funcionario.getComision().getValorBaseVenta().multiply(esquema.getVentaLinea());
				sucursales.setComisionVentaLinea(comision.multiply(sucursales.getCumplimientoVL()).divide(new BigDecimal("100")));
				sucursales.setImagen1("verde.png");
			}
			else{
				sucursales.setComisionVentaLinea(new BigDecimal("0"));
				sucursales.setImagen1("rojo.png");
			}

			consulta = session.createCriteria(PresupuestoE.class);			
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			valor = (BigDecimal) consulta.uniqueResult();
			sucursales.setPresupuestoB(valor);

			consulta = session.createCriteria(Detalle.class);
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("valorNeto"));
			totalWages = (Long) consulta.uniqueResult();
			totalWages = (totalWages == null)? 0: totalWages;
			sucursales.setIngresoRealB(new BigDecimal(totalWages* -1));

			//			System.out.println(sucursales.getIngresoRealB() + "###");
			//			System.out.println(sucursales.getPresupuestoB()  + "####");
			if(sucursales.getIngresoRealB().intValue() == 0 || sucursales.getPresupuestoB().intValue() == 0 ){
				sucursales.setCumplimientoVP(new BigDecimal("0"));
			}
			else{
				sucursales.setCumplimientoVP(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
			}

			if(sucursales.getCumplimientoVP().intValue() >= esquema.getUmbralComision().intValue()){
				BigDecimal comision = funcionario.getComision().getValorBaseVenta().multiply(esquema.getVentaPais());
				sucursales.setComisionVentaPais(comision.multiply(sucursales.getCumplimientoVP()).divide(new BigDecimal("100")));
				sucursales.setImagen1("verde.png");
			}
			else{
				sucursales.setComisionVentaPais(new BigDecimal("0"));
				sucursales.setImagen1("rojo.png");
			}
			sucursales.setDetalleVL("false");
			sucursales.setDetalleVP("false");
			sucursales.setDetalleML("true");
			sucursales.setDetalleZC("true");
			sucursales.setDetalleR("true");
			
			sucursales.setComisionTotal(sucursales.getComisionVentaLinea().add(sucursales.getComisionVentaPais()));
			return sucursales;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	//*Comision Director Comercial *//

	@SuppressWarnings("unchecked")
	public ComisionVendedores comisionDirectorComercial(Funcionario funcionario, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		ComisionVendedores sucursales =  new ComisionVendedores();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);
			BigDecimal totalMix = new BigDecimal("0") ;

			Criteria consulta = session.createCriteria(PresupuestoE.class);
			consulta.add(Restrictions.eq("oficinaVentas", 1000 ));
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal preTotal = (BigDecimal) consulta.uniqueResult();

			LineaDao daoD = new LineaDao();
			List<Linea> lineas =  daoD.listar();
			
			UsuarioDao daoU = new UsuarioDao();
			Usuario usuario = daoU.buscar(funcionario.getId_funcionario());
			
			EsquemasDao daoE = new EsquemasDao();
			Esquemas esquema = daoE.buscarEsquema(usuario.getPerfil().getId());

			for (Linea linea : lineas) {
				consulta = session.createCriteria(PresupuestoE.class);
				consulta.add(Restrictions.eq("linea", linea.getId()));
				consulta.add(Restrictions.eq("oficinaVentas", 1000 ));
				consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("ingresos"));
				BigDecimal valor = (BigDecimal) consulta.uniqueResult();
				sucursales.setPresupuestoB(valor);

				consulta = session.createCriteria(Detalle.class);
				consulta.add(Restrictions.eq("linea", linea.getId()));
				consulta.add(Restrictions.eq("sucursal", 1000 ));			
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("valorNeto"));
				Long  totalWages = (Long) consulta.uniqueResult();
				totalWages = (totalWages == null)? 0: totalWages;
				sucursales.setIngresoRealB(new BigDecimal(totalWages* -1));

				//				System.out.println(sucursales.getIngresoRealB());
				//				System.out.println(sucursales.getPresupuestoB());

				if(sucursales.getIngresoRealB().intValue() == 0 || sucursales.getPresupuestoB().intValue() == 0 ){
					sucursales.setCumplimientoML(new BigDecimal("0"));
				}
				else{
					sucursales.setCumplimientoML(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				}
				

				if(sucursales.getCumplimientoML().intValue() >= esquema.getUmbralComision().intValue()){
					BigDecimal distribucion = sucursales.getPresupuestoB().divide(preTotal, 4, BigDecimal.ROUND_HALF_UP);
					//					System.out.println(distribucion + "Cumplimiento XX");
					BigDecimal comision = funcionario.getComision().getValorBaseVenta().multiply(esquema.getMixLinea());
					sucursales.setComisionMixLinea(comision.multiply(distribucion));
					sucursales.setImagen1("verde.png");
					totalMix = totalMix.add(sucursales.getComisionMixLinea());
				}
				else{
					sucursales.setComisionMixLinea(sucursales.getComisionMixLinea());
					sucursales.setImagen1("rojo.png");
				}

			}

			sucursales.setComisionMixLinea(totalMix);


			Zona_ventaDao daoZo = new Zona_ventaDao();
			List<Zona_venta> listaZona = daoZo.buscarZonaSucursal(1);
			sucursales.setPresupuestoB(new BigDecimal("0"));
			sucursales.setIngresoRealB(new BigDecimal("0"));		
			for (Zona_venta zona_venta : listaZona) {
				//				System.out.println(zona_venta.getId_zona_venta());
				consulta = session.createCriteria(Recaudo.class);			
				consulta.createAlias("zonaVenta", "z");
				consulta.add(Restrictions.eq("z.id_zona_venta", zona_venta.getId_zona_venta()));
				consulta.add(Restrictions.between("fecha", fechaInicial, fechaFinal));
				List<Recaudo> recaudo = consulta.list();

				if (recaudo.size() <= 0 ){
					sucursales.setPresupuestoB(sucursales.getPresupuestoB());
					sucursales.setIngresoRealB(sucursales.getIngresoRealB());
				}
				else{
					//					System.out.println( recaudo.get(0).getPresupuesto() + " ####### " + recaudo.size());
					sucursales.setPresupuestoB(sucursales.getPresupuestoB().add(recaudo.get(0).getPresupuesto()));
					sucursales.setIngresoRealB(sucursales.getIngresoRealB().add(recaudo.get(0).getReal()));
				}
			}	


			if(sucursales.getIngresoRealB().intValue() == 0 || sucursales.getPresupuestoB().intValue() == 0 ){
				sucursales.setCumplimientoR(new BigDecimal("0"));
			}
			else{
				sucursales.setCumplimientoR(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
			}
			System.out.println(sucursales.getCumplimientoR().intValue() );
			if(sucursales.getCumplimientoR().intValue() >= esquema.getUmbralComision().intValue()){
				BigDecimal comision = funcionario.getComision().getValorBaseVenta().multiply(esquema.getRecaudoCartera());
				//				System.out.println( comision + "Comisiomn" + sucursales.getCumplimientoR());
				sucursales.setComisionRecaudo(comision.multiply(sucursales.getCumplimientoR()).divide(new BigDecimal("100")));
				sucursales.setImagen1("verde.png");
			}
			else{
				sucursales.setComisionRecaudo(new BigDecimal("0"));
				sucursales.setImagen1("rojo.png");
			}
			sucursales.setDetalleVL("true");
			sucursales.setDetalleVP("true");
			sucursales.setDetalleML("false");
			sucursales.setDetalleZC("true");
			sucursales.setDetalleR("false");

			sucursales.setComisionTotal(sucursales.getComisionMixLinea().add(sucursales.getComisionRecaudo()));
			return sucursales;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	//*Comision Jefe Internos*//

	@SuppressWarnings("unchecked")
	public ComisionVendedores comisionJefeInternos(Funcionario funcionario, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		ComisionVendedores sucursales =  new ComisionVendedores();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);
			BigDecimal totalPre = new BigDecimal("0") ;
			BigDecimal totalMix = new BigDecimal("0") ;

			Criteria consulta = session.createCriteria(OficinaVendedorInterno.class);
			consulta.add(Restrictions.eq("oficinadeventas", 1000 ));
			List<OficinaVendedorInterno> listaVI = consulta.list();
			
			UsuarioDao daoU = new UsuarioDao();
			Usuario usuario = daoU.buscar(funcionario.getId_funcionario());
			
			EsquemasDao daoE = new EsquemasDao();
			Esquemas esquema = daoE.buscarEsquema(usuario.getPerfil().getId());
			
			for (OficinaVendedorInterno vendedorI : listaVI) {
				consulta = session.createCriteria(Presupuesto.class);
				consulta.add(Restrictions.eq("funcionario", vendedorI.getCodigosap()));
				consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("ingresos"));
				BigDecimal preTotal = (BigDecimal) consulta.uniqueResult();
				totalPre = totalPre.add(preTotal);
			}


			LineaDao daoD = new LineaDao();
			List<Linea> lineas =  daoD.listar();

			for (Linea linea : lineas) {
				sucursales.setPresupuestoB(new BigDecimal("0"));
				for (OficinaVendedorInterno vendedorI : listaVI) {
					consulta = session.createCriteria(Presupuesto.class);
					consulta.add(Restrictions.eq("linea", linea.getId()));
					consulta.add(Restrictions.eq("funcionario", vendedorI.getCodigosap()));
					consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
					consulta.setProjection(Projections.sum("ingresos"));
					BigDecimal valor = (BigDecimal) consulta.uniqueResult();
					valor = (valor == null )? new BigDecimal("0"): valor;
					sucursales.setPresupuestoB(sucursales.getPresupuestoB().add(valor));
				}	

				consulta = session.createCriteria(Detalle.class);
				consulta.add(Restrictions.eq("linea", linea.getId()));
				consulta.add(Restrictions.eq("sucursal", 1000 ));			
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("valorNeto"));
				Long  totalWages = (Long) consulta.uniqueResult();
				totalWages = (totalWages == null)? 0: totalWages;
				sucursales.setIngresoRealB(new BigDecimal(totalWages* -1));

				System.out.println(sucursales.getIngresoRealB());
				System.out.println(sucursales.getPresupuestoB());

				if(sucursales.getIngresoRealB().intValue() == 0 || sucursales.getPresupuestoB().intValue() == 0 ){
					sucursales.setCumplimientoML(new BigDecimal("0"));
				}
				else{
					sucursales.setCumplimientoML(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				}

				if(sucursales.getCumplimientoML().intValue() >= esquema.getUmbralComision().intValue()){
					BigDecimal distribucion = sucursales.getPresupuestoB().divide(totalPre, 4, BigDecimal.ROUND_HALF_UP);
					System.out.println(distribucion + "Cumplimiento XX");
					BigDecimal comision = funcionario.getComision().getValorBaseVenta().multiply(esquema.getMixLinea());
					sucursales.setComisionMixLinea(comision.multiply(distribucion));
					sucursales.setImagen1("verde.png");
					System.out.println(sucursales.getComisionMixLinea() + "Comision Mix de linea");
					totalMix = totalMix.add(sucursales.getComisionMixLinea());
				}
				else{
					sucursales.setComisionMixLinea(sucursales.getComisionMixLinea());
					sucursales.setImagen1("rojo.png");
				}

			}

			sucursales.setComisionMixLinea(totalMix);


			Zona_ventaDao daoZo = new Zona_ventaDao();
			List<Zona_venta> listaZona = daoZo.buscarZonaJefeInterno();

			for (Zona_venta zona_venta : listaZona) {
				System.out.println(zona_venta.getId_zona_venta());
				consulta = session.createCriteria(Recaudo.class);			
				consulta.createAlias("zonaVenta", "z");
				consulta.add(Restrictions.eq("z.id_zona_venta", zona_venta.getId_zona_venta()));
				consulta.add(Restrictions.between("fecha", fechaInicial, fechaFinal));
				List<Recaudo> recaudo = consulta.list();

				if (recaudo.size() <= 0 ){
					sucursales.setPresupuestoB(sucursales.getPresupuestoB());
					sucursales.setIngresoRealB(sucursales.getIngresoRealB());
				}
				else{
					System.out.println( recaudo.get(0).getPresupuesto() + " ####### " + recaudo.size());
					sucursales.setPresupuestoB(sucursales.getPresupuestoB().add(recaudo.get(0).getPresupuesto()));
					sucursales.setIngresoRealB(sucursales.getIngresoRealB().add(recaudo.get(0).getReal()));
				}
			}	


			if(sucursales.getIngresoRealB().intValue() == 0 || sucursales.getPresupuestoB().intValue() == 0 ){
				sucursales.setCumplimientoR(new BigDecimal("0"));
			}
			else{
				sucursales.setCumplimientoR(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
			}
			System.out.println(sucursales.getCumplimientoR().intValue() );
			if(sucursales.getCumplimientoR().intValue() >= esquema.getUmbralComision().intValue()){
				BigDecimal comision = funcionario.getComision().getValorBaseVenta().multiply(esquema.getRecaudoCartera());
				System.out.println( comision + "Comisiomn" + sucursales.getCumplimientoR());
				sucursales.setComisionRecaudo(comision.multiply(sucursales.getCumplimientoR()).divide(new BigDecimal("100")));
				sucursales.setImagen1("verde.png");
			}
			else{
				sucursales.setComisionRecaudo(new BigDecimal("0"));
				sucursales.setImagen1("rojo.png");
			}
			sucursales.setDetalleVL("true");
			sucursales.setDetalleVP("true");
			sucursales.setDetalleML("false");
			sucursales.setDetalleZC("true");
			sucursales.setDetalleR("false");

			sucursales.setComisionTotal(sucursales.getComisionMixLinea().add(sucursales.getComisionRecaudo()));
			return sucursales;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	//*Comision Director Regional *//

	@SuppressWarnings("unchecked")
	public ComisionVendedores comisionDirectorRegional(Funcionario funcionario, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		ComisionVendedores sucursales =  new ComisionVendedores();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);
			BigDecimal totalMix = new BigDecimal("0") ;
			BigDecimal totalZonasCargo = new BigDecimal("0") ;

			Zona_FuncionarioDao daoZ = new Zona_FuncionarioDao();
			Zona_Funcionario zona =  daoZ.buscarFuncionarioZona(funcionario.getId_funcionario());

			int oficina = (zona.getCiudad().getId() == 1 )? 1000: (zona.getCiudad().getId() == 7 )? 2000 : ((zona.getCiudad().getId() + 1) * 1000);  
			System.out.println(oficina + "oficina" + funcionario.getPersona().getNombre()+ "fun"+ funcionario.getId_funcionario());
			Criteria consulta = session.createCriteria(PresupuestoE.class);
			consulta.add(Restrictions.eq("oficinaVentas", oficina ));
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal preTotal = (BigDecimal) consulta.uniqueResult();

			LineaDao daoD = new LineaDao();
			List<Linea> lineas =  daoD.listar();

			UsuarioDao daoU = new UsuarioDao();
			Usuario usuario = daoU.buscar(funcionario.getId_funcionario());
			
			EsquemasDao daoE = new EsquemasDao();
			Esquemas esquema = daoE.buscarEsquema(usuario.getPerfil().getId());
			
			for (Linea linea : lineas) {
				consulta = session.createCriteria(PresupuestoE.class);
				consulta.add(Restrictions.eq("linea", linea.getId()));
				consulta.add(Restrictions.eq("oficinaVentas", oficina ));
				consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("ingresos"));
				BigDecimal valor = (BigDecimal) consulta.uniqueResult();
				valor = (valor == null)? new BigDecimal("0"): valor; 
				sucursales.setPresupuestoB(valor);

				consulta = session.createCriteria(Detalle.class);
				consulta.add(Restrictions.eq("linea", linea.getId()));
				consulta.add(Restrictions.eq("sucursal", oficina ));			
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("valorNeto"));
				Long  totalWages = (Long) consulta.uniqueResult();
				totalWages = (totalWages == null)? 0: totalWages;
				sucursales.setIngresoRealB(new BigDecimal(totalWages* -1));

				System.out.println(linea.getId() + "linea" + sucursales.getIngresoRealB() +"  "+ sucursales.getPresupuestoB());
				//					System.out.println(sucursales.getIngresoRealB());
				//					System.out.println(sucursales.getPresupuestoB());

				if(sucursales.getIngresoRealB().intValue() == 0 || sucursales.getPresupuestoB().intValue() == 0 ){
					sucursales.setCumplimientoML(new BigDecimal("0"));
				}
				else{
					sucursales.setCumplimientoML(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				}

				if(sucursales.getCumplimientoML().intValue() >= esquema.getUmbralComision().intValue()){
					BigDecimal distribucion = sucursales.getPresupuestoB().divide(preTotal, 4, BigDecimal.ROUND_HALF_UP);
					//						System.out.println(distribucion + "Cumplimiento XX");
					BigDecimal comision = funcionario.getComision().getValorBaseVenta().multiply(esquema.getMixLinea());
					sucursales.setComisionMixLinea(comision.multiply(distribucion));
					sucursales.setImagen1("verde.png");
					totalMix = totalMix.add(sucursales.getComisionMixLinea());
				}
				else{
					sucursales.setComisionMixLinea(sucursales.getComisionMixLinea());
					sucursales.setImagen1("rojo.png");
				}

			}
			sucursales.setComisionMixLinea(totalMix);


			for (Linea linea : lineas) {
				consulta = session.createCriteria(PresupuestoE.class);
				consulta.add(Restrictions.eq("linea", linea.getId()));
				consulta.add(Restrictions.eq("funcionario", funcionario.getId_funcionario() ));
				consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("ingresos"));
				BigDecimal valor = (BigDecimal) consulta.uniqueResult();
				valor = (valor == null)? new BigDecimal("0"): valor; 
				sucursales.setPresupuestoB(valor);

				consulta.setProjection(Projections.sum("distribucionPorLinea"));
				BigDecimal disPorLinea = (BigDecimal) consulta.uniqueResult();
				disPorLinea = (disPorLinea == null)? new BigDecimal("0"): disPorLinea; 


				consulta = session.createCriteria(Detalle.class);
				consulta.add(Restrictions.eq("linea", linea.getId()));
				consulta.add(Restrictions.eq("funcionario", funcionario.getId_funcionario()));			
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("valorNeto"));
				Long  totalWages = (Long) consulta.uniqueResult();
				totalWages = (totalWages == null)? 0: totalWages;
				sucursales.setIngresoRealB(new BigDecimal(totalWages* -1));

				System.out.println(linea.getId() + "linea" + sucursales.getIngresoRealB() +"  "+ sucursales.getPresupuestoB());
				//					System.out.println(sucursales.getIngresoRealB());
				//					System.out.println(sucursales.getPresupuestoB());

				if(sucursales.getIngresoRealB().intValue() == 0 || sucursales.getPresupuestoB().intValue() == 0 ){
					sucursales.setCumplimientoZC(new BigDecimal("0"));
				}
				else{
					sucursales.setCumplimientoZC(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				}

				if(sucursales.getCumplimientoZC().intValue() >= esquema.getUmbralComision().intValue()){
					//BigDecimal distribucion = sucursales.getPresupuestoB().divide(preTotal, 4, BigDecimal.ROUND_HALF_UP);
					//						System.out.println(distribucion + "Cumplimiento XX");
					BigDecimal comision = funcionario.getComision().getValorBaseVenta().multiply(esquema.getZonasCargo());
					sucursales.setComisionZonasCargo(comision.multiply(disPorLinea));
					sucursales.setImagen1("verde.png");
					totalZonasCargo = totalZonasCargo.add(sucursales.getComisionZonasCargo());
				}
				else{
					sucursales.setComisionZonasCargo(sucursales.getComisionZonasCargo());
					sucursales.setImagen1("rojo.png");
				}

			}

			sucursales.setComisionZonasCargo(totalZonasCargo);

			Zona_ventaDao daoZo = new Zona_ventaDao();
			List<Zona_venta> listaZona = daoZo.buscarZonaSucursal(zona.getCiudad().getId());

			for (Zona_venta zona_venta : listaZona) {
				//					System.out.println(zona_venta.getId_zona_venta());
				consulta = session.createCriteria(Recaudo.class);			
				consulta.createAlias("zonaVenta", "z");
				consulta.add(Restrictions.eq("z.id_zona_venta", zona_venta.getId_zona_venta()));
				consulta.add(Restrictions.between("fecha", fechaInicial, fechaFinal));
				List<Recaudo> recaudo = consulta.list();

				if (recaudo.size() <= 0 ){
					sucursales.setPresupuestoB(sucursales.getPresupuestoB());
					sucursales.setIngresoRealB(sucursales.getIngresoRealB());
				}
				else{
					//						System.out.println( recaudo.get(0).getPresupuesto() + " ####### " + recaudo.size());
					sucursales.setPresupuestoB(sucursales.getPresupuestoB().add(recaudo.get(0).getPresupuesto()));
					sucursales.setIngresoRealB(sucursales.getIngresoRealB().add(recaudo.get(0).getReal()));
				}
			}	


			if(sucursales.getIngresoRealB().intValue() == 0 || sucursales.getPresupuestoB().intValue() == 0 ){
				sucursales.setCumplimientoR(new BigDecimal("0"));
			}
			else{
				sucursales.setCumplimientoR(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
			}
			//				System.out.println(sucursales.getCumplimientoR().intValue() );
			if(sucursales.getCumplimientoR().intValue() >= esquema.getUmbralComision().intValue()){
				BigDecimal comision = funcionario.getComision().getValorBaseVenta().multiply(esquema.getRecaudoCartera());
				//					System.out.println( comision + "Comisiomn" + sucursales.getCumplimientoR());
				sucursales.setComisionRecaudo(comision.multiply(sucursales.getCumplimientoR()).divide(new BigDecimal("100")));
				sucursales.setImagen1("verde.png");
			}
			else{
				sucursales.setComisionRecaudo(new BigDecimal("0"));
				sucursales.setImagen1("rojo.png");
			}
			sucursales.setDetalleVL("true");
			sucursales.setDetalleVP("true");
			sucursales.setDetalleML("false");
			sucursales.setDetalleZC("false");
			sucursales.setDetalleR("false");

			sucursales.setComisionTotal(sucursales.getComisionMixLinea().add(sucursales.getComisionRecaudo().add(sucursales.getComisionZonasCargo())));

			return sucursales;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	public List<ComisionVendedores>  directorVL(int linea, Funcionario funcionario, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<ComisionVendedores> listasucursales = new ArrayList<>();   
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);

			Criteria consulta = session.createCriteria(PresupuestoE.class);			
			consulta.add(Restrictions.eq("linea", linea));
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal totalPre = (BigDecimal) consulta.uniqueResult();


			CiudadDao daoC = new CiudadDao();
			List<Ciudad> ciudades =  daoC.listar();

			LineaDao daoL = new LineaDao();
			Linea lineas = daoL.buscar(linea);
			for (Ciudad ciudad : ciudades) {
				ComisionVendedores sucursales =  new ComisionVendedores();
				int oficina = (ciudad.getId() == 1 )? 1000: (ciudad.getId() == 7 )? 2000 : ((ciudad.getId() + 1) * 1000);

				sucursales.setConcepto(ciudad.getNombre());
				sucursales.setNombre(lineas.getNombre());
				consulta = session.createCriteria(PresupuestoE.class);			
				consulta.add(Restrictions.eq("oficinaVentas", oficina));
				consulta.add(Restrictions.eq("linea", linea));
				consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("ingresos"));
				BigDecimal valor = (BigDecimal) consulta.uniqueResult();
				valor = (valor == null) ? new BigDecimal("0"): valor;
				sucursales.setPresupuestoB(valor);

				consulta.setProjection(Projections.sum("utilidad"));
				valor = (BigDecimal) consulta.uniqueResult();
				sucursales.setUtilpresupuesto(valor);

				consulta = session.createCriteria(Detalle.class);
				consulta.add(Restrictions.eq("sucursal", oficina));
				consulta.add(Restrictions.eq("linea", linea));
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("valorNeto"));
				Long  totalWages = (Long) consulta.uniqueResult();
				totalWages = (totalWages == null)? 0: totalWages;
				sucursales.setIngresoRealB(new BigDecimal(totalWages* -1));

				consulta.setProjection(Projections.sum("costoTotal"));
				totalWages = (Long) consulta.uniqueResult();
				totalWages = (totalWages == null)? 0: totalWages;
				sucursales.setUtilidadReal(new BigDecimal(totalWages));

				//			System.out.println(sucursales.getIngresoRealB());
				//			System.out.println(sucursales.getPresupuestoB());
				if(sucursales.getIngresoRealB().longValue() == 0 || sucursales.getPresupuestoB().longValue() == 0 ){
					sucursales.setCumplimiento(new BigDecimal("0"));
				}
				else{
					sucursales.setCumplimiento(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
					sucursales.setCumplimiento(sucursales.getCumplimiento().setScale(2, BigDecimal.ROUND_HALF_UP));
				}

				sucursales.setComisionRecaudo(funcionario.getComision().getValorBaseVenta());
				BigDecimal comision = funcionario.getComision().getValorBaseVenta().multiply(new BigDecimal("0.8"));
				sucursales.setComisionTotal(comision);
				sucursales.setUmbralCV(funcionario.getComision().getUmbralVenta());
				if(sucursales.getCumplimiento().intValue() >= 85){
					sucursales.setComisionMixLinea(sucursales.getPresupuestoB().divide(totalPre, 4, BigDecimal.ROUND_HALF_UP));
					sucursales.setComisionVentaLinea(comision.multiply(sucursales.getCumplimiento()).divide(new BigDecimal("100")).multiply(sucursales.getComisionMixLinea()));
					sucursales.setComisionMixLinea(sucursales.getComisionMixLinea().multiply(new BigDecimal("100")));
					sucursales.setComisionMixLinea(sucursales.getComisionMixLinea().setScale(2, BigDecimal.ROUND_HALF_UP));
					sucursales.setImagen1("verde.png");
				}
				else{
					sucursales.setComisionMixLinea(sucursales.getPresupuestoB().divide(totalPre, 4, BigDecimal.ROUND_HALF_UP));
					sucursales.setComisionMixLinea(sucursales.getComisionMixLinea().multiply(new BigDecimal("100")));
					sucursales.setComisionMixLinea(sucursales.getComisionMixLinea().setScale(2, BigDecimal.ROUND_HALF_UP));
					sucursales.setComisionVentaLinea(new BigDecimal("0"));
					sucursales.setImagen1("rojo.png");
				}
				listasucursales.add(sucursales);

			}
			return listasucursales;			
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	public List<ComisionVendedores>  directorAnVL(Funcionario funcionario, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<ComisionVendedores> listasucursales = new ArrayList<>();   
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);

			List<Linea> lineas = new ArrayList<>();
			Linea linea1 = new Linea();
			linea1.setId(6);
			linea1.setNombre("Acero Antidesgaste");
			lineas.add(0, linea1);
			linea1 = new Linea();
			linea1.setId(10);
			linea1.setNombre("Acero Oxi-Antidesgaste");
			lineas.add(1, linea1);

			CiudadDao daoC = new CiudadDao();
			List<Ciudad> ciudades =  daoC.listar();
			ComisionVendedores sucursales = null;
			for (Ciudad ciudad : ciudades) {

				int oficina = (ciudad.getId() == 1 )? 1000: (ciudad.getId() == 7 )? 2000 : ((ciudad.getId() + 1) * 1000);
				for (Linea linea : lineas) {
					sucursales =  new ComisionVendedores();
					Criteria consulta = session.createCriteria(PresupuestoE.class);			
					consulta.add(Restrictions.eq("linea", linea.getId()));
					consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
					consulta.setProjection(Projections.sum("ingresos"));
					BigDecimal totalPre = (BigDecimal) consulta.uniqueResult();


					sucursales.setConcepto(ciudad.getNombre());
					sucursales.setNombre(linea.getNombre());
					consulta = session.createCriteria(PresupuestoE.class);			
					consulta.add(Restrictions.eq("oficinaVentas", oficina));
					consulta.add(Restrictions.eq("linea", linea.getId()));
					consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
					consulta.setProjection(Projections.sum("ingresos"));
					BigDecimal valor = (BigDecimal) consulta.uniqueResult();
					valor = (valor == null) ? new BigDecimal("0"): valor;
					sucursales.setPresupuestoB(valor);

					consulta.setProjection(Projections.sum("utilidad"));
					valor = (BigDecimal) consulta.uniqueResult();
					sucursales.setUtilpresupuesto(valor);

					consulta = session.createCriteria(Detalle.class);
					consulta.add(Restrictions.eq("sucursal", oficina));
					consulta.add(Restrictions.eq("linea", linea.getId()));
					consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
					consulta.setProjection(Projections.sum("valorNeto"));
					Long  totalWages = (Long) consulta.uniqueResult();
					totalWages = (totalWages == null)? 0: totalWages;
					sucursales.setIngresoRealB(new BigDecimal(totalWages* -1));

					consulta.setProjection(Projections.sum("costoTotal"));
					totalWages = (Long) consulta.uniqueResult();
					totalWages = (totalWages == null)? 0: totalWages;
					sucursales.setUtilidadReal(new BigDecimal(totalWages));

					//			System.out.println(sucursales.getIngresoRealB());
					//			System.out.println(sucursales.getPresupuestoB());
					if(sucursales.getIngresoRealB().longValue() == 0 || sucursales.getPresupuestoB().longValue() == 0 ){
						sucursales.setCumplimiento(new BigDecimal("0"));
					}
					else{
						sucursales.setCumplimiento(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
						sucursales.setCumplimiento(sucursales.getCumplimiento().setScale(2, BigDecimal.ROUND_HALF_UP));
					}

					sucursales.setComisionRecaudo(funcionario.getComision().getValorBaseVenta());
					BigDecimal comision = funcionario.getComision().getValorBaseVenta().multiply(new BigDecimal("0.8"));
					sucursales.setComisionTotal(comision);
					sucursales.setUmbralCV(funcionario.getComision().getUmbralVenta());
					if(sucursales.getCumplimiento().intValue() >= 85){
						sucursales.setComisionMixLinea(sucursales.getPresupuestoB().divide(totalPre, 4, BigDecimal.ROUND_HALF_UP));
						sucursales.setComisionVentaLinea(comision.multiply(sucursales.getCumplimiento()).divide(new BigDecimal("100")).multiply(sucursales.getComisionMixLinea()));
						sucursales.setComisionMixLinea(sucursales.getComisionMixLinea().multiply(new BigDecimal("100")));
						sucursales.setComisionMixLinea(sucursales.getComisionMixLinea().setScale(2, BigDecimal.ROUND_HALF_UP));
						sucursales.setImagen1("verde.png");
					}
					else{
						sucursales.setComisionMixLinea(sucursales.getPresupuestoB().divide(totalPre, 4, BigDecimal.ROUND_HALF_UP));
						sucursales.setComisionMixLinea(sucursales.getComisionMixLinea().multiply(new BigDecimal("100")));
						sucursales.setComisionMixLinea(sucursales.getComisionMixLinea().setScale(2, BigDecimal.ROUND_HALF_UP));
						sucursales.setComisionVentaLinea(new BigDecimal("0"));
						sucursales.setImagen1("rojo.png");
					}
					listasucursales.add(sucursales);
				}
			}
			return listasucursales;			
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	//*Detalle de cumplimiento pais directores "gh" "detalleVP"*//

	public List<ComisionVendedores> cumplimientoPais(Funcionario funcionario, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<ComisionVendedores> listasucursales = new ArrayList<>();   
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);

			Criteria consulta = session.createCriteria(PresupuestoE.class);			
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal totalPre = (BigDecimal) consulta.uniqueResult();


			CiudadDao daoC = new CiudadDao();
			List<Ciudad> ciudades =  daoC.listar();

			for (Ciudad ciudad : ciudades) {
				ComisionVendedores sucursales =  new ComisionVendedores();
				int oficina = (ciudad.getId() == 1 )? 1000: (ciudad.getId() == 7 )? 2000 : ((ciudad.getId() + 1) * 1000);

				sucursales.setConcepto(ciudad.getNombre());
				consulta = session.createCriteria(PresupuestoE.class);			
				consulta.add(Restrictions.eq("oficinaVentas", oficina));
				consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("ingresos"));
				BigDecimal valor = (BigDecimal) consulta.uniqueResult();
				valor = (valor == null) ? new BigDecimal("0"): valor;
				sucursales.setPresupuestoB(valor);

				consulta.setProjection(Projections.sum("utilidad"));
				valor = (BigDecimal) consulta.uniqueResult();
				sucursales.setUtilpresupuesto(valor);

				consulta = session.createCriteria(Detalle.class);
				consulta.add(Restrictions.eq("sucursal", oficina));
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("valorNeto"));
				Long  totalWages = (Long) consulta.uniqueResult();
				totalWages = (totalWages == null)? 0: totalWages;
				sucursales.setIngresoRealB(new BigDecimal(totalWages* -1));

				consulta.setProjection(Projections.sum("costoTotal"));
				totalWages = (Long) consulta.uniqueResult();
				totalWages = (totalWages == null)? 0: totalWages;
				sucursales.setUtilidadReal(new BigDecimal(totalWages));

				//			System.out.println(sucursales.getIngresoRealB());
				//			System.out.println(sucursales.getPresupuestoB());
				if(sucursales.getIngresoRealB().longValue() == 0 || sucursales.getPresupuestoB().longValue() == 0 ){
					sucursales.setCumplimiento(new BigDecimal("0"));
				}
				else{
					sucursales.setCumplimiento(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
					sucursales.setCumplimiento(sucursales.getCumplimiento().setScale(2, BigDecimal.ROUND_HALF_UP));
				}

				sucursales.setComisionRecaudo(funcionario.getComision().getValorBaseVenta());
				BigDecimal comision = funcionario.getComision().getValorBaseVenta().multiply(new BigDecimal("0.2"));
				sucursales.setComisionTotal(comision);
				sucursales.setUmbralCV(funcionario.getComision().getUmbralVenta());
				if(sucursales.getCumplimiento().intValue() >= 85){
					sucursales.setComisionMixLinea(sucursales.getPresupuestoB().divide(totalPre, 4, BigDecimal.ROUND_HALF_UP));
					sucursales.setComisionVentaLinea(comision.multiply(sucursales.getCumplimiento()).divide(new BigDecimal("100")).multiply(sucursales.getComisionMixLinea()));
					sucursales.setComisionMixLinea(sucursales.getComisionMixLinea().multiply(new BigDecimal("100")));
					sucursales.setComisionMixLinea(sucursales.getComisionMixLinea().setScale(2, BigDecimal.ROUND_HALF_UP));
					sucursales.setImagen1("verde.png");
				}
				else{
					sucursales.setComisionMixLinea(sucursales.getPresupuestoB().divide(totalPre, 4, BigDecimal.ROUND_HALF_UP));
					sucursales.setComisionMixLinea(sucursales.getComisionMixLinea().multiply(new BigDecimal("100")));
					sucursales.setComisionMixLinea(sucursales.getComisionMixLinea().setScale(2, BigDecimal.ROUND_HALF_UP));
					sucursales.setComisionVentaLinea(new BigDecimal("0"));
					sucursales.setImagen1("rojo.png");
				}
				listasucursales.add(sucursales);

			}
			return listasucursales;			
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	//*Detalle mix de lineas Comision Director Comercial  "gh" "detalleML" *//

	public List<ComisionVendedores> detalleML(Funcionario funcionario, String fecMes, String fecYear){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<ComisionVendedores> listaLineas = new ArrayList<>();
		
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);
			BigDecimal totalMix = new BigDecimal("0") ;

			Zona_FuncionarioDao daoF = new Zona_FuncionarioDao();
			Zona_Funcionario zona = daoF.buscarFuncionarioZona(funcionario.getId_funcionario());				
			int oficina = (zona.getCiudad().getId() == 1 )? 1000: (zona.getCiudad().getId() == 7 )? 2000 : ((zona.getCiudad().getId() + 1) * 1000);

			Criteria consulta = session.createCriteria(PresupuestoE.class);
			consulta.add(Restrictions.eq("oficinaVentas", oficina ));
			consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.sum("ingresos"));
			BigDecimal preTotal = (BigDecimal) consulta.uniqueResult();

			LineaDao daoD = new LineaDao();
			List<Linea> lineas =  daoD.listar();

			for (Linea linea : lineas) {
				ComisionVendedores sucursales =  new ComisionVendedores();
				
				sucursales.setConcepto(zona.getCiudad().getNombre());
				sucursales.setNombre(linea.getNombre());
				
				consulta = session.createCriteria(PresupuestoE.class);
				consulta.add(Restrictions.eq("linea", linea.getId()));
				consulta.add(Restrictions.eq("oficinaVentas", oficina ));
				consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("ingresos"));
				BigDecimal valor = (BigDecimal) consulta.uniqueResult();
				sucursales.setPresupuestoB(valor);

				consulta = session.createCriteria(Detalle.class);
				consulta.add(Restrictions.eq("linea", linea.getId()));
				consulta.add(Restrictions.eq("sucursal", oficina ));			
				consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
				consulta.setProjection(Projections.sum("valorNeto"));
				Long  totalWages = (Long) consulta.uniqueResult();
				totalWages = (totalWages == null)? 0: totalWages;
				sucursales.setIngresoRealB(new BigDecimal(totalWages* -1));

				//				System.out.println(sucursales.getIngresoRealB());
				//				System.out.println(sucursales.getPresupuestoB());

				if(sucursales.getIngresoRealB().intValue() == 0 || sucursales.getPresupuestoB().intValue() == 0 ){
					sucursales.setCumplimientoML(new BigDecimal("0"));
				}
				else{
					sucursales.setCumplimientoML(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
					sucursales.setCumplimientoML(sucursales.getCumplimientoML().setScale(2, BigDecimal.ROUND_HALF_UP));
				}
				UsuarioDao daoU = new UsuarioDao();
				Usuario usuario = daoU.buscar(funcionario.getId_funcionario());
				
				EsquemasDao daoE = new EsquemasDao();
				Esquemas esquema = daoE.buscarEsquema(usuario.getPerfil().getId());
				
				
				sucursales.setComisionRecaudo(funcionario.getComision().getValorBaseVenta());
				BigDecimal comision = funcionario.getComision().getValorBaseVenta().multiply(esquema.getMixLinea());
				sucursales.setComisionTotal(comision);
				sucursales.setUmbralCV(funcionario.getComision().getUmbralVenta());
				
				if(sucursales.getCumplimientoML().intValue() >= esquema.getUmbralComision().intValue()){
					BigDecimal distribucion = sucursales.getPresupuestoB().divide(preTotal, 4, BigDecimal.ROUND_HALF_UP);
					sucursales.setComisionMixLinea(comision.multiply(distribucion).setScale(2, BigDecimal.ROUND_HALF_UP));
					sucursales.setImagen1("verde.png");
					sucursales.setComisionVentaLinea(distribucion.multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP));
					totalMix = totalMix.add(sucursales.getComisionMixLinea());					
				}
				else{
					BigDecimal distribucion = sucursales.getPresupuestoB().divide(preTotal, 4, BigDecimal.ROUND_HALF_UP);
					sucursales.setComisionVentaLinea(distribucion.multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP));
					sucursales.setComisionMixLinea(new BigDecimal("0"));
					sucursales.setImagen1("rojo.png");
				}
				listaLineas.add(sucursales);
			}
			//sucursales.setComisionMixLinea(totalMix);
			return listaLineas;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	
	//*Detalle recaudo de  Comision Director  *//

		@SuppressWarnings("unchecked")
		public List<ComisionVendedores> detalleRecaudo(Funcionario funcionario, String fecMes, String fecYear){

			Session session = HibernateUtil.getSessionfactory().openSession();
			List<ComisionVendedores> listaLineas = new ArrayList<>();
			ComisionVendedores sucursales =  new ComisionVendedores();
			try{
				Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
				Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);
				
				UsuarioDao daoU = new UsuarioDao();
				Usuario usuario = daoU.buscar(funcionario.getId_funcionario());
				
				EsquemasDao daoE = new EsquemasDao();
				Esquemas esquema = daoE.buscarEsquema(usuario.getPerfil().getId());
				
				Zona_FuncionarioDao daoZ = new Zona_FuncionarioDao();
				Zona_Funcionario zona = daoZ.buscarFuncionarioZona(funcionario.getId_funcionario());
				 
				Zona_ventaDao daoZo = new Zona_ventaDao();
				List<Zona_venta> listaZona = daoZo.buscarZonaSucursal(zona.getCiudad().getId());
				
				sucursales.setPresupuestoB(new BigDecimal("0"));
				sucursales.setIngresoRealB(new BigDecimal("0"));
				for (Zona_venta zona_venta : listaZona) {
					//				System.out.println(zona_venta.getId_zona_venta());
					Criteria consulta = session.createCriteria(Recaudo.class);			
					consulta.createAlias("zonaVenta", "z");
					consulta.add(Restrictions.eq("z.id_zona_venta", zona_venta.getId_zona_venta()));
					consulta.add(Restrictions.between("fecha", fechaInicial, fechaFinal));
					List<Recaudo> recaudo = consulta.list();

					if (recaudo.size() <= 0 ){
						sucursales.setPresupuestoB(sucursales.getPresupuestoB());
						sucursales.setIngresoRealB(sucursales.getIngresoRealB());
					}
					else{
						//					System.out.println( recaudo.get(0).getPresupuesto() + " ####### " + recaudo.size());
						sucursales.setPresupuestoB(sucursales.getPresupuestoB().add(recaudo.get(0).getPresupuesto()));
						sucursales.setIngresoRealB(sucursales.getIngresoRealB().add(recaudo.get(0).getReal()));
					}
				}	


				if(sucursales.getIngresoRealB().intValue() == 0 || sucursales.getPresupuestoB().intValue() == 0 ){
					sucursales.setCumplimientoR(new BigDecimal("0"));
				}
				else{
					sucursales.setCumplimientoR(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				}
				BigDecimal comision = funcionario.getComision().getValorBaseVenta().multiply(esquema.getRecaudoCartera());
				sucursales.setComisionVentaLinea(funcionario.getComision().getValorBaseVenta());
				sucursales.setComisionTotal(comision);	
				sucursales.setUmbralCV(esquema.getUmbralComision());
				if(sucursales.getCumplimientoR().intValue() >= esquema.getUmbralComision().intValue()){
					sucursales.setComisionRecaudo(comision.multiply(sucursales.getCumplimientoR()).divide(new BigDecimal("100")));
					sucursales.setImagen1("verde.png");
				}
				else{
					sucursales.setComisionRecaudo(new BigDecimal("0"));
					sucursales.setImagen1("rojo.png");
				}
				listaLineas.add(sucursales); 
				
				return listaLineas;
			} catch (RuntimeException ex) {
				throw ex;
			}
			finally{
				session.close();
			}
		}

		//*detalle de los director de las zonas a cargo *//

		public List<ComisionVendedores> detalleZC(Funcionario funcionario, String fecMes, String fecYear){

			Session session = HibernateUtil.getSessionfactory().openSession();
			List<ComisionVendedores> listaLineas = new ArrayList<>();
			
			try{
				Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
				Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);
				BigDecimal totalZonasCargo = new BigDecimal("0") ;

				LineaDao daoD = new LineaDao();
				List<Linea> lineas =  daoD.listar();

				UsuarioDao daoU = new UsuarioDao();
				Usuario usuario = daoU.buscar(funcionario.getId_funcionario());
				
				EsquemasDao daoE = new EsquemasDao();
				Esquemas esquema = daoE.buscarEsquema(usuario.getPerfil().getId());
				
				for (Linea linea : lineas) {
			
					ComisionVendedores sucursales =  new ComisionVendedores();
					sucursales.setNombre(linea.getNombre());
				    Criteria consulta = session.createCriteria(PresupuestoE.class);
					consulta.add(Restrictions.eq("linea", linea.getId()));
					consulta.add(Restrictions.eq("funcionario", funcionario.getId_funcionario() ));
					consulta.add(Restrictions.between("periodo", fechaInicial, fechaFinal));
					consulta.setProjection(Projections.sum("ingresos"));
					BigDecimal valor = (BigDecimal) consulta.uniqueResult();
					valor = (valor == null)? new BigDecimal("0"): valor; 
					sucursales.setPresupuestoB(valor);

					consulta.setProjection(Projections.sum("distribucionPorLinea"));
					BigDecimal disPorLinea = (BigDecimal) consulta.uniqueResult();
					disPorLinea = (disPorLinea == null)? new BigDecimal("0"): disPorLinea; 


					consulta = session.createCriteria(Detalle.class);
					consulta.add(Restrictions.eq("linea", linea.getId()));
					consulta.add(Restrictions.eq("funcionario", funcionario.getId_funcionario()));			
					consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
					consulta.setProjection(Projections.sum("valorNeto"));
					Long  totalWages = (Long) consulta.uniqueResult();
					totalWages = (totalWages == null)? 0: totalWages;
					sucursales.setIngresoRealB(new BigDecimal(totalWages* -1));

					if(sucursales.getIngresoRealB().intValue() == 0 || sucursales.getPresupuestoB().intValue() == 0 ){
						sucursales.setCumplimientoZC(new BigDecimal("0"));
					}
					else{
						sucursales.setCumplimientoZC(sucursales.getIngresoRealB().divide(sucursales.getPresupuestoB(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP));
					}
					BigDecimal comision = funcionario.getComision().getValorBaseVenta().multiply(esquema.getZonasCargo());
					sucursales.setComisionRecaudo(funcionario.getComision().getValorBaseVenta());
					sucursales.setComisionTotal(comision);
					sucursales.setUmbralCV(esquema.getUmbralComision());
					sucursales.setComisionMixLinea(disPorLinea.multiply(new BigDecimal("100")));
					sucursales.setComisionMixLinea(sucursales.getComisionMixLinea().setScale(2, BigDecimal.ROUND_HALF_UP));
					if(sucursales.getCumplimientoZC().intValue() >= esquema.getUmbralComision().intValue()){
						sucursales.setComisionZonasCargo(comision.multiply(disPorLinea));
						sucursales.setImagen1("verde.png");
						totalZonasCargo = totalZonasCargo.add(sucursales.getComisionZonasCargo());
					}
					else{
						sucursales.setComisionZonasCargo(new BigDecimal("0"));
						sucursales.setImagen1("rojo.png");
					}
					listaLineas.add(sucursales); 
				}

				//sucursales.setComisionZonasCargo(totalZonasCargo);
				
				
				return listaLineas;
			} catch (RuntimeException ex) {
				throw ex;
			}
			finally{
				session.close();
			}
		}			
}
