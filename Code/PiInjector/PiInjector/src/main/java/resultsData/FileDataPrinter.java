package resultsData;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;


public class FileDataPrinter implements Runnable {
	private List<DataContainer> listData;
	private String nameFile;


	public FileDataPrinter(List<DataContainer> list, String nameFile){
		this.listData = list;
		this.nameFile = nameFile;
	}
	
	
	@Override
	public void run() {
		PrintWriter writer;
		try {
			writer = new PrintWriter(nameFile + ".txt", "UTF-8");
			for(DataContainer data : listData) {
				writer.println(data.getNbSec() + ";" + data.getNbRequest());
			}
			writer.close();
		}
		catch(FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
