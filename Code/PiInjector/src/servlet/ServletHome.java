package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ServletHome extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet");
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String[] st = {
				"index.jsp",
				"/index.jsp",
				"./index.jsp",
				"../index.jsp",
				"WebContent/index.jsp",
				"/WebContent/index.jsp",
				"./WebContent/index.jsp",
				"../WebContent/index.jsp"
		};
		for(String s : st) {
			try {
				ServletContext sc = this.getServletContext();
				RequestDispatcher rd = sc.getRequestDispatcher(s);
				rd.forward(request, response);
				System.out.println("SUCCESS " + s + " ?");
				//request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
			catch(Exception e) {
				System.err.println("ERROR " + s);
			}
		}
		
		out.close();
	}
}
