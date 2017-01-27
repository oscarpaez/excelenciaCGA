package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ost_ticket_priority database table.
 * 
 */

@SuppressWarnings("serial")
@Entity
public class Ost_Ticket_Priority implements Serializable {
	

	@Id
	private byte priority_id;

	private byte ispublic;

	private String priority;

	private String priority_color;

	private String priority_desc;

	private byte priority_urgency;

	public byte getPriority_id() {
		return priority_id;
	}

	public void setPriority_id(byte priority_id) {
		this.priority_id = priority_id;
	}

	public byte getIspublic() {
		return ispublic;
	}

	public void setIspublic(byte ispublic) {
		this.ispublic = ispublic;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getPriority_color() {
		return priority_color;
	}

	public void setPriority_color(String priority_color) {
		this.priority_color = priority_color;
	}

	public String getPriority_desc() {
		return priority_desc;
	}

	public void setPriority_desc(String priority_desc) {
		this.priority_desc = priority_desc;
	}

	public byte getPriority_urgency() {
		return priority_urgency;
	}

	public void setPriority_urgency(byte priority_urgency) {
		this.priority_urgency = priority_urgency;
	}

	
}