package resultsData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import scripts.LDAPScript.LDAPRequestType;


public class LDAPResults implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private long 					totalExecutionTime;
	private Map<Integer, Long>		scriptExecutionTimeMap;
	private List<RequestData>		requestExecutionTimeList;
	
	
	public LDAPResults() {
		totalExecutionTime			= 0;
		scriptExecutionTimeMap		= new HashMap<Integer, Long>();
		requestExecutionTimeList	= new ArrayList<RequestData>();
	}
	
	
	
	
	public long getTotalExecutionTime() {
		return totalExecutionTime;
	}
	
	public void setTotalExecutionTime(long totalExecutionTime) {
		this.totalExecutionTime = totalExecutionTime;
	}
	
	
	
	public Map<Integer, Long> getScriptExecutionTimeMap() {
		return scriptExecutionTimeMap;
	}
	
	public void addScriptExecutionTime(
		int		scriptCounter,
		long	scriptExecutionTime
	) {
		scriptExecutionTimeMap.put(scriptCounter, scriptExecutionTime);
	}
	
	
	
	public List<RequestData> getRequestExecutionTimeList() {
		return requestExecutionTimeList;
	}
	
	public void addRequestExecutionTime(
		int				scriptCounter,
		LDAPRequestType	requestType,
		long			requestExecutionTime
	) {
		requestExecutionTimeList.add(
				new RequestData(scriptCounter, requestType, requestExecutionTime));
	}
	
	
	
	
	public class RequestData implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		private int 			scriptCounter;
		private LDAPRequestType	requestType;
		private long			requestExecutionTime;
		
		public RequestData(
			int 			scriptCounter,
			LDAPRequestType	requestType,
			long			requestExecutionTime
		) {
			this.scriptCounter			= scriptCounter;
			this.requestType			= requestType;
			this.requestExecutionTime	= requestExecutionTime;
		}
		
		
		public int				getScriptCounter() {
			return scriptCounter;
		}
		
		public LDAPRequestType	getRequestType() {
			return requestType;
		}
		
		public long				getRequestExecutionTime() {
			return requestExecutionTime;
		}
	}
}
