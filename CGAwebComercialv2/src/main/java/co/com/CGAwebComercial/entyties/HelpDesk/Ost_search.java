package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ost_search database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Ost_search implements Serializable {
	

	@Id
	private int object_id;

	public int getObject_id() {
		return object_id;
	}

	public void setObject_id(int object_id) {
		this.object_id = object_id;
	}
}