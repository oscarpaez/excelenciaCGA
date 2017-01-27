package co.com.CGAwebComercial.dao.Encuesta;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;


import co.com.CGAwebComercial.entyties.encuesta.Persona;
import co.com.CGAwebComercial.util.HibernateUtil;

public class PersonaDao extends GenericDao<Persona>{

	@SuppressWarnings("unchecked")
	public Persona ultimoRegistro(){

		Session session = HibernateUtil.getSessionfactory2().openSession();
		List<Persona> listaP = new ArrayList<>();
		Persona persona = new Persona();
		try{

			DetachedCriteria maxId = DetachedCriteria.forClass(Persona.class)
					.setProjection(Projections.max("id") );

			Criteria consulta = session.createCriteria(Persona.class);
			consulta.add(Property.forName("id").eq(maxId));
			listaP = consulta.list();
			System.out.println(listaP.get(0).getId());
			persona = listaP.get(0);
			return persona;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

}
