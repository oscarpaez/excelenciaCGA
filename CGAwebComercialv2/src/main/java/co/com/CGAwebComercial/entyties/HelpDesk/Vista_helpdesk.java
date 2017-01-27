package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

@SuppressWarnings("serial")
@Entity
@Subselect("SELECT t.ticket_id, t.number, t.closed, t.created, t.lastmessage, t.lastresponse, t.status_id, "
                  + " s.firstname, s.lastname, s.username, "
                  + " h.topic, h.topic_pid, c.Area, c.CSOPOR, c.priority, c.`subject`, c.ZTIPO, c.ZUSD1, "
                  + " st.state, "
                  + " p.priority_desc "	
                  + " FROM ost_ticket t "
                  + " INNER JOIN  ost_staff  s on  s.staff_id = t.staff_id "
                  + " INNER JOIN  ost_help_topic h on h.topic_id = t.topic_id "
                  + " INNER JOIN  ost_ticket__cdata c on c.ticket_id = t.ticket_id "
                  + " INNER JOIN  ost_ticket_status st on st.id  = t.status_id "
                  + " INNER JOIN  ost_ticket_priority p on p.priority_id = c.priority ")

@Synchronize({"ost_ticket", "ost_staff, ost_help_topic, ost_ticket__cdata, ost_ticket_status, ost_ticket_priority"} )
public class Vista_helpdesk implements Serializable {
	
	 
	@Lob
	private String area;

	@Temporal(TemporalType.TIMESTAMP)
	private Date closed;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@Lob
	private String csopor;

	private String firstname;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastmessage;

	private String lastname;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastresponse;

	private String number;

	@Lob
	private String priority;

	
	private String priority_desc;

	private String state;

	
	private int status_id;

	@Lob
	private String subject;

	@Id
	private int ticket_id;

	private String topic;

	private String username;

	@Lob
	private String ztipo;

	@Lob
	private String zusd1;
	
	private int topic_pid;

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Date getClosed() {
		return closed;
	}

	public void setClosed(Date closed) {
		this.closed = closed;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getCsopor() {
		return csopor;
	}

	public void setCsopor(String csopor) {
		this.csopor = csopor;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public Date getLastmessage() {
		return lastmessage;
	}

	public void setLastmessage(Date lastmessage) {
		this.lastmessage = lastmessage;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getLastresponse() {
		return lastresponse;
	}

	public void setLastresponse(Date lastresponse) {
		this.lastresponse = lastresponse;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getPriority_desc() {
		return priority_desc;
	}

	public void setPriority_desc(String priority_desc) {
		this.priority_desc = priority_desc;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getStatus_id() {
		return status_id;
	}

	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getTicket_id() {
		return ticket_id;
	}

	public void setTicket_id(int ticket_id) {
		this.ticket_id = ticket_id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getZtipo() {
		return ztipo;
	}

	public void setZtipo(String ztipo) {
		this.ztipo = ztipo;
	}

	public String getZusd1() {
		return zusd1;
	}

	public void setZusd1(String zusd1) {
		this.zusd1 = zusd1;
	}

	public int getTopic_pid() {
		return topic_pid;
	}

	public void setTopic_pid(int topic_pid) {
		this.topic_pid = topic_pid;
	}
}
