package main.java.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;


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
		
		out.print("<html><head><title>Form</title></head><body><p>Params {<br/>");
		Enumeration<String> parameterNames = request.getParameterNames();
		while(parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			out.write(paramName);
			out.write("<br/>");

			String[] paramValues = request.getParameterValues(paramName);
			for (int i = 0; i < paramValues.length; i++) {
				String paramValue = paramValues[i];
				out.write("- " + paramValue + "<br/>");
			}
		}

		out.print("<br/>}</p></body></html>");
		out.close();
		return;
		
		
		/*
		
		String name = StringEscapeUtils.escapeHtml(request.getParameter("form_test_name"));
		if(name == null || name.equals("")) {
			response.setStatus(599);
			//return;
		}
		
		int nbInjectors = Integer.valueOf(request.getParameter("form_test_nb_injectors"));
		if(nbInjectors <= 0) {
			response.setStatus(598);
			//return;
		}
		
		int nbThreads = Integer.valueOf(request.getParameter("form_test_nb_threads"));
		if(nbThreads <= 0) {
			response.setStatus(597);
			//return;
		}
		
		int duration = Integer.valueOf(request.getParameter("form_test_duration"));
		if(duration <= 0) {
			response.setStatus(596);
			//return;
		}
		
		String protocol = request.getParameter("form_test_protocol");
		if(protocol == null || protocol.equals("")) {
			response.setStatus(595);
			//return;
		}
		
		out.print(name + ", " + nbInjectors + ", " + nbThreads + ", " + duration + ", " + protocol);
		
		if(protocol != null && protocol.equals("LDAP")) {
			String servername = request.getParameter("form_test_servername");
			int port = Integer.valueOf(request.getParameter("form_test_port"));
			String dn = request.getParameter("form_test_dn");
			String username = request.getParameter("form_test_username");
			String password = request.getParameter("form_test_password");
			int nbPlan = Integer.valueOf(request.getParameter("form_test_nb-plan"));
			
			out.print(servername + ", " + port + ", " + dn + ", " + username + ", " + password + ", " + nbPlan);
			
			for(int i = 0; i < nbPlan; i++) {
				String action = request.getParameter("form_test_plan_action-" + i);
				
				out.print(action);
				
				switch(action) {
					case "add" :
						String entryDnAdd = request.getParameter("form_test_plan_entry-dn-" + i);
						out.print(entryDnAdd);
						break;
					
					case "bind" :
						break;
					
					case "bind-unbind" :
						break;
					
					case "compare" :
						String entryDnCompare = request.getParameter("form_test_plan_entry-dn-" + i);
						String filterCompare = request.getParameter("form_test_plan_filter-" + i);
						out.print(entryDnCompare + ", " + filterCompare);
						break;
					
					case "delete" :
						String entryDnDelete = request.getParameter("form_test_plan_entry-dn-" + i);
						out.print(entryDnDelete);
						break;
					
					case "modify" :
						String entryDnModify = request.getParameter("form_test_plan_entry-dn-" + i);
						String attributeModify = request.getParameter("form_test_plan_attribute-" + i);
						String valueModify = request.getParameter("form_test_plan_value-" + i);
						String opcodeModify = request.getParameter("form_test_plan_opcode-" + i);
						out.print(entryDnModify + ", " + attributeModify + ", " + valueModify + ", " + opcodeModify);
						break;
					
					case "rename" :
						String oldEntryDnRename = request.getParameter("form_test_plan_old-entry-dn-" + i);
						String newEntryDnRename = request.getParameter("form_test_plan_new-entry-dn-" + i);
						out.print(oldEntryDnRename + ", " + newEntryDnRename);
						break;
					
					case "search" :
						String baseSearch = request.getParameter("form_test_plan_base-" + i);
						String filterSearch = request.getParameter("form_test_plan_filter-" + i);
						String scopeSearch = request.getParameter("form_test_plan_scope-" + i);
						out.print(baseSearch + ", " + filterSearch + ", " + scopeSearch);
						break;
					
					case "unbind" :
						break;
				}
			}
		} // LDAP
		
		out.print("</p></body></html>");
		out.close();*/
	}
}
