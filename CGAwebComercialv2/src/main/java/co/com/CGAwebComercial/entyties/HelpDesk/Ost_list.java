package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ost_list database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Ost_list implements Serializable {

	@Id
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private int masks;

	private String name;

	private String name_plural;

	@Lob
	private String notes;

	private String sort_mode;

	private String type;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;

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

	public int getMasks() {
		return masks;
	}

	public void setMasks(int masks) {
		this.masks = masks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName_plural() {
		return name_plural;
	}

	public void setName_plural(String name_plural) {
		this.name_plural = name_plural;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getSort_mode() {
		return sort_mode;
	}

	public void setSort_mode(String sort_mode) {
		this.sort_mode = sort_mode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
}