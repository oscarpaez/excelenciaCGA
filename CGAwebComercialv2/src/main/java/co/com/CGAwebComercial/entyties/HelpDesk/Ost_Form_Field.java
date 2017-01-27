package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ost_form_field database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
public class Ost_Form_Field implements Serializable {
	

	@Id
	private int id;

	@Lob
	private String configuration;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private byte edit_mask;

	private int form_id;

	private String hint;

	private String label;

	private String name;

	@Column(name="private")
	private byte private_;

	private byte required;

	private int sort;

	private String type;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getConfiguration() {
		return configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public byte getEdit_mask() {
		return edit_mask;
	}

	public void setEdit_mask(byte edit_mask) {
		this.edit_mask = edit_mask;
	}

	public int getForm_id() {
		return form_id;
	}

	public void setForm_id(int form_id) {
		this.form_id = form_id;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getPrivate_() {
		return private_;
	}

	public void setPrivate_(byte private_) {
		this.private_ = private_;
	}

	public byte getRequired() {
		return required;
	}

	public void setRequired(byte required) {
		this.required = required;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
}