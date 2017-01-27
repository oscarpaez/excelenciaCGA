package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@SuppressWarnings("serial")
@Entity
public class Ost_ticket implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ticket_Id;

	private String number;
	
	private int user_id;
	
	private int user_email_id;
	
	private int status_id;
	
	private int dept_id;
	
	private int sla_id;
	
	private int topic_id;
	
	private int staff_id;
	
	private int team_id;
	
	private int email_id;
	
	private int flags;
	
	private String ip_address;
	
	private String source;
	
	private byte isoverdue;
	
	private byte isanswered;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date duedate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date reopened;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date closed;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastmessage;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastresponse;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;

	private int userId;

	public int getTicket_Id() {
		return ticket_Id;
	}

	public void setTicket_Id(int ticket_Id) {
		this.ticket_Id = ticket_Id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getUser_email_id() {
		return user_email_id;
	}

	public void setUser_email_id(int user_email_id) {
		this.user_email_id = user_email_id;
	}

	public int getStatus_id() {
		return status_id;
	}

	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}

	public int getDept_id() {
		return dept_id;
	}

	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}

	public int getSla_id() {
		return sla_id;
	}

	public void setSla_id(int sla_id) {
		this.sla_id = sla_id;
	}

	public int getTopic_id() {
		return topic_id;
	}

	public void setTopic_id(int topic_id) {
		this.topic_id = topic_id;
	}

	public int getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(int staff_id) {
		this.staff_id = staff_id;
	}

	public int getTeam_id() {
		return team_id;
	}

	public void setTeam_id(int team_id) {
		this.team_id = team_id;
	}

	public int getEmail_id() {
		return email_id;
	}

	public void setEmail_id(int email_id) {
		this.email_id = email_id;
	}

	public int getFlags() {
		return flags;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public byte getIsoverdue() {
		return isoverdue;
	}

	public void setIsoverdue(byte isoverdue) {
		this.isoverdue = isoverdue;
	}

	public byte getIsanswered() {
		return isanswered;
	}

	public void setIsanswered(byte isanswered) {
		this.isanswered = isanswered;
	}

	public Date getDuedate() {
		return duedate;
	}

	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}

	public Date getReopened() {
		return reopened;
	}

	public void setReopened(Date reopened) {
		this.reopened = reopened;
	}

	public Date getClosed() {
		return closed;
	}

	public void setClosed(Date closed) {
		this.closed = closed;
	}

	public Date getLastmessage() {
		return lastmessage;
	}

	public void setLastmessage(Date lastmessage) {
		this.lastmessage = lastmessage;
	}

	public Date getLastresponse() {
		return lastresponse;
	}

	public void setLastresponse(Date lastresponse) {
		this.lastresponse = lastresponse;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
}
