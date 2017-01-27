package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ost_faq_category database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Ost_faq_category implements Serializable {
	

	@Id
	private int category_id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@Lob
	private String description;

	private byte ispublic;

	private String name;

	@Lob
	private String notes;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte getIspublic() {
		return ispublic;
	}

	public void setIspublic(byte ispublic) {
		this.ispublic = ispublic;
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

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
}