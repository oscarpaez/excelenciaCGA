package co.com.CGAwebComercial.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.Detallesin;
import co.com.CGAwebComercial.entyties.bajaRotacion;
import co.com.CGAwebComercial.util.HibernateUtil;

public class DetallesinDao extends GenericDao<Detallesin>{

	//*lista el detalle de un Vendedor Especialista o Interno *//

	@SuppressWarnings("unchecked")
	public List<Detallesin> listarDetallePorFecha(String tipo, int codigo, int idPersona,  String fecMes, String fecYear ){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Detallesin> detalle = null;
		List<bajaRotacion> detalleBR = null;
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);

			Criteria consulta = session.createCriteria(Detallesin.class);
			consulta.add(Restrictions.eq(tipo, idPersona));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			detalle = consulta.list();

			for (Detallesin detallesin : detalle) {
				if(tipo.equals("funcionario")){
					detallesin.setFuncionario(codigo);
				}
				else{
					detallesin.setFuncionarioI(codigo);
				}
				merge(detallesin);
			}

			String tipoBR = (tipo.equals("funcionario"))? "codEspecialista" :"codVendedorInt";

			consulta = session.createCriteria(bajaRotacion.class);
			consulta.add(Restrictions.eq(tipoBR, idPersona));
			consulta.add(Restrictions.between("fechaFactura", fechaInicial, fechaFinal));
			detalleBR = consulta.list();

			BajaRotacionDao daoB = new BajaRotacionDao();
			for (bajaRotacion bajaRotacion : detalleBR) {
				if(tipoBR.equals("codEspecialista")){
					bajaRotacion.setCodEspecialista(codigo);
				}
				else{
					bajaRotacion.setCodVendedorInt(codigo);
				}
				daoB.editar(bajaRotacion);
			}

			return detalle;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}


	//*Actualiza la tabla lenta baja rotacion y detalle sin Por cliente *//

	@SuppressWarnings("unchecked")
	public List<Detallesin> actualizarDetalle(String tipo, int codigo, int idPersona, int cliente, String fecMes, String fecYear ){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Detallesin> detalle = null;
		List<bajaRotacion> detalleBR = null;
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);

			Criteria consulta = session.createCriteria(Detallesin.class);
			consulta.add(Restrictions.eq(tipo, idPersona));
			consulta.add(Restrictions.eq("cliente", cliente));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			detalle = consulta.list();

			for (Detallesin detallesin : detalle) {
				if(tipo.equals("funcionario")){
					detallesin.setFuncionario(codigo);
				}
				else{
					detallesin.setFuncionarioI(codigo);
				}
				merge(detallesin);
			}

			String tipoBR = (tipo.equals("funcionario"))? "codEspecialista" :"codVendedorInt";

			consulta = session.createCriteria(bajaRotacion.class);
			consulta.add(Restrictions.eq(tipoBR, idPersona));
			consulta.add(Restrictions.eq("codCliente", cliente));
			consulta.add(Restrictions.between("fechaFactura", fechaInicial, fechaFinal));
			detalleBR = consulta.list();

			BajaRotacionDao daoB = new BajaRotacionDao();
			for (bajaRotacion bajaRotacion : detalleBR) {
				if(tipoBR.equals("codEspecialista")){
					bajaRotacion.setCodEspecialista(codigo);
				}
				else{
					bajaRotacion.setCodVendedorInt(codigo);
				}
				daoB.editar(bajaRotacion);
			}
			return detalle;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}
	
	@SuppressWarnings("unchecked")
	public List<Detallesin> actualizarDetallePorLinea(String tipo, int codigo, int idLinea, int idLineaA, String fecMes, String fecYear ){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Detallesin> detalle = null;
		List<bajaRotacion> detalleBR = null;
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);

			Criteria consulta = session.createCriteria(Detallesin.class);
			consulta.add(Restrictions.eq(tipo, codigo));
			consulta.add(Restrictions.eq("linea", idLineaA));
			consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			detalle = consulta.list();

			for (Detallesin detallesin : detalle) {
				System.out.println(detallesin.getLinea() + "WWW" +  idLinea);
				detallesin.setLinea(idLinea);				
				editar(detallesin);
			}

			String tipoBR = (tipo.equals("funcionario"))? "codEspecialista" :"codVendedorInt";

			consulta = session.createCriteria(bajaRotacion.class);
			consulta.add(Restrictions.eq(tipoBR, codigo));
			consulta.add(Restrictions.eq("codLinea", idLineaA));
			consulta.add(Restrictions.between("fechaFactura", fechaInicial, fechaFinal));
			detalleBR = consulta.list();

			BajaRotacionDao daoB = new BajaRotacionDao();
			for (bajaRotacion bajaRotacion : detalleBR) {
				
				bajaRotacion.setCodLinea(idLinea);				
				daoB.editar(bajaRotacion);
			}
			return detalle;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}	
	}
}
