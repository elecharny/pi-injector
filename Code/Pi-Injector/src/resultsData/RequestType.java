package resultsData;

import java.io.Serializable;

public class RequestType<E extends Enum<?>> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public E type;
	
	private long duree;
	
	private long datetime; 
	
	


	public long getDuree() {
		return duree;
	}

	public void setDuree(long duree) {
		this.duree = duree;
	}

	public long getDatetime() {
		return datetime;
	}

	public void setDatetime(long datetime) {
		this.datetime = datetime;
	}

	public E getType() {
		return type;
	}

	public void setType(E type) {
		this.type = type;
	}
	
	

}