package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ost_canned_response database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Ost_canned_response implements Serializable {
	

	@Id
	private int canned_id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private int dept_id;

	private byte isenabled;

	private String lang;

	@Lob
	private String notes;

	@Lob
	private String response;

	private String title;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;

	public int getCanned_id() {
		return canned_id;
	}

	public void setCanned_id(int canned_id) {
		this.canned_id = canned_id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public int getDept_id() {
		return dept_id;
	}

	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}

	public byte getIsenabled() {
		return isenabled;
	}

	public void setIsenabled(byte isenabled) {
		this.isenabled = isenabled;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
}