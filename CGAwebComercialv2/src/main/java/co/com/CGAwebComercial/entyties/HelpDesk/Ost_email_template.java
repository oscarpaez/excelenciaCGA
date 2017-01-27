package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ost_email_template database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Ost_email_template implements Serializable {
	

	@Id
	private int id;

	@Lob
	private String body;

	private String code_name;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@Lob
	private String notes;

	private String subject;

	private int tpl_id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getCode_name() {
		return code_name;
	}

	public void setCode_name(String code_name) {
		this.code_name = code_name;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getTpl_id() {
		return tpl_id;
	}

	public void setTpl_id(int tpl_id) {
		this.tpl_id = tpl_id;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
}