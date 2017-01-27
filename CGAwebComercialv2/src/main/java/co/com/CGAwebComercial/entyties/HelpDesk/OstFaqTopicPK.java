package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ost_faq_topic database table.
 * 
 */
@SuppressWarnings("serial")
@Embeddable
public class OstFaqTopicPK implements Serializable {
	
	
	private int faq_id;

	private int topic_id;

	
	public int getFaq_id() {
		return faq_id;
	}

	public void setFaq_id(int faq_id) {
		this.faq_id = faq_id;
	}

	public int getTopic_id() {
		return topic_id;
	}

	public void setTopic_id(int topic_id) {
		this.topic_id = topic_id;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof OstFaqTopicPK)) {
			return false;
		}
		OstFaqTopicPK castOther = (OstFaqTopicPK)other;
		return 
			(this.faq_id == castOther.faq_id)
			&& (this.topic_id == castOther.topic_id);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.faq_id;
		hash = hash * prime + this.topic_id;
		
		return hash;
	}
}