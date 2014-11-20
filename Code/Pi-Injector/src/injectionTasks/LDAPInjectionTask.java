package injectionTasks;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.jppf.node.protocol.AbstractTask;



import resultsData.RequestTimer;
import scripts.LDAPScript;
import scripts.LDAPScript.LDAPRequestType;
import scripts.LDAPScript.LDAPRequestWithParams;

public class LDAPInjectionTask extends AbstractTask<ArrayList<RequestTimer<LDAPRequestType>>> {
	
	private static final long serialVersionUID = 1L;
	
	private LDAPScript		script;
	private ArrayList<RequestTimer<LDAPRequestType>>	results;
	
	private LdapConnection	connection;

	
	public LDAPInjectionTask(LDAPScript script) {
		this.script = script;
		//results = new LDAPResults();
		results = new ArrayList<RequestTimer<LDAPRequestType>>();
	}

	
	@Override
	public void run() {
		
		System.out.println("Requests injection started...");
		
		try {

			connection = new LdapNetworkConnection(
					script.getServername(), script.getServerport());
			
			// on commence par save en premier le current time du noeud qui va servir a agréger nos données
			
			Thread.sleep((long) (1000 * Math.random() * 10));
			
			RequestTimer<LDAPRequestType> first = new RequestTimer<LDAPRequestType>();
			first.setStartTime(System.currentTimeMillis());
			results.add(first);
			
			for (int i = 0; i < 1000000; i++) {
			
				for (LDAPRequestWithParams request : script.getScriptRequestsList()) {
					
				//	long startCurrentOperationTime = System.nanoTime();
					
					RequestTimer<LDAPRequestType> data = new RequestTimer<LDAPRequestType>();
					data.setStartTime(System.nanoTime());
					executeRequest(request, data);
					
					results.add(data);

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
	
	

	public void executeRequest(LDAPRequestWithParams request, RequestTimer<LDAPRequestType> data) throws Exception {
		
		if (connection != null) {
			
			LDAPRequestType requestType = request.getRequestType();
			
			if (requestType == LDAPRequestType.BIND) {
				long startCurrentOperationTime = System.nanoTime();

				data.setRequestType(LDAPRequestType.BIND);
				
				connection.bind(
						(String)request.getParams().get(0),
						(String)request.getParams().get(1));
				data.setExecutionTime( System.nanoTime() - startCurrentOperationTime);
			}
			else {
				System.out.println("Type de requête inconnue...");
			}
			
			// TODO: Rajouter les exécutions des autres types de requêtes
		}
	}
}
