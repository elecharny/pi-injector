package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import scripts.AbstractScript;
import scripts.LDAPScript;
import jppf.GridClient;

/**
 * Servlet implementation class MyServlet
 */
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyServlet() {
    	super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<AbstractScript> scriptList = new ArrayList<>();
		
		LDAPScript script = new LDAPScript("192.168.1.28", 10389);
		script.addBindRequest("uid=admin,ou=system", "secret");
		
		scriptList.add(script);
		
		System.out.println("Launching Grid client...");
		GridClient client = new GridClient();
		System.out.println("Grid client launched.");
		
		System.out.println("Launching stress test...");
		client.launchScriptList(scriptList);
		System.out.println("Stress test successfull !!!");
	}

}
