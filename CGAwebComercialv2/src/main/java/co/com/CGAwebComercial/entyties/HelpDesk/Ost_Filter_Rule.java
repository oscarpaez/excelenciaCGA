package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ost_filter_rule database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Ost_Filter_Rule implements Serializable {
	

	@Id
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private int filter_id;

	private String how;

	private byte isactive;

	@Lob
	private String notes;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;

	private String val;

	private String what;

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

	public int getFilter_id() {
		return filter_id;
	}

	public void setFilter_id(int filter_id) {
		this.filter_id = filter_id;
	}

	public String getHow() {
		return how;
	}

	public void setHow(String how) {
		this.how = how;
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

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getWhat() {
		return what;
	}

	public void setWhat(String what) {
		this.what = what;
	}
}