package scripts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;

public class LDAPScript extends AbstractScript {

	private static final long serialVersionUID = 1L;

	private String 				servername; 
	private int 				serverport;
	private LdapConnection 		connection;
	
	
	public LDAPScript(String servername, int serverport) {
		this.servername = servername;
		this.serverport = serverport;
	}
	
	
	
	
	// -------------------------------------------------------- SCRIPT SETTINGS
	
	@Override
	public void beforeRun() {
		connection = new LdapNetworkConnection(servername, serverport);
	}
	
	@Override
	public void afterRun() {
		
		try {
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Permet d'éviter une exception LdapNetworkConnection
		connection = null;
	}
	
	
	
	
	
	// --------------------------------------------------- REQUESTS DECLARATION
	
	public void addBindRequest(String userDN, String userPassword) {
		
		List<Object> params = new ArrayList<Object>();
		params.add(userDN);
		params.add(userPassword);
		
		addNewMethod(
				"executeBindRequest",
				new Class[] {String.class, String.class},
				params);
	}
	
	public void executeBindRequest(String userDN, String userPassword) {
		
		try {
			connection.bind(userDN, userPassword);
		} catch (LdapException e) {
			e.printStackTrace();
		}
	}
	
	
	// TODO: Ajouter ici les autre méthodes add et execute...
}
