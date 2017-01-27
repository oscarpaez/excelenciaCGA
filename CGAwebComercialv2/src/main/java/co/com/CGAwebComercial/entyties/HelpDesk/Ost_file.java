package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.math.BigInteger;


/**
 * The persistent class for the ost_file database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Ost_file implements Serializable {
	
	@Id
	private int id;

	private String attrs;

	private String bk;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private String ft;

	private String key;

	private String name;

	private String signature;

	private BigInteger size;

	private String type;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAttrs() {
		return this.attrs;
	}

	public void setAttrs(String attrs) {
		this.attrs = attrs;
	}

	public String getBk() {
		return this.bk;
	}

	public void setBk(String bk) {
		this.bk = bk;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getFt() {
		return this.ft;
	}

	public void setFt(String ft) {
		this.ft = ft;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSignature() {
		return this.signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public BigInteger getSize() {
		return this.size;
	}

	public void setSize(BigInteger size) {
		this.size = size;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}
}