package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jppf.GridClient;

import org.apache.commons.lang.StringEscapeUtils;

import scripts.AbstractScript;
import servlet.protocol.LDAPScriptBuilder;


@SuppressWarnings("serial")
public class TestNew extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			System.out.print(paramName + " : ");

			String[] paramValues = request.getParameterValues(paramName);
			for (int i = 0; i < paramValues.length; i++) {
				String paramValue = paramValues[i];
				System.out.println(paramValue + "\t");
			}

		}
		
		response.setContentType("html");
		response.setCharacterEncoding("utf-8");
		
		List<AbstractScript> scriptList = new ArrayList<AbstractScript>();
		
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
		
		if(protocol != null && protocol.equals("LDAP")) {
			scriptList.add(LDAPScriptBuilder.getScript(request));
		}
		// ----------------------------------------- ADD NEW PROTOCOL USAGE HERE
		
		GridClient.getInstance().launchScriptList(scriptList);
	}
}
