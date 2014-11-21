package launcher;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jppf.client.JPPFClient;
import org.jppf.client.JPPFJob;
import org.jppf.node.protocol.Task;

import resultsData.DataContainer;
import resultsData.FileDataPrinter;
import resultsData.RequestTimer;
import scripts.LDAPScript.LDAPRequestType;

public class ApplicationRunner {


	public void					executeNonBlockingJob(
			final JPPFClient	jppfClient,
			final JPPFJob		jppfJob
			) throws Exception {

		jppfJob.setBlocking(false);

		jppfClient.submitJob(jppfJob);

		// Do something here...

		List<Task<?>> results = jppfJob.awaitResults();

		processExecutionResults(jppfJob.getName(), results);
	}


	public void					executeBlockingJob(
			final JPPFClient	jppfClient,
			final JPPFJob		jppfJob
			) throws Exception {

		jppfJob.setBlocking(true);

		List<Task<?>> results = jppfClient.submitJob(jppfJob);

		processExecutionResults(jppfJob.getName(), results);
	}


	/**
	 * Process results for a job
	 * @param jobName
	 * @param results
	 */
	public synchronized void	processExecutionResults(
			final String		jobName,
			final List<Task<?>>	results
			) {

		System.out.printf("Results for job '%s' :\n", jobName);

		// on va créé des liste pour chaque graphe voulu et on va les save dans un fichier
		TreeMap<Integer, Integer> agregation = new TreeMap<Integer, Integer>();
		long minCurrentTime = 0;

		for (Task<?> task : results) {
			String taskName = task.getId();
			if (task.getThrowable() != null) {
				System.out.println(
						taskName +
						", an exception was raised: " +
						task.getThrowable().getMessage());
			}
			else {
				System.out.println(
						taskName +
						", execution result: \n");

				@SuppressWarnings("unchecked")
				ArrayList<RequestTimer<LDAPRequestType>> ldapResults = (ArrayList<RequestTimer<LDAPRequestType>>) task.getResult();
				for (RequestTimer<LDAPRequestType> data : ldapResults) {
					if(minCurrentTime != 0 && minCurrentTime >= data.getStartTime()){
						minCurrentTime = data.getStartTime();
					}
					if(minCurrentTime == 0){
						minCurrentTime = data.getStartTime();
					}
					break;
				}
			}
		}



		for (Task<?> task : results) {
			ArrayList<DataContainer> listData = new ArrayList<DataContainer>();
			String taskName = task.getId();

			if (task.getThrowable() != null) {
				System.out.println(
						taskName + 
						", an exception was raised: " + 
						task.getThrowable().getMessage());
			}
			else {
				System.out.println(
						taskName +
						", execution result: \n");

				@SuppressWarnings("unchecked")
				ArrayList<RequestTimer<LDAPRequestType>> ldapResults = (ArrayList<RequestTimer<LDAPRequestType>>) task.getResult();


				long min = 0;
				int nbRequestSec = 0;
				int nbSec = 0;
				Boolean flag = true;
				Boolean first = true;

				for (RequestTimer<LDAPRequestType> data : ldapResults) {

					if(!first){
						if(flag){
							min = data.getStartTime()/1000000000L;
							flag = false;
						}

						if(data.getStartTime()/1000000000L <= min){
							nbRequestSec++;
						}else{
							listData.add(new DataContainer(nbRequestSec, nbSec));
							//writer.println(nbSec + ";" + nbRequestSec);
							if(agregation.containsKey(nbSec)){
								agregation.put(nbSec, agregation.get(nbSec) + nbRequestSec);
							}else{
								agregation.put(nbSec, nbRequestSec);
							}
							nbRequestSec = 1;
							nbSec += data.getStartTime()/1000000000L - min;
							min =  data.getStartTime()/1000000000L;
						}
					}else{
						first = false;

						//Va nous servire pour pouvoir agréger les données, car la premiére valeur est le currenttime
						if(minCurrentTime < data.getStartTime()){
							nbSec = (int) ((data.getStartTime() - minCurrentTime)/1000L);
						}
					}
				}
				// On lance l'écriture du fichier
				new Thread(new FileDataPrinter(listData, task.getId())).start();
			}
		}
		// maintenant on crée le fichier total
		PrintWriter writer;

		try {
			writer = new PrintWriter("resultatFinal" + ".txt", "UTF-8");
			for(Map.Entry<Integer,Integer> entry : agregation.entrySet()) {
				writer.println(entry.getKey() + ";" + entry.getValue());
			}
			writer.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}


	}
}
