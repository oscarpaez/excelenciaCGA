package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ost_form_entry_values database table.
 * 
 */
@SuppressWarnings("serial")
@Embeddable
public class OstFormEntryValuePK implements Serializable {
	

	private int entry_id;

	private int field_id;

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof OstFormEntryValuePK)) {
			return false;
		}
		OstFormEntryValuePK castOther = (OstFormEntryValuePK)other;
		return 
			(this.entry_id == castOther.entry_id)
			&& (this.field_id == castOther.field_id);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.entry_id;
		hash = hash * prime + this.field_id;
		
		return hash;
	}
}