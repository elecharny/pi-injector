package launcher;

import injectionTasks.LDAPInjectionTask;

import org.jppf.client.JPPFClient;
import org.jppf.client.JPPFClientConnection;
import org.jppf.client.JPPFJob;
import org.jppf.management.JMXDriverConnectionWrapper;
import org.jppf.management.JPPFDriverAdminMBean;
import org.jppf.utils.JPPFConfiguration;
import org.jppf.utils.TypedProperties;

import scripts.LDAPScript;


public class Launcher {

	public static void main(String[] args) {
		
		System.out.println("Launching...");

		try (JPPFClient jppfClient = new JPPFClient()) {
			int nbNodes = 0;
			
			JPPFClientConnection conn = jppfClient.getClientConnection();
			System.out.println(conn);
			JMXDriverConnectionWrapper driverJmx = conn.getConnectionPool().getJmxConnection();
			nbNodes = (Integer)driverJmx.invoke(JPPFDriverAdminMBean.MBEAN_NAME, "nbNodes");
			
			/*try (JMXDriverConnectionWrapper jmxConn = new JMXDriverConnectionWrapper("localhost", 11198)) {
				jmxConn.connectAndWait(500L);
				nbNodes = (Integer)jmxConn.invoke(JPPFDriverAdminMBean.MBEAN_NAME, "nbNodes");
			}*/
			
			System.out.println("NOMBRE DE NOEUDS : " + nbNodes);
			
			/*
			ApplicationRunner runner = new ApplicationRunner();
			
			JPPFJob jppfJob = new JPPFJob();
			jppfJob.setName("LDAP Injection Job");
			
			LDAPScript ldapScript = new LDAPScript("192.168.1.28", 10389);
			ldapScript.addBindRequest("uid=admin,ou=system", "secret");
			
			for (int i = 0; i < nbNodes; i++) {
				LDAPInjectionTask jppfTask = new LDAPInjectionTask(ldapScript);
				jppfTask.setId(jppfJob.getName() + " - Task " + i);
				jppfJob.add(jppfTask, (Object[])null);
			}
			
			
			runner.executeBlockingJob(jppfClient, jppfJob);
			*/
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
