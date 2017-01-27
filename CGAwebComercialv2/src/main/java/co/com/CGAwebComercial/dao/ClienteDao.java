package co.com.CGAwebComercial.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;

import co.com.CGAwebComercial.entyties.Cliente;
import co.com.CGAwebComercial.util.HibernateUtil;

public class ClienteDao extends GenericDao<Cliente> {
	
	@SuppressWarnings("unchecked")
	public Cliente ultimoRegistro(){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Cliente> listaM = new ArrayList<>();
		Cliente cliente = new Cliente();
		try{
			DetachedCriteria maxId = DetachedCriteria.forClass(Cliente.class)
				    .setProjection(Projections.max("id_cliente") );
			
			Criteria consulta = session.createCriteria(Cliente.class);
			consulta.add(Property.forName("id_cliente").eq(maxId));
			listaM = consulta.list();
			cliente = listaM.get(0);
			return cliente;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
}
