package co.com.CGAwebComercial.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.CargaTrabajo;
import co.com.CGAwebComercial.util.HibernateUtil;

public class CargaTrabajoDao extends GenericDao<CargaTrabajo> {


	@SuppressWarnings("unchecked")
	public List<CargaTrabajo> listarCargaPorArea(Long id, Long idSucursal){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<CargaTrabajo> listaCargaT = new ArrayList<>();
		try{
			
			Criteria consulta = session.createCriteria(CargaTrabajo.class);
			if(idSucursal == 0){				
				consulta.createAlias("trabajoArea", "ta");
				consulta.add(Restrictions.eq("ta.id", id ));
				listaCargaT = consulta.list();
				
			}
			else{				
				consulta.createAlias("trabajoArea", "ta");
				consulta.createAlias("sucursal", "s");
				consulta.add(Restrictions.eq("ta.id", id ));
				consulta.add(Restrictions.eq("s.id", idSucursal ));
				listaCargaT = consulta.list();
			}
			return listaCargaT;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

	public int ultimoRegistroIngresado(){

		Session session = HibernateUtil.getSessionfactory().openSession();
		try{

			Criteria consulta = session.createCriteria(CargaTrabajo.class);
			consulta.setProjection(Projections.max("id"));
			int valor =(Integer) consulta.uniqueResult();
			return valor;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

}
