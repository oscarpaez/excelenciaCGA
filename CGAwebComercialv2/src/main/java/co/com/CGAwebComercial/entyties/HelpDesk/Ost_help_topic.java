package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ost_help_topic database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Ost_help_topic implements Serializable {
	

	@Id
	private int topic_id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private int dept_id;

	private int flags;

	private int form_id;

	private byte isactive;

	private byte ispublic;

	private byte noautoresp;

	@Lob
	private String notes;

	private String number_format;

	private int page_id;

	private int priority_id;

	private int sequence_id;

	private int sla_id;

	private int sort;

	private int staff_id;

	private int status_id;

	private int team_id;

	private String topic;

	private int topic_pid;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;

	public int getTopic_id() {
		return topic_id;
	}

	public void setTopic_id(int topic_id) {
		this.topic_id = topic_id;
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

	public int getFlags() {
		return flags;
	}

	public void setFlags(int flags) {
		this.flags = flags;
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

	public byte getIspublic() {
		return ispublic;
	}

	public void setIspublic(byte ispublic) {
		this.ispublic = ispublic;
	}

	public byte getNoautoresp() {
		return noautoresp;
	}

	public void setNoautoresp(byte noautoresp) {
		this.noautoresp = noautoresp;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getNumber_format() {
		return number_format;
	}

	public void setNumber_format(String number_format) {
		this.number_format = number_format;
	}

	public int getPage_id() {
		return page_id;
	}

	public void setPage_id(int page_id) {
		this.page_id = page_id;
	}

	public int getPriority_id() {
		return priority_id;
	}

	public void setPriority_id(int priority_id) {
		this.priority_id = priority_id;
	}

	public int getSequence_id() {
		return sequence_id;
	}

	public void setSequence_id(int sequence_id) {
		this.sequence_id = sequence_id;
	}

	public int getSla_id() {
		return sla_id;
	}

	public void setSla_id(int sla_id) {
		this.sla_id = sla_id;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(int staff_id) {
		this.staff_id = staff_id;
	}

	public int getStatus_id() {
		return status_id;
	}

	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}

	public int getTeam_id() {
		return team_id;
	}

	public void setTeam_id(int team_id) {
		this.team_id = team_id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public int getTopic_pid() {
		return topic_pid;
	}

	public void setTopic_pid(int topic_pid) {
		this.topic_pid = topic_pid;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
	
}