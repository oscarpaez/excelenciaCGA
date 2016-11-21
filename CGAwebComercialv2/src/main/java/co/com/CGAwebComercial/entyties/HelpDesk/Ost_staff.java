package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
public class Ost_staff implements Serializable{
	
	@Id
	private int staff_id;

	private byte assigned_only;

	private int auto_refresh_rate;

	private String backend;

	private byte change_passwd;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private byte daylight_saving;

	private String default_paper_size;

	private String default_signature_type;

	private int dept_id;

	private String email;

	private String firstname;

	private int group_id;

	private byte isactive;

	private byte isadmin;

	private byte isvisible;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastlogin;

	private String lastname;

	private int max_page_size;

	private String mobile;

	@Lob
	private String notes;

	private byte onvacation;

	private String passwd;

	@Temporal(TemporalType.TIMESTAMP)
	private Date passwdreset;

	private String phone;

	private String phone_ext;

	private byte show_assigned_tickets;

	@Lob
	private String signature;

	private int timezoneId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;

	private String username;

	public int getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(int staff_id) {
		this.staff_id = staff_id;
	}

	public byte getAssigned_only() {
		return assigned_only;
	}

	public void setAssigned_only(byte assigned_only) {
		this.assigned_only = assigned_only;
	}

	public int getAuto_refresh_rate() {
		return auto_refresh_rate;
	}

	public void setAuto_refresh_rate(int auto_refresh_rate) {
		this.auto_refresh_rate = auto_refresh_rate;
	}

	public String getBackend() {
		return backend;
	}

	public void setBackend(String backend) {
		this.backend = backend;
	}

	public byte getChange_passwd() {
		return change_passwd;
	}

	public void setChange_passwd(byte change_passwd) {
		this.change_passwd = change_passwd;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public byte getDaylight_saving() {
		return daylight_saving;
	}

	public void setDaylight_saving(byte daylight_saving) {
		this.daylight_saving = daylight_saving;
	}

	public String getDefault_paper_size() {
		return default_paper_size;
	}

	public void setDefault_paper_size(String default_paper_size) {
		this.default_paper_size = default_paper_size;
	}

	public String getDefault_signature_type() {
		return default_signature_type;
	}

	public void setDefault_signature_type(String default_signature_type) {
		this.default_signature_type = default_signature_type;
	}

	public int getDept_id() {
		return dept_id;
	}

	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public int getGroup_id() {
		return group_id;
	}

	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}

	public byte getIsactive() {
		return isactive;
	}

	public void setIsactive(byte isactive) {
		this.isactive = isactive;
	}

	public byte getIsadmin() {
		return isadmin;
	}

	public void setIsadmin(byte isadmin) {
		this.isadmin = isadmin;
	}

	public byte getIsvisible() {
		return isvisible;
	}

	public void setIsvisible(byte isvisible) {
		this.isvisible = isvisible;
	}

	public Date getLastlogin() {
		return lastlogin;
	}

	public void setLastlogin(Date lastlogin) {
		this.lastlogin = lastlogin;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getMax_page_size() {
		return max_page_size;
	}

	public void setMax_page_size(int max_page_size) {
		this.max_page_size = max_page_size;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public byte getOnvacation() {
		return onvacation;
	}

	public void setOnvacation(byte onvacation) {
		this.onvacation = onvacation;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Date getPasswdreset() {
		return passwdreset;
	}

	public void setPasswdreset(Date passwdreset) {
		this.passwdreset = passwdreset;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone_ext() {
		return phone_ext;
	}

	public void setPhone_ext(String phone_ext) {
		this.phone_ext = phone_ext;
	}

	public byte getShow_assigned_tickets() {
		return show_assigned_tickets;
	}

	public void setShow_assigned_tickets(byte show_assigned_tickets) {
		this.show_assigned_tickets = show_assigned_tickets;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public int getTimezoneId() {
		return timezoneId;
	}

	public void setTimezoneId(int timezoneId) {
		this.timezoneId = timezoneId;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
