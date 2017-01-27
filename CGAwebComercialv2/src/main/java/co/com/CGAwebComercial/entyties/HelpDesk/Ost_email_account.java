package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the ost_email_account database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Ost_email_account implements Serializable {
	
	@Id
	private int id;

	private byte active;

	private Timestamp created;

	private int errors;

	private String host;

	private Timestamp lastconnect;

	private Timestamp lasterror;

	private String name;

	private String options;

	private String password;

	private int port;

	private String protocol;

	private Timestamp updated;

	private String username;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getActive() {
		return active;
	}

	public void setActive(byte active) {
		this.active = active;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public int getErrors() {
		return errors;
	}

	public void setErrors(int errors) {
		this.errors = errors;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Timestamp getLastconnect() {
		return lastconnect;
	}

	public void setLastconnect(Timestamp lastconnect) {
		this.lastconnect = lastconnect;
	}

	public Timestamp getLasterror() {
		return lasterror;
	}

	public void setLasterror(Timestamp lasterror) {
		this.lasterror = lasterror;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public Timestamp getUpdated() {
		return updated;
	}

	public void setUpdated(Timestamp updated) {
		this.updated = updated;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}