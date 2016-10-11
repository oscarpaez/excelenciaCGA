package co.com.CGAwebComercial.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.Linea;
import co.com.CGAwebComercial.entyties.Material;
import co.com.CGAwebComercial.util.HibernateUtil;

public class MaterialDao extends GenericDao<Material>{
	
	@SuppressWarnings("unchecked")
	public Material ultimoRegistro(){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Material> listaM = new ArrayList<>();
		Material material = new Material();
		try{
			
			DetachedCriteria maxId = DetachedCriteria.forClass(Material.class)
				    .setProjection(Projections.max("id") );
			
			Criteria consulta = session.createCriteria(Material.class);
			consulta.add(Property.forName("id").eq(maxId));
			listaM = consulta.list();
			System.out.println(listaM.get(0).getId());
			material = listaM.get(0);
			return material;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Linea> buscarPorLinea(int  estadoCodigo){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		try {
			Criteria consulta = session.createCriteria(Linea.class);
			consulta.add(Restrictions.eq("id", estadoCodigo));
			List<Linea> resultado = consulta.list();			
			return resultado;
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}

}
