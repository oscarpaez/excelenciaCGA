package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the ost_user_account database table.
 * 
 */

@SuppressWarnings("serial")
@Entity
public class Ost_User_Account implements Serializable {
	

	@Id
	private int id;

	private String backend;

	private byte dst;

	private String lang;

	private String passwd;

	private Timestamp registered;

	private int status;

	private int timezone_id;

	private int user_id;

	private String username;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBackend() {
		return backend;
	}

	public void setBackend(String backend) {
		this.backend = backend;
	}

	public byte getDst() {
		return dst;
	}

	public void setDst(byte dst) {
		this.dst = dst;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Timestamp getRegistered() {
		return registered;
	}

	public void setRegistered(Timestamp registered) {
		this.registered = registered;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getTimezone_id() {
		return timezone_id;
	}

	public void setTimezone_id(int timezone_id) {
		this.timezone_id = timezone_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}