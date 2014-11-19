package injectionTasks;

import java.io.IOException;

import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.jppf.node.protocol.AbstractTask;

import resultsData.LDAPResults;
import scripts.LDAPScript;
import scripts.LDAPScript.LDAPRequestType;
import scripts.LDAPScript.LDAPRequestWithParams;

public class LDAPInjectionTask extends AbstractTask<LDAPResults> {
	
	private static final long serialVersionUID = 1L;
	
	private LDAPScript		script;
	private LDAPResults		results;
	
	private LdapConnection	connection;

	
	public LDAPInjectionTask(LDAPScript script) {
		this.script = script;
		results = new LDAPResults();
	}

	
	@Override
	public void run() {
		
		System.out.println("Requests injection started...");
		
		try {

			connection = new LdapNetworkConnection(
					script.getServername(), script.getServerport());
			
			long startTotalExecutionTime = System.nanoTime();
			
			// TODO: Changer cette boucle en while true avec un mécanisme d'arrêt depuis
			// le client (interrupt ...)
			for (int i = 0; i < 500; i++) {
			
				long startScriptLoopTime = System.nanoTime();
				
				for (LDAPRequestWithParams request : script.getScriptRequestsList()) {
					
					long startCurrentOperationTime = System.nanoTime();

					executeRequest(request);
					
					results.addRequestExecutionTime(i,
							request.getRequestType(),
							System.nanoTime() - startCurrentOperationTime);
				}
				
				results.addScriptExecutionTime(i,
						System.nanoTime() - startScriptLoopTime);
			}
			
			
			results.setTotalExecutionTime(
					System.nanoTime() - startTotalExecutionTime);
			
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
	
	
	public void executeRequest(LDAPRequestWithParams request) throws Exception {
		
		if (connection != null) {
			
			LDAPRequestType requestType = request.getRequestType();
			
			if (requestType == LDAPRequestType.BIND) {
				
				connection.bind(
						(String)request.getParams().get(0),
						(String)request.getParams().get(1));
			}
			else {
				System.out.println("Type de requête inconnue...");
			}
			
			// TODO: Rajouter les exécutions des autres types de requêtes
		}
	}
}
