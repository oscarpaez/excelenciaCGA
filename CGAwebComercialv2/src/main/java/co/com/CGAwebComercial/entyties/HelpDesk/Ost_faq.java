package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ost_faq database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Ost_faq implements Serializable {
	
	@Id
	private int faq_id;

	@Lob
	private String answer;

	private int category_id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private byte ispublished;

	@Lob
	private String keywords;

	@Lob
	private String notes;

	private String question;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;

	public int getFaq_id() {
		return faq_id;
	}

	public void setFaq_id(int faq_id) {
		this.faq_id = faq_id;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public byte getIspublished() {
		return ispublished;
	}

	public void setIspublished(byte ispublished) {
		this.ispublished = ispublished;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
}