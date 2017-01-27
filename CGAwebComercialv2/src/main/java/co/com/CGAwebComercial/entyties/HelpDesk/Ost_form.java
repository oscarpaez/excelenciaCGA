package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ost_form database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Ost_form implements Serializable {
	
	@Id
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private byte deletable;

	private String instructions;

	@Lob
	private String notes;

	private String title;

	private String type;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public byte getDeletable() {
		return this.deletable;
	}

	public void setDeletable(byte deletable) {
		this.deletable = deletable;
	}

	public String getInstructions() {
		return this.instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getUpdated() {
		return this.updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

}