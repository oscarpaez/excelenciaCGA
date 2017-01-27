package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ost_team_member database table.
 * 
 */
@SuppressWarnings("serial")
@Embeddable
public class OstTeamMemberPK implements Serializable {
	

	private int team_id;

	private int staff_id;

	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof OstTeamMemberPK)) {
			return false;
		}
		OstTeamMemberPK castOther = (OstTeamMemberPK)other;
		return 
			(this.team_id == castOther.team_id)
			&& (this.staff_id == castOther.staff_id);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.team_id;
		hash = hash * prime + this.staff_id;
		
		return hash;
	}
}