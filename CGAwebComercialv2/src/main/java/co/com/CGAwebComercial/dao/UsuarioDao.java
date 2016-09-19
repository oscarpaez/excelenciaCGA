package co.com.CGAwebComercial.dao;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.Usuario;
import co.com.CGAwebComercial.util.HibernateUtil;

public class UsuarioDao extends GenericDao<Usuario> {
	
	public Usuario autenticar(int persona, String clave){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		try {
			Criteria consulta = session.createCriteria(Usuario.class);
			consulta.createAlias("persona", "p");			
			consulta.add(Restrictions.eq("p.cedula", persona));
			SimpleHash hash = new SimpleHash("md5", clave);
			consulta.add(Restrictions.eq("clave", hash.toHex()));
			
			Usuario resultado = (Usuario) consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	
	
}
