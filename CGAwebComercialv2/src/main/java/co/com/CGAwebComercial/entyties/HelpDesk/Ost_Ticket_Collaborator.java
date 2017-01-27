package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ost_ticket_collaborator database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Ost_Ticket_Collaborator implements Serializable {
	

	@Id
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private byte isactive;

	private String role;

	private int ticket_id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;

	private int user_id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public byte getIsactive() {
		return isactive;
	}

	public void setIsactive(byte isactive) {
		this.isactive = isactive;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getTicket_id() {
		return ticket_id;
	}

	public void setTicket_id(int ticket_id) {
		this.ticket_id = ticket_id;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
}