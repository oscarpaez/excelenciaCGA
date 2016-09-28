package co.com.CGAwebComercial.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.entyties.Funcionario;
import co.com.CGAwebComercial.entyties.Persona;
import co.com.CGAwebComercial.entyties.Registro_Ingresos;
import co.com.CGAwebComercial.util.HibernateUtil;

public class Registro_IngresosDao extends GenericDao<Registro_Ingresos> {

	
	@SuppressWarnings({ "unchecked"})
	public List<Registro_Ingresos> listaIngresos(String fecMes, String fecYear){
		
		Session session = HibernateUtil.getSessionfactory().openSession();
		List<Registro_Ingresos> listaR = new ArrayList<>();
		try{
			Date fechaFinal = (fecMes.equals("") || fecMes == null)? fechaFinal():fechaFinal(fecMes, fecYear);
			Date fechaInicial =(fecYear.equals("") || fecYear == null) ? fechaInicial() : fechaInicial(fecMes, fecYear);
			System.out.println(fechaInicial +"--" + fechaFinal);
			Criteria consulta = session.createCriteria(Registro_Ingresos.class);
			consulta.createAlias("persona", "p");
			consulta.createAlias("funcionario", "f");
			consulta.add(Restrictions.between("fechaIngreso", fechaInicial, fechaFinal));
			consulta.setProjection(Projections.projectionList().add(
					Projections.groupProperty("persona")).add(Projections.groupProperty("funcionario")).add(
							Projections.rowCount(), "p.cedula"));				
			List<Object[]> results = consulta.list();
			
			System.out.println(results.size());
			for (Object[] objects : results) {
				Registro_Ingresos registro = new Registro_Ingresos();
				System.out.println(objects[0] + "EEEEE" + objects[1] + " 22 " +objects[2] );
				Persona persona = (Persona) objects[0];
				long id = (long) objects[2];
				Funcionario funcionario = (Funcionario) objects[1];
				System.out.println(funcionario.getId_funcionario());
				registro.setPersona(persona);
				registro.setId_Registro((int) id);
				registro.setFuncionario(funcionario);
				listaR.add(registro);
			}
			return  listaR;
			
		} catch (RuntimeException ex) {
			throw ex;
		}
		finally{
			session.close();
		}
	}
}
