package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;


public class Launch {
	public static String getResourcePath(String fileName) {
		return new File(".").getAbsolutePath() + File.separator + fileName;
	}
	
	
	public static File getResource(String fileName) {
		return new File(getResourcePath(fileName));
	}
	
	
	public static List<String> readFile(File file) throws Exception {
		List<String> result = new ArrayList<String>();
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		for(String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
			result.add(line);
		}
		
		fileReader.close();
		bufferedReader.close();
		
		return result;
	}
	
	
	public static void main(String[] args) {
		try {
			// cpu: system, user
			String[] search = {"system", "user"};
			
			//String fileName = "../Log_serveur/glances.csv";
			String[] fileName = {
					"../../injector-tests/1_agent/essai_1/Log_serveur/glances.csv",
					"../../injector-tests/1_agent/essai_2/Log_serveur/glances.csv",
					"../../injector-tests/1_agent/essai_3/Log_serveur/glances.csv",
					"../../injector-tests/2_agents/essai_1/Log_serveur/glances.csv",
					"../../injector-tests/2_agents/essai_2/Log_serveur/glances.csv",
					"../../injector-tests/2_agents/essai_3/Log_serveur/glances.csv",
					"../../injector-tests/3_agents/essai_1/Log_serveur/glances.csv",
					"../../injector-tests/3_agents/essai_2/Log_serveur/glances.csv",
					"../../injector-tests/3_agents/essai_3/Log_serveur/glances.csv",
					"../../injector-tests/4_agents/essai_1/Log_serveur/glances.csv",
					"../../injector-tests/4_agents/essai_2/Log_serveur/glances.csv",
					"../../injector-tests/4_agents/essai_3/Log_serveur/glances.csv"
			};
			
			for(String fn : fileName) {
				File file = getResource(fn);
				List<String> lines = readFile(file);
				
				List<String> result = new ArrayList<String>();
				StringBuilder header = new StringBuilder("t;");
				for(int i = 0; i < search.length; i++) {
					header.append(search[i] + ";");
				}
				result.add(header.toString());
				
				int t = 0;
				// pour chaque ligne du CSV
				for(int i1 = 0; i1 < lines.size(); i1++) {
					// si la ligne est un commentaire
					if(lines.get(i1).startsWith("#")) {
						StringBuilder line = new StringBuilder();
						
						// pour chaque information recherchée
						for(int i2 = 0; i2 < search.length; i2++) {
							// si le commentaire contient une information recherchée
							if(lines.get(i1).contains(search[i2])) {
								String[] temp1 = lines.get(i1).split(" ");
								String[] temp2 = temp1[2].split("\\|");
								
								for(int i3 = 0; i3 < temp2.length; i3++) {
									if(temp2[i3].equals(search[i2])) {
										String[] temp3 = lines.get(i1 + 1).split(",");
										line.append(temp3[i3].replaceAll("\\.", ",") + ";");
									}
								}
							}
						}
						
						if(!line.toString().isEmpty())
							result.add((t++) + ";" + line.toString());
						
						i1++;
					}
				}
				
				File newFile = new File(fn.substring(0, fn.length() - "glances.csv".length()) + "glances-result.csv");
				newFile.createNewFile();
				Writer writer = new FileWriter(newFile);
				for(String s : result) {
					writer.write(s + "\n");
					//System.out.println(s);
				}
				writer.close();
				System.out.println(fn + " done.");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
