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

import resultsData.RequestType;
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
			@SuppressWarnings("unchecked")
			ArrayList<RequestType<LDAPRequestType>> ldapResults = (ArrayList<RequestType<LDAPRequestType>>) task.getResult();
			for (RequestType<LDAPRequestType> data : ldapResults) {
				if(minCurrentTime != 0 && minCurrentTime >= data.getDatetime()){
					minCurrentTime = data.getDatetime();
				}
				if(minCurrentTime == 0){
					minCurrentTime = data.getDatetime();
				}
				break;
			}
		}



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
				ArrayList<RequestType<LDAPRequestType>> ldapResults = (ArrayList<RequestType<LDAPRequestType>>) task.getResult();

				PrintWriter writer;
				try {

					writer = new PrintWriter(task.getId() + ".txt", "UTF-8");

					long min = 0;
					int nbRequestSec = 0;
					int nbSec = 0;
					Boolean flag = true;
					Boolean first = true;

					for (RequestType<LDAPRequestType> data : ldapResults) {

						if(!first){
							if(flag){
								min = data.getDatetime()/1000000000L;
								flag = false;
							}

							if(data.getDatetime()/1000000000L <= min){
								nbRequestSec++;
							}else{
								writer.println(nbSec + ";" + nbRequestSec);
								if(agregation.containsKey(nbSec)){
									agregation.put(nbSec, agregation.get(nbSec) + nbRequestSec);
								}else{
									agregation.put(nbSec, nbRequestSec);
								}
								nbRequestSec = 1;
								nbSec += data.getDatetime()/1000000000L - min;
								min =  data.getDatetime()/1000000000L;
							}
						}else{
							first = false;

							//Va nous servire pour pouvoir agréger les données, car la premiére valeur est le currenttime
							if(minCurrentTime < data.getDatetime()){
								nbSec = (int) ((data.getDatetime() - minCurrentTime)/1000L);
							}
						}
					}	

					writer.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

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
