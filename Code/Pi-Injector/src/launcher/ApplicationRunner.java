package launcher;

import java.util.List;

import org.jppf.client.JPPFClient;
import org.jppf.client.JPPFJob;
import org.jppf.node.protocol.Task;

import resultsData.LDAPResults;
import resultsData.LDAPResults.RequestData;

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
		
		for (Task<?> task : results) {
			String taskName = task.getId();
			
			if (task.getThrowable() != null) {
				System.out.println(
						taskName + 
						", an exception was raised: " + 
						task.getThrowable ().getMessage());
			}
			else {
				System.out.println(
						taskName +
						", execution result: \n");
				
				LDAPResults ldapResults = (LDAPResults)task.getResult();
				
				for (RequestData data : ldapResults.getRequestExecutionTimeList()) {
					System.out.println(
							data.getScriptCounter() + ", " + 
							data.getRequestType() + ", " + 
							data.getRequestExecutionTime());
				}
				
				System.out.println("Total execution time : " +
							ldapResults.getTotalExecutionTime());
			}
		}
	}
}
