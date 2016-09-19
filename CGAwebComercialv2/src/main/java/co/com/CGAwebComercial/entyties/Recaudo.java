package co.com.CGAwebComercial.entyties;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class Recaudo implements Serializable{
	
	
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private int idRecaudo;
		
		@Column(scale = 2)
		private BigDecimal presupuesto;
		
		@Column(scale = 2)
		private BigDecimal real;
		
		@Temporal(TemporalType.DATE)
		private Date fecha;
		
		@Transient
		private BigDecimal umbral;
		
		@Transient
		private BigDecimal cumplimiento;
		
		@Transient
		private String imagen;
		
		@Transient
		private String mes;
		
		@Transient
		private BigDecimal valorComision;	
		
		@OneToOne
		@JoinColumn(nullable = false)
		private Zona_venta zonaVenta;
		
		@ManyToOne
		@JoinColumn(nullable = false)		
		private Funcionario funcionario;
		
		
		public int getIdRecaudo() {
			return idRecaudo;
		}

		public void setIdRecaudo(int idRecaudo) {
			this.idRecaudo = idRecaudo;
		}

		public BigDecimal getPresupuesto() {
			return presupuesto;
		}

		public void setPresupuesto(BigDecimal presupuesto) {
			this.presupuesto = presupuesto;
		}

		public BigDecimal getReal() {
			return real;
		}

		public void setReal(BigDecimal real) {
			this.real = real;
		}

		public Date getFecha() {
			return fecha;
		}

		public void setFecha(Date fecha) {
			this.fecha = fecha;
		}

		public Zona_venta getZonaVenta() {
			return zonaVenta;
		}

		public void setZonaVenta(Zona_venta zonaVenta) {
			this.zonaVenta = zonaVenta;
		}

		

		
		public BigDecimal getCumplimiento() {
			return cumplimiento;
		}

		public void setCumplimiento(BigDecimal cumplimiento) {
			this.cumplimiento = cumplimiento;
		}

		public String getImagen() {
			return imagen;
		}

		public void setImagen(String imagen) {
			this.imagen = imagen;
		}

		public BigDecimal getValorComision() {
			return valorComision;
		}

		public void setValorComision(BigDecimal valorComision) {
			this.valorComision = valorComision;
		}

		public BigDecimal getUmbral() {
			return umbral;
		}

		public void setUmbral(BigDecimal umbral) {
			this.umbral = umbral;
		}

		public String getMes() {
			return mes;
		}

		public void setMes(String mes) {
			this.mes = mes;
		}
		
}

