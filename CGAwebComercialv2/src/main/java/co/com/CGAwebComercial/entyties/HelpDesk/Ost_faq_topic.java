package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ost_faq_topic database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Ost_faq_topic implements Serializable {
	

	@EmbeddedId
	private OstFaqTopicPK id;

	

	public OstFaqTopicPK getId() {
		return this.id;
	}

	public void setId(OstFaqTopicPK id) {
		this.id = id;
	}

}