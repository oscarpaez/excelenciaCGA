package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ost_ticket_email_info database table.
 * 
 */

@SuppressWarnings("serial")
@Entity
public class Ost_Ticket_Email_Info implements Serializable {
	

	@Id
	private int id;

	private String email_mid;

	@Lob
	private String headers;

	private int thread_id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail_mid() {
		return email_mid;
	}

	public void setEmail_mid(String email_mid) {
		this.email_mid = email_mid;
	}

	public String getHeaders() {
		return headers;
	}

	public void setHeaders(String headers) {
		this.headers = headers;
	}

	public int getThread_id() {
		return thread_id;
	}

	public void setThread_id(int thread_id) {
		this.thread_id = thread_id;
	}
}