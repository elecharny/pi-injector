package scripts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.directory.api.ldap.model.entry.DefaultEntry;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.message.SearchRequest;
import org.apache.directory.api.ldap.model.message.SearchRequestImpl;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.api.ldap.model.name.Dn;
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
	
	
	public void addUnbindRequest() {
		
		addNewMethod(
				"executeUnbindRequest",
				new Class[] {},
				new ArrayList<Object>());
	}
	
	public void executeUnbindRequest() {
		
		try {
			connection.unBind();
		} catch (LdapException e) {
			e.printStackTrace();
		}
	}
	
	
	public void addAddRequest(String entryDN) {
		
		List<Object> params = new ArrayList<>();
		params.add(entryDN);
		
		addNewMethod(
				"executeAddRequest",
				new Class[] {String.class},
				params);
	}
	
	public void executeAddRequest(String entryDN) {
		
		try {
			Entry entry = new DefaultEntry(entryDN);
			connection.add(entry);
		} catch (LdapException e) {
			e.printStackTrace();
		}
	}
	
	
	public void addDeleteRequest(String dn) {
		
		List<Object> params = new ArrayList<>();
		params.add(dn);
		
		addNewMethod(
				"executeDeleteRequest",
				new Class[] {String.class},
				params);
	}
	
	public void executeDeleteRequest(String dn) {
		
		try {
			connection.delete(dn);
		} catch (LdapException e) {
			e.printStackTrace();
		}
	}
	
	
	public void addSearchRequest(String dn, String filter, SearchScope scope) {
		
		List<Object> params = new ArrayList<>();
		params.add(dn);
		params.add(filter);
		params.add(scope);
		
		addNewMethod(
				"executeSearchRequest",
				new Class[] {String.class, String.class, SearchScope.class},
				params);
	}
	
	public void executeSearchRequest(String dn, String filter, SearchScope scope) {
		
		SearchRequest req = new SearchRequestImpl();

		try {
			req.setBase(new Dn(dn));
			req.setFilter(filter);
			req.setScope(scope);
			
			connection.search(req);
		} catch (LdapException e) {
			e.printStackTrace();
		}
	}
	

	public void addModifyRequest() {
		
		//addNewMethod("executeModifyRequest");
	}
	
	public void executeModifyRequest() {
		
	}
	
	
	public void addRenameRequest() {
		
		//addNewMethod("executeRenameRequest");
	}
	
	public void executeRenameRequest() {
		
	}
	
	
	// TODO: Ajouter ici les autre méthodes add et execute...
}
