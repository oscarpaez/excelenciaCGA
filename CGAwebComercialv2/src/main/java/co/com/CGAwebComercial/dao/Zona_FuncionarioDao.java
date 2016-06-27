package co.com.CGAwebComercial.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.Zona_Funcionario;
import co.com.CGAwebComercial.util.HibernateUtil;

public class Zona_FuncionarioDao extends GenericDao<Zona_Funcionario> {

	public Zona_Funcionario buscarFuncionarioZona(int idFuncionario){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		try{
			Criteria consulta = session.createCriteria(Zona_Funcionario.class);
			consulta.createAlias("funcionario", "f");
			consulta.add(Restrictions.eq("f.id_funcionario", idFuncionario));
			Zona_Funcionario resultado = (Zona_Funcionario)consulta.uniqueResult();
			return resultado;
			
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
	
}
