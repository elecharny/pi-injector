package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jppf.GridClient;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.directory.api.ldap.model.message.SearchScope;

import scripts.AbstractScript;
import scripts.LDAPScript;


@SuppressWarnings("serial")
public class TestNew extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		List<AbstractScript> scriptList = new ArrayList<AbstractScript>();
		
		out.print("<html><head><title>Form</title></head><body><p>");
		out.print(System.getProperty("user.dir") + "<br/>");
		
		String name = StringEscapeUtils.escapeHtml(request.getParameter("form_test_name"));
		if(name == null || name.equals("")) {
			response.setStatus(599);
			//return;
		}
		
		int nbInjectors = request.getParameter("form_test_nb-injectors") != null && !request.getParameter("form_test_nb-injectors").equals("") ? Integer.valueOf(request.getParameter("form_test_nb-injectors")) : -1;
		if(nbInjectors <= 0) {
			response.setStatus(598);
			//return;
		}
		
		int nbThreads = request.getParameter("form_test_nb-threads") != null && !request.getParameter("form_test_nb-threads").equals("") ? Integer.valueOf(request.getParameter("form_test_nb-threads")) : -1;
		if(nbThreads <= 0) {
			response.setStatus(597);
			//return;
		}
		
		int durationValue = request.getParameter("form_test_duration-value") != null && !request.getParameter("form_test_duration-value").equals("") ? Integer.valueOf(request.getParameter("form_test_duration-value")) : -1;
		if(durationValue <= 0) {
			response.setStatus(596);
			//return;
		}
		
		String durationUnit = request.getParameter("form_test_duration-unit");
		if(durationUnit == null || durationUnit.equals("")) {
			response.setStatus(595);
			//return;
		}
		
		String protocol = request.getParameter("form_test_protocol");
		if(protocol == null || protocol.equals("")) {
			response.setStatus(594);
			//return;
		}
		
		out.print(name + ", " + nbInjectors + ", " + nbThreads + ", " + durationValue + ", " + durationUnit + ", " + protocol + ", ");
		
		if(protocol != null && protocol.equals("LDAP")) {
			String servername = request.getParameter("form_test_servername");
			int port = request.getParameter("form_test_port") != null && !request.getParameter("form_test_port").equals("") ? Integer.valueOf(request.getParameter("form_test_port")) : -1;
			String dn = request.getParameter("form_test_dn");
			if(!dn.isEmpty() && !dn.endsWith(","))
				dn += ",";
			String username = request.getParameter("form_test_username");
			String password = request.getParameter("form_test_password");
			int nbPlan = request.getParameter("form_test_nb-plan") != null && !request.getParameter("form_test_nb-plan").equals("") ? Integer.valueOf(request.getParameter("form_test_nb-plan")) : -1;
			
			LDAPScript script = new LDAPScript(servername, port);
			
			out.print(servername + ", " + port + ", " + dn + ", " + username + ", " + password + ", " + nbPlan + ", ");
			
			for(int i = 1; i <= nbPlan; i++) {
				String action = request.getParameter("form_test_plan_action-" + i);
				
				out.print(action + ", ");
				
				switch(action) {
					case "add" :
						String entryDnAdd = request.getParameter("form_test_plan_entry-dn-" + i);
						script.addAddRequest(dn + entryDnAdd);
						out.print(dn + entryDnAdd);
						break;
					
					case "bind" :
						script.addBindRequest(username, password);
						break;
					
					case "bind-unbind" :
						//TODO
						script.addBindRequest(username, password);
						script.addUnbindRequest();
						break;
					
					case "compare" :
						String entryDnCompare = request.getParameter("form_test_plan_entry-dn-" + i);
						String filterCompare = request.getParameter("form_test_plan_filter-" + i);
						//TODO
						out.print(dn + entryDnCompare + ", " + filterCompare + ", ");
						break;
					
					case "delete" :
						String entryDnDelete = request.getParameter("form_test_plan_entry-dn-" + i);
						script.addDeleteRequest(dn + entryDnDelete);
						out.print(dn + entryDnDelete + ", ");
						break;
					
					case "modify" :
						String entryDnModify = request.getParameter("form_test_plan_entry-dn-" + i);
						String attributeModify = request.getParameter("form_test_plan_attribute-" + i);
						String valueModify = request.getParameter("form_test_plan_value-" + i);
						String opcodeModify = request.getParameter("form_test_plan_opcode-" + i);
						//TODO
						out.print(dn + entryDnModify + ", " + attributeModify + ", " + dn + valueModify + ", " + opcodeModify + ", ");
						break;
					
					case "rename" :
						String oldEntryDnRename = request.getParameter("form_test_plan_old-entry-dn-" + i);
						String newEntryDnRename = request.getParameter("form_test_plan_new-entry-dn-" + i);
						//TODO
						out.print(dn + oldEntryDnRename + ", " + dn + newEntryDnRename + ", ");
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
						script.addSearchRequest(dn + baseSearch, filterSearch, scopeSearch);
						out.print(dn + baseSearch + ", " + filterSearch + ", " + scopeSearch + ", ");
						break;
					
					case "unbind" :
						script.addUnbindRequest();
						break;
					
					default :
						out.print("unknown");
						break;
				}
			}
			
			scriptList.add(script);
		} // LDAP
		
		GridClient client = new GridClient();
		client.launchScriptList(scriptList);
		
		out.print("</p></body></html>");
		out.close();
	}
}
