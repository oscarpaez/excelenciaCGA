package co.com.CGAwebComercial.dao.HelpDesk;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import co.com.CGAwebComercial.dao.GenericDao;
import co.com.CGAwebComercial.entyties.HelpDesk.Vista_helpdesk;
import co.com.CGAwebComercial.resource.Recursos;
import co.com.CGAwebComercial.util.Fechas;
import co.com.CGAwebComercial.util.HibernateUtil;

public class Vista_helpdeskDao  extends GenericDao<Vista_helpdesk> {

	@SuppressWarnings("unchecked")
	public List<Vista_helpdesk> listar(){

		Session session = HibernateUtil.getSessionfactory1().openSession();
		List<Vista_helpdesk> lista = new ArrayList<>();

		try{
			Criteria consulta = session.createCriteria(Vista_helpdesk.class);
			lista = consulta.list();

			System.out.println(lista.size()+ "####");

			for (Vista_helpdesk v : lista) {				
				System.out.println(v.getCreated() + " -VH- " + v.getFirstname());
			}
			return lista;

		} catch (RuntimeException ex) {			
			throw ex;
		} finally {
			session.close();
		}

	}

	@SuppressWarnings({ "deprecation", "rawtypes" })
	public List<Vista_helpdesk> listarKpi(){

		Session session = HibernateUtil.getSessionfactory1().openSession();
		List<Vista_helpdesk> lista = new ArrayList<>();

		try{
			String fecMes = "";
			String fecYear = "";
			String fechaConsulta = "";
			Date fechaFinal = null; 
			Date fechaInicial = null;	

			Calendar fechas = Calendar.getInstance();

			int month = fechas.get(Calendar.MONTH);
			int year = fechas.get(Calendar.YEAR);
			int y =0;
			lista = new ArrayList<>();

			Vista_helpdesk v = new Vista_helpdesk();			
			lista.add(0, v);
			v = new Vista_helpdesk();
			lista.add(1, v);
			v = new Vista_helpdesk();
			lista.add(2, v);

			for(int i=0; i<12; i++){				

				if(month < 0){
					month += 13;
					if(year > y){
						year -= 1;
						y = year;
					}	
				}
				else{
					month += 1;
				}
				fecMes = String.valueOf(month);
				fecYear = String.valueOf(year);				

				fechaFinal = fechaFinal(fecMes  , fecYear);
				fechaInicial = fechaInicial(fecMes, fecYear);				

				//				Calendar c = Calendar.getInstance();
				fechas.set(Calendar.HOUR_OF_DAY, 23);
				fechas.set(Calendar.MINUTE, 59);
				fechas.set(Calendar.SECOND, 59);
				fechaFinal.setHours(23); 
				fechaFinal.setMinutes(59);
				fechaFinal.setSeconds(59);

				Timestamp t = new Timestamp(fechaInicial.getTime());
				Timestamp tf = new Timestamp(fechaFinal.getTime());

				System.out.println(t + "-TT-" + tf);
				Criteria consulta = session.createCriteria(Vista_helpdesk.class);
				consulta.add(Restrictions.between("created", t, tf));				
				consulta.setProjection(Projections.projectionList()
						.add(Projections.count("created")));
				List results = consulta.list();
				//int j = 0;

				//Object[] obc = (Object[]) results.get(0);
				//Long a = (Long) obc[0];
				Long a = (Long) results.get(0);
				
				consulta.add(Restrictions.between("closed", t, tf));
				consulta.add(Restrictions.between("created", t, tf));
				consulta.setProjection(Projections.projectionList()
						.add(Projections.count("closed")));
				results = consulta.list();
				
//				obc = (Object[]) results.get(0);
//				Long b = (Long) obc[0];
				Long b = (Long) results.get(0);
				System.out.println(a + "-"+month+"-" + b);	

				Recursos recurso = new Recursos();
				List<Fechas>listaFechas = recurso.cargarFechas();
				for (Fechas fecha: listaFechas) {
					fechaConsulta  = (month<10)?(fecha.getValorMes().equals(String.valueOf("0"+month)))? fecha.getMes():fechaConsulta:(fecha.getValorMes().equals(String.valueOf(""+month)))? fecha.getMes(): fechaConsulta;
				}

				System.out.println("Mes" + fechaConsulta);	

				switch(month){

				case 1:
					BigDecimal p = (a == 0 || b == 0)? new BigDecimal("0") :new BigDecimal(b).divide(new BigDecimal(a), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
					System.out.println(fechaConsulta +" *%* " +p);
					lista.get(0).setItemE(new BigDecimal(a));
					lista.get(2).setTitulo(fechaConsulta);
					lista.get(1).setItemE(new BigDecimal(b));
					lista.get(2).setItemE(p);
					lista.get(0).setEtiqueta("Solicitud");
					lista.get(1).setEtiqueta("Resueltos");
					lista.get(2).setEtiqueta("Efectividad");
					lista.get(0).setReder(1);
					break;

				case 2:
					BigDecimal p1 = (a == 0 || b == 0)? new BigDecimal("0") :new BigDecimal(b).divide(new BigDecimal(a), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
					System.out.println(fechaConsulta + " *%1* " +p1);
					lista.get(2).setTitulo1(fechaConsulta);
					lista.get(0).setItemE1(new BigDecimal(a));
					lista.get(1).setItemE1(new BigDecimal(b));
					lista.get(2).setItemE1(p1);
					break;

				case 3:
					BigDecimal p2 = (a == 0 || b == 0)? new BigDecimal("0") :new BigDecimal(b).divide(new BigDecimal(a), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
					System.out.println(fechaConsulta + " *%2* " +p2);
					lista.get(2).setTitulo2(fechaConsulta);
					lista.get(0).setItemE2(new BigDecimal(a));
					lista.get(1).setItemE2(new BigDecimal(b));
					lista.get(2).setItemE2(p2);
					break;

				case 4:
					BigDecimal p3 = (a == 0 || b == 0)? new BigDecimal("0") :new BigDecimal(b).divide(new BigDecimal(a), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
					System.out.println(fechaConsulta + " *%2* " +p3);
					lista.get(2).setTitulo3(fechaConsulta);
					lista.get(0).setItemE3(new BigDecimal(a));
					lista.get(1).setItemE3(new BigDecimal(b));
					lista.get(2).setItemE3(p3);
					break;

				case 5:
					BigDecimal p4 = (a == 0 || b == 0)? new BigDecimal("0") :new BigDecimal(b).divide(new BigDecimal(a), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
					System.out.println(fechaConsulta + " *%2* " +p4);
					lista.get(2).setTitulo4(fechaConsulta);
					lista.get(0).setItemE4(new BigDecimal(a));
					lista.get(1).setItemE4(new BigDecimal(b));
					lista.get(2).setItemE4(p4);
					break;

				case 6:
					BigDecimal p5 = (a == 0 || b == 0)? new BigDecimal("0") :new BigDecimal(b).divide(new BigDecimal(a), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
					System.out.println(fechaConsulta + " *%2* " +p5);
					lista.get(2).setTitulo5(fechaConsulta);
					lista.get(0).setItemE5(new BigDecimal(a));
					lista.get(1).setItemE5(new BigDecimal(b));
					lista.get(2).setItemE5(p5);
					break;

				case 7:
					BigDecimal p6 = (a == 0 || b == 0)? new BigDecimal("0") :new BigDecimal(b).divide(new BigDecimal(a), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
					System.out.println(fechaConsulta + " *%2* " +p6);
					lista.get(2).setTitulo6(fechaConsulta);
					lista.get(0).setItemE6(new BigDecimal(a));
					lista.get(1).setItemE6(new BigDecimal(b));
					lista.get(2).setItemE6(p6);
					break;

				case 8:
					BigDecimal p7 = (a == 0 || b == 0)? new BigDecimal("0") :new BigDecimal(b).divide(new BigDecimal(a), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
					System.out.println(fechaConsulta + " *%2* " +p7);
					lista.get(2).setTitulo7(fechaConsulta);
					lista.get(0).setItemE7(new BigDecimal(a));
					lista.get(1).setItemE7(new BigDecimal(b));
					lista.get(2).setItemE7(p7);
					break;

				case 9:
					BigDecimal p8 = (a == 0 || b == 0)? new BigDecimal("0") :new BigDecimal(b).divide(new BigDecimal(a), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
					System.out.println(fechaConsulta + " *%2* " +p8);
					lista.get(2).setTitulo8(fechaConsulta);
					lista.get(0).setItemE8(new BigDecimal(a));
					lista.get(1).setItemE8(new BigDecimal(b));
					lista.get(2).setItemE8(p8);
					break;

				case 10:
					BigDecimal p9 = (a == 0 || b == 0)? new BigDecimal("0") :new BigDecimal(b).divide(new BigDecimal(a), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
					System.out.println(fechaConsulta + " *%2* " +p9);
					lista.get(2).setTitulo9(fechaConsulta);
					lista.get(0).setItemE9(new BigDecimal(a));
					lista.get(1).setItemE9(new BigDecimal(b));
					lista.get(2).setItemE9(p9);
					break;

				case 11:
					BigDecimal p10 = (a == 0 || b == 0)? new BigDecimal("0") :new BigDecimal(b).divide(new BigDecimal(a), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
					System.out.println(fechaConsulta + " *%2* " +p10);
					lista.get(2).setTitulo10(fechaConsulta);
					lista.get(0).setItemE10(new BigDecimal(a));
					lista.get(1).setItemE10(new BigDecimal(b));
					lista.get(2).setItemE10(p10);
					break;

				case 12:
					BigDecimal p11 = (a == 0 || b == 0)? new BigDecimal("0") :new BigDecimal(b).divide(new BigDecimal(a), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
					System.out.println(fechaConsulta + " *%2* " +p11);
					lista.get(2).setTitulo11(fechaConsulta);
					lista.get(0).setItemE11(new BigDecimal(a));
					lista.get(1).setItemE11(new BigDecimal(b));
					lista.get(2).setItemE11(p11);
					break;

				default:
					lista.get(2).setTitulo1(fechaConsulta);
					lista.get(0).setItemE3(new BigDecimal("0"));
					lista.get(1).setItemE3(new BigDecimal("0"));
					lista.get(2).setItemE3(new BigDecimal("0"));
					break;

				}			

								
				//				System.out.println(results1.size() + "Tamaño" );
				//				
				//				
				//				for (Object object : results1) {
				//					Object[] obc = (Object[]) object;
				//					Long a = (Long) obc[0];
				//					Long b = (Long) obc[1];
				//					System.out.println(a + "--" + b);
				//					if(a != 0 || b != 0){
				//						BigDecimal p = new BigDecimal(b).divide(new BigDecimal(a), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
				//						System.out.println(" *%Topic* " +p);
				//					}else{
				//						System.out.println(" *% TNO* " );
				//					}
				//					
				//				}

				//				consulta.setProjection(Projections.count("created"));
				//				Long a = (Long) consulta.uniqueResult();
				//				
				//				consulta.setProjection(Projections.count("closed"));
				//				Long b = (Long) consulta.uniqueResult();

				//				System.out.println(a + "--" + b);
				//				consulta.setProjection(Projections.projectionList()
				//						.add(Projections.count("topic_pid"))
				//						.add(Projections.count("closed")));
				//				List<Object> results = consulta.list();
				month -= 2;	
			}



			//consulta.add(Restrictions.between("fechaCreacion", fechaInicial, fechaFinal));
			//consulta.add(Restrictions.eq("topic_pid",  0 ));


		} catch (RuntimeException ex) {			
			throw ex;
		} finally {
			session.close();
		}
		return lista;
	}

	@SuppressWarnings({ "deprecation", "rawtypes" })
	public List<Vista_helpdesk> listarKpiSap(){

		Session session = HibernateUtil.getSessionfactory1().openSession();
		List<Vista_helpdesk> lista = new ArrayList<>();

		try{
			String fecMes = "";
			String fecYear = "";
			String fechaConsulta = "";
			Date fechaFinal = null; 
			Date fechaInicial = null;	

			Calendar fechas = Calendar.getInstance();

			int month = fechas.get(Calendar.MONTH);
			int year = fechas.get(Calendar.YEAR);
			int y =0;
			lista = new ArrayList<>();

			Vista_helpdesk v = new Vista_helpdesk();			
			lista.add(0, v);
			v = new Vista_helpdesk();
			lista.add(1, v);
			v = new Vista_helpdesk();
			lista.add(2, v);

			for(int i=0; i<12; i++){				

				if(month < 0){
					month += 13;
					if(year > y){
						year -= 1;
						y = year;
					}	
				}
				else{
					month += 1;
				}
				fecMes = String.valueOf(month);
				fecYear = String.valueOf(year);				

				fechaFinal = fechaFinal(fecMes  , fecYear);
				fechaInicial = fechaInicial(fecMes, fecYear);				

				//				Calendar c = Calendar.getInstance();
				fechas.set(Calendar.HOUR_OF_DAY, 23);
				fechas.set(Calendar.MINUTE, 59);
				fechas.set(Calendar.SECOND, 59);
				fechaFinal.setHours(23); 
				fechaFinal.setMinutes(59);
				fechaFinal.setSeconds(59);

				Timestamp t = new Timestamp(fechaInicial.getTime());
				Timestamp tf = new Timestamp(fechaFinal.getTime());

				System.out.println(t + "-TT-" + tf);
				Criteria consulta = session.createCriteria(Vista_helpdesk.class);
				consulta.add(Restrictions.between("created", t, tf));
				consulta.add(Restrictions.like("topic", "2", MatchMode.START));
				consulta.setProjection(Projections.projectionList()
						.add(Projections.count("created")));
				List results = consulta.list();
				//int j = 0;

//				Object[] obc = (Object[]) results.get(0);
//				Long a = (Long) obc[0];
				Long a = (Long) results.get(0);
				
				consulta.add(Restrictions.between("closed", t, tf));
				consulta.add(Restrictions.between("created", t, tf));
				consulta.setProjection(Projections.projectionList()
						.add(Projections.count("closed")));
				results = consulta.list();
				
//				obc = (Object[]) results.get(0);
//				Long b = (Long) obc[0];
				Long b = (Long) results.get(0);
				System.out.println(a + "-"+month+"-" + b);	

				Recursos recurso = new Recursos();
				List<Fechas>listaFechas = recurso.cargarFechas();
				for (Fechas fecha: listaFechas) {
					fechaConsulta  = (month<10)?(fecha.getValorMes().equals(String.valueOf("0"+month)))? fecha.getMes():fechaConsulta:(fecha.getValorMes().equals(String.valueOf(""+month)))? fecha.getMes(): fechaConsulta;
				}

				System.out.println("Mes" + fechaConsulta);	

				switch(month){

				case 1:
					BigDecimal p = (a == 0 || b == 0)? new BigDecimal("0") :new BigDecimal(b).divide(new BigDecimal(a), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
					System.out.println(fechaConsulta +" *%* " +p);
					lista.get(0).setItemE(new BigDecimal(a));
					lista.get(2).setTitulo(fechaConsulta);
					lista.get(1).setItemE(new BigDecimal(b));
					lista.get(2).setItemE(p);
					lista.get(0).setEtiqueta("Solicitud");
					lista.get(1).setEtiqueta("Resueltos");
					lista.get(2).setEtiqueta("Efectividad");
					lista.get(0).setReder(1);
					break;

				case 2:
					BigDecimal p1 = (a == 0 || b == 0)? new BigDecimal("0") :new BigDecimal(b).divide(new BigDecimal(a), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
					System.out.println(fechaConsulta + " *%1* " +p1);
					lista.get(2).setTitulo1(fechaConsulta);
					lista.get(0).setItemE1(new BigDecimal(a));
					lista.get(1).setItemE1(new BigDecimal(b));
					lista.get(2).setItemE1(p1);
					break;

				case 3:
					BigDecimal p2 = (a == 0 || b == 0)? new BigDecimal("0") :new BigDecimal(b).divide(new BigDecimal(a), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
					System.out.println(fechaConsulta + " *%2* " +p2);
					lista.get(2).setTitulo2(fechaConsulta);
					lista.get(0).setItemE2(new BigDecimal(a));
					lista.get(1).setItemE2(new BigDecimal(b));
					lista.get(2).setItemE2(p2);
					break;

				case 4:
					BigDecimal p3 = (a == 0 || b == 0)? new BigDecimal("0") :new BigDecimal(b).divide(new BigDecimal(a), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
					System.out.println(fechaConsulta + " *%2* " +p3);
					lista.get(2).setTitulo3(fechaConsulta);
					lista.get(0).setItemE3(new BigDecimal(a));
					lista.get(1).setItemE3(new BigDecimal(b));
					lista.get(2).setItemE3(p3);
					break;

				case 5:
					BigDecimal p4 = (a == 0 || b == 0)? new BigDecimal("0") :new BigDecimal(b).divide(new BigDecimal(a), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
					System.out.println(fechaConsulta + " *%2* " +p4);
					lista.get(2).setTitulo4(fechaConsulta);
					lista.get(0).setItemE4(new BigDecimal(a));
					lista.get(1).setItemE4(new BigDecimal(b));
					lista.get(2).setItemE4(p4);
					break;

				case 6:
					BigDecimal p5 = (a == 0 || b == 0)? new BigDecimal("0") :new BigDecimal(b).divide(new BigDecimal(a), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
					System.out.println(fechaConsulta + " *%2* " +p5);
					lista.get(2).setTitulo5(fechaConsulta);
					lista.get(0).setItemE5(new BigDecimal(a));
					lista.get(1).setItemE5(new BigDecimal(b));
					lista.get(2).setItemE5(p5);
					break;

				case 7:
					BigDecimal p6 = (a == 0 || b == 0)? new BigDecimal("0") :new BigDecimal(b).divide(new BigDecimal(a), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
					System.out.println(fechaConsulta + " *%2* " +p6);
					lista.get(2).setTitulo6(fechaConsulta);
					lista.get(0).setItemE6(new BigDecimal(a));
					lista.get(1).setItemE6(new BigDecimal(b));
					lista.get(2).setItemE6(p6);
					break;

				case 8:
					BigDecimal p7 = (a == 0 || b == 0)? new BigDecimal("0") :new BigDecimal(b).divide(new BigDecimal(a), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
					System.out.println(fechaConsulta + " *%2* " +p7);
					lista.get(2).setTitulo7(fechaConsulta);
					lista.get(0).setItemE7(new BigDecimal(a));
					lista.get(1).setItemE7(new BigDecimal(b));
					lista.get(2).setItemE7(p7);
					break;

				case 9:
					BigDecimal p8 = (a == 0 || b == 0)? new BigDecimal("0") :new BigDecimal(b).divide(new BigDecimal(a), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
					System.out.println(fechaConsulta + " *%2* " +p8);
					lista.get(2).setTitulo8(fechaConsulta);
					lista.get(0).setItemE8(new BigDecimal(a));
					lista.get(1).setItemE8(new BigDecimal(b));
					lista.get(2).setItemE8(p8);
					break;

				case 10:
					BigDecimal p9 = (a == 0 || b == 0)? new BigDecimal("0") :new BigDecimal(b).divide(new BigDecimal(a), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
					System.out.println(fechaConsulta + " *%2* " +p9);
					lista.get(2).setTitulo9(fechaConsulta);
					lista.get(0).setItemE9(new BigDecimal(a));
					lista.get(1).setItemE9(new BigDecimal(b));
					lista.get(2).setItemE9(p9);
					break;

				case 11:
					BigDecimal p10 = (a == 0 || b == 0)? new BigDecimal("0") :new BigDecimal(b).divide(new BigDecimal(a), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
					System.out.println(fechaConsulta + " *%2* " +p10);
					lista.get(2).setTitulo10(fechaConsulta);
					lista.get(0).setItemE10(new BigDecimal(a));
					lista.get(1).setItemE10(new BigDecimal(b));
					lista.get(2).setItemE10(p10);
					break;

				case 12:
					BigDecimal p11 = (a == 0 || b == 0)? new BigDecimal("0") :new BigDecimal(b).divide(new BigDecimal(a), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
					System.out.println(fechaConsulta + " *%2* " +p11);
					lista.get(2).setTitulo11(fechaConsulta);
					lista.get(0).setItemE11(new BigDecimal(a));
					lista.get(1).setItemE11(new BigDecimal(b));
					lista.get(2).setItemE11(p11);
					break;

				default:
					lista.get(2).setTitulo1(fechaConsulta);
					lista.get(0).setItemE3(new BigDecimal("0"));
					lista.get(1).setItemE3(new BigDecimal("0"));
					lista.get(2).setItemE3(new BigDecimal("0"));
					break;
				}
				month -= 2;
			}
			
		} catch (RuntimeException ex) {			
			throw ex;
		} finally {
			session.close();
		}
		return lista;
	}				


}
