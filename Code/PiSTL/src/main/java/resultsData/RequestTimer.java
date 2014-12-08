package main.java.resultsData;

import java.io.Serializable;


@SuppressWarnings("serial")
public class RequestTimer<E extends Enum<?>> implements Serializable {
	private long	executionTime;
	private long	startTime;
	private E		requestType;


	public long		getExecutionTime() {
		return executionTime;
	}

	public void		setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}

	
	public long		getStartTime() {
		return startTime;
	}

	public void		setStartTime(long startTime) {
		this.startTime = startTime;
	}

	
	public E		getRequestType() {
		return requestType;
	}

	public void		setRequestType(E requestType) {
		this.requestType = requestType;
	}
}