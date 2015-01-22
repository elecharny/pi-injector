package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class TestRunning extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("json");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		out.print("{ \"tests\":[");
		StringBuilder sb = new StringBuilder();
		@SuppressWarnings("unchecked")
		Map<String, Double> tests = (Map<String, Double>) request.getServletContext().getAttribute("TestProgress");
		for(Entry<String, Double> test : tests.entrySet()) {
			sb.append("\"" + test.getKey() + "\":\"" + test.getValue() + "\",");
		}
		out.print(sb.toString().substring(0, sb.length() - 1));
		out.print("]}");
		
		out.close();
	}
}
