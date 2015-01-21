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
	
	
	public void addAddRequest(String dn, List<LDAPAttribute> attributes) {
		
		List<Object> params = new ArrayList<>();
		params.add(dn);
		params.add(attributes);
		
		addNewMethod(
				"executeAddRequest",
				new Class[] {String.class, List.class},
				params);
	}
	
	public void executeAddRequest(String dn, List<LDAPAttribute> attributes) {
		
		try {
			
			List<String> strings = new ArrayList<String>();
			
			for (LDAPAttribute attribute : attributes) {
				strings.add(attribute.getAttribute() + ": " + attribute.getValue());
			}
			
			Entry entry = new DefaultEntry(dn, strings.toArray());
			
			//Entry entry = new DefaultEntry(Dn dn, Object...dn elements);
			//Entry entry = new DefaultEntry(String dn, Object...dn elements);
			
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
	
	
	public void addSearchRequest(String dn, String filter,
			SearchScope scope, List<LDAPAttribute> attributes) {
		
		List<Object> params = new ArrayList<>();
		params.add(dn);
		params.add(filter);
		params.add(scope);
		params.add(attributes);
		
		addNewMethod(
				"executeSearchRequest",
				new Class[] {String.class, String.class, SearchScope.class, List.class},
				params);
	}
	
	public void executeSearchRequest(String dn, String filter,
			SearchScope scope, List<LDAPAttribute> attributes) {
		
		SearchRequest req = new SearchRequestImpl();

		try {
			req.setBase(new Dn(dn));
			req.setFilter(filter); // mettre les parenthèses autour du filter ?
			req.setScope(scope);
			
			List<String> strings = new ArrayList<String>();
			
			for (LDAPAttribute attribute : attributes) {
				strings.add(attribute.getAttribute() + ": " + attribute.getValue());
			}
			
			req.addAttributes(strings.toArray(new String[strings.size()]));
			
			connection.search(req);
		} catch (LdapException e) {
			e.printStackTrace();
		}
	}
}
