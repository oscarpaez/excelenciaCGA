package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ost_user database table.
 * 
 */

@SuppressWarnings("serial")
@Entity
public class Ost_User implements Serializable {
	
	@Id
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private int default_email_id;

	private String name;

	private int org_id;

	private int status;

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

	public int getDefault_email_id() {
		return default_email_id;
	}

	public void setDefault_email_id(int default_email_id) {
		this.default_email_id = default_email_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrg_id() {
		return org_id;
	}

	public void setOrg_id(int org_id) {
		this.org_id = org_id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
}