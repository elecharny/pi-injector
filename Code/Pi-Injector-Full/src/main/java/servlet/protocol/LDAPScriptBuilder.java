package servlet.protocol;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.directory.api.ldap.model.message.SearchScope;

import scripts.AbstractScript;
import scripts.ldap.LDAPAttribute;
import scripts.ldap.LDAPScript;


public class LDAPScriptBuilder {
	public static AbstractScript getScript(HttpServletRequest request) {
		String servername = request.getParameter("form_test_servername");
		int port = request.getParameter("form_test_port") != null && !request.getParameter("form_test_port").equals("") ? Integer.valueOf(request.getParameter("form_test_port")) : 0;
		int nbPlan = request.getParameter("form_test_nb-plan") != null && !request.getParameter("form_test_nb-plan").equals("") ? Integer.valueOf(request.getParameter("form_test_nb-plan")) : 0;
		
		System.out.println(servername + ", " + port + ", " + nbPlan);
		
		LDAPScript script = new LDAPScript(servername, port);
		
		for(int i = 1; i <= nbPlan; i++) {
			String action = request.getParameter("form_test_plan_action-" + i);
			
			System.out.print(action + ", ");
			
			switch(action) {
				case "add" :
					String entryDnAdd = request.getParameter("form_test_plan_entry-dn-" + i);
					int nbAdd = request.getParameter("form_test_nb-add-" + i) != null && !request.getParameter("form_test_nb-add-" + i).equals("") ? Integer.valueOf(request.getParameter("form_test_nb-add-" + i)) : -1;
					System.out.print(entryDnAdd + ", " + nbAdd + ", ");;
					List<LDAPAttribute> addAttributes = new ArrayList<LDAPAttribute>();
					for(int j = 1; j <= nbAdd; j++) {
						String attributeAdd = request.getParameter("form_test_plan_attribute-" + i + "-" + j);
						String valuesAdd = request.getParameter("form_test_plan_value-" + i + "-" + j);
						if(attributeAdd != null && !attributeAdd.isEmpty()) {
							addAttributes.add(new LDAPAttribute(attributeAdd, valuesAdd));
							System.out.print(attributeAdd + ", " + valuesAdd + ", ");
						}
					}
					script.addAddRequest(entryDnAdd, addAttributes);
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
					System.out.println(baseSearch + ", " + filterSearch + ", " + scopeSearch);
					int nbSearch = request.getParameter("form_test_nb-search-" + i) != null && !request.getParameter("form_test_nb-search-" + i).equals("") ? Integer.valueOf(request.getParameter("form_test_nb-search-" + i)) : -1;
					List<LDAPAttribute> searchAttributes = new ArrayList<LDAPAttribute>();
					for(int j = 1; j <= nbSearch; j++) {
						String attributeSearch = request.getParameter("form_test_plan_attribute-" + i + "-" + j);
						String valuesSearch = request.getParameter("form_test_plan_value-" + i + "-" + j);
						if(attributeSearch != null && !attributeSearch.isEmpty()) {
							searchAttributes.add(new LDAPAttribute(attributeSearch, valuesSearch));
							System.out.print(attributeSearch + ", " + valuesSearch + ", ");
						}
					}
					script.addSearchRequest(baseSearch, filterSearch, scopeSearch, searchAttributes);
					break;
				
				case "unbind" :
					script.addUnbindRequest();
					break;
				
				// ------------------------------ ADD HERE NEW REQUESTS FOR LDAP
				
				default :
					System.out.println("unknown");
					break;
			}
		}
		
		return script;
	}
}
