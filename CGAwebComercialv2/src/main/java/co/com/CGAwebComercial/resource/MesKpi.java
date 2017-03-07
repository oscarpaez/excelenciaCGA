package co.com.CGAwebComercial.resource;

import java.math.BigDecimal;

public class MesKpi {
	
	private String nombre;
	private int itemS;
	private int itemR;
	private BigDecimal itemE;
	
	public int getItemS() {
		return itemS;
	}
	public void setItemS(int itemS) {
		this.itemS = itemS;
	}
	public int getItemR() {
		return itemR;
	}
	public void setItemR(int itemR) {
		this.itemR = itemR;
	}
	public BigDecimal getItemE() {
		return itemE;
	}
	public void setItemE(BigDecimal itemE) {
		this.itemE = itemE;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
