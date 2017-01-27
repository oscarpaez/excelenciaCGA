package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ost_file_chunk database table.
 * 
 */
@SuppressWarnings("serial")
@Entity

public class Ost_file_chunk implements Serializable {
	

	@EmbeddedId
	private OstFileChunkPK id;

	@Lob
	private byte[] filedata;

	public OstFileChunkPK getId() {
		return id;
	}

	public void setId(OstFileChunkPK id) {
		this.id = id;
	}

	public byte[] getFiledata() {
		return filedata;
	}

	public void setFiledata(byte[] filedata) {
		this.filedata = filedata;
	}
}