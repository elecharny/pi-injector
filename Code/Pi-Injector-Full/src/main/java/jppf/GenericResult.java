package jppf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class GenericResult implements Serializable, Comparable<GenericResult> {
	private static final long serialVersionUID = 1L;
	// Script UUID associated to
	private final UUID 			scriptUUID;
	// Start time in milliseconds
	private long 				startTime;
	// Execution time in nanoseconds
	private long 				totalScriptExecutionTime;
	// List of the execution times of the various requests from the script
	private List<Long> 			requestsExecutionTimes = new ArrayList<>();
	
	
	public GenericResult(UUID scriptUUID) {
		this.scriptUUID = scriptUUID;
	}
	
	
	public UUID getScriptUUID() {
		return scriptUUID;
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	public long getTotalScriptExecutionTime() {
		return totalScriptExecutionTime;
	}
	
	public void setTotalScriptExecutionTime(long totalScriptExecutionTime) {
		this.totalScriptExecutionTime = totalScriptExecutionTime;
	}
	
	public List<Long> getRequestsExecutionTimes() {
		return new ArrayList<>(requestsExecutionTimes);
	}
	
	public void addRequestExecutionTime(long requestExecutionTime) {
		this.requestsExecutionTimes.add(requestExecutionTime);
	}


	@Override
	public int compareTo(GenericResult other) {
		long otherCurrentTime = ((GenericResult) other).getStartTime();
		if(otherCurrentTime > this.getStartTime())
			return -1;
		else if(otherCurrentTime == this.getStartTime())
			return 0;
		else
			return 1;
	}

}
