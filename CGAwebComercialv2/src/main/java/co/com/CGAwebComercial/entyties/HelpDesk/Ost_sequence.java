package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.math.BigInteger;


/**
 * The persistent class for the ost_sequence database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Ost_sequence implements Serializable {
	
	@Id
	private int id;

	private int flags;

	private int increment;

	private String name;

	private BigInteger next;

	private String padding;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFlags() {
		return this.flags;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}

	public int getIncrement() {
		return this.increment;
	}

	public void setIncrement(int increment) {
		this.increment = increment;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigInteger getNext() {
		return this.next;
	}

	public void setNext(BigInteger next) {
		this.next = next;
	}

	public String getPadding() {
		return this.padding;
	}

	public void setPadding(String padding) {
		this.padding = padding;
	}

	public Date getUpdated() {
		return this.updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

}