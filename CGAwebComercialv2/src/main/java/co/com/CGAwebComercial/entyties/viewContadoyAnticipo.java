package co.com.CGAwebComercial.entyties;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.NamedNativeQuery;
//import org.hibernate.annotations.Subselect;

@SuppressWarnings("serial")
@Entity
@NamedNativeQuery(name = "order", query = "select NoPersonal, sum(totalrecCA)*0.25 as Total from contadoanticipo group by NoPersonal order by NoPersonal", resultClass = viewContadoyAnticipo.class)
public class viewContadoyAnticipo  implements Serializable{

	private int noPersonal;
	
	@Column(length = 35 , scale=2)
	private BigDecimal total;

	public int getNoPersonal() {
		return noPersonal;
	}

	public void setNoPersonal(int noPersonal) {
		this.noPersonal = noPersonal;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	
}
