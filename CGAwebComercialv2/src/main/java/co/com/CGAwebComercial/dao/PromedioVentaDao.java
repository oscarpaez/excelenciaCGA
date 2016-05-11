package co.com.CGAwebComercial.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;


import co.com.CGAwebComercial.entyties.PromedioVenta;
import co.com.CGAwebComercial.util.HibernateUtil;

public class PromedioVentaDao extends GenericDao<PromedioVenta> {
	
	public PromedioVenta listarMeta(int idFuncionario){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		PromedioVenta promedioVenta = new PromedioVenta();
		try {
			Criteria consulta = session.createCriteria(PromedioVenta.class);
			consulta.createAlias("funcionario", "f");
			consulta.add(Restrictions.eq("f.id_funcionario", idFuncionario));
			consulta.add(Restrictions.eq("mes", "Abril"));
			promedioVenta = (PromedioVenta) consulta.uniqueResult();
			return promedioVenta;
		} catch (RuntimeException ex) {
			throw ex;
		}
	}

}
