package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

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
	
	@Transient
	private BigDecimal itemE;
	
	@Transient
	private BigDecimal itemE1;
	
	@Transient
	private BigDecimal itemE2;
	
	@Transient
	private BigDecimal itemE3;
	
	@Transient
	private BigDecimal itemE4;
	
	@Transient
	private BigDecimal itemE5;
	
	@Transient
	private BigDecimal itemE6;
	@Transient
	private BigDecimal itemE7;
	
	@Transient
	private BigDecimal itemE8;
	
	@Transient
	private BigDecimal itemE9;
	
	@Transient
	private BigDecimal itemE10;
	
	@Transient
	private BigDecimal itemE11;
	
	@Transient
	private String titulo;
	
	@Transient
	private String titulo1;
	
	@Transient
	private String titulo2;
	
	@Transient
	private String titulo3;
	
	@Transient
	private String titulo4;
	
	@Transient
	private String titulo5;
	
	@Transient
	private String titulo6;
	
	@Transient
	private String titulo7;
	
	@Transient
	private String titulo8;
	
	@Transient
	private String titulo9;
	
	@Transient
	private String titulo10;
	
	@Transient
	private String titulo11;	
	
	@Transient
	private String etiqueta;
	
	@Transient
	private int reder;
	

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

	public BigDecimal getItemE() {
		return itemE;
	}

	public void setItemE(BigDecimal itemE) {
		this.itemE = itemE;
	}

	public BigDecimal getItemE1() {
		return itemE1;
	}

	public void setItemE1(BigDecimal itemE1) {
		this.itemE1 = itemE1;
	}

	public BigDecimal getItemE2() {
		return itemE2;
	}

	public void setItemE2(BigDecimal itemE2) {
		this.itemE2 = itemE2;
	}

	public BigDecimal getItemE3() {
		return itemE3;
	}

	public void setItemE3(BigDecimal itemE3) {
		this.itemE3 = itemE3;
	}

	public BigDecimal getItemE4() {
		return itemE4;
	}

	public void setItemE4(BigDecimal itemE4) {
		this.itemE4 = itemE4;
	}

	public BigDecimal getItemE5() {
		return itemE5;
	}

	public void setItemE5(BigDecimal itemE5) {
		this.itemE5 = itemE5;
	}

	public BigDecimal getItemE6() {
		return itemE6;
	}

	public void setItemE6(BigDecimal itemE6) {
		this.itemE6 = itemE6;
	}

	public BigDecimal getItemE7() {
		return itemE7;
	}

	public void setItemE7(BigDecimal itemE7) {
		this.itemE7 = itemE7;
	}

	public BigDecimal getItemE8() {
		return itemE8;
	}

	public void setItemE8(BigDecimal itemE8) {
		this.itemE8 = itemE8;
	}

	public BigDecimal getItemE9() {
		return itemE9;
	}

	public void setItemE9(BigDecimal itemE9) {
		this.itemE9 = itemE9;
	}

	public BigDecimal getItemE10() {
		return itemE10;
	}

	public void setItemE10(BigDecimal itemE10) {
		this.itemE10 = itemE10;
	}

	public BigDecimal getItemE11() {
		return itemE11;
	}

	public void setItemE11(BigDecimal itemE11) {
		this.itemE11 = itemE11;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTitulo1() {
		return titulo1;
	}

	public void setTitulo1(String titulo1) {
		this.titulo1 = titulo1;
	}

	public String getTitulo2() {
		return titulo2;
	}

	public void setTitulo2(String titulo2) {
		this.titulo2 = titulo2;
	}

	public String getTitulo3() {
		return titulo3;
	}

	public void setTitulo3(String titulo3) {
		this.titulo3 = titulo3;
	}

	public String getTitulo4() {
		return titulo4;
	}

	public void setTitulo4(String titulo4) {
		this.titulo4 = titulo4;
	}

	public String getTitulo5() {
		return titulo5;
	}

	public void setTitulo5(String titulo5) {
		this.titulo5 = titulo5;
	}

	public String getTitulo6() {
		return titulo6;
	}

	public void setTitulo6(String titulo6) {
		this.titulo6 = titulo6;
	}

	public String getTitulo7() {
		return titulo7;
	}

	public void setTitulo7(String titulo7) {
		this.titulo7 = titulo7;
	}

	public String getTitulo8() {
		return titulo8;
	}

	public void setTitulo8(String titulo8) {
		this.titulo8 = titulo8;
	}

	public String getTitulo9() {
		return titulo9;
	}

	public void setTitulo9(String titulo9) {
		this.titulo9 = titulo9;
	}

	public String getTitulo10() {
		return titulo10;
	}

	public void setTitulo10(String titulo10) {
		this.titulo10 = titulo10;
	}

	public String getTitulo11() {
		return titulo11;
	}

	public void setTitulo11(String titulo11) {
		this.titulo11 = titulo11;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public int getReder() {
		return reder;
	}

	public void setReder(int reder) {
		this.reder = reder;
	}	
}
