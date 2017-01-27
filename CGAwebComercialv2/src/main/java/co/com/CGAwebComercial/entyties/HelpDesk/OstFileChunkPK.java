package co.com.CGAwebComercial.entyties.HelpDesk;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ost_file_chunk database table.
 * 
 */
@SuppressWarnings("serial")
@Embeddable
public class OstFileChunkPK implements Serializable {
	

	
	private int file_id;

	private int chunk_id;

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof OstFileChunkPK)) {
			return false;
		}
		OstFileChunkPK castOther = (OstFileChunkPK)other;
		return 
			(this.file_id == castOther.file_id)
			&& (this.chunk_id == castOther.chunk_id);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.file_id;
		hash = hash * prime + this.chunk_id;
		
		return hash;
	}

	public int getFile_id() {
		return file_id;
	}

	public void setFile_id(int file_id) {
		this.file_id = file_id;
	}

	public int getChunk_id() {
		return chunk_id;
	}

	public void setChunk_id(int chunk_id) {
		this.chunk_id = chunk_id;
	}	
}