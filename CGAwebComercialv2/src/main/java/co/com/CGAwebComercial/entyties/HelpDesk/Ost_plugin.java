package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ost_plugin database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Ost_plugin implements Serializable {
	
	@Id
	private int id;

	private String install_path;

	@Temporal(TemporalType.TIMESTAMP)
	private Date installed;

	private byte isactive;

	private byte isphar;

	private String name;

	private String version;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInstall_path() {
		return install_path;
	}

	public void setInstall_path(String install_path) {
		this.install_path = install_path;
	}

	public Date getInstalled() {
		return installed;
	}

	public void setInstalled(Date installed) {
		this.installed = installed;
	}

	public byte getIsactive() {
		return isactive;
	}

	public void setIsactive(byte isactive) {
		this.isactive = isactive;
	}

	public byte getIsphar() {
		return isphar;
	}

	public void setIsphar(byte isphar) {
		this.isphar = isphar;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}