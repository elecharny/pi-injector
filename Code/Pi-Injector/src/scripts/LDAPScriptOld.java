package scripts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LDAPScriptOld extends AbstractScript {

	private static final long serialVersionUID = 1L;
	
	
	private List<LDAPRequestWithParams> requestsList;

	
	public LDAPScriptOld(String servername, int serverport) {
		//super(servername, serverport);
		
		requestsList = new ArrayList<LDAPRequestWithParams>();
	}
	
	
	
	public List<LDAPRequestWithParams> getScriptRequestsList() {
		return requestsList;
	}
	
	
	public void addBindRequest(String userDN, String userPassword) {
		
		List<Object> params = new ArrayList<Object>();
		params.add(userDN);
		params.add(userPassword);
		
		requestsList.add(new LDAPRequestWithParams(LDAPRequestType.BIND, params));
	}
	
	// TODO: Ajouter tous les types de requêtes LDAP
	
	
	
	public enum LDAPRequestType {
		BIND, UNBIND, ADD, SEARCH, MODIFY, DELETE
	}
	
	
	public class LDAPRequestWithParams implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		private LDAPRequestType	requestType;
		private List<Object>	params;
		
		public LDAPRequestWithParams(
			LDAPRequestType	requestType,
			List<Object>	params
		) {
			this.requestType	= requestType;
			this.params			= params;
		}
		
		
		public LDAPRequestType	getRequestType() {
			return requestType;
		}
		
		public List<Object>		getParams() {
			return params;
		}
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
