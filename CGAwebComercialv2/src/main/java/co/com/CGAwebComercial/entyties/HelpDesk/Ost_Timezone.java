package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ost_timezone database table.
 * 
 */

@SuppressWarnings("serial")
@Entity
public class Ost_Timezone implements Serializable {
	
	@Id
	private int id;

	private float offset;

	private String timezone;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getOffset() {
		return offset;
	}

	public void setOffset(float offset) {
		this.offset = offset;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
}