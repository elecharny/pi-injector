package launcher;

import injectionTasks.LDAPInjectionTask;

import org.jppf.client.JPPFClient;
import org.jppf.client.JPPFJob;

import scripts.LDAPScript;


public class Launcher {

	public static void main(String[] args) {
		
		System.out.println("Launching...");
		
		try (JPPFClient jppfClient = new JPPFClient()) {
			ApplicationRunner runner = new ApplicationRunner();
			
			JPPFJob jppfJob = new JPPFJob();
			jppfJob.setName("LDAP Injection Job");
			jppfJob.getSLA().setBroadcastJob(true);
			
			LDAPScript ldapScript = new LDAPScript("192.168.1.28", 10389);
			ldapScript.addBindRequest("uid=admin,ou=system", "secret");
			
			
			LDAPInjectionTask jppfTask = new LDAPInjectionTask(ldapScript);
			jppfTask.setId(jppfJob.getName() + " - Task 1");
			jppfJob.add(jppfTask, (Object[])null);
			
			
			runner.executeBlockingJob(jppfClient, jppfJob);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
