package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ost_email database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Ost_email implements Serializable {
	

	@Id
	private int email_id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private byte dept_id;

	private String email;

	private byte mail_active;

	private String mail_archivefolder;

	private byte mail_delete;

	private String mail_encryption;

	private byte mail_errors;

	private byte mail_fetchfreq;

	private byte mail_fetchmax;

	private String mail_host;

	@Temporal(TemporalType.TIMESTAMP)
	private Date mail_lasterror;

	@Temporal(TemporalType.TIMESTAMP)
	private Date mail_lastfetch;

	private int mail_port;

	private String mail_protocol;

	private String name;

	private byte noautoresp;

	@Lob
	private String notes;

	private byte priority_id;

	private byte smtp_active;

	private byte smtp_auth;

	private String smtp_host;

	private int smtp_port;

	private byte smtp_secure;

	private byte smtp_spoofing;

	private int topic_id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;

	private String userid;

	private String userpass;

	public int getEmail_id() {
		return email_id;
	}

	public void setEmail_id(int email_id) {
		this.email_id = email_id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public byte getDept_id() {
		return dept_id;
	}

	public void setDept_id(byte dept_id) {
		this.dept_id = dept_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte getMail_active() {
		return mail_active;
	}

	public void setMail_active(byte mail_active) {
		this.mail_active = mail_active;
	}

	public String getMail_archivefolder() {
		return mail_archivefolder;
	}

	public void setMail_archivefolder(String mail_archivefolder) {
		this.mail_archivefolder = mail_archivefolder;
	}

	public byte getMail_delete() {
		return mail_delete;
	}

	public void setMail_delete(byte mail_delete) {
		this.mail_delete = mail_delete;
	}

	public String getMail_encryption() {
		return mail_encryption;
	}

	public void setMail_encryption(String mail_encryption) {
		this.mail_encryption = mail_encryption;
	}

	public byte getMail_errors() {
		return mail_errors;
	}

	public void setMail_errors(byte mail_errors) {
		this.mail_errors = mail_errors;
	}

	public byte getMail_fetchfreq() {
		return mail_fetchfreq;
	}

	public void setMail_fetchfreq(byte mail_fetchfreq) {
		this.mail_fetchfreq = mail_fetchfreq;
	}

	public byte getMail_fetchmax() {
		return mail_fetchmax;
	}

	public void setMail_fetchmax(byte mail_fetchmax) {
		this.mail_fetchmax = mail_fetchmax;
	}

	public String getMail_host() {
		return mail_host;
	}

	public void setMail_host(String mail_host) {
		this.mail_host = mail_host;
	}

	public Date getMail_lasterror() {
		return mail_lasterror;
	}

	public void setMail_lasterror(Date mail_lasterror) {
		this.mail_lasterror = mail_lasterror;
	}

	public Date getMail_lastfetch() {
		return mail_lastfetch;
	}

	public void setMail_lastfetch(Date mail_lastfetch) {
		this.mail_lastfetch = mail_lastfetch;
	}

	public int getMail_port() {
		return mail_port;
	}

	public void setMail_port(int mail_port) {
		this.mail_port = mail_port;
	}

	public String getMail_protocol() {
		return mail_protocol;
	}

	public void setMail_protocol(String mail_protocol) {
		this.mail_protocol = mail_protocol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public byte getPriority_id() {
		return priority_id;
	}

	public void setPriority_id(byte priority_id) {
		this.priority_id = priority_id;
	}

	public byte getSmtp_active() {
		return smtp_active;
	}

	public void setSmtp_active(byte smtp_active) {
		this.smtp_active = smtp_active;
	}

	public byte getSmtp_auth() {
		return smtp_auth;
	}

	public void setSmtp_auth(byte smtp_auth) {
		this.smtp_auth = smtp_auth;
	}

	public String getSmtp_host() {
		return smtp_host;
	}

	public void setSmtp_host(String smtp_host) {
		this.smtp_host = smtp_host;
	}

	public int getSmtp_port() {
		return smtp_port;
	}

	public void setSmtp_port(int smtp_port) {
		this.smtp_port = smtp_port;
	}

	public byte getSmtp_secure() {
		return smtp_secure;
	}

	public void setSmtp_secure(byte smtp_secure) {
		this.smtp_secure = smtp_secure;
	}

	public byte getSmtp_spoofing() {
		return smtp_spoofing;
	}

	public void setSmtp_spoofing(byte smtp_spoofing) {
		this.smtp_spoofing = smtp_spoofing;
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

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserpass() {
		return userpass;
	}

	public void setUserpass(String userpass) {
		this.userpass = userpass;
	}
}