package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ost_session database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Ost_session implements Serializable {
	
	@Id
	private String session_id;

	@Lob
	private byte[] session_data;

	@Temporal(TemporalType.TIMESTAMP)
	private Date session_expire;

	@Temporal(TemporalType.TIMESTAMP)
	private Date session_updated;

	private String user_agent;

	private String user_id;

	private String user_ip;

	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public byte[] getSession_data() {
		return session_data;
	}

	public void setSession_data(byte[] session_data) {
		this.session_data = session_data;
	}

	public Date getSession_expire() {
		return session_expire;
	}

	public void setSession_expire(Date session_expire) {
		this.session_expire = session_expire;
	}

	public Date getSession_updated() {
		return session_updated;
	}

	public void setSession_updated(Date session_updated) {
		this.session_updated = session_updated;
	}

	public String getUser_agent() {
		return user_agent;
	}

	public void setUser_agent(String user_agent) {
		this.user_agent = user_agent;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_ip() {
		return user_ip;
	}

	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip;
	}
}