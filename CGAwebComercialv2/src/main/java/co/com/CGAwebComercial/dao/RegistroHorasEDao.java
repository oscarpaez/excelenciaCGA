package co.com.CGAwebComercial.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.RegistroHorasE;
import co.com.CGAwebComercial.resource.Recursos;
import co.com.CGAwebComercial.util.HibernateUtil;

public class RegistroHorasEDao extends GenericDao<RegistroHorasE> {
	
	
	@SuppressWarnings("unchecked")
	public List<RegistroHorasE> listarPorSucursal (){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		List<RegistroHorasE> listaRegistroE = new ArrayList<>();
		Recursos recurso = new Recursos();
		try{
			Date fechaInicial = fechaInicial();
			Date fechaFinal = recurso.fechaUnMesDespues();
			Criteria consulta = session.createCriteria(RegistroHorasE.class);
			System.out.println(fechaInicial + " /// " + fechaFinal);
			consulta.add(Restrictions.between("fechaSolicitud", fechaInicial, fechaFinal ));
			listaRegistroE = consulta.list();
			return listaRegistroE;
			
		} catch (RuntimeException ex) {
			throw ex;
		}
	}
}
