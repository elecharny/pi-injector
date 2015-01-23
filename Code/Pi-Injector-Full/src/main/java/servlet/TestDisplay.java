package servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class TestDisplay extends HttpServlet {
	// Colors used for the chart
	String[] colors = {
			"220,20,60", //crimson
			"50,205,50", //lime green
			"30,144,255", //dodger blue
			"255,215,0", //gold
			"135,206,235", //sky blue
			"238,130,238", //violet
			"210,105,30", //chocolate
			"240,230,140", //khaki
			"255,192,203", //pink
			"255,0,0", //red
			"255,255,0", //yellow
			"0,255,0", //lime
			"0,255,255", //cyan
			"0,0,255", //blue
			"255,0,255", //magenta
			"128,128,128" //grey
	};
	
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("json");
		response.setCharacterEncoding("utf-8");
		
		File file = new File(".." + File.separator + "tests-results" + File.separator + request.getParameter("file"));
		if(file.exists()) {
			try {
				List<String> lines = readFile(file);
				if(lines.size() <= 0)
					throw new Exception();
				
				String[] firstLine = lines.get(0).split(";");
				int nbColumns = firstLine.length;
				
				StringBuilder[] data = new StringBuilder[nbColumns];
				for(int i = 0; i < data.length; i++)
					data[i] = new StringBuilder();
				
				double form_display_average = 0.0;
				int form_display_seconds_1 = request.getParameter("form_display_seconds-1") != null && !request.getParameter("form_display_seconds-1").equals("") ? Math.max(Integer.valueOf(request.getParameter("form_display_seconds-1")), 0) : 0;
				int form_display_seconds_2 = request.getParameter("form_display_seconds-2") != null && !request.getParameter("form_display_seconds-2").equals("") ? Math.min(Integer.valueOf(request.getParameter("form_display_seconds-2")), lines.size() - 1) : lines.size() - 1;
				if(form_display_seconds_1 > form_display_seconds_2) {
					int tmp = form_display_seconds_1;
					form_display_seconds_1 = form_display_seconds_2;
					form_display_seconds_2 = tmp;
				}
				
				for(int i = 1, max = lines.size(); i < max; i++) {
					String[] line = lines.get(i).split(";");
					if(i % 60 == 0)
						data[0].append("\"" + (i / 60) + "\",");
					else
						data[0].append("\"\",");
					
					for(int j = 1; j < nbColumns; j++) {
						data[j].append("\"" + line[j] + "\",");
						if(j + 1 == nbColumns && form_display_seconds_1 <= i && i <= form_display_seconds_2)
							form_display_average += Double.valueOf(line[j]);
					}
				}
				form_display_average /= (form_display_seconds_2 - form_display_seconds_1);
				
				PrintWriter out = response.getWriter();
				out.print("{\"labels\":[" + data[0].substring(0, data[0].length() - 1) + "],");
				out.print("\"datasets\":[");
				if(nbColumns == 3) {
					String color = colors[0];
					out.print("{"
							+ "\"label\":\"Injector 1\","
							+ "\"fillColor\":\"rgba(" + color + ",0.2)\","
							+ "\"strokeColor\":\"rgb(" + color + ")\","
							+ "\"pointColor\":\"rgb(" + color + ")\","
							+ "\"pointStrokeColor\":\"#fff\","
							+ "\"pointHighlightFill\":\"#fff\","
							+ "\"pointHighlightStroke\":\"rgb(" + color + ")\","
							+ "\"data\":[" + data[1].substring(0, data[1].length() - 1) + "]"
							+ "}"
					);
				}
				else {
					for(int i = 1; i < nbColumns; i++) {
						String color = (i != (nbColumns - 1)) ? colors[i % colors.length] : "0,0,0";
						String label = (i + 1 < nbColumns) ? "Injector " + i : "All injectors";
						out.print("{"
								+ "\"label\":\"" + label + "\","
								+ "\"fillColor\":\"rgba(" + color + ",0.2)\","
								+ "\"strokeColor\":\"rgb(" + color + ")\","
								+ "\"pointColor\":\"rgb(" + color + ")\","
								+ "\"pointStrokeColor\":\"#fff\","
								+ "\"pointHighlightFill\":\"#fff\","
								+ "\"pointHighlightStroke\":\"rgb(" + color + ")\","
								+ "\"data\":[" + data[i].substring(0, data[i].length() - 1) + "]"
								+ "}"
						);
						if(i + 1 < nbColumns)
							out.print(",");
					}
				}
				out.print("],");
				out.print("\"form_display_average\":\"" + form_display_average + "\",");
				out.print("\"form_display_seconds-1\":\"" + form_display_average + "\",");
				out.print("\"form_display_seconds-2\":\"" + form_display_average + "\"");
				out.print("}");
				out.close();
			}
			catch(Exception e) {
				e.printStackTrace();
				response.setStatus(598);
				return;
			}
		}
		else {
			response.setStatus(599);
		}
	}
	
	public static List<String> readFile(File file) throws Exception {
		List<String> result = new ArrayList<String>();
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		for(String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine())
			result.add(line);
		
		fileReader.close();
		bufferedReader.close();
		
		return result;
	}
}
