package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ost_attachment database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Ost_attachment implements Serializable {
	

	@EmbeddedId
	private OstAttachmentPK id;

	private byte inline;

	
	public OstAttachmentPK getId() {
		return this.id;
	}

	public void setId(OstAttachmentPK id) {
		this.id = id;
	}

	public byte getInline() {
		return this.inline;
	}

	public void setInline(byte inline) {
		this.inline = inline;
	}

}