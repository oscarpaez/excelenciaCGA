package co.com.CGAwebComercial.entyties;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@SuppressWarnings("serial")
public class InformeFacturadoLinea implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column (scale = 2)
	private BigDecimal pptoKG;
	
	@Column (scale = 2)
	private BigDecimal realKG;
	
	@Column (scale = 2)
	private BigDecimal ejecKG;
	
	@Column (scale = 2)
	private BigDecimal pptoVentas;
	
	@Column (scale = 2)
	private BigDecimal realVetas;
	
	@Column (scale = 2)
	private BigDecimal ejecVentas;
	
	@Column (scale = 2)
	private BigDecimal pendPlanta;
	
	@Column (scale = 2)
	private BigDecimal acumulado;
	
	

}
