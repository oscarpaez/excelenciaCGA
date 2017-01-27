package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ost_ticket__cdata database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Ost_Ticket__Cdata implements Serializable {
	

	@Id
	private int ticket_id;

	@Lob
	private String area;

	@Lob
	private String csopor;

	@Lob
	private String priority;

	@Lob
	private String subject;

	@Lob
	private String ztipo;

	@Lob
	private String zusd1;

	public int getTicket_id() {
		return ticket_id;
	}

	public void setTicket_id(int ticket_id) {
		this.ticket_id = ticket_id;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCsopor() {
		return csopor;
	}

	public void setCsopor(String csopor) {
		this.csopor = csopor;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getZtipo() {
		return ztipo;
	}

	public void setZtipo(String ztipo) {
		this.ztipo = ztipo;
	}

	public String getZusd1() {
		return zusd1;
	}

	public void setZusd1(String zusd1) {
		this.zusd1 = zusd1;
	}
}