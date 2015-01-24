package servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

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
		while(parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			System.out.print(paramName + " : ");

			String[] paramValues = request.getParameterValues(paramName);
			for(int i = 0; i < paramValues.length; i++) {
				String paramValue = paramValues[i];
				System.out.println(paramValue + "\t");
			}

		}
		
		response.setContentType("html");
		response.setCharacterEncoding("utf-8");
		
		String name = StringEscapeUtils.escapeHtml(request.getParameter("form_test_name")).trim();
		if(name == null || name.equals("")) {
			response.setStatus(599);
			//return;
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		name = df.format(new Date()) + "_" + name;
		
		int nbInjectors = request.getParameter("form_test_nb-injectors") != null && !request.getParameter("form_test_nb-injectors").equals("") ? Integer.valueOf(request.getParameter("form_test_nb-injectors")) : -1;
		if(nbInjectors <= 0) {
			response.setStatus(598);
			//return;
		}
		
		if(GridClient.getInstance().refreshNodesCount() <= 0) {
			response.setStatus(597);
			//return;
		}
		
		int iterations = request.getParameter("form_test_iterations") != null && !request.getParameter("form_test_iterations").equals("") ? Integer.valueOf(request.getParameter("form_test_iterations")) : -1;
		if(iterations <= 0) {
			response.setStatus(596);
			//return;
		}
		
		String protocol = request.getParameter("form_test_protocol");
		if(protocol == null || protocol.equals("")) {
			response.setStatus(594);
			//return;
		}
		
		if(protocol != null && protocol.equals("LDAP")) {
			AbstractScript script = LDAPScriptBuilder.getScript(request);
			GridClient.getInstance().launchScript(script, name, nbInjectors, iterations, request.getServletContext());
		}
		// ----------------------------------------- ADD NEW PROTOCOL USAGE HERE
		
		request.getRequestDispatcher("/index.jsp?page=test-running").forward(request, response);
	}
}
