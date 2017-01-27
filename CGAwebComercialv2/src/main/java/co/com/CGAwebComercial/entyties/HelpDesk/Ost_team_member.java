package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ost_team_member database table.
 * 
 */

@SuppressWarnings("serial")
@Entity
public class Ost_team_member implements Serializable {
	
	@EmbeddedId
	private OstTeamMemberPK id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;


	public OstTeamMemberPK getId() {
		return this.id;
	}

	public void setId(OstTeamMemberPK id) {
		this.id = id;
	}

	public Date getUpdated() {
		return this.updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

}