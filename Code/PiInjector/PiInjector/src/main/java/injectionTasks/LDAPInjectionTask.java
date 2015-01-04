package injectionTasks;

import java.io.IOException;
import java.util.ArrayList;

import resultsData.RequestTimer;
import scripts.LDAPScript;
import scripts.LDAPScript.LDAPRequestType;
import scripts.LDAPScript.LDAPRequestWithParams;

import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.jppf.node.protocol.AbstractTask;


@SuppressWarnings("serial")
public class LDAPInjectionTask extends AbstractTask<ArrayList<RequestTimer<LDAPRequestType>>> {
	private LDAPScript									script;
	private ArrayList<RequestTimer<LDAPRequestType>>	results;
	private LdapConnection								connection;

	
	public LDAPInjectionTask(LDAPScript script) {
		this.script = script;
		results = new ArrayList<RequestTimer<LDAPRequestType>>();
	}

	
	@Override
	public void run() {
		System.out.println("Requests injection started...");
		
		try {

			connection = new LdapNetworkConnection(
					script.getServername(), script.getServerport());
			
			RequestTimer<LDAPRequestType> first = new RequestTimer<LDAPRequestType>();
			first.setStartTime(System.currentTimeMillis());
			System.out.println(System.currentTimeMillis());
			results.add(first);
			
			// TODO: Boucle infinie ici
			for (int i = 0; i < 150000; i++) {
				for (LDAPRequestWithParams request : script.getScriptRequestsList()) {
					
					RequestTimer<LDAPRequestType> result = executeRequest(request);
					
					if (result != null)
						results.add(result);
				}
			}
					
			connection.close();
			
			// Permet d'éviter une exception LdapNetworkConnection
			connection = null;
		}
		catch (LdapException e) {
			setThrowable(e);
			return;
		}
		catch (IOException e) {
			setThrowable(e);
			return;
		}
		catch (InterruptedException e) {
			setThrowable(e);
			return;
		}
		catch (Exception e) {
			setThrowable(e);
			return;
		}
		
		
		setResult(results);
	}
	

	public RequestTimer<LDAPRequestType> executeRequest(LDAPRequestWithParams request) throws Exception {
		
		RequestTimer<LDAPRequestType> data = null;
		
		if (connection != null) {
			
			LDAPRequestType requestType = request.getRequestType();
			int paramsCount = request.getParams().size();
			
			if (requestType == LDAPRequestType.BIND && paramsCount == 2) {

				data = new RequestTimer<LDAPRequestType>();
				data.setRequestType(LDAPRequestType.BIND);
				
				data.setStartTime(System.nanoTime());
				
				connection.bind(
						(String)request.getParams().get(0),
						(String)request.getParams().get(1));
				
				data.setExecutionTime(System.nanoTime() - data.getStartTime());
			}
			// TODO: Rajouter les exécutions des autres types de requêtes
			else {
				System.out.println("Type de requête inconnue...");
			}
		}
		
		return data;
	}
}
