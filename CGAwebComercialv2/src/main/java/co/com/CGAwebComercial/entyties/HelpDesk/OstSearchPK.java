package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ost__search database table.
 * 
 */
@SuppressWarnings("serial")
@Embeddable
public class OstSearchPK implements Serializable {
	
	private String object_type;

	private int object_id;

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof OstSearchPK)) {
			return false;
		}
		OstSearchPK castOther = (OstSearchPK)other;
		return 
			this.object_type.equals(castOther.object_type)
			&& (this.object_id == castOther.object_id);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.object_type.hashCode();
		hash = hash * prime + this.object_id;
		
		return hash;
	}
}