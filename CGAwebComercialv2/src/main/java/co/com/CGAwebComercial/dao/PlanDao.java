package co.com.CGAwebComercial.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.Funcionario;
import co.com.CGAwebComercial.entyties.Plan;
import co.com.CGAwebComercial.entyties.Usuario;
import co.com.CGAwebComercial.util.HibernateUtil;

public class PlanDao extends GenericDao<Plan>{

	@SuppressWarnings("unchecked")
	public List<Plan> listarItems(Usuario usuario){

		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Plan> plan = null;
		try{
			//Date fechaFinal = fechaFinal();
			//Date fechaInicial = fechaInicial();
			//System.out.println("El último día del mes es: " + fechaInicial + "--" +fechaFinal);
			FuncionarioDao daof = new FuncionarioDao();
			Funcionario funcionario = daof.buscarPersona(usuario.getPersona().getCedula());
			System.out.println(funcionario.getId_funcionario()+ "fun");
			int idF= funcionario.getId_funcionario();
			Criteria consulta = session.createCriteria(Plan.class);
			consulta.createAlias("funcionario", "f");
			consulta.add(Restrictions.eq("f.id_funcionario", idF ));
			//consulta.add(Restrictions.between("fechaIngreso", fechaInicial, fechaFinal));
			
			plan = consulta.list();
			return plan;

		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}



}
