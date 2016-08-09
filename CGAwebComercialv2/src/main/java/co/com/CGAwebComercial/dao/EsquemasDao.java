package co.com.CGAwebComercial.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.Esquemas;
import co.com.CGAwebComercial.util.HibernateUtil;

public class EsquemasDao extends GenericDao<Esquemas>{
	
	public Esquemas buscarEsquema(int idPerfil){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		try {
			Criteria consulta = session.createCriteria(Esquemas.class);		
			consulta.createAlias("perfil", "p" );
			consulta.add(Restrictions.eq("p.id", idPerfil));
			Esquemas esquema = (Esquemas) consulta.uniqueResult();
			
			return esquema;
			
		} catch (RuntimeException e) {
			throw e;
		}
		finally {
			session.close();
		}
	}
}
