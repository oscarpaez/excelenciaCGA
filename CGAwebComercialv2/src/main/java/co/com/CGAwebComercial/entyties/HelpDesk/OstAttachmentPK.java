package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ost_attachment database table.
 * 
 */
@SuppressWarnings("serial")
@Embeddable
public class OstAttachmentPK implements Serializable {
	
	
	private int object_id;

	private int file_id;

	private String type;
	
	
	public int getObject_id() {
		return object_id;
	}

	public void setObject_id(int object_id) {
		this.object_id = object_id;
	}

	public int getFile_id() {
		return file_id;
	}

	public void setFile_id(int file_id) {
		this.file_id = file_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof OstAttachmentPK)) {
			return false;
		}
		OstAttachmentPK castOther = (OstAttachmentPK)other;
		return 
			(this.object_id == castOther.object_id)
			&& (this.file_id == castOther.file_id)
			&& this.type.equals(castOther.type);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.object_id;
		hash = hash * prime + this.file_id;
		hash = hash * prime + this.type.hashCode();
		
		return hash;
	}
}