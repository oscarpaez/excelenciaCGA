package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ost_api_key database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Ost_api_key implements Serializable {
	
	@Id
	private int id;

	private String apikey;

	private byte can_create_tickets;

	private byte can_exec_cron;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private String ipaddr;

	private byte isactive;

	@Lob
	private String notes;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public byte getCan_create_tickets() {
		return can_create_tickets;
	}

	public void setCan_create_tickets(byte can_create_tickets) {
		this.can_create_tickets = can_create_tickets;
	}

	public byte getCan_exec_cron() {
		return can_exec_cron;
	}

	public void setCan_exec_cron(byte can_exec_cron) {
		this.can_exec_cron = can_exec_cron;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getIpaddr() {
		return ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	public byte getIsactive() {
		return isactive;
	}

	public void setIsactive(byte isactive) {
		this.isactive = isactive;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
	
}