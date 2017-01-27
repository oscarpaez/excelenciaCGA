package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the ost_email_template_group database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Ost_email_template_group implements Serializable {
	

	@Id
	private int tpl_id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private byte isactive;

	private String lang;

	private String name;

	@Lob
	private String notes;

	private Timestamp updated;

	public int getTpl_id() {
		return tpl_id;
	}

	public void setTpl_id(int tpl_id) {
		this.tpl_id = tpl_id;
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

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Timestamp getUpdated() {
		return updated;
	}

	public void setUpdated(Timestamp updated) {
		this.updated = updated;
	}
}