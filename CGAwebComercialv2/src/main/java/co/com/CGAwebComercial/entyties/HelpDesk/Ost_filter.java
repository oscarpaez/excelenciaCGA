package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ost_filter database table.
 * 
 */
@SuppressWarnings("serial")
@Entity

public class Ost_filter implements Serializable {
	
	@Id
	private int id;

	private int canned_response_id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private int dept_id;

	private byte disable_autoresponder;

	private int email_id;

	private int execorder;

	private String ext_id;

	private int form_id;

	private byte isactive;

	private byte match_all_rules;

	private String name;

	@Lob
	private String notes;

	private int priority_id;

	private byte reject_ticket;

	private int sla_id;

	private int staff_id;

	private int status;

	private int status_id;

	private byte stop_onmatch;

	private String target;

	private int team_id;

	private int topic_id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;

	private byte use_replyto_email;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCanned_response_id() {
		return canned_response_id;
	}

	public void setCanned_response_id(int canned_response_id) {
		this.canned_response_id = canned_response_id;
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

	public byte getDisable_autoresponder() {
		return disable_autoresponder;
	}

	public void setDisable_autoresponder(byte disable_autoresponder) {
		this.disable_autoresponder = disable_autoresponder;
	}

	public int getEmail_id() {
		return email_id;
	}

	public void setEmail_id(int email_id) {
		this.email_id = email_id;
	}

	public int getExecorder() {
		return execorder;
	}

	public void setExecorder(int execorder) {
		this.execorder = execorder;
	}

	public String getExt_id() {
		return ext_id;
	}

	public void setExt_id(String ext_id) {
		this.ext_id = ext_id;
	}

	public int getForm_id() {
		return form_id;
	}

	public void setForm_id(int form_id) {
		this.form_id = form_id;
	}

	public byte getIsactive() {
		return isactive;
	}

	public void setIsactive(byte isactive) {
		this.isactive = isactive;
	}

	public byte getMatch_all_rules() {
		return match_all_rules;
	}

	public void setMatch_all_rules(byte match_all_rules) {
		this.match_all_rules = match_all_rules;
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

	public int getPriority_id() {
		return priority_id;
	}

	public void setPriority_id(int priority_id) {
		this.priority_id = priority_id;
	}

	public byte getReject_ticket() {
		return reject_ticket;
	}

	public void setReject_ticket(byte reject_ticket) {
		this.reject_ticket = reject_ticket;
	}

	public int getSla_id() {
		return sla_id;
	}

	public void setSla_id(int sla_id) {
		this.sla_id = sla_id;
	}

	public int getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(int staff_id) {
		this.staff_id = staff_id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus_id() {
		return status_id;
	}

	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}

	public byte getStop_onmatch() {
		return stop_onmatch;
	}

	public void setStop_onmatch(byte stop_onmatch) {
		this.stop_onmatch = stop_onmatch;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public int getTeam_id() {
		return team_id;
	}

	public void setTeam_id(int team_id) {
		this.team_id = team_id;
	}

	public int getTopic_id() {
		return topic_id;
	}

	public void setTopic_id(int topic_id) {
		this.topic_id = topic_id;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public byte getUse_replyto_email() {
		return use_replyto_email;
	}

	public void setUse_replyto_email(byte use_replyto_email) {
		this.use_replyto_email = use_replyto_email;
	}
	
	
}