package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class TestRemove extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("json");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		File file = new File(".." + File.separator + "tests-results" + File.separator + request.getParameter("file"));
		if(file.exists()) {
			try {
				if(!file.delete())
					out.print("{\"success\":\"false\"}");
			}
			catch(Exception e) {
				e.printStackTrace();
				response.setStatus(599);
				return;
			}
		}
		out.print("{\"success\":\"true\"}");
		out.close();
	}
}
