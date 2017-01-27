package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ost_sla database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Ost_sla implements Serializable {
	

	@Id
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private byte disable_overdue_alerts;

	private byte enable_priority_escalation;

	private int grace_period;

	private byte isactive;

	private String name;

	@Lob
	private String notes;

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

	public byte getDisable_overdue_alerts() {
		return disable_overdue_alerts;
	}

	public void setDisable_overdue_alerts(byte disable_overdue_alerts) {
		this.disable_overdue_alerts = disable_overdue_alerts;
	}

	public byte getEnable_priority_escalation() {
		return enable_priority_escalation;
	}

	public void setEnable_priority_escalation(byte enable_priority_escalation) {
		this.enable_priority_escalation = enable_priority_escalation;
	}

	public int getGrace_period() {
		return grace_period;
	}

	public void setGrace_period(int grace_period) {
		this.grace_period = grace_period;
	}

	public byte getIsactive() {
		return isactive;
	}

	public void setIsactive(byte isactive) {
		this.isactive = isactive;
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