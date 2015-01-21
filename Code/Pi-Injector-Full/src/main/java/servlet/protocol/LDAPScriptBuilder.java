package servlet.protocol;

import javax.servlet.http.HttpServletRequest;

import org.apache.directory.api.ldap.model.message.SearchScope;

import scripts.AbstractScript;
import scripts.LDAPScript;


public class LDAPScriptBuilder {
	public static AbstractScript getScript(HttpServletRequest request) {
		String servername = request.getParameter("form_test_servername");
		int port = request.getParameter("form_test_port") != null && !request.getParameter("form_test_port").equals("") ? Integer.valueOf(request.getParameter("form_test_port")) : -1;
		int nbPlan = request.getParameter("form_test_nb-plan") != null && !request.getParameter("form_test_nb-plan").equals("") ? Integer.valueOf(request.getParameter("form_test_nb-plan")) : -1;
		
		LDAPScript script = new LDAPScript(servername, port);
		
		System.out.println(servername + ", " + port + ", " + nbPlan);
		
		for(int i = 1; i <= nbPlan; i++) {
			String action = request.getParameter("form_test_plan_action-" + i);
			
			System.out.print(action + ", ");
			
			switch(action) {
				case "add" :
					String entryDnAdd = request.getParameter("form_test_plan_entry-dn-" + i);
					script.addAddRequest(entryDnAdd);
					System.out.println(entryDnAdd);
					break;
				
				case "bind" :
					String usernameBind = request.getParameter("form_test_plan_username-" + i);
					String passwordBind = request.getParameter("form_test_plan_password-" + i);
					script.addBindRequest(usernameBind, passwordBind);
					System.out.println(usernameBind + ", " + passwordBind);
					break;
					
				case "delete" :
					String entryDnDelete = request.getParameter("form_test_plan_entry-dn-" + i);
					script.addDeleteRequest(entryDnDelete);
					System.out.println(entryDnDelete);
					break;
					
				case "search" :
					String baseSearch = request.getParameter("form_test_plan_base-" + i);
					String filterSearch = request.getParameter("form_test_plan_filter-" + i);
					SearchScope scopeSearch = null;
					switch(request.getParameter("form_test_plan_scope-" + i)) {
						case "base-object" :
							scopeSearch = SearchScope.OBJECT;
							break;
						case "one-level" :
							scopeSearch = SearchScope.ONELEVEL;
							break;
						case "subtree" :
							scopeSearch = SearchScope.SUBTREE;
							break;
					}
					script.addSearchRequest(baseSearch, filterSearch, scopeSearch);
					System.out.println(baseSearch + ", " + filterSearch + ", " + scopeSearch);
					break;
				
				case "unbind" :
					script.addUnbindRequest();
					break;
				
				// Ajouter ici les autres types de requêtes pour le protocol
				
				default :
					System.out.println("unknown");
					break;
			}
		}
		
		return script;
	}
}
