package co.com.CGAwebComercial.entyties;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@SuppressWarnings("serial")
@Entity
public class Esquemas implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(scale = 2)
	private BigDecimal umbralRecaudo;

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getUmbralRecaudo() {
		return umbralRecaudo;
	}

	public void setUmbralRecaudo(BigDecimal umbralRecaudo) {
		this.umbralRecaudo = umbralRecaudo;
	}
}
