package co.com.CGAwebComercial.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import co.com.CGAwebComercial.dao.HelpDesk.Vista_helpdeskDao;
import co.com.CGAwebComercial.entyties.HelpDesk.Vista_helpdesk;


@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class Vista_helpdeskBean implements Serializable{

	@ManagedProperty("#{autenticacionBean}")
	private AutenticacionBean autenticacion;

	private List<Vista_helpdesk> listaVista;
	private List<Vista_helpdesk> listaVistaS;
	private List<Vista_helpdesk> listaVistaFiltro;

	private LineChartModel kpi;
	private LineChartModel kpiS;


	public void listarVistaKpi(){

		try{
			Vista_helpdeskDao dao = new Vista_helpdeskDao();
			listaVista = dao.listarKpi();
			listaVistaS = dao.listarKpiSap();	

			LineChartModel model = new LineChartModel();
			LineChartModel model2 = new LineChartModel();
			LineChartSeries resueltos1 = new LineChartSeries();
			LineChartSeries resueltos = new LineChartSeries();

			int i =0;
			for (Vista_helpdesk v  : listaVista) {

				if(i == 2){										

					resueltos.set("Ene", v.getItemE().intValue());
					resueltos.set("Feb", v.getItemE1().intValue());
					resueltos.set("Mar", v.getItemE2().intValue());
					resueltos.set("Abr", v.getItemE3().intValue());
					resueltos.set("May", v.getItemE4().intValue());
					resueltos.set("Jun", v.getItemE5().intValue());
					resueltos.set("Jul", v.getItemE6().intValue());
					resueltos.set("Ago", v.getItemE7().intValue());
					resueltos.set("Sep", v.getItemE8().intValue());
					resueltos.set("Oct", v.getItemE9().intValue());
					resueltos.set("Nov", v.getItemE10().intValue());
					resueltos.set("Dic", v.getItemE11().intValue());
				}
				i++;
			}

			model.addSeries(resueltos);
			kpi = model;
			kpi.setTitle("KPI Infraestrutura");
			kpi.setAnimate(true);
			kpi.setShowPointLabels(true);
			kpi.setSeriesColors("915608");
			kpi.getAxes().put(AxisType.X, new CategoryAxis("Periodo"));
			Axis yAxis = kpi.getAxis(AxisType.Y);
			yAxis.setMin(0);
			yAxis.setMax(120);
			yAxis.setTickFormat("%.0f%%");
			i=0;
			for (Vista_helpdesk v  : listaVistaS) {

				if(i == 2){										

					resueltos1.set("Ene", v.getItemE().intValue());
					resueltos1.set("Feb", v.getItemE1().intValue());
					resueltos1.set("Mar", v.getItemE2().intValue());
					resueltos1.set("Abr", v.getItemE3().intValue());
					resueltos1.set("May", v.getItemE4().intValue());
					resueltos1.set("Jun", v.getItemE5().intValue());
					resueltos1.set("Jul", v.getItemE6().intValue());
					resueltos1.set("Ago", v.getItemE7().intValue());
					resueltos1.set("Sep", v.getItemE8().intValue());
					resueltos1.set("Oct", v.getItemE9().intValue());
					resueltos1.set("Nov", v.getItemE10().intValue());
					resueltos1.set("Dic", v.getItemE11().intValue());
				}
				i++;
			}

			model2.addSeries(resueltos1);
			kpiS = model2;
			kpiS.setTitle("KPI SAP");
			kpiS.setAnimate(true);
			//kpi.setLegendPosition("e");			
			kpiS.setShowPointLabels(true);
			kpiS.setSeriesColors("58BA27");
			kpiS.getAxes().put(AxisType.X, new CategoryAxis("Periodo"));
			yAxis = kpiS.getAxis(AxisType.Y);
			yAxis.setMin(0);
			yAxis.setMax(120);
			yAxis.setTickFormat("%.0f%%");

						
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			Messages.addGlobalError("Error no se Cargo la lista de la Vista");
		}
	}


	public AutenticacionBean getAutenticacion() {
		return autenticacion;
	}
	public void setAutenticacion(AutenticacionBean autenticacion) {
		this.autenticacion = autenticacion;
	}
	public List<Vista_helpdesk> getListaVista() {
		return listaVista;
	}
	public void setListaVista(List<Vista_helpdesk> listaVista) {
		this.listaVista = listaVista;
	}
	public List<Vista_helpdesk> getListaVistaFiltro() {
		return listaVistaFiltro;
	}
	public void setListaVistaFiltro(List<Vista_helpdesk> listaVistaFiltro) {
		this.listaVistaFiltro = listaVistaFiltro;
	}
	public List<Vista_helpdesk> getListaVistaS() {
		return listaVistaS;
	}
	public void setListaVistaS(List<Vista_helpdesk> listaVistaS) {
		this.listaVistaS = listaVistaS;
	}
	public LineChartModel getKpi() {
		return kpi;
	}
	public void setKpi(LineChartModel kpi) {
		this.kpi = kpi;
	}
	public LineChartModel getKpiS() {
		return kpiS;
	}
	public void setKpiS(LineChartModel kpiS) {
		this.kpiS = kpiS;
	}
}
