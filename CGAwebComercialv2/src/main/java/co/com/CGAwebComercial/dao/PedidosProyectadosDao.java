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

import co.com.CGAwebComercial.entyties.PedidosProyectados;
import co.com.CGAwebComercial.util.HibernateUtil;

public class PedidosProyectadosDao extends GenericDao<PedidosProyectados> {

	public BigDecimal valorPedidosProyectados(int idFun){

		Session session = HibernateUtil.getSessionfactory().openSession();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();

			Criteria consulta = session.createCriteria(PedidosProyectados.class);
			consulta.createAlias("funcionario", "f");
			consulta.add(Restrictions.eq("f.id_funcionario", idFun));
			consulta.add(Restrictions.between("fechaEntrega", fechaInicial , fechaFinal));
			consulta.setProjection(Projections.sum("valorPedido"));
			Long  totalWages = (Long) consulta.uniqueResult();
			BigDecimal total =(totalWages == null)? new BigDecimal("0"):new BigDecimal(totalWages);
			return total;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	public BigDecimal valorPedidosProyectadosOficina(int idCiudad){

		Session session = HibernateUtil.getSessionfactory().openSession();
		BigDecimal  total = new BigDecimal("0");
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();

			//tipo =(tipo == "funcionario")? "codEspecialista": "codInterno";
			
			//for (Zona_venta zona: lista) {

				Criteria consulta = session.createCriteria(PedidosProyectados.class);
				consulta.createAlias("funcionario", "f");
				consulta.createAlias("ciudad", "c");
				consulta.add(Restrictions.eq("c.id", idCiudad));
				consulta.add(Restrictions.between("fechaEntrega", fechaInicial , fechaFinal));
				consulta.setProjection(Projections.sum("valorPedido"));
				Long  totalWages = (Long) consulta.uniqueResult();
				totalWages= (totalWages == null)? 0: totalWages;
				//System.out.println(total + " ** " + zona.getId_zona_venta());
				total = total.add(new BigDecimal(totalWages));
			//}
			return total;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<PedidosProyectados> pedidosProyectadosUsuario(int idFun){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<PedidosProyectados> listaPP = new ArrayList<>();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();

			Criteria consulta = session.createCriteria(PedidosProyectados.class);
			consulta.createAlias("funcionario", "f");
			consulta.add(Restrictions.eq("f.id_funcionario", idFun));
			consulta.add(Restrictions.between("fechaEntrega", fechaInicial , fechaFinal));
			listaPP = consulta.list();
			return listaPP;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<PedidosProyectados> pedidosProyectadosOficina(int idCiudad){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<PedidosProyectados> listaPP = new ArrayList<>();
		try{
			Date fechaFinal = fechaFinal();
			Date fechaInicial = fechaInicial();

			Criteria consulta = session.createCriteria(PedidosProyectados.class);
			consulta.createAlias("ciudad", "c");
			if(idCiudad == 3){
				Criterion resul =Restrictions.in("c.id", new Integer[]{3,6});
				consulta.add(resul);
			}
			else{
				consulta.add(Restrictions.eq("c.id", idCiudad));				
			}
			consulta.add(Restrictions.between("fechaEntrega", fechaInicial , fechaFinal));
			listaPP = consulta.list();
			return listaPP;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

}
