package co.com.CGAwebComercial.dao.HelpDesk;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import co.com.CGAwebComercial.entyties.HelpDesk.Vista_helpdesk;
import co.com.CGAwebComercial.util.HibernateUtil;

public class Vista_helpdeskDao {
	
	@SuppressWarnings("unchecked")
	public List<Vista_helpdesk> listar(){
		
		Session session = HibernateUtil.getSessionfactory1().openSession();
		List<Vista_helpdesk> lista = null;
		
		try{
			Criteria consulta = session.createCriteria(Vista_helpdesk.class);			
			lista = consulta.list();
			
		} catch (RuntimeException ex) {			
			throw ex;
		} finally {
			session.close();
		}
		return lista;
	}

}