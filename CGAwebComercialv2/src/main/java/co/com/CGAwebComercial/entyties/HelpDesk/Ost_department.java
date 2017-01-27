package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ost_department database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Ost_department implements Serializable {
	

	@Id
	private int dept_id;

	private int autoresp_email_id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private String dept_name;

	@Lob
	private String dept_signature;

	
	private int email_id;

	private byte group_membership;

	private byte ispublic;

	private int manager_id;

	private byte message_auto_response;

	private int sla_id;

	private byte ticket_auto_response;

	private int tpl_id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;

	public int getDept_id() {
		return dept_id;
	}

	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}

	public int getAutoresp_email_id() {
		return autoresp_email_id;
	}

	public void setAutoresp_email_id(int autoresp_email_id) {
		this.autoresp_email_id = autoresp_email_id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getDept_signature() {
		return dept_signature;
	}

	public void setDept_signature(String dept_signature) {
		this.dept_signature = dept_signature;
	}

	public int getEmail_id() {
		return email_id;
	}

	public void setEmail_id(int email_id) {
		this.email_id = email_id;
	}

	public byte getGroup_membership() {
		return group_membership;
	}

	public void setGroup_membership(byte group_membership) {
		this.group_membership = group_membership;
	}

	public byte getIspublic() {
		return ispublic;
	}

	public void setIspublic(byte ispublic) {
		this.ispublic = ispublic;
	}

	public int getManager_id() {
		return manager_id;
	}

	public void setManager_id(int manager_id) {
		this.manager_id = manager_id;
	}

	public byte getMessage_auto_response() {
		return message_auto_response;
	}

	public void setMessage_auto_response(byte message_auto_response) {
		this.message_auto_response = message_auto_response;
	}

	public int getSla_id() {
		return sla_id;
	}

	public void setSla_id(int sla_id) {
		this.sla_id = sla_id;
	}

	public byte getTicket_auto_response() {
		return ticket_auto_response;
	}

	public void setTicket_auto_response(byte ticket_auto_response) {
		this.ticket_auto_response = ticket_auto_response;
	}

	public int getTpl_id() {
		return tpl_id;
	}

	public void setTpl_id(int tpl_id) {
		this.tpl_id = tpl_id;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
}