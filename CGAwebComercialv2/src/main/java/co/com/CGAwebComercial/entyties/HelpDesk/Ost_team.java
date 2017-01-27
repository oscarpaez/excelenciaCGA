package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ost_team database table.
 * 
 */

@SuppressWarnings("serial")
@Entity
public class Ost_team implements Serializable {
	

	@Id
	private int team_id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private byte isenabled;

	private int lead_id;

	private String name;

	private byte noalerts;

	@Lob
	private String notes;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;

	public int getTeam_id() {
		return team_id;
	}

	public void setTeam_id(int team_id) {
		this.team_id = team_id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public byte getIsenabled() {
		return isenabled;
	}

	public void setIsenabled(byte isenabled) {
		this.isenabled = isenabled;
	}

	public int getLead_id() {
		return lead_id;
	}

	public void setLead_id(int lead_id) {
		this.lead_id = lead_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getNoalerts() {
		return noalerts;
	}

	public void setNoalerts(byte noalerts) {
		this.noalerts = noalerts;
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