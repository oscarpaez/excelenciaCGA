package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ost_groups database table.
 * 
 */

@SuppressWarnings("serial")
@Entity
public class Ost_groups implements Serializable {
	
	@Id
	private int group_id;

	private byte can_assign_tickets;

	private byte can_ban_emails;

	private byte can_close_tickets;

	private byte can_create_tickets;

	private byte can_delete_tickets;

	private byte can_edit_tickets;

	private byte can_manage_faq;

	private byte can_manage_premade;

	private byte can_post_ticket_reply;

	private byte can_transfer_tickets;

	private byte can_view_staff_stats;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private byte group_enabled;

	private String group_name;

	@Lob
	private String notes;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;

	public int getGroup_id() {
		return group_id;
	}

	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}

	public byte getCan_assign_tickets() {
		return can_assign_tickets;
	}

	public void setCan_assign_tickets(byte can_assign_tickets) {
		this.can_assign_tickets = can_assign_tickets;
	}

	public byte getCan_ban_emails() {
		return can_ban_emails;
	}

	public void setCan_ban_emails(byte can_ban_emails) {
		this.can_ban_emails = can_ban_emails;
	}

	public byte getCan_close_tickets() {
		return can_close_tickets;
	}

	public void setCan_close_tickets(byte can_close_tickets) {
		this.can_close_tickets = can_close_tickets;
	}

	public byte getCan_create_tickets() {
		return can_create_tickets;
	}

	public void setCan_create_tickets(byte can_create_tickets) {
		this.can_create_tickets = can_create_tickets;
	}

	public byte getCan_delete_tickets() {
		return can_delete_tickets;
	}

	public void setCan_delete_tickets(byte can_delete_tickets) {
		this.can_delete_tickets = can_delete_tickets;
	}

	public byte getCan_edit_tickets() {
		return can_edit_tickets;
	}

	public void setCan_edit_tickets(byte can_edit_tickets) {
		this.can_edit_tickets = can_edit_tickets;
	}

	public byte getCan_manage_faq() {
		return can_manage_faq;
	}

	public void setCan_manage_faq(byte can_manage_faq) {
		this.can_manage_faq = can_manage_faq;
	}

	public byte getCan_manage_premade() {
		return can_manage_premade;
	}

	public void setCan_manage_premade(byte can_manage_premade) {
		this.can_manage_premade = can_manage_premade;
	}

	public byte getCan_post_ticket_reply() {
		return can_post_ticket_reply;
	}

	public void setCan_post_ticket_reply(byte can_post_ticket_reply) {
		this.can_post_ticket_reply = can_post_ticket_reply;
	}

	public byte getCan_transfer_tickets() {
		return can_transfer_tickets;
	}

	public void setCan_transfer_tickets(byte can_transfer_tickets) {
		this.can_transfer_tickets = can_transfer_tickets;
	}

	public byte getCan_view_staff_stats() {
		return can_view_staff_stats;
	}

	public void setCan_view_staff_stats(byte can_view_staff_stats) {
		this.can_view_staff_stats = can_view_staff_stats;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public byte getGroup_enabled() {
		return group_enabled;
	}

	public void setGroup_enabled(byte group_enabled) {
		this.group_enabled = group_enabled;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	
}