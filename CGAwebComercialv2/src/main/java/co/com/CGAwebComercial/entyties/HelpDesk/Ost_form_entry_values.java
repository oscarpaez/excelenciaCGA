package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ost_form_entry_values database table.
 * 
 */
@SuppressWarnings("serial")
@Entity

public class Ost_form_entry_values implements Serializable {
	

	@EmbeddedId
	private OstFormEntryValuePK id;

	@Lob
	private String value;

	private int value_id;

	public OstFormEntryValuePK getId() {
		return id;
	}

	public void setId(OstFormEntryValuePK id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getValue_id() {
		return value_id;
	}

	public void setValue_id(int value_id) {
		this.value_id = value_id;
	}
}