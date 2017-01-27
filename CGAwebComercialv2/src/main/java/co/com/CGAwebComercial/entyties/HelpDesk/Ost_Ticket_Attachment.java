package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ost_ticket_attachment database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Ost_Ticket_Attachment implements Serializable {
	

	@Id
	private int attach_id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private int file_id;

	private byte inline;

	private int ref_id;

	private int ticket_id;

	public int getAttach_id() {
		return attach_id;
	}

	public void setAttach_id(int attach_id) {
		this.attach_id = attach_id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public int getFile_id() {
		return file_id;
	}

	public void setFile_id(int file_id) {
		this.file_id = file_id;
	}

	public byte getInline() {
		return inline;
	}

	public void setInline(byte inline) {
		this.inline = inline;
	}

	public int getRef_id() {
		return ref_id;
	}

	public void setRef_id(int ref_id) {
		this.ref_id = ref_id;
	}

	public int getTicket_id() {
		return ticket_id;
	}

	public void setTicket_id(int ticket_id) {
		this.ticket_id = ticket_id;
	}
}